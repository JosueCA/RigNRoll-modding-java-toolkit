/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import players.semitrailer;
import rnrcore.SCRcamera;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.SimplePresets;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscr.Helper;
import rnrscr.trackscripts;
import scriptActions.MakeCallAction;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=9)
public final class sc00860
extends stage {
    private static final int SEMITRAILER_SHIFT_RELATIVE_TO_HOST_CAR = 5;
    private static semitrailer semitrailerToDeliver;
    private static final String ANIMATION_CALL = "Topo chase customer call 3";
    private static final String FINISHED_CALL = "cb00860 finished";
    private static final String ANIMATION_FINISHED = "Topo chase animate dark truck ended";
    private static final String UNSUSPEND = "unsuspend";
    private final Object synchronizationMonitor = new Object();
    private boolean waitingForCbvFinish = false;
    private static boolean m_debugScene;

    public static void setSemitrailerToDeliver(semitrailer semitrailerToDeliver) {
        sc00860.semitrailerToDeliver = semitrailerToDeliver;
    }

    public static void setDebug() {
        m_debugScene = true;
    }

    public sc00860(Object monitor) {
        super(monitor, "sc00860");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unsuspend() {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            this.synchronizationMonitor.notify();
            this.waitingForCbvFinish = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void waitUntilCbvFinished() throws InterruptedException {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            while (this.waitingForCbvFinish) {
                this.synchronizationMonitor.wait();
            }
        }
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        traffic.enterChaseModeImmediately();
        actorveh playerCar = Crew.getIgrokCar();
        actorveh darkTruck = Crew.getMappedCar("DARK TRUCK");
        vectorJ pos = darkTruck.gPosition();
        vectorJ dir = darkTruck.gDir();
        dir.mult(-40.0);
        pos.oPlus(dir);
        playerCar.sVeclocity(0.0);
        playerCar.setHandBreak(true);
        Vector<actorveh> actors = new Vector<actorveh>();
        actors.add(playerCar);
        actorveh.aligncars(actors, pos, 0.0, 0.0, 1, 1);
        SCRcamera.jumpInCabin(true);
        this.waitingForCbvFinish = true;
        EventsControllerHelper.getInstance().addMessageListener(this, UNSUSPEND, FINISHED_CALL);
        eng.predefinedAnimationLaunchedFromJava(false);
        if (!m_debugScene) {
            EventsControllerHelper.messageEventHappened(ANIMATION_CALL);
        } else {
            MakeCallAction actionCall = new MakeCallAction(scenarioscript.script);
            actionCall.immediate = true;
            actionCall.name = "cb00860";
            actionCall.act();
            m_debugScene = false;
        }
        eng.unlock();
        try {
            this.waitUntilCbvFinished();
            eng.waitUntilEngineCanPlayScene();
        }
        catch (InterruptedException e) {
            System.err.print(e.getMessage());
        }
        eng.lock();
        if (null != semitrailerToDeliver) {
            vectorJ semitarailerPosition = playerCar.gPosition();
            semitarailerPosition.oMinus(playerCar.gMatrix().v1.getMultiplied(5.0));
            semitrailerToDeliver.setVelocityModule(0.0);
            semitrailerToDeliver.setMatrix(playerCar.gMatrix());
            semitrailerToDeliver.setPosition(semitarailerPosition);
            playerCar.attachSemitrailer(semitrailerToDeliver);
        }
        eng.disableControl();
        EventsControllerHelper.getInstance().removeMessageListener(this, UNSUSPEND, FINISHED_CALL);
        aiplayer bill = aiplayer.getScenarioAiplayer("SC_BILL_OF_LANDING");
        bill.bePassangerOfCar_simple(playerCar);
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(new SceneActorsPool("bill", bill.getModel()));
        SCRcamera.jumpInCabin(false);
        if (null != semitrailerToDeliver) {
            playerCar.attachSemitrailer(semitrailerToDeliver);
        }
        eng.unlock();
        SimplePresets pres = scenarioscript.script.prepareDarkTruckMatrix();
        Presets data = new Presets();
        data.car = playerCar;
        data.M = pres.M;
        data.P = pres.P;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00860_part1", true, pool, data);
        trs.PlaySceneXMLThreaded("00860_part2", true, data);
        trs.PlaySceneXMLThreaded("00860_part3", false, pool, data);
        eng.lock();
        bill.abondoneCar(playerCar);
        traffic.setTrafficMode(0);
        semitrailerToDeliver = null;
        EventsControllerHelper.messageEventHappened(ANIMATION_FINISHED);
        scenarioscript.script.finishChaseTopo();
        playerCar.teleport(eng.getNamedOfficePosition("office_OV_01"));
        playerCar.setHandBreak(false);
        eng.unlock();
        rnrscenario.tech.Helper.waitTeleport();
        Helper.restoreCameraToIgrokCar();
        trs.PlaySceneXMLThreaded("00861", false);
        eng.startMangedFadeAnimation();
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static {
        m_debugScene = false;
    }

    static class Presets {
        matrixJ M;
        vectorJ P;
        actorveh car;

        Presets() {
        }
    }
}

