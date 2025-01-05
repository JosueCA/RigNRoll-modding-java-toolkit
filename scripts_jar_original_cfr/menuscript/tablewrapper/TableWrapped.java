/*
 * Decompiled with CFR 0.151.
 */
package menuscript.tablewrapper;

import java.util.ArrayList;
import java.util.Iterator;
import menu.Cmenu_TTI;
import menu.FocusManager;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.table.IFocusListener;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import menuscript.tablewrapper.TableData;
import menuscript.tablewrapper.TableLine;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class TableWrapped
implements ISetupLine,
ISelectLineListener,
IFocusListener {
    private final String SORT_METHOD = "onSort";
    protected Table table;
    protected TableLine selected = null;
    protected TableData TABLE_DATA = new TableData();

    public abstract void onSort(long var1, MENUsimplebutton_field var3);

    public abstract void SetupLineInTable(long var1, int var3, TableLine var4);

    public abstract void updateSelectedInfo(TableLine var1);

    protected abstract void reciveTableData();

    public void EnterFocus() {
        FocusManager.enterFocus(this.table);
    }

    public boolean isInFocus() {
        return FocusManager.isFocused(this.table);
    }

    public TableWrapped(long _menu, int selectionType, int shiftType, boolean track_activity, String XML, String TABLE, String TABLE_RANGER, String LINE, String[] LINE_ELEMENTS, String[] SORT) {
        this.table = new Table(_menu, TABLE, TABLE_RANGER);
        this.table.setSelectionMode(selectionType);
        this.table.setShiftMode(shiftType);
        this.table.fillWithLines(XML, LINE, LINE_ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.table.addListener(this);
        this.table.addFocusListener(this);
        this.reciveTableDataWrapped();
        this.build_tree_data();
        if (track_activity) {
            this.table.makeTrackActivity();
        }
        if (selectionType != 0) {
            for (String name : LINE_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
        }
        if (null != SORT) {
            int i = 0;
            while (i < SORT.length) {
                long field = menues.FindFieldInMenu(_menu, SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
        }
    }

    public TableWrapped(long _menu, int selectionType, boolean track_activity, String XML, String TABLE, String TABLE_RANGER, String LINE, String[] LINE_ELEMENTS, boolean[] isRadioButton, String[] SORT) {
        int i;
        this.table = new Table(_menu, TABLE, TABLE_RANGER);
        this.table.setSelectionMode(selectionType);
        this.table.setShiftMode(1);
        this.table.fillWithLines(XML, LINE, LINE_ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.table.addListener(this);
        this.table.addFocusListener(this);
        this.reciveTableDataWrapped();
        this.build_tree_data();
        if (track_activity) {
            this.table.makeTrackActivity();
        }
        if (selectionType != 0) {
            if (isRadioButton != null) {
                for (i = 0; i < Math.min(isRadioButton.length, LINE_ELEMENTS.length); ++i) {
                    if (!isRadioButton[i]) continue;
                    this.table.initLinesSelection(LINE_ELEMENTS[i]);
                }
            } else {
                for (i = 0; i < LINE_ELEMENTS.length; ++i) {
                    this.table.initLinesSelection(LINE_ELEMENTS[i]);
                }
            }
        }
        if (null != SORT) {
            i = 0;
            while (i < SORT.length) {
                long field = menues.FindFieldInMenu(_menu, SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
        }
    }

    public TableWrapped(long _menu, int selectionType, boolean track_activity, String XML, String TABLE, String TABLE_RANGER, String LINE, String[] LINE_ELEMENTS, boolean[] isRadioButton, String[] SORT, int rowsNum) {
        int i;
        this.table = new Table(_menu, TABLE, TABLE_RANGER, rowsNum);
        this.table.setSelectionMode(selectionType);
        this.table.setShiftMode(1);
        this.table.fillWithLines(XML, LINE, LINE_ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.table.addListener(this);
        this.table.addFocusListener(this);
        this.reciveTableDataWrapped();
        this.build_tree_data();
        if (track_activity) {
            this.table.makeTrackActivity();
        }
        if (selectionType != 0) {
            if (isRadioButton != null) {
                for (i = 0; i < Math.min(isRadioButton.length, LINE_ELEMENTS.length); ++i) {
                    if (!isRadioButton[i]) continue;
                    this.table.initLinesSelection(LINE_ELEMENTS[i]);
                }
            } else {
                for (i = 0; i < LINE_ELEMENTS.length; ++i) {
                    this.table.initLinesSelection(LINE_ELEMENTS[i]);
                }
            }
        }
        if (null != SORT) {
            i = 0;
            while (i < SORT.length) {
                long field = menues.FindFieldInMenu(_menu, SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                buts.userid = i++;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
        }
    }

    public void afterInit() {
        this.table.afterInit();
    }

    private void reciveTableDataWrapped() {
        this.TABLE_DATA.all_lines.clear();
        this.reciveTableData();
        this.buildvoidcells();
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

    protected void buildvoidcells() {
        block4: {
            block3: {
                if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                for (int i = 0; i < dif; ++i) {
                    TableLine data = new TableLine();
                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
                break block4;
            }
            int count_good_data = 0;
            Iterator<TableLine> iter = this.TABLE_DATA.all_lines.iterator();
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

    public void updateTable() {
        this.selected = null;
        this.reciveTableDataWrapped();
        this.build_tree_data();
        this.table.select_line(0);
        this.table.refresh();
    }

    public void redrawTable() {
        this.table.redrawTable();
    }

    @Override
    public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int position = this.table.getMarkedPosition(obj.nativePointer);
        this.SetupLineInTable(obj.nativePointer, position, line);
    }

    @Override
    public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int position = this.table.getMarkedPosition(obj.nativePointer);
        this.SetupLineInTable(obj.nativePointer, position, line);
    }

    @Override
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int position = this.table.getMarkedPosition(obj.nativePointer);
        this.SetupLineInTable(obj.nativePointer, position, line);
    }

    @Override
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int position = this.table.getMarkedPosition(obj.nativePointer);
        this.SetupLineInTable(obj.nativePointer, position, line);
    }

    @Override
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int position = this.table.getMarkedPosition(obj.nativePointer);
        this.SetupLineInTable(obj.nativePointer, position, line);
    }

    @Override
    public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int position = this.table.getMarkedPosition(obj.nativePointer);
        this.SetupLineInTable(obj.nativePointer, position, line);
    }

    @Override
    public void selectLineEvent(Table table, int line) {
        for (TableLine item : this.TABLE_DATA.all_lines) {
            item.selected = false;
        }
        TableLine data = (TableLine)table.getItemOnLine((int)line).item;
        data.selected = true;
        this.selected = data;
        this.updateSelectedInfo(this.selected);
    }

    @Override
    public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        for (TableLine tableLine : this.TABLE_DATA.all_lines) {
            tableLine.selected = false;
        }
        for (Cmenu_TTI cmenu_TTI : lines) {
            if (cmenu_TTI.item == null) continue;
            TableLine data = (TableLine)cmenu_TTI.item;
            data.selected = true;
        }
        this.selected = (TableLine)table.getSelectedData().item;
        this.updateSelectedInfo(this.selected);
    }

    public TableLine getSelected() {
        return this.selected;
    }

    public TableLine getTop() {
        return (TableLine)this.table.getItemOnLine((int)0).item;
    }

    public TableLine getLineItem(int line) {
        return (TableLine)this.table.getItemOnLine((int)line).item;
    }

    public ArrayList<TableLine> getSelectedMultiple() {
        ArrayList<TableLine> res = new ArrayList<TableLine>();
        for (TableLine line : this.TABLE_DATA.all_lines) {
            if (!line.selected) continue;
            res.add(line);
        }
        return res;
    }

    public void selectLineByData(TableLine data) {
        this.table.select_line_by_data(data);
    }

    public void deselectOnLooseFocus(boolean value) {
        this.table.setLooseSelectionLooseFocus(value);
    }

    public void activeLineByData(TableLine data, boolean value) {
        this.table.active_line_by_data(data, value);
    }

    public void pressedLineByData(TableLine data, boolean value) {
        this.table.pressed_line_by_data(data, value);
    }
}

