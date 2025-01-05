/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.consistency;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import rnrcore.eng;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.consistency.ScenarioStage;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ScenarioGarbageFinder {
    private static final int ERROR_REPORT_CAPACITY = 1024;
    private static final StringBuilder STRING_BUFFER = new StringBuilder(1024);
    private static boolean fatalOnGarbage = true;

    private ScenarioGarbageFinder() {
    }

    public static void setFatalOnGarbage(boolean fatalOnGarbage) {
        ScenarioGarbageFinder.fatalOnGarbage = fatalOnGarbage;
    }

    public static boolean isExpired(String context, Object targetToTest, ScenarioStage scenarioStage) {
        if (null == targetToTest || null == scenarioStage) {
            System.err.println("Invalid arguments came into ScenarioGarbageFinder.isExpired");
            return false;
        }
        try {
            if (ScenarioGarbageFinder.isExpired(targetToTest, scenarioStage)) {
                if (!eng.noNative && fatalOnGarbage) {
                    eng.fatal(String.format("Detected invalid object in scenario stage %d; context: %s, object class: %s", scenarioStage.getScenarioStage(), context, targetToTest.getClass()));
                }
                return true;
            }
        }
        catch (IllegalAccessException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
        catch (NoSuchFieldException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isExpired(Object targetToTest, ScenarioStage scenarioStage) throws IllegalAccessException, NoSuchFieldException {
        if (null == targetToTest || null == scenarioStage) {
            return false;
        }
        ScenarioClass scenarioMetaData = targetToTest.getClass().getAnnotation(ScenarioClass.class);
        if (null == scenarioMetaData) return false;
        if (-1 == scenarioMetaData.scenarioStage()) {
            Field fieldWithStage = targetToTest.getClass().getDeclaredField(scenarioMetaData.fieldWithDesiredStage());
            fieldWithStage.setAccessible(true);
            if (Integer.TYPE != fieldWithStage.getType()) throw new IllegalStateException("Illegal annotation on " + targetToTest.getClass() + " class: field " + fieldWithStage + "is not 'int'");
            int desiredScanrioStage = fieldWithStage.getInt(targetToTest);
            if (desiredScanrioStage == scenarioStage.getScenarioStage()) return false;
            return true;
        }
        if (scenarioMetaData.scenarioStage() == scenarioStage.getScenarioStage()) return false;
        return true;
    }

    private static void findOutOfDateScenarioObjects(String containerName, Iterable container, ScenarioStage scenarioStage, GarbageProcessor processor) {
        STRING_BUFFER.setLength(0);
        int objectsRemoved = 0;
        if (null != container && null != scenarioStage) {
            Iterator iter = container.iterator();
            while (iter.hasNext()) {
                Object objectFromContainer = iter.next();
                try {
                    if (!ScenarioGarbageFinder.isExpired(objectFromContainer, scenarioStage)) continue;
                    ++objectsRemoved;
                    STRING_BUFFER.append("\tobject's class=").append(objectFromContainer.getClass()).append('\n');
                    if (null == processor) continue;
                    processor.process(iter, objectFromContainer);
                }
                catch (IllegalAccessException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace(System.err);
                }
                catch (NoSuchFieldException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace(System.err);
                }
            }
        }
        if (0 < objectsRemoved) {
            STRING_BUFFER.insert(0, " contains garbage:\n");
            STRING_BUFFER.insert(0, containerName);
            STRING_BUFFER.append("total ").append(objectsRemoved).append(" objects were removed");
            if (fatalOnGarbage) {
                if (!eng.noNative) {
                    eng.fatal(STRING_BUFFER.toString());
                }
            } else {
                eng.err(STRING_BUFFER.toString());
            }
        }
    }

    public static List<Object> getOutOfDateScenarioObjects(String containerName, Iterable container, ScenarioStage scenarioStage) {
        final ArrayList<Object> garbageList = new ArrayList<Object>();
        ScenarioGarbageFinder.findOutOfDateScenarioObjects(containerName, container, scenarioStage, new GarbageProcessor(){

            public void process(Iterator containerIterator, Object garbage) {
                garbageList.add(garbage);
            }
        });
        return garbageList;
    }

    public static void deleteOutOfDateScenarioObjects(String containerName, Iterable container, ScenarioStage scenarioStage) {
        ScenarioGarbageFinder.findOutOfDateScenarioObjects(containerName, container, scenarioStage, new GarbageProcessor(){

            public void process(Iterator containerIterator, Object garbage) {
                containerIterator.remove();
            }
        });
    }

    private static interface GarbageProcessor {
        public void process(Iterator var1, Object var2);
    }
}

