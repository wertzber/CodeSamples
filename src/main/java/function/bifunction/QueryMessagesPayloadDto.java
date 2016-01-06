package function.bifunction;


/**
 * Created by dannyl on 1/28/2015.
 */
public class QueryMessagesPayloadDto {

    private int maxQuantity = -1;
    private int olderThanSequence = -1;
    private int newerThanSequence = -1;



    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getOlderThanSequence() {
        return olderThanSequence;
    }

    public void setOlderThanSequence(int olderThanSequence) {
        this.olderThanSequence = olderThanSequence;
    }

    public int getNewerThanSequence() {
        return newerThanSequence;
    }

    public void setNewerThanSequence(int newerThanSequence) {
        this.newerThanSequence = newerThanSequence;
    }


    @Override
    public String toString() {
        return "QueryMessagesPayloadDto{" +
                "maxQuantity=" + maxQuantity +
                ", olderThanSequence=" + olderThanSequence +
                ", newerThanSequence=" + newerThanSequence +
                '}';
    }
}
