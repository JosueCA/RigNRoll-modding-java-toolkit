/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ExactEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private ScriptEvent exactEvent = null;
    private ScriptEvent lastPossetiveChecked = null;
    private boolean is_expected = true;

    public ExactEventChecker(ScriptEvent exactEvent, boolean is_expected) {
        this.exactEvent = exactEvent;
        this.is_expected = is_expected;
    }

    // @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.exactEvent) {
            return true;
        }
        for (ScriptEvent event2 : eventTuple) {
            if (!event2.equals(this.exactEvent)) continue;
            this.lastPossetiveChecked = event2;
            return true;
        }
        return false;
    }

    // @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastPossetiveChecked;
    }

    // @Override
    public List<ScriptEvent> getExpectantEvent() {
        if (!this.is_expected) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<ScriptEvent> res = new ArrayList<ScriptEvent>();
        res.add(this.exactEvent);
        return res;
    }

    // @Override
    public String isValid() {
        return this.exactEvent == null ? "exactEvent is null" : null;
    }

    // @Override
    public void deactivateChecker() {
    }
}

