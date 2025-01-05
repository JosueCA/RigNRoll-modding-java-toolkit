/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.CutSceneAuxPersonCreator;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01080
extends stage {
    public sc01080(Object monitor) {
        super(monitor, "sc01080");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        aiplayer cips1 = new aiplayer("SC_CHIPSY");
        cips1.sPoolBased("chips1");
        cips1.setModelCreator(new CutSceneAuxPersonCreator(), "chips1");
        aiplayer cips2 = new aiplayer("SC_CHIPSY");
        cips2.sPoolBased("chips2");
        cips2.setModelCreator(new CutSceneAuxPersonCreator(), "chips2");
        aiplayer coffe1 = new aiplayer("SC_COFFE");
        coffe1.sPoolBased("coffee1");
        coffe1.setModelCreator(new CutSceneAuxPersonCreator(), "coffee1");
        aiplayer coffe2 = new aiplayer("SC_COFFE");
        coffe2.sPoolBased("coffee2");
        coffe2.setModelCreator(new CutSceneAuxPersonCreator(), "coffee2");
        aiplayer coffe3 = new aiplayer("SC_COFFE");
        coffe3.sPoolBased("coffee3");
        coffe3.setModelCreator(new CutSceneAuxPersonCreator(), "coffee3");
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        SCRuniperson person_cips1 = cips1.getModel();
        SCRuniperson person_cips2 = cips2.getModel();
        SCRuniperson person_coffe1 = coffe1.getModel();
        SCRuniperson person_coffe2 = coffe2.getModel();
        SCRuniperson person_coffe3 = coffe3.getModel();
        person_cips1.lockPerson();
        person_cips2.lockPerson();
        person_coffe1.lockPerson();
        person_coffe2.lockPerson();
        person_coffe3.lockPerson();
        pool.add(new SceneActorsPool("chips1", person_cips1));
        pool.add(new SceneActorsPool("chips2", person_cips2));
        pool.add(new SceneActorsPool("coffee1", person_coffe1));
        pool.add(new SceneActorsPool("coffee2", person_coffe2));
        pool.add(new SceneActorsPool("coffee3", person_coffe3));
        eng.unlock();
        rnrscenario.tech.Helper.makeComeOut();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("01080_big_bone", false, pool, new Object());
        eng.lock();
        person_cips1.unlockPerson();
        person_cips2.unlockPerson();
        person_coffe1.unlockPerson();
        person_coffe2.unlockPerson();
        person_coffe3.unlockPerson();
        eng.unlock();
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.enableControl();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

