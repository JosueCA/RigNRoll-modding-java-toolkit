/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrcore.eng;
import scriptActions.ScriptAction;

public final class PoliceImmunity
extends ScriptAction {
    private boolean value;

    public PoliceImmunity setValue(boolean value) {
        this.value = value;
        return this;
    }

    public void act() {
        eng.makePoliceImmunity(this.value);
    }
}

