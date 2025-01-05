/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenes.scBase0048;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=6)
public final class sc00480
extends scBase0048 {
    public sc00480(Object monitor) {
        super(monitor, "sc00480");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        this.preAction(automat);
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00480", false);
        this.postAction(automat);
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

