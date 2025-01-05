/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Collide;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStageNumber")
public final class sc00520
extends stage {
    private int scenarioStageNumber;

    private Presets updatePositions(actorveh car) {
        Presets res = new Presets();
        res.M = rnrscenario.tech.Helper.makeMatrixAlignedToZ(car.gMatrix());
        res.P = car.gPosition();
        vectorJ shift = car.gDir();
        shift.mult(10.0);
        res.P.oPlus(shift);
        vectorJ p1 = res.P.oPlusN(new vectorJ(0.0, 0.0, 10.0));
        vectorJ p2 = res.P.oPlusN(new vectorJ(0.0, 0.0, -10.0));
        res.P = Collide.collidePoint(p1, p2);
        return res;
    }

    public sc00520(Object monitor, int scenarioStageNumber) {
        super(monitor, "sc00520");
        this.scenarioStageNumber = scenarioStageNumber;
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        car.setHandBreak(true);
        eng.SwitchDriver_outside_cabin(car.getCar());
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00520", false, this.updatePositions(car));
        eng.lock();
        car.setHandBreak(false);
        event.Setevent(465);
        eng.SwitchDriver_in_cabin(car.getCar());
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presets {
        matrixJ M;
        vectorJ P;

        Presets() {
        }
    }
}

