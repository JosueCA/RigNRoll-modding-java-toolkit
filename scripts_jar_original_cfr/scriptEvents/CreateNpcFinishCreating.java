/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public class CreateNpcFinishCreating
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    public String missionName;

    public CreateNpcFinishCreating(String missionName) {
        this.missionName = missionName;
    }
}

