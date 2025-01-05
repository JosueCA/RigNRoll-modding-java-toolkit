/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=4)
public final class sc00454
extends stage {
    public sc00454(Object monitor) {
        super(monitor, "sc00454");
    }

    static void teleportToBarWithBandits() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace("MP_Bar_SB_01");
        if (null != place) {
            vectorJ position = place.getCoords();
            position.x += 50.0;
            position.y += 50.0;
            Crew.getIgrokCar().teleport(position);
            rnrscenario.tech.Helper.waitTeleport();
        }
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
        trs.PlaySceneXMLThreaded("00454", false);
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

