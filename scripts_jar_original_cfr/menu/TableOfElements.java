/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import java.util.Iterator;
import menu.ITableInsertable;
import menu.IWheelEnabled;
import menu.MENU_ranger;
import menu.menues;
import rnrcore.Log;

public class TableOfElements
extends IWheelEnabled {
    private long _menu = 0L;
    private long table_root = 0L;
    private long table_ranger = 0L;
    private int num_rows = 0;
    private int rows_shift = 0;
    private ArrayList<ITableInsertable> elements = new ArrayList();
    private int currentOnTop = 0;
    private boolean _is_hidden = false;
    private boolean f_tableUpdated = false;

    public TableOfElements(long _menu, String table_name) {
        this._menu = _menu;
        this.table_root = menues.FindFieldInMenu(_menu, table_name);
        String[] astr = table_name.split(" ");
        if (astr.length < 2) {
            Log.menu("TableOfElements const. Bad name for root element - does not include table sizes. Name:\t" + table_name);
            return;
        }
        this.num_rows = Integer.decode(astr[astr.length - 2]);
        this.rows_shift = Integer.decode(astr[astr.length - 1]);
        this.wheelInit(_menu, table_name);
    }

    public TableOfElements(long _menu, String table_name, String rangerName) {
        String[] astr;
        this._menu = _menu;
        this.table_root = menues.FindFieldInMenu(_menu, table_name);
        if (rangerName != null) {
            this.initRanger(rangerName);
        }
        if ((astr = table_name.split(" ")).length < 2) {
            Log.menu("TableOfElements const. Bad name for root element - does not include table sizes. Name:\t" + table_name);
            return;
        }
        this.num_rows = Integer.decode(astr[astr.length - 2]);
        this.rows_shift = Integer.decode(astr[astr.length - 1]);
        this.wheelInit(_menu, table_name);
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

    void OnRangerScroll(long _menu, MENU_ranger scroller) {
        if (!this.f_tableUpdated) {
            return;
        }
        this.currentOnTop = scroller.current_value;
        this.updateTable();
    }

    public void cbEnterFocus() {
    }

    public void cbLeaveFocus() {
    }

    public void ControlsCtrlAPressed() {
    }

    public void ControlsMouseWheel(int value) {
        MENU_ranger scroller = menues.ConvertRanger(this.table_ranger);
        if (scroller == null) {
            return;
        }
        scroller.current_value -= value;
        if (scroller.current_value > scroller.max_value) {
            scroller.current_value = scroller.max_value;
        }
        if (scroller.current_value < scroller.min_value) {
            scroller.current_value = scroller.min_value;
        }
        menues.UpdateField(scroller);
        this.updateTable();
    }

    public void insert(ITableInsertable item) {
        item.insertInTable(this._menu, this.table_root);
        this.elements.add(item);
        if (0L != this.table_ranger) {
            MENU_ranger rang = menues.ConvertRanger(this.table_ranger);
            rang.max_value = this.elements.size() - this.num_rows;
            if (rang.max_value < rang.min_value) {
                rang.max_value = rang.min_value;
            }
            menues.UpdateField(rang);
        }
    }

    public void hideTable() {
        this._is_hidden = true;
        for (ITableInsertable elem : this.elements) {
            elem.hide();
        }
    }

    public void showTable() {
        this._is_hidden = false;
        this.updateTable();
    }

    public void initTable() {
        for (ITableInsertable elem : this.elements) {
            elem.init();
        }
        this.updateTable();
    }

    private void updateTable() {
        if (this._is_hidden) {
            return;
        }
        this.f_tableUpdated = true;
        Iterator<ITableInsertable> iter = this.elements.iterator();
        int num = 0;
        while (iter.hasNext()) {
            ITableInsertable elem = iter.next();
            int line = num - this.currentOnTop;
            if (num < this.currentOnTop || line >= this.num_rows) {
                ++num;
                elem.hide();
                continue;
            }
            elem.show();
            elem.updatePositon(0, this.rows_shift * line);
            ++num;
        }
    }

    public void DeInit() {
        this.wheelDeinit();
    }
}

