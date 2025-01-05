/*
 * Decompiled with CFR 0.151.
 */
package scenarioAnalysis;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import scenarioUtils.OnlyMessageOutFormatter;
import scenarioUtils.SimpleFormatter;
import scenarioUtils.TextFileHandler;

public final class ScenarioAnalysisLogger {
    private static final ScenarioAnalysisLogger ourInstance = new ScenarioAnalysisLogger();
    private static final String LOGGER_NAME = "rnr.ScenarioAnalysisLogger";
    private Logger log = Logger.getLogger("rnr.ScenarioAnalysisLogger");

    public static ScenarioAnalysisLogger getInstance() {
        return ourInstance;
    }

    private ScenarioAnalysisLogger() {
        this.log.setUseParentHandlers(false);
        try {
            this.log.addHandler(new TextFileHandler(".\\warnings\\scenarioAnalisys.log", false, new SimpleFormatter()));
            TextFileHandler staticAnalysisLogHandler = new TextFileHandler(".\\warnings\\staticScenarioAnalisys.log", false, new OnlyMessageOutFormatter());
            staticAnalysisLogHandler.setFilter(new Filter(){

                public boolean isLoggable(LogRecord record) {
                    return Level.INFO == record.getLevel();
                }
            });
            this.log.addHandler(staticAnalysisLogHandler);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    final Logger getLog() {
        return this.log;
    }
}

