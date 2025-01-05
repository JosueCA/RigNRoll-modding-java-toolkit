/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import java.util.Vector;
import java.util.logging.Level;
import menu.menues;
import players.CarName;
import players.Chase;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import players.semitrailer;
import rnrcore.ChaseTopoObjectXmlSerializable;
import rnrcore.Collide;
import rnrcore.IXMLSerializable;
import rnrcore.Log;
import rnrcore.ObjectXmlSerializable;
import rnrcore.ScriptRef;
import rnrcore.Sphere;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrloggers.ScriptsLogger;
import rnrscenario.Helper;
import rnrscenario.ReachPosition;
import rnrscenario.ScenarioSave;
import rnrscenario.SimplePresets;
import rnrscenario.SpaceEvent;
import rnrscenario.SpaceEventGeometry;
import rnrscenario.SpaceEventListener;
import rnrscenario.configurators.SpecialCargoConfig;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.scenes.sc00860;
import rnrscenario.sctask;
import rnrscr.cSpecObjects;
import rnrscr.drvscripts;
import rnrscr.parkingplace;
import rnrscr.specobjects;
import scriptEvents.EventsControllerHelper;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@ScenarioClass(scenarioStage=9)
public class chaseTopo
extends sctask
implements anm,
ScenarioController {
    public static final String STOP_THIS_SCENARIO_BRANCH_MESSAGE = "finish special cargo and scenario";
    private ScenarioHost host;
    private static final String SPECIAL_CARGO_DELIVERY_MISSION_NAME = "sc00610";
    private static final String MISSION_NAME = "sc00610";
    private static final int COCH_CALL_LOCATION_SIZE = 50;
    private static final double BANDITS_VELOCITY_AFTER_CATCHING_PLAYER = 5.0;
    private static final String MAKEBANDITS_CALL = "Topo chase bandits call";
    private static final String MAKEKOH_CALL = "Topo chase Koh call";
    private static final String MAKEDAKOTA_CALL = "Topo chase Dakota call";
    private static final String ANIMATE_DAKOTA = "Topo chase animate Dakota";
    private static final String MAKECUSTOMER_CALL = "Topo chase customer call 1";
    private static final String ANIMATE_PUNKTA = "Topo chase animate punkt a";
    private static final String MAKECUSTOMER2_CALL = "Topo chase customer call 2";
    private static final String MESSAGE_PLAYER_ON_TRUCKSTOP = "Topo chase player on truckstop";
    private static final String ANIMATE_DARKTRUCK = "Topo chase animate dark truck";
    private static final String MESSAGE_PICKUP = "Topo chase pickup";
    private static final String MESSAGE_STARTCHSE_NO_TRAILER = "Topo chase enter chase notrailer";
    private static final String ANIMATE_BLOWCAR = "Topo chase bad end";
    public static final String POINT_PUNKT_A = "KeyPoint_750";
    private static final String COCH_CALL_POSITION = "CochKeyPoint_OV_LB";
    private static final String BANDINT_APPEARANCE_POINT = "BanditsAppearKeyPoint_OV_LB";
    private static final String DARK_TRUCK_CROSSROAD = "KeyPoint_860";
    private static final String DAKOTA_CROSSROAD = "KeyPoint_730";
    private static final String POINT_BAD_EXIT_LA = "CP_badexit_LA";
    private static final String POINT_101_126 = "CP_126_start";
    private static final String POINT_EXIT5 = "CP_exit5_1";
    public static final String controlPointName1 = "CP_126_5";
    public static final String controlPointName2 = "CP_LB_TS";
    public static final String controlPointName3 = "CP_exit5_bridge";
    public static final String controlPointName4 = "CP_meet_on_14";
    public static final double TRAILER_PICKUP_DISTANCE = 200.0;
    private static final int eventid_enter126 = 20611;
    private static final int eventid_dakotacall = 20691;
    private static final int eventid_dakota = 20730;
    private static final int eventid_cargocall1 = 20732;
    private static final int eventid_meetfriends1 = 20750;
    private static final int eventid_darktruck = 20860;
    private static final int eventid_TRUCKSTOP = 20830;
    private static final int eventid_TRUCKSTOP_CUSTOMER_ROOM_SB = 20805;
    private static final int eventid_TRUCKSTOP_CUSTOMER_ROOM_LB = 20806;
    private static final int Edefault = -1;
    private static final int Echase_before_dakota = 0;
    private static final int Eanimate_dakota = 9;
    private static final int Eafterdakota_befor_punktA = 10;
    private static final int Eanimate_punktA = 19;
    private static final int Eafter_punktA = 20;
    private static final int Eanimate_punktB = 39;
    private static final int Eafter_punktB = 40;
    private static final int Eafter_secondCustomerCall = 41;
    private static final int Eanimate_darktruck = 42;
    private static final int Eblow = 60;
    private static final int Ebankrupt = 100;
    private int charged_eventid_enter126 = 0;
    private int charged_eventid_dakota = 0;
    private int charged_eventid_meetfriends1 = 0;
    private int charged_eventid_darktruck = 0;
    private int charged_eventid_cargocall1 = 0;
    private int charged_eventid_dakotacall = 0;
    private int charged_eventid_TRUCKSTOP = 0;
    private int charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_SB = 0;
    private int charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_LB = 0;
    private static final int use_nothing = 1;
    private static final int use_badexitLA = 2;
    private static final int use_goodpoint1 = 4;
    private final double DakoTaCallDistance = 1000.0;
    private String cachedTrailerModelName = null;
    private int value_goodposition = 1;
    private parkingplace parking_punktB = null;
    private actorveh ourcar;
    private shooting shootingController = new shooting();
    private int CURstate = -1;
    private boolean finishcallKohrein = false;
    private boolean startcallKohrein = false;
    private boolean to_prepatreDakota = false;
    private vectorJ cochCallLocation = null;
    private boolean deinitDakota_done = false;
    private boolean to_prepare_punktA = true;
    private boolean to_prepatreDarkTruck = true;
    private boolean was_in_chaseagain0 = false;
    private boolean rightorder_on_bridgescene = true;
    private boolean tostop = false;
    private boolean to_stop = false;
    private boolean alreadyblown = false;
    private boolean move_to_badscenario = false;
    private boolean move_to_chase = false;
    private int count_timeSilentQuest = 0;
    private Vector<Stalker> stalkers = new Vector();
    private double m_stalkerInitialVelocity = 5.0;
    private boolean waitCusomerCallEvent = false;
    private boolean m_animateBridge = false;
    private double animateBridgeTime = -1.0;
    private double animateBridgeStartTime = -1.0;
    private static final double contestEventRadius = 50.0;
    private static final vectorJ contestPosition = new vectorJ(10277.0, -23351.0, 49.0);
    private boolean contestProceeding = false;
    private boolean m_needCustoremCall1 = false;
    private TrajectoryRadiusMeter m_radiusAccelerationMeter = new TrajectoryRadiusMeter();
    private final ScriptRef uid = new ScriptRef();
    private final ObjectXmlSerializable serializator;

    private void disableGeometryTriggers() {
        this.disable_enter126();
        this.disable_dakota();
        this.disable_meetfriends1();
        this.disable_darktruck();
        this.disable_cargocall1();
        this.disable_dakotacall();
        this.disableTruckstopTrigger();
        this.disableSecondCustomerTriggers();
    }

    private void disableTruckstopTrigger() {
        event.removeEventObject(this.charged_eventid_TRUCKSTOP);
        this.charged_eventid_TRUCKSTOP = 0;
    }

    private void disableSecondCustomerTriggers() {
        event.removeEventObject(this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_SB);
        event.removeEventObject(this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_LB);
        this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_SB = 0;
        this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_LB = 0;
    }

    // @Override
    public void gameDeinitLaunched() {
        if (!this.f_finished) {
            if (null != ScenarioSave.getInstance()) {
                ScenarioSave.getInstance().setChaseTopo(null);
            }
            this.finish();
            this.finishImmediately();
            this.disableGeometryTriggers();
        }
    }

    // @Override
    public void setUid(int value) {
        this.uid.setUid(value);
    }

    // @Override
    public int getUid() {
        return this.uid.getUid(this);
    }

    // @Override
    public void updateNative(int p) {
    }

    // @Override
    public void removeRef() {
        this.uid.removeRef(this);
    }

    public chaseTopo(ScenarioHost scenarioHost, SpecialCargoConfig config) {
        super(3, true);
        assert (null != scenarioHost && null != config);
        eng.CreateInfinitScriptAnimation(this);
        this.serializator = new ChaseTopoObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
        this.shootingController.blowRadius = config.getBlowRadius();
        this.host = scenarioHost;
        this.host.registerController(this);
        this.enableQuestTriggers();
        this.ourcar = Crew.getIgrokCar();
        this.start();
        EventsControllerHelper.getInstance().addMessageListener(this, "gameDeinitLaunched", STOP_THIS_SCENARIO_BRANCH_MESSAGE);
    }

    // @Override
    public boolean animaterun(double dt) {
        return this.to_stop || this.chasecycle(dt);
    }

    @Override
    public void run() {
        switch (this.CURstate) {
            case 0: {
                this.PrepareDakota();
                break;
            }
            case 10: {
                this.preparePunktA();
                this.count_timeSilentQuest += 3;
                break;
            }
            case 40: 
            case 41: {
                this.prepareDarkTruck();
                this.count_timeSilentQuest += 3;
                break;
            }
            case 20: 
            case 100: {
                this.count_timeSilentQuest += 3;
                break;
            }
            case 60: {
                if (!menues.cancreate_messagewindow()) break;
                this.BlowDaCar();
            }
        }
        if (this.CURstate == 100) {
            eng.pager("bankrupt");
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.host.unregisterController(this);
        ScenarioSave.getInstance().setChaseTopo(null);
        EventsControllerHelper.getInstance().removeMessageListener(this, "gameDeinitLaunched", STOP_THIS_SCENARIO_BRANCH_MESSAGE);
        if (null != this.serializator) {
            this.serializator.unRegisterObjectXmlSerializable();
        }
        Crew.deactivateMappedCar("DARK TRUCK");
        Crew.deactivateMappedCar("DOROTHY");
        Crew.deactivateMappedCar("KOH");
        Crew.deactivateMappedCar("DAKOTA");
        this.deleteStalkers();
        this.to_stop = true;
    }

    private void stopStalkers() {
        Log.simpleMessage("chaseTopo. StalkersStopped.");
        for (int i = 0; i < this.stalkers.size(); ++i) {
            Stalker st = this.stalkers.elementAt(i);
            st.player.leave_target();
        }
    }

    private void createBandits() {
        PareGoodPsition location;
        cSpecObjects banditsPlacement = specobjects.getInstance().GetLoadedNamedScenarioObject(BANDINT_APPEARANCE_POINT);
        if (null != banditsPlacement) {
            location = new PareGoodPsition();
            location.M = banditsPlacement.matrix;
            location.V = banditsPlacement.position;
        } else {
            location = new PareGoodPsition();
            location.V = eng.getControlPointPosition(POINT_101_126);
            vectorJ vectorJ2 = new vectorJ(2333.0, -22707.0, 10.0);
            if (location.V.len2(vectorJ2) > 10000.0) {
                location.V = new vectorJ(2333.0, -22707.0, 10.0);
            }
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, "failed to find AO_ScriptObject: BanditsAppearKeyPoint_OV_LB");
        }
        actorveh car = eng.CreateCarForScenario(CarName.CAR_BANDITS, location.M, location.V);
        car.showOnMap(true);
        Helper.makePowerEngine(car);
        car.sVeclocity(this.m_stalkerInitialVelocity);
        Crew.addMappedCar("ARGOSY BANDIT", car);
        aiplayer ai = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        ai.beDriverOfCar(car);
        Stalker stalker = new Stalker(car);
        this.stalkers.add(stalker);
        Chase chase = new Chase();
        chase.setParameters("easyChasing");
        chase.makechase(car, this.ourcar);
        this.typicalStalkerDrivers(car);
    }

    private PareGoodPsition makeFGoodPosition() {
        PareGoodPsition GP = null;
        switch (this.value_goodposition) {
            case 1: {
                vectorJ pos = this.ourcar.gDir();
                pos.mult(-100.0);
                GP = new PareGoodPsition();
                GP.M = this.ourcar.gMatrix();
                GP.V = pos.oPlusN(this.ourcar.gPosition());
                break;
            }
            case 2: {
                GP = new PareGoodPsition();
                GP.V = eng.getControlPointPosition(POINT_BAD_EXIT_LA);
                break;
            }
            case 4: {
                GP = new PareGoodPsition();
                GP.V = eng.getControlPointPosition(POINT_EXIT5);
            }
        }
        return GP;
    }

    private void makeContestStalkers(int nom, vectorJ positionToRaceTo) {
        for (int i = 0; i < nom; ++i) {
            PareGoodPsition GP = this.makeFGoodPosition();
            actorveh car = eng.CreateCarForScenario(CarName.CAR_BANDITS, GP.M, GP.V);
            car.sVeclocity(this.m_stalkerInitialVelocity);
            Crew.addMappedCar("ARGOSY BANDIT", car);
            aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
            pl.beDriverOfCar(car);
            this.typicalStalkerDrivers(car);
            car.autopilotTo(positionToRaceTo);
            Helper.makePowerEngine(car);
            this.stalkers.add(new Stalker(car));
            Chase chase = new Chase();
            chase.setParameters("easyChasing");
        }
    }

    private void typicalStalkerDrivers(actorveh car) {
        aiplayer Bandit2 = aiplayer.getSimpleAiplayer("SC_BANDITJOELOW");
        aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");
        Bandit2.bePassangerOfCar(car);
        GUN.bePassangerOfCar_simple_like(car, Bandit2.gModelname());
    }

    private boolean chasecycle(double dt) {
        this.m_radiusAccelerationMeter.setLevelOfDificulty(eng.getDifficultyLevel());
        this.m_radiusAccelerationMeter.animaterun(dt);
        this.animateChasing();
        return false;
    }

    private void animateChasing() {
        int oldstate = this.CURstate;
        switch (this.CURstate) {
            case -1: {
                if (!this.move_to_chase) break;
                this.CURstate = 0;
                break;
            }
            case 0: {
                this.callCochraine();
                this.CURstate = this.detectKilledByBandits();
                this.CURstate = this.detectTrailerLost();
            }
        }
        if (this.CURstate != oldstate) {
            this.enterState();
        }
    }

    private void enterState() {
        switch (this.CURstate) {
            case 0: {
                int new_state = this.changeStateIfLostTrailer();
                if (new_state != this.CURstate) {
                    this.CURstate = new_state;
                    this.enterState();
                    break;
                }
                this.shootingController.reset();
                this.createBandits();
                this.to_prepatreDakota = true;
                this.value_goodposition = 1;
                EventsControllerHelper.messageEventHappened(MAKEBANDITS_CALL);
                eng.pager("Echase_before_dakota");
                break;
            }
            case 9: {
                eng.pager("Eanimate_dakota");
                actorveh dakotacar = Crew.getMappedCar("DAKOTA");
                dakotacar.loadImmediateForPredefineAnimation();
                dakotacar.UpdateCar();
                dakotacar.registerCar("dakotacar");
                actorveh banditsCar = Crew.getMappedCar("ARGOSY BANDIT");
                banditsCar.loadImmediateForPredefineAnimation();
                banditsCar.UpdateCar();
                banditsCar.registerCar("banditcar");
                banditsCar.leave_target();
                EventsControllerHelper.messageEventHappened(ANIMATE_DAKOTA);
                break;
            }
            case 19: {
                eng.pager("Eanimate_punktA");
                EventsControllerHelper.messageEventHappened(ANIMATE_PUNKTA);
                break;
            }
            case 39: {
                eng.makePoliceImmunity(false);
                eng.pager("Eanimate_punktB");
                break;
            }
            case 20: {
                this.preparePunktB();
                this.count_timeSilentQuest = 0;
                break;
            }
            case 40: {
                this.count_timeSilentQuest = 0;
                break;
            }
            case 10: {
                this.deinitDakota();
                this.deleteStalkers();
                this.m_needCustoremCall1 = true;
                traffic.setTrafficMode(0);
                eng.makePoliceImmunity(false);
                this.count_timeSilentQuest = 0;
                eng.pager("Eafterdakota_befor_punktA or Eatferbridge_before_punktB");
                this.ourcar.stop_autopilot();
                break;
            }
            case 60: {
                actorveh banditsCar = Crew.getMappedCar("ARGOSY BANDIT");
                if (null != banditsCar) {
                    banditsCar.leave_target();
                    banditsCar.sVeclocity(5.0);
                }
                if (!menues.cancreate_messagewindow()) break;
                this.BlowDaCar();
                break;
            }
            case 100: {
                eng.pager("bankrupt");
                this.stopStalkers();
                this.disableQuestTriggers();
                break;
            }
            case 42: {
                eng.pager("Eanimate_darktruckB");
                this.to_stop = true;
                this.disableGeometryTriggers();
                EventsControllerHelper.messageEventHappened(ANIMATE_DARKTRUCK);
            }
        }
    }

    void callCochraine() {
        cSpecObjects cochCallPointInGameWorld;
        if (null == this.cochCallLocation && null != (cochCallPointInGameWorld = specobjects.getInstance().GetLoadedNamedScenarioObject(COCH_CALL_POSITION))) {
            this.cochCallLocation = cochCallPointInGameWorld.position;
        }
        if (this.finishcallKohrein || null == this.cochCallLocation) {
            return;
        }
        if (2500.0 < this.cochCallLocation.len2(this.ourcar.gPosition())) {
            EventsControllerHelper.messageEventHappened(MAKEKOH_CALL);
            this.finishcallKohrein = true;
        }
    }

    public void PrepareDakota() {
        if (!this.to_prepatreDakota) {
            return;
        }
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(DAKOTA_CROSSROAD);
        if (null != crossroad) {
            vectorJ posDakota = crossroad.position;
            matrixJ matDakota = crossroad.matrix;
            assert (null != matDakota);
            assert (null != posDakota);
            actorveh dakotacar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, matDakota, posDakota);
            aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");
            dakota.beDriverOfCar(dakotacar);
            Crew.addMappedCar("DAKOTA", dakotacar);
            this.charged_eventid_dakotacall = event.eventObject(20691, this, "enterEvent_dakotacall");
            this.to_prepatreDakota = false;
        }
    }

    public void deinitDakota() {
        if (this.deinitDakota_done) {
            return;
        }
        this.deinitDakota_done = true;
        actorveh dakotaCar = Crew.getMappedCar("DAKOTA");
        if (null != dakotaCar) {
            dakotaCar.deactivate();
        }
    }

    private void preparePunktA() {
        if (!this.to_prepare_punktA) {
            return;
        }
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(POINT_PUNKT_A);
        if (crossroad != null) {
            actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_COCH, crossroad.matrix, crossroad.position);
            vectorJ shift = new vectorJ(crossroad.matrix.v0);
            shift.mult(5.0);
            actorveh dorcar = eng.CreateCarForScenario(CarName.CAR_DOROTHY, crossroad.matrix, crossroad.position.oPlusN(shift));
            aiplayer kohrein = aiplayer.getScenarioAiplayer("SC_KOH");
            aiplayer dorothy = aiplayer.getScenarioAiplayer("SC_DOROTHY");
            kohrein.beDriverOfCar(kohcar);
            dorothy.beDriverOfCar(dorcar);
            this.to_prepare_punktA = false;
            Crew.addMappedCar("DOROTHY", dorcar);
            Crew.addMappedCar("KOH", kohcar);
        }
    }

    private Vector friendCars() {
        actorveh dorcar = Crew.getMappedCar("DOROTHY");
        actorveh kohcar = Crew.getMappedCar("KOH");
        if (null != dorcar && null != kohcar) {
            Vector<actorveh> v = new Vector<actorveh>();
            v.add(dorcar);
            v.add(kohcar);
            return v;
        }
        return null;
    }

    private void createFriendCars() {
        matrixJ m = new matrixJ();
        vectorJ p = eng.getControlPointPosition(controlPointName2);
        vectorJ shift = new vectorJ(m.v0);
        shift.mult(5.0);
        actorveh cochCar = eng.CreateCarForScenario(CarName.CAR_COCH, m, p);
        actorveh dorothyCar = eng.CreateCarForScenario(CarName.CAR_DOROTHY, m, p.oPlusN(shift));
        cochCar.setHandBreak(true);
        dorothyCar.setHandBreak(true);
        Crew.addMappedCar("DOROTHY", dorothyCar);
        Crew.addMappedCar("KOH", cochCar);
    }

    public static SimplePresets preparePunctBMatrix() {
        actorveh ourcar = Crew.getIgrokCar();
        matrixJ punktBM = ourcar.gMatrix();
        punktBM.mult(matrixJ.Mz(3.14159));
        vectorJ pos1 = ourcar.gPosition().oPlusN(new vectorJ(0.0, 0.0, 100.0));
        vectorJ pos2 = ourcar.gPosition().oPlusN(new vectorJ(0.0, 0.0, -100.0));
        vectorJ punktBV = Collide.collidePoint(pos1, pos2);
        if (null == punktBV) {
            punktBV = ourcar.gPositionSaddle();
        }
        return new SimplePresets(punktBV, punktBM);
    }

    public void parkOnPunktB() {
        this.preparePunktB();
        this.ourcar.makeParking(this.parking_punktB, 4);
        Vector cars = this.friendCars();
        if (null == cars || cars.size() != 2) {
            this.createFriendCars();
            cars = this.friendCars();
        }
        ((actorveh)cars.elementAt(0)).makeParkingAnimated(this.parking_punktB, 4.0, 6);
        ((actorveh)cars.elementAt(1)).makeParkingAnimated(this.parking_punktB, 5.0, 7);
    }

    private void preparePunktB() {
        if (null != this.parking_punktB) {
            return;
        }
        vectorJ pos = eng.getControlPointPosition(controlPointName2);
        if (pos != null) {
            this.parking_punktB = parkingplace.findParkingByName("PK_LA_LB_01", pos);
        }
    }

    public static SimplePresets prepareDarkTruckMatrix() {
        actorveh ourcar = Crew.getIgrokCar();
        matrixJ darkTruckM = ourcar.gMatrix();
        darkTruckM.mult(matrixJ.Mz(3.14159));
        vectorJ pos1 = ourcar.gPositionSaddle().oPlusN(new vectorJ(0.0, 0.0, 100.0));
        vectorJ pos2 = new vectorJ(pos1).oPlusN(new vectorJ(0.0, 0.0, -200.0));
        vectorJ darkTruckV = Collide.collidePoint(pos1, pos2);
        if (null == darkTruckV) {
            darkTruckV = ourcar.gPositionSaddle();
        }
        return new SimplePresets(darkTruckV, darkTruckM);
    }

    private void prepareDarkTruck() {
        if (!this.to_prepatreDarkTruck) {
            return;
        }
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        if (crossroad != null && crossroad.name.compareToIgnoreCase(DARK_TRUCK_CROSSROAD) == 0) {
            actorveh darktruck = eng.CreateCarForScenario(CarName.CAR_MATHEW_DEAD, crossroad.matrix, crossroad.position);
            Vector<actorveh> actors = new Vector<actorveh>();
            actors.add(darktruck);
            actorveh.aligncars(actors, crossroad.position, 15.0, 10.0, 1, 0);
            Crew.addMappedCar("DARK TRUCK", darktruck);
            this.charged_eventid_darktruck = event.eventObject(20860, this, "enterEvent_darktruck");
            this.to_prepatreDarkTruck = false;
        }
    }

    private int detectKilledByBandits() {
        Stalker st;
        boolean blowup = false;
        for (int i = 0; i < this.stalkers.size() && !(blowup = this.shootingController.shootNblow(st = this.stalkers.elementAt(i))); ++i) {
        }
        this.shootingController.unlock();
        if (blowup || this.shootingController.numbershotsSuceeded()) {
            return 60;
        }
        return this.CURstate;
    }

    private int changeStateIfLostTrailer() {
        semitrailer trailer = this.querryTrailer();
        if (null == trailer) {
            return 100;
        }
        if (this.ourcar.isTrailerAttachedBySaddle(trailer)) {
            return this.CURstate;
        }
        EventsControllerHelper.messageEventHappened(MESSAGE_STARTCHSE_NO_TRAILER);
        return 100;
    }

    private int detectTrailerLost() {
        semitrailer trailer = this.querryTrailer();
        if (null == trailer) {
            EventsControllerHelper.messageEventHappened(MESSAGE_PICKUP);
            return 100;
        }
        if (this.ourcar.isTrailerAttachedBySaddle(trailer)) {
            return this.CURstate;
        }
        vectorJ pos = trailer.getPosition();
        double distance = pos.len2(this.ourcar.gPosition());
        if (distance > 40000.0) {
            EventsControllerHelper.messageEventHappened(MESSAGE_PICKUP);
            return 100;
        }
        return this.CURstate;
    }

    private void disableQuestTriggers() {
        eng.setdword("DWORD_TopoQuest_Events", 0);
        eng.setdword("DWORD_TopoQuest_Bridge", 0);
        eng.looseresourse("DWORD_TopoQuest_Events");
        eng.looseresourse("DWORD_TopoQuest_Bridge");
        this.deinitDakota();
        this.disable_enter126();
        this.disable_dakota();
        this.disable_meetfriends1();
        this.disable_darktruck();
        this.disable_cargocall1();
        this.disable_dakotacall();
        this.disableTruckstopTrigger();
        this.disableSecondCustomerTriggers();
    }

    private void disable_dakotacall() {
        if (0 != this.charged_eventid_dakotacall) {
            event.removeEventObject(this.charged_eventid_dakotacall);
        }
        this.charged_eventid_dakotacall = 0;
    }

    private void disable_cargocall1() {
        if (0 != this.charged_eventid_cargocall1) {
            event.removeEventObject(this.charged_eventid_cargocall1);
        }
        this.charged_eventid_cargocall1 = 0;
    }

    private void disable_darktruck() {
        if (0 != this.charged_eventid_darktruck) {
            event.removeEventObject(this.charged_eventid_darktruck);
        }
        this.charged_eventid_darktruck = 0;
    }

    private void disable_meetfriends1() {
        if (0 != this.charged_eventid_meetfriends1) {
            event.removeEventObject(this.charged_eventid_meetfriends1);
        }
        this.charged_eventid_meetfriends1 = 0;
    }

    private void disable_dakota() {
        if (0 != this.charged_eventid_dakota) {
            event.removeEventObject(this.charged_eventid_dakota);
        }
        this.charged_eventid_dakota = 0;
    }

    private void disable_enter126() {
        if (0 != this.charged_eventid_enter126) {
            event.removeEventObject(this.charged_eventid_enter126);
        }
        this.charged_eventid_enter126 = 0;
    }

    private void enableQuestTriggers() {
        eng.setdword("DWORD_TopoQuest_Events", 1);
        this.charged_eventid_enter126 = event.eventObject(20611, this, "enterEvent_126");
        this.charged_eventid_dakota = event.eventObject(20730, this, "enterEvent_dakota");
        this.charged_eventid_cargocall1 = event.eventObject(20732, this, "enterEvent_cargocall1");
    }

    public void enterEvent_126() {
        eng.pager("enterEvent_126");
        this.move_to_chase = true;
        this.charged_eventid_enter126 = 0;
        cSpecObjects cochCallPointInGameWorld = specobjects.getInstance().GetLoadedNamedScenarioObject(COCH_CALL_POSITION);
        if (null != cochCallPointInGameWorld) {
            this.cochCallLocation = cochCallPointInGameWorld.position;
        } else {
            eng.writeLog("failed to find point CochKeyPoint_OV_LB");
        }
    }

    public void enterEvent_dakota() {
        eng.pager("enterEvent_dakota");
        this.CURstate = 9;
        this.charged_eventid_dakota = 0;
        this.enterState();
    }

    public void enterEvent_dakotacall() {
        eng.pager("enterEvent_dakotacall");
        this.charged_eventid_dakotacall = 0;
        EventsControllerHelper.messageEventHappened(MAKEDAKOTA_CALL);
    }

    public void exitAnimation_dakota() {
        eng.pager("exitAnimation_dakota");
        this.CURstate = 10;
        this.enterState();
    }

    public void triggerTruckstop() {
        this.charged_eventid_TRUCKSTOP = 0;
        EventsControllerHelper.messageEventHappened(MESSAGE_PLAYER_ON_TRUCKSTOP);
    }

    public void enterEvent_cargocall1() {
        eng.pager("enterEvent_cargocall1");
        this.charged_eventid_cargocall1 = 0;
        this.m_needCustoremCall1 = false;
        this.charged_eventid_meetfriends1 = event.eventObject(20750, this, "enterEvent_mettfriends1");
        this.charged_eventid_TRUCKSTOP = event.eventObject(20830, this, "triggerTruckstop");
        this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_SB = event.eventObject(20805, this, "trigger1SecondCustomerCall");
        this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_LB = event.eventObject(20806, this, "trigger2SecondCustomerCall");
        this.disable_enter126();
        this.disable_dakota();
        this.disable_dakotacall();
        this.CURstate = 10;
        EventsControllerHelper.messageEventHappened(MAKECUSTOMER_CALL);
    }

    public void trigger1SecondCustomerCall() {
        event.removeEventObject(this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_LB);
        this.secondCustomerCall();
    }

    public void trigger2SecondCustomerCall() {
        event.removeEventObject(this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_SB);
        this.secondCustomerCall();
    }

    private void secondCustomerCall() {
        eng.pager("enterEvent_cargocall2");
        this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_LB = 0;
        this.charged_eventid_TRUCKSTOP_CUSTOMER_ROOM_SB = 0;
        this.CURstate = 41;
        this.enterState();
        EventsControllerHelper.messageEventHappened(MAKECUSTOMER2_CALL);
    }

    public void enterEvent_mettfriends1() {
        eng.pager("enterEvent_mettfriends1");
        this.CURstate = 19;
        this.charged_eventid_meetfriends1 = 0;
        this.enterState();
    }

    public void createRaceBanditsToBridge() {
        this.value_goodposition = 4;
        this.deleteStalkers();
        vectorJ racePosition = eng.getControlPointPosition(controlPointName2);
        this.makeContestStalkers(1, racePosition);
        this.shootingController.reset();
        this.createHitEventListeners();
    }

    private void deleteStalkers() {
        Log.simpleMessage("chaseTopo. StalkersDeleted.");
        for (Stalker stalker : this.stalkers) {
            if (null == stalker) continue;
            stalker.delete();
        }
        this.stalkers.clear();
    }

    private void createHitEventListeners() {
        this.contestProceeding = true;
        new HitEventListener(this.ourcar, contestPosition);
        assert (this.stalkers != null && !this.stalkers.isEmpty() && this.stalkers.get(0) != null);
        new HitEventListener(this.stalkers.get((int)0).player, contestPosition);
    }

    public void enterEvent_goodexit5() {
        eng.pager("enterEvent_goodexit5");
        actorveh car1 = Crew.getMappedCar("DOROTHY");
        actorveh car2 = Crew.getMappedCar("KOH");
        car1.sVeclocity(30.0);
        car2.sVeclocity(30.0);
        Vector<actorveh> players = new Vector<actorveh>();
        players.add(car1);
        actorveh.autopilotOnTrajectory(players, "dorothytrajectory");
        players = new Vector();
        players.add(car2);
        actorveh.autopilotOnTrajectory(players, "kohtrajectory");
    }

    public void exitAnimation_punktA() {
        eng.pager("exitAnimation_punktA");
        actorveh dorothyCar = Crew.getMappedCar("DOROTHY");
        actorveh cochCar = Crew.getMappedCar("KOH");
        aiplayer.getScenarioAiplayer("SC_DOROTHY").abondoneCar(dorothyCar);
        aiplayer.getScenarioAiplayer("SC_KOH").abondoneCar(cochCar);
    }

    public void exitAnimation_punktB() {
        eng.pager("exitAnimation_punktB");
        this.CURstate = 40;
        this.enterState();
    }

    public void enterEvent_darktruck() {
        if (eng.dontQuestItemLost("sc00610")) {
            eng.pager("enterEvent_126");
            this.charged_eventid_darktruck = 0;
            this.CURstate = 42;
            sc00860.setSemitrailerToDeliver(this.ourcar.querryTrailer());
            this.enterState();
        } else {
            this.charged_eventid_darktruck = event.eventObject(20860, this, "enterEvent_darktruck");
        }
    }

    private void BlowDaCar() {
        if (this.alreadyblown) {
            return;
        }
        this.alreadyblown = true;
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
        EventsControllerHelper.messageEventHappened(ANIMATE_BLOWCAR);
    }

    private void blowpager() {
        eng.pager("Blow");
    }

    private semitrailer querryTrailer() {
        semitrailer trailer = semitrailer.querryMissionSemitrailer("sc00610");
        if (this.cachedTrailerModelName == null && trailer != null) {
            this.cachedTrailerModelName = trailer.querryModelName();
        }
        return trailer;
    }

    // @Override
    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    public boolean isWaitCustomerFinisnCall2Event() {
        return this.waitCusomerCallEvent;
    }

    public int getCURstate() {
        return this.CURstate;
    }

    public void setCURstate(int rstate) {
        this.CURstate = rstate;
        if (this.CURstate >= 0) {
            this.disable_enter126();
        }
        if (this.CURstate >= 9) {
            this.disable_dakota();
        }
        if (this.CURstate >= 19) {
            this.disable_meetfriends1();
        }
        if (this.CURstate >= 42) {
            eng.setdword("DWORD_TopoQuest_Events", 0);
            eng.setdword("DWORD_TopoQuest_Bridge", 0);
            eng.looseresourse("DWORD_TopoQuest_Events");
            eng.looseresourse("DWORD_TopoQuest_Bridge");
            this.disable_darktruck();
        }
        if (this.CURstate >= 100) {
            this.disableQuestTriggers();
        }
        if (this.CURstate == 0 && !this.to_prepatreDakota) {
            cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(DAKOTA_CROSSROAD);
            if (null != crossroad) {
                new ReachPosition(20691, crossroad.position, this.ourcar, 1000.0);
                this.charged_eventid_dakotacall = event.eventObject(20691, this, "enterEvent_dakotacall");
            } else {
                eng.err("failed to find special object named KeyPoint_730 while loading chaseTopo");
            }
        }
        if (this.CURstate == 40 && !this.to_prepatreDarkTruck) {
            this.charged_eventid_darktruck = event.eventObject(20860, this, "enterEvent_darktruck");
        }
    }

    public boolean isFinishcallKohrein() {
        return this.finishcallKohrein;
    }

    public void setFinishcallKohrein(boolean finishcallKohrein) {
        this.finishcallKohrein = finishcallKohrein;
    }

    public boolean isStartcallKohrein() {
        return this.startcallKohrein;
    }

    public void setStartcallKohrein(boolean startcallKohrein) {
        this.startcallKohrein = startcallKohrein;
    }

    public boolean isTo_prepare_punktA() {
        return this.to_prepare_punktA;
    }

    public void setTo_prepare_punktA(boolean to_prepare_punktA) {
        this.to_prepare_punktA = to_prepare_punktA;
    }

    public boolean isTo_prepatreDakota() {
        return this.to_prepatreDakota;
    }

    public void setTo_prepatreDakota(boolean to_prepatreDakota) {
        this.to_prepatreDakota = to_prepatreDakota;
    }

    public boolean isTo_prepatreDarkTruck() {
        return this.to_prepatreDarkTruck;
    }

    public void setTo_prepatreDarkTruck(boolean to_prepatreDarkTruck) {
        this.to_prepatreDarkTruck = to_prepatreDarkTruck;
    }

    public boolean isTostop() {
        return this.tostop;
    }

    public void setTostop(boolean tostop) {
        this.tostop = tostop;
    }

    public boolean isWas_in_chaseagain0() {
        return this.was_in_chaseagain0;
    }

    public void setWas_in_chaseagain0(boolean was_in_chaseagain0) {
        this.was_in_chaseagain0 = was_in_chaseagain0;
    }

    public boolean isAlreadyblown() {
        return this.alreadyblown;
    }

    public void setAlreadyblown(boolean alreadyblown) {
        this.alreadyblown = alreadyblown;
    }

    public int getCount_timeSilentQuest() {
        return this.count_timeSilentQuest;
    }

    public void setCount_timeSilentQuest(int count_timeSilentQuest) {
        this.count_timeSilentQuest = count_timeSilentQuest;
    }

    public matrixJ getMatDakota() {
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(DAKOTA_CROSSROAD);
        matrixJ matDakota = null;
        if (crossroad != null && crossroad.name.compareToIgnoreCase(DAKOTA_CROSSROAD) == 0) {
            matDakota = crossroad.matrix;
            assert (matDakota != null);
        }
        return matDakota;
    }

    public boolean isMove_to_badscenario() {
        return this.move_to_badscenario;
    }

    public void setMove_to_badscenario(boolean move_to_badscenario) {
        this.move_to_badscenario = move_to_badscenario;
    }

    public boolean isMove_to_chase() {
        return this.move_to_chase;
    }

    public void setMove_to_chase(boolean move_to_chase) {
        this.move_to_chase = move_to_chase;
    }

    public vectorJ getPosDakota() {
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(DAKOTA_CROSSROAD);
        vectorJ posDakota = null;
        if (crossroad != null && crossroad.name.compareToIgnoreCase(DAKOTA_CROSSROAD) == 0) {
            posDakota = crossroad.position;
            assert (posDakota != null);
        }
        return posDakota;
    }

    public boolean isRightorder_on_bridgescene() {
        return this.rightorder_on_bridgescene;
    }

    public void setRightorder_on_bridgescene(boolean rightorder_on_bridgescene) {
        this.rightorder_on_bridgescene = rightorder_on_bridgescene;
    }

    public Vector<Stalker> getStalkers() {
        return this.stalkers;
    }

    public void setStalkers(Vector<Stalker> stalkers) {
        this.stalkers = stalkers;
    }

    public boolean isTo_stop() {
        return this.to_stop;
    }

    public void setTo_stop(boolean to_stop) {
        this.to_stop = to_stop;
    }

    public boolean isDeinitDakota_done() {
        return this.deinitDakota_done;
    }

    public void setDeinitDakota_done(boolean deinitDakota_done) {
        this.deinitDakota_done = deinitDakota_done;
    }

    public boolean isM_animateBridge() {
        return this.m_animateBridge;
    }

    public void setM_animateBridge(boolean bridge) {
        this.m_animateBridge = bridge;
    }

    public double getAnimateBridgeTime() {
        return this.animateBridgeTime;
    }

    public void setAnimateBridgeTime(double animateBridgeTime) {
        this.animateBridgeTime = animateBridgeTime;
    }

    public double getAnimateBridgeStartTime() {
        return this.animateBridgeStartTime;
    }

    public void setAnimateBridgeStartTime(double animateBridgeStartTime) {
        this.animateBridgeStartTime = animateBridgeStartTime;
    }

    public boolean isContestProceeding() {
        return this.contestProceeding;
    }

    public void setContestProceeding(boolean contestProceeding) {
        if (contestProceeding) {
            this.createHitEventListeners();
        }
    }

    public boolean isM_needCustoremCall1() {
        return this.m_needCustoremCall1;
    }

    public void setM_needCustoremCall1(boolean custoremCall1) {
        this.m_needCustoremCall1 = custoremCall1;
        if (this.CURstate == 10 && this.m_needCustoremCall1 && !this.to_prepare_punktA) {
            cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(POINT_PUNKT_A);
            new ReachPosition(20732, crossroad.position, Crew.getIgrokCar(), 500.0);
            this.charged_eventid_cargocall1 = event.eventObject(20732, this, "enterEvent_cargocall1");
        }
    }

    private final class shooting {
        private int countSuccesfullShotsInOwrCar = 0;
        private final int SuccesfullShotsInOwrCarLIMIT = 100;
        private final double lenlimit = 50.0;
        private double blowRadius = 8.0;
        private vectorJ lastIgrokPos = null;
        private vectorJ lastIgrokPos2 = null;
        private double playerDistance2 = 0.0;
        private vectorJ banditsPositionOnPreviousFrame;
        private final Sphere playerBoundingSphere = new Sphere(0.0, 0.0, 0.0, this.blowRadius);
        boolean locked = false;

        private shooting() {
        }

        void reset() {
            this.countSuccesfullShotsInOwrCar = 0;
            this.lastIgrokPos = null;
            this.lastIgrokPos2 = null;
            this.playerDistance2 = 0.0;
        }

        boolean numbershotsSuceeded() {
            return 100 <= this.countSuccesfullShotsInOwrCar;
        }

        void lock() {
            this.locked = true;
        }

        void unlock() {
            this.locked = false;
        }

        boolean shootNblow(Stalker Bandit) {
            if (0 == Bandit.player.getCar()) {
                return false;
            }
            vectorJ banditsPositionOnThisFrame = Bandit.player.gPosition();
            vectorJ playerPosition = chaseTopo.this.ourcar.gPosition();
            this.playerDistance2 = banditsPositionOnThisFrame.len2(playerPosition);
            boolean blowup = false;
            if (this.playerDistance2 < 2500.0) {
                if (null == this.banditsPositionOnPreviousFrame) {
                    this.playerBoundingSphere.setRadius(this.blowRadius);
                    if (this.playerDistance2 < this.blowRadius * this.blowRadius) {
                        blowup = true;
                    }
                } else {
                    this.playerBoundingSphere.setCenter(playerPosition.x, playerPosition.y, playerPosition.z);
                    if (this.playerBoundingSphere.intersecs(this.banditsPositionOnPreviousFrame, banditsPositionOnThisFrame)) {
                        blowup = true;
                    }
                }
                this.banditsPositionOnPreviousFrame = banditsPositionOnThisFrame;
                if (!blowup && Bandit.getLastpos() != null) {
                    vectorJ fromBandit2Igrok;
                    vectorJ moveBandit = banditsPositionOnThisFrame.oMinusN(Bandit.getLastpos());
                    blowup = moveBandit.dot(fromBandit2Igrok = playerPosition.oMinusN(banditsPositionOnThisFrame)) < 0.0;
                }
                Bandit.setLastpos(banditsPositionOnThisFrame);
                if (!chaseTopo.this.m_radiusAccelerationMeter.getRadiusCheck()) {
                    ++this.countSuccesfullShotsInOwrCar;
                    eng.pager("Hit count " + this.countSuccesfullShotsInOwrCar + "//" + 100);
                }
            }
            Bandit.setLastpos(banditsPositionOnThisFrame);
            if (!this.locked) {
                this.lock();
                this.lastIgrokPos2 = this.lastIgrokPos;
                this.lastIgrokPos = playerPosition;
            }
            if (blowup) {
                chaseTopo.this.blowpager();
            }
            return blowup;
        }

        boolean snipers(Stalker Bandit) {
            vectorJ pos2;
            vectorJ pos1;
            assert (Bandit != null);
            assert (Bandit.player != null);
            Bandit.player.UpdateCar();
            int barditCar = Bandit.player.getCar();
            if (barditCar == 0) {
                return false;
            }
            assert (0 != barditCar);
            long bandirDriver = eng.GetVehicleDriver(barditCar);
            long ourDriver = eng.GetVehicleDriver(chaseTopo.this.ourcar.getCar());
            if (bandirDriver != 0L && ourDriver != 0L) {
                assert (0L != bandirDriver);
                pos1 = eng.GetVehicle_steeringwheel_pos(bandirDriver);
                assert (pos1 != null);
                pos2 = eng.GetVehicle_steeringwheel_pos(ourDriver);
                assert (pos2 != null);
            } else {
                pos1 = Bandit.player.gPosition();
                pos2 = chaseTopo.this.ourcar.gPosition();
            }
            this.playerDistance2 = pos1.len2(pos2);
            return this.playerDistance2 < 2500.0;
        }

        double getRadiusAcceleration(vectorJ pos2) {
            double res = 10.0;
            if (this.lastIgrokPos2 == null || this.lastIgrokPos == null) {
                return res;
            }
            vectorJ resV = new vectorJ(pos2);
            resV.oPlus(this.lastIgrokPos2);
            resV.oMinus(new vectorJ(this.lastIgrokPos.x * 2.0, this.lastIgrokPos.y * 2.0, this.lastIgrokPos.z * 2.0));
            res = resV.length() / 18.0;
            return res;
        }
    }

    public static class PareGoodPsition {
        public matrixJ M = new matrixJ();
        public vectorJ V = new vectorJ();
    }

    public static final class Stalker {
        actorveh player = null;
        vectorJ lastpos = null;

        Stalker(actorveh player) {
            this.player = player;
        }

        public Stalker(actorveh player, vectorJ pos) {
            this.player = player;
            this.lastpos = pos;
        }

        void delete() {
            this.player.deactivate();
            this.player = null;
        }

        public vectorJ getLastpos() {
            return this.lastpos;
        }

        public void setLastpos(vectorJ lastpos) {
            this.lastpos = lastpos;
        }

        public actorveh getPlayer() {
            return this.player;
        }
    }

    private static final class HitEventListener
    implements SpaceEventListener {
        private SpaceEvent m_event;
        private boolean m_onEvent = false;

        HitEventListener(actorveh car, vectorJ position) {
            this.m_event = new SpaceEvent(new SpaceEventGeometry(position, 50.0), car, this);
        }

        public void hitevent() {
            this.m_onEvent = true;
            this.m_event.finish();
        }

        public boolean onEvent() {
            return this.m_onEvent;
        }
    }

    private static final class TrajectoryRadiusMeter {
        private vectorJ[] m_positionHistory = new vectorJ[]{null, null, null};
        private vectorJ m_radiusHistory = null;
        private boolean m_canCheckRadiusChangeState = true;
        private boolean m_currentRadiusChangeState = true;
        private boolean m_radiusChanged = true;
        private double m_lastTime = 0.0;
        private double m_frameLength = 0.0;
        private double m_countShiftTime = 0.0;
        private double m_countRotateRadius = 0.0;
        private final double s_timeToShift = 0.1;
        private double s_timeToRotateRadius = 1.0;

        private TrajectoryRadiusMeter() {
        }

        public void setLevelOfDificulty(int value) {
            switch (value) {
                case 0: {
                    this.s_timeToRotateRadius = 1.2;
                }
                case 1: {
                    this.s_timeToRotateRadius = 1.0;
                }
                case 2: {
                    this.s_timeToRotateRadius = 0.5;
                }
            }
        }

        private void shiftPositionHistiry(vectorJ pos) {
            this.m_positionHistory[2] = this.m_positionHistory[1];
            this.m_positionHistory[1] = this.m_positionHistory[0];
            this.m_positionHistory[0] = pos;
        }

        private void rememberPositions() {
            this.m_countShiftTime += this.m_frameLength;
            if (this.m_countShiftTime < 0.1) {
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
            return false;
        }
    }
}

