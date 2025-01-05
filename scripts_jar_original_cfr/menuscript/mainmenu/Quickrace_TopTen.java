/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.IValueChanged;
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
import menu.menues;
import menuscript.Converts;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrcore.loc;

public class Quickrace_TopTen
extends PanelDialog
implements IValueChanged {
    private static final String[] LISTOFALTERANTIVESGROUP = new String[]{"Tablegroup - ELEMENTS - TopTen List Special CATEGORY", "Tablegroup - ELEMENTS - TopTen List Special CLASS"};
    private static final String[] STITLES = new String[]{"menu_MAIN.xml\\Tablegroup - ELEMENTS - TopTen List Special CATEGORY\\QUICK RACE - TOP TEN - CATEGORY\\TITLE", "menu_MAIN.xml\\Tablegroup - ELEMENTS - TopTen List Special CLASS\\QUICK RACE - TOP TEN - CLASS\\TITLE"};
    private static final String[] CATEGORY_TEXT = new String[]{loc.getMENUString("BEST TRUCKERS"), loc.getMENUString("BEST RACERS"), loc.getMENUString("BEST SHIPPERS")};
    private static final String[] CLASS_TEXT = new String[]{loc.getMENUString("LOD_EASY"), loc.getMENUString("LOD_NORMAL"), loc.getMENUString("LOD_HARD")};
    ListOfAlternatives _category = null;
    ListOfAlternatives _class = null;
    long best_text_filed0_id = 0L;
    long best_text_filed1_id = 0L;
    long best_picture_id = 0L;
    long my_text_filed0_id = 0L;
    long my_text_filed1_id = 0L;
    long my_picture_id = 0L;
    long blind_id = 0L;
    private static final String XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String TABLE_BEST = "TABLEGROUP - QUICK RACE - TOP TEN - BEST RESULTS - 10 36";
    private static final String TABLE_BEST_LINE = "Tablegroup - ELEMENTS - TopTenBestResults Lines";
    private static final String[] TABLE_BEST_ELEMENTS = new String[]{"QUICK RACE - TOP TEN - BEST RESULTS - NAME - VALUE", "QUICK RACE - TOP TEN - BEST RESULTS - PROFIT - VALUE", "QUICK RACE - TOP TEN - BEST RESULTS - SPEED - VALUE", "QUICK RACE - TOP TEN - BEST RESULTS - CAREFULNESS - VALUE"};
    private static final String TABLE_MY = "TABLEGROUP - QUICK RACE - TOP TEN - MY RESULTS - 10 36";
    private static final String TABLE_MY_LINE = "Tablegroup - ELEMENTS - TopTenMyResults Lines";
    private static final String[] TABLE_MY_ELEMENTS = new String[]{"QUICK RACE - TOP TEN - MY RESULTS - DATE - VALUE", "QUICK RACE - TOP TEN - MY RESULTS - PROFIT - VALUE", "QUICK RACE - TOP TEN - MY RESULTS - SPEED - VALUE", "QUICK RACE - TOP TEN - MY RESULTS - CAREFULNESS - VALUE"};
    private static final String[] SUMMARY_ELEMENTS = new String[]{"QUICK RACE - TOP TEN - MY STATISTICS - MAX SPEED - VALUE", "QUICK RACE - TOP TEN - MY STATISTICS - MAX DISTANCE - VALUE", "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL DISTANCE - VALUE", "QUICK RACE - TOP TEN - MY STATISTICS - EXECUTED ORDERS - VALUE", "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL PROFIT - VALUE", "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL REPAIR - VALUE", "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL TICKETS - VALUE"};
    private String[] summary_text = null;
    public sort current_sort = new sort();
    public Vector<MyResultsInfo> in_my_lines = new Vector();
    public Vector<BestResultsInfo> in_best_lines = new Vector();
    BestResults best_results = null;
    MyResults my_results = null;
    private long _menu = 0L;
    Summary summary = new Summary();

    public void exitMenu() {
        this.deinit();
        super.exitMenu();
    }

    public Quickrace_TopTen(long _menu, long[] controls, long window, long okButton, Panel parent) {
        super(_menu, window, controls, okButton, 0L, 0L, 0L, parent);
        this._menu = _menu;
        JavaEvents.SendEvent(65, 19, this.current_sort);
        this._category = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP[0], loc.getMENUString(STITLES[0]), CATEGORY_TEXT, false);
        this._class = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP[1], loc.getMENUString(STITLES[1]), CLASS_TEXT, false);
        this._category.load(_menu);
        this._class.load(_menu);
        this.param_values.addParametr("QUICK RACE CATEGORY", this.current_sort._category, 0, this._category);
        this.param_values.addParametr("QUICK RACE CLASS", this.current_sort._class, 0, this._class);
        this._category.insertInTable(_menu, menues.FindFieldInMenu(_menu, "TABLEGROUP - QUICK RACE - TOP TEN - CATEGORY"));
        this._class.insertInTable(_menu, menues.FindFieldInMenu(_menu, "TABLEGROUP - QUICK RACE - TOP TEN - CLASS"));
        this._category.addListener(this);
        this._class.addListener(this);
        this.best_picture_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - BEST RESULTS - PROFIT - TITLE");
        this.best_text_filed0_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - BEST RESULTS - SPEED - TITLE");
        this.best_text_filed1_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - BEST RESULTS - CAREFULNESS - TITLE");
        this.my_picture_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - MY RESULTS - PROFIT - TITLE");
        this.my_text_filed0_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - MY RESULTS - SPEED - TITLE");
        this.my_text_filed1_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - MY RESULTS - CAREFULNESS - TITLE");
        this.blind_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - TABLE - ALL BORDERS");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.best_picture_id), this, "onProfitSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.my_picture_id), this, "onProfitSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.best_text_filed0_id), this, "onSpeedSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.my_text_filed0_id), this, "onSpeedSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.best_text_filed1_id), this, "onCarefulnessSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.my_text_filed1_id), this, "onCarefulnessSelected", 2L);
        this.best_results = new BestResults(_menu, this);
        this.my_results = new MyResults(_menu, this);
    }

    public void onProfitSelected(long _menu, MENUbutton_field button) {
        this._category.setValue(0);
        this.hide_some_controls();
    }

    public void onSpeedSelected(long _menu, MENUbutton_field button) {
        this._category.setValue(1);
        this.hide_some_controls();
    }

    public void onCarefulnessSelected(long _menu, MENUbutton_field button) {
        this._category.setValue(2);
        this.hide_some_controls();
    }

    void hide_some_controls() {
        if (this.best_picture_id != 0L) {
            menues.SetFieldState(this.best_picture_id, this.current_sort._category == 0 ? 1 : 0);
        }
        if (this.best_text_filed0_id != 0L) {
            menues.SetFieldState(this.best_text_filed0_id, this.current_sort._category == 1 ? 1 : 0);
        }
        if (this.best_text_filed1_id != 0L) {
            menues.SetFieldState(this.best_text_filed1_id, this.current_sort._category == 2 ? 1 : 0);
        }
        if (this.my_picture_id != 0L) {
            menues.SetFieldState(this.my_picture_id, this.current_sort._category == 0 ? 1 : 0);
        }
        if (this.my_text_filed0_id != 0L) {
            menues.SetFieldState(this.my_text_filed0_id, this.current_sort._category == 1 ? 1 : 0);
        }
        if (this.my_text_filed1_id != 0L) {
            menues.SetFieldState(this.my_text_filed1_id, this.current_sort._category == 2 ? 1 : 0);
        }
        if (this.blind_id != 0L) {
            menues.SetBlindess(this.blind_id, true);
            menues.SetIgnoreEvents(this.blind_id, true);
        }
    }

    public void valueChanged() {
        this.current_sort._category = this._category.getValue();
        this.current_sort._class = this._class.getValue();
        this.hide_some_controls();
        if (this.best_results != null) {
            this.best_results.updateTable();
        }
        if (this.my_results != null) {
            this.my_results.updateTable();
        }
    }

    public void updateSummary() {
        JavaEvents.SendEvent(65, 23, this.summary);
        if (this.summary_text == null) {
            this.summary_text = new String[SUMMARY_ELEMENTS.length];
            for (int i = 0; i < SUMMARY_ELEMENTS.length; ++i) {
                this.summary_text[i] = menues.GetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[i]));
            }
        }
        KeyPair[] pairs = new KeyPair[]{new KeyPair("VALUE", "" + this.summary.MAX_SPEED)};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[0]), MacroKit.Parse(this.summary_text[0], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[0])));
        pairs = new KeyPair[]{new KeyPair("VALUE", "" + this.summary.MAX_DISTANCE)};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[1]), MacroKit.Parse(this.summary_text[1], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[1])));
        pairs = new KeyPair[]{new KeyPair("VALUE", "" + this.summary.TOTAL_DISTANCE)};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[2]), MacroKit.Parse(this.summary_text[2], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[2])));
        pairs = new KeyPair[]{new KeyPair("VALUE", "" + this.summary.EXECUTED_ORDERS)};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[3]), MacroKit.Parse(this.summary_text[3], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[3])));
        pairs = new KeyPair[]{new KeyPair("SIGN", "" + (this.summary.TOTAL_PROFIT >= 0 ? "" : "-")), new KeyPair("MONEY", "" + Math.abs(this.summary.TOTAL_PROFIT))};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[4]), MacroKit.Parse(this.summary_text[4], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[4])));
        pairs = new KeyPair[]{new KeyPair("MONEY", "" + this.summary.TOTAL_REPAIR)};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[5]), MacroKit.Parse(this.summary_text[5], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[5])));
        pairs = new KeyPair[]{new KeyPair("MONEY", "" + this.summary.TOTAL_TICKETS)};
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[6]), MacroKit.Parse(this.summary_text[6], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[6])));
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        JavaEvents.SendEvent(65, 22, this.current_sort);
        super.OnExit(_menu, button);
    }

    public void afterInit() {
        super.afterInit();
        this.best_results.afterInit();
        this.my_results.afterInit();
        this.hide_some_controls();
        this.updateSummary();
    }

    public void update() {
        super.update();
        this.best_results.updateTable();
        this.my_results.updateTable();
        this.updateSummary();
    }

    public void deinit() {
        this.best_results.deinit();
        this.my_results.deinit();
    }

    protected void onShow(boolean value) {
        this.hide_some_controls();
    }

    protected void onFreeze(boolean value) {
        this.hide_some_controls();
    }

    public void readParamValues() {
        super.update();
        this.best_results.updateTable();
        this.my_results.updateTable();
        this.updateSummary();
    }

    static class Summary {
        int MAX_SPEED = 0;
        int MAX_DISTANCE = 0;
        int TOTAL_DISTANCE = 0;
        int EXECUTED_ORDERS = 0;
        int TOTAL_PROFIT = 0;
        int TOTAL_REPAIR = 0;
        int TOTAL_TICKETS = 0;

        Summary() {
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class BestResults
    implements ISetupLine {
        private best_table_data TABLE_DATA = new best_table_data();
        private long _menu = 0L;
        Quickrace_TopTen _parent = null;
        Table table;
        private String RESULT_PROFIT_text = null;
        private String RESULT_SPEED_text = null;
        private String RESULT_CAREFULNESS_text = null;

        BestResults(long menu, Quickrace_TopTen parent) {
            this._parent = parent;
            this._menu = menu;
            this.table = new Table(this._menu, Quickrace_TopTen.TABLE_BEST, null);
            this.table.setSelectionMode(0);
            this.table.fillWithLines(Quickrace_TopTen.XML, Quickrace_TopTen.TABLE_BEST_LINE, TABLE_BEST_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.reciveTableData();
            this.build_tree_data();
        }

        @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
            BestResultsInfo line = (BestResultsInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: {
                    obj.text = line.name;
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                }
            }
            menues.SetShowField(obj.nativePointer, true);
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            BestResultsInfo line = (BestResultsInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: {
                    break;
                }
                case 1: {
                    if (this.RESULT_PROFIT_text == null) {
                        this.RESULT_PROFIT_text = obj.text;
                    }
                    KeyPair[] keys = new KeyPair[]{new KeyPair("SIGN", "" + (line.profit >= 0 ? " " : "-")), new KeyPair("MONEY", "" + Math.abs(line.profit))};
                    obj.text = MacroKit.Parse(this.RESULT_PROFIT_text, keys);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    menues.SetFieldState(obj.nativePointer, Quickrace_TopTen.this.current_sort._category == 0 ? 1 : 0);
                    break;
                }
                case 2: {
                    if (this.RESULT_SPEED_text == null) {
                        this.RESULT_SPEED_text = obj.text;
                    }
                    KeyPair[] keys1 = new KeyPair[]{new KeyPair("VALUE", "" + line.speed)};
                    obj.text = MacroKit.Parse(this.RESULT_SPEED_text, keys1);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    menues.SetFieldState(obj.nativePointer, Quickrace_TopTen.this.current_sort._category == 1 ? 1 : 0);
                    break;
                }
                case 3: {
                    if (this.RESULT_CAREFULNESS_text == null) {
                        this.RESULT_CAREFULNESS_text = obj.text;
                    }
                    KeyPair[] keys2 = new KeyPair[]{new KeyPair("VALUE", "" + line.carefulness)};
                    obj.text = MacroKit.Parse(this.RESULT_CAREFULNESS_text, keys2);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    menues.SetFieldState(obj.nativePointer, Quickrace_TopTen.this.current_sort._category == 2 ? 2 : 0);
                }
            }
            menues.SetShowField(obj.nativePointer, true);
        }

        @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
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

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        BestResultsInfo data = new BestResultsInfo();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<BestResultsInfo> iter = this.TABLE_DATA.all_lines.iterator();
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
            long[] contrls_0 = null;
            if (0 < TABLE_BEST_ELEMENTS.length) {
                contrls_0 = this.table.getLineStatistics_controls(TABLE_BEST_ELEMENTS[0]);
            }
            long[] contrls_1 = null;
            if (1 < TABLE_BEST_ELEMENTS.length) {
                contrls_1 = this.table.getLineStatistics_controls(TABLE_BEST_ELEMENTS[1]);
            }
            long[] contrls_2 = null;
            if (2 < TABLE_BEST_ELEMENTS.length) {
                contrls_2 = this.table.getLineStatistics_controls(TABLE_BEST_ELEMENTS[2]);
            }
            long[] contrls_3 = null;
            if (1 < TABLE_BEST_ELEMENTS.length) {
                contrls_3 = this.table.getLineStatistics_controls(TABLE_BEST_ELEMENTS[3]);
            }
            if (null == contrls_0 || null == contrls_1 || null == contrls_2 || null == contrls_3) {
                return;
            }
            for (int i = 0; i < contrls_0.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_0[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_1[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_2[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_3[i]);
            }
        }

        public void afterInit() {
            this.table.afterInit();
            this.make_sync_group();
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        public void selectLineEvent(Table table, int line) {
        }

        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 21, this._parent);
            for (int i = 0; i < Math.min(Quickrace_TopTen.this.in_best_lines.size(), 10); ++i) {
                this.TABLE_DATA.all_lines.add(Quickrace_TopTen.this.in_best_lines.elementAt(i));
            }
            this.buildvoidcells();
        }

        public void deinit() {
            this.table.deinit();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class MyResults
    implements ISetupLine {
        private my_table_data TABLE_DATA = new my_table_data();
        private long _menu = 0L;
        Quickrace_TopTen _parent = null;
        Table table;
        private String RESULT_TIME_text = null;
        private String RESULT_PROFIT_text = null;
        private String RESULT_SPEED_text = null;
        private String RESULT_CAREFULNESS_text = null;

        MyResults(long menu, Quickrace_TopTen parent) {
            this._parent = parent;
            this._menu = menu;
            this.table = new Table(this._menu, Quickrace_TopTen.TABLE_MY, null);
            this.table.setSelectionMode(0);
            this.table.fillWithLines(Quickrace_TopTen.XML, Quickrace_TopTen.TABLE_MY_LINE, TABLE_MY_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.reciveTableData();
            this.build_tree_data();
        }

        @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
            MyResultsInfo line = (MyResultsInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: {
                    if (this.RESULT_TIME_text == null) {
                        this.RESULT_TIME_text = obj.text;
                    }
                    obj.text = Converts.ConvertDateAbsolute(this.RESULT_TIME_text, line.date.month, line.date.day, line.date.year, line.date.hour, line.date.min, line.date.sec);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 1: {
                    if (this.RESULT_PROFIT_text == null) {
                        this.RESULT_PROFIT_text = obj.text;
                    }
                    KeyPair[] keys = new KeyPair[]{new KeyPair("SIGN", "" + (line.profit >= 0 ? " " : "-")), new KeyPair("MONEY", "" + Math.abs(line.profit))};
                    obj.text = MacroKit.Parse(this.RESULT_PROFIT_text, keys);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 2: {
                    if (this.RESULT_SPEED_text == null) {
                        this.RESULT_SPEED_text = obj.text;
                    }
                    KeyPair[] keys1 = new KeyPair[]{new KeyPair("VALUE", "" + line.speed)};
                    obj.text = MacroKit.Parse(this.RESULT_SPEED_text, keys1);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 3: {
                    if (this.RESULT_CAREFULNESS_text == null) {
                        this.RESULT_CAREFULNESS_text = obj.text;
                    }
                    KeyPair[] keys2 = new KeyPair[]{new KeyPair("VALUE", "" + line.carefulness)};
                    obj.text = MacroKit.Parse(this.RESULT_CAREFULNESS_text, keys2);
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                }
            }
            menues.SetShowField(obj.nativePointer, true);
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        }

        @Override
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

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        MyResultsInfo data = new MyResultsInfo();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<MyResultsInfo> iter = this.TABLE_DATA.all_lines.iterator();
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
            long[] contrls_0 = null;
            if (0 < TABLE_MY_ELEMENTS.length) {
                contrls_0 = this.table.getLineStatistics_controls(TABLE_MY_ELEMENTS[0]);
            }
            long[] contrls_1 = null;
            if (1 < TABLE_MY_ELEMENTS.length) {
                contrls_1 = this.table.getLineStatistics_controls(TABLE_MY_ELEMENTS[1]);
            }
            long[] contrls_2 = null;
            if (2 < TABLE_MY_ELEMENTS.length) {
                contrls_2 = this.table.getLineStatistics_controls(TABLE_MY_ELEMENTS[2]);
            }
            long[] contrls_3 = null;
            if (1 < TABLE_MY_ELEMENTS.length) {
                contrls_3 = this.table.getLineStatistics_controls(TABLE_MY_ELEMENTS[3]);
            }
            if (null == contrls_0 || null == contrls_1 || null == contrls_2 || null == contrls_3) {
                return;
            }
            for (int i = 0; i < contrls_0.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_0[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_1[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_2[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_3[i]);
            }
        }

        public void afterInit() {
            this.table.afterInit();
            this.make_sync_group();
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        public void selectLineEvent(Table table, int line) {
        }

        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 20, this._parent);
            for (int i = 0; i < Math.min(Quickrace_TopTen.this.in_my_lines.size(), 10); ++i) {
                this.TABLE_DATA.all_lines.add(Quickrace_TopTen.this.in_my_lines.elementAt(i));
            }
            this.buildvoidcells();
        }

        public void deinit() {
            this.table.deinit();
        }
    }

    static class sort {
        int _class = 0;
        int _category = 0;

        sort() {
        }
    }

    static class my_table_data {
        Vector<MyResultsInfo> all_lines = new Vector();

        my_table_data() {
        }
    }

    static class best_table_data {
        Vector<BestResultsInfo> all_lines = new Vector();

        best_table_data() {
        }
    }

    static class MyResultsInfo {
        MediaTime date = new MediaTime();
        int profit = 0;
        int speed = 0;
        int carefulness = 0;
        boolean wheather_show = true;

        MyResultsInfo() {
        }
    }

    static class MediaTime {
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int min = 0;
        int sec = 0;

        MediaTime() {
        }
    }

    static class BestResultsInfo {
        String name = null;
        int profit = 0;
        int speed = 0;
        int carefulness = 0;
        boolean wheather_show = true;

        BestResultsInfo() {
        }
    }
}

