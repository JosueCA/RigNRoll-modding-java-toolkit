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
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=5)
public final class sc00465
extends stage {
    public sc00465(Object monitor) {
        super(monitor, "sc00465");
    }

    private Presets updatePositions(actorveh car) {
        Presets res = new Presets();
        res.M = rnrscenario.tech.Helper.makeMatrixAlignedToZ(car.gMatrix());
        res.P = car.gPosition();
        vectorJ shift = new vectorJ(res.M.v1);
        shift.mult(10.0);
        res.P.oPlus(shift);
        vectorJ p1 = res.P.oPlusN(new vectorJ(0.0, 0.0, 10.0));
        vectorJ p2 = res.P.oPlusN(new vectorJ(0.0, 0.0, -10.0));
        res.P = Collide.collidePoint(p1, p2);
        return res;
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.setHandBreak(true);
        eng.SwitchDriver_outside_cabin(car.getCar());
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00465", false, this.updatePositions(car));
        eng.enableControl();
        rnrscenario.tech.Helper.waitSimpleState();
        eng.lock();
        car.setHandBreak(false);
        event.Setevent(465);
        eng.SwitchDriver_in_cabin(car.getCar());
        EventsControllerHelper.messageEventHappened("Player loose car");
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

