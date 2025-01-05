/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import java.util.ArrayList;
import menu.menues;
import menu.xmlcontrols;
import menuscript.IUpdateListener;
import menuscript.org.ChampionShipCallendar;
import menuscript.org.ChampionShipCurrentRace;
import menuscript.org.ChampionShipTopTen;
import menuscript.org.IOrgTab;
import menuscript.org.OrganiserMenu;
import menuscript.org.VoidTab;
import rnrcore.eng;

public class ChampionShipPane
implements IOrgTab,
IUpdateListener {
    public static final int TAB_CURRENT_RACE = 0;
    public static final int TAB_CALENDAR = 1;
    public static final int TAB_TOP_TEN = 2;
    private static final String TAB_METHOD = "onTab";
    private static final String GRAY_BUTTON = "Tab0 - CHAMPIONSHIP - CURRENT RACE - GRAY";
    private static final String[] TAB_NAMES = new String[]{"Tab0 - CHAMPIONSHIP - CURRENT RACE", "Tab0 - CHAMPIONSHIP - CALENDAR", "Tab0 - CHAMPIONSHIP - TOP TEN"};
    OrganiserMenu parent = null;
    long _menu = 0L;
    boolean bWasAfterInit = false;
    private ArrayList<IOrgTab> tabs = new ArrayList();

    ChampionShipPane(long _menu, OrganiserMenu parent) {
        this._menu = _menu;
        this.parent = parent;
        this.InitMenu(_menu);
    }

    public void InitMenu(long _menu) {
        block5: for (int i = 0; i < TAB_NAMES.length; ++i) {
            long tab = menues.FindFieldInMenu(_menu, TAB_NAMES[i]);
            xmlcontrols.MENUCustomStuff obj_tab = (xmlcontrols.MENUCustomStuff)menues.ConvertMenuFields(tab);
            obj_tab.userid = i;
            menues.UpdateMenuField(obj_tab);
            menues.SetScriptOnControl(_menu, obj_tab, this, TAB_METHOD, 10L);
            switch (i) {
                case 0: {
                    this.tabs.add(new ChampionShipCurrentRace(_menu, this));
                    continue block5;
                }
                case 1: {
                    this.tabs.add(new ChampionShipCallendar(_menu, this));
                    continue block5;
                }
                case 2: {
                    this.tabs.add(new ChampionShipTopTen(_menu, this));
                    continue block5;
                }
                default: {
                    this.tabs.add(new VoidTab());
                }
            }
        }
    }

    public void onTab(long _menu, xmlcontrols.MENUCustomStuff button) {
        if (this.bWasAfterInit) {
            for (int i = 0; i < this.tabs.size(); ++i) {
                if (i == button.userid) {
                    this.tabs.get(i).enterFocus();
                    continue;
                }
                this.tabs.get(i).leaveFocus();
            }
        }
    }

    public void enterFocus() {
        if (this.bWasAfterInit) {
            this.update();
        }
    }

    public void leaveFocus() {
    }

    public void update() {
        long tab = menues.FindFieldInMenu(this._menu, TAB_NAMES[0]);
        long gray_tab = menues.FindFieldInMenu(this._menu, GRAY_BUTTON);
        if (tab != 0L) {
            if (!eng.hasBigraceOrder()) {
                menues.SetBlindess(tab, true);
                menues.SetIgnoreEvents(tab, true);
                if (gray_tab != 0L) {
                    menues.SetShowField(tab, false);
                    menues.SetShowField(gray_tab, true);
                }
                menues.SetFieldState(menues.FindFieldInMenu(this._menu, TAB_NAMES[1]), 1);
            } else {
                if (gray_tab != 0L) {
                    menues.SetShowField(tab, true);
                    menues.SetShowField(gray_tab, false);
                }
                menues.SetBlindess(tab, false);
                menues.SetIgnoreEvents(tab, false);
                menues.SetFieldState(tab, 1);
            }
        }
    }

    public void afterInit() {
        for (IOrgTab pane : this.tabs) {
            pane.afterInit();
        }
        this.update();
        this.bWasAfterInit = true;
    }

    public void exitMenu() {
        for (IOrgTab pane : this.tabs) {
            pane.exitMenu();
        }
    }

    public void ShowTabHelp(int tab_id) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(4, tab_id);
        }
    }

    public void onUpdate() {
        if (this.bWasAfterInit) {
            this.update();
        }
    }
}

