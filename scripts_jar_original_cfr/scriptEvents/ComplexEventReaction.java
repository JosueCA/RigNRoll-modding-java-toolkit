/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import scriptActions.ScriptAction;
import scriptEvents.EventChecker;
import scriptEvents.EventReaction;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ComplexEventReaction
extends EventReaction {
    static final long serialVersionUID = 0L;
    private final LinkedList<ScriptAction> scriptActions = new LinkedList();
    private EventChecker eventChecker = null;

    @Override
    void deactivateReactor() {
        this.eventChecker.deactivateChecker();
    }

    public ComplexEventReaction(EventChecker eventChecker, int uid) {
        super(uid);
        if (null == eventChecker) {
            throw new IllegalArgumentException("eventChecker must be non-null reference");
        }
        this.eventChecker = eventChecker;
    }

    public void addAction(ScriptAction action) {
        if (null != action) {
            this.scriptActions.add(action);
        }
    }

    public void addAction(ScriptAction[] action) {
        if (null != action && 0 < action.length) {
            this.scriptActions.addAll(Arrays.asList(action));
        }
    }

    public void addAction(List<ScriptAction> action) {
        if (null != action && 0 < action.size()) {
            for (ScriptAction act : action) {
                if (null == act) continue;
                this.scriptActions.add(act);
            }
        }
    }

    @Override
    public boolean react(List<ScriptEvent> eventTuple) {
        if (null != this.eventChecker && !eventTuple.isEmpty() && this.eventChecker.checkEvent(eventTuple)) {
            for (ScriptAction action : this.scriptActions) {
                if (null == action) continue;
                action.act();
            }
            return true;
        }
        return false;
    }

    @Override
    public ScriptEvent getLastReacted() {
        return this.eventChecker.lastPossetiveChecked();
    }

    @Override
    public List<ScriptAction> getAllAvalibleReactions() {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>(this.scriptActions);
        for (ScriptAction action : this.scriptActions) {
            if (!action.hasChildAction()) continue;
            out.add(action.getChildAction());
        }
        return out;
    }

    @Override
    public List<ScriptEvent> getAllAvalibleEvents() {
        if (null != this.eventChecker) {
            return this.eventChecker.getExpectantEvent();
        }
        return Collections.emptyList();
    }
}

