/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.List;
import rnrconfig.MerchandizeInformation;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrorg.EventGetPointLocInfo;
import rnrorg.IStoreorgelement;
import rnrorg.MissionEventsMaker;
import rnrorg.journable;
import rnrorg.journalelement;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JournalStartWarehouse
extends journalelement {
    private static final String[] START_JOURNALS = new String[]{"DELIVERY START JOURNAL", "TENDER START JOURNAL", "CONTEST START JOURNAL", "BIGRACE START JOURNAL NOSEMI 1", "BIGRACE START JOURNAL NOSEMI 1", "BIGRACE START JOURNAL NOSEMI 1", "BIGRACE START JOURNAL NOSEMI 1", "BIGRACE START JOURNAL NOSEMI 1", "BIGRACE START JOURNAL SEMI 1", "BIGRACE START JOURNAL SEMI 1", "BIGRACE START JOURNAL SEMI 1", "BIGRACE START JOURNAL SEMI 1", "BIGRACE START JOURNAL SEMI 1"};
    private static final int DELIVERY = 0;
    private static final int TENDER = 1;
    private static final int CONTEST = 2;
    private static final int BIGRACE_0_NOSEMI = 3;
    private static final int BIGRACE_1_NOSEMI = 4;
    private static final int BIGRACE_2_NOSEMI = 5;
    private static final int BIGRACE_3_NOSEMI = 6;
    private static final int BIGRACE_4_NOSEMI = 7;
    private static final int BIGRACE_0_SEMI = 8;
    private static final int BIGRACE_1_SEMI = 9;
    private static final int BIGRACE_2_SEMI = 10;
    private static final int BIGRACE_3_SEMI = 11;
    private static final int BIGRACE_4_SEMI = 12;

    public JournalStartWarehouse(String descriptionLocRef, List<Macros> macrosPairs) {
        super(descriptionLocRef, macrosPairs);
    }

    public JournalStartWarehouse() {
    }

    public static journable createStartJournalNote(String description, IStoreorgelement.Type type, String merchandise, String load_point, String complete_point, String raceName, int stageId) {
        EventGetPointLocInfo info_load = MissionEventsMaker.getLocalisationMissionPointInfoLocRefs(load_point);
        EventGetPointLocInfo info_complete = MissionEventsMaker.getLocalisationMissionPointInfoLocRefs(complete_point);
        String note_loc_ref = START_JOURNALS[0];
        ArrayList<Macros> macroces = new ArrayList<Macros>();
        int num = 0;
        MerchandizeInformation merchandizeInfo = new MerchandizeInformation(merchandise);
        block0 : switch (type) {
            case baseDelivery: {
                note_loc_ref = START_JOURNALS[0];
                macroces.add(new Macros("CARGO", MacroBuilder.makeSimpleMacroBody("MERCHNAME", merchandizeInfo.getMerchandizeName())));
                macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
                macroces.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));
                break;
            }
            case tender: {
                note_loc_ref = START_JOURNALS[1];
                macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
                macroces.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));
                break;
            }
            case competition: {
                note_loc_ref = START_JOURNALS[2];
                macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
                macroces.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));
                break;
            }
            case bigrace0: 
            case bigrace1: 
            case bigrace2: 
            case bigrace3: 
            case bigrace4: 
            case bigrace0_semi: 
            case bigrace1_semi: 
            case bigrace2_semi: 
            case bigrace3_semi: 
            case bigrace4_semi: {
                switch (type) {
                    case bigrace0: {
                        num = 3;
                        break;
                    }
                    case bigrace1: {
                        num = 4;
                        break;
                    }
                    case bigrace2: {
                        num = 5;
                        break;
                    }
                    case bigrace3: {
                        num = 6;
                        break;
                    }
                    case bigrace4: {
                        num = 7;
                        break;
                    }
                    case bigrace0_semi: {
                        num = 8;
                        break;
                    }
                    case bigrace1_semi: {
                        num = 9;
                        break;
                    }
                    case bigrace2_semi: {
                        num = 10;
                        break;
                    }
                    case bigrace3_semi: {
                        num = 11;
                        break;
                    }
                    case bigrace4_semi: {
                        num = 12;
                    }
                }
                note_loc_ref = START_JOURNALS[num];
                macroces.add(new Macros("CARGO", MacroBuilder.makeSimpleMacroBody("MERCHNAME", merchandizeInfo.getMerchandizeName())));
                macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
                macroces.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));
                macroces.add(new Macros("SHORTRACENAME", MacroBuilder.makeSimpleMacroBody("BIGRACE_SHORTNAME", raceName)));
                switch (stageId) {
                    case 0: {
                        macroces.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE I - TEXT - CaseSmall - Short")));
                        break;
                    }
                    case 1: {
                        macroces.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE II - TEXT - CaseSmall - Short")));
                        break;
                    }
                    case 2: {
                        macroces.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE III - TEXT - CaseSmall - Short")));
                        break;
                    }
                    default: {
                        macroces.add(new Macros("STAGE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRaceStage - STAGE I - TEXT - CaseSmall - Short")));
                    }
                }
                switch (type) {
                    case bigrace0: 
                    case bigrace0_semi: {
                        macroces.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - SECOND LEAGUE")));
                        break block0;
                    }
                    case bigrace1: 
                    case bigrace1_semi: {
                        macroces.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - FIRST LEAGUE")));
                        break block0;
                    }
                    case bigrace2: 
                    case bigrace2_semi: {
                        macroces.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - PREMIERE LEAGUE")));
                        break block0;
                    }
                    case bigrace3: 
                    case bigrace3_semi: {
                        macroces.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - CHAMPION LEAGUE")));
                        break block0;
                    }
                    case bigrace4: 
                    case bigrace4_semi: {
                        macroces.add(new Macros("LEAGUE", MacroBuilder.makeSimpleMacroBody("MENU", "common\\BigRacewLeague - MONSTER CUP")));
                    }
                }
            }
        }
        return new JournalStartWarehouse(note_loc_ref, macroces);
    }
}

