/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.Vector;
import menu.BaseMenu;
import menu.Common;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.Table;
import menu.TextScroller;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import rnrcore.event;
import rnrscr.gameinfo;

public class PoliceMenu
extends BaseMenu
implements menucreation {
    static Vector s_items;
    static int s_recovery;
    static int s_balance;
    public static final int TEXT = 0;
    public static final int AMOUNT = 1;
    public static final int PRICE = 2;
    TextScroller m_scroller;
    Table m_Table;

    public void restartMenu(long _menu) {
    }

    public static long CreatePoliceMenu(Vector items, int recovery, int balance) {
        s_items = items;
        s_recovery = recovery;
        s_balance = balance;
        return menues.createSimpleMenu(new PoliceMenu(), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        menues.InitXml(_menu, Common.ConstructPath("menu_police.xml"), "Police01 - Ticket");
        menues.SetMenuCallBack_ExitMenu(_menu, this.uiTools.FindSimpleButton((String)"BUTTON - PAY").nativePointer, 4L);
    }

    public void AfterInitMenu(long _menu) {
        menues.setfocuscontrolonmenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - PAY"));
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.setShowMenu(_menu, true);
        MENUText_field t = this.uiTools.FindTextField("Police01 - TEXT");
        int texh = menues.GetTextLineHeight(t.nativePointer);
        int startbase = menues.GetBaseLine(t.nativePointer);
        int total = 0;
        String itemlist = "";
        String pricelist = "";
        int linecounter = 0;
        for (int i = 0; i < s_items.size(); ++i) {
            PoliceItem item = (PoliceItem)s_items.get(i);
            itemlist = itemlist + (i + 1) + ". " + item.name;
            int curlines = Converts.HeightToLines(menues.GetTextHeight(t.nativePointer, itemlist), startbase, texh);
            pricelist = pricelist + gameinfo.ConvertMoney(item.money);
            for (int j = 0; j < curlines - linecounter; ++j) {
                pricelist = pricelist + "\n";
            }
            itemlist = itemlist + "\n";
            linecounter = curlines;
            total += item.money;
        }
        int linescreen = Converts.HeightToLines(t.leny, startbase, texh);
        this.uiTools.SetTextValue("Police01 - TEXT", itemlist);
        this.uiTools.SetTextValue("Police01 - VALUE", pricelist);
        this.m_scroller = new TextScroller(this.uiTools, this.uiTools.FindScroller("Tableranger - FAULTS"), linecounter, linescreen, texh, startbase, false, "TABLEGROUP Police01 - 5 38");
        this.m_scroller.AddTextControl(this.uiTools.FindTextField("Police01 - TEXT"));
        this.m_scroller.AddTextControl(this.uiTools.FindTextField("Police01 - VALUE"));
        s_items = null;
        KeyPair[] macro = new KeyPair[]{new KeyPair("MONEY", "" + s_recovery)};
        MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Police01 - RecoveryVALUE"), macro);
        macro = new KeyPair[]{new KeyPair("MONEY", "" + (total += s_recovery))};
        MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Police01 - Total VALUE"), macro);
        macro = new KeyPair[]{new KeyPair("SIGN", s_balance >= 0 ? "" : "-"), new KeyPair("MONEY", Converts.ConvertNumeric(Math.abs(s_balance)))};
        MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Police01 - Balance VALUE"), macro);
    }

    public void exitMenu(long _menu) {
        if (this.m_Table != null) {
            this.m_Table.DeInit();
        }
        if (this.m_scroller != null) {
            this.m_scroller.Deinit();
        }
        event.Setevent(9802);
    }

    public String getMenuId() {
        return "policeMENU";
    }

    static class PoliceItem {
        String name;
        int money;

        PoliceItem() {
        }
    }
}

