/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public class MissionEndDialogEnded
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private String mission_name = null;
    public String channel_uid;

    public MissionEndDialogEnded(String mission_name, String channel_uid) {
        this.mission_name = mission_name;
        this.channel_uid = channel_uid;
    }

    public boolean isMission(String mission_name) {
        return 0 == mission_name.compareToIgnoreCase(this.mission_name);
    }
}

