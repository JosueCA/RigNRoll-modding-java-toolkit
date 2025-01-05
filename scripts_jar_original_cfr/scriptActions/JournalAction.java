/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrorg.journable;
import rnrorg.journalelement;
import scriptActions.ScriptAction;

public class JournalAction
extends ScriptAction {
    private journable jou = null;
    private String description = null;

    public JournalAction(journable jou) {
        this.jou = jou;
    }

    public JournalAction() {
    }

    public void act() {
        if (null == this.jou) {
            if (null != this.description) {
                this.jou = new journalelement(this.description, null);
            } else {
                ScenarioLogger.getInstance().machineWarning("JournalAction wasn't correctly initialized");
                return;
            }
        }
        this.jou.start();
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed: " + this.getClass().getName() + "; journal description: " + this.jou.description());
    }
}

