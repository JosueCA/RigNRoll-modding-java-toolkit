/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.TableComparator;

public abstract class TableCmp
implements TableComparator {
    public boolean order;

    public void SetOrder(boolean isascending) {
        this.order = isascending;
    }
}

