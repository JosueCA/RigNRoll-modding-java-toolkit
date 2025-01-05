/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import scriptEvents.ScriptEvent;

public class NeedPostMissionInfoEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private String missionName = null;

    public String getMissionName() {
        return this.missionName;
    }

    public NeedPostMissionInfoEvent(String missionName) {
        this.missionName = missionName;
    }
}

