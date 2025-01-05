/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.List;
import rnrorg.MissionEventsMaker;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.MissionEndDialogEnded;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionEndDialogEndListener
implements EventListener {
    private String mission_name = null;

    public MissionEndDialogEndListener(String mission_name) {
        this.mission_name = mission_name;
    }

    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        if (eventTuple == null || eventTuple.isEmpty()) {
            return;
        }
        for (ScriptEvent event2 : eventTuple) {
            MissionEndDialogEnded mission_event;
            if (!(event2 instanceof MissionEndDialogEnded) || !(mission_event = (MissionEndDialogEnded)event2).isMission(this.mission_name)) continue;
            MissionEventsMaker.channelSayEndEventToNative(this.mission_name, mission_event.channel_uid);
            EventsController.getInstance().removeListener(this);
        }
    }
}

