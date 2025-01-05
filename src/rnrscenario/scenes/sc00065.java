/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.drvscripts;
import rnrscr.parkingplace;
import rnrscr.specobjects;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=1)
public final class sc00065
extends stage {
    public sc00065(Object monitor) {
        super(monitor, "sc00065");
    }

    protected void debugSetupPrecondition() {
        eng.lock();
        actorveh car = Crew.getIgrokCar();
        car.makeParking(parkingplace.findParkingByName("Oxnard_Parking_01", car.gPosition()), 3);
        eng.unlock();
        rnrscenario.tech.Helper.waitParked(car);
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.unlock();
        drvscripts _drvscripts = new drvscripts(this.getSyncMonitor());
        _drvscripts.playOutOffCarThreaded(Crew.getIgrok(), Crew.getIgrokCar());
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00065", false, specobjects.getBarPresets());
        eng.lock();
        eng.SwitchDriver_in_cabin(Crew.getIgrokCar().getCar());
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        eng.unlock();
        rnrscenario.tech.Helper.waitSimpleState();
        eng.lock();
        EventsControllerHelper.messageEventHappened("loose");
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

