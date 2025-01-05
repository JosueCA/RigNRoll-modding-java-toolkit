/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscenario.tech.Helper;

@ScenarioClass(scenarioStage=6)
public abstract class scBase0048
extends stage {
    public scBase0048(Object monitor, String sceneName) {
        super(monitor, "sceneName");
    }

    void preAction(cScriptFuncs automat) {
        eng.disableControl();
        Helper.makeComeOut();
    }

    void postAction(cScriptFuncs automat) {
        Helper.makeComeInAndLeaveParking();
        eng.enableControl();
    }
}

