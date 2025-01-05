/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.missions.CreateMissionEvent;
import rnrscenario.missions.IStartMissionListener;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.StartMissionListeners;
import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;

public class StartMissionAction
extends ScriptAction {
    static final long serialVersionUID = 0L;
    public String name = null;
    private String activatedby = MissionInfo.getLoadingMissionName();

    public StartMissionAction() {
    }

    public StartMissionAction(scenarioscript scenario) {
    }

    public void setActivatedBy(String value) {
        this.activatedby = value;
    }

    public void act() {
        MissionSystemInitializer.getMissionsManager().activateDependantMission(this.activatedby, this.name);
        EventsController.getInstance().eventHappen(new CreateMissionEvent(this.name));
        IStartMissionListener lst = StartMissionListeners.getInstance().getStartMissionListener(this.name);
        if (null != lst) {
            lst.missionStarted();
        }
    }
}

