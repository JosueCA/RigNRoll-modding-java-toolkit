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
import rnrscr.cSpecObjects;
import rnrscr.specobjects;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01620
extends stage {
    public sc01620(Object monitor) {
        super(monitor, "sc01620");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        Data data;
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        aiplayer monica = aiplayer.getScenarioAiplayer("SC_MONICA");
        monica.bePassangerOfCar(car);
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        if (null != crossroad && 0 == crossroad.name.compareToIgnoreCase("KeyPoint_1620")) {
            data = new Data(crossroad.matrix, crossroad.position, car);
            car.sPosition(crossroad.position, crossroad.matrix);
            car.sVeclocity(0.0);
        } else {
            data = new Data(new matrixJ(), new vectorJ(), car);
        }
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("01620", false, data);
        eng.lock();
        monica.abondoneCar(car);
        if (null != EnemyBase.getInstance()) {
            EnemyBase.getInstance().met_monica();
        }
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    private static final class Data {
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

