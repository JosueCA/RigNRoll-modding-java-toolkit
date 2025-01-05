/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import scenarioMachine.FiniteStateMachine;
import scriptActions.ScriptAction;

public abstract class ScenarioBranchAction
extends ScriptAction {
    String name = null;
    FiniteStateMachine machine = null;

    public ScenarioBranchAction(String scenarioNodeName, FiniteStateMachine machine) {
        super(16);
        if (null == scenarioNodeName || null == machine) {
            throw new IllegalArgumentException("all arguments must be non-null reference");
        }
        this.name = scenarioNodeName;
        this.machine = machine;
    }

    public ScenarioBranchAction(FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }
        this.machine = machine;
    }

    public boolean validate() {
        return null != this.name && null != this.machine;
    }

    public abstract void act();
}

