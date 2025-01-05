/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.PoPUpMenu;

public class TooltipButton {
    private static final String METH = "onButton";
    private PoPUpMenu menu = null;
    private long[] text = null;
    private int state = 0;
    private boolean bEnabled = true;

    public TooltipButton(long _menu, String buttonName, String menu_xml, String menu_controlgroup, String[] tooltipTextControl) {
        this.menu = new PoPUpMenu(_menu, menu_xml, menu_controlgroup, menu_controlgroup);
        long button = menues.FindFieldInMenu(_menu, buttonName);
        int count = 0;
        this.text = new long[tooltipTextControl.length];
        for (String name : tooltipTextControl) {
            this.text[count++] = this.menu.getField(name);
        }
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METH, 4L);
    }

    public void afterInit() {
        this.menu.afterInit();
        this.setState(this.state);
    }

    public void Enable(boolean value) {
        this.bEnabled = value;
    }

    public void onButton(long _menu, MENUsimplebutton_field button) {
        if (this.bEnabled) {
            this.menu.show();
        }
    }

    public void setText(String data) {
        for (long control : this.text) {
            menues.SetFieldText(control, data);
            menues.UpdateMenuField(menues.ConvertMenuFields(control));
        }
    }

    public String GetText() {
        if (this.text != null && this.text.length > 0) {
            return menues.GetFieldText(this.text[0]);
        }
        return null;
    }

    public void setState(int state) {
        if (state >= this.text.length || this.state == state) {
            return;
        }
        this.state = state;
        for (int i = 0; i < this.text.length; ++i) {
            menues.SetShowField(this.text[i], i == state);
        }
    }
}

