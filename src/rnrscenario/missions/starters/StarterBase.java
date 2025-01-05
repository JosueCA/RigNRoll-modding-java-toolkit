/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.starters;

import java.util.ArrayList;
import java.util.List;
import rnrcore.CoreTime;
import rnrorg.MissionEventsMaker;
import rnrscenario.controllers.starters.Start0430;
import rnrscenario.controllers.starters.Start0454;
import rnrscenario.controllers.starters.Start0600;
import rnrscenario.missions.ScenarioMission;
import rnrscenario.missions.starters.ConditionChecker;
import rnrscenario.missions.starters.IStarter;
import rnrscenario.sctask;

public class StarterBase
extends sctask
implements IStarter {
    private String mission_name;
    private String organizerName;
    private String finishPoint;
    private boolean move_time;
    private boolean needFinishIcon;
    protected CoreTime time_mission_end = null;
    private static final List<StarterBase> allStarters = new ArrayList<StarterBase>();

    public static void deinitScenarioMissionsStarters() {
        for (StarterBase starter : allStarters) {
            starter.finishImmediately();
        }
        allStarters.clear();
    }

    public StarterBase() {
        super(0, true);
        this.start();
        allStarters.add(this);
    }

    protected final String getFinishPoint() {
        return this.finishPoint;
    }

    public void start(String mission_name, String organizerName, String finishPoint, double timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        this.mission_name = mission_name;
        this.organizerName = organizerName;
        this.finishPoint = finishPoint;
        this.move_time = move_time;
        this.needFinishIcon = needFinishIcon;
    }

    public void run() {
        ScenarioMission.activateMission(this.mission_name, this.organizerName, this.finishPoint, this.time_mission_end, this.move_time, this.needFinishIcon);
        ConditionChecker conditionChecker = this.getConditionChecker(this.mission_name);
        if (conditionChecker != null) {
            MissionEventsMaker.makeMissionFinishChecker(conditionChecker);
        }
        this.finish();
    }

    public void finish() {
        super.finish();
        allStarters.remove(this);
    }

    public ConditionChecker getConditionChecker(String missionName) {
        return null;
    }

    public static void init0454Condition(String missionName) {
        MissionEventsMaker.makeMissionFinishChecker(new Start0454.Checker(new CoreTime(), missionName));
    }

    public static void init0430Condition(String missionName) {
        MissionEventsMaker.makeMissionFinishChecker(new Start0430.Checker(missionName));
    }

    public static void init0600Condition(String missionName) {
        MissionEventsMaker.makeMissionFinishChecker(new Start0600.Checker(missionName));
    }
}

