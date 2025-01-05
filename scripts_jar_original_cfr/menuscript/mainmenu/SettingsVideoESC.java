/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menuscript.IresolutionChanged;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.SettingsVideo;

public class SettingsVideoESC
extends SettingsVideo {
    private static String filename;
    private static String prev_table_device;
    private static String prev_table_settings;
    private static String prev_ranger;
    private static String prev_warning;
    private static String prev_warning_wnd;
    private static final String _TABLE_DEVICE = "TABLEGROUP - SETTINGS - VIDEO - 1 60";
    private static final String _TABLE_SETTINGS = "TABLEGROUP VIDEO 02 - 5 65";
    private static final String _RANGER = "STRING - VIDEO - Tableranger";
    private static final String _FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String _WARNING = "Tablegroup - SETTINGS - VIDEO - CONFIRM MESSAGE";
    private static final String _WARNING_WND = "PopUpWarning - RESOLUTIONCHANGED";
    private ArrayList<IresolutionChanged> listeners = new ArrayList();

    public void addListener(IresolutionChanged listener) {
        this.listeners.add(listener);
    }

    private static void changeTableStrings() {
        prev_table_device = TABLE_DEVICE;
        prev_table_settings = TABLE_SETTINGS;
        prev_ranger = RANGER;
        prev_warning = WARNING;
        prev_warning_wnd = WARNING_WND;
        filename = CA.FILENAME;
        TABLE_DEVICE = _TABLE_DEVICE;
        TABLE_SETTINGS = _TABLE_SETTINGS;
        RANGER = _RANGER;
        WARNING = _WARNING;
        WARNING_WND = _WARNING_WND;
        CA.FILENAME = _FILE;
    }

    private static void changeBACK() {
        TABLE_DEVICE = prev_table_device;
        TABLE_SETTINGS = prev_table_settings;
        RANGER = prev_ranger;
        WARNING = prev_warning;
        WARNING_WND = prev_warning_wnd;
        CA.FILENAME = filename;
    }

    public static SettingsVideoESC create(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        SettingsVideoESC.changeTableStrings();
        SettingsVideoESC result = new SettingsVideoESC(_menu, exitButton, defaultButton, okButton, applyButton);
        SettingsVideoESC.changeBACK();
        return result;
    }

    private SettingsVideoESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, new long[0], 0L, exitButton, defaultButton, okButton, applyButton, new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        this.update();
    }

    public void OnExit(long _menu, SMenu window) {
    }

    public void exitMenu() {
        this.listeners = null;
        super.exitMenu();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        if (this.res_changed) {
            this.callResolutionChanged();
        }
        super.OnOk(_menu, button);
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        if (this.res_changed) {
            this.callResolutionChanged();
        }
        super.OnApply(_menu, button);
    }

    public void onClose() {
        this.callResolutionChanged();
        super.onClose();
    }

    public void onAgreeclose() {
        super.onAgreeclose();
    }

    private void callResolutionChanged() {
        Iterator<IresolutionChanged> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().changed();
        }
    }
}

