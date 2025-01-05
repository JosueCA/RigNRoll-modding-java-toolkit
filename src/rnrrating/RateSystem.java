/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import rnrcore.eng;
import rnrrating.BigRace;
import rnrrating.LivePlayerRatingSystem;
import rnrrating.NPCRatingSystem;
import rnrrating.PlayerRatingStats;
import rnrrating.Rater;
import rnrrating.WHRating;
import rnrscr.AdvancedRandom;

public class RateSystem {
    public static final double minimalRating = 20.0;
    private LivePlayerRatingSystem live = new LivePlayerRatingSystem();
    private NPCRatingSystem npc = new NPCRatingSystem();
    private static RateSystem system = null;

    public static final void deinit() {
        system = null;
    }

    private RateSystem() {
    }

    public static RateSystem gSystem() {
        if (system == null) {
            system = new RateSystem();
        }
        return system;
    }

    private static PlayerRatingStats getStats(String name) {
        PlayerRatingStats stats = null;
        stats = name.compareToIgnoreCase("LIVE") == 0 ? RateSystem.gSystem().live : RateSystem.gSystem().npc.getRating(name);
        return stats;
    }

    public static void rateDelievery(String name) {
        RateSystem.getStats(name).sRate(Rater.initWhDelievery(WHRating.gRating(0), WHRating.gRating(1), WHRating.gRating(2), WHRating.gRating(3), WHRating.leaveDelieveryPenalty()));
    }

    public static void rateAnotherOrder(String name) {
        RateSystem.getStats(name).sRate(Rater.initWhOtherOrder(WHRating.gRating(0), WHRating.gRating(1), WHRating.gRating(2), WHRating.gRating(3), WHRating.leaveDelieveryPenalty()));
    }

    public static void rateDrivingContest(String name) {
        RateSystem.getStats(name).sRate(Rater.initWhDrivingContest(WHRating.gRating(0), WHRating.gRating(1), WHRating.gRating(2), WHRating.gRating(3), WHRating.leaveDelieveryPenalty()));
    }

    public static void rateRoadContest(String name) {
        RateSystem.getStats(name).sRate(Rater.initWhDrivingContest(2 * WHRating.gRating(0), 2 * WHRating.gRating(1), 2 * WHRating.gRating(2), 2 * WHRating.gRating(3), 2 * WHRating.leaveDelieveryPenalty()));
    }

    public static void rateBigRace(String name, int series) {
        Rater rater = Rater.initWhDrivingContest(WHRating.gRating(0, series), WHRating.gRating(1, series), WHRating.gRating(2, series), WHRating.gRating(3, series), WHRating.leaveBigRacePenalty(series));
        rater.setCheckPointRating(WHRating.gRating(0, series - 1));
        RateSystem.getStats(name).sRate(rater);
    }

    public static void rateMission(String name, String mission_name, double coef_succes, double coef_fail) {
        RateSystem.getStats(name).sRate(mission_name, Rater.initMission(WHRating.gRating(0), WHRating.leaveDelieveryPenalty(), coef_succes, coef_fail));
    }

    public static int finish(String name, int place) {
        switch (place) {
            case 0: {
                return RateSystem.getStats(name).gold();
            }
            case 1: {
                return RateSystem.getStats(name).silver();
            }
            case 2: {
                return RateSystem.getStats(name).bronze();
            }
        }
        return RateSystem.getStats(name).finish();
    }

    public static int passCheckpoint(String name, int place) {
        switch (place) {
            case 0: {
                return RateSystem.getStats(name).checkpoint();
            }
        }
        return 0;
    }

    public static int finishMission(String name, String mission_name) {
        return RateSystem.getStats(name).finishMission(mission_name);
    }

    public static int fail(String name) {
        return RateSystem.getStats(name).fail();
    }

    public static int failMission(String name, String mission_name) {
        return RateSystem.getStats(name).failMission(mission_name);
    }

    public static double gLiveRating() {
        return RateSystem.gSystem().live.getrating();
    }

    public static int gLiveRatingAroung() {
        int res = 0;
        double rnd2 = AdvancedRandom.getRandomDouble();
        double live_rating = RateSystem.gSystem().live.getrating();
        for (int iter = 1; iter <= BigRace.series_highest; ++iter) {
            if (iter == BigRace.series_highest || !(live_rating >= (double)BigRace.limits_series[iter]) || !(live_rating < (double)BigRace.limits_series[iter + 1])) continue;
            double br2 = Math.log(BigRace.limits_series[iter + 1]);
            double br1 = Math.log(BigRace.limits_series[iter]);
            double res_log = rnd2 * (br2 - br1) + br1;
            res = (int)Math.exp(res_log);
            return res;
        }
        double double_res = (rnd2 + 1.0) * (double)BigRace.limits_series[BigRace.series_highest];
        res = (int)double_res;
        return res;
    }

    public static double gBracketRatingAroung(int lvl) {
        double res = 0.0;
        double rnd2 = AdvancedRandom.getRandomDouble();
        if (lvl != BigRace.series_highest) {
            double br2 = Math.log(BigRace.limits_series[lvl + 1]);
            double br1 = Math.log(BigRace.limits_series[lvl]);
            double res_log = rnd2 * (br2 - br1) + br1;
            res = Math.exp(res_log);
            return res;
        }
        double double_res = (rnd2 + 1.0) * (double)BigRace.limits_series[BigRace.series_highest];
        res = (int)double_res;
        return res;
    }

    public static void DEBUGsetLiveRating(int value) {
        RateSystem.gSystem().live.DEBUGsetrating(value);
    }

    public static double gNormalLiveRating() {
        return eng.getCodedLiveRating();
    }

    public static double estimateDelivery() {
        return WHRating.gRating(0);
    }

    public static double estimateFailDelivery() {
        return WHRating.leaveDelieveryPenalty();
    }

    public static double estimateAnotherOrder() {
        return WHRating.gRating(0);
    }

    public static double estimateFailAnotherOrder() {
        return WHRating.leaveDelieveryPenalty();
    }

    public static double estimateContest() {
        return WHRating.gRating(0);
    }

    public static double estimateFailContest() {
        return WHRating.leaveDelieveryPenalty();
    }

    public static double estimateFinishLiveMission(String mission_name) {
        return RateSystem.gSystem().live.estimateFinishMission(mission_name);
    }

    public static double estimateFailLiveMission(String mission_name) {
        return RateSystem.gSystem().live.estimateFailMission(mission_name);
    }

    public static double estimateGoldRace(int raceid) {
        if (raceid > WHRating.series_GOLD.length - 1 || raceid < 0) {
            return -1.0;
        }
        return WHRating.series_GOLD[raceid];
    }

    public static double estimateFailRace(int raceid) {
        if (raceid > WHRating.series_GOLD.length - 1 || raceid < 0) {
            return -1.0;
        }
        return WHRating.leaveBigRacePenalty(raceid);
    }

    public static void addLiveRating(double coefficient) {
        if (system == null) {
            return;
        }
        RateSystem.system.live.addRating((int)(coefficient * RateSystem.estimateDelivery()));
    }

    public LivePlayerRatingSystem getLive() {
        return this.live;
    }

    public void setLive(LivePlayerRatingSystem live) {
        this.live = live;
    }

    public NPCRatingSystem getNpc() {
        return this.npc;
    }

    public void setNpc(NPCRatingSystem npc) {
        this.npc = npc;
    }
}

