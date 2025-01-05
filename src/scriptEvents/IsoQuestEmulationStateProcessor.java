/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteState;
import scriptActions.ScenarioAnalysisMarkAction;
import scriptActions.ScriptAction;
import scriptActions.SingleStepScenarioAdvanceAction;
import scriptEvents.EventReaction;
import scriptEvents.ScenarioStateNeedMoveEvent;
import scriptEvents.ScriptEvent;
import scriptEvents.UniversalStateProcessor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class IsoQuestEmulationStateProcessor
extends UniversalStateProcessor {
    private boolean transitionDone = false;
    private String implicitNodeName = null;
    private List<Integer> doneReactions = new LinkedList<Integer>();

    private static FiniteState findState(List<FiniteState> avalibleStates, String name) throws MultipleChoseException, NodeNotFoundException {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }
        FiniteState found = null;
        for (FiniteState candidate : avalibleStates) {
            if (0 != name.compareTo(candidate.getName())) continue;
            if (null != found) {
                throw new MultipleChoseException("scenario logic error: multiple state choose " + name);
            }
            found = candidate;
            break;
        }
        if (null != found) {
            return found;
        }
        throw new NodeNotFoundException("can't find node with name " + name);
    }

    public IsoQuestEmulationStateProcessor(String implicitStepNextNodeName) {
        this.implicitNodeName = implicitStepNextNodeName;
    }

    public void setImplicitStepNextNodeName(String implicitStepNextNodeName) {
        this.implicitNodeName = implicitStepNextNodeName;
    }

    // @Override
    public FiniteState processEvent(List<ScriptEvent> event2, LinkedList<FiniteState> avalibleStates, FiniteState currentState) {
        if (null == event2 || null == avalibleStates || null == currentState) {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "IsoQuestEmulationStateProcessor.processEvent: invalid arguments");
            return currentState;
        }
        this.logEvent(event2);
        FiniteState newState = currentState;
        boolean atLeastOneReacted = false;
        ListIterator iter = this.reactionsOnEvents.listIterator();
        while (iter.hasNext()) {
            EventReaction reactor = (EventReaction)iter.next();
            if (!reactor.react(event2)) continue;
            if (0 != reactor.getUid()) {
                this.doneReactions.add(reactor.getUid());
            }
            atLeastOneReacted = true;
            if (reactor.getLastReacted() instanceof ScenarioStateNeedMoveEvent) {
                try {
                    String newNodeName = ((ScenarioStateNeedMoveEvent)reactor.getLastReacted()).getNodeStringName();
                    newState = IsoQuestEmulationStateProcessor.findState(avalibleStates, newNodeName);
                    this.transitionDone = true;
                }
                catch (MultipleChoseException exception) {
                    ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
                }
                catch (NodeNotFoundException exception) {
                    ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
                }
                continue;
            }
            reactor.deactivateReactor();
            iter.remove();
        }
        boolean effectiveReactorsEmpty = true;
        for (EventReaction reaction : this.reactionsOnEvents) {
            if (null == reaction.getAllAvalibleReactions() || reaction.getAllAvalibleReactions().isEmpty()) continue;
            effectiveReactorsEmpty = false;
            break;
        }
        if (!this.transitionDone && effectiveReactorsEmpty && atLeastOneReacted && null != this.implicitNodeName) {
            try {
                newState = IsoQuestEmulationStateProcessor.findState(avalibleStates, this.implicitNodeName);
            }
            catch (MultipleChoseException exception) {
                ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
            }
            catch (NodeNotFoundException exception) {
                ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
            }
        }
        return newState;
    }

    private boolean checkForDuplicateOfImplicitStep(List actionsToCheckForDuplicates) {
        for (Object genericAction : actionsToCheckForDuplicates) {
            SingleStepScenarioAdvanceAction action;
            String destination;
            if (!(genericAction instanceof SingleStepScenarioAdvanceAction) || !this.implicitNodeName.contains(destination = (action = (SingleStepScenarioAdvanceAction)genericAction).getName())) continue;
            return true;
        }
        return false;
    }

    // @Override
    public List<ScriptAction> getAllAvalibleActions(List actionsToCheckForDuplicates) {
        List<ScriptAction> actions = super.getAllAvalibleActions(actionsToCheckForDuplicates);
        if (null != this.implicitNodeName) {
            if (this.checkForDuplicateOfImplicitStep(actions) || this.checkForDuplicateOfImplicitStep(actionsToCheckForDuplicates)) {
                return actions;
            }
            if (0 != this.implicitNodeName.length()) {
                actions.add(new ScenarioAnalysisMarkAction(this.implicitNodeName));
            }
        }
        return actions;
    }

    // @Override
    public List<Integer> getDataToSave() {
        return Collections.unmodifiableList(this.doneReactions);
    }

    // @Override
    public void restoreFromData(List<Integer> savedData) {
        if (null == savedData) {
            throw new IllegalArgumentException("savedData must be non-null");
        }
        for (Integer reactionUid : savedData) {
            ListIterator iter = this.reactionsOnEvents.listIterator();
            while (iter.hasNext()) {
                EventReaction reaction = (EventReaction)iter.next();
                if (reactionUid.intValue() != reaction.getUid()) continue;
                reaction.deactivateReactor();
                iter.remove();
            }
        }
    }

    public String getImplicitNodeName() {
        return this.implicitNodeName;
    }

    private static final class NodeNotFoundException
    extends Exception {
        static final long serialVersionUID = 1L;

        NodeNotFoundException() {
        }

        NodeNotFoundException(String message) {
            super(message);
        }
    }

    private static final class MultipleChoseException
    extends Exception {
        static final long serialVersionUID = 1L;

        MultipleChoseException() {
        }

        MultipleChoseException(String message) {
            super(message);
        }
    }
}

