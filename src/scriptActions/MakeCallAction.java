/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrcore.eng;
import rnrloggers.ScenarioLogger;
import rnrscenario.scenarioscript;
import scriptActions.ScenarioAction;
import scriptEvents.CbVideoEvent;
import scriptEvents.EventsController;

public class MakeCallAction
extends ScenarioAction {
    public String name = "Undefined";
    public boolean immediate = false;

    public MakeCallAction(scenarioscript scenario) {
        super(scenario);
    }

    public void setCallName(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }
        this.name = name;
    }

    public void act() {
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " " + this.name);
        if (!eng.noNative) {
            if (!this.immediate) {
                this.getScript().launchCall(this.name);
            } else {
                this.getScript().immediateLaunchCall(this.name);
            }
        } else {
            EventsController.getInstance().eventHappen(new CbVideoEvent(this.name, CbVideoEvent.EventType.START, 0));
            EventsController.getInstance().eventHappen(new CbVideoEvent(this.name, CbVideoEvent.EventType.FINISH, 0));
        }
    }
}

