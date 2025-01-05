/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import menuscript.Converts;
import rnrcore.CoreTime;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.loc;
import rnrorg.EmptyCustomer;
import rnrorg.IStoreorgelement;
import rnrorg.MissionEventsMaker;
import rnrorg.MissionTime;
import rnrorg.Organizers;
import rnrorg.RewardForfeit;
import rnrorg.Scorgelement;
import rnrorg.XmlInit;
import rnrorg.journable;
import rnrorg.journalelement;
import rnrscenario.missions.IMissionStarter;
import rnrscenario.missions.MissionCreationContext;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.QIVisit;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscr.smi.BigRaceAnnounceCreator;
import scriptActions.ExternalChannelSayAppear;
import scriptActions.JournalAction;
import scriptActions.JournalActiveAction;
import scriptActions.ScriptAction;
import scriptActions.StartMissionAction;
import scriptEvents.AfterEventsRun;
import scriptEvents.IAfterEventsRun;

public class BigRaceAnnounceMission
implements Serializable {
    static final long serialVersionUID = 0L;
    private static final String[] ANNOUNCED_JOURNAL = new String[]{"FAKE", "BIGRACE ANNOUNCED 1", "BIGRACE ANNOUNCED 2", "BIGRACE ANNOUNCED 3", "BIGRACE ANNOUNCED 4"};
    private static int count_announsments = 0;
    public static final String APPEAR_CHANNEL_JOURNAL_NOTE = "";
    public static final String DESCRIPTION_LOC = "";
    private static BigRaceAnnounceMission instance = null;
    private HashMap<Integer, Params> missions = new HashMap();

    private static BigRaceAnnounceMission getInstance() {
        if (null == instance) {
            instance = new BigRaceAnnounceMission();
        }
        return instance;
    }

    private static String getDescriptionString(String shortracename) {
        return loc.getBigraceShortName(shortracename);
    }

    private static String createResourceRefForMissionStartChannel(String mission_name) {
        return mission_name + " startchannel";
    }

    private static JournalNoteData getAppearStartChannleJournalNote(int type, String shortracename, CoreTime startTime, String source, String destination, double limitrating) {
        ArrayList<Macros> macroces = new ArrayList<Macros>();
        macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("CITYNAME", source)));
        macroces.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("CITYNAME", destination)));
        macroces.add(new Macros("SHORTRACENAME", MacroBuilder.makeSimpleMacroBody("BIGRACE_SHORTNAME", shortracename)));
        macroces.add(new Macros("RATING", MacroBuilder.makeSimpleMacroBody("" + limitrating)));
        macroces.addAll(Converts.ConvertDateMacroAbsolute(startTime.gMonth(), startTime.gDate(), startTime.gYear(), startTime.gHour(), startTime.gMinute()));
        JournalNoteData result = new JournalNoteData(ANNOUNCED_JOURNAL[type], macroces);
        return result;
    }

    private static void activateMission(Params params2, boolean isImmediate) {
        MissionInfo mission = BigRaceAnnounceMission.createMission(params2, isImmediate);
        mission.setSelfPlacedMission(true);
        MissionPlacement mission_placement = new MissionPlacement(mission, new ArrayList<String>());
        MissionSystemInitializer.getMissionsManager().placeMissionToWorld(mission_placement);
        params2.created_mission = mission;
    }

    public static void announceBigRace(boolean immediate_mission, String finish_point, int race_uid, int type, String raceName, String shortRaceName, String startWarehouse, String finishWarehouse, int moneyPrize, double ratingThreshold, int yearstart, int monthstart, int daystart, int hourstart, int minstart, int yearfinish, int monthfinish, int dayfinish, int hourfinish, int minfinish, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        Params params2 = new Params(finish_point, race_uid, type, raceName, shortRaceName, startWarehouse, finishWarehouse, moneyPrize, ratingThreshold, yearstart, monthstart, daystart, hourstart, minstart, yearfinish, monthfinish, dayfinish, hourfinish, minfinish, yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
        BigRaceAnnounceMission.getInstance().missions.put(race_uid, params2);
        BigRaceAnnounceMission.activateMission(params2, immediate_mission);
        if (immediate_mission) {
            AfterEventsRun.getInstance().addListener(new ImmediateMissionAnnounce(params2.created_mission.getName()));
        }
    }

    public static void clearAnnounced(int race_uid) {
        if (BigRaceAnnounceMission.getInstance().missions.containsKey(race_uid)) {
            Params params2 = BigRaceAnnounceMission.getInstance().missions.remove(race_uid);
            params2.created_mission.setSelfPlacedMission(false);
        }
    }

    private static MissionInfo createMission(Params params2, boolean isImmediate) {
        ArrayList<ScriptAction> decline_actions;
        StartMissionAction act;
        ArrayList<ScriptAction> accept_actions;
        JournalNoteData journalData;
        ArrayList<ScriptAction> appear_actions;
        Scorgelement orgElement;
        String mission_name = "BigRaceAnnounce" + params2.race_uid + "nom" + count_announsments++;
        String organizerName = mission_name + " org";
        String resource_ref = BigRaceAnnounceMission.createResourceRefForMissionStartChannel(mission_name);
        String finishPointName = params2.finish_point;
        MissionCreationContext context = new MissionCreationContext(mission_name);
        QIVisit qi = new QIVisit();
        qi.model = "VISIT";
        Scorgelement orgelement = null;
        IStoreorgelement.Type tip = IStoreorgelement.Type.notype;
        switch (params2.type) {
            case 4: {
                tip = IStoreorgelement.Type.bigrace0_announce;
                break;
            }
            case 3: {
                tip = IStoreorgelement.Type.bigrace1_announce;
                break;
            }
            case 2: {
                tip = IStoreorgelement.Type.bigrace2_announce;
                break;
            }
            case 1: {
                tip = IStoreorgelement.Type.bigrace3_announce;
                break;
            }
            case 0: {
                tip = IStoreorgelement.Type.bigrace4_announce;
            }
        }
        orgelement = orgElement = new Scorgelement(organizerName, tip, false, 8, new RewardForfeit(0.0, 0.0, 0.0), 0, new RewardForfeit(0.0, 0.0, 0.0), BigRaceAnnounceMission.getDescriptionString(params2.shortRaceName), new EmptyCustomer(), new MissionTime(), new MissionTime(), null, null, new journable[XmlInit.TAG_FAIL.length]);
        Organizers.getInstance().addSpecial(organizerName, orgelement);
        ArrayList<InformationChannelData> start_channels = new ArrayList<InformationChannelData>();
        if (!isImmediate) {
            appear_actions = new ArrayList<ScriptAction>();
            journalData = BigRaceAnnounceMission.getAppearStartChannleJournalNote(params2.type, params2.shortRaceName, new CoreTime(params2.yearstart, params2.monthstart, params2.daystart, params2.hourstart, params2.minstart), params2.startWarehouse, params2.finishWarehouse, params2.ratingThreshold);
            appear_actions.add(new JournalActiveAction(new journalelement(journalData.getDescriptionLocRef(), journalData.getMacrosPairs())));
            accept_actions = new ArrayList<ScriptAction>();
            act = new StartMissionAction();
            act.name = mission_name;
            act.setActivatedBy(mission_name);
            accept_actions.add(act);
            decline_actions = new ArrayList<ScriptAction>();
            BigRaceAnnounceCreator dataForChannelConstruct = new BigRaceAnnounceCreator(params2.race_uid, params2.type, params2.raceName, params2.shortRaceName, params2.startWarehouse, params2.finishWarehouse, params2.moneyPrize, params2.ratingThreshold, params2.yearstart, params2.monthstart, params2.daystart, params2.hourstart, params2.minstart, params2.yearfinish, params2.monthfinish, params2.dayfinish, params2.hourfinish, params2.minfinish, params2.yearArticleLife, params2.monthArticleLife, params2.dayArticleLife, params2.hourArticleLife);
            InformationChannelData channel = new InformationChannelData("ArticleChannel", resource_ref, dataForChannelConstruct, appear_actions, accept_actions, decline_actions, context);
            start_channels.add(channel);
        } else {
            appear_actions = new ArrayList();
            journalData = BigRaceAnnounceMission.getAppearStartChannleJournalNote(params2.type, params2.shortRaceName, new CoreTime(params2.yearstart, params2.monthstart, params2.daystart, params2.hourstart, params2.minstart), params2.startWarehouse, params2.finishWarehouse, params2.ratingThreshold);
            appear_actions.add(new JournalAction(new journalelement(journalData.getDescriptionLocRef(), journalData.getMacrosPairs())));
            act = new StartMissionAction();
            act.name = mission_name;
            act.setActivatedBy(mission_name);
            appear_actions.add(act);
            accept_actions = new ArrayList();
            decline_actions = new ArrayList();
            BigRaceAnnounceCreator dataForChannelConstruct = new BigRaceAnnounceCreator(params2.race_uid, params2.type, params2.raceName, params2.shortRaceName, params2.startWarehouse, params2.finishWarehouse, params2.moneyPrize, params2.ratingThreshold, params2.yearstart, params2.monthstart, params2.daystart, params2.hourstart, params2.minstart, params2.yearfinish, params2.monthfinish, params2.dayfinish, params2.hourfinish, params2.minfinish, params2.yearArticleLife, params2.monthArticleLife, params2.dayArticleLife, params2.hourArticleLife);
            InformationChannelData channel = new InformationChannelData("ExternalChannel", resource_ref, dataForChannelConstruct, appear_actions, accept_actions, decline_actions, context);
            start_channels.add(channel);
        }
        ArrayList<ScriptAction> start_actions = new ArrayList<ScriptAction>();
        ArrayList<ScriptAction> any_end_actions = new ArrayList<ScriptAction>();
        any_end_actions.add(new CycleActivateMission(params2.race_uid));
        MissionInfo res = new MissionInfo(mission_name, finishPointName, organizerName, qi, start_channels, start_actions, any_end_actions, any_end_actions, any_end_actions, any_end_actions, any_end_actions);
        res.setStarter(params2);
        return res;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class JournalNoteData {
        private String descriptionLocRef;
        private List<Macros> macrosPairs = new ArrayList<Macros>();

        JournalNoteData(String descriptionLocRef, List<Macros> macrosPairs) {
            this.descriptionLocRef = descriptionLocRef;
            this.macrosPairs.addAll(macrosPairs);
        }

        String getDescriptionLocRef() {
            return this.descriptionLocRef;
        }

        List<Macros> getMacrosPairs() {
            return this.macrosPairs;
        }
    }

    static class ImmediateMissionAnnounce
    implements IAfterEventsRun {
        String mission_name;

        private ImmediateMissionAnnounce(String mission_name) {
            this.mission_name = mission_name;
        }

        public void run() {
            ExternalChannelSayAppear appear_action = new ExternalChannelSayAppear();
            appear_action.name = BigRaceAnnounceMission.createResourceRefForMissionStartChannel(this.mission_name);
            appear_action.act();
        }
    }

    static class CycleActivateMission
    extends ScriptAction {
        static final long serialVersionUID = 0L;
        private int uid;

        CycleActivateMission(int uid) {
            this.uid = uid;
        }

        void run() {
            BigRaceAnnounceMission.activateMission((Params)BigRaceAnnounceMission.getInstance().missions.get(this.uid), false);
        }

        public void act() {
            if (BigRaceAnnounceMission.getInstance().missions.containsKey(this.uid)) {
                eng.CreateInfinitScriptAnimation(new CycleActivateMissionRune(this));
            }
        }
    }

    static class CycleActivateMissionRune
    extends TypicalAnm {
        private CycleActivateMission cycle;

        CycleActivateMissionRune(CycleActivateMission cycle) {
            this.cycle = cycle;
        }

        public boolean animaterun(double dt) {
            this.cycle.run();
            return true;
        }
    }

    static class Params
    implements IMissionStarter {
        String finish_point;
        int race_uid;
        int type;
        String raceName;
        String shortRaceName;
        String startWarehouse;
        String finishWarehouse;
        int moneyPrize;
        double ratingThreshold;
        int yearstart;
        int monthstart;
        int daystart;
        int hourstart;
        int minstart;
        int yearfinish;
        int monthfinish;
        int dayfinish;
        int hourfinish;
        int minfinish;
        int yearArticleLife;
        int monthArticleLife;
        int dayArticleLife;
        int hourArticleLife;
        MissionInfo created_mission = null;

        public Params(String finish_point, int race_uid, int type, String raceName, String shortRaceName, String startWarehouse, String finishWarehouse, int moneyPrize, double ratingThreshold, int yearstart, int monthstart, int daystart, int hourstart, int minstart, int yearfinish, int monthfinish, int dayfinish, int hourfinish, int minfinish, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
            this.finish_point = finish_point;
            this.race_uid = race_uid;
            this.type = type;
            this.raceName = raceName;
            this.shortRaceName = shortRaceName;
            this.startWarehouse = startWarehouse;
            this.finishWarehouse = finishWarehouse;
            this.moneyPrize = moneyPrize;
            this.ratingThreshold = ratingThreshold;
            this.yearstart = yearstart;
            this.monthstart = monthstart;
            this.daystart = daystart;
            this.hourstart = hourstart;
            this.minstart = minstart;
            this.yearfinish = yearfinish;
            this.monthfinish = monthfinish;
            this.dayfinish = dayfinish;
            this.hourfinish = hourfinish;
            this.minfinish = minfinish;
            this.yearArticleLife = yearArticleLife;
            this.monthArticleLife = monthArticleLife;
            this.dayArticleLife = dayArticleLife;
            this.hourArticleLife = hourArticleLife;
        }

        public String startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point) {
            MissionEventsMaker.startMission_AnnounceBigRace(this.race_uid, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point);
            return null;
        }

        public void startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point, String missionStartPlaceName) {
            MissionEventsMaker.startMission_AnnounceBigRace(this.race_uid, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point);
        }
    }
}

