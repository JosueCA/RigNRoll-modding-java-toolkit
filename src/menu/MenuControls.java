/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import menu.menues;
import rnrcore.Log;

public class MenuControls {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "MenuControls";
    private long[] controls;

    public MenuControls(long _menu, String xmlname, String group, boolean make_unique) {
        this.controls = menues.InitXml(_menu, xmlname, group);
        if (this.controls == null) {
            Log.menu("ERRORR. MenuControls. Reading " + xmlname + " controlgroup " + group);
        }
        if (!make_unique) {
            return;
        }
        if (this.controls.length == 0) {
            Log.menu("ERRORR. MenuControls. make_unique group has no controls. Reading " + xmlname + " controlgroup " + group);
        }
        String parentName = menues.GetFieldName(this.controls[0]) + NAMEUNIQSUFF + NUMELEM++;
        menues.SetFieldName(_menu, this.controls[0], parentName);
        for (int i = 1; i < this.controls.length; ++i) {
            menues.SetFieldParentName(this.controls[i], parentName);
        }
    }

    public MenuControls(long _menu, String xmlname, String group) {
        this.controls = menues.InitXml(_menu, xmlname, group);
        if (this.controls == null) {
            Log.menu("ERRORR. MenuControls. Reading " + xmlname + " controlgroup " + group);
        }
    }

    public long getTopControl() {
        if (this.controls == null) {
            Log.menu("ERRORR. MenuControls. getTopControl Bad Controls... controls==null");
            return 0L;
        }
        if (this.controls.length == 0) {
            Log.menu("ERRORR. MenuControls. getTopControl Bad Controls... controls.length==0");
            return 0L;
        }
        return this.controls[0];
    }

    public long findControl(String name) {
        if (this.controls == null) {
            return 0L;
        }
        for (long crtl : this.controls) {
            if (menues.GetFieldName(crtl).compareTo(name) != 0) continue;
            return crtl;
        }
        return 0L;
    }

    public long[] findControls(String name) {
        if (this.controls == null) {
            return new long[0];
        }
        ArrayList<Long> res = new ArrayList<Long>();
        for (long crtl : this.controls) {
            if (menues.GetFieldName(crtl).compareTo(name) != 0) continue;
            res.add(crtl);
        }
        long[] arr = new long[res.size()];
        int iter = 0;
        for (Long ctrl : res) {
            arr[iter++] = ctrl;
        }
        return arr;
    }

    public long[] findControls_Contains(String name) {
        if (this.controls == null) {
            return new long[0];
        }
        ArrayList<Long> res = new ArrayList<Long>();
        for (long crtl : this.controls) {
            if (!menues.GetFieldName(crtl).matches("*" + name + "*")) continue;
            res.add(crtl);
        }
        long[] arr = new long[res.size()];
        int iter = 0;
        for (Long ctrl : res) {
            arr[iter++] = ctrl;
        }
        return arr;
    }

    public void show() {
        menues.SetShowField(this.controls[0], true);
    }

    public void hide() {
        menues.SetShowField(this.controls[0], false);
    }

    public void setNoFocusOnControls() {
        for (long item : this.controls) {
            menues.setFocusOnControl(item, false);
            menues.SetIgnoreEvents(item, true);
            menues.SetBlindess(item, true);
        }
    }
}

