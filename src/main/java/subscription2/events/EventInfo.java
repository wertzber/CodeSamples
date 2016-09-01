package subscription2.events;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 2/2/2016
 * Time: 10:18 AM
 */
public class EventInfo {

    private final EventType eventType;
    private final Object oldState;
    private final Object newState;

    public EventInfo(EventType eventType, Object oldState, Object newState) {
        this.eventType = eventType;
        this.oldState = oldState;
        this.newState = newState;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Object getOldState() {
        return oldState;
    }

    public Object getNewState() {
        return newState;
    }
}
