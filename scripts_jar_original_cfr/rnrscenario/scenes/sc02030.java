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
import rnrscenario.controllers.EnemyBase;
import rnrscenario.scenes.sc02040;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=14)
public final class sc02030
extends stage {
    public sc02030(Object monitor) {
        super(monitor, "sc02030");
    }

    public void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.doWide(true);
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        vectorJ pos_Igrokcar = new vectorJ(3856.20603, 13122.95883, 317.619);
        matrixJ matr_Igrokcar = matrixJ.Mz(0.7874925584998415);
        car.sPosition(pos_Igrokcar, matr_Igrokcar);
        vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        v.add(new SceneActorsPool("baza", person));
        Data _data = new Data(new matrixJ(), pos);
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        long fadescene = trs.createSceneXML("justfadecutscene", null, _data);
        long scene1 = trs.createSceneXML("02030", v, _data);
        long scene2 = trs.createSceneXML("02040a_part1", v, _data);
        sc02040.prepareScene(this.getSyncMonitor());
        eng.unlock();
        trs.PlaySceneXMLThreaded(fadescene, true);
        trs.PlaySceneXMLThreaded(scene1, true);
        trs.PlaySceneXMLThreaded(scene2, false);
        eng.lock();
        if (EnemyBase.getInstance() != null) {
            EnemyBase.getInstance().assault_begun();
        }
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        eng.unlock();
        trs.PlaySceneXMLThreaded("02031", false);
        if (!stage.isInterrupted()) {
            eng.lock();
            Helper.debugSceneFinishedEvent();
            eng.unlock();
        }
    }

    static class Data {
        matrixJ M;
        vectorJ P;

        Data() {
        }

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }
}

