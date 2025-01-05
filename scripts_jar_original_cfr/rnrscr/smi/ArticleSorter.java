/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import java.util.Comparator;
import rnrscr.smi.IArticle;

public class ArticleSorter
implements Comparator {
    public int compare(Object arg0, Object arg1) {
        IArticle article1 = (IArticle)arg0;
        IArticle article2 = (IArticle)arg1;
        return article2.getPriority() - article1.getPriority();
    }
}

