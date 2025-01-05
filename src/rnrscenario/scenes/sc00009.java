/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.CarName;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import players.vehicle;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscr.parkingplace;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=0)
public final class sc00009
extends stage {
    private static final String SCENES_PROCEED = "Begining Scene phase1";
    private static final String SKIP_SCENE = "Skip intro";
    private static final double ROOM_SIZE = 500.0;
    private static final String SCENE_POINT = "intro_scene";
    public static final vectorJ officePos = new vectorJ(4831.0, -24135.0, 0.0);
    private vectorJ START_POSITION = new vectorJ(4131.0, -24059.0, 0.0);
    private actorveh playersCar = null;
    private vehicle carlast = null;
    private vehicle carnew = null;
    vectorJ positionLastCar = null;
    matrixJ matrixLastCar = null;
    private matrixJ M = new matrixJ();

    public sc00009(Object monitor) {
        super(monitor, "sc00009");
    }

    private void makecar() {
        eng.lock();
        eng.disableControl();
        this.playersCar = Crew.getIgrokCar();
        boolean toMakeParking = true;
        if (500.0 > this.playersCar.gPosition().oMinusN(officePos).length()) {
            this.playersCar.makeParking(parkingplace.findParkingByName("OxnardParking04", officePos), 0);
        } else {
            toMakeParking = false;
        }
        eng.unlock();
        if (toMakeParking) {
            rnrscenario.tech.Helper.waitParked(this.playersCar);
        }
        eng.lock();
        traffic.enterCutSceneModeImmediately();
        this.positionLastCar = this.playersCar.gPosition();
        this.matrixLastCar = this.playersCar.gMatrix();
        Crew.getIgrok().abondoneCar(this.playersCar);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_MATHEW, new matrixJ(), this.START_POSITION);
        this.carnew = car.takeoff_currentcar();
        this.carlast = this.playersCar.querryCurrentCar();
        this.M.v0 = new vectorJ(0.0, -1.0, 0.0);
        this.M.v1 = new vectorJ(1.0, 0.0, 0.0);
        vehicle.changeLiveVehicle(this.playersCar, this.carnew, this.M, this.START_POSITION);
        car.deactivate();
        eng.unlock();
        rnrscenario.tech.Helper.waitVehicleChanged();
        eng.lock();
        Crew.getIgrokCar().UpdateCar();
        Crew.getIgrokCar().registerCar("matcar");
        aiplayer mathew = aiplayer.getScenarioAiplayer("SC_MATTHEW");
        mathew.beDriverOfCar(this.playersCar);
        Crew.getIgrok().bePassangerOfCar(this.playersCar);
        this.playersCar.switchOffEngine();
        eng.unlock();
    }

    private void afterScene() {
        eng.lock();
        traffic.setTrafficMode(0);
        Crew.getIgrok().abondoneCar(this.playersCar);
        aiplayer mathew = aiplayer.getScenarioAiplayer("SC_MATTHEW");
        mathew.abondoneCar(this.playersCar);
        vehicle.changeLiveVehicle(this.playersCar, this.carlast, this.matrixLastCar, this.positionLastCar);
        Crew.getIgrok().beDriverOfCar(this.playersCar);
        EventsControllerHelper.messageEventHappened(SCENES_PROCEED);
        eng.unlock();
        rnrscenario.tech.Helper.waitVehicleChanged();
        eng.lock();
        this.carnew.delete();
        eng.unlock();
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        if (eng.introPlayingFirstTime()) {
            eng.switchOffIntro();
        }
        eng.unlock();
        if (this.bad_conditions()) {
            eng.lock();
            this.playersCar = Crew.getIgrokCar();
            if (this.playersCar.gPosition().oMinusN(officePos).length() < 500.0) {
                this.playersCar.makeParking(parkingplace.findParkingByName("OxnardParking04", officePos), 0);
            }
            EventsControllerHelper.messageEventHappened(SKIP_SCENE);
            this.playersCar.leaveParking();
            Helper.debugSceneFinishedEvent();
            eng.unlock();
            return;
        }
        eng.startMangedFadeAnimation();
        eng.lock();
        traffic.enterCutSceneModeImmediately();
        eng.unlock();
        this.makecar();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        presets data = new presets();
        data.car = this.playersCar;
        data.P = eng.getControlPointPosition(SCENE_POINT);
        trs.PlaySceneXMLThreaded("00009", false, data);
        eng.startMangedFadeAnimation();
        this.afterScene();
        eng.lock();
        traffic.setTrafficMode(0);
        Helper.debugSceneFinishedEvent();
        eng.unlock();
        eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }

    private boolean bad_conditions() {
        this.playersCar = Crew.getIgrokCar();
        return scenarioscript.isAuotestRun() || !scenarioscript.isTutorialOn() || this.playersCar.gPosition().oMinusN(officePos).length() > 500.0;
    }

    static class presets {
        actorveh car;
        vectorJ P;
        matrixJ M = new matrixJ();

        presets() {
        }
    }
}

