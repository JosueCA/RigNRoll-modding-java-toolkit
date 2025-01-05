/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.LinkedList;
import scenarioXml.IsoQuest;
import scenarioXml.PhasedQuest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class ParsingOut {
    private final LinkedList<IsoQuest> isoQuests = new LinkedList();
    private final LinkedList<PhasedQuest> phasedQuests = new LinkedList();

    ParsingOut() {
    }

    LinkedList<IsoQuest> getIsoQuests() {
        return this.isoQuests;
    }

    LinkedList<PhasedQuest> getPhasedQuests() {
        return this.phasedQuests;
    }

    void addQuest(IsoQuest quest) {
        this.isoQuests.add(quest);
    }

    void addQuest(PhasedQuest quest) {
        this.phasedQuests.add(quest);
    }
}

