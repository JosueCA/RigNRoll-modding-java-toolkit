/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;
import rnrscr.smi.Newspapers;

public class NewspaperChannel
extends InformationChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;

    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;
        IMissionInformation mission_info = MissionDialogs.getMissionInfo(resource);
        Newspapers.addMissionNews(resource, mission_info);
    }

    public NewspaperChannel clone() {
        return new NewspaperChannel();
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
            Newspapers.removeMissionNews(this.resource_hold);
        }
    }

    public void delayedRealInfoPost() {
        String resource;
        this.resource_hold = resource = this.delayedInfo.resource;
        IMissionInformation mission_info = MissionDialogs.getMissionInfo(resource);
        Newspapers.addMissionNews(resource, mission_info);
    }
}

