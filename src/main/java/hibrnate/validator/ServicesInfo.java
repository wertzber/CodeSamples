package hibrnate.validator;



import hibrnate.validator.error.RejectsCollector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eladw on 2/2/2015.
 * Holds the source and update dto and gather services results
 */
public class ServicesInfo {

    public enum ServicesTypes {Sequence, Validator, Store, Multicast, Unicast, Reject};

    //must
    private Dto sourceDto;
    private Dto updateDto;

    //optional
    private boolean isLastSuccess;
    private ServicesTypes lastServiceType;

    private String conversationId;

    private Map<ServicesTypes, Object> servicesResults = new ConcurrentHashMap<ServicesTypes, Object>();

    private RejectsCollector rejectsCollector;

    public ServicesInfo(Dto sourceDto, String conversationId) {
        this.sourceDto = sourceDto;
        this.conversationId = conversationId;
        this.rejectsCollector = new RejectsCollector();
    }

    //Immutable
    public Dto getSourceDto() {
        return sourceDto;
    }
    public String getConversationId() {
        return conversationId;
    }


    public Dto getUpdateDto() {
        return updateDto;
    }

    public void setUpdateDto(Dto updateDto) {
        this.updateDto = updateDto;
    }

    public boolean isLastSuccess() {
        return isLastSuccess;
    }

    public ServicesInfo setLastSuccess(boolean isLastSuccess) {
        this.isLastSuccess = isLastSuccess;
        return this;
    }

    public ServicesTypes getLastServiceType() {
        return lastServiceType;
    }

    public ServicesInfo setLastServiceType(ServicesTypes lastServiceType) {
        this.lastServiceType = lastServiceType;
        return this;
    }

    public Map<ServicesTypes, Object> getServicesResults() {
        return servicesResults;
    }

    public void addServiceResults(ServicesTypes key, Object value) {
        if (key != null && value != null) {
            this.servicesResults.put(key, value);
        }
    }

    public RejectsCollector getRejectsCollector() {
        return rejectsCollector;
    }


}
