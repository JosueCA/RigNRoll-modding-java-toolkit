/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteStateMachine;
import scriptActions.ScenarioBranchAction;

public class StartScenarioBranchAction
extends ScenarioBranchAction {
    static final long serialVersionUID = 0L;
    private static final String NAME_ATTRIBUTE_NAME = "name";

    public StartScenarioBranchAction(String scenarioNodeName, FiniteStateMachine machine) {
        super(scenarioNodeName, machine);
    }

    public StartScenarioBranchAction(FiniteStateMachine machine) {
        super(machine);
    }

    String getBranchName() {
        return this.name;
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("StartScenarioBranchAction instance wasn't correctly initialized");
            return;
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        this.machine.activateState(this.name, this.name + "_phase_" + 1);
    }

    public String toString() {
        return this.getClass().getName() + " " + this.name;
    }
}

