/*
 * Decompiled with CFR 0.151.
 */
package menuscript.table;

import java.util.ArrayList;
import java.util.Iterator;
import menu.Cmenu_TTI;
import menu.Controls;
import menu.Helper;
import menu.IActivePressedTracker;
import menu.IRadioAccess;
import menu.IRadioChangeListener;
import menu.ITableNodeVisitor;
import menu.IWheelEnabled;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.RadioGroup;
import menu.menues;
import menuscript.table.ICompareLines;
import menuscript.table.IFocusListener;
import menuscript.table.IRangerListener;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Line;
import rnrcore.Log;
import rnrcore.eng;

public class Table
extends IWheelEnabled
implements IRadioChangeListener,
IRadioAccess {
    private static int ACTIVE_SYNC = 0;
    private int thisACTIVE_SYNC = 0;
    public static final int SELECTION_NON = 0;
    public static final int SELECTION_SIGLE = 1;
    public static final int SELECTION_MULIPE = 2;
    public static final int SHIFT_X_POSITIVE = 0;
    public static final int SHIFT_Y_POSITIVE = 1;
    public static final int SHIFT_X_NEGATIVE = 2;
    public static final int SHIFT_Y_NEGATIVE = 3;
    private static final String MAKETREEDATA_METHOD = "reciveTreeData_native";
    private long _menu;
    private long table;
    protected int num_rows = 0;
    private int rows_shift = 0;
    private long table_root = 0L;
    private long table_ranger = 0L;
    private ArrayList<Line> lines = new ArrayList();
    protected Cmenu_TTI root = null;
    private int nom_line = 0;
    private boolean init_session = true;
    private int mode_selection = 1;
    private int mode_shift = 1;
    private boolean state_raging = false;
    private boolean state_crtlA = false;
    private boolean looseSelectionOnLeaveFocus = false;
    protected ArrayList<RadioGroup> selectedLine = new ArrayList();
    protected TrackActivity trackActivity = null;
    protected boolean make_track_activity = false;
    protected ArrayList<ISelectLineListener> select_listeners = new ArrayList();
    protected ArrayList<IFocusListener> focus_listeners = new ArrayList();
    protected ArrayList<IRangerListener> ranger_listeners = new ArrayList();
    protected Cmenu_TTI selected_one = null;
    private ArrayList<Cmenu_TTI> selectedLines = new ArrayList();

    public void makeTrackActivity() {
        this.make_track_activity = true;
        this.trackActivity = new TrackActivity();
    }

    public boolean isLineActiveNow(int line) {
        if (!this.make_track_activity || this.trackActivity == null) {
            return false;
        }
        return this.trackActivity.count_active[line] > 0;
    }

    public boolean isLinePressedNow(int line) {
        if (!this.make_track_activity || this.trackActivity == null) {
            return false;
        }
        return this.trackActivity.count_pressed[line] > 0;
    }

    public void setSelectionMode(int mode) {
        this.mode_selection = mode;
    }

    public void setShiftMode(int mode) {
        this.mode_shift = mode;
    }

    public void setLooseSelectionLooseFocus(boolean value) {
        this.looseSelectionOnLeaveFocus = value;
    }

    private int get_line_nom() {
        return this.nom_line++;
    }

    public int getMarkedPosition(long control) {
        Iterator<Line> iter = this.lines.iterator();
        while (iter.hasNext()) {
            int mark = iter.next().getMarkedPosition(control);
            if (mark == -1) continue;
            return mark;
        }
        return -1;
    }

    public Table(long _menu, String table_name, String rangerName) {
        String[] astr;
        this.thisACTIVE_SYNC = ACTIVE_SYNC += 100;
        this._menu = _menu;
        this.table = menues.CreateTable(_menu);
        this.table_root = menues.FindFieldInMenu(_menu, table_name);
        if (0L == this.table_root) {
            eng.err("Table Name " + table_name + " not found.");
        }
        if (rangerName != null) {
            this.initRanger(rangerName);
        }
        if ((astr = table_name.split(" ")).length < 2) {
            Log.menu("Table. Bad name for root element - does not include table sizes. Name:\t" + table_name);
            return;
        }
        this.wheelInit(_menu, table_name);
        this.num_rows = Integer.decode(astr[astr.length - 2]);
        this.rows_shift = Integer.decode(astr[astr.length - 1]);
    }

    public Table(long _menu, String table_name, String rangerName, int rowsNum) {
        String[] astr;
        this.thisACTIVE_SYNC = ACTIVE_SYNC += 100;
        this._menu = _menu;
        this.table = menues.CreateTable(_menu);
        this.table_root = menues.FindFieldInMenu(_menu, table_name);
        if (0L == this.table_root) {
            eng.err("Table Name " + table_name + " not found.");
        }
        if (rangerName != null) {
            this.initRanger(rangerName);
        }
        if ((astr = table_name.split(" ")).length < 2) {
            Log.menu("Table. Bad name for root element - does not include table sizes. Name:\t" + table_name);
            return;
        }
        this.wheelInit(_menu, table_name);
        this.num_rows = rowsNum > 0 ? rowsNum : Integer.decode(astr[astr.length - 2]);
        this.rows_shift = Integer.decode(astr[astr.length - 1]);
    }

    private void initRanger(String rangerName) {
        this.table_ranger = menues.FindFieldInMenu(this._menu, rangerName);
        if (0L == this.table_ranger) {
            Log.menu("TableOfElements const. initRanger - no sych ranger. Name:\t" + rangerName);
            return;
        }
        MENU_ranger rang = menues.ConvertRanger(this.table_ranger);
        rang.min_value = 0;
        rang.max_value = 0;
        menues.SetScriptOnControl(this._menu, rang, this, "OnRangerScroll", 1L);
        menues.UpdateField(rang);
    }

    protected void OnRangerScroll(long _menu, MENU_ranger scroller) {
        if (this.init_session) {
            return;
        }
        this.state_raging = true;
        this.refresf_table(scroller.current_value);
        this.renewSelection();
        for (IRangerListener lst : this.ranger_listeners) {
            lst.rangerMoved();
        }
        this.state_raging = false;
    }

    protected void renewSelection() {
        switch (this.mode_selection) {
            case 1: {
                int selected_line_num = Helper.tellLine(this.root, this.selected_one);
                if (selected_line_num < 0 || selected_line_num >= this.num_rows) {
                    for (RadioGroup group : this.selectedLine) {
                        group.deselectall();
                    }
                } else {
                    for (RadioGroup group : this.selectedLine) {
                        group.silentSelect(selected_line_num);
                    }
                }
                break;
            }
            case 2: {
                for (RadioGroup group : this.selectedLine) {
                    group.deselectall();
                }
                for (Cmenu_TTI item : this.selectedLines) {
                    int nom_line = Helper.tellLine(this.root, item);
                    if (nom_line < 0 || nom_line >= this.num_rows) continue;
                    for (RadioGroup group : this.selectedLine) {
                        group.silentSelect(nom_line);
                    }
                }
                break;
            }
        }
    }

    public void redrawTable() {
        this.refresf_table(Helper.tell0Line(this.root));
    }

    private void refresf_table(int position_on_top) {
        Helper.setNumVisibleNodeOnTop(this.root, position_on_top + 1);
        menues.UpdateDataWithChildren(this.root);
        menues.ConnectTableAndData(this._menu, this.table);
        menues.RedrawTable(this._menu, this.table);
    }

    private void addLine(Line line) {
        line.insertInTable(this._menu, this.table_root);
        this.lines.add(line);
        if (0L != this.table_ranger) {
            MENU_ranger rang = menues.ConvertRanger(this.table_ranger);
            rang.max_value = this.lines.size() - this.num_rows;
            if (rang.max_value < rang.min_value) {
                rang.max_value = rang.min_value;
            }
            menues.UpdateField(rang);
        }
        menues.AddGroupInTable(this._menu, this.table, this.get_line_nom(), line.getLine());
    }

    public void fillWithLines(String xmlfilename, String xmlcontrolgroup, String[] marked_names) {
        block6: for (int i = 0; i < this.num_rows; ++i) {
            Line line = new Line(xmlfilename, xmlcontrolgroup, marked_names);
            line.load(this._menu);
            this.addLine(line);
            switch (this.mode_shift) {
                case 2: {
                    line.shiftLine(-this.rows_shift * i, 0);
                    continue block6;
                }
                case 3: {
                    line.shiftLine(0, -this.rows_shift * i);
                    continue block6;
                }
                case 0: {
                    line.shiftLine(this.rows_shift * i, 0);
                    continue block6;
                }
                case 1: {
                    line.shiftLine(0, this.rows_shift * i);
                }
            }
        }
    }

    public void reciveTreeData(Cmenu_TTI data) {
        this.root = data;
        menues.FillMajorDataTable_ScriptObject(this._menu, this.table, this, MAKETREEDATA_METHOD);
        menues.ConnectTableAndData(this._menu, this.table);
        this.selected_one = null;
        this.selectedLines.clear();
        this.renewSelection();
    }

    private Cmenu_TTI findEqualObject(ICompareLines comparator, Cmenu_TTI root, Cmenu_TTI compareto) {
        if (null == compareto || null == root) {
            return null;
        }
        if (comparator.equal(root.item, compareto.item)) {
            return root;
        }
        for (Object item : root.children) {
            Cmenu_TTI menu_data = (Cmenu_TTI)item;
            Cmenu_TTI res = this.findEqualObject(comparator, menu_data, compareto);
            if (null == res) continue;
            return res;
        }
        return null;
    }

    public void reciveTreeData(Cmenu_TTI data, ICompareLines comparator) {
        this.root = data;
        menues.FillMajorDataTable_ScriptObject(this._menu, this.table, this, MAKETREEDATA_METHOD);
        menues.ConnectTableAndData(this._menu, this.table);
        this.selected_one = this.findEqualObject(comparator, this.root, this.selected_one);
        ArrayList old_selectedLines = (ArrayList)this.selectedLines.clone();
        this.selectedLines.clear();
        for (int i = 0; i < old_selectedLines.size(); ++i) {
            Cmenu_TTI selected = this.findEqualObject(comparator, this.root, (Cmenu_TTI)old_selectedLines.get(i));
            if (selected == null) continue;
            this.selectedLines.add(selected);
        }
        this.renewSelection();
    }

    public void updateTreeData(Cmenu_TTI data) {
        if (null == this.root) {
            return;
        }
        this.root.children = data.children;
        menues.UpdateDataWithChildren(this.root);
        menues.SetXMLDataOnTable(this._menu, this.table, this.root);
        menues.ConnectTableAndData(this._menu, this.table);
        this.selectedLines.clear();
        this.renewSelection();
    }

    protected void reciveTreeData_native(Cmenu_TTI data) {
        if (null == this.root) {
            return;
        }
        data.children = this.root.children;
        this.root = data;
        menues.UpdateDataWithChildren(this.root);
    }

    public void takeSetuperForAllLines(ISetupLine setup) {
        Iterator<Line> iter = this.lines.iterator();
        while (iter.hasNext()) {
            menues.ScriptObjSyncGroup(this._menu, iter.next().getLine(), setup, "SetupLineInTable");
        }
    }

    public void initLinesSelection(String controlsname) {
        this.initRadioGroup(this._menu, controlsname);
    }

    private void initRadioGroup(long _menu, String name) {
        long[] res = this.getLineStatistics_controls(name);
        if (res.length == 0) {
            return;
        }
        MENUbutton_field[] buttons = new MENUbutton_field[res.length];
        for (int i = 0; i < res.length; ++i) {
            buttons[i] = menues.ConvertButton(res[i]);
            buttons[i].userid = i;
            menues.UpdateField(buttons[i]);
            menues.SetSyncControlState(_menu, this.thisACTIVE_SYNC + i, buttons[i].nativePointer);
            menues.SetSyncControlActive(_menu, this.thisACTIVE_SYNC + i, buttons[i].nativePointer);
        }
        RadioGroup group = new RadioGroup(_menu, buttons, this.make_track_activity);
        group.addListener(this);
        group.addAccessListener(this);
        if (this.make_track_activity) {
            group.addActiveListener(this.trackActivity);
        }
        this.selectedLine.add(group);
    }

    private void makeSingleSelection(MENUbutton_field button, int cs) {
        if (cs == 1) {
            Iterator<ISelectLineListener> iter = this.select_listeners.iterator();
            while (iter.hasNext()) {
                iter.next().selectLineEvent(this, button.userid);
            }
            this.selected_one = this.getItemOnLine(button.userid);
            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(button.userid);
            }
        } else {
            for (RadioGroup group : this.selectedLine) {
                group.silentDeselect(button.userid);
            }
        }
        switch (this.mode_selection) {
            case 1: {
                break;
            }
            case 2: {
                if (1 == cs) {
                    this.selectedLines.clear();
                    this.selectedLines.add(this.selected_one);
                    break;
                }
                this.selectedLines.remove(this.getItemOnLine(button.userid));
            }
        }
    }

    private void makeShiftSelection(MENUbutton_field button, int cs) {
        Cmenu_TTI clicked_one = this.getItemOnLine(button.userid);
        for (RadioGroup group : this.selectedLine) {
            group.deselectall();
        }
        if (this.selected_one.equals(clicked_one)) {
            this.selectedLines.clear();
            this.selectedLines.add(this.selected_one);
            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(button.userid);
            }
            this.tellMulipleSelection();
            return;
        }
        this.selectedLines.clear();
        int firstline = Helper.tellLine(this.root, this.selected_one);
        int secondline = Helper.tellLine(this.root, clicked_one);
        if (firstline < secondline) {
            Helper.traverseTree(this.root, this.selected_one, clicked_one, new Visitor());
        } else {
            Helper.traverseTree(this.root, clicked_one, this.selected_one, new Visitor());
        }
        for (Cmenu_TTI line : this.selectedLines) {
            for (RadioGroup group : this.selectedLine) {
                int line_num = Helper.tellLine(this.root, line);
                if (line_num < 0 || line_num >= this.num_rows) continue;
                group.silentSelect(line_num);
            }
        }
        this.tellMulipleSelection();
    }

    private void makeCrtlSelection(MENUbutton_field button, int cs) {
        Cmenu_TTI selected_item = this.getItemOnLine(button.userid);
        if (cs == 1 && selected_item != this.selected_one && !this.selectedLines.contains(selected_item)) {
            Iterator<ISelectLineListener> iter = this.select_listeners.iterator();
            while (iter.hasNext()) {
                iter.next().selectLineEvent(this, button.userid);
            }
            this.selected_one = selected_item;
            if (!this.selectedLines.contains(this.selected_one)) {
                this.selectedLines.add(this.selected_one);
            }
            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(button.userid);
            }
        } else {
            this.selectedLines.remove(selected_item);
            for (RadioGroup group : this.selectedLine) {
                group.silentDeselect(button.userid);
            }
        }
        this.tellMulipleSelection();
    }

    private void tellMulipleSelection() {
        Iterator<ISelectLineListener> iter = this.select_listeners.iterator();
        while (iter.hasNext()) {
            iter.next().selectMultipleLinesEvent(this, new ArrayList<Cmenu_TTI>(this.selectedLines));
        }
    }

    public void controlSelected(MENUbutton_field button, int cs) {
        if (this.selectedLine == null || this.selectedLine.isEmpty()) {
            return;
        }
        this.wheelControlSelected();
        switch (this.mode_selection) {
            case 1: {
                this.makeSingleSelection(button, cs);
                break;
            }
            case 2: {
                boolean is_multyselect = false;
                if (!this.state_raging) {
                    boolean shift = Controls.isShiftPressed();
                    boolean ctrl = Controls.isControlPressed();
                    boolean bl = is_multyselect = shift || ctrl || this.state_crtlA;
                    if (!is_multyselect) {
                        this.makeSingleSelection(button, cs);
                        break;
                    }
                    if (!this.state_crtlA && shift) {
                        this.makeShiftSelection(button, cs);
                        break;
                    }
                    if (!this.state_crtlA && !ctrl) break;
                    this.makeCrtlSelection(button, cs);
                    break;
                }
                boolean bl = is_multyselect = this.selectedLines.size() > 1;
                if (!is_multyselect) {
                    this.makeSingleSelection(button, cs);
                    break;
                }
                this.makeCrtlSelection(button, cs);
            }
        }
    }

    public void addListener(ISelectLineListener add) {
        this.select_listeners.add(add);
    }

    public void addFocusListener(IFocusListener add) {
        this.focus_listeners.add(add);
    }

    public void addRangerListener(IRangerListener add) {
        this.ranger_listeners.add(add);
    }

    public void afterInit() {
        this.init_session = false;
        if (this.selectedLine != null && this.root != null && !this.root.children.isEmpty()) {
            this.selected_one = (Cmenu_TTI)this.root.children.get(0);
        }
        this.refresh();
    }

    public void deinit() {
        this.wheelDeinit();
        this.selected_one = null;
        this.select_listeners = null;
        this.focus_listeners = null;
        if (this.selectedLines != null) {
            this.selectedLines.clear();
        }
        this.selectedLines = null;
        this.root = null;
    }

    public void refreshLine(int line) {
        menues.RedrawGroup(this._menu, menues.GetGroupOnLine(this._menu, this.table, line));
    }

    public void refresh_no_select() {
        MENU_ranger rang;
        int pos;
        if (0L != this.table_ranger) {
            MENU_ranger ranger;
            int rows = Helper.numVisibleNodes(this.root);
            if (rows <= this.num_rows) {
                ranger = menues.ConvertRanger(this.table_ranger);
                ranger.min_value = 0;
                ranger.max_value = 0;
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            } else {
                ranger = menues.ConvertRanger(this.table_ranger);
                ranger.min_value = 0;
                ranger.max_value = rows - this.num_rows;
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            }
        }
        this.refresf_table(0);
        if (this.selected_one != null) {
            pos = Helper.tellLine(this.root, this.selected_one);
            if (pos < 0) {
                rang = menues.ConvertRanger(this.table_ranger);
                rang.current_value += pos;
                menues.UpdateField(rang);
            } else if (pos >= this.num_rows) {
                rang = menues.ConvertRanger(this.table_ranger);
                rang.current_value += pos - this.num_rows + 1;
                menues.UpdateField(rang);
            }
        } else if (this.selectedLines.isEmpty()) {
            this.select_line(0);
        } else {
            for (Cmenu_TTI item : this.selectedLines) {
                int pos2 = Helper.tellLine(this.root, item);
                if (pos2 < 0 || pos2 >= this.num_rows) continue;
                this.selected_one = item;
                return;
            }
            pos = Helper.tellLine(this.root, this.selectedLines.get(0));
            if (pos < 0) {
                rang = menues.ConvertRanger(this.table_ranger);
                rang.current_value += pos;
                menues.UpdateField(rang);
            } else if (pos >= this.num_rows) {
                rang = menues.ConvertRanger(this.table_ranger);
                rang.current_value += pos - this.num_rows + 1;
                menues.UpdateField(rang);
            }
            this.selected_one = this.selectedLines.get(0);
        }
        ArrayList<Cmenu_TTI> lines = new ArrayList<Cmenu_TTI>();
        for (Cmenu_TTI item : this.selectedLines) {
            lines.add(item);
        }
        Iterator<ISelectLineListener> iter = this.select_listeners.iterator();
        while (iter.hasNext()) {
            iter.next().selectMultipleLinesEvent(this, lines);
        }
    }

    public void refresh() {
        if (0L != this.table_ranger) {
            int rows = Helper.numVisibleNodes(this.root);
            if (rows <= this.num_rows) {
                MENU_ranger ranger = menues.ConvertRanger(this.table_ranger);
                ranger.min_value = 0;
                ranger.max_value = 0;
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            } else {
                MENU_ranger ranger = menues.ConvertRanger(this.table_ranger);
                ranger.min_value = 0;
                ranger.max_value = rows - this.num_rows;
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            }
        }
        if (this.selectedLine != null && this.root != null && !this.root.children.isEmpty()) {
            this.selected_one = (Cmenu_TTI)this.root.children.get(0);
        }
        this.refresf_table(0);
        this.select_line(0);
    }

    public int getNumRows() {
        return this.num_rows;
    }

    public long[] getLineStatistics_controls(String name) {
        long[] res = new long[this.lines.size()];
        int i = 0;
        Iterator<Line> iter = this.lines.iterator();
        while (iter.hasNext()) {
            res[i++] = iter.next().getNamedControl(name);
        }
        return res;
    }

    public Cmenu_TTI getItemOnLine(int line) {
        if (line >= this.lines.size()) {
            return null;
        }
        return menues.GetXMLDataOnGroup(this._menu, this.lines.get(line).getLine());
    }

    public void active_line(int line, boolean value) {
        if (this.selectedLine != null && line < this.lines.size()) {
            for (RadioGroup group : this.selectedLine) {
                group.makeactive(line, value);
            }
        }
    }

    public void pressed_line(int line, boolean value) {
        if (this.selectedLine != null && line < this.lines.size()) {
            for (RadioGroup group : this.selectedLine) {
                group.makepressed(line, value);
            }
        }
    }

    public void select_line(int line) {
        if (this.selectedLine != null && line < this.lines.size()) {
            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(line);
            }
        }
        MENUbutton_field butt = new MENUbutton_field();
        butt.userid = line;
        this.makeSingleSelection(butt, 1);
    }

    public int getSelected() {
        if (this.selectedLine != null) {
            return Helper.tellItemLine(this.root, this.selected_one);
        }
        return 0;
    }

    public Cmenu_TTI getSelectedData() {
        return this.selected_one;
    }

    public boolean active_line_by_data(Object obj, boolean value) {
        if (this.selectedLine != null) {
            Cmenu_TTI item = Helper.findInTree(this.root, obj);
            if (item == null) {
                return false;
            }
            int pos = Helper.tellLine(this.root, item);
            if (pos >= 0 && pos < this.num_rows) {
                this.active_line(pos, value);
            }
            return true;
        }
        return false;
    }

    public boolean pressed_line_by_data(Object obj, boolean value) {
        if (this.selectedLine != null) {
            Cmenu_TTI item = Helper.findInTree(this.root, obj);
            if (item == null) {
                return false;
            }
            int pos = Helper.tellLine(this.root, item);
            if (pos >= 0 && pos < this.num_rows) {
                this.pressed_line(pos, value);
            }
            return true;
        }
        return false;
    }

    public boolean select_line_by_data(Object obj) {
        if (this.selectedLine != null) {
            Cmenu_TTI item = Helper.findInTree(this.root, obj);
            if (item == null) {
                return false;
            }
            int pos = Helper.tellLine(this.root, item);
            if (pos < 0) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);
                rang.current_value += pos;
                menues.UpdateField(rang);
                this.refresf_table(rang.current_value);
                this.select_line(0);
            } else if (pos >= this.num_rows) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);
                rang.current_value += pos - this.num_rows + 1;
                menues.UpdateField(rang);
                this.refresf_table(rang.current_value);
                this.select_line(this.num_rows - 1);
            } else {
                this.select_line(pos);
            }
            return true;
        }
        return false;
    }

    public boolean controlAccessed(MENUbutton_field button, int state) {
        if (this.selectedLine == null || this.selectedLine.isEmpty()) {
            return false;
        }
        switch (this.mode_selection) {
            case 1: {
                return state == 1;
            }
            case 2: {
                boolean is_multyselect = false;
                if (!this.state_raging) {
                    boolean shift = Controls.isShiftPressed();
                    boolean ctrl = Controls.isControlPressed();
                    if (!(shift || ctrl || this.state_crtlA)) {
                        for (RadioGroup item : this.selectedLine) {
                            item.setSingleSelectionMode();
                        }
                    } else {
                        is_multyselect = true;
                        for (RadioGroup item : this.selectedLine) {
                            item.setMultySelectionMode();
                        }
                    }
                    if (!is_multyselect) {
                        return state == 1;
                    }
                    if (!this.state_crtlA && shift) {
                        return true;
                    }
                    if (this.state_crtlA || ctrl) {
                        if (state == 0) {
                            return this.selectedLines.size() != 1 || !this.selectedLines.contains(this.getItemOnLine(button.userid));
                        }
                        return true;
                    }
                } else {
                    boolean bl = is_multyselect = this.selectedLines.size() > 1;
                    if (!is_multyselect) {
                        return state == 1;
                    }
                    if (state == 0) {
                        return this.selectedLines.size() != 1 || !this.selectedLines.contains(this.getItemOnLine(button.userid));
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void ControlsCtrlAPressed() {
        this.state_crtlA = true;
        this.selectedLines.clear();
        for (RadioGroup item : this.selectedLine) {
            item.setMultySelectionMode();
        }
        Helper.traverseTree(this.root, new Visitor());
        for (Cmenu_TTI line : this.selectedLines) {
            for (RadioGroup group : this.selectedLine) {
                int line_num = Helper.tellLine(this.root, line);
                if (line_num < 0 || line_num >= this.num_rows) continue;
                group.silentSelect(line_num);
            }
        }
        this.tellMulipleSelection();
        this.state_crtlA = false;
    }

    public void ControlsMouseWheel(int value) {
        if (this.table_ranger != 0L) {
            MENU_ranger rang = menues.ConvertRanger(this.table_ranger);
            rang.current_value -= value;
            if (rang.current_value > rang.max_value) {
                rang.current_value = rang.max_value;
            }
            if (rang.current_value < rang.min_value) {
                rang.current_value = rang.min_value;
            }
            menues.UpdateField(rang);
            this.refresf_table(rang.current_value);
        }
    }

    public void deselectAll() {
        for (RadioGroup group : this.selectedLine) {
            group.deselectall();
        }
    }

    public void cbEnterFocus() {
        for (IFocusListener item : this.focus_listeners) {
            item.enterFocus(this);
        }
    }

    public void cbLeaveFocus() {
        for (IFocusListener iFocusListener : this.focus_listeners) {
            iFocusListener.leaveFocus(this);
        }
        if (this.looseSelectionOnLeaveFocus) {
            for (RadioGroup radioGroup : this.selectedLine) {
                radioGroup.deselectall();
            }
        }
    }

    class Visitor
    implements ITableNodeVisitor {
        Visitor() {
        }

        public void visitNode(Cmenu_TTI node) {
            Table.this.selectedLines.add(node);
        }
    }

    class TrackActivity
    implements IActivePressedTracker {
        int[] count_pressed;
        int[] count_active;

        TrackActivity() {
            this.count_active = new int[Table.this.num_rows];
            this.count_pressed = new int[Table.this.num_rows];
            for (int i = 0; i < Table.this.num_rows; ++i) {
                this.count_active[i] = 0;
                this.count_pressed[i] = 0;
            }
        }

        public void onActive(int id) {
            int n = id;
            this.count_active[n] = this.count_active[n] + 1;
        }

        public void onPassive(int id) {
            int n = id;
            this.count_active[n] = this.count_active[n] - 1;
        }

        public void onPress(int id) {
            if (this.count_pressed[id] == 0) {
                int n = id;
                this.count_pressed[n] = this.count_pressed[n] + 1;
            }
        }

        public void onRelease(int id) {
            if (this.count_pressed[id] > 0) {
                int n = id;
                this.count_pressed[n] = this.count_pressed[n] - 1;
            }
        }
    }
}

