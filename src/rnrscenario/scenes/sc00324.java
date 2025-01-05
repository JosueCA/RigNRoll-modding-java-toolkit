/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menuscript.AnswerMenu;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.loc;
import rnrcore.scenetrack;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.IDialogListener;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=2)
public final class sc00324
extends stage
implements IDialogListener {
    private final Object latch = new Object();
    private static final String[] SCENES = new String[]{"00324", "00324_menu", "00324_accept", "00324_reject"};
    private static final int SCENE_START = 0;
    private static final int SCENE_MENU = 1;
    private static final int SCENE_ACCEPT = 2;
    private static final int SCENE_REJECT = 3;
    private static final String METHOD_TO_CALL_WHEN_CHOOSE_MENU_CLOSED = "menuClosed";
    private static final String msg_ok = "Scenario Answer Ok";
    private static final String msg_cancel = "Scenario Answer Cancel";
    private static final String[] ANSWERS = new String[]{loc.getDialogName("NicA_SC00324_05"), loc.getDialogName("NicA_SC00324_06")};
    private static final int YES = 0;
    private static final int NO = 1;
    private boolean f_menu_closed = false;
    private int i_resultmenu = 1;
    private static boolean m_isDebugOn = false;
    private static boolean m_debugAnswer = false;

    public static void setDebugUnswer(boolean value) {
        m_isDebugOn = true;
        m_debugAnswer = value;
    }

    public sc00324(Object monitor) {
        super(monitor, "sc00324");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void menuClosed() {
        Object object = this.latch;
        synchronized (object) {
            this.f_menu_closed = true;
            this.latch.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void playSceneBody(cScriptFuncs automat) {
        eng.doWide(true);
        eng.disableControl();
        eng.startMangedFadeAnimation();
        rnrscenario.tech.Helper.makeComeOut();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded(SCENES[0], true);
        long sc = scenetrack.CreateSceneXML(SCENES[1], 5, null);
        EventsControllerHelper.getInstance().addMessageListener(this, METHOD_TO_CALL_WHEN_CHOOSE_MENU_CLOSED, "Scenario Answer Recived");
        eng.lock();
        if (m_isDebugOn) {
            this.f_menu_closed = true;
            m_isDebugOn = false;
            this.i_resultmenu = m_debugAnswer ? 0 : 1;
        } else {
            AnswerMenu.createAnswerMenu(ANSWERS, this);
        }
        eng.unlock();
        while (!this.f_menu_closed) {
            Object object = this.latch;
            synchronized (object) {
                try {
                    this.latch.wait();
                }
                catch (InterruptedException e) {
                    eng.writeLog("Error in sc00324.playSceneBody interrupted while waiting for user input.");
                    break;
                }
            }
        }
        scenetrack.DeleteScene(sc);
        if (this.i_resultmenu == 0) {
            eng.lock();
            EventsControllerHelper.messageEventHappened(msg_ok);
            eng.unlock();
            trs.PlaySceneXMLThreaded(SCENES[2], false);
        } else {
            eng.lock();
            EventsControllerHelper.messageEventHappened(msg_cancel);
            eng.unlock();
            trs.PlaySceneXMLThreaded(SCENES[3], false);
        }
        eng.startMangedFadeAnimation();
        EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_TO_CALL_WHEN_CHOOSE_MENU_CLOSED, "Scenario Answer Recived");
        rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.doWide(false);
        eng.enableControl();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }

    public void onAppear(String dialog_name) {
    }

    public void onNo(String dialog_name) {
        this.menuClosed();
        this.i_resultmenu = 1;
    }

    public void onYes(String dialog_name) {
        this.menuClosed();
        this.i_resultmenu = 0;
    }
}

