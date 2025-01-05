/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.Vector;
import menu.Common;
import menu.JavaEvents;
import menu.ListenerManager;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menucreation;
import menu.menues;
import menuscript.ConfirmDialog;
import menuscript.EscapeMainMenu;
import menuscript.EscapeOptions;
import menuscript.EscapeQuit;
import menuscript.IMenuListener;
import menuscript.IQuitMenu;
import menuscript.ISubMenu;
import menuscript.IresolutionChanged;
import menuscript.ParentMenu;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.DLCShopMenu.EscapeDLCShopMenu;
import menuscript.mainmenu.EscapeSaveLoad;
import menuscript.mainmenu.EscapeSaveLoadReplay;
import menuscript.mainmenu.TopWindow;
import rnrcore.Log;
import rnrcore.eng;
import rnrscenario.ScenarioFlagsManager;
import rnrscr.gameinfo;

public class EscapeMenu
extends ParentMenu
implements menucreation,
IresolutionChanged,
IQuitMenu {
    EscapeDLCShopMenu dlcShopMenu;
    static final int TAB_MAIN = 0;
    static final int TAB_RESTART = 1;
    static final int TAB_SAVELOAD = 2;
    static final int TAB_OPTIONS = 3;
    static final int TAB_QUIT = 4;
    static final int TAB_ROADSERVICE = 5;
    static final int TAB_BACK = 6;
    static final int TAB_DLCSHOP = 7;
    static final int TAB_MAX = 8;
    public static final int GAME_SINGLE_PLAYER = 0;
    public static final int GAME_MULTI_PLAYER = 1;
    public static final int GAME_INSTANT_ORDER = 2;
    public static final int GAME_REPLAY = 3;
    private static int game_type = 0;
    private static String[] s_single_buttonnames = new String[]{"Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Save/Load", "Tab0 - Options", "Tab0 - Quit Game", "Tab0 - Road Service", "Tab0 - Back", "Button DLC STORE"};
    private static String[] s_single_graybuttonnames = new String[]{"Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Save/Load GRAY", "Tab0 - Options", "Tab0 - Quit Game", "Tab0 - Road Service GRAY", "Tab0 - Back", "Button DLC STORE"};
    private static String[] s_replay_buttonnames = new String[]{"Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Load", "Tab0 - Options", "Tab0 - Quit Game", "Tab0 - Road Service", "Tab0 - Back", "Button DLC STORE"};
    private static String[] s_replay_graybuttonnames = new String[]{"Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Load", "Tab0 - Options", "Tab0 - Quit Game", "Tab0 - Road Service GRAY", "Tab0 - Back", "Button DLC STORE"};
    private static boolean[] s_buttons_gray = new boolean[]{false, false, false, false, false, false, false, false};
    public static final int ID_SAVE = 0;
    Vector m_Saves = new Vector();
    ConfirmDialog m_cdialog;
    private boolean f_initCompleted = false;
    public static boolean created = false;
    private static boolean f_changeVideoResolution = false;

    public static void SetGameType(long type) {
        if (type == 0L) {
            EscapeMenu.s_buttons_gray[2] = false;
            EscapeMenu.s_buttons_gray[5] = false;
            game_type = 0;
        } else if (type == 1L) {
            EscapeMenu.s_buttons_gray[2] = false;
            EscapeMenu.s_buttons_gray[5] = false;
            game_type = 1;
        } else if (type == 2L) {
            EscapeMenu.s_buttons_gray[2] = true;
            EscapeMenu.s_buttons_gray[5] = false;
            game_type = 2;
        } else if (type == 3L) {
            EscapeMenu.s_buttons_gray[2] = false;
            EscapeMenu.s_buttons_gray[5] = true;
            game_type = 3;
        } else if (type == 4L) {
            EscapeMenu.s_buttons_gray[2] = true;
            EscapeMenu.s_buttons_gray[5] = false;
            game_type = 0;
        }
        if (game_type == 0 && !s_buttons_gray[2] && ScenarioFlagsManager.getInstance() != null && !ScenarioFlagsManager.getInstance().getFlagValue("SavesEnabledByScenario")) {
            EscapeMenu.s_buttons_gray[2] = true;
        }
    }

    public void InitMenu(long _menu) {
        int i;
        TopWindow.Register(this);
        this.uiTools = new Common(_menu);
        ListenerManager.TriggerEvent(103);
        menues.InitXml(_menu, Common.ConstructPath("menu_esc.xml"), "Menu Esc");
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        JavaEvents.SendEvent(2, 0, this);
        this.m_cdialog = new ConfirmDialog(this.uiTools, "TabSwitchWarning - REMAP", "TabSwitchWarning - REMAP - TEXT", "BUTT - TabSwitchWarning - REMAP - YES", "BUTT - TabSwitchWarning - REMAP - NO", "BUTT - TabSwitchWarning - REMAP - CANCEL");
        int NUM_BUTTONS = game_type != 3 ? s_single_buttonnames.length : s_replay_buttonnames.length;
        ISubMenu[] tab_menues = new ISubMenu[NUM_BUTTONS];
        for (i = 0; i < NUM_BUTTONS; ++i) {
            if (i == 3) {
                EscapeOptions settings = new EscapeOptions(_menu, this.uiTools, this.m_cdialog, game_type);
                settings.addListener(new MenuClose(i));
                tab_menues[i] = settings;
                continue;
            }
            if (i == 4) {
                EscapeQuit quit = new EscapeQuit(_menu);
                quit.addListener(new MenuClose(i));
                tab_menues[i] = quit;
                continue;
            }
            if (i == 0) {
                EscapeMainMenu mmenu = new EscapeMainMenu(_menu);
                mmenu.addListener(new MenuClose(i));
                tab_menues[i] = mmenu;
                continue;
            }
            if (i == 2) {
                WindowParentMenu saveload;
                if (game_type != 3) {
                    saveload = new EscapeSaveLoad(_menu, this.uiTools, this.m_cdialog);
                    saveload.addListener(new MenuClose(i));
                    tab_menues[i] = saveload;
                    continue;
                }
                saveload = new EscapeSaveLoadReplay(_menu, this.uiTools, this.m_cdialog);
                saveload.addListener(new MenuClose(i));
                tab_menues[i] = saveload;
                continue;
            }
            if (i == 1) {
                tab_menues[i] = null;
                continue;
            }
            if (i == 7) {
                this.dlcShopMenu = new EscapeDLCShopMenu(_menu);
                this.dlcShopMenu.addListener(new MenuClose(i));
                tab_menues[i] = this.dlcShopMenu;
                continue;
            }
            tab_menues[i] = null;
        }
        for (i = 0; i < NUM_BUTTONS; ++i) {
            if (game_type != 3) {
                if (s_buttons_gray[i]) {
                    this.addButton(this.uiTools.FindRadioButton(s_single_graybuttonnames[i]), tab_menues[i]);
                    continue;
                }
                this.addButton(this.uiTools.FindRadioButton(s_single_buttonnames[i]), tab_menues[i]);
                continue;
            }
            if (s_buttons_gray[i]) {
                this.addButton(this.uiTools.FindRadioButton(s_replay_graybuttonnames[i]), tab_menues[i]);
                continue;
            }
            this.addButton(this.uiTools.FindRadioButton(s_replay_buttonnames[i]), tab_menues[i]);
        }
        this.initSubMenues();
        EscapeOptions esc_op = (EscapeOptions)tab_menues[3];
        esc_op.addVideoSettingsListener(this);
    }

    public void AfterInitMenu(long _menu) {
        this.f_initCompleted = true;
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
        this.afterInitSubMenues();
        ListenerManager.TriggerEvent(104);
        menues.setShowMenu(_menu, true);
        menues.SetStopWorld(_menu, true);
        if (eng._dontShowDLCButton) {
            EscapeMenu.s_buttons_gray[7] = true;
        }
        if (game_type != 3) {
            int i;
            for (i = 0; i < s_single_buttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[i]), false);
            }
            for (i = 0; i < s_single_graybuttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), false);
            }
            for (i = 0; i < s_buttons_gray.length; ++i) {
                if (s_buttons_gray[i]) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), true);
                    menues.SetIgnoreEvents(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), true);
                    menues.SetBlindess(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), true);
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[i]), false);
                    continue;
                }
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), false);
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[i]), true);
            }
            menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[1]), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[1]), false);
        } else {
            int i;
            for (i = 0; i < s_single_buttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[i]), false);
            }
            for (i = 0; i < s_single_graybuttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), false);
            }
            for (i = 0; i < s_buttons_gray.length; ++i) {
                if (s_buttons_gray[i]) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), true);
                    menues.SetIgnoreEvents(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), true);
                    menues.SetBlindess(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), true);
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[i]), false);
                    continue;
                }
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), false);
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[i]), true);
            }
            menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[1]), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[1]), false);
        }
        menues.setFocusWindow(menues.getMenuID(_menu));
        if (f_changeVideoResolution) {
            this.selectButton(3);
            menues.SetFieldState(menues.FindFieldInMenu(_menu, "Tab0 - VIDEO"), 1);
            f_changeVideoResolution = false;
        } else {
            menues.setfocuscontrolonmenu(_menu, (Long)this.buttons.get(5));
        }
    }

    private void SimpleButtonCheck(int m_curtab) {
        if (m_curtab == 5) {
            JavaEvents.SendEvent(6, 0, this);
            menues.CallMenuCallBack_ExitMenu(this.uiTools.GetMenu());
        } else if (m_curtab == 6) {
            menues.CallMenuCallBack_ExitMenu(this.uiTools.GetMenu());
        }
    }

    public void OnButton(long _menu, MENUbutton_field tab) {
        if (!this.f_initCompleted) {
            return;
        }
        super.OnButton(_menu, tab);
        if (!this.buttonPending) {
            this.SimpleButtonCheck(tab.userid);
        }
    }

    public void exitMenu(long _menu) {
        TopWindow.UnRegister(this);
        created = false;
        super.exitMenu();
        JavaEvents.SendEvent(3, 0, this);
        ListenerManager.TriggerEvent(105);
        this.buttons = null;
        this.dlcShopMenu.deinit();
    }

    public void restartMenu(long _menu) {
    }

    public String getMenuId() {
        return "escMENU";
    }

    public void On911(long _menu, MENUsimplebutton_field button) {
        long pplayer = gameinfo.script.m_pPlayer;
        EscapeMenu.RepairPlayer(pplayer);
    }

    private EscapeMenu() {
    }

    public static long CreateEscapeMenu() {
        if (!created) {
            created = true;
            return menues.createSimpleMenu(new EscapeMenu(), 1.0E7, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        }
        return 0L;
    }

    void RefreshGameData(int value) {
        JavaEvents.SendEvent(0, value, this);
    }

    void UpdateGameData(int value) {
        JavaEvents.SendEvent(1, value, this);
    }

    public static native void RepairPlayer(long var0);

    void NeedToConfirm(String text) {
        this.m_cdialog.AskUser(this, text);
    }

    public void changed() {
        f_changeVideoResolution = true;
    }

    public void OnExit(long _menu, SMenu menu) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void quitMenu() {
        menues.CallMenuCallBack_ExitMenu(this.uiTools.s_menu);
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    class MenuClose
    implements IMenuListener {
        public int nom_tab;

        MenuClose(int nom_tab) {
            this.nom_tab = nom_tab;
            if (nom_tab >= 8) {
                Log.menu("MenuClose asked to act with wrong tab. nomtab = " + nom_tab);
            }
        }

        public void onClose() {
            EscapeMenu.this.makeNonActiveState();
            menues.setfocuscontrolonmenu(EscapeMenu.this.uiTools.GetMenu(), (Long)EscapeMenu.this.buttons.get(6));
        }

        public void onOpen() {
        }
    }

    static class SaveItem {
        String name;

        SaveItem(String _name) {
            this.name = _name;
        }
    }
}

