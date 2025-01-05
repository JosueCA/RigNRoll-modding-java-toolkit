/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.ArrayList;
import rnrcore.CoreTime;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.CreateMissionEvent;
import rnrscenario.missions.IMissionStarter;
import rnrscenario.missions.IStartMissionListener;
import rnrscenario.missions.MissionCreationContext;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.QIVisit;
import rnrscenario.missions.StartMissionListeners;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.map.Place;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;

public class ScenarioMission {
    static InformationChannelData channel;

    private static String getChannelName(String mission_name) {
        return mission_name;
    }

    public static void activateMission(String mission_name, String organizerName, String finishPoint, CoreTime timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        MissionInfo mission = ScenarioMission.createMission(mission_name, organizerName, finishPoint, timeForMissionEnd, move_time, needFinishIcon);
        MissionPlacement mission_placement = new MissionPlacement(mission, new ArrayList<String>());
        MissionSystemInitializer.getMissionsManager().placeMissionToWorld(mission_placement);
        MissionSystemInitializer.getMissionsManager().activateDependantMission(mission_name, mission_name);
        EventsController.getInstance().eventHappen(new CreateMissionEvent(mission_name));
        IStartMissionListener lst = StartMissionListeners.getInstance().getStartMissionListener(mission_name);
        String missionStartPlaceName = null;
        if (null != lst) {
            missionStartPlaceName = lst.missionStarted();
        }
        mission.setMissionStartPlaceName(missionStartPlaceName);
    }

    public static void activateMissionLoad(String mission_name, String organizerName, String finishPoint, CoreTime timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        MissionInfo mission = ScenarioMission.createMission(mission_name, organizerName, finishPoint, timeForMissionEnd, move_time, needFinishIcon);
        MissionPlacement mission_placement = new MissionPlacement(mission, new ArrayList<String>());
        MissionSystemInitializer.getMissionsManager().placeMissionToWorld(mission_placement);
    }

    private static MissionInfo createMission(String mission_name, String organizerName, String finishPoint, CoreTime timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        ScenarioMission.makefakechannel(ScenarioMission.getChannelName(mission_name));
        QIVisit qi = new QIVisit();
        qi.model = "VISIT";
        ArrayList<InformationChannelData> start_channels = new ArrayList<InformationChannelData>();
        ArrayList<ScriptAction> start_actions = new ArrayList<ScriptAction>();
        ArrayList<ScriptAction> any_end_actions = new ArrayList<ScriptAction>();
        MissionInfo result = new MissionInfo(mission_name, finishPoint, organizerName, qi, start_channels, start_actions, any_end_actions, any_end_actions, any_end_actions, any_end_actions, any_end_actions);
        result.setScenarioMission(true);
        result.setStarter(new Params(timeForMissionEnd, move_time, needFinishIcon));
        return result;
    }

    private static void makefakechannel(String resource_ref) {
        ArrayList<ScriptAction> appear_actions = new ArrayList<ScriptAction>();
        ArrayList<ScriptAction> accept_actions = new ArrayList<ScriptAction>();
        ArrayList<ScriptAction> decline_actions = new ArrayList<ScriptAction>();
        MissionCreationContext context = new MissionCreationContext(resource_ref);
        channel = new InformationChannelData("CbvChannel", resource_ref, null, appear_actions, accept_actions, decline_actions, context);
    }

    static class Params
    implements IMissionStarter {
        CoreTime endTime;
        boolean move_time;
        boolean needFinishIcon;

        Params(CoreTime endTime, boolean move_time, boolean needFinishIcon) {
            this.endTime = endTime;
            this.move_time = move_time;
            this.needFinishIcon = needFinishIcon;
        }

        public String startMission(String mission_name, boolean isChannelImmediate, String info_channel_point, String qi_point, String finish_point) {
            Place place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1);
            if (place.getName().compareTo(finish_point) == 0) {
                place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1, place);
            }
            MissionEventsMaker.startMission_ScenarioMission(mission_name, isChannelImmediate, place.getName(), qi_point, finish_point, this.endTime, this.move_time, this.needFinishIcon);
            return place.getName();
        }

        public void startMission(String mission_name, boolean isChannelImmediate, String info_channel_point, String qi_point, String finish_point, String nearestPlaceName) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(nearestPlaceName);
            assert (place != null);
            MissionEventsMaker.startMission_ScenarioMission(mission_name, isChannelImmediate, place.getName(), qi_point, finish_point, this.endTime, this.move_time, this.needFinishIcon);
        }
    }
}

