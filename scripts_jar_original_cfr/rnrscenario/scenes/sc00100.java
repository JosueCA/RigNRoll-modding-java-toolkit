/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=1)
public final class sc00100
extends stage {
    public sc00100(Object monitor) {
        super(monitor, "sc00100");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        car.setHandBreak(true);
        aiplayer Dorothy = aiplayer.getScenarioAiplayer("SC_DOROTHY");
        Dorothy.bePassangerOfCar(car);
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        Presets data = new Presets();
        data.car = car;
        eng.unlock();
        trs.PlaySceneXMLThreaded("00100part1", false, data);
        eng.lock();
        car.sOnTheRoadLaneAndStop(1);
        eng.unlock();
        trs.PlaySceneXMLThreaded("00100part2", false, data);
        eng.lock();
        rnrscr.Helper.restoreCameraToIgrokCar();
        Dorothy.abondoneCar(car);
        eng.enableControl();
        car.setHandBreak(false);
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presets {
        actorveh car;

        Presets() {
        }
    }
}

