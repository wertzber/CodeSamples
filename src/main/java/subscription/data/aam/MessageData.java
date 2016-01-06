package subscription.data.aam;

import com.liveperson.api.ams.cm.types.ParticipantRole;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * Created by eladw on 7/28/2015.
 * Holds data in couch which is relevant for the message
 */
public class MessageData {

    //last message delivered on the conversation
    private String msg;

    //time of last msg
    protected Long msgTs;

    // role - Consumer / Agent
    protected ParticipantRole msgRole;

    // sequence of last message
    protected Long msgSequence;

    protected String msgOrigId;

    public MessageData() {
        this(null, null, null, "", "");
    }

    @GeneratePojoBuilder
    public MessageData(Long lastMsgTs, ParticipantRole lastMsgRole,
                       Long lastMsgSequence, String lastMsg,String origId) {
        this.msg = lastMsg;
        this.msgTs = lastMsgTs;
        this.msgRole = lastMsgRole;
        this.msgSequence = lastMsgSequence;
        this.msgOrigId = origId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getMsgTs() {
        return msgTs;
    }

    public void setMsgTs(Long msgTs) {
        this.msgTs = msgTs;
    }

    public ParticipantRole getMsgRole() {
        return msgRole;
    }

    public void setMsgRole(ParticipantRole msgRole) {
        this.msgRole = msgRole;
    }

    public Long getMsgSequence() {
        return msgSequence;
    }

    public void setMsgSequence(Long msgSequence) {
        this.msgSequence = msgSequence;
    }

    public String getMsgOrigId() {
        return msgOrigId;
    }

    public void setMsgOrigId(String msgOrigId) {
        this.msgOrigId = msgOrigId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageData{");
        sb.append("msg='").append(msg).append('\'');
        sb.append(", msgTs=").append(msgTs);
        sb.append(", msgRole=").append(msgRole);
        sb.append(", msgSequence=").append(msgSequence);
        sb.append('}');
        return sb.toString();
    }
}
