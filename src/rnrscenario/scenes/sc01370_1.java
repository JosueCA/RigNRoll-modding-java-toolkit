/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01370_1
extends stage {
    public sc01370_1(Object monitor) {
        super(monitor, "sc01370_1");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        Crew.getIgrokCar().registerCar("ourcar");
        vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
        Data data = new Data();
        data.P = pos;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("01370_1", false, data);
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Data {
        vectorJ P;
        matrixJ M = new matrixJ();

        Data() {
        }
    }
}

