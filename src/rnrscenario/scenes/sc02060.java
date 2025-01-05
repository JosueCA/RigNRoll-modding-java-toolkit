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
import rnrscenario.controllers.ChaseKoh;
import rnrscenario.stage;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=15)
public final class sc02060
extends stage {
    private static boolean NEED_BLOW_CAR = true;

    public static void setDebugMode(boolean value) {
        NEED_BLOW_CAR = !value;
    }

    public sc02060(Object monitor) {
        super(monitor, "sc02060");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.doWide(true);
        eng.disableControl();
        Data data = new Data();
        data.car = Crew.getMappedCar("KOH");
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.unlock();
        trs.PlaySceneXMLThreaded("02060", false, data);
        eng.lock();
        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().endChase();
        }
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        eng.unlock();
        rnrscenario.tech.Helper.waitSimpleState();
        eng.lock();
        if (NEED_BLOW_CAR) {
            EventsControllerHelper.messageEventHappened("blowcar");
        }
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    private static final class Data {
        actorveh car;

        private Data() {
        }
    }
}

