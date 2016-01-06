package jackson2;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eladw on 12/3/2014.
 * Meta data Dto
 */
public class MessageMetadataDto {

    public enum MessageType {DATA};

    public enum ContentTypeFormat {
        Text("text/plain"), Img("img");

        String name;

        ContentTypeFormat(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    };


    //Type of message
    @JsonProperty("messageType")
    private MessageType messageType;

    //Content Type
    @JsonProperty("contentTypeFormat")
    private String contentTypeFormat;

    //time received by the server
    @JsonProperty("serverReceivedTime")
    private long serverReceivedTime;

    //who created the original message
    @JsonProperty("origId")
    private String origId;

    public MessageMetadataDto(){

    }

    public MessageMetadataDto(MessageType messageType, String contentType, long serverReceivedTime,
                              String origMemberId){

        this.messageType = messageType;
        this.contentTypeFormat = contentType;

        this.serverReceivedTime = serverReceivedTime;
        this.origId = origMemberId;
    }

    //Immutable
    public MessageType getMessageType() {
        return messageType;
    }

    public String getContentTypeFormat() {
        return contentTypeFormat;
    }

    public long getServerReceivedTime() {
        return serverReceivedTime;
    }

    @Override
    public String toString() {
        return "MessageMetadataDto{" +
                "messageType=" + messageType +
                ", contentTypeFormat=" + contentTypeFormat +
                ", serverReceivedTime=" + serverReceivedTime +
                ", origId='" + origId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageMetadataDto that = (MessageMetadataDto) o;

        if (serverReceivedTime != that.serverReceivedTime) return false;
        if (contentTypeFormat != null ? !contentTypeFormat.equals(that.contentTypeFormat) : that.contentTypeFormat != null)
            return false;
        if (messageType != that.messageType) return false;
        if (origId != null ? !origId.equals(that.origId) : that.origId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageType != null ? messageType.hashCode() : 0;
        result = 31 * result + (contentTypeFormat != null ? contentTypeFormat.hashCode() : 0);
        result = 31 * result + (int) (serverReceivedTime ^ (serverReceivedTime >>> 32));
        result = 31 * result + (origId != null ? origId.hashCode() : 0);
        return result;
    }
}
