/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.MENUsimplebutton_field;
import menu.SMenu;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.RemapControlsTable;
import menuscript.mainmenu.SettingsControls;

public class SettingsControlsESC
extends SettingsControls {
    private static String prev_table;
    private static String prev_table_settings;
    private static int num_tables;
    private static String[] remap_tables;
    private static String filename;
    private static String prev_popup;
    private static String prev_popupwnd;

    private static void changeTableStrings() {
        prev_table = TABLE;
        prev_table_settings = TABLE_SETTINGS;
        num_tables = RemapControlsTable.st_NUM_TABLES;
        remap_tables = RemapControlsTable.TABLES;
        prev_popup = POPUP;
        prev_popupwnd = POPUPWND;
        filename = CA.FILENAME;
        TABLE = "TABLEGROUP - SETTINGS - CONTROLS - 1 60";
        TABLE_SETTINGS = "TABLEGROUP - SETTINGS - CONTROLS - 4 65";
        RemapControlsTable.TABLES = new String[]{"TABLEGROUP - SETTINGS - CONTROLS 01 - 8 38"};
        RemapControlsTable.st_NUM_TABLES = 1;
        POPUP = "Tablegroup - SETTINGS - CONTROLS - CONFIRM MESSAGE";
        POPUPWND = null;
        CA.FILENAME = "..\\data\\config\\menu\\menu_esc.xml";
    }

    private static void changeBACK() {
        TABLE = prev_table;
        TABLE_SETTINGS = prev_table_settings;
        RemapControlsTable.st_NUM_TABLES = num_tables;
        RemapControlsTable.TABLES = remap_tables;
        POPUP = prev_popup;
        POPUPWND = prev_popupwnd;
        CA.FILENAME = filename;
    }

    public static SettingsControlsESC create(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        SettingsControlsESC.changeTableStrings();
        SettingsControlsESC result = new SettingsControlsESC(_menu, exitButton, defaultButton, okButton, applyButton);
        SettingsControlsESC.changeBACK();
        return result;
    }

    private SettingsControlsESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, new long[0], 0L, exitButton, defaultButton, okButton, applyButton, new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        this.update();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        super.OnOk(_menu, button);
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        super.OnApply(_menu, button);
    }

    public void OnExit(long _menu, SMenu window) {
    }

    public void exitMenu() {
        this.remap_table.deinit();
        super.exitMenu();
    }
}

