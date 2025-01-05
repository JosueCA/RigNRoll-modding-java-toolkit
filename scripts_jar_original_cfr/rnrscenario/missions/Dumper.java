/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import config.ApplicationFolders;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.Dumpable;
import rnrscenario.sctask;

final class Dumper
extends sctask {
    static final long serialVersionUID = 1L;
    private static final int RUN_FREQUENCY = 60;
    private static final int DUMP_SIZE = 2;
    private static final String FOLDER = ApplicationFolders.RCMDF() + ".\\warnings\\";
    private static final String FILE = "missionsDump.log";
    private boolean on = false;
    private List<Dumpable> toDump = new ArrayList<Dumpable>(2);

    Dumper() {
        super(60, false);
        super.start();
        File folderToLog = new File(FOLDER);
        if (!folderToLog.exists()) {
            folderToLog.mkdir();
        }
    }

    void addTask(Dumpable task) {
        if (null != task) {
            this.toDump.add(task);
        }
    }

    public void on() {
        this.on = true;
    }

    public void off() {
        this.on = false;
    }

    public void run() {
        if (!this.on) {
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(FOLDER + FILE);
            for (Dumpable dumpable : this.toDump) {
                dumpable.makeDump(out);
            }
        }
        catch (FileNotFoundException e) {
            MissionsLogger.getInstance().doLog("dumper failed to do dump: " + e.getMessage(), Level.WARNING);
        }
    }
}

