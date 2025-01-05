/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.actorveh;
import players.semitrailer;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.IQuestItem;

public class QuestSemitrailer
implements IQuestItem {
    private static final String NONAME = "no name";
    String model = "no name";
    String place = "no name";
    private semitrailer trailer = null;

    public QuestSemitrailer() {
    }

    public QuestSemitrailer(String model, String place) {
        this.model = model;
        this.place = place;
    }

    public void create() {
        vectorJ pos = eng.getControlPointPosition(this.place);
        if (pos.length() < 1.0) {
            pos = new vectorJ(3225.0, -24500.0, 2.0);
        }
        this.trailer = semitrailer.create(this.model, new matrixJ(), pos);
    }

    public void destroy() {
        if (null != this.trailer) {
            this.trailer.delete();
            this.trailer = null;
        }
    }

    public boolean have(actorveh player) {
        semitrailer palyer_semi = player.querryTrailer();
        if (palyer_semi == null) {
            return false;
        }
        return palyer_semi.equal(this.trailer);
    }

    public String getPlacement() {
        return this.place;
    }
}

