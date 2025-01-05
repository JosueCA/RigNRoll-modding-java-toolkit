/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.EnemyBase;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01650
extends stage {
    public sc01650(Object monitor) {
        super(monitor, "sc01650");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        aiplayer monica = aiplayer.getScenarioAiplayer("SC_MONICA");
        monica.bePassangerOfCar(car);
        car.sOnTheRoadLaneAndStop(1);
        Data data = new Data(car.gMatrix(), car.gPosition(), car);
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("01650", false, data);
        eng.lock();
        monica.abondoneCar(car);
        if (EnemyBase.getInstance() != null) {
            EnemyBase.getInstance().finish_tunnel(true);
        }
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Data {
        matrixJ M;
        vectorJ P;
        actorveh car;

        Data() {
        }

        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }
}

