/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.consistency;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import rnr.tech.Code0;
import rnr.tech.EmptyCode0;
import rnrloggers.ScriptsLogger;
import rnrscenario.consistency.ConsistancyCorruptedReporter;
import rnrscenario.consistency.StageChangedListener;
import xmlserialization.nxs.AnnotatedSerializable;
import xmlserialization.nxs.LoadFrom;
import xmlserialization.nxs.SaveTo;

public final class ScenarioStage
implements AnnotatedSerializable {
    public static final int SCENARIO_STAGE_UNDEFINED_MARKER = Integer.MIN_VALUE;
    public static final int SCENARIO_STAGE_FINISHED_SUCCESSFULLY_MARKER = 0x7FFFFFFE;
    public static final int SCENARIO_STAGE_FINISHED_UNSUCCESSFULLY_MARKER = 0x7FFFFFFD;
    public static final int SCENARIO_STAGE_UNLOADED_MARKER = Integer.MAX_VALUE;
    private static final String MY_SERIALIZATION_UID = "ScenarioStage";
    private static final int LISTENERS_INITIAL_CAPACITY = 7;
    private List<StageChangedListener> listeners = new ArrayList<StageChangedListener>(7);
    private final Object latch = new Object();
    @SaveTo(destinationNodeName="stage")
    @LoadFrom(sourceNodeName="stage")
    private int stageNumber = Integer.MIN_VALUE;
    private Code0 executeWhenScenarioFinished = new EmptyCode0();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addListener(StageChangedListener listener) {
        Object object = this.latch;
        synchronized (object) {
            if (null != listener) {
                this.listeners.add(listener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void scenarioStageUndefined() {
        Object object = this.latch;
        synchronized (object) {
            this.stageNumber = Integer.MIN_VALUE;
        }
    }

    public void scenarioFinished(boolean successful) {
        ScriptsLogger.getInstance().log(Level.INFO, 4, "scenario stopped at stage #" + this.stageNumber);
        this.setStageNumber(successful ? 0x7FFFFFFE : 0x7FFFFFFD);
        this.executeWhenScenarioFinished.execute();
    }

    public void setCallbackOnScenarioFinish(Code0 callback) {
        if (null != callback) {
            this.executeWhenScenarioFinished = callback;
        }
    }

    public void scenarioUnloaded() {
        ScriptsLogger.getInstance().log(Level.INFO, 4, "scenario stopped at stage #" + this.stageNumber);
        this.setStageNumber(Integer.MAX_VALUE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setStageNumber(int value) {
        Object object = this.latch;
        synchronized (object) {
            if (value < this.stageNumber) {
                ScriptsLogger.getInstance().log(Level.SEVERE, 4, "attempt to setup lower scenario stage value!");
                ConsistancyCorruptedReporter.scenarioBackwardMoving();
                return;
            }
            this.stageNumber = value;
            for (StageChangedListener listener : this.listeners) {
                listener.scenarioCheckPointReached(this);
            }
            ScriptsLogger.getInstance().log(Level.INFO, 4, "entered scenario stage #" + value);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getScenarioStage() {
        Object object = this.latch;
        synchronized (object) {
            return this.stageNumber;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isScenarioFinished() {
        Object object = this.latch;
        synchronized (object) {
            boolean finished = 0x7FFFFFFE == this.stageNumber;
            finished |= 0x7FFFFFFD == this.stageNumber;
            boolean bl = Integer.MAX_VALUE == this.stageNumber;
            return finished |= bl;
        }
    }

    public void finalizeDeserialization() {
        ScriptsLogger.getInstance().log(Level.INFO, 4, "scenario loaded at stage #" + this.stageNumber);
    }

    public static String getUid() {
        return MY_SERIALIZATION_UID;
    }

    public String getId() {
        return ScenarioStage.getUid();
    }

    public Object getLatch() {
        return this.latch;
    }
}

