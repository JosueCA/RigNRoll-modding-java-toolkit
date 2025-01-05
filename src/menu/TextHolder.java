/*
 * Decompiled with CFR 0.151.
 */
package menu;

import gameobj.WHOrderInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import menu.BaseMenu;
import menu.Common;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RatingData;
import menu.SelectCb;
import menu.TableNode;
import menu.menues;
import menuscript.Converts;
import menuscript.OfficeTab;
import rnrcore.loc;

public class TextHolder
extends BaseMenu {
    static final int TEXTFIELD = 0;
    static final int SIMPLEBUTTON = 1;
    static final int RADIOBUTTON = 2;
    SelectCb m_cbs;
    RatingData m_ratingdata;
    Vector m_radios = new Vector();
    OfficeTab.FadeAnimation m_FadeAnim;
    long m_SelectedControl;
    int[] m_LineID;
    HashMap m_staticobjs;

    public TextHolder(Common common) {
        this.uiTools = common;
        this.m_staticobjs = new HashMap();
    }

    public void AttachControl(int ID, MENUText_field text) {
        text.userid = ID;
        menues.UpdateField(text);
        this.m_staticobjs.put(new Integer(ID), new TextField(text));
    }

    public void AttachControl(int ID, MENUsimplebutton_field field) {
        field.userid = ID;
        menues.UpdateField(field);
        this.m_staticobjs.put(new Integer(ID), new SimpleButton(field));
    }

    void OnRadioPress(long _menu, MENUbutton_field field) {
        for (int i = 0; i < this.m_radios.size(); ++i) {
            MENUbutton_field radio = (MENUbutton_field)this.m_radios.get(i);
            if (radio != field) {
                menues.SetFieldState(radio.nativePointer, 0);
                continue;
            }
            menues.SetFieldState(radio.nativePointer, 1);
        }
        if (this.m_cbs != null) {
            this.m_cbs.OnSelect(field.userid, this);
        }
        this.Deselect();
        this.Select(field.nativePointer);
    }

    private void Deselect() {
        if (this.m_SelectedControl == 0L) {
            return;
        }
        this.m_FadeAnim.Finish(this.m_SelectedControl);
        this.m_SelectedControl = 0L;
    }

    private void Select(long control) {
        this.m_SelectedControl = control;
        this.m_FadeAnim.Start(this.m_SelectedControl);
    }

    public void AttachControl(int ID, MENUbutton_field field) {
        field.userid = ID;
        menues.UpdateField(field);
        this.m_staticobjs.put(new Integer(ID), new RadioButton(field));
        menues.SetScriptOnControl(this.uiTools.GetMenu(), field, this, "OnRadioPress", 2L);
        this.m_radios.add(field);
    }

    private void FillInfo(WHOrderInfo order, HashMap map) {
    	Set<Entry<Integer, Control>> entrySet = map.entrySet();
        for (Map.Entry entry : entrySet) {
            int id = (Integer)entry.getKey();
            Control c = (Control)entry.getValue();
            if (order != null) {
                KeyPair[] keys = new KeyPair[1];
                switch (id) {
                    case 3: {
                        if (order.GetOrderType() == 0) {
                            c.SetText(loc.getAiString("DELIVERY"));
                            break;
                        }
                        if (order.GetOrderType() == 1) {
                            c.SetText(loc.getAiString("QUALIFYING_RACE"));
                            break;
                        }
                        if (order.GetOrderType() == 2) {
                            c.SetText(loc.getAiString("ANOTHER_ORDER"));
                            break;
                        }
                        if (order.GetOrderType() == 3) {
                            c.SetText(loc.getAiString("BIGRACE"));
                            break;
                        }
                        c.SetText(loc.getAiString("DELIVERY"));
                        break;
                    }
                    case 4: {
                        c.SetText(order.shipto);
                        break;
                    }
                    case 5: {
                        c.SetText(order.freight);
                        break;
                    }
                    case 6: {
                        keys[0] = new KeyPair("MONEY", "" + Converts.ConvertNumeric(order.charges));
                        c.SetText(MacroKit.Parse(c.GetNativeString(), keys));
                        break;
                    }
                    case 7: {
                        c.SetText(order.competition ? loc.getMENUString("common\\YES") : loc.getMENUString("common\\NO"));
                        break;
                    }
                    case 8: {
                        keys[0] = new KeyPair("MONEY", "" + order.forfeit);
                        c.SetText(MacroKit.Parse(c.GetNativeString(), keys));
                        break;
                    }
                    case 9: {
                        c.SetText(Converts.ConverTime3Plus2(c.GetNativeString(), order.time_limit_hour, order.time_limit_min));
                        break;
                    }
                    case 10: {
                        keys[0] = new KeyPair("VALUE", "" + Converts.ConvertDouble(order.weight, 3));
                        c.SetText(MacroKit.Parse(c.GetNativeString(), keys));
                        break;
                    }
                    case 11: {
                        keys[0] = new KeyPair("VALUE", "" + Converts.ConvertDouble(order.fragil * 100.0, 0));
                        c.SetText(MacroKit.Parse(c.GetNativeString(), keys));
                        break;
                    }
                    case 12: {
                        keys[0] = new KeyPair("VALUE", "" + Converts.ConvertDouble(order.distance + 0.5, 0));
                        c.SetText(MacroKit.Parse(c.GetNativeString(), keys));
                    }
                }
            } else {
                c.SetText("");
            }
            c.Update();
        }
    }

    public void FillWHOrderInfo(WHOrderInfo order) {
        this.FillInfo(order, this.m_staticobjs);
    }

    public void SetupLineInTable(int type, TableNode node, Object control) {
    }

    class RadioButton
    implements Control {
        MENUbutton_field field;
        String native_text;

        public RadioButton(MENUbutton_field _field) {
            this.field = _field;
            this.native_text = this.field.text;
        }

        public void SetText(String text) {
            this.field.text = text;
        }

        public String GetNativeString() {
            return this.native_text;
        }

        public void Update() {
            menues.UpdateField(this.field);
        }

        public int GetType() {
            return 2;
        }

        public int GetX() {
            return this.field.pox;
        }

        public int GetY() {
            return this.field.poy;
        }

        public void SetX(int x) {
            this.field.pox = x;
        }

        public void SetY(int y) {
            this.field.poy = y;
        }

        public void SetParent(String parent) {
            this.field.parentName = parent;
        }

        public long GetNativeP() {
            return this.field.nativePointer;
        }
    }

    class SimpleButton
    implements Control {
        MENUsimplebutton_field field;
        String native_text;

        public SimpleButton(MENUsimplebutton_field _field) {
            this.field = _field;
            this.native_text = this.field.text;
        }

        public void SetText(String text) {
            this.field.text = text;
        }

        public String GetNativeString() {
            return this.native_text;
        }

        public void Update() {
            menues.UpdateField(this.field);
        }

        public int GetType() {
            return 1;
        }

        public int GetX() {
            return this.field.pox;
        }

        public int GetY() {
            return this.field.poy;
        }

        public void SetX(int x) {
            this.field.pox = x;
        }

        public void SetY(int y) {
            this.field.poy = y;
        }

        public void SetParent(String parent) {
            this.field.parentName = parent;
        }

        public long GetNativeP() {
            return this.field.nativePointer;
        }
    }

    class TextField
    implements Control {
        MENUText_field field;
        String native_text;

        public TextField(MENUText_field _field) {
            this.field = _field;
            this.native_text = this.field.text;
        }

        public void SetText(String text) {
            this.field.text = text;
        }

        public String GetNativeString() {
            return this.native_text;
        }

        public void Update() {
            menues.UpdateField(this.field);
        }

        public int GetType() {
            return 0;
        }

        public int GetX() {
            return this.field.pox;
        }

        public int GetY() {
            return this.field.poy;
        }

        public void SetX(int x) {
            this.field.pox = x;
        }

        public void SetY(int y) {
            this.field.poy = y;
        }

        public void SetParent(String parent) {
            this.field.parentName = parent;
        }

        public long GetNativeP() {
            return this.field.nativePointer;
        }
    }

    public static interface Control {
        public void SetText(String var1);

        public String GetNativeString();

        public void Update();

        public int GetX();

        public int GetY();

        public void SetX(int var1);

        public void SetY(int var1);

        public void SetParent(String var1);

        public int GetType();

        public long GetNativeP();
    }
}

