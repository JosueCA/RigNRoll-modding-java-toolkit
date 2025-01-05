/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.menues;
import players.Crew;
import players.semitrailer;
import rnrcore.Helper;
import rnrcore.INativeMessageEvent;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.CursedHiWay;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01370
extends stage {
    public static String MESSAGE = "Blow";

    public sc01370(Object monitor) {
        super(monitor, "sc01370");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        semitrailer semi = Crew.getIgrokCar().querryTrailer();
        if (semi != null) {
            Crew.getIgrokCar().deattach(semi);
        }
        Crew.getIgrokCar().registerCar("ourcar");
        vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
        Data data = new Data();
        data.P = pos;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        Helper.addNativeEventListener(new Blow(MESSAGE, Crew.getIgrokCar().getCar()));
        eng.unlock();
        trs.PlaySceneXMLThreaded("01370", false, data);
        eng.lock();
        CursedHiWay.finishCursedHiWay();
        menues.gameoverMenu();
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Blow
    implements INativeMessageEvent {
        static final long serialVersionUID = 0L;
        private String message;
        private int car;

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

    static class Data {
        vectorJ P;
        matrixJ M = new matrixJ();

        Data() {
        }
    }
}

