/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.DateData;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.TextScroller;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import rnrcore.CoreTime;
import rnrcore.loc;
import scriptEvents.EventsControllerHelper;

public class ScenarioBigRaceConfirmation
implements menucreation {
    private static final String ok_button = "ACCEPT MONSTER CUP - OK";
    private static final String cancel_button = "ACCEPT MONSTER CUP - CANCEL";
    private static final String method_ok = "onOk";
    private static final String method_cancel = "onCancel";
    private static final String msg_ok = "Scenario Answer Ok";
    private static final String msg_cancel = "Scenario Answer Cancel";
    public static final String msg_menu_closed = "Scenario Answer Recived";
    private static boolean result = false;
    private String start;
    private CoreTime start_date;
    private String finish;
    private DateData finish_date;
    private int winmoney;
    TextScroller scroller = null;
    private static ScenarioBigRaceConfirmation m_lastMenu = null;
    private boolean f_buttonPressed = false;

    public void restartMenu(long _menu) {
    }

    public static final ScenarioBigRaceConfirmation getLastMenu() {
        return m_lastMenu;
    }

    public static final boolean gResult() {
        return result;
    }

    public static final long createScenarioBigRaceConfirmationMenu(String start, CoreTime start_date, String finish, DateData finish_date, int winmoney) {
        m_lastMenu = new ScenarioBigRaceConfirmation();
        return m_lastMenu._createScenarioBigRaceConfirmationMenu(start, start_date, finish, finish_date, winmoney);
    }

    private final long _createScenarioBigRaceConfirmationMenu(String _start, CoreTime _start_date, String _finish, DateData _finish_date, int _winmoney) {
        this.start = _start;
        this.start_date = _start_date;
        this.finish = _finish;
        this.finish_date = _finish_date;
        this.winmoney = _winmoney;
        return menues.createSimpleMenu(this, 1.0E9, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_documents.xml", "DOCUMENT - WELCOME TO MONSTER CUP");
        long okButton = menues.FindFieldInMenu(_menu, ok_button);
        long cancelButton = menues.FindFieldInMenu(_menu, cancel_button);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(okButton), this, method_ok, 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(cancelButton), this, method_cancel, 4L);
    }

    public void onOk(long _menu, MENUsimplebutton_field button) {
        this.f_buttonPressed = true;
        result = true;
        EventsControllerHelper.messageEventHappened(msg_ok);
        EventsControllerHelper.messageEventHappened(msg_menu_closed);
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void onCancel(long _menu, MENUsimplebutton_field button) {
        this.f_buttonPressed = true;
        result = false;
        EventsControllerHelper.messageEventHappened(msg_cancel);
        EventsControllerHelper.messageEventHappened(msg_menu_closed);
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void AfterInitMenu(long _menu) {
        String final_text;
        Common common = new Common(_menu);
        MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, "Tableranger - Newspaper"));
        MENUText_field text = (MENUText_field)menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, "MONSTER CUP - TEXT"));
        KeyPair[] pair1 = new KeyPair[]{new KeyPair("FULL_DATE1", loc.getDateString("FULL_DATE"))};
        String source1 = MacroKit.Parse(text.text, pair1);
        KeyPair[] pairs1 = new KeyPair[]{new KeyPair("YEAR", "" + this.start_date.gYear()), new KeyPair("MONTH", Converts.makeClock(this.start_date.gMonth())), new KeyPair("DATE", Converts.makeClock(this.start_date.gDate())), new KeyPair("HOURS", Converts.makeClock(this.start_date.gHour())), new KeyPair("MINUTES", Converts.makeClock(this.start_date.gMinute())), new KeyPair("SOURCE", this.start), new KeyPair("DESTINATION", this.finish), new KeyPair("MONEY", "" + this.winmoney)};
        String source2 = MacroKit.Parse(source1, pairs1);
        KeyPair[] pair2 = new KeyPair[]{new KeyPair("FULL_DATE2", loc.getDateString("FULL_DATE"))};
        String source3 = MacroKit.Parse(source2, pair2);
        KeyPair[] pairs2 = new KeyPair[]{new KeyPair("YEAR", "" + this.finish_date.year), new KeyPair("MONTH", Converts.makeClock(this.finish_date.month)), new KeyPair("DATE", Converts.makeClock(this.finish_date.day))};
        text.text = final_text = MacroKit.Parse(source3, pairs2);
        menues.UpdateField(text);
        int texh = menues.GetTextLineHeight(text.nativePointer);
        int startbase = menues.GetBaseLine(text.nativePointer);
        int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
        int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, final_text), startbase, texh);
        this.scroller = new TextScroller(common, ranger, linecounter, linescreen, texh, startbase, false, "DOCUMENT - WELCOME TO MONSTER CUP - ARTICLE");
        this.scroller.AddTextControl(text);
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.SetMenagPOLOSY(_menu, false);
        menues.setShowMenu(_menu, true);
    }

    public void exitMenu(long _menu) {
        m_lastMenu = null;
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
        if (!this.f_buttonPressed) {
            result = false;
            EventsControllerHelper.messageEventHappened(msg_cancel);
            EventsControllerHelper.messageEventHappened(msg_menu_closed);
        }
    }

    public String getMenuId() {
        return "scenarioBigRaceConfirmationMENU";
    }
}

