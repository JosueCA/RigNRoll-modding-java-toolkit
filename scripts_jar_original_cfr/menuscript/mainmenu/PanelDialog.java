/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.FabricControlColor;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.IWindowContext;
import menuscript.mainmenu.Panel;
import menuscript.parametrs.ParametrsBlock;

public abstract class PanelDialog
implements IWindowContext {
    private static final String EXITMETHOD = "OnExit";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    private static final String APPLYMETHOD = "OnApply";
    protected Panel parent = null;
    long[] controls = null;
    protected ParametrsBlock param_values = new ParametrsBlock();
    private boolean animating;

    protected PanelDialog(long _menu, long window, long[] controls, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent) {
        Object field;
        this.parent = parent;
        this.controls = controls;
        if (0L != exitButton) {
            field = menues.ConvertMenuFields(exitButton);
            menues.SetScriptOnControl(_menu, field, this, EXITMETHOD, 4L);
        }
        if (0L != defaultButton) {
            field = menues.ConvertMenuFields(defaultButton);
            menues.SetScriptOnControl(_menu, field, this, DEFAULTMETHOD, 4L);
        }
        if (0L != okButton) {
            field = menues.ConvertMenuFields(okButton);
            menues.SetScriptOnControl(_menu, field, this, OKMETHOD, 4L);
        }
        if (0L != applyButton) {
            field = menues.ConvertMenuFields(applyButton);
            menues.SetScriptOnControl(_menu, field, this, APPLYMETHOD, 4L);
        }
        if (0L != window) {
            Object _window = menues.ConvertMenuFields(window);
            menues.SetScriptOnControl(_menu, _window, this, EXITMETHOD, 17L);
        }
    }

    public void afterInit() {
        this.param_values.onUpdate();
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        this.exitDialog();
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        this.exitDialog();
    }

    public void OnDefault(long _menu, MENUsimplebutton_field button) {
        this.param_values.onDefault();
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
    }

    public void OnExit(long _menu, SMenu window) {
        this.exitDialog();
    }

    protected void exitDialog() {
        if (null != this.parent) {
            this.parent.OnDialogExit(this);
        }
    }

    boolean isAnimating() {
        return this.animating;
    }

    void setAnimating(boolean value) {
        this.animating = value;
    }

    void setFreeze(boolean value) {
        if (value) {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.freezControl(this.controls[i]);
            }
        } else {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.unfreezControl(this.controls[i]);
            }
        }
        this.onFreeze(value);
    }

    void setShow(boolean value) {
        if (value) {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.showControl(this.controls[i]);
            }
        } else {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.hideControl(this.controls[i]);
            }
        }
        this.onShow(value);
    }

    void setAlpha(int alpha) {
        for (int i = 0; i < this.controls.length; ++i) {
            FabricControlColor.setControlAlfa(this.controls[i], alpha);
        }
    }

    public void readParamValues() {
    }

    public void update() {
        this.param_values.onUpdate();
    }

    public void exitMenu() {
    }

    public boolean areValuesChanged() {
        return this.param_values.areValuesChanged();
    }

    public void exitWindowContext() {
        this.exitDialog();
    }

    public void updateWindowContext() {
        this.update();
    }

    protected void onShow(boolean value) {
    }

    protected void onFreeze(boolean value) {
    }
}

