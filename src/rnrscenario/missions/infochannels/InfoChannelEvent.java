/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.infochannels.InfoChannelEventCallback;
import scriptEvents.ScriptEvent;

public abstract class InfoChannelEvent
implements ScriptEvent {
    private String channelUid = null;
    private String eventPlace = null;

    InfoChannelEvent(String channelUid, String place) {
        assert (null != channelUid) : "channelUid must be non-null reference";
        assert (null != place) : "place must be non-null reference";
        this.channelUid = channelUid;
        this.eventPlace = place;
    }

    String getSourceUid() {
        return this.channelUid;
    }

    String getPlace() {
        return this.eventPlace;
    }

    public abstract void executeCallBack(InfoChannelEventCallback var1);
}

