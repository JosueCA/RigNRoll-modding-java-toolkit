// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

import java.util.HashMap;

import menu.JavaEventCb;
import menu.ListenerManager;
import menu.SelectCb;
import rickroll.log.RickRollLog;
import rnrcore.eng;

public class JavaEvents
implements SelectCb {
    public static final int ESCAPE_REFRESH = 0;
    public static final int ESCAPE_UPDATE = 1;
    public static final int ESCAPE_INIT = 2;
    public static final int ESCAPE_DESTROY = 3;
    public static final int ESCAPE_CHECKKEY = 4;
    public static final int ESCAPE_QUITGAME = 5;
    public static final int ESCAPE_ROADSERVICE = 6;
    public static final int JOURNAL_WEATHER = 7;
    public static final int WH_REFRESH = 8;
    public static final int HELP_REFRESH = 9;
    public static final int STO_APPLY = 10;
    public static final int RACE_FADECOMPLETE = 11;
    public static final int SINGLEPLAYERGAMEOPTIONS_READY_RECIEVE = 12;
    public static final int SINGLEPLAYERGAMEOPTIONS_UPDATE = 13;
    public static final int SETTINGSCONTROLS_READY_RECIEVE = 14;
    public static final int SETTINGSCONTROLS_UPDATE = 15;
    public static final int ESCAPE_CHECKAXE = 16;
    public static final int SETTINGSCONTROLS_GETAXENAME = 17;
    public static final int SETTINGSAUDIO_PROVIDE = 18;
    public static final int SETTINGSAUDIO_UPDATE = 19;
    public static final int SETTINGSVIDEO_PROVIDE = 20;
    public static final int SETTINGSVIDEO_UPDATE = 21;
    public static final int SETTINGSVIDEO_CHANGECARD = 22;
    public static final int ESCAPE_QUITMAINMENU = 23;
    public static final int ESCAPE_QUITMAINMENU_MAINMENU = 1;
    public static final int ESCAPE_QUITMAINMENU_RESTART = 2;
    public static final int ESCAPE_QUITMAINMENU_RESTART_INSTANTORDER = 3;
    public static final int MAIN_MENU_PROFILE = 24;
    public static final int SAVE_LOAD_COMMON = 25;
    public static final int SET_INTERCEPT_PAUSE = 26;
    public static final int INPAUSE_ON_GAME = 27;
    public static final int OFFPAUSE_ON_GAME = 28;
    public static final int MANAGE_DRIVERS = 29;
    public static final int CONTROL_CHECK_SHIFT = 30;
    public static final int CONTROL_CHECK_CTRL = 31;
    public static final int CONTROL_CATCH_CTRL_A = 32;
    public static final int MANAGE_FLEET = 33;
    public static final int RECIEVE_TEXTURECOORDS = 34;
    public static final int FIRE_HIRE_DRIVERS = 35;
    public static final int RECIEVE_WORLDCOORDINATES = 36;
    public static final int MANAGE_BRANCHES = 37;
    public static final int RECIEVE_CARINFO = 38;
    public static final int TRACE_FREEWAY = 39;
    public static final int RECIEVE_GAMESTATE = 40;
    public static final int MEASSURE_RACE_TIME = 41;
    public static final int CHECK_GAME_WORLD_STATE = 42;
    public static final int CAR_REPAIR = 43;
    public static final int OFFICE_ACTIONS = 44;
    public static final int PERFORM_AUTO_UNSETTLED_DEPT = 45;
    public static final int PEEKMESSAGE = 46;
    public static final int MISSIONCHANNEL_MESSAGES = 47;
    public static final int MISSIONSTART = 48;
    public static final int MISSIONDECLINE = 49;
    public static final int UPDATE_MISSION_PARAMS = 50;
    public static final int MISSION_QUESTITEM = 51;
    public static final int MISSION_QUEUERESOURCE = 52;
    public static final int CONTROL_CATCH_YES_KEY = 53;
    public static final int CONTROL_CATCH_NO_KEY = 54;
    public static final int RECIEVE_PACKAGESHIFT = 55;
    public static final int GET_MAPPOINTS_INFO = 56;
    public static final int GET_NPC_INFO = 57;
    public static final int CHECK_MISSIONSLOT_BUZZY = 58;
    public static final int QUERRY_MISSION_SLOTS = 59;
    public static final int MISSION_CLEANRESOURCE = 60;
    public static final int ABONISH_MISSION = 61;
    public static final int MENU_PHOTO_ALBUM = 62;
    public static final int GET_TRUCKER_IDENTITIES = 63;
    public static final int LOADMODEL_CALLBACK = 64;
    public static final int QUICK_RACE_MENU_CALLBACK = 65;
    public static final int WAREHOUSE_MENU_CALLBACK = 66;
    public static final int STO_VEHICLE_UPGRADES = 67;
    public static final int ORGANAIZER_PHOTO_ALBUM = 68;
    public static final int IS_BASE_READY_FOR_CHECKIN = 69;
    public static final int CONVERT_GAME_TIME = 70;
    public static final int MENU_MISC = 71;
    public static final int NARKO_INFLUENCE = 72;
    public static final int CHANGEMISSION_DESTINATION = 73;
    public static final int REPLAYPLAYERGAMEOPTIONS_READY_RECIEVE = 74;
    public static final int REPLAYPLAYERGAMEOPTIONS_UPDATE = 75;
    public static final int CONTROL_MOUSE_WHEEL = 76;
    public static final int GET_MERCHANDIZE_REAL_NAME = 77;
    public static final int GET_WAREHOUSE_REAL_NAME = 78;
    public static final int CONTROL_CATCH_ESC = 80;
    public static final int BALANCE_CHANGED = 81;
    public static final int CONTROL_CATCH_ENTER_KEY = 82;
    public static final int CHANEL_IS_ACTIVE = 83;
    public static final int MISSION_ON_MONEY = 84;
    public static final int SAME_PASSANGER = 85;
    public static final int MISSION_FINISH_SCRIPT_CONDITION = 86;
    public static final int FREE_SLOT_FOR_MISSION = 87;
    public static final int IS_WIDE = 88;
    public static final int DLC_SHOP_IMPLEMENT = 90;
    static JavaEvents s_instance = null;

    HashMap<Integer, JavaEventCb> m_hashmap = new HashMap<Integer, JavaEventCb>();
    
    public static void RegisterEvent(int ID, JavaEventCb cb) {
        if (s_instance == null) {
            s_instance = new JavaEvents();
        }
        
        // JavaEventCb obj;
        Object obj = s_instance.m_hashmap.put(new Integer(ID), cb);
        
        if (obj != null) {
          eng.writeLog("Duplicate entry for ID " + ID);
          
          RickRollLog.dumpStackTrace("JAVA_EVENTS: Duplicate entry for ID " + ID);
        }
        
//        if ((obj = JavaEvents.s_instance.m_hashmap.put(new Integer(ID), cb)) != null) {
//            eng.writeLog("Duplicate entry for ID " + ID);
//        }
    }

    public static void DispatchEvent(int ID, int value, Object obj) {
        JavaEvents.SendEvent(ID, value, obj);
        if (s_instance == null) {
            return;
        }
        // Is this casting necessary??
        // JavaEventCb cb = (JavaEventCb)JavaEvents.s_instance.m_hashmap.get(new Integer(ID));
        JavaEventCb cb = JavaEvents.s_instance.m_hashmap.get(new Integer(ID));
        if (cb != null) {
            cb.OnEvent(ID, value, obj);
        }
    }

    public static void UnregisterEvent(int ID) {
        if (s_instance == null) {
            return;
        }
        JavaEvents.s_instance.m_hashmap.remove(new Integer(ID));
    }

    public static native void SendEvent(int var0, int var1, Object var2);

    public void OnSelect(int state, Object sender) {
    }

    private JavaEvents() {
        ListenerManager.AddListener(105, this);
    }
}
