package hibrnate.validator.error;

/**
 * Created by eladw on 2/4/2015.
 */
public class RejectService {

    private RejectCodes rejectCode;
    private String rejectInfo;

    public RejectService(RejectCodes rejectCode, String rejectInfo) {
        this.rejectCode = rejectCode;
        this.rejectInfo = rejectInfo;
    }

    public String getRejectInfo() {
        return rejectInfo;
    }

    public RejectCodes getRejectCode() {
        return rejectCode;
    }
}
