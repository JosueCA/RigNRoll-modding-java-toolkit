/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import gameobj.WarehouseInfo;
import gameobj.WeatherPoint;
import java.nio.ByteBuffer;
import java.util.HashSet;
import menu.BaseMenu;
import menu.Common;
import menu.DateData;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RNRMap;
import menu.SelectCb;
import menu.Table;
import menu.TableCallbacks;
import menu.TableCmp;
import menu.TableNode;
import menu.menues;
import menuscript.Converts;
import menuscript.org.IOrgTab;
import menuscript.org.OrganiserMenu;
import menuscript.org.WeatherData;

public class OrganizerWeather
extends BaseMenu
implements IOrgTab,
TableCallbacks,
SelectCb {
    OrganiserMenu parent = null;
    WeatherData m_wdata = new WeatherData();
    MENUText_field[] m_4dayTextFields = new MENUText_field[8];
    MENUsimplebutton_field[] m_4dayIcons = new MENUsimplebutton_field[24];
    MENUbutton_field[] m_daybuttons = new MENUbutton_field[4];
    Table m_WhTable;
    String[] locFarenheitGraduses = new String[2];
    int m_curwh = 0;
    int m_4day = 0;
    RNRMap m_map;
    int[] m_wiconids;
    ByteBuffer m_convertbuffer;
    boolean m_hideflag = true;
    HashSet m_hidenames;

    public OrganizerWeather(long _menu, OrganiserMenu parent) {
        this.parent = parent;
        this.uiTools = new Common(_menu);
        this.InitMenu();
        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - WEATHER");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    void InitMenu() {
        long _menu = this.uiTools.GetMenu();
        this.m_WhTable = new Table(_menu, "Weather whs");
        this.m_WhTable.AddRadioButton("Weather - City", 0);
        this.m_WhTable.AddEvent(2);
        this.m_WhTable.Setup(38L, 20, Common.ConstructPath("menu_com.xml"), "CB RADIO - WEATHER", "TABLEGROUP Weather - City - 19 38", this, 10);
        this.m_WhTable.AttachRanger(this.uiTools.FindScroller("Tableranger - Weather - City"));
        this.m_map = this.uiTools.FindRNRMapByParent("MAP - zooming picture", "Weather - MAP");
        this.m_daybuttons[0] = this.uiTools.FindRadioButton("BUTTON - TODAY");
        this.m_daybuttons[0].userid = 0;
        menues.UpdateField(this.m_daybuttons[0]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[0], this, "On4DaySwitch", 2L);
        this.m_daybuttons[1] = this.uiTools.FindRadioButton("BUTTON - 2 DAY");
        this.m_daybuttons[1].userid = 1;
        menues.UpdateField(this.m_daybuttons[1]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[1], this, "On4DaySwitch", 2L);
        this.m_daybuttons[2] = this.uiTools.FindRadioButton("BUTTON - 3 DAY");
        this.m_daybuttons[2].userid = 2;
        menues.UpdateField(this.m_daybuttons[2]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[2], this, "On4DaySwitch", 2L);
        this.m_daybuttons[3] = this.uiTools.FindRadioButton("BUTTON - 4 DAY");
        this.m_daybuttons[3].userid = 3;
        menues.UpdateField(this.m_daybuttons[3]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[3], this, "On4DaySwitch", 2L);
        this.m_wdata.Init();
        this.m_map = this.uiTools.FindRNRMap("MAP - zooming picture");
        this.uiTools.SetScriptOnButton("BUTTON - CITY", this, "OnCitySort");
        this.InitIcons();
        this.InitHideMap();
        this.m_convertbuffer = ByteBuffer.allocate(4);
    }

    void InitIcons() {
        String[] iconnames = new String[]{"4DayForecast - BUTT - SUNNY", "4DayForecast - BUTT - MOSTLY CLEAR", "4DayForecast - BUTT - CLOUDY", "4DayForecast - BUTT - RAINY", "4DayForecast - BUTT - SNOWY", "4DayForecast - BUTT - STORMY"};
        String[] textnames = new String[]{"4DayForecast - Temperature - F VALUE", "4DayForecast - Temperature - C VALUE"};
        String[] parents = new String[]{"GROUP Today", "GROUP 2 DAY", "GROUP 3 DAY", "GROUP 4 DAY"};
        for (int i = 0; i < 4; ++i) {
            long control;
            int ic;
            for (ic = 0; ic < iconnames.length; ++ic) {
                control = menues.InitXmlControl(this.uiTools.GetMenu(), Common.ConstructPath("menu_com.xml"), "CB RADIO - WEATHER", iconnames[ic]);
                MENUsimplebutton_field b = menues.ConvertSimpleButton(control);
                b.parentName = parents[i];
                menues.UpdateField(b);
                this.m_4dayIcons[i * iconnames.length + ic] = b;
            }
            for (ic = 0; ic < textnames.length; ++ic) {
                control = menues.InitXmlControl(this.uiTools.GetMenu(), Common.ConstructPath("menu_com.xml"), "CB RADIO - WEATHER", textnames[ic]);
                MENUText_field text = menues.ConvertTextFields(control);
                this.locFarenheitGraduses[ic] = text.text;
                text.parentName = parents[i];
                menues.UpdateField(text);
                this.m_4dayTextFields[i * textnames.length + ic] = text;
            }
        }
    }

    void UpdateIcons() {
        for (int i = 0; i < 4; ++i) {
            WeatherPoint wpoint = this.m_wdata.GetWPoint(i, false, this.m_curwh);
            double farenheit = Converts.ConvertFahrenheit(wpoint.temp);
            KeyPair[] macro = new KeyPair[]{new KeyPair("SIGN", farenheit >= 0.0 ? "+" : "-"), new KeyPair("TEMPERATURE_F", Converts.ConvertDouble(Math.abs(farenheit), 0))};
            this.m_4dayTextFields[i * 2 + 0].text = MacroKit.Parse(this.locFarenheitGraduses[0], macro);
            menues.UpdateField(this.m_4dayTextFields[i * 2 + 0]);
            macro[0] = new KeyPair("SIGN", wpoint.temp >= 0.0 ? "+" : "-");
            macro[1] = new KeyPair("TEMPERATURE_C", Converts.ConvertDouble(Math.abs(wpoint.temp), 0));
            this.m_4dayTextFields[i * 2 + 1].text = MacroKit.Parse(this.locFarenheitGraduses[1], macro);
            menues.UpdateField(this.m_4dayTextFields[i * 2 + 1]);
            int wstate = wpoint.GetState();
            for (int ic = 0; ic < 6; ++ic) {
                menues.SetShowField(this.m_4dayIcons[i * 6 + ic].nativePointer, ic == wstate);
            }
        }
    }

    void FullRefreshMap(WeatherData d) {
        this.m_map.ClearObjects();
        this.m_map.SetClickableGroup(25, true);
        this.m_map.SetClickableGroup(26, true);
        this.m_wiconids = new int[d.m_warehouses.size()];
        for (int i = 0; i < d.m_warehouses.size(); ++i) {
            WarehouseInfo w = (WarehouseInfo)d.m_warehouses.get(i);
            w.ID = w.bIsMine ? this.m_map.AddObject(26, this.m_WhTable.GetNodeByLine(i), w.mapx, w.mapy, w.name, "") : this.m_map.AddObject(25, this.m_WhTable.GetNodeByLine(i), w.mapx, w.mapy, w.name, "");
            WeatherPoint p = this.m_wdata.GetWPoint(0, false, i);
            int id = Common.GetID();
            KeyPair[] macro = new KeyPair[2];
            double farenheit = Converts.ConvertFahrenheit(p.temp);
            macro[0] = new KeyPair("SIGN", farenheit >= 0.0 ? "+" : "-");
            macro[1] = new KeyPair("TEMPERATURE_F", Converts.ConvertDouble(Math.abs(farenheit), 0));
            this.m_map.AddMapObject(16 + p.GetState(), w.mapx, w.mapy, false, false, id, MacroKit.Parse(this.locFarenheitGraduses[0], macro), "");
            this.m_wiconids[i] = id;
        }
        this.m_map.AttachCallback(this.uiTools, this);
        this.ShowHideMap(true);
    }

    void UpdateMap() {
        for (int i = 0; i < this.m_wdata.m_warehouses.size(); ++i) {
            WeatherPoint wpoint = this.m_wdata.GetWPoint(this.m_4day, false, i);
            this.m_map.SetObjectType(this.m_wiconids[i], wpoint.GetState() + 16);
            KeyPair[] macro = new KeyPair[2];
            double farenheit = Converts.ConvertFahrenheit(wpoint.temp);
            macro[0] = new KeyPair("SIGN", farenheit >= 0.0 ? "+" : "-");
            macro[1] = new KeyPair("TEMPERATURE_F", Converts.ConvertDouble(Math.abs(farenheit), 0));
            this.m_map.SetObjectText(this.m_wiconids[i], MacroKit.Parse(this.locFarenheitGraduses[0], macro));
        }
    }

    void AfterInitMenu() {
        menues.SetIgnoreEvents(this.uiTools.FindSimpleButton((String)"CommWeatherMapBorder_01").nativePointer, true);
        menues.SetIgnoreEvents(this.uiTools.FindSimpleButton((String)"CommWeatherMapBorder_02").nativePointer, true);
        this.m_map.AttachStandardControls(this.uiTools, "Weather - MAP - Shift Buttons", "Weather - MAP - Interface");
        menues.SetScriptOnControlDataPass(this.uiTools.GetMenu(), this.m_map, this, "OnMapZoom", 327676L);
    }

    void OnMapZoom(long _menu, RNRMap map, long data) {
        boolean newhideflag;
        this.m_convertbuffer.clear();
        this.m_convertbuffer.putInt((int)data);
        this.m_convertbuffer.rewind();
        float zoom = this.m_convertbuffer.getFloat();
        boolean bl = newhideflag = zoom <= 1.0f * RNRMap.ALPHA + 0.01f;
        if (this.m_hideflag != newhideflag) {
            this.ShowHideMap(newhideflag);
        }
    }

    void InitHideMap() {
        this.m_hidenames = new HashSet();
        this.m_hidenames.add("WV");
        this.m_hidenames.add("MW");
        this.m_hidenames.add("KM");
        this.m_hidenames.add("MD");
        this.m_hidenames.add("PS");
        this.m_hidenames.add("PD");
        this.m_hidenames.add("KM");
        this.m_hidenames.add("TM");
        this.m_hidenames.add("SC");
        this.m_hidenames.add("ST");
        this.m_hidenames.add("MC");
        this.m_hidenames.add("LB");
    }

    void ShowHideMap(boolean hide) {
        for (int i = 0; i < this.m_wdata.m_warehouses.size(); ++i) {
            WarehouseInfo w = (WarehouseInfo)this.m_wdata.m_warehouses.get(i);
            if (!this.m_hidenames.contains(w.idname)) continue;
            if (hide) {
                this.m_map.HideMapObject(w.ID);
                this.m_map.HideMapObject(this.m_wiconids[i]);
                continue;
            }
            this.m_map.ShowMapObject(w.ID);
            this.m_map.ShowMapObject(this.m_wiconids[i]);
        }
        this.m_hideflag = hide;
    }

    void SetDayButtonsState() {
        for (int i = 0; i < this.m_daybuttons.length; ++i) {
            menues.SetFieldState(this.m_daybuttons[i].nativePointer, i == this.m_4day ? 1 : 0);
        }
    }

    void On4DaySwitch(long _menu, MENUbutton_field radio) {
        if (radio.userid == this.m_4day) {
            menues.SetFieldState(this.m_daybuttons[this.m_4day].nativePointer, 1);
            return;
        }
        this.m_4day = radio.userid;
        this.SetDayButtonsState();
        this.UpdateMap();
    }

    void Refresh() {
        JavaEvents.SendEvent(7, 0, this);
        this.SetDayButtonsState();
        WeatherData d = this.m_wdata;
        for (int i = 0; i < d.m_warehouses.size(); ++i) {
            WarehouseInfo wh = (WarehouseInfo)d.m_warehouses.get(i);
            wh.handle = i;
            this.m_WhTable.AddItem(null, wh, false);
        }
        this.m_WhTable.RefillTree();
        this.FullRefreshMap(this.m_wdata);
        this.UpdateWHInfo();
        DateData dau2 = (DateData)this.m_wdata.m_dates.get(2);
        Converts.ConvertDate(this.uiTools.FindRadioButton("BUTTON - 3 DAY"), dau2.month, dau2.day, dau2.year);
        DateData dau3 = (DateData)this.m_wdata.m_dates.get(3);
        Converts.ConvertDate(this.uiTools.FindRadioButton("BUTTON - 4 DAY"), dau3.month, dau3.day, dau3.year);
    }

    void UpdateWHInfo() {
        int currentwh;
        TableNode n = this.m_WhTable.GetSingleChecked();
        WarehouseInfo whinfo = (WarehouseInfo)n.item;
        if (whinfo == null) {
            return;
        }
        for (int i = 0; i < this.m_wdata.m_warehouses.size(); ++i) {
            WarehouseInfo w = (WarehouseInfo)this.m_wdata.m_warehouses.get(i);
            this.m_map.SelectMapObject(w.ID, false);
        }
        this.m_curwh = currentwh = (int)whinfo.handle;
        this.m_map.SelectMapObject(whinfo.ID, true);
        this.UpdateIcons();
    }

    void OnCitySort(long _menu, MENUsimplebutton_field b) {
        this.m_WhTable.SortTable(0, new WarehouseSorter());
        this.UpdateWHInfo();
        this.UpdateMap();
    }

    public void OnSelect(int state, Object sender) {
        TableNode node = (TableNode)sender;
        this.m_WhTable.Check(node);
        this.UpdateWHInfo();
        this.UpdateMap();
    }

    public void SetupLineInTable(TableNode node, MENUText_field text) {
    }

    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
    }

    public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
        WarehouseInfo wh = (WarehouseInfo)node.item;
        radio.text = wh.name;
        menues.SetFieldState(radio.nativePointer, node.checked ? 1 : 0);
    }

    public void SetupLineInTable(int type, TableNode node, Object control) {
    }

    public void OnEvent(long event2, TableNode node, long group, long _menu) {
        this.m_WhTable.Check(node);
        this.UpdateWHInfo();
        this.UpdateMap();
    }

    public void afterInit() {
        this.AfterInitMenu();
    }

    public void exitMenu() {
        if (this.m_WhTable != null) {
            this.m_WhTable.DeInit();
        }
    }

    public void enterFocus() {
        this.Refresh();
    }

    public void leaveFocus() {
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(2, 0);
        }
    }

    public static class WarehouseSorter
    extends TableCmp {
        public int compare(Object o1, Object o2) {
            WarehouseInfo w1 = (WarehouseInfo)o1;
            WarehouseInfo w2 = (WarehouseInfo)o2;
            return Common.Compare(w1.name, w2.name, this.order);
        }
    }
}

