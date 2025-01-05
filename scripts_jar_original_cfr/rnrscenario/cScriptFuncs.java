/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import rnrcore.ScenarioSync;
import rnrcore.eng;
import rnrscenario.stage;
import scenarioUtils.Pair;
import scriptEvents.EventsControllerHelper;

public final class cScriptFuncs
implements Runnable {
    private final Object monitor;
    private final Object criticalSectionMonitor = new Object();
    private boolean needToSuspend = false;
    private boolean waiting = false;
    private String packageWithSceneClasses = null;

    public boolean toRun() {
        return ScenarioSync.needRunScript() || ScenarioSync.isScriptReserved();
    }

    public void reset() {
        ScenarioSync.setNeedToRunScript(false);
        ScenarioSync.setReserved(false);
    }

    public cScriptFuncs(Object syncronizationMonitor, String scenePackage) {
        if (null == syncronizationMonitor) {
            throw new IllegalArgumentException("syncronizationMonitor mast be valid non-null reference");
        }
        this.monitor = syncronizationMonitor;
        this.packageWithSceneClasses = scenePackage;
    }

    public void interruptCurrentScene() {
        stage.interrupt();
        eng.forceInterruptLock();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void tryToSuspendOnce() throws InterruptedException {
        Object object = this.monitor;
        synchronized (object) {
            if (this.needToSuspend) {
                this.monitor.wait();
                this.needToSuspend = false;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void suspend() {
        Object object = this.monitor;
        synchronized (object) {
            this.needToSuspend = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isWaiting() {
        Object object = this.criticalSectionMonitor;
        synchronized (object) {
            return this.waiting;
        }
    }

    public void run() {
        try {
            while (true) {
                this.eventhandler();
                this.tryToSuspendOnce();
            }
        }
        catch (InterruptedException e) {
            if (!eng.noNative) {
                eng.writeLog("Script Error: eventhandler was interrupted");
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void eventhandler() throws InterruptedException {
        Object object;
        block17: {
            if (this.toRun()) {
                this.reset();
                try {
                    eng.waitUntilEngineCanPlayScene();
                    Pair<String, Integer> sceneToPlayParams = ScenarioSync.extrudeSceneToPlay();
                    String sceneToPlay = sceneToPlayParams.getFirst();
                    Integer currentScenarioStage = sceneToPlayParams.getSecond();
                    if (null == sceneToPlay) break block17;
                    try {
                        stage launchedScene = this.playScene(sceneToPlay, currentScenarioStage);
                        if (null == launchedScene) {
                            System.err.println("Failed to create instance of scene " + sceneToPlay + ". Possible not enough parameters to constructor.");
                        }
                    }
                    catch (Exception exception) {
                        System.err.println("exception has occured while trying to run scene whith name == " + sceneToPlay);
                        System.err.println(exception.getLocalizedMessage());
                        exception.printStackTrace(System.err);
                    }
                }
                finally {
                    eng.predefinedAnimationLaunchedFromJava(false);
                    stage.resetInterruptionStatus();
                    stage.resetDebugPrePostConditions();
                }
            }
        }
        if (!ScenarioSync.isScriptReserved()) {
            object = this.criticalSectionMonitor;
            synchronized (object) {
                this.waiting = true;
            }
            object = this.monitor;
            synchronized (object) {
                this.monitor.wait();
            }
        }
        object = this.criticalSectionMonitor;
        synchronized (object) {
            this.waiting = false;
        }
    }

    private stage playScene(String sceneToPlay, Integer currentScenarioStage) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> sceneClass = Class.forName(this.packageWithSceneClasses + '.' + sceneToPlay);
        Constructor<?>[] creationStrategies = sceneClass.getConstructors();
        stage scene = null;
        for (Constructor<?> creator : creationStrategies) {
            if (null != currentScenarioStage) {
                if (2 == creator.getParameterTypes().length && Object.class == creator.getParameterTypes()[0] && Integer.TYPE == creator.getParameterTypes()[1]) {
                    creator.setAccessible(true);
                    scene = (stage)creator.newInstance(this.monitor, currentScenarioStage);
                }
            } else if (1 == creator.getParameterTypes().length && Object.class == creator.getParameterTypes()[0]) {
                creator.setAccessible(true);
                scene = (stage)creator.newInstance(this.monitor);
            }
            if (null == scene) continue;
            scene.playScene(this);
            EventsControllerHelper.messageEventHappened(sceneToPlay + " finished");
            break;
        }
        return scene;
    }
}

