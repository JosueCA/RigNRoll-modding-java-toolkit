/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import scriptEvents.ScriptEvent;

public class CreateMissionEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private String missionName = null;

    public CreateMissionEvent(String misssionName) {
        assert (null != misssionName) : "missionName must be valid non-null reference";
        this.missionName = misssionName;
    }

    String getMissionName() {
        return this.missionName;
    }
}

