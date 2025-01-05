/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace.data;

import java.util.List;
import menu.TimeData;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class RaceParticipantStatistics {
    private final String name;
    private final TimeData time;
    private final double maxSpeed;
    private final double avgSpeed;
    private final int placeOnFinish;
    private final double d_rating;

    public RaceParticipantStatistics(String name, TimeData time, double maxSpeed, double avgSpeed, int placeOnFinish, double rating) {
        assert (null != name) : "'name' must be non-null reference";
        assert (null != time) : "'time' must be non-null reference";
        this.name = name;
        this.time = time;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.placeOnFinish = placeOnFinish;
        this.d_rating = rating;
    }

    public static void addParticipantToList(List<RaceParticipantStatistics> container, String name, TimeData time, double maxSpeed, double avgSpeed, int placeOnFinish, double rating) {
        container.add(new RaceParticipantStatistics(name, time, maxSpeed, avgSpeed, placeOnFinish, rating));
    }

    public String getName() {
        return this.name;
    }

    public TimeData getTime() {
        return this.time;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public int getPlaceOnFinish() {
        return this.placeOnFinish;
    }

    public double getRating() {
        return this.d_rating;
    }

    public double getAvgSpeed() {
        return this.avgSpeed;
    }
}

