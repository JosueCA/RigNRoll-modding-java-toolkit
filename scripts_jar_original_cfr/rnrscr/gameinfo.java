/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import gameobj.CarParts;
import gameobj.WHOrderInfo;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Vector;
import menu.CInteger;
import menu.DateData;
import menuscript.OfficeGameData;
import menuscript.mainmenu.VehicleDLCTextureInfo;
import rnrcore.CoreTime;
import rnrcore.eng;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class gameinfo {
    public static gameinfo script = null;
    public int m_StartMonth = 5;
    public int m_StartYear = 2012;
    public Vector CurrenWareHouseOrders;
    public Vector aWarehouses;
    public int m_CurrentWarehouse;
    public int m_CurrentOrder;
    public int m_HighlightedOrder;
    public int m_iStartLitres;
    public int m_iMaxLitres;
    public int m_iPricePerLitre;
    public int m_iCurBought;
    public int m_iMaxBought;
    public CarParts m_CarParts;
    public int m_iRepairTableVisSize;
    public int m_iCurScroll;
    public String m_sVehType;
    public String m_sVehModelInternal;
    public String m_sVehManufactLocalized;
    public String m_sVehModelLocalized;
    public String m_sTruckName;
    public int m_iTruckInstance;
    public long m_pVehiclePointer;
    public long m_pVehicleActorPointer;
    public int m_iVehicleMainColor;
    public int m_iVehicleDLCTexture;
    public int m_iVehicleLeather;
    public int m_iVehicleCloth;
    public int m_iVehicleDash;
    public int m_iVehicleDashGauges;
    public int m_iVehicleGlasses;
    public boolean m_bVehicleMettalic;
    public Vector m_RestyleBodyColorPricelist = new Vector();
    public Vector m_RestyleBodyColorMetallicPricelist = new Vector();
    public Vector m_RestyleBodyColorChameleonPricelist = new Vector();
    public Vector m_RestyleBodyColorMetallicChameleonPricelist = new Vector();
    public Vector m_RestyleLeatherPricelist = new Vector();
    public Vector m_RestyleClothPricelist = new Vector();
    public Vector m_RestyleDashPricelist = new Vector();
    public Vector m_RestyleDashGaugesPricelist = new Vector();
    public Vector m_RestyleGlassesPricelist = new Vector();
    public Vector m_userTextures = new Vector();
    public OfficeGameData m_GameData;
    public MotelData m_moteldata;
    public long m_pPlayer;
    public int m_iTotalAuth;
    public int m_iTestMenu;
    public cCreateWindowDispatch CW_info;

    public gameinfo() {
        script = this;
        this.FirstFrame();
    }

    public static String ConvertMoney(int value) {
        if (value < 0) {
            return "-$ " + gameinfo.ConvertNumeric(-value);
        }
        return "$ " + gameinfo.ConvertNumeric(value);
    }

    public static String ConvertDouble(double value, int precision) {
        if (precision == 0) {
            return (int)value + "";
        }
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < precision; ++i) {
            buf.append("#");
        }
        MessageFormat mf = new MessageFormat("{0,number,#." + buf.toString() + "}");
        Object[] objs = new Object[]{new Double(value)};
        return mf.format(objs);
    }

    public static String ConvertTime(int hour, int min) {
        return (hour >= 10 ? hour + "" : "0" + hour) + ":" + (min >= 10 ? min + "" : "0" + min);
    }

    public static String ConvertSpeed(double mph) {
        return gameinfo.ConvertDouble(mph, 2) + " mph";
    }

    public static String ConvertTime(int hour, int min, int sec) {
        return (hour >= 10 ? hour + "" : "0" + hour) + ":" + (min >= 10 ? min + "" : "0" + min) + ":" + (sec >= 10 ? sec + "" : "0" + sec);
    }

    public static String ConvertNumeric(int value) {
        StringBuffer start = new StringBuffer("" + value);
        int len = start.length();
        int i = 1;
        while (value / 1000 > 0) {
            start.insert(len - i * 3, ' ');
            value /= 1000;
            ++i;
        }
        return start.toString();
    }

    public static String ConvertCelcius(double value) {
        return (value >= 0.0 ? "+" : "-") + gameinfo.ConvertDouble(value, 0) + "@C";
    }

    public static String ConvertFahrenheit(double value) {
        return (value >= 0.0 ? "+" : "-") + gameinfo.ConvertDouble(value * 9.0 / 5.0 + 32.0, 0) + "@F";
    }

    public static String ConvertMonth(int month) {
        switch (month - 1) {
            case 0: {
                return "January";
            }
            case 1: {
                return "February";
            }
            case 2: {
                return "March";
            }
            case 3: {
                return "April";
            }
            case 4: {
                return "May";
            }
            case 5: {
                return "June";
            }
            case 6: {
                return "July";
            }
            case 7: {
                return "August";
            }
            case 8: {
                return "September";
            }
            case 9: {
                return "October";
            }
            case 10: {
                return "November";
            }
            case 11: {
                return "December";
            }
        }
        return null;
    }

    public static String ConvertFullDate(DateData d) {
        return gameinfo.ConvertMonth(d.month) + " " + d.day + ", " + d.year;
    }

    public static String ConvertBriefDate(DateData d) {
        return gameinfo.ConvertMonth(d.month) + " " + d.day;
    }

    public static String ConvertWeight(double weight) {
        return gameinfo.ConvertDouble(weight, 3) + " ton";
    }

    public static String ConvertDistance(double distance) {
        return gameinfo.ConvertDouble(distance + 0.5, 0) + ((int)(distance + 0.5) == 1 ? " mile" : " miles");
    }

    public void FirstFrame() {
        this.CurrenWareHouseOrders = new Vector();
        this.aWarehouses = new Vector();
        this.m_CarParts = new CarParts();
        this.m_moteldata = new MotelData();
        this.m_CurrentWarehouse = -1;
        this.m_CurrentOrder = -1;
        this.m_iStartLitres = 10;
        this.m_iMaxLitres = 100;
        this.m_iPricePerLitre = 234;
        this.CW_info = new cCreateWindowDispatch();
    }

    public int GetSelectedOrder() {
        return this.m_CurrentOrder;
    }

    public WHOrderInfo GetSelectetOrder() {
        for (Object obj : this.CurrenWareHouseOrders) {
            WHOrderInfo order = (WHOrderInfo)obj;
            if (order.WH_slot_ID != this.m_CurrentOrder) continue;
            return order;
        }
        return null;
    }

    public void LoadRepairPrices() {
        Node top = XmlUtils.parse("..\\data\\config\\restyle_prices.xml");
        NodeList root = top != null ? top.getNamedChildren("types") : null;
        ArrayList part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("body") : null;
        Node node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        String str_price = node != null ? node.getAttribute("price") : "0";
        CInteger price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("body_metallic") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorMetallicPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("body_chameleon") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorChameleonPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("body_metallic_chameleon") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorMetallicChameleonPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("leather") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleLeatherPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("cloth") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleClothPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("dash") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleDashPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("dash_gauges") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleDashGaugesPricelist.add(price);
        }
        part = root != null && !root.isEmpty() ? ((Node)root.get(0)).getNamedChildren("glasses") : null;
        node = part != null && !part.isEmpty() ? (Node)part.get(0) : null;
        str_price = node != null ? node.getAttribute("price") : "0";
        price = new CInteger(0);
        try {
            price.value = Integer.parseInt(str_price);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }
        for (int i = 0; i < 30; ++i) {
            this.m_RestyleGlassesPricelist.add(price);
        }
    }

    public Vector<VehicleDLCTextureInfo> getVehicleDLCTexturesInfo() {
        Vector<VehicleDLCTextureInfo> result = new Vector<VehicleDLCTextureInfo>();
        for (Object obj : this.m_userTextures) {
            if (!(obj instanceof UserTextureInfo)) continue;
            UserTextureInfo textureInfo = (UserTextureInfo)obj;
            VehicleDLCTextureInfo info = new VehicleDLCTextureInfo();
            info.textureId = textureInfo.textureId;
            info.textureName = textureInfo.textureName;
            result.add(info);
        }
        return result;
    }

    public class OneTitrInfo {
        public float time_to_show = 0.0f;
        public float time_elapsed = 0.0f;
        public int nom_shifts = 1;
        public long p_menu = 0L;
    }

    public class cCreateWindowDispatch {
        public boolean OnSpecObject = false;
        public boolean InsideBar = false;
        public boolean Titre = false;
        public Vector strings = new Vector();
        public String menuId;

        public void ClearAll() {
            this.strings.clear();
            this.OnSpecObject = false;
            this.InsideBar = false;
            this.Titre = false;
        }

        public void AddString(String str) {
            this.strings.add(str);
        }
    }

    public class MotelData {
        public int iCheckInHour = 10;
        public int iCheckInMin = 12;
        public int iCheckOutHour = 10;
        public int iCheckOutMin = 12;
        public double dCostPerHour = 20.0;
        public CoreTime current_date = new CoreTime(1, 1, 1, 1, 1);
        public boolean bEnoughtMoney = true;
        public String city_name = "Unknow";
    }

    public static class UserTextureInfo {
        String textureName;
        int textureId;
    }

    public class Warehouse {
        public String name;
        public double x;
        public double y;
        public int ID;
        public int arrowindex;
        public boolean bIsMine;
    }
}

