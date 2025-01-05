/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.lang.reflect.Constructor;
import rnrcore.CoreTime;
import rnrcore.eng;
import rnrorg.ScenarioMissionItem;
import rnrorg.ScenarioMissions;
import rnrscenario.missions.ScenarioMission;
import rnrscenario.missions.starters.IStarter;
import scenarioUtils.AdvancedClass;
import scriptActions.ScriptAction;

public class StartScenarioMissionAction
extends ScriptAction {
    public String name = null;
    public String timer = null;

    public StartScenarioMissionAction() {
    }

    public StartScenarioMissionAction(int priority) {
        super(priority);
    }

    public void act() {
        ScenarioMissionItem item = ScenarioMissions.getInstance().get(this.name);
        if (null == item) {
            eng.err("StartScenarioMissionAction cannot find scenario mission fith name " + this.name);
            return;
        }
        if (null == this.timer) {
            ScenarioMission.activateMission(item.getMission_name(), item.getOrg_name(), item.getPoint_name(), new CoreTime(), item.getMoveTime(), item.getNeedFinishIcon());
            return;
        }
        try {
            AdvancedClass startedClass = new AdvancedClass(this.timer, "rnrscenario.controllers.starters");
            Constructor constructor = startedClass.getAllConstructors()[0];
            Object newObject = constructor.newInstance(new Object[0]);
            if (newObject instanceof IStarter) {
                IStarter starter = (IStarter)newObject;
                starter.start(item.getMission_name(), item.getOrg_name(), item.getPoint_name(), item.getTimeOnMission(), item.getMoveTime(), item.getNeedFinishIcon());
                eng.blockEscapeMenu();
            } else {
                eng.err("StartScenarioMissionAction has starter " + this.timer + " that not implements IStarter.");
            }
        }
        catch (Exception e) {
            eng.err("StartScenarioMissionAction EXCEPTION ON AdvancedClass new. Log: " + e.toString());
        }
    }
}

