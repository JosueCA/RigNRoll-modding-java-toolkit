/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import menu.FabricControlColor;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.mainmenu.Animation;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.IFadeOutFadeIn;
import menuscript.mainmenu.IWindowContext;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.PanelDialog;
import rnrcore.Log;

public class Panel
implements IWindowContext {
    MainMenu menu;
    private static final String ACTIONMETHOD = "OnAction";
    private static final String EXITMETHOD = "OnExit";
    protected static final String BACK_SUFF = " - BACKGROUND";
    protected static final String EXIT_KEY_SUFF = " Exit";
    protected static final String DEFAULT_KEY_SUFF = " DEFAULT";
    protected static final String OK_KEY_SUFF = " OK";
    protected static final String DIALOG_KEY_PREF = "Button ";
    private HashMap<String, PanelDialog> dialogs = new HashMap();
    private long[] floating_background = null;
    private long[] buttons = null;
    protected HashMap<Integer, String> dials_on_buttons = new HashMap();
    private long exit_key = 0L;
    private long window = 0L;
    private String panelname;
    private ArrayList<String> blind = new ArrayList();
    private LastPanelState lastPanelState = null;

    public String getName() {
        return this.panelname;
    }

    public void restoreBlindness() {
        for (String name : this.blind) {
            long field = this.menu.findField(name);
            if (0L == field) {
                Log.menu("restoreBlindness. Has no such field as " + name);
                continue;
            }
            CA.freezControl(field);
        }
    }

    protected void addBlind(String name) {
        this.blind.add(name);
    }

    protected Panel(MainMenu menu, String panelname, String[] dialogs) {
        this.menu = menu;
        this.panelname = panelname;
        this.floating_background = menu.loadGroup(panelname + BACK_SUFF);
        this.buttons = new long[dialogs.length];
        for (int i = 0; i < dialogs.length; ++i) {
            this.buttons[i] = menu.findField(DIALOG_KEY_PREF + dialogs[i]);
            this.dials_on_buttons.put(new Integer(i), dialogs[i]);
        }
        this.exit_key = menu.findField(panelname + EXIT_KEY_SUFF);
        this.window = menu.findField(panelname);
    }

    protected void add(String name, PanelDialog dialog) {
        this.dialogs.put(name, dialog);
    }

    void init() {
        Set<Map.Entry<String, PanelDialog>> dials = this.dialogs.entrySet();
        for (Map.Entry<String, PanelDialog> entry : dials) {
            PanelDialog single_dial = entry.getValue();
            single_dial.setShow(false);
            single_dial.afterInit();
        }
        this.setShowBackground(false);
        int i = 0;
        while (i < this.buttons.length) {
            MENUsimplebutton_field field = menues.ConvertSimpleButton(this.buttons[i]);
            field.userid = i++;
            menues.UpdateField(field);
            menues.SetScriptOnControl(this.menu._menu, field, this, ACTIONMETHOD, 4L);
        }
        MENUsimplebutton_field exitfield = menues.ConvertSimpleButton(this.exit_key);
        menues.SetScriptOnControl(this.menu._menu, exitfield, this, EXITMETHOD, 4L);
        SMenu _window = menues.ConvertWindow(this.window);
        menues.SetScriptOnControl(this.menu._menu, _window, this, EXITMETHOD, 17L);
    }

    public void OnAction(long _menu, MENUsimplebutton_field button) {
        this.menu.OnDialogOpen(new StartDialog(0));
        PanelDialog dialog = this.dialogs.get(this.dials_on_buttons.get(new Integer(button.userid)));
        dialog.update();
        dialog.setAnimating(true);
        dialog.setShow(false);
        dialog.setFreeze(true);
        this.setShowBackground(false);
        this.setFreezeBackground(true);
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        this.exitWindow();
    }

    public void OnExit(long _menu, SMenu button) {
        this.exitWindow();
    }

    private void exitWindow() {
        this.menu.resetToDefaulScreen();
    }

    public void exitWindowContext() {
        this.exitWindow();
    }

    void OnDialogExit(PanelDialog dialog) {
        dialog.setAnimating(true);
        dialog.setShow(true);
        dialog.setFreeze(true);
        this.setFreezeBackground(true);
        this.setShowBackground(true);
        new FadeOutDialog(new StartDialog(2));
    }

    private void setAlphaBackground(int value) {
        if (null == this.floating_background) {
            return;
        }
        for (int i = 0; i < this.floating_background.length; ++i) {
            FabricControlColor.setControlAlfa(this.floating_background[i], value);
        }
    }

    private void setShowBackground(boolean value) {
        if (null == this.floating_background) {
            return;
        }
        if (value) {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.showControl(this.floating_background[i]);
            }
        } else {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.hideControl(this.floating_background[i]);
            }
        }
    }

    private void setFreezeBackground(boolean value) {
        if (null == this.floating_background) {
            return;
        }
        if (value) {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.freezControl(this.floating_background[i]);
            }
        } else {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.unfreezControl(this.floating_background[i]);
            }
        }
    }

    public void exitMenu() {
        Set<Map.Entry<String, PanelDialog>> dials = this.dialogs.entrySet();
        for (Map.Entry<String, PanelDialog> entry : dials) {
            PanelDialog single_dial = entry.getValue();
            single_dial.exitMenu();
        }
        this.dialogs.clear();
        this.dialogs = null;
    }

    public void restoreLastState() {
        if (null != this.lastPanelState) {
            this.lastPanelState.restoreState();
        }
    }

    public void makeLastState(String name) {
        this.lastPanelState = new LastPanelState(name);
    }

    public void updateWindowContext() {
    }

    public void restoreProfileValuesToPanel() {
        Set<Map.Entry<String, PanelDialog>> set = this.dialogs.entrySet();
        for (Map.Entry<String, PanelDialog> entry : set) {
            entry.getValue().readParamValues();
        }
    }

    class LastPanelState {
        private String dialogOpened;

        LastPanelState(String name) {
            this.dialogOpened = name;
        }

        public void restoreState() {
            String name = this.dialogOpened;
            int userid = -1;
            for (int i = 0; i < Panel.this.buttons.length; ++i) {
                String fname = menues.GetFieldName(Panel.this.buttons[i]);
                if (!fname.contains(name)) continue;
                userid = i;
                break;
            }
            Panel.this.menu.OnDialogOpenImmediate();
            PanelDialog dialog = (PanelDialog)Panel.this.dialogs.get(Panel.this.dials_on_buttons.get(new Integer(userid)));
            dialog.update();
            dialog.setShow(true);
            dialog.setAlpha(255);
            Panel.this.setAlphaBackground(255);
            dialog.setFreeze(false);
            Panel.this.setFreezeBackground(false);
            Panel.this.setShowBackground(true);
        }
    }

    class FadeOutDialog {
        private IFadeOutFadeIn cb = null;

        FadeOutDialog(IFadeOutFadeIn cb) {
            this.cb = cb;
            Panel.this.menu.getClass();
            menues.StopScriptAnimation(8L);
            Panel.this.menu.getClass();
            menues.SetScriptObjectAnimation(0L, 8L, this, "animate");
        }

        void animate(long c, double time) {
            int res_alpha = Animation.alpha_fadeout(time, Panel.this.menu.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            Set dials = Panel.this.dialogs.entrySet();
            for (Map.Entry entry : dials) {
                PanelDialog single_dial = (PanelDialog)entry.getValue();
                if (!single_dial.isAnimating()) continue;
                single_dial.setAlpha(res_alpha);
            }
            Panel.this.setAlphaBackground(res_alpha);
            if (end_animation) {
                this.cb.fadeoutEnded();
                Panel.this.menu.getClass();
                menues.StopScriptAnimation(8L);
            }
        }
    }

    class FadeInDialog {
        private IFadeOutFadeIn cb = null;

        FadeInDialog(IFadeOutFadeIn cb) {
            this.cb = cb;
            Panel.this.menu.getClass();
            menues.StopScriptAnimation(9L);
            Panel.this.menu.getClass();
            menues.SetScriptObjectAnimation(0L, 9L, this, "animate");
        }

        void animate(long c, double time) {
            int res_alpha = Animation.alpha_fadein(time, Panel.this.menu.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            Set dials = Panel.this.dialogs.entrySet();
            for (Map.Entry entry : dials) {
                PanelDialog single_dial = (PanelDialog)entry.getValue();
                if (!single_dial.isAnimating()) continue;
                single_dial.setAlpha(res_alpha);
            }
            Panel.this.setAlphaBackground(res_alpha);
            if (end_animation) {
                this.cb.fadeinEnded();
                Panel.this.menu.getClass();
                menues.StopScriptAnimation(9L);
            }
        }
    }

    public class StartDialog
    implements IFadeOutFadeIn {
        final int STARTMENU_HEADFADED = 0;
        final int DIALOG_FADEDIN = 1;
        final int DIALOG_FADED = 2;
        final int STARTMENU_HEADFADEDIN = 3;
        private int order = 0;

        StartDialog(int order) {
            this.order = order;
        }

        public void fadeinEnded() {
            switch (this.order) {
                case 1: {
                    Set dials = Panel.this.dialogs.entrySet();
                    for (Map.Entry entry : dials) {
                        PanelDialog single_dial = (PanelDialog)entry.getValue();
                        if (!single_dial.isAnimating()) continue;
                        single_dial.setFreeze(false);
                        single_dial.setAnimating(false);
                        Panel.this.menu.rememberPanelState(Panel.this.panelname, (String)entry.getKey());
                    }
                    Panel.this.setFreezeBackground(false);
                    break;
                }
            }
        }

        public void fadeoutEnded() {
            switch (this.order) {
                case 0: {
                    this.order = 1;
                    Set dials = Panel.this.dialogs.entrySet();
                    for (Map.Entry entry : dials) {
                        PanelDialog single_dial = (PanelDialog)entry.getValue();
                        if (!single_dial.isAnimating()) continue;
                        single_dial.setShow(true);
                    }
                    Panel.this.setShowBackground(true);
                    new FadeInDialog(this);
                    break;
                }
                case 2: {
                    this.order = 3;
                    Set dials = Panel.this.dialogs.entrySet();
                    for (Map.Entry entry : dials) {
                        PanelDialog single_dial = (PanelDialog)entry.getValue();
                        if (!single_dial.isAnimating()) continue;
                        single_dial.setShow(false);
                        single_dial.setAnimating(false);
                        Panel.this.menu.forgetPanelState(Panel.this.panelname);
                    }
                    Panel.this.setShowBackground(false);
                    Panel.this.menu.OnDialogClose(this);
                }
            }
        }
    }
}

