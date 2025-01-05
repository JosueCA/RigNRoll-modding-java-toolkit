/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.Crew;
import players.CutSceneAuxPersonCreator;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrcore.teleport.ITeleported;
import rnrcore.teleport.MakeTeleport;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ChaseKoh;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscr.parkingplace;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=15)
public final class sc02065
extends stage
implements ITeleported {
    private static final boolean DEBUG = false;
    boolean was_teleported = false;

    public sc02065(Object monitor) {
        super(monitor, "sc02065");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().finishShootingAnimation();
        }
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");
        dakota.bePassangerOfCar(car);
        aiplayer gun = new aiplayer("SC_DAKOTAGUN");
        gun.sPoolBased("dakota_cut_scene_gun");
        gun.setModelCreator(new CutSceneAuxPersonCreator(), "dakotagun");
        gun.bePassangerOfCar_simple(car);
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(new SceneActorsPool("gun", gun.getModel()));
        Data _data = new Data(car);
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.unlock();
        trs.PlaySceneXMLThreaded("02065", false, pool, _data);
        eng.lock();
        gun.abondoneCar(car);
        dakota.abondoneCar(car);
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().endChaseButContinueRun();
        }
        car.teleport(scenarioscript.OXNARD_OFFICE);
        MakeTeleport.teleport(this);
        eng.unlock();
        while (true) {
            this.simplewaitFor(100);
            eng.lock();
            if (this.was_teleported) break;
            eng.unlock();
        }
        eng.unlock();
        eng.lock();
        parkingplace place = parkingplace.findParkingByName("OxnardParking04", scenarioscript.OXNARD_OFFICE);
        car.makeParking(place, 0);
        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().scheduleCarBlow();
        }
        eng.enableControl();
        eng.unlock();
        rnrscenario.tech.Helper.makeComeInAndLeaveParkingFast();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    public void teleported() {
        this.was_teleported = true;
    }

    void simplewaitFor(int timemillesec) {
        try {
            this.waitFor(timemillesec);
        }
        catch (InterruptedException e) {
            eng.writeLog("Script Error. Stage sc02065 error.");
        }
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }

    class Data {
        actorveh car;

        Data(actorveh car) {
            this.car = car;
        }
    }
}

