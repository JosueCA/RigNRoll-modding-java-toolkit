/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.EventStateProcessor;
import scenarioMachine.FsmActionListener;
import scriptActions.ScriptAction;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FiniteState
implements Comparable {
    private final LinkedList<FiniteState> nextStates = new LinkedList();
    private final LinkedList<FsmActionListener> stateChangedListeners = new LinkedList();
    private EventStateProcessor eventProcessor = null;
    private String stateName = null;
    private boolean isLeaved = false;
    private boolean isEntered = false;
    public static final int DEFAULT_STATE_NOM = -1;

    public FiniteState(EventStateProcessor processor, String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }
        this.eventProcessor = processor;
        this.stateName = name;
    }

    public void setEventProcessor(EventStateProcessor processor) {
        if (null == processor) {
            throw new IllegalArgumentException("processor must be non-null reference");
        }
        this.eventProcessor = processor;
    }

    public final String getName() {
        return this.stateName;
    }

    public final void addNextState(FiniteState state) {
        if (null == state) {
            throw new IllegalArgumentException("state must be non-null reference");
        }
        this.nextStates.add(state);
    }

    public final LinkedList<FiniteState> getNextStates() {
        return this.nextStates;
    }

    public final FiniteState process(List<ScriptEvent> eventTuple) {
        ScenarioLogger.getInstance().machineLog(Level.FINEST, this.stateName + " state: processing event tuple");
        if (null == this.eventProcessor) {
            return this;
        }
        for (FsmActionListener listener : this.stateChangedListeners) {
            ScenarioLogger.getInstance().machineLog(Level.FINEST, "listener \"" + listener.getClass().getName() + "\" called");
            listener.stateProcessed(this);
        }
        return this.eventProcessor.processEvent(eventTuple, this.nextStates, this);
    }

    public final void entered() {
        if (!this.isEntered) {
            this.isEntered = true;
            ScenarioLogger.getInstance().machineLog(Level.FINEST, this.stateName + " state entered");
            for (FsmActionListener listener : this.stateChangedListeners) {
                ScenarioLogger.getInstance().machineLog(Level.FINEST, listener.getClass().getName() + " listener");
                listener.stateEntered(this);
            }
        }
    }

    public final void leaved() {
        if (!this.isLeaved) {
            this.isLeaved = true;
            ScenarioLogger.getInstance().machineLog(Level.FINEST, this.stateName + " state leaved");
            for (FsmActionListener listener : this.stateChangedListeners) {
                ScenarioLogger.getInstance().machineLog(Level.FINEST, listener.getClass().getName() + " listener");
                listener.stateLeaved(this);
            }
        }
    }

    public void addStateChangedListener(FsmActionListener listener) {
        if (null != listener) {
            this.stateChangedListeners.add(listener);
        }
    }

    public List<ScriptAction> getAllAvalibleActions() {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();
        for (FsmActionListener stateListener : this.stateChangedListeners) {
            out.addAll(stateListener.getAllAvalibleActions());
        }
        if (null != this.eventProcessor) {
            out.addAll(this.eventProcessor.getAllAvalibleActions(out));
        }
        if (!out.isEmpty()) {
            return out;
        }
        return Collections.emptyList();
    }

    public List<List<ScriptEvent>> getExpectedEvents() {
        if (null != this.eventProcessor) {
            return this.eventProcessor.getAllAvalibleEvents();
        }
        return null;
    }

    public List<Integer> getDataToSave() {
        if (null != this.eventProcessor) {
            return this.eventProcessor.getDataToSave();
        }
        return Collections.emptyList();
    }

    public void restoreState(List<Integer> savedData) {
        this.eventProcessor.restoreFromData(savedData);
    }

    public final int compareTo(Object o) {
        return this.stateName.compareTo((String)o);
    }
}

