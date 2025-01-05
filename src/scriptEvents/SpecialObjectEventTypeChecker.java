/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.List;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;
import scriptEvents.SpecialObjectEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class SpecialObjectEventTypeChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private SpecialObjectEvent.EventType expected = SpecialObjectEvent.EventType.any;
    private ScriptEvent lastSuccessfullyChecked = null;

    public SpecialObjectEventTypeChecker(SpecialObjectEvent.EventType expected) {
        this.expected = expected;
    }

    // @Override
    public void deactivateChecker() {
    }

    // @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof SpecialObjectEvent) || ((SpecialObjectEvent)event2).getEventType() != this.expected) continue;
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
        out.add(new SpecialObjectEvent("unknown", 0, this.expected));
        return out;
    }

    // @Override
    public String isValid() {
        if (null == this.expected) {
            return "expected is null";
        }
        return null;
    }
}

