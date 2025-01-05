/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenes.sc00454;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=4)
public final class sc00455
extends stage {
    public sc00455(Object monitor) {
        super(monitor, "sc00455");
    }

    protected void debugSetupPrecondition() {
        sc00454.teleportToBarWithBandits();
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.unlock();
        rnrscenario.tech.Helper.makeParkAndComeOut("SB_Parking01", 4);
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00455", false);
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

