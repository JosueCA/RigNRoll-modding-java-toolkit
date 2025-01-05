/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.MissionRequirement;
import rnrscenario.missions.requirements.MissionsLog;

public final class MissionCompleteRequirement
extends MissionRequirement {
    static final long serialVersionUID = 0L;

    public MissionCompleteRequirement(String name) {
        super(name);
    }

    public boolean check() {
        MissionsLog.MissionState missionState = MissionsLog.getInstance().getMissionState(this.missionName());
        return null != missionState && missionState.getOccuredEvents().contains((Object)MissionsLog.Event.MISSION_COMPLETE);
    }

    public int getPriorityIncrement() {
        if (null != this.getPriorityTable()) {
            return this.getPriorityTable().getPriority(this.missionName());
        }
        return 0;
    }
}

