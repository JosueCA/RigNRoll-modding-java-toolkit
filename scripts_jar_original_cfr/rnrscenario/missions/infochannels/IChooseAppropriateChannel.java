/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface IChooseAppropriateChannel {
    public void choose(String var1);

    public void choose();

    public ArrayList<String> getPlaces();

    public boolean isChosenChannel();
}

