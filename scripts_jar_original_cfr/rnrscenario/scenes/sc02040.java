/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.CarName;
import players.Crew;
import players.CutSceneAuxPersonCreator;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.EnemyBase;
import rnrscenario.stage;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=14)
public final class sc02040
extends stage {
    private static long scene = 0L;

    public static void prepareScene(Object monitor) {
        vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        v.add(new SceneActorsPool("baza", person));
        Data _data = new Data(new matrixJ(), pos);
        trackscripts trs = new trackscripts(monitor);
        scene = trs.createSceneXML("02040a_part3", v, _data);
    }

    public static void removeScene() {
        if (0L != scene) {
            scenetrack.DeleteScene(scene);
            scene = 0L;
        }
    }

    public sc02040(Object monitor) {
        super(monitor, "sc02040");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        v.add(new SceneActorsPool("baza", person));
        aiplayer dakota_for_scene = new aiplayer("SC_ONTANIELOLOW");
        dakota_for_scene.sPoolBased("dakota_for_2040");
        dakota_for_scene.setModelCreator(new CutSceneAuxPersonCreator(), "dakota_for_2040");
        SCRuniperson dakota_person = dakota_for_scene.getModel();
        dakota_person.lockPerson();
        v.add(new SceneActorsPool("dakota", dakota_person));
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_2040");
        Data _data1 = null != crossroad ? new Data(crossroad.matrix, crossroad.position) : new Data(new matrixJ(), new vectorJ(3926.0, 13128.0, 317.0));
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded(scene, true);
        eng.lock();
        actorveh police1 = eng.CreateCarForScenario(CarName.CAR_POLICE, new matrixJ(), _data1.P);
        police1.registerCar("police1");
        actorveh police2 = eng.CreateCarForScenario(CarName.CAR_POLICE, new matrixJ(), _data1.P);
        police2.registerCar("police2");
        eng.unlock();
        trs.PlaySceneXMLThreaded("02040", false, v, _data1);
        eng.lock();
        dakota_person.unlockPerson();
        police1.deactivate();
        police2.deactivate();
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.SwitchDriver_in_cabin(Crew.getIgrokCar().getCar());
        eng.enableControl();
        if (null != EnemyBase.getInstance()) {
            EnemyBase.getInstance().finish_Enemy_base();
        }
        Helper.debugSceneFinishedEvent();
        eng.unlock();
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

