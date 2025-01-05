/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import rnrorg.JournalActiveListener;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ActiveJournalListeners {
    private static ArrayList<JournalActiveListener> listeners = new ArrayList();

    public static void startActiveJournals(JournalActiveListener lst) {
        listeners.add(lst);
    }

    public static void endActiveJournals() {
        listeners.clear();
    }

    public static ArrayList<JournalActiveListener> getActiveListeners() {
        return listeners;
    }
}

