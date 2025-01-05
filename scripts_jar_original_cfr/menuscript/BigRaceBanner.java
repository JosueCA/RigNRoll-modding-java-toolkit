/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.menucreation;
import menu.menues;
import rnrconfig.IconMappings;
import rnrcore.loc;

public class BigRaceBanner
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\Menu_banner.xml";
    private String menuid = null;
    private String s_city_name = null;
    private String s_race_name = null;
    private String s_race_logo_id = null;
    private int s_race_stage = -1;
    private int s_race_id = -1;

    public String getMenuId() {
        return this.menuid;
    }

    BigRaceBanner(int race_id, String race_name, int race_stage, String cityname) {
        this.s_city_name = cityname;
        this.s_race_name = loc.getBigraceShortName(race_name);
        this.s_race_logo_id = race_name;
        this.s_race_stage = race_stage;
        this.s_race_id = race_id;
        this.menuid = String.format("banner%02dMENU", this.s_race_id + 1);
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, String.format("BANNER Race%02d", this.s_race_id + 1));
    }

    public static long CreateBigRaceBanner(int race_id, String race_name, int race_stage, String cityname) {
        return menues.createSimpleMenu(new BigRaceBanner(race_id, race_name, race_stage, cityname), 1.0E8, "ESC", 1600, 1200, 600, 300, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void AfterInitMenu(long _menu) {
        long race_logo;
        MENUText_field field;
        long control;
        if (this.s_city_name != null && (control = menues.FindFieldInMenu(_menu, "CitiesNames")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            KeyPair[] macro = new KeyPair[]{new KeyPair("START", this.s_city_name)};
            MacroKit.ApplyToTextfield(field, macro);
        }
        if (this.s_race_stage != -1 && (control = menues.FindFieldInMenu(_menu, "RaceStage - SYMBOL")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            switch (this.s_race_stage) {
                case 0: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - SYMBOL");
                    break;
                }
                case 1: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE II - SYMBOL");
                    break;
                }
                case 2: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE III - SYMBOL");
                    break;
                }
                default: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - SYMBOL");
                }
            }
            menues.UpdateField(field);
        }
        if (this.s_race_stage != -1 && (control = menues.FindFieldInMenu(_menu, "RaceStage - TEXT")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            switch (this.s_race_stage) {
                case 0: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT");
                    break;
                }
                case 1: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE II - TEXT");
                    break;
                }
                case 2: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE III - TEXT");
                    break;
                }
                default: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT");
                }
            }
            menues.UpdateField(field);
        }
        if (this.s_race_name != null && (control = menues.FindFieldInMenu(_menu, "RaceName")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            field.text = this.s_race_name;
            menues.UpdateField(field);
        }
        if ((race_logo = menues.FindFieldInMenu(_menu, "THE RACE LOGOTYPE")) != 0L) {
            if (this.s_race_logo_id != null) {
                menues.SetShowField(race_logo, true);
                IconMappings.remapRaceLogos(this.s_race_logo_id, race_logo);
            } else {
                menues.SetShowField(race_logo, false);
            }
        }
        menues.WindowSet_ShowCursor(_menu, false);
        menues.setShowMenu(_menu, false);
    }

    public void exitMenu(long _menu) {
    }

    public void restartMenu(long _menu) {
    }
}

