/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.MENUsimplebutton_field;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.LoadGameTable;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.SinglePlayerGameOptions;
import menuscript.mainmenu.SinglePlayerLoadGame;
import menuscript.mainmenu.SinglePlayerNewGame;

public class SinglePlayer
extends Panel {
    private static final String[] PANELS_GROUPS = new String[]{"SINGLE PLAYER - NEW GAME", "SINGLE PLAYER - LOAD GAME", "SINGLE PLAYER - GAME OPTIONS"};
    private static final String PANELNAME = "SINGLE PLAYER";
    private static final int LOAD_GAME_PANEL = 1;
    private static final String XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String CANNOTSHOW_GROUP = "Tablegroup - SINGLE PLAYER - LOAD GAME - NO FILES FOUND";
    private static final String CANNOTSHOW_WINDOW = "SINGLE PLAYER - LOAD GAME - NoFilesFound";
    private PoPUpMenu info_cannotshow = null;

    SinglePlayer(MainMenu menu) {
        super(menu, PANELNAME, PANELS_GROUPS);
        this.add(PANELS_GROUPS[0], new SinglePlayerNewGame(menu._menu, menu.loadGroup(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0] + " Exit"), menu.findField(PANELS_GROUPS[0] + " DEFAULT"), menu.findField(PANELS_GROUPS[0] + " OK"), 0L, (Panel)this));
        this.add(PANELS_GROUPS[1], new SinglePlayerLoadGame(menu._menu, menu.loadGroup(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1] + " Exit"), menu.findField(PANELS_GROUPS[1] + " DEFAULT"), menu.findField(PANELS_GROUPS[1] + " OK"), 0L, (Panel)this));
        this.add(PANELS_GROUPS[2], new SinglePlayerGameOptions(menu._menu, menu.loadGroup(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2] + " Exit"), menu.findField(PANELS_GROUPS[2] + " DEFAULT"), menu.findField(PANELS_GROUPS[2] + " OK"), 0L, this, 0, false));
        this.info_cannotshow = new PoPUpMenu(menu._menu, XML_NAME, CANNOTSHOW_GROUP, CANNOTSHOW_WINDOW);
        this.info_cannotshow.addListener(new InWarning());
    }

    void init() {
        super.init();
        this.info_cannotshow.afterInit();
    }

    public void OnAction(long _menu, MENUsimplebutton_field button) {
        String name_dialog = (String)this.dials_on_buttons.get(new Integer(button.userid));
        if (name_dialog.compareTo(PANELS_GROUPS[1]) == 0) {
            if (!LoadGameTable.isLoadgameListEmpty(1, false)) {
                super.OnAction(_menu, button);
            } else {
                this.info_cannotshow.show();
            }
        } else {
            super.OnAction(_menu, button);
        }
    }

    class InWarning
    implements IPoPUpMenuListener {
        InWarning() {
        }

        public void onAgreeclose() {
        }

        public void onClose() {
        }

        public void onOpen() {
        }

        public void onCancel() {
        }
    }
}

