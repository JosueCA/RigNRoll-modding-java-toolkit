/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import java.util.Iterator;
import menu.IRadioChangeListener;
import menu.ITableInsertable;
import menu.IValueChanged;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.menues;
import rnrcore.Log;

public class SmartArrowedGroup
implements IRadioChangeListener,
ITableInsertable {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "SmartGroupTop";
    private static final String HEAD = "TITLE";
    private static final String[] ARROWS = new String[]{"LEFT", "RIGHT"};
    private static final String[] ARROWSCB = new String[]{"OnLeft", "OnRight"};
    protected String xml_file;
    protected String xml_node_group;
    protected String title;
    protected long[] controls = null;
    private long[] arrows = new long[2];
    protected long head = 0L;
    private int toppic_x = 0;
    private int toppic_y = 0;
    private boolean bBlind = false;
    private ArrayList<IValueChanged> cbs = new ArrayList();

    public void addListener(IValueChanged cb) {
        this.cbs.add(cb);
    }

    protected void callValueChanged() {
        Iterator<IValueChanged> iter = this.cbs.iterator();
        while (iter.hasNext()) {
            iter.next().valueChanged();
        }
    }

    public SmartArrowedGroup(String xml_file, String xml_node_group, String title, boolean bBlind) {
        this.title = title;
        this.xml_file = xml_file;
        this.xml_node_group = xml_node_group;
        this.bBlind = bBlind;
    }

    public void load(long _menu) {
        this.controls = menues.InitXml(_menu, this.xml_file, this.xml_node_group);
        this.initTopPic(_menu);
        for (int i = 0; i < this.controls.length; ++i) {
            if (!this.readArrows(_menu, this.controls[i]) && this.readHead(this.controls[i])) continue;
        }
    }

    public void init() {
        menues.SetFieldText(this.head, this.title);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.head));
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
            Log.menu("Slider Group. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group " + this.xml_node_group + ".");
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

    private boolean readArrows(long _menu, long field) {
        String name = menues.GetFieldName(field);
        for (int i = 0; i < ARROWS.length; ++i) {
            if (name.compareTo(ARROWS[i]) != 0) continue;
            this.arrows[i] = field;
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.arrows[i]), this, ARROWSCB[i], 4L);
            return true;
        }
        return false;
    }

    private boolean readHead(long field) {
        String name = menues.GetFieldName(field);
        if (name.compareTo(HEAD) == 0) {
            this.head = field;
            return true;
        }
        return false;
    }

    public void OnLeft(long _menu, MENUsimplebutton_field button) {
    }

    public void OnRight(long _menu, MENUsimplebutton_field button) {
    }

    public void controlSelected(MENUbutton_field button, int cs) {
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
            menues.SetBlindess(this.controls[i], this.bBlind);
            menues.SetIgnoreEvents(this.controls[i], this.bBlind);
            menues.SetShowField(this.controls[i], true);
        }
    }

    public void updatePositon(int shiftx, int shifty) {
        MENUText_field top = menues.ConvertTextFields(this.controls[0]);
        if (null == top) {
            Log.menu("Slider Group. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group " + this.xml_node_group + ".");
            return;
        }
        top.pox = this.toppic_x + shiftx;
        top.poy = this.toppic_y + shifty;
        menues.UpdateField(top);
    }
}

