/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.logging.Level;
import rnrloggers.MissionsLogger;
import rnrscenario.CBCallsStorage;
import rnrscenario.CBVideoStroredCall;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.DelayedChannel;
import rnrscenario.missions.map.AbstractMissionsMap;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;

public class CbvChannel
extends DelayedChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;
    private boolean posted = false;

    public CbvChannel(AbstractMissionsMap map) {
        super(map);
    }

    public void postStartMissionInfo_Detailed(MissionInfo info, String resource) {
        this.resource_hold = resource;
    }

    public CbvChannel clone() {
        return new CbvChannel(this.getMap());
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
        }
    }

    public void delayedRealInfoPost() {
        String resource;
        String mission_name = this.delayedInfo.mission_name;
        this.resource_hold = resource = this.delayedInfo.resource;
        if (this.posted) {
            return;
        }
        this.posted = true;
        IMissionInformation dialog = MissionDialogs.queueDialog(this.getUid());
        String dialog_name = dialog.getDialogName();
        CBVideoStroredCall call = CBCallsStorage.getInstance().getStoredCall(dialog_name);
        if (null == call) {
            MissionsLogger.getInstance().doLog("CbvChannel for mission " + mission_name + " can find dialog named " + dialog_name, Level.SEVERE);
        }
        call.makecall(this.identitie, mission_name);
    }
}

