/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class OrEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private final LinkedList<EventChecker> orCheckersList = new LinkedList();
    private ScriptEvent lastSuccessfullyChecked = null;
    private Random rndGenerator = null;

    public void addOrChecker(EventChecker orChecker) {
        this.rndGenerator = new Random(System.nanoTime());
        if (null != orChecker) {
            this.orCheckersList.add(orChecker);
        }
    }

    @Override
    public void deactivateChecker() {
        for (EventChecker checker : this.orCheckersList) {
            checker.deactivateChecker();
        }
    }

    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        for (EventChecker checker : this.orCheckersList) {
            if (!checker.checkEvent(eventTuple)) continue;
            this.lastSuccessfullyChecked = checker.lastPossetiveChecked();
            return true;
        }
        return false;
    }

    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    @Override
    public List<ScriptEvent> getExpectantEvent() {
        if (!this.orCheckersList.isEmpty()) {
            return this.orCheckersList.get(this.rndGenerator.nextInt(this.orCheckersList.size())).getExpectantEvent();
        }
        return null;
    }

    @Override
    public String isValid() {
        return null;
    }
}

