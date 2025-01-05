/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public final class MessageEvent
implements ScriptEvent {
    private String message = null;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        if (null != this.message) {
            return "MessageEvent with message " + this.message;
        }
        return "";
    }
}

