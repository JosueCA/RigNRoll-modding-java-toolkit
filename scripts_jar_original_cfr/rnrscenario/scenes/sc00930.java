/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.CarName;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.SCRuniperson;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.cSpecObjects;
import rnrscr.drvscripts;
import rnrscr.parkingplace;
import rnrscr.specobjects;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=10)
public final class sc00930
extends stage {
    public sc00930(Object monitor) {
        super(monitor, "sc00930");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        actorveh car = Crew.getIgrokCar();
        drvscripts drv = new drvscripts(this.getSyncMonitor());
        drv.playOutOffCarThreaded(Crew.getIgrok(), car);
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.lock();
        eng.disableControl();
        parkingplace pPlace = parkingplace.findParkingByName("Oxnard_Police_Parking", car.gPosition());
        cSpecObjects pPlaceWorld = specobjects.getInstance().GetNearestLoadedParkingPlace();
        actorveh dakotacar = null;
        actorveh cohcar = null;
        if (null != pPlaceWorld) {
            dakotacar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, pPlaceWorld.matrix, pPlaceWorld.position);
            dakotacar.makeParking(pPlace, 2);
            cohcar = eng.CreateCarForScenario(CarName.CAR_COCH, pPlaceWorld.matrix, pPlaceWorld.position);
            cohcar.makeParking(pPlace, 1);
        }
        eng.unlock();
        trs.PlaySceneXMLThreaded("00930", true);
        eng.lock();
        eng.returnCameraToGameWorld();
        if (null != dakotacar) {
            dakotacar.leaveParking();
            dakotacar.autopilotTo(eng.getControlPointPosition("Oxnard_Bar_01"));
        }
        eng.unlock();
        rnrscenario.tech.Helper.waitGameWorldLoad();
        trs.PlaySceneXMLThreaded("00931", false, specobjects.getPolicePresets());
        eng.lock();
        if (null != dakotacar) {
            dakotacar.deactivate();
        }
        if (null != cohcar) {
            cohcar.deactivate();
        }
        aiplayer livePlayer = Crew.getIgrok();
        SCRuniperson person = livePlayer.getModel();
        person.play();
        eng.SwitchDriver_in_cabin(car.getCar());
        car.leaveParking();
        eng.doWide(false);
        eng.enableControl();
        rnrscr.Helper.restoreCameraToIgrokCar();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

