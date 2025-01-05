/*
 * Decompiled with CFR 0.151.
 */
package menuscript.cbvideo;

import menu.MENUText_field;
import menu.MenuAfterInitNarrator;
import menu.MenuControls;
import menu.menucreation;
import menu.menues;
import menuscript.IMenuListener;
import menuscript.MenuListeners;
import rnrcore.Log;

public class MenuNotify
implements menucreation {
    private static final String FILE = "..\\data\\config\\menu\\menu_com.xml";
    private static final String GROUP = "CB RADIO - CALL NOTIFICATION";
    private static final String ANIMATIONMETHOD = "animate";
    private static final String CONTROL_ANIM_LINE = "Field RED - ProgressBar";
    private final long animationID = 0L;
    private double time_animate = 0.0;
    private long _menu = 0L;
    private MenuControls controls = null;
    private int initialBarLength = 0;
    private MenuListeners listeners = new MenuListeners();

    public void restartMenu(long _menu) {
    }

    public void addListener(IMenuListener listener) {
        this.listeners.addListener(listener);
    }

    public void InitMenu(long _menu) {
        this._menu = _menu;
        this.controls = new MenuControls(_menu, FILE, GROUP);
    }

    public void AfterInitMenu(long _menu) {
        long line = this.controls.findControl(CONTROL_ANIM_LINE);
        if (line == 0L) {
            Log.menu("ERRORR. MenuNotify. Cannot find control Field RED - ProgressBar for progree bar animation");
            return;
        }
        MENUText_field field = menues.ConvertTextFields(line);
        this.initialBarLength = field.lenx;
        menues.SetScriptObjectAnimation(line, 0L, this, ANIMATIONMETHOD);
        this.controls.setNoFocusOnControls();
        menues.SetIgnoreEvents(menues.GetBackMenu(_menu), true);
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
        menues.StopScriptAnimation(0L);
        this.listeners.close();
    }

    public String getMenuId() {
        return "cbvnotifyMENU";
    }

    public void animate(long cook, double time) {
        if (time > this.time_animate || this.time_animate <= 0.0) {
            this.close();
            return;
        }
        MENUText_field field = menues.ConvertTextFields(cook);
        field.lenx = (int)((double)this.initialBarLength * (1.0 - time / this.time_animate));
        menues.UpdateField(field);
    }

    public MenuNotify(double time) {
        this.time_animate = time;
    }

    public static MenuNotify create(double time) {
        MenuNotify menu = new MenuNotify(time);
        menu._menu = menues.createSimpleMenu(menu, 3, "ENTER");
        return menu;
    }

    public long getMenuDescriptor() {
        return this._menu;
    }

    public void close() {
        menues.StopScriptAnimation(0L);
        menues.CallMenuCallBack_ExitMenu(this._menu);
    }
}

