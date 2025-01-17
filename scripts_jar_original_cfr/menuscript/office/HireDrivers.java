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
import menuscript.PoPUpMenu;
import menuscript.TooltipButton;
import menuscript.office.ApplicationTab;
import menuscript.office.HireFireDriversManager;
import menuscript.office.IChoosedata;
import menuscript.office.OfficeMenu;
import menuscript.office.PopUpSearch;
import menuscript.table.ICompareLines;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrconfig.IconMappings;
import rnrcore.Log;
import rnrcore.loc;

public class HireDrivers
extends ApplicationTab {
    private static final String TAB_NAME = "HIRE DRIVERS";
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TABLE_MYTEAM = "HFD My Drivers - TABLEGROUP - 9 38";
    private static final String RANGER_MYTEAM = "Tableranger - HFD My Drivers";
    private static final String TABLE_MYTEAM_LINE = "Tablegroup - ELEMENTS - MyTeam";
    private static final String[] TABLE_MYTEAM_ELEMENTS = new String[]{"BUTTON - HFD My Drivers - Name VALUE", "BUTTON - HFD My Drivers - Rank VALUE", "BUTTON - HFD My Drivers - Wage VALUE", "BUTTON - HFD My Drivers - Name VALUE GRAY", "BUTTON - HFD My Drivers - Rank VALUE GRAY", "BUTTON - HFD My Drivers - Wage VALUE GRAY"};
    private static final int MYTEAM_NAME_TABLE = 0;
    private static final int MYTEAM_RANK = 1;
    private static final int MYTEAM_WAGE = 2;
    private static final int MYTEAM_NAME_TABLE_GRAY = 3;
    private static final int MYTEAM_RANK_GRAY = 4;
    private static final int MYTEAM_WAGE_GRAY = 5;
    private static final String[] MYTEAM_FULL_INFO = new String[]{"HFD My Drivers - DriverFile NAME 3 Rows", "HFD My Drivers - FileVALUE Loyalty", "HFD My Drivers - FileVALUE Return", "HFD My Drivers - FileVALUE Wins/Missions", "HFD My Drivers - FileVALUE Forfeit", "HFD My Drivers - FileVALUE Maintenance", "HFD My Drivers - FileVALUE Gas", "HFD My Drivers - FileVALUE Vehicle", "HFD My Drivers - FileVALUE Vehicle MARKED"};
    private static final int MYTEAM_NAME_FULLINFO = 0;
    private static final int MYTEAM_LOYALITY = 1;
    private static final int MYTEAM_ROI = 2;
    private static final int MYTEAM_WINS = 3;
    private static final int MYTEAM_FORFEIT = 4;
    private static final int MYTEAM_MAINTENANCE = 5;
    private static final int MYTEAM_GAS = 6;
    private static final int MYTEAM_VEHICLE = 7;
    private static final int MYTEAM_VEHICLE_MARKED = 8;
    private static final String MYTEAM_PHOTO = "HFD My Drivers - DriverFile PHOTO";
    private static final String[] MYTEAM_SORT = new String[]{"BUTTON - HFD My Drivers - Name", "BUTTON - HFD My Drivers - Rank", "BUTTON - HFD My Drivers - Wage", "BUTTON - My Drivers - Loyalty", "BUTTON - My Drivers - Return", "BUTTON - My Drivers - Wins/Missions", "BUTTON - My Drivers - Forfeit", "BUTTON - My Drivers - Maintenance", "BUTTON - My Drivers - Gas", "BUTTON - My Drivers - Vehicle"};
    private static final int SORT_MYTEAM_NAME = 0;
    private static final int SORT_MYTEAM_RANK = 1;
    private static final int SORT_MYTEAM_WAGE = 2;
    private static final int SORT_MYTEAM_LOYALITY = 3;
    private static final int SORT_MYTEAM_ROI = 4;
    private static final int SORT_MYTEAM_WINS = 5;
    private static final int SORT_MYTEAM_FORFEIT = 6;
    private static final int SORT_MYTEAM_MAINTENANCE = 7;
    private static final int SORT_MYTEAM_GAS = 8;
    private static final int SORT_MYTEAM_VEHICLE = 9;
    private static final String TABLE_HIRELINGS = "HFD Recruits - TABLEGROUP - 10 38";
    private static final String RANGER_HIRELINGS = "Tableranger - HFD Recruits";
    private static final String TABLE_HIRELINGS_LINE = "Tablegroup - ELEMENTS - Hirelings";
    private static final String[] TABLE_HIRELINGS_ELEMENTS = new String[]{"BUTTON - HFD Recruits - Name VALUE", "BUTTON - HFD Recruits - Recruiter VALUE", "BUTTON - HFD Recruits - Rank VALUE", "BUTTON - HFD Recruits - Wage VALUE", "BUTTON - HFD Recruits - Name VALUE MARKED", "BUTTON - HFD Recruits - Recruiter VALUE MARKED", "BUTTON - HFD Recruits - Rank VALUE MARKED", "BUTTON - HFD Recruits - Wage VALUE MARKED", "BUTTON - HFD Recruits - Name VALUE GRAY", "BUTTON - HFD Recruits - Recruiter VALUE GRAY", "BUTTON - HFD Recruits - Rank VALUE GRAY", "BUTTON - HFD Recruits - Wage VALUE GRAY"};
    private static final int HIRELINGS_NAME = 0;
    private static final int HIRELINGS_RECRUITER = 1;
    private static final int HIRELINGS_RANK = 2;
    private static final int HIRELINGS_WAGE = 3;
    private static final int HIRELINGS_NAME_GRAY = 4;
    private static final int HIRELINGS_RECRUITER_GRAY = 5;
    private static final int HIRELINGS_RANK_GRAY = 6;
    private static final int HIRELINGS_WAGE_GRAY = 7;
    private static final int HIRELINGS_NAME_GRAY2 = 8;
    private static final int HIRELINGS_RECRUITER_GRAY2 = 9;
    private static final int HIRELINGS_RANK_GRAY2 = 10;
    private static final int HIRELINGS_WAGE_GRAY2 = 11;
    private static final String[] HIRELINGS_FULL_INFO = new String[]{"HFD Recruits - Resume NAME 3 Rows", "HFD Recruits - FileVALUE Age", "HFD Recruits - FileVALUE Gender", "HFD Recruits - FileVALUE Tickets", "HFD Recruits - FileVALUE Accidents", "HFD Recruits - FileVALUE Has Felony", "HFD Recruits - FileVALUE Experience"};
    private static final int HIRELINGS_FULLNAME = 0;
    private static final int HIRELINGS_AGE = 1;
    private static final int HIRELINGS_GENDER = 2;
    private static final int HIRELINGS_TICKETS = 3;
    private static final int HIRELINGS_ACCIDENTS = 4;
    private static final int HIRELINGS_FELONY = 5;
    private static final int HIRELINGS_EXPERIENCE = 6;
    private static final String HIRELINGS_PHOTO = "HFD Recruits - Resume PHOTO";
    private static final String[] HIRELINGS_FULL_INFO_HASFELONY = new String[]{loc.getMENUString("common\\Yes"), loc.getMENUString("common\\No")};
    private static final int HIRELINGS_FULL_INFO_HASFELONY_YES = 0;
    private static final int HIRELINGS_FULL_INFO_HASFELONY_NO = 1;
    private static final String[] HIRELINGS_SORT = new String[]{"BUTTON - HFD Recruits - Name", "BUTTON - HFD Recruits - Recruiter", "BUTTON - HFD Recruits - Rank", "BUTTON - HFD Recruits - Wage", "BUTTON - HFD Recruits - Gender", "BUTTON - HFD Recruits - Age", "BUTTON - HFD Recruits - Tickets", "BUTTON - HFD Recruits - Accidents", "BUTTON - HFD Recruits - Has Felony", "BUTTON - HFD Recruits - Experience"};
    private static final int SORT_HIRELINGS_NAME = 0;
    private static final int SORT_HIRELINGS_RECUITER = 1;
    private static final int SORT_HIRELINGS_RANK = 2;
    private static final int SORT_HIRELINGS_WAGE = 3;
    private static final int SORT_HIRELINGS_GENDER = 4;
    private static final int SORT_HIRELINGS_AGE = 5;
    private static final int SORT_HIRELINGS_TICKETS = 6;
    private static final int SORT_HIRELINGS_ACCIDENTS = 7;
    private static final int SORT_HIRELINGS_FELONY = 8;
    private static final int SORT_HIRELINGS_EXPERIENCE = 9;
    private static final String[] ACTION_BUTTONS = new String[]{"BUTTON - HFD - HIRE", "BUTTON - HFD - FIRE"};
    private static final String[] ACTION_METHODS = new String[]{"onHire", "onFire"};
    private static final int SELECT_HIRE = 0;
    private static final int SELECT_FIRE = 1;
    private static String SEARCH_MENU_GROUP = "Tablegroup - ELEMENTS - HFD Filer Menu";
    private static final String[] SEARCH_BUTTONS = new String[]{"BUTTON PopUP - HFD Resume Search - Gender", "BUTTON PopUP - HFD Resume Search - Age", "BUTTON PopUP - HFD Resume Search - Tickets", "BUTTON PopUP - HFD Resume Search - Accidents", "BUTTON PopUP - HFD Resume Search - Has Felony", "BUTTON PopUP - HFD Resume Search - Experience"};
    private static final String[] SEARCH_TEXTS = new String[]{"HFD Resume Search - Gender - VALUE", "HFD Resume Search - Age - VALUE", "HFD Resume Search - Tickets - VALUE", "HFD Resume Search - Accidents - VALUE", "HFD Resume Search - Has Felony - VALUE", "HFD Resume Search - Experience - VALUE"};
    private static final int SEARCH_GENDER = 0;
    private static final int SEARCH_AGE = 1;
    private static final int SEARCH_TICKETS = 2;
    private static final int SEARCH_ACCIDENTS = 3;
    private static final int SEARCH_FELONY = 4;
    private static final int SEARCH_EXPERIENCE = 5;
    private static final String[] SUMMARY_TEXTS = new String[]{"Vacant VALUE HIRE", "To Fire VALUE HIRE", "To Hire VALUE HIRE", "Advance VALUE HIRE", "Commission VALUE HIRE", "Total VALUE HIRE"};
    private static final int SUMMARY_VACANT = 0;
    private static final int SUMMARY_FIRE = 1;
    private static final int SUMMARY_HIRE = 2;
    private static final int SUMMARY_ADVENCED = 3;
    private static final int SUMMARY_COMMISIONS = 4;
    private static final int SUMMARY_TOTAL = 5;
    private static final String MACRO_MONEY = "MONEY";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_NAME = "NAME";
    private static final String MACRO_AGE = "AGE";
    private static final String MACRO_WINS = "WINS";
    private static final String MACRO_SIGN = "SIGN";
    private static final String[] SUMMARY_MACRO = new String[]{"VALUE", "VALUE", "VALUE", "MONEY", "MONEY", "MONEY"};
    private static final String[] SUMMARY_MACRO2 = new String[]{"", "", "", "SIGN", "SIGN", "SIGN"};
    private static final int[] SUMMARY_MACRO_NUM = new int[]{1, 1, 1, 2, 2, 2};
    private static final String TOOLTIPBUTTON_XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TOOLTIPBUTTON_BUTTON = "HFD My Drivers - Details";
    private static final String TOOLTIPBUTTON_MENUGROUP = "TOOLTIP - HFD - Detailed VehicleName";
    private static final String[] TOOLTIPBUTTON_TEXT = new String[]{"HFD DriverFile - Vehicle VALUE", "HFD DriverFile - Vehicle VALUE MARKED"};
    private static final String XML_WARNING_RANK = "..\\data\\config\\menu\\menu_office.xml";
    private static final String GROUP_WARNING_RANK = "MESSAGE - Not sufficient rank";
    private MyTeam myTeamTable = null;
    private Hirelings hirelingsTable = null;
    private SearchFilter hirelingFilter = null;
    private String[] initialSummaryTexts = new String[SUMMARY_TEXTS.length];
    private long[] summaryTexts = new long[SUMMARY_TEXTS.length];
    private TooltipButton tooltipbutton = null;
    private PoPUpMenu warning_rank = null;
    TermometrClass[] termometrs = null;
    private static final String[] TERMOMETRS_NAMES = new String[]{"HFD My Drivers - FileVALUE Loyalty - INDICATOR", "HFD My Drivers - FileVALUE Return - INDICATOR", "HFD My Drivers - FileVALUE Wins/Missions - INDICATOR", "HFD My Drivers - FileVALUE Forfeit - INDICATOR", "HFD My Drivers - FileVALUE Maintenance - INDICATOR", "HFD My Drivers - FileVALUE Gas - INDICATOR"};
    private static final int TERMOMETR_Loyalty = 0;
    private static final int TERMOMETR_Return = 1;
    private static final int TERMOMETR_Wins_Missions = 2;
    private static final int TERMOMETR_Forfeit = 3;
    private static final int TERMOMETR_Maintenance = 4;
    private static final int TERMOMETR_Gas = 5;
    long _menu = 0L;
    sort myteam_table_sort = new sort(1, true);
    sort hirelings_table_sort = new sort(3, true);
    String tool_tip_text = null;

    public HireDrivers(long _menu, OfficeMenu parent) {
        super(_menu, TAB_NAME, parent);
        this._menu = _menu;
        this.init(_menu);
    }

    private void init(long _menu) {
        this.hirelingFilter = new SearchFilter(_menu);
        this.myTeamTable = new MyTeam(_menu);
        this.hirelingsTable = new Hirelings(_menu);
        for (int i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summaryTexts[i] = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
            this.initialSummaryTexts[i] = menues.GetFieldText(this.summaryTexts[i]);
        }
        this.tooltipbutton = new TooltipButton(_menu, TOOLTIPBUTTON_BUTTON, "..\\data\\config\\menu\\menu_office.xml", TOOLTIPBUTTON_MENUGROUP, TOOLTIPBUTTON_TEXT);
        this.warning_rank = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", GROUP_WARNING_RANK, GROUP_WARNING_RANK);
        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - HIRE/FIRE DRIVER");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    public void afterInit() {
        this.termometrs = new TermometrClass[TERMOMETRS_NAMES.length];
        for (int i = 0; i < TERMOMETRS_NAMES.length; ++i) {
            this.termometrs[i] = new TermometrClass(this._menu, TERMOMETRS_NAMES[i]);
        }
        this.tooltipbutton.afterInit();
        this.tooltipbutton.Enable(false);
        this.myTeamTable.afterInit();
        this.hirelingsTable.afterInit();
        this.warning_rank.afterInit();
        this.update();
    }

    public boolean update() {
        if (!super.update()) {
            return false;
        }
        this.myTeamTable.updateTable();
        this.hirelingsTable.updateTable();
        this.refresh_summary();
        this.makeUpdate();
        return true;
    }

    public void deinit() {
        this.hirelingFilter.deinit();
        this.myTeamTable.table.deinit();
        this.hirelingsTable.table.deinit();
    }

    private void refresh_summary() {
        HireFireDriversManager.Summary summary = HireFireDriversManager.getHireFireDriversManager().GetSummary();
        for (int i = 0; i < this.summaryTexts.length; ++i) {
            KeyPair[] keys = new KeyPair[SUMMARY_MACRO_NUM[i]];
            switch (i) {
                case 0: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.vacant);
                    break;
                }
                case 2: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.to_hire);
                    break;
                }
                case 1: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.to_fire);
                    break;
                }
                case 3: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.advance));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.advance) >= 0 ? "" : "-");
                    break;
                }
                case 4: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.commission));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.commission) >= 0 ? "" : "-");
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
        int err = HireFireDriversManager.getHireFireDriversManager().Apply();
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
        HireFireDriversManager.getHireFireDriversManager().Discard();
        this.update();
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(2);
        }
    }

    class SearchFilter
    implements IChoosedata {
        private final String SEARCH_METH = "onSearch";
        private final String ANIMATE_METH = "onAnimate";
        private static final int _IDanimateClose = 2;
        private PopUpSearch search = null;
        private long[] texts = null;
        private int[] shifts = null;
        private boolean pend_close = false;
        private int filter_current = 0;
        private int filter_choose = 0;
        private String[] lastFilter = null;
        private HireFireDriversManager.Filter filter = null;

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
            menues.SetScriptObjectAnimation(0L, 2L, this, "onAnimate");
            this.texts = new long[SEARCH_TEXTS.length];
            for (i = 0; i < SEARCH_TEXTS.length; ++i) {
                this.texts[i] = menues.FindFieldInMenu(_menu, SEARCH_TEXTS[i]);
                String[] textdata = this.getFilterValues(i);
                if (textdata.length <= 0) continue;
                menues.SetFieldText(this.texts[i], textdata[textdata.length - 1]);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[i]));
            }
            this.filter = new HireFireDriversManager.Filter();
            this.filter.fileds = new int[6];
        }

        public void deinit() {
            menues.StopScriptAnimation(2L);
            this.search.removeListener(this);
        }

        public void onSearch(long _menu, MENUsimplebutton_field field) {
            this.search.show(this.shifts[field.userid]);
            String[] data = this.getFilterValues(field.userid);
            this.search.setData(data);
            this.filter_current = field.userid;
        }

        public void onAnimate(long cookie, double time) {
            if (this.pend_close) {
                this.search.hide();
                this.updateFilter();
            }
            this.pend_close = false;
        }

        public void selectData(int data) {
            this.pend_close = true;
            this.filter_choose = data;
        }

        void updateFilter() {
            int ch = this.filter_choose;
            Vector<HireFireDriversManager.FilterField> fields = HireFireDriversManager.getHireFireDriversManager().GetShortFilterFields(this.filter_current);
            int res_choose = 0;
            for (HireFireDriversManager.FilterField f : fields) {
                if ((ch -= f.show_me ? 1 : 0) < 0) break;
                ++res_choose;
            }
            this.filter.fileds[this.filter_current] = res_choose;
            HireFireDriversManager.getHireFireDriversManager().SetFilter(this.filter);
            HireDrivers.this.hirelingsTable.updateTable();
            menues.SetFieldText(this.texts[this.filter_current], this.lastFilter[this.filter_choose]);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
        }

        String[] getFilterValues(int taken) {
            int flt = 0;
            switch (taken) {
                case 1: {
                    flt = 1;
                    break;
                }
                case 3: {
                    flt = 3;
                    break;
                }
                case 5: {
                    flt = 5;
                    break;
                }
                case 4: {
                    flt = 4;
                    break;
                }
                case 0: {
                    flt = 0;
                    break;
                }
                case 2: {
                    flt = 2;
                }
            }
            Vector<HireFireDriversManager.FilterField> fields = HireFireDriversManager.getHireFireDriversManager().GetFilterFields(flt);
            int count = 0;
            for (HireFireDriversManager.FilterField f : fields) {
                count += f.show_me ? 1 : 0;
            }
            String[] data = new String[count];
            count = 0;
            for (int i = 0; i < fields.size(); ++i) {
                if (!fields.get((int)i).show_me) continue;
                data[count++] = fields.get((int)i).name;
            }
            this.lastFilter = data;
            return data;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class Hirelings
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        Table table;
        private Hirelingsline selected = null;
        private table_data_hirelingfleet TABLE_DATA = new table_data_hirelingfleet();
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_initials = null;
        private String hireling_wage;
        private long man_photo;

        Hirelings(long _menu) {
            int i;
            this.table = new Table(_menu, HireDrivers.TABLE_HIRELINGS, HireDrivers.RANGER_HIRELINGS);
            this.table.setSelectionMode(2);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml", HireDrivers.TABLE_HIRELINGS_LINE, TABLE_HIRELINGS_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : TABLE_HIRELINGS_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            this.fullinfotexts = new long[HIRELINGS_FULL_INFO.length];
            this.fullinfotexts_initials = new String[HIRELINGS_FULL_INFO.length];
            for (i = 0; i < HIRELINGS_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, HIRELINGS_FULL_INFO[i]);
                this.fullinfotexts_initials[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }
            i = 0;
            while (i < HIRELINGS_SORT.length) {
                long field = menues.FindFieldInMenu(_menu, HIRELINGS_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
            long[] stat_myteam_wage = this.table.getLineStatistics_controls(TABLE_HIRELINGS_ELEMENTS[7]);
            long[] stat_hireling_wage = this.table.getLineStatistics_controls(TABLE_HIRELINGS_ELEMENTS[3]);
            if (stat_myteam_wage == null || stat_myteam_wage.length < 1) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + TABLE_HIRELINGS_ELEMENTS[7]);
            }
            if (stat_hireling_wage == null || stat_hireling_wage.length < 1) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + TABLE_HIRELINGS_ELEMENTS[3]);
            }
            this.hireling_wage = menues.GetFieldText(stat_hireling_wage[0]);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ACTION_BUTTONS[0])), this, ACTION_METHODS[0], 4L);
            this.man_photo = menues.FindFieldInMenu(_menu, HireDrivers.HIRELINGS_PHOTO);
        }

        public void afterInit() {
            this.table.afterInit();
        }

        public void onHire(long _menu, MENUsimplebutton_field button) {
            HireDrivers.this.setDirty();
            Vector<HireFireDriversManager.DriverId> res = new Vector<HireFireDriversManager.DriverId>();
            for (Hirelingsline line : this.TABLE_DATA.all_lines) {
                if (!line.selected || !line.wheather_show) continue;
                res.add(line.id);
            }
            int err = 0;
            if (!res.isEmpty()) {
                err = HireFireDriversManager.getHireFireDriversManager().I_Hire(res);
            }
            if (3 == err) {
                HireDrivers.this.warning_rank.show();
            }
            HireDrivers.this.update();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    HireDrivers.this.hirelings_table_sort = new sort(1, HireDrivers.this.hirelings_table_sort.type == 1 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 2: {
                    HireDrivers.this.hirelings_table_sort = new sort(3, HireDrivers.this.hirelings_table_sort.type == 3 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 1: {
                    HireDrivers.this.hirelings_table_sort = new sort(2, HireDrivers.this.hirelings_table_sort.type == 2 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 3: {
                    HireDrivers.this.hirelings_table_sort = new sort(4, HireDrivers.this.hirelings_table_sort.type == 4 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 4: {
                    HireDrivers.this.hirelings_table_sort = new sort(5, HireDrivers.this.hirelings_table_sort.type == 5 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 5: {
                    HireDrivers.this.hirelings_table_sort = new sort(6, HireDrivers.this.hirelings_table_sort.type == 6 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 6: {
                    HireDrivers.this.hirelings_table_sort = new sort(7, HireDrivers.this.hirelings_table_sort.type == 7 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 7: {
                    HireDrivers.this.hirelings_table_sort = new sort(8, HireDrivers.this.hirelings_table_sort.type == 8 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 8: {
                    HireDrivers.this.hirelings_table_sort = new sort(9, HireDrivers.this.hirelings_table_sort.type == 9 ? !HireDrivers.this.hirelings_table_sort.up : true);
                    break;
                }
                case 9: {
                    HireDrivers.this.hirelings_table_sort = new sort(10, HireDrivers.this.hirelings_table_sort.type == 10 ? !HireDrivers.this.hirelings_table_sort.up : true);
                }
            }
            this.updateTable();
            HireDrivers.this.refresh_summary();
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
                        Hirelingsline data = new Hirelingsline();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<Hirelingsline> iter = this.TABLE_DATA.all_lines.iterator();
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
            this.TABLE_DATA.all_lines.clear();
            Vector<HireFireDriversManager.ShotDealerDriverInfo> drids = HireFireDriversManager.getHireFireDriversManager().GetDealerVehiclesList(HireDrivers.this.hirelingFilter.filter, HireDrivers.this.hirelings_table_sort.type, HireDrivers.this.hirelings_table_sort.up);
            for (HireFireDriversManager.ShotDealerDriverInfo inf : drids) {
                Hirelingsline data = new Hirelingsline();
                data.id = inf.id;
                data.driver_name = inf.driver_name;
                data.dealer_name = inf.dealer_name;
                data.rank = inf.rank;
                data.wage = inf.wage;
                data.isGray = inf.justFire;
                data.isGray2 = !inf.bCanHire;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
            }
            this.buildvoidcells();
        }

        public void updateTable() {
            this.selected = null;
            this.reciveTableData();
            this.build_tree_data(new CompareDealerDrivers());
            this.table.refresh_no_select();
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            Hirelingsline line = (Hirelingsline)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int position = this.table.getMarkedPosition(obj.nativePointer);
            switch (position) {
                case 0: {
                    if (line.isGray || line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.driver_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 1: {
                    if (line.isGray || line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.dealer_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 2: {
                    if (line.isGray || line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, "" + line.rank);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 3: {
                    if (line.isGray || line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)line.wage))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.hireling_wage, macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 4: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.driver_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 5: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.dealer_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 6: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, "" + line.rank);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 7: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)line.wage))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.hireling_wage, macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 8: {
                    if (!line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.driver_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 9: {
                    if (!line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.dealer_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 10: {
                    if (!line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, "" + line.rank);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 11: {
                    if (!line.isGray2) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)line.wage))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.hireling_wage, macro));
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
            for (Hirelingsline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }
            Hirelingsline data = (Hirelingsline)table.getItemOnLine((int)line).item;
            data.selected = true;
            this.selected = data;
            this.updateSelectedInfo();
        }

        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            for (Hirelingsline hirelingsline : this.TABLE_DATA.all_lines) {
                hirelingsline.selected = false;
            }
            for (Cmenu_TTI cmenu_TTI : lines) {
                if (cmenu_TTI.item == null) continue;
                Hirelingsline data = (Hirelingsline)cmenu_TTI.item;
                data.selected = true;
            }
            this.selected = (Hirelingsline)table.getSelectedData().item;
            this.updateSelectedInfo();
        }

        private void updateSelectedInfo() {
            if (null == this.selected.id) {
                for (int i = 0; i < this.fullinfotexts.length; ++i) {
                    menues.SetFieldText(this.fullinfotexts[i], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
                }
                return;
            }
            HireFireDriversManager.FullDealerDriverInfo inf = HireFireDriversManager.getHireFireDriversManager().GetDealerVehiclesInfo(this.selected.id);
            boolean bad = this.selected.id.id == 0;
            for (int i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                    case 0: {
                        KeyPair[] keys;
                        if (!bad) {
                            keys = new KeyPair[]{new KeyPair(HireDrivers.MACRO_NAME, inf.driver_name), new KeyPair(HireDrivers.MACRO_AGE, "" + inf.age)};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_initials[i], keys));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 1: {
                        KeyPair[] keys;
                        if (!bad) {
                            keys = new KeyPair[]{new KeyPair(HireDrivers.MACRO_VALUE, "" + inf.age)};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_initials[i], keys));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 2: {
                        if (!bad) {
                            menues.SetFieldText(this.fullinfotexts[i], inf.gender);
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 3: {
                        if (!bad) {
                            menues.SetFieldText(this.fullinfotexts[i], "" + inf.tickets);
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 4: {
                        if (!bad) {
                            menues.SetFieldText(this.fullinfotexts[i], "" + inf.accidents);
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 5: {
                        if (!bad) {
                            menues.SetFieldText(this.fullinfotexts[i], inf.bHasFelony ? HIRELINGS_FULL_INFO_HASFELONY[0] : HIRELINGS_FULL_INFO_HASFELONY[1]);
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 6: {
                        if (!bad) {
                            KeyPair[] exp = new KeyPair[]{new KeyPair(HireDrivers.MACRO_VALUE, "" + inf.experience)};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_initials[i], exp));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                    }
                }
                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }
            IconMappings.remapPersonIcon("" + inf.face_index, this.man_photo);
        }

        class CompareDealerDrivers
        implements ICompareLines {
            CompareDealerDrivers() {
            }

            public boolean equal(Object object1, Object object2) {
                if (object1 == null || object2 == null) {
                    return false;
                }
                Hirelingsline line1 = (Hirelingsline)object1;
                Hirelingsline line2 = (Hirelingsline)object2;
                return line1.id != null && line2.id != null && line1.id.id == line2.id.id;
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class MyTeam
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        Table table;
        Myteamline selected = null;
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_iitials = null;
        private table_data_myteam TABLE_DATA = new table_data_myteam();
        private String myteam_wage;
        private long man_photo = 0L;

        MyTeam(long _menu) {
            int i;
            this.table = new Table(_menu, HireDrivers.TABLE_MYTEAM, HireDrivers.RANGER_MYTEAM);
            this.table.setSelectionMode(2);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml", HireDrivers.TABLE_MYTEAM_LINE, TABLE_MYTEAM_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : TABLE_MYTEAM_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            this.fullinfotexts = new long[MYTEAM_FULL_INFO.length];
            this.fullinfotexts_iitials = new String[MYTEAM_FULL_INFO.length];
            for (i = 0; i < MYTEAM_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, MYTEAM_FULL_INFO[i]);
                this.fullinfotexts_iitials[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }
            i = 0;
            while (i < MYTEAM_SORT.length) {
                long field = menues.FindFieldInMenu(_menu, MYTEAM_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
            long[] stat_myteam_wage = this.table.getLineStatistics_controls(TABLE_MYTEAM_ELEMENTS[2]);
            if (stat_myteam_wage == null || stat_myteam_wage.length < 1) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + TABLE_MYTEAM_ELEMENTS[2]);
            }
            this.myteam_wage = menues.GetFieldText(stat_myteam_wage[0]);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, ACTION_BUTTONS[1])), this, ACTION_METHODS[1], 4L);
            this.man_photo = menues.FindFieldInMenu(_menu, HireDrivers.MYTEAM_PHOTO);
        }

        public void afterInit() {
            this.table.afterInit();
        }

        public void onFire(long _menu, MENUsimplebutton_field button) {
            HireDrivers.this.setDirty();
            Vector<HireFireDriversManager.DriverId> res = new Vector<HireFireDriversManager.DriverId>();
            for (Myteamline line : this.TABLE_DATA.all_lines) {
                if (!line.selected || !line.wheather_show) continue;
                res.add(line.id);
            }
            if (!res.isEmpty()) {
                HireFireDriversManager.getHireFireDriversManager().I_Fire(res);
            }
            HireDrivers.this.update();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    HireDrivers.this.myteam_table_sort = new sort(1, HireDrivers.this.myteam_table_sort.type == 1 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 1: {
                    HireDrivers.this.myteam_table_sort = new sort(2, HireDrivers.this.myteam_table_sort.type == 2 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 4: {
                    HireDrivers.this.myteam_table_sort = new sort(5, HireDrivers.this.myteam_table_sort.type == 5 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 2: {
                    HireDrivers.this.myteam_table_sort = new sort(3, HireDrivers.this.myteam_table_sort.type == 3 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 6: {
                    HireDrivers.this.myteam_table_sort = new sort(7, HireDrivers.this.myteam_table_sort.type == 7 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 3: {
                    HireDrivers.this.myteam_table_sort = new sort(4, HireDrivers.this.myteam_table_sort.type == 4 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 8: {
                    HireDrivers.this.myteam_table_sort = new sort(9, HireDrivers.this.myteam_table_sort.type == 9 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 7: {
                    HireDrivers.this.myteam_table_sort = new sort(8, HireDrivers.this.myteam_table_sort.type == 8 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 5: {
                    HireDrivers.this.myteam_table_sort = new sort(6, HireDrivers.this.myteam_table_sort.type == 6 ? !HireDrivers.this.myteam_table_sort.up : true);
                    break;
                }
                case 9: {
                    HireDrivers.this.myteam_table_sort = new sort(10, HireDrivers.this.myteam_table_sort.type == 10 ? !HireDrivers.this.myteam_table_sort.up : true);
                }
            }
            this.updateTable();
            HireDrivers.this.refresh_summary();
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
                        Myteamline data = new Myteamline();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<Myteamline> iter = this.TABLE_DATA.all_lines.iterator();
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
            this.TABLE_DATA.all_lines.clear();
            Vector<HireFireDriversManager.ShotMyDriverInfo> drids = HireFireDriversManager.getHireFireDriversManager().GetMyDriverList(HireDrivers.this.myteam_table_sort.type, HireDrivers.this.myteam_table_sort.up);
            for (HireFireDriversManager.ShotMyDriverInfo inf : drids) {
                Myteamline data = new Myteamline();
                data.id = inf.id;
                data.driver_name = inf.driver_name;
                data.rank = inf.rank;
                data.wage = inf.wage;
                data.isGray = inf.justHire;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
            }
            this.buildvoidcells();
        }

        public void updateTable() {
            this.selected = null;
            this.reciveTableData();
            this.build_tree_data(new CompareMyDrivers());
            this.table.refresh_no_select();
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            Myteamline line = (Myteamline)table_node.item;
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
                    menues.SetFieldText(obj.nativePointer, line.driver_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 1: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, "" + line.rank);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 2: {
                    if (line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)line.wage))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.myteam_wage, macro));
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 3: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, line.driver_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 4: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, "" + line.rank);
                    menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
                    break;
                }
                case 5: {
                    if (!line.isGray) {
                        menues.SetShowField(obj.nativePointer, false);
                        break;
                    }
                    KeyPair[] macro = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)line.wage))};
                    menues.SetShowField(obj.nativePointer, true);
                    menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.myteam_wage, macro));
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
            for (Myteamline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }
            Myteamline data = (Myteamline)table.getItemOnLine((int)line).item;
            data.selected = true;
            this.selected = data;
            this.updateSelectedInfo(this.selected.id);
        }

        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            for (Myteamline myteamline : this.TABLE_DATA.all_lines) {
                myteamline.selected = false;
            }
            for (Cmenu_TTI cmenu_TTI : lines) {
                if (cmenu_TTI.item == null) continue;
                Myteamline data = (Myteamline)cmenu_TTI.item;
                data.selected = true;
            }
            this.selected = (Myteamline)table.getSelectedData().item;
            this.updateSelectedInfo(this.selected.id);
        }

        private void updateSelectedInfo(HireFireDriversManager.DriverId id) {
            int i;
            if (HireDrivers.this.tool_tip_text == null) {
                HireDrivers.this.tool_tip_text = HireDrivers.this.tooltipbutton.GetText();
            }
            if (null == id) {
                int i2;
                for (i2 = 0; i2 < this.fullinfotexts.length; ++i2) {
                    switch (i2) {
                        case 7: {
                            menues.SetShowField(this.fullinfotexts[i2], true);
                            break;
                        }
                        case 8: {
                            menues.SetShowField(this.fullinfotexts[i2], false);
                        }
                    }
                    menues.SetFieldText(this.fullinfotexts[i2], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i2]));
                }
                for (i2 = 0; i2 < HireDrivers.this.termometrs.length; ++i2) {
                    HireDrivers.this.termometrs[i2].Update(0);
                }
                HireDrivers.this.tooltipbutton.Enable(false);
                return;
            }
            HireFireDriversManager.FullMyDriverInfo inf = HireFireDriversManager.getHireFireDriversManager().GetMyDriverInfo(id);
            boolean bad = this.selected.id.id == 0;
            for (i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                    case 0: {
                        if (!bad) {
                            KeyPair[] keys = new KeyPair[]{new KeyPair(HireDrivers.MACRO_NAME, inf.driver_name), new KeyPair(HireDrivers.MACRO_AGE, "" + inf.age)};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_iitials[i], keys));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 1: {
                        if (!bad) {
                            menues.SetFieldText(this.fullinfotexts[i], "" + (int)inf.loyalty + "%");
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 2: {
                        if (!bad) {
                            KeyPair[] keysroi = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney(Math.abs((int)inf.roi))), new KeyPair(HireDrivers.MACRO_SIGN, inf.roi >= 0.0f ? "" : "-")};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_iitials[i], keysroi));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 3: {
                        if (!bad) {
                            KeyPair[] keys2 = new KeyPair[]{new KeyPair(HireDrivers.MACRO_WINS, "" + inf.wins_missions)};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_iitials[i], keys2));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 4: {
                        if (!bad) {
                            KeyPair[] keys3 = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)inf.forefit))};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_iitials[i], keys3));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 5: {
                        if (!bad) {
                            KeyPair[] keys4 = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)inf.maintenance))};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_iitials[i], keys4));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 6: {
                        if (!bad) {
                            KeyPair[] keys5 = new KeyPair[]{new KeyPair(HireDrivers.MACRO_MONEY, Helper.convertMoney((int)inf.gas))};
                            menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_iitials[i], keys5));
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 7: {
                        if (!bad) {
                            menues.SetShowField(this.fullinfotexts[i], !inf.vehJustBought);
                            menues.SetFieldText(this.fullinfotexts[i], inf.short_vehicle_name);
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                        break;
                    }
                    case 8: {
                        if (!bad) {
                            menues.SetShowField(this.fullinfotexts[i], inf.vehJustBought);
                            menues.SetFieldText(this.fullinfotexts[i], inf.short_vehicle_name);
                            break;
                        }
                        menues.SetFieldText(this.fullinfotexts[i], "---");
                    }
                }
                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }
            block26: for (i = 0; i < HireDrivers.this.termometrs.length; ++i) {
                switch (i) {
                    case 0: {
                        if (!bad) {
                            HireDrivers.this.termometrs[i].Update((int)(100.0 * (double)inf.loyalty_temp));
                            continue block26;
                        }
                        HireDrivers.this.termometrs[i].Update(0);
                        continue block26;
                    }
                    case 1: {
                        if (!bad) {
                            HireDrivers.this.termometrs[i].Update((int)(100.0 * (double)inf.roi_temp));
                            continue block26;
                        }
                        HireDrivers.this.termometrs[i].Update(0);
                        continue block26;
                    }
                    case 2: {
                        if (!bad) {
                            HireDrivers.this.termometrs[i].Update((int)(100.0 * (double)inf.wins_temp));
                            continue block26;
                        }
                        HireDrivers.this.termometrs[i].Update(0);
                        continue block26;
                    }
                    case 3: {
                        if (!bad) {
                            HireDrivers.this.termometrs[i].Update((int)(100.0 * (double)inf.forefit_temp));
                            continue block26;
                        }
                        HireDrivers.this.termometrs[i].Update(0);
                        continue block26;
                    }
                    case 4: {
                        if (!bad) {
                            HireDrivers.this.termometrs[i].Update((int)(100.0 * (double)inf.maintenance_temp));
                            continue block26;
                        }
                        HireDrivers.this.termometrs[i].Update(0);
                        continue block26;
                    }
                    case 5: {
                        if (!bad) {
                            HireDrivers.this.termometrs[i].Update((int)(100.0 * (double)inf.gas_temp));
                            continue block26;
                        }
                        HireDrivers.this.termometrs[i].Update(0);
                    }
                }
            }
            IconMappings.remapPersonIcon("" + inf.face_index, this.man_photo);
            if (inf.make == null || inf.make.length() == 0 || inf.model == null || inf.model.length() == 0 || inf.license_plate == null || inf.license_plate.length() == 0) {
                HireDrivers.this.tooltipbutton.Enable(false);
            } else {
                KeyPair[] pairs = new KeyPair[]{new KeyPair("MAKE", inf.make), new KeyPair("MODEL", inf.model), new KeyPair("LICENSE_PLATE", inf.license_plate)};
                HireDrivers.this.tooltipbutton.setText(MacroKit.Parse(HireDrivers.this.tool_tip_text, pairs));
                HireDrivers.this.tooltipbutton.Enable(true);
                HireDrivers.this.tooltipbutton.setState(inf.vehJustBought ? 1 : 0);
            }
        }

        class CompareMyDrivers
        implements ICompareLines {
            CompareMyDrivers() {
            }

            public boolean equal(Object object1, Object object2) {
                if (object1 == null || object2 == null) {
                    return false;
                }
                Myteamline line1 = (Myteamline)object1;
                Myteamline line2 = (Myteamline)object2;
                return line1.id != null && line2.id != null && line1.id.id == line2.id.id;
            }
        }
    }

    static class table_data_hirelingfleet {
        Vector<Hirelingsline> all_lines = new Vector();

        table_data_hirelingfleet() {
        }
    }

    static class Hirelingsline {
        HireFireDriversManager.DriverId id = new HireFireDriversManager.DriverId();
        String driver_name;
        String dealer_name;
        int rank;
        float wage;
        boolean isGray;
        boolean isGray2;
        boolean selected = false;
        boolean wheather_show;

        Hirelingsline() {
        }
    }

    static class table_data_myteam {
        Vector<Myteamline> all_lines = new Vector();

        table_data_myteam() {
        }
    }

    static class Myteamline {
        HireFireDriversManager.DriverId id;
        String driver_name;
        int rank;
        float wage;
        boolean isGray;
        boolean selected = false;
        boolean wheather_show;

        Myteamline() {
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

