/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.MissionRequirement;
import rnrscenario.missions.requirements.MissionsLog;

public final class PlayerInformedAboutMissionRequirement
extends MissionRequirement {
    static final long serialVersionUID = 0L;

    public PlayerInformedAboutMissionRequirement(String name) {
        super(name);
    }

    public boolean check() {
        MissionsLog.MissionState missionState = MissionsLog.getInstance().getMissionState(this.missionName());
        if (null != missionState) {
            return missionState.getOccuredEvents().contains((Object)MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
        }
        return false;
    }
}

