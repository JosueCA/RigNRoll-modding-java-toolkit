/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.menucreation;
import menu.menues;
import menuscript.VictoryMenuExitListener;

public class TotalVictoryMenu
implements menucreation {
    private static final String continue_button = "GameoverTOTAL - Buttons - CONTINUE";
    private static final String exit_button = "GameoverTOTAL - Buttons - MAIN MENU";
    private static final String[] fieldToHideNames = new String[]{"GameoverTOTAL - Buttons - END FREEWAY", "GameoverTOTAL - 000 - BottomText - YOU HAVE LOST", "GameoverTOTAL - xx0 - Prize - SOCIAL LOST", "GameoverTOTAL - xx0 - Prize - ECONOMIC LOST", "GameoverTOTAL - xx0 - Prize - SPORT LOST", "GameoverTOTAL - Buttons - CONTINUE", "GameoverTOTAL - xx0 - BottomText - GAMEOVER", "GameoverTOTAL - xx0 - Prize - SOCIAL WON", "GameoverTOTAL - xx0 - Prize - SOCIAL LOST", "GameoverTOTAL - xx0 - Prize - ECONOMIC WON", "GameoverTOTAL - xx0 - Prize - ECONOMIC LOST", "GameoverTOTAL - xx0 - Prize - SPORT WON", "GameoverTOTAL - xx0 - Prize - SPORT LOST", "GameoverTOTAL - Buttons - CONTINUE", "GameoverTOTAL - xx1 - BottomText - YOU HAVE WON", "GameoverTOTAL - xx1 - 3Prizes - SOCIAL n SPORT n ECONOMIC", "GameoverTOTAL - xx1 - 2Prizes - SOCIAL n SPORT", "GameoverTOTAL - xx1 - 2Prizes - SPORT n ECONOMIC", "GameoverTOTAL - xx1 - 2Prizes - SOCIAL n ECONOMIC", "GameoverTOTAL - xx1 - 1Prize - SOCIAL WON", "GameoverTOTAL - xx1 - 1Prize - SPORT WON", "GameoverTOTAL - xx1 - 1Prize - ECONOMIC WON"};
    private static final String methodContinue = "onContinue";
    private static final String methodExit = "onExit";
    private static final String XML = "..\\data\\config\\menu\\menu_msg_gameover_TOTAL.xml";
    private VictoryMenuExitListener cbs = null;

    public void restartMenu(long _menu) {
    }

    public TotalVictoryMenu(VictoryMenuExitListener _cbs) {
        this.cbs = _cbs;
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, "GameoverTOTAL");
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, continue_button)), this, methodContinue, 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, exit_button)), this, methodExit, 4L);
    }

    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.SetMenagPOLOSY(_menu, false);
        menues.setShowMenu(_menu, true);
        GameState game_state = new GameState();
        JavaEvents.SendEvent(40, 0, game_state);
        for (int i = 0; i < fieldToHideNames.length; ++i) {
            menues.SetShowField(menues.FindFieldInMenu(_menu, fieldToHideNames[i]), false);
        }
        if (game_state.succeeded_scenario_win && game_state.succeeded_economy_win) {
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - Buttons - END FREEWAY"), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - 000 - BottomText - YOU HAVE LOST"), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - SOCIAL LOST"), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - ECONOMIC LOST"), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - SPORT LOST"), false);
        }
        if (game_state.last_loose) {
            menues.SetShowField(menues.FindFieldInMenu(_menu, continue_button), true);
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - BottomText - GAMEOVER"), true);
            if (game_state.succeeded_scenario_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - SOCIAL WON"), true);
            } else {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - SOCIAL LOST"), true);
            }
            if (game_state.succeeded_economy_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - ECONOMIC WON"), true);
            } else {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - ECONOMIC LOST"), true);
            }
            if (game_state.succeeded_race_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - SPORT WON"), true);
            } else {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx0 - Prize - SPORT LOST"), true);
            }
        } else {
            menues.SetShowField(menues.FindFieldInMenu(_menu, continue_button), true);
            menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - BottomText - YOU HAVE WON"), true);
            if (game_state.succeeded_scenario_win && game_state.succeeded_economy_win && game_state.succeeded_race_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 3Prizes - SOCIAL n SPORT n ECONOMIC"), true);
            } else if (game_state.succeeded_scenario_win && game_state.succeeded_race_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 2Prizes - SOCIAL n SPORT"), true);
            } else if (game_state.succeeded_scenario_win && game_state.succeeded_economy_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 2Prizes - SOCIAL n ECONOMIC"), true);
            } else if (game_state.succeeded_economy_win && game_state.succeeded_race_win) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 2Prizes - SPORT n ECONOMIC"), true);
            } else {
                if (game_state.succeeded_scenario_win) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 1Prize - SOCIAL WON"), true);
                }
                if (game_state.succeeded_economy_win) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 1Prize - ECONOMIC WON"), true);
                }
                if (game_state.succeeded_race_win) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, "GameoverTOTAL - xx1 - 1Prize - SPORT WON"), true);
                }
            }
        }
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "gameoverTOTALMENU";
    }

    public void onContinue(long _menu, MENUsimplebutton_field button) {
        if (this.cbs != null) {
            this.cbs.OnMenuExit(0);
        }
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void onExit(long _menu, MENUsimplebutton_field button) {
        if (this.cbs != null) {
            this.cbs.OnMenuExit(1);
        }
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public static long createGameOverTotal(VictoryMenuExitListener cbs) {
        TotalVictoryMenu menu = new TotalVictoryMenu(cbs);
        return menues.createSimpleMenu(menu);
    }

    static class GameState {
        boolean has_economy_contest;
        boolean succeeded_economy_win;
        boolean has_scenario_contest;
        boolean succeeded_scenario_win;
        boolean has_race_contest;
        boolean succeeded_race_win;
        boolean last_loose;

        GameState() {
        }
    }
}

