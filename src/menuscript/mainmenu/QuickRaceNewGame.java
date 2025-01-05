/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.JavaEvents;
import menu.KeyPair;
import menu.ListOfAlternatives;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RadioGroupSmartSwitch;
import menu.TableOfElements;
import menu.menues;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.QuickRace_RestyleVehicle;
import menuscript.mainmenu.SaveLoadCommonManagement;
import menuscript.mainmenu.TopWindow;
import menuscript.parametrs.ParametrsBlock;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrcore.Log;
import rnrcore.loc;

public class QuickRaceNewGame
extends PanelDialog {
    protected static String TABLE_DEVICE = "Tab0 - START SETTINGS";
    protected static String TABLE_SETTINGS = "TABLEGROUP - INSTANT ORDER - NEW GAME - START SETTINGS - 5 60";
    protected static String START_PARAMS_DEFAULTS = "INSTANT ORDER - NEW GAME - START SETTINGS DEFAULT";
    protected static String OK_BUTTON = "QUICK RACE - NEW GAME OK";
    protected static String OK_BUTTON_GRAY = "QUICK RACE - NEW GAME OK - GRAY";
    private long ok_button = 0L;
    private long ok_gray = 0L;
    private boolean choose_gray_wh = false;
    private boolean choose_gray_veh = false;
    private static final String[] SETTINGSTITLES = new String[]{"LOD TITLE", "INSTANT ORDER START TIME TITLE", "INSTANT ORDER TIME OF DAY FREEZE TITLE", "INSTANT ORDER WEATHER AT START TITLE", "INSTANT ORDER WEATHER CHANGES TITLE"};
    private static final String[] LODs = new String[]{loc.getMENUString("LOD_EASY"), loc.getMENUString("LOD_NORMAL"), loc.getMENUString("LOD_HARD")};
    private static final String[] START_TIMEs = new String[]{loc.getMENUString("START_TIME_MORNING"), loc.getMENUString("START_TIME_NOON"), loc.getMENUString("START_TIME_EVENING"), loc.getMENUString("START_TIME_NIGHT")};
    private static final String[] START_WEATHERs = new String[]{loc.getMENUString("START_WEATHER_FINE"), loc.getMENUString("START_WEATHER_CLOUDY"), loc.getMENUString("START_WEATHER_RAINY")};
    private static final String[] WEATHER_CHANGES = new String[]{loc.getMENUString("WEATHER_CHANGES_FAST"), loc.getMENUString("WEATHER_CHANGES_MODERATE"), loc.getMENUString("WEATHER_CHANGES_SLOW"), loc.getMENUString("WEATHER_CHANGES_NEVER")};
    protected ParametrsBlock param_values = new ParametrsBlock();
    private TableOfElements table_settings = null;
    ListOfAlternatives settings0 = null;
    ListOfAlternatives settings1 = null;
    RadioGroupSmartSwitch settings2 = null;
    ListOfAlternatives settings3 = null;
    ListOfAlternatives settings4 = null;
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List";
    private static final String RADIOGROUPSMARTSWITCHGROUP = "Tablegroup - ELEMENTS - Switch";
    private Trucks truckTable = null;
    private static final String XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String RANGER_TRUCKS = "Tableranger - INSTANT ORDER - NEW GAME - SELECT VEHICLE";
    private static final String TABLE_TRUCKS = "TABLEGROUP - INSTANT ORDER - NEW GAME - SELECT VEHICLE - 14 40";
    private static final String TABLE_TRUCK_LINE = "Tablegroup - QUICK RACE - SELECT VEHICLE";
    private static final String[] TABLE_TRUCK_ELEMENTS = new String[]{"BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - MODEL VALUE", "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - HORSE POWER VALUE", "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - MODEL VALUE - GRAY", "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - HORSE POWER VALUE - GRAY"};
    public Vector<TruckInfo> in_truck_lines = new Vector();
    public sort out_truck_sort_mode = null;
    public QUICK_RACE_TRUCK_TABLE_DATA int_truck_table_data = new QUICK_RACE_TRUCK_TABLE_DATA();
    private Warehouse warehouseTable = null;
    private static final String RANGER_WAREHOUSE = "Tableranger - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE";
    private static final String TABLE_WAREHOUSE = "TABLEGROUP - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - 14 40";
    private static final String TABLE_WAREHOUSE_LINE = "Tablegroup - QUICK RACE - SELECT RACE";
    private static final String[] TABLE_WAREHOUSE_ELEMENTS = new String[]{"BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME", "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME DETAILS", "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - ROC VALUE", "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME - GRAY", "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME DETAILS - GRAY", "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - ROC VALUE - GRAY"};
    Vector<MapWarehouseInfo> map_warehouses = new Vector();
    public Vector<WarehouseInfo> in_warehouse_lines = new Vector();
    public sort out_warehouse_sort_mode = null;
    public QUICK_RACE_WAREHOUSE_TABLE_DATA int_warehouse_table_data = new QUICK_RACE_WAREHOUSE_TABLE_DATA();
    private static final String RESTYLE_VEHICLE_BUTTON = "INSTANT ORDER - NEW GAME - SELECT VEHICLE - RestyleWindow - RESTYLE";
    private static final String RESTYLE_VEHICLE_BUTTON_GRAY = "INSTANT ORDER - NEW GAME - SELECT VEHICLE - RestyleWindow - RESTYLE - GRAY";
    private static final String RESTYLE_VEHICLE_METHOD = "OnRestyle";
    private long restyle_button = 0L;
    private long restyle_button_gray = 0L;
    QuickRace_RestyleVehicle restyle_vehicle = null;

    public void exitMenu() {
        this.table_settings.DeInit();
        this.deinit();
        super.exitMenu();
    }

    public QuickRaceNewGame(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this.table_settings = new TableOfElements(_menu, TABLE_SETTINGS);
        this.settings0 = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, loc.getMENUString(SETTINGSTITLES[0]), LODs, false);
        this.settings1 = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, loc.getMENUString(SETTINGSTITLES[1]), START_TIMEs, false);
        this.settings2 = new RadioGroupSmartSwitch(parent.menu.XML_FILE, RADIOGROUPSMARTSWITCHGROUP, loc.getMENUString(SETTINGSTITLES[2]), true, false);
        this.settings3 = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, loc.getMENUString(SETTINGSTITLES[3]), START_WEATHERs, false);
        this.settings4 = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, loc.getMENUString(SETTINGSTITLES[4]), WEATHER_CHANGES, false);
        this.settings0.load(_menu);
        this.settings1.load(_menu);
        this.settings2.load(_menu);
        this.settings3.load(_menu);
        this.settings4.load(_menu);
        this.param_values.addParametr("QUICK RACE LOD", 0, 0, this.settings0);
        this.param_values.addParametr("QUICK RACE START_TIME", 1, 1, this.settings1);
        this.param_values.addParametr("QUICK RACE DAY_FREEZE", false, false, this.settings2);
        this.param_values.addParametr("QUICK RACE WEATHER_AT_START", 1, 1, this.settings3);
        this.param_values.addParametr("QUICK RACE WEATHER_CHANGES", 1, 1, this.settings4);
        this.table_settings.insert(this.settings0);
        this.table_settings.insert(this.settings1);
        this.table_settings.insert(this.settings2);
        this.table_settings.insert(this.settings3);
        this.table_settings.insert(this.settings4);
        long field = menues.FindFieldInMenu(_menu, START_PARAMS_DEFAULTS);
        MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
        menues.SetScriptOnControl(_menu, buts, this, "OnStartParamsDefault", 4L);
        this.restyle_button = menues.FindFieldInMenu(_menu, RESTYLE_VEHICLE_BUTTON);
        MENUsimplebutton_field buts2 = menues.ConvertSimpleButton(this.restyle_button);
        menues.SetScriptOnControl(_menu, buts2, this, RESTYLE_VEHICLE_METHOD, 4L);
        this.restyle_button_gray = menues.FindFieldInMenu(_menu, RESTYLE_VEHICLE_BUTTON_GRAY);
        this.ok_button = menues.FindFieldInMenu(_menu, OK_BUTTON);
        this.ok_gray = menues.FindFieldInMenu(_menu, OK_BUTTON_GRAY);
        this.truckTable = new Trucks(_menu, this);
        this.warehouseTable = new Warehouse(_menu, this);
    }

    public void OnRestyle(long _menu, MENUsimplebutton_field button) {
        menues.createSimpleMenu(new QuickRace_RestyleVehicle(this.int_truck_table_data.current_truck_id, this));
    }

    public void OnStartParamsDefault(long _menu, MENUsimplebutton_field button) {
        this.param_values.onDefault();
    }

    public void afterInit() {
        this.table_settings.initTable();
        this.truckTable.afterInit();
        this.warehouseTable.afterInit();
        super.afterInit();
        JavaEvents.SendEvent(65, 7, this.param_values);
        this.param_values.onUpdate();
    }

    public void update() {
        super.update();
        int old_truck_selection = this.int_truck_table_data.current_truck_id;
        this.truckTable.updateTable();
        for (int i = 0; i < ((Trucks)this.truckTable).TABLE_DATA.all_lines.size(); ++i) {
            if (((Trucks)this.truckTable).TABLE_DATA.all_lines.elementAt((int)i).truck_id != old_truck_selection) continue;
            this.truckTable.table.select_line_by_data(((Trucks)this.truckTable).TABLE_DATA.all_lines.elementAt(i));
        }
        int old_warehouse_selection = this.int_warehouse_table_data.current_warehouse_id;
        this.warehouseTable.updateTable();
        for (int i = 0; i < ((Warehouse)this.warehouseTable).TABLE_DATA.all_lines.size(); ++i) {
            if (((Warehouse)this.warehouseTable).TABLE_DATA.all_lines.elementAt((int)i).wh_id != old_warehouse_selection) continue;
            this.warehouseTable.table.select_line_by_data(((Warehouse)this.warehouseTable).TABLE_DATA.all_lines.elementAt(i));
        }
    }

    public void deinit() {
        this.truckTable.deinit();
        this.warehouseTable.deinit();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(65, 8, this.param_values);
        this.truckTable.onOk();
        this.warehouseTable.onOk();
        SaveLoadCommonManagement.getSaveLoadCommonManager().SetStartNewGameFlag(4);
        TopWindow.quitTopMenu();
    }

    private void UpdateGrayButtons() {
        if (this.ok_button != 0L) {
            if (this.choose_gray_wh || this.choose_gray_veh) {
                menues.SetShowField(this.ok_button, false);
            } else {
                menues.SetShowField(this.ok_button, true);
            }
        }
        if (this.ok_gray != 0L) {
            menues.SetBlindess(this.ok_gray, true);
            menues.SetIgnoreEvents(this.ok_gray, true);
        }
        if (this.restyle_button_gray != 0L) {
            menues.SetBlindess(this.restyle_button_gray, true);
            menues.SetIgnoreEvents(this.restyle_button_gray, true);
        }
        if (this.ok_gray != 0L) {
            if (this.choose_gray_wh || this.choose_gray_veh) {
                menues.SetShowField(this.ok_gray, true);
            } else {
                menues.SetShowField(this.ok_gray, false);
            }
        }
        if (this.restyle_button != 0L) {
            if (this.choose_gray_veh) {
                menues.SetShowField(this.restyle_button, false);
            } else {
                menues.SetShowField(this.restyle_button, true);
            }
        }
        if (this.restyle_button_gray != 0L) {
            if (this.choose_gray_veh) {
                menues.SetShowField(this.restyle_button_gray, true);
            } else {
                menues.SetShowField(this.restyle_button_gray, false);
            }
        }
    }

    public void valueChanged() {
    }

    protected void onShow(boolean value) {
        this.warehouseTable.make_map_sync_group_and_se\u00e5_blindness();
        this.warehouseTable.hide_left_warehouse();
        if (value) {
            this.UpdateGrayButtons();
        }
    }

    protected void onFreeze(boolean value) {
        this.warehouseTable.make_map_sync_group_and_se\u00e5_blindness();
        this.warehouseTable.hide_left_warehouse();
        if (!value) {
            this.UpdateGrayButtons();
        }
    }

    public void readParamValues() {
        JavaEvents.SendEvent(65, 1, this.int_truck_table_data);
        int old_truck_selection = this.int_truck_table_data.current_truck_id;
        this.truckTable.updateTable();
        for (int i = 0; i < ((Trucks)this.truckTable).TABLE_DATA.all_lines.size(); ++i) {
            if (((Trucks)this.truckTable).TABLE_DATA.all_lines.elementAt((int)i).truck_id != old_truck_selection) continue;
            this.truckTable.table.select_line_by_data(((Trucks)this.truckTable).TABLE_DATA.all_lines.elementAt(i));
        }
        JavaEvents.SendEvent(65, 4, this.int_warehouse_table_data);
        int old_warehouse_selection = this.int_warehouse_table_data.current_warehouse_id;
        this.warehouseTable.updateTable();
        for (int i = 0; i < ((Warehouse)this.warehouseTable).TABLE_DATA.all_lines.size(); ++i) {
            if (((Warehouse)this.warehouseTable).TABLE_DATA.all_lines.elementAt((int)i).wh_id != old_warehouse_selection) continue;
            this.warehouseTable.table.select_line_by_data(((Warehouse)this.warehouseTable).TABLE_DATA.all_lines.elementAt(i));
        }
        JavaEvents.SendEvent(65, 7, this.param_values);
        this.param_values.onUpdate();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class Warehouse
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private final String DEFAULT_METHOD = "onDefault";
        private final String ON_MAP_METHOD_EXECUTE = "onMapExectute";
        private final String ON_FULL_INFO = "onFullInfo";
        private static final int WAREHOUSE_NAME = 0;
        private static final int WAREHOUSE_POWER = 1;
        private final String[] SORT = new String[]{"BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE TITLE", "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - ROC TITLE"};
        private final String DEFAULT = "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - DEFAULT";
        private warehouse_table_data TABLE_DATA = new warehouse_table_data();
        private long _menu = 0L;
        QuickRaceNewGame _parent = null;
        PopUpLongWarehouseInfo pop_up = null;
        private int[] old_sync_group = null;
        Table table;
        String warehouse_commodities_string = null;

        Warehouse(long menu, QuickRaceNewGame parent) {
            QuickRaceNewGame.this.out_warehouse_sort_mode = new sort(0, true);
            this._parent = parent;
            this._menu = menu;
            this.table = new Table(this._menu, QuickRaceNewGame.TABLE_WAREHOUSE, QuickRaceNewGame.RANGER_WAREHOUSE);
            this.table.setSelectionMode(1);
            this.table.fillWithLines(QuickRaceNewGame.XML, QuickRaceNewGame.TABLE_WAREHOUSE_LINE, TABLE_WAREHOUSE_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            for (String name : TABLE_WAREHOUSE_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            if (null != this.SORT) {
                int i = 0;
                while (i < this.SORT.length) {
                    long field = menues.FindFieldInMenu(this._menu, this.SORT[i]);
                    MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                    buts.userid = i++;
                    menues.SetScriptOnControl(this._menu, buts, this, "onSort", 4L);
                    menues.UpdateField(buts);
                }
            }
            long field = menues.FindFieldInMenu(this._menu, "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - DEFAULT");
            MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
            menues.SetScriptOnControl(this._menu, buts, this, "onDefault", 4L);
            menues.UpdateField(buts);
            this.pop_up = new PopUpLongWarehouseInfo(menu);
        }

        // @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            WarehouseInfo line = (WarehouseInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: 
                case 3: {
                    obj.text = line.warehouse_name;
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 1: 
                case 4: {
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 2: 
                case 5: {
                    if (this.warehouse_commodities_string == null) {
                        this.warehouse_commodities_string = obj.text;
                    }
                    KeyPair[] pairs = new KeyPair[]{new KeyPair("VALUE", "" + line.warehouse_commodities)};
                    obj.text = MacroKit.Parse(this.warehouse_commodities_string, pairs);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                }
            }
            if (this.old_sync_group != null) {
                menues.RemoveSyncControlActive(this._menu, this.old_sync_group[obj.userid], obj.nativePointer);
                menues.RemoveSyncControlState(this._menu, this.old_sync_group[obj.userid], obj.nativePointer);
            }
            menues.SetSyncControlActive(this._menu, this.get_sync_group(line.wh_id), obj.nativePointer);
            menues.SetSyncControlState(this._menu, this.get_sync_group(line.wh_id), obj.nativePointer);
            if (control == 5) {
                if (this.old_sync_group == null) {
                    this.old_sync_group = new int[this.table.getNumRows()];
                }
                this.old_sync_group[obj.userid] = this.get_sync_group(line.wh_id);
            }
            if (line.isGray) {
                if (control <= 2) {
                    menues.SetShowField(obj.nativePointer, false);
                } else {
                    menues.SetShowField(obj.nativePointer, true);
                }
            } else if (control <= 2) {
                menues.SetShowField(obj.nativePointer, true);
            } else {
                menues.SetShowField(obj.nativePointer, false);
            }
        }

        // @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
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

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 3, this._parent);
            for (int i = 0; i < QuickRaceNewGame.this.in_warehouse_lines.size(); ++i) {
                WarehouseInfo info = QuickRaceNewGame.this.in_warehouse_lines.elementAt(i);
                this.TABLE_DATA.all_lines.add(info);
            }
            this.buildvoidcells();
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        WarehouseInfo data = new WarehouseInfo();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<WarehouseInfo> iter = this.TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private int get_sync_group(int wh_id) {
            for (int i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                if (QuickRaceNewGame.this.map_warehouses.elementAt((int)i).wh_id != wh_id) continue;
                return QuickRaceNewGame.this.map_warehouses.elementAt((int)i).sync_group;
            }
            return 0;
        }

        private void make_map_sync_group_and_se\u00e5_blindness() {
            int i;
            if (QuickRaceNewGame.this.map_warehouses.size() == 0) {
                JavaEvents.SendEvent(65, 3, this._parent);
                for (i = 0; i < QuickRaceNewGame.this.in_warehouse_lines.size(); ++i) {
                    MapWarehouseInfo map_info = new MapWarehouseInfo();
                    WarehouseInfo info = QuickRaceNewGame.this.in_warehouse_lines.elementAt(i);
                    map_info.button_icon_id = menues.FindFieldInMenu(this._menu, "ButtonMap - " + info.warehouse_internal_name);
                    map_info.button_text_id = menues.FindFieldInMenu(this._menu, "ButtonMapText - " + info.warehouse_internal_name);
                    map_info.button_icon = menues.ConvertButton(map_info.button_icon_id);
                    map_info.button_text = menues.ConvertButton(map_info.button_text_id);
                    if (map_info.button_icon_id == 0L || map_info.button_text_id == 0L || map_info.button_icon == null || map_info.button_text == null) continue;
                    menues.SetScriptOnControl(this._menu, map_info.button_icon, this, "onMapExectute", 2L);
                    menues.UpdateField(map_info.button_icon);
                    map_info.button_text.text = info.warehouse_name;
                    menues.UpdateField(map_info.button_text);
                    map_info.wh_id = info.wh_id;
                    map_info.sync_group = 20 + i;
                    menues.SetSyncControlActive(this._menu, map_info.sync_group, map_info.button_icon_id);
                    menues.SetSyncControlState(this._menu, map_info.sync_group, map_info.button_icon_id);
                    menues.SetSyncControlActive(this._menu, map_info.sync_group, map_info.button_text_id);
                    menues.SetSyncControlState(this._menu, map_info.sync_group, map_info.button_text_id);
                    QuickRaceNewGame.this.map_warehouses.add(map_info);
                }
            }
            for (i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                if (QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_icon == null || QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_text == null) continue;
                menues.SetBlindess(QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_text.nativePointer, true);
                menues.SetIgnoreEvents(QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_text.nativePointer, true);
            }
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        private void bind_detailed_info() {
            long[] contrls_but = null;
            contrls_but = this.table.getLineStatistics_controls(TABLE_WAREHOUSE_ELEMENTS[1]);
            for (int i = 0; i < contrls_but.length; ++i) {
                MENUbutton_field obj = menues.ConvertButton(contrls_but[i]);
                menues.SetScriptOnControl(this._menu, obj, this, "onFullInfo", 4L);
            }
        }

        private void hide_left_warehouse() {
            MENUbutton_field icon;
            long text_id = menues.FindFieldInMenu(this._menu, "ButtonMapText - MW");
            long icon_id = menues.FindFieldInMenu(this._menu, "ButtonMap - MW");
            MENUbutton_field text = menues.ConvertButton(text_id);
            if (text != null && text.nativePointer != 0L) {
                menues.SetShowField(text.nativePointer, false);
            }
            if ((icon = menues.ConvertButton(icon_id)) != null && icon.nativePointer != 0L) {
                menues.SetShowField(icon.nativePointer, false);
            }
        }

        public void afterInit() {
            JavaEvents.SendEvent(65, 4, QuickRaceNewGame.this.int_warehouse_table_data);
            int old_warehouse_selection = QuickRaceNewGame.this.int_warehouse_table_data.current_warehouse_id;
            this.table.afterInit();
            this.make_map_sync_group_and_se\u00e5_blindness();
            this.bind_detailed_info();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt((int)i).wh_id != old_warehouse_selection) continue;
                this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        public void deinit() {
            this.table.deinit();
        }

        public void onFullInfo(long _menu, MENUbutton_field button) {
            WarehouseInfo line = (WarehouseInfo)this.table.getItemOnLine((int)button.userid).item;
            this.pop_up.show(button.userid * 40, line.warehouse_long_name);
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    QuickRaceNewGame.this.out_warehouse_sort_mode = new sort(0, QuickRaceNewGame.this.out_warehouse_sort_mode.type == 0 ? !QuickRaceNewGame.this.out_warehouse_sort_mode.up : true);
                    break;
                }
                case 1: {
                    QuickRaceNewGame.this.out_warehouse_sort_mode = new sort(1, QuickRaceNewGame.this.out_warehouse_sort_mode.type == 1 ? !QuickRaceNewGame.this.out_warehouse_sort_mode.up : true);
                }
            }
            int old_warehouse_selection = QuickRaceNewGame.this.int_warehouse_table_data.current_warehouse_id;
            this.updateTable();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt((int)i).wh_id != old_warehouse_selection) continue;
                this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        public void onDefault(long _menu, MENUsimplebutton_field button) {
            this.updateTable();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt((int)i).wh_id != QuickRaceNewGame.this.int_warehouse_table_data.default_warehouse_id) continue;
                this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        // @Override
        public void selectLineEvent(Table table, int line) {
            if (table != null && table.getItemOnLine(line) != null) {
                WarehouseInfo data = (WarehouseInfo)table.getItemOnLine((int)line).item;
                QuickRaceNewGame.this.int_warehouse_table_data.current_warehouse_id = data.wh_id;
                JavaEvents.SendEvent(65, 11, QuickRaceNewGame.this.int_warehouse_table_data);
                this.ExternalSelectWarehouse(data.wh_id);
                QuickRaceNewGame.this.choose_gray_wh = data.isGray;
                QuickRaceNewGame.this.UpdateGrayButtons();
            }
        }

        public void ExternalSelectWarehouse(int wh_id) {
            for (int i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                menues.SetFieldState(QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_icon_id, QuickRaceNewGame.this.map_warehouses.elementAt((int)i).wh_id == wh_id ? 1 : 0);
                menues.SetFieldState(QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_text_id, QuickRaceNewGame.this.map_warehouses.elementAt((int)i).wh_id == wh_id ? 1 : 0);
            }
        }

        public void onMapExectute(long _menu, MENUbutton_field button) {
            int i;
            int wh_id = -1;
            for (i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                if (QuickRaceNewGame.this.map_warehouses.elementAt((int)i).button_icon != button) continue;
                wh_id = QuickRaceNewGame.this.map_warehouses.elementAt((int)i).wh_id;
                break;
            }
            if (wh_id >= 0) {
                for (i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                    if (this.TABLE_DATA.all_lines.elementAt((int)i).wh_id != wh_id) continue;
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        // @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }

        public void onOk() {
            JavaEvents.SendEvent(65, 9, QuickRaceNewGame.this.int_warehouse_table_data);
        }
    }

    static class QUICK_RACE_WAREHOUSE_TABLE_DATA {
        int default_warehouse_id = 0;
        int current_warehouse_id = 0;

        QUICK_RACE_WAREHOUSE_TABLE_DATA() {
        }
    }

    static class warehouse_table_data {
        Vector<WarehouseInfo> all_lines = new Vector();

        warehouse_table_data() {
        }
    }

    static class MapWarehouseInfo {
        MENUbutton_field button_text = null;
        MENUbutton_field button_icon = null;
        int sync_group = 0;
        long button_text_id = 0L;
        long button_icon_id = 0L;
        int wh_id = 0;

        MapWarehouseInfo() {
        }
    }

    static class WarehouseInfo {
        String warehouse_name;
        String warehouse_long_name;
        String warehouse_internal_name;
        int warehouse_commodities;
        int wh_id = 0;
        boolean wheather_show = true;
        boolean isGray = true;

        WarehouseInfo() {
        }
    }

    class PopUpLongWarehouseInfo {
        private PoPUpMenu menu = null;
        private long text = 0L;
        private static final String MENU_GROUP = "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS";
        private static final String TOOLTIP_TEXT = "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS - DETAILS TOOLTIP - TEXT";

        PopUpLongWarehouseInfo(long _menu) {
            this.menu = new PoPUpMenu(_menu, QuickRaceNewGame.XML, MENU_GROUP, MENU_GROUP);
            this.text = this.menu.getField(TOOLTIP_TEXT);
        }

        public void afterInit() {
            this.menu.afterInit();
        }

        void show(int shift, String name) {
            menues.SetFieldText(this.text, name);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.text));
            this.menu.MoveByFromOrigin(0, shift);
            this.menu.show();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class Trucks
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private final String DEFAULT_METHOD = "onDefault";
        private static final int TRUCK_NAME = 0;
        private static final int TRUCK_POWER = 1;
        private static final int TRUCK_NAME_GRAY = 2;
        private static final int TRUCK_POWER_GRAY = 3;
        private final String[] SORT = new String[]{"BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - MODEL TITLE", "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - HORSE POWER TITLE"};
        private final String DEFAULT = "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - DEFAULT";
        private truck_table_data TABLE_DATA = new truck_table_data();
        private long _menu = 0L;
        QuickRaceNewGame _parent = null;
        Table table;
        String truck_power_string = null;

        Trucks(long menu, QuickRaceNewGame parent) {
            QuickRaceNewGame.this.out_truck_sort_mode = new sort(0, true);
            this._parent = parent;
            this._menu = menu;
            this.table = new Table(this._menu, QuickRaceNewGame.TABLE_TRUCKS, QuickRaceNewGame.RANGER_TRUCKS);
            this.table.setSelectionMode(1);
            this.table.fillWithLines(QuickRaceNewGame.XML, QuickRaceNewGame.TABLE_TRUCK_LINE, TABLE_TRUCK_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : TABLE_TRUCK_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            if (null != this.SORT) {
                int i = 0;
                while (i < this.SORT.length) {
                    long field = menues.FindFieldInMenu(this._menu, this.SORT[i]);
                    MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                    buts.userid = i++;
                    menues.SetScriptOnControl(this._menu, buts, this, "onSort", 4L);
                    menues.UpdateField(buts);
                }
            }
            long field = menues.FindFieldInMenu(this._menu, "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - DEFAULT");
            MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
            menues.SetScriptOnControl(this._menu, buts, this, "onDefault", 4L);
            menues.UpdateField(buts);
        }

        // @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            TruckInfo line = (TruckInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            KeyPair[] pairs = new KeyPair[1];
            switch (control) {
                case 0: {
                    obj.text = line.manufacturer_name + " " + line.truck_name;
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 1: {
                    if (this.truck_power_string == null) {
                        this.truck_power_string = obj.text;
                    }
                    pairs[0] = new KeyPair("VALUE", "" + line.horse_power);
                    obj.text = MacroKit.Parse(this.truck_power_string, pairs);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 2: {
                    obj.text = line.manufacturer_name + " " + line.truck_name;
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 3: {
                    if (this.truck_power_string == null) {
                        this.truck_power_string = obj.text;
                    }
                    pairs[0] = new KeyPair("VALUE", "" + line.horse_power);
                    obj.text = MacroKit.Parse(this.truck_power_string, pairs);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                }
            }
            if (line.isGray) {
                if (control == 0 || control == 1) {
                    menues.SetShowField(obj.nativePointer, false);
                } else {
                    menues.SetShowField(obj.nativePointer, true);
                }
            } else if (control == 0 || control == 1) {
                menues.SetShowField(obj.nativePointer, true);
            } else {
                menues.SetShowField(obj.nativePointer, false);
            }
        }

        // @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        }

        // @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
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

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 0, this._parent);
            for (int i = 0; i < QuickRaceNewGame.this.in_truck_lines.size(); ++i) {
                this.TABLE_DATA.all_lines.add(QuickRaceNewGame.this.in_truck_lines.elementAt(i));
            }
            this.buildvoidcells();
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        TruckInfo data = new TruckInfo();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<TruckInfo> iter = this.TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private void make_sync_group() {
            long[] contrls_name = this.table.getLineStatistics_controls(TABLE_TRUCK_ELEMENTS[0]);
            long[] contrls_power = this.table.getLineStatistics_controls(TABLE_TRUCK_ELEMENTS[1]);
            long[] contrls_name_gray = this.table.getLineStatistics_controls(TABLE_TRUCK_ELEMENTS[2]);
            long[] contrls_power_gray = this.table.getLineStatistics_controls(TABLE_TRUCK_ELEMENTS[3]);
            if (contrls_name.length != contrls_power.length) {
                Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length + " contrls_time.length is " + contrls_power.length);
                return;
            }
            for (int i = 0; i < contrls_name.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_power[i]);
                menues.SetSyncControlState(this._menu, i, contrls_power[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_name_gray[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name_gray[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_power_gray[i]);
                menues.SetSyncControlState(this._menu, i, contrls_power_gray[i]);
            }
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        public void afterInit() {
            JavaEvents.SendEvent(65, 1, QuickRaceNewGame.this.int_truck_table_data);
            int old_truck_selection = QuickRaceNewGame.this.int_truck_table_data.current_truck_id;
            this.table.afterInit();
            this.make_sync_group();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt((int)i).truck_id != old_truck_selection) continue;
                this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        public void deinit() {
            this.table.deinit();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    QuickRaceNewGame.this.out_truck_sort_mode = new sort(0, QuickRaceNewGame.this.out_truck_sort_mode.type == 0 ? !QuickRaceNewGame.this.out_truck_sort_mode.up : true);
                    break;
                }
                case 1: {
                    QuickRaceNewGame.this.out_truck_sort_mode = new sort(1, QuickRaceNewGame.this.out_truck_sort_mode.type == 1 ? !QuickRaceNewGame.this.out_truck_sort_mode.up : true);
                }
            }
            int old_truck_selection = QuickRaceNewGame.this.int_truck_table_data.current_truck_id;
            this.updateTable();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt((int)i).truck_id != old_truck_selection) continue;
                this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        public void onDefault(long _menu, MENUsimplebutton_field button) {
            this.updateTable();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt((int)i).truck_id != QuickRaceNewGame.this.int_truck_table_data.default_truck_id) continue;
                this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        // @Override
        public void selectLineEvent(Table table, int line) {
            TruckInfo data = (TruckInfo)table.getItemOnLine((int)line).item;
            QuickRaceNewGame.this.int_truck_table_data.current_truck_id = data.truck_id;
            QuickRaceNewGame.this.choose_gray_veh = data.isGray;
            QuickRaceNewGame.this.UpdateGrayButtons();
            JavaEvents.SendEvent(65, 2, QuickRaceNewGame.this.int_truck_table_data);
        }

        // @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }

        public void onOk() {
            JavaEvents.SendEvent(65, 10, QuickRaceNewGame.this.int_truck_table_data);
        }
    }

    static class QUICK_RACE_TRUCK_TABLE_DATA {
        int default_truck_id = 0;
        int current_truck_id = 0;

        QUICK_RACE_TRUCK_TABLE_DATA() {
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

    static class truck_table_data {
        Vector<TruckInfo> all_lines = new Vector();

        truck_table_data() {
        }
    }

    static class TruckInfo {
        String truck_name;
        String manufacturer_name;
        int horse_power;
        int truck_id;
        boolean wheather_show = true;
        boolean isGray = true;

        TruckInfo() {
        }
    }
}

