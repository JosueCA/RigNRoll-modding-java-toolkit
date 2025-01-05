/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.PayOffManager;
import scriptActions.ScriptAction;

public class MakePayOff
extends ScriptAction {
    public String name = "";

    public void act() {
        PayOffManager.getInstance().makePayOff(this.name);
    }
}

