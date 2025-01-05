/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.DateData;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.TimeData;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;
import rnrconfig.IconMappings;
import rnrcore.CoreTime;
import rnrcore.loc;

public class BannerMenu
implements menucreation {
    private static final String LOGO_FIELD = "THE RACE LOGOTYPE";
    private static final String FIELD_RACE_NAME = "RaceName";
    public Common common;
    private String menuid;
    MENUText_field m_cdownfield = null;
    int m_animid = -1;
    CoreTime s_start_date = null;
    String s_controlgroup = null;
    String race_name;
    String logo_name;
    boolean need_race_name = false;
    boolean isCountDown = false;
    int s_raceid = -1;
    String s_start;
    String s_finish;
    String s_race_logo_id = null;
    int s_winmoney = -1;
    double s_rating = -1.0;
    TimeData s_time;
    DateData s_date;
    String s_cityname;

    BannerMenu(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuId() {
        return this.menuid;
    }

    public static long createSameMenu(BannerMenu menu) {
        BannerMenu newmenu = new BannerMenu(menu.menuid);
        newmenu.race_name = menu.race_name;
        newmenu.need_race_name = menu.need_race_name;
        newmenu.s_controlgroup = menu.s_controlgroup;
        newmenu.s_raceid = menu.s_raceid;
        newmenu.s_start = menu.s_start;
        newmenu.s_finish = menu.s_finish;
        newmenu.s_rating = menu.s_rating;
        newmenu.s_time = menu.s_time;
        newmenu.s_date = menu.s_date;
        newmenu.isCountDown = menu.isCountDown;
        newmenu.s_cityname = menu.s_cityname;
        newmenu.s_race_logo_id = menu.s_race_logo_id;
        newmenu.s_start_date = menu.s_start_date;
        newmenu.m_animid = -1;
        return menues.createSimpleMenu(newmenu, 1.0E8, "", 1600, 1200, 600, 300, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateStellaCheckIn(int raceid, CoreTime date) {
        String menuid = "";
        switch (raceid) {
            case 1: {
                menuid = "racecheckin04MENU";
                break;
            }
            case 2: {
                menuid = "racecheckin03MENU";
                break;
            }
            case 3: {
                menuid = "racecheckin02MENU";
                break;
            }
            case 4: {
                menuid = "racecheckin01MENU";
            }
        }
        BannerMenu bmenu = new BannerMenu(menuid);
        bmenu.s_raceid = raceid;
        bmenu.isCountDown = true;
        bmenu.s_start_date = date;
        bmenu.s_controlgroup = "STELLA Race" + Converts.bigRaceSuffixes(raceid) + " - Check-IN IN";
        return menues.createSimpleMenu(bmenu, 1.0E8, "", 1600, 1200, 600, 150, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateStellaStartIn(int raceid) {
        String menuid = "";
        switch (raceid) {
            case 1: {
                menuid = "racestartin04MENU";
                break;
            }
            case 2: {
                menuid = "racestartin03MENU";
                break;
            }
            case 3: {
                menuid = "racestartin02MENU";
                break;
            }
            case 4: {
                menuid = "racestartin01MENU";
            }
        }
        BannerMenu bmenu = new BannerMenu(menuid);
        bmenu.s_raceid = raceid;
        bmenu.isCountDown = true;
        bmenu.s_controlgroup = "STELLA Race" + Converts.bigRaceSuffixes(raceid) + " - Start IN";
        return menues.createSimpleMenu(bmenu, 1.0E8, "", 1600, 1200, 600, 150, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateStellaPrepareToOrders() {
        BannerMenu bmenu = new BannerMenu("stellaPreparingToOrdersMENU");
        bmenu.isCountDown = true;
        bmenu.s_controlgroup = "STELLA - PREPARING TO ORDERS";
        return menues.createSimpleMenu(bmenu, 1.0E8, "", 1600, 1200, 600, 150, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateStellaPrepareToRace() {
        BannerMenu bmenu = new BannerMenu("stellaPreparingToRaceMENU");
        bmenu.isCountDown = true;
        bmenu.s_controlgroup = "STELLA - PREPARING TO RACE";
        return menues.createSimpleMenu(bmenu, 1.0E8, "", 1600, 1200, 600, 150, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateStellaWelcome(String cityname) {
        BannerMenu bmenu = new BannerMenu("stellaWilcomMENU");
        bmenu.s_controlgroup = "STELLA - WELCOME";
        bmenu.s_cityname = cityname;
        return menues.createSimpleMenu(bmenu, 1.0E8, "", 1600, 1200, 600, 150, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long CreateBannerMenu(int raceid, String start, String finish, String shortRacename, String logoname, int winmoney, double rating, TimeData time, DateData date) {
        String menuid = "";
        String grouop_suffix = "";
        switch (raceid) {
            case 1: {
                menuid = "banner04MENU";
                grouop_suffix = "04";
                break;
            }
            case 2: {
                menuid = "banner03MENU";
                grouop_suffix = "03";
                break;
            }
            case 3: {
                menuid = "banner02MENU";
                grouop_suffix = "02";
                break;
            }
            case 4: {
                menuid = "banner01MENU";
                grouop_suffix = "01";
            }
        }
        BannerMenu bmenu = new BannerMenu(menuid);
        bmenu.race_name = loc.getBigraceFullName(shortRacename);
        bmenu.need_race_name = true;
        bmenu.s_raceid = raceid;
        bmenu.s_start = start;
        bmenu.s_time = time;
        bmenu.s_date = date;
        bmenu.s_race_logo_id = logoname;
        bmenu.s_controlgroup = "BANNER Race" + grouop_suffix;
        return menues.createSimpleMenu(bmenu, 1.0E8, "", 1600, 1200, 600, 300, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void AfterInitMenu(long _menu) {
        long race_logo = menues.FindFieldInMenu(_menu, LOGO_FIELD);
        if (race_logo != 0L) {
            if (this.s_race_logo_id != null) {
                menues.SetShowField(race_logo, true);
                IconMappings.remapSmallRaceLogos(this.s_race_logo_id, race_logo);
            } else {
                menues.SetShowField(race_logo, false);
            }
        }
        this.ClearStatic2();
        menues.WindowSet_ShowCursor(_menu, false);
        menues.SetStopWorld(_menu, false);
    }

    public void exitMenu(long _menu) {
        if (this.m_animid != -1) {
            menues.StopScriptAnimation(this.m_animid);
        }
    }

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        MENUText_field text;
        this.common = new Common(_menu);
        long[] testcontrols = menues.InitXml(this.common.GetMenu(), Common.ConstructPath("menu_banner.xml"), this.s_controlgroup);
        if (testcontrols.length == 0) {
            if (this.s_controlgroup == "BANNER Race" + Converts.bigRaceSuffixes(this.s_raceid)) {
                this.s_raceid = 0;
                this.s_controlgroup = "BANNER Race" + Converts.bigRaceSuffixes(this.s_raceid);
                menues.InitXml(this.common.GetMenu(), Common.ConstructPath("menu_banner.xml"), this.s_controlgroup);
            } else {
                return;
            }
        }
        if (this.s_start != null && this.s_date != null) {
            KeyPair[] a1 = new KeyPair[]{new KeyPair("START", this.s_start), new KeyPair("DATE", (this.s_date.month > 9 ? "" + this.s_date.month : "0" + this.s_date.month) + "/" + (this.s_date.day > 9 ? "" + this.s_date.day : "0" + this.s_date.day))};
            MacroKit.ApplyToTextfield(this.common.FindTextField("CitiesNames"), a1);
        }
        if (this.need_race_name) {
            long field = menues.FindFieldInMenu(_menu, FIELD_RACE_NAME);
            menues.SetFieldText(field, this.race_name);
        }
        if (this.s_rating != -1.0) {
            KeyPair[] a2 = new KeyPair[]{new KeyPair("RATING", this.s_rating + "")};
            MacroKit.ApplyToTextfield(this.common.FindTextField("ParticipationRating"), a2);
        }
        if (this.s_cityname != null) {
            KeyPair[] a = new KeyPair[]{new KeyPair("CITY", this.s_cityname)};
            MacroKit.ApplyToTextfield(this.common.FindTextField("To The City"), a);
        }
        if (this.isCountDown) {
            this.m_cdownfield = this.common.FindTextField("Time");
        }
        if (this.s_start_date != null && (text = this.common.FindTextField("Text - Start Date and Time")) != null && text.text != null) {
            if (text.origtext == null) {
                text.origtext = text.text;
            }
            text.text = Converts.ConvertDateAbsolute(text.origtext, this.s_start_date.gMonth(), this.s_start_date.gDate(), this.s_start_date.gYear(), this.s_start_date.gHour(), this.s_start_date.gMinute());
            menues.UpdateField(text);
        }
        this.ClearStatic();
    }

    void setTime(double T) {
        if (null == this.m_cdownfield) {
            return;
        }
        int hours = (int)Math.floor(T / 3600.0);
        int minutes = (int)Math.floor((T - (double)hours * 3600.0) / 60.0);
        int seconds = (int)Math.floor(T - (double)hours * 3600.0 - (double)minutes * 60.0);
        Converts.ConverTime3Plus2(this.m_cdownfield, hours, minutes, seconds);
    }

    public void ClearStatic() {
        this.s_raceid = -1;
        this.s_start = null;
        this.s_finish = null;
        this.s_rating = -1.0;
        this.s_time = null;
        this.s_date = null;
        this.s_start_date = null;
    }

    public void ClearStatic2() {
        this.s_race_logo_id = null;
    }
}

