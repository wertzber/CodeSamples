package jackson2;

/**
 * Created by eladw on 2/5/2015.
 */
public class MCCAEnums {

    public enum ContentType {
        Text_Plain("text/plain"),Img("img");

        private String name;

        private ContentType(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Status {read, accept};

    public enum DistributeType { query, realtime};

    public enum ChatState {
        active,inactive,gone,composing,pause;
    }


    public enum MessageType {
        ChatStatePublish("chat_state_publish"),
        ChatStateDistribute("chat_state_distribute"),
        MsgDistribute("msg_distribute"),
        MsgAcceptStatusPublish("msg_accept_status_publish"),
        QueryMessages("query_messages"),
        MsgReject("msg_reject"),
        QueryMessagesResponse("query_messages_response"),
        SystemMessage("sysmsg_publish"),
        MsgPublish("msg_publish"),
        MsgDto("msg_dto"),
        convDto("conv_dto"),

        ;


        private String type;

        private MessageType(String type){
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }


}


