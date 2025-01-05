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
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=14)
public final class sc02045
extends stage {
    public static final boolean DEBUG = false;

    public sc02045(Object monitor) {
        super(monitor, "sc02045");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        Data _data;
        if (!scenarioscript.script.setPlayerCarHasBlown()) {
            return;
        }
        eng.lock();
        eng.doWide(true);
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        SCRuniperson shooter = SCRuniperson.createCutSceneAmbientPerson("Man_024", null);
        v.add(new SceneActorsPool("baza", person));
        v.add(new SceneActorsPool("shooter", shooter));
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_2045");
        if (crossroad != null) {
            _data = new Data(new matrixJ(), crossroad.position);
        } else {
            matrixJ M = new matrixJ();
            vectorJ P = new vectorJ(3618.49, 13370.0, 320.0);
            _data = new Data(M, P);
        }
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        long fadescene = trs.createSceneXML("justfadecutscene", null, _data);
        long scene1 = trs.createSceneXML("02045", v, _data);
        eng.unlock();
        trs.PlaySceneXMLThreaded(fadescene, true);
        trs.PlaySceneXMLThreaded(scene1, false);
        eng.lock();
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        EventsControllerHelper.messageEventHappened("blowcar");
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static final class Data {
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

