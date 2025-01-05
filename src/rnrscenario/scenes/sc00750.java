/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Collide;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.SimplePresets;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscr.cSpecObjects;
import rnrscr.drvscripts;
import rnrscr.specobjects;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=9)
public final class sc00750
extends stage {
    private static final String ANIMATION_FINISHED = "Topo chase animate punkt a ended";

    public sc00750(Object monitor) {
        super(monitor, "sc00750");
    }

    SimplePresets makePresets() {
        matrixJ punktAM = null;
        vectorJ punktAV = null;
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        if (crossroad != null && crossroad.name.compareToIgnoreCase("KeyPoint_750") == 0) {
            vectorJ pos2;
            punktAM = crossroad.matrix;
            vectorJ pos1 = crossroad.position.oPlusN(new vectorJ(0.0, 0.0, 100.0));
            punktAV = Collide.collidePoint(pos1, pos2 = crossroad.position.oPlusN(new vectorJ(0.0, 0.0, -100.0)));
            if (punktAV == null) {
                punktAV = crossroad.position;
            }
            vectorJ shift_scene = new vectorJ(crossroad.matrix.v0);
            shift_scene.mult(-20.0);
            punktAV.oPlus(shift_scene);
        }
        return new SimplePresets(punktAV, punktAM);
    }

    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("justfade", false);
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        car.sVeclocity(0.0);
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        if (null != crossroad && 0 == "KeyPoint_750".compareTo(crossroad.name)) {
            vectorJ shift = new vectorJ(crossroad.matrix.v0);
            shift.mult(-5.0);
            vectorJ pos_to_place_our_car = crossroad.position.oPlusN(shift);
            car.sPosition(pos_to_place_our_car);
            car.sOnTheRoadLaneAndStop(1);
        }
        drvscripts.outCabinState(car).launch();
        drvscripts.outCabinState(Crew.getMappedCar("DOROTHY")).launch();
        drvscripts.outCabinState(Crew.getMappedCar("KOH")).launch();
        eng.unlock();
        trs.PlaySceneXMLThreaded("00750", false, this.makePresets());
        eng.lock();
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        actorveh car1 = Crew.getIgrokCar();
        drvscripts.playInsideCarFast(Crew.getIgrok(), car1);
        scenarioscript.script.chaseTopoexitAnimationPunktA();
        EventsControllerHelper.messageEventHappened(ANIMATION_FINISHED);
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

