/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.JavaEvents;
import menu.menues;
import players.Crew;
import rnrcore.Helper;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrscenario.PoliceScene;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscenario.tech.SleepOnTime;
import rnrscr.ILeaveMenuListener;

@ScenarioClass(scenarioStage=12)
public final class sc01440
extends stage {
    private boolean w8WorldLoaded = false;
    private static final int SCENEFLAGS = 2;

    public sc01440(Object monitor) {
        super(monitor, "sc01440");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        long scene = scenetrack.CreateSceneXML("01440", 2, null);
        PoliceScene.reserved_for_scene = false;
        new cSceneSuspendEventWaiter(scene);
        eng.unlock();
        while (!this.w8WorldLoaded) {
            new SleepOnTime(10);
        }
        rnrscenario.tech.Helper.waitGameWorldLoad();
        eng.lock();
        JavaEvents.SendEvent(23, 1, this);
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    class cSceneSuspendEventWaiter
    extends TypicalAnm
    implements ILeaveMenuListener {
        private long scene;
        private boolean check = true;
        private boolean toFinish = false;

        cSceneSuspendEventWaiter(long scene) {
            this.scene = scene;
            event.eventObject((int)scene + 1, this, "event");
            eng.CreateInfinitScriptAnimation(this);
            scenetrack.UpdateSceneFlags(scene, 1);
        }

        public void event() {
            assert (this.check);
            this.check = false;
            menues.gameoverMenuMurder(this);
        }

        public void menuLeaved() {
            assert (!this.check);
            this.toFinish = true;
        }

        public boolean animaterun(double dt) {
            if (this.toFinish) {
                eng.startMangedFadeAnimation();
                scenetrack.DeleteScene(this.scene);
                eng.enableControl();
                Crew.getIgrok().getModel().SetInGameWorld();
                rnrscr.Helper.restoreCameraToIgrokCar();
                sc01440.this.w8WorldLoaded = true;
                eng.blockEscapeMenu();
                return true;
            }
            return false;
        }
    }
}

