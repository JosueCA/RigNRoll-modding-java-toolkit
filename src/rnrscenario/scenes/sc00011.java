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
import rnrscr.specobjects;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=0)
public final class sc00011
extends stage {
    private static final String SCENES_PROCEED = "Begining Scene phase3";

    public sc00011(Object monitor) {
        super(monitor, "sc00011");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        actorveh playersCar = Crew.getIgrokCar();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00011", false, specobjects.getOfficePresets());
        eng.lock();
        eng.SwitchDriver_in_cabin(playersCar.getCar());
        rnrscr.Helper.restoreCameraToIgrokCar();
        playersCar.leaveParking();
        eng.enableControl();
        eng.unlock();
        EventsControllerHelper.messageEventHappened(SCENES_PROCEED);
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

