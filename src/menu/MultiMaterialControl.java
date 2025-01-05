/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.HashMap;
import java.util.Map;
import menu.Common;
import menu.MENUText_field;
import menu.menues;

public class MultiMaterialControl {
    String name;
    String parentname;
    String controlgroup;
    String filename;
    Common common;
    boolean vis = true;
    long currentcontrol = 0L;
    HashMap<String, Long> m_controls = new HashMap();

    public MultiMaterialControl(Common common, String name, String parentname, String filename, String controlgroup) {
        this.name = name;
        this.parentname = parentname;
        this.controlgroup = controlgroup;
        this.filename = filename;
        this.common = common;
    }

    public void AddMaterial(String materialname) {
        if (this.m_controls.containsKey(materialname)) {
            return;
        }
        long control = menues.InitXmlControl(this.common.GetMenu(), this.filename, this.controlgroup, this.name);
        MENUText_field pic = menues.ConvertTextFields(control);
        pic.parentName = this.parentname;
        menues.UpdateField(pic);
        menues.ChangeAllMaterialsOnControl(this.common.GetMenu(), control, materialname);
        this.m_controls.put(materialname, new Long(control));
    }

    public void SetIgnoreEvents() {
        for (Map.Entry entry : this.m_controls.entrySet()) {
            Long c = (Long)entry.getValue();
            menues.SetIgnoreEvents(c, true);
        }
    }

    public void SetVis(boolean tohide) {
        if (this.vis == tohide) {
            return;
        }
        this.vis = tohide;
        if (this.currentcontrol != 0L) {
            menues.SetShowField(this.currentcontrol, this.vis);
        }
    }

    public void SetMaterial(String materialname) {
        Long l = (Long)this.m_controls.get(materialname);
        this.currentcontrol = l == null ? 0L : l;
        for (Map.Entry entry : this.m_controls.entrySet()) {
            Long c = (Long)entry.getValue();
            menues.SetShowField(c, c == this.currentcontrol && this.vis);
        }
    }

    public void HideAll() {
        for (Map.Entry entry : this.m_controls.entrySet()) {
            Long c = (Long)entry.getValue();
            menues.SetShowField(c, false);
        }
        this.vis = false;
    }

    public MENUText_field GetControl() {
        return menues.ConvertTextFields(this.currentcontrol);
    }
}

