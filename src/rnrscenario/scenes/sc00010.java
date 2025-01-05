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
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=0)
public final class sc00010
extends stage {
    private static final String SCENES_PROCEED = "Begining Scene phase2";

    public sc00010(Object monitor) {
        super(monitor, "sc00010");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00010", false);
        EventsControllerHelper.messageEventHappened(SCENES_PROCEED);
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
        eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }
}

