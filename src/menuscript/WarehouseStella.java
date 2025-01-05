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

public class WarehouseStella
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_banner.xml";
    private String menuid;
    private String s_controlgroup;
    private String s_city_name = null;
    private String s_race_name = null;
    private String s_race_logo_id = null;
    private int s_race_stage = -1;
    private int s_warehouse_type = -1;
    private long _menu;

    public String getMenuId() {
        return this.menuid;
    }

    WarehouseStella(String menuid, String control_group) {
        this.menuid = menuid;
        this.s_controlgroup = control_group;
    }

    public static long CreateBigRaceStella(int race_id) {
        WarehouseStella menu = null;
        switch (race_id) {
            case 0: {
                menu = new WarehouseStella("stellaWilcomMENU", "STELLA - WELCOME");
                break;
            }
            case 1: {
                menu = new WarehouseStella("raceStartFinishWarehouse01MENU", "STELLA Race01 - Start/Finish");
                break;
            }
            case 2: {
                menu = new WarehouseStella("raceStartFinishWarehouse02MENU", "STELLA Race02 - Start/Finish");
                break;
            }
            case 3: {
                menu = new WarehouseStella("raceStartFinishWarehouse03MENU", "STELLA Race03 - Start/Finish");
                break;
            }
            case 4: {
                menu = new WarehouseStella("raceStartFinishWarehouse04MENU", "STELLA Race04 - Start/Finish");
                break;
            }
            case 5: {
                menu = new WarehouseStella("raceStartFinishWarehouse05MENU", "STELLA Race05 - Start/Finish");
                break;
            }
            default: {
                menu = new WarehouseStella("raceStartFinishWarehouse05MENU", "STELLA Race05 - Start/Finish");
            }
        }
        return menues.createSimpleMenu(menu, 1.0E8, "ESC", 1600, 1200, 600, 150, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, this.s_controlgroup);
        this._menu = _menu;
    }

    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, false);
        menues.setShowMenu(_menu, false);
    }

    void Update() {
        MENUText_field field;
        long control;
        long race_logo;
        MENUText_field field2;
        long control2;
        if (this.s_city_name != null && (control2 = menues.FindFieldInMenu(this._menu, "To The City")) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            KeyPair[] macro = new KeyPair[]{new KeyPair("CITY", this.s_city_name)};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if ((race_logo = menues.FindFieldInMenu(this._menu, "THE RACE LOGOTYPE")) != 0L) {
            if (this.s_race_logo_id != null) {
                menues.SetShowField(race_logo, true);
                IconMappings.remapRaceLogos(this.s_race_logo_id, race_logo);
            } else {
                menues.SetShowField(race_logo, false);
            }
        }
        if (this.s_race_name != null && (control = menues.FindFieldInMenu(this._menu, "RaceName")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            field.text = this.s_race_name;
            menues.UpdateField(field);
        }
        if (this.s_warehouse_type != -1 && (control = menues.FindFieldInMenu(this._menu, "Warehouse Start/Finish")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            switch (this.s_warehouse_type) {
                case 1: {
                    field.text = loc.getMENUString("common\\BigRacewWarehouse - START WAREHOUSE");
                    break;
                }
                case 0: {
                    field.text = loc.getMENUString("common\\BigRacewWarehouse - FINISH WAREHOUSE");
                    break;
                }
                default: {
                    field.text = loc.getMENUString("common\\BigRacewWarehouse - FINISH WAREHOUSE");
                }
            }
            menues.UpdateField(field);
        }
        if (this.s_race_stage != -1 && (control = menues.FindFieldInMenu(this._menu, "RaceStage - SYMBOL")) != 0L && (field = menues.ConvertTextFields(control)) != null) {
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
    }

    public void UpdateData(String race_name, int race_stage, int warehouse_type) {
        this.s_race_name = loc.getBigraceShortName(race_name);
        this.s_race_logo_id = race_name;
        this.s_race_stage = race_stage;
        this.s_warehouse_type = warehouse_type;
        this.Update();
    }

    public void UpdateData(String cityname) {
        this.s_city_name = cityname;
        this.Update();
    }

    public void exitMenu(long _menu) {
    }

    public void restartMenu(long _menu) {
    }
}

