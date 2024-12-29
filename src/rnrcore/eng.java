// Decompiled with: CFR 0.152
// Class Version: 5
package rnrcore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;

import players.CarName;
import players.actorveh;
import rickroll.DirectoryScanner;
import rickroll.RickRollConfig;
import rickroll.TransformPKQTToXML;
import rickroll.TransformerFileDecoder;
import rickroll.log.RickRollLog;
import rnrcore.IScriptTask;
import rnrcore.Log;
import rnrcore.SCRperson;
import rnrcore.SCRuniperson;
import rnrcore.anm;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrloggers.ScriptsLogger;
import rnrscenario.consistency.ScenarioStage;

public class eng {
    public static final String TASK_UNSUSPEND = "unsuspend";
    public static final String TASK_PLAY = "play";
    public static final String TASK_END = "end";
    public static final String TASK_FADEIN = "fadein";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;
    public static boolean canShowPagerMessages = true;
    public static boolean noNative = false;
    public static boolean _12HourFormat = false;
    public static boolean _dontShowDLCButton = false;
    private static final Object engineLockLatch;
    private static int engineLockCounter;
    private static final int DELAY_BETWEEN_POLLS = 1000;
    public static boolean CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE;
    public static boolean ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE;

    public static void setUse_12HourTimeFormat(boolean value) {
        _12HourFormat = value;
    }

    public static void setDontShowDLCButton(boolean value) {
        _dontShowDLCButton = value;
    }

    public static boolean useNative() {
        return !noNative;
    }

    public static boolean use_12HourTimeFormat() {
        return _12HourFormat;
    }

    public static void pager(String message) {
        if (!canShowPagerMessages) {
            return;
        }
        eng.CreatePagerMessage(message);
    }

    public static void err(String errcode) {
    	 // RICK: DISABLED, trying to save some disk space 
        // eng.writeLog("eng.err " + errcode);
    }

    public static void log(String errcode) {
    	 // RICK: DISABLED, trying to save some disk space 
        // eng.writeLog("eng.log " + errcode);
    }

    private static native void assertInLog(String var0);

    private static native void CreatePagerMessage(String var0);

    public static native void fatal(String var0);

    public static native void executeScript(String var0, String var1);

    public static native void CreateInfinitScriptAnimation(anm var0);

    public static native void CreateInfinitCycleScriptAnimation(anm var0, double var1);

    public static void writeLog(String str) {
        if (noNative) {
            System.err.println(str);
        } else {
        	// RICK (disabled, too verbose)
        	// RickRollLog.log(str);
        	// END RICK
        	
        	 // RICK: DISABLED, trying to save some disk space 
            // eng.assertInLog(str);
        }
    }

    public static native void play(SCRperson var0);

    public static native void setShow(SCRperson var0, boolean var1);

    public static native int RecieveEvent();

    public static native void playTASK(long var0);

    public static native long CreateTASK(SCRuniperson var0);

    public static native void DeleteTASK(long var0);

    public static native long AddAnimTASKLoop(long var0, String var2, double var3);

    public static native long AddAnimTASK(long var0, String var2, double var3);

    public static native long AddAnimInversTASK(long var0, String var2, double var3);

    public static native long AddAnimTASKLoop(long var0, String var2, double var3, boolean var5);

    public static native long AddAnimTASK(long var0, String var2, double var3, boolean var5);

    public static native long AddAnimInversTASK(long var0, String var2, double var3, boolean var5);

    public static native long AddAnimMixTASK(long var0, String var2, String var3, double var4, double var6, double var8, double var10);

    public static native long AddMergeAnimPositioningTASK(long var0, vectorJ var2, vectorJ var3, vectorJ var4, vectorJ var5, String var6, String var7, String var8, String var9, double var10);

    public static native long AddMergeAnimPositioningTASK_CarWheel_start(long var0, actorveh var2, SCRuniperson var3, vectorJ var4, vectorJ var5, String var6, String var7, String var8, String var9, double var10);

    public static native long AddMergeAnimPositioningTASK_CarWheel_finish(long var0, vectorJ var2, vectorJ var3, actorveh var4, SCRuniperson var5, String var6, String var7, String var8, String var9, double var10);

    public static native void Anim_SetCamera(long var0, long var2);

    public static native void OnEndTASK(long var0, String var2, long var3);

    public static native void OnBegTASK(long var0, String var2, long var3);

    public static native void OnMidTASK(long var0, double var2, double var4, String var6, long var7);

    public static native long AddEventTask(long var0);

    public static native long AddHasteTask(long var0);

    public static native void AddSceneTrackToTask(long var0, long var2);

    public static native void EventTask_onParking(long var0, actorveh var2, boolean var3);

    public static native void EventTask_onBARMessageClosed(long var0, boolean var2);

    public static native void EventTask_onMOTELMessageClosed(long var0, boolean var2);

    public static native void EventTask_onMOTELDevelopFinish(long var0, boolean var2);

    public static native void EventTask_onNewCarCreated(long var0, boolean var2);

    public static native void EventTask_onOfficeMessageClosed(long var0, boolean var2);

    public static native void EventTask_onOfficeBankrupt(long var0, boolean var2);

    public static native void EventTask_onGasStationMessageClosed(long var0, boolean var2);

    public static native void EventTask_onGasStationLeave(long var0, boolean var2);

    public static native void EventTask_onGasStationCame(long var0, boolean var2);

    public static native void EventTask_onWhLeaveGates(long var0, boolean var2);

    public static native void EventTask_onWhCame(long var0, boolean var2);

    public static native void EventTask_onWhLeave(long var0, boolean var2);

    public static native void EventTask_onWhLoad(long var0, boolean var2);

    public static native void EventTask_onWhUnLoad(long var0, boolean var2);

    public static native void EventTask_onWhGoFromEmpty(long var0, boolean var2);

    public static native void EventTask_onWhGoFromLoaded(long var0, boolean var2);

    public static native void EventTask_ExitGame(long var0, boolean var2);

    public static native void EventTask_AnimationEventIn(long var0, boolean var2);

    public static native void EventTask_AnimationEventOut(long var0, boolean var2);

    public static native long AddScriptTask(long var0, IScriptTask var2);

    public static native long AddChangeWorldTask(long var0, String var2, String var3);

    public static native void ConvertVector_Vehicle(long var0, vectorJ var2);

    public static native vectorJ GetVehicle_steeringwheel_pos(long var0);

    public static native vectorJ GetVehicle_steeringwheel_dir(long var0);

    public static native vectorJ GetVehicle_pos(long var0);

    public static native vectorJ GetVehicle_dir(long var0);

    public static native long GetVehicle(long var0);

    public static native long GetVehicleDriver(long var0);

    public static native long CarNode(long var0);

    public static native String GetVehiclePrefix(long var0);

    public static native String GetVehicleNodeName(long var0);

    public static native String GetManPrefix(long var0);

    public static native void SwitchDriver_in_cabin(long var0);

    public static native void SwitchDriver_outside_cabin(long var0);

    public static native void SwitchDriver_nevermind(long var0);

    public static native double GetAnimationLength(long var0);

    public static native long GetCurrentWarehouse();

    public static native long GetCurrentBar();

    public static native long GetCurrentGasStation();

    public static native long GetCurrentRepairShop();

    public static native String GetBarInnerName(long var0);

    public static native String GetBarOutterName(long var0);

    public static native String GetWarehouseInnerName(long var0);

    public static native String GetWarehouseOutterName(long var0);

    public static native String GetGasStationInnerName(long var0);

    public static native String GetGasStationOutterName(long var0);

    public static native String GetRepairShopInnerName(long var0);

    public static native String GetRepairShopOutterName(long var0);

    public static native vectorJ setCurrentOfficeNear(vectorJ var0);

    public static native vectorJ getNamedOfficePosition(String var0);

    public static native int GetXResolution();

    public static native int GetYResolution();

    public static native void SetCrashBind_DO(long var0, int var2, int var3, int var4);

    private static native actorveh CreateCarForPredefinedAnimation(String var0, int var1, matrixJ var2, vectorJ var3);

    public static actorveh CreateCarForScenario(CarName carModelMame, matrixJ matrix, vectorJ position) {
        Log.simpleMessage("loading car for scenario");
        actorveh ware = eng.CreateCarForPredefinedAnimation(carModelMame.getName(), carModelMame.getColor(), matrix, position);
        ware.UpdateCar();
        return ware;
    }

    public static native void scenarioCheckPointReached(ScenarioStage var0);

    @Deprecated
    public static native actorveh CreateCarImmediatly(String var0, matrixJ var1, vectorJ var2);

    @Deprecated
    public static native actorveh CreateCar_onway(String var0, vectorJ var1);

    public static native actorveh CreateAnimatedCar(String var0, String var1, String var2);

    public static native void BlowCar(int var0);

    public static native void console(String var0);

    public static native void doWide(boolean var0);

    private static native void setEscKeyEnabled(boolean var0);

    public static void enableEscKeyScenesSkip() {
        eng.setEscKeyEnabled(true);
    }

    public static void disableEscKeyScenesSkip() {
        eng.setEscKeyEnabled(false);
    }

    public static native int gYear();

    public static native int gMonth();

    public static native int gDate();

    public static native int gHour();

    public static native int gMinuten();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void lock() {
        Object object = engineLockLatch;
        synchronized (object) {
            if (0 == engineLockCounter) {
                eng.lockEngine();
            }
            ++engineLockCounter;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void unlock() {
        Object object = engineLockLatch;
        synchronized (object) {
            if (0 == engineLockCounter) {
                return;
            }
            if (0 == --engineLockCounter) {
                eng.unlockEngine();
            }
        }
    }

    public static native void forceInterruptLock();

    public static native void predefinedAnimationLaunchedFromJava(boolean var0);

    public static native void explosionsWhilePredefinedAnimation(boolean var0);

    public static native boolean canRunScenarioAnimation();

    public static native boolean gameWorldStopped();

    public static void waitUntilEngineCanPlayScene() throws InterruptedException {
        if (CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE && ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE) {
            eng.lock();
            while (!eng.canRunScenarioAnimation()) {
                ScriptsLogger.getInstance().log(Level.INFO, 4, "waiting for engine to enable play animation");
                eng.unlock();
                Thread.sleep(1000L);
                eng.lock();
            }
            eng.predefinedAnimationLaunchedFromJava(true);
            eng.unlock();
        }
        ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = true;
    }

    private static native void lockEngine();

    private static native void unlockEngine();

    public static native boolean introPlayingFirstTime();

    public static native void switchOffIntro();

    public static native void blockEscapeMenu();

    public static native void exceptionDuringGameSerialization();

    public static native void forceSwitchRain(boolean var0);

    public static native String getStringRef(String var0, String var1);

    public static native void reloadScriptObjectsUids();

    public static native void holdresourse(String var0);

    public static native void looseresourse(String var0);

    public static native void setdword(String var0, int var1);

    public static native vectorJ getControlPointPosition(String var0);

    @Deprecated
    public static native void markPointOnMap(vectorJ var0);

    @Deprecated
    public static native void removeMarksOnMap();

    public static native void enableControl();

    public static native void disableControl();

    public static native void pauseGameExceptPredefineAnimation(boolean var0);

    public static native void enableCabinView(boolean var0);

    public static native void returnCameraToGameWorld();

    public static native void getFilesAllyed(String var0, Vector var1);

    public static native void blockSO(int var0);

    public static native void unblockSO(int var0);

    public static native void blockNamedSO(int var0, String var1);

    public static native void unblockNamedSO(int var0, String var1);

    public static native void startMangedFadeAnimation();

    public static native void makePoliceImmunity(boolean var0);

    public static native int getDifficultyLevel();

    public static native boolean getScenarioStatus();

    public static native void setScenarioStatus(boolean var0);

    public static native void setBigRaceStatus(boolean var0);

    public static native void anonusebigrace();

    public static native void setParallelWorldCondition(String var0, String var1);

    public static native void makePayOff(double var0, double var2, double var4, String var6);

    public static native boolean dontQuestItemLost(String var0);

    public static native double visualRating(double var0);

    public static native int visualLeague();

    public static native boolean hasBigraceOrder();

    public static native boolean isBigRaceEnable();

    public static native boolean isBigRaceAvaible();

    public static native boolean isBigRaceInstalled();

    public static native double getCodedLiveRating();

    static {
        try {
            System.loadLibrary("rnr");
        }
        catch (UnsatisfiedLinkError e) {
            Properties prop = System.getProperties();
            System.err.println("java.library.path: " + prop.getProperty("java.library.path"));
            System.err.println("Cannot link rnr");
        }
        engineLockLatch = new Object();
        engineLockCounter = 0;
        CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = true;
        ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = true;
        
        // RICK
        RickRollLog.log(RickRollLog.getVMFlags());
        
        // WORKING
        if(RickRollConfig.DECODE_FILES && !RickRollConfig.SOURCES_DATA_DIR_LIST.isEmpty()) {
        	int myLength = RickRollConfig.SOURCES_DECODED_FILE_EXTENSIONS.length;
        	for(int i = 0; i < myLength; i++) {
        		String currentEncodedExtension = RickRollConfig.SOURCES_ENCODED_FILE_EXTENSIONS[i];
        		String currentDecodedExtension = RickRollConfig.SOURCES_DECODED_FILE_EXTENSIONS[i];
	        	for(String path : RickRollConfig.SOURCES_DATA_DIR_LIST) {
			        File filePath = new File(path);
					DirectoryScanner myDS = new DirectoryScanner(filePath, currentEncodedExtension);
					myDS.recursiveScan();
					if(!myDS.encodedFiles.isEmpty()) {
						TransformerFileDecoder transformerFileDecoder = new TransformerFileDecoder(
								currentEncodedExtension,
								currentDecodedExtension
						);
				        for(String file: myDS.encodedFiles) {
				        	transformerFileDecoder.transform(file);
				        }
					}
	        	}
        	}
        }
        
        // END RICK
    }
}
