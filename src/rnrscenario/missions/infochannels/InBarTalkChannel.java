/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscr.MissionDialogs;

public class InBarTalkChannel
extends InformationChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;

    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;
        MissionDialogs.activateDialog(resource);
    }

    public InBarTalkChannel clone() {
        return new InBarTalkChannel();
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
        }
    }

    public void delayedRealInfoPost() {
        String resource;
        this.resource_hold = resource = this.delayedInfo.resource;
        MissionDialogs.activateDialog(resource);
    }
}

