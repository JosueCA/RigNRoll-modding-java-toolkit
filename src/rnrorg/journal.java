/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import menuscript.HeadUpDisplay;
import rnrorg.journable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class journal {
    private static journal JOURNAL = null;
    private ArrayList<journable> alljournalelements = new ArrayList();
    private ArrayList<journable> activeJournalElements = new ArrayList();

    public static journal getInstance() {
        if (null == JOURNAL) {
            JOURNAL = new journal();
        }
        return JOURNAL;
    }

    public static void deinit() {
        JOURNAL = null;
    }

    private journal() {
    }

    public void resortactiveNotes() {
        for (journable jou : this.alljournalelements) {
            if (!jou.isQuestion() || this.activeJournalElements.contains(jou)) continue;
            this.activeJournalElements.add(jou);
        }
        HeadUpDisplay.updateUnread();
    }

    public void add(journable val) {
        this.alljournalelements.add(val);
        if (val.isQuestion()) {
            this.activeJournalElements.add(val);
            HeadUpDisplay.updateUnread();
        }
    }

    public boolean hasUnread() {
        return !this.activeJournalElements.isEmpty();
    }

    public static boolean isUnreadMission(String name) {
        for (journable j : journal.getInstance().activeJournalElements) {
            if (name.compareTo(j.getMissionName()) != 0) continue;
            return true;
        }
        return false;
    }

    public void updateActiveNotes() {
        boolean make_changes = false;
        ArrayList<journable> toclean = new ArrayList<journable>();
        for (journable elem : this.activeJournalElements) {
            if (elem.isQuestion()) continue;
            make_changes = true;
            toclean.add(elem);
        }
        this.activeJournalElements.removeAll(toclean);
        if (make_changes) {
            HeadUpDisplay.updateUnread();
        }
    }

    public void declineAll() {
        for (journable elem : this.activeJournalElements) {
            if (!elem.isQuestion()) continue;
            elem.decline();
        }
    }

    public int journalSize() {
        return this.alljournalelements.size();
    }

    public journable get(int index) {
        if (this.alljournalelements.isEmpty()) {
            return null;
        }
        if (index < 0 || index >= this.alljournalelements.size()) {
            return null;
        }
        return this.alljournalelements.get(index);
    }

    public void remove(journable obj) {
        for (int i = 0; i < this.alljournalelements.size(); ++i) {
            if (!this.alljournalelements.get(i).equals(obj)) continue;
            this.alljournalelements.remove(i);
            break;
        }
    }

    public ArrayList<journable> getAlljournalelements() {
        return this.alljournalelements;
    }

    public void setAlljournalelements(ArrayList<journable> alljournalelements) {
        this.alljournalelements = alljournalelements;
    }
}

