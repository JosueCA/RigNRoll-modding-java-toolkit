/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.IMenuListener;
import menuscript.ParentMenu;
import rnrcore.Log;

public class WindowParentMenu
extends ParentMenu {
    private ArrayList<IMenuListener> listeners = new ArrayList();
    private long[] controls = null;
    private static final String[] ACTION_SUFF = new String[]{"CANCEL", "OK"};
    private static final String[] ACTION_METH = new String[]{"OnClose", "OnOk"};
    private static final String EXIT_ACTION = "OnExit";

    void NeedToConfirm(String text) {
    }

    public void addListener(IMenuListener listener) {
        this.listeners.add(listener);
    }

    public WindowParentMenu(long _menu, String filename, String controlGroup) {
        this.controls = menues.InitXml(_menu, filename, controlGroup);
        if (null == this.controls) {
            Log.menu("WindowParentMenu. group " + controlGroup + " is empty. File " + filename);
            return;
        }
        for (int i = 0; i < this.controls.length; ++i) {
            String name = menues.GetFieldName(this.controls[i]);
            for (int suff = 0; suff < ACTION_SUFF.length; ++suff) {
                if (!name.endsWith(ACTION_SUFF[suff])) continue;
                Object field = menues.ConvertMenuFields(this.controls[i]);
                menues.SetScriptOnControl(_menu, field, this, ACTION_METH[suff], 4L);
            }
        }
        Object menu = menues.ConvertMenuFields(this.controls[0]);
        menues.SetScriptOnControl(_menu, menu, this, EXIT_ACTION, 17L);
    }

    protected void OnClose(long _menu, MENUsimplebutton_field button) {
        this.hide();
    }

    void OnOk(long _menu, MENUsimplebutton_field button) {
        this.OnClose(_menu, button);
    }

    void OnExit(long _menu, SMenu wnd) {
        this.hide();
    }

    public void Activate() {
        super.Activate();
        SMenu menu = menues.ConvertWindow(this.controls[0]);
        menues.setFocusWindow(menu.ID);
    }

    void show() {
        Iterator<IMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onOpen();
        }
        menues.SetShowField(this.controls[0], true);
    }

    public void hide() {
        Iterator<IMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onClose();
        }
        menues.SetShowField(this.controls[0], false);
    }

    public void InitMenu(long _menu) {
    }
}

