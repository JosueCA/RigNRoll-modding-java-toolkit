/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.ProfileNewProfile;
import menuscript.mainmenu.ProfileSelectProfile;

public class Profile
extends Panel {
    private static final String[] PANELS_GROUPS = new String[]{"PROFILE - NEW PROFILE", "PROFILE - SELECT PROFILE"};
    private static final int eNEWPROFILE = 0;
    private static final int eSELECTPROFILE = 1;
    private static final String PANELNAME = "PROFILE";

    Profile(MainMenu menu) {
        super(menu, PANELNAME, PANELS_GROUPS);
        for (int i = 0; i < PANELS_GROUPS.length; ++i) {
            PanelDialog pd = null;
            switch (i) {
                case 0: {
                    pd = new ProfileNewProfile(menu._menu, menu.loadGroup(PANELS_GROUPS[i]), menu.findField(PANELS_GROUPS[i]), menu.findField(PANELS_GROUPS[i] + " Exit"), menu.findField(PANELS_GROUPS[i] + " DEFAULT"), menu.findField(PANELS_GROUPS[i] + " OK"), this);
                    break;
                }
                case 1: {
                    pd = new ProfileSelectProfile(menu._menu, menu.loadGroup(PANELS_GROUPS[i]), menu.findField(PANELS_GROUPS[i]), menu.findField(PANELS_GROUPS[i] + " Exit"), menu.findField(PANELS_GROUPS[i] + " DEFAULT"), menu.findField(PANELS_GROUPS[i] + " OK"), this);
                }
            }
            this.add(PANELS_GROUPS[i], pd);
        }
    }
}

