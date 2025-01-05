/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.HashMap;
import java.util.Map;
import rnrcore.eng;

public class MissionCreationContext {
    private String missionName = "badname";
    private int numPhase = 0;
    private Map<String, Integer> numChannel = new HashMap<String, Integer>();

    MissionCreationContext(String missionName) {
        this.missionName = missionName;
    }

    void enterPhase() {
        ++this.numPhase;
    }

    void exitPhase() {
        this.numChannel.clear();
    }

    public void enterChannel(String channelName) {
        if (this.numChannel.containsKey(channelName)) {
            int num = this.numChannel.get(channelName);
            this.numChannel.put(channelName, ++num);
        } else {
            this.numChannel.put(channelName, 0);
        }
    }

    public String getChannelUid(String channelName) {
        int num = 0;
        if (!this.numChannel.containsKey(channelName)) {
            eng.err("MissionCreationContext does not contain channelName " + channelName + " in mission " + this.missionName + ".");
        } else {
            num = this.numChannel.get(channelName);
        }
        return this.missionName + "_" + this.numPhase + "_" + channelName + "_" + num;
    }
}

