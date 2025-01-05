/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.List;
import rnrscenario.missions.infochannels.InfoChannelEvent;
import rnrscenario.missions.infochannels.InfoChannelEventCallback;
import rnrscenario.missions.infochannels.WhereWasChannelAccepted;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class InfoChannelEventsListener
extends InfoChannelEventCallback
implements EventListener,
WhereWasChannelAccepted {
    private String sourceUid = null;

    public InfoChannelEventsListener(String desiredSourceUid) {
        assert (null != desiredSourceUid) : "desiredSourceUid must be non-null reference";
        this.sourceUid = desiredSourceUid;
    }

    // @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            InfoChannelEvent channelEvent;
            if (!(event2 instanceof InfoChannelEvent) || 0 != this.sourceUid.compareTo((channelEvent = (InfoChannelEvent)event2).getSourceUid())) continue;
            InfoChannelEvent channelProcessor = (InfoChannelEvent)event2;
            channelProcessor.executeCallBack(this);
        }
        if (this.channelClosed()) {
            EventsController.getInstance().removeListener(this);
        }
    }

    public boolean hasAcceptAction(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            InfoChannelEvent channelEvent;
            if (!(event2 instanceof InfoChannelEvent) || 0 != this.sourceUid.compareTo((channelEvent = (InfoChannelEvent)event2).getSourceUid()) || !this.hasAcceptAction()) continue;
            return true;
        }
        return false;
    }

    // @Override
    public String where() {
        return super.whereAccepted();
    }
}

