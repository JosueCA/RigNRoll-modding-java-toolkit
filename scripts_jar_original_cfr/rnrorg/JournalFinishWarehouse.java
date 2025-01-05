/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.List;
import menu.Helper;
import menuscript.Converts;
import rnrcore.MacroBody;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrcore.eng;
import rnrcore.loc;
import rnrorg.AlbumFinishWarehouse;
import rnrorg.IStoreorgelement;
import rnrorg.journalelement;
import rnrorg.organaiser;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class JournalFinishWarehouse
extends journalelement {
    private static final String[] NOTES = new String[]{"DELIVERY FINISH", "DELIVERY TIMEOUT", "DELIVERY CANCEL", "DELIVERY DAMAGED", "DELIVERY EVACUATION", "TENDER FINISH GOLD", "TENDER FINISH SILVER", "TENDER FINISH BRONZE", "TENDER FINISH SIMPLE", "TENDER TIMEOUT", "TENDER CANCEL", "CONTEST FINISH WON", "CONTEST FINISH SIMPLE", "CONTEST TIMEOUT", "CONTEST CANCEL", "BIGRACE FINISH CHECKPOINT 1", "BIGRACE FINISH CHECKPOINT 2", "BIGRACE FINISH CHECKPOINT 3", "BIGRACE FINISH CHECKPOINT 4", "BIGRACE FINISH 1", "BIGRACE FINISH 1", "BIGRACE FINISH 1", "BIGRACE FINISH 1", "BIGRACE FINISH 1", "BIGRACE GOLD 1", "BIGRACE GOLD 1", "BIGRACE GOLD 1", "BIGRACE GOLD 1", "BIGRACE GOLD 1", "BIGRACE SILVER 1", "BIGRACE SILVER 1", "BIGRACE SILVER 1", "BIGRACE SILVER 1", "BIGRACE SILVER 1", "BIGRACE BRONZE 1", "BIGRACE BRONZE 1", "BIGRACE BRONZE 1", "BIGRACE BRONZE 1", "BIGRACE BRONZE 1", "BIGRACE DAMAGED 1", "BIGRACE DAMAGED 1", "BIGRACE DAMAGED 1", "BIGRACE DAMAGED 1", "BIGRACE DAMAGED 1", "BIGRACE EVACUATION 1", "BIGRACE EVACUATION 1", "BIGRACE EVACUATION 1", "BIGRACE EVACUATION 1", "BIGRACE EVACUATION 1", "BIGRACE TIMEOUT 1", "BIGRACE TIMEOUT 1", "BIGRACE TIMEOUT 1", "BIGRACE TIMEOUT 1", "BIGRACE TIMEOUT 1", "BIGRACE DECLINE 1", "BIGRACE DECLINE 1", "BIGRACE DECLINE 1", "BIGRACE DECLINE 1", "BIGRACE DECLINE 1", "DELIVERY EVACUATION ONLY"};
    public static final int DELIVERY_FINISH = 0;
    public static final int DELIVERY_TIMEOUT = 1;
    public static final int DELIVERY_CANCEL = 2;
    public static final int DELIVERY_DAMAGED = 3;
    public static final int DELIVERY_EVACUATION = 4;
    public static final int TENDER_FINISH_GOLD = 5;
    public static final int TENDER_FINISH_SILVER = 6;
    public static final int TENDER_FINISH_BRONZE = 7;
    public static final int TENDER_FINISH_SIMPLE = 8;
    public static final int TENDER_TIMEOUT = 9;
    public static final int TENDER_CANCEL = 10;
    public static final int CONTEST_FINISH_WON = 11;
    public static final int CONTEST_FINISH_SIMPLE = 12;
    public static final int CONTEST_TIMEOUT = 13;
    public static final int CONTEST_CANCEL = 14;
    public static final int BIGRACE_FINISH_CHECKPOINT_1 = 15;
    public static final int BIGRACE_FINISH_CHECKPOINT_2 = 16;
    public static final int BIGRACE_FINISH_CHECKPOINT_3 = 17;
    public static final int BIGRACE_FINISH_CHECKPOINT_4 = 18;
    public static final int BIGRACE_FINISH_0 = 19;
    public static final int BIGRACE_FINISH_1 = 20;
    public static final int BIGRACE_FINISH_2 = 21;
    public static final int BIGRACE_FINISH_3 = 22;
    public static final int BIGRACE_FINISH_4 = 23;
    public static final int BIGRACE_GOLD_0 = 24;
    public static final int BIGRACE_GOLD_1 = 25;
    public static final int BIGRACE_GOLD_2 = 26;
    public static final int BIGRACE_GOLD_3 = 27;
    public static final int BIGRACE_GOLD_4 = 28;
    public static final int BIGRACE_SILVER_0 = 29;
    public static final int BIGRACE_SILVER_1 = 30;
    public static final int BIGRACE_SILVER_2 = 31;
    public static final int BIGRACE_SILVER_3 = 32;
    public static final int BIGRACE_SILVER_4 = 33;
    public static final int BIGRACE_BRONZE_0 = 34;
    public static final int BIGRACE_BRONZE_1 = 35;
    public static final int BIGRACE_BRONZE_2 = 36;
    public static final int BIGRACE_BRONZE_3 = 37;
    public static final int BIGRACE_BRONZE_4 = 38;
    public static final int BIGRACE_DAMAGED_0 = 39;
    public static final int BIGRACE_DAMAGED_1 = 40;
    public static final int BIGRACE_DAMAGED_2 = 41;
    public static final int BIGRACE_DAMAGED_3 = 42;
    public static final int BIGRACE_DAMAGED_4 = 43;
    public static final int BIGRACE_EVACUATION_0 = 44;
    public static final int BIGRACE_EVACUATION_1 = 45;
    public static final int BIGRACE_EVACUATION_2 = 46;
    public static final int BIGRACE_EVACUATION_3 = 47;
    public static final int BIGRACE_EVACUATION_4 = 48;
    public static final int BIGRACE_TIMEOUT_0 = 49;
    public static final int BIGRACE_TIMEOUT_1 = 50;
    public static final int BIGRACE_TIMEOUT_2 = 51;
    public static final int BIGRACE_TIMEOUT_3 = 52;
    public static final int BIGRACE_TIMEOUT_4 = 53;
    public static final int BIGRACE_DECLINE_0 = 54;
    public static final int BIGRACE_DECLINE_1 = 55;
    public static final int BIGRACE_DECLINE_2 = 56;
    public static final int BIGRACE_DECLINE_3 = 57;
    public static final int BIGRACE_DECLINE_4 = 58;
    public static final int DELIVERY_EVACUATION_ONLY = 59;
    private static final int[] FROM_PLACE_TO_INDEX = new int[]{24, 29, 34};
    private static final int[] FROM_FAIL_TYPE_TO_INDEX = new int[]{39, 44, 49, 54};

    private JournalFinishWarehouse(String description, List<Macros> macrosPairs) {
        super(description, macrosPairs);
    }

    public static void createNote(int type) {
        JournalFinishWarehouse.missionEventType(type);
        new JournalFinishWarehouse(NOTES[type], null).start();
    }

    public static void createNote(int type, List<Macros> macroces) {
        JournalFinishWarehouse.missionEventType(type);
        new JournalFinishWarehouse(NOTES[type], macroces).start();
    }

    public static void createNoteL17DPlacesNames(int type, String raceName, String logoName, MacroBody source, MacroBody destination, int place, double rating, int money, int stageId) {
        JournalFinishWarehouse.missionEventType(type);
        AlbumFinishWarehouse.postNote(type, raceName, logoName);
        ArrayList<Macros> macro = new ArrayList<Macros>();
        macro.add(new Macros("SHORTRACENAME", MacroBuilder.makeSimpleMacroBody("BIGRACE_SHORTNAME", raceName)));
        macro.add(new Macros("SOURCE", source));
        macro.add(new Macros("DESTINATION", destination));
        macro.add(new Macros("PLACE", MacroBuilder.makeSimpleMacroBody(Integer.toString(place))));
        macro.add(new Macros("MONEY", MacroBuilder.makeSimpleMacroBody(Helper.convertMoney(money))));
        macro.add(new Macros("RATING", MacroBuilder.makeSimpleMacroBody(Converts.ConvertRating(eng.visualRating(rating)))));
        switch (stageId) {
            case 0: {
                macro.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE I - TEXT - CaseSmall - Short")));
                break;
            }
            case 1: {
                macro.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE II - TEXT - CaseSmall - Short")));
                break;
            }
            case 2: {
                macro.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE III - TEXT - CaseSmall - Short")));
                break;
            }
            default: {
                macro.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE I - TEXT - CaseSmall - Short")));
            }
        }
        switch (type) {
            case 19: 
            case 24: 
            case 29: 
            case 34: 
            case 39: 
            case 44: 
            case 49: 
            case 54: {
                macro.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - MONSTER CUP")));
                break;
            }
            case 20: 
            case 25: 
            case 30: 
            case 35: 
            case 40: 
            case 45: 
            case 50: 
            case 55: {
                macro.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - CHAMPION LEAGUE")));
                break;
            }
            case 21: 
            case 26: 
            case 31: 
            case 36: 
            case 41: 
            case 46: 
            case 51: 
            case 56: {
                macro.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - PREMIERE LEAGUE")));
                break;
            }
            case 22: 
            case 27: 
            case 32: 
            case 37: 
            case 42: 
            case 47: 
            case 52: 
            case 57: {
                macro.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - FIRST LEAGUE")));
                break;
            }
            case 23: 
            case 28: 
            case 33: 
            case 38: 
            case 43: 
            case 48: 
            case 53: 
            case 58: {
                macro.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - SECOND LEAGUE")));
            }
        }
        new JournalFinishWarehouse(NOTES[type], macro).start();
    }

    public static void createNote(int type, String raceName, String logoName, String source, String destination, int place, double rating, int money, int stageId) {
        JournalFinishWarehouse.createNoteL17DPlacesNames(type, raceName, logoName, MacroBuilder.makeSimpleMacroBody("CITYNAME", source), MacroBuilder.makeSimpleMacroBody("CITYNAME", destination), place, rating, money, stageId);
    }

    private static void missionEventType(int type) {
        IStoreorgelement elem = organaiser.getInstance().getCurrentWarehouseOrder();
        if (null == elem) {
            return;
        }
        switch (type) {
            case 0: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 11: 
            case 12: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: {
                elem.finish();
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 9: 
            case 10: 
            case 13: 
            case 14: 
            case 39: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                elem.fail(0);
            }
        }
    }

    private static void createNote(int baseIndex, int leagueId, int stageId, String raceName, int place, double ratingDelta, int moneyDelta, String source, String destination) {
        if (0 > leagueId || 5 <= leagueId) {
            eng.writeLog(leagueId + " is not valid value for league id: JournalFinishWarehouse.createNote");
            return;
        }
        if (null == raceName) {
            eng.writeLog("'raceName' must be non-null value: JournalFinishWarehouse.createNote");
            return;
        }
        if (null == source) {
            eng.writeLog("'source' must be non-null value: JournalFinishWarehouse.createNote");
            return;
        }
        if (null == destination) {
            eng.writeLog("'destination' must be non-null value: JournalFinishWarehouse.createNote");
            return;
        }
        JournalFinishWarehouse.createNote(baseIndex + leagueId, loc.getBigraceShortName(raceName), raceName, source, destination, place, ratingDelta, moneyDelta, stageId);
    }

    public static void createNoteAboutWin(int leagueId, int stageId, String raceName, int place, double ratingDelta, int moneyDelta, String source, String destination) {
        if (1 > place || FROM_PLACE_TO_INDEX.length < place) {
            eng.writeLog(place + " is not valid value for place: JournalFinishWarehouse.createNote");
            return;
        }
        JournalFinishWarehouse.createNote(FROM_PLACE_TO_INDEX[place - 1], leagueId, stageId, raceName, place, ratingDelta, moneyDelta, source, destination);
    }

    public static void createNoteAboutSuccess(int leagueId, int stageId, String raceName, int place, double ratingDelta, int moneyDelta, String source, String destination) {
        JournalFinishWarehouse.createNote(19, leagueId, stageId, raceName, place, ratingDelta, moneyDelta, source, destination);
    }

    public static void createNoteAboutFail(int failKind, int leagueId, int stageId, String raceName, String source, String destination) {
        JournalFinishWarehouse.createNote(FROM_FAIL_TYPE_TO_INDEX[failKind], leagueId, stageId, raceName, 0, 0.0, 0, source, destination);
    }
}

