/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import rnrscenario.missions.QINpc;
import scriptEvents.ScriptEvent;

public class CreateNpcFromQuestItem
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    public QINpc questItemNpc;
    public String missionName;

    public CreateNpcFromQuestItem(QINpc questItemNpc, String missionName) {
        this.questItemNpc = questItemNpc;
        this.missionName = missionName;
    }
}

