/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.logging.Level;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrloggers.ScriptsLogger;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenarioscript;
import rnrscenario.stage;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=9)
public final class sc00730
extends stage {
    private static final String ANIMATION_FINISHED = "Topo chase animate Dakota ended";
    private static final String WHERE_TO_PLACE_PLAYER_CAR_WHILE_SCENE = "DakotaCrossRoadKeyPoint_OV_LB";
    private static vectorJ debugPosDakota = null;
    private static matrixJ debugMatrixDakota = null;
    private static boolean m_useDebugMatrices = false;

    public sc00730(Object monitor) {
        super(monitor, "sc00730");
    }

    public static void setDebugMatrices(vectorJ pos, matrixJ mat) {
        debugPosDakota = pos;
        debugMatrixDakota = mat;
        m_useDebugMatrices = true;
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 1");
        aiplayer bandDriver = aiplayer.getScenarioAiplayer("SC_BANDIT3");
        aiplayer bandShooter = aiplayer.getScenarioAiplayer("SC_BANDITJOE");
        actorveh banditCar = Crew.getMappedCar("ARGOSY BANDIT");
        bandDriver.beDriverOfCar(banditCar);
        bandShooter.bePassangerOfCar(banditCar);
        banditCar.setVisible();
        actorveh playerCar = Crew.getIgrokCar();
        cSpecObjects playerPlacement = specobjects.getInstance().GetLoadedNamedScenarioObject(WHERE_TO_PLACE_PLAYER_CAR_WHILE_SCENE);
        if (null != playerPlacement) {
            if (null != playerCar) {
                playerCar.sVeclocity(0.0);
                playerCar.setHandBreak(true);
                playerCar.sPosition(playerPlacement.position, playerPlacement.matrix);
            }
        } else {
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, "failed to find AO_ScriptObject: DakotaCrossRoadKeyPoint_OV_LB");
        }
        eng.unlock();
        Params p = new Params();
        if (m_useDebugMatrices) {
            p.M = debugMatrixDakota;
            p.P = debugPosDakota;
            m_useDebugMatrices = false;
        } else {
            p.M = new matrixJ(scenarioscript.script.getChaseTopoMatDakota());
            p.P = new vectorJ(scenarioscript.script.getChaseTopoPosDakota());
        }
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("00730", false, null, p);
        eng.lock();
        rnrscr.Helper.restoreCameraToIgrokCar();
        if (0 != banditCar.getAi_player()) {
            bandDriver.abondoneCar(banditCar);
            bandShooter.abondoneCar(banditCar);
        }
        eng.enableControl();
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 0");
        if (null != playerCar) {
            playerCar.setHandBreak(false);
        }
        scenarioscript.script.chaseTopoexitAnimationDakota();
        EventsControllerHelper.messageEventHappened(ANIMATION_FINISHED);
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    class Params {
        matrixJ M = new matrixJ();
        vectorJ P = new vectorJ();

        Params() {
        }
    }
}

