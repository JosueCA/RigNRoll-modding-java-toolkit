/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.actorveh;

public interface IQuestItem {
    public void create();

    public void destroy();

    public boolean have(actorveh var1);

    public String getPlacement();
}

