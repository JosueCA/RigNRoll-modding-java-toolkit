/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.DelayedChannel;
import rnrscenario.missions.map.AbstractMissionsMap;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;
import rnrscr.smi.RadioNews;

public class RadioChannel
extends DelayedChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;

    public RadioChannel(AbstractMissionsMap map) {
        super(map);
    }

    public void postStartMissionInfo_Detailed(MissionInfo info, String resource) {
        this.resource_hold = resource;
    }

    public RadioChannel clone() {
        return new RadioChannel(this.getMap());
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
            RadioNews.remove(this.resource_hold);
        }
    }

    public void delayedRealInfoPost() {
        String resource;
        this.resource_hold = resource = this.delayedInfo.resource;
        IMissionInformation mission_info = MissionDialogs.queueDialog(this.getUid());
        RadioNews.add(resource, new RadioNews(mission_info, resource));
        RadioNews.appear(resource);
    }
}

