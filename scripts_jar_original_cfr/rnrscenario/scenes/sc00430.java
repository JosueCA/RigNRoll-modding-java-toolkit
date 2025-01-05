/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=3)
public final class sc00430
extends stage {
    public sc00430(Object monitor) {
        super(monitor, "sc00430");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.unlock();
        rnrscenario.tech.Helper.makeComeOut();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00430", false);
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

