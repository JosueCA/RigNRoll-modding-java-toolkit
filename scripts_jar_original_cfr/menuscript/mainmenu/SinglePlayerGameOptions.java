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
import rnrcore.eng;
import rnrcore.loc;

public class SinglePlayerGameOptions
extends PanelDialog {
    private static final String OK = "SINGLE PLAYER - GAME OPTIONS OK";
    private static final String DEFAULT = "SINGLE PLAYER - GAME OPTIONS DEFAULT";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    protected static String TABLE = "TABLEGROUP - SINGLE PLAYER - GAME OPTIONS - 11 60";
    protected static String RANGER = "Tableranger - SINGLE PLAYER - GAME OPTIONS";
    private static final String TITLE_TRAFFIC = "TRAFFIC LEVEL";
    private static final String TITLE_TUTORIAL = "OPTION TUTORIAL";
    private static final String TITLE_CUTSCENES = "OPTION CUTSCENES";
    private static final String TITLE_MAPORIENTATION = "OPTION MAPORIENTATION";
    private static final String[] MAPORIENTATIONS = new String[]{loc.getMENUString("ORIENTATION FORWARD"), loc.getMENUString("ORIENTATION NORTH")};
    TableOfElements table = null;
    public int game_type = 0;

    public SinglePlayerGameOptions(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent, int _game_type, boolean bIsEscMenu) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        this.game_type = _game_type;
        this.table = new TableOfElements(_menu, TABLE, RANGER);
        SliderGroupRadioButtons trafficLevel = CA.createSliderRadioButtons(loc.getMENUString(TITLE_TRAFFIC), 0, 100, 50, true);
        RadioGroupSmartSwitch tutorial = null;
        if (this.game_type == 0 && !bIsEscMenu) {
            tutorial = eng.getScenarioStatus() ? CA.createRadioGroupSmartSwitch(loc.getMENUString(TITLE_TUTORIAL), true, true) : CA.createRadioGroupSmartSwitchGray(loc.getMENUString(TITLE_TUTORIAL), false, false);
        }
        RadioGroupSmartSwitch cutscenes = CA.createRadioGroupSmartSwitch(loc.getMENUString(TITLE_CUTSCENES), true, true);
        ListOfAlternatives map_orientations = CA.createListOfAlternatives(loc.getMENUString(TITLE_MAPORIENTATION), MAPORIENTATIONS, true);
        trafficLevel.load(_menu);
        if (this.game_type == 0 && !bIsEscMenu && null != tutorial) {
            tutorial.load(_menu);
        }
        cutscenes.load(_menu);
        map_orientations.load(_menu);
        this.table.insert(trafficLevel);
        if (this.game_type == 0 && !bIsEscMenu) {
            this.table.insert(tutorial);
        }
        this.table.insert(cutscenes);
        this.table.insert(map_orientations);
        if (this.game_type == 2) {
            this.param_values.addParametr("QUICK RACE TRAFFIC", 50, 50, trafficLevel);
            this.param_values.addParametr("QUICK RACE CUTSCENES", true, true, cutscenes);
            this.param_values.addParametr("QUICK RACE MAPORIENTATION", 0, 0, map_orientations);
        } else if (this.game_type == 0) {
            this.param_values.addParametr("SINGLE PLAYER TRAFFIC", 50, 50, trafficLevel);
            if (!bIsEscMenu) {
                if (eng.getScenarioStatus()) {
                    this.param_values.addParametr(TITLE_TUTORIAL, true, true, tutorial);
                } else {
                    this.param_values.addParametr(TITLE_TUTORIAL, false, false, tutorial);
                }
            }
            this.param_values.addParametr(TITLE_CUTSCENES, true, true, cutscenes);
            this.param_values.addParametr(TITLE_MAPORIENTATION, 0, 0, map_orientations);
        }
        JavaEvents.SendEvent(12, this.game_type, this.param_values);
        MENUsimplebutton_field ok_button = menues.ConvertSimpleButton(parent.menu.findField(OK));
        MENUsimplebutton_field default_button = menues.ConvertSimpleButton(parent.menu.findField(DEFAULT));
        menues.SetScriptOnControl(_menu, ok_button, this, OKMETHOD, 4L);
        menues.SetScriptOnControl(_menu, default_button, this, DEFAULTMETHOD, 4L);
    }

    public void afterInit() {
        this.table.initTable();
        this.param_values.onUpdate();
    }

    public void exitMenu() {
        this.table.DeInit();
        super.exitMenu();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(13, this.game_type, this.param_values);
        this.exitDialog();
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(13, this.game_type, this.param_values);
    }

    public void update() {
        JavaEvents.SendEvent(12, this.game_type, this.param_values);
        super.update();
    }

    public void readParamValues() {
        JavaEvents.SendEvent(12, this.game_type, this.param_values);
        this.param_values.onUpdate();
        JavaEvents.SendEvent(13, this.game_type, this.param_values);
    }
}

