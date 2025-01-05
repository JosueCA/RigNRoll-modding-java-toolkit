/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import java.util.Iterator;
import menu.ContainerTextTitleTextValue;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.Table;
import menu.TableCallbacks;
import menu.TableNode;
import menu.menues;
import rnrcore.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TableTextAndRadio
implements TableCallbacks {
    private static final String TITLE = "TEXT - TITLE";
    private static final String VALUE = "RADIO - VALUE";
    private Table table = null;
    private int num_rows = 0;
    private int rows_shift = 0;
    private static final String TABLENAME = "TableTextAndRadio";
    private static int num_table = 0;

    public TableTextAndRadio(long _menu, String FILENAME, String CONTROLGROUP, String PARENT) {
        this.readName(PARENT);
        this.table = new Table(_menu, TABLENAME + num_table++);
        this.table.AddEvent(2);
        this.table.AddTextField(TITLE, 1);
        this.table.AddRadioButton(VALUE, 2);
        this.table.Setup(this.rows_shift, this.num_rows, FILENAME, CONTROLGROUP, PARENT, this, 0);
    }

    public TableTextAndRadio(long _menu, String FILENAME, String CONTROLGROUP, String PARENT, String RANGER) {
        this.readName(PARENT);
        this.table = new Table(_menu, TABLENAME + num_table++);
        this.table.AddEvent(2);
        this.table.AddTextField(TITLE, 1);
        this.table.AddRadioButton(VALUE, 2);
        this.table.Setup(this.rows_shift, this.num_rows, FILENAME, CONTROLGROUP, PARENT, this, 0);
        long field_ranger = menues.FindFieldInMenu(_menu, RANGER);
        if (field_ranger != 0L) {
            this.table.AttachRanger(menues.ConvertRanger(field_ranger));
        } else {
            Log.menu("TableTextAndRadio. Cannot find ranger named " + RANGER);
        }
    }

    public void DeInit() {
        if (this.table != null) {
            this.table.DeInit();
        }
    }

    public void afterInit() {
    }

    public int sliderPosition() {
        return this.table.sliderPosition();
    }

    private void readName(String table_name) {
        String[] astr = table_name.split(" ");
        if (astr.length < 2) {
            Log.menu("TableOfElements const. Bad name for root element - does not include table sizes. Name:\t" + table_name);
            return;
        }
        this.num_rows = Integer.decode(astr[astr.length - 2]);
        this.rows_shift = Integer.decode(astr[astr.length - 1]);
    }

    public void fillTable(ArrayList<ContainerTextTitleTextValue> data) {
        this.table.SetupRootNode();
        this.readData(data);
        this.table.RefillTree();
        this.table.RedrawTable();
    }

    public void redraw() {
        this.table.RedrawTable();
    }

    private void readData(ArrayList<ContainerTextTitleTextValue> data) {
        Iterator<ContainerTextTitleTextValue> iter = data.iterator();
        while (iter.hasNext()) {
            this.table.AddItem(null, iter.next(), true);
        }
    }

    // @Override
    public void OnEvent(long event2, TableNode node, long group, long _menu) {
    }

    // @Override
    public void SetupLineInTable(int type, TableNode node, Object control) {
    }

    // @Override
    public void SetupLineInTable(TableNode node, MENUbutton_field button) {
        if (null == node.item) {
            button.text = "";
            menues.SetShowField(button.nativePointer, false);
            menues.UpdateField(button);
            return;
        }
        button.text = ((ContainerTextTitleTextValue)node.item).loc_value;
        menues.SetShowField(button.nativePointer, true);
        menues.UpdateField(button);
        menues.SetFieldState(button.nativePointer, 0);
    }

    // @Override
    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
    }

    // @Override
    public void SetupLineInTable(TableNode node, MENUText_field text) {
        if (null == node.item) {
            text.text = "";
            menues.SetShowField(text.nativePointer, false);
            menues.UpdateField(text);
            return;
        }
        text.text = ((ContainerTextTitleTextValue)node.item).loc_title;
        menues.SetShowField(text.nativePointer, true);
        menues.UpdateField(text);
    }
}

