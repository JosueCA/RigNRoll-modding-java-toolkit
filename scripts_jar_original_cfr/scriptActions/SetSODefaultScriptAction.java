/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrcore.Helper;
import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public class SetSODefaultScriptAction
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private boolean value = false;

    public SetSODefaultScriptAction(scenarioscript scenario) {
        super(4);
    }

    public void act() {
        if (this.value) {
            Helper.peekNativeMessage("enable so predefine");
        } else {
            Helper.peekNativeMessage("disable so predefine");
        }
    }
}

