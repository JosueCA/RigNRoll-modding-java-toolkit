/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;
import scriptActions.ScriptAction;

public class ExternalChannelSayAppear
extends ScriptAction {
    public String name;
    public String point = "";

    public ExternalChannelSayAppear() {
        super(9);
    }

    public void act() {
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed: " + this.getClass().getName() + "; channel name: " + this.name);
        IMissionInformation channelInformation = MissionDialogs.getMissionInfo(this.name);
        if (null != channelInformation && null != this.point) {
            channelInformation.setPointName(this.point);
        }
        MissionDialogs.sayAppear(this.name);
    }
}

