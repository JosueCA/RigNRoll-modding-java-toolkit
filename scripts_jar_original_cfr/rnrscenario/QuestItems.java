/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.HashMap;
import rnrscenario.IQuestItem;

public class QuestItems {
    private HashMap<String, IQuestItem> items = new HashMap();
    private static QuestItems object = null;

    private QuestItems() {
        object = this;
    }

    private static final QuestItems gReference() {
        if (null == object) {
            new QuestItems();
        }
        return object;
    }

    public static final void addQuestItem(String name, IQuestItem item) {
        QuestItems.gReference().items.put(name, item);
    }

    public static final IQuestItem getQuestItem(String name) {
        return QuestItems.gReference().items.get(name);
    }

    public static final IQuestItem removeQuestItem(String name) {
        return QuestItems.gReference().items.remove(name);
    }
}

