/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.scenarioscript;
import scenarioUtils.AdvancedClass;
import scriptActions.ScriptAction;

public class CreateRace
extends ScriptAction {
    public String name = "no class";
    public String race = "no name";
    private static final int RACE_TYPE = 3;

    public CreateRace() {
        super(8);
    }

    public void act() {
        try {
            AdvancedClass advancedClazz = new AdvancedClass(this.name, "rnrscenario.controllers");
            Constructor constructor = advancedClazz.getConstructor(String.class, Integer.TYPE, ScenarioHost.class);
            constructor.newInstance(this.race, 3, scenarioscript.script);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace(System.err);
            ScenarioLogger.getInstance().machineLog(Level.WARNING, e.getMessage());
        }
        catch (InstantiationException e) {
            e.printStackTrace(System.err);
            ScenarioLogger.getInstance().machineLog(Level.WARNING, e.getMessage());
        }
        catch (InvocationTargetException e) {
            e.printStackTrace(System.err);
            ScenarioLogger.getInstance().machineLog(Level.WARNING, e.getMessage());
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace(System.err);
            ScenarioLogger.getInstance().machineLog(Level.WARNING, e.getMessage());
        }
        catch (IllegalAccessException e) {
            e.printStackTrace(System.err);
            ScenarioLogger.getInstance().machineLog(Level.WARNING, e.getMessage());
        }
    }
}

