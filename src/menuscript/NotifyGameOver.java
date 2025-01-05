/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.BaseMenu;
import menu.Common;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MacroKit;
import menu.menucreation;
import menu.menues;
import players.Crew;
import rnrcore.event;
import rnrscenario.scenarioscript;
import rnrscr.ILeaveMenuListener;

public class NotifyGameOver
extends BaseMenu
implements menucreation {
    public static final int GAMEOVER = 0;
    public static final int GAMEOVER_JAIL = 1;
    public static final int GAMEOVER_MURDER = 2;
    public static final int GAMEOVER_BANKRUPT = 3;
    public static final int GAMEOVER_BLACK_SCREEN = 4;
    static Common s_common;
    static NotifyGameOver s_this;
    static int s_gameovertype;
    private ILeaveMenuListener leaveMenuListener = null;

    public void restartMenu(long _menu) {
    }

    public static long CreateNotifyGameOver(String exitkey, double duration, int gameovertype, ILeaveMenuListener listener) {
        s_gameovertype = gameovertype;
        return menues.createSimpleMenu(new NotifyGameOver(listener), duration, exitkey, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateNotifyGameOver(String exitkey, double duration, int gameovertype) {
        s_gameovertype = gameovertype;
        return menues.createSimpleMenu(new NotifyGameOver(), duration, exitkey, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    static void KillOrderMenu() {
        if (s_this != null) {
            menues.CallMenuCallBack_ExitMenu(s_common.GetMenu());
        }
    }

    NotifyGameOver() {
    }

    NotifyGameOver(ILeaveMenuListener listener) {
        this.leaveMenuListener = listener;
    }

    public void InitMenu(long _menu) {
        s_this = this;
        s_common = this.uiTools = new Common(_menu);
        String cgroup = "Message GAMEOVER";
        switch (s_gameovertype) {
            case 0: {
                cgroup = "Message GAMEOVER";
                break;
            }
            case 1: {
                cgroup = "Message GAMEOVER 01";
                break;
            }
            case 2: {
                cgroup = "Message GAMEOVER 02";
                break;
            }
            case 3: {
                cgroup = "Message GAMEOVER 03";
                break;
            }
            case 4: {
                cgroup = "Message GAMEOVER with Black Background";
            }
        }
        menues.InitXml(_menu, Common.ConstructPath("menu_msg_gameover.xml"), cgroup);
        menues.SetMenuCallBack_ExitMenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - OK"), 4L);
        menues.WindowSet_ShowCursor(_menu, true);
    }

    public void AfterInitMenu(long _menu) {
        String _textV;
        long _textId;
        menues.setShowMenu(_menu, true);
        menues.setfocuscontrolonmenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - OK"));
        menues.SetStopWorld(_menu, true);
        if ((s_gameovertype == 4 || s_gameovertype == 0 || s_gameovertype == 1) && Crew.getIgrok() != null && Crew.getIgrok().gFirstName() != null && Crew.getIgrok().gLastName() != null && (_textId = menues.FindFieldInMenu(_menu, "BackPaper - text")) != 0L && (_textV = menues.GetFieldText(_textId)) != null) {
            KeyPair[] pairs = new KeyPair[]{new KeyPair("NICOLAS_ARMSTRONG", Crew.getIgrok().gFirstName() + " " + Crew.getIgrok().gLastName())};
            menues.SetFieldText(_textId, MacroKit.Parse(_textV, pairs));
            menues.UpdateMenuField(menues.ConvertMenuFields(_textId));
        }
        switch (s_gameovertype) {
            case 0: {
                JavaEvents.SendEvent(71, 9, null);
                break;
            }
            case 1: {
                JavaEvents.SendEvent(71, 10, null);
                break;
            }
            case 2: {
                JavaEvents.SendEvent(71, 10, null);
                break;
            }
            case 3: {
                JavaEvents.SendEvent(71, 8, null);
                break;
            }
            case 4: {
                JavaEvents.SendEvent(71, 9, null);
            }
        }
    }

    public void exitMenu(long _menu) {
        if (this.leaveMenuListener != null) {
            this.leaveMenuListener.menuLeaved();
        }
        s_this = null;
        event.Setevent(9001);
        if (scenarioscript.script.isInstantOrder()) {
            JavaEvents.SendEvent(23, 3, this);
        } else if (s_gameovertype != 3 && s_gameovertype != 2) {
            JavaEvents.SendEvent(23, 1, this);
        }
    }

    public String getMenuId() {
        switch (s_gameovertype) {
            case 0: {
                return "gameoverEndMENU";
            }
            case 1: {
                return "gameoverJailMENU";
            }
            case 2: {
                return "gameoverMurderMENU";
            }
            case 3: {
                return "gameoverBankruptMENU";
            }
            case 4: {
                return "gameoverEndWthBackgroundMENU";
            }
        }
        return null;
    }

    static {
        s_gameovertype = 0;
    }
}

