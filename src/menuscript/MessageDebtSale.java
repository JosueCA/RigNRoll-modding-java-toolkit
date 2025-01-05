/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;

public class MessageDebtSale
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String GROUP = "Message Debt Sale";
    private int dept = 0;

    public void restartMenu(long _menu) {
    }

    MessageDebtSale(int dept) {
        this.dept = dept;
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
        menues.SetMenuCallBack_ExitMenu(_menu, menues.ConvertSimpleButton((long)menues.FindFieldInMenu((long)_menu, (String)"OK")).nativePointer, 4L);
    }

    public void AfterInitMenu(long _menu) {
        MENUText_field field;
        long control = menues.FindFieldInMenu(_menu, "Message Debt Sale - Text 1");
        if (control != 0L && (field = menues.ConvertTextFields(control)) != null) {
            KeyPair[] key = new KeyPair[]{new KeyPair("MONEY", "" + Converts.ConvertNumeric(this.dept))};
            MacroKit.ApplyToTextfield(field, key);
        }
        MenuAfterInitNarrator.justShowAndStop(_menu);
    }

    public String getMenuId() {
        return "unsettledDebtMENU";
    }

    public void exitMenu(long _menu) {
    }

    public static long CreateMessageDeptMenu(int dept) {
        return menues.createSimpleMenu(new MessageDebtSale(Math.abs(dept)), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}

