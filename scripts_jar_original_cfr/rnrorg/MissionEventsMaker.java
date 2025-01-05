/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import menu.JavaEvents;
import menuscript.BarMenu;
import players.aiplayer;
import rnrcore.CoreTime;
import rnrcore.SCRuniperson;
import rnrcore.eng;
import rnrcore.loc;
import rnrloggers.MissionsLogger;
import rnrorg.EventGetPointLocInfo;
import rnrorg.IDeclineOrgListener;
import rnrorg.IStoreorgelement;
import rnrorg.MissionOrganiser;
import rnrorg.Organizers;
import rnrorg.Scorgelement;
import rnrorg.WarehouseOrder;
import rnrorg.organaiser;
import rnrscenario.missions.IChannelEventListener;
import rnrscenario.missions.StartMissionListeners;
import rnrscenario.missions.infochannels.InfoChannelAcceptEvent;
import rnrscenario.missions.infochannels.InfoChannelAppearEvent;
import rnrscenario.missions.infochannels.InfoChannelDeclineEvent;
import rnrscenario.missions.infochannels.InfoChannelEndEvent;
import rnrscr.IMissionInformation;
import scriptEvents.EventsControllerHelper;
import scriptEvents.MissionEndDialogEnded;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionEventsMaker {
    private static volatile boolean flag = false;

    public static void changeMissionDestination(String mission_name, String finish_point) {
        JavaEvents.SendEvent(73, 0, new GetTwoStrings(mission_name, finish_point));
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);
        if (null == org_name || org_name.length() == 0) {
            eng.err("changeMissionDestination couldn't find organiser for mission " + mission_name);
            return;
        }
        IStoreorgelement elem = Organizers.getInstance().get(org_name);
        if (null == elem || !(elem instanceof Scorgelement)) {
            eng.err("changeMissionDestination couldn't find organiser element named " + org_name + " for mission " + mission_name + " or have another class realization.");
        } else {
            Scorgelement scorgelem = (Scorgelement)elem;
            scorgelem.changeDestination(finish_point);
            organaiser.getInstance().updateMissionsOrgElements();
        }
    }

    public static void startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point) {
        MissionEventsMaker.startMission_parammed(0, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, -1, null, false, true);
    }

    public static void startMission_AnnounceBigRace(int race_uid, String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point) {
        MissionEventsMaker.startMission_parammed(1, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, race_uid, null, false, true);
    }

    public static void startMission_ScenarioMission(String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point, CoreTime time_on_mission, boolean move_time, boolean needFinishIcon) {
        MissionEventsMaker.startMission_parammed(2, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, -1, time_on_mission, move_time, needFinishIcon);
    }

    private static void startMission_parammed(int event_value, String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point, int race_uid, CoreTime time_on_mission, boolean move_time, boolean needFinishIcon) {
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);
        if (null == org_name) {
            MissionsLogger.getInstance().doLog("startMission. Cannot find org name for mission named " + mission_name, Level.SEVERE);
            return;
        }
        IStoreorgelement elem = Organizers.getInstance().get(org_name);
        if (null == elem) {
            MissionsLogger.getInstance().doLog("startMission. Cannot find org instance for mission named " + mission_name + "and org named " + org_name, Level.SEVERE);
            return;
        }
        elem.addDeclineListener(new DeclineMissionListener(mission_name));
        if (elem instanceof Scorgelement) {
            if (qi_point == null) {
                qi_point = info_channel_point;
            }
            Scorgelement org_elem = (Scorgelement)elem;
            org_elem.setSerialPoints(info_channel_point, qi_point, finish_point);
            JavaEvents.SendEvent(48, event_value, new StartMissionData(mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, org_elem, race_uid, time_on_mission, move_time, needFinishIcon));
        } else {
            MissionsLogger.getInstance().doLog("startMission. Undefined behovouir. Org instance for mission named " + mission_name + "and org named " + org_name + " has class named " + elem.getClass().getName(), Level.SEVERE);
        }
    }

    public static void declineMission(String mission_name) {
        JavaEvents.SendEvent(49, 0, new Data(mission_name));
    }

    public static void declineWareHouseMission() {
        JavaEvents.SendEvent(49, 1, new Object());
    }

    public static void createQuestItemSemitrailer(String mission_name, String merchandise_name, String point_name) {
        JavaEvents.SendEvent(51, 0, new QuestItemData(mission_name, merchandise_name, point_name, "no uid"));
    }

    public static void createQuestItemPackage(String mission_name, String merchandise_name, String point_name) {
        JavaEvents.SendEvent(51, 1, new QuestItemData(mission_name, merchandise_name, point_name, "no uid"));
    }

    public static void createChannelResourcesPlaces(String mission_name, String channel_uid, ArrayList<String> places, int priority, boolean important, boolean fginish, boolean succes, boolean fmain) {
        GetMissionChannelResourcesInfo info = new GetMissionChannelResourcesInfo(mission_name, channel_uid, priority, important, fginish, succes, fmain);
        info.data = new Vector<String>(places);
        JavaEvents.SendEvent(51, 6, info);
    }

    public static void createQuestItemAddItem(String mission_name, String model_name, int color, String uid) {
        QuestItemData data = new QuestItemData(mission_name, model_name, "", uid);
        data.color = color;
        JavaEvents.SendEvent(51, 2, data);
    }

    public static void createQuestItemNPC(String mission_name, String model_name, String uid) {
        JavaEvents.SendEvent(51, 3, new QuestItemData(mission_name, model_name, "", uid));
    }

    public static void createQuestItemPassanger(String mission_name, String model_name, String point_name) {
        JavaEvents.SendEvent(51, 4, new QuestItemData(mission_name, model_name, point_name, "no uid"));
    }

    public static void createQuestItemPassangerNoAnimation(String mission_name, String model_name, String point_name) {
        JavaEvents.SendEvent(51, 4, new QuestItemData(mission_name, model_name, point_name, "no uid", false));
    }

    public static void createQuestItemVisit(String mission_name, String model_name, String point_name) {
        JavaEvents.SendEvent(51, 5, new QuestItemData(mission_name, model_name, point_name, "no uid"));
    }

    public static void clearResource(String mission_name, String uid) {
        GetTwoStrings info = new GetTwoStrings(mission_name, uid);
        JavaEvents.SendEvent(60, 0, info);
    }

    public static void abonishMission(String mission_name) {
        DataName info = new DataName(mission_name);
        JavaEvents.SendEvent(61, 0, info);
    }

    public static void declineMissionNotFromOrganoser(String mission_name) {
        organaiser.declineMissionByName(mission_name);
    }

    public static SCRuniperson queueNPCResourseForDialog(IMissionInformation dialog) {
        QueueDialogResource res = new QueueDialogResource(dialog.getMissionName(), dialog.getChannelId());
        JavaEvents.SendEvent(52, 1, res);
        return res.person;
    }

    public static aiplayer queueNPCPlayer_FreeFromVoter(IMissionInformation dialog, String identitie) {
        QueueDialogResource res = new QueueDialogResource(dialog.getMissionName(), dialog.getChannelId());
        JavaEvents.SendEvent(52, 2, res);
        aiplayer npc = aiplayer.getNamedAiplayerHiPoly(identitie, dialog.getMissionName() + " hot person");
        return npc;
    }

    public static void npcPlayer_ResumeVoter(IMissionInformation dialog) {
        QueueDialogResource res = new QueueDialogResource(dialog.getMissionName(), dialog.getChannelId());
        JavaEvents.SendEvent(52, 3, res);
    }

    public static void channelSayAccept(String channel_id, String mission_name, String point_name, boolean isChannelImmediate) {
        IChannelEventListener lst;
        JavaEvents.SendEvent(47, 1, new DataName(mission_name));
        if (null == point_name) {
            point_name = "";
        }
        if ((lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name)) != null) {
            lst.eventOnChannel(point_name, channel_id, isChannelImmediate);
        }
        InfoChannelAcceptEvent event2 = new InfoChannelAcceptEvent(channel_id, point_name);
        EventsControllerHelper.eventHappened(event2);
        EventsControllerHelper.messageEventHappened(mission_name + " accepted");
    }

    public static void channelSayDeclein(String channel_id, String mission_name, String point_name, boolean isChannelImmediate) {
        IChannelEventListener lst;
        JavaEvents.SendEvent(47, 2, new DataName(mission_name));
        if (null == point_name) {
            point_name = "";
        }
        if ((lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name)) != null) {
            lst.eventOnChannel(point_name, channel_id, isChannelImmediate);
        }
        InfoChannelDeclineEvent event2 = new InfoChannelDeclineEvent(channel_id, point_name);
        EventsControllerHelper.eventHappened(event2);
        EventsControllerHelper.messageEventHappened(mission_name + " declined");
    }

    public static void channelSayAppear(String channel_id, String mission_name, String point_name, boolean isChannelImmediate) {
        IChannelEventListener lst;
        JavaEvents.SendEvent(47, 0, new GetBoolNamed(mission_name, isChannelImmediate));
        if (point_name == null) {
            point_name = "";
        }
        if ((lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name)) != null) {
            lst.eventOnChannel(point_name, channel_id, isChannelImmediate);
        }
        InfoChannelAppearEvent event2 = new InfoChannelAppearEvent(channel_id, point_name);
        EventsControllerHelper.eventHappened(event2);
    }

    public static void channelSayEnd(String channel_id, String mission_name, String point_name, boolean isChannleImmediate) {
        MissionEndDialogEnded event2 = new MissionEndDialogEnded(mission_name, channel_id);
        EventsControllerHelper.eventHappened(event2);
        if (null == point_name) {
            point_name = "";
        }
        InfoChannelEndEvent endevent = new InfoChannelEndEvent(channel_id, point_name);
        EventsControllerHelper.eventHappened(endevent);
    }

    public static void channelSayEndEventToNative(String mission_name, String channelID) {
        JavaEvents.SendEvent(47, 3, new GetTwoStrings(mission_name, channelID));
    }

    public static void channelCleanResources(String channelID) {
        JavaEvents.SendEvent(47, 8, new DataName(channelID));
    }

    public static void cleanResourcesFade(String mission_name) {
        JavaEvents.SendEvent(47, 9, new DataName(mission_name));
    }

    public static void updateOrganiser(String mission_name, Scorgelement element) {
        JavaEvents.SendEvent(50, 0, new UpdateData(mission_name, element));
    }

    public static void updateOrganiserSligtly(String mission_name, Scorgelement element) {
        JavaEvents.SendEvent(50, 2, new UpdateData(mission_name, element));
    }

    public static void updateOrganiser(String mission_name, WarehouseOrder element) {
        JavaEvents.SendEvent(50, 1, new UpdateDataWarehouse(mission_name, element));
    }

    public static EventGetPointLocInfo getLocalisationMissionPointInfo(String point_name) {
        EventGetPointLocInfo info = new EventGetPointLocInfo(point_name);
        if (point_name == null) {
            info.long_name = "";
            info.short_name = "";
            return info;
        }
        JavaEvents.SendEvent(56, 0, info);
        info.long_name = loc.getMissionPointLongName(info.long_name);
        info.short_name = loc.getMissionPointShortName(info.short_name);
        return info;
    }

    public static EventGetPointLocInfo getLocalisationMissionPointInfoLocRefs(String point_name) {
        EventGetPointLocInfo info = new EventGetPointLocInfo(point_name);
        if (point_name == null) {
            info.long_name = "";
            info.short_name = "";
            return info;
        }
        JavaEvents.SendEvent(56, 0, info);
        return info;
    }

    public static String getLocalisationNPCInfo(String person_name) {
        GetNpcLoc info = new GetNpcLoc(person_name);
        JavaEvents.SendEvent(57, 0, info);
        return info.person_loc;
    }

    public static void RegisterActivationPoint(String mission_name, String channel_uid) {
        GetTwoStrings info = new GetTwoStrings(mission_name, channel_uid);
        JavaEvents.SendEvent(47, 4, info);
    }

    public static void RegisterSuccesMissionChannelAsBounding(String mission_name, String uid) {
        GetTwoStrings info = new GetTwoStrings(mission_name, uid);
        JavaEvents.SendEvent(47, 7, info);
    }

    public static boolean isChannelBlockedByPackage(String mission_name, String point_name) {
        GetBooleanOnTwoStrings info = new GetBooleanOnTwoStrings(point_name, mission_name);
        JavaEvents.SendEvent(47, 10, info);
        return info.value;
    }

    public static boolean isChannelBlockedByPassanger(String mission_name, String point_name) {
        GetBooleanOnTwoStrings info = new GetBooleanOnTwoStrings(point_name, mission_name);
        JavaEvents.SendEvent(47, 11, info);
        return info.value;
    }

    public static boolean isPassanerSlotBuzzy() {
        GetBool info = new GetBool();
        JavaEvents.SendEvent(58, 0, info);
        return info.value;
    }

    public static boolean freeSlotForMission(String mission) {
        GetBooleanOnTwoStrings info = new GetBooleanOnTwoStrings(mission, "");
        JavaEvents.SendEvent(87, 0, info);
        return info.value;
    }

    public static boolean samePassanger(String mission) {
        GetBoolNamed info = new GetBoolNamed(mission, true);
        JavaEvents.SendEvent(85, 0, info);
        return info.value;
    }

    public static boolean isWide() {
        GetBool info = new GetBool();
        JavaEvents.SendEvent(88, 0, info);
        return info.value;
    }

    public static boolean isPackageSlotBuzzy() {
        GetBool info = new GetBool();
        JavaEvents.SendEvent(58, 1, info);
        return info.value;
    }

    public static boolean isTrailerSlotBuzzy() {
        GetBool info = new GetBool();
        JavaEvents.SendEvent(58, 2, info);
        return info.value;
    }

    public static BarMenu.BarPack[] querryBarSlots(String barname) {
        GetBarSlotsInformation info = new GetBarSlotsInformation(barname);
        JavaEvents.SendEvent(59, 0, info);
        BarMenu.BarPack[] res = new BarMenu.BarPack[info.data.size()];
        for (int i = 0; i < info.data.size(); ++i) {
            BarMenu.BarPack pack = new BarMenu.BarPack();
            SingleBarSlotsInformation single_info = (SingleBarSlotsInformation)info.data.get(i);
            pack.type = single_info.is_pack ? 1 : 3;
            pack.mission_name = single_info.mission_name;
            res[i] = pack;
        }
        return res;
    }

    public static void pickupQuestItem(String bar_name, String mission_name) {
        GetTwoStrings info = new GetTwoStrings(bar_name, mission_name);
        JavaEvents.SendEvent(59, 1, info);
    }

    public static void makeRadioBreakNews(String resource) {
        DataName data = new DataName(resource);
        JavaEvents.SendEvent(47, 6, data);
    }

    public static void channalIsActive(String ch_name) {
        DataName data = new DataName(ch_name);
        JavaEvents.SendEvent(83, 0, data);
    }

    public static void missionOnMoney(String mission) {
        DataName data = new DataName(mission);
        JavaEvents.SendEvent(84, 0, data);
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        MissionEventsMaker.flag = flag;
    }

    public static void makeMissionFinishChecker(Object checker) {
        if (eng.noNative) {
            MissionEventsMaker.setFlag(true);
        } else {
            JavaEvents.SendEvent(86, 0, checker);
        }
    }

    public static class DeclineMissionListener
    implements IDeclineOrgListener {
        private String mission_name;

        public DeclineMissionListener(String mission_name) {
            this.mission_name = mission_name;
        }

        public void declined() {
            MissionEventsMaker.declineMission(this.mission_name);
        }

        public String getMission_name() {
            return this.mission_name;
        }
    }

    static class GetMissionChannelResourcesInfo {
        String affinity;
        String name;
        Vector data = new Vector();
        int priority;
        boolean important;
        boolean finish;
        boolean succes;
        boolean is_main;

        GetMissionChannelResourcesInfo(String affinity, String name, int priority, boolean important, boolean finish, boolean succes, boolean is_main) {
            this.name = name;
            this.important = important;
            this.finish = finish;
            this.priority = priority;
            this.succes = succes;
            this.is_main = is_main;
            this.affinity = affinity;
        }
    }

    static class GetBooleanOnTwoStrings {
        String name;
        String name1;
        boolean value;

        GetBooleanOnTwoStrings(String name, String name1) {
            this.name = name;
            this.name1 = name1;
        }
    }

    static class GetThreeStrings {
        String name;
        String name1;
        String name2;

        GetThreeStrings(String name, String name1, String name2) {
            this.name = name;
            this.name1 = name1;
            this.name2 = name2;
        }
    }

    static class GetTwoStrings {
        String name;
        String name1;

        GetTwoStrings(String name, String name1) {
            this.name = name;
            this.name1 = name1;
        }
    }

    static class SingleBarSlotsInformation {
        boolean is_pack;
        boolean is_pass;
        String mission_name;

        SingleBarSlotsInformation() {
        }
    }

    static class GetBarSlotsInformation {
        String name;
        Vector data = new Vector();

        GetBarSlotsInformation(String name) {
            this.name = name;
        }
    }

    static class GetBoolNamed {
        boolean value;
        String name;

        GetBoolNamed(String name, boolean value) {
            this.name = name;
            this.value = value;
        }
    }

    static class GetBool {
        boolean value;

        GetBool() {
        }
    }

    static class GetNpcLoc {
        String person;
        String person_loc;

        GetNpcLoc(String person) {
            this.person = person;
        }
    }

    static class UpdateDataWarehouse {
        String mission_name = null;
        WarehouseOrder element = null;

        UpdateDataWarehouse(String mission_name, WarehouseOrder element) {
            this.mission_name = mission_name;
            this.element = element;
        }
    }

    static class UpdateData {
        String mission_name = null;
        Scorgelement element = null;

        UpdateData(String mission_name, Scorgelement element) {
            this.mission_name = mission_name;
            this.element = element;
        }
    }

    static class DataName {
        String name;

        DataName(String name) {
            this.name = name;
        }
    }

    static class QueueDialogResource {
        SCRuniperson person;
        String mission_name;
        String channel_uid;

        QueueDialogResource() {
        }

        QueueDialogResource(String mission_name, String channeluid) {
            this.mission_name = mission_name;
            this.channel_uid = channeluid;
        }
    }

    static class QuestItemData {
        String mission_name;
        String merchandise_name;
        String point_name;
        String uid;
        int color;
        boolean make_place;

        QuestItemData() {
        }

        QuestItemData(String mission_name, String merchandise_name, String point_name, String uid) {
            this.mission_name = mission_name;
            this.merchandise_name = merchandise_name;
            this.point_name = point_name;
            this.uid = uid;
            this.make_place = true;
        }

        QuestItemData(String mission_name, String merchandise_name, String point_name, String uid, boolean make_place) {
            this.mission_name = mission_name;
            this.merchandise_name = merchandise_name;
            this.point_name = point_name;
            this.uid = uid;
            this.make_place = make_place;
        }
    }

    static class StartMissionData {
        String mission_name;
        CoreTime time_on_mission = null;
        boolean move_time = false;
        boolean needFinishIcon = true;
        boolean channel_immediate;
        String p1;
        String p2;
        String p3;
        int race_uid;
        Scorgelement element;

        StartMissionData() {
        }

        StartMissionData(String mission_name, boolean isChannelImmediate, String p1, String p2, String p3, Scorgelement element, int race_id) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_immediate = isChannelImmediate;
            this.race_uid = race_id;
        }

        StartMissionData(String mission_name, boolean isChannelImmediate, String p1, String p2, String p3, Scorgelement element, int race_id, CoreTime time_on_mission, boolean move_time, boolean needFinishIcon) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_immediate = isChannelImmediate;
            this.race_uid = race_id;
            this.time_on_mission = time_on_mission;
            this.move_time = move_time;
            this.needFinishIcon = needFinishIcon;
        }
    }

    static class Data {
        String mission_name;
        CoreTime time_on_mission = null;
        boolean move_time = false;
        boolean needFinishIcon = true;
        String channel_uid;
        String p1;
        String p2;
        String p3;
        int race_uid;
        Scorgelement element;

        Data() {
        }

        Data(String mission_name, String channel_uid, String p1, String p2, String p3, Scorgelement element, int race_id) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_uid = channel_uid;
            this.race_uid = race_id;
        }

        Data(String mission_name, String channel_uid, String p1, String p2, String p3, Scorgelement element, int race_id, CoreTime time_on_mission, boolean move_time, boolean needFinishIcon) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_uid = channel_uid;
            this.race_uid = race_id;
            this.time_on_mission = time_on_mission;
            this.move_time = move_time;
            this.needFinishIcon = needFinishIcon;
        }

        Data(String mission_name) {
            this.mission_name = mission_name;
        }
    }
}

