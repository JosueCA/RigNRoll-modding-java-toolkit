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
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=12)
public final class sc01420
extends stage {
    public sc01420(Object monitor) {
        super(monitor, "sc01420");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        actorveh car = Crew.getIgrokCar();
        drvscripts _drvscripts = new drvscripts(this.getSyncMonitor());
        eng.unlock();
        _drvscripts.playOutOffCarThreaded(Crew.getIgrok(), car);
        trs.PlaySceneXMLThreaded("01420", false);
        eng.lock();
        eng.enableControl();
        eng.unlock();
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

