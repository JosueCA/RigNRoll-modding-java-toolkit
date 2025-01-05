/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public final class ScenarioFinishedAction
extends ScriptAction {
    private final scenarioscript scenarioStuff;
    private boolean successfully = false;
    private boolean delayed = false;

    ScenarioFinishedAction(scenarioscript scenario) {
        this.scenarioStuff = scenario;
    }

    public void act() {
        if (null != this.scenarioStuff) {
            if (this.delayed) {
                this.scenarioStuff.sheduleScenarioStopOnNextFrame(this.successfully);
            } else {
                this.scenarioStuff.getStage().scenarioFinished(this.successfully);
            }
        }
    }

    public void setSuccessfully(boolean successfully) {
        this.successfully = successfully;
    }
}

