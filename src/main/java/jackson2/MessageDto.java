package jackson2;


import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by eladw on 12/3/2014.
 * single entry in db .
 * conv:123::msg:1 -> MessageDto
 */
public class MessageDto {

    @JsonProperty("msgType")
    private String msgType = MCCAEnums.MessageType.MsgDto.getType();

    @JsonProperty("conversationId")
    private String conversationId;

    @JsonProperty("messageId")
    private String messageId;

    @JsonProperty("sequenceNumber")
    private long sequenceNumber;

    @JsonDeserialize(as=MsgDistributePayload.class)
    private Entity data;

    @JsonProperty("messageMetadataDto")
    private MessageMetadataDto messageMetadataDto;

    public MessageDto(){

    }

    public MessageDto(String conversationId, MessageMetadataDto messageMetadataDto, MsgDistributePayload data, long seq){
    	this.messageMetadataDto = messageMetadataDto;
    	this.conversationId = conversationId;
        this.messageId = DaoMsUtils.createMsgKeyFromScratch(conversationId, String.valueOf(seq));
        this.data = data;
        this.sequenceNumber = seq;
    }

    //immutable
    public String getConversationId() {
        return conversationId;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public Entity getData() {
        return data;
    }

    public MessageMetadataDto getMessageMetadataDto() {
        return messageMetadataDto;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDto that = (MessageDto) o;

        if (sequenceNumber != that.sequenceNumber) return false;
        if (conversationId != null ? !conversationId.equals(that.conversationId) : that.conversationId != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (messageMetadataDto != null ? !messageMetadataDto.equals(that.messageMetadataDto) : that.messageMetadataDto != null)
            return false;
        if (msgType != null ? !msgType.equals(that.msgType) : that.msgType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = conversationId != null ? conversationId.hashCode() : 0;
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (int) (sequenceNumber ^ (sequenceNumber >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (messageMetadataDto != null ? messageMetadataDto.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "conversationId='" + conversationId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", data=" + data +
                ", messageMetadataDto=" + messageMetadataDto +
                '}';
    }
}
