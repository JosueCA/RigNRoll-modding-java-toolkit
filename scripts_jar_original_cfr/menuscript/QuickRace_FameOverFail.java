/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.menucreation;
import menu.menues;
import menuscript.QuickRace_GameOverSuccess;
import rnrcore.loc;

public class QuickRace_FameOverFail
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_msg_gameover.xml";
    private static final String MENU_GROUP = "Message GAMEOVER INSTANTORDER - DEFAULTED";
    private static final String restart_button = "BUTTON - GAMEOVER INSTANTORDER - DEFAULTED - RESTART";
    private static final String main_menu_button = "BUTTON - GAMEOVER INSTANTORDER - DEFAULTED - MAIN MENU";
    private static final String methodRestart = "onRestart";
    private static final String methodExit2MainMenu = "onExit";
    QuickRace_GameOverSuccess.Results in_results = new QuickRace_GameOverSuccess.Results();
    public static final int ACTION_RESTART = 0;
    public static final int ACTION_QUIT_TO_MAIN_MENU = 1;
    private static int out_action = 0;
    private static final String[] loc_titles = new String[]{"menu_msg_gameover.xml\\Message GAMEOVER INSTANTORDER - DEFAULTED\\Message GAMEOVER INSTANTORDER - DEFAULTED - BACK\\GameoverINSTANTORDER - DEFAULTED - BACK 02\\GameoverINSTANTORDER - TEXT - DEFAULTED"};

    public void restartMenu(long _menu) {
    }

    private QuickRace_FameOverFail() {
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, MENU_GROUP);
        long _restart_button = menues.FindFieldInMenu(_menu, restart_button);
        long _main_menu_button = menues.FindFieldInMenu(_menu, main_menu_button);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(_restart_button), this, methodRestart, 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(_main_menu_button), this, methodExit2MainMenu, 4L);
    }

    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.setShowMenu(_menu, true);
        for (String name : loc_titles) {
            menues.SetFieldText(menues.FindFieldInMenu(_menu, name), loc.getMENUString(name));
        }
    }

    public void exitMenu(long _menu) {
    }

    public static final int GetAction() {
        return out_action;
    }

    public void onRestart(long _menu, MENUsimplebutton_field button) {
        out_action = 0;
        menues.CallMenuCallBack_ExitMenu(_menu);
        JavaEvents.SendEvent(65, 13, null);
    }

    public void onExit(long _menu, MENUsimplebutton_field button) {
        out_action = 1;
        menues.CallMenuCallBack_ExitMenu(_menu);
        JavaEvents.SendEvent(65, 13, null);
    }

    public String getMenuId() {
        return "gameoverInstantOrderDefaultedMENU";
    }

    public static long Create() {
        return menues.createSimpleMenu(new QuickRace_FameOverFail());
    }
}

