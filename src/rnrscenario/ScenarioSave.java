// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario;

import rnrscenario.Tutorial;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import rnrscenario.controllers.chaseTopo;
import rnrscenario.scrun;
import rnrscenario.sctaskmanager;

public class ScenarioSave
implements StageChangedListener {
    private static ScenarioSave instance = null;
    private final sctaskmanager tasks_on_0sec = new sctaskmanager();
    private final sctaskmanager tasks_on_3sec = new sctaskmanager();
    private final sctaskmanager tasks_on_60sec = new sctaskmanager();
    private final sctaskmanager tasks_on_600sec = new sctaskmanager();
    chaseTopo CHASETOPO = null;
    Tutorial tutor = new Tutorial();

    public static ScenarioSave getInstance() {
        if (null == instance) {
            instance = new ScenarioSave();
        }
        return instance;
    }

    private ScenarioSave() {
    }

    public void gameDeinitLaunched() {
        this.tasks_on_0sec.gameDeinitLaunched();
        this.tasks_on_3sec.gameDeinitLaunched();
        this.tasks_on_60sec.gameDeinitLaunched();
        this.tasks_on_600sec.gameDeinitLaunched();
    }

    void run_0() {
        this.tasks_on_0sec.run();
    }

    void run_3() {
        this.tasks_on_3sec.run();
    }

    void run_60() {
        this.tasks_on_60sec.run();
    }

    void run_600() {
        this.tasks_on_600sec.run();
    }

    void addTaskOnEveryFrame(scrun task) {
        this.tasks_on_0sec.add(task);
    }

    void addTaskOn3Seconds(scrun task) {
        this.tasks_on_3sec.add(task);
    }

    void addTaskOn60Seconds(scrun task) {
        this.tasks_on_60sec.add(task);
    }

    void addTaskOn600Seconds(scrun task) {
        this.tasks_on_600sec.add(task);
    }

    void removeTaskOnEveryFrame(scrun task) {
        this.tasks_on_0sec.remove(task);
    }

    void removeTaskOn3Seconds(scrun task) {
        this.tasks_on_3sec.remove(task);
    }

    void removeTaskOn60Seconds(scrun task) {
        this.tasks_on_60sec.remove(task);
    }

    void removeTaskOn600Seconds(scrun task) {
        this.tasks_on_600sec.remove(task);
    }

    public void setChaseTopo(chaseTopo object) {
        this.CHASETOPO = object;
    }

    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        this.tasks_on_0sec.scenarioCheckPointReached(scenarioStage);
        this.tasks_on_3sec.scenarioCheckPointReached(scenarioStage);
        this.tasks_on_60sec.scenarioCheckPointReached(scenarioStage);
        this.tasks_on_600sec.scenarioCheckPointReached(scenarioStage);
        if (null != this.CHASETOPO && ScenarioGarbageFinder.isExpired("ScenarioSave", this.CHASETOPO, scenarioStage)) {
            this.CHASETOPO.finishImmediately();
            this.CHASETOPO = null;
        }
    }
}
