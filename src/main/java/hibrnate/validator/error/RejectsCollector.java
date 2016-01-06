package hibrnate.validator.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eladw on 2/3/2015.
 */
public class RejectsCollector {

    List<RejectService> rejects;

    public RejectsCollector() {
        rejects = new ArrayList<>();
    }

    public List<RejectService> getRejects() {
        return rejects;
    }

    public void addReject(RejectService rejectService){
        if(rejectService!=null) rejects.add(rejectService);
    }


}
