/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrscenario.ScenarioFlagsManager;
import scriptActions.ScriptAction;

public class SetScenarioFlag
extends ScriptAction {
    public String name;
    public boolean value = true;
    public int days = 0;

    public void act() {
        if (ScenarioFlagsManager.getInstance() != null) {
            if (this.days == 0) {
                ScenarioFlagsManager.getInstance().setFlagValue(this.name, this.value);
            } else {
                ScenarioFlagsManager.getInstance().setFlagValueTimed(this.name, this.days);
            }
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " scenario flag " + this.name + " setted " + this.value + " days " + this.days);
    }
}

