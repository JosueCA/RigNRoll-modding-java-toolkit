/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public abstract class ScenarioAction
extends ScriptAction {
    private scenarioscript script = null;

    ScenarioAction(scenarioscript scenario) {
        if (null == scenario) {
            throw new IllegalArgumentException("scenario must be non-null reference");
        }
        this.script = scenario;
    }

    scenarioscript getScript() {
        return this.script;
    }

    public abstract void act();
}

