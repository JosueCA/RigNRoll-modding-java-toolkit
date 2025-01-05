/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.PastFewDaysMenu;
import menuscript.VictoryMenu;
import menuscript.VictoryMenuExitListener;
import players.CarName;
import players.Crew;
import players.ScenarioPersonPassanger;
import players.actorveh;
import players.aiplayer;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.PayOffManager;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ChaseKoh;
import rnrscenario.scenarioscript;
import rnrscenario.scenes.sc03000;
import rnrscenario.stage;
import rnrscr.Helper;
import rnrscr.drvscripts;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=15)
public final class sc02070
extends stage {
    boolean menu_closed = false;
    private static final int TIME_MENU_SCENARIO_VICTORY_OFFSET = 28000;
    private static final int LAG = 100;
    private aiplayer cochCarDriver = null;

    public sc02070(Object monitor) {
        super(monitor, "sc02070");
    }

    protected void debugSetupPrecondition() {
        eng.lock();
        actorveh cochCar = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(), Crew.getIgrokCar().gPosition().oPlusN(new vectorJ(20.0, 20.0, 0.0)));
        Crew.addMappedCar("KOH", cochCar);
        this.cochCarDriver = new aiplayer("SC_KOHLOW");
        this.cochCarDriver.setModelCreator(new ScenarioPersonPassanger(), "koh");
        this.cochCarDriver.beDriverOfCar(cochCar);
        eng.unlock();
    }

    protected void debugSetupPostcondition() {
        eng.lock();
        this.cochCarDriver.abondoneCar(Crew.getMappedCar("KOH"));
        Crew.deactivateMappedCar("KOH");
        eng.unlock();
    }

    protected void playSceneBody(cScriptFuncs automat) {
        boolean to_break;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        actorveh carkoh = Crew.getMappedCar("KOH");
        carkoh.stop_autopilot();
        aiplayer player = null;
        if (ChaseKoh.getInstance() != null) {
            player = ChaseKoh.getInstance().getKohChased();
        } else if (null != this.cochCarDriver) {
            player = this.cochCarDriver;
        }
        if (player != null) {
            drvscripts.BlowSceneOtherPlayer(player, carkoh);
        }
        eng.unlock();
        trs.playSceneThreaded();
        eng.lock();
        if (ChaseKoh.getInstance() != null) {
            ChaseKoh.getInstance().endChase();
        }
        Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        PastFewDaysMenu.create();
        eng.unlock();
        trs.playSceneThreaded();
        eng.lock();
        eng.forceSwitchRain(false);
        eng.unlock();
        long scene_prize = trackscripts.CreateSceneXMLCycle("02080", null, null);
        this.simplewaitFor(28000);
        scenarioscript.script.winScenario();
        VictoryMenu.createWinSocial(new VictoryMenuExitListener(){

            public void OnMenuExit(int result) {
                sc02070.this.menu_closed = true;
            }
        });
        do {
            this.simplewaitFor(100);
            eng.lock();
            to_break = this.menu_closed;
            eng.unlock();
        } while (!to_break);
        eng.lock();
        scenetrack.StopScene(scene_prize);
        scenetrack.DeleteScene(scene_prize);
        Helper.restoreCameraToIgrokCar();
        PayOffManager.getInstance().makePayOff(PayOffManager.PAYOFF_NAMES[11]);
        eng.forceSwitchRain(true);
        eng.unlock();
        new sc03000(this.getSyncMonitor()).playSceneBody(automat);
    }

    void simplewaitFor(int timemillesec) {
        try {
            Thread.sleep(timemillesec);
        }
        catch (InterruptedException e) {
            eng.writeLog("Script Error. Stage sc00324 error.");
        }
    }
}

