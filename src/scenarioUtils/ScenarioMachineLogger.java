/*
 * Decompiled with CFR 0.151.
 */
package scenarioUtils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import rnrcore.eng;
import scenarioUtils.SimpleFormatter;
import scenarioUtils.TextFileHandler;

public class ScenarioMachineLogger {
    private static ScenarioMachineLogger ourInstance = new ScenarioMachineLogger();
    private Logger log = Logger.getLogger("rnr.ScenarioLogger");
    private static final String LOGGER_NAME = "rnr.ScenarioLogger";

    public static ScenarioMachineLogger getInstance() {
        return ourInstance;
    }

    private ScenarioMachineLogger() {
        this.log.setUseParentHandlers(false);
        try {
            TextFileHandler handler = new TextFileHandler(".\\warnings\\scenarioGraphMachineLog.log", false, new SimpleFormatter());
            handler.setFilter(new Filter(){

                public boolean isLoggable(LogRecord record) {
                    return true;
                }
            });
            this.log.addHandler(handler);
            this.log.addHandler(new FileHandler(".\\warnings\\scenarioGraphMachineLog.xml"));
        }
        catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
            exception.printStackTrace(System.err);
        }
    }

    private void externalLog(String message) {
        try {
            eng.writeLog(message);
        }
        catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    public Logger getLog() {
        return this.log;
    }

    public void switchOffLoging() {
        this.log.setLevel(Level.OFF);
    }

    public void warning(String message) {
        this.log.warning(message);
        this.externalLog(message);
    }

    public void error(String message) {
        this.log.severe(message);
        this.externalLog(message);
        System.err.println(message);
    }
}

