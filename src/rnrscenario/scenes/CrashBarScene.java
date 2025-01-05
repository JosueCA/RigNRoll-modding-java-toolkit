/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.aiplayer;
import rnrcore.IScriptTask;
import rnrcore.SCRuniperson;
import rnrcore.ScenarioSync;
import rnrcore.eng;
import rnrscenario.consistency.ScenarioClass;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=15)
public class CrashBarScene {
    public static boolean DEBUG = false;

    public void run() {
        aiplayer player = Crew.getIgrok();
        SCRuniperson person = player.getModel();
        long task = eng.CreateTASK(person);
        long bar_world = eng.AddChangeWorldTask(task, "bar_crash", "cutscene");
        long startinsideanimation = eng.AddScriptTask(task, new StartScene(task));
        long dummy = eng.AddEventTask(task);
        eng.OnEndTASK(bar_world, "play", dummy);
        eng.OnMidTASK(dummy, 1.0, 1.0, "play", startinsideanimation);
        eng.playTASK(bar_world);
    }

    static class StartScene
    implements IScriptTask {
        private long task;

        StartScene(long task) {
            this.task = task;
        }

        public void launch() {
            if (DEBUG) {
                ScenarioSync.setPlayScene("sc02050");
                DEBUG = false;
            } else {
                EventsControllerHelper.messageEventHappened("start 2050");
            }
            eng.DeleteTASK(this.task);
        }
    }
}

