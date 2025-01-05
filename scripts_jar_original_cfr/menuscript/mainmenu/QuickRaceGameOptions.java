/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.JavaEvents;
import menu.ListOfAlternatives;
import menu.MENUsimplebutton_field;
import menu.RadioGroupSmartSwitch;
import menu.SliderGroupRadioButtons;
import menu.TableOfElements;
import menu.menues;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import rnrcore.loc;

public class QuickRaceGameOptions
extends PanelDialog {
    private static final String OK = "QUICK RACE - GAME OPTIONS OK";
    private static final String DEFAULT = "QUICK RACE - GAME OPTIONS DEFAULT";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    protected static String TABLE = "TABLEGROUP - QUICK RACE - GAME OPTIONS - 11 60";
    protected static String RANGER = "Tableranger - QUICK RACE - GAME OPTIONS";
    private static final String TITLE_TRAFFIC = "TRAFFIC LEVEL";
    private static final String TITLE_CUTSCENES = "OPTION CUTSCENES";
    private static final String TITLE_MAPORIENTATION = "OPTION MAPORIENTATION";
    private static final String[] MAPORIENTATIONS = new String[]{loc.getMENUString("ORIENTATION FORWARD"), loc.getMENUString("ORIENTATION NORTH")};
    TableOfElements table = null;
    SliderGroupRadioButtons trafficLevel = null;
    RadioGroupSmartSwitch cutscenes = null;

    public void exitMenu() {
        this.table.DeInit();
        super.exitMenu();
    }

    public QuickRaceGameOptions(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this.table = new TableOfElements(_menu, TABLE, RANGER);
        SliderGroupRadioButtons trafficLevel = CA.createSliderRadioButtons(loc.getMENUString(TITLE_TRAFFIC), 0, 100, 50, true);
        RadioGroupSmartSwitch cutscenes = CA.createRadioGroupSmartSwitch(loc.getMENUString(TITLE_CUTSCENES), true, true);
        ListOfAlternatives map_orientations = CA.createListOfAlternatives(loc.getMENUString(TITLE_MAPORIENTATION), MAPORIENTATIONS, true);
        trafficLevel.load(_menu);
        cutscenes.load(_menu);
        map_orientations.load(_menu);
        this.table.insert(trafficLevel);
        this.table.insert(cutscenes);
        this.table.insert(map_orientations);
        this.param_values.addParametr("QUICK RACE TRAFFIC", 50, 50, trafficLevel);
        this.param_values.addParametr("QUICK RACE CUTSCENES", true, true, cutscenes);
        this.param_values.addParametr("QUICK RACE MAPORIENTATION", 0, 0, map_orientations);
        JavaEvents.SendEvent(65, 5, this.param_values);
        MENUsimplebutton_field ok_button = menues.ConvertSimpleButton(parent.menu.findField(OK));
        MENUsimplebutton_field default_button = menues.ConvertSimpleButton(parent.menu.findField(DEFAULT));
        menues.SetScriptOnControl(_menu, ok_button, this, OKMETHOD, 4L);
        menues.SetScriptOnControl(_menu, default_button, this, DEFAULTMETHOD, 4L);
    }

    public void afterInit() {
        this.table.initTable();
        this.param_values.onUpdate();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(65, 6, this.param_values);
        this.exitDialog();
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
    }

    public void update() {
        super.update();
    }

    public void readParamValues() {
        JavaEvents.SendEvent(65, 5, this.param_values);
        this.param_values.onUpdate();
    }
}

