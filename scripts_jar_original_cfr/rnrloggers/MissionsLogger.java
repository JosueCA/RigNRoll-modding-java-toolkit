/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rnrloggers.PathHolder;
import rnrloggers.SimpleFormatter;
import rnrloggers.TextFileHandler;

public class MissionsLogger {
    private static final String MISSIONS_LOGGER_LOGGER_NAME = "rnr.MissionsLogger";
    private static final String OUT_FOLDER = PathHolder.getWritablePath() + "warnings\\";
    private static MissionsLogger ourInstance = new MissionsLogger();
    private Logger log = Logger.getLogger("rnr.MissionsLogger");

    public static MissionsLogger getInstance() {
        return ourInstance;
    }

    private MissionsLogger() {
        this.log.setUseParentHandlers(false);
        try {
            File outFolder = new File(OUT_FOLDER);
            if (!outFolder.exists()) {
                outFolder.mkdir();
            }
            TextFileHandler handler = new TextFileHandler(OUT_FOLDER + "missions.log", false, new SimpleFormatter());
            this.log.addHandler(handler);
        }
        catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
            exception.printStackTrace(System.err);
        }
    }

    public void doLog(String message, Level level) {
        if (null != message && null != level) {
            this.log.log(level, message);
        }
    }
}

