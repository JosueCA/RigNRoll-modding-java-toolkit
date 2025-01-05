/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.missions.MissionSystemInitializer;
import scriptActions.ScriptAction;

public class CreateAsideMission
extends ScriptAction {
    public String name;

    public CreateAsideMission() {
    }

    public CreateAsideMission(int priority) {
        super(priority);
    }

    public void act() {
        MissionSystemInitializer.getMissionsManager().activateAsideMission(this.name);
    }
}

