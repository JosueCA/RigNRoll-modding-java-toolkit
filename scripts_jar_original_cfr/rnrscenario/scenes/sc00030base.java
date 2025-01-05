/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Collide;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.KohHelpManage;
import rnrscenario.controllers.Location;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=0)
public abstract class sc00030base
extends stage {
    static boolean NO_TRAFFIC_IN_SCENE = false;
    private static final double SHIFT_Y = 7.0;
    private static final double SHIFT_X = -1.0;
    private static final String sceneNamepart1 = "00030part1";

    protected abstract String getSceneName();

    public sc00030base(Object monitor, String sceneName) {
        super(monitor, sceneName);
    }

    private void rotateSceneAcordingApproachingDirection(vectorJ pos, matrixJ Mscene, actorveh meet_car) {
        vectorJ R = meet_car.gPosition().oMinusN(pos);
        if (0.0 < Mscene.v1.dot(R)) {
            Mscene.v0.mult(-1.0);
            Mscene.v1.mult(-1.0);
        }
    }

    private presets makePresets(actorveh car) {
        presets pres = new presets();
        pres.M = car.gMatrix();
        pres.P = car.gPosition();
        vectorJ shiftY = new vectorJ(pres.M.v1);
        shiftY.mult(7.0);
        vectorJ shiftX = new vectorJ(pres.M.v0);
        shiftX.mult(-1.0);
        pres.P.oPlus(shiftY.oPlusN(shiftX));
        vectorJ pos1 = new vectorJ(pres.P);
        vectorJ pos2 = new vectorJ(pres.P);
        pos1.z -= 100.0;
        pos2.z += 100.0;
        vectorJ colPoint = Collide.collidePoint(pos1, pos2);
        if (colPoint != null) {
            pres.P = colPoint;
        }
        return pres;
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        actorveh cochCar = Crew.getMappedCar("KOH");
        presets pres = this.makePresets(cochCar);
        if (NO_TRAFFIC_IN_SCENE) {
            traffic.enterCutSceneMode(pres.P);
        }
        eng.disableControl();
        actorveh playerCar = Crew.getIgrokCar();
        this.rotateSceneAcordingApproachingDirection(pres.P, pres.M, playerCar);
        Location location = KohHelpManage.getInstance().getNickLocation();
        assert (null != location) : "location for nick's car is null";
        playerCar.sVeclocity(0.0);
        playerCar.setHandBreak(true);
        playerCar.sPosition(location.getPosition(), location.getOrientation());
        trackscripts track = new trackscripts(this.getSyncMonitor());
        eng.unlock();
        track.PlaySceneXMLThreaded("00030part1", true, pres);
        eng.lock();
        playerCar.sVeclocity(0.0);
        cochCar.sVeclocity(0.0);
        pres = this.makePresets(cochCar);
        eng.unlock();
        track.PlaySceneXMLThreaded(this.getSceneName(), false, pres);
        eng.lock();
        eng.enableControl();
        cochCar.deactivate();
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.SwitchDriver_in_cabin(playerCar.getCar());
        KohHelpManage.questFinished();
        if (NO_TRAFFIC_IN_SCENE) {
            traffic.setTrafficMode(0);
        }
        playerCar.setHandBreak(false);
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static final class presets {
        matrixJ M;
        vectorJ P;

        presets() {
        }
    }
}

