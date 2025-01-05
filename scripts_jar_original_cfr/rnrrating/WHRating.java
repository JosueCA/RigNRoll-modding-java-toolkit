/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

public class WHRating {
    public static final int firstPlace = 0;
    public static final int secondPlace = 1;
    public static final int thirdPlace = 2;
    public static int[] series_GOLD = new int[]{6, 70, 1650, 14000, 3000000, 10000000};
    public static int[] series_SILVER = new int[]{(int)((double)series_GOLD[0] * 0.8), (int)((double)series_GOLD[1] * 0.8), (int)((double)series_GOLD[2] * 0.8), (int)((double)series_GOLD[3] * 0.8), (int)((double)series_GOLD[4] * 0.8), (int)((double)series_GOLD[5] * 0.8)};
    public static final int[] series_BRONZE = new int[]{(int)((double)series_GOLD[0] * 0.5), (int)((double)series_GOLD[1] * 0.5), (int)((double)series_GOLD[2] * 0.5), (int)((double)series_GOLD[3] * 0.5), (int)((double)series_GOLD[4] * 0.5), (int)((double)series_GOLD[5] * 0.5)};
    public static int[] series_FINISH = new int[]{0, 0, 0, 0, 0, 0};
    public static double penaltyCoeffecient = 0.3;
    public static int[] series_PENALTY = new int[]{-((int)((double)series_GOLD[0] * penaltyCoeffecient)), -((int)((double)series_GOLD[1] * penaltyCoeffecient)), -((int)((double)series_GOLD[2] * penaltyCoeffecient)), -((int)((double)series_GOLD[3] * penaltyCoeffecient)), -((int)((double)series_GOLD[4] * penaltyCoeffecient)), -((int)((double)series_GOLD[5] * penaltyCoeffecient))};

    public static final void renew() {
        for (int i = 0; i < 6; ++i) {
            WHRating.series_SILVER[i] = (int)((double)series_GOLD[i] * 0.8);
            WHRating.series_BRONZE[i] = (int)((double)series_GOLD[i] * 0.5);
            WHRating.series_FINISH[i] = 0;
            WHRating.series_PENALTY[i] = -((int)((double)series_GOLD[i] * penaltyCoeffecient));
        }
    }

    protected static int gRating(int place) {
        switch (place) {
            case 0: {
                return series_GOLD[0];
            }
            case 1: {
                return series_SILVER[0];
            }
            case 2: {
                return series_BRONZE[0];
            }
        }
        return series_FINISH[0];
    }

    protected static int gRating(int place, int series) {
        switch (place) {
            case 0: {
                return series_GOLD[series];
            }
            case 1: {
                return series_SILVER[series];
            }
            case 2: {
                return series_BRONZE[series];
            }
        }
        return series_FINISH[series];
    }

    protected static int leaveDelieveryPenalty() {
        return series_PENALTY[0];
    }

    protected static int leaveBigRacePenalty(int series) {
        return series_PENALTY[series];
    }
}

