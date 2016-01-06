package jackson2;

/**
 * Created by eladw on 3/9/2015.
 */
public class BaseMsDalDto {

    private int version;

    public BaseMsDalDto(){

    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BaseMsDalDto{" +
                "version=" + version +
                '}';
    }
}
