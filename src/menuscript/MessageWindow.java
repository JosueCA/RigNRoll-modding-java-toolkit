/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.BaseMenu;
import menu.Common;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.menucreation;
import menu.menues;

public class MessageWindow
extends BaseMenu
implements menucreation {
    private String menuId = "";
    String m_Text;
    boolean m_hasok;
    boolean m_istutorial;
    boolean m_stopworld;
    boolean m_polosy;
    static String s_Text;
    static boolean s_hasok;
    static boolean s_istutorial;
    static boolean s_stopworld;
    static boolean s_polosy;

    public void restartMenu(long _menu) {
    }

    private MessageWindow(String type) {
        this.menuId = type;
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        this.m_Text = s_Text;
        s_Text = null;
        this.m_hasok = s_hasok;
        this.m_istutorial = s_istutorial;
        this.m_polosy = s_polosy;
        this.m_stopworld = s_stopworld;
        menues.SetStopWorld(_menu, this.m_stopworld);
        menues.SetMenagPOLOSY(_menu, this.m_polosy);
        if (this.m_istutorial) {
            menues.InitXml(_menu, Common.ConstructPath("menu_tutorial.xml"), this.m_Text);
        } else {
            menues.InitXmlControl(_menu, Common.ConstructPath("menu_com.xml"), "MESSAGE", "Message Tutorial");
            MENUText_field text = this.uiTools.FindTextField("Just Text");
            text.text = this.m_Text;
            menues.UpdateField(text);
        }
        MENUsimplebutton_field okbutton = this.uiTools.FindSimpleButton("BUTTON - OK");
        if (this.m_hasok) {
            menues.SetMenuCallBack_ExitMenu(_menu, okbutton.nativePointer, 4L);
        } else {
            menues.SetShowField(okbutton.nativePointer, false);
        }
        menues.WindowSet_ShowCursor(_menu, this.m_hasok);
    }

    public void AfterInitMenu(long _menu) {
        menues.setShowMenu(_menu, true);
        MENUsimplebutton_field okbutton = this.uiTools.FindSimpleButton("BUTTON - OK");
        menues.setfocuscontrolonmenu(_menu, okbutton.nativePointer);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return this.menuId;
    }

    public static long CreateMessageWindow(String message, boolean hasok, boolean istutorial, double lifetime, String exitkey, boolean stopworld, boolean polosy) {
        s_Text = message;
        s_hasok = hasok;
        s_istutorial = istutorial;
        s_stopworld = stopworld;
        s_polosy = polosy;
        String id = "";
        if (message.compareTo("TUTORIAL - WAREHOUSE") == 0) {
            id = "tutorialTruckStopMENU";
        } else if (message.compareTo("TUTORIAL - TRUCKSTOP") == 0) {
            id = "tutorialWarehouseMENU";
        }
        return menues.createSimpleMenu(new MessageWindow(id), lifetime, exitkey, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}

