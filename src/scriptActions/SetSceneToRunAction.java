/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrcore.ScenarioSync;
import rnrcore.eng;
import rnrcore.event;
import rnrloggers.ScenarioLogger;
import rnrscenario.scenarioscript;
import scriptActions.ScenarioAction;

public final class SetSceneToRunAction
extends ScenarioAction {
    String name = null;
    boolean waitEvent = false;
    private boolean afterparking = false;
    private boolean otherevent = false;
    private int scenarioStageNumberForScene = -1;

    public SetSceneToRunAction(scenarioscript scenario) {
        super(scenario);
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("SetSceneToRun wasn't correctly initialized, scene name is null");
            return;
        }
        if (!this.waitEvent) {
            if (this.afterparking) {
                this.waitEvent = true;
                try {
                    event.eventObject(25001, this, "act");
                }
                catch (UnsatisfiedLinkError error) {
                    return;
                }
                return;
            }
            if (this.otherevent) {
                this.waitEvent = true;
                String message = "code ERROR. Unaccesiible field is filled. SetSceneToRunAction. act";
                eng.err(message);
                ScenarioLogger.getInstance().machineLog(Level.WARNING, message);
                return;
            }
        }
        if (!eng.noNative) {
            if (this.afterparking) {
                eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
            }
            if (-1 == this.scenarioStageNumberForScene) {
                ScenarioSync.setPlayScene(this.name);
            } else {
                ScenarioSync.setPlayScene(this.name, this.scenarioStageNumberForScene);
            }
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " scene to play ==" + this.name);
    }

    public boolean validate() {
        return null != this.name;
    }
}

