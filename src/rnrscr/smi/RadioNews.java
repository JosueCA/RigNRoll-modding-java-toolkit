/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import java.util.HashMap;
import rnrorg.ActiveJournalListeners;
import rnrorg.JournalActiveListener;
import rnrorg.MissionEventsMaker;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;

public class RadioNews {
    private static HashMap<String, RadioNews> allnews = new HashMap();
    private String resource;

    public RadioNews(IMissionInformation mission_info, String resource) {
        this.resource = resource;
        MissionEventsMaker.makeRadioBreakNews(resource);
    }

    public void onAppear() {
        ActiveJournalListeners.startActiveJournals(new JournalActiveListener(this.resource));
        MissionDialogs.sayAppear(this.resource);
        ActiveJournalListeners.endActiveJournals();
        MissionDialogs.sayEnd(this.resource);
    }

    public static void appear(String resource) {
        if (!allnews.containsKey(resource)) {
            return;
        }
        allnews.get(resource).onAppear();
        allnews.remove(resource);
    }

    public static void add(String resource, RadioNews news) {
        allnews.put(resource, news);
    }

    public static void remove(String resource) {
        if (!allnews.containsKey(resource)) {
            return;
        }
        allnews.remove(resource);
    }
}

