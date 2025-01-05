/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrorg.MissionEventsMaker;
import scriptActions.ScriptAction;

public class ChangeMissionDestination
extends ScriptAction {
    static final long serialVersionUID = 0L;
    public String mission;
    public String destination;

    public ChangeMissionDestination() {
    }

    public ChangeMissionDestination(int priority) {
        super(priority);
    }

    public void act() {
        MissionEventsMaker.changeMissionDestination(this.mission, this.destination);
    }
}

