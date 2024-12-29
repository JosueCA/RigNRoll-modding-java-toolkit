// Decompiled with: CFR 0.152
// Class Version: 5
package scriptActions;

import rnrcore.traffic;
import scriptActions.ScriptAction;

public final class TrafficAction
extends ScriptAction {
    private boolean on = true;

    public void act() {
        if (this.on) {
            traffic.setTrafficMode(0);
        } else {
            traffic.enterChaseModeImmediately();
        }
    }

    public TrafficAction setOn(boolean on) {
        this.on = on;
        return this;
    }
}
