/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.Helper;
import menu.KeyPair;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.menues;
import menuscript.Converts;
import menuscript.office.ApplicationTab;
import menuscript.office.IChoosedata;
import menuscript.office.IVehicleDetailesListener;
import menuscript.office.ManageFleetVehicles;
import menuscript.office.ManageFlitManager;
import menuscript.office.OfficeMenu;
import menuscript.office.PopUpSearch;
import menuscript.office.VehicleDetails;
import menuscript.table.ICompareLines;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrconfig.IconMappings;
import rnrcore.Log;
import rnrcore.eng;

public class ManageFleet
extends ApplicationTab {
    private static final String TAB_NAME = "MANAGE FLEET";
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TABLE_MYFLEET = "MF - My Fleet - TABLEGROUP - 10 38";
    private static final String RANGER_MYFLEET = "MF - My Fleet - Tableranger";
    private static final String TABLE_MYFLEET_LINE = "Tablegroup - ELEMENTS - My Fleet";
    private static final String[] TABLE_MYFLEET_ELEMENTS = new String[]{"MF - My Fleet - MY FLEET - Vehicle", "MF - My Fleet - MY FLEET - Price", "MF - My Fleet - DEALER OFFERS - Vehicle", "MF - My Fleet - DEALER OFFERS - Price"};
    private static final int MYFLEET_MYVEHICLE = 0;
    private static final int MYFLEET_MYPRICE = 1;
    private static final int MYFLEET_DEALERVEHICLE = 2;
    private static final int MYFLEET_DEALERPRICE = 3;
    private static final String[] MYFLEET_FULL_INFO = new String[]{"MF - My Fleet Specifications - License Plate - VALUE", "MF - My Fleet Specifications - Type - VALUE", "MF - My Fleet Specifications - Condition - VALUE", "MF - My Fleet Specifications - Wear - VALUE", "MF - My Fleet Specifications - Speed - VALUE", "MF - My Fleet Specifications - Load Safety - VALUE", "MF - My Fleet Specifications - Horse Power - VALUE", "MF - My Fleet Specifications - Color - VALUE", "MF - My Fleet Specifications - 3 ROWS"};
    private static final int MYFLEET_LICENSE = 0;
    private static final int MYFLEET_TYPE = 1;
    private static final int MYFLEET_CONDITION = 2;
    private static final int MYFLEET_WEAR = 3;
    private static final int MYFLEET_SPEED = 4;
    private static final int MYFLEET_LOADSAFETY = 5;
    private static final int MYFLEET_HORSEPOWER = 6;
    private static final int MYFLEET_COLOR = 7;
    private static final int MYFLEET_VEHICLE_NAME = 8;
    private static final String MYFLEET_PHOTO = "MF - My Fleet Specifications - Photo";
    private static final String MYFLEET_SHOW = "BUTTON - MF - My Fleet Specifications - SHOW";
    private static final String[] MYFLEET_SORT = new String[]{"BUTTON - MF - My Fleet - Vehicle", "BUTTON - MF - My Fleet - Price", "BUTTON - MF - My Fleet Specifications - License Plate", "BUTTON - MF - My Fleet Specifications - Type", "BUTTON - MF - My Fleet Specifications - Condition", "BUTTON - MF - My Fleet Specifications - Wear", "BUTTON - MF - My Fleet Specifications - Speed", "BUTTON - MF - My Fleet Specifications - Load Safety", "BUTTON - MF - My Fleet Specifications - Horse Power", "BUTTON - MF - My Fleet Specifications - Color"};
    private static final int SORT_MYFLEET_NAME = 0;
    private static final int SORT_MYFLEET_PRICE = 1;
    private static final int SORT_MYFLEET_LICENCE = 2;
    private static final int SORT_MYFLEET_TYPE = 3;
    private static final int SORT_MYFLEET_CONDITION = 4;
    private static final int SORT_MYFLEET_WEAR = 5;
    private static final int SORT_MYFLEET_SPEED = 6;
    private static final int SORT_MYFLEET_LOADSAFETY = 7;
    private static final int SORT_MYFLEET_HORSEPOWER = 8;
    private static final int SORT_MYFLEET_COLOR = 9;
    private static final String TABLE_DEALERFLEET = "MF - Dealer Offers - TABLEGROUP - 11 38";
    private static final String RANGER_DEALERFLEET = "MF - Dealer Offers - Tableranger";
    private static final String TABLE_DEALERFLEET_LINE = "Tablegroup - ELEMENTS - Dealer Offers";
    private static final String[] TABLE_DEALERFLEET_ELEMENTS = new String[]{"MF - Dealer Offers - MY FLEET - Vehicle", "MF - Dealer Offers - MY FLEET - Dealer", "MF - Dealer Offers - MY FLEET - Discount", "MF - Dealer Offers - MY FLEET - Price", "MF - Dealer Offers - DEALER OFFERS - Vehicle", "MF - Dealer Offers - DEALER OFFERS - Dealer", "MF - Dealer Offers - DEALER OFFERS - Discount", "MF - Dealer Offers - DEALER OFFERS - Price"};
    private static final String[] TABLE_DEALERFLEET_ELEMENTS_text = new String[TABLE_DEALERFLEET_ELEMENTS.length];
    private static final int DEALERFLEET_VEHICLE = 4;
    private static final int DEALERFLEET_DEALER = 5;
    private static final int DEALERFLEET_DISCOUNT = 6;
    private static final int DEALERFLEET_PRICE = 7;
    private static final int DEALERFLEET_MYVEHICLE = 0;
    private static final int DEALERFLEET_MYDEALER = 1;
    private static final int DEALERFLEET_MYDISCOUNT = 2;
    private static final int DEALERFLEET_MYPRICE = 3;
    private static final String[] DEALERS_FULL_INFO = new String[]{"MF - Dealer Offers Specifications - Price - VALUE", "MF - Dealer Offers Specifications - Type - VALUE", "MF - Dealer Offers Specifications - Make - VALUE", "MF - Dealer Offers Specifications - Model - VALUE", "MF - Dealer Offers Specifications - Mileage - VALUE", "MF - Dealer Offers Specifications - Suspension - VALUE", "MF - Dealer Offers Specifications - Horse Power - VALUE", "MF - Dealer Offers Specifications - Color - VALUE", "MF - Dealer Offers Specifications - 3 ROWS"};
    private static final int DEALERINF_PRICE = 0;
    private static final int DEALERINF_TYPE = 1;
    private static final int DEALERINF_MAKE = 2;
    private static final int DEALERINF_MODEL = 3;
    private static final int DEALERINF_MILIAGE = 4;
    private static final int DEALERINF_SUSPENSION = 5;
    private static final int DEALERINF_HORSEPOWER = 6;
    private static final int DEALERINF_COLOR = 7;
    private static final int DEALERINF_VEHICLE_NAME = 8;
    private static final String DEALERINF_PHOTO = "MF - Dealer Offers Specifications - Photo";
    private static final String DEALERINF_SHOW = "BUTTON - MF - Dealer Offers Specifications - Dealer Offers Specifications - SHOW";
    private static final String[] DEALER_SORT = new String[]{"BUTTON - MF - Dealer Offers - Vehicle", "BUTTON - MF - Dealer Offers - Dealer", "BUTTON - MF - Dealer Offers - Discount", "BUTTON - MF - Dealer Offers - Price", "BUTTON - MF - Dealer Offers Specifications - Price", "BUTTON - MF - Dealer Offers Specifications - Type", "BUTTON - MF - Dealer Offers Specifications - Make", "BUTTON - MF - Dealer Offers Specifications - Model", "BUTTON - MF - Dealer Offers Specifications - Mileage", "BUTTON - MF - Dealer Offers Specifications - Suspension", "BUTTON - MF - Dealer Offers Specifications - Horse Power", "BUTTON - MF - Dealer Offers Specifications - Color"};
    private static final int SORT_DEALER_NAME = 0;
    private static final int SORT_DEALER_DEALERNAME = 1;
    private static final int SORT_DEALER_DISCOUNT = 2;
    private static final int SORT_DEALER_PRICE = 3;
    private static final int SORT_DEALER_PRICE1 = 4;
    private static final int SORT_DEALER_TYPE = 5;
    private static final int SORT_DEALER_MAKE = 6;
    private static final int SORT_DEALER_MODEL = 7;
    private static final int SORT_DEALER_MILEAGE = 8;
    private static final int SORT_DEALER_SUSPENSION = 9;
    private static final int SORT_DEALER_HORSEPOWER = 10;
    private static final int SORT_DEALER_COLOR = 11;
    private static final String[] LINE_MYCAR = new String[]{"MF - My Fleet - MY TRUCK - Vehicle", "MF - My Fleet - MY TRUCK - Price"};
    private static final String[] ACTION_BUTTONS = new String[]{"BUTTON - MF - MY TRUCK", "BUTTON - MF - SELL", "BUTTON - MF - PURCHASE", "BUTTON - MF - MY TRUCK - GRAY"};
    private static final String[] ACTION_METHODS = new String[]{"onMyTruck", "onSell", "onPurchase"};
    private static final int SELECT_MYTRUCK = 0;
    private static final int SELECT_SELL = 1;
    private static final int SELECT_PURCHASE = 2;
    private static final int SELECT_MYTRUCK_GRAY = 3;
    private long select_mytruck_button = 0L;
    private long select_mytruck_button_gray = 0L;
    private static String SEARCH_MENU_GROUP = "Tablegroup - ELEMENTS - MF Filer Menu";
    private static final String[] SEARCH_BUTTONS = new String[]{"BUTTON PopUP - MF - Dealer Offers Search - Price", "BUTTON PopUP - MF - Dealer Offers Search - Type", "BUTTON PopUP - MF - Dealer Offers Search - Make", "BUTTON PopUP - MF - Dealer Offers Search - Model", "BUTTON PopUP - MF - Dealer Offers Search - Mileage", "BUTTON PopUP - MF - Dealer Offers Search - Suspension", "BUTTON PopUP - MF - Dealer Offers Search - Horse Power", "BUTTON PopUP - MF - Dealer Offers Search - Color"};
    private static final String[] SEARCH_TEXTS = new String[]{"MF - Dealer Offers Search - Price - VALUE", "MF - Dealer Offers Search - Type - VALUE", "MF - Dealer Offers Search - Make - VALUE", "MF - Dealer Offers Search - Model - VALUE", "MF - Dealer Offers Search - Mileage - VALUE", "MF - Dealer Offers Search - Suspension - VALUE", "MF - Dealer Offers Search - Horse Power - VALUE", "MF - Dealer Offers Search - Color - VALUE"};
    private static final String SEARCH_MODEL_BUTTON = "MF - Dealer Offers Search - Model - VALUE-Select";
    private static final int SEARCH_PRICE = 0;
    private static final int SEARCH_TYPE = 1;
    private static final int SEARCH_MAKE = 2;
    private static final int SEARCH_MODEL = 3;
    private static final int SEARCH_MILEAGE = 4;
    private static final int SEARCH_SUSPENSION = 5;
    private static final int SEARCH_HORSES = 6;
    private static final int SEARCH_COLOR = 7;
    private static final String[] SUMMARY_TEXTS = new String[]{"Vehicles To Sell VALUE FLEET", "Vehicles To Purchase VALUE FLEET", "Proceeds VALUE FLEET", "Expenses VALUE FLEET", "Overhead VALUE FLEET", "Total VALUE FLEET"};
    private static final int SUMMARY_VEHICLESTOSALE = 0;
    private static final int SUMMARY_VEHICLESTOPURCHASE = 1;
    private static final int SUMMARY_PROCEEDS = 2;
    private static final int SUMMARY_EXPENSES = 3;
    private static final int SUMMARY_OVERHEAD = 4;
    private static final int SUMMARY_TOTAL = 5;
    private static final String MACRO_MONEY = "MONEY";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_SIGN = "SIGN";
    private static final String[] SUMMARY_MACRO = new String[]{"VALUE", "VALUE", "MONEY", "MONEY", "MONEY", "MONEY"};
    private static final String[] SUMMARY_MACRO2 = new String[]{"", "", "SIGN", "SIGN", "SIGN", "SIGN"};
    private static final int[] SUMMARY_MACRO_NUM = new int[]{1, 1, 2, 2, 2, 2};
    private long _menu = 0L;
    private VehicleDetails vehicleDetailes = null;
    private MyFleet myFleetTable = null;
    private DealerFleet dealerFleetTable = null;
    private SearchFilter purchaseFilter = null;
    private String[] initialSummaryTexts = new String[SUMMARY_TEXTS.length];
    private long[] summaryTexts = new long[SUMMARY_TEXTS.length];
    TermometrClass[] termometrs = null;
    private static final String[] TERMOMETRS_NAMES = new String[]{"MF - My Fleet Specifications - Condition - VALUE - INDICATOR", "MF - My Fleet Specifications - Wear - VALUE - INDICATOR", "MF - My Fleet Specifications - Speed - VALUE - INDICATOR", "MF - My Fleet Specifications - Load Safety - VALUE - INDICATOR"};
    private static final int TERMOMETR_Condition = 0;
    private static final int TERMOMETR_Wear = 1;
    private static final int TERMOMETR_Speed = 2;
    private static final int TERMOMETR_Load_Safety = 3;
    sort myfleet_table_sort = new sort(1, true);
    sort dealerfleet_table_sort = new sort(8, true);
    ManageFlitManager.Filter filter = null;

    public ManageFleet(long _menu, OfficeMenu parent) {
        super(_menu, TAB_NAME, parent);
        ManageFlitManager.getManageFlitManager().OnEnterMenu();
        this.init(_menu);
    }

    private void init(long _menu) {
        this._menu = _menu;
        this.myFleetTable = new MyFleet(_menu);
        this.dealerFleetTable = new DealerFleet(_menu);
        this.purchaseFilter = new SearchFilter(_menu);
        for (int i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summaryTexts[i] = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
            this.initialSummaryTexts[i] = menues.GetFieldText(this.summaryTexts[i]);
        }
        this.vehicleDetailes = new VehicleDetails(_menu);
        this.vehicleDetailes.addListener(new closeVehicleDeatiled());
        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - MANAGE FLEET");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    public void afterInit() {
        this.termometrs = new TermometrClass[TERMOMETRS_NAMES.length];
        for (int i = 0; i < TERMOMETRS_NAMES.length; ++i) {
            this.termometrs[i] = new TermometrClass(this._menu, TERMOMETRS_NAMES[i]);
        }
        this.myFleetTable.afterInit();
        this.dealerFleetTable.afterInit();
        this.purchaseFilter.afterInit();
        this.vehicleDetailes.afterInit();
        this.vehicleDetailes.hide();
        this.update();
    }

    public boolean update() {
        if (!super.update()) {
            return false;
        }
        this.myFleetTable.updateTable();
        this.dealerFleetTable.updateTable();
        this.refresh_summary();
        this.makeUpdate();
        return true;
    }

    public void deinit() {
        this.purchaseFilter.deinit();
        this.myFleetTable.table.deinit();
        this.dealerFleetTable.table.deinit();
        this.vehicleDetailes.DeInit();
        ManageFlitManager.getManageFlitManager().OnLeaveMenu();
    }

    private void refresh_summary() {
        ManageFlitManager.Summary summary = ManageFlitManager.getManageFlitManager().GetSummary();
        for (int i = 0; i < this.summaryTexts.length; ++i) {
            KeyPair[] keys = new KeyPair[SUMMARY_MACRO_NUM[i]];
            switch (i) {
                case 0: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.vehiles_to_sell);
                    break;
                }
                case 1: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.vehiles_to_purchase);
                    break;
                }
                case 2: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.proceeds));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.proceeds) >= 0 ? "" : "-");
                    break;
                }
                case 3: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.expenses));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.expenses) >= 0 ? "" : "-");
                    break;
                }
                case 4: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.overhead));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.overhead) >= 0 ? "" : "-");
                    break;
                }
                case 5: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.total));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.total) >= 0 ? "" : "-");
                }
            }
            menues.SetFieldText(this.summaryTexts[i], MacroKit.Parse(this.initialSummaryTexts[i], keys));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.summaryTexts[i]));
        }
    }

    public void apply() {
        this.setDirty();
        int err = ManageFlitManager.getManageFlitManager().Apply();
        switch (err) {
            case 2: {
                this.makeNotEnoughMoney();
                break;
            }
            case 1: {
                this.makeNotEnoughCars();
            }
        }
        this.update();
    }

    public void discard() {
        this.setDirty();
        ManageFlitManager.getManageFlitManager().Discard();
        this.update();
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(0);
        }
    }

    class closeVehicleDeatiled
    implements IVehicleDetailesListener {
        closeVehicleDeatiled() {
        }

        public void onClose() {
            menues.SetShowField(menues.GetBackMenu(ManageFleet.this._menu), true);
        }
    }

    class SearchFilter
    implements IChoosedata {
        private final String SEARCH_METH = "onSearch";
        private final String SEARCHMODEL_METH = "onModelSearch";
        private final String SEARCHMODEL_ENTER_METH = "onModelEnter";
        private final String SEARCHMODEL_DISMISS_METH = "onModelDismiss";
        private final String ANIMATE_METH = "onAnimate";
        private static final int _IDanimateClose = 1;
        private PopUpSearch search = null;
        private long[] texts = null;
        private long edit_box = 0L;
        private int[] shifts = null;
        private boolean pend_close = false;
        private boolean pend_edit = false;
        private boolean editing = false;
        private boolean pend_exit_edit = false;
        private int filter_current = 0;
        private int filter_choose = 0;
        private String filter_text = "";
        private ManageFlitManager.Filter filter = null;
        private boolean canEdit = false;

        SearchFilter(long _menu) {
            this.search = new PopUpSearch(_menu, SEARCH_MENU_GROUP);
            this.search.addListener(this);
            this.shifts = new int[SEARCH_BUTTONS.length];
            int i = 0;
            while (i < SEARCH_BUTTONS.length) {
                long sbutt = menues.FindFieldInMenu(_menu, SEARCH_BUTTONS[i]);
                MENUsimplebutton_field button = menues.ConvertSimpleButton(sbutt);
                button.userid = i++;
                this.shifts[i] = button.poy;
                menues.SetScriptOnControl(_menu, button, this, "onSearch", 4L);
                menues.UpdateField(button);
            }
            i = this.shifts.length - 1;
            while (i >= 0) {
                int n = i--;
                this.shifts[n] = this.shifts[n] - this.shifts[0];
            }
            menues.SetScriptObjectAnimation(0L, 1L, this, "onAnimate");
            this.texts = new long[SEARCH_TEXTS.length];
            for (i = 0; i < SEARCH_TEXTS.length; ++i) {
                String[] textdata;
                this.texts[i] = menues.FindFieldInMenu(_menu, SEARCH_TEXTS[i]);
                if (i == 3) {
                    menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.texts[i]), this, "onModelDismiss", 19L);
                    menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.texts[i]), this, "onModelEnter", 16L);
                }
                if ((textdata = this.getFilterValuesShort(i)).length <= 0) continue;
                menues.SetFieldText(this.texts[i], textdata[textdata.length - 1]);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[i]));
            }
            this.edit_box = menues.FindFieldInMenu(_menu, ManageFleet.SEARCH_MODEL_BUTTON);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_box), this, "onModelSearch", 4L);
            this.filter = new ManageFlitManager.Filter();
            this.filter.fileds = new int[8];
            this.filter.model_user = "";
        }

        public void afterInit() {
            menues.SetShowField(this.edit_box, true);
            menues.SetShowField(this.texts[3], false);
        }

        public void deinit() {
            menues.StopScriptAnimation(1L);
            this.search.removeListener(this);
        }

        public void onSearch(long _menu, MENUsimplebutton_field field) {
            this.search.show(this.shifts[field.userid]);
            String[] data = this.getFilterValues(field.userid);
            this.search.setData(data);
            this.filter_current = field.userid;
            if (3 == field.userid && this.canEdit) {
                this.pend_edit = true;
            }
        }

        public void onModelSearch(long _menu, MENUsimplebutton_field field) {
            if (!this.canEdit) {
                return;
            }
            this.pend_edit = true;
            this.search.show(this.shifts[3]);
            this.search.setData(this.getFilterValues(3));
            this.filter_current = 3;
        }

        public void onModelEnter(long _menu, MENUEditBox field) {
            this.selectData(this.search.getFocusedData());
        }

        public void onModelDismiss(long _menu, MENUEditBox field) {
            this.selectData(this.search.getFocusedData());
        }

        public void onChange(long _menu, long field) {
            this.filter_choose = -1;
            String newtext = menues.GetFieldText(field);
            if (newtext.compareTo(this.filter_text) != 0) {
                this.filter_text = newtext;
                this.search.setData(this.getModelFilterValues());
            }
        }

        public void onAnimate(long cookie, double time) {
            this.canEdit = true;
            if (this.pend_close) {
                this.editing = false;
                this.pend_exit_edit = true;
                this.search.hide();
                this.updateFilter();
            }
            this.pend_close = false;
            if (this.canEdit && this.pend_edit) {
                menues.SetShowField(this.edit_box, false);
                menues.SetShowField(this.texts[3], true);
                menues.setFocusWindow(menues.ConvertWindow((long)menues.GetBackMenu((long)((ManageFleet)ManageFleet.this)._menu)).ID);
                menues.setfocuscontrolonmenu(ManageFleet.this._menu, this.texts[3]);
                this.editing = true;
            }
            this.pend_edit = false;
            if (this.pend_exit_edit) {
                menues.SetShowField(this.edit_box, true);
                menues.SetShowField(this.texts[3], false);
                this.editing = false;
            }
            this.pend_exit_edit = false;
            if (this.editing) {
                this.onChange(ManageFleet.this._menu, this.texts[3]);
                this.search.show(this.shifts[3]);
                menues.setFocusWindow(menues.ConvertWindow((long)menues.GetBackMenu((long)((ManageFleet)ManageFleet.this)._menu)).ID);
                menues.setfocuscontrolonmenu(ManageFleet.this._menu, this.texts[3]);
                this.pend_edit = true;
            }
        }

        public void selectData(int data) {
            this.pend_close = true;
            this.filter_choose = data;
            if (this.filter_current == 3) {
                this.filter_text = this.getSelectedString();
                menues.SetFieldText(this.texts[3], this.filter_text);
                menues.SetFieldText(this.edit_box, this.filter_text);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[3]));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
                this.filter_text = "";
            }
        }

        private String getSelectedString() {
            int ch = this.filter_choose;
            int flt = 0;
            switch (this.filter_current) {
                case 0: {
                    flt = 0;
                    break;
                }
                case 1: {
                    flt = 1;
                    break;
                }
                case 2: {
                    flt = 2;
                    break;
                }
                case 3: {
                    flt = 3;
                    break;
                }
                case 4: {
                    flt = 4;
                    break;
                }
                case 5: {
                    flt = 6;
                    break;
                }
                case 6: {
                    flt = 5;
                    break;
                }
                case 7: {
                    flt = 7;
                }
            }
            Vector<ManageFlitManager.FilterField> fields = ManageFlitManager.getManageFlitManager().GetFilterFields(flt);
            int res_choose = 0;
            for (ManageFlitManager.FilterField f : fields) {
                if ((ch -= f.show_me ? 1 : 0) < 0) break;
                ++res_choose;
            }
            return this.getFilterValuesShort(this.filter_current)[res_choose];
        }

        void updateFilter() {
            int ch = this.filter_choose;
            int flt = 0;
            switch (this.filter_current) {
                case 0: {
                    flt = 0;
                    break;
                }
                case 1: {
                    flt = 1;
                    break;
                }
                case 2: {
                    flt = 2;
                    break;
                }
                case 3: {
                    flt = 3;
                    break;
                }
                case 4: {
                    flt = 4;
                    break;
                }
                case 5: {
                    flt = 6;
                    break;
                }
                case 6: {
                    flt = 5;
                    break;
                }
                case 7: {
                    flt = 7;
                }
            }
            Vector<ManageFlitManager.FilterField> fields = ManageFlitManager.getManageFlitManager().GetShortFilterFields(flt);
            int res_choose = 0;
            for (ManageFlitManager.FilterField f : fields) {
                if ((ch -= f.show_me ? 1 : 0) < 0) break;
                ++res_choose;
            }
            this.filter.fileds[flt] = res_choose;
            this.filter.model_user = this.filter_text;
            String shortName = fields.get((int)res_choose).name;
            ManageFlitManager.getManageFlitManager().SetFilter(this.filter);
            ManageFleet.this.dealerFleetTable.updateTable();
            if (this.filter_current != 3) {
                menues.SetFieldText(this.texts[this.filter_current], shortName);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
            } else if (this.filter_choose == -1) {
                menues.SetFieldText(this.texts[this.filter_current], this.filter_text);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
                menues.SetFieldText(this.edit_box, this.filter_text);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
            } else {
                menues.SetFieldText(this.texts[this.filter_current], shortName);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
                menues.SetFieldText(this.edit_box, shortName);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
            }
        }

        String[] getFilterValues(int taken) {
            return this.getFilterValuesAllTypes(taken, false);
        }

        String[] getFilterValuesShort(int taken) {
            return this.getFilterValuesAllTypes(taken, true);
        }

        private String[] getFilterValuesAllTypes(int taken, boolean is_short) {
            int flt = 0;
            switch (taken) {
                case 0: {
                    flt = 0;
                    break;
                }
                case 1: {
                    flt = 1;
                    break;
                }
                case 2: {
                    flt = 2;
                    break;
                }
                case 3: {
                    flt = 3;
                    break;
                }
                case 4: {
                    flt = 4;
                    break;
                }
                case 5: {
                    flt = 6;
                    break;
                }
                case 6: {
                    flt = 5;
                    break;
                }
                case 7: {
                    flt = 7;
                }
            }
            Vector<ManageFlitManager.FilterField> fields = null;
            fields = is_short ? ManageFlitManager.getManageFlitManager().GetShortFilterFields(flt) : ManageFlitManager.getManageFlitManager().GetFilterFields(flt);
            int count = 0;
            for (ManageFlitManager.FilterField f : fields) {
                count += f.show_me ? 1 : 0;
            }
            String[] data = new String[count];
            count = 0;
            for (int i = 0; i < fields.size(); ++i) {
                if (!fields.get((int)i).show_me) continue;
                data[count++] = fields.get((int)i).name;
            }
            return data;
        }

        String[] getModelFilterValues() {
            this.filter.model_user = this.filter_text;
            ManageFlitManager.getManageFlitManager().SetFilter(this.filter);
            Vector<ManageFlitManager.FilterField> fields = ManageFlitManager.getManageFlitManager().GetShortFilterFields(3);
            int count = 0;
            for (ManageFlitManager.FilterField f : fields) {
                count += f.show_me ? 1 : 0;
            }
            String[] data = new String[count];
            count = 0;
            for (int i = 0; i < fields.size(); ++i) {
                if (!fields.get((int)i).show_me) continue;
                data[count++] = fields.get((int)i).name;
            }
            return data;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class DealerFleet
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private final String SHOW_METH = "onShow";
        Table table;
        private Dealerfleetline selected = null;
        private table_data_dealerfleet TABLE_DATA = new table_data_dealerfleet();
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_string = null;
        private long vehicle_photo;
        private ManageFleetVehicles vehicles = new ManageFleetVehicles();

        DealerFleet(long _menu) {
            int i;
            this.table = new Table(_menu, ManageFleet.TABLE_DEALERFLEET, ManageFleet.RANGER_DEALERFLEET);
            this.table.setSelectionMode(2);
            this.table.fillWithLines(ManageFleet.XML, ManageFleet.TABLE_DEALERFLEET_LINE, TABLE_DEALERFLEET_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : TABLE_DEALERFLEET_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            this.fullinfotexts = new long[DEALERS_FULL_INFO.length];
            this.fullinfotexts_string = new String[DEALERS_FULL_INFO.length];
            for (i = 0; i < DEALERS_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, DEALERS_FULL_INFO[i]);
                this.fullinfotexts_string[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }
            i = 0;
            while (i < DEALER_SORT.length) {
                long field = menues.FindFieldInMenu(_menu, DEALER_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
            for (i = 0; i < TABLE_DEALERFLEET_ELEMENTS.length; ++i) {
                long[] stat = this.table.getLineStatistics_controls(TABLE_DEALERFLEET_ELEMENTS[i]);
                if (stat == null || stat.length == 0) {
                    Log.menu("ERRORR. MyFleet table. Has no field named " + TABLE_MYFLEET_ELEMENTS[i]);
                }
                TABLE_DEALERFLEET_ELEMENTS_text[i] = menues.GetFieldText(stat[0]);
            }
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ACTION_BUTTONS[2])), this, ACTION_METHODS[2], 4L);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ManageFleet.DEALERINF_SHOW)), this, "onShow", 4L);
            this.vehicle_photo = menues.FindFieldInMenu(_menu, ManageFleet.DEALERINF_PHOTO);
        }

        public void afterInit() {
            this.table.afterInit();
        }

        public void onShow(long _menu, MENUsimplebutton_field button) {
            menues.SetShowField(menues.GetBackMenu(_menu), false);
            ManageFleet.this.vehicleDetailes.show(this.vehicles.get(this.selected.id), this.selected.id, false);
        }

        public void onPurchase(long _menu, MENUsimplebutton_field button) {
            ManageFleet.this.setDirty();
            Vector<ManageFlitManager.VehicleId> res = new Vector<ManageFlitManager.VehicleId>();
            for (Dealerfleetline line : this.TABLE_DATA.all_lines) {
                if (!line.selected || !line.wheather_show) continue;
                res.add(line.id);
            }
            if (!res.isEmpty()) {
                ManageFlitManager.getManageFlitManager().I_Purchase(res);
            }
            ManageFleet.this.update();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(1, ManageFleet.this.dealerfleet_table_sort.type == 1 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 1: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(2, ManageFleet.this.dealerfleet_table_sort.type == 2 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 2: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(3, ManageFleet.this.dealerfleet_table_sort.type == 3 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 3: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(4, ManageFleet.this.dealerfleet_table_sort.type == 4 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 4: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(4, ManageFleet.this.dealerfleet_table_sort.type == 4 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 5: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(5, ManageFleet.this.dealerfleet_table_sort.type == 5 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 6: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(6, ManageFleet.this.dealerfleet_table_sort.type == 6 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 7: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(7, ManageFleet.this.dealerfleet_table_sort.type == 7 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 8: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(8, ManageFleet.this.dealerfleet_table_sort.type == 8 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 9: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(9, ManageFleet.this.dealerfleet_table_sort.type == 9 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 10: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(10, ManageFleet.this.dealerfleet_table_sort.type == 10 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                    break;
                }
                case 11: {
                    ManageFleet.this.dealerfleet_table_sort = new sort(11, ManageFleet.this.dealerfleet_table_sort.type == 11 ? !ManageFleet.this.dealerfleet_table_sort.up : true);
                }
            }
            this.updateTable();
            ManageFleet.this.refresh_summary();
        }

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();
                ch.toshow = true;
                ch.ontop = i == 0;
                ch.item = this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }
            return root;
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        Dealerfleetline data = new Dealerfleetline();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<Dealerfleetline> iter = this.TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private void reciveTableData() {
            if (eng.noNative) {
                return;
            }
            this.TABLE_DATA.all_lines.clear();
            this.vehicles.clear();
            Vector<ManageFlitManager.ShotDealerVehicleInfo> drids = ManageFlitManager.getManageFlitManager().GetDealerVehiclesList(ManageFleet.this.filter, ManageFleet.this.dealerfleet_table_sort.type, ManageFleet.this.dealerfleet_table_sort.up);
            for (ManageFlitManager.ShotDealerVehicleInfo inf : drids) {
                Dealerfleetline data = new Dealerfleetline();
                data.id = inf.id;
                data.isGray = inf.isGray;
                data.price = inf.price;
                data.vehicle_name = inf.vehicle_name;
                data.discount = inf.discount;
                data.dealer_name = inf.dealer_name;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
                this.vehicles.add(inf.id);
            }
            this.buildvoidcells();
        }

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void build_tree_data(ICompareLines comparator) {
            this.table.reciveTreeData(this.convertTableData(), comparator);
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data(new CompareDealerFleet());
            this.table.refresh_no_select();
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            Dealerfleetline line = (Dealerfleetline)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int position = this.table.getMarkedPosition(obj.nativePointer);
            switch (position) {
                case 4: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 5: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.dealer_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 6: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, "" + (int)(100.0 * (double)line.discount))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(TABLE_DEALERFLEET_ELEMENTS_text[6], macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 7: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_MONEY, Helper.convertMoney((int)line.price))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(TABLE_DEALERFLEET_ELEMENTS_text[7], macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 0: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 1: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.dealer_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 2: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, "" + (int)(100.0 * (double)line.discount))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(TABLE_DEALERFLEET_ELEMENTS_text[2], macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 3: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_MONEY, Helper.convertMoney((int)line.price))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(TABLE_DEALERFLEET_ELEMENTS_text[3], macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                }
            }
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
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

        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        }

        @Override
        public void selectLineEvent(Table table, int line) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }
            for (Dealerfleetline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }
            Dealerfleetline data = (Dealerfleetline)table.getItemOnLine((int)line).item;
            data.selected = true;
            this.selected = data;
            this.updateSelectedInfo();
        }

        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }
            for (Dealerfleetline dealerfleetline : this.TABLE_DATA.all_lines) {
                dealerfleetline.selected = false;
            }
            for (Cmenu_TTI cmenu_TTI : lines) {
                if (cmenu_TTI.item == null) continue;
                Dealerfleetline data = (Dealerfleetline)cmenu_TTI.item;
                data.selected = true;
            }
            this.selected = (Dealerfleetline)table.getSelectedData().item;
            this.updateSelectedInfo();
        }

        private void updateSelectedInfo() {
            if (null == this.selected.id) {
                for (int i = 0; i < this.fullinfotexts.length; ++i) {
                    menues.SetFieldText(this.fullinfotexts[i], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
                }
                menues.SetShowField(this.vehicle_photo, false);
                return;
            }
            ManageFlitManager.FullDealerVehicleInfo inf = ManageFlitManager.getManageFlitManager().GetDealerVehiclesInfo(this.selected.id);
            KeyPair[] macro = new KeyPair[1];
            for (int i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                    case 0: {
                        macro[0] = new KeyPair(ManageFleet.MACRO_MONEY, Helper.convertMoney((int)inf.price));
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_string[i], macro));
                        break;
                    }
                    case 1: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.type);
                        break;
                    }
                    case 2: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.make);
                        break;
                    }
                    case 3: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.model);
                        break;
                    }
                    case 4: {
                        macro[0] = new KeyPair(ManageFleet.MACRO_VALUE, Helper.convertMoney((int)inf.mileage));
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_string[i], macro));
                        break;
                    }
                    case 5: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.suspension);
                        break;
                    }
                    case 6: {
                        macro[0] = new KeyPair(ManageFleet.MACRO_VALUE, Helper.convertMoney((int)inf.horsepower));
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_string[i], macro));
                        break;
                    }
                    case 7: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.color);
                        break;
                    }
                    case 8: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.vehicle_name);
                    }
                }
                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }
            menues.SetShowField(this.vehicle_photo, true);
            IconMappings.remapVehicleIcon("" + inf.vehicle_picture, this.vehicle_photo);
        }

        class CompareDealerFleet
        implements ICompareLines {
            CompareDealerFleet() {
            }

            public boolean equal(Object object1, Object object2) {
                if (object1 == null || object2 == null) {
                    return false;
                }
                Dealerfleetline line1 = (Dealerfleetline)object1;
                Dealerfleetline line2 = (Dealerfleetline)object2;
                return line1.id != null && line2.id != null && line1.id.id == line2.id.id;
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class MyFleet
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private final String MYCAR_METH = "onMycar";
        private final String SHOW_METH = "onShow";
        Table table;
        ManageFlitManager.VehicleId selected = null;
        ManageFlitManager.VehicleId focued_selected = null;
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_native = null;
        private table_data_myfleet TABLE_DATA = new table_data_myfleet();
        private String myvehicles_price;
        private String dealers_price;
        private long[] mycar_lines = new long[ManageFleet.access$000().length];
        private String mycarpriceText;
        private Myfleetline mycarinfo = null;
        private long vehicle_photo = 0L;
        private ManageFleetVehicles vehicles = new ManageFleetVehicles();

        MyFleet(long _menu) {
            int i;
            this.table = new Table(_menu, ManageFleet.TABLE_MYFLEET, ManageFleet.RANGER_MYFLEET);
            this.table.setSelectionMode(2);
            this.table.fillWithLines(ManageFleet.XML, ManageFleet.TABLE_MYFLEET_LINE, TABLE_MYFLEET_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : TABLE_MYFLEET_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            this.fullinfotexts = new long[MYFLEET_FULL_INFO.length];
            this.fullinfotexts_native = new String[MYFLEET_FULL_INFO.length];
            for (i = 0; i < MYFLEET_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, MYFLEET_FULL_INFO[i]);
            }
            i = 0;
            while (i < MYFLEET_SORT.length) {
                long field = menues.FindFieldInMenu(_menu, MYFLEET_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
            long[] stat_myvehicles_price = this.table.getLineStatistics_controls(TABLE_MYFLEET_ELEMENTS[1]);
            long[] stat_dealers_price = this.table.getLineStatistics_controls(TABLE_MYFLEET_ELEMENTS[3]);
            if (stat_myvehicles_price == null || stat_myvehicles_price.length < 1) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + TABLE_MYFLEET_ELEMENTS[1]);
            }
            if (stat_dealers_price == null || stat_dealers_price.length < 1) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + TABLE_MYFLEET_ELEMENTS[3]);
            }
            this.myvehicles_price = menues.GetFieldText(stat_myvehicles_price[0]);
            this.dealers_price = menues.GetFieldText(stat_dealers_price[0]);
            for (int i2 = 0; i2 < LINE_MYCAR.length; ++i2) {
                this.mycar_lines[i2] = menues.FindFieldInMenu(_menu, LINE_MYCAR[i2]);
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.mycar_lines[i2]), this, "onMycar", 2L);
                if (i2 != 1) continue;
                this.mycarpriceText = menues.GetFieldText(this.mycar_lines[i2]);
            }
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ACTION_BUTTONS[0])), this, ACTION_METHODS[0], 4L);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ACTION_BUTTONS[1])), this, ACTION_METHODS[1], 4L);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ManageFleet.MYFLEET_SHOW)), this, "onShow", 4L);
            this.vehicle_photo = menues.FindFieldInMenu(_menu, ManageFleet.MYFLEET_PHOTO);
            ManageFleet.this.select_mytruck_button = menues.FindFieldInMenu(_menu, ACTION_BUTTONS[0]);
            ManageFleet.this.select_mytruck_button_gray = menues.FindFieldInMenu(_menu, ACTION_BUTTONS[3]);
        }

        public void afterInit() {
            for (int i = 0; i < MYFLEET_FULL_INFO.length; ++i) {
                if (this.fullinfotexts[i] == 0L) continue;
                this.fullinfotexts_native[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }
            if (ManageFleet.this.select_mytruck_button_gray != 0L) {
                menues.SetShowField(ManageFleet.this.select_mytruck_button_gray, false);
                menues.SetBlindess(ManageFleet.this.select_mytruck_button_gray, true);
                menues.SetIgnoreEvents(ManageFleet.this.select_mytruck_button_gray, true);
            }
            this.table.afterInit();
        }

        public void onShow(long _menu, MENUsimplebutton_field button) {
            if (null != this.focued_selected && this.focued_selected.id != 0) {
                menues.SetShowField(menues.GetBackMenu(_menu), false);
                ManageFleet.this.vehicleDetailes.show(this.vehicles.get(this.focued_selected), this.focued_selected, true);
            }
        }

        public void onSell(long _menu, MENUsimplebutton_field button) {
            ManageFleet.this.setDirty();
            Vector<ManageFlitManager.VehicleId> res = new Vector<ManageFlitManager.VehicleId>();
            for (Myfleetline line : this.TABLE_DATA.all_lines) {
                if (!line.selected || !line.wheather_show || line.id == null || line.id.id == 0) continue;
                res.add(line.id);
            }
            if (!res.isEmpty()) {
                ManageFlitManager.getManageFlitManager().I_Sell(res);
            }
            ManageFleet.this.update();
        }

        public void onMyTruck(long _menu, MENUsimplebutton_field button) {
            ManageFleet.this.setDirty();
            if (null == this.selected || this.selected.id == 0 || !ManageFlitManager.getManageFlitManager().Can_I_Take()) {
                return;
            }
            ManageFlitManager.getManageFlitManager().I_Take(this.selected);
            this.updateTable();
            ManageFleet.this.refresh_summary();
        }

        public void onMycar(long _menu, MENUbutton_field button) {
            for (int i = 0; i < this.mycar_lines.length; ++i) {
                menues.SetFieldState(this.mycar_lines[i], 1);
            }
            this.updateSelectedInfo(this.mycarinfo.id);
        }

        private void updateMyCar() {
            if (eng.noNative) {
                return;
            }
            ManageFlitManager.ShotMyVehicleInfo inf = ManageFlitManager.getManageFlitManager().GetMyVehicleInfo();
            this.mycarinfo = new Myfleetline();
            this.mycarinfo.id = inf.id;
            if (inf.id != null && inf.id.id != 0) {
                this.mycarinfo.price = inf.price;
                this.mycarinfo.vehicle_name = inf.vehicle_name;
                block8: for (int i = 0; i < this.mycar_lines.length; ++i) {
                    switch (i) {
                        case 0: {
                            menues.SetFieldText(this.mycar_lines[i], this.mycarinfo.vehicle_name);
                            menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));
                            continue block8;
                        }
                        case 1: {
                            KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_MONEY, Helper.convertMoney((int)this.mycarinfo.price))};
                            menues.SetShowField(this.mycar_lines[i], true);
                            menues.SetFieldText(this.mycar_lines[i], MacroKit.Parse(this.mycarpriceText, macro));
                            menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));
                        }
                    }
                }
            } else {
                this.mycarinfo.price = 0.0f;
                this.mycarinfo.vehicle_name = "---";
                block9: for (int i = 0; i < this.mycar_lines.length; ++i) {
                    switch (i) {
                        case 0: {
                            menues.SetFieldText(this.mycar_lines[i], "---");
                            menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));
                            continue block9;
                        }
                        case 1: {
                            menues.SetShowField(this.mycar_lines[i], true);
                            menues.SetFieldText(this.mycar_lines[i], "---");
                            menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));
                        }
                    }
                }
            }
            this.vehicles.add(this.mycarinfo.id);
        }

        private void deselectMyCar() {
            for (int i = 0; i < this.mycar_lines.length; ++i) {
                menues.SetFieldState(this.mycar_lines[i], 0);
            }
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    ManageFleet.this.myfleet_table_sort = new sort(1, ManageFleet.this.myfleet_table_sort.type == 1 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 1: {
                    ManageFleet.this.myfleet_table_sort = new sort(2, ManageFleet.this.myfleet_table_sort.type == 2 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 2: {
                    ManageFleet.this.myfleet_table_sort = new sort(3, ManageFleet.this.myfleet_table_sort.type == 3 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 3: {
                    ManageFleet.this.myfleet_table_sort = new sort(4, ManageFleet.this.myfleet_table_sort.type == 4 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 4: {
                    ManageFleet.this.myfleet_table_sort = new sort(5, ManageFleet.this.myfleet_table_sort.type == 5 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 5: {
                    ManageFleet.this.myfleet_table_sort = new sort(6, ManageFleet.this.myfleet_table_sort.type == 6 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 6: {
                    ManageFleet.this.myfleet_table_sort = new sort(7, ManageFleet.this.myfleet_table_sort.type == 7 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 7: {
                    ManageFleet.this.myfleet_table_sort = new sort(8, ManageFleet.this.myfleet_table_sort.type == 8 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 8: {
                    ManageFleet.this.myfleet_table_sort = new sort(9, ManageFleet.this.myfleet_table_sort.type == 9 ? !ManageFleet.this.myfleet_table_sort.up : true);
                    break;
                }
                case 9: {
                    ManageFleet.this.myfleet_table_sort = new sort(10, ManageFleet.this.myfleet_table_sort.type == 10 ? !ManageFleet.this.myfleet_table_sort.up : true);
                }
            }
            this.updateTable();
            ManageFleet.this.refresh_summary();
        }

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();
                ch.toshow = true;
                ch.ontop = i == 0;
                ch.item = this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }
            return root;
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        Myfleetline data = new Myfleetline();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<Myfleetline> iter = this.TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void build_tree_data(ICompareLines comparator) {
            this.table.reciveTreeData(this.convertTableData(), comparator);
        }

        private void reciveTableData() {
            if (eng.noNative) {
                return;
            }
            this.TABLE_DATA.all_lines.clear();
            this.vehicles.clear();
            Vector<ManageFlitManager.ShotMyVehicleInfo> drids = ManageFlitManager.getManageFlitManager().GetMyVehiclesList(ManageFleet.this.myfleet_table_sort.type, ManageFleet.this.myfleet_table_sort.up);
            for (ManageFlitManager.ShotMyVehicleInfo inf : drids) {
                Myfleetline data = new Myfleetline();
                data.id = inf.id;
                data.isGray = inf.isGray;
                data.price = inf.price;
                data.vehicle_name = inf.vehicle_name;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
                this.vehicles.add(inf.id);
            }
            if (null != this.mycarinfo) {
                this.vehicles.add(this.mycarinfo.id);
            }
            this.buildvoidcells();
        }

        public void updateTable() {
            if (ManageFlitManager.getManageFlitManager().Can_I_Take()) {
                if (ManageFleet.this.select_mytruck_button_gray != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button_gray, false);
                }
                if (ManageFleet.this.select_mytruck_button != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button, true);
                }
            } else {
                if (ManageFleet.this.select_mytruck_button_gray != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button_gray, true);
                }
                if (ManageFleet.this.select_mytruck_button != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button, false);
                }
            }
            this.updateMyCar();
            this.selected = null;
            this.reciveTableData();
            this.build_tree_data(new CompareMyFleet());
            this.table.refresh_no_select();
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            Myfleetline line = (Myfleetline)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int position = this.table.getMarkedPosition(obj.nativePointer);
            switch (position) {
                case 0: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 1: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_MONEY, Helper.convertMoney((int)line.price))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.myvehicles_price, macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 2: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 3: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(ManageFleet.MACRO_MONEY, Helper.convertMoney((int)line.price))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.dealers_price, macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                }
            }
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
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

        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        }

        @Override
        public void selectLineEvent(Table table, int line) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }
            for (Myfleetline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }
            Myfleetline data = (Myfleetline)table.getItemOnLine((int)line).item;
            data.selected = true;
            this.selected = data.id;
            this.deselectMyCar();
            this.updateSelectedInfo(this.selected);
        }

        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }
            for (Myfleetline myfleetline : this.TABLE_DATA.all_lines) {
                myfleetline.selected = false;
            }
            for (Cmenu_TTI cmenu_TTI : lines) {
                if (cmenu_TTI.item == null) continue;
                Myfleetline data = (Myfleetline)cmenu_TTI.item;
                data.selected = true;
            }
            this.selected = ((Myfleetline)table.getSelectedData().item).id;
            this.deselectMyCar();
            this.updateSelectedInfo(this.selected);
        }

        private void updateSelectedInfo(ManageFlitManager.VehicleId id) {
            int i;
            this.focued_selected = id;
            if (null == id || id.id == 0) {
                int i2;
                for (i2 = 0; i2 < this.fullinfotexts.length; ++i2) {
                    menues.SetFieldText(this.fullinfotexts[i2], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i2]));
                }
                for (i2 = 0; i2 < ManageFleet.this.termometrs.length; ++i2) {
                    ManageFleet.this.termometrs[i2].Update(0);
                }
                menues.SetShowField(this.vehicle_photo, false);
                return;
            }
            ManageFlitManager.FullMyVehicleInfo inf = ManageFlitManager.getManageFlitManager().GetMyVehiclesInfo(id);
            for (i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                    case 0: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.license_plate);
                        break;
                    }
                    case 1: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.type);
                        break;
                    }
                    case 2: {
                        KeyPair[] macro0 = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, Converts.ConvertNumeric((int)(100.0 * (double)inf.condition)))};
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro0));
                        break;
                    }
                    case 3: {
                        KeyPair[] macro1 = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, Converts.ConvertNumeric((int)(100.0 * (double)inf.wear)))};
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro1));
                        break;
                    }
                    case 4: {
                        KeyPair[] macro2 = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, Converts.ConvertNumeric((int)(100.0 * (double)inf.speed)))};
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro2));
                        break;
                    }
                    case 5: {
                        KeyPair[] macro3 = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, Converts.ConvertNumeric((int)(100.0 * (double)inf.load_safety)))};
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro3));
                        break;
                    }
                    case 6: {
                        KeyPair[] macro4 = new KeyPair[]{new KeyPair(ManageFleet.MACRO_VALUE, Converts.ConvertNumeric((int)inf.horse_power))};
                        menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro4));
                        break;
                    }
                    case 7: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.color);
                        break;
                    }
                    case 8: {
                        menues.SetFieldText(this.fullinfotexts[i], inf.vehicle_name);
                    }
                }
                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }
            block20: for (i = 0; i < ManageFleet.this.termometrs.length; ++i) {
                switch (i) {
                    case 0: {
                        ManageFleet.this.termometrs[i].Update((int)(100.0 * (double)inf.condition));
                        continue block20;
                    }
                    case 1: {
                        ManageFleet.this.termometrs[i].Update((int)(100.0 * (double)inf.wear));
                        continue block20;
                    }
                    case 2: {
                        ManageFleet.this.termometrs[i].Update((int)(100.0 * (double)inf.speed));
                        continue block20;
                    }
                    case 3: {
                        ManageFleet.this.termometrs[i].Update((int)(100.0 * (double)inf.load_safety));
                    }
                }
            }
            menues.SetShowField(this.vehicle_photo, true);
            IconMappings.remapVehicleIcon("" + inf.vehicle_picture, this.vehicle_photo);
        }

        class CompareMyFleet
        implements ICompareLines {
            CompareMyFleet() {
            }

            public boolean equal(Object object1, Object object2) {
                if (object1 == null || object2 == null) {
                    return false;
                }
                Myfleetline line1 = (Myfleetline)object1;
                Myfleetline line2 = (Myfleetline)object2;
                return line1.id != null && line2.id != null && line1.id.id == line2.id.id;
            }
        }
    }

    static class table_data_dealerfleet {
        Vector<Dealerfleetline> all_lines = new Vector();

        table_data_dealerfleet() {
        }
    }

    static class Dealerfleetline {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        String dealer_name;
        float discount;
        float price;
        boolean isGray;
        boolean selected = false;
        boolean wheather_show;

        Dealerfleetline() {
        }
    }

    static class table_data_myfleet {
        Vector<Myfleetline> all_lines = new Vector();

        table_data_myfleet() {
        }
    }

    static class Myfleetline {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        float price;
        boolean isGray;
        boolean selected = false;
        boolean wheather_show;

        Myfleetline() {
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
}

