/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.map;

import rnrcore.vectorJ;
import rnrscenario.missions.map.MapEventsListener;
import rnrscenario.missions.map.Place;

public interface AbstractMissionsMap {
    public void addPlace(Place var1);

    public boolean onPlace(MapEventsListener var1, double var2);

    public boolean onLoadingDistance(String var1, vectorJ var2);

    public boolean afterUnloadingDistance(String var1, vectorJ var2);

    public void addListener(MapEventsListener var1, double var2);

    public void removeListener(MapEventsListener var1);
}

