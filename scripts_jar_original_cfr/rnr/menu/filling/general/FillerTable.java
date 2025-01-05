/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.Table;
import menu.TableCallbacks;
import menu.TableCallbacksAdapter;
import rnr.menu.filling.MenuFieldFiller;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FillerTable
extends MenuFieldFiller {
    private final Pair<String, Integer>[] tableRows;
    private Object[] tableItems = null;
    private Table table = null;
    private final String parentUiElementName;
    private final String uiControlsGroupName;
    private final String xmlWithLayout;
    private final int rowHeight;
    private final int rowsCount;
    private TableCallbacks tableDataFiller = new TableCallbacksAdapter();

    public FillerTable(String name, String parentUiElementName, String uiControlsGroupName, String xmlWithLayout, int rowHeight, int rowsCount, Pair<String, Integer> ... tableRows) {
        super(name);
        assert (null != parentUiElementName) : "'parentUiElementName' must be non-null reference";
        assert (null != uiControlsGroupName) : "'uiControlsGroupName' must be non-null reference";
        assert (null != xmlWithLayout) : "'xmlWithLayout' must be non-null reference";
        this.parentUiElementName = parentUiElementName;
        this.uiControlsGroupName = uiControlsGroupName;
        this.xmlWithLayout = xmlWithLayout;
        this.rowHeight = rowHeight;
        this.rowsCount = rowsCount;
        this.tableRows = tableRows;
    }

    public void setTableItems(Object[] tableItems) {
        this.tableItems = tableItems;
    }

    public void setTableDataFiller(TableCallbacks tableDataFiller) {
        if (null != tableDataFiller) {
            this.tableDataFiller = tableDataFiller;
        }
    }

    @Override
    public void fillFieldOfMenu(long menuHandle) {
        if (null != this.tableRows && null != this.getFieldName()) {
            this.table = new Table(menuHandle, null);
            for (Pair<String, Integer> tableRow : this.tableRows) {
                this.table.AddTextField(tableRow.getFirst(), tableRow.getSecond());
            }
            if (null != this.tableItems) {
                for (Object tableItem : this.tableItems) {
                    this.table.AddItem(null, tableItem, false);
                }
            }
            this.table.Setup(this.rowHeight, this.rowsCount, this.xmlWithLayout, this.uiControlsGroupName, this.parentUiElementName, this.tableDataFiller, 8);
        }
    }

    @Override
    public void freeResources() {
        if (null != this.table) {
            this.table.DeInit();
        }
    }
}

