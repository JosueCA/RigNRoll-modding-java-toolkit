/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.DateData;
import menuscript.ScenarioBigRaceConfirmation;
import rnrcore.CoreTime;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.scenetrack;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscenario.tech.SleepOnTime;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=-2)
public final class sc01030
extends stage {
    private static final String method_menu_closed = "menuClosed";
    private final Object latch = new Object();
    private boolean f_menu_closed = false;
    private static boolean m_debugScene = false;
    private static boolean m_debugAnswerYes = false;
    private long m_menuCreated = 0L;

    public static void setDebugSceneAnswer(boolean value) {
        m_debugScene = true;
        m_debugAnswerYes = value;
    }

    public sc01030(Object monitor) {
        super(monitor, "sc01030");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void menuClosed() {
        Object object = this.latch;
        synchronized (object) {
            this.f_menu_closed = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void playSceneBody(cScriptFuncs automat) {
        long sc;
        trackscripts trs;
        block9: {
            eng.disableControl();
            rnrscenario.tech.Helper.makeParkAndComeOut("OxnardParking04", 0);
            trs = new trackscripts(this.getSyncMonitor());
            trs.PlaySceneXMLThreaded("01030_part1", true);
            trs.PlaySceneXMLThreaded("01030_part2", false);
            eng.lock();
            sc = scenetrack.CreateSceneXML("01030_part3", 5, null);
            EventsControllerHelper.getInstance().addMessageListener(this, method_menu_closed, "Scenario Answer Recived");
            this.m_menuCreated = ScenarioBigRaceConfirmation.createScenarioBigRaceConfirmationMenu("", new CoreTime(), "", new DateData(), 40);
            eng.unlock();
            int countSleepTimes = 0;
            do {
                ++countSleepTimes;
                new SleepOnTime(100);
                Object object = this.latch;
                synchronized (object) {
                    if (this.f_menu_closed) {
                        break block9;
                    }
                }
            } while (!m_debugScene || countSleepTimes <= 30);
            if (m_debugAnswerYes) {
                ScenarioBigRaceConfirmation.getLastMenu().onOk(this.m_menuCreated, null);
            } else {
                ScenarioBigRaceConfirmation.getLastMenu().onCancel(this.m_menuCreated, null);
            }
            m_debugScene = false;
        }
        eng.lock();
        scenetrack.DeleteScene(sc);
        eng.unlock();
        boolean acceptScene = ScenarioBigRaceConfirmation.gResult();
        if (acceptScene) {
            trs.PlaySceneXMLThreaded("01030_part4", false);
        } else {
            trs.PlaySceneXMLThreaded("01030_part5", false);
        }
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.enableControl();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

