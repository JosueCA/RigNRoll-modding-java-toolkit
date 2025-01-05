/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public class DeactivateMission
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private String name = null;
    private String activatedby = null;

    public DeactivateMission() {
        this.activatedby = MissionInfo.getLoadingMissionName();
    }

    public DeactivateMission(scenarioscript scenario) {
    }

    public void act() {
        MissionSystemInitializer.getMissionsManager().deactivateDependantMission(this.activatedby, this.name);
    }
}

