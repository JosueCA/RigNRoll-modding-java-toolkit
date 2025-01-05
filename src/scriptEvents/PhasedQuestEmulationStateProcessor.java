/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteState;
import scriptEvents.EventReaction;
import scriptEvents.ScenarioStateNeedMoveEvent;
import scriptEvents.ScriptEvent;
import scriptEvents.UniversalStateProcessor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class PhasedQuestEmulationStateProcessor
extends UniversalStateProcessor {
    // @Override
    public FiniteState processEvent(List<ScriptEvent> event2, LinkedList<FiniteState> avalibleStates, FiniteState currentState) {
        if (null == event2 || null == avalibleStates || null == currentState) {
            ScenarioLogger.getInstance().machineWarning("IsoQuestEmulationStateProcessor.processEvent: invalid arguments");
            return currentState;
        }
        this.logEvent(event2);
        FiniteState newState = currentState;
        for (EventReaction reactor : this.reactionsOnEvents) {
            if (!reactor.react(event2) || !(reactor.getLastReacted() instanceof ScenarioStateNeedMoveEvent)) continue;
            ScenarioStateNeedMoveEvent advanceScenarioEvent = (ScenarioStateNeedMoveEvent)reactor.getLastReacted();
            String newStateName = advanceScenarioEvent.getNodeStringName();
            FiniteState transitionTarget = null;
            for (FiniteState avalibleState : avalibleStates) {
                if (0 != newStateName.compareTo(avalibleState.getName())) continue;
                if (null != transitionTarget) {
                    ScenarioLogger.getInstance().machineWarning("scenario logic error: multiple state choose; current state == " + currentState.getName());
                }
                transitionTarget = avalibleState;
                newState = avalibleState;
            }
            if (null != transitionTarget) continue;
            ScenarioLogger.getInstance().machineWarning("scenario logic error: couldn't find next state with name " + newStateName + "; current state == " + currentState);
        }
        return newState;
    }

    // @Override
    public List<Integer> getDataToSave() {
        return Collections.emptyList();
    }

    // @Override
    public void restoreFromData(List<Integer> savedData) {
    }
}

