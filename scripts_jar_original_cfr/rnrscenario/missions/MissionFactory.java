/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionsController;
import rnrscenario.missions.SingleMission;

public class MissionFactory
implements MissionsController {
    static final long serialVersionUID = 0L;
    private Map<String, MissionInfo> wares = new HashMap<String, MissionInfo>();

    SingleMission create(String name) {
        assert (null != name) : "name must be valid non-null pointer";
        MissionInfo infoToContructFrom = this.wares.remove(name);
        if (null != infoToContructFrom) {
            infoToContructFrom.executeStartActions();
            MissionsLogger.getInstance().doLog("mission with name '" + name + "' has been created", Level.FINE);
            return infoToContructFrom.constructMission();
        }
        MissionsLogger.getInstance().doLog("mission with name '" + name + "' wasn't loaded into factory", Level.WARNING);
        return null;
    }

    public void uploadMission(MissionInfo constructFrom) {
        assert (null != constructFrom) : "constructFrom must be valid non-null pointer";
        if (this.wares.keySet().contains(constructFrom.getName())) {
            MissionsLogger.getInstance().doLog("mission with name '" + constructFrom.getName() + "' already loaded in factory", Level.FINE);
        } else {
            this.wares.put(constructFrom.getName(), constructFrom);
            MissionsLogger.getInstance().doLog("mission with name '" + constructFrom.getName() + "' has been uploaded to factory", Level.FINE);
        }
    }

    public void unloadMission(String missionName) {
        assert (null != missionName) : "missionName must be valid non-null pointer";
        if (null == this.wares.remove(missionName)) {
            MissionsLogger.getInstance().doLog("mission with name '" + missionName + "' wasn't loaded into factory", Level.FINE);
        } else {
            MissionsLogger.getInstance().doLog("mission with name '" + missionName + "' has been unloaded from factory", Level.FINE);
        }
    }
}

