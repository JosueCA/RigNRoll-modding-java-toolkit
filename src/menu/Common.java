// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.RNRMap;
import menu.RatingData;
import menu.SMenu;
import menu.TimeData;
import menu.menues;
import menu.xmlcontrols;
import rnrscr.gameinfo;

public class Common {
    public long s_menu;
    public static Common s_lastcommon;
    public PatchModif s_modif = new PatchModif();
    public static int s_ID;

    public Common(long _menu) {
        this.s_menu = _menu;
        s_lastcommon = this;
    }

    public long GetMenu() {
        return this.s_menu;
    }

    public void InitBalance() {
        gameinfo.script.m_iTotalAuth = 0;
    }

    public SMenu FindWindow(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return menues.ConvertWindow(control);
    }

    public MENUText_field FindTextField(String name, long _group) {
        long control = menues.FindFieldInGroup(this.s_menu, _group, name);
        return menues.ConvertTextFields(control);
    }

    public MENUsimplebutton_field FindSimpleButton(String name, long _group) {
        long control = menues.FindFieldInGroup(this.s_menu, _group, name);
        return menues.ConvertSimpleButton(control);
    }

    public MENUsimplebutton_field FindSimpleButtonByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);
        return menues.ConvertSimpleButton(control);
    }

    public static String ConstructPath(String xmlfilename) {
        return "..\\data\\config\\menu\\" + xmlfilename;
    }

    public MENUbutton_field FindRadioButton(String name, long _group) {
        long control = menues.FindFieldInGroup(this.s_menu, _group, name);
        return menues.ConvertButton(control);
    }

    public MENUbutton_field FindRadioButtonByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);
        return menues.ConvertButton(control);
    }

    public MENUText_field FindTextField(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return menues.ConvertTextFields(control);
    }

    public MENUText_field FindTextFieldByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);
        return menues.ConvertTextFields(control);
    }

    public MENUsimplebutton_field FindSimpleButton(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return menues.ConvertSimpleButton(control);
    }

    public void SetScriptOnButtonByControlGroup(String name, Object obj, String method, long group) {
        menues.SetScriptOnControl(this.s_menu, this.FindSimpleButton(name, group), obj, method, 4L);
    }

    public void SetScriptOnButton(String name, Object obj, String method) {
        menues.SetScriptOnControl(this.s_menu, this.FindSimpleButton(name), obj, method, 4L);
    }

    public void SetScriptOnButtonUserid(String name, Object obj, String method, int userid) {
        MENUsimplebutton_field b = this.FindSimpleButton(name);
        b.userid = userid;
        menues.UpdateField(b);
        menues.SetScriptOnControl(this.s_menu, b, obj, method, 4L);
    }

    public MENUTruckview FindTruckView(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return (MENUTruckview)menues.ConvertMenuFields(control);
    }

    public xmlcontrols.MENUCustomStuff FindTab(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return (xmlcontrols.MENUCustomStuff)menues.ConvertMenuFields(control);
    }

    public MENUbutton_field CreateRadioButton(String name, String parent, int shiftx, int shifty, String xmlfile, String controlgroup) {
        MENUbutton_field radio = this.CreateRadioButton(name, parent, xmlfile, controlgroup);
        radio.pox += shiftx;
        radio.poy += shifty;
        menues.UpdateField(radio);
        return radio;
    }

    public MENUbutton_field CreateRadioButton(String name, String parent, String xmlfile, String controlgroup) {
        long control = menues.InitXmlControl(this.s_menu, xmlfile, controlgroup, name);
        MENUbutton_field button = menues.ConvertButton(control);
        button.parentName = parent;
        menues.UpdateField(button);
        return button;
    }

    public RNRMap FindRNRMap(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return menues.ConvertRNRMAPFields(control);
    }

    public RNRMap FindRNRMapByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);
        return menues.ConvertRNRMAPFields(control);
    }

    public MENUbutton_field FindRadioButton(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return menues.ConvertButton(control);
    }

    public MENU_ranger FindScroller(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);
        return menues.ConvertRanger(control);
    }

    public MENU_ranger FindScrollerByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);
        return menues.ConvertRanger(control);
    }

    public RatingData GetRatingData(long control) {
        RatingData data = new RatingData();
        this.s_modif.Setup(data);
        menues.CallMappingModifications(control, this.s_modif, "GetData");
        return data;
    }

    public void SetRadioPercent(MENUbutton_field button, String text, double percent, RatingData data) {
        button.text = text;
        button.lenx = Math.max((int)(percent * (double)data.picsize), data.textsize);
        this.s_modif.Setup(data, (int)(percent * (double)data.picsize));
        menues.CallMappingModifications(button.nativePointer, this.s_modif, "FillData");
        menues.UpdateField(button);
    }

    public void SetTextValue(String name, String text) {
        MENUText_field field = this.FindTextField(name);
        field.text = text;
        menues.UpdateField(field);
    }

    public void AddTextValue(String name, String text) {
        MENUText_field field = this.FindTextField(name);
        field.text = field.text + text;
        menues.UpdateField(field);
    }

    public static int Compare(String s1, String s2, boolean isascending) {
        int order;
        int n = order = isascending ? 1 : -1;
        if (s1 == null) {
            return order;
        }
        return s1.compareTo(s2) * order;
    }

    public static int Compare(int i1, int i2, boolean isascending) {
        int order = isascending ? 1 : -1;
        int diff = i1 - i2;
        return diff * order;
    }

    public static int Compare(double d1, double d2, boolean isascending) {
        int order = isascending ? 1 : -1;
        double d = d1 - d2;
        int diff = 0;
        if (d > 0.0) {
            diff = 1;
        }
        if (d < 0.0) {
            diff = -1;
        }
        return diff * order;
    }

    public static int Sign(double a) {
        if (a == 0.0) {
            return 0;
        }
        return a > 0.0 ? 1 : -1;
    }

    public static int Compare(boolean b1, boolean b2, boolean isascending) {
        int order = isascending ? 1 : -1;
        int a = b1 ? 1 : 0;
        int b = b2 ? 1 : 0;
        return (a - b) * order;
    }

    public static int Compare(TimeData t1, TimeData t2, boolean isascending) {
        return Common.Compare(t1.hours * 60 + t1.minutes, t2.hours * 60 + t2.minutes, isascending);
    }

    public static int GetID() {
        return s_ID++;
    }

    static {
        s_ID = 0;
    }

    public class PatchModif {
        RatingData data;
        int len;

        void Setup(RatingData _data) {
            this.data = _data;
        }

        void Setup(RatingData _data, int _len) {
            this.data = _data;
            this.len = _len;
        }

        void GetData(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            this.data.picsize = sizex;
            for (int i = 0; i < stuff.length; ++i) {
                if (!stuff[i].usepatch) continue;
                this.data.textsize = stuff[i]._patch.sx;
                return;
            }
        }

        void FillData(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            for (int i = 0; i < stuff.length; ++i) {
                if (!stuff[i].usepatch) continue;
                stuff[i]._patch.sx = this.len;
            }
        }
    }
}
