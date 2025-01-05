// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

import java.util.Vector;
import menu.Cmenu_TTI;
import menu.Common;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.RNRMap;
import menu.SMenu;
import menu.menucreation;
import menuscript.EscapeMenu;
import menuscript.HelpMenu;
import menuscript.NotifyGameOver;
import menuscript.office.OfficeMenu;
import rickroll.log.RickRollLog;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.event;
import rnrscr.ILeaveMenuListener;
import rnrscr.gameinfo;

public class menues
implements menucreation {
    private String menuid = null;
    public static final int CB_CHANGERAGER = 1;
    public static final int CB_RADIOPRESS = 2;
    public static final int CB_MOUSEOVER = 3;
    public static final int CB_BUTTONPRESS = 4;
    public static final int CB_MOUSEOUTSIDE = 5;
    public static final int CB_MOUSEINSIDE = 6;
    public static final int CB_CONTROLSELECT = 7;
    public static final int CB_ACTIONBUTTONPRESS = 8;
    public static final int CB_USERACTION = 9;
    public static final int CB_SWITCHON = 10;
    public static final int CB_SWITCHOFF = 11;
    public static final int CB_ACTION_KEY_OUT_LEFT = 12;
    public static final int CB_ACTION_KEY_OUT_RIGHT = 13;
    public static final int CB_ACTION_KEY_OUT_DOWN = 14;
    public static final int CB_ACTION_KEY_OUT_UP = 15;
    public static final int CB_TEXTEXECUTE = 16;
    public static final int CB_CLOSEWINDOW = 17;
    public static final int CB_BEFOREACTION = 18;
    public static final int CB_TEXTDISSMIS = 19;
    public static final int CB_MAPOBJECTPRESSED = 20;
    public static final int CB_MAPOBJECTACTIVE = 21;
    public static final int CB_RELEASE = 22;
    public static final int VWI_UI_CONTROL_ACTION_USER = 65535;
    public static final int VWI_UI_CONTROL_ACTION_USER1 = 131070;
    public static final int VWI_UI_CONTROL_ACTION_USER2 = 196605;
    public static final int VWI_UI_CONTROL_ACTION_USER3 = 262140;
    public static final int VWI_UI_CONTROL_ACTION_USER4 = 327675;
    public static final int LOGIC_MENU_F2 = 0;
    public static final int LOGIC_MENU_MESSAGE = 1;
    public static final int LOGIC_MENU_WH = 2;
    public static final int LOGIC_MENU_CB = 3;
    public static final int LOGIC_MENU_TITRE = 4;
    public static final int LOGIC_MENU_PAUSE = 5;
    public static final long eTITRESNIMATIONID = 57087L;
    static long HELP_MENU = 0L;

    public void exitMenu(long _menu) {
    }

    public void restartMenu(long _menu) {
    }

    public String getMenuId() {
        if (this.menuid == null) {
            gameinfo info = gameinfo.script;
            return info.CW_info.menuId;
        }
        return this.menuid;
    }

    public static native void CreateGasMenu(int var0, int var1, int var2, int var3, int var4, int var5, String var6, int var7, int var8);

    public static native void CreateSTOMenu(int var0, int var1, int var2, int var3, int var4, int var5, String var6, int var7, int var8);

    public static native void CreateWareHouseMenu(int var0, int var1, int var2, int var3, int var4, int var5, String var6, int var7, int var8);

    public static native void CreateMotelMenu(int var0, int var1, int var2, int var3, int var4, int var5, String var6, int var7, int var8);

    public static native long CreateOfficeMenu(menucreation var0, int var1, int var2, int var3, int var4, int var5, int var6, String var7, int var8, int var9);

    public static native long CreateJournalMenu(menucreation var0, int var1, int var2, int var3, int var4, int var5, int var6, String var7, int var8, int var9);

    public static native long createTitre(menucreation var0, double var1);

    // menues.createSimpleMenu(
    // new HelpMenu(), 
    // 240000.0, 
    // "ESC", ¿Key? 
    // 1600, 
    // 1200, 
    // 1600, 
    // 1200, 
    // 0, 
    // 0, 
    // "..\\Data\\Menu\\Cursors\\cursor_01.tga", 
    // 0, 
    // 0);
    public static native long createSimpleMenu(menucreation var0, double var1, String var3, int var4, int var5, int var6, int var7, int var8, int var9, String var10, int var11, int var12);

    public static native long createSimpleMenu(menucreation var0, int var1, double var2, String var4, int var5, int var6, int var7, int var8, int var9, int var10, String var11, int var12, int var13);

    public static long createSimpleMenu(menucreation menuObj) {
        return menues.createSimpleMenu(menuObj, 1.0E7, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long createSimpleMenu(menucreation menuObj, int logic_type) {
        return menues.createSimpleMenu(menuObj, logic_type, 1.0E7, "", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long createSimpleMenu(menucreation menuObj, int logic_type, String hot_key) {
        return menues.createSimpleMenu(menuObj, logic_type, 1.0E7, hot_key, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public static long createSimpleMenu(menucreation menuObj, String hotkey_exit) {
        return menues.createSimpleMenu(menuObj, 1.0E7, hotkey_exit, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void ShowMenu() {
        menues.setShowMenu(Common.s_lastcommon.GetMenu(), true);
    }

    public static void setShowMenu(long _menu, boolean value) {
        if (value) {
            menues.showMenu(menues.getMenuID(_menu));
        } else {
            menues.hideMenu(menues.getMenuID(_menu));
        }
    }

    public static void CreateGASSTATIONMENU() {
        menues.CreateGasMenu(1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void ExitGASSTATIONMENU() {
        event.Setevent(1001);
    }

    public void ExitREPAIRMENU() {
        event.Setevent(2001);
    }

    public void ExitSimpleMenu() {
        if (!eng.noNative) {
            event.Setevent(8001);
        }
    }

    public void CreateWHMENU() {
        menues.CreateWareHouseMenu(1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void ExitWHMENU() {
        event.Setevent(3001);
    }

    public static void CreateMotelMENU() {
        menues.CreateMotelMenu(1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void ExitMotelMENU() {
        event.Setevent(5001);
    }

    public static void CreateOfficeMENU() {
        OfficeMenu.create();
    }

    public void ExitOfficeMENU() {
        event.Setevent(6001);
    }

    public boolean CreateJournalMENU() {
        if (!menues.cancreate_somenu()) {
            return false;
        }
        menues.CreateJournalMenu(null, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        return true;
    }

    public void CreateJournalMENU_void() {
        menues.CreateJournalMenu(null, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void ExitJournalMENU() {
        event.Setevent(7001);
    }

    public static void ExitBarMENU() {
        Helper.peekNativeMessage("Bar menu exit");
    }

    public void InitMenu(long _menu) {
    	
    	
    	RickRollLog.dumpStackTrace("InitMenu; _menu: " + _menu);
        menues.SetStopWorld(_menu, false);
    	
    	
        gameinfo info = gameinfo.script;
        info.CW_info.menuId = null;
        if (info.CW_info.OnSpecObject) {
            for (int i = 0; i < info.CW_info.strings.size(); ++i) {
                menues.InitXml(_menu, Common.ConstructPath("specmenu.xml"), (String)info.CW_info.strings.get(i));
            }
        }
    }

    public void AfterInitMenu(long _menu) {
        menues.setShowMenu(_menu, true);
        gameinfo info = gameinfo.script;
        if (info.CW_info.Titre) {
            menues.SetScriptAnimation(_menu, 57087L, "menu/menues", "TitresMovement");
        }
    }

    public void SpecObjectMessage1() {
        this.SpecObjectMessage(2);
    }

    public void SpecObjectMessage2() {
        this.SpecObjectMessage(3);
    }

    public void SpecObjectMessage3() {
        this.SpecObjectMessage(4);
    }

    public void SpecObjectMessage4() {
        this.SpecObjectMessage(6);
    }

    public void SpecObjectMessage5() {
        this.SpecObjectMessage(7);
    }

    public boolean check_escapemenu_existance() {
        return EscapeMenu.created;
    }

    public long escapemenu() {
        return EscapeMenu.CreateEscapeMenu();
    }

    public long HelpF1Message() {
        return HelpMenu.CreateMenu();
    }

    public static void gameoverMenuBlackScreen() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 4);
    }

    public static void gameoverMenu() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 0);
    }

    public static void gameoverMenuJail() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 1);
    }

    public static void gameoverMenuMurder(ILeaveMenuListener listener) {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0, 2, listener);
    }

    public static void gameoverBankrupt(ILeaveMenuListener listener) {
        NotifyGameOver.CreateNotifyGameOver("", 1000.0, 3, listener);
    }

    public long SpecObjectMessage(int type_SpecObject) {
        if (type_SpecObject == 1) {
            return 0L;
        }
        menues menu = new menues();
        long wnd = menues.createSimpleMenu((menucreation)menu, 0);
        gameinfo info = gameinfo.script;
        switch (type_SpecObject) {
            case 2: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpecgasf2");
                info.CW_info.menuId = "f2gasMENU";
                break;
            }
            case 3: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpecrepairf2");
                info.CW_info.menuId = "f2repairMENU";
                break;
            }
            case 4: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpecbarf2");
                info.CW_info.menuId = "f2barMENU";
                break;
            }
            case 6: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpecmotelf2");
                info.CW_info.menuId = "f2motelMENU";
                break;
            }
            case 7: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpecofficef2");
                info.CW_info.menuId = "f2officeMENU";
                break;
            }
            case 8: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpecpolice2");
                info.CW_info.menuId = "f2policeMENU";
                break;
            }
            default: {
                info.CW_info.ClearAll();
                info.CW_info.OnSpecObject = true;
                info.CW_info.AddString("cpdefaultf2");
                info.CW_info.menuId = "f2defaultMENU";
            }
        }
        menu.menuid = info.CW_info.menuId;
        return wnd;
    }

    public static native void SetMenagPOLOSY(long var0, boolean var2);

    public static native void menu_set_polosy(long var0, boolean var2);

    public static native void SetStopWorld(long var0, boolean var2);

    public static native void WindowSet_ShowCursor(long var0, boolean var2);

    public static native MENUText_field InitiateTextFields(long var0, String var2, String var3);

    public static native MENUsimplebutton_field InitiateSimpleButton(long var0, String var2, String var3);

    public static native MENU_ranger InitiateRanger(long var0, String var2, String var3);

    public static native MENUbutton_field InitiateButton(long var0, String var2, String var3);

    public static native long CreateTable(long var0);

    public static native long CreateGroup(long var0);

    public static native void AddGroupInTable(long var0, long var2, long var4, long var6);

    public static native void AddControlInGroup(long var0, long var2, Object var4);

    public static native long FillMajorDataTable(long var0, long var2, String var4, String var5);

    public static native void ScriptSyncGroup(long var0, long var2, String var4, String var5);

    public static native void ChangableFieldOnGroup(long var0, Object var2);

    public static native long FillMajorDataTable_ScriptObject(long var0, long var2, Object var4, String var5);

    public static native void ScriptObjSyncGroup(long var0, long var2, Object var4, String var5);

    public static native void LinkGroupAndControl(long var0, Object var2);

    public static native void LinkTableAndControl(long var0, Object var2);

    public static native void LingGroupAndGroup(long var0, long var2, long var4);

    public static native void LingGroupAndTable(long var0, long var2);

    public static native void LingGroupAndTable(long var0, long var2, long var4);

    public static native void LingTableAndTable(long var0, long var2, long var4);

    public static native void StoreControlState(long var0, Object var2);

    public static native void SetRunScriptOnControl(long var0, Object var2, String var3, String var4);

    public static native void SetRunScriptOnControl(long var0, Object var2, String var3, String var4, long var5);

    public static native void SetRunScriptOnControlDataPass(long var0, Object var2, String var3, String var4);

    public static native void SetRunScriptOnControlDataPass(long var0, Object var2, String var3, String var4, long var5);

    public static native void SetRunScriptOnGroup(long var0, long var2, String var4, String var5);

    public static native void SetRunScriptOnGroup(long var0, long var2, String var4, String var5, long var6);

    public static native void SetRunScriptOnTable(long var0, long var2, String var4, String var5);

    public static native void SetRunScriptOnTable(long var0, long var2, String var4, String var5, long var6);

    public static native void SetScriptOnControl(long var0, Object var2, Object var3, String var4);

    public static native void SetScriptOnControl(long var0, Object var2, Object var3, String var4, long var5);

    public static native void SetScriptOnControlDataPass(long var0, Object var2, Object var3, String var4);

    public static native void SetScriptOnControlDataPass(long var0, Object var2, Object var3, String var4, long var5);

    public static native void SetScriptOnGroup(long var0, long var2, Object var4, String var5);

    public static native void SetScriptOnGroup(long var0, long var2, Object var4, String var5, long var6);

    public static native void SetScriptOnTable(long var0, long var2, Object var4, String var5);

    public static native void SetScriptOnTable(long var0, long var2, Object var4, String var5, long var6);

    public static native void RedrawAll(long var0);

    public static native void RedrawTable(long var0, long var2);

    public static native void RedrawGroup(long var0, long var2);

    public static native long GetGroupOnLine(long var0, long var2, int var4);

    public static native Cmenu_TTI GetXMLDataOnGroup(long var0, long var2);

    public static native Cmenu_TTI GetXMLDataOnTable(long var0, long var2);

    public static native void SetXMLDataOnGroup(long var0, long var2, Cmenu_TTI var4);

    public static native void SetXMLDataOnTable(long var0, long var2, Cmenu_TTI var4);

    public static native void UpdateData(Cmenu_TTI var0);

    public static native void UpdateDataWithChildren(Cmenu_TTI var0);

    public static native void WrapData(Cmenu_TTI var0);

    public static native void WrapDataWithChildren(Cmenu_TTI var0);

    public static native void ConnectTableAndData(long var0, long var2);

    public static native long GetTable_byGroup(long var0, long var2);

    public static native long GetLineInTable(long var0, long var2);

    public static native int GetTextHeight(long var0, String var2);

    public static native int GetTextLineHeight(long var0);

    public static native int GetBaseLine(long var0);

    public static native void SetBaseLine(long var0, int var2);

    public static native int GetBaseLine(long var0, boolean var2, int var3);

    public static native void SetBaseLine(long var0, int var2, boolean var3, int var4);

    public static native int GetBaseLinePressed(long var0, int var2);

    public static native void SetBaseLinePressed(long var0, int var2, int var3);

    public static native int GetNumStates(long var0);

    public static native long GetBackMenu(long var0);

    public static native long FindFieldInMenu(long var0, String var2);

    public static native long FindFieldInMenu_ByParent(long var0, String var2, String var3);

    public static native long FindChildInControl(long var0, long var2, String var4);

    public static native long FindFieldInGroup(long var0, long var2, String var4);

    public static native long FindFieldInTable(long var0, long var2, String var4);

    public static native void SetFieldText(long var0, String var2);

    public static native void SetFieldState(long var0, int var2);

    public static native void SetShowField(long var0, boolean var2);

    public static native String GetFieldText(long var0);

    public static native int GetFieldState(long var0);

    public static native boolean GetShowField(long var0);

    public static native long GetStoredState(long var0, long var2);

    public static native String GetFieldName(long var0);

    public static native void SetFieldParentName(long var0, String var2);

    public static native String GetFieldParentName(long var0);

    public static native void SetFieldName(long var0, long var2, String var4);

    public static native Object ConvertMenuFields(long var0);

    public static native void UpdateMenuField(Object var0);

    public static native void SetUVRotationOnGroup(long var0, long var2, String var4, boolean var5, int var6, double var7, double var9);

    public static native void SetUVRotationOnGroupTree(long var0, long var2, String var4, boolean var5, int var6, double var7, double var9);

    public static native void CopyUVRotationOnGroupTree(long var0, long var2, String var4, boolean var5, int var6, long var7, boolean var9, int var10);

    public static native void SetUVAnimationCallBackOnGroup(long var0, long var2, String var4, boolean var5, int var6, String var7);

    public static native void SetUVAnimationCallBackOnGroupTree(long var0, long var2, String var4, boolean var5, int var6, String var7);

    public static native void SetAlfaAnimationOnGroup(long var0, long var2, String var4, double var5, double var7, String var9);

    public static native void SetAlfaAnimationOnGroupTree(long var0, long var2, String var4, double var5, double var7, String var9);

    public static native void SetUVAnimationCallBackOnControl(long var0, String var2, boolean var3, int var4, String var5);

    public static native void SetAlfaAnimationOnControl(long var0, String var2, double var3, double var5, String var7);

    public static native long[] InitXml(long var0, String var2, String var3);

    public static native long InitXmlControl(long var0, String var2, String var3, String var4);

    public static native void SetMenuCallBack_ExitMenu(long var0, long var2);

    public static native void SetMenuCallBack_OKMenu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel1Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel2Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel3Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel4Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel5Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel6Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel7Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel8Menu(long var0, long var2);

    public static native void SetMenuCallBack_Cancel9Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK1Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK2Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK3Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK4Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK5Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK6Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK7Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK8Menu(long var0, long var2);

    public static native void SetMenuCallBack_OK9Menu(long var0, long var2);

    public static native void SetMenuCallBack_ExitMenu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OKMenu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel1Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel2Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel3Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel4Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel5Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel6Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel7Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel8Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_Cancel9Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK1Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK2Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK3Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK4Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK5Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK6Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK7Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK8Menu(long var0, long var2, long var4);

    public static native void SetMenuCallBack_OK9Menu(long var0, long var2, long var4);

    public static native void CallMenuCallBack_ExitMenu(long var0);

    public static native void CallMenuCallBack_OKMenu(long var0);

    public static native void CallMenuCallBack_Cancel1Menu(long var0);

    public static native void CallMenuCallBack_Cancel2Menu(long var0);

    public static native void CallMenuCallBack_Cancel3Menu(long var0);

    public static native void CallMenuCallBack_Cancel4Menu(long var0);

    public static native void CallMenuCallBack_Cancel5Menu(long var0);

    public static native void CallMenuCallBack_Cancel6Menu(long var0);

    public static native void CallMenuCallBack_Cancel7Menu(long var0);

    public static native void CallMenuCallBack_Cancel8Menu(long var0);

    public static native void CallMenuCallBack_Cancel9Menu(long var0);

    public static native void CallMenuCallBack_OK1Menu(long var0);

    public static native void CallMenuCallBack_OK2Menu(long var0);

    public static native void CallMenuCallBack_OK3Menu(long var0);

    public static native void CallMenuCallBack_OK4Menu(long var0);

    public static native void CallMenuCallBack_OK5Menu(long var0);

    public static native void CallMenuCallBack_OK6Menu(long var0);

    public static native void CallMenuCallBack_OK7Menu(long var0);

    public static native void CallMenuCallBack_OK8Menu(long var0);

    public static native void CallMenuCallBack_OK9Menu(long var0);

    public static native void SetSyncControlState(long var0, int var2, long var3);

    public static native void SetSyncControlActive(long var0, int var2, long var3);

    public static native void RemoveSyncControlState(long var0, int var2, long var3);

    public static native void RemoveSyncControlActive(long var0, int var2, long var3);

    public static native void SetScriptAnimation(long var0, long var2, String var4, String var5);

    public static native void SetScriptAnimation(long var0, long var2, String var4, String var5, double var6);

    public static native void SetScriptObjectAnimation(long var0, long var2, Object var4, String var5);

    public static native void SetScriptObjectAnimation(long var0, long var2, Object var4, String var5, double var6);

    public static native void StopScriptAnimation(long var0);

    public static native int GetControlGroup(long var0, long var2);

    public static native int GetControlTable(long var0, long var2);

    public static native void ChangeAllMaterialsOnControl(long var0, long var2, String var4);

    public static native void ChangeAllMaterialsOnControl(long var0, String var2, String var3);

    public static native void SetControlColorOnAllSatetesAndTextsAnimated(long var0, int var2);

    public static native void SetControlColor(long var0, boolean var2, int var3, int var4);

    public static native void SetControlColor(long var0, boolean var2, boolean var3, int var4, int var5);

    public static native void SetTextColor(long var0, boolean var2, int var3, int var4);

    public static native void SetTextColor(long var0, int var2);

    public static native int GetTextColor(long var0, boolean var2, int var3);

    public static native int GetTextColor(long var0);

    public static native void CallMappingModifications(long var0, Object var2, String var3);

    public static native void SetBlindess(long var0, boolean var2);

    public static native void SetIgnoreEvents(long var0, boolean var2);

    public static native boolean cancreate_messagewindow();

    public static native boolean cancreate_somenu();

    public static native void setfocuscontrolonmenu(long var0, long var2);

    public static native int getSX(long var0);

    public static native int getSY(long var0);

    public static native int getMenuID(long var0);

    public static native void setMenuID(long var0, int var2);

    public static native void renderToTarget(long var0);

    public static native void renderToTargetContinuously(long var0);

    public static native void menuAnimation_stop(long var0);

    public static native void menuAnimation_resume(long var0);

    public static native void menuAnimation_setonend(long var0);

    public static native void menuAnimation_suspendonend(long var0);

    public static native void menuAnimation_reset(long var0);

    public static native void setControlCanOperate(long var0, boolean var2);

    public static native void setFocusWindow(int var0);

    public static native void setSuffixText(long var0, String var2);

    public static native boolean isControlOnFocus(long var0, long var2);

    public static native void setFocusOnControl(long var0, boolean var2);

    public static native void setActiveControl(long var0, boolean var2);

    public static native void setPressedControl(long var0, boolean var2);

    public static native void showMenu(int var0);

    public static native void hideMenu(int var0);

    public static RNRMap ConvertRNRMAPFields(long field) {
        return (RNRMap)menues.ConvertMenuFields(field);
    }

    public static MENUText_field ConvertTextFields(long field) {
        return (MENUText_field)menues.ConvertMenuFields(field);
    }

    public static MENUsimplebutton_field ConvertSimpleButton(long field) {
        return (MENUsimplebutton_field)menues.ConvertMenuFields(field);
    }

    public static MENU_ranger ConvertRanger(long field) {
        return (MENU_ranger)menues.ConvertMenuFields(field);
    }

    public static MENUbutton_field ConvertButton(long field) {
        return (MENUbutton_field)menues.ConvertMenuFields(field);
    }

    public static SMenu ConvertWindow(long field) {
        return (SMenu)menues.ConvertMenuFields(field);
    }

    public static void UpdateField(MENUText_field fild) {
        menues.UpdateMenuField(fild);
    }

    public static void UpdateField(MENUsimplebutton_field fild) {
        menues.UpdateMenuField(fild);
    }

    public static void UpdateField(MENU_ranger fild) {
        menues.UpdateMenuField(fild);
    }

    public static void UpdateField(MENUbutton_field fild) {
        menues.UpdateMenuField(fild);
    }

    public static void UpdateField(SMenu fild) {
        menues.UpdateMenuField(fild);
    }

    void SetOneTestTextItem(MENUText_field field, int resolution) {
        int sizefont24 = 24;
        double diff = (double)resolution / 1600.0;
        sizefont24 = (int)Math.floor((double)sizefont24 * diff);
        this.SetOneTextField(field, 1600, 1200, 1101, 163, 330, 36, "Arial", sizefont24, (int)(26.0 * diff), "LeftBase", -2334, true, false);
    }

    void SetUpOneMenu(SMenu _menu, int xres, int yres, int px, int py, int lx, int ly, String texname, boolean statettex, double tx1, double ty1, double tx2, double ty2) {
        _menu.Xres = xres;
        _menu.Yres = yres;
        _menu.pox = px;
        _menu.poy = py;
        _menu.lenx = lx;
        _menu.leny = ly;
        _menu.texture = new CTextur_whithmapping();
        _menu.material = new CMaterial_whithmapping();
        _menu.material.material = "";
        _menu.texture.state = statettex;
        _menu.texture.t1x = tx1;
        _menu.texture.t1y = ty1;
        _menu.texture.t2x = tx2;
        _menu.texture.t2y = ty2;
        _menu.texture.texture = texname;
    }

    void SetUpOneMenu(SMenu _menu, int xres, int yres, int px, int py, int lx, int ly) {
        _menu.Xres = xres;
        _menu.Yres = yres;
        _menu.pox = px;
        _menu.poy = py;
        _menu.lenx = lx;
        _menu.leny = ly;
        _menu.texture = new CTextur_whithmapping();
        _menu.material = new CMaterial_whithmapping();
    }

    void SetOneTextField(MENUText_field field, int xres, int yres, int px, int py, int lx, int ly, String fnt, int sz, int bzln, String TextFlags, int textcolor, boolean _bold, boolean _itallic) {
        field.Xres = xres;
        field.Yres = yres;
        field.pox = px;
        field.poy = py;
        field.lenx = lx;
        field.leny = ly;
        field.font = fnt;
        field.fonssz = sz;
        field.baseline = bzln;
        field.bold = _bold;
        field.italic = _itallic;
        field.TextFlags = TextFlags;
        field.textColor = textcolor;
        field.callbacks = new Vector();
    }

    void SetOneSimpleButton(MENUsimplebutton_field button, int xres, int yres, int px, int py, int lx, int ly) {
        button.Xres = xres;
        button.Yres = yres;
        button.pox = px;
        button.poy = py;
        button.lenx = lx;
        button.leny = ly;
        button.callbacks = new Vector();
    }

    void SetOneButton(MENUbutton_field button, int xres, int yres, int px, int py, int lx, int ly, int nomstates) {
        button.Xres = xres;
        button.Yres = yres;
        button.pox = px;
        button.poy = py;
        button.lenx = lx;
        button.leny = ly;
        button.nomstates = nomstates;
        button.callbacks = new Vector();
    }

    void SetOneRanger(MENU_ranger button, int xres, int yres, int px, int py, int lx, int ly, String _tip, String _orientation) {
        button.Xres = xres;
        button.Yres = yres;
        button.pox = px;
        button.poy = py;
        button.lenx = lx;
        button.leny = ly;
        button.type = _tip;
        button.orientation = _orientation;
        button.thumb_texstates = new Vector();
        button.buttonup_texstates = new Vector();
        button.buttondown_texstates = new Vector();
        button.thumb_material = new Vector();
        button.buttonup_material = new Vector();
        button.buttondown_material = new Vector();
        button.callbacks = new Vector();
    }

    CTextur_whithmapping PrepairTexture(boolean _state, double tex1, double tey1, double lengthx, double lengthy, double lnx, double lny, String _name) {
        double t1 = tex1 / lnx;
        double t2 = tey1 / lny;
        double t3 = (tex1 + lengthx) / lnx;
        double t4 = (tey1 + lengthy) / lny;
        CTextur_whithmapping inTEX = new CTextur_whithmapping();
        inTEX.state = _state;
        inTEX.t1x = t1;
        inTEX.t1y = t2;
        inTEX.t2x = t3;
        inTEX.t2y = t4;
        inTEX.texture = _name;
        return inTEX;
    }

    CTextur_whithmapping PrepairTexture(boolean _state, double t1, double t2, double t3, double t4, String _name) {
        CTextur_whithmapping inTEX = new CTextur_whithmapping();
        inTEX.state = _state;
        inTEX.t1x = t1;
        inTEX.t1y = t2;
        inTEX.t2x = t3;
        inTEX.t2y = t4;
        inTEX.texture = _name;
        return inTEX;
    }

    CMaterial_whithmapping PrepairTexture(boolean _active, String _name) {
        CMaterial_whithmapping inTEX = new CMaterial_whithmapping();
        inTEX.tex = new Vector();
        inTEX._active = _active;
        inTEX.material = _name;
        return inTEX;
    }

    void AddMaterial(CMaterial_whithmapping inTEX, int nomtex, double x0, double y0, double lx, double ly, double rx, double ry) {
        double tx0f = x0 / rx;
        double ty0f = y0 / ry;
        double tx1f = (x0 + lx) / rx;
        double ty1f = y0 / ry;
        double tx2f = (x0 + lx) / rx;
        double ty2f = (y0 + ly) / ry;
        double tx3f = x0 / rx;
        double ty3f = (y0 + ly) / ry;
        inTEX.Add(nomtex, (float)tx0f, (float)ty0f, (float)tx1f, (float)ty1f, (float)tx2f, (float)ty2f, (float)tx3f, (float)ty3f);
    }

    void Detail1(CMaterial_whithmapping mat1_tex, double xl, double yl) {
        double xres0 = 1024.0;
        double yres0 = 512.0;
        double xres0LEN = 1600.0;
        double yres0LEN = 1200.0;
        double xres = eng.GetXResolution();
        double yres = eng.GetYResolution();
        double xl0 = 1024.0;
        double yl0 = 512.0;
        double tilex = 1.0;
        double tiley = 35.0;
        double tilexres = xres0 * tilex * (xres * xres * xl) / (xres0 * xl0 * xres0LEN);
        double tileyres = yres0 * tiley * (yres * yres * yl) / (yres0 * yl0 * yres0LEN);
        mat1_tex.AddMaterial(1, 0.0, 0.0, tilexres, tileyres, xres0, yres0);
    }

    void Detail2(CMaterial_whithmapping mat1_tex, double xl, double yl) {
        double xres0 = 1024.0;
        double yres0 = 512.0;
        double xres0LEN = 1600.0;
        double yres0LEN = 1200.0;
        double xres = eng.GetXResolution();
        double yres = eng.GetYResolution();
        double xl0 = 1024.0;
        double yl0 = 512.0;
        double tilex = 1.0;
        double tiley = 0.05;
        double tilexres = xres0 * tilex * (xres * xres * xl) / (xres0 * xl0 * xres0LEN);
        double tileyres = yres0 * tiley * (yres * yres * yl) / (yres0 * yl0 * yres0LEN);
        mat1_tex.AddMaterial(2, 0.0, 0.0, tilexres, tileyres, xres0, yres0);
    }

    void Detail2(CMaterial_whithmapping mat1_tex, double x0, double y0, double _X0, double _Y0, double x, double y, double xl, double yl) {
        double xres0 = 4.0;
        double yres0 = 1024.0;
        double tilex = 1.0;
        double tiley = 0.05;
        double tx = tilex * xres0;
        double ty = tiley * yres0;
        double u = (x - x0) * tx / _X0;
        double v = (y - y0) * ty / _Y0;
        double wu = xl * tx / _X0;
        double wv = yl * ty / _Y0;
        mat1_tex.AddMaterial(2, u, v, wu, wv, xres0, yres0);
    }

    void testestes(CMaterial_whithmapping matt) {
        matt._state = 3;
        Object[] a = matt.tex.toArray();
        ctexcoord_multylayer F = (ctexcoord_multylayer)a[0];
        F.index = 50;
    }

    public class s_nat_patch {
        public int x;
        public int y;
        public int sx;
        public int sy;
        public String tip = new String();
    }

    public class ctexcoord_multylayer {
        public int index;
        public boolean video;
        public float t0x;
        public float t0y;
        public float t1x;
        public float t1y;
        public float t2x;
        public float t2y;
        public float t3x;
        public float t3y;
    }

    class CTextur_whithmapping {
        boolean state;
        double t1x;
        double t1y;
        double t2x;
        double t2y;
        String texture;

        CTextur_whithmapping() {
        }
    }

    public class CMaterial_whithmapping {
        public int _state;
        public boolean _active;
        public boolean usepatch;
        public boolean pressed;
        public s_nat_patch _patch;
        public Vector tex = new Vector();
        public String material;

        void Add(int nomtexcoords, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3) {
            ctexcoord_multylayer texcoord = new ctexcoord_multylayer();
            texcoord.index = nomtexcoords;
            texcoord.t0x = tx0;
            texcoord.t0y = ty0;
            texcoord.t1x = tx1;
            texcoord.t1y = ty1;
            texcoord.t2x = tx2;
            texcoord.t2y = ty2;
            texcoord.t3x = tx3;
            texcoord.t3y = ty3;
            this.tex.add(texcoord);
        }

        void AddMaterial(int nomtex, double x0, double y0, double lx, double ly, double rx, double ry) {
            double tx0f = x0 / rx;
            double ty0f = y0 / ry;
            double tx1f = (x0 + lx) / rx;
            double ty1f = y0 / ry;
            double tx2f = (x0 + lx) / rx;
            double ty2f = (y0 + ly) / ry;
            double tx3f = x0 / rx;
            double ty3f = (y0 + ly) / ry;
            this.Add(nomtex, (float)tx0f, (float)ty0f, (float)tx1f, (float)ty1f, (float)tx2f, (float)ty2f, (float)tx3f, (float)ty3f);
        }

        void AddMaterial(int nomtex, double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3, double rx, double ry) {
            double tx0f = x0 / rx;
            double ty0f = y0 / ry;
            double tx1f = x1 / rx;
            double ty1f = y1 / ry;
            double tx2f = x2 / rx;
            double ty2f = y2 / ry;
            double tx3f = x3 / rx;
            double ty3f = y3 / ry;
            this.Add(nomtex, (float)tx0f, (float)ty0f, (float)tx1f, (float)ty1f, (float)tx2f, (float)ty2f, (float)tx3f, (float)ty3f);
        }

        CMaterial_whithmapping() {
        }
    }

    public class cMenuItemCallback {
        int id;
        String ScriptName;
        String MethodName;

        cMenuItemCallback(int _id, String _Scr, String _Meth) {
            this.id = _id;
            this.ScriptName = _Scr;
            this.MethodName = _Meth;
        }
    }

    public class CRepairItem {
        boolean openlist;
        String name;
        double price;
        int condition;
        boolean authorize;
        int[] nom_lines;
        String otstup;
    }
}
