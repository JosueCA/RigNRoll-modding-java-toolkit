/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.common;

import rnr.bigrace.race.BigRaceLeagueId;
import rnr.bigrace.race.BigRacePlaceId;
import rnr.bigrace.race.BigRaceStageId;

public final class BigRaceUtils {
    public static BigRaceLeagueId convertToLeagueId(int leagueId) {
        switch (leagueId) {
            case 2: {
                return BigRaceLeagueId.SECOND_LEAGUE;
            }
            case 1: {
                return BigRaceLeagueId.FIRST_LEAGUE;
            }
            case 10: {
                return BigRaceLeagueId.PREMIER_LEAGUE;
            }
            case 100: {
                return BigRaceLeagueId.CHAMPIONS_LEAGUE;
            }
            case 1000: {
                return BigRaceLeagueId.MONSTER_LEAGUE;
            }
        }
        return BigRaceLeagueId.SECOND_LEAGUE;
    }

    public static BigRaceStageId convertToStageId(int stageId) {
        switch (stageId) {
            case 1: {
                return BigRaceStageId.QUALIFYING_STAGE;
            }
            case 2: {
                return BigRaceStageId.SEMIFINAL_STAGE;
            }
            case 3: {
                return BigRaceStageId.FINAL_STAGE;
            }
        }
        return BigRaceStageId.QUALIFYING_STAGE;
    }

    public static BigRacePlaceId convertToPlaceId(int placeId) {
        switch (placeId) {
            case 1: {
                return BigRacePlaceId.FIRST_PLACE;
            }
            case 2: {
                return BigRacePlaceId.SECOND_PLACE;
            }
            case 3: {
                return BigRacePlaceId.THIRD_PLACE;
            }
            case 4: {
                return BigRacePlaceId.PARTAKING_PLACE;
            }
        }
        return BigRacePlaceId.FAIL_PLACE;
    }

    public static boolean isPrizePlace(BigRacePlaceId placeId) {
        return placeId.getId() == BigRacePlaceId.FIRST_PLACE.getId() || placeId.getId() == BigRacePlaceId.SECOND_PLACE.getId() || placeId.getId() == BigRacePlaceId.THIRD_PLACE.getId() || placeId.getId() == BigRacePlaceId.PARTAKING_PLACE.getId();
    }

    public static boolean isLeagueId(int leagueId) {
        return leagueId == 2 || leagueId == 1 || leagueId == 10 || leagueId == 100 || leagueId == 1000;
    }

    public static boolean isStageId(int stageId) {
        return stageId == 1 || stageId == 2 || stageId == 3;
    }

    public static boolean isPlaceId(int placeId) {
        return placeId == 1 || placeId == 2 || placeId == 3 || placeId == 4 || placeId == 5;
    }
}

