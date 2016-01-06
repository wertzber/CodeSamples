package jackson2;

/**
 * Created by adamsh on 2/5/2015.
 */
public class DaoMsFactory {


    private static GenericDao<String,ConversationDto> conversationDao;
    private static GenericDao<String,MessageDto> messageDao;


    private DaoMsFactory(){

    }

    public static GenericDao<String, ConversationDto> getConversationDao() {
        if(conversationDao!=null) return conversationDao;
        else {
            return conversationDao;
        }
    }

    public static GenericDao<String, MessageDto> getMessageDao() {
        if (messageDao != null) return messageDao;
        else {
            return messageDao;
        }
    }


}
