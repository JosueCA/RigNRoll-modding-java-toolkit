/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.util.Collection;
import rnrscenario.missions.requirements.MissionRequirement;
import rnrscenario.missions.requirements.MissionsLog;

public final class MissionFailureRequirement
extends MissionRequirement {
    static final long serialVersionUID = 0L;
    private MissionsLog.Event failureReason = null;

    public MissionFailureRequirement(String name, String reason) {
        super(name);
        this.failureReason = null == reason ? null : MissionsLog.Event.valueOf(reason);
    }

    public boolean check() {
        MissionsLog.MissionState missionState = MissionsLog.getInstance().getMissionState(this.missionName());
        if (null == missionState) {
            return false;
        }
        if (null != this.failureReason) {
            return null != missionState && missionState.getOccuredEvents().contains((Object)this.failureReason);
        }
        Collection<MissionsLog.Event> occuredEvents = missionState.getOccuredEvents();
        for (MissionsLog.Event event2 : occuredEvents) {
            if (!event2.missionFailed()) continue;
            return true;
        }
        return false;
    }
}

