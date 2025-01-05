/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import menu.Common;
import menu.ListenerManager;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.SelectCb;
import menu.menues;

public class ComboBox
implements SelectCb {
    SelectCb m_cb;
    String m_prefix;
    int m_maxnum;
    int m_num;
    int m_sizey;
    int m_pox;
    int id;
    boolean m_isup;
    boolean m_ispic;
    boolean m_isradiooff;
    SMenu m_list;
    MENUbutton_field m_radio;
    MENUsimplebutton_field m_button;
    MENUText_field[] m_pictures;
    MENUsimplebutton_field[] m_buttons;
    int m_baseshift;
    public static final int UP = 1;
    public static final int PIC = 2;
    public static final int RADIOOFF = 4;
    Common common;
    float m_vertsize;
    float m_tile;
    int m_currentindex;
    int[] m_indexlist;
    Vector m_stuff = new Vector();

    public ComboBox(Common common, String xmlfilename, String controlgroup, String controlname, String windowname, MENUbutton_field radio, MENUsimplebutton_field button, int maxnum, int sizey, int baseshift, int flags) {
        this.common = common;
        long _menu = common.GetMenu();
        this.id = Common.GetID();
        this.m_isup = (flags & 1) != 0;
        this.m_ispic = (flags & 2) != 0;
        this.m_isradiooff = (flags & 4) != 0;
        this.m_sizey = sizey;
        this.m_num = 0;
        this.m_baseshift = baseshift;
        this.m_maxnum = maxnum;
        this.m_radio = radio;
        this.m_button = button;
        this.m_list = menues.ConvertWindow(menues.InitXmlControl(_menu, xmlfilename, controlgroup, windowname));
        this.m_list.nameID = this.m_list.nameID + this.id;
        this.m_pox = this.m_list.pox;
        this.m_list.pox = radio.pox;
        this.m_list.poy = radio.poy - sizey * this.m_maxnum;
        this.m_list.leny = sizey * this.m_maxnum;
        menues.UpdateField(this.m_list);
        ListenerManager.AddListener(104, this);
        if (this.m_ispic) {
            this.m_pictures = new MENUText_field[this.m_maxnum];
        }
        this.m_buttons = new MENUsimplebutton_field[this.m_maxnum];
        for (int i = 0; i < this.m_maxnum; ++i) {
            if (this.m_ispic) {
                this.m_pictures[i] = menues.ConvertTextFields(menues.InitXmlControl(_menu, xmlfilename, controlgroup, controlname + "_pic"));
                this.m_pictures[i].poy += i * sizey;
                this.m_pictures[i].parentName = this.m_list.nameID;
                menues.UpdateField(this.m_pictures[i]);
            }
            this.m_buttons[i] = menues.ConvertSimpleButton(menues.InitXmlControl(_menu, xmlfilename, controlgroup, controlname));
            this.m_buttons[i].userid = i;
            this.m_buttons[i].parentName = this.m_list.nameID;
            this.m_buttons[i].poy += i * sizey;
            menues.UpdateField(this.m_buttons[i]);
            menues.SetScriptOnControl(_menu, this.m_buttons[i], this, "OnListButton", 4L);
        }
        menues.SetScriptOnControl(_menu, radio, this, "OnRadio", 2L);
        menues.SetScriptOnControl(_menu, button, this, "OnButton", 4L);
    }

    public void Setup(int num) {
        int i;
        this.m_num = Math.min(num, this.m_maxnum);
        int radiox = menues.getSX(this.m_radio.nativePointer);
        int radioy = menues.getSY(this.m_radio.nativePointer);
        this.m_list.pox = radiox + this.m_pox;
        this.m_list.poy = this.m_isup ? radioy - this.m_sizey * (this.m_num - 1) - this.m_baseshift : radioy + this.m_baseshift;
        this.m_list.leny = this.m_sizey * this.m_num;
        menues.UpdateField(this.m_list);
        for (i = 0; i < this.m_num; ++i) {
            menues.SetShowField(this.m_buttons[i].nativePointer, true);
            if (!this.m_ispic) continue;
            menues.SetShowField(this.m_pictures[i].nativePointer, true);
        }
        for (i = this.m_num; i < this.m_maxnum; ++i) {
            menues.SetShowField(this.m_buttons[i].nativePointer, false);
            if (!this.m_ispic) continue;
            menues.SetShowField(this.m_pictures[i].nativePointer, false);
        }
    }

    public MENUsimplebutton_field GetButton(int num) {
        return this.m_buttons[num];
    }

    public MENUbutton_field GetRadio() {
        return this.m_radio;
    }

    public void SetMaterial(String material) {
        menues.ChangeAllMaterialsOnControl(this.common.GetMenu(), this.m_radio.nativePointer, material);
        for (int i = 0; i < this.m_pictures.length; ++i) {
            menues.ChangeAllMaterialsOnControl(this.common.GetMenu(), this.m_pictures[i].nativePointer, material);
        }
    }

    public void FillMappingData(float vertsize, int[] list) {
        int i;
        this.m_indexlist = list;
        this.m_vertsize = vertsize;
        this.Setup(list.length);
        int maxlen = Math.min(list.length, this.m_maxnum);
        menues.CallMappingModifications(this.m_radio.nativePointer, this, "RadioMapping");
        for (i = 0; i < maxlen; ++i) {
            this.m_currentindex = list[i];
            menues.CallMappingModifications(this.m_pictures[i].nativePointer, this, "ListMapping");
            menues.SetShowField(this.m_buttons[i].nativePointer, true);
            menues.SetShowField(this.m_pictures[i].nativePointer, true);
        }
        for (i = maxlen; i < this.m_maxnum; ++i) {
            menues.SetShowField(this.m_buttons[i].nativePointer, false);
            menues.SetShowField(this.m_pictures[i].nativePointer, false);
        }
        this.m_indexlist = null;
    }

    private int ConstructStuffNumber(int state, boolean active, boolean pressed) {
        if (pressed) {
            return state * 3 + 2;
        }
        if (active) {
            return state * 3 + 1;
        }
        return state * 3;
    }

    void RadioFillData(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (int i = 0; i < stuff.length; ++i) {
            menues.CMaterial_whithmapping m = stuff[i];
            if (stuff[i].usepatch || m.tex.size() == 0) continue;
            int index = this.ConstructStuffNumber(m._state, m._active, m.pressed);
            if (index >= this.m_stuff.size()) {
                this.m_stuff.setSize(index + 1);
            }
            this.m_stuff.set(index, m.tex.get(0));
        }
    }

    void RadioMapping(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (int i = 0; i < stuff.length; ++i) {
            int index;
            if (stuff[i].tex.size() == 0 || (index = stuff[i]._state) < 0 || index >= this.m_indexlist.length) continue;
            stuff[i].tex.set(0, this.m_stuff.get(this.ConstructStuffNumber(this.m_indexlist[index], stuff[i]._active, stuff[i].pressed)));
        }
    }

    void ListMapping(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (int i = 0; i < stuff.length; ++i) {
            stuff[i].tex.set(0, this.m_stuff.get(this.ConstructStuffNumber(this.m_currentindex, false, false)));
        }
    }

    void OnListButton(long _menu, MENUsimplebutton_field button) {
        menues.SetFieldState(this.m_radio.nativePointer, button.userid);
        menues.SetShowField(this.m_list.nativePointer, false);
        if (this.m_cb != null) {
            this.m_cb.OnSelect(button.userid, this);
        }
        menues.setfocuscontrolonmenu(_menu, this.m_button.nativePointer);
    }

    public void ShowPopup() {
        menues.SetShowField(this.m_list.nativePointer, true);
        int cur = menues.GetFieldState(this.m_radio.nativePointer);
        menues.setfocuscontrolonmenu(this.common.GetMenu(), this.m_buttons[cur].nativePointer);
    }

    public void OnButton(long _menu, MENUsimplebutton_field button) {
        this.ShowPopup();
    }

    public void Select(int state) {
        if (this.m_radio != null && this.m_radio.nativePointer != 0L && state >= 0 && state < this.m_num) {
            menues.SetFieldState(this.m_radio.nativePointer, state);
        }
    }

    public void OnRadio(long _menu, MENUbutton_field button) {
        if (this.m_isradiooff) {
            int newstate = menues.GetFieldState(button.nativePointer);
            int oldstate = newstate - 1;
            if (oldstate < 0) {
                oldstate = this.m_num - 1;
            }
            menues.SetFieldState(button.nativePointer, oldstate);
            return;
        }
        if (menues.GetFieldState(button.nativePointer) >= this.m_num) {
            menues.SetFieldState(button.nativePointer, 0);
        }
        if (this.m_cb != null) {
            this.m_cb.OnSelect(menues.GetFieldState(button.nativePointer), this);
        }
    }

    public void AddListener(SelectCb cb) {
        this.m_cb = cb;
    }

    public void OnSelect(int state, Object sender) {
        menues.SetShowField(this.m_list.nativePointer, false);
        menues.CallMappingModifications(this.m_radio.nativePointer, this, "RadioFillData");
    }
}

