/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.ParentMenu;

public class ConfirmDialog {
    ParentMenu m_curmenu;
    SMenu m_warnwindow;
    String m_warntext;
    MENUText_field m_warntextfield;

    public ConfirmDialog(Common common, String menuname, String textname, String yesbutton, String nobutton, String cancelbutton) {
        MENUsimplebutton_field button;
        this.m_warnwindow = common.FindWindow(menuname);
        this.m_warntextfield = common.FindTextField(textname);
        if (this.m_warntextfield != null) {
            this.m_warntext = this.m_warntextfield.text;
        }
        if (yesbutton != null && (button = common.FindSimpleButton(yesbutton)) != null) {
            menues.SetScriptOnControl(common.GetMenu(), button, this, "OnYes", 4L);
        }
        if (nobutton != null && (button = common.FindSimpleButton(nobutton)) != null) {
            menues.SetScriptOnControl(common.GetMenu(), button, this, "OnNo", 4L);
        }
        if (cancelbutton != null && (button = common.FindSimpleButton(cancelbutton)) != null) {
            menues.SetScriptOnControl(common.GetMenu(), button, this, "OnCancel", 4L);
        }
        if (this.m_warnwindow != null) {
            menues.SetScriptOnControl(common.GetMenu(), this.m_warnwindow, this, "OnExit", 17L);
        }
    }

    public void AskUser(ParentMenu menu, String text) {
        if (this.m_warntextfield != null) {
            if (text == null) {
                text = this.m_warntext;
            }
            this.m_warntextfield.text = this.m_warntext;
            menues.UpdateField(this.m_warntextfield);
        }
        this.m_curmenu = menu;
        menues.SetShowField(this.m_warnwindow.nativePointer, true);
    }

    void OnYes(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(0);
    }

    void OnNo(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(1);
    }

    void OnCancel(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(2);
    }

    void OnExit(long _menu, SMenu wnd) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(2);
    }
}

