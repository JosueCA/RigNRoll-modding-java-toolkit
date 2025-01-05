/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import rnrloggers.OnlyMessageOutFormatter;
import rnrloggers.PathHolder;
import rnrloggers.SimpleFormatter;
import rnrloggers.TextFileHandler;
import scenarioAnalysis.DynamicScenarioAnalyzer;
import scenarioAnalysis.StaticScenarioAnalyzer;

public final class ScenarioAnalysisLogger {
    private static final String LOGGER_NAME = "rnr.ScenarioAnalysisLogger";
    private static final String LOGS_DIRECTORY = PathHolder.getWritablePath() + "warnings\\";
    private static final ScenarioAnalysisLogger ourInstance = new ScenarioAnalysisLogger();
    private Logger log = Logger.getLogger("rnr.ScenarioAnalysisLogger");

    public static ScenarioAnalysisLogger getInstance() {
        return ourInstance;
    }

    private ScenarioAnalysisLogger() {
        this.log.setUseParentHandlers(false);
        try {
            this.log.addHandler(new TextFileHandler(LOGS_DIRECTORY + "scenarioAnalisys.log", false, new SimpleFormatter()));
            TextFileHandler staticAnalysisLogHandler = new TextFileHandler(LOGS_DIRECTORY + "staticScenarioAnalisys.log", false, new OnlyMessageOutFormatter());
            TextFileHandler dynamicAnalysisLogHandler = new TextFileHandler(LOGS_DIRECTORY + "dynamicScenarioAnalisys.log", false, new OnlyMessageOutFormatter());
            staticAnalysisLogHandler.setFilter(new Filter(){

                public boolean isLoggable(LogRecord record) {
                    return 0 == StaticScenarioAnalyzer.class.getName().compareToIgnoreCase(record.getSourceClassName()) && record.getLevel().intValue() >= Level.INFO.intValue();
                }
            });
            dynamicAnalysisLogHandler.setFilter(new Filter(){

                public boolean isLoggable(LogRecord record) {
                    return 0 == DynamicScenarioAnalyzer.class.getName().compareToIgnoreCase(record.getSourceClassName());
                }
            });
            this.log.addHandler(staticAnalysisLogHandler);
            this.log.addHandler(dynamicAnalysisLogHandler);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Logger getLog() {
        return this.log;
    }
}

