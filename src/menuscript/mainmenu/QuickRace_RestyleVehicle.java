/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.Vector;
import menu.ComboBox;
import menu.Common;
import menu.JavaEvents;
import menu.ListenerManager;
import menu.MENUTruckview;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MenuControls;
import menu.SelectCb;
import menu.menucreation;
import menu.menues;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.RestylingMenu_ExteriorTab;
import menuscript.mainmenu.RestylingMenu_ExteriorTabImplementation;
import menuscript.mainmenu.VehicleDLCTextureInfo;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class QuickRace_RestyleVehicle
implements SelectCb,
menucreation,
RestylingMenu_ExteriorTabImplementation {
    public Common common;
    MenuControls controls;
    private final RestylingMenu_ExteriorTab exteriorTab = new RestylingMenu_ExteriorTab(RestylingMenu_ExteriorTab.CalledFromMenu.QUICK_RACE_MENU, this);
    private static final int DLC_TEXTURE_COLOR = 6;
    private static final String XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String CONTROL_GROUP = "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE";
    private static final String VEHICLE_NAME = "Model - VALUE";
    private static final String VEHICLE_MANUFACTURER = "Make - VALUE";
    private static final String VEHICLE_TYPE = "Vehicle type - VALUE";
    private static final String[] BUTTONS = new String[]{"QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE OK", "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE DEFAULT", "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE Exit"};
    private static final String[] METHODS = new String[]{"OnOk", "OnDefault", "OnCancel"};
    MENUTruckview view_ext = null;
    MENUTruckview view_int = null;
    private static final String METALLICBUTTON = "METALLIC - Check BOX";
    private static final String CHAMELIONBUTTON = "CHAMELEON - Check BOX";
    private static final String CHAMELION_METALLIC_METHOD = "onColorChange";
    static final int BODY_ID = 0;
    static final int LEATHER_ID = 1;
    static final int DASH_ID = 2;
    static final int CLOTH_ID = 3;
    static final int GAUGES_ID = 4;
    static final int GLASSES_ID = 5;
    static final int MAX_ID = 6;
    static final int METALLIC_ID = 10;
    static final int DLC_BODY_ID = 20;
    ComboBox[] m_combobox = new ComboBox[6];
    static String[] s_xmltypenames = new String[]{"MainColor", "Leather", "Dash", "Cloth", "Gauges", "Glasses"};
    static String[] s_xmltypetitle = new String[]{"MAIN", "LEATHER", "DASH", "CLOTH", "GAUGES", "GLASSES"};
    static String[] s_mattypenames = new String[]{"color", "leather", "dash", "cloth", "gauges", "glass"};
    static int[] s_matnum = new int[]{6, 8, 6, 16, 8, 16};
    static int[] s_mattexturesize = new int[]{256, 256, 512, 512, 512, 512};
    static int[] s_numpos = new int[]{6, 8, 6, 16, 9, 16};
    private long control_metallic = 0L;
    private long control_chamelion = 0L;
    VehicleState vehicle_state = new VehicleState();
    PanelDialog parent = null;

    // @Override
    public void restartMenu(long _menu) {
    }

    public QuickRace_RestyleVehicle(int _num_tech, PanelDialog _parent) {
        this.vehicle_state.iTechNumber = _num_tech;
        this.parent = _parent;
        this.parent.setShow(false);
    }

    // @Override
    public void InitMenu(long _menu) {
        this.controls = new MenuControls(_menu, XML_NAME, CONTROL_GROUP);
        this._initMenu(_menu);
    }

    public void _initMenu(long _menu) {
        this.common = new Common(_menu);
        JavaEvents.SendEvent(65, 14, this.vehicle_state);
        this.control_chamelion = menues.FindFieldInMenu(_menu, CHAMELIONBUTTON);
        this.control_metallic = menues.FindFieldInMenu(_menu, METALLICBUTTON);
        for (int i = 0; i < this.m_combobox.length; ++i) {
            int numitems = s_matnum[i];
            String intext = i == 0 || i == 5 ? "Exterior" : "Interior";
            this.m_combobox[i] = new ComboBox(this.common, XML_NAME, "MenuMain RestyleVEHICLE - " + intext + " - " + s_xmltypenames[i], s_xmltypenames[i] + "00", "Border - " + s_xmltypenames[i] + " - JustForTest 8 positions", this.common.FindRadioButton("Restyle - " + s_xmltypenames[i]), this.common.FindSimpleButton(s_xmltypetitle[i] + " - PopUP button"), numitems, 38, 2, 3);
            this.m_combobox[i].AddListener(this);
            this.m_combobox[i].SetMaterial("mat_" + this.vehicle_state.selction_name + "_" + s_mattypenames[i] + "_110");
        }
        this.exteriorTab.initMenu(_menu);
    }

    public void InitRestyle(long _menu) {
        for (int type = 0; type < 6; ++type) {
            int state = 0;
            switch (type) {
                case 0: {
                    state = s_matnum[0] - 1 - this.vehicle_state.iMainColor;
                    break;
                }
                case 1: {
                    state = this.vehicle_state.iLeather;
                    break;
                }
                case 3: {
                    state = this.vehicle_state.iCloth;
                    break;
                }
                case 2: {
                    state = this.vehicle_state.iDash;
                    break;
                }
                case 4: {
                    state = this.vehicle_state.iDashGauges;
                    break;
                }
                case 5: {
                    state = this.vehicle_state.iGlass;
                }
            }
            menues.SetFieldState(this.common.FindRadioButton((String)new StringBuilder().append((String)"Restyle - ").append((String)QuickRace_RestyleVehicle.s_xmltypenames[type]).toString()).nativePointer, state);
            int num = s_matnum[type];
            int[] temp = new int[num];
            for (int i = 0; i < num; ++i) {
                temp[i] = i;
            }
            this.m_combobox[type].FillMappingData(1.0f / (float)num, temp);
        }
    }

    private void reciveMetallicChamelionFlags() {
        menues.SetFieldState(this.control_metallic, this.vehicle_state.isMetallic ? 1 : 0);
        menues.SetFieldState(this.control_chamelion, this.vehicle_state.isHamelion ? 1 : 0);
    }

    // @Override
    public void AfterInitMenu(long _menu) {
        ListenerManager.TriggerEvent(104);
        this.InitRestyle(this.common.GetMenu());
        this.reciveMetallicChamelionFlags();
        this.view_ext = this.common.FindTruckView("Exterior");
        this.view_ext.BindVehicle(this.vehicle_state.handle, 1, 8);
        this.view_int = this.common.FindTruckView("Interior");
        this.view_int.BindVehicle(this.vehicle_state.handle, 0, 0);
        this.exteriorTab.AfterInitMenu(_menu);
        this.reinitExteriorTab(_menu);
        this.RedrawRestyle();
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, BUTTONS[0])), this, METHODS[0], 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, BUTTONS[1])), this, METHODS[1], 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, BUTTONS[2])), this, METHODS[2], 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.control_chamelion), this, CHAMELION_METALLIC_METHOD, 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.control_metallic), this, CHAMELION_METALLIC_METHOD, 2L);
    }

    // @Override
    public int getTextureId() {
        return this.vehicle_state.iDLCTexture;
    }

    // @Override
    public void onLeftPressed() {
        this.vehicle_state.iMainColor = this.vehicle_state.iStoredBaseColor;
        this.vehicle_state.isHamelion = this.vehicle_state.iStoredIsHamelion;
        this.SelectColor(0, this.vehicle_state.iMainColor);
    }

    // @Override
    public void onRightPressed() {
        if (this.vehicle_state.iMainColor != 6) {
            this.vehicle_state.iStoredBaseColor = this.vehicle_state.iMainColor;
            this.vehicle_state.iStoredIsHamelion = this.vehicle_state.isHamelion;
        }
        this.vehicle_state.iMainColor = 6;
        this.vehicle_state.isHamelion = false;
    }

    // @Override
    public void selectDLCTexture(int textureId) {
        this.vehicle_state.iMainColor = 6;
        this.vehicle_state.iDLCTexture = textureId;
        this.vehicle_state.isHamelion = false;
        this.SelectColor(0, this.vehicle_state.iMainColor);
        this.SelectColor(20, this.vehicle_state.iDLCTexture);
    }

    // @Override
    public Vector<VehicleDLCTextureInfo> getUserTextureInfoVec() {
        return this.vehicle_state.getVehicleDLCTexturesInfo();
    }

    private void reinitExteriorTab(long _menu) {
        if (this.vehicle_state.iMainColor == 6) {
            this.vehicle_state.iStoredBaseColor = 0;
            this.vehicle_state.iStoredIsHamelion = this.vehicle_state.isHamelion;
            this.exteriorTab.onRightPressed(_menu, null);
        } else {
            this.vehicle_state.iStoredBaseColor = this.vehicle_state.iMainColor;
            this.vehicle_state.iStoredIsHamelion = this.vehicle_state.isHamelion;
            this.exteriorTab.onLeftPressed(_menu, null);
        }
    }

    private int getValueColor(int truecolor, boolean is_hamelion) {
        return is_hamelion ? truecolor * 2 + 1 : truecolor * 2;
    }

    private void RedrawRestyle() {
        this.SelectColor(0, this.vehicle_state.iMainColor);
        this.SelectColor(1, this.vehicle_state.iLeather);
        this.SelectColor(3, this.vehicle_state.iCloth);
        this.SelectColor(2, this.vehicle_state.iDash);
        this.SelectColor(4, this.vehicle_state.iDashGauges);
        this.SelectColor(5, this.vehicle_state.iGlass);
        this.SelectColor(10, this.vehicle_state.isMetallic ? 1 : 0);
        if (this.control_metallic != 0L) {
            menues.SetFieldState(this.control_metallic, this.vehicle_state.isMetallic ? 1 : 0);
        }
        if (this.control_chamelion != 0L) {
            menues.SetFieldState(this.control_chamelion, this.vehicle_state.isHamelion ? 1 : 0);
        }
        this.m_combobox[0].Select(s_matnum[0] - 1 - this.vehicle_state.iMainColor);
        this.m_combobox[1].Select(this.vehicle_state.iLeather);
        this.m_combobox[2].Select(this.vehicle_state.iDash);
        this.m_combobox[3].Select(this.vehicle_state.iCloth);
        this.m_combobox[4].Select(this.vehicle_state.iDashGauges);
        this.m_combobox[5].Select(this.vehicle_state.iGlass);
    }

    public void onColorChange(long _menu, MENUbutton_field field) {
        boolean oldmetal = this.vehicle_state.isMetallic;
        boolean oldcham = this.vehicle_state.isHamelion;
        this.vehicle_state.isMetallic = menues.GetFieldState(this.control_metallic) != 0;
        boolean bl = this.vehicle_state.isHamelion = menues.GetFieldState(this.control_chamelion) != 0;
        if (oldcham != this.vehicle_state.isHamelion) {
            this.SelectColor(0, this.vehicle_state.iMainColor);
        }
        if (oldmetal != this.vehicle_state.isMetallic) {
            this.SelectColor(10, this.vehicle_state.isMetallic ? 1 : 0);
        }
    }

    public void SelectColor(int type, int color) {
        switch (type) {
            case 0: {
                this.vehicle_state.iMainColor = color;
                break;
            }
            case 1: {
                this.vehicle_state.iLeather = color;
                break;
            }
            case 3: {
                this.vehicle_state.iCloth = color;
                break;
            }
            case 2: {
                this.vehicle_state.iDash = color;
                break;
            }
            case 4: {
                this.vehicle_state.iDashGauges = color;
                break;
            }
            case 5: {
                this.vehicle_state.iGlass = color;
                break;
            }
            case 20: {
                this.vehicle_state.iDLCTexture = color;
            }
        }
        MENUTruckview truckview = null;
        truckview = type == 0 || type == 5 || type == 10 || type == 20 ? this.common.FindTruckView("Exterior") : this.common.FindTruckView("Interior");
        switch (type) {
            case 0: {
                truckview.SetState(0, type, this.getValueColor(this.vehicle_state.iMainColor, this.vehicle_state.isHamelion));
                break;
            }
            case 10: {
                truckview.SetState(0, type, this.vehicle_state.isMetallic ? 1 : 0);
                break;
            }
            case 20: {
                truckview.SetState(0, type, this.vehicle_state.iDLCTexture);
                break;
            }
            default: {
                truckview.SetState(0, type, color);
            }
        }
    }

    int GetTypeByName(String name, String[] names) {
        for (int i = 0; i < names.length; ++i) {
            if (name.indexOf(names[i]) == -1) continue;
            return i;
        }
        return -1;
    }

    // @Override
    public void OnSelect(int state, Object sender) {
        for (int i = 0; i < this.m_combobox.length; ++i) {
            if (sender != this.m_combobox[i]) continue;
            this.SelectColor(i, i == 0 ? s_matnum[i] - 1 - state : state);
        }
    }

    // @Override
    public void exitMenu(long _menu) {
        ListenerManager.TriggerEvent(105);
        this.parent.setShow(true);
        this.exteriorTab.deinitMenu();
    }

    // @Override
    public String getMenuId() {
        return "mainRestyleMENU";
    }

    public void OnOk(long _menu, MENUsimplebutton_field field) {
        this.view_ext.UnBind3DModel();
        this.view_int.UnBind3DModel();
        JavaEvents.SendEvent(65, 15, this.vehicle_state);
        JavaEvents.SendEvent(65, 16, this.vehicle_state);
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void OnCancel(long _menu, MENUsimplebutton_field field) {
        this.view_ext.UnBind3DModel();
        this.view_int.UnBind3DModel();
        JavaEvents.SendEvent(65, 16, this.vehicle_state);
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    public void OnDefault(long _menu, MENUsimplebutton_field field) {
        JavaEvents.SendEvent(65, 17, this.vehicle_state);
        this.reinitExteriorTab(_menu);
        this.RedrawRestyle();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class VehicleState {
        public long handle = 0L;
        public long internal_handle = 0L;
        public int iTechNumber = 0;
        public int iMainColor = 0;
        public int iLeather = 0;
        public int iCloth = 0;
        public int iDash = 0;
        public int iDashGauges = 0;
        public int iGlass = 0;
        public String selction_name = null;
        public int iDLCTexture = 0;
        private int iStoredBaseColor = 0;
        private boolean iStoredIsHamelion = false;
        public boolean isMetallic = true;
        public boolean isHamelion = false;
        public Vector userTextures = new Vector();

        VehicleState() {
        }

        public Vector<VehicleDLCTextureInfo> getVehicleDLCTexturesInfo() {
            Vector<VehicleDLCTextureInfo> result = new Vector<VehicleDLCTextureInfo>();
            for (Object obj : this.userTextures) {
                if (!(obj instanceof UserTextureInfo)) continue;
                UserTextureInfo textureInfo = (UserTextureInfo)obj;
                VehicleDLCTextureInfo info = new VehicleDLCTextureInfo();
                info.textureId = textureInfo.textureId;
                info.textureName = textureInfo.textureName;
                result.add(info);
            }
            return result;
        }
    }

    public static class UserTextureInfo {
        String textureName;
        int textureId;
    }
}

