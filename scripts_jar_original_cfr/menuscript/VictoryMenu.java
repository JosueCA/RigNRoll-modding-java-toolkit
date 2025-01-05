/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.menucreation;
import menu.menues;
import menuscript.VictoryMenuExitListener;

public class VictoryMenu
implements menucreation {
    private static final String continue_button_loose = "BUTTON - OK";
    private static final String[] xmlGroupLoose = new String[]{"Message GAMEOVER_Social", "Message GAMEOVER_Economic", "Message GAMEOVER_Sport"};
    private static final String[] continue_button_win = new String[]{"Message Victory_Social - BUTTON - CONTINUE", "Message Victory_Economic - BUTTON - CONTINUE", "Message Victory_Sport - BUTTON - CONTINUE"};
    private static final String[] xmlGroupWin = new String[]{"Message Victory_Social", "Message Victory_Economic", "Message Victory_Sport"};
    private static final String methodContinue = "onContinue";
    private static final String XML = "..\\data\\config\\menu\\menu_msg_gameover.xml";
    private static final int SocialGameOver = 0;
    private static final int EconomyGameOver = 1;
    private static final int SportGameOver = 2;
    private int gameOverState = -1;
    private boolean wasWin = false;
    private VictoryMenuExitListener cbs = null;

    public void restartMenu(long _menu) {
    }

    public VictoryMenu(int state, boolean _wasWin, VictoryMenuExitListener _cbs) {
        this.gameOverState = state;
        this.gameOverState = this.gameOverState < 0 ? 0 : this.gameOverState;
        this.gameOverState = this.gameOverState > 2 ? 2 : this.gameOverState;
        this.wasWin = _wasWin;
        this.cbs = _cbs;
    }

    public void InitMenu(long _menu) {
        if (this.wasWin) {
            menues.InitXml(_menu, XML, xmlGroupWin[this.gameOverState]);
            menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, continue_button_win[this.gameOverState])), this, methodContinue, 4L);
        } else {
            menues.InitXml(_menu, XML, xmlGroupLoose[this.gameOverState]);
            menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, continue_button_loose)), this, methodContinue, 4L);
        }
    }

    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.SetMenagPOLOSY(_menu, false);
        menues.setShowMenu(_menu, true);
        if (this.wasWin) {
            if (this.gameOverState == 0) {
                JavaEvents.SendEvent(71, 13, null);
            } else if (this.gameOverState == 1) {
                JavaEvents.SendEvent(71, 12, null);
            } else if (this.gameOverState == 2) {
                JavaEvents.SendEvent(71, 11, null);
            }
        } else {
            JavaEvents.SendEvent(71, 14, null);
        }
    }

    public void exitMenu(long _menu) {
        if (this.cbs != null) {
            this.cbs.OnMenuExit(0);
        }
    }

    public String getMenuId() {
        if (this.wasWin) {
            if (this.gameOverState == 0) {
                return "victoryScenarioMENU";
            }
            if (this.gameOverState == 1) {
                return "victoryEconomyMENU";
            }
            if (this.gameOverState == 2) {
                return "victorySportMENU";
            }
        } else {
            if (this.gameOverState == 0) {
                return "looseScenarioMENU";
            }
            if (this.gameOverState == 1) {
                return "looseEconomyMENU";
            }
            if (this.gameOverState == 2) {
                return "looseSportMENU";
            }
        }
        return "looseSportMENU";
    }

    public void onContinue(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public static long createLooseEconomy(VictoryMenuExitListener cbs) {
        VictoryMenu menu = new VictoryMenu(1, false, cbs);
        return menues.createSimpleMenu((menucreation)menu, "ESC");
    }

    public static long createLooseSocial(VictoryMenuExitListener cbs) {
        VictoryMenu menu = new VictoryMenu(0, false, cbs);
        return menues.createSimpleMenu((menucreation)menu, "ESC");
    }

    public static long createLooseSport(VictoryMenuExitListener cbs) {
        VictoryMenu menu = new VictoryMenu(2, false, cbs);
        return menues.createSimpleMenu((menucreation)menu, "ESC");
    }

    public static long createWinEconomy(VictoryMenuExitListener cbs) {
        VictoryMenu menu = new VictoryMenu(1, true, cbs);
        return menues.createSimpleMenu((menucreation)menu, "ESC");
    }

    public static long createWinSocial(VictoryMenuExitListener cbs) {
        VictoryMenu menu = new VictoryMenu(0, true, cbs);
        return menues.createSimpleMenu((menucreation)menu, "ESC");
    }

    public static long createWinSport(VictoryMenuExitListener cbs) {
        VictoryMenu menu = new VictoryMenu(2, true, cbs);
        return menues.createSimpleMenu((menucreation)menu, "ESC");
    }
}

