package hibrnate.validator;



import hibrnate.annotations.StringAnnotation;

import javax.validation.constraints.NotNull;

/**
 * Created by dannyl on 1/26/2015.
 */
public class MsgPublishPayLoadDto extends PayloadDto {

    @NotNull(message = "content type should not be null")
    @StringAnnotation(value = "text/plain,img" ,message = "content type is wrong")
    private String contentType;

    @NotNull(message = "message should not be null")
    //@Length(min=1, message="msg should have at least 1 char")
    private String message;

    @NotNull(message = "timestamp should not be null")
    //@Length(min=5, message="timestamp should be at least 5 numbers long")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MsgPublishPayLoadDto)) return false;
        if (!super.equals(o)) return false;

        MsgPublishPayLoadDto that = (MsgPublishPayLoadDto) o;

        if (endpointTimestamp != that.endpointTimestamp) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (int) (endpointTimestamp ^ (endpointTimestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SendMCCAPayLoadDto{" +
                "contentType='" + contentType + '\'' +
                ", message='" + message + '\'' +
                ", endpointTimestamp=" + endpointTimestamp +
                '}';
    }
}
