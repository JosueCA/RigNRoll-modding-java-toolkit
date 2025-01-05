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

@ScenarioClass(scenarioStage=7)
public final class sc00530
extends stage {
    private static boolean DEBUG = false;

    public static void setDebug() {
        DEBUG = true;
    }

    public sc00530(Object monitor) {
        super(monitor, "sc00530");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        eng.SwitchDriver_outside_cabin(car.getCar());
        car.setHandBreak(true);
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        Presests data = new Presests();
        data.M = car.gMatrix();
        vectorJ shift = new vectorJ(data.M.v1);
        shift.mult(10.0);
        vectorJ pos1 = car.gPosition().oPlusN(shift);
        vectorJ pos2 = new vectorJ(pos1);
        pos1.z += 100.0;
        pos2.z -= 100.0;
        data.P = Collide.collidePoint(pos1, pos2);
        trs.PlaySceneXMLThreaded("00530", false, data);
        eng.lock();
        car.setHandBreak(false);
        event.Setevent(530);
        eng.SwitchDriver_in_cabin(car.getCar());
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        if (!DEBUG) {
            EventsControllerHelper.messageEventHappened("bankrupt");
        }
        DEBUG = false;
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presests {
        matrixJ M;
        vectorJ P;

        Presests() {
        }
    }
}

