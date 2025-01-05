/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.RadioGroupSmartSwitch;
import menu.SMenu;
import menu.TableOfElements;
import menu.menues;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import rnrcore.loc;

public class RaplayGameOptionsESC
extends PanelDialog {
    private static final String OK = "BUTT - REPLAY OPTIONS - OK";
    private static final String DEFAULT = "BUTT - REPLAY OPTIONS - DEFAULT";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    protected static String TABLE = "TABLEGROUP REPLAY OPTIONS 02 - 2 65";
    protected static String RANGER = null;
    private static final String TITLE_REPEAT_FOREVER = "OPTION_REPLAY_REPEAT_FOREVER";
    TableOfElements table = null;

    public static RaplayGameOptionsESC create(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        RaplayGameOptionsESC result = new RaplayGameOptionsESC(_menu, exitButton, defaultButton, okButton, applyButton);
        return result;
    }

    private RaplayGameOptionsESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, 0L, new long[0], exitButton, defaultButton, okButton, applyButton, new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
        this.table = new TableOfElements(_menu, TABLE, RANGER);
        String filename = CA.FILENAME;
        CA.FILENAME = "..\\data\\config\\menu\\menu_esc.xml";
        RadioGroupSmartSwitch repeat_replay = CA.createRadioGroupSmartSwitch(loc.getMENUString(TITLE_REPEAT_FOREVER), true, true);
        repeat_replay.load(_menu);
        this.table.insert(repeat_replay);
        this.param_values.addParametr("OPTION REPLAY REPEAT FOREVER", false, false, repeat_replay);
        CA.FILENAME = filename;
        JavaEvents.SendEvent(74, 0, this.param_values);
        MENUsimplebutton_field ok_button = menues.ConvertSimpleButton(this.parent.menu.findField(OK));
        MENUsimplebutton_field default_button = menues.ConvertSimpleButton(this.parent.menu.findField(DEFAULT));
        menues.SetScriptOnControl(_menu, ok_button, this, OKMETHOD, 4L);
        menues.SetScriptOnControl(_menu, default_button, this, DEFAULTMETHOD, 4L);
    }

    public void afterInit() {
        this.table.initTable();
        this.param_values.onUpdate();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(75, 0, this.param_values);
        this.exitDialog();
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(75, 0, this.param_values);
    }

    public void update() {
        JavaEvents.SendEvent(74, 0, this.param_values);
        super.update();
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        this.update();
    }

    public void OnExit(long _menu, SMenu window) {
    }

    public void exitMenu() {
        this.table.DeInit();
        super.exitMenu();
    }

    public void readParamValues() {
        JavaEvents.SendEvent(75, 0, this.param_values);
        this.param_values.onUpdate();
        JavaEvents.SendEvent(75, 0, this.param_values);
    }
}

