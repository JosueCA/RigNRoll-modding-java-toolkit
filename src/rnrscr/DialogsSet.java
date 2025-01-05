/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import rnrscr.SODialogParams;

public class DialogsSet {
    private ArrayList<SODialogParams> quests = new ArrayList();

    public void addQuest(SODialogParams task) {
        this.quests.add(task);
    }

    public int getQuestCount() {
        return this.quests.size();
    }

    public SODialogParams getQuest(int nom) {
        if (0 > nom || nom >= this.getQuestCount()) {
            throw new IllegalArgumentException("illegal nom");
        }
        return this.quests.get(nom);
    }
}

