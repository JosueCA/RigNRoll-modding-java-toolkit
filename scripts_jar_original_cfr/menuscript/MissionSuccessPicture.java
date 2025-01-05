/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.SMenu;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import rnrcore.CoreTime;
import scriptEvents.EventsControllerHelper;
import scriptEvents.SuccessEventPicture;

public class MissionSuccessPicture
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_documents.xml";
    private static final String GROUP = "Message TASK EXECUTED";
    private static final String BUTTON_OK = "OK";
    private static final String METHOD_OK = "onOk";
    private static final String TEXT_FIELD_MESSAGE = "Task Executed - PHOTO - TEXT";
    private static final String TEXT_FIELD_DATE = "Task Executed - PHOTO - DATE";
    private static final String TEXT_FIELD_MISSION_NAME = "BACK Icon - Incoming Message";
    private String tex_name = null;
    private String text_message = null;
    private String mission_name = null;
    private CoreTime date_time;

    public void restartMenu(long _menu) {
    }

    public MissionSuccessPicture(String mission_name, String text_message, CoreTime date_time, String tex_name) {
        this.mission_name = mission_name;
        this.text_message = text_message;
        this.date_time = date_time;
        this.tex_name = tex_name;
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
        long control = menues.FindFieldInMenu(_menu, BUTTON_OK);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, METHOD_OK, 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
        long text_field0 = menues.FindFieldInMenu(_menu, TEXT_FIELD_MISSION_NAME);
        long text_field1 = menues.FindFieldInMenu(_menu, TEXT_FIELD_DATE);
        long text_field2 = menues.FindFieldInMenu(_menu, TEXT_FIELD_MESSAGE);
        String xml_text_name = menues.GetFieldText(text_field0);
        KeyPair[] pairs = new KeyPair[]{new KeyPair("TASK", this.mission_name)};
        menues.SetFieldText(text_field0, MacroKit.Parse(xml_text_name, pairs));
        xml_text_name = menues.GetFieldText(text_field1);
        menues.SetFieldText(text_field1, Converts.ConvertDateAbsolute(xml_text_name, this.date_time.gMonth(), this.date_time.gDate(), this.date_time.gYear(), this.date_time.gHour(), this.date_time.gMinute()));
        menues.SetFieldText(text_field2, this.text_message);
    }

    public void AfterInitMenu(long _menu) {
        JavaEvents.SendEvent(62, 1, this);
        MenuAfterInitNarrator.justShowAndStop(_menu);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "missionSuccessMENU";
    }

    private void callEvent() {
        EventsControllerHelper.eventHappened(new SuccessEventPicture());
    }

    public void OnExit(long _menu, SMenu menu) {
        this.callEvent();
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void onOk(long _menu, MENUsimplebutton_field button) {
        this.callEvent();
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public static long create(String mission_name, String text_message, CoreTime date_time, String texture_name) {
        MissionSuccessPicture menu = new MissionSuccessPicture(mission_name, text_message, date_time, texture_name);
        return menues.createSimpleMenu((menucreation)menu, 1);
    }
}

