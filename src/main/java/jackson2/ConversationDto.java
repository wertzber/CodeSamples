package jackson2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eladw on 1/26/2015.
 */
public class ConversationDto extends BaseMsDalDto{

    private String conversationId;
    private List<String> messageIds;

    public ConversationDto(){

    }

    public ConversationDto(String conversationId, int versionInput) {
        this.conversationId = conversationId;
        super.setVersion(versionInput);

        //TODO - consider other structure
        this.messageIds = new ArrayList<>();
    }

    public void addMessageId(String newMsgId){
        if(newMsgId!=null) messageIds.add(newMsgId);
    }

    public void addMessageId(List<String> newMsgList){
        if(newMsgList!=null) messageIds.addAll(newMsgList);
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String id) {
        this.conversationId = id;
    }


    public List<String> getMessageIds() {
        return messageIds;
    }


    @Override
    public String toString() {
        return "ConversationDto{" +
                "conversationId='" + conversationId + '\'' +
                ", version=" + super.getVersion() +
                ", messageIds=" + messageIds +
                '}';
    }
}
