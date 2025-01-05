/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrcore.eng;
import rnrscenario.cScriptFuncs;
import scriptEvents.EventsController;
import scriptEvents.MessageEvent;

public abstract class stage {
    private final Object syncMonitor;
    private final String messageOnFinish;
    private static final Object interruptionLatch = new Object();
    private static boolean interrupted = false;
    private static boolean useDebugConditions = false;

    protected static boolean isInterrupted() {
        return interrupted;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void interrupt() {
        Object object = interruptionLatch;
        synchronized (object) {
            interrupted = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetInterruptionStatus() {
        Object object = interruptionLatch;
        synchronized (object) {
            interrupted = false;
        }
    }

    public static void performDebugPrePostConditions() {
        useDebugConditions = true;
    }

    public static void resetDebugPrePostConditions() {
        useDebugConditions = false;
    }

    public stage(Object monitor, String stageSymbolicName) {
        if (null == monitor || null == stageSymbolicName) {
            throw new IllegalArgumentException("monitor or stageSymbolicName is null");
        }
        this.syncMonitor = monitor;
        this.messageOnFinish = "finished scene " + stageSymbolicName;
    }

    public Object getSyncMonitor() {
        return this.syncMonitor;
    }

    protected abstract void playSceneBody(cScriptFuncs var1);

    protected void debugSetupPrecondition() {
    }

    protected void debugSetupPostcondition() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void playScene(cScriptFuncs automat) {
        if (useDebugConditions) {
            this.debugSetupPrecondition();
        }
        try {
            eng.disableEscKeyScenesSkip();
            this.playSceneBody(automat);
        }
        finally {
            eng.enableEscKeyScenesSkip();
            eng.blockEscapeMenu();
        }
        if (useDebugConditions) {
            this.debugSetupPostcondition();
        }
        if (interrupted) {
            return;
        }
        eng.lock();
        Object object = interruptionLatch;
        synchronized (object) {
            if (!interrupted) {
                EventsController.getInstance().eventHappen(new MessageEvent(this.messageOnFinish));
            }
        }
        eng.unlock();
    }
}

