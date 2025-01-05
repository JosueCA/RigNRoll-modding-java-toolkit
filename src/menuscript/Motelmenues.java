/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.Common;
import menu.KeyPair;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SMenu;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import players.Crew;
import rnrcore.CoreTime;
import rnrscr.CBVideoInterruptCalls;
import rnrscr.gameinfo;

public class Motelmenues
extends BaseMenu
implements menucreation {
    String MenuRepairTexture;
    static int s_iStoredHour;
    static int s_iStoredMin;
    static boolean s_bHoldHit;
    private final String[] control_names = new String[]{"Check IN - Date - VALUE", "Check OUT - Date - VALUE", "1 VALUE", "2 VALUE", "3 VALUE", "Motel MAIN 2 - TITLE"};
    private static final int CHECK_IN_DATE = 0;
    private static final int CHECK_OUT_DATE = 1;
    private static final int MOTEL_RATE = 2;
    private static final int MOTEL_HOURS_SPENT = 3;
    private static final int MOTEL_MONEY_TOTAL = 4;
    private static final int CITY_NAME = 5;
    private static final int MAX = 6;
    String[] loc_text = null;
    long[] controls = null;
    private PoPUpMenu not_enought_money = null;
    private static final String[] POPUP_BUTTONS;
    private static final String[] POPUP_METHODS;
    private long balanc_control = 0L;

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        menues.InitXml(_menu, Common.ConstructPath("menu_motel.xml"), "MenuMotel COMMON");
        menues.InitXml(_menu, Common.ConstructPath("menu_motel.xml"), "MenuMotel");
        this.balanc_control = menues.FindFieldInMenu(_menu, "YOUR BALANCE - VALUE");
        gameinfo info = gameinfo.script;
        MENUText_field checkin = this.uiTools.FindTextField("Check IN - VALUE");
        checkin.text = Converts.ConverTimeForMotel(checkin.text, info.m_moteldata.iCheckInHour, info.m_moteldata.iCheckInMin);
        menues.UpdateField(checkin);
        this.controls = new long[6];
        for (int i = 0; i < 6; ++i) {
            this.controls[i] = menues.FindFieldInMenu(_menu, this.control_names[i]);
        }
        if (Crew.getIgrok() != null && Crew.getIgrok().gFirstName() != null && Crew.getIgrok().gLastName() != null) {
            KeyPair[] pairs = new KeyPair[]{new KeyPair("NICOLAS_ARMSTRONG", Crew.getIgrok().gFirstName() + " " + Crew.getIgrok().gLastName())};
            menues.SetFieldText(menues.FindFieldInMenu(_menu, "Guest VALUE"), MacroKit.Parse(menues.GetFieldText(menues.FindFieldInMenu(_menu, "Guest VALUE")), pairs));
            menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, "Guest VALUE")));
        }
        this.not_enought_money = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_motel.xml", "MESSAGE - Not enough money", "MESSAGE - Not enough money", POPUP_BUTTONS, POPUP_METHODS);
    }

    public void AfterInitMenu(long _menu) {
        BalanceUpdater.AddBalanceControl(this.balanc_control);
        gameinfo info = gameinfo.script;
        long p = menues.FindFieldInMenu(_menu, "Check OUT - VALUE");
        MENUEditBox editbox = (MENUEditBox)menues.ConvertMenuFields(p);
        editbox.SetMode(1);
        editbox.SetHour(info.m_moteldata.iCheckOutHour);
        editbox.SetMin(info.m_moteldata.iCheckOutMin);
        menues.setfocuscontrolonmenu(_menu, editbox.nativePointer);
        menues.SetScriptOnControl(_menu, editbox, this, "OnHitEnter", 16L);
        this.loc_text = new String[6];
        for (int i = 0; i < 6; ++i) {
            this.loc_text[i] = menues.GetFieldText(this.controls[i]);
        }
        this.UpdatePrice(_menu, info);
        MENUsimplebutton_field buttonup = this.uiTools.FindSimpleButton("BUTTON - Scroller ArrowUP");
        MENUsimplebutton_field buttondown = this.uiTools.FindSimpleButton("BUTTON - Scroller ArrowDOWN");
        menues.SetScriptOnControlDataPass(_menu, buttonup, this, "OnUpDown", 8L);
        menues.SetScriptOnControlDataPass(_menu, buttondown, this, "OnUpDown", 8L);
        menues.SetScriptOnControlDataPass(_menu, buttonup, this, "OnUpDownRelease", 4L);
        menues.SetScriptOnControlDataPass(_menu, buttondown, this, "OnUpDownRelease", 4L);
        menues.SetScriptOnControl(_menu, editbox, this, "OnClockChange", 9L);
        long control = menues.FindFieldInMenu(_menu, "BUTTON - OK");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "OnOk", 4L);
        control = menues.FindFieldInMenu(_menu, "BUTTON - CANCEL");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "OnCancel", 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
        this.not_enought_money.addListener(new MenuNotEnoughtMoneyListener());
        this.not_enought_money.afterInit();
    }

    MENUEditBox FindEditbox(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);
        return (MENUEditBox)menues.ConvertMenuFields(control);
    }

    void UpdatePrice(long _menu, gameinfo info) {
        if (this.controls == null || this.loc_text == null) {
            return;
        }
        int hour = info.m_moteldata.iCheckOutHour * 60 + info.m_moteldata.iCheckOutMin - (info.m_moteldata.iCheckInHour * 60 + info.m_moteldata.iCheckInMin);
        if (hour < 0) {
            hour += 1440;
        }
        int real_min = hour;
        hour = (hour + 59) / 60;
        CoreTime check_in_date = info.m_moteldata.current_date;
        CoreTime check_out_date = new CoreTime(check_in_date.gYear(), check_in_date.gMonth(), check_in_date.gDate(), check_in_date.gHour(), check_in_date.gMinute());
        CoreTime add = new CoreTime(0, 0, 0, 0, real_min);
        check_out_date.plus(add);
        for (int i = 0; i < 6; ++i) {
            if (this.controls[i] == 0L || this.loc_text[i] == null) continue;
            KeyPair[] pairs = null;
            if (i == 0) {
                menues.SetFieldText(this.controls[i], Converts.ConvertDate(this.loc_text[i], check_in_date.gMonth(), check_in_date.gDate(), check_in_date.gYear()));
                if (menues.ConvertMenuFields(this.controls[i]) == null) continue;
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[i]));
                continue;
            }
            if (i == 1) {
                menues.SetFieldText(this.controls[i], Converts.ConvertDate(this.loc_text[i], check_out_date.gMonth(), check_out_date.gDate(), check_out_date.gYear()));
                if (menues.ConvertMenuFields(this.controls[i]) == null) continue;
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[i]));
                continue;
            }
            if (i == 2) {
                pairs = new KeyPair[]{new KeyPair("MOTEL_RATE", "" + Converts.ConvertDouble(info.m_moteldata.dCostPerHour, 2))};
                menues.SetFieldText(this.controls[i], MacroKit.Parse(this.loc_text[i], pairs));
                if (menues.ConvertMenuFields(this.controls[i]) == null) continue;
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[i]));
                continue;
            }
            if (i == 3) {
                pairs = new KeyPair[]{new KeyPair("MOTEL_HOURS_SPENT", "" + hour)};
                menues.SetFieldText(this.controls[i], MacroKit.Parse(this.loc_text[i], pairs));
                if (menues.ConvertMenuFields(this.controls[i]) == null) continue;
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[i]));
                continue;
            }
            if (i == 4) {
                pairs = new KeyPair[]{new KeyPair("MOTEL_MONEY_TOTAL", "" + Converts.ConvertDouble((double)hour * info.m_moteldata.dCostPerHour, 2))};
                menues.SetFieldText(this.controls[i], MacroKit.Parse(this.loc_text[i], pairs));
                if (menues.ConvertMenuFields(this.controls[i]) == null) continue;
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[i]));
                continue;
            }
            if (i != 5) continue;
            pairs = new KeyPair[]{new KeyPair("CITY", info.m_moteldata.city_name)};
            menues.SetFieldText(this.controls[i], MacroKit.Parse(this.loc_text[i], pairs));
            if (menues.ConvertMenuFields(this.controls[i]) == null) continue;
            menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[i]));
        }
    }

    public void SyncinTime(long _menu, gameinfo info) {
        MENUEditBox clock = this.FindEditbox(_menu, "Check OUT - VALUE");
        info.m_moteldata.iCheckOutHour = clock.GetHour();
        info.m_moteldata.iCheckOutMin = clock.GetMin();
        this.UpdatePrice(_menu, info);
    }

    private void AddTime(long _menu, int direction) {
        MENUEditBox clock = this.FindEditbox(_menu, "Check OUT - VALUE");
        clock.IncDecValue(direction);
    }

    public void OnHitEnter(long _menu, MENUEditBox obj) {
        this.fixupData();
        menues.CallMenuCallBack_OKMenu(_menu);
        gameinfo info = gameinfo.script;
        if (!info.m_moteldata.bEnoughtMoney) {
            this.not_enought_money.show();
        }
    }

    public void OnOk(long _menu, MENUsimplebutton_field obj) {
        this.fixupData();
        menues.CallMenuCallBack_OKMenu(_menu);
        gameinfo info = gameinfo.script;
        if (!info.m_moteldata.bEnoughtMoney) {
            this.not_enought_money.show();
        }
    }

    public void OnExit(long _menu, SMenu button) {
        this.fixupData();
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void OnCancel(long _menu, MENUsimplebutton_field obj) {
        this.fixupData();
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void OnClockChange(long _menu, MENUEditBox button) {
        gameinfo info = gameinfo.script;
        this.SyncinTime(_menu, info);
    }

    public void OnUpDown(long _menu, MENUsimplebutton_field button, long data) {
        int direction = button.nameID.indexOf("UP") != -1 ? 1 : -1;
        gameinfo info = gameinfo.script;
        if (data == 0L) {
            s_bHoldHit = false;
        } else {
            if (data == 1L) {
                return;
            }
            this.AddTime(_menu, direction);
        }
    }

    public void OnUpDownRelease(long _menu, MENUsimplebutton_field button, long data) {
        if (s_bHoldHit) {
            s_bHoldHit = false;
            return;
        }
        int direction = button.nameID.indexOf("UP") != -1 ? 1 : -1;
        gameinfo info = gameinfo.script;
        this.AddTime(_menu, direction);
    }

    public void exitMenu(long _menu) {
        BalanceUpdater.RemoveBalanceControl(this.balanc_control);
    }

    public String getMenuId() {
        return "motelMENU";
    }

    private void fixupData() {
        gameinfo.MotelData data = gameinfo.script.m_moteldata;
        CoreTime checkoutTime = new CoreTime();
        if (data.iCheckInHour > data.iCheckOutHour) {
            checkoutTime.plus_days(1);
        } else if (data.iCheckInHour == data.iCheckOutHour && data.iCheckInMin > data.iCheckOutMin) {
            checkoutTime.plus_days(1);
        }
        checkoutTime.sHour(data.iCheckOutHour);
        checkoutTime.sMinute(data.iCheckOutMin);
        CoreTime timenew = CBVideoInterruptCalls.interruptCall(new CoreTime(), checkoutTime);
        data.iCheckOutHour = timenew.gHour();
        data.iCheckOutMin = timenew.gMinute();
    }

    static {
        POPUP_BUTTONS = new String[]{"OK"};
        POPUP_METHODS = new String[]{"OnOk"};
    }

    class MenuNotEnoughtMoneyListener
    implements IPoPUpMenuListener {
        MenuNotEnoughtMoneyListener() {
        }

        public void onAgreeclose() {
        }

        public void onClose() {
        }

        public void onOpen() {
        }

        public void onCancel() {
        }
    }
}

