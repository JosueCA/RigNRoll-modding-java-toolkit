/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import rnrloggers.MissionsLogger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionOrganiser {
    private HashMap<String, String> missions = new HashMap();
    private static MissionOrganiser instance = null;

    public static void deinit() {
        instance = null;
    }

    public static MissionOrganiser getInstance() {
        if (null == instance) {
            instance = new MissionOrganiser();
        }
        return instance;
    }

    private MissionOrganiser() {
    }

    public void addMission(String mission_name, String org_name) {
        if (this.missions.containsKey(mission_name)) {
            MissionsLogger.getInstance().doLog("MissionOrganiser already contains mission named " + mission_name, Level.SEVERE);
            return;
        }
        if (this.missions.containsValue(org_name)) {
            MissionsLogger.getInstance().doLog("MissionOrganiser already organiser named " + org_name + ". Try was fro mission named " + mission_name, Level.SEVERE);
            return;
        }
        this.missions.put(mission_name, org_name);
    }

    public String getOrgForMission(String mission_name) {
        if (!this.missions.containsKey(mission_name)) {
            return null;
        }
        return this.missions.get(mission_name);
    }

    public String getMissionForOrganiser(String org_name) {
        assert (org_name != null);
        Set<String> keys = this.missions.keySet();
        for (String key : keys) {
            if (this.missions.get(key).compareToIgnoreCase(org_name) != 0) continue;
            return key;
        }
        MissionsLogger.getInstance().doLog("Trying to fins org " + org_name + "in missions. Cannot find!", Level.SEVERE);
        return null;
    }

    public HashMap<String, String> getMissions() {
        return this.missions;
    }

    public void setMissions(HashMap<String, String> missions) {
        this.missions = missions;
    }
}

