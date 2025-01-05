/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.SMenu;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.SettingsAudio;

public class SettingsAudioESC
extends SettingsAudio {
    private static String filename;
    private static String prev_table_device;
    private static String prev_table_settings;
    private static String _TABLE_DEVICE;
    private static String _TABLE_SETTINGS;
    private static String _FILE;

    private static void changeTableStrings() {
        prev_table_device = TABLE_DEVICE;
        prev_table_settings = TABLE_SETTINGS;
        filename = CA.FILENAME;
        TABLE_DEVICE = _TABLE_DEVICE;
        TABLE_SETTINGS = _TABLE_SETTINGS;
        CA.FILENAME = _FILE;
    }

    private static void changeBACK() {
        TABLE_DEVICE = prev_table_device;
        TABLE_SETTINGS = prev_table_settings;
        CA.FILENAME = filename;
    }

    public static SettingsAudioESC create(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        SettingsAudioESC.changeTableStrings();
        SettingsAudioESC result = new SettingsAudioESC(_menu, exitButton, defaultButton, okButton, applyButton);
        SettingsAudioESC.changeBACK();
        return result;
    }

    private SettingsAudioESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, new long[0], 0L, exitButton, defaultButton, okButton, applyButton, new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
    }

    public void OnExit(long _menu, SMenu window) {
    }

    static {
        prev_table_device = "TABLEGROUP - SETTINGS - AUDIO - 1 60";
        prev_table_settings = "TABLEGROUP AUDIO 02 - 5 65";
        _TABLE_DEVICE = "TABLEGROUP - SETTINGS - AUDIO - 1 60";
        _TABLE_SETTINGS = "TABLEGROUP AUDIO 02 - 5 65";
        _FILE = "..\\data\\config\\menu\\menu_esc.xml";
    }
}

