/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrscenario.EndScenario;
import rnrscenario.scenarioscript;
import scriptActions.ScenarioAction;

public class RemoveTimeAction
extends ScenarioAction {
    static final long serialVersionUID = 0L;
    private String name = null;

    public RemoveTimeAction(scenarioscript scenario) {
        super(scenario);
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("TimeAction instance wasn't correctly initialized! scenario system could work uncorrect");
            return;
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " name ==" + this.name);
        EndScenario.getInstance().removeTimeQuest(this.name);
    }

    public boolean validate() {
        return null != this.name;
    }
}

