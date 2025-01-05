/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import players.CarName;
import players.Chase;
import players.Crew;
import players.ScenarioPersonPassanger;
import players.actorveh;
import players.aiplayer;
import players.vehicle;
import rnrcore.ConvertGameTime;
import rnrcore.CoreTime;
import rnrcore.EnemyBaseObjectXmlSerializable;
import rnrcore.ObjectXmlSerializable;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.Helper;
import rnrscenario.configurators.EnemyBaseConfig;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.scenarioscript;
import rnrscenario.scenes.sc02040;
import rnrscr.AdvancedRandom;
import rnrscr.CBVideoInterruptCalls;
import rnrscr.IInterruptCall;
import rnrscr.parkingplace;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@ScenarioClass(scenarioStage=14)
public final class EnemyBase
extends TypicalAnm
implements ScenarioController {
    private static final boolean DEBUG = false;
    private static final double TRACK_MIN_DISTANCE_2 = 1600.0;
    private static final double TRACK_MAX_DISTANCE_2 = 160000.0;
    private static final double DAKOTA_WARNING_TIME = 60.0;
    private static final double TIME_MONICA_FINDS_OUT = 900.0;
    private static final double TIME_DAKOTA_CALL = 600.0;
    private static final double TIME_2020_CALL = 1800.0;
    public static final String[] MESSAGES = new String[]{"reach EnemyBaseTruckstop", "start_1600", "Dorothy_call", "cb01600 finished", "m01600 failed", "Monica discovered EnemyBase", "cb02010 finished", "threaten_call_1", "cb01660 finished", "track to EnemyBase failed", "track to EnemyBase succeded", "make_Dacota_call", "cb02015 finished", "met assault team", "Reach EnemyBase failure", "Reach EnemyBase succes", "EnemyBaseAssault succes", "EnemyBaseAssault covard", "EnemyBaseAssault failed", "cb02020 finished", "cb02021 finished", "enemybase moat", "Dakota warns about bomb", "EnemyBaseFinish", "m01600 activated", "m02025 activated", "m02025 failed", "make 2020 call"};
    public static final int MESSAGE_MET_MONICA = 0;
    public static final int MESSAGE_START = 1;
    public static final int MESSAGE_MONICA_CALL1 = 2;
    public static final int MESSAGE_MONICA_CALL1_FINISHED = 3;
    public static final int MESSAGE_MET_MONICA_FAILED = 4;
    public static final int MESSAGE_MONICA_DISCOVERED_ENEMYBASE = 5;
    public static final int MESSAGE_MONICA_CALL2_FINISHED = 6;
    public static final int MESSAGE_THREATS_CALL = 7;
    public static final int MESSAGE_THREATS_CALL_FINISHED = 8;
    public static final int MESSAGE_TRACK_FAILED = 9;
    public static final int MESSAGE_TRACK_SUCCEED = 10;
    public static final int MESSAGE_DAKOTA_CALL = 11;
    public static final int MESSAGE_DAKOTA_CALL_FINISHED = 12;
    public static final int MESSAGE_MET_ASSAULT_TEAM = 13;
    public static final int MESSAGE_REACH_ENEMYBASE_FAILURE = 14;
    public static final int MESSAGE_REACH_ENEMYBASE_SUCCEED = 15;
    public static final int MESSAGE_ASSAULT_SUCCEED = 16;
    public static final int MESSAGE_ASSAULT_COVARD = 17;
    public static final int MESSAGE_ASSAULT_FAILED = 18;
    public static final int MESSAGE_THREATS_CALL1_FINISHED = 19;
    public static final int MESSAGE_THREATS_CALL2_FINISHED = 20;
    public static final int MESSAGE_OVER_ENEMYBASE_MOAT = 21;
    public static final int MESSAGE_DAKOTA_WARNING = 22;
    public static final int MESSAGE_FINISH = 23;
    public static final int MESSAGE_1600_STARTED = 24;
    public static final int MESSAGE_2025_FAILED = 26;
    public static final int MESSAGE_2020_CALL = 27;
    public static final String METHOD_THREATCALL = "threat_call";
    public static final String METHOD_DACOTACALL = "dakota_call";
    public static final String METHOD_MONICA_DISCOVERED_BASE = "monica_discovered_base";
    public static final String METHOD_MONICA_CALL2_FINISHED = "monica_discovered_base_finish_call";
    public static final String METHOD_2025_FAILED = "failed_2025";
    public static final String METHOD_1600_STARTED = "started_1600";
    private static final int[] EVENTS = new int[]{22046, 22047, 21620, 22025, 21640, 22030};
    private static final String[] EVENT_METHODS = new String[]{"enter_moat", "enter_nearbase", "enter_behindmotel", "enter_assaultteam", "enter_tracktunnel", "enter_assault"};
    private static final int EVENTID_ENTER_MOAT = 0;
    public static final String DWORD_ENEMYBASE = "DWORD_EnemyBase";
    public static final String DWORD_MONICA = "DWORD_EnemyBaseMonica";
    public static final String DWORD_ASSAULTTEAM = "DWORD_EnemyBaseAssaultTeam";
    public static final String DWORD_ENEMYBASE_TUNNEL = "DWORD_EnemyBaseTunnel";
    public static final String DWORD_ENEMYBASE_ASSAULT = "DWORD_EnemyBaseAssault";
    public static final String DWORD_WIREROPE = "DWORD_EnemyBaseWireRope";
    private static final Set<String> allDwords = new TreeSet<String>();
    private static final String POINT_TRUCKSTOP = "EnemyBaseTruckstop";
    private static final String POINT_TUNNEL = "EnemyBaseTunnel";
    public static final String POINT_ASSAULT = "EnemyBaseAssaultRoot";
    public static final String POINT_LINEUP = "LineUp2030";
    public static final String PROC_MOTEL = "KeyPoint_1620";
    public static final String PROC_TUNNEL = "KeyPoint_1640";
    public static final String PROC_SNIPER = "KeyPoint_2045";
    public static final String PROC_WIN = "KeyPoint_2040";
    public static final CarName[] CAR_NAMES;
    private static final int[] PARKINGPLACES;
    public static final int BANDIT_CAR_TUNNEL = 0;
    public static final int BANDIT_CAR2 = 1;
    public static final int MONICA_CAR = 2;
    public static final CarName[] CAR_NAMES_ASSAULT;
    private static final int[] PARKINGPLACES_ASSAULT;
    public static final int GEPARD_CAR_ASSAULT = 0;
    public static final int JOHN_CAR_ASSAULT = 1;
    public static final int DAKOTA_CAR_ASSAULT = 2;
    public static final String MISSION_MET_MONICA = "m01600";
    public static final String MISSION_MET_ASSAULTTEAM = "m02025";
    private static int moatTriggerId;
    private static EnemyBase gENEMYBASE;
    private int[] event_ids = new int[EVENTS.length];
    private long assault_cycling_scene = 0L;
    private boolean firstTimeMeasure = true;
    private double lastTime = 0.0;
    private boolean moatEventTriggerRegistered = false;
    private boolean stopPlay = false;
    private volatile boolean interruptPlay = false;
    private boolean isAssaultCycleSceneCreated = false;
    private boolean wasVehicleExchange = false;
    private boolean bad_conditions = false;
    private actorveh[] cars = new actorveh[CAR_NAMES.length];
    public actorveh[] cars_assault = new actorveh[CAR_NAMES_ASSAULT.length];
    private aiplayer monica = null;
    private aiplayer dakota = null;
    private Set<String> currentActiveDwords = new TreeSet<String>();
    private boolean assault_started = false;
    private double assault_start_time = 0.0;
    private double dakota_warning_start_time = 0.0;
    private boolean want_dakota_warning = false;
    private boolean want_dakota_warning_started = false;
    private boolean to_slow_down_gepard = false;
    private boolean slowdown_start = true;
    private double initialVelocity = 0.0;
    private double slowDownAcceleration = -20.0;
    private Timing_MonicaFindsOut to_make_discover_base = null;
    private Timing_DakotaCall to_make_dakota_call = null;
    private Timing_ThreatenCall to_make_threat_call = null;
    private Timing_BadCall to_make_2020_call = null;
    private boolean to_track_tunnel = false;
    private boolean was_near_base = false;
    private ObjectXmlSerializable serializator;
    private final ScenarioHost host;
    private final EnemyBaseConfig config;
    private double slowDowningTime = 0.0;
    private static final double MAX_TIME_TO_SLOW_DOWN = 15.0;
    private boolean assaultFailed = false;

    public static EnemyBase getInstance() {
        return gENEMYBASE;
    }

    // @Override
    public void gameDeinitLaunched() {
        this.deinit();
    }

    public EnemyBase(EnemyBaseConfig config, ScenarioHost host) {
        assert (null != config && null != host);
        this.config = config;
        this.host = host;
        this.host.registerController(this);
        boolean bl = this.bad_conditions = EVENTS.length != EVENT_METHODS.length;
        if (this.bad_conditions) {
            eng.log(this.getClass().getName() + " has bad conditions\n");
            return;
        }
        if (eng.useNative()) {
            for (int i = 0; i < EVENTS.length; ++i) {
                this.event_ids[i] = event.eventObject(EVENTS[i], this, EVENT_METHODS[i]);
            }
            eng.CreateInfinitScriptAnimation(this);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_DACOTACALL, MESSAGES[12]);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_THREATCALL, MESSAGES[8]);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_MONICA_DISCOVERED_BASE, MESSAGES[4]);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_MONICA_DISCOVERED_BASE, MESSAGES[9]);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_MONICA_CALL2_FINISHED, MESSAGES[6]);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_1600_STARTED, MESSAGES[24]);
            EventsControllerHelper.getInstance().addMessageListener(this, METHOD_2025_FAILED, MESSAGES[26]);
            EventsControllerHelper.getInstance().addMessageListener(this, "failedToReachEnemyBase", "m02030 failed");
            this.monica_call();
            this.serializator = new EnemyBaseObjectXmlSerializable(this);
            this.serializator.registerObjectXmlSerializable();
        }
        gENEMYBASE = this;
        this.setDword(DWORD_ENEMYBASE);
    }

    public void failedToReachEnemyBase() {
        this.deinit();
    }

    public static vectorJ getPOINT_TRUCKSTOP() {
        vectorJ pos = eng.getControlPointPosition(POINT_TRUCKSTOP);
        if (pos.length() < 1.0) {
            pos.x = 6977.0;
            pos.y = 14065.0;
            pos.z = 190.0;
        }
        return pos;
    }

    public static vectorJ getPOINT_LINEUP() {
        vectorJ pos = eng.getControlPointPosition(POINT_LINEUP);
        if (pos.length() < 1.0) {
            pos.x = 3837.0;
            pos.y = 13149.0;
            pos.z = 318.0;
        }
        return pos;
    }

    private static void registerMoatEvent() {
        moatTriggerId = event.eventObject(EVENTS[0], scenarioscript.script, EVENT_METHODS[0]);
    }

    private static void unregisterMoatEvent() {
        event.removeEventObject(moatTriggerId);
    }

    private void setDword(String dword) {
        if (null != dword) {
            this.currentActiveDwords.add(dword);
            if (eng.useNative()) {
                eng.setdword(dword, 1);
            }
        }
    }

    private void offDword(String dword) {
        if (null != dword) {
            this.currentActiveDwords.remove(dword);
            eng.setdword(dword, 0);
        }
    }

    public void enableTruckstopTrigger() {
        this.setDword(DWORD_ASSAULTTEAM);
        EventsControllerHelper.getInstance().removeMessageListener(this, "enableTruckstopTrigger", "cb02015 finished");
    }

    @Override
    public boolean animaterun(double timeFromNative) {
        if (!this.moatEventTriggerRegistered) {
            if (!eng.noNative) {
                EnemyBase.registerMoatEvent();
            }
            this.moatEventTriggerRegistered = true;
        }
        double deltaT = timeFromNative - this.lastTime;
        this.lastTime = timeFromNative;
        if (this.to_slow_down_gepard) {
            this.slowdownGaperd(deltaT);
        }
        if (this.interruptPlay) {
            return true;
        }
        if (this.firstTimeMeasure) {
            for (actorveh carForPredefinedScene : this.cars_assault) {
                if (null == carForPredefinedScene) continue;
                carForPredefinedScene.setNeverUnloadFlag();
            }
            this.lastTime = timeFromNative;
            this.firstTimeMeasure = false;
        }
        if (null != this.to_make_dakota_call && this.to_make_dakota_call.run()) {
            this.createCars_meet_assaultteam();
            EventsControllerHelper.getInstance().addMessageListener(this, "enableTruckstopTrigger", "cb02015 finished");
            this.to_make_dakota_call = null;
        }
        if (null != this.to_make_threat_call && this.to_make_threat_call.run()) {
            this.to_make_threat_call = null;
        }
        if (null != this.to_make_2020_call && this.to_make_2020_call.run()) {
            this.to_make_2020_call = null;
        }
        if (null != this.to_make_discover_base && this.to_make_discover_base.run()) {
            this.to_make_discover_base = null;
        }
        if (this.to_track_tunnel) {
            this.track_tunnel();
        }
        if (this.assault_started) {
            this.assault_start_time += deltaT;
            if (this.assault_start_time > this.config.getTiming(0)) {
                this.assault_failed();
            }
        }
        if (this.want_dakota_warning) {
            if (!this.want_dakota_warning_started) {
                this.want_dakota_warning_started = true;
                this.dakota_warning_start_time = 0.0;
            }
            this.dakota_warning_start_time += deltaT;
            if (this.dakota_warning_start_time > 60.0) {
                this.want_dakota_warning = false;
                EventsControllerHelper.messageEventHappened(MESSAGES[22]);
            }
        }
        return this.stopPlay;
    }

    private void slowdownGaperd(double dt) {
        actorveh car = Crew.getIgrokCar();
        if (this.slowdown_start) {
            this.slowdown_start = false;
            double breakingDistance = this.config.getAfterRopeBreakingDistance();
            double initialVelocity = car.gVelocity().length();
            this.slowDownAcceleration = -(initialVelocity * initialVelocity) / (2.0 * breakingDistance);
        }
        double newVelocity = car.gVelocity().length() + this.slowDownAcceleration * dt;
        this.slowDowningTime += dt;
        if (0.0 >= newVelocity || this.slowDowningTime > 15.0) {
            this.to_slow_down_gepard = false;
            newVelocity = 0.0;
            car.setHandBreak(true);
            this.interruptPerFrameExecution();
        }
        car.sVeclocity(newVelocity);
    }

    private void createCars_meet_monica() {
        vectorJ pos = EnemyBase.getPOINT_TRUCKSTOP();
        parkingplace place = parkingplace.findParkingByName("pk_BR_MD_01", pos);
        for (int i = 0; i < CAR_NAMES.length; ++i) {
            actorveh car = eng.CreateCarForScenario(CAR_NAMES[i], new matrixJ(), pos);
            car.makeParking(place, PARKINGPLACES[i]);
            this.cars[i] = car;
        }
    }

    private void createCars_meet_assaultteam() {
        vectorJ pos = EnemyBase.getPOINT_TRUCKSTOP();
        parkingplace place = parkingplace.findParkingByName("pk_BR_MD_01", pos);
        for (int i = 0; i < CAR_NAMES_ASSAULT.length; ++i) {
            actorveh car = eng.CreateCarForScenario(CAR_NAMES_ASSAULT[i], new matrixJ(), pos);
            car.makeParking(place, PARKINGPLACES_ASSAULT[i]);
            this.cars_assault[i] = car;
        }
    }

    private void track_tunnel() {
        vectorJ pos_we;
        vectorJ pos_enemy = this.cars[0].gPosition();
        double len2 = pos_enemy.len2(pos_we = Crew.getIgrokCar().gPosition());
        if (len2 < 1600.0) {
            this.cars[0].stop_autopilot();
            EventsControllerHelper.messageEventHappened("blowcar");
            this.setTrackTunnel(false);
        } else if (len2 > 160000.0) {
            EventsControllerHelper.messageEventHappened(MESSAGES[9]);
            this.setTrackTunnel(false);
        }
    }

    public void met_monica() {
        actorveh car = Crew.getIgrokCar();
        this.monica = new aiplayer("ID_MONICA_2");
        this.monica.setModelCreator(new ScenarioPersonPassanger(), null);
        this.monica.bePassangerOfCar(car);
        vectorJ pos = eng.getControlPointPosition(POINT_TUNNEL);
        this.cars[0].leaveParking();
        this.cars[0].autopilotTo(pos);
        Chase chase = new Chase();
        chase.paramModerateChasing();
        chase.be_ahead(this.cars[0], Crew.getIgrokCar());
        aiplayer driver = new aiplayer("SC_BANDIT");
        driver.setModelCreator(new ScenarioPersonPassanger(), null);
        driver.beDriverOfCar(this.cars[0]);
        this.offDword(DWORD_MONICA);
        this.setDword(DWORD_ENEMYBASE_TUNNEL);
        this.setTrackTunnel(true);
    }

    private void setTrackTunnel(boolean value) {
        if (value) {
            this.to_track_tunnel = true;
            traffic.enterChaseModeSmooth();
        } else {
            this.to_track_tunnel = false;
            traffic.setTrafficMode(0);
        }
    }

    public void met_assault_team() {
        eng.lock();
        actorveh player = Crew.getIgrokCar();
        vectorJ pos = this.cars_assault[0].gPosition();
        matrixJ mat = this.cars_assault[0].gMatrix();
        player.leaveParking();
        player.deleteSemitrailerIfExists();
        this.cars_assault[0].leaveParking();
        vehicle gepard = this.cars_assault[0].takeoff_currentcar();
        gepard.setLeased(true);
        vehicle lastPlayerVehicle = player.querryCurrentCar();
        this.wasVehicleExchange = true;
        vehicle.changeLiveVehicle(player, gepard, mat, pos);
        eng.unlock();
        rnrscenario.tech.Helper.waitVehicleChanged();
        eng.lock();
        Helper.placeLiveCarInGarage(lastPlayerVehicle);
        for (int i = 0; i < this.cars_assault.length; ++i) {
            if (null == this.cars_assault[i]) continue;
            this.deactivateAssaultCar(i);
        }
        this.offDword(DWORD_ASSAULTTEAM);
        this.setDword(DWORD_ENEMYBASE_ASSAULT);
        vectorJ pos_linup = EnemyBase.getPOINT_LINEUP();
        for (int i = 0; i < CAR_NAMES_ASSAULT.length; ++i) {
            pos_linup.x -= 4.0;
            if (i == 0) continue;
            this.cars_assault[i] = eng.CreateCarForScenario(CAR_NAMES_ASSAULT[i], new matrixJ(), pos_linup);
        }
        eng.unlock();
        this.want_dakota_warning = true;
    }

    public void finish_tunnel(boolean _fail) {
        actorveh car = Crew.getIgrokCar();
        this.monica.abondoneCar(car);
        for (actorveh _car : this.cars) {
            _car.deactivate();
        }
        vectorJ pos = EnemyBase.getPOINT_TRUCKSTOP();
        car.teleport(pos);
        if (!_fail) {
            this.to_make_threat_call = new Timing_ThreatenCall();
        }
    }

    public void threat_call() {
        this.to_make_dakota_call = new Timing_DakotaCall();
    }

    public void dakota_call() {
    }

    public void monica_discovered_base() {
        this.to_make_discover_base = new Timing_MonicaFindsOut();
    }

    public void monica_call() {
    }

    public void monica_call_quest_started() {
        this.setDword(DWORD_MONICA);
        this.createCars_meet_monica();
    }

    public void monica_discovered_base_finish_call() {
        this.to_make_threat_call = new Timing_ThreatenCall();
    }

    private void createAssaultCycleScene() {
        if (eng.useNative()) {
            vectorJ pos = eng.getControlPointPosition(POINT_ASSAULT);
            Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
            SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
            v.add(new SceneActorsPool("baza", person));
            Data _data = new Data(new matrixJ(), pos);
            this.assault_cycling_scene = trackscripts.CreateSceneXMLCycle("02040a_part2", v, _data);
        }
        this.isAssaultCycleSceneCreated = true;
    }

    public void assault_begun() {
        this.setDword(DWORD_WIREROPE);
        this.createAssaultCycleScene();
        this.assault_started = true;
        this.assault_start_time = 0.0;
    }

    public void assault_succeded() {
        this.to_slow_down_gepard = true;
        this.slowdown_start = true;
        this.assault_started = false;
        this.deleteAssaultCycleScene();
        EventsControllerHelper.messageEventHappened(MESSAGES[16]);
    }

    public void deleteScenesResources() {
        sc02040.removeScene();
        if (this.isAssaultCycleSceneCreated) {
            this.isAssaultCycleSceneCreated = false;
            scenetrack.DeleteScene(this.assault_cycling_scene);
        }
    }

    private void deleteAssaultCycleScene() {
        if (this.isAssaultCycleSceneCreated) {
            this.isAssaultCycleSceneCreated = false;
            scenetrack.DeleteScene(this.assault_cycling_scene);
        }
    }

    public boolean isAssaultFailed() {
        return this.assaultFailed;
    }

    public void assault_failed() {
        if (eng.useNative()) {
            sc02040.removeScene();
        }
        this.assault_started = false;
        this.assaultFailed = true;
        if (eng.useNative()) {
            this.deleteAssaultCycleScene();
            sc02040.removeScene();
            EnemyBase.unregisterMoatEvent();
            if (this.was_near_base) {
                EventsControllerHelper.messageEventHappened(MESSAGES[18]);
            } else {
                EventsControllerHelper.messageEventHappened(MESSAGES[17]);
            }
        }
    }

    public void finish_Enemy_base() {
        this.deinit();
        EventsControllerHelper.messageEventHappened(MESSAGES[23]);
        this.stopPlay = true;
    }

    private void deactivateAssaultCarsButGepard() {
        for (int i = 0; i < this.cars_assault.length; ++i) {
            if (i == 0) continue;
            this.deactivateAssaultCar(i);
        }
    }

    public void interruptPerFrameExecution() {
        this.interruptPlay = true;
    }

    public void deinit() {
        gENEMYBASE = null;
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
        this.stopPlay = true;
        this.interruptPlay = true;
        for (int eventId : this.event_ids) {
            event.removeEventObject(eventId);
        }
        if (null != this.dakota) {
            this.dakota.abondoneCar(this.cars_assault[2]);
            this.dakota = null;
        }
        eng.explosionsWhilePredefinedAnimation(false);
        this.deleteAssaultCycleScene();
        EnemyBase.unregisterMoatEvent();
        sc02040.removeScene();
        this.deactivateAssaultCarsButGepard();
        this.switchOffActiveDwords();
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_DACOTACALL, MESSAGES[12]);
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_THREATCALL, MESSAGES[8]);
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_MONICA_DISCOVERED_BASE, MESSAGES[4]);
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_MONICA_DISCOVERED_BASE, MESSAGES[9]);
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_MONICA_CALL2_FINISHED, MESSAGES[6]);
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_1600_STARTED, MESSAGES[24]);
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_2025_FAILED, MESSAGES[26]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "failedToReachEnemyBase", "m02030 failed");
    }

    private void switchOffActiveDwords() {
        for (String currentActiveDword : this.currentActiveDwords) {
            if (null == currentActiveDword) continue;
            eng.setdword(currentActiveDword, 0);
        }
        this.currentActiveDwords.clear();
    }

    private void deactivateAssaultCar(int i) {
        if (null != this.cars_assault[i]) {
            this.cars_assault[i].deactivate();
            this.cars_assault[i] = null;
        }
    }

    void enter_moat() {
        this.assault_started = false;
        this.deleteAssaultCycleScene();
        sc02040.removeScene();
        EventsControllerHelper.messageEventHappened("EnemyBaseAssault fell in moat");
    }

    void enter_nearbase() {
        this.was_near_base = true;
    }

    void enter_behindmotel() {
        event.finishScenarioMission(MISSION_MET_MONICA);
        EventsControllerHelper.messageEventHappened(MESSAGES[0]);
    }

    void enter_assaultteam() {
        EventsControllerHelper.messageEventHappened(MESSAGES[13]);
    }

    void enter_tracktunnel() {
        this.setTrackTunnel(false);
        this.offDword(DWORD_ENEMYBASE_TUNNEL);
        EventsControllerHelper.messageEventHappened(MESSAGES[10]);
    }

    void enter_assault() {
        this.cars_assault[1].UpdateCar();
        this.cars_assault[1].registerCar("JOHN");
        this.cars_assault[1].setCollideMode(false);
        this.cars_assault[2].UpdateCar();
        this.cars_assault[2].registerCar("DAKOTA");
        this.dakota = new aiplayer("SC_ONTANIELOLOW");
        this.dakota.setModelCreator(new ScenarioPersonPassanger(), null);
        this.dakota.beDriverOfCar(this.cars_assault[2]);
        eng.explosionsWhilePredefinedAnimation(true);
        EventsControllerHelper.messageEventHappened(MESSAGES[15]);
    }

    public void failed_2025() {
        for (int i = 0; i < this.cars_assault.length; ++i) {
            this.deactivateAssaultCar(i);
        }
        this.to_make_2020_call = new Timing_BadCall();
    }

    public void started_1600() {
        this.monica_call_quest_started();
    }

    public boolean isWasVehicleExchange() {
        return this.wasVehicleExchange;
    }

    public void setWasVehicleExchange(boolean wasVehicleExchange) {
        this.wasVehicleExchange = wasVehicleExchange;
    }

    public boolean isAssaultCycleSceneCreated() {
        return this.isAssaultCycleSceneCreated;
    }

    public void setAssaultCycleSceneCreated(boolean value) {
        this.isAssaultCycleSceneCreated = value;
        if (this.isAssaultCycleSceneCreated) {
            this.createAssaultCycleScene();
        }
    }

    public boolean isAssault_started() {
        return this.assault_started;
    }

    public void setAssault_started(boolean assault_started) {
        this.assault_started = assault_started;
    }

    public boolean isBad_conditions() {
        return this.bad_conditions;
    }

    public void setBad_conditions(boolean bad_conditions) {
        this.bad_conditions = bad_conditions;
    }

    public actorveh[] getCars() {
        return this.cars;
    }

    public void setCars(actorveh[] cars) {
        this.cars = cars;
    }

    public actorveh[] getCars_assault() {
        return this.cars_assault;
    }

    public void setCars_assault(actorveh[] cars_assault) {
        this.cars_assault = cars_assault;
    }

    public aiplayer getDakota() {
        return this.dakota;
    }

    public void setDakota(aiplayer dakota) {
        this.dakota = dakota;
    }

    public aiplayer getMonica() {
        return this.monica;
    }

    public void setMonica(aiplayer monica) {
        this.monica = monica;
    }

    public double getAssault_start_time() {
        return this.assault_start_time;
    }

    public void setAssault_start_time(double assault_start_time) {
        this.assault_start_time = assault_start_time;
    }

    public double getDakota_warning_start_time() {
        return this.dakota_warning_start_time;
    }

    public void setDakota_warning_start_time(double dakota_warning_start_time) {
        this.dakota_warning_start_time = dakota_warning_start_time;
    }

    public double getInitialVelocity() {
        return this.initialVelocity;
    }

    public void setInitialVelocity(double initialVelocity) {
        this.initialVelocity = initialVelocity;
    }

    public double getSlowDownAcceleration() {
        return this.slowDownAcceleration;
    }

    public void setSlowDownAcceleration(double slow_down_accel) {
        this.slowDownAcceleration = slow_down_accel;
    }

    public boolean isSlowdown_start() {
        return this.slowdown_start;
    }

    public void setSlowdown_start(boolean slowdown_start) {
        this.slowdown_start = slowdown_start;
    }

    public Timing_BadCall getTo_make_2020_call() {
        return this.to_make_2020_call;
    }

    public void setTo_make_2020_call(Timing_BadCall to_make_2020_call) {
        this.to_make_2020_call = to_make_2020_call;
    }

    public Timing_DakotaCall getTo_make_dakota_call() {
        return this.to_make_dakota_call;
    }

    public void setTo_make_dakota_call(Timing_DakotaCall to_make_dakota_call) {
        this.to_make_dakota_call = to_make_dakota_call;
    }

    public Timing_MonicaFindsOut getTo_make_discover_base() {
        return this.to_make_discover_base;
    }

    public void setTo_make_discover_base(Timing_MonicaFindsOut to_make_discover_base) {
        this.to_make_discover_base = to_make_discover_base;
    }

    public Timing_ThreatenCall getTo_make_threat_call() {
        return this.to_make_threat_call;
    }

    public void setTo_make_threat_call(Timing_ThreatenCall to_make_threat_call) {
        this.to_make_threat_call = to_make_threat_call;
    }

    public boolean isTo_slow_down_gepard() {
        return this.to_slow_down_gepard;
    }

    public void setTo_slow_down_gepard(boolean to_slow_down_gepard) {
        this.to_slow_down_gepard = to_slow_down_gepard;
    }

    public boolean isTo_track_tunnel() {
        return this.to_track_tunnel;
    }

    public void setTo_track_tunnel(boolean to_track_tunnel) {
        this.to_track_tunnel = to_track_tunnel;
    }

    public boolean isWant_dakota_warning() {
        return this.want_dakota_warning;
    }

    public void setWant_dakota_warning(boolean want_dakota_warning) {
        this.want_dakota_warning = want_dakota_warning;
    }

    public boolean isWant_dakota_warning_started() {
        return this.want_dakota_warning_started;
    }

    public void setWant_dakota_warning_started(boolean want_dakota_warning_started) {
        this.want_dakota_warning_started = want_dakota_warning_started;
    }

    public boolean isWas_near_base() {
        return this.was_near_base;
    }

    public void setWas_near_base(boolean was_near_base) {
        this.was_near_base = was_near_base;
    }

    public void activateDword(String dword) {
        if (null != dword && allDwords.contains(dword) && !this.currentActiveDwords.contains(dword)) {
            this.setDword(dword);
        }
    }

    public Collection<String> getCurrentDwords() {
        return Collections.unmodifiableSet(this.currentActiveDwords);
    }

    static {
        allDwords.add(DWORD_ENEMYBASE);
        allDwords.add(DWORD_ENEMYBASE);
        allDwords.add(DWORD_MONICA);
        allDwords.add(DWORD_ASSAULTTEAM);
        allDwords.add(DWORD_ENEMYBASE_TUNNEL);
        allDwords.add(DWORD_ENEMYBASE_ASSAULT);
        allDwords.add(DWORD_WIREROPE);
        CAR_NAMES = new CarName[]{CarName.CAR_BANDITS, CarName.CAR_BANDITS, CarName.CAR_MONICA};
        PARKINGPLACES = new int[]{5, 6, 7};
        CAR_NAMES_ASSAULT = new CarName[]{CarName.CAR_GEPARD, CarName.CAR_JOHN, CarName.CAR_DAKOTA};
        PARKINGPLACES_ASSAULT = new int[]{4, 5, 7};
        moatTriggerId = 0;
        gENEMYBASE = null;
    }

    public static class Timing_BadCall
    implements IInterruptCall {
        boolean finished = false;
        CoreTime finishTime = ConvertGameTime.convertFromCurrent(1800);

        public Timing_BadCall() {
            CBVideoInterruptCalls.add(this);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();
            if (!this.finished && current_time.moreThan(this.finishTime) >= 0) {
                EventsControllerHelper.messageEventHappened(MESSAGES[27]);
                CBVideoInterruptCalls.remove(this);
                this.finished = true;
                return true;
            }
            return false;
        }

        public CoreTime getInterruptTime() {
            return this.finishTime;
        }

        public void setInterruptTime(CoreTime value) {
            this.finishTime = value;
        }
    }

    public static class Timing_DakotaCall
    implements IInterruptCall {
        boolean finished = false;
        CoreTime finishTime = ConvertGameTime.convertFromCurrent(600);

        public Timing_DakotaCall() {
            CBVideoInterruptCalls.add(this);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();
            if (!this.finished && 0 <= current_time.moreThan(this.finishTime)) {
                EventsControllerHelper.messageEventHappened(MESSAGES[11]);
                CBVideoInterruptCalls.remove(this);
                this.finished = true;
                return true;
            }
            return false;
        }

        public CoreTime getInterruptTime() {
            return this.finishTime;
        }

        public void setInterruptTime(CoreTime value) {
            this.finishTime = value;
        }
    }

    public static class Timing_ThreatenCall {
        private static final double MOURNING_TIME = 6.0;
        boolean finished = false;
        CoreTime finishTime = new CoreTime();

        private int getRandomHour() {
            return AdvancedRandom.RandFromInreval(12, 16);
        }

        public Timing_ThreatenCall() {
            int year = this.finishTime.gYear();
            int month = this.finishTime.gMonth();
            int date = this.finishTime.gDate();
            int hour = this.finishTime.gHour();
            if ((double)hour < 6.0) {
                this.finishTime = new CoreTime(year, month, date, this.getRandomHour(), 0);
            } else {
                this.finishTime = new CoreTime(year, month, date, this.getRandomHour(), 0);
                this.finishTime.plus_days(1);
            }
        }

        boolean run() {
            CoreTime current_time = new CoreTime();
            if (!this.finished && current_time.moreThan(this.finishTime) >= 0) {
                EventsControllerHelper.messageEventHappened(MESSAGES[7]);
                this.finished = true;
                return true;
            }
            return false;
        }

        public CoreTime getFinishTime() {
            return this.finishTime;
        }

        public void setFinishTime(CoreTime finishTime) {
            this.finishTime = finishTime;
        }
    }

    public static class Timing_MonicaFindsOut
    implements IInterruptCall {
        boolean finished = false;
        CoreTime finishTime = ConvertGameTime.convertFromCurrent(900);

        public Timing_MonicaFindsOut() {
            CBVideoInterruptCalls.add(this);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();
            if (!this.finished && current_time.moreThan(this.finishTime) >= 0) {
                EventsControllerHelper.messageEventHappened(MESSAGES[5]);
                this.finished = true;
                CBVideoInterruptCalls.remove(this);
                return true;
            }
            return false;
        }

        public CoreTime getInterruptTime() {
            return this.finishTime;
        }

        public void setInterruptTime(CoreTime value) {
            this.finishTime = value;
        }
    }

    static class Data {
        matrixJ M;
        vectorJ P;

        Data() {
        }

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }
}

