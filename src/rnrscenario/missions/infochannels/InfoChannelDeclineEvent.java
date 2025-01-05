/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.infochannels.InfoChannelEvent;
import rnrscenario.missions.infochannels.InfoChannelEventCallback;

public class InfoChannelDeclineEvent
extends InfoChannelEvent {
    static final long serialVersionUID = 0L;

    public InfoChannelDeclineEvent(String channelUid, String place) {
        super(channelUid, place);
    }

    public void executeCallBack(InfoChannelEventCallback target) {
        if (null != target) {
            target.callBackDecline(this.getPlace());
        }
    }
}

