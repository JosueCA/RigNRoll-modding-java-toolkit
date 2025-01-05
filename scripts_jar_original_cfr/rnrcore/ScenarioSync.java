/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.Resumer;
import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import rnrscr.SyncMonitors;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScenarioSync {
    private static final String SCENE_PACKAGE = "rnrscenario.scenes";
    private final Object monitor = new Object();
    private boolean torunscript;
    private boolean reserved = false;
    private Resumer resumer = new Resumer(SyncMonitors.getScenarioMonitor());
    private cScriptFuncs scenesPerformer;
    private Thread scriptrunningthreadrefference;
    private Integer currentScenarioStage = null;
    private String sceneToPlay = null;
    private static ScenarioSync instance = null;
    private static final int NO_CURRENT_SCENARIO_STAGE_REQUIRED = -1;

    private ScenarioSync() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ScenarioSync getInstance() {
        if (null == instance) {
            instance = new ScenarioSync();
            Object object = ScenarioSync.instance.monitor;
            synchronized (object) {
                ScenarioSync.instance.torunscript = false;
                ScenarioSync.instance.scenesPerformer = new cScriptFuncs(SyncMonitors.getScenarioMonitor(), SCENE_PACKAGE);
                ScenarioSync.instance.scriptrunningthreadrefference = new Thread(ScenarioSync.instance.scenesPerformer);
                ScenarioSync.instance.scriptrunningthreadrefference.start();
            }
        }
        return instance;
    }

    public static void gameWentInMainMenu() {
        ScenarioSync.dropSceneToPlay();
        ScenarioSync.getInstance().scenesPerformer.interruptCurrentScene();
        ScenarioSync.resumeScene();
    }

    public static void resumeScene() {
        SyncMonitors.setNotificationFlag(true);
        ScenarioSync.getInstance().resumer.resume();
    }

    public static void runscript() {
        ScenarioSync.getInstance().torunscript = true;
        boolean was_reserved = ScenarioSync.getInstance().reserved;
        ScenarioSync.getInstance().reserved = true;
        if (!was_reserved) {
            ScenarioSync.resumeScene();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pausegame() {
        Object object = this.monitor;
        synchronized (object) {
            if (!this.scenesPerformer.isWaiting()) {
                this.scenesPerformer.suspend();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resumegame() {
        Object object = this.monitor;
        synchronized (object) {
            if (this.scenesPerformer.isWaiting()) {
                SyncMonitors.setNotificationFlag(true);
                this.resumer.resume();
            }
        }
    }

    public static void interruptScriptRunningThread() {
        if (null != ScenarioSync.getInstance().scriptrunningthreadrefference) {
            ScenarioSync.getInstance().scriptrunningthreadrefference.interrupt();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Pair<String, Integer> extrudeSceneToPlay() {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            String sceneNameToReturn = ScenarioSync.getInstance().sceneToPlay;
            Integer currentScenarioStage = ScenarioSync.getInstance().currentScenarioStage;
            ScenarioSync.getInstance().sceneToPlay = null;
            ScenarioSync.getInstance().currentScenarioStage = null;
            return new Pair<String, Integer>(sceneNameToReturn, currentScenarioStage);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setPlayScene(String sceneName, int currentScenarioStage) {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            eng.log("setPlayScene is " + sceneName);
            if (null != sceneName) {
                ScenarioSync.getInstance().currentScenarioStage = 0 <= currentScenarioStage ? Integer.valueOf(currentScenarioStage) : null;
                ScenarioSync.getInstance().sceneToPlay = sceneName;
            }
            ScenarioSync.runscript();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void dropSceneToPlay() {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            ScenarioSync.getInstance().currentScenarioStage = null;
            ScenarioSync.getInstance().sceneToPlay = null;
        }
    }

    public static void setPlayScene(String sceneName) {
        ScenarioSync.setPlayScene(sceneName, -1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean needRunScript() {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            return ScenarioSync.getInstance().torunscript;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isScriptReserved() {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            return ScenarioSync.getInstance().reserved;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setReserved(boolean value) {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            ScenarioSync.getInstance().reserved = value;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setNeedToRunScript(boolean value) {
        Object object = ScenarioSync.getInstance().monitor;
        synchronized (object) {
            ScenarioSync.getInstance().torunscript = value;
        }
    }

    public static cScriptFuncs getSceneObject() {
        return ScenarioSync.getInstance().scenesPerformer;
    }
}

