/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.missions.MissionManager;
import scriptActions.ScriptAction;

public final class KillTakenMissionsAction
extends ScriptAction {
    public KillTakenMissionsAction() {
        super(8);
    }

    public void act() {
        MissionManager.declineMissions();
    }
}

