/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.JavaEvents;
import menu.ListOfAlternatives;
import menu.MENUsimplebutton_field;
import menu.RadioGroupSmartSwitch;
import menu.TableOfElements;
import menu.menues;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.SaveLoadCommonManagement;
import menuscript.mainmenu.TopWindow;
import rnrcore.loc;

public class SinglePlayerNewGame
extends PanelDialog {
    private static final String STARTGAME = "SINGLE PLAYER - NEW GAME START";
    private static final String STARTGAMEMETH = "OnStart";
    private static final String[] LODs = new String[]{loc.getMENUString("LOD_EASY"), loc.getMENUString("LOD_NORMAL"), loc.getMENUString("LOD_HARD")};
    private static final String SCENARIO_STATUS = loc.getMENUString("common\\Scenario");
    private static final String TITLE = loc.getMENUString("LOD TITLE");
    private static final String TABLE = "TABLEGROUP - SINGLE PLAYER - NEW GAME - 11 60";
    private TableOfElements table = null;

    public SinglePlayerNewGame(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        MENUsimplebutton_field startButton = menues.ConvertSimpleButton(parent.menu.findField(STARTGAME));
        menues.SetScriptOnControl(parent.menu._menu, startButton, this, STARTGAMEMETH, 4L);
        this.table = new TableOfElements(_menu, TABLE);
        ListOfAlternatives levelOfDifficulty = CA.createListOfAlternatives(TITLE, LODs, true);
        levelOfDifficulty.load(_menu);
        this.table.insert(levelOfDifficulty);
        this.param_values.addParametr("SINGLE PLAYER LOD", 0, 0, levelOfDifficulty);
        RadioGroupSmartSwitch scenarioStatus = CA.createRadioGroupSmartSwitch(SCENARIO_STATUS, true, true);
        scenarioStatus.load(_menu);
        this.table.insert(scenarioStatus);
        this.param_values.addParametr("SINGLE PLAYER SCENARIO STATUS", true, true, scenarioStatus);
        JavaEvents.SendEvent(12, 3, this.param_values);
    }

    public void OnStart(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(12, 4, this.param_values);
        SaveLoadCommonManagement.getSaveLoadCommonManager().SetStartNewGameFlag(1);
        TopWindow.quitTopMenu();
    }

    public void afterInit() {
        super.afterInit();
        this.table.initTable();
        this.param_values.onUpdate();
    }

    public void exitMenu() {
        this.table.DeInit();
        super.exitMenu();
    }
}

