/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;
import scriptEvents.VoidEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class VoidEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private VoidEvent exactEvent = null;
    private ScriptEvent lastPossetiveChecked = null;
    private boolean is_expected = true;

    public VoidEventChecker(VoidEvent exactEvent, boolean is_expected) {
        this.exactEvent = exactEvent;
        this.is_expected = is_expected;
    }

    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.exactEvent) {
            return true;
        }
        for (ScriptEvent event2 : eventTuple) {
            VoidEvent voidEvent;
            if (!(event2 instanceof VoidEvent) || (voidEvent = (VoidEvent)event2).getInfo().compareTo(this.exactEvent.getInfo()) != 0) continue;
            this.lastPossetiveChecked = event2;
            return true;
        }
        return false;
    }

    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastPossetiveChecked;
    }

    @Override
    public List<ScriptEvent> getExpectantEvent() {
        if (!this.is_expected) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<ScriptEvent> res = new ArrayList<ScriptEvent>();
        res.add(this.exactEvent);
        return res;
    }

    @Override
    public String isValid() {
        return this.exactEvent == null ? "exactEvent is null" : null;
    }

    @Override
    public void deactivateChecker() {
    }
}

