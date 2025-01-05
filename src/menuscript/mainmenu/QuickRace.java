/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.MENUsimplebutton_field;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.QuickRaceGameOptions;
import menuscript.mainmenu.QuickRaceNewGame;
import menuscript.mainmenu.Quickrace_TopTen;

public class QuickRace
extends Panel {
    private static final String[] PANELS_GROUPS = new String[]{"QUICK RACE - NEW GAME", "QUICK RACE - TOP TEN", "QUICK RACE - GAME OPTIONS"};
    private static final String PANELNAME = "QUICK RACE";

    QuickRace(MainMenu menu) {
        super(menu, PANELNAME, PANELS_GROUPS);
        this.add(PANELS_GROUPS[0], new QuickRaceNewGame(menu._menu, menu.loadGroup(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0] + " Exit"), menu.findField(PANELS_GROUPS[0] + " DEFAULT"), menu.findField(PANELS_GROUPS[0] + " OK"), this));
        this.add(PANELS_GROUPS[1], new Quickrace_TopTen(menu._menu, menu.loadGroup(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1] + " Exit"), this));
        this.add(PANELS_GROUPS[2], new QuickRaceGameOptions(menu._menu, menu.loadGroup(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2] + " Exit"), menu.findField(PANELS_GROUPS[2] + " DEFAULT"), menu.findField(PANELS_GROUPS[2] + " OK"), this));
    }

    void init() {
        super.init();
    }

    public void OnAction(long _menu, MENUsimplebutton_field button) {
        super.OnAction(_menu, button);
    }
}

