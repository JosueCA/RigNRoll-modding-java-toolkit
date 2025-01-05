/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import java.util.HashMap;
import rnrcore.Log;
import rnrrating.RatedItem;
import rnrrating.Rater;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PlayerRatingStats {
    private RatedItem rating;
    private Rater current = null;
    private HashMap<String, Rater> current_missions = new HashMap();

    public PlayerRatingStats(String id) {
        this.rating = new RatedItem(id);
    }

    void sRate(Rater item) {
        if (this.current != null) {
            this.fail();
        }
        this.current = item;
    }

    void sRate(String mission_name, Rater item) {
        if (this.current_missions.containsKey(mission_name)) {
            System.err.print("ERRORR. MissionSistem passed to rate mission " + mission_name + "that already in list.");
            return;
        }
        this.current_missions.put(mission_name, item);
    }

    double getrating() {
        return this.rating.getRating();
    }

    void addRating(int value) {
        this.rating.addRating(value);
    }

    void DEBUGsetrating(int value) {
        this.rating.setRating(value);
    }

    int finish() {
        if (this.current == null) {
            Log.simpleMessage("Trying to finish player, while have no rated quests");
            return 0;
        }
        int diff_rating = this.current.gRatingOnFinish();
        this.rating.addRating(diff_rating);
        this.current = null;
        return diff_rating;
    }

    int gold() {
        if (this.current == null) {
            Log.simpleMessage("Trying to gold player, while have no rated quests");
            return 0;
        }
        int diff_rating = this.current.gRatingOnGold();
        this.rating.addRating(diff_rating);
        this.current = null;
        return diff_rating;
    }

    int silver() {
        if (this.current == null) {
            Log.simpleMessage("Trying to silver player, while have no rated quests");
            return 0;
        }
        int diff_rating = this.current.gRatingOnSilver();
        this.rating.addRating(diff_rating);
        this.current = null;
        return diff_rating;
    }

    int bronze() {
        if (this.current == null) {
            Log.simpleMessage("Trying to bronze player, while have no rated quests");
            return 0;
        }
        int diff_rating = this.current.gRatingOnBronze();
        this.rating.addRating(diff_rating);
        this.current = null;
        return diff_rating;
    }

    int checkpoint() {
        if (this.current == null) {
            Log.simpleMessage("Trying to checkpoint player, while have no rated quests");
            return 0;
        }
        int diff_rating = this.current.gRatingOnCheckPoint();
        this.rating.addRating(diff_rating);
        this.current = null;
        return diff_rating;
    }

    int fail() {
        if (this.current == null) {
            Log.simpleMessage("Trying to fail player, while have no rated quests");
            return 0;
        }
        int diff_rating = this.current.gRatingOnFail();
        this.rating.addRating(diff_rating);
        this.current = null;
        return diff_rating;
    }

    int finishMission(String mission_name) {
        if (!this.current_missions.containsKey(mission_name)) {
            Log.simpleMessage("Trying to finishMission player, while have no rated mission with name " + mission_name);
            return 0;
        }
        Rater rat = this.current_missions.get(mission_name);
        int diff_rating = rat.gRatingOnFinish();
        this.rating.addRating(diff_rating);
        this.current_missions.remove(mission_name);
        return diff_rating;
    }

    int failMission(String mission_name) {
        if (!this.current_missions.containsKey(mission_name)) {
            Log.simpleMessage("Trying to failMission player, while have no rated mission with name " + mission_name);
            return 0;
        }
        Rater rat = this.current_missions.get(mission_name);
        int diff_rating = rat.gRatingOnFail();
        this.rating.addRating(diff_rating);
        this.current_missions.remove(mission_name);
        return diff_rating;
    }

    int estimateFinishMission(String mission_name) {
        if (!this.current_missions.containsKey(mission_name)) {
            Log.simpleMessage("Trying to estimateFinishMission player, while have no rated mission with name " + mission_name);
            return 0;
        }
        return this.current_missions.get(mission_name).gRatingOnGold();
    }

    int estimateFailMission(String mission_name) {
        if (!this.current_missions.containsKey(mission_name)) {
            Log.simpleMessage("Trying to estimateFailMission player, while have no rated mission with name " + mission_name);
            return 0;
        }
        return this.current_missions.get(mission_name).gRatingOnFail();
    }

    public Rater getCurrent() {
        return this.current;
    }

    public void setCurrent(Rater current) {
        this.current = current;
    }

    public HashMap<String, Rater> getCurrent_missions() {
        return this.current_missions;
    }

    public void setCurrent_missions(HashMap<String, Rater> current_missions) {
        this.current_missions = current_missions;
    }

    public RatedItem getRating() {
        return this.rating;
    }

    public void setRating(RatedItem rating) {
        this.rating = rating;
    }
}

