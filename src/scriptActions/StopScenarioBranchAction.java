/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteStateMachine;
import scriptActions.ScenarioBranchAction;

public class StopScenarioBranchAction
extends ScenarioBranchAction {
    static final long serialVersionUID = 0L;

    public StopScenarioBranchAction(String scenarioNodeName, FiniteStateMachine machine) {
        super(scenarioNodeName, machine);
    }

    public StopScenarioBranchAction(FiniteStateMachine machine) {
        super(machine);
    }

    private String findNodeName(Pattern pattern) {
        Set<String> activeStates = this.machine.getStatesNames();
        for (String stateName : activeStates) {
            if (!pattern.matcher(stateName).matches()) continue;
            return stateName;
        }
        return null;
    }

    private String findActiveNodeName(Pattern pattern) {
        Collection<String> activeStates = this.machine.getCurrentActiveStates();
        for (String stateName : activeStates) {
            if (!pattern.matcher(stateName).matches()) continue;
            return stateName;
        }
        return null;
    }

    public String getDestination() {
        Pattern pattern = Pattern.compile(this.name + "(" + "_phase_" + "\\d+)?");
        String startOfWay = this.findNodeName(pattern);
        if (null != startOfWay) {
            return this.machine.findEndNode(startOfWay);
        }
        return null;
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("StartScenarioBranchAction instance wasn't correctly initialized");
            return;
        }
        Pattern pattern = Pattern.compile(this.name + "(" + "_phase_" + "\\d+)?");
        String startOfWay = this.findActiveNodeName(pattern);
        if (null != startOfWay) {
            this.machine.addNodeToWalkFromToEnd(startOfWay);
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        } else {
            ScenarioLogger.getInstance().machineWarning(this.name + "node wasn't found while trying to stop scenario branch");
        }
    }
}

