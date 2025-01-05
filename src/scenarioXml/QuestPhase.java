/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.LinkedList;
import scenarioXml.ObjectProperties;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class QuestPhase
implements Comparable {
    private int nom = 0;
    private LinkedList<ObjectProperties> actionList = null;
    private String missionPoint = null;

    QuestPhase(int nom, String missionPoint) {
        this.nom = nom;
        this.missionPoint = missionPoint;
    }

    public String getMissionPoint() {
        return this.missionPoint;
    }

    public final int getNom() {
        return this.nom;
    }

    public int compareTo(Object o) {
        QuestPhase other = (QuestPhase)o;
        if (this.nom < other.nom) {
            return -1;
        }
        if (this.nom > other.nom) {
            return 1;
        }
        return 0;
    }

    void setActionList(LinkedList<ObjectProperties> list) {
        if (null != list) {
            this.actionList = list;
        }
    }

    final LinkedList<ObjectProperties> getActionList() {
        return this.actionList;
    }
}

