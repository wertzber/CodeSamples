package jackson2;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonCreator;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by dannyl on 2/5/2015.
 */
@JsonTypeName("msg_accept_status_publish")
public class MsgAcceptStatusPublishPayload extends Entity {

    //@JsonProperty("status")
    @NotNull(message = "Status should not be null")
    private MCCAEnums.Status status;

    @JsonProperty("sequenceList")
    @NotNull(message = "seq list must not be null")
    @Size(min = 1, message = "seq list size should be at least 1")
    private List<Long> sequenceList;


    public MCCAEnums.Status getStatus() {
        return status;
    }

    @JsonCreator
    public void setStatus(MCCAEnums.Status status) {
        this.status = status;
    }

    public List<Long> getSequenceList() {
        return sequenceList;
    }

    public void setSequenceList(List<Long> sequenceList) {
        this.sequenceList = sequenceList;
    }

    public MsgAcceptStatusPublishPayload(){

    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((sequenceList == null) ? 0 : sequenceList.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		MsgAcceptStatusPublishPayload other = (MsgAcceptStatusPublishPayload) obj;
		if (sequenceList == null) {
			if (other.sequenceList != null)
				return false;
		} else if (!sequenceList.equals(other.sequenceList))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MsgAcceptStatusPublishPayload [");
		if (status != null)
			builder.append("status=").append(status).append(", ");
		if (sequenceList != null)
			builder.append("sequenceList=").append(sequenceList);
		builder.append(super.toString()).append("]");
		return builder.toString();
	}
}
