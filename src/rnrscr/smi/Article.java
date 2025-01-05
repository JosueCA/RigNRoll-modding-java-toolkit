/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import rnrcore.CoreTime;
import rnrscr.smi.IArticle;
import rnrscr.smi.NewspaperQuest;

public abstract class Article
implements IArticle {
    protected CoreTime dueToTime = null;
    private int paperindex = 1;
    private int uid = -1;

    final boolean sameNews(int uid) {
        return this.uid != -1 && uid == this.uid;
    }

    final boolean sameNews(Article article) {
        return this.sameNews(article.uid);
    }

    protected final void setUid(int uid) {
        this.uid = uid;
    }

    protected final void setDueToTime(int year, int month, int day, int hour) {
        this.dueToTime = new CoreTime(year, month, day, hour, 0);
    }

    public final CoreTime getDueToTime() {
        return this.dueToTime;
    }

    public final boolean isOldArticle(CoreTime currentTime) {
        return this.dueToTime != null && currentTime.moreThan(this.dueToTime) >= 0;
    }

    public boolean isNews() {
        return false;
    }

    public boolean isRaceAnnouncement() {
        return false;
    }

    public boolean isRaceSummary() {
        return false;
    }

    public Article() {
    }

    public Article(int paperindex) {
        this.paperindex = paperindex;
    }

    public void readArticle() {
    }

    public NewspaperQuest isQuest() {
        return null;
    }

    public final int paperIndex() {
        return this.paperindex;
    }
}

