/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public class VoidEvent
implements ScriptEvent {
    private String info;

    public VoidEvent(String info) {
        this.info = info;
    }

    public String toString() {
        return this.info;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

