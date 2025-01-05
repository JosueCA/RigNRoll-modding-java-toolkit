/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.EventStateProcessor;
import scenarioMachine.FiniteState;
import scriptActions.ScriptAction;
import scriptEvents.EventReaction;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class UniversalStateProcessor
implements EventStateProcessor {
    final LinkedList<EventReaction> reactionsOnEvents = new LinkedList();

    UniversalStateProcessor() {
    }

    public final void addReaction(EventReaction reaction) {
        if (null != reaction) {
            this.reactionsOnEvents.add(reaction);
        }
    }

    void logEvent(List<ScriptEvent> event2) {
        for (ScriptEvent e : event2) {
            ScenarioLogger.getInstance().machineLog(Level.INFO, "tuple element " + e.toString());
        }
    }

    @Override
    public List<ScriptAction> getAllAvalibleActions(List actionsToCheckForDuplicates) {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();
        for (EventReaction reaction : this.reactionsOnEvents) {
            List<ScriptAction> reactions = reaction.getAllAvalibleReactions();
            for (ScriptAction act : reactions) {
                if (null == act) continue;
                out.add(act);
            }
        }
        return out;
    }

    @Override
    public List<List<ScriptEvent>> getAllAvalibleEvents() {
        LinkedList<List<ScriptEvent>> out = new LinkedList<List<ScriptEvent>>();
        for (EventReaction reaction : this.reactionsOnEvents) {
            List<ScriptEvent> eventTuple = reaction.getAllAvalibleEvents();
            if (null == eventTuple || eventTuple.isEmpty()) continue;
            out.add(eventTuple);
        }
        return out;
    }

    @Override
    public abstract FiniteState processEvent(List<ScriptEvent> var1, LinkedList<FiniteState> var2, FiniteState var3);
}

