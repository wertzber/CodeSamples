package subscription.data.aam;

import com.liveperson.api.ams.cm.types.ParticipantRole;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 8/4/2015
 * Time: 4:59 PM
 */
public class BaseMessage {
    //time of last msg
    protected Long lastMsgTs;
    // role - Consumer / Agent
    protected ParticipantRole lastMsgRole;
    // sequence of last message
    protected Long lastMsgSequence;

    public BaseMessage() {
        this(null,null,null);
    }

    @GeneratePojoBuilder
    public BaseMessage(Long lastMsgTs, ParticipantRole lastMsgRole, Long lastMsgSequence) {
        this.lastMsgTs = lastMsgTs;
        this.lastMsgRole = lastMsgRole;
        this.lastMsgSequence = lastMsgSequence;
    }

    public Long getLastMsgTs() {
        return lastMsgTs;
    }

    public void setLastMsgTs(Long lastMsgTs) {
        this.lastMsgTs = lastMsgTs;
    }

    public ParticipantRole getLastMsgRole() {
        return lastMsgRole;
    }

    public void setLastMsgRole(ParticipantRole lastMsgRole) {
        this.lastMsgRole = lastMsgRole;
    }

    public Long getLastMsgSequence() {
        return lastMsgSequence;
    }

    public void setLastMsgSequence(Long lastMsgSequence) {
        this.lastMsgSequence = lastMsgSequence;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "msgTs=" + lastMsgTs +
                ", msgRole='" + lastMsgRole + '\'' +
                ", msgSequence=" + lastMsgSequence +
                '}';
    }
}
