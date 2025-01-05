/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.Vector;
import menu.BaseMenu;
import menu.Common;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.MenuControls;
import menu.NotifyInformation;
import menu.TextScroller;
import menu.menucreation;
import menu.menues;
import menuscript.BarMenuSlots;
import menuscript.Converts;
import players.Crew;
import players.IcontaktCB;
import rnrcore.SCRtalkingperson;
import rnrcore.SCRuniperson;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscr.Bar;
import rnrscr.BarMenuCreator;
import rnrscr.Dialog;
import rnrscr.IBarMoveNewspaperAnimation;
import rnrscr.SODialogParams;
import rnrscr.smi.IArticle;
import rnrscr.smi.Newspapers;

public class BarMenu
extends BaseMenu
implements menucreation {
    public static final boolean DEBUG = eng.noNative;
    private static final String XML = "..\\data\\config\\menu\\menu_bar.xml";
    public static final int NEWS = 0;
    public static final int RACE = 1;
    public static final int SUMMARY = 2;
    public static final int TYPENEWS_SIZE = 3;
    private static BarMenu gmenu;
    private String bar_name;
    private vectorJ pos;
    private long _menu;
    private IBarMoveNewspaperAnimation animation = null;
    boolean m_singlepaper;
    BarEntry simple_entry;
    Vector m_photopapers = new Vector();
    MENUText_field m_mainmenu;
    MENUText_field m_papermenu;
    MENUText_field m_articlemenu;
    MENUText_field m_photopic;
    MENUsimplebutton_field[] m_newsbuttons;
    private BarMenuSlots slots;
    int lasttextlines = 1;
    int lasthlines = 1;
    TextScroller scroller;
    private static final int max_lines_in_text_body = 15;
    private static final int max_lines_in_head_body = 4;
    public String texture_name = "none";
    private String currentdialog = "";
    private SelectionPaper currentselection = null;
    private MenuCamera currentmenucamera = null;
    private static boolean was_dialog;
    public static boolean dialog_ended;
    public static boolean was_news;

    private BarMenu(String bar_name, vectorJ position, IBarMoveNewspaperAnimation animation2) {
        this.animation = animation2;
        this.bar_name = bar_name;
        this.pos = position;
    }

    public static long CreateBarMenu(String bar_name, vectorJ position, IBarMoveNewspaperAnimation animation2) {
        BarMenu menu;
        gmenu = menu = new BarMenu(bar_name, position, animation2);
        was_dialog = false;
        was_news = false;
        return menues.createSimpleMenu(menu, 1.0E7, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void InitMenu(long _menu) {
        this._menu = _menu;
        MenuControls allmenu = new MenuControls(_menu, XML, "BAR ALL", false);
        long text_field = allmenu.findControl("BarMenu - ALL");
        this.m_mainmenu = menues.ConvertTextFields(text_field);
        this.slots = new BarMenuSlots(this, _menu, this.pos);
        this.uiTools = new Common(_menu);
        this.m_singlepaper = DEBUG ? false : BarMenuCreator.isSinglePaperInBar(this.pos);
        this.m_singlepaper = false;
        this.InitPaper();
        if (this.m_singlepaper) {
            Place bar_place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
            BarEntry[] entries = null;
            if (null != bar_place) {
                entries = Newspapers.getTheOnlyNewsPaper_BarEntries(bar_place.getName());
            }
            this.simple_entry = entries[0];
        } else {
            this.manageMenuCamera_enter();
        }
        menues.InitXml(_menu, Common.ConstructPath("menu_bar.xml"), "NewsTEXT 1 row");
        this.m_articlemenu = this.uiTools.FindTextField("News 1 row - 55 SymbPerRow - 31 pixel");
        MENUsimplebutton_field b = this.uiTools.FindSimpleButton("BUTTON - OK");
        if (this.m_singlepaper) {
            // empty if block
        }
        menues.SetScriptOnControl(this.uiTools.GetMenu(), b, this, "OnPaperClose", 4L);
        menues.SetMenuCallBack_ExitMenu(_menu, this.uiTools.FindSimpleButton((String)"BUTTON - EXIT").nativePointer, 4L);
    }

    void FillPaper(BarEntry entry) {
        this.m_papermenu = this.uiTools.FindTextField("News");
        menues.SetShowField(this.m_papermenu.nativePointer, true);
        menues.SetShowField(this.m_articlemenu.nativePointer, true);
        this.texture_name = entry.texture_name != null ? entry.texture_name : "none";
        JavaEvents.SendEvent(62, 2, this);
        MENUText_field head = this.uiTools.FindTextField("TEXT - HeadLine");
        head.text = entry.headline;
        MENUText_field text = this.uiTools.FindTextField("TEXT - Body");
        text.text = MacroKit.Parse(entry.papertext, entry.keys);
        int htexth = menues.GetTextHeight(head.nativePointer, head.text);
        int hbl = menues.GetBaseLine(head.nativePointer);
        int htlh = menues.GetTextLineHeight(head.nativePointer);
        int headlines = Converts.HeightToLines(htexth, hbl, htlh);
        int textlines = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, text.text), menues.GetBaseLine(text.nativePointer), menues.GetTextLineHeight(text.nativePointer));
        int headshift = (int)((double)(Math.min(headlines, 4) - this.lasthlines) * 26.5);
        int textshift = (int)((double)(Math.min(textlines, 15) - this.lasttextlines) * 26.5);
        this.lasthlines = Math.min(headlines, 4);
        this.lasttextlines = Math.min(textlines, 15);
        head.leny += headshift;
        head.poy -= headshift + textshift;
        text.leny += textshift;
        text.poy -= textshift;
        menues.UpdateField(head);
        menues.UpdateField(text);
        MENUText_field backshadow = this.uiTools.FindTextField("BackPaperShadow");
        backshadow.poy -= headshift + textshift;
        backshadow.leny += headshift + textshift;
        menues.UpdateField(backshadow);
        MENUText_field back = this.uiTools.FindTextField("BackPaper");
        back.poy -= headshift + textshift;
        back.leny += headshift + textshift;
        menues.UpdateField(back);
        MENUText_field dark1 = this.uiTools.FindTextField("Border DARK 1");
        dark1.poy -= textshift;
        menues.UpdateField(dark1);
        MENUText_field dark2 = this.uiTools.FindTextField("Border DARK 2");
        dark2.poy -= textshift;
        menues.UpdateField(dark2);
        if (textlines > 15) {
            if (this.scroller != null) {
                this.scroller.Deinit();
            }
            this.scroller = new TextScroller(this.uiTools, this.uiTools.FindScroller("Tableranger - Newspaper"), textlines, 15, menues.GetTextLineHeight(text.nativePointer), menues.GetBaseLine(text.nativePointer), false, "GROUP - Tableranger - Newspaper");
            this.scroller.AddTextControl(text);
            menues.SetShowField(menues.FindFieldInMenu(this.uiTools.GetMenu(), "GROUP - Tableranger - Newspaper"), true);
        } else {
            if (this.scroller != null) {
                this.scroller.Deinit();
            }
            this.scroller = null;
            menues.SetShowField(menues.FindFieldInMenu(this.uiTools.GetMenu(), "GROUP - Tableranger - Newspaper"), false);
        }
        if (entry.type == 2) {
            PaperIndex pindex = new PaperIndex(entry);
            for (int i = 0; i < this.m_photopapers.size(); ++i) {
                PaperPhoto p = (PaperPhoto)this.m_photopapers.get(i);
                if (!p.index.equals(pindex)) continue;
                this.m_photopic = p.control;
                menues.SetShowField(this.m_photopic.nativePointer, true);
            }
        }
    }

    void InitPaper() {
        menues.InitXml(this._menu, XML, "News");
    }

    void OnSingleExit(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    void actorDialogFinished() {
        this.manageMenuCamera_enter();
        menues.menu_set_polosy(this.uiTools.s_menu, false);
        if (this.m_mainmenu != null) {
            menues.SetShowField(this.m_mainmenu.nativePointer, true);
        }
        Bar.getInstance().endDialog(this.currentdialog);
        this.slots.update();
    }

    void startDialog(BarActor dialog) {
        this.manageMenuCamera_exit();
        menues.menu_set_polosy(this._menu, true);
        if (this.m_mainmenu != null) {
            menues.SetShowField(this.m_mainmenu.nativePointer, false);
        }
        this.currentdialog = dialog.dialogname;
        dialog.params.play();
        Bar.getInstance().startDialog(this.currentdialog);
        Dialog.getDialog(this.currentdialog).start_bar(dialog.model, Crew.getIgrok().getModel(), dialog.params.getIdentitie());
        event.eventObject(9850, this, "actorDialogFinished");
    }

    void pickUpPack(String mission_name) {
        MissionEventsMaker.pickupQuestItem(this.bar_name, mission_name);
        this.slots.update();
    }

    private void manageMenuCamera_exit() {
        if (DEBUG) {
            return;
        }
        if (null != this.currentmenucamera) {
            this.currentmenucamera.closeSelection();
            this.currentmenucamera = null;
        }
    }

    private void manageMenuCamera_enter() {
        if (DEBUG) {
            return;
        }
        this.currentmenucamera = new MenuCamera();
    }

    void OnPaperSelect(BarEntry entry) {
        this.manageMenuCamera_exit();
        this.currentselection = new SelectionPaper(this._menu, entry);
        entry.article.readArticle();
    }

    void OnPaperClose(long _menu, MENUsimplebutton_field button) {
        if (null != this.currentselection) {
            this.currentselection.closeSelection();
            this.currentselection = null;
        }
        this.manageMenuCamera_enter();
        menues.SetShowField(this.m_papermenu.nativePointer, false);
        menues.SetShowField(this.m_articlemenu.nativePointer, false);
        if (this.m_mainmenu != null) {
            menues.SetShowField(this.m_mainmenu.nativePointer, true);
        }
        if (this.m_photopic != null) {
            menues.SetShowField(this.m_photopic.nativePointer, false);
            this.m_photopic = null;
        }
        this.slots.update();
    }

    public void AfterInitMenu(long _menu) {
        this.slots.afterInit();
        menues.WindowSet_ShowCursor(_menu, NotifyInformation.notify_should_show_cursor);
        menues.SetStopWorld(_menu, NotifyInformation.notify_should_stop_world);
        menues.SetMenagPOLOSY(_menu, true);
        menues.setShowMenu(_menu, true);
        if (this.m_singlepaper) {
            this.FillPaper(this.simple_entry);
            this.simple_entry.article.readArticle();
            if (this.m_mainmenu != null) {
                menues.SetShowField(this.m_mainmenu.nativePointer, false);
            }
            return;
        }
        menues.SetShowField(this.m_articlemenu.nativePointer, false);
        MENUText_field t = this.uiTools.FindTextField("News");
        menues.SetShowField(t.nativePointer, false);
        for (int i = 0; i < this.m_photopapers.size(); ++i) {
            PaperPhoto p = (PaperPhoto)this.m_photopapers.get(i);
            menues.SetShowField(p.control.nativePointer, false);
        }
    }

    public void exitMenu(long _menu) {
        if (Bar.getInstance() != null) {
            Bar.getInstance().leaveBar();
        }
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
        menues.ExitBarMENU();
        this.manageMenuCamera_exit();
        gmenu = null;
    }

    public void restartMenu(long _menu) {
    }

    public String getMenuId() {
        return "barMENU";
    }

    public static boolean AnyStartDialogOrExit() {
        if (gmenu == null) {
            return false;
        }
        if (!was_dialog && BarMenu.gmenu.slots.anyDialogStart()) {
            was_dialog = true;
            dialog_ended = false;
            return true;
        }
        if (!dialog_ended) {
            return true;
        }
        menues.CallMenuCallBack_ExitMenu(BarMenu.gmenu._menu);
        return true;
    }

    static {
        was_dialog = false;
        dialog_ended = true;
        was_news = false;
    }

    class MenuCamera {
        long scene = Bar.getInstance().playMenuCamera();

        MenuCamera() {
        }

        void closeSelection() {
            scenetrack.DeleteScene(this.scene);
        }
    }

    class SelectionPaper {
        long scene = 0L;
        long _menu;
        BarEntry entry;

        SelectionPaper(long _menu, BarEntry entry) {
            this._menu = _menu;
            this.entry = entry;
            if (BarMenu.this.m_mainmenu != null) {
                menues.SetShowField(BarMenu.this.m_mainmenu.nativePointer, false);
            }
            if (null != BarMenu.this.animation) {
                this.scene = BarMenu.this.animation.playMoveNeswpaper();
                event.eventObject((int)this.scene, this, "act");
            } else {
                this.act();
            }
        }

        void act() {
            BarMenu.this.FillPaper(this.entry);
        }

        void closeSelection() {
            scenetrack.DeleteScene(this.scene);
        }
    }

    private static class PaperPhoto {
        PaperIndex index;
        MENUText_field control;

        PaperPhoto(PaperIndex index, MENUText_field control) {
            this.index = index;
            this.control = control;
        }
    }

    private class PaperIndex {
        public int index;
        public int type;

        public boolean equals(Object obj) {
            PaperIndex p = (PaperIndex)obj;
            return p.index == this.index && p.type == this.type;
        }

        public PaperIndex(int index, int type) {
            this.index = index;
            this.type = type;
        }

        public PaperIndex(BarEntry entry) {
            this.index = entry.paperindex;
            this.type = entry.type;
        }

        public int hashCode() {
            return this.index << 4 | this.type;
        }
    }

    public static class BarPack {
        public int type;
        public String mission_name;
    }

    public static class BarActor {
        SODialogParams params;
        public String dialogname = "";
        public SCRuniperson model = null;
        public SCRtalkingperson person = null;
    }

    public static class BarEntry {
        public String headline;
        public String papertext;
        public int paperindex;
        public int type;
        public KeyPair[] keys;
        public IcontaktCB winner;
        public IArticle article;
        public String texture_name;
    }
}

