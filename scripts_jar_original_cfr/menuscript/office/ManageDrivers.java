/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.Helper;
import menu.IRangerChanged;
import menu.KeyPair;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RNRMap;
import menu.Ranger;
import menu.SelectCb;
import menu.menues;
import menuscript.RNRMapWrapper;
import menuscript.TooltipButton;
import menuscript.office.ApplicationTab;
import menuscript.office.ManageBranchesManager;
import menuscript.office.ManageDriversManager;
import menuscript.office.OfficeMenu;
import menuscript.table.ICompareLines;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrconfig.IconMappings;
import rnrconfig.WorldCoordinates;
import rnrcore.Log;

public class ManageDrivers
extends ApplicationTab {
    private static final boolean bDebug = false;
    private static final String TAB_NAME = "MANAGE DRIVERS";
    private static final String[] SLIDERS = new String[]{"Tableranger - MD Order Type", "Tableranger - MD Load Fragility", "Tableranger - MD Distances", "Tableranger - MD Wear", "Tableranger - MD Speed", "Tableranger - MD Load Safety", "Tableranger - MD Wage change"};
    private static final int SLIDER_ORDERTYPE = 0;
    private static final int SLIDER_LOADFRAG = 1;
    private static final int SLIDER_DISTANCES = 2;
    private static final int SLIDER_ORDERCRITERIA = 3;
    private static final int SLIDER_WEAR = 3;
    private static final int SLIDER_SPEED = 4;
    private static final int SLIDER_LOADSAFETY = 5;
    private static final int SLIDER_TRUCKSELECTIONCRITERIA = 6;
    private static final int SLIDER_WAGECHANGES = 6;
    private static final int SLIDER_WAGECHANGECRITERIA = 7;
    private static final String[] TEXT_SLIDERS = new String[]{"MD Order Type TITLE", "MD Load Fragility TITLE", "MD Distances TITLE", "MD Wear TITLE", "MD Speed TITLE", "MD Load Safety TITLE", "MD Wage change VALUE"};
    private static final int NORMALSTATE = 0;
    private static final int GREYSTATE = 1;
    private static final String[] CHECKBOXES = new String[]{"Order selection criteria - AUTO", "Truck selection criteria - AUTO", "Wage change - AUTO"};
    private static final int CHECKBOX_AUTO_ORDERCRITERIA = 0;
    private static final int CHECKBOX_AUTO_TRUCKSELECTIONCRITERIA = 1;
    private static final int CHECKBOX_AUTO_WAGECHANGE = 2;
    private static final String METH_CHECKBOXES = "onCheckBox";
    private static final String[] SUMMARY_TEXTS = new String[]{"Drivers Involved VALUE", "Order Criteria Changed VALUE", "Truck Criteria Changed VALUE", "Drivers VALUE", "$/Day VALUE"};
    private static final int TEXT_SUMMARY_DRIVER = 0;
    private static final int TEXT_SUMMARY_ORDER = 1;
    private static final int TEXT_SUMMARY_TRUCK = 2;
    private static final int TEXT_SUMMARY_WAGEDRIVERS = 3;
    private static final int TEXT_SUMMARY_WAGEMONEY = 4;
    private static final String[] FILE_TEXTS = new String[]{"MD DriverFile NAME 3 Rows", "FileVALUE Loyalty", "FileVALUE Return", "FileVALUE Wins/Missions", "FileVALUE Forfeit", "FileVALUE Maintenance", "FileVALUE Gas", "FileVALUE Vehicle", "FileVALUE Vehicle MARKED"};
    private static final int TEXT_FILE_TITLE = 0;
    private static final int TEXT_FILE_LOYALITY = 1;
    private static final int TEXT_FILE_ROI = 2;
    private static final int TEXT_FILE_WINS = 3;
    private static final int TEXT_FILE_FORFEI = 4;
    private static final int TEXT_FILE_MAINTANANCE = 5;
    private static final int TEXT_FILE_GAS = 6;
    private static final int TEXT_FILE_VEHICLE = 7;
    private static final int TEXT_FILE_VEHICLE_MARKED = 8;
    private String[] FILE_TEXTS_INITIAL_VALUES = new String[FILE_TEXTS.length];
    private static final String CONTROL_PHOTO = "MD DriverFile PHOTO";
    private static final String[] SORT_FIELDS = new String[]{"BUTTON - Loyalty", "BUTTON - Return", "BUTTON - Wins/Missions", "BUTTON - Forfeit", "BUTTON - Maintenance", "BUTTON - Gas", "BUTTON - Vehicle", "BUTTON - Name", "BUTTON - Rank", "BUTTON - Wage"};
    private static final int SOTR_FILE_LOYALITY = 0;
    private static final int SORT_FILE_ROI = 1;
    private static final int SORT_FILE_WINS = 2;
    private static final int SORT_FILE_FORFEI = 3;
    private static final int SORT_FILE_MAINTANANCE = 4;
    private static final int SORT_FILE_GAS = 5;
    private static final int SORT_FILE_VEHICLE = 6;
    private static final int SORT_NAME = 7;
    private static final int SORT_RANK = 8;
    private static final int SORT_WAGE = 9;
    private static final String METH_SORT = "onSort";
    private static final String CONTROL_APPLY = "BUTTON - APPLY";
    private static final String CONTROL_DISCARD = "BUTTON - DISCARD";
    private static final String METH_APPLY = "onApply";
    private static final String METH_DISCARD = "onDiscard";
    private final String KEY_NAME = "NAME";
    private final String KEY_AGE = "AGE";
    private final String KEY_MONEY = "MONEY";
    private final String KEY_WINS = "WINS";
    private final String KEY_SIGN = "SIGN";
    private static final String MAP_NAME = "MAP - zooming picture";
    private static final String MAP_ZOOM = "MD - MAP";
    private static final String MAP_SHIFT = "MD - MAP";
    private static final String TOOLTIPBUTTON_XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TOOLTIPBUTTON_BUTTON = "MD DriverFile - Details";
    private static final String TOOLTIPBUTTON_MENUGROUP = "TOOLTIP Deatiled VehicleName";
    private static final String[] TOOLTIPBUTTON_TEXT = new String[]{"MD DriverFile - Vehicle VALUE", "MD DriverFile - Vehicle VALUE MARKED"};
    private static final int min_sliders = 0;
    private static final int max_sliders = 100;
    private static final int default_sliders = 50;
    private long current_selected_id = 0L;
    private table_data TABLE_DATA = new table_data();
    private DriversTable dr_table = new DriversTable();
    private Ranger[] sliders = new Ranger[SLIDERS.length];
    private long[] checkboxes = new long[CHECKBOXES.length];
    private long[] summarytexts = new long[SUMMARY_TEXTS.length];
    private long[] filetexts = new long[FILE_TEXTS.length];
    private long[] slider_text = null;
    private String[] slider_text_text = null;
    private long driver_photo;
    RNRMapWrapper mapa;
    private TooltipButton tooltipbutton = null;
    sort table_sort = new sort(1, true);
    WorldCoordinates worldRectangle = null;
    TermometrClass[] termometrs = null;
    private static final String[] TERMOMETRS_NAMES = new String[]{"FileVALUE Loyalty - INDICATOR", "FileVALUE Return - INDICATOR", "FileVALUE Wins/Missions - INDICATOR", "FileVALUE Forfeit - INDICATOR", "FileVALUE Maintenance - INDICATOR", "FileVALUE Gas - INDICATOR"};
    private static final int TERMOMETR_Loyalty = 0;
    private static final int TERMOMETR_Return = 1;
    private static final int TERMOMETR_Wins_Missions = 2;
    private static final int TERMOMETR_Forfeit = 3;
    private static final int TERMOMETR_Maintenance = 4;
    private static final int TERMOMETR_Gas = 5;
    long _menu = 0L;
    boolean bSelectFromMap = false;
    String tool_tip_text = null;

    public ManageDrivers(long _menu, OfficeMenu parent) {
        super(_menu, TAB_NAME, parent);
        this._menu = _menu;
        this.worldRectangle = WorldCoordinates.getCoordinates();
        ManageDriversManager.getManageDriversManager().OnEnterOffice();
        this.init(_menu);
    }

    private void init(long _menu) {
        long field;
        int i = 0;
        while (i < CHECKBOXES.length) {
            this.checkboxes[i] = field = menues.FindFieldInMenu(_menu, CHECKBOXES[i]);
            MENUbutton_field but = menues.ConvertButton(field);
            but.userid = i++;
            menues.SetScriptOnControl(_menu, but, this, METH_CHECKBOXES, 2L);
            menues.UpdateField(but);
        }
        for (i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summarytexts[i] = field = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
        }
        for (i = 0; i < FILE_TEXTS.length; ++i) {
            this.filetexts[i] = field = menues.FindFieldInMenu(_menu, FILE_TEXTS[i]);
            this.FILE_TEXTS_INITIAL_VALUES[i] = menues.GetFieldText(field);
        }
        i = 0;
        while (i < SORT_FIELDS.length) {
            field = menues.FindFieldInMenu(_menu, SORT_FIELDS[i]);
            MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
            buts.userid = i++;
            menues.SetScriptOnControl(_menu, buts, this, METH_SORT, 4L);
            menues.UpdateField(buts);
        }
        this.slider_text = new long[TEXT_SLIDERS.length];
        this.slider_text_text = new String[TEXT_SLIDERS.length];
        for (i = 0; i < TEXT_SLIDERS.length; ++i) {
            this.slider_text[i] = menues.FindFieldInMenu(_menu, TEXT_SLIDERS[i]);
            if (this.slider_text[i] == 0L) continue;
            this.slider_text_text[i] = menues.GetFieldText(this.slider_text[i]);
        }
        this.dr_table.init(_menu);
        this.initSliders(_menu);
        long control = menues.FindFieldInMenu(_menu, CONTROL_APPLY);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, METH_APPLY, 4L);
        control = menues.FindFieldInMenu(_menu, CONTROL_DISCARD);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, METH_DISCARD, 4L);
        control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - MANAGE DRIVERS");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
        long mapfield = menues.FindFieldInMenu(_menu, MAP_NAME);
        if (0L == mapfield) {
            Log.menu("ERRORR. ManageDrivers. No rnr map named MAP - zooming picture");
        }
        this.mapa = new RNRMapWrapper(_menu, MAP_NAME, "MD - MAP", "MD - MAP", new SelectMapControl());
        this.driver_photo = menues.FindFieldInMenu(_menu, CONTROL_PHOTO);
        this.tooltipbutton = new TooltipButton(_menu, TOOLTIPBUTTON_BUTTON, TOOLTIPBUTTON_XML, TOOLTIPBUTTON_MENUGROUP, TOOLTIPBUTTON_TEXT);
    }

    public void afterInit() {
        this.termometrs = new TermometrClass[TERMOMETRS_NAMES.length];
        for (int i = 0; i < TERMOMETRS_NAMES.length; ++i) {
            this.termometrs[i] = new TermometrClass(this._menu, TERMOMETRS_NAMES[i]);
        }
        this.dr_table.afterInit();
        this.mapa.afterInit();
        this.mapa.workWith(10);
        this.tooltipbutton.afterInit();
        this.tooltipbutton.Enable(false);
        this.update();
    }

    public boolean update() {
        if (!super.update()) {
            return false;
        }
        this.dr_table.update();
        this.refresh_fileinfo();
        this.refresh_summary();
        this.refresh_sliders();
        this.refresh_checkboxes();
        this.refresh_mapa();
        this.makeUpdate();
        return true;
    }

    public void deinit() {
        ManageDriversManager.getManageDriversManager().OnLeaveOffice();
        this.dr_table.table.deinit();
    }

    private void initSliders(long _menu) {
        for (int i = 0; i < SLIDERS.length; ++i) {
            this.sliders[i] = new Ranger(_menu, SLIDERS[i], 0, 100, 50);
            this.sliders[i].addListener(new SliderChange(i));
        }
    }

    private void selectDriver(ManageDriversManager.DriverId id) {
        this.current_selected_id = id != null ? (long)id.id : 0L;
        this.refresh_fileinfo();
        this.refresh_sliders();
        this.refresh_checkboxes();
        if (!this.bSelectFromMap) {
            for (line_data item : this.TABLE_DATA.all_lines) {
                if (!item.wheather_show || item.id.id != id.id) continue;
                this.mapa.selectSelect(item.map_id, true);
            }
        }
    }

    private void slider_moved(int slider_nom) {
        if (slider_nom >= 0 && slider_nom < 3) {
            this.update_turnoffRadio(0, false);
        } else if (slider_nom >= 3 && slider_nom < 6) {
            this.update_turnoffRadio(1, false);
        } else if (slider_nom >= 6 && slider_nom < 7) {
            this.update_turnoffRadio(2, false);
        }
    }

    private void drop_slider_group(int group) {
        switch (group) {
            case 0: {
                for (int i = 0; i < 3; ++i) {
                    this.sliders[i].setValue(50, true);
                }
                break;
            }
            case 1: {
                for (int i = 3; i < 6; ++i) {
                    this.sliders[i].setValue(50, true);
                }
                break;
            }
            case 2: {
                for (int i = 6; i < 7; ++i) {
                    Vector bunch = this.dr_table.getSelectedDrivers();
                    int[] wages = ManageDriversManager.getManageDriversManager().GetWage(bunch);
                    ManageDriversManager.getManageDriversManager().GetWage(bunch);
                    this.sliders[i].setValue(wages[0], wages[1], wages[2], (int)(0.1 * (double)(wages[1] - wages[0])), true);
                    KeyPair[] macro = new KeyPair[]{new KeyPair("MONEY", "" + wages[2])};
                    menues.SetFieldText(this.slider_text[6], MacroKit.Parse(this.slider_text_text[6], macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.slider_text[6]));
                }
                break;
            }
        }
    }

    private void manage_gray_sliders() {
        for (Ranger item : this.sliders) {
            item.setState(0);
        }
        for (int i = 0; i < this.TABLE_DATA.all_lines.size() - 1; ++i) {
            for (int j = i + 1; j < this.TABLE_DATA.all_lines.size(); ++j) {
                if (!this.TABLE_DATA.all_lines.get((int)i).selected || !this.TABLE_DATA.all_lines.get((int)j).selected) continue;
                for (int k = 0; k < 7; ++k) {
                    if (this.ask_samevalues_sliders(k, this.TABLE_DATA.all_lines.get(i), this.TABLE_DATA.all_lines.get(j))) continue;
                    this.sliders[k].setState(1);
                }
            }
        }
    }

    public void onCheckBox(long _menu, MENUbutton_field button) {
        this.setDirty();
        int state = menues.GetFieldState(button.nativePointer);
        this.update_turnoffRadio(button.userid, state != 0);
        switch (button.userid) {
            case 0: {
                for (int i = 0; i < 3; ++i) {
                    this.sliders[i].setState(0);
                }
                break;
            }
            case 1: {
                for (int i = 3; i < 6; ++i) {
                    this.sliders[i].setState(0);
                }
                break;
            }
            case 2: {
                for (int i = 6; i < 7; ++i) {
                    this.sliders[i].setState(0);
                }
                break;
            }
        }
        if (state == 1) {
            this.drop_slider_group(button.userid);
        }
        this.refresh_summary();
    }

    public void onSort(long _menu, MENUsimplebutton_field button) {
        this.update_sort(button.userid);
    }

    public void onApply(long _menu, MENUsimplebutton_field button) {
        this.update_apply();
    }

    public void onDiscard(long _menu, MENUsimplebutton_field button) {
        this.update_discard();
    }

    private float c01(int value) {
        if (value >= 100) {
            return 1.0f;
        }
        if (value <= 0) {
            return 0.0f;
        }
        return (float)value / 100.0f;
    }

    private int c0100_01(float value) {
        if (value >= 1.0f) {
            return 100;
        }
        if (value <= 0.0f) {
            return 0;
        }
        return 0 + (int)(100.0f * value);
    }

    private float c_11(int value) {
        if (value >= 100) {
            return 1.0f;
        }
        if (value <= 0) {
            return -1.0f;
        }
        return -1.0f + (float)value / 50.0f;
    }

    private void update_turnoffRadio(int button, boolean value) {
        Vector bunch = this.dr_table.getSelectedDrivers();
        if (bunch.isEmpty()) {
            return;
        }
        switch (button) {
            case 0: {
                menues.SetFieldState(this.checkboxes[button], value ? 1 : 0);
                ManageDriversManager.getManageDriversManager().SetOrderSelectionCriteria(bunch, value);
                break;
            }
            case 1: {
                menues.SetFieldState(this.checkboxes[button], value ? 1 : 0);
                ManageDriversManager.getManageDriversManager().SetTruckSelectionCriteria(bunch, value);
                break;
            }
            case 2: {
                menues.SetFieldState(this.checkboxes[button], value ? 1 : 0);
                ManageDriversManager.getManageDriversManager().SetWageChangeCriteria(bunch, value);
            }
        }
    }

    private void update_slider(int button, int value) {
        Vector bunch = this.dr_table.getSelectedDrivers();
        switch (button) {
            case 0: {
                ManageDriversManager.getManageDriversManager().SetOrderType(bunch, this.c01(value));
                break;
            }
            case 1: {
                ManageDriversManager.getManageDriversManager().SetLoadFargylity(bunch, this.c01(value));
                break;
            }
            case 2: {
                ManageDriversManager.getManageDriversManager().SetDistances(bunch, this.c01(value));
                break;
            }
            case 3: {
                ManageDriversManager.getManageDriversManager().SetWear(bunch, this.c01(value));
                break;
            }
            case 4: {
                ManageDriversManager.getManageDriversManager().SetSpeed(bunch, this.c01(value));
                break;
            }
            case 5: {
                ManageDriversManager.getManageDriversManager().SetLoadSafety(bunch, this.c01(value));
                break;
            }
            case 6: {
                int converted = value;
                KeyPair[] macro = new KeyPair[]{new KeyPair("MONEY", "" + converted)};
                menues.SetFieldText(this.slider_text[6], MacroKit.Parse(this.slider_text_text[6], macro));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.slider_text[6]));
                ManageDriversManager.getManageDriversManager().SetWage(bunch, value);
            }
        }
    }

    private void refresh_fileinfo() {
        int i;
        if (this.tool_tip_text == null) {
            this.tool_tip_text = this.tooltipbutton.GetText();
        }
        line_data current = null;
        for (line_data item : this.TABLE_DATA.all_lines) {
            if (item.id == null || (long)item.id.id != this.current_selected_id) continue;
            current = item;
            break;
        }
        if (null == current || 0L == this.current_selected_id) {
            int i2;
            menues.SetShowField(this.driver_photo, false);
            for (i2 = 0; i2 < this.filetexts.length; ++i2) {
                menues.SetFieldText(this.filetexts[i2], "");
                menues.UpdateMenuField(menues.ConvertMenuFields(this.filetexts[i2]));
            }
            this.tooltipbutton.setText("");
            this.tooltipbutton.Enable(false);
            for (i2 = 0; i2 < this.termometrs.length; ++i2) {
                this.termometrs[i2].Update(0);
            }
            return;
        }
        for (i = 0; i < this.filetexts.length; ++i) {
            switch (i) {
                case 0: {
                    KeyPair[] pairs6 = new KeyPair[]{new KeyPair("NAME", "" + current.driver_name), new KeyPair("AGE", "" + current.age)};
                    menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs6));
                    break;
                }
                case 1: {
                    menues.SetFieldText(this.filetexts[i], "" + (int)current.loyalty + "%");
                    break;
                }
                case 2: {
                    KeyPair[] pairs1 = new KeyPair[]{new KeyPair("MONEY", Helper.convertMoney(Math.abs((int)current.roi))), new KeyPair("SIGN", (int)current.roi >= 0 ? "" : "-")};
                    menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs1));
                    break;
                }
                case 3: {
                    KeyPair[] pairs5 = new KeyPair[]{new KeyPair("WINS", "" + current.wins_missions)};
                    menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs5));
                    break;
                }
                case 4: {
                    KeyPair[] pairs2 = new KeyPair[]{new KeyPair("MONEY", Helper.convertMoney((int)current.forefit))};
                    menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs2));
                    break;
                }
                case 5: {
                    KeyPair[] pairs3 = new KeyPair[]{new KeyPair("MONEY", Helper.convertMoney((int)current.maintenance))};
                    menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs3));
                    break;
                }
                case 6: {
                    KeyPair[] pairs4 = new KeyPair[]{new KeyPair("MONEY", Helper.convertMoney((int)current.gas))};
                    menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs4));
                    break;
                }
                case 7: {
                    menues.SetShowField(this.filetexts[i], !current.vehickeJustBought);
                    menues.SetFieldText(this.filetexts[i], current.short_vehicle_name);
                    break;
                }
                case 8: {
                    menues.SetShowField(this.filetexts[i], current.vehickeJustBought);
                    menues.SetFieldText(this.filetexts[i], current.short_vehicle_name);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(this.filetexts[i]));
        }
        block23: for (i = 0; i < this.termometrs.length; ++i) {
            switch (i) {
                case 0: {
                    this.termometrs[i].Update((int)(100.0 * (double)current.loyalty_temp));
                    continue block23;
                }
                case 1: {
                    this.termometrs[i].Update((int)(100.0 * (double)current.roi_temp));
                    continue block23;
                }
                case 2: {
                    this.termometrs[i].Update((int)(100.0 * (double)current.wins_temp));
                    continue block23;
                }
                case 3: {
                    this.termometrs[i].Update((int)(100.0 * (double)current.forefit_temp));
                    continue block23;
                }
                case 4: {
                    this.termometrs[i].Update((int)(100.0 * (double)current.maintenance_temp));
                    continue block23;
                }
                case 5: {
                    this.termometrs[i].Update((int)(100.0 * (double)current.gas_temp));
                }
            }
        }
        if (null == current.face_index) {
            menues.SetShowField(this.driver_photo, false);
        } else {
            menues.SetShowField(this.driver_photo, true);
            IconMappings.remapPersonIcon("" + current.face_index, this.driver_photo);
        }
        if (current.make == null || current.make.length() == 0 || current.model == null || current.model.length() == 0 || current.license_plate == null || current.license_plate.length() == 0) {
            this.tooltipbutton.Enable(false);
        } else {
            KeyPair[] pairs = new KeyPair[]{new KeyPair("MAKE", current.make), new KeyPair("MODEL", current.model), new KeyPair("LICENSE_PLATE", current.license_plate)};
            this.tooltipbutton.setText(MacroKit.Parse(this.tool_tip_text, pairs));
            this.tooltipbutton.setState(current.vehickeJustBought ? 1 : 0);
            this.tooltipbutton.Enable(true);
        }
    }

    private void refresh_summary() {
        ManageDriversManager.Summary summ = ManageDriversManager.getManageDriversManager().GetSummary();
        for (int i = 0; i < this.summarytexts.length; ++i) {
            switch (i) {
                case 0: {
                    menues.SetFieldText(this.summarytexts[i], "" + summ.drivers_involved);
                    break;
                }
                case 1: {
                    menues.SetFieldText(this.summarytexts[i], "" + summ.order_criteria_changed);
                    break;
                }
                case 2: {
                    menues.SetFieldText(this.summarytexts[i], "" + summ.truck_criteria_changed);
                    break;
                }
                case 3: {
                    menues.SetFieldText(this.summarytexts[i], "" + summ.wages_changed_drivers);
                    break;
                }
                case 4: {
                    menues.SetFieldText(this.summarytexts[i], (summ.wages_changed_day < 0.0f ? "-" : " ") + Helper.convertMoney((int)summ.wages_changed_day));
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(this.summarytexts[i]));
        }
    }

    private void refresh_mapa() {
        this.mapa.ClearData();
        for (line_data item : this.TABLE_DATA.all_lines) {
            if (!item.wheather_show) continue;
            String name_to_display = "";
            item.map_id = this.mapa.addObject(10, (float)this.worldRectangle.convertX(item.x), (float)this.worldRectangle.convertY(item.y), name_to_display, item);
            this.setPriority(item.map_id, -2147383649);
        }
        boolean first = true;
        for (line_data item : this.TABLE_DATA.all_lines) {
            if (!item.wheather_show || !first || !item.selected || item.id == null) continue;
            this.mapa.selectSelect(item.map_id, true);
            first = false;
        }
        this.updateWarehousesOnMapa();
    }

    private void setPriority(int icon, int value) {
        int p1 = 1;
        int p2 = 2;
        int p3 = 3;
        int p4 = 4;
        int p5 = 5;
        RNRMap.Priority priority = this.mapa.createPriority();
        priority.SetPriority(0, true, true, value + p5);
        priority.SetPriority(0, true, false, value + p4);
        priority.SetPriority(0, false, false, value);
        priority.SetPriority(0, false, true, value + p1);
        priority.SetPriority(1, true, true, value + p5);
        priority.SetPriority(1, true, false, value + p4);
        priority.SetPriority(1, false, false, value + p4);
        priority.SetPriority(1, false, true, value + p5);
        priority.SetPriority(2, true, true, value + p3);
        priority.SetPriority(2, true, false, value + p2);
        priority.SetPriority(2, false, false, value + p2);
        priority.SetPriority(2, false, true, value + p3);
        this.mapa.setPriority(icon, priority);
    }

    private void refresh_checkboxes() {
        Vector bunch = this.dr_table.getSelectedDrivers();
        for (int i = 0; i < this.checkboxes.length; ++i) {
            boolean value = false;
            switch (i) {
                case 0: {
                    value = ManageDriversManager.getManageDriversManager().GetOrderSelectionCriteria(bunch);
                    break;
                }
                case 1: {
                    value = ManageDriversManager.getManageDriversManager().GetTruckSelectionCriteria(bunch);
                    break;
                }
                case 2: {
                    value = ManageDriversManager.getManageDriversManager().GetWageChangeCriteria(bunch);
                }
            }
            menues.SetFieldState(this.checkboxes[i], value ? 1 : 0);
            if (!value) continue;
            this.drop_slider_group(i);
        }
    }

    private void refresh_sliders() {
        Vector bunch = this.dr_table.getSelectedDrivers();
        block9: for (int i = 0; i < this.sliders.length; ++i) {
            int value = 0;
            switch (i) {
                case 0: {
                    value = this.c0100_01(ManageDriversManager.getManageDriversManager().GetOrderType(bunch));
                    this.sliders[i].setValue(value, true);
                    continue block9;
                }
                case 1: {
                    value = this.c0100_01(ManageDriversManager.getManageDriversManager().GetLoadFragylity(bunch));
                    this.sliders[i].setValue(value, true);
                    continue block9;
                }
                case 2: {
                    value = this.c0100_01(ManageDriversManager.getManageDriversManager().GetDistances(bunch));
                    this.sliders[i].setValue(value, true);
                    continue block9;
                }
                case 3: {
                    value = this.c0100_01(ManageDriversManager.getManageDriversManager().GetWear(bunch));
                    this.sliders[i].setValue(value, true);
                    continue block9;
                }
                case 4: {
                    value = this.c0100_01(ManageDriversManager.getManageDriversManager().GetSpeed(bunch));
                    this.sliders[i].setValue(value, true);
                    continue block9;
                }
                case 5: {
                    value = this.c0100_01(ManageDriversManager.getManageDriversManager().GetLoadSafety(bunch));
                    this.sliders[i].setValue(value, true);
                    continue block9;
                }
                case 6: {
                    int[] wages = ManageDriversManager.getManageDriversManager().GetWage(bunch);
                    value = wages[2];
                    KeyPair[] macro = new KeyPair[]{new KeyPair("MONEY", "" + wages[2])};
                    menues.SetFieldText(this.slider_text[6], MacroKit.Parse(this.slider_text_text[6], macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.slider_text[6]));
                    this.sliders[i].setValue(wages[0], wages[1], wages[2], (int)(0.1 * (double)(wages[1] - wages[0])), true);
                    continue block9;
                }
                default: {
                    this.sliders[i].setValue(value, true);
                }
            }
        }
    }

    private void update_sort(int type_sort) {
        switch (type_sort) {
            case 0: {
                this.table_sort = new sort(4, this.table_sort.type == 4 ? !this.table_sort.up : true);
                break;
            }
            case 1: {
                this.table_sort = new sort(5, this.table_sort.type == 5 ? !this.table_sort.up : true);
                break;
            }
            case 2: {
                this.table_sort = new sort(6, this.table_sort.type == 6 ? !this.table_sort.up : true);
                break;
            }
            case 3: {
                this.table_sort = new sort(7, this.table_sort.type == 7 ? !this.table_sort.up : true);
                break;
            }
            case 4: {
                this.table_sort = new sort(8, this.table_sort.type == 8 ? !this.table_sort.up : true);
                break;
            }
            case 5: {
                this.table_sort = new sort(9, this.table_sort.type == 9 ? !this.table_sort.up : true);
                break;
            }
            case 6: {
                this.table_sort = new sort(10, this.table_sort.type == 10 ? !this.table_sort.up : true);
                break;
            }
            case 7: {
                this.table_sort = new sort(1, this.table_sort.type == 1 ? !this.table_sort.up : true);
                break;
            }
            case 8: {
                this.table_sort = new sort(2, this.table_sort.type == 2 ? !this.table_sort.up : true);
                break;
            }
            case 9: {
                this.table_sort = new sort(3, this.table_sort.type == 3 ? !this.table_sort.up : true);
            }
        }
        this.setDirty();
        this.update();
    }

    private void updateWarehousesOnMapa() {
        HashMap<String, ManageBranchesManager.WarehouseInfo> warehouses = new HashMap<String, ManageBranchesManager.WarehouseInfo>();
        Vector<ManageBranchesManager.OfficeInfo> our_offices = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches(1, true);
        Vector<ManageBranchesManager.OfficeInfo> sales_offices = ManageBranchesManager.getManageBranchesManager().GetOfficesForSale(1, true);
        Vector<ManageBranchesManager.OfficeId> offices = new Vector<ManageBranchesManager.OfficeId>();
        for (ManageBranchesManager.OfficeInfo info : our_offices) {
            offices.add(info.id);
        }
        for (ManageBranchesManager.OfficeInfo info : sales_offices) {
            offices.add(info.id);
        }
        Vector<ManageBranchesManager.WarehouseInfo> warehouse_collection = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(offices);
        for (ManageBranchesManager.WarehouseInfo ware : warehouse_collection) {
            ManageBranchesManager.WarehouseInfo inf = (ManageBranchesManager.WarehouseInfo)warehouses.get(ware.name);
            if (null != inf) continue;
            warehouses.put(ware.name, ware);
        }
        Collection warecoll = warehouses.values();
        for (ManageBranchesManager.WarehouseInfo info : warecoll) {
            int type = info.companyAffected ? 4 : 3;
            this.mapa.addObject(type, (float)this.worldRectangle.convertX(info.x), (float)this.worldRectangle.convertY(info.y), info.name, new Object());
        }
    }

    private void update_apply() {
        this.setDirty();
        if (!ManageDriversManager.getManageDriversManager().Apply()) {
            this.makeNotEnoughMoney();
        }
        this.update();
    }

    private void update_discard() {
        this.setDirty();
        ManageDriversManager.getManageDriversManager().Discard();
        this.update();
    }

    private boolean ask_samevalues_sliders(int type, line_data line1, line_data line2) {
        return false;
    }

    public void apply() {
        ManageDriversManager.getManageDriversManager().Apply();
        this.update();
    }

    public void discard() {
        ManageDriversManager.getManageDriversManager().Discard();
        this.update();
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(1);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class DriversTable
    implements ISetupLine,
    ISelectLineListener {
        private final String TABLE = "MD Drivers - TABLEGROUP - 7 38";
        private final String TABLE_RANGER = "Tableranger - MD - NameRating";
        private final String XML_LINE = "..\\data\\config\\menu\\menu_office.xml";
        private final String CONTROLGROUP_LINE = "Tablegroup - ELEMENTS - Drivers";
        private final String[] MARKS = new String[]{"BUTTON - Name VALUE", "BUTTON - Name VALUE GRAY", "BUTTON - Rank VALUE", "BUTTON - Rank VALUE GRAY", "BUTTON - Wage VALUE", "BUTTON - Wage VALUE GRAY"};
        private final int MARK_NAME = 0;
        private final int MARK_NAME_GRAY = 1;
        private final int MARK_RANK = 2;
        private final int MARK_RANK_GRAY = 3;
        private final int MARK_WAGE = 4;
        private final int MARK_WAGE_GRAY = 5;
        private final String KEY = "MONEY";
        private String SUBSOURCE = "";
        private long _menu;
        Table table = null;

        DriversTable() {
        }

        void init(long _menu) {
            this._menu = _menu;
            this.table = new Table(_menu, "MD Drivers - TABLEGROUP - 7 38", "Tableranger - MD - NameRating");
            this.table.setSelectionMode(1);
            this.table.fillWithLines(ManageDrivers.TOOLTIPBUTTON_XML, "Tablegroup - ELEMENTS - Drivers", this.MARKS);
            this.table.takeSetuperForAllLines(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : this.MARKS) {
                this.table.initLinesSelection(name);
            }
            this.table.addListener(this);
            long[] name_controls = this.table.getLineStatistics_controls(this.MARKS[4]);
            if (name_controls == null || name_controls.length == 0) {
                Log.menu("ERRORR. DriversTable. Table has no fields named " + this.MARKS[4]);
            }
            this.SUBSOURCE = menues.GetFieldText(name_controls[1]);
        }

        void afterInit() {
            this.table.afterInit();
            this.make_sync_group();
        }

        private void update() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void reciveTableData() {
            ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.clear();
            Vector<ManageDriversManager.ShotDriverInfo> drids = ManageDriversManager.getManageDriversManager().GetShortDriverInfoList(ManageDrivers.this.table_sort.type, ManageDrivers.this.table_sort.up);
            if (ManageDrivers.this.current_selected_id == 0L && !drids.isEmpty()) {
                ManageDrivers.this.current_selected_id = drids.get((int)0).id != null ? (long)drids.get((int)0).id.id : 0L;
            }
            for (ManageDriversManager.ShotDriverInfo inf : drids) {
                ManageDriversManager.FullDriverInfo local_info = ManageDriversManager.getManageDriversManager().GetFullDriverInfo(inf.id);
                line_data data = new line_data();
                data.id = inf.id;
                data.money_hour = inf.wage;
                data.driver_name_table = inf.driver_name;
                data.driver_name = local_info.driver_name;
                data.age = local_info.age;
                data.face_index = local_info.face_index;
                data.rank = inf.rank;
                data.loyalty = local_info.loyalty;
                data.loyalty_temp = local_info.loyalty_temp;
                data.roi = local_info.roi;
                data.roi_temp = local_info.roi_temp;
                data.wins_temp = local_info.wins_temp;
                data.wins_missions = local_info.wins_missions;
                data.forefit = local_info.forefit;
                data.forefit_temp = local_info.forefit_temp;
                data.maintenance = local_info.maintenance;
                data.maintenance_temp = local_info.maintenance_temp;
                data.gas = local_info.gas;
                data.gas_temp = local_info.gas_temp;
                data.model = local_info.model;
                data.license_plate = local_info.license_plate;
                data.make = local_info.make;
                data.short_vehicle_name = local_info.short_vehicle_name;
                data.vehickeJustBought = local_info.vehJustBought;
                data.is_gray = inf.isGray;
                data.x = inf.x;
                data.y = inf.y;
                data.wheather_show = true;
                ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.add(data);
            }
            this.buildvoidcells();
        }

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();
            for (int i = 0; i < ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();
                ch.toshow = true;
                ch.ontop = i == 0;
                ch.item = ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }
            return root;
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        line_data data = new line_data();
                        data.wheather_show = false;
                        ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<line_data> iter = ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.size()) break block4;
                for (int i = ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            line_data line = (line_data)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: 
                case 1: {
                    obj.text = line.driver_name_table;
                    menues.UpdateMenuField(obj);
                    break;
                }
                case 2: 
                case 3: {
                    obj.text = "" + line.rank;
                    menues.UpdateMenuField(obj);
                    break;
                }
                case 4: 
                case 5: {
                    String to_set = Helper.convertMoney((int)line.money_hour);
                    KeyPair[] pair = new KeyPair[]{new KeyPair("MONEY", to_set)};
                    obj.text = MacroKit.Parse(this.SUBSOURCE, pair);
                    menues.UpdateMenuField(obj);
                }
            }
            switch (control) {
                case 0: 
                case 2: 
                case 4: {
                    menues.SetShowField(obj.nativePointer, !line.is_gray);
                    break;
                }
                case 1: 
                case 3: 
                case 5: {
                    menues.SetShowField(obj.nativePointer, line.is_gray);
                }
            }
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        }

        private void make_sync_group() {
            long[] contrls_name = null;
            if (0 < this.MARKS.length) {
                contrls_name = this.table.getLineStatistics_controls(this.MARKS[0]);
            }
            long[] contrls_rank = null;
            if (2 < this.MARKS.length) {
                contrls_rank = this.table.getLineStatistics_controls(this.MARKS[2]);
            }
            long[] contrls_wage = null;
            if (4 < this.MARKS.length) {
                contrls_wage = this.table.getLineStatistics_controls(this.MARKS[4]);
            }
            if (null == contrls_name || null == contrls_rank || null == contrls_wage) {
                return;
            }
            if (contrls_name.length != contrls_rank.length || contrls_name.length != contrls_wage.length) {
                Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length + " contrls_rank.length is " + contrls_rank.length + " contrls_wage.length is " + contrls_wage.length);
                return;
            }
            for (int i = 0; i < contrls_name.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_rank[i]);
                menues.SetSyncControlState(this._menu, i, contrls_rank[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_wage[i]);
                menues.SetSyncControlState(this._menu, i, contrls_wage[i]);
            }
        }

        @Override
        public void selectLineEvent(Table table, int line) {
            for (line_data item : ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines) {
                item.selected = false;
            }
            line_data data = (line_data)table.getItemOnLine((int)line).item;
            data.selected = true;
            ManageDrivers.this.selectDriver(data.id);
            ManageDrivers.this.manage_gray_sliders();
        }

        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }

        private Vector<ManageDriversManager.DriverId> getSelectedDrivers() {
            Vector<ManageDriversManager.DriverId> res = new Vector<ManageDriversManager.DriverId>();
            for (line_data item : ((ManageDrivers)ManageDrivers.this).TABLE_DATA.all_lines) {
                if (!item.wheather_show || !item.selected) continue;
                res.add(item.id);
            }
            return res;
        }

        class CompareDriversFleet
        implements ICompareLines {
            CompareDriversFleet() {
            }

            public boolean equal(Object object1, Object object2) {
                if (object1 == null || object2 == null) {
                    return false;
                }
                line_data line1 = (line_data)object1;
                line_data line2 = (line_data)object2;
                return line1.id != null && line2.id != null && line1.id.id == line2.id.id;
            }
        }
    }

    static class table_data {
        Vector<line_data> all_lines = new Vector();

        table_data() {
        }
    }

    static class line_data {
        ManageDriversManager.DriverId id;
        int map_id;
        String driver_name_table;
        String driver_name;
        int age;
        String face_index;
        int rank;
        float money_hour;
        float loyalty;
        float loyalty_temp;
        float roi;
        float roi_temp;
        int wins_missions;
        float wins_temp;
        float forefit;
        float forefit_temp;
        float maintenance;
        float maintenance_temp;
        float gas;
        float gas_temp;
        String short_vehicle_name;
        String make;
        String model;
        String license_plate;
        boolean vehickeJustBought;
        float x;
        float y;
        boolean is_gray;
        boolean selected;
        boolean wheather_show;

        line_data() {
        }
    }

    class SliderChange
    implements IRangerChanged {
        private int id;

        SliderChange(int id) {
            this.id = id;
        }

        public void rangerChanged(int to_value) {
            if (0L == ManageDrivers.this.current_selected_id) {
                return;
            }
            ManageDrivers.this.setDirty();
            ManageDrivers.this.slider_moved(this.id);
            ManageDrivers.this.update_slider(this.id, to_value);
            ManageDrivers.this.refresh_summary();
            ManageDrivers.this.sliders[this.id].setState(0);
        }
    }

    class SelectMapControl
    implements SelectCb {
        SelectMapControl() {
        }

        public void OnSelect(int state, Object sender) {
            ManageDrivers.this.bSelectFromMap = true;
            ((ManageDrivers)ManageDrivers.this).dr_table.table.select_line_by_data(sender);
            ManageDrivers.this.bSelectFromMap = false;
        }
    }

    class TermometrClass {
        long control;
        int value = 0;
        int initial_len_x;
        boolean bShow = false;

        TermometrClass(long _menu, String name) {
            MENUText_field field;
            this.control = menues.FindFieldInMenu(_menu, name);
            if (this.control != 0L && (field = (MENUText_field)menues.ConvertMenuFields(this.control)) != null) {
                this.initial_len_x = field.lenx;
            }
        }

        void Update(int _value) {
            this.value = _value < 0 ? 0 : _value;
            this.value = _value > 100 ? 100 : _value;
            boolean bl = this.bShow = this.value != 0;
            if (this.control != 0L) {
                MENUText_field field = (MENUText_field)menues.ConvertMenuFields(this.control);
                if (field != null) {
                    field.lenx = this.initial_len_x * this.value / 100;
                    menues.UpdateMenuField(field);
                }
                menues.SetShowField(this.control, this.bShow);
            }
        }
    }

    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }
}

