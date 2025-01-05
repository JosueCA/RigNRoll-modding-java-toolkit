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

@ScenarioClass(scenarioStage=7)
public final class sc00510
extends stage {
    public sc00510(Object monitor) {
        super(monitor, "sc00510");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.unlock();
        rnrscenario.tech.Helper.makeComeOut();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00510", false);
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.startMangedFadeAnimation();
        eng.enableControl();
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

