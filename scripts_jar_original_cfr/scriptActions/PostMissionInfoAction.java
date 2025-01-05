/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.missions.NeedPostMissionInfoEvent;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;

public class PostMissionInfoAction
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private String mission = null;

    public void act() {
        if (null != this.mission) {
            EventsController.getInstance().eventHappen(new NeedPostMissionInfoEvent(this.mission));
        }
    }

    public boolean validate() {
        return null != this.mission;
    }
}

