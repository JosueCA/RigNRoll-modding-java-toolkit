/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.JavaEvents;
import menuscript.TotalVictoryMenu;
import menuscript.VictoryMenu;
import menuscript.VictoryMenuExitListener;
import players.Crew;
import players.actorveh;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.PayOffManager;
import rnrscenario.cScriptFuncs;
import rnrscenario.stage;
import rnrscenario.tech.Helper;
import rnrscenario.tech.SleepOnTime;
import rnrscr.Bar;
import rnrscr.drvscripts;
import rnrscr.trackscripts;

public final class victory_economy
extends stage {
    private final Object latch = new Object();
    private final boolean DEBUG_SHOWFINAL_MENU = false;
    private static final String SCENE = "gold_driver";
    private static final String BAR_NAME = "Oxnard_Bar_01";
    private boolean f_menu_closed = false;
    private boolean f_create_final_victory_menu = false;
    private int menu_result = 0;

    public victory_economy(Object monitor) {
        super(monitor, "victory_economy");
    }

    private void enterBar() {
        Bar.setCutSceneAmbientBar(true);
        eng.console("createbarpeople");
    }

    private void exitBar() {
        Bar.setCutSceneAmbientBar(false);
        eng.console("createbarpeople exit");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.lock();
        eng.startMangedFadeAnimation();
        eng.disableControl();
        vectorJ pos = eng.getControlPointPosition(BAR_NAME);
        actorveh car = Crew.getIgrokCar();
        car.teleport(pos);
        eng.unlock();
        Helper.waitTeleport();
        trs.PlaySceneXMLThreaded("justfade", false);
        eng.lock();
        long cam_scene = scenetrack.CreateSceneXML(SCENE, 2, null);
        this.enterBar();
        eng.unlock();
        new cSceneSuspendEventWaiter(cam_scene).waitEvent();
        eng.lock();
        VictoryMenu.createWinEconomy(new WinMenuClosed());
        boolean hasContest = rnrcore.Helper.hasContest();
        this.f_create_final_victory_menu = !hasContest;
        eng.unlock();
        while (true) {
            new SleepOnTime(100);
            Object object = this.latch;
            synchronized (object) {
                if (this.f_menu_closed) {
                    break;
                }
            }
        }
        eng.lock();
        PayOffManager.getInstance().makePayOff(PayOffManager.PAYOFF_NAMES[10]);
        eng.startMangedFadeAnimation();
        scenetrack.DeleteScene(cam_scene);
        this.exitBar();
        eng.unlock();
        switch (this.menu_result) {
            case 0: {
                Helper.makeComeInAndLeaveParking();
                drvscripts.playInsideCarFast(Crew.getIgrok(), car);
                eng.lock();
                eng.enableControl();
                eng.unlock();
                break;
            }
            case 1: {
                eng.lock();
                eng.enableControl();
                eng.unlock();
                Helper.waitGameWorldLoad();
                eng.lock();
                JavaEvents.SendEvent(23, 1, null);
                eng.unlock();
            }
        }
        rnrcore.Helper.debugSceneFinishedEvent();
    }

    class cSceneSuspendEventWaiter {
        long scene;
        private boolean toFinish = false;

        cSceneSuspendEventWaiter(long scene) {
            this.scene = scene;
            event.eventObject((int)scene + 1, this, "event");
            scenetrack.UpdateSceneFlags(scene, 1);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void event() {
            Object object = victory_economy.this.latch;
            synchronized (object) {
                this.toFinish = true;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void waitEvent() {
            boolean isFinished;
            do {
                new SleepOnTime(100);
                Object object = victory_economy.this.getSyncMonitor();
                synchronized (object) {
                    isFinished = this.toFinish;
                }
            } while (!isFinished);
        }
    }

    class TotalVictoryMenuClosed
    implements VictoryMenuExitListener {
        TotalVictoryMenuClosed() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void OnMenuExit(int result) {
            Object object = victory_economy.this.latch;
            synchronized (object) {
                victory_economy.this.menu_result = result;
                victory_economy.this.f_menu_closed = true;
            }
        }
    }

    class WinMenuClosed
    implements VictoryMenuExitListener {
        WinMenuClosed() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void OnMenuExit(int result) {
            Object object = victory_economy.this.latch;
            synchronized (object) {
                if (victory_economy.this.f_create_final_victory_menu) {
                    TotalVictoryMenu.createGameOverTotal(new TotalVictoryMenuClosed());
                } else {
                    victory_economy.this.menu_result = result;
                    victory_economy.this.f_menu_closed = true;
                }
            }
        }
    }
}

