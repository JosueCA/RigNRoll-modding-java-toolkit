/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrcore.eng;
import rnrorg.MissionEventsMaker;
import scriptActions.ScriptAction;

public class DeclineScenerioMission
extends ScriptAction {
    static final long serialVersionUID = 0L;
    public String name = null;

    public DeclineScenerioMission() {
    }

    public DeclineScenerioMission(int priority) {
        super(priority);
    }

    public void act() {
        if (this.name == null) {
            eng.log(this.getClass().getName() + " has null name.");
            return;
        }
        MissionEventsMaker.declineMissionNotFromOrganoser(this.name);
    }
}

