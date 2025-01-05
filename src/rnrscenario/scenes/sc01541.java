/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import rnrcore.Helper;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenes.sc01540_base;

@ScenarioClass(scenarioStage=13)
public final class sc01541
extends sc01540_base {
    public sc01541(Object monitor) {
        super(monitor, "01541");
    }

    public void playSceneBody(cScriptFuncs automat) {
        super.playSceneBody(automat);
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

