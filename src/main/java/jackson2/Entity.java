package jackson2;


import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonSubTypes;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;

/**
 * Created by dannyl on 2/5/2015.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "msgType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MsgPublishPayLoad.class, name = "msg_publish"),
        @JsonSubTypes.Type(value = MsgAcceptStatusPublishPayload.class, name = "msg_accept_status_publish"),
        @JsonSubTypes.Type(value = MsgDistributePayload.class, name = "msg_distribute") })

public class Entity implements Cloneable{
    //TODO -add validation of msgType format
    @NotNull(message = "msg type should not be null")
    private String msgType;

    @JsonProperty("msgType")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Entity clone() throws CloneNotSupportedException{
		return (Entity)super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (msgType == null) {
			if (other.msgType != null)
				return false;
		} else if (!msgType.equals(other.msgType))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entity [msgType=").append(msgType).append("]");
		return builder.toString();
	}
	
	
}
