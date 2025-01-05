/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import menu.JavaEvents;
import rnrcore.TimerMessage;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrloggers.ScenarioLogger;
import rnrscr.Helper;
import scriptActions.ScriptAction;

public class ReachPointTimer
extends ScriptAction {
    static final long serialVersionUID = 0L;
    String point;
    String message;

    public ReachPointTimer() {
    }

    public ReachPointTimer(int priority) {
        super(priority);
    }

    public void act() {
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        if (!eng.noNative) {
            Data data = new Data();
            data.pos1 = Helper.getCurrentPosition();
            data.pos2 = eng.getControlPointPosition(this.point);
            JavaEvents.SendEvent(41, 0, data);
            new TimerMessage(data.time, this.message);
        }
    }

    static class Data {
        vectorJ pos1;
        vectorJ pos2;
        double time;

        Data() {
        }
    }
}

