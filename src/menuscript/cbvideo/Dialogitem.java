/*
 * Decompiled with CFR 0.151.
 */
package menuscript.cbvideo;

import players.IcontaktCB;

public class Dialogitem {
    IcontaktCB contact;
    String text;
    String[] alts;
    boolean bIsAlternative;
    int default_alts;
    int answered;

    public Dialogitem(IcontaktCB contact, String text) {
        this.contact = contact;
        this.text = text;
        this.alts = null;
        this.bIsAlternative = false;
        this.default_alts = 0;
    }

    public Dialogitem(IcontaktCB contact, String[] alts, int default_alts, int answered) {
        this.contact = contact;
        if (alts != null && alts.length == 2) {
            this.text = null;
            this.alts = new String[alts.length];
            for (int i = 0; i < alts.length; ++i) {
                this.alts[i] = alts[i];
            }
            this.bIsAlternative = true;
            this.default_alts = default_alts;
            this.answered = answered;
        } else {
            this.text = alts != null ? alts[0] : null;
            this.alts = null;
            this.bIsAlternative = false;
            default_alts = 0;
        }
    }
}

