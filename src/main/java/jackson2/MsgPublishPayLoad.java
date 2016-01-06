package jackson2;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Created by dannyl on 2/5/2015.
 */
public class MsgPublishPayLoad extends Entity {

    @NotNull(message = "content type should not be null")
    @StringAnnotation(value = "text/plain,img", message = "wrong content type")
    private String contentType;

    @NotNull(message = "message should not be null")
    private String message;

    //date epoch 2015 10-2
    @Min(1423519053)
    private long endpointTimestamp;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getEndpointTimestamp() {
        return endpointTimestamp;
    }

    public void setEndpointTimestamp(long endpointTimestamp) {
        this.endpointTimestamp = endpointTimestamp;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ (int) (endpointTimestamp ^ (endpointTimestamp >>> 32));
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		MsgPublishPayLoad other = (MsgPublishPayLoad) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (endpointTimestamp != other.endpointTimestamp)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MsgPublishPayLoad [");
		if (contentType != null)
			builder.append("contentType=").append(contentType).append(", ");
		if (message != null)
			builder.append("message=").append(message).append(", ");
		builder.append("endpointTimestamp=").append(endpointTimestamp).
		append(super.toString()).append("]");
		return builder.toString();
	}
}
