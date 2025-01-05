/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.HashMap;
import rnrscenario.missions.IChannelEventListener;
import rnrscenario.missions.IStartMissionListener;

public class StartMissionListeners {
    private static StartMissionListeners instance = null;
    private HashMap<String, IStartMissionListener> startlisteners = new HashMap();
    private HashMap<String, IChannelEventListener> channellisteners = new HashMap();

    private StartMissionListeners() {
    }

    public static StartMissionListeners getInstance() {
        if (null == instance) {
            instance = new StartMissionListeners();
        }
        return instance;
    }

    public static void deinit() {
        instance = null;
    }

    public void registerStartMissionListener(String missionName, IStartMissionListener listener) {
        this.startlisteners.put(missionName, listener);
    }

    public void registerChannelEventListener(String missionName, IChannelEventListener listener) {
        this.channellisteners.put(missionName, listener);
    }

    public void unregisterStartMissionListener(String missionName) {
        this.startlisteners.remove(missionName);
    }

    public void unregisterChannelEventListener(String missionName) {
        this.channellisteners.remove(missionName);
    }

    public IStartMissionListener getStartMissionListener(String missionName) {
        if (this.startlisteners.containsKey(missionName)) {
            return this.startlisteners.get(missionName);
        }
        return null;
    }

    public IChannelEventListener getChannleEventListener(String missionName) {
        if (this.channellisteners.containsKey(missionName)) {
            return this.channellisteners.get(missionName);
        }
        return null;
    }
}

