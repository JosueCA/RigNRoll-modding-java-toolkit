/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.HashMap;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;

public final class XmlStrings {
    private static final int INITIAL_CAPACITY = 24;
    private static final HashMap<String, String> nameResolveTable = new HashMap(24);
    public static final String DEFAULT_NAME = "unkonwn";
    public static final String THIS_NAME = "this";

    private XmlStrings() {
    }

    public static String resolveName(String nameToResolve) {
        if (null == nameToResolve) {
            ScenarioLogger.getInstance().parserLog(Level.SEVERE, "nameToResolve is null");
            return null;
        }
        String resolved = nameResolveTable.get(nameToResolve);
        if (null == resolved) {
            ScenarioLogger.getInstance().parserLog(Level.SEVERE, "can't resolve name == \"" + nameToResolve + '\"');
        }
        return resolved;
    }

    static {
        nameResolveTable.put("startscenariomission", "StartScenarioMissionAction");
        nameResolveTable.put("failorg", "FailOrgAction");
        nameResolveTable.put("startorg", "StartOrgAction");
        nameResolveTable.put("finishorg", "FinishOrgAction");
        nameResolveTable.put("startquest", "StartScenarioBranchAction");
        nameResolveTable.put("finishquest", "StopScenarioBranchAction");
        nameResolveTable.put("jou", "JournalAction");
        nameResolveTable.put("journal", "JournalAction");
        nameResolveTable.put("journal_active", "JournalActiveAction");
        nameResolveTable.put("phasequest", "SingleStepScenarioAdvanceAction");
        nameResolveTable.put("MP", "MissionPointMarkAction");
        nameResolveTable.put("wait", "TimeAction");
        nameResolveTable.put("so_animation", "SetSODefaultScriptAction");
        nameResolveTable.put("sc_countdown", "ScenarioCountdown");
        nameResolveTable.put("stop_sc_countdown", "StopScenarioCountdown");
        nameResolveTable.put("race", "CreateRace");
        nameResolveTable.put("QIsemitrailer", "CreateQISemitailer");
        nameResolveTable.put("deletequestitem", "DeleteQuestItem");
        nameResolveTable.put("removequestitem", "RemoveQuestItem");
        nameResolveTable.put("startmission", "StartMissionAction");
        nameResolveTable.put("declinemission", "DeclineScenerioMission");
        nameResolveTable.put("finishmission", "FinishScenarioMission");
        nameResolveTable.put("changemd", "ChangeMissionDestination");
        nameResolveTable.put("set_scenario_flag", "SetScenarioFlag");
        nameResolveTable.put("make_payoff", "MakePayOff");
        nameResolveTable.put("postinfo", "PostMissionInfoAction");
        nameResolveTable.put("deactivatemission", "DeactivateMission");
        nameResolveTable.put("scenario_stage", "SetTrapScenarioStageAction");
        nameResolveTable.put("scenario_finished", "ScenarioFinishedAction");
        nameResolveTable.put("police_immunity", "PoliceImmunity");
        nameResolveTable.put("kill_taken_missions", "KillTakenMissionsAction");
        nameResolveTable.put("traffic", "TrafficAction");
        nameResolveTable.put("so", "SpecialObjectEventChecker");
        nameResolveTable.put("questitem", "QuestItemEventChecker");
        nameResolveTable.put("daytime", "TimeEventChecker");
        nameResolveTable.put("blockso", "BlockSpecialObject");
        nameResolveTable.put("createquest", "StartScenarioBranchAction");
        nameResolveTable.put("unblockso", "UnblockSpecialObject");
        nameResolveTable.put("makecall", "MakeCallAction");
        nameResolveTable.put("removeaction", "RemoveTimeAction");
        nameResolveTable.put("scene", "SetSceneToRunAction");
        nameResolveTable.put("message", "MessageEventChecker");
        nameResolveTable.put("nativemessage", "NativeMessageListener");
        nameResolveTable.put("missionend", "MissionEndchecker");
        nameResolveTable.put("event", "PostMessageEvent");
        nameResolveTable.put("loosegame", "GameLooseAction");
        nameResolveTable.put("policescene", "ReservePoliceSceneScriptAction");
        nameResolveTable.put("reachtimer", "ReachPointTimer");
        nameResolveTable.put("removeresources", "MissionActionRemoveResources");
        nameResolveTable.put("faderemoveresource", "MissionActionRemoveResourcesFade");
        nameResolveTable.put("start_aside_mission", "CreateAsideMission");
        nameResolveTable.put("extermal_channel_appear", "ExternalChannelSayAppear");
        nameResolveTable.put("postinfo", "PostMissionInfoAction");
        nameResolveTable.put("fromhour", "start");
        nameResolveTable.put("tohour", "end");
        nameResolveTable.put("soname", "name");
        nameResolveTable.put("startscenariomission", "StartScenarioMissionAction");
    }

    public static final class AttributesStrings {
        public static final String DESCIPTION_ATTR_STR = "description";
        public static final String NAME_ATTR_STR = "name";
        public static final String ORG_ATTR_STR = "org";
        public static final String FINISH_ON_LAST_PHASE_ATTR_STR = "finishonlastphase";
        public static final String PHASE_ATTR_STR = "phase";
        public static final String FINISH_ATTR_STR = "finish";
        public static final String SO_ATTR_STR = "so";
        public static final String QUESTITEM_ATTR_STR = "questitem";
        public static final String SO_NAME_ATTR_STR = "soname";
        public static final String FROM_HOUR_ATTR_STR = "fromhour";
        public static final String TO_HOUR_ATTR_STR = "tohour";
        public static final String CREATE_QUEST_ATTR_STR = "createquest";
        public static final String GAIN_ATTR_STR = "gain";
        public static final String STATUS_ATTR_STR = "status";
        public static final String NOM_ATTR_STR = "nom";
        public static final String DAYS_ATTR_STR = "days";
        public static final String HOURS_ATTR_STR = "hours";
        public static final String DAY_TIME_ATTR_STR = "daytime";
        public static final String BLOCK_SO_ATTR_STR = "blockso";
        public static final String UNBLOCK_SO_ATTR_STR = "unblockso";
        public static final String MAKE_CALL_ATTR_STR = "makecall";
        public static final String REMOVE_TIME_ACTION_ATTR_STR = "removeaction";
        public static final String SCENE_ATTR_STR = "scene";
        public static final String MESSAGE_ATTR_STR = "message";
        public static final String NATIVE_MESSAGE_ATTR_STR = "nativemessage";
        public static final String MISSIONEND_ATTR_STR = "missionend";
        public static final String EVENT_ATTR_STR = "event";
        public static final String LOOSE_GAME_ATTR_STR = "loosegame";
        public static final String POLICE_SCENE_RESERVE_ATTR_STR = "policescene";
        public static final String REACHPOINT_TIMER_ATTR_STR = "reachtimer";
        public static final String REMOVEMISSIONRESOURSES_ATTR_STR = "removeresources";
        public static final String REMOVEMISSIONRESOURSESFADE_ATTR_STR = "faderemoveresource";
        public static final String POST_MISSION_INFO_ATTR_STR = "postinfo";
        public static final String START_ASIDE_MISSION_ATTR_STR = "start_aside_mission";
        public static final String EXTERNAL_CHANNEL_APPEARED_ATTR_STR = "extermal_channel_appear";
        public static final String STAR_SCENARIO_MISSION_ATTR_STR = "startscenariomission";

        private AttributesStrings() {
        }
    }

    public static final class TagStrings {
        public static final String CB_CALL_NODE = "cbcall";
        public static final String ELEMENT_NODE = "element";
        public static final String ISO_QUESTS_NODE_STR = "isoquests";
        public static final String QUESTS_NODE_STR = "quests";
        public static final String ITEM_NODE_STR = "item";
        public static final String TASK_NODE_STR = "task";
        public static final String CONDITION_NODE_STR = "cond";
        public static final String ACTION_NODE_STR = "action";
        public static final String Q_NODE_STR = "q";
        public static final String PHASE_NODE_STR = "phase";
        public static final String START_NODE_STR = "start";
        public static final String FINISH_NODE_STR = "finish";
        public static final String EXPIRED_NODE_STR = "expired";
        public static final String FAIL_NODE_STR = "fail";
        public static final String JOU_NODE_STR = "jou";
        public static final String JOUFULL_NODE_STR = "journal";
        public static final String JOURNAL_ACTIVE_NODE_STR = "journal_active";
        public static final String START_QUEST_NODE_STR = "startquest";
        public static final String FINISH_QUEST_NODE_STR = "finishquest";
        public static final String START_ORG_NODE_STR = "startorg";
        public static final String FINISH_ORG_NODE_STR = "finishorg";
        public static final String FAIL_ORG_NODE_STR = "failorg";
        public static final String MP_NODE_STR = "MP";
        public static final String PHASE_QUEST_NODE_STR = "phasequest";
        public static final String WAIT_NODE_STR = "wait";
        public static final String SETSODEFAULT_STR = "so_animation";
        public static final String ENDSCENARIO_STR = "sc_countdown";
        public static final String STOPENDSCENARIO_STR = "stop_sc_countdown";
        public static final String CREATE_RACE_ATTR_STR = "race";
        public static final String CREATE_QUEST_ITEM_SEMITRAILER_ATTR_STR = "QIsemitrailer";
        public static final String CREATE_QUEST_ITEM_DELETE_ATTR_STR = "deletequestitem";
        public static final String CREATE_QUEST_ITEM_REMOVE_ATTR_STR = "removequestitem";
        public static final String START_MISSION_NODE_STR = "startmission";
        public static final String DECLINE_MISSION_NODE_STR = "declinemission";
        public static final String FINISH_MISSION_NODE_STR = "finishmission";
        public static final String POST_MISSIONINFO_NODE_STR = "postinfo";
        public static final String DEACTIVATE_MISSION_NODE_STR = "deactivatemission";
        public static final String STAR_SCENARIO_MISSION_NODE_STR = "startscenariomission";
        public static final String CHANGE_MISSION_DESTINATION_NODE_STR = "changemd";
        public static final String SCENARIO_FLAG_NODE_STR = "set_scenario_flag";
        public static final String MAKE_PAYOFF_NODE_STR = "make_payoff";
        public static final String STAGE_NODE_STR = "scenario_stage";
        public static final String SCENARIO_FINISHED_NODE_STR = "scenario_finished";
        public static final String POLICE_IMMUNITY_NODE_STR = "police_immunity";
        public static final String KILL_TAKEN_MISSIONS_NODE_STR = "kill_taken_missions";
        public static final String TRAFFIC_NODE_STR = "traffic";

        private TagStrings() {
        }
    }
}

