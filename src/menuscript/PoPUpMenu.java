/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.IPoPUpMenuListener;
import rnrcore.Log;

public class PoPUpMenu {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "SmartGroupTop";
    private static final String[] _BUTTONS = new String[]{"CANCEL", "OK"};
    private static final String[] _METHODS = new String[]{"OnCancel", "OnOk"};
    private static final String EXITMETH = "OnExit";
    private boolean unique = true;
    private boolean bFindWindow = true;
    private long[] controls = null;
    ArrayList<IPoPUpMenuListener> listeners = new ArrayList();
    private int origin_x = 0;
    private int origin_y = 0;

    public PoPUpMenu(long _menu, String xmlFile, String controlGroup, String windowName, boolean unique) {
        this.unique = unique;
        this.init(_menu, xmlFile, controlGroup, windowName, _BUTTONS, _METHODS);
    }

    public PoPUpMenu(long _menu, String xmlFile, String controlGroup) {
        this.unique = false;
        this.bFindWindow = false;
        this.init(_menu, xmlFile, controlGroup, null, _BUTTONS, _METHODS);
    }

    public PoPUpMenu(long _menu, String xmlFile, String controlGroup, String windowName) {
        this.init(_menu, xmlFile, controlGroup, windowName, _BUTTONS, _METHODS);
    }

    public PoPUpMenu(long _menu, String xmlFile, String controlGroup, String windowName, String[] BUTTONS, String[] METHODS) {
        this.init(_menu, xmlFile, controlGroup, windowName, BUTTONS, METHODS);
    }

    public PoPUpMenu(long _menu, String xmlFile, String controlGroup, String windowName, String[] BUTTONS, String[] METHODS, boolean unique) {
        this.unique = unique;
        this.init(_menu, xmlFile, controlGroup, windowName, BUTTONS, METHODS);
    }

    private void init(long _menu, String xmlFile, String controlGroup, String windowName, String[] BUTTONS, String[] METHODS) {
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
        if (this.unique) {
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
        if (this.bFindWindow && this.controls != null && this.controls.length > 0 && this.controls[0] != 0L) {
            SMenu __menu = menues.ConvertWindow(this.controls[0]);
            this.origin_y = __menu.poy;
            this.origin_x = __menu.pox;
        }
    }

    public void afterInit() {
        this.setShow(false);
    }

    public void OnCancel(long _menu, MENUsimplebutton_field field) {
        this.setShow(false);
        for (IPoPUpMenuListener listener : this.listeners) {
            if (null == listener) continue;
            listener.onCancel();
            listener.onClose();
        }
    }

    public void OnExit(long _menu, SMenu field) {
        this.setShow(false);
        for (IPoPUpMenuListener listener : this.listeners) {
            listener.onClose();
        }
    }

    public void OnOk(long _menu, MENUsimplebutton_field field) {
        this.setShow(false);
        for (IPoPUpMenuListener listener : this.listeners) {
            listener.onAgreeclose();
        }
    }

    private void setShow(boolean value) {
        if (null == this.controls) {
            return;
        }
        for (long control : this.controls) {
            menues.SetShowField(control, value);
        }
    }

    public void show() {
        this.setShow(true);
        for (IPoPUpMenuListener listener : this.listeners) {
            listener.onOpen();
        }
    }

    public void hide() {
        this.setShow(false);
        for (IPoPUpMenuListener listener : this.listeners) {
            listener.onClose();
        }
    }

    public void addListener(IPoPUpMenuListener lst) {
        this.listeners.add(lst);
    }

    public long getField(String name) {
        return this.findControlNamed(name);
    }

    public long findControlNamed(String name) {
        if (null == this.controls) {
            return 0L;
        }
        long result = 0L;
        for (long control : this.controls) {
            if (0 != menues.GetFieldName(control).compareTo(name)) continue;
            result = control;
            break;
        }
        return result;
    }

    public long[] getControls() {
        return this.controls;
    }

    public boolean is_bad() {
        return null == this.controls;
    }

    public void MoveByFromOrigin(int shiftX, int shiftY) {
        SMenu _menu = menues.ConvertWindow(this.controls[0]);
        _menu.pox = this.origin_x + shiftX;
        _menu.poy = this.origin_y + shiftY;
        menues.UpdateField(_menu);
    }

    public void resize(int deltaX, int deltaY) {
        SMenu _menu = menues.ConvertWindow(this.controls[0]);
        _menu.lenx += deltaX;
        _menu.leny += deltaY;
        menues.UpdateField(_menu);
    }
}

