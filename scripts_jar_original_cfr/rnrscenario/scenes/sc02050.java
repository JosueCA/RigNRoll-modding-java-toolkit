/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ChaseKoh;
import rnrscenario.stage;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=15)
public final class sc02050
extends stage {
    private static final boolean DEBUG = false;

    public sc02050(Object monitor) {
        super(monitor, "sc02050");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh playerCar = Crew.getIgrokCar();
        playerCar.setHandBreak(true);
        actorveh cochCar = Crew.getMappedCar("KOH");
        cochCar.registerCar("madcar");
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("BarIn4");
        v.add(new SceneActorsPool("bar", person));
        Data _data = new Data(new matrixJ(), new vectorJ(), null);
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        Data _data1 = new Data(new matrixJ(), new vectorJ(), null);
        if (null != crossroad && 0 == "KeyPoint_2050".compareToIgnoreCase(crossroad.name)) {
            _data1 = new Data(crossroad.matrix, crossroad.position, null);
        }
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("02050", true, v, _data);
        eng.lock();
        ChaseKoh.demolishBar();
        eng.unlock();
        trs.PlaySceneXMLThreaded("02050_part2", false, null, _data1);
        eng.lock();
        if (ChaseKoh.getInstance() != null) {
            ChaseKoh.getInstance().start_chase();
        }
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.SwitchDriver_in_cabin(playerCar.getCar());
        playerCar.setHandBreak(false);
        eng.enableControl();
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

