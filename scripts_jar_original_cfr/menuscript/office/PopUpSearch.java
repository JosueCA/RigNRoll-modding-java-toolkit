/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.ArrayList;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.office.IChoosedata;
import rnrcore.Log;

public class PopUpSearch {
    private static String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static int MENU_POSITION = 0;
    private static String TEMPLATE_GROOUP = "Tablegroup - ELEMENTS - Filer Button";
    private static String BUTTON_TEMPLATE = "SelectSearch";
    private static String ACTION_METH = "onButton";
    private static int count = 0;
    private static final String BUTTONS_SUFFIX = " PopUpSearch";
    private long _menu = 0L;
    private int num_controls = 0;
    private int shift_controls = 0;
    private long menu_control = 0L;
    private String menu_name;
    private int initial_y = 0;
    private long[] buttons = null;
    private int size = 0;
    private ArrayList<IChoosedata> listeners = new ArrayList();

    void addListener(IChoosedata listener) {
        this.listeners.add(listener);
    }

    void removeListener(IChoosedata listener) {
        this.listeners.remove(listener);
    }

    PopUpSearch(long _menu, String MENU_GROUP) {
        this._menu = _menu;
        long[] menu_controls = menues.InitXml(_menu, XML, MENU_GROUP);
        if (null == menu_controls || menu_controls.length < MENU_POSITION) {
            Log.menu("ERRORR. PopUpSearch cannot load xml " + XML + " group " + MENU_GROUP);
            return;
        }
        this.menu_control = menu_controls[MENU_POSITION];
        SMenu window_control = menues.ConvertWindow(this.menu_control);
        this.menu_name = window_control.nameID;
        String[] astr = this.menu_name.split(" ");
        if (astr.length < 2) {
            Log.menu("ERRORR. PopUpSearch. Menu Table. Bad name for root element - does not include table sizes. Name:\t" + this.menu_name);
            return;
        }
        this.num_controls = Integer.decode(astr[astr.length - 2]);
        this.shift_controls = Integer.decode(astr[astr.length - 1]);
        window_control.nameID = this.menu_name = this.menu_name + BUTTONS_SUFFIX + count;
        menues.UpdateField(window_control);
        this.formMenu();
        this.formControls(_menu);
    }

    private void formMenu() {
        SMenu menu = menues.ConvertWindow(this.menu_control);
        menu.poy -= this.shift_controls * (this.num_controls - 1);
        menu.leny = this.shift_controls * this.num_controls;
        this.initial_y = menu.poy;
        menues.UpdateField(menu);
    }

    private void formControls(long _menu) {
        this.buttons = new long[this.num_controls];
        for (int i = 0; i < this.num_controls; ++i) {
            long button;
            this.buttons[i] = button = menues.InitXmlControl(_menu, XML, TEMPLATE_GROOUP, BUTTON_TEMPLATE);
            MENUsimplebutton_field butt = menues.ConvertSimpleButton(button);
            butt.nameID = butt.nameID + BUTTONS_SUFFIX + ++count;
            butt.userid = i;
            butt.parentName = this.menu_name;
            butt.poy += i * this.shift_controls;
            butt.text = "Boo" + i;
            menues.UpdateField(butt);
            menues.SetScriptOnControl(_menu, butt, this, ACTION_METH, 4L);
        }
    }

    void show(int shift) {
        menues.SetShowField(this.menu_control, true);
        SMenu menu = menues.ConvertWindow(this.menu_control);
        menu.poy = this.initial_y + shift;
        menues.UpdateField(menu);
    }

    void hide() {
        menues.SetShowField(this.menu_control, false);
    }

    private int getButtonIndex(int dataindex) {
        return this.num_controls - dataindex - 1;
    }

    public int getFocusedData() {
        int count = 0;
        for (long ser : this.buttons) {
            if (menues.isControlOnFocus(this.menu_control, ser)) {
                return this.getDataIndex(count);
            }
            ++count;
        }
        return this.getDataIndex(this.size - 1);
    }

    private int getDataIndex(int buttonindex) {
        return this.num_controls - buttonindex - 1;
    }

    void setData(String[] data) {
        int i;
        this.size = data.length > this.num_controls ? this.num_controls : data.length;
        for (i = 0; i < this.size; ++i) {
            menues.SetShowField(this.buttons[this.getButtonIndex(i)], true);
            menues.SetFieldText(this.buttons[this.getButtonIndex(i)], data[i]);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.buttons[this.getButtonIndex(i)]));
        }
        menues.setfocuscontrolonmenu(this._menu, this.buttons[this.getButtonIndex(this.size - 1)]);
        for (i = this.size; i < this.num_controls; ++i) {
            menues.SetShowField(this.buttons[this.getButtonIndex(i)], false);
        }
    }

    public void onButton(long _menu, MENUsimplebutton_field field) {
        for (IChoosedata data : this.listeners) {
            data.selectData(this.getDataIndex(field.userid));
        }
    }
}

