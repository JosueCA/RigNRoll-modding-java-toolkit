/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import java.util.ArrayList;
import menu.Common;
import menu.Helper;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.MenuControls;
import menu.TextScroller;
import menu.menucreation;
import menu.menues;
import menu.xmlcontrols;
import menuscript.Converts;
import menuscript.IUpdateListener;
import menuscript.PoPUpMenu;
import menuscript.org.ChampionShipPane;
import menuscript.org.IOrgTab;
import menuscript.org.JournalPane;
import menuscript.org.OrganiserPane;
import menuscript.org.OrganizerWeather;
import menuscript.org.VoidTab;
import rnrcore.CoreTime;
import rnrcore.eng;
import rnrcore.loc;

public class OrganiserMenu
implements menucreation,
IUpdateListener {
    static final boolean DEBUG = eng.noNative;
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String CONTROLS_MAIN = "Communicator";
    private static final String[] TAB_NAMES = new String[]{"Tab0 - ORGANIZER", "Tab0 - JOURNAL", "Tab0 - WEATHER", "Tab0 - EXIT", "Tab0 - CHAMPIONSHIP", "Tab0 - PHOTO ALBUM"};
    private static final String GRAY_BUTTON = "Tab0 - CHAMPIONSHIP - GRAY";
    public static final int TAB_ORG = 0;
    public static final int TAB_JOU = 1;
    public static final int TAB_WEATHER = 2;
    private static final int TAB_EXIT = 3;
    public static final int TAB_CHAMPIONSHIP = 4;
    public static final int TAB_PHOTO_ALBUM = 5;
    private static final String TAB_METHOD = "onTab";
    private static final String PARAMS = "INFORMATION STRING";
    private static final String[] MACROSES = new String[]{"SIGN", "MONEY", "RANK", "RATING"};
    private static final int MACRO_SIGN = 0;
    private static final int MACRO_MONEY = 1;
    private static final int MACRO_RANK = 2;
    private static final int MACRO_RATING = 3;
    private PoPUpMenu show_help = null;
    private long help_text_title = 0L;
    private long help_text = 0L;
    private long help_text_scroller = 0L;
    TextScroller scroller = null;
    String info_text = null;
    Common common;
    private PoPUpMenu championshipActivateToAccess = null;
    private PoPUpMenu championshipStartAfterTruckerOfTheYear = null;
    private MenuControls allmenu = null;
    private ArrayList<IOrgTab> tabs = new ArrayList();
    private long params_text;
    private String params_text_value;

    public void restartMenu(long _menu) {
    }

    public static long create() {
        return menues.CreateJournalMenu(new OrganiserMenu(), 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void InitMenu(long _menu) {
        if (DEBUG) {
            eng.noNative = true;
        }
        this.common = new Common(_menu);
        this.allmenu = new MenuControls(_menu, XML, CONTROLS_MAIN);
        this.params_text = this.allmenu.findControl(PARAMS);
        this.params_text_value = menues.GetFieldText(this.params_text);
        OrganiserPane org = null;
        ChampionShipPane champ = null;
        block6: for (int i = 0; i < TAB_NAMES.length; ++i) {
            if (i == 5) continue;
            long tab = menues.FindFieldInMenu(_menu, TAB_NAMES[i]);
            xmlcontrols.MENUCustomStuff obj_tab = (xmlcontrols.MENUCustomStuff)menues.ConvertMenuFields(tab);
            obj_tab.userid = i;
            menues.UpdateMenuField(obj_tab);
            menues.SetScriptOnControl(_menu, obj_tab, this, TAB_METHOD, 10L);
            switch (i) {
                case 0: {
                    org = new OrganiserPane(_menu, this);
                    this.tabs.add(org);
                    continue block6;
                }
                case 1: {
                    this.tabs.add(new JournalPane(_menu, this));
                    continue block6;
                }
                case 2: {
                    this.tabs.add(new OrganizerWeather(_menu, this));
                    continue block6;
                }
                case 4: {
                    champ = new ChampionShipPane(_menu, this);
                    this.tabs.add(champ);
                    continue block6;
                }
                default: {
                    this.tabs.add(new VoidTab());
                }
            }
        }
        if (org != null) {
            org.addUpdateListener(this);
            if (champ != null) {
                org.addUpdateListener(champ);
            }
        }
        menues.InitXml(_menu, XML, "TOOLTIP - common");
        this.show_help = new PoPUpMenu(_menu, XML, "TOOLTIP - BLACK SHARK");
        this.help_text_title = this.show_help.getField("CALL COMMUNICATOR HELP - TITLE");
        this.help_text = this.show_help.getField("CALL COMMUNICATOR HELP - TEXT");
        this.help_text_scroller = this.show_help.getField("CALL COMMUNICATOR HELP - Tableranger");
        this.championshipActivateToAccess = new PoPUpMenu(_menu, XML, "MESSAGE - CHAMPIONSHIP - Activate to access");
        this.championshipStartAfterTruckerOfTheYear = new PoPUpMenu(_menu, XML, "MESSAGE - CHAMPIONSHIP - Start after TruckerOfTheYear");
    }

    public void AfterInitMenu(long _menu) {
        long photo_album;
        for (IOrgTab pane : this.tabs) {
            pane.afterInit();
        }
        this.show_help.afterInit();
        this.championshipActivateToAccess.afterInit();
        this.championshipStartAfterTruckerOfTheYear.afterInit();
        MenuAfterInitNarrator.justShowAndStop(_menu);
        menues.SetFieldState(menues.FindFieldInMenu(_menu, TAB_NAMES[0]), 1);
        long control_champ = menues.FindFieldInMenu(_menu, TAB_NAMES[4]);
        long control_champ_gray = menues.FindFieldInMenu(_menu, GRAY_BUTTON);
        if (control_champ != 0L && control_champ_gray != 0L) {
            if (!eng.isBigRaceEnable()) {
                menues.SetShowField(control_champ, false);
                menues.SetShowField(control_champ_gray, true);
                menues.SetBlindess(control_champ_gray, false);
                menues.SetIgnoreEvents(control_champ_gray, false);
                Object field = menues.ConvertMenuFields(control_champ_gray);
                if (field != null) {
                    menues.SetScriptOnControl(_menu, field, this, "OnGrayPressed", 4L);
                }
            } else {
                menues.SetShowField(control_champ, true);
                menues.SetShowField(control_champ_gray, false);
            }
        }
        if ((photo_album = menues.FindFieldInMenu(_menu, TAB_NAMES[5])) != 0L) {
            menues.SetShowField(photo_album, false);
        }
        this.updateStatusBar();
    }

    public void OnGrayPressed(long _menu, MENUsimplebutton_field button) {
        if (eng.isBigRaceInstalled()) {
            if (!eng.isBigRaceEnable()) {
                if (!eng.isBigRaceAvaible()) {
                    this.championshipActivateToAccess.show();
                } else {
                    this.championshipStartAfterTruckerOfTheYear.show();
                }
            }
        } else {
            this.championshipActivateToAccess.show();
        }
    }

    public void exitMenu(long _menu) {
        for (IOrgTab pane : this.tabs) {
            pane.exitMenu();
        }
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    public String getMenuId() {
        return "organiserMENU";
    }

    public void onTab(long _menu, xmlcontrols.MENUCustomStuff button) {
        if (button.userid == 3) {
            menues.CallMenuCallBack_ExitMenu(_menu);
            return;
        }
        for (int i = 0; i < this.tabs.size(); ++i) {
            if (i == button.userid) {
                this.tabs.get(i).enterFocus();
                continue;
            }
            this.tabs.get(i).leaveFocus();
        }
    }

    public void onUpdate() {
        this.updateStatusBar();
    }

    private void updateStatusBar() {
        if (!DEBUG) {
            TotalsValues values = new TotalsValues();
            JavaEvents.SendEvent(44, 9, values);
            CoreTime time = new CoreTime();
            KeyPair[] pairs = new KeyPair[MACROSES.length];
            pairs[0] = new KeyPair(MACROSES[0], Math.abs(values.balance) >= 0 ? "" : "-");
            pairs[1] = new KeyPair(MACROSES[1], Helper.convertMoney(values.balance));
            pairs[2] = new KeyPair(MACROSES[2], "" + values.rank);
            pairs[3] = new KeyPair(MACROSES[3], Converts.ConvertRating(eng.visualRating(values.rating)));
            menues.SetFieldText(this.params_text, MacroKit.Parse(Converts.ConvertDateAbsolute(this.params_text_value, time.gMonth(), time.gDate(), time.gYear(), time.gHour(), time.gMinute()), pairs));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.params_text));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void ShowTabHelp(int tab_id, int sub_tab_id) {
        String help_text_text;
        String tite_loc = null;
        String help_loc = null;
        if (tab_id == 0) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - ORGANIZER";
            help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - ORGANIZER";
        } else if (tab_id == 1) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - JOURNAL";
            help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - JOURNAL";
        } else if (tab_id == 5) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - PHOTO ALBUM";
            help_loc = "menu_com.xml\\TOOLTIP BLACK SHARK\\TOOLTIP BLACK SHARK\\CALL COMMUNICATOR HELP PHOTO ALBUM";
        } else if (tab_id == 2) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - WEATHER";
            help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - WEATHER";
        } else {
            if (tab_id != 4) return;
            if (sub_tab_id == 0) {
                tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - CHAMPIONSHIP\\CHAMPIONSHIP - CURRENT RACE\\CHAMPIONSHIP - CURRENT RACE - TITLE";
                help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - CHAMPIONSHIP - CURRENT RACE";
            } else if (sub_tab_id == 1) {
                tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - CHAMPIONSHIP\\CHAMPIONSHIP - CALENDAR\\CHAMPIONSHIP - CALENDAR - TITLE";
                help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - CHAMPIONSHIP - CALENDAR";
            } else {
                if (sub_tab_id != 2) return;
                tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - CHAMPIONSHIP\\CHAMPIONSHIP - TOP TEN\\CHAMPIONSHIP - TOP TEN - TITLE";
                help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - CHAMPIONSHIP - TOP TEN";
            }
        }
        if (this.help_text_title != 0L) {
            menues.SetFieldText(this.help_text_title, loc.getMENUString(tite_loc));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.help_text_title));
        }
        if ((help_text_text = loc.getMENUString(help_loc)) != null && this.help_text != 0L && this.help_text_scroller != 0L) {
            MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(this.help_text_scroller);
            MENUText_field text = (MENUText_field)menues.ConvertMenuFields(this.help_text);
            if (ranger != null && text != null) {
                text.text = help_text_text;
                menues.UpdateField(text);
                int texh = menues.GetTextLineHeight(text.nativePointer);
                int startbase = menues.GetBaseLine(text.nativePointer);
                int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
                int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, help_text_text), startbase, texh) + 3;
                if (this.scroller != null) {
                    this.scroller.Deinit();
                }
                this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, true, "CALL OFFICE HELP - TEXT");
                this.scroller.AddTextControl(text);
            }
        }
        this.show_help.show();
    }

    static class TotalsValues {
        int balance;
        int rank;
        double rating;

        TotalsValues() {
        }
    }
}

