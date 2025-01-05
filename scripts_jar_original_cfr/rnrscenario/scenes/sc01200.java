/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.BigRaceScenario;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=11)
public final class sc01200
extends stage {
    public sc01200(Object monitor) {
        super(monitor, "sc01200");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.lock();
        eng.disableControl();
        eng.startMangedFadeAnimation();
        actorveh car = Crew.getIgrokCar();
        BigRaceScenario.finishRace();
        vectorJ office_pos = eng.getNamedOfficePosition("office_OV_01");
        car.teleport(eng.setCurrentOfficeNear(office_pos));
        eng.unlock();
        rnrscenario.tech.Helper.waitTeleport();
        trs.PlaySceneXMLThreaded("01200", false);
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

