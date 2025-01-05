/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public class MissionSucceedEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private String mission_name;

    public MissionSucceedEvent(String mission_name) {
        this.mission_name = mission_name;
    }

    public String getMissionName() {
        return this.mission_name;
    }
}

