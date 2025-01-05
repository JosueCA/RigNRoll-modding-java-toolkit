/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.TableNode;

public interface TableCallbacks {
    public void SetupLineInTable(TableNode var1, MENUText_field var2);

    public void SetupLineInTable(TableNode var1, MENUsimplebutton_field var2);

    public void SetupLineInTable(TableNode var1, MENUbutton_field var2);

    public void SetupLineInTable(int var1, TableNode var2, Object var3);

    public void OnEvent(long var1, TableNode var3, long var4, long var6);
}

