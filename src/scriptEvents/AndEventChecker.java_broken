/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.LinkedList;
import java.util.List;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class AndEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private final LinkedList<EventChecker> andCheckersList = new LinkedList();
    private ScriptEvent lastSuccessfullyChecked = null;

    public void addAndChecker(EventChecker andChecker) {
        if (null != andChecker) {
            this.andCheckersList.add(andChecker);
        }
    }

    @Override
    public void deactivateChecker() {
        for (EventChecker checker : this.andCheckersList) {
            checker.deactivateChecker();
        }
    }

    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        for (EventChecker checker : this.andCheckersList) {
            if (!checker.checkEvent(eventTuple)) {
                return false;
            }
            this.lastSuccessfullyChecked = checker.lastPossetiveChecked();
        }
        return true;
    }

    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    @Override
    public List<ScriptEvent> getExpectantEvent() {
        LinkedList<ScriptEvent> out = new LinkedList<ScriptEvent>();
        for (EventChecker checker : this.andCheckersList) {
            List<ScriptEvent> expected = checker.getExpectantEvent();
            if (null == expected || expected.isEmpty()) continue;
            out.addAll(expected);
        }
        return out;
    }

    @Override
    public String isValid() {
        return null;
    }
}

