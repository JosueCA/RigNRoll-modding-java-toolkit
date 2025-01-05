/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;
import scriptEvents.MessageEvent;

public final class PostMessageEvent
extends ScriptAction {
    private String text = null;

    public PostMessageEvent() {
        super(8);
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("PostMessageEvent wasn't correctly inited");
            return;
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " text == " + this.text);
        EventsController.getInstance().eventHappen(new MessageEvent(this.text));
    }

    public boolean validate() {
        return null != this.text;
    }
}

