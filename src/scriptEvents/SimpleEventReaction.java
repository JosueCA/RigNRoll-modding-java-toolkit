/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

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
public final class SimpleEventReaction
extends EventReaction {
    static final long serialVersionUID = 0L;
    private ScriptAction scriptAction = null;
    private EventChecker eventChecker = null;

    @Override
    void deactivateReactor() {
        this.eventChecker.deactivateChecker();
    }

    public SimpleEventReaction(ScriptAction scriptAction, EventChecker eventChecker, int uid) {
        super(uid);
        if (null == eventChecker) {
            throw new IllegalArgumentException("eventChecker must be non-null reference");
        }
        this.scriptAction = scriptAction;
        this.eventChecker = eventChecker;
    }

    @Override
    public boolean react(List<ScriptEvent> eventTuple) {
        if (null != eventTuple && !eventTuple.isEmpty() && this.eventChecker.checkEvent(eventTuple)) {
            if (null != this.scriptAction) {
                this.scriptAction.act();
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
        if (null != this.scriptAction) {
            LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();
            out.add(this.scriptAction);
            if (this.scriptAction.hasChildAction()) {
                out.add(this.scriptAction.getChildAction());
            }
            return out;
        }
        return Collections.emptyList();
    }

    @Override
    public List<ScriptEvent> getAllAvalibleEvents() {
        if (null != this.eventChecker) {
            return this.eventChecker.getExpectantEvent();
        }
        return Collections.emptyList();
    }
}

