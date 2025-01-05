/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import rnrcore.eng;
import rnrloggers.IntegerFilter;
import rnrloggers.PathHolder;
import rnrloggers.SimpleFormatter;
import rnrloggers.TextFileHandler;

public final class ScenarioLogger {
    private static final String LOGGER_NAME = "rnr.ScenarioLogger";
    private static final String LOGS_DIRECTORY = PathHolder.getWritablePath() + "warnings\\";
    private static final int XML_PARSER_MARK = 0;
    private static final int SCENARIO_MACHINE_MARK = 1;
    private static final ScenarioLogger ourInstance = new ScenarioLogger();
    private Logger log = null;

    public static ScenarioLogger getInstance() {
        return ourInstance;
    }

    private void createLoggingDirectory() {
        File logsDirectory = new File(LOGS_DIRECTORY);
        if (!logsDirectory.exists() && !logsDirectory.mkdir()) {
            System.err.println("failed to make directory for scenario system logs");
        }
    }

    private ScenarioLogger() {
        this.createLoggingDirectory();
        this.log = Logger.getLogger(LOGGER_NAME);
        this.log.setUseParentHandlers(false);
        try {
            TextFileHandler machineHandler = new TextFileHandler(LOGS_DIRECTORY + "scenarioGraphMachine.log", false, new SimpleFormatter());
            machineHandler.setFilter(new IntegerFilter(1));
            TextFileHandler parserHandler = new TextFileHandler(LOGS_DIRECTORY + "scenarioParsing.log", false, new SimpleFormatter());
            parserHandler.setFilter(new IntegerFilter(0));
            this.log.addHandler(machineHandler);
            this.log.addHandler(parserHandler);
            this.log.addHandler(new FileHandler(LOGS_DIRECTORY + "scenarioLog.xml"));
        }
        catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
            exception.printStackTrace(System.err);
        }
    }

    private static void externalLog(String message) {
        try {
            eng.writeLog(message);
        }
        catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    public void switchOffLoging() {
        this.log.setLevel(Level.OFF);
    }

    private void error(String message, Object param) {
        if (null != message) {
            this.log.log(Level.SEVERE, message, param);
            ScenarioLogger.externalLog(message);
            System.err.println(message);
        }
    }

    private void warning(String message, Object param) {
        if (null != message) {
            this.log.log(Level.WARNING, message, param);
            ScenarioLogger.externalLog(message);
        }
    }

    public void machineWarning(String message) {
        if (null != message) {
            this.warning(message, 1);
        }
    }

    public void parserWarning(String message) {
        if (null != message) {
            this.warning(message, 0);
        }
    }

    public void machineError(String message) {
        if (null != message) {
            this.error(message, 1);
        }
    }

    public void parserError(String message) {
        if (null != message) {
            this.error(message, 0);
        }
    }

    public void parserLog(Level level, String message) {
        if (null != level && null != message) {
            this.log.log(level, message, 0);
        }
    }

    public void machineLog(Level level, String message) {
        if (null != level && null != message) {
            this.log.log(level, message, 1);
        }
    }
}

