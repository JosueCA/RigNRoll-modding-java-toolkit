/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.Common;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SMenu;
import menu.menucreation;
import menu.menues;
import rnrscr.gameinfo;

public class gasstationmenu
extends BaseMenu
implements menucreation {
    private static final String[] BUTTONS = new String[]{"CANCEL button", "PAY button", "FULL button"};
    private static final String[] ACTIONS = new String[]{"OnCancel", "OnPay", "OnFull"};
    private static final int full_button = 2;
    private static final String RANGER = "RANGER button";
    private static final String RANGER_BACK = "FuelTankIndicator";
    static double s_fAnimDurationMin = 0.1;
    static double s_fAnimDurationMax = 7.0;
    static double s_fMaxLitres = 500.0;
    private long[] buttons = new long[BUTTONS.length];
    static int s_fProgressBarLen;
    static MENUText_field s_fieldThisSale;
    static MENUText_field s_fieldGallons;
    static MENUText_field s_fieldProgressBar;
    static gameinfo s_info;
    static double s_fAnimationLen;
    static int s_animid;
    private boolean action_started = false;
    int m_iMaxPossiblePurchase;
    String thisSaleText = null;
    String pricePerGallonText = null;
    String totalPriceText = null;

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        this.uiTools.InitBalance();
        menues.InitXml(_menu, Common.ConstructPath("menu_gasstation.xml"), "Menu GAS");
    }

    public void PrintSimpleButton(MENUsimplebutton_field button) {
    }

    MENUText_field FindTextField(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);
        if (control == 0L) {
            // empty if block
        }
        return menues.ConvertTextFields(control);
    }

    MENUsimplebutton_field FindSimpleButton(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);
        if (control == 0L) {
            // empty if block
        }
        return menues.ConvertSimpleButton(control);
    }

    MENU_ranger FindScroller(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);
        if (control == 0L) {
            // empty if block
        }
        return menues.ConvertRanger(control);
    }

    public void PrintRanger(MENU_ranger ranger) {
    }

    public void AfterInitMenu(long _menu) {
        this.InitData(_menu);
        menues.setShowMenu(_menu, true);
        for (int i = 0; i < BUTTONS.length; ++i) {
            MENUsimplebutton_field field = this.FindSimpleButton(_menu, BUTTONS[i]);
            this.buttons[i] = field.nativePointer;
            menues.SetScriptOnControl(_menu, field, this, ACTIONS[i], 4L);
            if (i != 2) continue;
            menues.setfocuscontrolonmenu(_menu, field.nativePointer);
        }
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
    }

    void SetFieldText(long menu, String name, String text) {
        MENUText_field field = this.FindTextField(menu, name);
        field.text = text;
        menues.UpdateField(field);
    }

    String GetFieldText(long menu, String name) {
        MENUText_field field = this.FindTextField(menu, name);
        return field.text;
    }

    private void RecalucateBought(boolean bDoMaxBought) {
        gameinfo info = gameinfo.script;
        info.m_iMaxBought = info.m_iMaxLitres - info.m_iStartLitres;
        if (bDoMaxBought) {
            info.m_iCurBought = info.m_iMaxBought;
        }
        long balance = 100L * Math.max((long)BalanceUpdater.GetBalance(), 0L);
        this.m_iMaxPossiblePurchase = (int)Math.min(Math.max((long)((double)balance / (double)info.m_iPricePerLitre), 0L), (long)info.m_iMaxBought);
        info.m_iCurBought = info.m_iCurBought > this.m_iMaxPossiblePurchase ? this.m_iMaxPossiblePurchase : info.m_iCurBought;
    }

    public void InitData(long _menu) {
        this.thisSaleText = this.GetFieldText(_menu, "ThisSale");
        this.pricePerGallonText = this.GetFieldText(_menu, "PricePerGallon");
        this.totalPriceText = this.GetFieldText(_menu, "TotalPrice");
        gameinfo info = gameinfo.script;
        this.RecalucateBought(true);
        this.SetFieldText(_menu, "PricePerGallon", this.FormatPriceShort(info.m_iPricePerLitre, this.pricePerGallonText, "GAS_PRICE_PER_GALLON"));
        this.SetFieldText(_menu, "ThisSale", this.FormatPrice(0, this.thisSaleText, "GAS_THIS_SALE"));
        this.SetFieldText(_menu, "Gallons", this.FormatGallonsShort(0));
        this.SetFieldText(_menu, "TotalGallons", this.FormatGallonsShort(info.m_iCurBought));
        this.SetFieldText(_menu, "TotalPrice", this.FormatPrice(info.m_iCurBought * info.m_iPricePerLitre, this.totalPriceText, "GAS_TOTAL_PRICE"));
        MENUText_field progressbar = this.FindTextField(_menu, RANGER_BACK);
        s_fProgressBarLen = progressbar.lenx;
        progressbar.lenx = (int)((double)info.m_iStartLitres / (double)info.m_iMaxLitres * (double)s_fProgressBarLen);
        menues.UpdateField(progressbar);
        MENU_ranger ranger = this.FindScroller(_menu, RANGER);
        int newx = progressbar.pox + progressbar.lenx - ranger.thumbsize;
        ranger.lenx -= newx - ranger.pox;
        ranger.lenx -= ranger.thumbsize;
        int deltamax = (int)((double)(info.m_iMaxBought - this.m_iMaxPossiblePurchase) / (double)info.m_iMaxBought * (double)ranger.lenx);
        ranger.lenx -= deltamax;
        if (ranger.lenx <= 0) {
            ranger.lenx = 1;
        }
        ranger.lenx += ranger.thumbsize;
        ranger.pox = newx;
        ranger.min_value = 0;
        ranger.current_value = ranger.max_value = this.m_iMaxPossiblePurchase;
        if (ranger.max_value == 0) {
            menues.SetIgnoreEvents(ranger.nativePointer, true);
            ranger.max_value = 1;
        }
        menues.UpdateField(ranger);
        menues.UpdateField(ranger);
        menues.SetScriptOnControl(_menu, ranger, this, "OnScroller", 1L);
    }

    private void RecalculateAnimation() {
        double length;
        this.RecalucateBought(false);
        gameinfo info = gameinfo.script;
        double factor = Math.min((double)info.m_iCurBought / s_fMaxLitres, 1.0);
        s_fAnimationLen = length = s_fAnimDurationMin + (s_fAnimDurationMax - s_fAnimDurationMin) * factor;
        s_info = info;
    }

    public void FinalAnimation(long _menu, double time) {
        this.RecalculateAnimation();
        double factor = time / s_fAnimationLen;
        if (factor > 1.0) {
            menues.StopScriptAnimation(s_animid);
            s_fieldGallons = null;
            s_fieldThisSale = null;
            s_fieldProgressBar = null;
            s_info = null;
            menues.CallMenuCallBack_OKMenu(_menu);
            return;
        }
        int curvalue = (int)Math.ceil(factor * (double)gasstationmenu.s_info.m_iCurBought);
        gasstationmenu.s_fieldGallons.text = this.FormatGallonsShort(curvalue);
        gasstationmenu.s_fieldThisSale.text = this.FormatPrice(curvalue * gasstationmenu.s_info.m_iPricePerLitre, this.thisSaleText, "GAS_THIS_SALE");
        double barlen = (double)(curvalue + gasstationmenu.s_info.m_iStartLitres) / (double)gasstationmenu.s_info.m_iMaxLitres;
        gasstationmenu.s_fieldProgressBar.lenx = (int)(barlen * (double)s_fProgressBarLen);
        menues.UpdateField(s_fieldGallons);
        menues.UpdateField(s_fieldThisSale);
        menues.UpdateField(s_fieldProgressBar);
    }

    public void OnPay(long _menu, MENUsimplebutton_field button) {
        if (this.action_started) {
            return;
        }
        this.action_started = true;
        this.RecalculateAnimation();
        s_fieldGallons = this.FindTextField(_menu, "Gallons");
        s_fieldThisSale = this.FindTextField(_menu, "ThisSale");
        s_fieldProgressBar = this.FindTextField(_menu, RANGER_BACK);
        MENU_ranger ranger = this.FindScroller(_menu, RANGER);
        menues.SetIgnoreEvents(ranger.nativePointer, true);
        for (int i = 0; i < this.buttons.length; ++i) {
            menues.SetIgnoreEvents(this.buttons[i], true);
        }
        menues.SetScriptObjectAnimation(_menu, s_animid, this, "FinalAnimation");
    }

    public void OnExit(long _menu, SMenu button) {
        this.OnCancel(this.uiTools.s_menu, null);
    }

    public void OnCancel(long _menu, MENUsimplebutton_field button) {
        if (this.action_started) {
            return;
        }
        this.action_started = true;
        if (s_fieldProgressBar != null) {
            menues.StopScriptAnimation(s_animid);
            s_fieldGallons = null;
            s_fieldThisSale = null;
            s_fieldProgressBar = null;
            s_info = null;
        }
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public String FormatPrice(int _price, String text, String macro) {
        int i_price = (int)Math.floor((double)_price / 100.0);
        int m_price = _price - i_price * 100;
        String i_ret = i_price >= 1000 ? "" + i_price : (i_price >= 100 ? "0" + i_price : (i_price >= 10 ? "00" + i_price : "000" + i_price));
        String m_ret = m_price >= 10 ? "." + m_price : ".0" + m_price;
        if (text == null || macro == null) {
            return i_ret + m_ret;
        }
        KeyPair[] keys = new KeyPair[]{new KeyPair(macro, i_ret + m_ret)};
        return MacroKit.Parse(text, keys);
    }

    public String FormatPriceShort(int _price, String text, String macro) {
        int i_price = (int)Math.floor((double)_price / 100.0);
        int m_price = _price - i_price * 100;
        String i_ret = i_price >= 100 ? "" + i_price : (i_price >= 10 ? "0" + i_price : "00" + i_price);
        String m_ret = m_price >= 10 ? "." + m_price : ".0" + m_price;
        if (text == null || macro == null) {
            return i_ret + m_ret;
        }
        KeyPair[] keys = new KeyPair[]{new KeyPair(macro, i_ret + m_ret)};
        return MacroKit.Parse(text, keys);
    }

    public String FormatGallonsShort(int _gallon) {
        int gallon = _gallon * 100;
        int i_price = (int)Math.floor((double)gallon / 100.0);
        int m_price = gallon - i_price * 100;
        String i_ret = i_price >= 100 ? "" + i_price : (i_price >= 10 ? "0" + i_price : "00" + i_price);
        String m_ret = m_price >= 10 ? "." + m_price : ".0" + m_price;
        return i_ret + m_ret;
    }

    public void OnScroller(long _menu, MENU_ranger ranger) {
        gameinfo info = gameinfo.script;
        info.m_iCurBought = ranger.current_value;
        this.SetFieldText(_menu, "TotalGallons", this.FormatGallonsShort(info.m_iCurBought));
        this.SetFieldText(_menu, "TotalPrice", this.FormatPrice(info.m_iCurBought * info.m_iPricePerLitre, this.totalPriceText, "GAS_TOTAL_PRICE"));
    }

    public void OnFull(long _menu, MENUsimplebutton_field field) {
        MENU_ranger ranger = this.FindScroller(_menu, RANGER);
        ranger.current_value = this.m_iMaxPossiblePurchase;
        menues.UpdateField(ranger);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "gasMENU";
    }

    static {
        s_animid = Common.GetID();
    }
}

