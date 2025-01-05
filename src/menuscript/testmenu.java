/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.BaseMenu;
import menu.Common;
import menu.JavaEventCb;
import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.menucreation;
import menu.menues;
import menuscript.BannerMenu;
import menuscript.RacePanelMenu;

public class testmenu
extends BaseMenu
implements menucreation {
    static String s_xmlfile;
    static String s_controlgroup;

    public void restartMenu(long _menu) {
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "debugMENU";
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        menues.InitXml(_menu, s_xmlfile, s_controlgroup);
        MENUsimplebutton_field b = this.uiTools.FindSimpleButton("BUTTON - EXIT");
        if (b != null) {
            menues.SetMenuCallBack_ExitMenu(_menu, b.nativePointer, 4L);
        }
    }

    public void AfterInitMenu(long _menu) {
        menues.setShowMenu(_menu, true);
        menues.SetStopWorld(_menu, true);
    }

    public static long CreateTestMenu(String xmlfile, String controlgroup) {
        s_xmlfile = xmlfile;
        s_controlgroup = controlgroup;
        return menues.createSimpleMenu(new testmenu(), 1000000.0, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void CreateTutorialWindow() {
        testmenu.CreateTestMenu(Common.ConstructPath("menu_esc.xml"), null);
    }

    public void CreateTestMenu1() {
        testmenu.CreateTestMenu(Common.ConstructPath("menu_test.xml"), null);
    }

    public void CreateTest5() {
        BannerMenu.CreateStellaWelcome("SD");
    }

    public void CreateTest7() {
        JavaEvents.RegisterEvent(11, new TestEventSend());
        RacePanelMenu.PanelPreparingToRaceIn();
    }

    public void CreateTest8() {
        JavaEvents.RegisterEvent(11, new TestEventSend());
        RacePanelMenu.PanelPreparingToOrdersIn();
    }

    public void CreateTest10() {
        JavaEvents.RegisterEvent(11, new TestEventSend());
        RacePanelMenu.PanelPreparingToRaceOut();
    }

    public void CreateTest14() {
        JavaEvents.RegisterEvent(11, new TestEventSend());
        RacePanelMenu.PanelPreparingToOrdersOut();
    }

    static class TestEventSend
    implements JavaEventCb {
        TestEventSend() {
        }

        public void OnEvent(int ID, int value, Object obj) {
            RacePanelMenu.ExitPanel();
        }
    }
}

