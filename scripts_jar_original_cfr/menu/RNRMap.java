/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import menu.Common;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.SelectCb;
import menu.menues;

public class RNRMap {
    public static final int CB_MAPZOOM = 327676;
    long nativePointer;
    String nameID;
    String text;
    int ID;
    int userid;
    int Xres;
    int Yres;
    int poy;
    int pox;
    int leny;
    int lenx;
    Vector textures;
    Vector materials;
    Vector callbacks;
    String parentName;
    String parentType;
    public static final int ROAD_ICON0 = 0;
    public static final int ROAD_ICON1 = 1;
    public static final int ROAD_ICON2 = 2;
    public static final int WAREHOUSE = 3;
    public static final int GAMER_WAREHOUSE = 4;
    public static final int OFFICE_WAREHOUSE = 5;
    public static final int WAREHOUSE_ORDER = 6;
    public static final int EMPLOYEE_ORDER = 7;
    public static final int EMPLOYEE_TASK = 8;
    public static final int EMPLOYEE_MIGHTY = 9;
    public static final int EMPLOYEE = 10;
    public static final int OFFICE = 11;
    public static final int OFFICE_FOR_SALE = 12;
    public static final int REPAIR = 13;
    public static final int DEALER = 14;
    public static final int DESTINATION = 15;
    public static final int WEATHER_SUNNY = 16;
    public static final int WEATHER_MOSTLY_CLEAR = 17;
    public static final int WEATHER_CLOUDY = 18;
    public static final int WEATHER_RAIN = 19;
    public static final int WEATHER_THUNDER = 20;
    public static final int WEATHER_SNOW = 21;
    public static final int WEATHER_TEMPERARURE = 22;
    public static final int WEATHER_MOONCLEAR = 23;
    public static final int WEATHER_MOONCLOUDY = 24;
    public static final int WEATHER_WAREHOUSE = 25;
    public static final int WEATHER_WAREHOUSE_MY = 26;
    public static final int MAP_ORG_PLAYER_CAR = 27;
    public static final int MAP_ORG_TASK_CURRENT = 28;
    public static final int MAP_ORG_TASK_CURRENT_ICON = 29;
    public static final int MAP_ORG_TASK = 30;
    public static final int MAP_ORG_TASK_ICON = 31;
    public static final int MAP_ORG_DESTINATION_CURRENT = 32;
    public static final int MAP_ORG_DESTINATION = 33;
    public static final int MAP_ORG_SEMITRAILER = 34;
    public static final int MAP_ORG_PASSANGER = 35;
    public static final int MAP_ORG_SENDING = 36;
    public static final int OBJECT_MAX = 37;
    public static final int STATE_NONE = 0;
    public static final int STATE_DRAG = 1;
    public static final int STATE_ZOOM = 2;
    public static final int STATE_RAMKA = 3;
    public static final float MIN_ZOOM = 1.0f;
    public static final float MAX_ZOOM = 12.0f;
    public static final float START_X = 1.5f;
    public static final float START_Y = 1.5f;
    public static final int SCALE_NUM = 10;
    public static final float ALPHA = (float)Math.pow(12.0, 0.1111111119389534);
    public static final int MAP_START = 0;
    public static final int MAP_LEFT = 0;
    public static final int MAP_RIGHT = 1;
    public static final int MAP_UP = 2;
    public static final int MAP_DOWN = 3;
    public static final int MAP_ZOOMOUT = 4;
    public static final int MAP_ZOOMIN = 5;
    public static final int MAP_MAX = 6;
    SelectCb cb;
    HashMap objs = new HashMap();
    MENUbutton_field hand;
    MENUbutton_field select;

    public void ClearObjects() {
        for (Map.Entry entry : this.objs.entrySet()) {
            Integer ID = (Integer)entry.getKey();
            this.RemoveMapObject(ID);
        }
        this.objs.clear();
        this.RemoveAllObjects();
    }

    public int AddObject(int type, Object obj, float x, float y, String text, String tips) {
        int id = Common.GetID();
        this.AddMapObject(type, x, y, false, false, id, text, tips);
        this.objs.put(new Integer(id), obj);
        return id;
    }

    public int AddOrder(int type, Object obj, int startID, int endID, String tips) {
        int id = Common.GetID();
        this.AddOrder(type, startID, endID, false, false, id, tips);
        this.objs.put(new Integer(id), obj);
        return id;
    }

    public void AttachCallback(Common common, SelectCb cb) {
        this.cb = cb;
        menues.UpdateMenuField(this);
        menues.SetScriptOnControlDataPass(common.GetMenu(), this, this, "OnMapClick", 7L);
    }

    public void SetStartPosition() {
        this.SetMaxZoom(12.0f);
        this.SetMinZoom(1.0f);
        this.Move(1.5f, 1.5f);
    }

    public void AttachStandardControls(Common common, String parentname) {
        this.AttachStandardControls(common, parentname, parentname);
    }

    public void AttachStandardControls(Common common, String parentname, String parent1name) {
        MENUsimplebutton_field button;
        int i;
        this.SetStartPosition();
        String[] names = new String[]{"MAP shiftLEFT", "MAP shiftRIGHT", "MAP shiftUP", "MAP shiftDOWN", "MAP zoomMINUS", "MAP zoomPLUS"};
        String[] cbnames = new String[]{"OnLeft", "OnRight", "OnUp", "OnDown", "OnZoomOut", "OnZoomIn"};
        int parentswitch = 4;
        for (i = 0; i < 6; ++i) {
            button = common.FindSimpleButtonByParent(names[i], i >= parentswitch ? parent1name : parentname);
            button.userid = this.userid;
            menues.UpdateField(button);
            menues.SetScriptOnControl(common.GetMenu(), button, this, cbnames[i], 4L);
        }
        for (i = 0; i < 10; ++i) {
            button = common.FindSimpleButtonByParent("MAP zoomSCALE 0" + i, parent1name);
            button.userid = this.userid;
            menues.UpdateField(button);
            menues.SetScriptOnControl(common.GetMenu(), button, this, "OnZoomScale", 4L);
        }
        MENUbutton_field hand = common.FindRadioButtonByParent("MAP hand", parent1name);
        this.SetState(1);
        menues.SetFieldState(hand.nativePointer, 1);
        hand.userid = this.userid;
        menues.UpdateField(hand);
        MENUbutton_field select = common.FindRadioButtonByParent("MAP select", parent1name);
        menues.SetFieldState(select.nativePointer, 0);
        select.userid = this.userid;
        menues.UpdateField(select);
        this.select = select;
        this.hand = hand;
        menues.SetScriptOnControl(common.GetMenu(), hand, this, "OnHand", 2L);
        menues.SetScriptOnControl(common.GetMenu(), select, this, "OnSelect", 2L);
    }

    public void OnMapClick(long _menu, RNRMap map, long data) {
        this.cb.OnSelect(0, this.objs.get(new Integer((int)data)));
    }

    public void OnZoomIn(long _menu, MENUsimplebutton_field field) {
        this.ZoomIn();
    }

    public void OnZoomOut(long _menu, MENUsimplebutton_field field) {
        this.ZoomOut();
    }

    public void OnLeft(long _menu, MENUsimplebutton_field field) {
        this.MoveLeft();
    }

    public void OnRight(long _menu, MENUsimplebutton_field field) {
        this.MoveRight();
    }

    public void OnUp(long _menu, MENUsimplebutton_field field) {
        this.MoveUp();
    }

    public void OnDown(long _menu, MENUsimplebutton_field field) {
        this.MoveDown();
    }

    public void OnZoomScale(long _menu, MENUsimplebutton_field field) {
        int index = field.nameID.charAt(field.nameID.length() - 1) - 48;
        float minzoom = 1.0f;
        float zoom = minzoom * (float)Math.pow(ALPHA, index);
        this.Zoom(zoom);
    }

    public void OnSelect(long _menu, MENUbutton_field field) {
        if (this.GetState() != 3) {
            this.SetState(3);
        }
        menues.SetFieldState(this.select.nativePointer, 1);
        menues.SetFieldState(this.hand.nativePointer, 0);
    }

    public void OnHand(long _menu, MENUbutton_field field) {
        if (this.GetState() != 1) {
            this.SetState(1);
        }
        menues.SetFieldState(this.select.nativePointer, 0);
        menues.SetFieldState(this.hand.nativePointer, 1);
    }

    public native long Init(int var1, int var2, int var3, int var4, long var5);

    public native void AddMapObject(int var1, float var2, float var3, boolean var4, boolean var5, int var6, String var7, String var8);

    public native void AddOrder(int var1, int var2, int var3, boolean var4, boolean var5, int var6, String var7);

    public native void RemoveMapObject(int var1);

    public native void RemoveAllObjects();

    public native void ShowMapObject(int var1);

    public native void HideMapObject(int var1);

    public native void Zoom(float var1);

    public native void ZoomIn();

    public native void ZoomOut();

    public native void SetState(int var1);

    public native int GetState();

    public native boolean SelectMapObject(int var1, boolean var2);

    public native boolean HighlightMapObject(int var1, boolean var2);

    public native float GetMinZoom();

    public native float GetMaxZoom();

    public native float GetZoomStep();

    public native float GetXMoveStep();

    public native float GetYMoveStep();

    public native void SetMaxZoom(float var1);

    public native void SetMinZoom(float var1);

    public native void SetZoomStep(float var1);

    public native void SetXMoveStep(float var1);

    public native void SetYMoveStep(float var1);

    public native void MoveRight();

    public native void MoveLeft();

    public native void MoveUp();

    public native void MoveDown();

    public native void SetObjectType(int var1, int var2);

    public native void SetObjectText(int var1, String var2);

    public native void SetDefaultIconPicturePriority(int var1, Priority var2);

    public native void SetDefaultIconTextPriority(int var1, Priority var2);

    public native boolean SetMapObjectTextPriority(int var1, Priority var2);

    public native boolean SetMapObjectPicturePriority(int var1, Priority var2);

    public native void Move(float var1, float var2);

    public native void SetCallback(int var1, String var2, String var3);

    public native void SetClickableGroup(int var1, boolean var2);

    public native boolean GetClickableGroup(int var1);

    public native void makeDirectionsAsOne(int var1, int var2);

    public native void setActiveMapObject(int var1, boolean var2);

    public native void setPressedMapObject(int var1, boolean var2);

    public class Priority {
        int[][] priority = new int[3][4];

        public void SetPriority(int activestate, boolean highlighted, boolean selected, int value) {
            this.priority[activestate][(highlighted ? 2 : 0) + (selected ? 1 : 0)] = value;
        }

        public int GetPriority(int activestate, boolean highlighted, boolean selected, int value) {
            return this.priority[activestate][(highlighted ? 2 : 0) + (selected ? 1 : 0)];
        }
    }
}

