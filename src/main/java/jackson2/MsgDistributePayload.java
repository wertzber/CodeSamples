package jackson2;


import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonCreator;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dannyl on 2/5/2015.
 */
public class MsgDistributePayload extends Entity{


    private MCCAEnums.DistributeType distributeType;

    @JsonProperty("sequence")
    private int sequence;

    @JsonProperty("originatorId")
    private String originatorId;

    @JsonProperty("serverTimestamp")
    private long serverTimestamp;

    @JsonProperty("origPayload")
    private Entity origPayload;

    public MCCAEnums.DistributeType getDistributeType() {
        return distributeType;
    }

    @JsonCreator
    public void setDistributeType(MCCAEnums.DistributeType distributeType) {
        this.distributeType = distributeType;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getOriginatorId() {
        return originatorId;
    }

    public void setOriginatorId(String originatorId) {
        this.originatorId = originatorId;
    }

    public long getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(long serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public Entity getOrigPayload() {
        return origPayload;
    }

    public void setOrigPayload(Entity origPayload) {
        this.origPayload = origPayload;
    }


    /**
     *
     * @param userId
     * @param sequence
     * @param serverTimestamp
     * @param entity
     * @throws CloneNotSupportedException
     */
    public void deepCopy(String userId, int sequence, long serverTimestamp,
                         Entity entity) throws CloneNotSupportedException {
        setMsgType(MCCAEnums.MessageType.MsgDistribute.getType());
        // TODO - add option for query
        setDistributeType(MCCAEnums.DistributeType.realtime);
        setSequence(sequence);
        setOriginatorId(userId);
        setServerTimestamp(serverTimestamp);
        setOrigPayload(entity.clone());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((distributeType == null) ? 0 : distributeType.hashCode());
        result = prime * result
                + ((origPayload == null) ? 0 : origPayload.hashCode());
        result = prime * result
                + ((originatorId == null) ? 0 : originatorId.hashCode());
        result = prime * result + sequence;
        result = prime * result
                + (int) (serverTimestamp ^ (serverTimestamp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MsgDistributePayload other = (MsgDistributePayload) obj;
        if (distributeType != other.distributeType)
            return false;
        if (origPayload == null) {
            if (other.origPayload != null)
                return false;
        } else if (!origPayload.equals(other.origPayload))
            return false;
        if (originatorId == null) {
            if (other.originatorId != null)
                return false;
        } else if (!originatorId.equals(other.originatorId))
            return false;
        if (sequence != other.sequence)
            return false;
        if (serverTimestamp != other.serverTimestamp)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MsgDistributePayload [");
        if (distributeType != null)
            builder.append("distributeType=").append(distributeType)
                    .append(", ");
        builder.append("sequence=").append(sequence).append(", ");
        if (originatorId != null)
            builder.append("originatorId=").append(originatorId).append(", ");
        builder.append("serverTimestamp=").append(serverTimestamp).append(", ");
        if (origPayload != null)
            builder.append("origPayload=").append(origPayload);
        builder.append(super.toString()).append("]");
        return builder.toString();
    }

}
