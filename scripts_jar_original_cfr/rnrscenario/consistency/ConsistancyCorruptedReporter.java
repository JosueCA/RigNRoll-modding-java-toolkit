/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.consistency;

import java.util.logging.Level;
import rnrcore.eng;
import rnrloggers.ScriptsLogger;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.consistency.ScenarioConsistencyCorruptedException;
import rnrscenario.scenarioscript;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ConsistancyCorruptedReporter {
    private static final int ERROR_MESSAGE_CAPACITY = 1024;
    private static final StringBuilder STRING_DUFFER = new StringBuilder(1024);
    private static final int ENCLOSING_METHOD_INDEX = 3;
    private static final Object latch = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void writeStackTraceInfoIntoBuffer() {
        Object object = latch;
        synchronized (object) {
            StackTraceElement[] stackTrace;
            for (StackTraceElement stackTraceElement : stackTrace = Thread.currentThread().getStackTrace()) {
                STRING_DUFFER.append('\t').append(stackTraceElement).append('\n');
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void scenarioBackwardMoving() {
        Object object = latch;
        synchronized (object) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("backward scenario moving");
            }
            STRING_DUFFER.setLength(0);
            STRING_DUFFER.append("scenario corrupted (backward moving)\n");
            STRING_DUFFER.append("stack trace: \n");
            ConsistancyCorruptedReporter.writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal("Scenario state corruption detected: attempt to move state backward!");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void undeletedTrashFound(Class containerClass, Class<?> storedElementsType) {
        Object object = latch;
        synchronized (object) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("trash found in container");
            }
            STRING_DUFFER.setLength(0);
            ScenarioClass scenarioMetaData = storedElementsType.getAnnotation(ScenarioClass.class);
            assert (null != scenarioMetaData);
            STRING_DUFFER.append("scenario corrupted (undeleted reference found in container)\n");
            STRING_DUFFER.append("container class: ").append(containerClass.getName()).append('\n');
            STRING_DUFFER.append("reference class: ").append(storedElementsType.getName()).append('\n');
            STRING_DUFFER.append("current scenario stage: ").append(scenarioscript.script.getStage().getScenarioStage()).append('\n');
            STRING_DUFFER.append("desired scenario stage: ").append(scenarioMetaData.scenarioStage()).append('\n');
            STRING_DUFFER.append("stack trace: \n");
            ConsistancyCorruptedReporter.writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal("Scenario state corruption detected: garbage listeners found!");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void methodCalledNotInTime(String message) {
        Object object = latch;
        synchronized (object) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("method called not in time");
            }
            STRING_DUFFER.setLength(0);
            String badMethodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            STRING_DUFFER.append("scenario corrupted (method called not in time)\n");
            STRING_DUFFER.append("message: ").append(message).append('\n');
            STRING_DUFFER.append("method: ").append(badMethodName).append('\n');
            STRING_DUFFER.append("current scenario stage: ").append(scenarioscript.script.getStage().getScenarioStage()).append('\n');
            STRING_DUFFER.append("stack trace: \n");
            ConsistancyCorruptedReporter.writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal("Scenario state corruption detected: method called not in time! Futher game working is not guaranteed.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void methodCalledNotInTime(Class<?> clazz) {
        Object object = latch;
        synchronized (object) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("method called not in time");
            }
            STRING_DUFFER.setLength(0);
            String badMethodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            ScenarioClass scenarioMetaData = clazz.getAnnotation(ScenarioClass.class);
            assert (null != scenarioMetaData);
            STRING_DUFFER.append("scenario corrupted (method called not in time)\n");
            STRING_DUFFER.append("class: ").append(clazz.getName()).append('\n');
            STRING_DUFFER.append("method: ").append(badMethodName).append('\n');
            STRING_DUFFER.append("current scenario stage: ").append(scenarioscript.script.getStage().getScenarioStage()).append('\n');
            STRING_DUFFER.append("desired scenario stage: ").append(scenarioMetaData.scenarioStage()).append('\n');
            STRING_DUFFER.append("stack trace: \n");
            ConsistancyCorruptedReporter.writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal("Scenario state corruption detected: method called not in time! Futher game working is not guaranteed.");
        }
    }
}

