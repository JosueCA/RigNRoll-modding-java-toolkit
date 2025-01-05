/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.JavaEvents;
import players.Crew;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrscenario.cScriptFuncs;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscenario.tech.SleepOnTime;
import rnrscr.Helper;
import rnrscr.ILeaveMenuListener;

public final class bankrupt
extends stage {
    private static final String[] SCENENAME = new String[]{"bankrupt", "01190"};
    private static final int INDEX_SIMPLE_BANCRUPT = 0;
    private static final int INDEX_SCENARIO_BANCRUPT = 1;
    private static int s_chosenIndex = 0;
    private static final int SCENEFLAGS = 2;
    private boolean w8WorldLoaded = false;

    public bankrupt(Object monitor) {
        super(monitor, "bankrupt");
    }

    public static void setSimpleBankrupt() {
        s_chosenIndex = 0;
    }

    public static void setScenarioBankrupt() {
        s_chosenIndex = 1;
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        String sceneName = SCENENAME[s_chosenIndex];
        eng.unlock();
        long scene = scenetrack.CreateSceneXML(sceneName, 2, null);
        eng.lock();
        new cSceneSuspendEventWaiter(scene);
        eng.unlock();
        while (!this.w8WorldLoaded) {
            new SleepOnTime(10);
        }
        rnrscenario.tech.Helper.waitGameWorldLoad();
        eng.lock();
        if (scenarioscript.script.isInstantOrder()) {
            JavaEvents.SendEvent(23, 3, this);
        } else {
            JavaEvents.SendEvent(23, 1, this);
        }
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
            scenarioscript.script.menu_bankrupt(this);
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
                Helper.restoreCameraToIgrokCar();
                bankrupt.this.w8WorldLoaded = true;
                eng.blockEscapeMenu();
                return true;
            }
            return false;
        }
    }
}

