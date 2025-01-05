/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.Collection;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteStateMachine;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;
import scriptEvents.ScenarioStateNeedMoveEvent;

public class SingleStepScenarioAdvanceAction
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private static final int DEFAULT_PHASE_VALUE = -1;
    private String name = null;
    private int phase = -1;
    private FiniteStateMachine machine = null;

    public SingleStepScenarioAdvanceAction(String nextScenario, FiniteStateMachine machine) {
        super(16);
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }
        if (null == nextScenario) {
            throw new IllegalArgumentException("nextScenario must be non-null reference");
        }
        this.machine = machine;
        this.name = nextScenario;
    }

    public SingleStepScenarioAdvanceAction(FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }
        this.name = "unknown";
        this.machine = machine;
    }

    public SingleStepScenarioAdvanceAction(String name, int state, FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }
        this.machine = machine;
        this.name = name;
        this.phase = state;
    }

    public String getName() {
        return this.name;
    }

    public String getDestination() {
        if (-1 != this.phase) {
            return this.name + "_phase_" + this.phase;
        }
        return this.name;
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("SingleStepScenarioAdvanceAction instance wasn't correctly initialized");
            return;
        }
        if (-1 == this.phase) {
            if (!this.name.contains("_phase_")) {
                Collection<String> avalibleStatesToMoveFrom = this.machine.getCurrentActiveStates();
                boolean wasFound = false;
                for (String stateName : avalibleStatesToMoveFrom) {
                    if (!stateName.contains(this.name)) continue;
                    this.name = stateName;
                    wasFound = true;
                    break;
                }
                if (!wasFound) {
                    ScenarioLogger.getInstance().machineWarning("action SingleStepScenarioAdvanceAction couldn't be performed: " + this.name + " was not found");
                    return;
                }
            }
        } else {
            this.name = this.name + "_phase_" + this.phase;
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " state name == " + this.name);
        EventsController.getInstance().eventHappen(new ScenarioStateNeedMoveEvent(this.name, -1));
    }

    public boolean validate() {
        return null != this.name && null != this.machine;
    }
}

