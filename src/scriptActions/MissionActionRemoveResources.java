/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.missions.MissionInfo;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;
import scriptEvents.MissionEndDialogEndListener;

public class MissionActionRemoveResources
extends ScriptAction {
    static final long serialVersionUID = 0L;
    public String mission_name = null;

    public MissionActionRemoveResources() {
        this.mission_name = MissionInfo.getLoadingMissionName();
    }

    public MissionActionRemoveResources(int priority) {
        super(priority);
    }

    public void act() {
        MissionEndDialogEndListener endDialogListener = new MissionEndDialogEndListener(this.mission_name);
        EventsController.getInstance().addListener(endDialogListener);
    }
}

