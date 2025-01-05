/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import scriptActions.ScriptAction;

public class ScenarioAnalysisMarkAction
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private String destNode = null;

    public ScenarioAnalysisMarkAction(String destNode) {
        this.destNode = destNode;
    }

    public String getDestination() {
        return this.destNode;
    }

    public void act() {
    }
}

