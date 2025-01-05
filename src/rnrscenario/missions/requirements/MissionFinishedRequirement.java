/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.util.Collection;
import rnrscenario.missions.requirements.MissionRequirement;
import rnrscenario.missions.requirements.MissionsLog;

public final class MissionFinishedRequirement
extends MissionRequirement {
    static final long serialVersionUID = 0L;

    public MissionFinishedRequirement(String name) {
        super(name);
    }

    public boolean check() {
        MissionsLog.MissionState missionState = MissionsLog.getInstance().getMissionState(this.missionName());
        if (null == missionState) {
            return false;
        }
        Collection<MissionsLog.Event> occuredEvents = missionState.getOccuredEvents();
        for (MissionsLog.Event event2 : occuredEvents) {
            if (!event2.missionFinished()) continue;
            return true;
        }
        return false;
    }
}

