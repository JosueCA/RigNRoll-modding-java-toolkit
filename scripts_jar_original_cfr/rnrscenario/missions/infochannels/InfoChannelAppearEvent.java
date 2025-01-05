/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.infochannels.InfoChannelEvent;
import rnrscenario.missions.infochannels.InfoChannelEventCallback;

public class InfoChannelAppearEvent
extends InfoChannelEvent {
    static final long serialVersionUID = 0L;

    public InfoChannelAppearEvent(String channelUid, String place) {
        super(channelUid, place);
    }

    public void executeCallBack(InfoChannelEventCallback target) {
        if (null != target) {
            target.callBackAppear(this.getPlace());
        }
    }
}

