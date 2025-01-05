/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscr.MissionDialogs;

public class ExternalChannel
extends InformationChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;

    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
        }
    }

    public InformationChannel clone() {
        return new ExternalChannel();
    }

    public void delayedRealInfoPost() {
        this.resource_hold = this.delayedInfo.resource;
    }
}

