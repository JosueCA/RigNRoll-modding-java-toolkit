/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import menu.Helper;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RNRMap;
import menu.SelectCb;
import menu.menues;
import menuscript.Converts;
import menuscript.IPoPUpMenuListener;
import menuscript.IUpdateListener;
import menuscript.PoPUpMenu;
import menuscript.RNRMapWrapper;
import menuscript.TooltipButton;
import menuscript.org.IOrgTab;
import menuscript.org.OrganiserMenu;
import menuscript.table.Table;
import menuscript.tablewrapper.TableLine;
import menuscript.tablewrapper.TableWrapped;
import rnrconfig.WorldCoordinates;
import rnrcore.CoreTime;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.loc;
import rnrcore.vectorJ;
import rnrorg.CustomerWarehouseAssociation;
import rnrorg.EventGetPointLocInfo;
import rnrorg.IStoreorgelement;
import rnrorg.JournalFinishWarehouse;
import rnrorg.MissionEventsMaker;
import rnrorg.organaiser;
import rnrscr.CollectionOfData;
import rnrscr.DataWarehousesOnMap;
import rnrscr.EventsHelper;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class OrganiserPane
implements IOrgTab {
    private boolean DEBUG = OrganiserMenu.DEBUG;
    OrganiserMenu parent = null;
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String TABLE = "ORGANIZER - MY MISSIONS - Tablegroup 6 38";
    private static final String RANGER = "Tableranger - ORGANIZER - MY MISSIONS";
    private static final String LINE = "Tablegroup - ELEMENTS - ORGANIZER - MY MISSIONS";
    private static final String[] LINE_ELEMENTS = new String[]{"MISSION - Important YES", "MISSION - Important NO", "MISSION - Read YES", "MISSION - Read NO", "MISSION - Status NONE", "MISSION - Status PENDING", "MISSION - Status URGENT", "MISSION - Status EXECUTED", "MISSION - Status FAILED", "MISSION - Type DELIVERY", "MISSION - Type TENDER", "MISSION - Type CONTEST", "MISSION - Type DELIVERY to CLIENT", "MISSION - Type DELIVERY to ADDRESS", "MISSION - Type CLIENT to ADDRESS", "MISSION - Type BOX to CLIENT", "MISSION - Type VISIT", "MISSION - Type RACE01", "MISSION - Type RACE02", "MISSION - Type RACE03", "MISSION - Type RACE04", "MISSION - Type RACE05", "MISSION - From", "MISSION - To", "MISSION - Time Left", "MISSION - Type RACE01 wthTrailer", "MISSION - Type RACE02 wthTrailer", "MISSION - Type RACE03 wthTrailer", "MISSION - Type RACE04 wthTrailer", "MISSION - Type RACE05 wthTrailer", "MISSION - Type TO RACE01", "MISSION - Type TO RACE02", "MISSION - Type TO RACE03", "MISSION - Type TO RACE04", "MISSION - Type TO RACE05", "MISSION - Important YES - CURRENT", "MISSION - Important NO - CURRENT", "MISSION - Read YES - CURRENT", "MISSION - Read NO - CURRENT", "MISSION - Status NONE - CURRENT", "MISSION - Status PENDING - CURRENT", "MISSION - Status URGENT - CURRENT", "MISSION - Status EXECUTED - CURRENT", "MISSION - Status FAILED - CURRENT", "MISSION - Type DELIVERY - CURRENT", "MISSION - Type TENDER - CURRENT", "MISSION - Type CONTEST - CURRENT", "MISSION - Type DELIVERY to CLIENT - CURRENT", "MISSION - Type DELIVERY to ADDRESS - CURRENT", "MISSION - Type CLIENT to ADDRESS - CURRENT", "MISSION - Type BOX to CLIENT - CURRENT", "MISSION - Type VISIT - CURRENT", "MISSION - Type RACE01 - CURRENT", "MISSION - Type RACE02 - CURRENT", "MISSION - Type RACE03 - CURRENT", "MISSION - Type RACE04 - CURRENT", "MISSION - Type RACE05 - CURRENT", "MISSION - From - CURRENT", "MISSION - To - CURRENT", "MISSION - Time Left - CURRENT", "MISSION - Type RACE01 wthTrailer - CURRENT", "MISSION - Type RACE02 wthTrailer - CURRENT", "MISSION - Type RACE03 wthTrailer - CURRENT", "MISSION - Type RACE04 wthTrailer - CURRENT", "MISSION - Type RACE05 wthTrailer - CURRENT", "MISSION - Type TO RACE01 - CURRENT", "MISSION - Type TO RACE02 - CURRENT", "MISSION - Type TO RACE03 - CURRENT", "MISSION - Type TO RACE04 - CURRENT", "MISSION - Type TO RACE05 - CURRENT"};
    private static final int ELEMENT_IMPORTANT_YES = 0;
    private static final int ELEMENT_IMPORTANT_NO = 1;
    private static final int ELEMENT_READ_YES = 2;
    private static final int ELEMENT_READ_NO = 3;
    private static final int ELEMENT_STATUS_NONE = 4;
    private static final int ELEMENT_STATUS_PENDING = 5;
    private static final int ELEMENT_STATUS_URGENT = 6;
    private static final int ELEMENT_STATUS_EXECUTED = 7;
    private static final int ELEMENT_STATUS_FAILED = 8;
    private static final int ELEMENT_TYPE_DELIEVERY = 9;
    private static final int ELEMENT_TYPE_TENDER = 10;
    private static final int ELEMENT_TYPE_CONTEST = 11;
    private static final int ELEMENT_TYPE_DELIVERYTOCLIENT = 12;
    private static final int ELEMENT_TYPE_DELIVERYTOADRESS = 13;
    private static final int ELEMENT_TYPE_CLIENTTOADRESS = 14;
    private static final int ELEMENT_TYPE_BOXTOCLIENT = 15;
    private static final int ELEMENT_TYPE_VISIT = 16;
    private static final int ELEMENT_TYPE_RACE01 = 17;
    private static final int ELEMENT_TYPE_RACE02 = 18;
    private static final int ELEMENT_TYPE_RACE03 = 19;
    private static final int ELEMENT_TYPE_RACE04 = 20;
    private static final int ELEMENT_TYPE_RACE05 = 21;
    private static final int ELEMENT_MISSION_FROM = 22;
    private static final int ELEMENT_MISSION_TO = 23;
    private static final int ELEMENT_TIME_LEFT = 24;
    private static final int ELEMENT_TYPE_RACE01_SEMI = 25;
    private static final int ELEMENT_TYPE_RACE02_SEMI = 26;
    private static final int ELEMENT_TYPE_RACE03_SEMI = 27;
    private static final int ELEMENT_TYPE_RACE04_SEMI = 28;
    private static final int ELEMENT_TYPE_RACE05_SEMI = 29;
    private static final int ELEMENT_TYPE_RACE01_ANNON = 30;
    private static final int ELEMENT_TYPE_RACE02_ANNON = 31;
    private static final int ELEMENT_TYPE_RACE03_ANNON = 32;
    private static final int ELEMENT_TYPE_RACE04_ANNON = 33;
    private static final int ELEMENT_TYPE_RACE05_ANNON = 34;
    private static final int ELEMENTCURRENT_START = 35;
    private static final int ELEMENTCURRENT_IMPORTANT_YES = 35;
    private static final int ELEMENTCURRENT_IMPORTANT_NO = 36;
    private static final int ELEMENTCURRENT_READ_YES = 37;
    private static final int ELEMENTCURRENT_READ_NO = 38;
    private static final int ELEMENTCURRENT_STATUS_NONE = 39;
    private static final int ELEMENTCURRENT_STATUS_PENDING = 40;
    private static final int ELEMENTCURRENT_STATUS_URGENT = 41;
    private static final int ELEMENTCURRENT_STATUS_EXECUTED = 42;
    private static final int ELEMENTCURRENT_STATUS_FAILED = 43;
    private static final int ELEMENTCURRENT_TYPE_DELIEVERY = 44;
    private static final int ELEMENTCURRENT_TYPE_TENDER = 45;
    private static final int ELEMENTCURRENT_TYPE_CONTEST = 46;
    private static final int ELEMENTCURRENT_TYPE_DELIVERYTOCLIENT = 47;
    private static final int ELEMENTCURRENT_TYPE_DELIVERYTOADRESS = 48;
    private static final int ELEMENTCURRENT_TYPE_CLIENTTOADRESS = 49;
    private static final int ELEMENTCURRENT_TYPE_BOXTOCLIENT = 50;
    private static final int ELEMENTCURRENT_TYPE_VISIT = 51;
    private static final int ELEMENTCURRENT_TYPE_RACE01 = 52;
    private static final int ELEMENTCURRENT_TYPE_RACE02 = 53;
    private static final int ELEMENTCURRENT_TYPE_RACE03 = 54;
    private static final int ELEMENTCURRENT_TYPE_RACE04 = 55;
    private static final int ELEMENTCURRENT_TYPE_RACE05 = 56;
    private static final int ELEMENTCURRENT_MISSION_FROM = 57;
    private static final int ELEMENTCURRENT_MISSION_TO = 58;
    private static final int ELEMENTCURRENT_TIME_LEFT = 59;
    private static final int ELEMENTCURRENT_TYPE_RACE01_SEMI = 60;
    private static final int ELEMENTCURRENT_TYPE_RACE02_SEMI = 61;
    private static final int ELEMENTCURRENT_TYPE_RACE03_SEMI = 62;
    private static final int ELEMENTCURRENT_TYPE_RACE04_SEMI = 63;
    private static final int ELEMENTCURRENT_TYPE_RACE05_SEMI = 64;
    private static final int ELEMENTCURRENT_TYPE_RACE01_ANNON = 65;
    private static final int ELEMENTCURRENT_TYPE_RACE02_ANNON = 66;
    private static final int ELEMENTCURRENT_TYPE_RACE03_ANNON = 67;
    private static final int ELEMENTCURRENT_TYPE_RACE04_ANNON = 68;
    private static final int ELEMENTCURRENT_TYPE_RACE05_ANNON = 69;
    private static final String[] SORT = new String[]{"BUTTON - IMPORTANT", "BUTTON - READ", "BUTTON - STATUS", "BUTTON - TYPE", "BUTTON - FROM", "BUTTON - TO", "BUTTON - TIME LEFT", "BUTTON - Mission", "BUTTON - Requested by", "BUTTON - Date of request", "BUTTON - From", "BUTTON - To load until", "BUTTON - To", "BUTTON - To complete until", "BUTTON - Reward", "BUTTON - Forfeit", "BUTTON - Time left", "BUTTON - Load fragility"};
    private static final int SORT_IMPORTANCE = 0;
    private static final int SORT_READ = 1;
    private static final int SORT_STATUS = 2;
    private static final int SORT_TYPE = 3;
    private static final int SORT_FROM = 4;
    private static final int SORT_TO = 5;
    private static final int SORT_TIMELEFT = 6;
    private static final int SORT_NAME = 7;
    private static final int SORT_CUSTOMER = 8;
    private static final int SORT_DATEREQUEST = 9;
    private static final int SORT_SOURCE = 10;
    private static final int SORT_UNLOADTO = 11;
    private static final int SORT_DESTINATION = 12;
    private static final int SORT_COMPLETEUNTIL = 13;
    private static final int SORT_REWARD2 = 14;
    private static final int SORT_FORFEIT = 15;
    private static final int SORT_TIMELEFT2 = 16;
    private static final int SORT_FRAGILITY = 17;
    private static final String[] MISSION_DETALES = new String[]{"MISSION DETAILS - Requested by", "MISSION DETAILS - Date of request", "MISSION DETAILS - From", "MISSION DETAILS - To load until", "MISSION DETAILS - To", "MISSION DETAILS - To complete until", "MISSION DETAILS - Reward MONEY", "MISSION DETAILS - Reward RATING", "MISSION DETAILS - Reward INFO", "MISSION DETAILS - Forfeit", "MISSION DETAILS - Time Left", "MISSION DETAILS - Load fragility", "MISSION DETAILS - Reward RATING - League01", "MISSION DETAILS - Reward RATING - League02", "MISSION DETAILS - Reward RATING - League03", "MISSION DETAILS - Reward RATING - League04", "MISSION DETAILS - Reward RATING - League05"};
    private static final int DETALE_CUSTOMER = 0;
    private static final int DETALE_DATEOFREQUEST = 1;
    private static final int DETALE_SOURCE = 2;
    private static final int DETALE_LOADUNTIL = 3;
    private static final int DETALE_DESTINATION = 4;
    private static final int DETALE_COMPLETEUNTIL = 5;
    private static final int DETALE_REWARDMONEY = 6;
    private static final int DETALE_REWARDRATING = 7;
    private static final int DETALE_REWARDINFO = 8;
    private static final int DETALE_FORFEIT = 9;
    private static final int DETALE_TIMELEFT = 10;
    private static final int DETALE_FRAGILITY = 11;
    private static final int DETALE_REWARDRATING0 = 12;
    private static final int DETALE_REWARDRATING1 = 13;
    private static final int DETALE_REWARDRATING2 = 14;
    private static final int DETALE_REWARDRATING3 = 15;
    private static final int DETALE_REWARDRATING4 = 16;
    private static final String[] MISSION_DETAILES_DESCRIPTION = new String[]{"MISSION DETAILS - Type DELIVERY", "MISSION DETAILS - Type TENDER", "MISSION DETAILS - Type CONTEST", "MISSION DETAILS - Type DELIVERY to CLIENT", "MISSION DETAILS - Type DELIVERY to ADDRESS", "MISSION DETAILS - Type CLIENT to ADDRESS", "MISSION DETAILS - Type BOX to CLIENT", "MISSION DETAILS - Type VISIT", "MISSION DETAILS - Type RACE01", "MISSION DETAILS - Type RACE02", "MISSION DETAILS - Type RACE03", "MISSION DETAILS - Type RACE04", "MISSION DETAILS - Type RACE05", "MISSION DETAILS - Type RACE01 wthTrailer", "MISSION DETAILS - Type RACE02 wthTrailer", "MISSION DETAILS - Type RACE03 wthTrailer", "MISSION DETAILS - Type RACE04 wthTrailer", "MISSION DETAILS - Type RACE05 wthTrailer", "MISSION DETAILS - Type TO RACE01", "MISSION DETAILS - Type TO RACE02", "MISSION DETAILS - Type TO RACE03", "MISSION DETAILS - Type TO RACE04", "MISSION DETAILS - Type TO RACE05"};
    private static final int DESCRIPTION_DELIVERY = 0;
    private static final int DESCRIPTION_TENDER = 1;
    private static final int DESCRIPTION_CONTEST = 2;
    private static final int DESCRIPTION_DELIVERYTOCLIENT = 3;
    private static final int DESCRIPTION_DELIVERYTOADDRESS = 4;
    private static final int DESCRIPTION_CLIENTTOADDRESS = 5;
    private static final int DESCRIPTION_BOXTOCLIENT = 6;
    private static final int DESCRIPTION_VISIT = 7;
    private static final int DESCRIPTION_RACE01 = 8;
    private static final int DESCRIPTION_RACE02 = 9;
    private static final int DESCRIPTION_RACE03 = 10;
    private static final int DESCRIPTION_RACE04 = 11;
    private static final int DESCRIPTION_RACE05 = 12;
    private static final int DESCRIPTION_RACE01_SEMI = 13;
    private static final int DESCRIPTION_RACE02_SEMI = 14;
    private static final int DESCRIPTION_RACE03_SEMI = 15;
    private static final int DESCRIPTION_RACE04_SEMI = 16;
    private static final int DESCRIPTION_RACE05_SEMI = 17;
    private static final int DESCRIPTION_RACE01_ANNON = 18;
    private static final int DESCRIPTION_RACE02_ANNON = 19;
    private static final int DESCRIPTION_RACE03_ANNON = 20;
    private static final int DESCRIPTION_RACE04_ANNON = 21;
    private static final int DESCRIPTION_RACE05_ANNON = 22;
    private static final String FRAGILITY_SLIDER = "MISSION DETAILS - Load fragility - Progress Ind";
    private static final int TIME_LEFT_STATUS_PENDING = 0;
    private static final int TIME_LEFT_STATUS_URGENT = 1;
    private static final int TIME_LEFT_STATUS_EXECUTED = 2;
    private static final int TIME_LEFT_STATUS_FAILED = 3;
    private static final String TOOLTIP_SOURCE_BUTTON = "MISSION DETAILS - From - DETAILS BUTTON";
    private static final String TOOLTIP_DESTINATION_BUTTON = "MISSION DETAILS - To - DETAILS BUTTON";
    private static final String TOOLTIP_SOURCE_GROUP = "TOOLTIP - ORGANIZER - MISSION DETAILS - From";
    private static final String TOOLTIP_DESTINATION_GROUP = "TOOLTIP - ORGANIZER - MISSION DETAILS - To";
    private static final String[] TOOLTIP_SOURCE_TEXT = new String[]{"ORGANIZER - MISSION DETAILS - From - TEXT"};
    private static final String[] TOOLTIP_DESTINATION_TEXT = new String[]{"ORGANIZER - MISSION DETAILS - To - TEXT"};
    private static final String[] BUTTONS = new String[]{"ORGANIZER - MY MISSIONS - Button - SHOW ALL or ACTUAL", "ORGANIZER - MY MISSIONS - Button - GO TO CURRENT", "MARK AS CURRENT", "CANCEL MISSION"};
    private static final String[] BUTTONS_GRAY = new String[]{"MARK AS CURRENT - GRAY", "MARK AS CURRENT", "CANCEL MISSION - GRAY", "CANCEL MISSION"};
    private long[] BUTTONS_2_GRAY = new long[4];
    private static final int BUTTON_MARKCURRENT_GRAY = 0;
    private static final int BUTTON_DECLINE_GRAY = 2;
    private static final String[] METHODS = new String[]{"onActive", "onCurrent", "onMarkCurrent", "onCancelMission"};
    private PoPUpMenu warning_cancel_task = null;
    private String to_cancel_task_TEXT = "";
    private String to_cancel_task_Forfeit_TEXT = "";
    private static final int BUTTON_ONLYACTIVE = 0;
    private static final int BUTTON_GOTOCURRENT = 1;
    private static final int BUTTON_MARKCURRENT = 2;
    private static final int BUTTON_DECLINE = 3;
    private static final int BUTTON_ONLYACTIVE_STATETRUE = 0;
    private static final int BUTTON_ONLYACTIVE_STATEFALSE = 1;
    private static final String MAP_NAME = "ORGANIZER - MAP - zooming picture";
    private static final String MAP_ZOOM = "ORGANIZER - MAP";
    private static final String MAP_SHIFT = "ORGANIZER - MAP";
    private static final String[] MAP_INVISIBLES = new String[]{"CommOrganizerMapBorder_01", "CommOrganizerMapBorder_02"};
    public static final int PLAYER = 27;
    public static final int CURRENT_ORDER_ARROW = 28;
    public static final int ORDER_ARROW = 30;
    public static final int CURRENT_DESTINALTION = 32;
    public static final int DESTINALTION = 33;
    public static final int ORDER_TRAILER = 34;
    public static final int ORDER_PASS = 35;
    public static final int ORDER_PACK = 36;
    private static final String[] MACRO = new String[]{"VALUE", "MONEY", "RATING", "SIGN"};
    private static final int MACRO_VALUE = 0;
    private static final int MACRO_MONEY = 1;
    private static final int MACRO_RATING = 2;
    private static final String VOID_DETAIL_DATA = loc.getMENUString("NO_DATA");
    private static final String METHOD_ACTIVE_MAP = "activeMapControl";
    private static final String METHOD_PRESSED_MAP = "pressedMapControl";
    private static final String HASREWARD_YES = loc.getMENUString("INFO_REWARD_YES");
    private static final double TIME_READ = 2.0;
    private static final int TASK_INCREMENT_PRIORITY = 100;
    private static final int TASK_HIGTHEST_PRIORITY = 100000;
    private long _menu;
    private OrgTable table;
    private SortMode sort = new SortMode(true, 0);
    private long[] detailes = null;
    private long[] detailes_description = null;
    private String[] detailesStrings = null;
    private String detailesStringsTimeLeft = null;
    private String detailesCurrentStringsTimeLeft = null;
    private long fragility_slider = 0L;
    private int fragility_slider_sx0 = 0;
    private boolean is_empty_table = true;
    private TooltipButton tooltipSource = null;
    private TooltipButton tooltipDestination = null;
    private Element current = null;
    private RNRMapWrapper mapa;
    private boolean deinited = false;
    private boolean menu_exited = false;
    private WorldCoordinates coords;
    private boolean map_inited = false;
    private ArrayList<MapElement> mapElems = null;
    private int max_priority = 0;
    public CustomerWarehouseAssociation customer = new CustomerWarehouseAssociation();
    private ArrayList<IUpdateListener> listeners = new ArrayList();
    private static final String[] POPUP_BUTTONS = new String[]{"NO", "YES"};
    private static final String[] POPUP_METHODS = new String[]{"OnCancel", "OnOk"};

    public void addUpdateListener(IUpdateListener lst) {
        this.listeners.add(lst);
    }

    public OrganiserPane(long _menu, OrganiserMenu parent) {
        int i;
        this.parent = parent;
        this._menu = _menu;
        this.coords = WorldCoordinates.getCoordinates();
        this.mapa = new RNRMapWrapper(_menu, MAP_NAME, "ORGANIZER - MAP", "ORGANIZER - MAP", MAP_INVISIBLES, new SelectMapControl());
        this.detailes = new long[MISSION_DETALES.length];
        this.detailesStrings = new String[MISSION_DETALES.length];
        for (i = 0; i < this.detailes.length; ++i) {
            this.detailes[i] = menues.FindFieldInMenu(_menu, MISSION_DETALES[i]);
            this.detailesStrings[i] = menues.GetFieldText(this.detailes[i]);
        }
        this.detailes_description = new long[MISSION_DETAILES_DESCRIPTION.length];
        for (i = 0; i < this.detailes_description.length; ++i) {
            this.detailes_description[i] = menues.FindFieldInMenu(_menu, MISSION_DETAILES_DESCRIPTION[i]);
        }
        this.fragility_slider = menues.FindFieldInMenu(_menu, FRAGILITY_SLIDER);
        MENUText_field field = menues.ConvertTextFields(this.fragility_slider);
        this.fragility_slider_sx0 = field.lenx;
        this.table = new OrgTable(this._menu);
        this.tooltipSource = new TooltipButton(_menu, TOOLTIP_SOURCE_BUTTON, XML, TOOLTIP_SOURCE_GROUP, TOOLTIP_SOURCE_TEXT);
        this.tooltipDestination = new TooltipButton(_menu, TOOLTIP_DESTINATION_BUTTON, XML, TOOLTIP_DESTINATION_GROUP, TOOLTIP_DESTINATION_TEXT);
        long button = menues.FindFieldInMenu(_menu, BUTTONS[0]);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METHODS[0], 2L);
        button = menues.FindFieldInMenu(_menu, BUTTONS[1]);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METHODS[1], 4L);
        button = menues.FindFieldInMenu(_menu, BUTTONS[2]);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METHODS[2], 4L);
        button = menues.FindFieldInMenu(_menu, BUTTONS[3]);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METHODS[3], 4L);
        this.BUTTONS_2_GRAY[0] = menues.FindFieldInMenu(_menu, BUTTONS_GRAY[0]);
        this.BUTTONS_2_GRAY[1] = menues.FindFieldInMenu(_menu, BUTTONS_GRAY[1]);
        this.BUTTONS_2_GRAY[2] = menues.FindFieldInMenu(_menu, BUTTONS_GRAY[2]);
        this.BUTTONS_2_GRAY[3] = menues.FindFieldInMenu(_menu, BUTTONS_GRAY[3]);
        this.warning_cancel_task = new PoPUpMenu(_menu, XML, "MESSAGE - ORGANIZER - To cancel task", "MESSAGE - ORGANIZER - To cancel task", POPUP_BUTTONS, POPUP_METHODS);
        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - ORGANIZER");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    public void makeButtonGray() {
        Element selected = this.getSelected();
        if (selected == null) {
            menues.SetShowField(this.BUTTONS_2_GRAY[0], true);
            menues.SetShowField(this.BUTTONS_2_GRAY[1], false);
            menues.SetShowField(this.BUTTONS_2_GRAY[2], true);
            menues.SetShowField(this.BUTTONS_2_GRAY[3], false);
        } else if (selected.Status == IStoreorgelement.Status.executedMission || selected.Status == IStoreorgelement.Status.failedMission) {
            menues.SetShowField(this.BUTTONS_2_GRAY[2], true);
            menues.SetShowField(this.BUTTONS_2_GRAY[3], false);
            menues.SetShowField(this.BUTTONS_2_GRAY[0], true);
            menues.SetShowField(this.BUTTONS_2_GRAY[1], false);
        } else {
            menues.SetShowField(this.BUTTONS_2_GRAY[2], false);
            menues.SetShowField(this.BUTTONS_2_GRAY[3], true);
            menues.SetShowField(this.BUTTONS_2_GRAY[0], selected == this.current);
            menues.SetShowField(this.BUTTONS_2_GRAY[1], selected != this.current);
        }
    }

    @Override
    public void afterInit() {
        this.table.afterInit();
        this.tooltipSource.afterInit();
        this.tooltipDestination.afterInit();
        this.mapa.afterInit();
        this.mapa.workWith(28);
        this.mapa.workWith(30);
        this.mapa.addListenerOnActive(this, METHOD_ACTIVE_MAP);
        this.mapa.addListenerOnPressed(this, METHOD_PRESSED_MAP);
        this.map_inited = true;
        long button = menues.FindFieldInMenu(this._menu, BUTTONS[0]);
        menues.SetFieldState(button, SortMode.only_active ? 0 : 1);
        this.warning_cancel_task.addListener(new MenuCancelTaskListener());
        this.warning_cancel_task.afterInit();
        this.to_cancel_task_TEXT = menues.GetFieldText(this.warning_cancel_task.getField("To cancel task - TEXT"));
        this.to_cancel_task_Forfeit_TEXT = menues.GetFieldText(this.warning_cancel_task.getField("MESSAGE - ORGANIZER - To cancel task - Forfeit VALUE"));
        menues.SetBlindess(this.BUTTONS_2_GRAY[2], true);
        menues.SetIgnoreEvents(this.BUTTONS_2_GRAY[2], true);
        menues.SetBlindess(this.BUTTONS_2_GRAY[0], true);
        menues.SetIgnoreEvents(this.BUTTONS_2_GRAY[0], true);
    }

    private void drawCancelTaskWarning(String name, int money, double rating) {
        long field = this.warning_cancel_task.getField("To cancel task - TEXT");
        KeyPair[] pairs = new KeyPair[]{new KeyPair("TASK_NAME", "" + name)};
        String res_text = MacroKit.Parse(this.to_cancel_task_TEXT, pairs);
        menues.SetFieldText(field, res_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(field));
        field = this.warning_cancel_task.getField("MESSAGE - ORGANIZER - To cancel task - Forfeit VALUE");
        pairs = new KeyPair[]{new KeyPair("FORFEIT", Converts.ConvertNumeric(money))};
        res_text = MacroKit.Parse(this.to_cancel_task_Forfeit_TEXT, pairs);
        menues.SetFieldText(field, res_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(field));
        this.warning_cancel_task.show();
    }

    @Override
    public void exitMenu() {
        this.deinited = true;
        this.menu_exited = true;
        this.table.deinit();
    }

    private void updateVoidTable() {
        int i;
        for (i = 0; i < this.detailes.length; ++i) {
            menues.SetFieldText(this.detailes[i], VOID_DETAIL_DATA);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.detailes[i]));
        }
        menues.SetIgnoreEvents(this.detailes[10], true);
        for (i = 0; i < this.detailes_description.length; ++i) {
            menues.SetShowField(this.detailes_description[i], false);
        }
        MENUText_field field = menues.ConvertTextFields(this.fragility_slider);
        field.lenx = this.fragility_slider_sx0;
        menues.UpdateField(field);
    }

    private void updateAllDetailes() {
        if (this.is_empty_table) {
            this.updateVoidTable();
            this.updateTooltipsVoid();
        } else {
            this.updateDetailes();
            this.updateDetailesDescription();
            this.updateSlider();
            this.updateTooltips();
            this.updateMapSelection();
        }
    }

    String GetDetailsText(int Type2, Element info, String macro) {
        switch (info.Type) {
            case notype: {
                if (Type2 == 0) {
                    return info.customer == null ? "-" : info.customer;
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case baseDelivery: {
                if (Type2 == 0) {
                    return this.customer.getShortName();
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case trailerObjectDelivery: {
                if (Type2 == 0) {
                    return this.customer.getShortName();
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case competition: {
                if (Type2 == 0) {
                    return this.customer.getShortName();
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return "-";
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case tender: {
                if (Type2 == 0) {
                    return this.customer.getShortName();
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return "-";
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case trailerClientDelivery: {
                if (Type2 == 0) {
                    return info.customer == null ? "-" : info.customer;
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case pakageDelivery: {
                if (Type2 == 0) {
                    return info.customer == null ? "-" : info.customer;
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case passangerDelivery: {
                if (Type2 == 0) {
                    return info.customer == null ? "-" : info.customer;
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return Converts.ConvertDateAbsolute(macro, info.load_date.gMonth(), info.load_date.gDate(), info.load_date.gYear(), info.load_date.gHour(), info.load_date.gMinute());
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case visit: {
                if (Type2 == 0) {
                    return info.customer == null ? "-" : info.customer;
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return "-";
                }
                if (Type2 == 3) {
                    return "-";
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return "-";
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case bigrace0_announce: 
            case bigrace1_announce: 
            case bigrace2_announce: 
            case bigrace3_announce: 
            case bigrace4_announce: {
                if (Type2 == 0) {
                    return "-";
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return "-";
                }
                if (Type2 == 3) {
                    return "-";
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return "";
                }
                if (Type2 == 6) {
                    return "";
                }
                if (Type2 == 8) {
                    return "";
                }
                if (Type2 == 9) {
                    return "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return "-";
                }
                if (Type2 == 12) {
                    return "";
                }
                if (Type2 == 13) {
                    return "";
                }
                if (Type2 == 14) {
                    return "";
                }
                if (Type2 == 15) {
                    return "";
                }
                if (Type2 != 16) break;
                return "";
            }
            case bigrace0_semi: 
            case bigrace1_semi: 
            case bigrace2_semi: 
            case bigrace3_semi: 
            case bigrace4_semi: {
                if (Type2 == 0) {
                    return this.customer.getShortName();
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return "-";
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return info.data == null || info.data.getType() == IStoreorgelement.Type.passangerDelivery ? "-" : this.buildSimpleString(macro, info.fragility, 0);
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
            case bigrace0: 
            case bigrace1: 
            case bigrace2: 
            case bigrace3: 
            case bigrace4: {
                if (Type2 == 0) {
                    return this.customer.getShortName();
                }
                if (Type2 == 1) {
                    return Converts.ConvertDateAbsolute(macro, info.request_date.gMonth(), info.request_date.gDate(), info.request_date.gYear(), info.request_date.gHour(), info.request_date.gMinute());
                }
                if (Type2 == 2) {
                    return info.getSourceShort();
                }
                if (Type2 == 3) {
                    return "-";
                }
                if (Type2 == 4) {
                    return info.getDestinationShort();
                }
                if (Type2 == 5) {
                    return Converts.ConvertDateAbsolute(macro, info.complete_date.gMonth(), info.complete_date.gDate(), info.complete_date.gYear(), info.complete_date.gHour(), info.complete_date.gMinute());
                }
                if (Type2 == 7) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 6) {
                    return info.has_money_reward ? this.buildSignedString(macro, info.money_reward, 1, !info.is_active) : "";
                }
                if (Type2 == 8) {
                    return info.has_info_reward ? this.buildSimpleString(macro, HASREWARD_YES, 0) : "";
                }
                if (Type2 == 9) {
                    return info.has_forefit ? this.buildSimpleString(macro, info.forfeit, 1) : "-";
                }
                if (Type2 == 10) {
                    if (info.Status == IStoreorgelement.Status.executedMission || info.Status == IStoreorgelement.Status.failedMission) {
                        return Converts.ConverTime3Plus2(macro, 0, 0);
                    }
                    return Converts.ConverTime3Plus2(macro, info.timeleft_minutes, info.timeleft_seconds);
                }
                if (Type2 == 11) {
                    return "-";
                }
                if (Type2 == 12) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 13) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 14) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 == 15) {
                    return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
                }
                if (Type2 != 16) break;
                return info.has_rating_reward ? this.buildSignedString(macro, Converts.ConvertRating(eng.visualRating(info.rating_reward)), 2) : "";
            }
        }
        return "-";
    }

    private void updateDetailes() {
        Element choosen = this.getSelected();
        for (int i = 0; i < this.detailes.length; ++i) {
            if (this.detailes[i] == 0L) continue;
            menues.SetFieldText(this.detailes[i], this.GetDetailsText(i, choosen, this.detailesStrings[i]));
            if (i == 6 || i == 7 || i == 8 || i == 10 || i >= 12 && i <= 16) {
                menues.SetIgnoreEvents(this.detailes[i], true);
            }
            if (i == 6 || i == 7 || i == 8 || i >= 12 && i <= 16) {
                if (choosen.Type == IStoreorgelement.Type.bigrace0_announce || choosen.Type == IStoreorgelement.Type.bigrace1_announce || choosen.Type == IStoreorgelement.Type.bigrace2_announce || choosen.Type == IStoreorgelement.Type.bigrace3_announce || choosen.Type == IStoreorgelement.Type.bigrace4_announce) {
                    menues.SetFieldState(this.detailes[i], 0);
                } else {
                    if (i == 6) {
                        if (choosen.has_money_reward) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 7) {
                        if (choosen.has_rating_reward) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 12) {
                        if (choosen.has_rating_reward && eng.visualLeague() == 0) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 13) {
                        if (choosen.has_rating_reward && eng.visualLeague() == 1) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 14) {
                        if (choosen.has_rating_reward && eng.visualLeague() == 2) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 15) {
                        if (choosen.has_rating_reward && eng.visualLeague() == 3) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 16) {
                        if (choosen.has_rating_reward && eng.visualLeague() == 4) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                    if (i == 8) {
                        if (choosen.has_info_reward) {
                            menues.SetFieldState(this.detailes[i], 1);
                        } else {
                            menues.SetFieldState(this.detailes[i], 0);
                        }
                    }
                }
            }
            if (i == 10) {
                if (choosen.Status == IStoreorgelement.Status.pendingMission) {
                    menues.SetFieldState(this.detailes[i], 0);
                } else if (choosen.Status == IStoreorgelement.Status.urgentMission) {
                    menues.SetFieldState(this.detailes[i], 1);
                } else if (choosen.Status == IStoreorgelement.Status.executedMission) {
                    menues.SetFieldState(this.detailes[i], 2);
                } else if (choosen.Status == IStoreorgelement.Status.failedMission) {
                    menues.SetFieldState(this.detailes[i], 3);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(this.detailes[i]));
        }
    }

    private void updateDetailesDescription() {
        Element choosen = this.getSelected();
        for (int i = 0; i < this.detailes_description.length; ++i) {
            menues.SetFieldText(this.detailes_description[i], choosen.description);
            switch (i) {
                case 0: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.baseDelivery);
                    break;
                }
                case 1: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.tender);
                    break;
                }
                case 2: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.competition);
                    break;
                }
                case 3: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.trailerClientDelivery);
                    break;
                }
                case 4: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.trailerObjectDelivery);
                    break;
                }
                case 5: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.passangerDelivery);
                    break;
                }
                case 6: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.pakageDelivery);
                    break;
                }
                case 7: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.visit);
                    break;
                }
                case 8: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace4);
                    break;
                }
                case 9: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace3);
                    break;
                }
                case 10: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace2);
                    break;
                }
                case 11: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace1);
                    break;
                }
                case 12: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace0);
                    break;
                }
                case 13: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace4_semi);
                    break;
                }
                case 14: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace3_semi);
                    break;
                }
                case 15: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace2_semi);
                    break;
                }
                case 16: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace1_semi);
                    break;
                }
                case 17: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace0_semi);
                    break;
                }
                case 18: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace4_announce);
                    break;
                }
                case 19: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace3_announce);
                    break;
                }
                case 20: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace2_announce);
                    break;
                }
                case 21: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace1_announce);
                    break;
                }
                case 22: {
                    menues.SetShowField(this.detailes_description[i], choosen.Type == IStoreorgelement.Type.bigrace0_announce);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(this.detailes_description[i]));
        }
    }

    private void updateSlider() {
        Element choosen = this.getSelected();
        MENUText_field field = menues.ConvertTextFields(this.fragility_slider);
        field.lenx = choosen.data == null || choosen.data.getType() == IStoreorgelement.Type.passangerDelivery || choosen.Type == IStoreorgelement.Type.competition || choosen.Type == IStoreorgelement.Type.tender || choosen.Type == IStoreorgelement.Type.visit || choosen.Type == IStoreorgelement.Type.bigrace0_announce || choosen.Type == IStoreorgelement.Type.bigrace1_announce || choosen.Type == IStoreorgelement.Type.bigrace2_announce || choosen.Type == IStoreorgelement.Type.bigrace3_announce || choosen.Type == IStoreorgelement.Type.bigrace4_announce || choosen.Type == IStoreorgelement.Type.bigrace0_semi || choosen.Type == IStoreorgelement.Type.bigrace1_semi || choosen.Type == IStoreorgelement.Type.bigrace2_semi || choosen.Type == IStoreorgelement.Type.bigrace3_semi || choosen.Type == IStoreorgelement.Type.bigrace4_semi || choosen.Type == IStoreorgelement.Type.bigrace0 || choosen.Type == IStoreorgelement.Type.bigrace1 || choosen.Type == IStoreorgelement.Type.bigrace2 || choosen.Type == IStoreorgelement.Type.bigrace3 || choosen.Type == IStoreorgelement.Type.bigrace4 ? (int)(0.0 * (double)this.fragility_slider_sx0) : (int)(0.01 * (double)choosen.fragility * (double)this.fragility_slider_sx0);
        menues.UpdateField(field);
    }

    private void updateTooltipsVoid() {
        this.tooltipSource.Enable(false);
        this.tooltipSource.setText("");
        this.tooltipDestination.Enable(false);
        this.tooltipDestination.setText("");
    }

    private void updateTooltips() {
        Element choosen = this.getSelected();
        if (choosen.Type == IStoreorgelement.Type.bigrace0_announce || choosen.Type == IStoreorgelement.Type.bigrace1_announce || choosen.Type == IStoreorgelement.Type.bigrace2_announce || choosen.Type == IStoreorgelement.Type.bigrace3_announce || choosen.Type == IStoreorgelement.Type.bigrace4_announce || choosen.Type == IStoreorgelement.Type.visit) {
            this.tooltipSource.Enable(false);
        } else {
            this.tooltipSource.Enable(true);
        }
        this.tooltipDestination.Enable(true);
        this.tooltipSource.setText(choosen.getSourceFull());
        this.tooltipDestination.setText(choosen.getDestinationFull());
    }

    private void updateMapSelection() {
        if (this.is_empty_table || null == this.mapElems) {
            return;
        }
        Element choosen = this.getSelected();
        for (MapElement elem : this.mapElems) {
            elem.selected = elem.data.equals(choosen);
        }
        for (MapElement elem : this.mapElems) {
            if (elem.id1 != -1) {
                this.mapa.selectHighlight(elem.id1, elem.selected);
            }
            if (elem.id2 == -1) continue;
            this.mapa.selectHighlight(elem.id2, elem.selected);
        }
    }

    private String buildSignedString(String source, int value, int macroIndex, boolean addPlus) {
        KeyPair[] pairs = new KeyPair[2];
        pairs[0] = new KeyPair(MACRO[macroIndex], "" + Math.abs(value));
        pairs[1] = new KeyPair("SIGN", value != 0 ? (value > 0 ? (addPlus ? "+" : " ") : "-") : "");
        return MacroKit.Parse(source, pairs);
    }

    private String buildSignedString(String source, String value, int macroIndex) {
        KeyPair[] pairs = new KeyPair[]{new KeyPair(MACRO[macroIndex], value), new KeyPair("SIGN", "")};
        return MacroKit.Parse(source, pairs);
    }

    private String buildSimpleString(String source, int value, int macroIndex) {
        KeyPair[] pairs = new KeyPair[]{new KeyPair(MACRO[macroIndex], "" + value)};
        return MacroKit.Parse(source, pairs);
    }

    private String buildSimpleString(String source, String value, int macroIndex) {
        KeyPair[] pairs = new KeyPair[]{new KeyPair(MACRO[macroIndex], value)};
        return MacroKit.Parse(source, pairs);
    }

    private Element getSelected() {
        if (this.is_empty_table) {
            return null;
        }
        return (Element)this.table.getSelected();
    }

    public void update() {
        organaiser.getInstance().updateMissionsOrgElements();
        this.table.updateTable();
        for (IUpdateListener lst : this.listeners) {
            lst.onUpdate();
        }
    }

    @Override
    public void enterFocus() {
        this.deinited = false;
        new AnimateRead();
        this.update();
    }

    @Override
    public void leaveFocus() {
        this.deinited = true;
    }

    public void updateRedraw() {
        this.table.redrawTable();
    }

    private SortMode fromSort(int mode) {
        if (this.sort == null || this.sort.sort_mode != mode) {
            return new SortMode(true, mode);
        }
        return new SortMode(!this.sort.ascending, mode);
    }

    private ArrayList<Element> sortLines(ArrayList<Element> _data) {
        ArrayList<Element> res = new ArrayList<Element>();
        res.addAll(_data);
        boolean was_shift = true;
        while (was_shift) {
            was_shift = false;
            for (int i = res.size() - 1; i > 0; --i) {
                Element elem2;
                Element elem1 = res.get(i);
                if (!this.isLess(elem1, elem2 = res.get(i - 1))) continue;
                was_shift = true;
                res.remove(i);
                res.remove(i - 1);
                res.add(i - 1, elem1);
                res.add(i, elem2);
            }
        }
        if (SortMode.only_active) {
            ArrayList<Element> ret = new ArrayList<Element>();
            for (int i = 0; i < res.size(); ++i) {
                Element elem = res.get(i);
                if (elem.Status != IStoreorgelement.Status.urgentMission && elem.Status != IStoreorgelement.Status.pendingMission) continue;
                ret.add(elem);
            }
            return ret;
        }
        return res;
    }

    private boolean isLess(Element elem1, Element elem2) {
        switch (this.sort.sort_mode) {
            case 0: {
                if (this.sort.ascending) {
                    return elem1.is_important == elem2.is_important ? false : !elem1.is_important;
                }
                return elem1.is_important == elem2.is_important ? false : elem1.is_important;
            }
            case 1: {
                if (this.sort.ascending) {
                    return elem1.is_read == elem2.is_read ? false : !elem1.is_read;
                }
                return elem1.is_read == elem2.is_read ? false : elem1.is_read;
            }
            case 2: {
                return this.sort.ascending ? elem1.Status.ordinal() < elem2.Status.ordinal() : elem1.Status.ordinal() > elem2.Status.ordinal();
            }
            case 3: {
                return this.sort.ascending ? elem1.Type.ordinal() < elem2.Type.ordinal() : elem1.Type.ordinal() > elem2.Type.ordinal();
            }
            case 14: {
                int c1 = 0;
                int c2 = 0;
                c1 = elem1.Type == IStoreorgelement.Type.bigrace0_announce || elem1.Type == IStoreorgelement.Type.bigrace1_announce || elem1.Type == IStoreorgelement.Type.bigrace2_announce || elem1.Type == IStoreorgelement.Type.bigrace3_announce || elem1.Type == IStoreorgelement.Type.bigrace4_announce ? 0 : (elem1.has_info_reward ? 1 : 0) + (elem1.has_money_reward ? 2 : 0) + (elem1.has_rating_reward ? 4 : 0);
                c2 = elem2.Type == IStoreorgelement.Type.bigrace0_announce || elem2.Type == IStoreorgelement.Type.bigrace1_announce || elem2.Type == IStoreorgelement.Type.bigrace2_announce || elem2.Type == IStoreorgelement.Type.bigrace3_announce || elem2.Type == IStoreorgelement.Type.bigrace4_announce ? 0 : (elem2.has_info_reward ? 1 : 0) + (elem2.has_money_reward ? 2 : 0) + (elem2.has_rating_reward ? 4 : 0);
                return this.sort.ascending ? c1 < c2 : c1 > c2;
            }
            case 7: {
                return this.sort.ascending ? elem1.description.compareTo(elem2.description) < 0 : elem1.description.compareTo(elem2.description) > 0;
            }
            case 8: {
                String customer2;
                String customer1;
                if (elem1.Type == IStoreorgelement.Type.baseDelivery || elem1.Type == IStoreorgelement.Type.trailerObjectDelivery || elem1.Type == IStoreorgelement.Type.competition || elem1.Type == IStoreorgelement.Type.tender || elem1.Type == IStoreorgelement.Type.bigrace0_semi || elem1.Type == IStoreorgelement.Type.bigrace1_semi || elem1.Type == IStoreorgelement.Type.bigrace2_semi || elem1.Type == IStoreorgelement.Type.bigrace3_semi || elem1.Type == IStoreorgelement.Type.bigrace4_semi || elem1.Type == IStoreorgelement.Type.bigrace0 || elem1.Type == IStoreorgelement.Type.bigrace1 || elem1.Type == IStoreorgelement.Type.bigrace2 || elem1.Type == IStoreorgelement.Type.bigrace3 || elem1.Type == IStoreorgelement.Type.bigrace4) {
                    customer1 = this.customer.getShortName();
                } else if (elem1.Type == IStoreorgelement.Type.bigrace0_announce || elem1.Type == IStoreorgelement.Type.bigrace1_announce || elem1.Type == IStoreorgelement.Type.bigrace2_announce || elem1.Type == IStoreorgelement.Type.bigrace3_announce || elem1.Type == IStoreorgelement.Type.bigrace4_announce) {
                    customer1 = "-";
                } else {
                    String string = customer1 = elem1.customer != null ? elem1.customer : "-";
                }
                if (elem2.Type == IStoreorgelement.Type.baseDelivery || elem2.Type == IStoreorgelement.Type.trailerObjectDelivery || elem2.Type == IStoreorgelement.Type.competition || elem2.Type == IStoreorgelement.Type.tender || elem2.Type == IStoreorgelement.Type.bigrace0_semi || elem2.Type == IStoreorgelement.Type.bigrace1_semi || elem2.Type == IStoreorgelement.Type.bigrace2_semi || elem2.Type == IStoreorgelement.Type.bigrace3_semi || elem2.Type == IStoreorgelement.Type.bigrace4_semi || elem2.Type == IStoreorgelement.Type.bigrace0 || elem2.Type == IStoreorgelement.Type.bigrace1 || elem2.Type == IStoreorgelement.Type.bigrace2 || elem2.Type == IStoreorgelement.Type.bigrace3 || elem2.Type == IStoreorgelement.Type.bigrace4) {
                    customer2 = this.customer.getShortName();
                } else if (elem2.Type == IStoreorgelement.Type.bigrace0_announce || elem2.Type == IStoreorgelement.Type.bigrace1_announce || elem2.Type == IStoreorgelement.Type.bigrace2_announce || elem2.Type == IStoreorgelement.Type.bigrace3_announce || elem2.Type == IStoreorgelement.Type.bigrace4_announce) {
                    customer2 = "-";
                } else {
                    String string = customer2 = elem2.customer != null ? elem2.customer : "-";
                }
                return this.sort.ascending ? customer1.compareTo(customer2) < 0 : customer1.compareTo(customer2) > 0;
            }
            case 9: {
                return this.sort.ascending ? elem1.request_date.moreThan(elem2.request_date) < 0 : elem1.request_date.moreThan(elem2.request_date) > 0;
            }
            case 4: 
            case 10: {
                String punkt1 = elem1.getSourceFull();
                String punkt2 = elem2.getSourceFull();
                if (this.sort.sort_mode == 4) {
                    if (elem1.Type == IStoreorgelement.Type.bigrace0_announce || elem1.Type == IStoreorgelement.Type.bigrace1_announce || elem1.Type == IStoreorgelement.Type.bigrace2_announce || elem1.Type == IStoreorgelement.Type.bigrace3_announce || elem1.Type == IStoreorgelement.Type.bigrace4_announce || elem1.Type == IStoreorgelement.Type.visit) {
                        punkt1 = "-";
                    }
                    if (elem2.Type == IStoreorgelement.Type.bigrace0_announce || elem2.Type == IStoreorgelement.Type.bigrace1_announce || elem2.Type == IStoreorgelement.Type.bigrace2_announce || elem2.Type == IStoreorgelement.Type.bigrace3_announce || elem2.Type == IStoreorgelement.Type.bigrace4_announce || elem2.Type == IStoreorgelement.Type.visit) {
                        punkt2 = "-";
                    }
                }
                return this.sort.ascending ? punkt1.compareTo(punkt2) < 0 : punkt1.compareTo(punkt2) > 0;
            }
            case 11: {
                CoreTime time1 = new CoreTime();
                CoreTime time2 = new CoreTime();
                if (elem2.Type != IStoreorgelement.Type.bigrace0_announce && elem2.Type != IStoreorgelement.Type.bigrace1_announce && elem2.Type != IStoreorgelement.Type.bigrace2_announce && elem2.Type != IStoreorgelement.Type.bigrace3_announce && elem2.Type != IStoreorgelement.Type.bigrace4_announce && elem2.Type != IStoreorgelement.Type.visit) {
                    time1 = elem1.load_date;
                }
                if (elem1.Type != IStoreorgelement.Type.bigrace0_announce && elem1.Type != IStoreorgelement.Type.bigrace1_announce && elem1.Type != IStoreorgelement.Type.bigrace2_announce && elem1.Type != IStoreorgelement.Type.bigrace3_announce && elem1.Type != IStoreorgelement.Type.bigrace4_announce && elem1.Type != IStoreorgelement.Type.visit) {
                    time2 = elem2.load_date;
                }
                return this.sort.ascending ? time1.moreThan(time2) < 0 : time1.moreThan(time2) > 0;
            }
            case 5: 
            case 12: {
                return this.sort.ascending ? elem1.getDestinationFull().compareTo(elem2.getDestinationFull()) < 0 : elem1.getDestinationFull().compareTo(elem2.getDestinationFull()) > 0;
            }
            case 13: {
                return this.sort.ascending ? elem1.complete_date.moreThan(elem2.complete_date) < 0 : elem1.complete_date.moreThan(elem2.complete_date) > 0;
            }
            case 15: {
                int forefit2;
                int forefit1;
                int n = forefit1 = elem1.has_forefit ? elem1.forfeit : 0;
                if (elem1.Type == IStoreorgelement.Type.bigrace0_announce || elem1.Type == IStoreorgelement.Type.bigrace1_announce || elem1.Type == IStoreorgelement.Type.bigrace2_announce || elem1.Type == IStoreorgelement.Type.bigrace3_announce || elem1.Type == IStoreorgelement.Type.bigrace4_announce) {
                    forefit1 = 0;
                }
                int n2 = forefit2 = elem2.has_forefit ? elem2.forfeit : 0;
                if (elem2.Type == IStoreorgelement.Type.bigrace0_announce || elem2.Type == IStoreorgelement.Type.bigrace1_announce || elem2.Type == IStoreorgelement.Type.bigrace2_announce || elem2.Type == IStoreorgelement.Type.bigrace3_announce || elem2.Type == IStoreorgelement.Type.bigrace4_announce) {
                    forefit2 = 0;
                }
                return this.sort.ascending ? forefit1 < forefit2 : forefit1 > forefit2;
            }
            case 6: 
            case 16: {
                int minutes1 = 0;
                int seconds1 = 0;
                int minutes2 = 0;
                int seconds2 = 0;
                if (elem1.Status != IStoreorgelement.Status.executedMission || elem1.Status == IStoreorgelement.Status.failedMission) {
                    minutes1 = elem1.timeleft_minutes;
                    seconds1 = elem1.timeleft_seconds;
                }
                if (elem2.Status != IStoreorgelement.Status.executedMission || elem2.Status == IStoreorgelement.Status.failedMission) {
                    minutes2 = elem2.timeleft_minutes;
                    seconds2 = elem2.timeleft_seconds;
                }
                if (minutes1 != minutes2) {
                    return this.sort.ascending ? minutes1 < minutes2 : minutes1 > minutes2;
                }
                return this.sort.ascending ? seconds1 < seconds2 : seconds1 > seconds2;
            }
            case 17: {
                int frag1 = 0;
                if (elem1.Type != IStoreorgelement.Type.bigrace0 && elem1.Type != IStoreorgelement.Type.bigrace1 && elem1.Type != IStoreorgelement.Type.bigrace2 && elem1.Type != IStoreorgelement.Type.bigrace3 && elem1.Type != IStoreorgelement.Type.bigrace4 && elem1.Type != IStoreorgelement.Type.bigrace0_announce && elem1.Type != IStoreorgelement.Type.bigrace1_announce && elem1.Type != IStoreorgelement.Type.bigrace2_announce && elem1.Type != IStoreorgelement.Type.bigrace3_announce && elem1.Type != IStoreorgelement.Type.bigrace4_announce && elem1.Type != IStoreorgelement.Type.visit && elem1.Type != IStoreorgelement.Type.pakageDelivery) {
                    frag1 = elem1.fragility;
                }
                int frag2 = 0;
                if (elem2.Type != IStoreorgelement.Type.bigrace0 && elem2.Type != IStoreorgelement.Type.bigrace1 && elem2.Type != IStoreorgelement.Type.bigrace2 && elem2.Type != IStoreorgelement.Type.bigrace3 && elem2.Type != IStoreorgelement.Type.bigrace4 && elem2.Type != IStoreorgelement.Type.bigrace0_announce && elem2.Type != IStoreorgelement.Type.bigrace1_announce && elem2.Type != IStoreorgelement.Type.bigrace2_announce && elem2.Type != IStoreorgelement.Type.bigrace3_announce && elem2.Type != IStoreorgelement.Type.bigrace4_announce && elem2.Type != IStoreorgelement.Type.visit && elem2.Type != IStoreorgelement.Type.pakageDelivery) {
                    frag2 = elem2.fragility;
                }
                return this.sort.ascending ? frag1 < frag2 : frag1 > frag2;
            }
        }
        return this.sort.ascending ? elem1.hashCode() < elem2.hashCode() : elem1.hashCode() > elem2.hashCode();
    }

    public void onActive(long _menu, MENUbutton_field button) {
        SortMode.only_active = 0 == menues.GetFieldState(button.nativePointer);
        this.update();
    }

    public void onCurrent(long _menu, MENUsimplebutton_field button) {
        if (null != this.current) {
            this.table.selectLineByData(this.current);
        }
    }

    public void onMarkCurrent(long _menu, MENUsimplebutton_field button) {
        if (this.current == null) {
            return;
        }
        if (!this.DEBUG) {
            Element tobe_current = this.getSelected();
            organaiser.choose(tobe_current.data);
            this.update();
        } else {
            this.current.is_current = false;
            Element tobe_current = this.getSelected();
            tobe_current.is_current = true;
            this.current = tobe_current;
        }
        this.makeButtonGray();
    }

    private void realCancelTask() {
        if (this.is_empty_table) {
            return;
        }
        Element tobe_canceled = this.getSelected();
        if (!(this.DEBUG || tobe_canceled.Status != IStoreorgelement.Status.pendingMission && tobe_canceled.Status != IStoreorgelement.Status.urgentMission)) {
            if (tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace0 || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace1 || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace2 || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace3 || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace4 || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace0_semi || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace1_semi || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace2_semi || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace3_semi || tobe_canceled.data.getType() == IStoreorgelement.Type.bigrace4_semi) {
                int journal_message = 0;
                switch (tobe_canceled.data.getType()) {
                    case bigrace0_semi: 
                    case bigrace0: {
                        journal_message = 58;
                        break;
                    }
                    case bigrace1_semi: 
                    case bigrace1: {
                        journal_message = 57;
                        break;
                    }
                    case bigrace2_semi: 
                    case bigrace2: {
                        journal_message = 56;
                        break;
                    }
                    case bigrace3_semi: 
                    case bigrace3: {
                        journal_message = 55;
                        break;
                    }
                    case bigrace4_semi: 
                    case bigrace4: {
                        journal_message = 54;
                    }
                }
                JournalFinishWarehouse.createNoteL17DPlacesNames(journal_message, loc.getBigraceShortName(tobe_canceled.data.getRaceName()), tobe_canceled.data.getLogoName(), MacroBuilder.makeSimpleMacroBody(tobe_canceled._sourceShort), MacroBuilder.makeSimpleMacroBody(tobe_canceled._destinationShort), 1, 0.0, 0, tobe_canceled.data.getStageID());
            } else if (tobe_canceled.data.getType() == IStoreorgelement.Type.baseDelivery) {
                Macros macroMoney = new Macros("VALUE1", MacroBuilder.makeSimpleMacroBody(Helper.convertMoney(tobe_canceled.forfeit)));
                ArrayList<Macros> macroces = new ArrayList<Macros>();
                macroces.add(macroMoney);
                JournalFinishWarehouse.createNote(2, macroces);
            } else if (tobe_canceled.data.getType() == IStoreorgelement.Type.tender) {
                JournalFinishWarehouse.createNote(10);
            } else if (tobe_canceled.data.getType() == IStoreorgelement.Type.competition) {
                JournalFinishWarehouse.createNote(10);
            }
            tobe_canceled.data.decline();
            this.update();
        }
    }

    public void onCancelMission(long _menu, MENUsimplebutton_field button) {
        if (this.is_empty_table) {
            return;
        }
        Element tobe_canceled = this.getSelected();
        if (!(this.DEBUG || tobe_canceled.Status != IStoreorgelement.Status.pendingMission && tobe_canceled.Status != IStoreorgelement.Status.urgentMission)) {
            this.drawCancelTaskWarning(tobe_canceled.description, tobe_canceled.has_forefit ? tobe_canceled.forfeit : 0, tobe_canceled.rating_forefit);
        }
    }

    private ArrayList<Element> getAllLines() {
        Element elem;
        organaiser.getInstance().updateMissionsOrgElements();
        if (this.DEBUG) {
            ArrayList<MapElement> mapelems = new ArrayList<MapElement>();
            ArrayList<Element> res = new ArrayList<Element>();
            Element elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = true;
            elem2.is_read = true;
            elem2.Status = IStoreorgelement.Status.pendingMission;
            elem2.Type = IStoreorgelement.Type.baseDelivery;
            elem2.description = "Fake mission - first one";
            elem2.fragility = 1;
            elem2.is_current = true;
            this.current = elem2;
            MapElement mel = new MapElement();
            mel.data = elem2;
            mel.state = 0;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = true;
            mel.selected = false;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = false;
            elem2.is_read = true;
            elem2.Status = IStoreorgelement.Status.urgentMission;
            elem2.Type = IStoreorgelement.Type.pakageDelivery;
            elem2.description = "Fake mission - second one";
            elem2.fragility = 30;
            mel = new MapElement();
            mel.data = elem2;
            mel.state = 1;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = false;
            mel.selected = true;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = true;
            elem2.is_read = false;
            elem2.description = "Fake mission - third one";
            elem2.Status = IStoreorgelement.Status.executedMission;
            elem2.Type = IStoreorgelement.Type.passangerDelivery;
            elem2.fragility = 90;
            mel = new MapElement();
            mel.data = elem2;
            mel.state = 0;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = false;
            mel.selected = false;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = false;
            elem2.is_read = true;
            elem2.description = "Fake mission - forth one";
            elem2.Status = IStoreorgelement.Status.failedMission;
            elem2.Type = IStoreorgelement.Type.bigrace1;
            elem2.fragility = 10;
            mel = new MapElement();
            mel.data = elem2;
            mel.state = 1;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = false;
            mel.selected = false;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = false;
            elem2.is_read = true;
            elem2.description = "Fake mission - fifth one";
            elem2.Status = IStoreorgelement.Status.executedMission;
            elem2.Type = IStoreorgelement.Type.bigrace2;
            elem2.fragility = 99;
            mel = new MapElement();
            mel.data = elem2;
            mel.state = 0;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = false;
            mel.selected = false;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = true;
            elem2.is_read = true;
            elem2.description = "Fake mission - sixth one";
            elem2.Status = IStoreorgelement.Status.urgentMission;
            elem2.Type = IStoreorgelement.Type.bigrace3;
            elem2.fragility = 50;
            mel = new MapElement();
            mel.data = elem2;
            mel.state = 1;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = false;
            mel.selected = false;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            elem2 = new Element();
            elem2.timeleft_minutes = (int)rnd.r(0.0, 200.0);
            elem2.timeleft_seconds = (int)rnd.r(0.0, 60.0);
            elem2.is_important = false;
            elem2.is_read = false;
            elem2.description = "Fake mission - seventh one";
            elem2.Status = IStoreorgelement.Status.pendingMission;
            elem2.Type = IStoreorgelement.Type.bigrace4;
            elem2.fragility = 74;
            mel = new MapElement();
            mel.data = elem2;
            mel.state = 0;
            mel.x_start = (float)rnd.r(-1.0, 1.0);
            mel.y_start = (float)rnd.r(-1.0, 1.0);
            mel.x_source = (float)rnd.r(-1.0, 0.0);
            mel.y_source = (float)rnd.r(0.0, 1.0);
            mel.x_destination = (float)rnd.r(0.0, 1.0);
            mel.y_destination = (float)rnd.r(0.0, 1.0);
            mel.is_current = false;
            mel.selected = false;
            mel.type_delievery = rnd.rtype();
            mel.priority = (int)rnd.r(0.0, 10.0);
            mel.store_time = elem2.timeleft_seconds + 60 * elem2.timeleft_minutes;
            mapelems.add(mel);
            res.add(elem2);
            this.updateMap(mapelems);
            return this.sortLines(res);
        }
        ArrayList<Element> res = new ArrayList<Element>();
        ArrayList<Object> res_sorted = new ArrayList();
        Iterator<IStoreorgelement> iter = organaiser.getInstance().gOrganaiser();
        ArrayList<MapElement> mapelems = new ArrayList<MapElement>();
        while (iter.hasNext()) {
            IStoreorgelement istored = iter.next();
            elem = new Element();
            elem.data = istored;
            elem.Type = istored.getType();
            elem.is_important = elem.Type == IStoreorgelement.Type.bigrace0_announce || elem.Type == IStoreorgelement.Type.bigrace1_announce || elem.Type == IStoreorgelement.Type.bigrace2_announce || elem.Type == IStoreorgelement.Type.bigrace3_announce || elem.Type == IStoreorgelement.Type.bigrace4_announce || elem.Type == IStoreorgelement.Type.bigrace0_semi || elem.Type == IStoreorgelement.Type.bigrace1_semi || elem.Type == IStoreorgelement.Type.bigrace2_semi || elem.Type == IStoreorgelement.Type.bigrace3_semi || elem.Type == IStoreorgelement.Type.bigrace4_semi || elem.Type == IStoreorgelement.Type.bigrace0 || elem.Type == IStoreorgelement.Type.bigrace1 || elem.Type == IStoreorgelement.Type.bigrace2 || elem.Type == IStoreorgelement.Type.bigrace3 || elem.Type == IStoreorgelement.Type.bigrace4 ? true : istored.isImportant();
            elem.is_read = elem.Type == IStoreorgelement.Type.baseDelivery || elem.Type == IStoreorgelement.Type.trailerObjectDelivery || elem.Type == IStoreorgelement.Type.competition || elem.Type == IStoreorgelement.Type.tender || elem.Type == IStoreorgelement.Type.bigrace0_announce || elem.Type == IStoreorgelement.Type.bigrace1_announce || elem.Type == IStoreorgelement.Type.bigrace2_announce || elem.Type == IStoreorgelement.Type.bigrace3_announce || elem.Type == IStoreorgelement.Type.bigrace4_announce || elem.Type == IStoreorgelement.Type.bigrace0_semi || elem.Type == IStoreorgelement.Type.bigrace1_semi || elem.Type == IStoreorgelement.Type.bigrace2_semi || elem.Type == IStoreorgelement.Type.bigrace3_semi || elem.Type == IStoreorgelement.Type.bigrace4_semi || elem.Type == IStoreorgelement.Type.bigrace0 || elem.Type == IStoreorgelement.Type.bigrace1 || elem.Type == IStoreorgelement.Type.bigrace2 || elem.Type == IStoreorgelement.Type.bigrace3 || elem.Type == IStoreorgelement.Type.bigrace4 ? true : istored.isRead();
            elem.Status = istored.getStatus();
            elem._source = istored.loadPoint();
            elem._destination = istored.endPoint();
            if (istored.getStatus() == IStoreorgelement.Status.failedMission) {
                elem.has_forefit = false;
                elem.forfeit = 0;
                elem.has_rating_reward = (istored.getForfeitFlag() & 4) != 0;
                elem.rating_reward = istored.getForfeit().gRate();
                elem.has_money_reward = (istored.getForfeitFlag() & 1) != 0;
                elem.money_reward = -istored.getForfeit().gMoney();
                elem.is_active = false;
            } else if (istored.getStatus() == IStoreorgelement.Status.executedMission) {
                elem.has_money_reward = (istored.getRewardFlag() & 1) != 0;
                elem.has_rating_reward = (istored.getRewardFlag() & 4) != 0;
                elem.money_reward = istored.getReward().gMoney();
                elem.rating_reward = istored.getReward().gRate();
                elem.has_forefit = false;
                elem.forfeit = 0;
                elem.is_active = false;
            } else {
                elem.has_money_reward = (istored.getRewardFlag() & 1) != 0;
                elem.has_rating_reward = (istored.getRewardFlag() & 4) != 0;
                elem.money_reward = istored.getReward().gMoney();
                elem.rating_reward = istored.getReward().gRate();
                elem.has_forefit = (istored.getForfeitFlag() & 1) != 0;
                elem.forfeit = istored.getForfeit().gMoney();
                elem.is_active = true;
            }
            elem.has_info_reward = (istored.getRewardFlag() & 8) != 0;
            elem.customer = istored.getCustomer().getName();
            elem.request_date = istored.dateOfRequest();
            elem.load_date = istored.timeToPickUp();
            elem.complete_date = istored.timeToComplete();
            elem.timeleft_minutes = istored.get_minutes_toFail();
            elem.timeleft_seconds = istored.get_seconds_toFail();
            elem.fragility = istored.getCargoFragility();
            elem.description = istored.getDescription();
            elem.rating_forefit = istored.getForfeit().gRate();
            vectorJ pos_start = istored.pos_start();
            elem.x_start = pos_start.x;
            elem.y_start = pos_start.y;
            vectorJ pos_load = istored.pos_load();
            elem.x_source = pos_load.x;
            elem.y_source = pos_load.y;
            vectorJ pos_complete = istored.pos_complete();
            elem.x_destination = pos_complete.x;
            elem.y_destination = pos_complete.y;
            elem.is_current = organaiser.getInstance().isCurrent(istored);
            res.add(elem);
        }
        res_sorted = this.sortLines(res);
        for (int i = 0; i < res_sorted.size(); ++i) {
            elem = (Element)res_sorted.get(i);
            if (elem.is_current) {
                this.current = elem;
            }
            if (elem.Status != IStoreorgelement.Status.urgentMission && elem.Status != IStoreorgelement.Status.pendingMission) continue;
            MapElement mape = new MapElement();
            mape.data = elem;
            mape.is_current = elem.is_current;
            mape.state = elem.data.getMissionState();
            mape.x_start = (float)this.coords.convertX(elem.x_start);
            mape.y_start = (float)this.coords.convertY(elem.y_start);
            mape.x_source = (float)this.coords.convertX(elem.x_source);
            mape.y_source = (float)this.coords.convertY(elem.y_source);
            mape.x_destination = (float)this.coords.convertX(elem.x_destination);
            mape.y_destination = (float)this.coords.convertY(elem.y_destination);
            mape.type_delievery = elem.Type;
            mape.selected = false;
            mape.priority = 1;
            mape.store_time = elem.timeleft_seconds + 60 * elem.timeleft_minutes;
            mapelems.add(mape);
        }
        this.updateMap(mapelems);
        return res_sorted;
    }

    void updateMap(ArrayList<MapElement> tasks) {
        if (!this.map_inited) {
            return;
        }
        this.fixPriorities(tasks);
        this.mapa.ClearData();
        this.updateWarehousesOnMapa();
        this.mapElems = tasks;
        MapElement_player player_data = new MapElement_player();
        ArrayList<Object> elems = new ArrayList();
        if (this.DEBUG) {
            player_data.x = (float)rnd.r(-1.0, 0.0);
            player_data.y = (float)rnd.r(-1.0, 0.0);
            player_data.has_pack = false;
            player_data.has_trailer = true;
            player_data.has_passanger = false;
            elems = tasks;
        } else {
            vectorJ pos = rnrscr.Helper.getCurrentPosition();
            player_data.x = (float)this.coords.convertX(pos.x);
            player_data.y = (float)this.coords.convertY(pos.y);
            player_data.has_pack = MissionEventsMaker.isPackageSlotBuzzy();
            player_data.has_trailer = MissionEventsMaker.isTrailerSlotBuzzy();
            player_data.has_passanger = MissionEventsMaker.isPassanerSlotBuzzy();
            elems = tasks;
        }
        int icon_player = this.mapa.addObject(27, player_data.x, player_data.y, "", null);
        this.setPriority(icon_player, 100000);
        if (player_data.has_pack) {
            this.mapa.addObject(36, player_data.x, player_data.y, "", null);
        } else if (player_data.has_trailer) {
            this.mapa.addObject(34, player_data.x, player_data.y, "", null);
        } else if (player_data.has_passanger) {
            this.mapa.addObject(35, player_data.x, player_data.y, "", null);
        }
        for (MapElement mapElement : elems) {
            int dst1;
            SelectionCB cb = new SelectionCB(mapElement);
            mapElement.id_player = icon_player;
            if (mapElement.state == 0) {
                mapElement.id_load = this.mapa.addObject(mapElement.is_current ? 32 : 33, mapElement.x_source, mapElement.y_source, "", cb);
                this.setPriority(mapElement.id_load, mapElement.priority + 30);
            }
            mapElement.id_destination = this.mapa.addObject(mapElement.is_current ? 32 : 33, mapElement.x_destination, mapElement.y_destination, "", cb);
            this.setPriority(mapElement.id_destination, mapElement.priority);
            if (mapElement.state == 0) {
                if (mapElement.type_delievery == IStoreorgelement.Type.trailerClientDelivery || mapElement.type_delievery == IStoreorgelement.Type.trailerObjectDelivery || mapElement.type_delievery == IStoreorgelement.Type.baseDelivery) {
                    this.mapa.addObject(34, mapElement.x_source, mapElement.y_source, "", cb);
                } else if (mapElement.type_delievery == IStoreorgelement.Type.passangerDelivery) {
                    this.mapa.addObject(35, mapElement.x_source, mapElement.y_source, "", cb);
                } else if (mapElement.type_delievery == IStoreorgelement.Type.pakageDelivery) {
                    this.mapa.addObject(36, mapElement.x_source, mapElement.y_source, "", cb);
                }
                dst1 = this.mapa.addDestination(mapElement.is_current ? 28 : 30, mapElement.id_player, mapElement.id_load, "", cb);
                int dst2 = this.mapa.addDestination(mapElement.is_current ? 28 : 30, mapElement.id_load, mapElement.id_destination, "", cb);
                mapElement.id1 = dst1;
                mapElement.id2 = dst2;
                this.mapa.selectHighlight(dst1, mapElement.selected);
                this.mapa.selectHighlight(dst2, mapElement.selected);
                this.setPriority(dst1, mapElement.priority + 45);
                this.setPriority(dst2, mapElement.priority + 15);
                this.mapa.syncDirections(dst1, dst2);
                continue;
            }
            mapElement.id1 = dst1 = this.mapa.addDestination(mapElement.is_current ? 28 : 30, mapElement.id_player, mapElement.id_destination, "", cb);
            this.mapa.selectHighlight(dst1, mapElement.selected);
            this.setPriority(dst1, mapElement.priority + 15);
        }
    }

    private void fixPriorities(ArrayList<MapElement> elems) {
        Collections.sort(elems, new CompareMapElements());
        int priority = 100;
        for (MapElement mel : elems) {
            mel.priority = priority;
            priority += 100;
        }
        this.max_priority = priority + 100;
    }

    private void setPriority(int icon, int value) {
        int p1 = this.max_priority * 1;
        int p2 = this.max_priority * 2;
        int p3 = this.max_priority * 3;
        int p4 = this.max_priority * 4;
        int p5 = this.max_priority * 5;
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

    private void makeActiveActionOnMapFromTable(Element elem, boolean value) {
        for (MapElement mel : this.mapElems) {
            if (!mel.data.equals(elem)) continue;
            if (mel.id1 != -1) {
                this.mapa.setActiveObject(mel.id1, value);
            }
            if (mel.id2 == -1) continue;
            this.mapa.setActiveObject(mel.id2, value);
        }
    }

    private void makePressedActionOnMapFromTable(Element elem, boolean value) {
        for (MapElement mel : this.mapElems) {
            if (!mel.data.equals(elem)) continue;
            if (mel.id1 != -1) {
                this.mapa.setPressedObject(mel.id1, value);
                this.mapa.setActiveObject(mel.id1, value);
            }
            if (mel.id2 == -1) continue;
            this.mapa.setPressedObject(mel.id2, value);
            this.mapa.setActiveObject(mel.id2, value);
        }
    }

    public void activeMapControl(int id, boolean value) {
        for (MapElement elem : this.mapElems) {
            if (id != elem.id1 && id != elem.id2) continue;
            this.table.activeLineByData(elem.data, value);
        }
    }

    public void pressedMapControl(int id, boolean value) {
        for (MapElement elem : this.mapElems) {
            if (id != elem.id1 && id != elem.id2) continue;
            this.table.pressedLineByData(elem.data, value);
        }
    }

    private void updateWarehousesOnMapa() {
        CollectionOfData data = EventsHelper.getWarehousesData();
        Vector vec = data.data;
        for (DataWarehousesOnMap item : vec) {
            if (item.bIsMine) {
                this.mapa.addObject(4, (float)this.coords.convertX(item.x), (float)this.coords.convertY(item.y), item.name, new Object());
                continue;
            }
            this.mapa.addObject(3, (float)this.coords.convertX(item.x), (float)this.coords.convertY(item.y), item.name, new Object());
        }
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(0, 0);
        }
    }

    static class CompareMapElements
    implements Comparator {
        CompareMapElements() {
        }

        public int _compare(MapElement o1, MapElement o2) {
            return o1.store_time - o2.store_time;
        }

        public int compare(Object o1, Object o2) {
            return this._compare((MapElement)o1, (MapElement)o2);
        }
    }

    static class rnd {
        private static Random _r;

        rnd() {
        }

        public static final double r(double Min, double Max) {
            if (null == _r) {
                _r = new Random();
            }
            return Min + _r.nextDouble() * (Max - Min);
        }

        public static final IStoreorgelement.Type rtype() {
            double value = _r.nextDouble();
            if (value < 0.3) {
                return IStoreorgelement.Type.trailerClientDelivery;
            }
            if (value < 0.6) {
                return IStoreorgelement.Type.pakageDelivery;
            }
            return IStoreorgelement.Type.passangerDelivery;
        }
    }

    class AnimateRead
    extends TypicalAnm {
        private Element last_selected = null;
        private double time_countdown = 0.0;
        private boolean on_countdown = false;

        AnimateRead() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            if (OrganiserPane.this.deinited || OrganiserPane.this.menu_exited) {
                return true;
            }
            if (this.on_countdown) {
                Element sel = OrganiserPane.this.getSelected();
                if (null == sel) {
                    this.on_countdown = false;
                    this.last_selected = null;
                } else if (!this.last_selected.equals(sel)) {
                    this.on_countdown = true;
                    this.last_selected = sel;
                    this.time_countdown = dt;
                } else if (dt - this.time_countdown > 2.0) {
                    this.last_selected.data.makeRead();
                    organaiser.getInstance().readOne();
                    this.last_selected.is_read = true;
                    this.on_countdown = false;
                    this.last_selected = null;
                    OrganiserPane.this.updateRedraw();
                }
            } else {
                Element sel = OrganiserPane.this.getSelected();
                if (sel != null && sel.data != null && !sel.data.isRead()) {
                    this.last_selected = sel;
                    this.on_countdown = true;
                    this.time_countdown = dt;
                }
            }
            return false;
        }
    }

    class OrgTable
    extends TableWrapped {
        boolean[] last_active_tablestates;
        boolean[] changed_active_tablestates;
        boolean[] last_pressed_tablestates;
        boolean[] changed_pressed_tablestates;
        boolean deinited;

        public OrgTable(long _menu) {
            super(_menu, 1, true, OrganiserPane.XML, OrganiserPane.TABLE, OrganiserPane.RANGER, OrganiserPane.LINE, LINE_ELEMENTS, null, SORT);
            this.last_active_tablestates = null;
            this.changed_active_tablestates = null;
            this.last_pressed_tablestates = null;
            this.changed_pressed_tablestates = null;
            this.deinited = false;
            eng.CreateInfinitScriptAnimation(new Animate());
        }

        public void checkActivePassive() {
            Element elem;
            TableLine line;
            int i;
            int numrows = this.table.getNumRows();
            if (this.last_active_tablestates == null) {
                this.last_active_tablestates = new boolean[numrows];
                this.changed_active_tablestates = new boolean[numrows];
                for (i = 0; i < numrows; ++i) {
                    this.last_active_tablestates[i] = false;
                    this.changed_active_tablestates[i] = false;
                }
            }
            if (this.last_pressed_tablestates == null) {
                this.last_pressed_tablestates = new boolean[numrows];
                this.changed_pressed_tablestates = new boolean[numrows];
                for (i = 0; i < numrows; ++i) {
                    this.last_pressed_tablestates[i] = false;
                    this.changed_pressed_tablestates[i] = false;
                }
            }
            for (i = 0; i < numrows; ++i) {
                this.changed_active_tablestates[i] = false;
                this.changed_pressed_tablestates[i] = false;
            }
            for (i = 0; i < numrows; ++i) {
                if (this.table.isLineActiveNow(i)) {
                    if (!this.last_active_tablestates[i]) {
                        this.last_active_tablestates[i] = true;
                        this.changed_active_tablestates[i] = true;
                    }
                } else if (this.last_active_tablestates[i]) {
                    this.last_active_tablestates[i] = false;
                    this.changed_active_tablestates[i] = true;
                }
                if (this.table.isLinePressedNow(i)) {
                    if (this.last_pressed_tablestates[i]) continue;
                    this.last_pressed_tablestates[i] = true;
                    this.changed_pressed_tablestates[i] = true;
                    continue;
                }
                if (!this.last_pressed_tablestates[i]) continue;
                this.last_pressed_tablestates[i] = false;
                this.changed_pressed_tablestates[i] = true;
            }
            for (i = 0; i < this.changed_pressed_tablestates.length; ++i) {
                if (!this.changed_pressed_tablestates[i]) continue;
                line = this.getLineItem(i);
                if (!line.wheather_show) continue;
                elem = (Element)line;
                OrganiserPane.this.makePressedActionOnMapFromTable(elem, this.last_pressed_tablestates[i]);
            }
            for (i = 0; i < this.changed_active_tablestates.length; ++i) {
                if (!this.changed_active_tablestates[i]) continue;
                line = this.getLineItem(i);
                if (!line.wheather_show) continue;
                elem = (Element)line;
                OrganiserPane.this.makeActiveActionOnMapFromTable(elem, this.last_active_tablestates[i]);
            }
        }

        public void afterInit() {
            super.afterInit();
            this.makeSyncGroup();
        }

        private void makeSyncGroup() {
            for (int controls = 0; controls < LINE_ELEMENTS.length; ++controls) {
                long[] contrl = this.table.getLineStatistics_controls(LINE_ELEMENTS[controls]);
                for (int gr = 0; gr < contrl.length; ++gr) {
                    menues.SetSyncControlActive(OrganiserPane.this._menu, gr, contrl[gr]);
                    menues.SetSyncControlState(OrganiserPane.this._menu, gr, contrl[gr]);
                }
            }
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            OrganiserPane.this.sort = OrganiserPane.this.fromSort(button.userid);
            OrganiserPane.this.update();
        }

        protected void reciveTableData() {
            ArrayList all = OrganiserPane.this.getAllLines();
            OrganiserPane.this.is_empty_table = all == null || all.isEmpty();
            this.TABLE_DATA.all_lines.addAll(all);
        }

        public void SetupLineInTable(long button, int position, TableLine table_node) {
            Element line = (Element)table_node;
            if (line.is_current && position < 35) {
                menues.SetShowField(button, false);
                return;
            }
            if (!line.is_current && position >= 35) {
                menues.SetShowField(button, false);
                return;
            }
            switch (position) {
                case 0: 
                case 35: {
                    menues.SetShowField(button, line.is_important);
                    break;
                }
                case 1: 
                case 36: {
                    menues.SetShowField(button, !line.is_important);
                    break;
                }
                case 2: 
                case 37: {
                    menues.SetShowField(button, line.is_read);
                    break;
                }
                case 3: 
                case 38: {
                    menues.SetShowField(button, !line.is_read);
                    break;
                }
                case 7: 
                case 42: {
                    menues.SetShowField(button, line.Status == IStoreorgelement.Status.executedMission);
                    break;
                }
                case 8: 
                case 43: {
                    menues.SetShowField(button, line.Status == IStoreorgelement.Status.failedMission);
                    break;
                }
                case 4: 
                case 39: {
                    menues.SetShowField(button, line.Status == IStoreorgelement.Status.nostatus);
                    break;
                }
                case 5: 
                case 40: {
                    menues.SetShowField(button, line.Status == IStoreorgelement.Status.pendingMission);
                    break;
                }
                case 6: 
                case 41: {
                    menues.SetShowField(button, line.Status == IStoreorgelement.Status.urgentMission);
                    break;
                }
                case 15: 
                case 50: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.pakageDelivery);
                    break;
                }
                case 14: 
                case 49: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.passangerDelivery);
                    break;
                }
                case 11: 
                case 46: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.competition);
                    break;
                }
                case 9: 
                case 44: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.baseDelivery);
                    break;
                }
                case 13: 
                case 48: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.trailerObjectDelivery);
                    break;
                }
                case 12: 
                case 47: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.trailerClientDelivery);
                    break;
                }
                case 17: 
                case 52: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace4);
                    break;
                }
                case 18: 
                case 53: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace3);
                    break;
                }
                case 19: 
                case 54: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace2);
                    break;
                }
                case 20: 
                case 55: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace1);
                    break;
                }
                case 21: 
                case 56: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace0);
                    break;
                }
                case 25: 
                case 60: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace4_semi);
                    break;
                }
                case 26: 
                case 61: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace3_semi);
                    break;
                }
                case 27: 
                case 62: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace2_semi);
                    break;
                }
                case 28: 
                case 63: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace1_semi);
                    break;
                }
                case 29: 
                case 64: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace0_semi);
                    break;
                }
                case 30: 
                case 65: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace4_announce);
                    break;
                }
                case 31: 
                case 66: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace3_announce);
                    break;
                }
                case 32: 
                case 67: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace2_announce);
                    break;
                }
                case 33: 
                case 68: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace1_announce);
                    break;
                }
                case 34: 
                case 69: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.bigrace0_announce);
                    break;
                }
                case 10: 
                case 45: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.tender);
                    break;
                }
                case 16: 
                case 51: {
                    menues.SetShowField(button, line.Type == IStoreorgelement.Type.visit);
                    break;
                }
                case 23: 
                case 58: {
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.getDestinationShort());
                    break;
                }
                case 22: 
                case 57: {
                    menues.SetShowField(button, true);
                    if (line.Type == IStoreorgelement.Type.visit || line.Type == IStoreorgelement.Type.bigrace0_announce || line.Type == IStoreorgelement.Type.bigrace1_announce || line.Type == IStoreorgelement.Type.bigrace2_announce || line.Type == IStoreorgelement.Type.bigrace3_announce || line.Type == IStoreorgelement.Type.bigrace4_announce) {
                        menues.SetFieldText(button, "-");
                        break;
                    }
                    menues.SetFieldText(button, line.getSourceShort());
                    break;
                }
                case 59: {
                    menues.SetShowField(button, true);
                    if (OrganiserPane.this.detailesStringsTimeLeft == null) {
                        OrganiserPane.this.detailesStringsTimeLeft = menues.GetFieldText(button);
                    }
                    if (line.Status == IStoreorgelement.Status.executedMission || line.Status == IStoreorgelement.Status.failedMission) {
                        menues.SetFieldText(button, Converts.ConverTime3Plus2(OrganiserPane.this.detailesStringsTimeLeft, 0, 0));
                        break;
                    }
                    menues.SetFieldText(button, Converts.ConverTime3Plus2(OrganiserPane.this.detailesStringsTimeLeft, line.timeleft_minutes, line.timeleft_seconds));
                    break;
                }
                case 24: {
                    menues.SetShowField(button, true);
                    if (OrganiserPane.this.detailesCurrentStringsTimeLeft == null) {
                        OrganiserPane.this.detailesCurrentStringsTimeLeft = menues.GetFieldText(button);
                    }
                    if (line.Status == IStoreorgelement.Status.executedMission || line.Status == IStoreorgelement.Status.failedMission) {
                        menues.SetFieldText(button, Converts.ConverTime3Plus2(OrganiserPane.this.detailesCurrentStringsTimeLeft, 0, 0));
                        break;
                    }
                    menues.SetFieldText(button, Converts.ConverTime3Plus2(OrganiserPane.this.detailesCurrentStringsTimeLeft, line.timeleft_minutes, line.timeleft_seconds));
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        public void updateSelectedInfo(TableLine linedata) {
            OrganiserPane.this.updateAllDetailes();
            OrganiserPane.this.makeButtonGray();
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        public void deinit() {
            this.table.deinit();
            this.deinited = true;
        }

        class Animate
        extends TypicalAnm {
            Animate() {
            }

            public boolean animaterun(double dt) {
                if (OrgTable.this.deinited) {
                    return true;
                }
                OrgTable.this.checkActivePassive();
                return false;
            }
        }
    }

    static class Element
    extends TableLine {
        IStoreorgelement data;
        boolean is_important = false;
        boolean is_read = false;
        IStoreorgelement.Status Status = IStoreorgelement.Status.nostatus;
        IStoreorgelement.Type Type = IStoreorgelement.Type.notype;
        private boolean strings_updated = false;
        private String _source = "river";
        private String _destination = "cliver";
        private String _sourceShort = "river";
        private String _destinationShort = "cliver";
        private String _sourceFull = "Once upon a time there was river. And a field of big smacky mushrums. So you need go there!";
        private String _destinationFull = "Once upon a time wild warrior smashed clive apon enemies. Thst's one plce exactly!";
        boolean has_money_reward;
        boolean has_rating_reward;
        boolean has_info_reward;
        boolean has_forefit;
        boolean is_active;
        String customer = "biver";
        CoreTime request_date = new CoreTime(2007, 6, 9, 10, 45);
        CoreTime load_date = new CoreTime(2007, 5, 27, 11, 0);
        CoreTime complete_date = new CoreTime(2007, 5, 27, 9, 0);
        int timeleft_minutes;
        int timeleft_seconds;
        int money_reward;
        double rating_reward;
        int fragility;
        String description = "bugago";
        int forfeit;
        double rating_forefit = 0.0;
        double x_start;
        double y_start;
        double x_source;
        double y_source;
        double x_destination;
        double y_destination;
        boolean is_current;

        Element() {
        }

        private void updateStrings() {
            EventGetPointLocInfo info;
            if (this.strings_updated) {
                return;
            }
            this.strings_updated = true;
            if (this._source != null && this._source.length() != 0) {
                info = MissionEventsMaker.getLocalisationMissionPointInfo(this._source);
                this._sourceFull = info.long_name;
                this._sourceShort = info.short_name;
            }
            if (this._destination != null && this._destination.length() != 0) {
                info = MissionEventsMaker.getLocalisationMissionPointInfo(this._destination);
                this._destinationFull = info.long_name;
                this._destinationShort = info.short_name;
            }
        }

        String getSourceShort() {
            this.updateStrings();
            return this._sourceShort;
        }

        String getDestinationShort() {
            this.updateStrings();
            return this._destinationShort;
        }

        String getSourceFull() {
            this.updateStrings();
            return this._sourceFull;
        }

        String getDestinationFull() {
            this.updateStrings();
            return this._destinationFull;
        }
    }

    static class MapElement {
        Element data;
        int state;
        float x_start;
        float y_start;
        float x_source;
        float y_source;
        float x_destination;
        float y_destination;
        boolean is_current;
        int id_player;
        int id_load;
        int id_destination;
        IStoreorgelement.Type type_delievery;
        boolean selected;
        int priority;
        int store_time;
        int id1 = -1;
        int id2 = -1;

        MapElement() {
        }
    }

    static class MapElement_player {
        float x;
        float y;
        boolean has_passanger = false;
        boolean has_trailer = false;
        boolean has_pack = false;

        MapElement_player() {
        }
    }

    static class SortMode {
        static boolean only_active = false;
        boolean ascending = true;
        int sort_mode = 0;

        SortMode(boolean ascending, int sort_mode) {
            this.ascending = ascending;
            this.sort_mode = sort_mode;
        }
    }

    class MenuCancelTaskListener
    implements IPoPUpMenuListener {
        MenuCancelTaskListener() {
        }

        public void onAgreeclose() {
            OrganiserPane.this.realCancelTask();
        }

        public void onClose() {
        }

        public void onOpen() {
        }

        public void onCancel() {
        }
    }

    class SelectMapControl
    implements SelectCb {
        SelectMapControl() {
        }

        public void OnSelect(int state, Object sender) {
            SelectionCB cb = (SelectionCB)sender;
            OrganiserPane.this.table.selectLineByData(cb.elem.data);
        }
    }

    static class SelectionCB {
        MapElement elem;

        SelectionCB(MapElement elem) {
            this.elem = elem;
        }
    }
}

