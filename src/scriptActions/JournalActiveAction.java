/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.ArrayList;
import java.util.logging.Level;
import rnrcore.ConvertGameTime;
import rnrloggers.ScenarioLogger;
import rnrorg.ActiveJournalListeners;
import rnrorg.JournalActiveListener;
import rnrorg.journable;
import rnrorg.journalelement;
import scriptActions.ScriptAction;

public class JournalActiveAction
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private journable jou = null;
    private String description = null;
    private String time = null;

    public JournalActiveAction(journable jou) {
        this.jou = jou;
    }

    public JournalActiveAction() {
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
        ArrayList<JournalActiveListener> listeners = ActiveJournalListeners.getActiveListeners();
        for (JournalActiveListener lst : listeners) {
            this.jou.makeQuestionFor(lst);
        }
        this.jou.start();
        if (this.time != null) {
            try {
                int minutes = Integer.parseInt(this.time);
                this.jou.setDeactivationTime(ConvertGameTime.convertFromCurrent(minutes * 60));
            }
            catch (NumberFormatException exceptionInteger) {
                try {
                    double minutes = Double.parseDouble(this.time);
                    int seconds = (int)(minutes * 60.0);
                    this.jou.setDeactivationTime(ConvertGameTime.convertFromCurrent(seconds));
                }
                catch (NumberFormatException exceptionDouble) {
                    ScenarioLogger.getInstance().machineLog(Level.SEVERE, "action performed: " + this.getClass().getName() + "; journal " + this.description + " cannot covert passet time=" + this.time + " to integer or double.");
                }
            }
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed: " + this.getClass().getName() + "; journal description: " + this.jou.description());
    }
}

