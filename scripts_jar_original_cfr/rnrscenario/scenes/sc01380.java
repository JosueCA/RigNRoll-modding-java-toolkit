/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.CursedHiWay;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenes.sc01370;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01380
extends stage {
    public sc01380(Object monitor) {
        super(monitor, "sc01380");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        actorveh car = eng.CreateCarImmediatly("KENWORTH_T600W", new matrixJ(), Crew.getIgrokCar().gPosition().oPlusN(new vectorJ(20.0, 20.0, -20.0)));
        car.UpdateCar();
        car.registerCar("matcar");
        Crew.getIgrokCar().registerCar("ourcar");
        vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
        sc01370.Data data = new sc01370.Data();
        data.P = pos;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.unlock();
        trs.PlaySceneXMLThreaded("01380", false, data);
        eng.lock();
        CursedHiWay.finishCursedHiWay();
        rnrscr.Helper.restoreCameraToIgrokCar();
        Crew.getIgrokCar().sVeclocity(10.0);
        car.deactivate();
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

