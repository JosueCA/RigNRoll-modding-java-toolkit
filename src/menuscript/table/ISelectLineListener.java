/*
 * Decompiled with CFR 0.151.
 */
package menuscript.table;

import java.util.ArrayList;
import menu.Cmenu_TTI;
import menuscript.table.Table;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ISelectLineListener {
    public void selectLineEvent(Table var1, int var2);

    public void selectMultipleLinesEvent(Table var1, ArrayList<Cmenu_TTI> var2);
}

