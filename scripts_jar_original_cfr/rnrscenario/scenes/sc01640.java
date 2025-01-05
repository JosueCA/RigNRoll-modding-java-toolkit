/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
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
public final class sc01640
extends stage {
    private static final String ROOT_SCENE = "KeyPoint_2045";

    public sc01640(Object monitor) {
        super(monitor, "sc01640");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        cSpecObjects crossroad;
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        aiplayer monica = aiplayer.getScenarioAiplayer("SC_MONICA");
        monica.bePassangerOfCar(car);
        vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        pool.add(new SceneActorsPool("baza", person));
        Data data = new Data(new matrixJ(), new vectorJ(3618.495117, 13370.149048, 320.980331), new matrixJ(), pos, car);
        cSpecObjects sceneroot = specobjects.getInstance().GetLoadedNamedScenarioObject(ROOT_SCENE);
        if (sceneroot != null) {
            data.P = sceneroot.position;
        }
        if ((crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject()) != null && crossroad.name.compareToIgnoreCase("KeyPoint_1640") == 0) {
            vectorJ check = new vectorJ(3926.0, 13128.0, 317.0);
            if (check.len2(crossroad.position) > 1000000.0) {
                crossroad.position = check;
            }
            car.sPosition(crossroad.position, crossroad.matrix);
            car.sVeclocity(0.0);
        }
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.unlock();
        trs.PlaySceneXMLThreaded("01640", false, pool, data);
        eng.lock();
        monica.abondoneCar(car);
        if (EnemyBase.getInstance() != null) {
            EnemyBase.getInstance().finish_tunnel(false);
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
        matrixJ M1;
        vectorJ P1;
        actorveh car;

        Data(matrixJ M, vectorJ P, matrixJ M1, vectorJ P1, actorveh car) {
            this.M = M;
            this.P = P;
            this.M1 = M1;
            this.P1 = P1;
            this.car = car;
        }
    }
}

