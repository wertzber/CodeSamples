package subscription.data.aam;

import jackson2.MCCAEnums;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExtendedConversation {
    private AamConversation aamConversation;
    private EttrData ettrData;
    private MessageData messageData;


    // user1 -> read -> baseMessage
    //       -> accept -> baseMessage
    // maps userIds to a map of message status and it's data (timestamp, sequence, role, etc.)
    private Map<String, Map<MCCAEnums.Status, BaseMessage>> messageAcceptStatusTracker;
    private final Lock mapCreateLock = new ReentrantLock();

    @GeneratePojoBuilder(withCopyMethod = true)
    public ExtendedConversation(AamConversation aamConversation, EttrData ettrData, MessageData messageData, Map<String, Map<MCCAEnums.Status, BaseMessage>> messageAcceptStatusTracker) {
        this.messageAcceptStatusTracker = messageAcceptStatusTracker;
        this.aamConversation = aamConversation;
        this.ettrData = ettrData;
        this.messageData = messageData;
    }

    public ExtendedConversation() {
        this(null, null, null, null);
    }

    public AamConversation getAamConversation() {
        return aamConversation;
    }

    public void setAamConversation(AamConversation conversation) {
        this.aamConversation = conversation;
    }

    public EttrData getEttrData() {
        return ettrData;
    }

    public void setEttrData(EttrData ettrData) {
        this.ettrData = ettrData;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    public void upsertMessageAcceptStatus(String userId, MCCAEnums.Status status, BaseMessage baseMessage) {
        if (messageAcceptStatusTracker == null) {
            synchronized (mapCreateLock) {
                if (messageAcceptStatusTracker == null) {
                    // currently 2 supported participants so map is initialized to 2
                    messageAcceptStatusTracker = new ConcurrentHashMap<>(2);
                }
            }
        }

        Map<MCCAEnums.Status, BaseMessage> statusBaseMessageMap = messageAcceptStatusTracker.get(userId);
        // first synchronization for internal map creation if not exists
        if (statusBaseMessageMap == null) {
            synchronized (mapCreateLock) {
                if (statusBaseMessageMap == null) {
                    // currently only 2 known status types (read, accept) so initial map size is 2
                    statusBaseMessageMap = new ConcurrentHashMap<>(2);
                    messageAcceptStatusTracker.put(userId, statusBaseMessageMap);
                }
            }
        }
        // second synchronization for insert updated status
        synchronized (statusBaseMessageMap) {
            statusBaseMessageMap.put(status, baseMessage);
        }
    }

    // TODO return a clone when predicates start running on accept status
    public Map<String, Map<MCCAEnums.Status, BaseMessage>> getMessageAcceptStatusTracker() {
        return messageAcceptStatusTracker;
    }

    @Override
    public String toString() {
        return "ExtendedConversation{" +
                "aamConversation=" + aamConversation +
                ", ettrData=" + ettrData +
                ", messageData=" + messageData +
                ", messageAcceptStatusTracker=" + messageAcceptStatusTracker +
                ", mapCreateLock=" + mapCreateLock +
                '}';
    }
}
