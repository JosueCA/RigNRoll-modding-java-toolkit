// Decompiled with: CFR 0.152
// Class Version: 5
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import menu.FabricControlColor;
import menu.IRadioAccess;
import menu.IRadioChangeListener;
import menu.JavaEvents;
import menu.KeyPair;
import menu.ListenerManager;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MacroKit;
import menu.RadioGroup;
import menu.SMenu;
import menu.menucreation;
import menu.menues;
import menuscript.IPoPUpMenuListener;
import menuscript.IQuitMenu;
import menuscript.mainmenu.Animation;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.DLCShopMenu.DLCShopMenu;
import rickroll.log.RickRollLog;
import menuscript.mainmenu.IFadeOutFadeIn;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.Profile;
import menuscript.mainmenu.ProfileManagement;
import menuscript.mainmenu.QuickRace;
import menuscript.mainmenu.QuitMenu;
import menuscript.mainmenu.Replay;
import menuscript.mainmenu.Settings;
import menuscript.mainmenu.SinglePlayer;
import menuscript.mainmenu.TopWindow;
import rnrcore.eng;
import rnrcore.event;
import rnrscenario.scenarioscript;

public class StartMenu
extends MainMenu
implements menucreation,
IQuitMenu {
    private static StartMenu currentmenu = null;
    public double velocitylimit = 1000.0;
    public double fastveldump = 4000.0;
    public double accelerationlimit = 1000.0;
    public double accelerationcoef = 100.0;
    public int slowlimit = 3;
    public int timelimit = 100;
    public int timelimit0 = 50;
    public int timelimitlimit = 500;
    public double timesafecoef = 1.5;
    public double timediffcoef = 0.5;
    public int tab_backminsize = 80;
    private int lastState;
    private boolean needed_default_tab = false;
    private static final String TABS_PREFIX = "BUTTON TAB";
    private static final String BACKGROUND_GROUP_PERMANENT = "COMMON VIDEO";
    private static final String BACKGROUND_GROUP_FLOAT = "MAIN MENU - BACKGROUND";
    private static final String MAIN_GROUP = "MAIN BUTTONS";
    private static final String DEFAULTSCREEN_GROUP = "DEFAULT SCREEN";
    private static final int NOM_REPLAY = 4;
    private static final String BUTONPREFIX = "BUTTON ";
    private static final String TAB_BACK_NAME = "BACK";
    private static final String SLIDER_NAME = "SLIDEPOINTER";
    private static final String[] PROFILE_NAMES = new String[]{"Info - PROFILE - VALUE", "Info - PROFILE - PROFILE", "Info - SINGLE PLAYER - PROFILE", "Info - QUICK RACE - PROFILE", "Info - SETTINGS - PROFILE"};
    private static final String PROFILE_NAME_KEY = "PROFILE";
    private static final String EXITMETHOD = "OnExit";
    public static final int NUM_TABS = 6;
    private int EXIT_BUTTON_NOM = 5;
    private long[][] tabs = new long[6][];
    private long[] background_float = null;
    private long[] screen_float = null;
    private long[] buttonsgroup = null;
    private long[] buttons = new long[6];
    private boolean[] buttons_initiial = new boolean[6];
    private int[] buttonsY = new int[6];
    private long tab_back = 0L;
    MENUText_field back = null;
    private int tab_backmaxsize = 0;
    private long slider_back = 0L;
    private long[] profile_names = null;
    private String[] profile_names_sources = null;
    private RadioGroup rgroup = null;
    private SelectAnimation animation = null;
    private SliderAnimation slideranimation = null;
    private TabAnimations tabanimation = null;
    private long dlcShopButton = 0L;
    private ArrayList<Panel> _Menus = new ArrayList();
    private Panel replayPanel = null;
    DLCShopMenu m_dlcShopMenu;
    private int[] are_blind = new int[]{2, 4};
    public String inVersion;
    private boolean b_selectingDeafult = false;
    private boolean b_fistSelection = false;
    private boolean b_switchingInsideSelection = false;
    private static LastMenuState lastMenuState = null;
    private static StartMenu pr_Menu = null;

    public void restartMenu(long _menu) {
    }

    public static StartMenu getCurrentObject() {
        return currentmenu;
    }

    StartMenu() {
        super("menu_MAIN.xml");
    }

    private void restoreBlindness() {
        if (this.buttons[4] != 0L) {
            menues.SetShowField(this.buttons[4], false);
        }
        if (!scenarioscript.INSTANT_ORDER_ONLY) {
            return;
        }
        for (int i = 0; i < this.are_blind.length; ++i) {
            CA.freezControl(this.buttons[this.are_blind[i]]);
        }
    }

    public static long create() {
        return menues.createSimpleMenu(new StartMenu(), 1.0E9, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static void autotest_create() {
        menues.createSimpleMenu(new StartMenu(), 5.0, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    private void resolve_controls() {
        for (int tabgroups = 0; tabgroups < 6; ++tabgroups) {
            this.buttons[tabgroups] = this.findField(BUTONPREFIX + (tabgroups + 1));
        }
        this.tab_back = this.findField(TAB_BACK_NAME);
        this.slider_back = this.findField(SLIDER_NAME);
    }

    private void initRadioGroups() {
        MENUbutton_field[] buttons_fields = new MENUbutton_field[this.buttons.length];
        for (int i = 0; i < this.buttons.length; ++i) {
            buttons_fields[i] = menues.ConvertButton(this.buttons[i]);
            buttons_fields[i].userid = i;
            this.buttonsY[i] = buttons_fields[i].poy;
            if (i != 0) {
                int n = i;
                this.buttonsY[n] = this.buttonsY[n] - this.buttonsY[0];
            }
            menues.UpdateField(buttons_fields[i]);
        }
        if (this.buttonsY.length != 0) {
            this.buttonsY[0] = 0;
        }
        this.rgroup = new RadioGroup(this._menu, buttons_fields);
        this.rgroup.addAccessListener(new RadioButtonAccess());
        this.rgroup.addListener(this.animation);
    }

    public void InitMenu(long _menu) {
        TopWindow.Register(this);
        pr_Menu = this;
        if (null != lastMenuState) {
            lastMenuState.restore(this);
        }
        menues.SetMenagPOLOSY(_menu, false);
        currentmenu = this;
        this._menu = _menu;
        this.loadGroup(BACKGROUND_GROUP_PERMANENT);
        this.background_float = this.loadGroup(BACKGROUND_GROUP_FLOAT);
        this.screen_float = this.loadGroup(DEFAULTSCREEN_GROUP);
        this.buttonsgroup = this.loadGroup(MAIN_GROUP);
        for (int tabgroups = 0; tabgroups < 6; ++tabgroups) {
            this.tabs[tabgroups] = this.loadGroup(TABS_PREFIX + (tabgroups + 1));
        }
        this._Menus.add(new SinglePlayer(this));
        this._Menus.add(new Settings(this));
        this._Menus.add(new QuitMenu(this));
        this._Menus.add(new Profile(this));
        this._Menus.add(new QuickRace(this));
        this.dlcShopButton = menues.FindFieldInMenu(_menu, "Button DLC STORE");
        for (boolean initial : this.buttons_initiial) {
            initial = true;
        }
        this.m_dlcShopMenu = new DLCShopMenu(_menu, this);
    }

    public void OnOpenDLCShopPressed(long _menu, MENUbutton_field button) {
        if (eng._dontShowDLCButton) {
            return;
        }
        this.m_dlcShopMenu.show();
    }

    private void initanimations() {
        this.back = menues.ConvertTextFields(this.tab_back);
        this.tab_backmaxsize = this.back.lenx;
        this.animation = new SelectAnimation();
        this.slideranimation = null;
        this.tabanimation = new TabAnimations();
        menues.SetScriptOnControl(this._menu, menues.ConvertWindow(menues.GetBackMenu(this._menu)), this, EXITMETHOD, 17L);
    }

    public void update_profile_name() {
        if (null != this.profile_names) {
            String nm = new String();
            nm = ProfileManagement.getProfileManager().GetCurrentProfileName();
            String upper_name = nm.toUpperCase();
            KeyPair[] pair = new KeyPair[]{new KeyPair(PROFILE_NAME_KEY, upper_name)};
            for (int i = 0; i < this.profile_names.length; ++i) {
                if (this.profile_names_sources[i] == null) continue;
                String res = MacroKit.Parse(this.profile_names_sources[i], pair);
                menues.SetFieldText(this.profile_names[i], res);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.profile_names[i]));
            }
        }
    }

    public void AfterInitMenu(long _menu) {
        MENUText_field text;
        ListenerManager.TriggerEvent(104);
        this.profile_names = new long[PROFILE_NAMES.length];
        this.profile_names_sources = new String[PROFILE_NAMES.length];
        for (int i = 0; i < PROFILE_NAMES.length; ++i) {
            this.profile_names[i] = menues.FindFieldInMenu(_menu, PROFILE_NAMES[i]);
            this.profile_names_sources[i] = menues.GetFieldText(this.profile_names[i]);
        }
        this.update_profile_name();
        this.resolve_controls();
        this.initanimations();
        this.initRadioGroups();
        Iterator<Panel> iter = this._Menus.iterator();
        while (iter.hasNext()) {
            iter.next().init();
        }
        this.initialScreen();
        this.restoreLastState();
        this.accesorries();
        this.restoreBlindness();
        long version = menues.FindFieldInMenu(_menu, "The Version");
        if (version != 0L && (text = (MENUText_field)menues.ConvertMenuFields(version)) != null && text.text != null) {
            JavaEvents.SendEvent(71, 20, this);
            if (this.inVersion != null) {
                KeyPair[] pairs = new KeyPair[]{new KeyPair("RNR_VERSION", this.inVersion)};
                text.text = MacroKit.Parse(text.text, pairs);
                menues.UpdateField(text);
            }
        }
        this.m_dlcShopMenu.afterInit(_menu);
        if (this.dlcShopButton != 0L) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.dlcShopButton), this, "OnOpenDLCShopPressed", 2L);
        }
        eng.console("bik play -a -l -s ..\\Data\\video\\mainmenuvideo.bik 0");
    }

    private void restoreLastState() {
        if (this.needed_default_tab) {
            this.b_selectingDeafult = true;
            this.rgroup.select(this.lastState);
            this.b_selectingDeafult = false;
            for (int control = 0; control < this.tabs[this.lastState].length; ++control) {
                menues.SetShowField(this.tabs[this.lastState][control], true);
            }
            CA.showControl(this.slider_back);
            CA.showControl(this.tab_back);
            Set<Entry<String, String>> entry = this.panelDialogStates.entrySet();
            for (Map.Entry entr : entry) {
                String name = (String)entr.getKey();
                for (Panel pan : this._Menus) {
                    if (name.compareTo(pan.getName()) != 0) continue;
                    pan.makeLastState((String)entr.getValue());
                }
            }
            Iterator<Panel> menuiter = this._Menus.iterator();
            while (menuiter.hasNext()) {
                menuiter.next().restoreLastState();
            }
        }
    }

    private void accesorries() {
        menues.setShowMenu(this._menu, true);
        menues.WindowSet_ShowCursor(this._menu, true);
        menues.SetStopWorld(this._menu, true);
    }

    public void exitMenu(long _menu) {
        ListenerManager.TriggerEvent(105);
        TopWindow.UnRegister(this);
        pr_Menu = null;
        currentmenu = null;
        Iterator<Panel> iter = this._Menus.iterator();
        while (iter.hasNext()) {
            iter.next().exitMenu();
        }
        this.skippAniations();
        if (!eng.noNative) {
            event.Setevent(8001);
        }
        eng.console("bik stop");
        JavaEvents.SendEvent(3, 0, this);
        this.skippAniations();
        menues.StopScriptAnimation(7L);
        menues.StopScriptAnimation(6L);
        this.m_dlcShopMenu.deinit();
    }

    public String getMenuId() {
        return "mainMENU";
    }

    public void OnExit(long _menu, SMenu window) {
        this.rgroup.select(this.EXIT_BUTTON_NOM);
    }

    private void initialScreen() {
        for (int i = 0; i < 6; ++i) {
            for (int control = 0; control < this.tabs[i].length; ++control) {
                menues.SetShowField(this.tabs[i][control], false);
            }
        }
        CA.hideControl(this.slider_back);
        CA.hideControl(this.tab_back);
        this.b_fistSelection = true;
    }

    void resetToDefaulScreen() {
        this.freezeOnDialogEnd();
        this.b_switchingInsideSelection = true;
        this.tabanimation.turnoff();
        this.b_fistSelection = true;
        this.b_switchingInsideSelection = false;
    }

    private SliderAnimation getSlider() {
    	RickRollLog.dumpStackTrace("StartMenu getSlider();");
        if (null == this.slideranimation) {
            this.slideranimation = new SliderAnimation();
        }
        return this.slideranimation;
    }

    private void moveSliderToButton(int button_nom) {
        if (button_nom >= 6) {
            eng.err("Bad moveSliderToButton");
            return;
        }
        this.getSlider().setReachPoint(this.buttonsY[button_nom]);
    }

    private void skippAniations() {
        menues.StopScriptAnimation(2L);
        menues.StopScriptAnimation(1L);
        menues.StopScriptAnimation(3L);
        menues.StopScriptAnimation(5L);
        menues.StopScriptAnimation(4L);
    }

    private void skippFadeAniations() {
        menues.StopScriptAnimation(2L);
        menues.StopScriptAnimation(1L);
        menues.StopScriptAnimation(5L);
        menues.StopScriptAnimation(4L);
    }

    void unfreezeOnDialogEnd() {
        int i;
        this.tabanimation.restoreTabs();
        for (i = 0; i < this.background_float.length; ++i) {
            CA.unfreezControl(this.background_float[i]);
        }
        for (i = 0; i < this.buttons.length; ++i) {
            CA.unfreezControl(this.buttons[i]);
        }
        this.restoreBlindness();
    }

    void freezeOnDialogEnd() {
        int i;
        this.tabanimation.hideTabs();
        for (i = 0; i < this.background_float.length; ++i) {
            CA.freezControl(this.background_float[i]);
        }
        for (i = 0; i < this.buttons.length; ++i) {
            CA.freezControl(this.buttons[i]);
        }
        this.restoreBlindness();
    }

    public void OnDialogOpen(IFadeOutFadeIn cb) {
        this.freezeOnDialogEnd();
        new FadeOutHead(this.tabanimation.getCurrent(), new OnDialogActions(cb));
    }

    public void OnDialogOpenImmediate() {
        int i;
        this.unfreezeOnDialogEnd();
        CA.showControl(this.slider_back);
        CA.showControl(this.tab_back);
        int group = this.tabanimation.getCurrent();
        this.getSlider().setinPosition(this.buttonsY[group]);
        int res_aloha = 0;
        for (i = 0; i < this.tabs[group].length; ++i) {
            FabricControlColor.setControlAlfa(this.tabs[group][i], res_aloha);
        }
        FabricControlColor.setControlAlfa(this.tab_back, res_aloha);
        FabricControlColor.setControlAlfa(this.slider_back, res_aloha);
        for (i = 0; i < this.background_float.length; ++i) {
            FabricControlColor.setControlAlfa(this.background_float[i], res_aloha);
        }
        for (i = 0; i < this.buttonsgroup.length; ++i) {
            FabricControlColor.setControlAlfa(this.buttonsgroup[i], res_aloha);
        }
        for (i = 0; i < this.screen_float.length; ++i) {
            FabricControlColor.setControlAlfa(this.screen_float[i], res_aloha);
        }
    }

    public void OnDialogClose(IFadeOutFadeIn cb) {
        new FadeInHead(this.tabanimation.getCurrent(), new OnDialogActions(cb));
    }

    public static void menuNeedRestoreLastState() {
        if (null != pr_Menu) {
            lastMenuState = new LastMenuState(StartMenu.pr_Menu.panelDialogStates, StartMenu.pr_Menu.lastState, true);
        }
    }

    public void quitMenu() {
        menues.CallMenuCallBack_ExitMenu(StartMenu.getCurrentObject()._menu);
    }

    public static void DebugQuitMenu() {
        menues.CallMenuCallBack_ExitMenu(StartMenu.getCurrentObject()._menu);
    }

    public void restoreProfileValuesToMenu() {
        for (Panel panel : this._Menus) {
            panel.restoreProfileValuesToPanel();
        }
    }

    public void HideAllPanels(boolean bHide) {
        if (bHide) {
            this.resetToDefaulScreen();
            for (int i = 0; i < this.buttons.length; ++i) {
                if (this.buttons[i] == 0L) continue;
                this.buttons_initiial[i] = menues.GetShowField(this.buttons[i]);
                menues.SetShowField(this.buttons[i], false);
            }
            for (long field : this.background_float) {
                if (field == 0L) continue;
                menues.SetShowField(field, false);
            }
            if (this.dlcShopButton != 0L) {
                menues.SetShowField(this.dlcShopButton, false);
            }
        } else {
            for (int i = 0; i < this.buttons.length; ++i) {
                if (this.buttons[i] == 0L) continue;
                menues.SetShowField(this.buttons[i], this.buttons_initiial[i]);
            }
            for (long field : this.background_float) {
                if (field == 0L) continue;
                menues.SetShowField(field, true);
            }
            if (this.dlcShopButton != 0L) {
                menues.SetFieldState(this.dlcShopButton, 0);
                menues.SetShowField(this.dlcShopButton, true);
            }
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class LastMenuState {
        private HashMap<String, String> last_panelDialogStates;
        private int last_lastTab;
        private boolean last_needed_default_tab = false;

        private LastMenuState(HashMap<String, String> last_panelDialogStates, int last_lastTab, boolean last_needed_default_tab) {
            this.last_panelDialogStates = last_panelDialogStates;
            this.last_lastTab = last_lastTab;
            this.last_needed_default_tab = last_needed_default_tab;
        }

        void restore(StartMenu menu) {
            menu.needed_default_tab = this.last_needed_default_tab;
            menu.lastState = this.last_lastTab;
            menu.panelDialogStates = this.last_panelDialogStates;
        }
    }

    class DefaultScreenFade
    implements IFadeOutFadeIn {
        DefaultScreenFade() {
        }

        public void fadeinEnded() {
            CA.hideControl(StartMenu.this.slider_back);
            CA.hideControl(StartMenu.this.tab_back);
            StartMenu.this.tabanimation.turnoffTab(StartMenu.this.tabanimation.getCurrent());
            StartMenu.this.unfreezeOnDialogEnd();
        }

        public void fadeoutEnded() {
            StartMenu.this.unfreezeOnDialogEnd();
            CA.showControl(StartMenu.this.slider_back);
            CA.showControl(StartMenu.this.tab_back);
            StartMenu.this.tabanimation.appear();
        }
    }

    class DefaultScreenAnimation
    implements IFadeOutFadeIn {
        private IFadeOutFadeIn cb = null;

        DefaultScreenAnimation() {
        }

        DefaultScreenAnimation(boolean is_fadein) {
            StartMenu.this.freezeOnDialogEnd();
            this.cb = new DefaultScreenFade();
            menues.StopScriptAnimation(is_fadein ? 7L : 6L);
            menues.SetScriptObjectAnimation(0L, is_fadein ? 7L : 6L, this, is_fadein ? "fadein" : "fadeout");
        }

        void fadein(long c, double time) {
            int res_alpha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            for (int i = 0; i < StartMenu.this.screen_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.screen_float[i], res_alpha);
            }
            if (end_animation) {
                if (null != this.cb) {
                    this.cb.fadeinEnded();
                }
                menues.StopScriptAnimation(7L);
            }
        }

        void fadeout(long c, double time) {
            int res_alpha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();
            for (int i = 0; i < StartMenu.this.screen_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.screen_float[i], res_alpha);
            }
            if (end_animation) {
                if (null != this.cb) {
                    this.cb.fadeoutEnded();
                }
                menues.StopScriptAnimation(6L);
            }
        }

        public void fadeinEnded() {
        }

        public void fadeoutEnded() {
            new DefaultScreenAnimation(true);
        }
    }

    class FadeInHead {
        private IFadeOutFadeIn cb = null;

        FadeInHead(int tab_nom, IFadeOutFadeIn cb) {
            this.cb = cb;
            menues.StopScriptAnimation(7L);
            menues.SetScriptObjectAnimation(tab_nom, 7L, this, "animate");
        }

        void animate(long c, double time) {
            int i;
            int group = (int)c;
            int res_aloha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            for (i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }
            for (i = 0; i < StartMenu.this.background_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.background_float[i], res_aloha);
            }
            for (i = 0; i < StartMenu.this.buttonsgroup.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.buttonsgroup[i], res_aloha);
            }
            if (end_animation) {
                this.cb.fadeinEnded();
                menues.StopScriptAnimation(7L);
            }
        }
    }

    class FadeOutHead {
        private IFadeOutFadeIn cb = null;

        FadeOutHead(int tab_nom, IFadeOutFadeIn cb) {
            this.cb = cb;
            menues.StopScriptAnimation(6L);
            menues.SetScriptObjectAnimation(tab_nom, 6L, this, "animate");
        }

        void animate(long c, double time) {
            int i;
            int group = (int)c;
            int res_aloha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();
            for (i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }
            for (i = 0; i < StartMenu.this.background_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.background_float[i], res_aloha);
            }
            for (i = 0; i < StartMenu.this.buttonsgroup.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.buttonsgroup[i], res_aloha);
            }
            if (end_animation) {
                this.cb.fadeoutEnded();
                menues.StopScriptAnimation(6L);
            }
        }
    }

    class InWarningEmptyList
    implements IPoPUpMenuListener {
        InWarningEmptyList() {
        }

        public void onAgreeclose() {
        }

        public void onClose() {
        }

        public void onCancel() {
        }

        public void onOpen() {
            StartMenu.this.tabanimation.selectTabOnAnimation(StartMenu.this.tabanimation.pendedtab);
            StartMenu.this.resetToDefaulScreen();
        }
    }

    class OnDialogActions
    implements IFadeOutFadeIn {
        IFadeOutFadeIn cb;

        OnDialogActions(IFadeOutFadeIn cb) {
            this.cb = cb;
        }

        public void fadeinEnded() {
            if (null != this.cb) {
                this.cb.fadeinEnded();
            }
            StartMenu.this.unfreezeOnDialogEnd();
        }

        public void fadeoutEnded() {
            if (null != this.cb) {
                this.cb.fadeoutEnded();
            }
        }
    }

    public class TabAnimations
    implements IFadeOutFadeIn {
        private int currenttab;
        private int pendedtab;
        private boolean animating;

        public TabAnimations() {
            this.currenttab = StartMenu.this.lastState;
        }

        public void appear() {
            this.pendedtab = this.currenttab;
            new UnfoldPanelEnd();
            this.turnonTab(this.pendedtab);
            this.freezeTab(this.pendedtab);
            new FadeInCurrentPanel(this);
            StartMenu.this.getSlider().setinPosition(StartMenu.this.buttonsY[this.pendedtab]);
        }

        public void turnoff() {
            StartMenu.this.rgroup.deselectall();
            new FadeOutCurrentPanel(new DefaultScreenAnimation());
        }

        protected void setupTab(int tab) {
            this.currenttab = tab;
        }

        public void selectTabOnAnimation(int tab) {
            if (this.animating) {
                this.makeUnfoldOnly(tab);
            } else {
                if (tab == this.currenttab) {
                    return;
                }
                this.makeWholeAnimation(tab);
            }
        }

        public void foldingEnded() {
            new UnfoldPanel();
        }

        public void unfoldingEnded() {
            this.turnonTab(this.pendedtab);
            this.freezeTab(this.pendedtab);
            new FadeInPanel(this.pendedtab, this);
        }

        public void fadeinEnded() {
            this.currenttab = this.pendedtab;
            this.unfreezeTab(this.currenttab);
            StartMenu.this.restoreBlindness();
            this.animating = false;
            StartMenu.this.lastState = this.currenttab;
        }

        public void fadeoutEnded() {
            this.turnoffTab(this.currenttab);
            new FoldPanel();
        }

        private void makeUnfoldOnly(int tab) {
            if (tab == this.pendedtab) {
                this.freezeTab(this.currenttab);
                this.turnoffTab(this.currenttab);
                this.currenttab = this.pendedtab;
                this.unfreezeTab(this.currenttab);
                this.turnonTab(this.currenttab);
                new FadeInPanelEnd(this.currenttab);
                StartMenu.this.restoreBlindness();
                new UnfoldPanelEnd();
                this.animating = false;
                StartMenu.this.skippFadeAniations();
                return;
            }
            StartMenu.this.skippFadeAniations();
            this.turnoffTab(this.pendedtab);
            if (this.currenttab != this.pendedtab) {
                this.turnoffTab(this.currenttab);
            }
            this.freezeTab(this.pendedtab);
            this.pendedtab = tab;
            this.animating = true;
            new UnfoldPanel();
        }

        private void makeWholeAnimation(int tab) {
            this.animating = true;
            this.pendedtab = tab;
            this.freezeTab(this.currenttab);
            this.freezeTab(this.pendedtab);
            new FadeOutPanel(this.currenttab, this);
        }

        private void turnoffTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. turnoffTab");
                return;
            }
            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.hideControl(StartMenu.this.tabs[tab][control]);
            }
        }

        private void turnonTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. turnonTab");
                return;
            }
            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.showControl(StartMenu.this.tabs[tab][control]);
            }
        }

        private void freezeTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. freezeTab");
                return;
            }
            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.freezControl(StartMenu.this.tabs[tab][control]);
            }
        }

        private void unfreezeTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. unfreezeTab");
                return;
            }
            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.unfreezControl(StartMenu.this.tabs[tab][control]);
            }
        }

        int getCurrent() {
            return this.currenttab;
        }

        void hideTabs() {
            this.freezeTab(this.currenttab);
        }

        void restoreTabs() {
            this.unfreezeTab(this.currenttab);
        }
    }

    class SliderAnimation {
        private boolean f_parking = false;
        private double time_parking_end = 0.0;
        private double time_parking_start = 0.0;
        private int pos_parking_start = 0;
        private double vel_parking_start = 0.0;
        private double accelparking = 0.0;
        private MENUText_field slider_button = null;
        private int initialposition = 0;
        private int reachpoint = 0;
        private int currentpoint = 0;
        private double previoustime = 0.0;
        private double dt = 0.0;
        private double velocity = 0.0;

        SliderAnimation() {
            this.slider_button = menues.ConvertTextFields(StartMenu.this.slider_back);
            this.initialposition = this.slider_button.poy;
            menues.SetScriptObjectAnimation(StartMenu.this.slider_back, 3L, this, "animate");
            this.currentpoint = this.reachpoint;
        }

        private void deltaTime(double time) {
            this.dt = time - this.previoustime;
            this.previoustime = time;
            if (this.dt < 0.0) {
                eng.err("reverce move of time");
                return;
            }
        }

        private void updateSlider() {
            this.slider_button.poy = this.initialposition + this.currentpoint;
            menues.UpdateField(this.slider_button);
        }

        void setReachPoint(int point) {
            int diff = Math.abs(this.currentpoint - point);
            StartMenu.this.timelimit = (double)diff < StartMenu.this.timesafecoef * (double)StartMenu.this.timelimit0 ? StartMenu.this.timelimit0 : StartMenu.this.timelimit0 + (int)(((double)diff - StartMenu.this.timesafecoef * (double)StartMenu.this.timelimit0) * StartMenu.this.timediffcoef);
            if (StartMenu.this.timelimit > StartMenu.this.timelimitlimit) {
                StartMenu.this.timelimit = StartMenu.this.timelimitlimit;
            }
            this.reachpoint = point;
            this.f_parking = false;
        }

        protected void setinPosition(int reachpoint) {
            this.currentpoint = reachpoint;
            this.reachpoint = reachpoint;
            this.updateSlider();
        }

        void animate(long button, double time) {
            this.deltaTime(time);
            if (this.currentpoint == this.reachpoint) {
                return;
            }
            if (this.f_parking) {
                this.parking(time);
                this.updateSlider();
                return;
            }
            long diff = this.reachpoint - this.currentpoint;
            if (diff > 0L && diff < (long)StartMenu.this.slowlimit) {
                ++this.currentpoint;
                this.updateSlider();
                return;
            }
            if (diff < 0L && diff > (long)(-StartMenu.this.slowlimit)) {
                --this.currentpoint;
                this.updateSlider();
                return;
            }
            if (diff < 0L && diff > (long)(-StartMenu.this.timelimit)) {
                this.f_parking = true;
                this.accelparking = this.velocity * this.velocity / (2.0 * (double)diff);
                if (this.velocity < 0.0) {
                    this.accelparking *= -1.0;
                } else {
                    this.accelparking *= -1.0;
                    this.velocity *= -1.0;
                }
                this.time_parking_start = time - this.dt;
                this.time_parking_end = time + Math.abs(this.velocity / this.accelparking);
                this.pos_parking_start = this.currentpoint;
                this.vel_parking_start = this.velocity;
                this.parking(time);
                this.updateSlider();
                return;
            }
            if (diff > 0L && diff < (long)StartMenu.this.timelimit) {
                this.f_parking = true;
                this.accelparking = this.velocity * this.velocity / (2.0 * (double)diff);
                if (this.velocity > 0.0) {
                    this.accelparking *= -1.0;
                } else {
                    this.accelparking *= -1.0;
                    this.velocity *= -1.0;
                }
                this.time_parking_start = time - this.dt;
                this.time_parking_end = time + Math.abs(this.velocity / this.accelparking);
                this.pos_parking_start = this.currentpoint;
                this.vel_parking_start = this.velocity;
                this.parking(time);
                this.updateSlider();
                return;
            }
            double accel = StartMenu.this.accelerationcoef * (double)diff;
            if (accel > 0.0 && accel > StartMenu.this.accelerationlimit) {
                accel = StartMenu.this.accelerationlimit;
            } else if (accel < 0.0 && accel < -StartMenu.this.accelerationlimit) {
                accel = -StartMenu.this.accelerationlimit;
            }
            if (accel * this.velocity < 0.0) {
                this.velocity = this.velocity > 0.0 ? (this.velocity -= StartMenu.this.fastveldump * this.dt) : (this.velocity += StartMenu.this.fastveldump * this.dt);
            }
            this.velocity += accel * this.dt;
            if (this.velocity > 0.0 && this.velocity > StartMenu.this.velocitylimit) {
                this.velocity = StartMenu.this.velocitylimit;
            } else if (this.velocity < 0.0 && this.velocity < -StartMenu.this.velocitylimit) {
                this.velocity = -StartMenu.this.velocitylimit;
            }
            this.currentpoint += (int)(this.velocity * this.dt);
            this.updateSlider();
        }

        private void parking(double time) {
            if (time >= this.time_parking_end) {
                this.currentpoint = this.reachpoint;
                this.velocity = 0.0;
                this.f_parking = false;
                return;
            }
            double T = time - this.time_parking_start;
            this.currentpoint = (int)((double)this.pos_parking_start + (this.vel_parking_start + this.accelparking * T * 0.5) * T);
            this.velocity = this.vel_parking_start + this.accelparking * T;
            if (this.currentpoint == this.reachpoint) {
                this.velocity = 0.0;
                this.f_parking = false;
            }
        }
    }

    class UnfoldPanelEnd {
        UnfoldPanelEnd() {
            MENUText_field back = menues.ConvertTextFields(StartMenu.this.tab_back);
            back.lenx = StartMenu.this.tab_backmaxsize;
            menues.UpdateField(back);
        }
    }

    class UnfoldPanel {
        UnfoldPanel() {
            menues.StopScriptAnimation(5L);
            menues.SetScriptObjectAnimation(0L, 5L, this, "animate");
        }

        void animate(long c, double time) {
            double period = StartMenu.this.PERIODUNFOLD;
            double size = (double)StartMenu.this.tab_backminsize + (double)(StartMenu.this.tab_backmaxsize - StartMenu.this.tab_backminsize) * time / period;
            boolean end_animation = false;
            if (size >= (double)StartMenu.this.tab_backmaxsize) {
                size = StartMenu.this.tab_backmaxsize;
                end_animation = true;
            }
            MENUText_field back = menues.ConvertTextFields(StartMenu.this.tab_back);
            back.lenx = (int)size;
            menues.UpdateField(back);
            if (end_animation) {
                StartMenu.this.tabanimation.unfoldingEnded();
                menues.StopScriptAnimation(5L);
            }
        }
    }

    class FoldPanel {
        FoldPanel() {
            menues.StopScriptAnimation(4L);
            menues.SetScriptObjectAnimation(0L, 4L, this, "animate");
        }

        void animate(long c, double time) {
            double period = StartMenu.this.PERIODFOLD;
            double size = (double)StartMenu.this.tab_backminsize + (double)(StartMenu.this.tab_backmaxsize - StartMenu.this.tab_backminsize) * (period - time) / period;
            boolean end_animation = false;
            if (size <= (double)StartMenu.this.tab_backminsize) {
                size = StartMenu.this.tab_backminsize;
                end_animation = true;
            }
            StartMenu.this.back.lenx = (int)size;
            menues.UpdateField(StartMenu.this.back);
            if (end_animation) {
                StartMenu.this.tabanimation.foldingEnded();
                menues.StopScriptAnimation(4L);
            }
        }
    }

    class FadeInPanelEnd {
        FadeInPanelEnd(int group) {
            int res_aloha = 255;
            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }
        }
    }

    class FadeInCurrentPanel {
        private IFadeOutFadeIn cb = null;

        FadeInCurrentPanel(IFadeOutFadeIn cb) {
            this.cb = cb;
            menues.StopScriptAnimation(2L);
            menues.SetScriptObjectAnimation(0L, 2L, this, "animate");
        }

        void animate(long c, double time) {
            int group = StartMenu.this.tabanimation.getCurrent();
            int res_alpha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_alpha);
            }
            FabricControlColor.setControlAlfa(StartMenu.this.tab_back, res_alpha);
            FabricControlColor.setControlAlfa(StartMenu.this.slider_back, res_alpha);
            if (end_animation) {
                this.cb.fadeinEnded();
                menues.StopScriptAnimation(2L);
            }
        }
    }

    class FadeInPanel {
        private IFadeOutFadeIn cb = null;

        FadeInPanel(long group, IFadeOutFadeIn cb) {
            this.cb = cb;
            menues.StopScriptAnimation(2L);
            menues.SetScriptObjectAnimation(group, 2L, this, "animate");
        }

        void animate(long c, double time) {
            int group = (int)c;
            int res_aloha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }
            if (end_animation) {
                this.cb.fadeinEnded();
                menues.StopScriptAnimation(2L);
            }
        }
    }

    class FadeOutCurrentPanel {
        private IFadeOutFadeIn cb = null;

        FadeOutCurrentPanel(IFadeOutFadeIn cb) {
            this.cb = cb;
            menues.StopScriptAnimation(1L);
            menues.SetScriptObjectAnimation(0L, 1L, this, "animate");
        }

        void animate(long c, double time) {
            int group = StartMenu.this.tabanimation.getCurrent();
            int res_alpha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();
            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_alpha);
            }
            FabricControlColor.setControlAlfa(StartMenu.this.tab_back, res_alpha);
            FabricControlColor.setControlAlfa(StartMenu.this.slider_back, res_alpha);
            if (end_animation) {
                this.cb.fadeoutEnded();
                menues.StopScriptAnimation(1L);
            }
        }
    }

    class FadeOutPanel {
        private IFadeOutFadeIn cb = null;

        FadeOutPanel(long group, IFadeOutFadeIn cb) {
            this.cb = cb;
            menues.StopScriptAnimation(1L);
            menues.SetScriptObjectAnimation(group, 1L, this, "animate");
        }

        void animate(long c, double time) {
            int group = (int)c;
            int res_aloha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();
            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }
            if (end_animation) {
                this.cb.fadeoutEnded();
                menues.StopScriptAnimation(1L);
            }
        }
    }

    class SelectAnimation
    implements IRadioChangeListener {
        SelectAnimation() {
        }

        public final void controlSelected(MENUbutton_field button, int cs) {
            if (StartMenu.this.b_selectingDeafult || StartMenu.this.b_switchingInsideSelection) {
                StartMenu.this.b_fistSelection = false;
                StartMenu.this.tabanimation.setupTab(button.userid);
                return;
            }
            if (StartMenu.this.b_fistSelection) {
                if (4 == button.userid) {
                    return;
                }
                StartMenu.this.b_fistSelection = false;
                StartMenu.this.tabanimation.setupTab(button.userid);
                new DefaultScreenAnimation(false);
                return;
            }
            if (4 == button.userid) {
                if (((Replay)StartMenu.this.replayPanel).isClipsListEmpty()) {
                    ((Replay)StartMenu.this.replayPanel).warnAboutEmptyList();
                    return;
                }
                ((Replay)StartMenu.this.replayPanel).updateWindowContext();
            }
            StartMenu.this.tabanimation.selectTabOnAnimation(button.userid);
            StartMenu.this.moveSliderToButton(button.userid);
        }
    }

    class RadioButtonAccess
    implements IRadioAccess {
        RadioButtonAccess() {
        }

        public boolean controlAccessed(MENUbutton_field button, int state) {
            return state == 1;
        }
    }
}
