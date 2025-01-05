/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.ArrayList;
import menu.BalanceUpdater;
import menu.Common;
import menu.JavaEvents;
import menu.KeyPair;
import menu.ListenerManager;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.MenuControls;
import menu.SMenu;
import menu.TextScroller;
import menu.menucreation;
import menu.menues;
import menu.xmlcontrols;
import menuscript.Converts;
import menuscript.IPoPUpMenuListener;
import menuscript.IUpdateListener;
import menuscript.IYesNoCancelMenuListener;
import menuscript.PoPUpMenu;
import menuscript.YesNoCancelMenu;
import menuscript.office.ApplicationTab;
import menuscript.office.Branches;
import menuscript.office.CompanyStatistics;
import menuscript.office.GlobalStatistics;
import menuscript.office.HireDrivers;
import menuscript.office.IDirtyListener;
import menuscript.office.IWarningListener;
import menuscript.office.ManageDrivers;
import menuscript.office.ManageFleet;
import rnrcore.CoreTime;
import rnrcore.Log;
import rnrcore.eng;
import rnrcore.loc;
import rnrcore.vectorJ;

public class OfficeMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_MONEY = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_TURN = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_DUPT = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String XML_WARNING_APPLY = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_CARS = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_HAS_DEPT = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String CONTROLS_MAIN = "OFFICE";
    private static final String CONTROLS_WARNING_MONEY = "MESSAGE - Not enough money";
    private static final String CONTROLS_WARNING_TURNS = "MESSAGE - Not enough turns";
    private static final String CONTROLS_WARNING_DUBT = "Menu Debt";
    private static final String CONTROLS_WARNING_APPLY = "MESSAGE - Apply changes";
    private static final String CONTROLS_WARNING_CARS = "MESSAGE - Not enough vehicles";
    private static final String CONTROLS_WARNING_HASDEPT = "Message Debt";
    public static final int ERROR_CODE_NON = 0;
    public static final int ERROR_CODE_NOTENOUGHCARS = 1;
    public static final int ERROR_CODE_NOTENOUGHMONEY = 2;
    private static final String[] TAB_NAMES = new String[]{"Tab2 - Manage Fleet", "Tab2 - Manage Drivers", "Tab2 - Hire/Fire Driver", "Tab1 - Manage Branches", "Tab1 - Global statistics", "Tab1 - Company statistics"};
    private static final String TAB_METHOD = "onTab";
    public static final int TAB_MANAGEFLEET = 0;
    public static final int TAB_MANAGEDRIVERS = 1;
    public static final int TAB_HIREDRIVER = 2;
    public static final int TAB_BRANCHES = 3;
    public static final int TAB_GLOBAL_STATICSTICS = 4;
    public static final int TAB_COMPNANY_STATICSTICS = 5;
    public static final int INVALIDE_TAB = -1;
    private static final String[] ACTION_BUTTONS = new String[]{"BUTTON - EXIT", "BUTTON - DISCARD ALL", "BUTTON - APPLY ALL"};
    private static final String[] ACTION_FUNKS = new String[]{"onExit", "onDiscard", "onApply"};
    private static final String[] TOTALS_FIELDS = new String[]{"MY BALANCE - VALUE", "MY RANK - VALUE"};
    private static final int BALLANCE_TOTAL = 0;
    private static final int RANK_TOTAL = 1;
    private static final String MACRO_VALUE = "VALUE";
    static final int ANIMATION_MANAGEFLEET = 1;
    static final int ANIMATION_HIREDRIVERS = 2;
    private static boolean isMenuCreated = false;
    private static boolean isMenuAskedToCreate = false;
    private static boolean isShowMenuImmediately = false;
    MenuControls allmenu = null;
    private PoPUpMenu warning_has_dept = null;
    private PoPUpMenu warning_not_enough_money = null;
    private PoPUpMenu warning_not_enough_turns = null;
    private PoPUpMenu warning_not_enough_cars = null;
    private PoPUpMenu warning_unsettled_dubt = null;
    private YesNoCancelMenu warning_apply_changes = null;
    ArrayList<ApplicationTab> tabs = new ArrayList();
    private int current_tab = 0;
    private long _menu = 0L;
    private long[] totals = new long[TOTALS_FIELDS.length];
    private String[] totals_str = new String[TOTALS_FIELDS.length];
    private ApplyResult APPLYRESULT = null;
    private DiscardResult DISCARDRESULT = null;
    private ExitResult EXITRESULT = null;
    private CheckState CHECKSTATE = new CheckState();
    private String not_enought_turns_text = "";
    private String unsettled_dubt_text = "";
    private PoPUpMenu show_help = null;
    private long help_text_title = 0L;
    private long help_text = 0L;
    private long help_text_scroller = 0L;
    TextScroller scroller = null;
    String info_text = null;
    Common common;
    String tool_tip_text = null;
    public vectorJ out_position;
    public String inVersion;
    public String inOfficeName;

    public void restartMenu(long _menu) {
    }

    public static void showMenu() {
        if (isMenuCreated) {
            menues.showMenu(8000);
        } else if (isMenuAskedToCreate) {
            isShowMenuImmediately = true;
        }
    }

    private OfficeMenu() {
    }

    public void InitMenu(long _menu) {
        int i;
        JavaEvents.SendEvent(71, 17, this);
        this.common = new Common(_menu);
        isMenuAskedToCreate = false;
        this._menu = _menu;
        this.allmenu = new MenuControls(_menu, "..\\data\\config\\menu\\menu_office.xml", CONTROLS_MAIN);
        ShowWarning sw = new ShowWarning();
        UpdateTotals uTotals = new UpdateTotals();
        for (i = 0; i < TAB_NAMES.length; ++i) {
            long tab = menues.FindFieldInMenu(_menu, TAB_NAMES[i]);
            xmlcontrols.MENUCustomStuff obj_tab = (xmlcontrols.MENUCustomStuff)menues.ConvertMenuFields(tab);
            obj_tab.userid = i;
            menues.SetScriptOnControl(_menu, obj_tab, this, TAB_METHOD, 10L);
            switch (i) {
                case 1: {
                    this.tabs.add(new ManageDrivers(_menu, this));
                    break;
                }
                case 0: {
                    this.tabs.add(new ManageFleet(_menu, this));
                    break;
                }
                case 2: {
                    this.tabs.add(new HireDrivers(_menu, this));
                    break;
                }
                case 3: {
                    this.tabs.add(new Branches(_menu, this));
                    break;
                }
                case 5: {
                    this.tabs.add(new CompanyStatistics(_menu, this));
                    break;
                }
                case 4: {
                    this.tabs.add(new GlobalStatistics(_menu, this));
                }
            }
            if (!this.tabs.isEmpty()) {
                this.tabs.get(i).addListener(sw);
                this.tabs.get(i).addListenerUpdate(uTotals);
                this.tabs.get(i).addListenerDirty(new DirtyListener(i));
            }
            menues.UpdateMenuField(obj_tab);
        }
        for (i = 0; i < ACTION_BUTTONS.length; ++i) {
            long button = menues.FindFieldInMenu(_menu, ACTION_BUTTONS[i]);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, ACTION_FUNKS[i], 4L);
        }
        for (i = 0; i < TOTALS_FIELDS.length; ++i) {
            this.totals[i] = menues.FindFieldInMenu(_menu, TOTALS_FIELDS[i]);
            this.totals_str[i] = menues.GetFieldText(this.totals[i]);
        }
        this.warning_has_dept = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_unsettled_debt.xml", CONTROLS_WARNING_HASDEPT);
        this.warning_not_enough_money = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", CONTROLS_WARNING_MONEY, CONTROLS_WARNING_MONEY);
        this.warning_not_enough_turns = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", CONTROLS_WARNING_TURNS, CONTROLS_WARNING_TURNS);
        this.warning_apply_changes = new YesNoCancelMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", CONTROLS_WARNING_APPLY, CONTROLS_WARNING_APPLY);
        this.warning_not_enough_cars = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", CONTROLS_WARNING_CARS, CONTROLS_WARNING_CARS);
        this.warning_unsettled_dubt = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_unsettled_debt.xml", CONTROLS_WARNING_DUBT, CONTROLS_WARNING_DUBT, false);
        this.show_help = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", "TOOLTIP - FREIGHT MANAGER", "TOOLTIP - FREIGHT MANAGER");
        this.help_text_title = this.show_help.getField("CALL OFFICE HELP - TITLE");
        this.help_text = this.show_help.getField("CALL OFFICE HELP - TEXT");
        this.help_text_scroller = this.show_help.getField("CALL OFFICE HELP - Tableranger");
        this.warning_not_enough_turns.addListener(new NotEnough_Listener());
        this.warning_not_enough_money.addListener(new NotEnough_Listener());
        this.warning_not_enough_cars.addListener(new NotEnough_Listener());
        this.warning_unsettled_dubt.addListener(new MenuDeptListener());
        this.warning_apply_changes.addListener(new YesNoCancelListener());
        this.not_enought_turns_text = menues.GetFieldText(this.warning_not_enough_turns.getField("TEXT1"));
        this.unsettled_dubt_text = menues.GetFieldText(this.warning_has_dept.getField("Text 1"));
    }

    public void AfterInitMenu(long _menu) {
        isMenuCreated = true;
        BalanceUpdater.AddBalanceControl(this.totals[0]);
        for (ApplicationTab tab : this.tabs) {
            tab.afterInit();
        }
        this.warning_has_dept.afterInit();
        this.warning_not_enough_turns.afterInit();
        this.warning_not_enough_money.afterInit();
        this.warning_not_enough_cars.afterInit();
        this.warning_unsettled_dubt.afterInit();
        this.warning_apply_changes.afterInit();
        this.show_help.afterInit();
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.SetMenagPOLOSY(_menu, true);
        menues.SetFieldState(menues.FindFieldInMenu(_menu, TAB_NAMES[5]), 1);
        this.makeEnterOfficeMenu();
        if (eng.noNative) {
            menues.showMenu(8000);
        }
        if (isShowMenuImmediately) {
            isShowMenuImmediately = false;
            menues.showMenu(8000);
        }
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
    }

    public void exitMenu(long _menu) {
        isMenuAskedToCreate = false;
        isMenuCreated = false;
        BalanceUpdater.RemoveBalanceControl(this.totals[0]);
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
        for (ApplicationTab tab : this.tabs) {
            tab.deinit();
        }
    }

    public void ShowTabHelp(int tab_id) {
        MENUText_field text;
        long info;
        String help_text_text;
        String tite_loc = null;
        String help_loc = null;
        if (tab_id == 0) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab2 - Manage Fleet";
            help_loc = "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Manage Fleet";
        } else if (tab_id == 1) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab2 - Manage Drivers";
            help_loc = "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Manage Drivers";
        } else if (tab_id == 2) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab2 - Hire/Fire Driver";
            help_loc = "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Hire/Fire Driver";
        } else if (tab_id == 3) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab1 - Manage Branches";
            help_loc = "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Manage Branches";
        } else if (tab_id == 4) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab1 - Global statistics";
            help_loc = "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Global statistics";
        } else if (tab_id == 5) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab1 - Company statistics";
            help_loc = "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Company statistics";
        } else {
            return;
        }
        if (this.help_text_title != 0L) {
            menues.SetFieldText(this.help_text_title, loc.getMENUString(tite_loc));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.help_text_title));
        }
        if ((help_text_text = loc.getMENUString(help_loc)) != null && this.help_text != 0L && this.help_text_scroller != 0L) {
            MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(this.help_text_scroller);
            MENUText_field text2 = (MENUText_field)menues.ConvertMenuFields(this.help_text);
            if (ranger != null && text2 != null) {
                text2.text = help_text_text;
                menues.UpdateField(text2);
                int texh = menues.GetTextLineHeight(text2.nativePointer);
                int startbase = menues.GetBaseLine(text2.nativePointer);
                int linescreen = Converts.HeightToLines(text2.leny, startbase, texh);
                int linecounter = Converts.HeightToLines(menues.GetTextHeight(text2.nativePointer, help_text_text), startbase, texh) + 3;
                if (this.scroller != null) {
                    this.scroller.Deinit();
                }
                this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, true, "CALL OFFICE HELP - TEXT");
                this.scroller.AddTextControl(text2);
            }
        }
        if (!eng.noNative && (info = this.show_help.getField("THE CITY OFFICE TITLE - TEXT")) != 0L && (text = (MENUText_field)menues.ConvertMenuFields(info)) != null && text.text != null) {
            if (this.info_text == null) {
                this.info_text = text.text;
            }
            JavaEvents.SendEvent(71, 2, this);
            KeyPair[] pairs = new KeyPair[]{new KeyPair("RNR_VERSION", this.inVersion), new KeyPair("CITY", this.inOfficeName)};
            String semi = MacroKit.Parse(this.info_text, pairs);
            CoreTime time = new CoreTime();
            text.text = Converts.ConvertDateAbsolute(semi, time.gMonth(), time.gDate(), time.gYear(), time.gHour(), time.gMinute());
            menues.UpdateField(text);
        }
        this.show_help.show();
    }

    public String getMenuId() {
        return "officeMENU";
    }

    public void onTab(long _menu, xmlcontrols.MENUCustomStuff button) {
        if (-1 == button.userid || this.tabs.size() - 1 < button.userid) {
            return;
        }
        this.current_tab = button.userid;
        this.tabs.get(button.userid).update();
    }

    public void onExit(long _menu, MENUsimplebutton_field button) {
        this.pressExit();
    }

    public void OnExit(long _menu, SMenu button) {
        this.pressExit();
    }

    public void onDiscard(long _menu, MENUsimplebutton_field button) {
        this.pressDiscardAll();
    }

    public void onApply(long _menu, MENUsimplebutton_field button) {
        this.pressApplyAll();
    }

    public static long create() {
        isMenuAskedToCreate = true;
        return menues.CreateOfficeMenu(new OfficeMenu(), 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    private void updateAllTabs() {
        for (ApplicationTab t : this.tabs) {
            t.setDirty();
        }
        this.tabs.get(this.current_tab).update();
    }

    private void drawUnsettledDept() {
        long field = this.warning_has_dept.getField("Text 1");
        KeyPair[] pairs = new KeyPair[]{new KeyPair("MONEY", "" + this.getDept())};
        String res_text = MacroKit.Parse(this.unsettled_dubt_text, pairs);
        menues.SetFieldText(field, res_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(field));
        this.warning_has_dept.show();
    }

    private void drawNotEnoughTurnOver(int warehouses) {
        long field = this.warning_not_enough_turns.getField("TEXT1");
        KeyPair[] pairs = new KeyPair[]{new KeyPair(MACRO_VALUE, "" + warehouses)};
        String res_text = MacroKit.Parse(this.not_enought_turns_text, pairs);
        menues.SetFieldText(field, res_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(field));
        this.warning_not_enough_turns.show();
    }

    public void ESCPressed(int value) {
        this.pressExit();
    }

    private void pressExit() {
        this.makeExitOfficeMenu();
        if (this.hasChanges()) {
            this.warning_apply_changes.show();
            return;
        }
        if (this.hasDept()) {
            this.warning_unsettled_dubt.show();
            return;
        }
        if (!this.hasVehicle()) {
            this.warning_not_enough_cars.show();
            return;
        }
        this.makeFinalExit();
    }

    private void pressApplyAll() {
        this.makeApplyAll();
        if (this.notEnoughCars()) {
            this.warning_not_enough_cars.show();
        } else if (this.notEnoughMoney()) {
            this.warning_not_enough_money.show();
        } else if (this.notEnoughTurnOver()) {
            this.drawNotEnoughTurnOver(this.APPLYRESULT.not_enough_turnover_num_warehouses);
        }
        this.updateAllTabs();
    }

    private void pressDiscardAll() {
        this.makeDiscardAll();
        this.updateAllTabs();
    }

    private void errorr(String text) {
        Log.menu("Office menu have problem. " + text);
    }

    private boolean hasChanges() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.have_changes;
        }
        this.errorr("hasChanges. EXITRESULT is null");
        return false;
    }

    private boolean hasDept() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.have_dept;
        }
        return this.CHECKSTATE.have_dept;
    }

    private int getDept() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.dept_value;
        }
        return this.CHECKSTATE.dept_value;
    }

    private boolean hasVehicle() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.have_vehicle;
        }
        return this.CHECKSTATE.have_vehicle;
    }

    private boolean notEnoughCars() {
        if (this.APPLYRESULT != null) {
            return this.APPLYRESULT.not_enough_cars;
        }
        this.errorr("notEnoughCars. APPLYRESULT is null");
        return true;
    }

    private boolean notEnoughMoney() {
        if (this.APPLYRESULT != null) {
            return this.APPLYRESULT.not_enough_money;
        }
        this.errorr("notEnoughMoney. APPLYRESULT is null");
        return false;
    }

    private boolean notEnoughTurnOver() {
        if (this.APPLYRESULT != null) {
            return this.APPLYRESULT.not_enough_turnover;
        }
        this.errorr("notEnoughMoney. APPLYRESULT is null");
        return false;
    }

    private void makeApplyAll() {
        this.APPLYRESULT = null;
        this.DISCARDRESULT = null;
        this.EXITRESULT = null;
        this.APPLYRESULT = new ApplyResult();
        JavaEvents.SendEvent(44, 1, this.APPLYRESULT);
    }

    private void makeDiscardAll() {
        this.APPLYRESULT = null;
        this.DISCARDRESULT = null;
        this.EXITRESULT = null;
        this.DISCARDRESULT = new DiscardResult();
        JavaEvents.SendEvent(44, 2, this.DISCARDRESULT);
    }

    private void makeEnterOfficeMenu() {
        if (eng.noNative) {
            return;
        }
        JavaEvents.SendEvent(44, 0, this.CHECKSTATE);
        if (this.hasDept()) {
            this.drawUnsettledDept();
        }
    }

    private void makeExitOfficeMenu() {
        this.APPLYRESULT = null;
        this.DISCARDRESULT = null;
        this.EXITRESULT = null;
        this.EXITRESULT = new ExitResult();
        JavaEvents.SendEvent(44, 3, this.EXITRESULT);
    }

    private void makeFinalExit() {
        ListenerManager.TriggerEvent(105);
        JavaEvents.SendEvent(44, 4, this);
        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    private void makeAutoDebtBallance() {
        JavaEvents.SendEvent(45, 0, this);
    }

    class DirtyListener
    implements IDirtyListener {
        private int type;

        DirtyListener(int type) {
            this.type = type;
        }

        public void settedDirty(ApplicationTab tab) {
            int i = 0;
            for (ApplicationTab singletab : OfficeMenu.this.tabs) {
                if (this.type == i++) continue;
                singletab.setDirty();
            }
        }
    }

    class MenuDeptListener
    implements IPoPUpMenuListener {
        MenuDeptListener() {
        }

        public void onAgreeclose() {
            OfficeMenu.this.makeAutoDebtBallance();
            OfficeMenu.this.makeFinalExit();
        }

        public void onClose() {
            OfficeMenu.this.updateAllTabs();
        }

        public void onOpen() {
        }

        public void onCancel() {
        }
    }

    class NotEnough_Listener
    implements IPoPUpMenuListener {
        NotEnough_Listener() {
        }

        public void onAgreeclose() {
            this.onClose();
        }

        public void onClose() {
        }

        public void onOpen() {
        }

        public void onCancel() {
        }
    }

    class YesNoCancelListener
    implements IYesNoCancelMenuListener {
        YesNoCancelListener() {
        }

        public void onOpen() {
        }

        public void onCancelClose() {
        }

        public void onClose() {
        }

        public void onNoClose() {
            OfficeMenu.this.makeDiscardAll();
            OfficeMenu.this.pressExit();
        }

        public void onYesClose() {
            OfficeMenu.this.makeApplyAll();
            if (OfficeMenu.this.notEnoughCars()) {
                OfficeMenu.this.warning_not_enough_cars.show();
                OfficeMenu.this.updateAllTabs();
            } else if (OfficeMenu.this.notEnoughMoney()) {
                OfficeMenu.this.warning_not_enough_money.show();
                OfficeMenu.this.updateAllTabs();
            } else if (OfficeMenu.this.notEnoughTurnOver()) {
                OfficeMenu.this.drawNotEnoughTurnOver(((OfficeMenu)OfficeMenu.this).APPLYRESULT.not_enough_turnover_num_warehouses);
                OfficeMenu.this.updateAllTabs();
            } else {
                OfficeMenu.this.updateAllTabs();
                OfficeMenu.this.pressExit();
                return;
            }
        }
    }

    class ShowWarning
    implements IWarningListener {
        ShowWarning() {
        }

        public void makeNotEnoughCars() {
            OfficeMenu.this.warning_not_enough_cars.show();
        }

        public void makeNotEnoughMoney() {
            OfficeMenu.this.warning_not_enough_money.show();
        }

        public void makeNotEnoughTurnOver(int num_bases) {
            OfficeMenu.this.drawNotEnoughTurnOver(num_bases);
        }
    }

    class UpdateTotals
    implements IUpdateListener {
        UpdateTotals() {
        }

        public void onUpdate() {
            if (eng.noNative) {
                return;
            }
            TotalsValues values = new TotalsValues();
            JavaEvents.SendEvent(44, 5, values);
            int rank = values.rank;
            menues.SetFieldText(OfficeMenu.this.totals[1], "" + rank);
            for (long t : OfficeMenu.this.totals) {
                menues.UpdateMenuField(menues.ConvertMenuFields(t));
            }
        }
    }

    static class TotalsValues {
        int rank;

        TotalsValues() {
        }
    }

    static class CheckState {
        boolean have_dept;
        boolean have_vehicle;
        int dept_value;

        CheckState() {
        }
    }

    static class ExitResult {
        boolean have_changes;
        boolean have_dept;
        boolean have_vehicle;
        int dept_value;

        ExitResult() {
        }
    }

    static class DiscardResult {
        DiscardResult() {
        }
    }

    static class ApplyResult {
        boolean not_enough_cars = false;
        boolean not_enough_money = false;
        boolean not_enough_turnover = false;
        int not_enough_turnover_num_warehouses = 0;

        ApplyResult() {
        }
    }
}

