/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.Collections;
import java.util.List;
import scriptEvents.EventChecker;
import scriptEvents.ScenarioStateNeedMoveEvent;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ScenarioStateMoveEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private int nodeIntName = -1;
    private String nodeStringName = null;
    private ScriptEvent lastSuccessfullyChecked = null;

    // @Override
    public void deactivateChecker() {
    }

    public ScenarioStateMoveEventChecker(String name) {
        this.nodeStringName = name;
    }

    // @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.nodeStringName && -1 == this.nodeIntName) {
            System.err.println("ScenarioStateMoveEventChecker wasn't correctly initialized");
            return false;
        }
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof ScenarioStateNeedMoveEvent)) continue;
            ScenarioStateNeedMoveEvent moveScenarioEvent = (ScenarioStateNeedMoveEvent)event2;
            if (-1 == moveScenarioEvent.getNodeIntName() && null == moveScenarioEvent.getNodeStringName()) {
                System.err.println("ScenarioStateNeedMoveEvent wasn't correctly initialized");
                return false;
            }
            if (0 == moveScenarioEvent.getNodeStringName().compareTo(this.nodeStringName) || null == moveScenarioEvent.getNodeStringName() && this.nodeIntName == moveScenarioEvent.getNodeIntName()) {
                this.lastSuccessfullyChecked = event2;
                return true;
            }
            return false;
        }
        return false;
    }

    // @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    // @Override
    public List<ScriptEvent> getExpectantEvent() {
        return Collections.emptyList();
    }

    // @Override
    public String isValid() {
        return null;
    }
}

