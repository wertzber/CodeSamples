package function.bifunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Created by eladw on 1/19/2015.
 * @author eladw
 * @author igors
 */
//TODO - dal isn't a service, we;; change it later
public class ConversationDaoMapImpl {
    private static Logger logger = LoggerFactory.getLogger(ConversationDaoMapImpl.class);
    
    private Map<String, Dto> conversationMap = new ConcurrentHashMap<>();
    private Map<String, List<Dto>> persistData = new ConcurrentHashMap<>();

    private static final int MAX_QUERY = 10;

    private Map<Byte, BiFunction<QueryMessagesPayloadDto,String,List<Dto>>> queryMapper = new ConcurrentHashMap<>();

    private static byte GET_ALL = 0x0;
    private static byte GET_QUANTITY = 0x1;
    private static byte GET_OLDER_THEN = 0x3;
    private static byte GET_NEWER_THEN = 0x5;

    public ConversationDaoMapImpl() {
        queryMapper.put(GET_ALL, (dto, conversationId)->getLastMessages(conversationId, MAX_QUERY));
        queryMapper.put(GET_QUANTITY, (dto, conversationId)->getLastMessages(conversationId, dto.getMaxQuantity()));
        queryMapper.put(GET_OLDER_THEN, (dto, conversationId)->getOlderThanMessages(conversationId, dto.getMaxQuantity(),dto.getOlderThanSequence()));
        queryMapper.put(GET_NEWER_THEN, (dto, conversationId)->getNewerThanMessages(conversationId, dto.getMaxQuantity(), dto.getNewerThanSequence()));
     }


    public void addConversation(String conversationId) {
    	if(!conversationMap.containsKey(conversationId)){
    		conversationMap.put(conversationId, null);
    	}
    }



    public void addMessages(String conversationId, List<Dto> dtos) {
        try {
            List<Dto> lines  = persistData.get(conversationId);
            if(lines == null){
                lines = new ArrayList<Dto>();
                persistData.put(conversationId, lines);
            }
            lines.addAll(dtos);
            logger.info("Conversation: " + conversationId + " Save msg " + dtos);
        } catch (Exception e){
            logger.error("Failed to save msg " + dtos + " conversation: " + conversationId +" " + e);
        }
    }

    public List<Dto> getMessages(String conversationId, QueryMessagesPayloadDto dto) {
        byte type = 0;
        if(dto.getMaxQuantity() != -1){
            type = 0x1;
        }
        if(dto.getOlderThanSequence() != -1){
        	type |= 0x1 << 1;
        }
        if(dto.getNewerThanSequence() != -1){
        	type |= 0x1 << 2;
        }
        return queryMapper.get(type).apply(dto,conversationId);
    }

    public List<Dto> getAllMessages(String conversationId) {
        return getLastMessages(conversationId, MAX_QUERY);
    }

/////////////////////////////////////////////////////////////////////////////////////////////
// Private Implementation



    /**
     *
     * @param conversationId
     * @param numOfMessages
     * @return
     */
    private List<Dto> getLastMessages(String conversationId, int numOfMessages) {
    	List<Dto> list = persistData.get(conversationId);
    	if(list == null){
    		logger.error("Missing list of the messages for the conversationid:" + conversationId);
    		return null;
    	}
    	if(list.isEmpty()){
    		logger.error("The list is empty for the conversationid:" + conversationId);
    		return null;
    	}
    	int amount = getFromAmount(list, numOfMessages, 0);
    	return list.subList(list.size() - amount, list.size());
    }

    /**
     *
     * @param conversationId
     * @param numOfMessages
     * @param olderThan
     * @return
     */
   private List<Dto> getOlderThanMessages(String conversationId, int numOfMessages, int olderThan) {
       	List<Dto> list = persistData.get(conversationId);
    	if(list == null){
    		logger.error("Missing list of the messages for the conversationid:" + conversationId);
    		return null;
    	}
    	if(!checkRange(list, olderThan)){
    		logger.error("invalid olderThan:" + olderThan + " for the conversationid:" + conversationId);
    		return null;
    	}
    	int amount = getToAmount(list, numOfMessages, olderThan);
    	return list.subList(olderThan - amount, olderThan);
    }


    private List<Dto> getNewerThanMessages(String conversationId, int numOfMessages, int newerThan) {
        List<Dto> list = persistData.get(conversationId);
        if(list == null){
            logger.error("Missing list of the messages for the conversationid:" + conversationId);
            return null;
        }
        if(!checkRange(list, newerThan)){
            logger.error("invalid newerThan:" + newerThan + " for the conversationid:" + conversationId);
            return null;
        }
        int amount = getFromAmount(list, numOfMessages, newerThan);
        return list.subList(newerThan, newerThan + amount);
    }
    
    /**
     * 
     * @param list
     * @param index
     * @return
     */
    private boolean checkRange(List<Dto> list, int index){
    	return index >= 0 && index < list.size();
    }
    
    /**
     * 
     * @param list
     * @param numOfMessages
     * @param start
     * @return
     */
    private int getFromAmount(List<Dto> list, int numOfMessages, int start){
    	int amount = list.size() - start;
    	if(numOfMessages > amount){
    		numOfMessages = amount;
    	}
    	return numOfMessages;
    }

    /**
     *
     * @param list
     * @param numOfMessages
     * @param end
     * @return
     */
    private int getToAmount(List<Dto> list, int numOfMessages, int end){
        int amount = end;
        if(numOfMessages > amount){
            numOfMessages = amount;
        }
        return numOfMessages;
    }


}
