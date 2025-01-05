/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.List;
import players.Crew;
import rnrscenario.IQuestItem;
import rnrscenario.QuestItems;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class QuestItemEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private ScriptEvent lastSuccessfullyChecked = null;
    private static final String NONAME = "no name";
    String name = "no name";

    public QuestItemEventChecker(String inname) {
        this.name = inname;
    }

    public QuestItemEventChecker() {
    }

    @Override
    public void deactivateChecker() {
    }

    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        IQuestItem qui = QuestItems.getQuestItem(this.name);
        if (null == qui) {
            return true;
        }
        this.lastSuccessfullyChecked = eventTuple.isEmpty() ? null : eventTuple.get(0);
        return qui.have(Crew.getIgrokCar());
    }

    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    @Override
    public List<ScriptEvent> getExpectantEvent() {
        ArrayList<ScriptEvent> out = new ArrayList<ScriptEvent>(1);
        out.add(null);
        return out;
    }

    @Override
    public String isValid() {
        return null;
    }
}

