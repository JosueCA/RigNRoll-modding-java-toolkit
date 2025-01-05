/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import java.util.List;
import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.Common;
import menu.Helper;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.NotifyInformation;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrcore.event;
import rnrcore.loc;
import rnrorg.JournalFinishWarehouse;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PanelMenu
extends BaseMenu
implements menucreation {
    public static final int GOLD = 0;
    public static final int SILVER = 1;
    public static final int BRONZE = 2;
    public static int s_response;
    private String menuId;
    private static int s_towrate;
    private static int s_paycheck;
    private static int s_forfeit;
    private static int s_cargodamage;
    private static String s_cgroup;
    private static String s_whname;
    private static int s_ordertype;
    private static int s_place;
    private static Time s_totaltime;
    private static double s_maxspeed;
    private static double s_avespeed;
    private static double s_rating;
    private static int s_totalexpenses;
    private static int s_prizemoney;
    private static int s_yesno;
    private static boolean s_hidesemi;
    String Total_Time_VALUE_MACROS = null;
    private long balanc_control = 0L;

    // @Override
    public void restartMenu(long _menu) {
    }

    private PanelMenu(String menuId) {
        this.menuId = menuId;
    }

    public static int GetResponse() {
        return s_response;
    }

    public static long PanelCommonNotInArea(String whname) {
        s_cgroup = "Panel - Common - NOT IN AREA";
        s_whname = whname;
        return menues.createSimpleMenu(new PanelMenu("notinareaMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long PanelCommonOrderNotHere(String whname) {
        s_cgroup = "Panel - Common - ORDER NOT HERE";
        s_yesno = 1;
        s_whname = whname;
        return menues.createSimpleMenu(new PanelMenu("ordernothereMENU"), 1.0E9, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long PanelCommonMissionNotHere(String whname) {
        s_cgroup = "Panel - Common - MISSION NOT HERE";
        s_yesno = 1;
        s_whname = whname;
        return menues.createSimpleMenu(new PanelMenu("missionothereMENU"), 1.0E9, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long PanelCommonSemitrailerLost(String whname) {
        s_cgroup = "Panel - Common - SEMITRAILER LOST";
        s_yesno = 1;
        s_whname = whname;
        return menues.createSimpleMenu(new PanelMenu("semilostMENU"), 1.0E9, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    private static List<Macros> createMoneyMacro(int paycheck) {
        Macros macroMoney = new Macros("VALUE1", MacroBuilder.makeSimpleMacroBody(Helper.convertMoney(paycheck)));
        ArrayList<Macros> macroces = new ArrayList<Macros>();
        macroces.add(macroMoney);
        return macroces;
    }

    private static List<Macros> createMoneyMacro(int paycheck, int towerrate) {
        ArrayList<Macros> macroces = new ArrayList<Macros>();
        Macros macroMoney = new Macros("VALUE1", MacroBuilder.makeSimpleMacroBody(Helper.convertMoney(paycheck)));
        macroces.add(macroMoney);
        macroMoney = new Macros("VALUE2", MacroBuilder.makeSimpleMacroBody(Helper.convertMoney(towerrate)));
        macroces.add(macroMoney);
        return macroces;
    }

    public static long PanelDeliveryFirst(String whname, int cargodamage, int paycheck, int balance, double rating) {
        s_cgroup = "Panel - Delivery - FIRST";
        s_whname = whname;
        s_cargodamage = cargodamage;
        s_paycheck = paycheck;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("deliveryFirstMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(0, PanelMenu.createMoneyMacro(paycheck));
        return res;
    }

    public static long PanelDeliveryExecuted(String whname, int cargodamage, int paycheck, int balance, double rating) {
        s_cgroup = "Panel - Delivery - EXECUTED";
        s_whname = whname;
        s_paycheck = paycheck;
        s_rating = rating;
        s_cargodamage = cargodamage;
        long res = menues.createSimpleMenu(new PanelMenu("deliveryExecutedMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(0, PanelMenu.createMoneyMacro(paycheck));
        return res;
    }

    public static long PanelContestFirst(String whname, Time total, double maxspeed, double avespeed, double rating, int prizemoney, int balance) {
        s_cgroup = "Panel - Contest - FIRST";
        s_whname = whname;
        s_totaltime = total;
        s_maxspeed = maxspeed;
        s_avespeed = avespeed;
        s_rating = rating;
        s_prizemoney = prizemoney;
        long res = menues.createSimpleMenu(new PanelMenu("contestFirstMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(11, PanelMenu.createMoneyMacro(prizemoney));
        return res;
    }

    public static long PanelContestExecuted(String whname, int place, Time total, double maxspeed, double avespeed, double rating, int balance) {
        s_cgroup = "Panel - Contest - EXECUTED";
        s_whname = whname;
        s_place = place + 1;
        s_totaltime = total;
        s_maxspeed = maxspeed;
        s_avespeed = avespeed;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("contestExecutedMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(12);
        return res;
    }

    public static long PanelTenderFirst(String whname, int ordertype, Time total, double maxspeed, double avespeed, double rating, int balance) {
        s_cgroup = "Panel - Tender - FIRST";
        s_whname = whname;
        s_ordertype = ordertype;
        s_totaltime = total;
        s_maxspeed = maxspeed;
        s_avespeed = avespeed;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("tenderFirstMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        switch (ordertype) {
            case 0: {
                JournalFinishWarehouse.createNote(5);
                break;
            }
            case 1: {
                JournalFinishWarehouse.createNote(6);
                break;
            }
            case 2: {
                JournalFinishWarehouse.createNote(7);
            }
        }
        return res;
    }

    public static long PanelTenderLate(String whname, double rating, int balance) {
        s_cgroup = "Panel - Tender - LATE";
        s_whname = whname;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("tenderLateMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(8);
        return res;
    }

    public static long PanelCommonForfeitEvacuation(String whname, int towrate, int forfeit, double rating, int balance) {
        s_cgroup = "Panel - Common - FORFEIT AND EVACUATION";
        s_whname = whname;
        s_towrate = towrate;
        s_forfeit = forfeit;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("forfeitEvacuationMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(4, PanelMenu.createMoneyMacro(forfeit, towrate));
        return res;
    }

    public static long PanelCommonMissionCancelled(String whname) {
        s_cgroup = "Panel - Common - MISSION CANCELLED";
        s_whname = whname;
        return menues.createSimpleMenu(new PanelMenu("missionCanceledMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long PanelDeliveryOrderCancelled(String whname, double rating, int forfeit, int balance, boolean showsemi) {
        s_cgroup = "Panel - Delivery - ORDER CANCELLED";
        s_whname = whname;
        s_forfeit = forfeit;
        s_rating = rating;
        s_hidesemi = !showsemi;
        long res = menues.createSimpleMenu(new PanelMenu("orderCanceledMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(2, PanelMenu.createMoneyMacro(forfeit));
        return res;
    }

    public static long PanelDeliveryCommonCancelled(String whname, double rating, boolean is_tender) {
        s_cgroup = "Panel - Common - ORDER CANCELLED";
        s_whname = whname;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("orderCanceledCommonMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        if (is_tender) {
            JournalFinishWarehouse.createNote(10);
        } else {
            JournalFinishWarehouse.createNote(14);
        }
        return res;
    }

    public static long PanelDeliveryDamaged(String whname, int forfeit, double rating, int balance, boolean showsemi) {
        s_cgroup = "Panel - Delivery - DAMAGED";
        s_whname = whname;
        s_forfeit = forfeit;
        s_rating = rating;
        s_hidesemi = !showsemi;
        long res = menues.createSimpleMenu(new PanelMenu("deliveryDamagedMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(3, PanelMenu.createMoneyMacro(forfeit));
        return res;
    }

    public static long PanelDeliveryExpired(String whname, int forfeit, double rating, int balance, boolean showsemi) {
        s_cgroup = "Panel - Delivery - EXPIRED";
        s_whname = whname;
        s_forfeit = forfeit;
        s_rating = rating;
        s_hidesemi = !showsemi;
        long res = menues.createSimpleMenu(new PanelMenu("deliveryExpiredMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(1, PanelMenu.createMoneyMacro(forfeit));
        return res;
    }

    public static long PanelTenderDefaulted(String whname, double rating, int balance) {
        s_cgroup = "Panel - Tender - DEFAULTED";
        s_whname = whname;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("tenderDefaultMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(9);
        return res;
    }

    public static long PanelDeliveryTowedCancelled(String whname, int towrate, int forfeit, double rating, int balance) {
        s_cgroup = "Panel - Delivery - TOWED AND CANCELLED";
        s_whname = whname;
        s_forfeit = forfeit;
        s_towrate = towrate;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("deliveryTowedCanceledMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(4, PanelMenu.createMoneyMacro(forfeit, towrate));
        return res;
    }

    public static long PanelDeliveryTowed(String whname, int expenses, int balance) {
        s_cgroup = "Panel - Delivery - TOWED";
        s_whname = whname;
        s_totalexpenses = expenses;
        long res = menues.createSimpleMenu(new PanelMenu("deliveryTowedMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(59, PanelMenu.createMoneyMacro(expenses));
        return res;
    }

    public static long PanelContestDefaulted(String whname, double rating, int balance) {
        s_cgroup = "Panel - Contest - DEFAULTED";
        s_whname = whname;
        s_rating = rating;
        long res = menues.createSimpleMenu(new PanelMenu("contestDefaultMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        JournalFinishWarehouse.createNote(13);
        return res;
    }

    public static long PanelCommonTowed(String whname, int expenses, int balance) {
        s_cgroup = "Panel - Common - TOWED";
        s_whname = whname;
        s_totalexpenses = expenses;
        return menues.createSimpleMenu(new PanelMenu("towerdMENU"), 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    // @Override
    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        menues.InitXml(_menu, Common.ConstructPath("menu_warehouse.xml"), "COMMON");
        menues.InitXml(_menu, Common.ConstructPath("menu_warehouse.xml"), s_cgroup);
        if (s_yesno != -1) {
            this.uiTools.SetScriptOnButton("BUTTON - YES", this, "OnYes");
            this.uiTools.SetScriptOnButton("BUTTON - NO", this, "OnNo");
        }
        menues.SetMenuCallBack_ExitMenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - OK"), 4L);
        this.balanc_control = menues.FindFieldInMenu(_menu, "Your balance - VALUE");
    }

    public void OnYes(long _menu, MENUsimplebutton_field button) {
        s_response = 1;
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void OnNo(long _menu, MENUsimplebutton_field button) {
        s_response = 0;
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    // @Override
    public void AfterInitMenu(long _menu) {
        KeyPair[] macro;
        BalanceUpdater.AddBalanceControl(this.balanc_control);
        if (s_whname != null) {
            macro = new KeyPair[]{new KeyPair("WAREHOUSENAME", loc.getCityName(s_whname))};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Warehouse - TITLE"), macro);
        }
        if (s_towrate != -1) {
            macro = new KeyPair[]{new KeyPair("MONEY", "" + Converts.ConvertNumeric(s_towrate >= 0 ? s_towrate : -s_towrate)), new KeyPair("SIGN", s_towrate >= 0 ? "" : "-")};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Tow-away fee - VALUE"), macro);
        }
        if (s_paycheck != -1) {
            macro = new KeyPair[]{new KeyPair("MONEY", "" + Converts.ConvertNumeric(s_paycheck))};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("You made - VALUE"), macro);
        }
        if (s_forfeit != -1) {
            macro = new KeyPair[]{new KeyPair("MONEY", "" + Converts.ConvertNumeric(s_forfeit >= 0 ? s_forfeit : -s_forfeit)), new KeyPair("SIGN", s_forfeit >= 0 ? "" : "-")};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Forfeit - VALUE"), macro);
        }
        if (s_totalexpenses != -1) {
            macro = new KeyPair[]{new KeyPair("MONEY", "" + Converts.ConvertNumeric(s_totalexpenses >= 0 ? s_totalexpenses : -s_totalexpenses)), new KeyPair("SIGN", s_totalexpenses >= 0 ? "" : "-")};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Total expenses - VALUE"), macro);
        }
        if (s_cargodamage != -1) {
            macro = new KeyPair[]{new KeyPair("VALUE", "" + s_cargodamage)};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Load integrity - VALUE"), macro);
        }
        if (s_ordertype != -1) {
            switch (s_ordertype) {
                case 0: {
                    this.uiTools.SetTextValue("WH - Order First - TEXT", loc.getMENUString("menu_warehouse.xml\\Panel - Tender - FIRST\\ALL Tender - First\\WH - Order First - TEXT.GOLD"));
                    break;
                }
                case 1: {
                    this.uiTools.SetTextValue("WH - Order First - TEXT", loc.getMENUString("menu_warehouse.xml\\Panel - Tender - FIRST\\ALL Tender - First\\WH - Order First - TEXT.SILVER"));
                    break;
                }
                case 2: {
                    this.uiTools.SetTextValue("WH - Order First - TEXT", loc.getMENUString("menu_warehouse.xml\\Panel - Tender - FIRST\\ALL Tender - First\\WH - Order First - TEXT.BRONZE"));
                }
            }
        }
        if (s_place != -1) {
            this.uiTools.SetTextValue("Place - VALUE", s_place + "");
        }
        if (s_totaltime != null) {
            if (this.Total_Time_VALUE_MACROS == null) {
                this.Total_Time_VALUE_MACROS = menues.GetFieldText(menues.FindFieldInMenu(_menu, "Total Time - VALUE"));
            }
            this.uiTools.SetTextValue("Total Time - VALUE", Converts.ConverTime3Plus2(this.Total_Time_VALUE_MACROS, PanelMenu.s_totaltime.hour * 60 + PanelMenu.s_totaltime.min, PanelMenu.s_totaltime.sec));
        }
        if (s_maxspeed != -1.0) {
            macro = new KeyPair[]{new KeyPair("TOP_SPEED", Converts.ConvertDouble(s_maxspeed, 2))};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Top Speed - VALUE"), macro);
        }
        if (s_avespeed != -1.0) {
            macro = new KeyPair[]{new KeyPair("AVERAGE_SPEED", Converts.ConvertDouble(s_avespeed, 2))};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Average Speed - VALUE"), macro);
        }
        if (s_rating != -1000000.0) {
            // empty if block
        }
        if (s_prizemoney != -1) {
            macro = new KeyPair[]{new KeyPair("MONEY", Converts.ConvertNumeric(s_prizemoney))};
            MacroKit.ApplyToTextfield(this.uiTools.FindTextField("Prize Money - VALUE"), macro);
        }
        menues.setfocuscontrolonmenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - OK"));
        NotifyInformation.notifyAfterInit(_menu);
        if (s_hidesemi) {
            menues.SetShowField(this.uiTools.FindTextField((String)"Deliver the semitrailer").nativePointer, false);
        }
    }

    // @Override
    public void exitMenu(long _menu) {
        s_whname = null;
        s_towrate = -1;
        s_paycheck = -1;
        s_forfeit = -1;
        s_cargodamage = -1;
        s_cgroup = "";
        s_ordertype = -1;
        s_place = -1;
        s_totaltime = null;
        s_maxspeed = -1.0;
        s_avespeed = -1.0;
        s_rating = -1000000.0;
        s_prizemoney = -1;
        s_totalexpenses = -1;
        s_yesno = -1;
        s_hidesemi = false;
        BalanceUpdater.RemoveBalanceControl(this.balanc_control);
        event.Setevent(9001);
    }

    // @Override
    public String getMenuId() {
        return this.menuId;
    }

    static {
        s_towrate = -1;
        s_paycheck = -1;
        s_forfeit = -1;
        s_cargodamage = -1;
        s_cgroup = "";
        s_whname = "";
        s_ordertype = -1;
        s_place = -1;
        s_totaltime = null;
        s_maxspeed = -1.0;
        s_avespeed = -1.0;
        s_rating = -1000000.0;
        s_totalexpenses = -1;
        s_prizemoney = -1;
        s_yesno = -1;
        s_hidesemi = false;
    }

    public static class Time {
        int hour;
        int min;
        int sec;

        public Time() {
        }

        public Time(int hour, int min, int sec) {
            this.hour = hour;
            this.min = min;
            this.sec = sec;
        }
    }
}

