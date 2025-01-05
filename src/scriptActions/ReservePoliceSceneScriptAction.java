/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.PoliceScene;
import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public class ReservePoliceSceneScriptAction
extends ScriptAction {
    boolean reserve = false;

    public ReservePoliceSceneScriptAction(scenarioscript scenario) {
        super(8);
    }

    public boolean validate() {
        return true;
    }

    public void act() {
        PoliceScene.reserved_for_scene = this.reserve;
    }
}

