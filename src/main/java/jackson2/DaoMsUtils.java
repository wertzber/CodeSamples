package jackson2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Created by eladw on 3/3/2015.
 */
public class DaoMsUtils {
    private static Logger logger = LoggerFactory.getLogger(DaoMsUtils.class);




    /**
     * get the Query parameters
     * @param conversationId
     * @param dto
     * @return List<Entity>
     */
    /**
     *
     * @param conversationId
     * @param numOfMessages
     * @return
     */
    private static List<String> getLastMessages(String conversationId, int numOfMessages) {
        Optional<ConversationDto> conversationDto = getConversation(conversationId);
        if(conversationDto.isPresent()){
            List<String> msgIds = conversationDto.get().getMessageIds();
            int amount = getFromAmount(msgIds, numOfMessages, 0);
            return msgIds.subList(msgIds.size() - amount, msgIds.size());
        } else {
            logger.warn("getLastMessages fail: conv " + conversationId + " doesn't exists ");
            return null;
        }


    }

    /**
     *
     * @param conversationId
     * @param numOfMessages
     * @param olderThan
     * @return
     */
    private static List<String> getOlderThanMessages(String conversationId, int numOfMessages, int olderThan) {

        Optional<ConversationDto> conversationDto = getConversation(conversationId);
        if(conversationDto.isPresent()){
            List<String> msgIds = conversationDto.get().getMessageIds();
            if(msgIds == null){
                logger.error("Missing list of the messages for the conversationid:" + conversationId);
                return null;
            }
            if(!checkRange(msgIds, olderThan)){
                logger.error("invalid olderThan:" + olderThan + " for the conversationid:" + conversationId);
                return null;
            }
            int amount = getToAmount(numOfMessages, olderThan);
            return msgIds.subList(olderThan - amount, olderThan);

        } else {
            logger.warn("getOlderThanMessages fail: conv doesn't exists " + conversationId);
            return null;
        }

    }


    private static List<String> getNewerThanMessages(String conversationId, int numOfMessages, int newerThan) {

        Optional<ConversationDto> conversationDto = getConversation(conversationId);
        if(conversationDto.isPresent()){
            List<String> msgIds = conversationDto.get().getMessageIds();
            if(msgIds == null || msgIds.isEmpty()){
                logger.error("getLastMessages fail: empty list of the messages for the conversationid:" + conversationId);
                return null;
            }
            if(!checkRange(msgIds, newerThan)){
                logger.error("invalid newerThan:" + newerThan + " for the conversationid:" + conversationId);
                return null;
            }
            int amount = getFromAmount(msgIds, numOfMessages, newerThan);
            return msgIds.subList(newerThan, newerThan + amount);

        } else {
            logger.warn("getLastMessages fail: conv doesn't exists " + conversationId);
            return null;
        }

    }


    private static List<String> getRangeMessages(String conversationId, int numOfMessages, int olderThan, int newerThan) {

        Optional<ConversationDto> conversationDto = getConversation(conversationId);
        if(conversationDto.isPresent()){
            List<String> msgIds = conversationDto.get().getMessageIds();
            if(msgIds == null || msgIds.isEmpty()){
                logger.error("getLastMessages fail: empty list of the messages for the conversationid:" + conversationId);
                return null;
            }
            if(!checkRange(msgIds, newerThan)){
                logger.error("invalid newerThan:" + newerThan + " for the conversationid:" + conversationId);
                return null;
            }
            if(!checkRange(msgIds, olderThan)){
                logger.error("invalid olderthan:" + olderThan + " for the conversationid:" + conversationId);
                return null;
            }
            if(newerThan > olderThan){
                logger.error("invalid parameters:" + olderThan + " greater than newerThan: " + newerThan + " for the conversationid:" + conversationId);
                return null;
            }
            int amount = getRangeAmount(numOfMessages,newerThan, olderThan);
            if(newerThan >= 1) return msgIds.subList(newerThan - 1, newerThan -1 + amount);
            else return msgIds.subList(newerThan, newerThan + amount);

        } else {
            logger.warn("getLastMessages fail: conv doesn't exists " + conversationId);
            return null;
        }
    }

    /**
     *
     * @param list
     * @param index
     * @return
     */
    private static boolean checkRange(List<String> list, int index){
        return index >= 0 && index < list.size();
    }

    /**
     *
     * @param list
     * @param numOfMessages
     * @param start
     * @return
     */
    private static int getFromAmount(List<String> list, int numOfMessages, int start){
        int amount = list.size() - start;
        if(numOfMessages > amount){
            numOfMessages = amount;
        }
        return numOfMessages;
    }

    /**
     *
     * @param numOfMessages
     * @param end
     * @return
     */
    private static int getToAmount(int numOfMessages, int end){
        int amount = end;
        if(numOfMessages > amount){
            numOfMessages = amount;
        }
        return numOfMessages;
    }


    private static int getRangeAmount(int numOfMessages, int start, int end){
        int amount = end - start + 1;
        if(numOfMessages > amount){
            numOfMessages = amount;
        }
        return numOfMessages;
    }

    /**
     * Create key for message.
     * format: conv:123::msg:1
     * @param key conversation id
     * @param msgId msg seq id
     * @return formulate key
     */
    public static String createMsgKeyFromScratch(String key, String msgId){
        return "conv:" + key + "::msg:" + msgId;
    }

    public static String addMsgToConv(String conv, String msgId){
        return conv + "::msg:" + msgId;
    }

    /**
     * Create key for conversation
     * @param key conversation id
     * @return formulate key
     */
    public static String createConvKey(String key){
        return "conv:" + key;
    }



    private static Optional<ConversationDto> getConversation(String conversationId){
        Optional<ConversationDto> conversationDto = null;
        String keyToSearch = createConvKey(conversationId);

        return conversationDto;
    }





}
