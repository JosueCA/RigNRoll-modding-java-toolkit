/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import menu.JavaEvents;
import players.CarName;
import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.INativeMessageEvent;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=13)
public class sc01540_base
extends stage {
    private static final String MESSAGE = "1540 finished";
    private static final String POSITION = "Red Rock Canyon Scene";
    private static final String[] MESSAGE_EVENTS = new String[]{"Blow1", "Blow2"};
    private actorveh mathewCar = null;
    private String sceneName;

    private void createCar() {
        matrixJ M = new matrixJ();
        vectorJ pos = rnrscr.Helper.getCurrentPosition();
        pos.oPlus(new vectorJ(0.0, 300.0, 0.0));
        this.mathewCar = eng.CreateCarForScenario(CarName.CAR_MATHEW_DEAD, M, pos);
        Crew.addMappedCar("MAT", this.mathewCar);
    }

    public sc01540_base(Object monitor, String scenename) {
        super(monitor, "sc" + scenename);
        this.sceneName = scenename;
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        this.createCar();
        eng.disableControl();
        traffic.enterCutSceneModeImmediately();
        actorveh playerCar = Crew.getIgrokCar();
        playerCar.registerCar("ourcar");
        this.mathewCar.registerCar("matcar");
        params data = new params();
        data.M = new matrixJ();
        data.M.v0.mult(-1.0);
        data.M.v1.mult(-1.0);
        data.P = eng.getControlPointPosition(POSITION);
        if (null == data.P) {
            data.P = new vectorJ();
        }
        for (String MESSAGE_EVENT : MESSAGE_EVENTS) {
            Helper.addNativeEventListener(new Blow(MESSAGE_EVENT, this.mathewCar.getCar()));
        }
        Helper.addNativeEventListener(new Repair("RepairMatCar"));
        eng.unlock();
        trs.PlaySceneXMLThreaded(this.sceneName, false, data);
        eng.lock();
        Vector<actorveh> alllignedcars = new Vector<actorveh>();
        alllignedcars.add(playerCar);
        actorveh.aligncars(alllignedcars, playerCar.gPosition(), 1.0, 1.0, 1, 1);
        this.mathewCar.deactivate();
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        traffic.setTrafficMode(0);
        EventsControllerHelper.messageEventHappened(MESSAGE);
        eng.unlock();
    }

    private final class Repair
    implements INativeMessageEvent {
        private String message;

        Repair(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public void onEvent(String message) {
            JavaEvents.SendEvent(43, 0, sc01540_base.this.mathewCar);
        }

        public boolean removeOnEvent() {
            return true;
        }
    }

    private static final class Blow
    implements INativeMessageEvent {
        private final String message;
        private final int car;

        Blow(String message, int car) {
            this.message = message;
            this.car = car;
        }

        public String getMessage() {
            return this.message;
        }

        public void onEvent(String message) {
            eng.BlowCar(this.car);
        }

        public boolean removeOnEvent() {
            return true;
        }
    }

    static class params {
        matrixJ M;
        vectorJ P;

        params() {
        }
    }
}

