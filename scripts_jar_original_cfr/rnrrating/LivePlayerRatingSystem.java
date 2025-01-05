/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import rnrrating.PlayerRatingStats;

public class LivePlayerRatingSystem
extends PlayerRatingStats {
    LivePlayerRatingSystem() {
        super("LIVE");
    }

    public LivePlayerRatingSystem(PlayerRatingStats playerStats) {
        super("LIVE");
        this.setCurrent(playerStats.getCurrent());
        this.setCurrent_missions(playerStats.getCurrent_missions());
        this.setRating(playerStats.getRating());
    }
}

