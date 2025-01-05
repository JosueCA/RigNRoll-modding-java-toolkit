/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.LinkedList;
import scenarioXml.IsoQuestItemTask;

final class IsoQuest {
    private final LinkedList<IsoQuestItemTask> tasks = new LinkedList();
    private String name = null;
    private boolean finishOnLastPhase = true;

    IsoQuest() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinishOnLastPhase() {
        return this.finishOnLastPhase;
    }

    public void setFinishOnLastPhase(boolean finishOnLastPhase) {
        this.finishOnLastPhase = finishOnLastPhase;
    }

    public void addTask(IsoQuestItemTask task) {
        if (null != task) {
            this.tasks.add(task);
        }
    }

    public IsoQuestItemTask[] getTasks() {
        IsoQuestItemTask[] result = new IsoQuestItemTask[this.tasks.size()];
        return this.tasks.toArray(result);
    }
}

