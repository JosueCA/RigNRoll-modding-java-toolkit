/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import rnrcore.CoreTime;
import scriptEvents.ScriptEvent;

public final class TimeEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private CoreTime time = null;

    public TimeEvent(CoreTime time) {
        this.time = time;
    }

    void setTime(CoreTime time) {
        this.time = time;
    }

    public CoreTime getTime() {
        return this.time;
    }

    public String toString() {
        if (null != this.time) {
            return "TimeEvent " + this.time.gYear() + "." + this.time.gMonth() + "." + this.time.gDate() + "." + this.time.gHour() + "." + this.time.gMinute();
        }
        return "TimeEvent; time field == null";
    }
}

