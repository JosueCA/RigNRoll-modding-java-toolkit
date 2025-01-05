/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import java.util.HashMap;
import rnrrating.PlayerRatingStats;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NPCRatingSystem {
    HashMap<String, PlayerRatingStats> rating = new HashMap();

    PlayerRatingStats getRating(String playername) {
        if (this.rating.containsKey(playername)) {
            return this.rating.get(playername);
        }
        PlayerRatingStats stats = new PlayerRatingStats(playername);
        this.rating.put(playername, stats);
        return stats;
    }

    public HashMap<String, PlayerRatingStats> getRating() {
        return this.rating;
    }

    public void setRating(HashMap<String, PlayerRatingStats> rating) {
        this.rating = rating;
    }
}

