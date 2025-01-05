/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrscenario.missions.infochannels.InformationChannelData;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ResourcePlaser {
    private ResourcePlaser() {
    }

    public static void placeResources(Iterable<InformationChannelData> resourceHolders, String missionName, boolean missionImportant, boolean resourcesOnEnd) {
        if (null != resourceHolders) {
            for (InformationChannelData channelData : resourceHolders) {
                channelData.placeResources(missionName, missionImportant, resourcesOnEnd);
            }
        }
    }
}

