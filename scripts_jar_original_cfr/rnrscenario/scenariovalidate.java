/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.StaticScenarioStuff;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.scenarioscript;
import scriptEvents.EventsControllerHelper;

public class scenariovalidate {
    private static final String SAVE_DOROTHY_START = "start Rescue Dorothy";
    private static final String SCTRANGER_CALL_START = "starngercall";
    private static final String MEET_PITER_PAN = "SC meet Piter Pan";
    private static final String WAIT_POLICE_CALL = "wait_policecall";
    private static final String MEET_TOPO = "SC meet Topo";
    private static final String POLICE_AFTER_TOPO = "SC police";
    private static final String BIGRACE_START = "SC BigRace";
    private static final String BIGRACE_FAKE = "bigracequest_debug";
    private static final String DAKOTAQUEST_START = "SC Dakota";
    private static final String MATQUEST_START = "SC Meet Mat";
    private static final String ENEMY_BASE_START = "SC Enemy Base";
    private static final String CHASE_KOH_START = "SC Chase Koh";
    private static final String CURSEDHIWAY_START = "SC Cursed Hiway";

    public static void saveDorothy() {
        scenariovalidate.startany(SAVE_DOROTHY_START);
    }

    public static void strangerCall() {
        scenariovalidate.startany(SCTRANGER_CALL_START);
    }

    public static void meetPiterPan() {
        scenariovalidate.startany(MEET_PITER_PAN);
    }

    public static void meetPiterPanFinalRace() {
        scenarioscript.script.getScenarioMachine().activateState("SC meet Piter Pan_phase_4", "SC meet Piter Pan_phase_4");
    }

    public static void waitPoliceCall() {
        scenariovalidate.startany(WAIT_POLICE_CALL);
    }

    public static void meetToto() {
        scenariovalidate.startany(MEET_TOPO);
    }

    public static void policeAfterTopo() {
        scenariovalidate.startany(POLICE_AFTER_TOPO);
    }

    public static void bigRaceQuest() {
        scenariovalidate.startany(BIGRACE_START);
    }

    public static void bigRaceQuestFake() {
        eng.console("stats rating 100");
        scenariovalidate.startany(BIGRACE_FAKE);
    }

    public static void meetDakota() {
        scenariovalidate.startany(DAKOTAQUEST_START);
    }

    public static void meetMat() {
        scenariovalidate.startany(MATQUEST_START);
    }

    public static void checkJohnTask() {
        MissionSystemInitializer.getMissionsManager().activateAsideMission("sc01080");
        scenariovalidate.startany("activate john help quest");
        eng.unblockNamedSO(1024, "SP_John_House");
    }

    public static void enemyBase() {
        scenarioscript.script.getScenarioMachine().activateState(ENEMY_BASE_START, "SC Enemy Base_phase_1");
        EventsControllerHelper.messageEventHappened("Dorothy_call");
    }

    public static void chaseKoh() {
        scenarioscript.script.getScenarioMachine().activateState(CHASE_KOH_START, "SC Chase Koh_phase_1");
    }

    public static void cursedHiway() {
        scenarioscript.script.getScenarioMachine().activateState(CURSEDHIWAY_START, "SC Cursed Hiway_phase_1");
    }

    public static void cursedHiwayWithoutorder() {
        scenarioscript.script.getScenarioMachine().activateState(CURSEDHIWAY_START, "SC Cursed Hiway_phase_1");
        StaticScenarioStuff.makeReadyCursedHiWay(false);
        EventsControllerHelper.messageEventHappened("sc01300 accepted");
        Helper.peekNativeMessage("sc01300 loaded");
    }

    public static void policeEntrapped() {
        scenarioscript.script.getScenarioMachine().activateState("cursed_hiway", "cursed_hiway_phase_1");
    }

    public static void startany(String scenariopart) {
        scenarioscript.script.getScenarioMachine().activateState(scenariopart, scenariopart + "_phase_" + 1);
    }
}

