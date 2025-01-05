/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

public interface IArticle {
    public String getHeader();

    public String getBody();

    public String getTexture();

    public boolean isNews();

    public boolean isRaceAnnouncement();

    public boolean isRaceSummary();

    public int getPriority();

    public void readArticle();
}

