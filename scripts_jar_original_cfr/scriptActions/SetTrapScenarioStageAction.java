/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public final class SetTrapScenarioStageAction
extends ScriptAction {
    private int number = 0;
    private final scenarioscript scenarioStuff;

    SetTrapScenarioStageAction(scenarioscript scenario) {
        super(3);
        this.scenarioStuff = scenario;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void act() {
        if (null != this.scenarioStuff) {
            this.scenarioStuff.getStage().setStageNumber(this.number);
        }
    }
}

