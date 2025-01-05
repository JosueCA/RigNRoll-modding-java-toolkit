/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.chase00090;
import rnrscenario.scenes.sc00060base;
import rnrscr.drvscripts;
import rnrscr.parkingplace;
import rnrscr.specobjects;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=1)
public final class sc00060part1
extends sc00060base {
    public sc00060part1(Object monitor) {
        super(monitor, "sc00060part1");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        super.prepare();
        drvscripts _drvscripts = new drvscripts(this.getSyncMonitor());
        this.ourcar.makeParking(parkingplace.findParkingByName("Oxnard_Parking_01", this.ourcar.gPosition()), 3);
        eng.unlock();
        rnrscenario.tech.Helper.waitParked(this.ourcar);
        _drvscripts.playOutOffCarThreaded(Crew.getIgrok(), this.ourcar);
        eng.lock();
        long part1 = scenetrack.CreateSceneXML("00060part1", 0, specobjects.getBarPresets());
        eng.unlock();
        this.trs.PlaySceneXMLThreaded(part1, false);
        eng.lock();
        eng.disableControl();
        rnrscr.Helper.restoreCameraToIgrokCar();
        chase00090.createDorothyPassanger();
        drvscripts.playInsideCarFast(Crew.getIgrok(), this.ourcar);
        this.ourcar.leaveParking();
        Presets data = new Presets();
        data.car = this.ourcar;
        data.M = this.ourcar.gMatrix();
        data.P = this.ourcar.gPosition();
        data.P.z += 1.0;
        long part2 = scenetrack.CreateSceneXML("00060part2", 0, specobjects.getBarPresets());
        eng.unlock();
        this.ourcar.SetHidden(2);
        this.trs.PlaySceneXMLThreaded(part2, false);
        this.ourcar.SetHidden(0);
        eng.lock();
        EventsControllerHelper.messageEventHappened("sceneendedwithDorothy");
        eng.unlock();
        super.epilogue();
        eng.lock();
        if (null != chase00090.getInstance()) {
            chase00090.getInstance().startChasingPlayer();
        }
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presets {
        actorveh car;
        actorveh banditcar;
        matrixJ M;
        vectorJ P;

        Presets() {
        }
    }
}

