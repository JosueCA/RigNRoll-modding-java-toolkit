/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.Random;
import menu.MENUEditBox;
import menu.MENUsimplebutton_field;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import menuscript.office.IChoosedata;
import menuscript.office.PopUpSearch;

public class testpopup
implements menucreation,
IChoosedata {
    private PopUpSearch s;
    private boolean pend_close = false;
    private boolean pend_edit = false;
    private boolean editing = false;
    private boolean pend_exit_edit = false;
    private String filter_text = "";
    private long edit_box;
    private long edit_field;
    private long _menu = 0L;

    public void restartMenu(long _menu) {
    }

    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShowAndStop(_menu);
    }

    public void exitMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        this._menu = _menu;
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_office.xml", "OFFICE");
        this.s = new PopUpSearch(_menu, "Tablegroup - ELEMENTS - HFD Filer Menu");
        long sbutt = menues.FindFieldInMenu(_menu, "BUTTON PopUP - MF - Dealer Offers Search - Model");
        MENUsimplebutton_field button = menues.ConvertSimpleButton(sbutt);
        menues.SetScriptOnControl(_menu, button, this, "onSearch", 4L);
        this.edit_box = menues.FindFieldInMenu(_menu, "MF - Dealer Offers Search - Model - VALUE-Select");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_box), this, "onModelSearch", 4L);
        this.edit_field = menues.FindFieldInMenu(_menu, "MF - Dealer Offers Search - Model - VALUE");
        this.s.addListener(this);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_field), this, "onModelDismiss", 19L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_field), this, "onModelEnter", 16L);
        menues.SetScriptObjectAnimation(0L, 20L, this, "onAnimate");
    }

    public static void create() {
        menues.createSimpleMenu(new testpopup());
    }

    public void onSearch(long _menu, MENUsimplebutton_field field) {
        this.s.show(0);
        String[] data = new String[]{"stoke 1", "is it stroke - o what?", "smooth!"};
        this.s.setData(data);
        this.pend_edit = true;
    }

    public void onModelSearch(long _menu, MENUsimplebutton_field field) {
        this.pend_edit = true;
        this.s.show(0);
        String[] data = new String[]{"stoke 1", "is it stroke - o what?", "smooth!"};
        this.s.setData(data);
    }

    public void onModelEnter(long _menu, MENUEditBox field) {
        this.selectData(this.s.getFocusedData());
    }

    public void onModelDismiss(long _menu, MENUEditBox field) {
        this.selectData(this.s.getFocusedData());
    }

    public void onChange(long _menu, long field) {
        String newtext = menues.GetFieldText(field);
        if (newtext.compareTo(this.filter_text) != 0) {
            this.filter_text = newtext;
            String[] resHACK = new String[(int)(10.0 * new Random().nextDouble())];
            this.s.setData(resHACK);
        }
        this.filter_text = newtext;
    }

    public void onAnimate(long cookie, double time) {
        if (this.pend_close) {
            this.editing = false;
            this.pend_exit_edit = true;
            this.s.hide();
        }
        this.pend_close = false;
        if (this.pend_edit) {
            menues.SetShowField(this.edit_box, false);
            menues.SetShowField(this.edit_field, true);
            menues.setFocusWindow(menues.ConvertWindow((long)menues.GetBackMenu((long)this._menu)).ID);
            menues.setfocuscontrolonmenu(this._menu, this.edit_field);
            this.editing = true;
        }
        this.pend_edit = false;
        if (this.pend_exit_edit) {
            menues.SetShowField(this.edit_box, true);
            menues.SetShowField(this.edit_field, false);
            this.editing = false;
        }
        this.pend_exit_edit = false;
        if (this.editing) {
            this.onChange(this._menu, this.edit_field);
            this.s.show(0);
            this.pend_edit = true;
            menues.setFocusWindow(menues.ConvertWindow((long)menues.GetBackMenu((long)this._menu)).ID);
            menues.setfocuscontrolonmenu(this._menu, this.edit_field);
        }
    }

    public void selectData(int data) {
        this.pend_close = true;
        String[] str = new String[]{"stoke 1", "is it stroke - o what?", "smooth!"};
        this.filter_text = str[data];
        menues.SetFieldText(this.edit_field, this.filter_text);
        menues.SetFieldText(this.edit_box, this.filter_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_field));
        menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
    }

    public String getMenuId() {
        return "officeMENU";
    }
}

