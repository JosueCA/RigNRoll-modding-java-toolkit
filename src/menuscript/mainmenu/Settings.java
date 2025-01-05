/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.SettingsAudio;
import menuscript.mainmenu.SettingsControls;
import menuscript.mainmenu.SettingsVideo;

public class Settings
extends Panel {
    private static final String[] PANELS_GROUPS = new String[]{"SETTINGS - CONTROLS", "SETTINGS - AUDIO", "SETTINGS - VIDEO"};
    private static final String PANELNAME = "SETTINGS";

    Settings(MainMenu menu) {
        super(menu, PANELNAME, PANELS_GROUPS);
        this.add(PANELS_GROUPS[0], new SettingsControls(menu._menu, menu.loadGroup(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0] + " Exit"), menu.findField(PANELS_GROUPS[0] + " DEFAULT"), menu.findField(PANELS_GROUPS[0] + " OK"), 0L, (Panel)this));
        this.add(PANELS_GROUPS[1], new SettingsAudio(menu._menu, menu.loadGroup(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1] + " Exit"), menu.findField(PANELS_GROUPS[1] + " DEFAULT"), menu.findField(PANELS_GROUPS[1] + " OK"), 0L, (Panel)this));
        this.add(PANELS_GROUPS[2], new SettingsVideo(menu._menu, menu.loadGroup(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2] + " Exit"), menu.findField(PANELS_GROUPS[2] + " DEFAULT"), menu.findField(PANELS_GROUPS[2] + " OK"), 0L, (Panel)this));
    }

    public void restoreLastState() {
        super.restoreLastState();
    }
}

