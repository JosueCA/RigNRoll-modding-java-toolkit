/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import rnrscr.ILeaveMenuListener;

public class menuBridgeToll
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_documents.xml";
    private static final String GROUP = "Message BRIDGE TOLL";
    public double toll_money = 12.0;
    private ILeaveMenuListener m_leave_menu_listener = null;

    public void restartMenu(long _menu) {
    }

    public menuBridgeToll(ILeaveMenuListener listener) {
        this.m_leave_menu_listener = listener;
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
    }

    public void AfterInitMenu(long _menu) {
        MENUText_field field;
        long control = menues.FindFieldInMenu(_menu, "Message Bridge Toll - MONEY");
        if (control != 0L && (field = menues.ConvertTextFields(control)) != null) {
            JavaEvents.SendEvent(71, 0, this);
            KeyPair[] key = new KeyPair[]{new KeyPair("MONEY", "" + Converts.ConvertNumeric((int)this.toll_money))};
            MacroKit.ApplyToTextfield(field, key);
        }
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
        this.m_leave_menu_listener.menuLeaved();
        JavaEvents.SendEvent(71, 1, this);
    }

    public String getMenuId() {
        return "bridgeTallMENU";
    }

    public static long CreateBridgeTollMenu(ILeaveMenuListener listener) {
        return menues.createSimpleMenu(new menuBridgeToll(listener), 1, 2.0, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}

