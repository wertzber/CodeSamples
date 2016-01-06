/*
 * Created by dannyl on 1/26/2015.
 */
package hibrnate.validator;

import org.codehaus.jackson.annotate.JsonIgnore;

public class PayloadDto implements Cloneable{
    private String msgSubType;
	
	@JsonIgnore
	public PayloadDto clone() throws CloneNotSupportedException{
		return (PayloadDto)super.clone();
	}


    public String getMsgSubType() {
        return msgSubType;
    }

    public void setMsgSubType(String msgSubType) {
        this.msgSubType = msgSubType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadDto)) return false;
        PayloadDto that = (PayloadDto) o;
        if (msgSubType != null ? !msgSubType.equals(that.msgSubType) : that.msgSubType != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return msgSubType != null ? msgSubType.hashCode() : 0;
    }



}
