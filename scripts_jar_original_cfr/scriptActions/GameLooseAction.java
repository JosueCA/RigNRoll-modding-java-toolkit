/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import menu.menues;
import rnrcore.eng;
import rnrloggers.ScenarioLogger;
import scriptActions.ScriptAction;

public class GameLooseAction
extends ScriptAction {
    static final long serialVersionUID = 0L;

    public GameLooseAction() {
        super(16);
    }

    public void act() {
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        if (!eng.noNative) {
            menues.gameoverMenu();
        }
    }
}

