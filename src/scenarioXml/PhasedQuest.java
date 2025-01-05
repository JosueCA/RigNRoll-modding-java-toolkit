/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.LinkedList;
import scenarioXml.ObjectProperties;
import scenarioXml.QuestPhase;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class PhasedQuest {
    private LinkedList<ObjectProperties> actionsOnStart = new LinkedList();
    private LinkedList<ObjectProperties> actionsOnEnd = new LinkedList();
    private String name = null;
    private String missionPoint = null;
    private String organizer = null;
    private final LinkedList<QuestPhase> phases = new LinkedList();

    PhasedQuest() {
    }

    String getOrganizerRef() {
        return this.organizer;
    }

    LinkedList<QuestPhase> getPhases() {
        return this.phases;
    }

    LinkedList<ObjectProperties> getActionsOnStart() {
        return this.actionsOnStart;
    }

    LinkedList<ObjectProperties> getActionsOnEnd() {
        return this.actionsOnEnd;
    }

    String getMissionPoint() {
        return this.missionPoint;
    }

    String getName() {
        return this.name;
    }

    void setOrganizerRef(String organizer) {
        this.organizer = organizer;
    }

    void setName(String name) {
        this.name = name;
    }

    void setMissionPoint(String missionPoint) {
        this.missionPoint = missionPoint;
    }

    void addPhase(QuestPhase toAdd) {
        if (null != toAdd) {
            this.phases.add(toAdd);
        }
    }

    void setActionOnEnd(LinkedList<ObjectProperties> actionList) {
        if (null == actionList) {
            throw new IllegalArgumentException("actionList must be non-null references");
        }
        this.actionsOnEnd = actionList;
    }

    void setActionOnStart(LinkedList<ObjectProperties> actionList) {
        if (null == actionList) {
            throw new IllegalArgumentException("actionList must be non-null reference");
        }
        this.actionsOnStart = actionList;
    }
}

