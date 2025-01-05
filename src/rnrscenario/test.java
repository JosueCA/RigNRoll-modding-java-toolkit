// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario;

import adjusting.Variables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import menu.JavaEvents;
import menu.menues;
import menuscript.EscapeMenu;
import menuscript.NotifyGameOver;
import menuscript.PanelMenu;
import menuscript.RacePanelMenu;
import menuscript.TotalVictoryMenu;
import menuscript.VictoryMenu;
import menuscript.cbvideo.Dialogitem;
import menuscript.cbvideo.MenuCall;
import menuscript.mainmenu.StartMenu;
import menuscript.office.OfficeMenu;
import menuscript.org.OrganiserMenu;
import menuscript.testmenu;
import players.CarName;
import players.Chase;
import players.Crew;
import players.CutSceneAuxPersonCreator;
import players.IdentiteNames;
import players.RaceTrajectory;
import players.ScenarioPersonPassanger;
import players.actorveh;
import players.aiplayer;
import players.semitrailer;
import players.vehicle;
import rnrcore.Collide;
import rnrcore.INativeMessageEvent;
import rnrcore.IScriptTask;
import rnrcore.IXMLSerializable;
import rnrcore.Log;
import rnrcore.NativeEventController;
import rnrcore.SCRcamera;
import rnrcore.SCRuniperson;
import rnrcore.ScenarioSync;
import rnrcore.SceneActorsPool;
import rnrcore.ScriptRef;
import rnrcore.TypicalAnm;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.teleport.ITeleported;
import rnrcore.teleport.MakeTeleport;
import rnrcore.vectorJ;
import rnrorg.MissionEventsMaker;
import rnrrating.BigRace;
import rnrrating.FactionRater;
import rnrrating.RateSystem;
import rnrscenario.BigRaceCrowd;
import rnrscenario.CBCallsStorage;
import rnrscenario.CBVideoStroredCall;
import rnrscenario.ChaseTopoDebug;
import rnrscenario.EnemyBaseDebug;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ScenarioFlagsManager;
import rnrscenario.TestCreateAllMenues;
import rnrscenario.ThreadTask;
import rnrscenario.controllers.KohHelpManage;
import rnrscenario.controllers.PiterPanFinalrace;
import rnrscenario.controllers.ResqueDorothyShootAnimate;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.controllers.chase00090;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.scenarioscript;
import rnrscenario.scenariovalidate;
import rnrscenario.scenes.CrashBarScene;
import rnrscenario.scenes.sc00009;
import rnrscenario.scenes.sc00324;
import rnrscenario.scenes.sc00520;
import rnrscenario.scenes.sc00530;
import rnrscenario.scenes.sc00730;
import rnrscenario.scenes.sc00830;
import rnrscenario.scenes.sc00860;
import rnrscenario.scenes.sc01030;
import rnrscenario.scenes.sc01100;
import rnrscenario.scenes.sc02040;
import rnrscenario.scenes.sc02060;
import rnrscenario.stage;
import rnrscenario.tech.Helper;
import rnrscr.AdvancedRandom;
import rnrscr.Bar;
import rnrscr.CarInOutTasks;
import rnrscr.Dialog;
import rnrscr.MissionDialogs;
import rnrscr.Office;
import rnrscr.PedestrianManager;
import rnrscr.SOscene;
import rnrscr.cSpecObjects;
import rnrscr.camscripts;
import rnrscr.drvscripts;
import rnrscr.parkingplace;
import rnrscr.specobjects;
import rnrscr.trackscripts;
import scenarioUtils.Pair;
import scriptActions.ExternalChannelSayAppear;
import scriptActions.StartScenarioMissionAction;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.EventsControllerHelper;
import scriptEvents.MessageEvent;
import scriptEvents.ScriptEvent;

public class test {
    private static actorveh carkoh;
    static BigRaceCrowd pers_group1;
    private static actorveh car_park;
    private static long scene_test_mimic;
    public static int count_man;
    public static int count_ivan;
    public static int count_cam;
    private static final String[] Phrases;
    private ResqueDorothyShootAnimate m_debug_shootanimation;

    public void doNotKillTasks() {
        scenarioscript.script.doNotClearTasksOnGameDeinit();
    }

    public void stopCheckEngineWhilePlayingScenes() {
        eng.CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }

    public void oneShotStopCheckEngineWhilePlayingScenes() {
        eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }

    public void sc_sd() {
        scenariovalidate.saveDorothy();
    }

    public void sc_sc() {
        scenariovalidate.strangerCall();
    }

    public void sc_pp() {
        scenariovalidate.meetPiterPan();
    }

    public void sc_pp_final() {
        scenariovalidate.meetPiterPanFinalRace();
    }

    public void sc_pc() {
        scenariovalidate.waitPoliceCall();
    }

    public void sc_mt() {
        scenariovalidate.meetToto();
    }

    public void sc_dakota() {
        scenariovalidate.meetDakota();
    }

    public void sc_mat() {
        scenariovalidate.meetMat();
    }

    public void sc_eb() {
        scenariovalidate.enemyBase();
    }

    public void sc_ck() {
        scenariovalidate.chaseKoh();
    }

    public void sc_ch() {
        scenariovalidate.cursedHiway();
    }

    public void sc_ch_noorder() {
        scenariovalidate.cursedHiwayWithoutorder();
    }

    public void sc_police() {
        scenariovalidate.policeEntrapped();
    }

    public void sc_police_topo() {
        scenariovalidate.policeAfterTopo();
    }

    public void sc_john_bigrace() {
        scenariovalidate.checkJohnTask();
    }

    public void sc_br() {
        scenariovalidate.bigRaceQuest();
    }

    public void sc_br_fake() {
        scenariovalidate.bigRaceQuestFake();
    }

    public boolean hasLoadedScenarioPoint(String name) {
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        return null != crossroad && 0 == crossroad.name.compareToIgnoreCase(name);
    }

    public void call(String callname, String identitie) {
        CBVideoStroredCall call = CBCallsStorage.getInstance().getStoredCall(callname);
        call.makecall(identitie, "void_mission");
    }

    public void call(String callname) {
        scenarioscript.script.launchCall(callname);
    }

    public void ppcall() {
        scenarioscript.script.launchCall("cb01660");
    }

    public void preparetestbar() {
        actorveh car;
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        carkoh = car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);
        carkoh.UpdateCar();
        carkoh = car;
    }

    public void testbar() {
        carkoh.UpdateCar();
        carkoh.registerCar("madcar");
        carkoh.makeUnloadable(false);
        new CrashBarScene().run();
    }

    public void test_radius() {
        TypicalAnm anim = new TypicalAnm(){
            private vectorJ[] m_positionHistory = new vectorJ[]{null, null, null};
            private vectorJ m_radiusHistory = null;
            private boolean m_canCheckRadiusChangeState = true;
            private boolean m_currentRadiusChangeState = true;
            private boolean m_radiusChanged = true;
            private double m_lastTime = 0.0;
            private double m_frameLength = 0.0;
            private double m_countShiftTime = 0.0;
            private double m_countRotateRadius = 0.0;
            private double s_timeToShift = 0.1;
            private double s_timeToRotateRadius = 1.0;

            private void shiftPositionHistiry(vectorJ pos) {
                this.m_positionHistory[2] = this.m_positionHistory[1];
                this.m_positionHistory[1] = this.m_positionHistory[0];
                this.m_positionHistory[0] = pos;
            }

            private void rememberPositions() {
                this.m_countShiftTime += this.m_frameLength;
                if (this.m_countShiftTime < this.s_timeToShift) {
                    return;
                }
                this.m_countShiftTime = 0.0;
                vectorJ pos = Crew.getIgrokCar().gPosition();
                this.shiftPositionHistiry(pos);
            }

            private void rememberRadiusVector(vectorJ pos) {
                double modulo;
                this.m_countRotateRadius += this.m_frameLength;
                double d = modulo = this.m_radiusHistory != null ? this.m_radiusHistory.x * pos.x + this.m_radiusHistory.y * pos.y : -1.0;
                if (this.m_countRotateRadius < this.s_timeToRotateRadius) {
                    this.m_radiusChanged |= modulo < -1.0E-5;
                    return;
                }
                this.m_countRotateRadius = 0.0;
                this.m_radiusHistory = pos;
                this.m_canCheckRadiusChangeState = true;
                this.m_currentRadiusChangeState = this.m_radiusChanged;
                this.m_radiusChanged = modulo < -1.0E-5;
            }

            private vectorJ getRadius() {
                if (this.m_positionHistory[0] == null || this.m_positionHistory[1] == null || this.m_positionHistory[2] == null) {
                    return new vectorJ(1.0, 0.0, 10.0);
                }
                vectorJ r1 = new vectorJ(this.m_positionHistory[0]);
                r1.oMinus(this.m_positionHistory[1]);
                r1.norm();
                vectorJ r2 = new vectorJ(this.m_positionHistory[2]);
                r2.oMinus(this.m_positionHistory[1]);
                r2.norm();
                return r1.oPlusN(r2);
            }

            private boolean getRadiusCheck() {
                if (!this.m_canCheckRadiusChangeState) {
                    return true;
                }
                this.m_canCheckRadiusChangeState = false;
                return this.m_currentRadiusChangeState;
            }

            public boolean animaterun(double dt) {
                this.m_frameLength = dt - this.m_lastTime;
                this.m_lastTime = dt;
                this.rememberPositions();
                vectorJ radiusVector = this.getRadius();
                this.rememberRadiusVector(radiusVector);
                Log.simpleMessage("Radius changed " + this.getRadiusCheck());
                return false;
            }
        };
        eng.CreateInfinitScriptAnimation(anim);
    }

    void testchase() {
        new ChaseTopoTest();
    }

    void testridefar1() {
        Crew.getIgrokCar().autopilotTo(new vectorJ(3945.0, 39447.0, 136.0));
    }

    void testridefar2() {
        Crew.getIgrokCar().autopilotTo(new vectorJ(23894.0, -40765.0, -2.0));
    }

    public void group1() {
        if (pers_group1 == null) {
            pers_group1 = new BigRaceCrowd();
        }
        pers_group1.crowPlaceSomeWhere();
    }

    public void delgroup1() {
        pers_group1.delsome();
    }

    public void group1_play1() {
        pers_group1.play1();
    }

    public void group1_play2() {
        pers_group1.play2();
    }

    public void group1_play3() {
        pers_group1.play3();
    }

    public void cartrack() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);
        Crew.addMappedCar("DAKOTA", car);
        pos = new vectorJ(0.0, -400.0, 0.0);
        car = eng.CreateCarForScenario(CarName.CAR_BANDITS, M, pos);
        Crew.addMappedCar("ARGOSY BANDIT", car);
    }

    public void cartrack_check() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);
        Crew.addMappedCar("DAKOTA", car);
        pos = new vectorJ(0.0, -400.0, 0.0);
        car = eng.CreateCarForScenario(CarName.CAR_BANDITS, M, pos);
        Crew.addMappedCar("ARGOSY BANDIT", car);
    }

    public void cartrack1() {
        actorveh car = Crew.getMappedCar("DAKOTA");
        car.registerCar("dakotacar");
        car = Crew.getMappedCar("ARGOSY BANDIT");
        car.registerCar("banditcar");
    }

    public void createcar() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);
        Crew.addMappedCar("DAKOTA", car);
        aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");
        dakota.beDriverOfCar(car);
    }

    public void deletecar() {
        actorveh dakotaCarNumber = Crew.getMappedCar("DAKOTA");
        if (dakotaCarNumber != null) {
            dakotaCarNumber.deactivate();
        }
    }

    public void checkdeleted() {
        aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");
        dakota.getModel().play();
    }

    public void bar1cam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");
        freeCam.PlayCamera();
        freeCam.SetInBarWorld();
        vectorJ posit = new vectorJ(0.0, 0.0, 2.0);
        freeCam.SetCameraPosition(posit);
    }

    public void policeImmunityOff() {
        eng.makePoliceImmunity(false);
    }

    public void bar2cam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");
        freeCam.PlayCamera();
        freeCam.SetInBar2World();
        vectorJ posit = new vectorJ(0.0, 0.0, 2.0);
        freeCam.SetCameraPosition(posit);
    }

    public void bar3cam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");
        freeCam.PlayCamera();
        freeCam.SetInBar3World();
        vectorJ posit = new vectorJ(0.0, 0.0, 2.0);
        freeCam.SetCameraPosition(posit);
    }

    public void policecam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");
        freeCam.PlayCamera();
        freeCam.SetInPoliceWorld();
        vectorJ posit = new vectorJ(0.0, 0.0, 2.0);
        freeCam.SetCameraPosition(posit);
    }

    public void officecam() {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long officeWorld = eng.AddChangeWorldTask(task, "office", "simple");
        long officeCamera = eng.AddScriptTask(task, new OfficeCam());
        eng.OnEndTASK(officeWorld, "play", officeCamera);
        eng.playTASK(officeWorld);
    }

    public void motelcam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");
        freeCam.PlayCamera();
        freeCam.SetInHotelWorld();
        vectorJ posit = new vectorJ(0.0, 0.0, 2.0);
        freeCam.SetCameraPosition(posit);
    }

    public void PanelDeliveryDamaged() {
        PanelMenu.PanelDeliveryDamaged("from script", 101, 102.0, 103, false);
    }

    public void PanelDeliveryExpired() {
        PanelMenu.PanelDeliveryExpired("from script", 101, 102.0, 103, false);
    }

    public void PanelDeliveryTowed() {
        PanelMenu.PanelDeliveryTowed("from script", 101, 102);
    }

    public void PanelDeliveryCancelled() {
        PanelMenu.PanelDeliveryTowedCancelled("from script", 101, 102, 103.0, 104);
    }

    public void PanelDeliveryExecuted() {
        PanelMenu.PanelDeliveryExecuted("from script", 101, 102, 103, 104.0);
    }

    public void PanelDeliveryFirst() {
        PanelMenu.PanelDeliveryFirst("from script", 101, 102, 103, 104.0);
    }

    public void PanelAnotherOrderLate() {
        PanelMenu.PanelTenderLate("from script", 101.0, 102);
    }

    public void PanelAnotherOrderFirst() {
        PanelMenu.Time t = new PanelMenu.Time(1, 2, 3);
        PanelMenu.PanelTenderFirst("from script", 101, t, 103.0, 104.0, 105.0, 106);
    }

    public void PanelAnotherOrderDefault() {
        PanelMenu.PanelTenderDefaulted("from script", 101.0, 102);
    }

    public void PanelDrivingContestDefaulted() {
        PanelMenu.PanelContestDefaulted("from script", 101.0, 102);
    }

    public void PanelDrivingContestFirst() {
        PanelMenu.Time t = new PanelMenu.Time(1, 2, 3);
        PanelMenu.PanelContestFirst("from script", t, 101.0, 102.0, 103.0, 104, 105);
    }

    public void PanelDrivingContestFinished() {
        PanelMenu.Time t = new PanelMenu.Time(1, 2, 3);
        PanelMenu.PanelContestExecuted("from script", 101, t, 102.0, 103.0, 104.0, 105);
    }

    public void panelchange() {
        RacePanelMenu.PanelPreparingToRaceIn();
    }

    public void jail() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 1);
    }

    public void gameover() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 0);
    }

    public void murder() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 2);
    }

    public void testdrive(int posx, int posy, int posz) {
        Crew.getIgrokCar().autopilotTo(new vectorJ(posx, posy, posz));
    }

    public void stoptestdrive() {
        Crew.getIgrokCar().stop_autopilot();
    }

    public void msg(String message) {
        EventsControllerHelper.messageEventHappened(message);
    }

    public void quest(String q) {
        scenarioscript.script.gMachine().activateState(true, q + "_phase_" + 1);
    }

    public void startmenu() {
        StartMenu.create();
    }

    public void testallmenu() {
        TestCreateAllMenues.runAll();
    }

    public void testoffice() {
        OfficeMenu.create();
        eng.CreateInfinitScriptAnimation(new ShowOfficeMenu());
    }

    public void autotest_startmenu() {
        scenarioscript.setAutotestRun(true);
        StartMenu.autotest_create();
    }

    public void showSTOmenu() {
        menues.showMenu(6000);
    }

    public void motelmenu() {
        menues.CreateMotelMENU();
    }

    public void showmotelenu() {
        menues.showMenu(7000);
    }

    public void adjust() {
        Variables.adjust();
    }

    public long menu(String xmlname, String constrolgroup) {
        long menu = testmenu.CreateTestMenu("..\\data\\config\\menu\\" + xmlname, constrolgroup);
        menues.WindowSet_ShowCursor(menu, true);
        return menu;
    }

    public void smenu() {
        this.menu("menu_office.xml", "Tablegroup - ELEMENTS - CompanyBranches");
    }

    public void smenu(String xmlname, String constrolgroup) {
        this.menu(xmlname, constrolgroup);
    }

    public void crcar(String name) {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        actorveh car = eng.CreateCarImmediatly(name, M, pos);
        Crew.addMappedCar("DAKOTA", car);
    }

    public void mission(String mission_name) {
        MissionSystemInitializer.getMissionsManager().load_mission_debug(mission_name);
    }

    public boolean missionstart(String mission_name) {
        return MissionSystemInitializer.getMissionsManager().load_mission_debug(mission_name);
    }

    public void inoffice() {
        new Office().debug_scene();
    }

    public void create_pass() {
    }

    public void create_org() {
        OrganiserMenu.create();
    }

    public void makecar() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0, -24268.0, 15.0);
        car_park = eng.CreateCarImmediatly("PETERBILT_379", M, pos);
    }

    public void makecar2() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0, -24268.0, 15.0);
        car_park = eng.CreateCarImmediatly("PETERBILT_387", M, pos);
    }

    public void makecar3() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0, -24268.0, 15.0);
        car_park = eng.CreateCarImmediatly("KENWORTH_T600W", M, pos);
    }

    public void makecar4() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0, -24268.0, 15.0);
        car_park = eng.CreateCarImmediatly("KENWORTH_K100E", M, pos);
    }

    public void notifytest() {
        MenuCall menu = MenuCall.create();
        menu.setItem(new Dialogitem(Crew.getIgrok().createCBContacter(), "message"));
    }

    public void debug_set_rating(int value) {
        RateSystem.DEBUGsetLiveRating(value);
    }

    public void message() {
        PanelMenu.PanelTenderDefaulted("Some", 1.0, 1);
    }

    public void ped1(String model_name) {
        PedestrianManager.getInstance().removeNamedModel(model_name);
    }

    public void ped2() {
        PedestrianManager.getInstance().setPopulation(10);
    }

    public void ped3() {
        PedestrianManager.getInstance().setPopulation(100);
    }

    public void ped4() {
        PedestrianManager.getInstance().setPopulation(1);
    }

    public void testcar1() {
        vectorJ pos = new vectorJ(4315.0, 12620.0, 286.0);
        actorveh car = eng.CreateCar_onway("FREIGHTLINER_ARGOSY", pos);
        vectorJ postunnel = eng.getControlPointPosition("EnemyBaseTunnel");
        car.autopilotTo(postunnel);
    }

    public void testcar2() {
        actorveh car = Crew.getIgrokCar();
        actorveh dakotacar = eng.CreateCarImmediatly("PETERBILT_379", car.gMatrix(), car.gPosition().oPlusN(new vectorJ(0.0, 30.0, 0.0)));
        Crew.addMappedCar("DAKOTA", dakotacar);
        actorveh johncar = eng.CreateCarImmediatly("GMC_TOPKICK4500_JOHN_TRAMPLIN", car.gMatrix(), car.gPosition().oPlusN(new vectorJ(0.0, -30.0, 0.0)));
        Crew.addMappedCar("JOHN", johncar);
    }

    public void dakotadriver() {
        actorveh dakotacar = Crew.getMappedCar("DAKOTA");
        aiplayer dakota = new aiplayer("SC_ONTANIELOLOW");
        dakota.setModelCreator(new ScenarioPersonPassanger(), null);
        dakota.beDriverOfCar(dakotacar);
    }

    public void testcar3() {
        actorveh dakotacar = Crew.getMappedCar("DAKOTA");
        dakotacar.registerCar("DAKOTA");
        actorveh johncar = Crew.getMappedCar("JOHN");
        johncar.registerCar("JOHN");
        this.test2030();
    }

    public void testcar4() {
        actorveh police1 = eng.CreateCarImmediatly("Ford_CV_police", new matrixJ(), new vectorJ());
        police1.UpdateCar();
        police1.registerCar("police1");
        actorveh police2 = eng.CreateCarImmediatly("Ford_CV_police", new matrixJ(), new vectorJ(100.0, 0.0, 0.0));
        police2.UpdateCar();
        police2.registerCar("police2");
    }

    private matrixJ testassault_getMatrix() {
        return new matrixJ();
    }

    private vectorJ testassault_getPosition() {
        return eng.getControlPointPosition("EnemyBaseAssaultRoot");
    }

    public void testassault1() {
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        v.add(new SceneActorsPool("baza", person));
        Data _data = new Data(this.testassault_getMatrix(), this.testassault_getPosition(), null);
        trackscripts.CreateSceneXML("02040a_part1", v, _data);
    }

    public void testassault2() {
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        v.add(new SceneActorsPool("baza", person));
        Data _data = new Data(this.testassault_getMatrix(), this.testassault_getPosition(), null);
        trackscripts.CreateSceneXMLCycle("02040a_part2", v, _data);
    }

    public void testassault3() {
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        v.add(new SceneActorsPool("baza", person));
        Data _data = new Data(this.testassault_getMatrix(), this.testassault_getPosition(), null);
        trackscripts.CreateSceneXML("02040a_part3", v, _data);
    }

    public void enemybase_assault() {
        EnemyBaseDebug.assault();
    }

    public void changecar() {
        vectorJ ipos = rnrscr.Helper.getCurrentPosition();
        ipos.x += 100.0;
        ipos.y -= 100.0;
        actorveh car = eng.CreateCarForScenario(CarName.CAR_GEPARD, new matrixJ(), ipos);
        vectorJ pos_1 = car.gPosition();
        matrixJ mat_1 = car.gMatrix();
        vehicle gepard = car.takeoff_currentcar();
        actorveh ourcar = Crew.getIgrokCar();
        vehicle.changeLiveVehicle(ourcar, gepard, mat_1, pos_1);
        car.deactivate();
    }

    public void changecar(String carname) {
        vectorJ ipos = rnrscr.Helper.getCurrentPosition();
        ipos.x += 100.0;
        ipos.y -= 100.0;
        actorveh car = eng.CreateCarImmediatly(carname, new matrixJ(), ipos);
        vectorJ pos_1 = car.gPosition();
        matrixJ mat_1 = car.gMatrix();
        vehicle gepard = car.takeoff_currentcar();
        actorveh ourcar = Crew.getIgrokCar();
        vehicle.changeLiveVehicle(ourcar, gepard, mat_1, pos_1);
        car.deactivate();
    }

    public void testbarcrash() {
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("BarIn4");
        v.add(new SceneActorsPool("bar", person));
        Data _data = new Data(new matrixJ(), new vectorJ(), null);
        trackscripts.CreateSceneXML("02050", v, _data);
    }

    public void test1620() {
        ScenarioSync.setPlayScene("sc01620");
    }

    public void test2030() {
        ScenarioSync.setPlayScene("sc02030");
    }

    public void test2045() {
        ScenarioSync.setPlayScene("sc02045");
    }

    public void test2050_part2() {
        double delitel = 1.0;
        matrixJ M = new matrixJ();
        M.v0 = new vectorJ(-Math.cos(Math.PI / delitel), Math.cos(Math.PI / delitel), 0.0);
        M.v1 = new vectorJ(-Math.cos(Math.PI / delitel), -Math.cos(Math.PI / delitel), 0.0);
        M.v2 = new vectorJ(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ();
        Data _data = new Data(M, pos, null);
        trackscripts.CreateSceneXML("02050_part2", null, _data);
    }

    public void test2065() {
        ScenarioSync.setPlayScene("sc02065");
    }

    public void test1370() {
        ScenarioSync.setPlayScene("sc01370");
    }

    public void test1370_1() {
        ScenarioSync.setPlayScene("sc01370_1");
    }

    public void test3000() {
        ScenarioSync.setPlayScene("sc03000");
    }

    public void dakotashoot() {
        new ResqueDorothyShootAnimate(null, Crew.getIgrokCar(), false);
    }

    public void totalvictory() {
        TotalVictoryMenu.createGameOverTotal(null);
    }

    public void test1380() {
        ScenarioSync.setPlayScene("sc01380");
    }

    public void test_1() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0, 0);
    }

    public void test_2() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0, 3);
    }

    public void test_3() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0, 1);
    }

    public void test_4() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0, 2);
    }

    public void createLooseEconomy() {
        VictoryMenu.createLooseEconomy(null);
    }

    public void createLooseSocial() {
        VictoryMenu.createLooseSocial(null);
    }

    public void createLooseSport() {
        VictoryMenu.createLooseSport(null);
    }

    public void createWinEconomy() {
        VictoryMenu.createWinEconomy(null);
    }

    public void createWinSocial() {
        VictoryMenu.createWinSocial(null);
    }

    public void createWinSport() {
        VictoryMenu.createWinSport(null);
    }

    public void testgun() {
        aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");
        IdentiteNames info = new IdentiteNames("SC_BANDITJOE");
        if (!eng.noNative) {
            JavaEvents.SendEvent(57, 1, info);
        }
        GUN.bePassangerOfCar_simple_like(Crew.getIgrokCar(), info.modelName);
    }

    public void test100() {
        ScenarioSync.setPlayScene("sc00100");
    }

    public void test1640() {
        ScenarioSync.setPlayScene("sc01640");
    }

    public void testmission1() {
        StartScenarioMissionAction action = new StartScenarioMissionAction();
        action.name = "m00040";
        action.act();
    }

    public void testmission2() {
        MissionEventsMaker.changeMissionDestination("m00040", "MP_Bar_LA_LB_01");
    }

    public void testmission3() {
        MissionEventsMaker.changeMissionDestination("m00040", "MP_MT_SR_03");
    }

    public void test610() {
        ScenarioSync.setPlayScene("sc00610");
    }

    public void testmission_semitrailer() {
        MissionSystemInitializer.getMissionsManager().activateAsideMission("sc00610");
    }

    public void testmission_semitrailer_1() {
        MissionDialogs.sayAppear("sc00610_start_channel");
    }

    public void test_bankrupt() {
        EventsControllerHelper.messageEventHappened("bankrupt");
    }

    public void test_scenario_bankrupt() {
        EventsControllerHelper.messageEventHappened("scenario_bankrupt");
    }

    public void test_1080() {
        ScenarioSync.setPlayScene("sc01080");
    }

    public void testcam1() {
        SCRcamera cam_veh1 = SCRcamera.CreateCamera("anm");
        String CAM_VEHname2 = "camera_RUL10";
        matrixJ mrotVEH = new matrixJ();
        mrotVEH.v0.Set(0.0, 1.0, 0.0);
        mrotVEH.v1.Set(0.0, 0.0, 1.0);
        mrotVEH.v2.Set(1.0, 0.0, 0.0);
        matrixJ mrotcamVEH = new matrixJ();
        mrotcamVEH.v0.Set(0.0, 0.0, -1.0);
        mrotcamVEH.v1.Set(0.0, 1.0, 0.0);
        mrotcamVEH.v2.Set(1.0, 0.0, 0.0);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
        cam_veh1.BindToVehicleSteerWheel(Crew.getIgrokCar().getCar());
        cam_veh1.PlayCamera();
    }

    public void test_1100() {
        eng.console("stats rating 100");
        EventsControllerHelper.messageEventHappened("big_race_scenario");
        ScenarioSync.setPlayScene("sc01100");
    }

    public void bigraceannounceevent() {
        ExternalChannelSayAppear appear_action = new ExternalChannelSayAppear();
        appear_action.name = "BigRaceAnnounce1nom1 startchannel";
        appear_action.act();
    }

    public void test_430() {
        ScenarioSync.setPlayScene("sc00430");
    }

    public void test_1030() {
        ScenarioSync.setPlayScene("sc01030");
    }

    public void test_1420() {
        ScenarioSync.setPlayScene("sc01420");
    }

    public void test1540() {
        ScenarioSync.setPlayScene("sc01540");
    }

    public void test_events_topo() {
        eng.setdword("DWORD_TopoQuest_Events", 1);
    }

    public void test_topoquest() {
        ChaseTopoDebug.allTopo();
    }

    public void test_topochase() {
        ChaseTopoDebug.simplechase();
    }

    public void test_topotestchase() {
        ChaseTopoDebug.simpliestchase();
    }

    public void test_toporacebandits() {
        ChaseTopoDebug.testContestStalkers();
    }

    public void test_dark_truck() {
        ChaseTopoDebug.darkTruck();
    }

    public void test_topo_friends() {
        ChaseTopoDebug.createFriends();
    }

    public void test_topo_trailers() {
        ChaseTopoDebug.justFriends();
    }

    public void test_topo_friends_finish() {
        ChaseTopoDebug.friendsFinish();
    }

    public void test_topo_friends_ride() {
        ChaseTopoDebug.friendsRide();
    }

    public void repair() {
        JavaEvents.SendEvent(43, 0, Crew.getIgrokCar());
    }

    public void repairbody() {
        JavaEvents.SendEvent(43, 1, Crew.getIgrokCar());
    }

    public void repairglass() {
        JavaEvents.SendEvent(43, 2, Crew.getIgrokCar());
    }

    public void cycletrace1() {
        eng.console("tracetopoint MP_bar_MJ_BS_01 cycletrace2");
    }

    public void cycletrace2() {
        eng.console("tracetopoint MP_bar_BS_01 cycletrace3");
    }

    public void cycletrace3() {
        eng.console("tracetopoint MP_gs_MJ_03 cycletrace1");
    }

    public void create_office() {
        OfficeMenu.create();
    }

    public void create_cam_scene(String scenename) {
        actorveh car = Crew.getIgrokCar();
        car.registerCar("ourcar");
        create_passdata data = new create_passdata();
        data.car = car;
        scenetrack.CreateSceneXML(scenename, 17, data);
    }

    public void barstand1() {
        Bar.getInstance().startDialog("dia0");
    }

    public void barstand2() {
        Bar.getInstance().startDialog("dia1");
    }

    public void barstand3() {
        Bar.getInstance().startDialog("dia2");
    }

    public void barstand4() {
        Bar.getInstance().endDialog("dia0");
    }

    public void barstand5() {
        Bar.getInstance().endDialog("dia1");
    }

    public void barstand6() {
        Bar.getInstance().endDialog("dia2");
    }

    public void camout1() {
        SOscene.setOutSceneNum(0);
    }

    public void camout2() {
        SOscene.setOutSceneNum(1);
    }

    public void camout3() {
        SOscene.setOutSceneNum(2);
    }

    public void camout4() {
        SOscene.setOutSceneNum(3);
    }

    public void camout5() {
        SOscene.setOutSceneNum(4);
    }

    public void camout6() {
        SOscene.setOutSceneNum(5);
    }

    public void camout7() {
        SOscene.setOutSceneNum(6);
    }

    public void camout8() {
        SOscene.setOutSceneNum(7);
    }

    public void camout9() {
        SOscene.setOutSceneNum(8);
    }

    public void camin1() {
        SOscene.setInSceneNum(0);
    }

    public void camin2() {
        SOscene.setInSceneNum(1);
    }

    public void camin3() {
        SOscene.setInSceneNum(2);
    }

    public void camin4() {
        SOscene.setInSceneNum(3);
    }

    public void camin5() {
        SOscene.setInSceneNum(4);
    }

    public void camin6() {
        SOscene.setInSceneNum(5);
    }

    public void camin7() {
        SOscene.setInSceneNum(6);
    }

    public void camin8() {
        SOscene.setInSceneNum(7);
    }

    public void camin9() {
        SOscene.setInSceneNum(8);
    }

    public void camin10() {
        SOscene.setInSceneNum(9);
    }

    public void camin11() {
        SOscene.setInSceneNum(10);
    }

    public void camin12() {
        SOscene.setInSceneNum(11);
    }

    public void camin13() {
        SOscene.setInSceneNum(12);
    }

    public void camin14() {
        SOscene.setInSceneNum(13);
    }

    public void blow_car() {
        Preset blowpreset = new Preset();
        blowpreset.car = Crew.getIgrokCar();
        scenetrack.CreateSceneXML("blowcar", 1, blowpreset);
    }

    public void createtrailer() {
        actorveh ourcar = Crew.getIgrokCar();
        vectorJ dir = ourcar.gDir();
        dir.mult(-20.0);
        vectorJ pos = ourcar.gPosition();
        pos.oPlus(dir);
        semitrailer tr = semitrailer.create("model_Flat_bed_cargo3", ourcar.gMatrix(), pos);
        ourcar.attach(tr);
    }

    public void play_out_of_car() {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long startEvent = eng.AddEventTask(task);
        long timeEvent = eng.AddEventTask(task);
        long deleteTask = eng.AddScriptTask(task, new DeleteTask(task));
        long barWorld = eng.AddChangeWorldTask(task, "bar", "simple");
        long gameWorld = eng.AddChangeWorldTask(task, "game", "simple");
        SOscene SC = new SOscene();
        SC.task = task;
        SC.person = person;
        SC.actor = Crew.getIgrok();
        SC.vehicle = Crew.getIgrokCar();
        CarInOutTasks CAR_out = SC.makecaroutOnStart(startEvent, false);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(gameWorld);
        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", deleteTask);
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", barWorld);
        eng.OnEndTASK(barWorld, "play", timeEvent);
        eng.OnMidTASK(timeEvent, 2.0, 2.0, "play", gameWorld);
        eng.playTASK(startEvent);
    }

    public void play_out_of_car_simple() {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long startEvent = eng.AddEventTask(task);
        long endEvent = eng.AddEventTask(task);
        long deleteTask = eng.AddScriptTask(task, new DeleteTask(task));
        long timeEvent = eng.AddEventTask(task);
        SOscene SC = new SOscene();
        SC.task = task;
        SC.person = person;
        SC.actor = Crew.getIgrok();
        SC.vehicle = Crew.getIgrokCar();
        CarInOutTasks CAR_out = SC.makecaroutOnStart(startEvent, false);
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", timeEvent);
        eng.OnMidTASK(timeEvent, 5.0, 5.0, "play", endEvent);
        eng.OnBegTASK(endEvent, "play", CAR_out.getMTaskToDeleteAll());
        eng.OnBegTASK(endEvent, "play", deleteTask);
        eng.playTASK(startEvent);
    }

    void debug_stage(String sceneName) {
        stage.performDebugPrePostConditions();
        if (sc00520.class.getName().endsWith(sceneName)) {
            ScenarioSync.setPlayScene(sceneName, 0);
        } else {
            ScenarioSync.setPlayScene(sceneName);
        }
    }

    void test_finish_mission(String missionName) {
        event.finishScenarioMission(missionName);
    }

    void test_pool() {
        new cTestDriversPools();
    }

    void schedule_big_race() {
        BigRace.gReference().DEBUGscheduleRace();
    }

    void test_esc_menu_cycle() {
        CycleMenuCreate.makeTest();
    }

    void allocationsDump() {
    }

    void gcgcgc() {
        System.gc();
        System.gc();
        System.gc();
    }

    public void TravelWorldCallBack() {
        JavaEvents.SendEvent(71, 7, this);
    }

    void dead() {
        rnrcore.Helper.peekNativeMessage("dead black screen");
    }

    void engine() {
        Crew.getIgrokCar().changeEngine("BANDITS_ENGINE");
    }

    void create_register_61cars() {
        vectorJ pos = eng.getControlPointPosition("Room_Repair_Oxnard_01");
        assert (pos != null);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_BANDITS, new matrixJ(), pos);
        Crew.addMappedCar("ARGOSY BANDIT", car);
        Crew.getIgrokCar().registerCar("ourcar");
    }

    void ruscene61() {
        actorveh carBandit = Crew.getMappedCar("ARGOSY BANDIT");
        carBandit.registerCar("banditcar");
        vectorJ pos = eng.getControlPointPosition("Room_Repair_Oxnard_01");
        assert (pos != null);
        Data data = new Data(new matrixJ(), pos, Crew.getIgrokCar());
        data.M_180 = new matrixJ();
        data.M_180.v0 = new vectorJ(-1.0, 0.0, 0.0);
        data.M_180.v1 = new vectorJ(0.0, -1.0, 0.0);
        scenetrack.CreateSceneXML("00061", 17, data);
    }

    void blowcar() {
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
    }

    void check_stop_car() {
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        Vector<actorveh> vec_participants = new Vector<actorveh>();
        vec_participants.add(car);
        actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0, 0.0, 1, 0);
    }

    void check_pp_drivers() {
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = car.gPosition();
        vectorJ dir = car.gDir();
        dir.mult(10.0);
        pos.oPlus(dir);
        vectorJ posUp = new vectorJ(pos.x, pos.y, pos.z + 100.0);
        vectorJ posDown = new vectorJ(pos.x, pos.y, pos.z - 100.0);
        vectorJ resultPosition = Collide.collidePoint(posUp, posDown);
        aiplayer Bandit1 = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        aiplayer PiterPan = aiplayer.getSimpleAiplayer("SC_PITERPANLOW");
        actorveh banditsCar = eng.CreateCarForScenario(CarName.CAR_PITER_PAN, new matrixJ(), resultPosition);
        Crew.addMappedCar("ARGOSY BANDIT", banditsCar);
        Bandit1.bePassangerOfCar(banditsCar);
        PiterPan.beDriverOfCar(banditsCar);
    }

    void pp_final_race() {
        new PiterPanFinalrace("pp_race", 3, (ScenarioHost)scenarioscript.script);
    }

    public void craete_winner_scene_test() {
        vectorJ pos = new vectorJ();
        rnrscr.Helper.getNearestGoodPoint(pos);
        new BigRaceCrowd().winnersAnimation(1, pos, new matrixJ());
    }

    public void test_change1() {
        MakeRaceInitialization sceneThread = new MakeRaceInitialization();
        ThreadTask.create(sceneThread);
    }

    public void test_change2() {
        ÑhageCarsBack sceneThread = new ÑhageCarsBack();
        ThreadTask.create(sceneThread);
    }

    public void test861mission() {
        StartScenarioMissionAction action = new StartScenarioMissionAction();
        action.name = "m00861";
        action.timer = "Start0861";
        action.act();
    }

    public void test_mimic() {
        SCRuniperson person1 = SCRuniperson.createNamedCutScenePerson("IVAN_NEW", "IVAN_NEWman1", "man1");
        SCRuniperson person2 = SCRuniperson.createNamedCutScenePerson("IVAN_NEW", "IVAN_NEWman2", "man2");
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(new SceneActorsPool("man1", person1));
        pool.add(new SceneActorsPool("man2", person2));
        Data data = new Data(new matrixJ(), this.getNearCarPosition(), null);
        data.M_180 = new matrixJ();
        data.M_180.v0 = new vectorJ(0.0, -1.0, 0.0);
        data.M_180.v1 = new vectorJ(1.0, 0.0, 0.0);
        data.P2 = new vectorJ(data.P);
        data.P2.x += 0.5;
        scene_test_mimic = scenetrack.CreateSceneXMLPool("testmimic", 517, pool, data);
    }

    public void test_mimic_stop() {
        if (scene_test_mimic != 0L) {
            scenetrack.DeleteScene(scene_test_mimic);
        }
    }

    public void test_bar_empty() {
        eng.setParallelWorldCondition("bar", "bar_empty");
    }

    public void test_bar_full() {
        eng.setParallelWorldCondition("bar", "bar_full");
    }

    public void set_sc_flag_true(String flag_name) {
        ScenarioFlagsManager.getInstance().setFlagValue(flag_name, true);
    }

    public void set_sc_flag_false(String flag_name) {
        ScenarioFlagsManager.getInstance().setFlagValue(flag_name, false);
    }

    private String getPhrase() {
        int index = AdvancedRandom.RandFromInreval(0, Phrases.length - 1);
        return Phrases[index];
    }

    public void meet_client() {
        SCRuniperson person = SCRuniperson.createNamedCutScenePerson("Man_001HiPoly", "Client", "man");
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(new SceneActorsPool("man", person));
        Data data = new Data(new matrixJ(), this.getNearCarPosition(), null);
        data.Phrase = this.getPhrase();
        long scene = scenetrack.CreateSceneXMLPool("meet_client", 17, pool, data);
        int nomManAnim = AdvancedRandom.RandFromInreval(0, 2);
        scenetrack.ChangeClipParam(scene, "Man", "MAN_MeetClient_listen_Clip", this, nomManAnim == 0 ? "setClipActive" : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "Man", "MAN_MeetClient_talk_Clip", this, nomManAnim == 1 ? "setClipActive" : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "Man", "MAN_MeetClient_talk_with_shlders_Clip", this, nomManAnim == 2 ? "setClipActive" : "setClipPassive");
        int nomIvanAnim = AdvancedRandom.RandFromInreval(0, 1);
        scenetrack.ChangeClipParam(scene, "IVAN_NEW", "IVAN_MEW_MeetClient_listen_hand_Clip", this, nomIvanAnim == 0 ? "setClipActive" : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "IVAN_NEW", "IVAN_MEW_MeetClient_listen_2_Clip", this, nomIvanAnim == 1 ? "setClipActive" : "setClipPassive");
        int nomCamAnim = AdvancedRandom.RandFromInreval(0, 1);
        scenetrack.ChangeClipParam(scene, "CamS_MeetClient", "CamS_to_NPC_near_Clip", this, nomCamAnim == 0 ? "setClipActive" : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "CamS_to_NPC_far", "CamS_to_NPC_near_Clip", this, nomCamAnim == 1 ? "setClipActive" : "setClipPassive");
    }

    public void setClipActive(camscripts.trackclipparams pars) {
        pars.track_mute = false;
    }

    public void setClipPassive(camscripts.trackclipparams pars) {
        pars.track_mute = true;
    }

    public void meet_client(String dialog_name, String identitie) {
        Dialog.getDialog(dialog_name).start_simplePoint(Crew.getIgrok().getModel(), identitie, new matrixJ(), this.getNearCarPosition());
    }

    public void debug_sc00009() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                actorveh car = Crew.getIgrokCar();
                car.teleport(sc00009.officePos);
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00009");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00011() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                actorveh car = Crew.getIgrokCar();
                car.teleport(sc00009.officePos);
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00011");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    private vectorJ getNearCarPosition() {
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = car.gPosition();
        vectorJ dir = car.gDir();
        dir.mult(10.0);
        pos.oPlus(dir);
        vectorJ posUp = new vectorJ(pos.x, pos.y, pos.z + 100.0);
        vectorJ posDown = new vectorJ(pos.x, pos.y, pos.z - 100.0);
        vectorJ resultPosition = Collide.collidePoint(posUp, posDown);
        if (resultPosition == null) {
            resultPosition = pos;
        }
        return resultPosition;
    }

    public void debug_sc00030() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh carcoh = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(), test.this.getNearCarPosition());
                Crew.addMappedCar("KOH", carcoh);
                if (null == KohHelpManage.getInstance()) {
                    KohHelpManage.constructSingleton(scenarioscript.script);
                }
                if (null == KohHelpManage.getInstance().getNickLocation()) {
                    KohHelpManage.getInstance().debugSetNickLocation();
                }
                eng.unlock();
                Helper.waitLoaded(carcoh);
                eng.lock();
                test.this.debug_stage("sc00030");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00031() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh carcoh = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(), test.this.getNearCarPosition());
                Crew.addMappedCar("KOH", carcoh);
                if (null == KohHelpManage.getInstance()) {
                    KohHelpManage.constructSingleton(scenarioscript.script);
                }
                if (null == KohHelpManage.getInstance().getNickLocation()) {
                    KohHelpManage.getInstance().debugSetNickLocation();
                }
                eng.unlock();
                Helper.waitLoaded(carcoh);
                eng.lock();
                test.this.debug_stage("sc00031");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00032() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh carcoh = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(), test.this.getNearCarPosition());
                Crew.addMappedCar("KOH", carcoh);
                if (null == KohHelpManage.getInstance()) {
                    KohHelpManage.constructSingleton(scenarioscript.script);
                }
                if (null == KohHelpManage.getInstance().getNickLocation()) {
                    KohHelpManage.getInstance().debugSetNickLocation();
                }
                eng.unlock();
                Helper.waitLoaded(carcoh);
                eng.lock();
                test.this.debug_stage("sc00032");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00060_1() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Oxnard_Bar_01");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                specobjects so = specobjects.getInstance();
                cSpecObjects ob = so.GetNearestLoadedParkingPlace_nearBarNamed("Room_Oxnard_Bar1", 200.0);
                if (null != ob) {
                    actorveh banditsCar = eng.CreateCarForScenario(CarName.CAR_BANDITS, ob.matrix, ob.position);
                    banditsCar.UpdateCar();
                    if (0 == banditsCar.getCar()) {
                        eng.err("0==banditsCar.car in " + this.getClass().getName());
                    }
                    banditsCar.registerCar("banditcar");
                    Crew.addMappedCar("ARGOSY BANDIT", banditsCar);
                    parkingplace parking = parkingplace.findParkingByName("Oxnard_Parking_01", ob.position);
                    banditsCar.makeParking(parking, 4);
                }
                actorveh chaser = Crew.getMappedCar("ARGOSY BANDIT");
                test.this.m_debug_shootanimation = new ResqueDorothyShootAnimate(null, chaser, false);
                test.this.debug_stage("sc00060part1");
                eng.unlock();
                NativeEventController.addNativeEventListener(new INativeMessageEvent(){

                    public String getMessage() {
                        return "debug_scene_finished";
                    }

                    public boolean removeOnEvent() {
                        return true;
                    }

                    public void onEvent(String message) {
                        test.this.m_debug_shootanimation.finish();
                        actorveh banditcar = Crew.getMappedCar("ARGOSY BANDIT");
                        Crew.removeMappedCar("ARGOSY BANDIT");
                        banditcar.deactivate();
                        chase00090.stopDorothyPassanger();
                    }
                });
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00065() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Oxnard_Bar_01");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00065");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00324_yes() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                sc00324.setDebugUnswer(true);
                eng.lock();
                test.this.debug_stage("sc00324");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00324_no() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                sc00324.setDebugUnswer(false);
                eng.lock();
                test.this.debug_stage("sc00324");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00465() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = RaceTrajectory.getFinish("pp_race");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                Vector<actorveh> vec_participants = new Vector<actorveh>();
                actorveh pl = Crew.getIgrokCar();
                pl.sVeclocity(0.0);
                vec_participants.add(pl);
                actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0, 0.0, 1, 0);
                eng.lock();
                eng.unlock();
                test.this.debug_stage("sc00465");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00520() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = RaceTrajectory.getFinish("pp_race");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                Vector<actorveh> vec_participants = new Vector<actorveh>();
                actorveh pl = Crew.getIgrokCar();
                pl.sVeclocity(0.0);
                vec_participants.add(pl);
                actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0, 0.0, 1, 0);
                eng.lock();
                eng.unlock();
                test.this.debug_stage("sc00520");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00530() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = RaceTrajectory.getFinish("pp_race");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                Vector<actorveh> vec_participants = new Vector<actorveh>();
                actorveh pl = Crew.getIgrokCar();
                pl.sVeclocity(0.0);
                vec_participants.add(pl);
                actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0, 0.0, 1, 0);
                eng.unlock();
                eng.lock();
                sc00530.setDebug();
                test.this.debug_stage("sc00530");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00730() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = new vectorJ(6358.0, -20223.0, 106.0);
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                actorveh dakotacar = null;
                cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_730");
                if (null != crossroad) {
                    sc00730.setDebugMatrices(crossroad.position, crossroad.matrix);
                    dakotacar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, crossroad.matrix, crossroad.position);
                    aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");
                    dakota.beDriverOfCar(dakotacar);
                    Crew.addMappedCar("DAKOTA", dakotacar);
                }
                eng.unlock();
                Helper.waitLoaded(dakotacar);
                eng.lock();
                actorveh banditcar = eng.CreateCarForScenario(CarName.CAR_BANDITS, new matrixJ(), test.this.getNearCarPosition());
                Crew.addMappedCar("ARGOSY BANDIT", banditcar);
                aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
                pl.beDriverOfCar(banditcar);
                aiplayer Bandit2 = aiplayer.getSimpleAiplayer("SC_BANDITJOELOW");
                aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");
                Bandit2.bePassangerOfCar(banditcar);
                GUN.bePassangerOfCar_simple_like(banditcar, Bandit2.gModelname());
                eng.unlock();
                Helper.waitLoaded(banditcar);
                eng.lock();
                Crew.getMappedCar("DAKOTA").registerCar("dakotacar");
                Crew.getMappedCar("ARGOSY BANDIT").registerCar("banditcar");
                test.this.debug_stage("sc00730");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00750() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("CP_126_5");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
                if (crossroad != null && crossroad.name.compareToIgnoreCase("KeyPoint_750") == 0) {
                    actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_COCH, crossroad.matrix, crossroad.position);
                    vectorJ shift = new vectorJ(crossroad.matrix.v0);
                    shift.mult(5.0);
                    actorveh dorcar = eng.CreateCarForScenario(CarName.CAR_DOROTHY, crossroad.matrix, crossroad.position.oPlusN(shift));
                    Crew.addMappedCar("DOROTHY", dorcar);
                    Crew.addMappedCar("KOH", kohcar);
                }
                eng.unlock();
                eng.lock();
                test.this.debug_stage("sc00750");
                eng.unlock();
                NativeEventController.addNativeEventListener(new INativeMessageEvent(){

                    public String getMessage() {
                        return "debug_scene_finished";
                    }

                    public boolean removeOnEvent() {
                        return true;
                    }

                    public void onEvent(String message) {
                        actorveh dorcar = Crew.getMappedCar("DOROTHY");
                        actorveh kohcar = Crew.getMappedCar("KOH");
                        Crew.removeMappedCar("DOROTHY");
                        Crew.removeMappedCar("KOH");
                        dorcar.deactivate();
                        kohcar.deactivate();
                    }
                });
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00830() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                sc00830.setDebugScene();
                vectorJ pos = eng.getControlPointPosition("CP_LB_TS");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                parkingplace parking = parkingplace.findParkingByName("PK_LA_LB_01", pos);
                matrixJ m = new matrixJ();
                vectorJ p = eng.getControlPointPosition("CP_LB_TS");
                vectorJ shift = new vectorJ(m.v0);
                shift.mult(5.0);
                actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_COCH, m, p);
                actorveh dorcar = eng.CreateCarForScenario(CarName.CAR_DOROTHY, m, p.oPlusN(shift));
                Crew.addMappedCar("DOROTHY", dorcar);
                Crew.addMappedCar("KOH", kohcar);
                kohcar.makeParking(parking, 6);
                dorcar.makeParking(parking, 7);
                eng.unlock();
                test.this.debug_stage("sc00830");
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00860() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                sc00860.setDebug();
                vectorJ pos = eng.getControlPointPosition("CP_meet_on_14");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
                actorveh darktruck = null;
                if (crossroad != null && crossroad.name.compareToIgnoreCase("KeyPoint_860") == 0) {
                    darktruck = eng.CreateCarForScenario(CarName.CAR_MATHEW_DEAD, crossroad.matrix, crossroad.position);
                    Vector<actorveh> actors = new Vector<actorveh>();
                    actors.add(darktruck);
                    actorveh.aligncars(actors, crossroad.position, 15.0, 10.0, 1, 0);
                    Crew.addMappedCar("DARK TRUCK", darktruck);
                }
                eng.unlock();
                Helper.waitLoaded(darktruck);
                eng.lock();
                test.this.debug_stage("sc00860");
                eng.unlock();
                NativeEventController.addNativeEventListener(new INativeMessageEvent(){

                    public String getMessage() {
                        return "debug_scene_finished";
                    }

                    public boolean removeOnEvent() {
                        return true;
                    }

                    public void onEvent(String message) {
                        actorveh darktruck = Crew.getMappedCar("DARK TRUCK");
                        Crew.removeMappedCar("DARK TRUCK");
                        darktruck.deactivate();
                    }
                });
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc00930() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Oxnard_Police");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00930");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01030_yes() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                sc01030.setDebugSceneAnswer(true);
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(sc00009.officePos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01030");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01030_no() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                sc01030.setDebugSceneAnswer(false);
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(sc00009.officePos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01030");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01100() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                sc01100.setDebugScene();
                vectorJ pos = eng.getControlPointPosition("John_House");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01100");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01370() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01370");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01380() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01380");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01420() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(sc00009.officePos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01420");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01540() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Red Rock Canyon Scene");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01540");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01541() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("Red Rock Canyon Scene");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01541");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01620() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(new vectorJ(6979.0, 14062.0, 190.0));
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01620");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc01640() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(new vectorJ(3618.495117, 13370.149048, 320.980331));
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01640");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc02025() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vectorJ pos = eng.getControlPointPosition("EnemyBaseTruckstop");
                actorveh car = Crew.getIgrokCar();
                car.teleport(pos);
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc02025");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc02030() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                eng.setdword("DWORD_EnemyBaseAssaultTeam", 0);
                eng.setdword("DWORD_EnemyBaseAssault", 1);
                actorveh car = Crew.getIgrokCar();
                car.teleport(new vectorJ(3856.20603, 13122.95883, 317.619));
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                vectorJ posAssault = eng.getControlPointPosition("LineUp2030");
                actorveh car1 = eng.CreateCarForScenario(CarName.CAR_JOHN, new matrixJ(), posAssault);
                actorveh car2 = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), posAssault);
                eng.unlock();
                Helper.waitLoaded(car1);
                eng.lock();
                car1.UpdateCar();
                car1.registerCar("JOHN");
                car1.setCollideMode(false);
                eng.unlock();
                Helper.waitLoaded(car2);
                eng.lock();
                car2.UpdateCar();
                car2.registerCar("DAKOTA");
                aiplayer dakota = new aiplayer("SC_ONTANIELOLOW");
                dakota.setModelCreator(new ScenarioPersonPassanger(), null);
                dakota.beDriverOfCar(car2);
                eng.unlock();
                eng.lock();
                test.this.debug_stage("sc02030");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc02040() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(new vectorJ(3856.20603, 13122.95883, 317.619));
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                sc02040.prepareScene(new Object());
                test.this.debug_stage("sc02040");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc02045() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                actorveh car = Crew.getIgrokCar();
                car.teleport(new vectorJ(3856.20603, 13122.95883, 317.619));
                eng.unlock();
                Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc02045");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_sc02050() {
        CrashBarScene.DEBUG = true;
        final actorveh playerCar = Crew.getIgrokCar();
        playerCar.teleport(eng.getControlPointPosition("ChaseKohStart"));
        MakeTeleport.teleport(new ITeleported(){

            public void teleported() {
                vectorJ position = eng.getControlPointPosition("ChaseKohStart");
                parkingplace place = parkingplace.findParkingByName("pk_MW_01", position);
                playerCar.makeParking(place, 0);
                actorveh cochCar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), position);
                aiplayer person_player = new aiplayer("SC_KOH");
                person_player.sPoolBased("koh_cut_scene_driver");
                person_player.setModelCreator(new CutSceneAuxPersonCreator(), "koh");
                person_player.beDriverOfCar(cochCar);
                SCRuniperson koh_person = person_player.getModel();
                koh_person.SetInWorld("bar_crash");
                Crew.addMappedCar("KOH", cochCar);
                eng.setdword("Dword_BarIn4_Crash", 1);
                CrashBarScene.DEBUG = true;
                new CrashBarScene().run();
            }
        });
        EventsController.getInstance().addListener(new EventListener(){

            // @Override
            public void eventHappened(List<ScriptEvent> eventTuple) {
                if (null != eventTuple && 0 < eventTuple.size() && eventTuple.get(0) instanceof MessageEvent && 0 == "finished scene sc02050".compareTo(((MessageEvent)eventTuple.get(0)).getMessage())) {
                    Crew.deactivateMappedCar("KOH");
                    EventsController.getInstance().removeListener(this);
                }
            }
        });
    }

    public void debug_sc02060() {
        actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), this.getNearCarPosition());
        Crew.addMappedCar("KOH", kohcar);
        sc02060.setDebugMode(true);
        this.debug_stage("sc02060");
        NativeEventController.addNativeEventListener(new INativeMessageEvent(){

            public String getMessage() {
                return "debug_scene_finished";
            }

            public boolean removeOnEvent() {
                return true;
            }

            public void onEvent(String message) {
                actorveh cohcar = Crew.getMappedCar("SC_KOH");
                Crew.removeMappedCar("SC_KOH");
                cohcar.deactivate();
                sc02060.setDebugMode(false);
            }
        });
    }

    public void failIfScenarioAlive() {
        if (null != scenarioscript.script) {
            Collection<String> fsmActiveStates;
            if (null != scenarioscript.script.getStage() && scenarioscript.script.getStage().isScenarioFinished()) {
                int currentScenarioStage = scenarioscript.script.getStage().getScenarioStage();
                eng.fatal(String.format("Scenario State is not maximal (%d)", currentScenarioStage));
            }
            if (null != scenarioscript.script.getScenarioMachine() && !(fsmActiveStates = scenarioscript.script.getScenarioMachine().getCurrentActiveStates()).isEmpty()) {
                StringBuilder errorMessageComposer = new StringBuilder(1024);
                errorMessageComposer.append("Scenario FFSM has active states:");
                for (String stateName : fsmActiveStates) {
                    errorMessageComposer.append(' ').append(stateName);
                }
                eng.fatal(errorMessageComposer.toString());
            }
        }
    }

    public void debug_sc02065() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                vehicle carGepard = vehicle.create("GEPARD", 0);
                carGepard.setLeased(true);
                actorveh car = Crew.getIgrokCar();
                matrixJ M = car.gMatrix();
                vectorJ P = test.this.getNearCarPosition();
                vehicle.changeLiveVehicle(car, carGepard, M, P);
                eng.unlock();
                Helper.waitVehicleChanged();
                eng.lock();
                test.this.debug_stage("sc02065");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    public void debug_final_1() {
        Ithreadprocedure sceneThread = new Ithreadprocedure(){

            public void call() {
                eng.lock();
                ArrayList<Pair<String, String>> data = new ArrayList<Pair<String, String>>();
                data.add(new Pair<String, String>(FactionRater.FACTION_NAMES[1], "0"));
                data.add(new Pair<String, String>(FactionRater.FACTION_NAMES[0], "0"));
                FactionRater.setSerializationData(data);
                test.this.debug_stage("sc02070");
                eng.unlock();
            }

            public void take(ThreadTask safe) {
            }
        };
        ThreadTask.create(sceneThread);
    }

    static {
        pers_group1 = null;
        scene_test_mimic = 0L;
        count_man = 0;
        count_ivan = 0;
        count_cam = 0;
        Phrases = new String[]{"MatT_SC00009_04", "CocH_SC00011_01", "SteC_SC01200_04", "JohL_SC00480_03", "TomD_SC00430_03", "SteC_SC01030_07", "NicA_SC01200_06", "DorL_SC02050_04", "NicA_SCST_20_01", "SteC_SC01200_01", "MatT_SC00009_03", "NicA_SC01080_01"};
    }

    static class ÑhageCarsBack
    implements Ithreadprocedure {
        ÑhageCarsBack() {
        }

        public void call() {
            eng.lock();
            actorveh ourcar = Crew.getIgrokCar();
            matrixJ remM = ourcar.gMatrix();
            vectorJ remV = ourcar.gPosition();
            vehicle our_last_car = rnrscenario.Helper.getLiveCarFromGarage();
            vehicle gepard = ourcar.querryCurrentCar();
            vehicle.changeLiveVehicle(ourcar, our_last_car, remM, remV);
            eng.unlock();
            Helper.waitVehicleChanged();
            eng.lock();
            gepard.delete();
            eng.unlock();
        }

        public void take(ThreadTask safe) {
        }
    }

    static class MakeRaceInitialization
    implements Ithreadprocedure {
        MakeRaceInitialization() {
        }

        public void call() {
            eng.lock();
            actorveh ourcar = Crew.getIgrokCar();
            matrixJ remM = ourcar.gMatrix();
            vectorJ remV = ourcar.gPosition();
            remV.x += 100.0;
            vehicle gepard = vehicle.create("GEPARD", 0);
            gepard.setLeased(true);
            vehicle our_last_car = ourcar.querryCurrentCar();
            vehicle.changeLiveVehicle(ourcar, gepard, remM, remV);
            eng.unlock();
            Helper.waitVehicleChanged();
            eng.lock();
            rnrscenario.Helper.placeLiveCarInGarage(our_last_car);
            eng.unlock();
        }

        public void take(ThreadTask safe) {
        }
    }

    static class CycleMenuCreate
    extends TypicalAnm {
        private static final int MAX_CREATIONS = 300;
        private static final int DUMP_PERIOD = 10;
        private int m_mainMenuCreated = 0;
        private int m_journalMenuCreated = 0;
        private int m_counter = 0;
        private boolean m_menuCreated = false;
        private long m_menuHandle = 0L;
        private static CycleMenuCreate m_instance = null;

        CycleMenuCreate() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            if (300 < this.m_mainMenuCreated) {
                if (this.m_menuCreated) {
                    this.m_menuCreated = false;
                    menues.CallMenuCallBack_ExitMenu(this.m_menuHandle);
                    this.m_menuHandle = 0L;
                }
                return true;
            }
            if (++this.m_counter % 3 == 0) {
                this.m_counter = 0;
                if (this.m_menuCreated) {
                    assert (0L != this.m_menuHandle);
                    this.m_menuCreated = false;
                    menues.CallMenuCallBack_ExitMenu(this.m_menuHandle);
                    this.m_menuHandle = 0L;
                } else {
                    assert (0L == this.m_menuHandle);
                    this.m_menuCreated = true;
                    this.m_menuHandle = EscapeMenu.CreateEscapeMenu();
                    ++this.m_mainMenuCreated;
                    if (0 == this.m_mainMenuCreated % 10) {
                        System.out.println("mainMenuCreated: " + this.m_mainMenuCreated);
                        System.out.println("journalMenuCreated: " + this.m_journalMenuCreated);
                    }
                }
            }
            return false;
        }

        static void makeTest() {
            if (null == m_instance) {
                m_instance = new CycleMenuCreate();
            }
        }
    }

    private static final class cTestDriversPools
    extends TypicalAnm {
        cTestDriversPools() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            Crew.getInstance().getCarCreationController().getPool().makePoolCycle();
            return false;
        }
    }

    class DeleteTask
    implements IScriptTask {
        private long task;

        DeleteTask(long task) {
            this.task = task;
        }

        public void launch() {
            eng.DeleteTASK(this.task);
        }
    }

    static class Preset {
        actorveh car;

        Preset() {
        }
    }

    static class Data {
        matrixJ M;
        matrixJ M_180;
        vectorJ P;
        vectorJ P2;
        actorveh car;
        String Phrase;

        Data() {
        }

        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }

    static class create_passdata {
        actorveh car;

        create_passdata() {
        }
    }

    class ShowOfficeMenu
    implements anm {
        private ScriptRef uid = new ScriptRef();

        ShowOfficeMenu() {
        }

        public void setUid(int value) {
            this.uid.setUid(value);
        }

        public int getUid() {
            return this.uid.getUid(this);
        }

        public void removeRef() {
            this.uid.removeRef(this);
        }

        public void updateNative(int p) {
        }

        public boolean animaterun(double dt) {
            if (dt > 1.0) {
                menues.showMenu(8000);
                return true;
            }
            return false;
        }

        public IXMLSerializable getXmlSerializator() {
            return null;
        }
    }

    static class OfficeCam
    implements IScriptTask {
        OfficeCam() {
        }

        public void launch() {
            SCRcamera freeCam = SCRcamera.CreateCamera("free");
            freeCam.PlayCamera();
            freeCam.SetInOfficeWorld();
            vectorJ posit = new vectorJ(0.0, 0.0, 2.0);
            freeCam.SetCameraPosition(posit);
        }
    }

    static class ChaseTopoTest
    extends TypicalAnm {
        public int mode = 2;
        public double dist_ahead0 = 10.0;
        public double dist_ahead2 = 11.0;
        public double dist_behind0 = -10.0;
        public double dist_behind2 = -11.0;
        public double minvel = 40.0;
        public double maxvel = 60.0;
        public boolean toRenew = true;
        actorveh chaser = null;
        actorveh flee = Crew.getIgrokCar();

        vectorJ getPosBehind() {
            vectorJ pos = this.flee.gDir();
            pos.mult(-100.0);
            return pos.oPlusN(this.flee.gPosition());
        }

        void makeChaseParametrs() {
            Chase chase = new Chase();
            chase.mode = this.mode;
            chase.dist_ahead0 = this.dist_ahead0;
            chase.dist_ahead2 = this.dist_ahead2;
            chase.dist_behind0 = this.dist_behind0;
            chase.dist_behind2 = this.dist_behind2;
            chase.minvel = this.minvel;
            chase.maxvel = this.maxvel;
            chase.makechase(this.chaser, this.flee);
        }

        ChaseTopoTest() {
            eng.CreateInfinitScriptAnimation(this);
            this.chaser = eng.CreateCarForScenario(CarName.CAR_BANDITS, new matrixJ(), this.getPosBehind());
            this.chaser.sVeclocity(20.0);
            rnrscenario.Helper.makePowerEngine(this.chaser);
        }

        public boolean animaterun(double dt) {
            if (this.toRenew) {
                this.makeChaseParametrs();
                this.toRenew = false;
            }
            return false;
        }
    }
}
