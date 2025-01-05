/*
 * Decompiled with CFR 0.151.
 */
package menuscript.table;

import menu.Cmenu_TTI;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;

public interface ISetupLine {
    public static final String METHOD = "SetupLineInTable";

    public void SetupLineInTable(MENUbutton_field var1, Cmenu_TTI var2);

    public void SetupLineInTable(MENUsimplebutton_field var1, Cmenu_TTI var2);

    public void SetupLineInTable(MENUText_field var1, Cmenu_TTI var2);

    public void SetupLineInTable(MENUTruckview var1, Cmenu_TTI var2);

    public void SetupLineInTable(MENUEditBox var1, Cmenu_TTI var2);

    public void SetupLineInTable(MENU_ranger var1, Cmenu_TTI var2);
}

