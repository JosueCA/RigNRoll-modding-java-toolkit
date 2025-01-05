/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.List;
import scenarioUtils.StringToSOTypeConverter;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;
import scriptEvents.SpecialObjectEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class SpecialObjectEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private static final String NULL_NAME = "null name";
    private static final String UNKNOWN_NAME = "unknown";
    private int soType = 0;
    private String name = "null name";
    private String type = "unknown";
    private SpecialObjectEvent.EventType eventType = SpecialObjectEvent.EventType.any;
    private ScriptEvent lastSuccessfullyChecked = null;

    // @Override
    public void deactivateChecker() {
    }

    public SpecialObjectEventChecker(String name, String type, SpecialObjectEvent.EventType eventType) {
        this.name = name;
        this.eventType = eventType;
        this.soType = StringToSOTypeConverter.convert(type);
    }

    public SpecialObjectEventChecker(SpecialObjectEvent.EventType eventType) {
        this.eventType = eventType;
    }

    // @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (0 == this.soType) {
            this.soType = StringToSOTypeConverter.convert(this.type);
        }
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof SpecialObjectEvent)) continue;
            SpecialObjectEvent soEvent = (SpecialObjectEvent)event2;
            if (0 != this.name.compareToIgnoreCase(NULL_NAME) && 0 != soEvent.getObjectName().compareToIgnoreCase(this.name) || soEvent.getSoType() != this.soType || this.eventType != soEvent.getEventType()) continue;
            this.lastSuccessfullyChecked = event2;
            return true;
        }
        return false;
    }

    // @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    // @Override
    public List<ScriptEvent> getExpectantEvent() {
        ArrayList<ScriptEvent> out = new ArrayList<ScriptEvent>(1);
        out.add(new SpecialObjectEvent(this.name, StringToSOTypeConverter.convert(this.type), this.eventType));
        return out;
    }

    // @Override
    public String isValid() {
        if (null == this.name) {
            return "name is null";
        }
        if (null == this.type) {
            return "type is null";
        }
        if (null == this.eventType) {
            return "eventType is null";
        }
        return null;
    }
}

