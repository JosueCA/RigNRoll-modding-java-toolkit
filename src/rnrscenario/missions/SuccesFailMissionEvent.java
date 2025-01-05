/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrcore.INativeMessageEvent;
import scriptEvents.EventsControllerHelper;
import scriptEvents.ScriptEvent;

public class SuccesFailMissionEvent
implements INativeMessageEvent {
    static final long serialVersionUID = 0L;
    private String message = null;
    private ScriptEvent event_to_emmit;

    public SuccesFailMissionEvent(String mission_name, String suffix, ScriptEvent event_to_emmit) {
        this.event_to_emmit = event_to_emmit;
        this.message = mission_name + suffix;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean removeOnEvent() {
        return true;
    }

    public void onEvent(String message) {
        EventsControllerHelper.eventHappened(this.event_to_emmit);
    }
}

