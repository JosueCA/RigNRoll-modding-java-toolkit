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

@ScenarioClass(scenarioStage=8)
public final class sc00610
extends stage {
    public sc00610(Object monitor) {
        super(monitor, "sc00610");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.disableControl();
        rnrscenario.tech.Helper.makeComeOut();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00610", false);
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.enableControl();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

