/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrorg.MissionEventsMaker;
import rnrscenario.missions.MissionInfo;
import scriptActions.ScriptAction;

public class MissionActionRemoveResourcesFade
extends ScriptAction {
    static final long serialVersionUID = 0L;
    public String mission_name = null;

    public MissionActionRemoveResourcesFade() {
        this.mission_name = MissionInfo.getLoadingMissionName();
    }

    public MissionActionRemoveResourcesFade(int priority) {
        super(priority);
    }

    public void act() {
        MissionEventsMaker.cleanResourcesFade(this.mission_name);
    }
}

