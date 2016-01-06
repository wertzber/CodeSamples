/*
 * Created by dannyl on 1/26/2015.
 */
package hibrnate.validator;

import java.io.Serializable;

public class Dto implements Serializable{

    private long msgId;
    private String msgType;

    public Dto() {
    }

    public Dto(long msgId) {
        this.msgId = msgId;
    }

    public Dto(String msgType) {
        this.msgType = msgType;
    }

    public Dto(long msgId, String msgType, PayloadDto payload) {
        this.msgId = msgId;
        this.msgType = msgType;
        this.payload = payload;
    }

    private PayloadDto payload;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }


    public PayloadDto getPayload() {
        return payload;
    }

    public void setPayload(PayloadDto payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dto)) return false;

        Dto dto = (Dto) o;

        if (msgId != dto.msgId) return false;
        if (msgType != null ? !msgType.equals(dto.msgType) : dto.msgType != null) return false;
        if (payload != null ? !payload.equals(dto.payload) : dto.payload != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (msgId ^ (msgId >>> 32));
        result = 31 * result + (msgType != null ? msgType.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Dto{" +
                "msgId=" + msgId +
                ", msgType='" + msgType + '\'' +
                ", payload=" + payload +
                '}';
    }
}
