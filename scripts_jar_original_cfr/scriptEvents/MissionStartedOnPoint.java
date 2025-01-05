/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public class MissionStartedOnPoint
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private String mission_name;
    private String point_name;

    public MissionStartedOnPoint(String mission_name, String point_name) {
        this.mission_name = mission_name;
        this.point_name = point_name;
    }

    public boolean isMission(String mission_name) {
        return mission_name.compareTo(this.mission_name) == 0;
    }

    public String getPointName() {
        return this.point_name;
    }
}

