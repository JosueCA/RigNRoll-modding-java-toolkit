/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.IYesNoCancelMenuListener;
import rnrcore.Log;

public class YesNoCancelMenu {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "SmartGroupTop";
    private static final String[] BUTTONS = new String[]{"YES", "NO", "CANCEL"};
    private static final String[] METHODS = new String[]{"OnYes", "OnNo", "OnCancel"};
    private static final String EXITMETH = "OnExit";
    private long[] controls = null;
    ArrayList<IYesNoCancelMenuListener> listeners = new ArrayList();

    public YesNoCancelMenu() {
    }

    public YesNoCancelMenu(long _menu, String xmlFile, String controlGroup, String windowName) {
        this.controls = menues.InitXml(_menu, xmlFile, controlGroup);
        if (null == this.controls) {
            Log.menu("ERRORR. Cannot create PoPUpMenu. File " + xmlFile + " has no group named " + controlGroup);
            return;
        }
        if (BUTTONS.length != METHODS.length) {
            Log.menu("Bab Class PoPUpMenu. BUTTONS.length!=METHODS.length");
            return;
        }
        for (int i = 0; i < BUTTONS.length; ++i) {
            long exitButton = this.findControlNamed(BUTTONS[i]);
            if (0L == exitButton) continue;
            Object field = menues.ConvertMenuFields(exitButton);
            menues.SetScriptOnControl(_menu, field, this, METHODS[i], 4L);
        }
        if (windowName != null) {
            long wnd = this.findControlNamed(windowName);
            if (0L != wnd) {
                SMenu _wnd = menues.ConvertWindow(wnd);
                menues.SetScriptOnControl(_menu, _wnd, this, EXITMETH, 17L);
            } else {
                Log.menu("PoPUpMenu constructor. There is no window named " + windowName + " for controlgroup " + controlGroup);
            }
        }
        String parentName = controlGroup + NAMEUNIQSUFF + NUMELEM++;
        menues.SetFieldName(_menu, this.controls[0], parentName);
        for (int i = 1; i < this.controls.length; ++i) {
            menues.SetFieldParentName(this.controls[i], parentName);
        }
    }

    public void afterInit() {
        this.setShow(false);
    }

    public void OnYes(long _menu, MENUsimplebutton_field field) {
        this.setShow(false);
        Iterator<IYesNoCancelMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onYesClose();
        }
    }

    public void OnNo(long _menu, MENUsimplebutton_field field) {
        this.setShow(false);
        Iterator<IYesNoCancelMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onNoClose();
        }
    }

    public void OnCancel(long _menu, MENUsimplebutton_field field) {
        this.setShow(false);
        Iterator<IYesNoCancelMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onCancelClose();
        }
    }

    private void setShow(boolean value) {
        if (null == this.controls) {
            return;
        }
        if (value) {
            for (int i = 0; i < this.controls.length; ++i) {
                menues.SetShowField(this.controls[i], true);
            }
        } else {
            for (int i = 0; i < this.controls.length; ++i) {
                menues.SetShowField(this.controls[i], false);
            }
        }
    }

    public void show() {
        this.setShow(true);
        Iterator<IYesNoCancelMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onOpen();
        }
    }

    public void hide() {
        this.setShow(false);
        Iterator<IYesNoCancelMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onClose();
        }
    }

    public void addListener(IYesNoCancelMenuListener lst) {
        this.listeners.add(lst);
    }

    public void callonYesClose() {
        Iterator<IYesNoCancelMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onYesClose();
        }
    }

    public long getField(String name) {
        return this.findControlNamed(name);
    }

    private long findControlNamed(String name) {
        if (null == this.controls) {
            return 0L;
        }
        long res = 0L;
        for (int i = 0; i < this.controls.length; ++i) {
            if (menues.GetFieldName(this.controls[i]).compareTo(name) != 0) continue;
            res = this.controls[i];
            break;
        }
        return res;
    }

    boolean is_bad() {
        return null == this.controls;
    }
}

