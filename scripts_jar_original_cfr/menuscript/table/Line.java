/*
 * Decompiled with CFR 0.151.
 */
package menuscript.table;

import menu.MENUText_field;
import menu.menues;
import rnrcore.Log;

public class Line {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "SmartGroupTop";
    protected String xml_file;
    protected String xml_node_group;
    private long[] controls = null;
    private int toppic_x = 0;
    private int toppic_y = 0;
    private boolean f_initialized = false;
    private long group = 0L;
    private long[] marked_controls = null;
    private String[] marked_names = null;

    Line(String xmlfilename, String xmlcontrolgroup, String[] marked_names) {
        this.xml_file = xmlfilename;
        this.xml_node_group = xmlcontrolgroup;
        this.marked_names = marked_names;
        if (null != marked_names) {
            this.marked_controls = new long[marked_names.length];
        }
    }

    void load(long _menu) {
        if (this.f_initialized) {
            Log.menu("Line initialized not ones. xml file " + this.xml_file + " controlgroup " + "xml_node_group");
            return;
        }
        this.f_initialized = true;
        this.controls = menues.InitXml(_menu, this.xml_file, this.xml_node_group);
        this.initTopPic(_menu);
        this.initGroup(_menu);
    }

    long getLine() {
        if (!this.f_initialized) {
            Log.menu("getLine. Line not initialized. xml file " + this.xml_file + " controlgroup " + "xml_node_group");
            return -1L;
        }
        return this.group;
    }

    long getNamedControl(String name) {
        for (int i = 0; i < this.controls.length; ++i) {
            if (menues.GetFieldName(this.controls[i]).compareTo(name) != 0) continue;
            return this.controls[i];
        }
        return 0L;
    }

    private boolean mark_field(long field) {
        if (null == this.marked_names) {
            Log.menu("ERRORR. mark_field - wrong behaivoir.");
            return false;
        }
        for (int i = 0; i < this.marked_names.length; ++i) {
            if (menues.GetFieldName(field).compareTo(this.marked_names[i]) != 0) continue;
            this.marked_controls[i] = field;
            return true;
        }
        return false;
    }

    private void initGroup(long _menu) {
        this.group = menues.CreateGroup(_menu);
        if (this.marked_names != null) {
            for (int i = 0; i < this.controls.length; ++i) {
                if (!this.mark_field(this.controls[i])) continue;
                Object obj = menues.ConvertMenuFields(this.controls[i]);
                menues.AddControlInGroup(_menu, this.group, obj);
                menues.LinkGroupAndControl(_menu, obj);
                menues.ChangableFieldOnGroup(_menu, obj);
            }
        } else {
            this.marked_controls = new long[this.controls.length];
            for (int i = 0; i < this.controls.length; ++i) {
                this.marked_controls[i] = this.controls[i];
                Object obj = menues.ConvertMenuFields(this.controls[i]);
                menues.AddControlInGroup(_menu, this.group, obj);
                menues.LinkGroupAndControl(_menu, obj);
                menues.ChangableFieldOnGroup(_menu, obj);
            }
        }
    }

    private void initTopPic(long _menu) {
        MENUText_field toppic;
        if (null == this.controls) {
            Log.menu("Slider Group. Trying to access not created controls field. bindToParent");
            return;
        }
        if (0 == this.controls.length) {
            Log.menu("Slider Group. Trying to bindToParent broken group");
        }
        if (null == (toppic = menues.ConvertTextFields(this.controls[0]))) {
            Log.menu("ControlGroup. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group " + this.xml_node_group + ".");
            return;
        }
        this.toppic_x = toppic.pox;
        this.toppic_y = toppic.poy;
        String parentName = toppic.nameID + NAMEUNIQSUFF + NUMELEM++;
        menues.SetFieldName(_menu, this.controls[0], parentName);
        for (int i = 1; i < this.controls.length; ++i) {
            menues.SetFieldParentName(this.controls[i], parentName);
        }
    }

    void shiftLine(int shiftx, int shifty) {
        if (!this.f_initialized) {
            Log.menu("shiftLine. Line not initialized. xml file " + this.xml_file + " controlgroup " + "xml_node_group");
            return;
        }
        MENUText_field top = menues.ConvertTextFields(this.controls[0]);
        if (null == top) {
            Log.menu("Slider Group. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group " + this.xml_node_group + ".");
            return;
        }
        top.pox = this.toppic_x + shiftx;
        top.poy = this.toppic_y + shifty;
        menues.UpdateField(top);
    }

    int getMarkedPosition(long control) {
        if (null == this.marked_controls || 0L == control) {
            return -1;
        }
        for (int i = 0; i < this.marked_controls.length; ++i) {
            if (control != this.marked_controls[i]) continue;
            return i;
        }
        return -1;
    }

    public void hide() {
        for (int i = 0; i < this.controls.length; ++i) {
            menues.SetBlindess(this.controls[i], true);
            menues.SetIgnoreEvents(this.controls[i], true);
            menues.SetShowField(this.controls[i], false);
        }
    }

    public void insertInTable(long _menu, long parent) {
        String parentName = menues.GetFieldName(parent);
        MENUText_field top = menues.ConvertTextFields(this.controls[0]);
        top.parentName = parentName;
        menues.UpdateField(top);
    }

    public void show() {
        for (int i = 0; i < this.controls.length; ++i) {
            menues.SetBlindess(this.controls[i], false);
            menues.SetIgnoreEvents(this.controls[i], false);
            menues.SetShowField(this.controls[i], true);
        }
    }
}

