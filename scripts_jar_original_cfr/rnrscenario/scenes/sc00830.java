/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import players.semitrailer;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscenario.tech.SleepOnTime;
import rnrscr.drvscripts;
import rnrscr.parkingplace;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=9)
public final class sc00830
extends stage {
    private static final String ANIMATION_FINISHED = "Topo chase animate punkt b ended";
    private static boolean m_debugScene = false;

    public static void setDebugScene() {
        m_debugScene = true;
    }

    public sc00830(Object monitor) {
        super(monitor, "sc00830");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        semitrailer trailer2;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        actorveh car = Crew.getIgrokCar();
        if (!m_debugScene) {
            trs.PlaySceneXMLThreaded("justfade", false);
            eng.lock();
            eng.disableControl();
            vectorJ posTS = eng.getControlPointPosition("CP_LB_TS");
            car.sVeclocity(car.gVelocity().length() + 10.0);
            car.autopilotTo(posTS);
            scenarioscript.script.parkOnPunktB();
            eng.unlock();
        } else {
            eng.lock();
            parkingplace parking = parkingplace.findParkingByName("PK_LA_LB_01", eng.getControlPointPosition("CP_LB_TS"));
            car.makeParking(parking, 4);
            eng.unlock();
        }
        m_debugScene = false;
        new SleepOnTime(4000);
        rnrscenario.tech.Helper.waitParked(car);
        eng.lock();
        car.setHandBreak(true);
        eng.unlock();
        trs.PlaySceneXMLThreaded("justfade", false);
        drvscripts.outCabinState(car).launch();
        trs.PlaySceneXMLThreaded("00830", false, scenarioscript.script.preparePunctBMatrix());
        eng.lock();
        rnrscr.Helper.restoreCameraToIgrokCar();
        actorveh car1 = Crew.getIgrokCar();
        drvscripts.playInsideCarFast(Crew.getIgrok(), car1);
        car1.leaveParking();
        actorveh car1num = Crew.getMappedCar("DOROTHY");
        actorveh car2num = Crew.getMappedCar("KOH");
        semitrailer trailer1 = car1num.querryTrailer();
        if (trailer1 != null) {
            trailer1.delete();
        }
        if ((trailer2 = car2num.querryTrailer()) != null) {
            trailer2.delete();
        }
        car1num.deactivate();
        car2num.deactivate();
        car.setHandBreak(false);
        scenarioscript.script.exitAnimation_punktB();
        EventsControllerHelper.messageEventHappened(ANIMATION_FINISHED);
        eng.enableControl();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

