/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public class CreateNpcFromInfoChannel
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    public String missionName;
    public String place;

    public CreateNpcFromInfoChannel(String missionName, String place) {
        this.missionName = missionName;
        this.place = place;
    }
}

