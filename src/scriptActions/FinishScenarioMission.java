/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrcore.eng;
import rnrcore.event;
import scriptActions.ScriptAction;

public final class FinishScenarioMission
extends ScriptAction {
    public String name = null;
    public boolean fail = false;

    public FinishScenarioMission() {
    }

    public FinishScenarioMission(int priority) {
        super(priority);
    }

    public void act() {
        if (!eng.noNative) {
            if (this.fail) {
                event.failScenarioMission(this.name);
            } else {
                event.finishScenarioMission(this.name);
            }
        }
    }
}

