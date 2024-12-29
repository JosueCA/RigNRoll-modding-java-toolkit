// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import rnrscenario.scrun;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class sctaskmanager
implements StageChangedListener {
    private ArrayList<scrun> tasks = new ArrayList();
    private ArrayList<scrun> new_tasks = new ArrayList();
    private ScenarioStage scenarioStageChange = null;
    private boolean m_isRunning = false;
    private boolean _toprint = true;

    public void gameDeinitLaunched() {
        sctaskmanager.clearTasks(this.tasks);
        sctaskmanager.clearTasks(this.new_tasks);
    }

    private static void clearTasks(List<scrun> tasks) {
        Iterator<scrun> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            scrun task = taskIterator.next();
            if (null == task || task.couldSurviveDuringGameDeinit()) continue;
            taskIterator.remove();
        }
    }

    public void run() {
        this.m_isRunning = true;
        boolean rem = this._toprint;
        this._toprint = !this.tasks.isEmpty();
        for (scrun task : this.tasks) {
            if (task.marked()) continue;
            task.mark(true);
            if (!task.started() || task.finished()) continue;
            task.run();
        }
        if (!this.new_tasks.isEmpty()) {
            this.tasks.addAll(this.new_tasks);
            this.new_tasks.clear();
            this.m_isRunning = false;
            this.run();
            return;
        }
        Iterator<scrun> iter = this.tasks.iterator();
        while (iter.hasNext()) {
            scrun ob = iter.next();
            ob.mark(false);
            if (!ob.finished()) continue;
            iter.remove();
        }
        if (null != this.scenarioStageChange) {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.tasks, this.scenarioStageChange);
            this.scenarioStageChange = null;
        }
        this.m_isRunning = false;
        this._toprint = rem;
    }

    public void add(scrun ob) {
        this.new_tasks.add(ob);
    }

    public void remove(scrun ob) {
        if (!this.m_isRunning) {
            this.tasks.remove(ob);
            this.new_tasks.remove(ob);
        } else {
            ob.finish();
        }
    }

    public void finish() {
    }

    public void start() {
    }

    public boolean finished() {
        return false;
    }

    public boolean started() {
        return false;
    }

    // @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        if (this.m_isRunning) {
            this.scenarioStageChange = scenarioStage;
        } else {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.tasks, scenarioStage);
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.new_tasks, scenarioStage);
        }
    }
}
