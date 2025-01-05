/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SMenu;
import menu.menues;
import menuscript.PoPUpMenu;

public class PopUpSelfClose
extends PoPUpMenu {
    private static final double TIME = 15.0;
    private static final String COUNTDOUN = "PopUpWarning - COUNTDOWN";
    private long countdownTextField = 0L;

    public PopUpSelfClose(long _menu, String xmlFile, String controlGroup, String windowName) {
        super(_menu, xmlFile, controlGroup, windowName);
        this.countdownTextField = menues.FindFieldInMenu(_menu, COUNTDOUN);
    }

    private void updateCountdown(int time) {
        MENUText_field tf = menues.ConvertTextFields(this.countdownTextField);
        if (null != tf) {
            KeyPair[] keys = new KeyPair[]{new KeyPair("SECONDS", "" + time)};
            MacroKit.ApplyToTextfield(tf, keys);
        }
    }

    public void show() {
        menues.SetScriptObjectAnimation(0L, 299L, this, "animateClose");
        super.show();
    }

    public void OnCancel(long _menu, MENUsimplebutton_field field) {
        this.stopCloseAnimating();
        super.OnCancel(_menu, field);
    }

    public void OnExit(long _menu, SMenu field) {
        this.stopCloseAnimating();
        super.OnExit(_menu, field);
    }

    public void OnOk(long _menu, MENUsimplebutton_field field) {
        this.stopCloseAnimating();
        super.OnOk(_menu, field);
    }

    private void stopCloseAnimating() {
        menues.StopScriptAnimation(299L);
    }

    void animateClose(long _menu, double time) {
        if (time > 15.0) {
            this.OnCancel(0L, null);
            return;
        }
        this.updateCountdown((int)(15.0 - time));
    }
}

