package subscription.data.aam;

import com.liveperson.api.ams.cm.types.ParticipantRole;
import com.liveperson.api.ams.types.Delay;
import com.liveperson.api.ams.types.TTR;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * Created by eladw on 7/28/2015.
 * Holds data in couch which is relevant for the ettr
 */
public class EttrData {

    //Last ettr calc - meaning long of 11:00 13/8 2009
    private Long lastEttr;

    //Last msg Seq, for example if consumer send 1,2,3 we save only 1
    private Long lastUnrespondedSeq;

    //Time of last msg form consumer or Agent, for example if consumer send 1,2,3 we save only time of 1
    private Long lastUnrespondedTs;

    //role agent or consumer
    private ParticipantRole lastEttrRole;

    //Last time ettr recv conv update as a trigger. It will be used for ha to understand if the ttr and delay are updated
    //use case: 11:30 last conv update
    //          11:40 note
    //          11:45 update ttr
    //only the change in 11:45 should trigger ettr
    private Long lastConvUpdate;

    private TTR lastTtr;

    private Delay lastDelay;

    private Long lastManualTtr;

    private String brandId;

    @GeneratePojoBuilder
    public EttrData(Long lastEttr, Long lastUnrespondedSeq, Long lastUnrespondedTs, ParticipantRole lastEttrRole, Long lastConvUpdate, TTR lastTtr, Delay lastDelay,
                    Long lastManualTtr, String brandId) {
        this.lastEttr = lastEttr;
        this.lastUnrespondedSeq = lastUnrespondedSeq;
        this.lastUnrespondedTs = lastUnrespondedTs;
        this.lastEttrRole = lastEttrRole;
        this.lastConvUpdate = lastConvUpdate;
        this.lastTtr = lastTtr;
        this.lastDelay = lastDelay;
        this.lastManualTtr = lastManualTtr;
        this.brandId = brandId;
    }

    public EttrData(){
        this(null,null,null,null,null,null,null,null,null);
    }

    /**
     * copy constructor
     * @param other the EttrData to copy from
     */
    public EttrData(EttrData other) {
        this.lastEttr = other.lastEttr;
        this.lastUnrespondedSeq = other.lastUnrespondedSeq;
        this.lastEttrRole = other.lastEttrRole;
        this.lastConvUpdate = other.lastConvUpdate;
        TTR copyTTr = new TTR(other.lastTtr.ttrType, other.lastTtr.value);
        if (other.lastDelay != null)
            this.lastDelay = new Delay(other.lastDelay.type, other.lastDelay.tillWhen);
        else
            this.lastDelay = null;
        this.lastTtr = copyTTr;
        this.lastManualTtr = other.lastManualTtr;
        this.lastUnrespondedTs = other.lastUnrespondedTs;
        this.brandId = other.brandId;
    }

    public Long getLastEttr() {
        return lastEttr;
    }

    public void setLastEttr(Long ettr) {
        this.lastEttr = ettr;
    }

    public Long getLastUnrespondedSeq() {
        return lastUnrespondedSeq;
    }

    public void setLastUnrespondedSeq(Long lastUnrespondedSeq) {
        this.lastUnrespondedSeq = lastUnrespondedSeq;
    }

    public Long getLastUnrespondedTs() {
        return lastUnrespondedTs;
    }

    public void setLastUnrespondedTs(Long lastUnrespondedTs) {
        this.lastUnrespondedTs = lastUnrespondedTs;
    }

    public ParticipantRole getLastEttrRole() {
        return lastEttrRole;
    }

    public void setLastEttrRole(ParticipantRole lastEttrRole) {
        this.lastEttrRole = lastEttrRole;
    }

    public Long getLastConvUpdate() {
        return lastConvUpdate;
    }

    public void setLastConvUpdate(Long lastConvUpdate) {
        this.lastConvUpdate = lastConvUpdate;
    }

    public TTR getLastTtr() {
        return lastTtr;
    }

    public void setLastTtr(TTR lastTtr) {
        this.lastTtr = lastTtr;
    }

    public Delay getLastDelay() {
        return lastDelay;
    }

    public void setLastDelay(Delay lastDelay) {
        this.lastDelay = lastDelay;
    }

    public Long getLastManualTtr() {
        return lastManualTtr;
    }

    public void setLastManualTtr(Long lastManualTtr) {
        this.lastManualTtr = lastManualTtr;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}
