// Decompiled with: CFR 0.152
// Class Version: 5
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrscenario.EndScenario;
import rnrscenario.scenarioscript;
import scriptActions.ScenarioAction;
import scriptActions.ScriptAction;
import scriptEvents.ScriptEvent;
import scriptEvents.VoidEvent;

public class TimeAction
extends ScenarioAction {
    static final long serialVersionUID = 0L;
    public String name = null;
    public int days = -1;
    public int hours = -1;
    public ScriptAction action = null;
    ScriptEvent exactEvent_on_activate = null;
    ScriptEvent exactEvent_on_deactivate = null;

    public TimeAction(String name, Integer numdays, ScriptAction action, scenarioscript scenario) {
        super(scenario);
        this.name = name;
        this.days = numdays;
        this.action = action;
    }

    public TimeAction(scenarioscript scenario) {
        super(scenario);
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("TimeAction instance wasn't correctly initialized! scenario system could work uncorrect");
            return;
        }
        if (null != this.action) {
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
            this.hours = Math.max(0, this.hours);
            EndScenario.getInstance().delayAction(this.name, this.days, this.hours, this.exactEvent_on_activate, this.exactEvent_on_deactivate);
        } else {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "action hasn't been performed " + this.getClass().getName());
        }
    }

    public boolean validate() {
        return 0 <= this.days && null != this.name;
    }

    public boolean hasChildAction() {
        return true;
    }

    public ScriptAction getChildAction() {
        return this.action;
    }

    public boolean actActionAsScenarioNode() {
        return true;
    }

    public ScriptEvent getExactEventForConditionOnActivate() {
        if (this.exactEvent_on_activate == null) {
            this.exactEvent_on_activate = new VoidEvent(this.name + " activation.");
        }
        return this.exactEvent_on_activate;
    }

    public ScriptEvent getExactEventForConditionOnDeactivate() {
        if (null == this.exactEvent_on_deactivate) {
            this.exactEvent_on_deactivate = new VoidEvent(this.name + " deactivation.");
        }
        return this.exactEvent_on_deactivate;
    }
}
