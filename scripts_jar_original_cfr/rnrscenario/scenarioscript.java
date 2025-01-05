/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import menu.JavaEvents;
import menu.menues;
import menu.resource.MenuResourcesManager;
import menu.restartgame;
import menuscript.TotalVictoryMenu;
import menuscript.VictoryMenu;
import menuscript.VictoryMenuExitListener;
import players.Crew;
import players.CrewNamesManager;
import rnr.menu.test.FromConsoleLaunch;
import rnr.tech.Code0;
import rnrcore.CoreTime;
import rnrcore.GameXmlSerializator;
import rnrcore.Helper;
import rnrcore.INativeMessageEvent;
import rnrcore.NativeEventController;
import rnrcore.NativeSerializationInterface;
import rnrcore.ScenarioSync;
import rnrcore.ScriptRefStorage;
import rnrcore.UidStorage;
import rnrcore.XmlSerializable;
import rnrcore.XmlSerializationFabric;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrloggers.ScenarioLogger;
import rnrorg.Organizers;
import rnrorg.PickUpEventManager;
import rnrorg.RenewJourmalAfterLoad;
import rnrorg.XmlInit;
import rnrorg.journal;
import rnrorg.organaiser;
import rnrrating.BigRace;
import rnrrating.FactionRater;
import rnrrating.RateSystem;
import rnrscenario.BigRaceScenario;
import rnrscenario.CBCallsStorage;
import rnrscenario.CBVideoStroredCall;
import rnrscenario.CursedHiWay;
import rnrscenario.EndScenario;
import rnrscenario.FirstRun;
import rnrscenario.PoliceScene;
import rnrscenario.Sc_serial;
import rnrscenario.ScenarioFlagsManager;
import rnrscenario.ScenarioPassing;
import rnrscenario.ScenarioSave;
import rnrscenario.SimplePresets;
import rnrscenario.SoBlock;
import rnrscenario.StaticScenarioStuff;
import rnrscenario.cCBVideoCall_Caller;
import rnrscenario.config.Config;
import rnrscenario.config.ConfigManager;
import rnrscenario.configurators.AiChaseConfig;
import rnrscenario.configurators.ChaseCochConfig;
import rnrscenario.configurators.ChaseToRescueDorothyConfig;
import rnrscenario.configurators.EnemyBaseConfig;
import rnrscenario.configurators.SpecialCargoConfig;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import rnrscenario.controllers.ChaseKoh;
import rnrscenario.controllers.EnemyBase;
import rnrscenario.controllers.KohHelpManage;
import rnrscenario.controllers.RaceFailCondition;
import rnrscenario.controllers.RedRockCanyon;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.controllers.chase00090;
import rnrscenario.controllers.chaseTopo;
import rnrscenario.controllers.preparesc00060;
import rnrscenario.controllers.starters.Start0430;
import rnrscenario.controllers.starters.Start0454;
import rnrscenario.missions.DelayedResourceDisposer;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.starters.ConditionChecker;
import rnrscenario.missions.starters.StarterBase;
import rnrscenario.scenes.bankrupt;
import rnrscenario.stage;
import rnrscr.Dialog;
import rnrscr.ILeaveMenuListener;
import rnrscr.MissionDialogs;
import rnrscr.MissionPassanger;
import rnrscr.Office;
import rnrscr.PedestrianManager;
import rnrscr.RaceHistory;
import rnrscr.cSpecObjects;
import rnrscr.cbapparatus;
import rnrscr.drvscripts;
import rnrscr.smi.Newspapers;
import rnrscr.specobjects;
import scenarioMachine.FiniteStateMachine;
import scenarioXml.CbvEventListenerBuilder;
import scenarioXml.InternalScenarioRepresentation;
import scenarioXml.XmlScenarioMachineBuilder;
import scriptActions.BlockSpecialObject;
import scriptActions.PoliceImmunity;
import scriptActions.SetScenarioFlag;
import scriptActions.TrafficAction;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.EventsControllerHelper;
import scriptEvents.ScriptEvent;
import scriptEvents.SpecialObjectEvent;
import scriptEvents.TimeEvent;
import scriptEvents.VoidEvent;
import xmlserialization.AIPlayerSerializator;
import xmlserialization.ActorVehSerializator;
import xmlserialization.AlbumSerialization;
import xmlserialization.BigRaceSerialization;
import xmlserialization.BlockedSOSerializator;
import xmlserialization.CBVideoSerializator;
import xmlserialization.Chase00090Serializator;
import xmlserialization.ChaseKohSerializator;
import xmlserialization.ChaseTopoSerializator;
import xmlserialization.CrewXmlSerialization;
import xmlserialization.CursedHiWaySerializator;
import xmlserialization.DelayedChannelSerializator;
import xmlserialization.EndScenarioSerialization;
import xmlserialization.EnemyBaseSerializator;
import xmlserialization.FactionRaterSerializator;
import xmlserialization.JournalSerialization;
import xmlserialization.KohHelpSerializator;
import xmlserialization.MissionPassangerSerializator;
import xmlserialization.MissionsInitiatedSerialization;
import xmlserialization.MissionsLogSerialization;
import xmlserialization.MissionsSerialization;
import xmlserialization.NewspaperSerialization;
import xmlserialization.ObjectXmlSerializator;
import xmlserialization.OrganizerSerialization;
import xmlserialization.PiterPanDoomedRaceSerializator;
import xmlserialization.PiterPanFinalRaceSerializator;
import xmlserialization.Preparesc00060Serializator;
import xmlserialization.RatingSerialization;
import xmlserialization.RedRockCanyonSerializator;
import xmlserialization.ScenarioFlagsSerializator;
import xmlserialization.ScriptRefXmlSerialization;
import xmlserialization.SoSerialization;
import xmlserialization.StaticScenarioStuffSerializator;
import xmlserialization.UidStorageSerialization;
import xmlserialization.nxs.SerializatorOfAnnotated;
import xmlutils.Node;

public final class scenarioscript
implements ScenarioHost {
    public static final vectorJ OXNARD_OFFICE = new vectorJ(4831.0, -24135.0, 0.0);
    public static final String START_HELP_COCH = "start_help_coch";
    public static final String STARTDOROTHYSAVE = "startDorothySave";
    public static final String TELEPORTTOOFFICE = "teleporttooffice";
    public static final String CHASE_TOPO = "chase_topo";
    public static final String BIG_RACE_SCENARIO = "big_race_scenario";
    public static final String START_1300 = "start_1300";
    public static final String START_1500 = "start_1500";
    public static final String START_1600 = "start_1600";
    public static final String START_2050 = "start_2050";
    public static final String BANKRUPT = "bankrupt";
    public static final String SCENARIO_BANKRUPT = "scenario_bankrupt";
    public static final String BLOWCAR = "blowcar";
    public static final String TEAR_WIREROPE = "tear_wirerope";
    public static final String EVENT_RESUMESCRIPT = "resumescript";
    public static final String EVENT_BANKRUPT = "bankrupt";
    public static final String EVENT_SCENARIO_BANKRUPT = "scenario_bankrupt";
    public static final String EVENT_BLOW = "blowcar";
    public static final String EVENT_TEAR_WIREROPE = "tear_wirerope";
    private static final String[] FSM_NEWGANE = new String[]{"gamebegining", "gamebegining_no_scenario"};
    public static scenarioscript script = null;
    private boolean isInstantOrder = false;
    private static boolean isReleaseMode = false;
    private static boolean f_isTutorialOn = false;
    private static boolean isAutoTestRun = false;
    public static boolean INSTANT_ORDER_ONLY = false;
    public static final String EVENT_SOCIAL_LOOSE = "social loose";
    private static final int SOCIAL_LOOSE_MESSAGE_START_TIME = 8;
    private static final int SOCIAL_LOOSE_MESSAGE_END_TIME = 16;
    private static final int MAX_HOUR_VALUE = 23;
    private static int SCENARIO_ON_DEGUG_FLAG = 0;
    private boolean playerCarHasBlown = false;
    private final Object blownCarFlagLatch = new Object();
    private boolean f_messageEventsInited = false;
    private FiniteStateMachine scenarioMachine = null;
    private ScenarioStage scenarioStage = null;
    private ConfigManager configManager = new ConfigManager();
    private static final String DAFAULT_SAVING_FOLDER = "..\\saves\\";
    private static final String DAFAULT_SAVING_FILE = "gamesave.xml";
    private GameXmlSerializator xmlSerializator = new GameXmlSerializator("..\\saves\\", "gamesave.xml");
    private StageChangedListener defaultGameStatesRestorer = new StageChangedListener(){

        public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
            assert (null != scenarioStage);
            new PoliceImmunity().setValue(false).act();
            BlockSpecialObject blockPoliceAction = new BlockSpecialObject(scenarioscript.this);
            blockPoliceAction.setType("police");
            blockPoliceAction.act();
            BlockSpecialObject blockJohnHouseAction = new BlockSpecialObject(scenarioscript.this);
            blockJohnHouseAction.setType("johnhouse");
            blockJohnHouseAction.act();
            new TrafficAction().setOn(true).act();
            scenarioscript.resetScenarioFlagsToDefault();
            eng.explosionsWhilePredefinedAnimation(false);
            if (0x7FFFFFFD == scenarioStage.getScenarioStage()) {
                int daysDelta;
                int hoursDelta;
                CoreTime currentTime = new CoreTime();
                if (8 > currentTime.gHour()) {
                    hoursDelta = 8 - currentTime.gHour();
                    daysDelta = 1;
                } else if (16 < currentTime.gHour()) {
                    hoursDelta = 23 - currentTime.gHour() + 8;
                    daysDelta = 0;
                } else {
                    hoursDelta = 0;
                    daysDelta = 1;
                }
                EndScenario.getInstance().delayAction("SHOW SOCIAL LOOSE DIALOG", daysDelta, hoursDelta, new VoidEvent(scenarioscript.EVENT_SOCIAL_LOOSE), null);
            }
            eng.forceSwitchRain(true);
        }
    };
    private boolean f_start_help_coch = false;
    private boolean showScenarioLooseDialog = false;
    private boolean scheduleScenarioFinish = false;
    private boolean scheduledScenarioFinishSuccessful = false;
    private boolean clearTasks = true;
    private ScenarioController scenarioController = null;

    public static void setReleaseModeOn() {
        isReleaseMode = true;
    }

    public static void SetInstantOrderOnly() {
        INSTANT_ORDER_ONLY = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean setPlayerCarHasBlown() {
        Object object = this.blownCarFlagLatch;
        synchronized (object) {
            if (!this.playerCarHasBlown) {
                this.playerCarHasBlown = true;
                return true;
            }
            return false;
        }
    }

    FiniteStateMachine gMachine() {
        return this.scenarioMachine;
    }

    public ScenarioStage getStage() {
        return this.scenarioStage;
    }

    public void debugInitStage() {
        this.scenarioStage = new ScenarioStage();
    }

    public static void switchOffScenarioConsistencyChecking() {
        if (null != script && null != scenarioscript.script.scenarioStage) {
            scenarioscript.script.scenarioStage.scenarioStageUndefined();
        }
    }

    public boolean isInstantOrder() {
        return this.isInstantOrder;
    }

    public SoBlock getSoBlocker() {
        return SoBlock.getInstance();
    }

    public FiniteStateMachine getScenarioMachine() {
        return this.scenarioMachine;
    }

    private void clearFSM() {
        if (null != this.scenarioMachine) {
            this.scenarioMachine.clear();
            this.xmlSerializator.removeSerializationTarget(this.scenarioMachine);
            this.scenarioMachine = null;
        }
    }

    private void buildFSM(boolean scenarioStatus) {
        this.initMessageEvents();
        try {
            if (null == this.scenarioMachine) {
                if (scenarioStatus) {
                    InternalScenarioRepresentation scenario = XmlScenarioMachineBuilder.getScenarioMachine("..\\Data\\config\\quests.xml", false);
                    this.scenarioMachine = scenario.getStatesMachine();
                } else {
                    this.scenarioMachine = new FiniteStateMachine();
                }
                CbvEventListenerBuilder cbvBuilder = new CbvEventListenerBuilder("..\\Data\\config\\cbvideocalls.xml", this.scenarioMachine);
                EventsController.getInstance().addListener(cbvBuilder.getWare());
                EventsController.getInstance().addListener(this.scenarioMachine);
                this.xmlSerializator.addSerializationTarget(this.gMachine());
            } else {
                XmlScenarioMachineBuilder.reloadMachine(this.scenarioMachine, "..\\Data\\config\\quests.xml");
            }
        }
        catch (IOException exception) {
            ScenarioLogger.getInstance().machineError("Exception has been catched while loading scenario from XML; exception message: " + exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        }
    }

    private void setBigRaceStatus(boolean bigRaceStatus) {
        eng.setBigRaceStatus(bigRaceStatus);
    }

    private static void resetScenarioFlag(String flag) {
        assert (null != flag);
        SetScenarioFlag flags = new SetScenarioFlag();
        flags.value = true;
        flags.name = flag;
        flags.act();
    }

    private static void resetScenarioFlagsToDefault() {
        scenarioscript.resetScenarioFlag("WarehousesEnabledByScenario");
        scenarioscript.resetScenarioFlag("SavesEnabledByScenario");
        scenarioscript.resetScenarioFlag("MissionsEnebledByScenario");
    }

    private void registerScenarioStageChangeListeners() {
        this.scenarioStage.addListener(EventsController.getInstance());
        this.scenarioStage.addListener(EventsControllerHelper.getInstance());
        this.scenarioStage.addListener(this.xmlSerializator);
        this.scenarioStage.addListener(NativeEventController.getInstance());
        this.scenarioStage.addListener(ScenarioSave.getInstance());
        this.scenarioStage.addListener(new StageChangedListener(){

            public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
                ScenarioGarbageFinder.deleteOutOfDateScenarioObjects("ConditionChecker.allCheckers", ConditionChecker.getAllCheckers(), scenarioStage);
            }
        });
        this.scenarioStage.addListener(new StageChangedListener(){

            public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
                assert (null != scenarioStage);
                eng.scenarioCheckPointReached(scenarioStage);
            }
        });
        this.scenarioStage.addListener(this.defaultGameStatesRestorer);
    }

    private void beginFSM() {
        if (null == this.scenarioMachine) {
            System.err.print("ERRORR. beginFSM has null==scenarioMachine.");
        }
        this.scenarioMachine.activateState(true, FSM_NEWGANE[SCENARIO_ON_DEGUG_FLAG] + "_phase_" + 1);
    }

    void initMessageEvents() {
        if (this.f_messageEventsInited) {
            return;
        }
        this.f_messageEventsInited = true;
        EventsControllerHelper.getInstance().addMessageListener(script, START_HELP_COCH, START_HELP_COCH);
        EventsControllerHelper.getInstance().addMessageListener(script, STARTDOROTHYSAVE, STARTDOROTHYSAVE);
        EventsControllerHelper.getInstance().addMessageListener(script, TELEPORTTOOFFICE, TELEPORTTOOFFICE);
        EventsControllerHelper.getInstance().addMessageListener(script, CHASE_TOPO, CHASE_TOPO);
        EventsControllerHelper.getInstance().addMessageListener(script, BIG_RACE_SCENARIO, BIG_RACE_SCENARIO);
        EventsControllerHelper.getInstance().addMessageListener(script, START_1300, START_1300);
        EventsControllerHelper.getInstance().addMessageListener(script, START_1500, START_1500);
        EventsControllerHelper.getInstance().addMessageListener(script, START_1600, START_1600);
        EventsControllerHelper.getInstance().addMessageListener(script, START_2050, START_2050);
        EventsControllerHelper.getInstance().addMessageListener(script, "bankrupt", "bankrupt");
        EventsControllerHelper.getInstance().addMessageListener(script, "scenario_bankrupt", "scenario_bankrupt");
        EventsControllerHelper.getInstance().addMessageListener(script, "blowcar", "blowcar");
        EventsController.getInstance().addListener(new EventListener(){

            private boolean expectedTuple(List<ScriptEvent> eventTuple) {
                if (1 != eventTuple.size() || !(eventTuple.get(0) instanceof VoidEvent)) {
                    return false;
                }
                return 0 == scenarioscript.EVENT_SOCIAL_LOOSE.compareTo(((VoidEvent)eventTuple.get(0)).getInfo());
            }

            @Override
            public void eventHappened(List<ScriptEvent> eventTuple) {
                if (this.expectedTuple(eventTuple)) {
                    scenarioscript.this.showScenarioLooseDialog = true;
                }
            }
        });
    }

    public void reloadConfig() {
        this.configManager.load();
    }

    public void setGameDifficulty(int value) {
        this.configManager.setGameLevel(value);
    }

    public scenarioscript() {
        script = this;
        FromConsoleLaunch.init();
        this.configManager.addConfig(new EnemyBaseConfig());
        this.configManager.addConfig(new ChaseCochConfig());
        this.configManager.addConfig(new ChaseToRescueDorothyConfig());
        this.configManager.addConfig(new AiChaseConfig());
        this.configManager.addConfig(new SpecialCargoConfig());
        this.configManager.load();
        ConfigManager.setGlobal(this.configManager);
        if (isReleaseMode) {
            eng.canShowPagerMessages = false;
            this.configManager.reloadConfigsOnQuery(false);
            ScenarioGarbageFinder.setFatalOnGarbage(false);
            Logger.getLogger("rnr").setLevel(Level.WARNING);
        } else {
            eng.canShowPagerMessages = true;
            this.configManager.reloadConfigsOnQuery(true);
        }
        if (eng.noNative) {
            this.configManager.setGameLevel(0);
        } else {
            this.configManager.setGameLevel(eng.getDifficultyLevel());
        }
        MissionSystemInitializer.firstInitializeSystem();
        SerializatorOfAnnotated.getInstance().register(Start0430.Checker.getUid(), Start0430.Checker.class);
        SerializatorOfAnnotated.getInstance().register(Start0454.Checker.getUid(), Start0454.Checker.class);
        SerializatorOfAnnotated.getInstance().register(ScenarioStage.getUid(), ScenarioStage.class);
        SerializatorOfAnnotated.getInstance().register("chase00090", chase00090.class);
        SerializatorOfAnnotated.getInstance().register("RaceFailCondition", RaceFailCondition.class);
        XmlSerializationFabric.addRegisterDeSerializationInterface(ActorVehSerializator.getNodeName(), new ActorVehSerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(AIPlayerSerializator.getNodeName(), new AIPlayerSerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(Preparesc00060Serializator.getNodeName(), new Preparesc00060Serializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(Chase00090Serializator.getNodeName(), new Chase00090Serializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(ChaseKohSerializator.getNodeName(), new ChaseKohSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(ChaseTopoSerializator.getNodeName(), new ChaseTopoSerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(CursedHiWaySerializator.getNodeName(), new CursedHiWaySerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(EnemyBaseSerializator.getNodeName(), new EnemyBaseSerializator((EnemyBaseConfig)this.configManager.getConfig(0), this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(KohHelpSerializator.getNodeName(), new KohHelpSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(PiterPanDoomedRaceSerializator.getNodeName(), new PiterPanDoomedRaceSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(PiterPanFinalRaceSerializator.getNodeName(), new PiterPanFinalRaceSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(RedRockCanyonSerializator.getNodeName(), new RedRockCanyonSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(BlockedSOSerializator.getNodeName(), new BlockedSOSerializator());
        Chase00090Serializator.setHost(this);
        this.xmlSerializator.addSerializationTarget(new XmlSerializable(){
            private static final String MY_NODE = "SCENARIO_TECH";

            public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
                stream.println(String.format("<%s>%b</%s>", MY_NODE, scenarioscript.this.scheduleScenarioFinish, MY_NODE));
            }

            public void loadFromNode(org.w3c.dom.Node node) {
                if (null != node) {
                    scenarioscript.this.scheduleScenarioFinish = Boolean.parseBoolean(node.getTextContent());
                }
            }

            public void yourNodeWasNotFound() {
            }

            public String getRootNodeName() {
                return MY_NODE;
            }
        });
        this.xmlSerializator.addSerializationTargetExclusively(new XmlSerializable(){

            public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
                if (null != scenarioscript.this.scenarioStage) {
                    SerializatorOfAnnotated.getInstance().saveState(stream, scenarioscript.this.scenarioStage);
                }
            }

            public void loadFromNode(org.w3c.dom.Node node) {
                if (null != node) {
                    scenarioscript.this.scenarioStage = (ScenarioStage)SerializatorOfAnnotated.getInstance().loadStateOrNull(new Node(node));
                    if (null == scenarioscript.this.scenarioStage) {
                        scenarioscript.this.scenarioStage = new ScenarioStage();
                    }
                    scenarioscript.this.registerScenarioStageChangeListeners();
                    scenarioscript.this.setCallbackOnScenarioFinish();
                }
            }

            public void yourNodeWasNotFound() {
                scenarioscript.this.scenarioStage = new ScenarioStage();
                scenarioscript.this.registerScenarioStageChangeListeners();
                scenarioscript.this.setCallbackOnScenarioFinish();
            }

            public String getRootNodeName() {
                return ScenarioStage.getUid();
            }
        });
        this.xmlSerializator.addSerializationTargetExclusively(ScriptRefXmlSerialization.getInstance());
        this.xmlSerializator.addSerializationTargetExclusively(UidStorageSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(CrewXmlSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(BigRaceSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(SoSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(EndScenarioSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(ObjectXmlSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(StaticScenarioStuffSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionsSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionsLogSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionsInitiatedSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(NewspaperSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionPassangerSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(ScenarioFlagsSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(FactionRaterSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(RatingSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(CBVideoSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(DelayedChannelSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(OrganizerSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(JournalSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(AlbumSerialization.getInstance());
        NativeSerializationInterface.setGameSerializator(this.xmlSerializator);
        new FirstRun().start();
        new RaceHistory();
        eng.writeLog("Game started");
    }

    public void playBankrupt() {
        this.bankrupt();
    }

    public void bankrupt() {
        if (this.isInstantOrder) {
            this.menu_bankrupt(null);
        } else {
            bankrupt.setSimpleBankrupt();
            ScenarioSync.setPlayScene("bankrupt");
        }
    }

    public void scenario_bankrupt() {
        bankrupt.setScenarioBankrupt();
        ScenarioSync.setPlayScene("bankrupt");
    }

    public boolean arrested(int quilt) {
        System.out.println("arrested: " + quilt);
        EventsControllerHelper.messageEventHappened("Scenario Arrested");
        return PoliceScene.reserved_for_scene;
    }

    public void entereventSO(int lastzone) {
        specobjects specOb = specobjects.getInstance();
        switch (lastzone) {
            case 1: {
                specOb.last_SO_zone = 2;
                break;
            }
            case 2: {
                specOb.last_SO_zone = 32;
                break;
            }
            case 3: {
                specOb.last_SO_zone = 16;
                break;
            }
            case 4: {
                specOb.last_SO_zone = 8;
                break;
            }
            case 5: {
                specOb.last_SO_zone = 128;
                break;
            }
            case 6: {
                specOb.last_SO_zone = 4;
                break;
            }
            case 7: {
                specOb.last_SO_zone = 1;
                break;
            }
            case 8: {
                specOb.last_SO_zone = 64;
                break;
            }
            case 9: {
                specOb.last_SO_zone = 1024;
            }
        }
        cSpecObjects spec = specOb.getCurrentObject();
        if (null == spec) {
            return;
        }
        EventsController.getInstance().eventHappen(new SpecialObjectEvent(spec, SpecialObjectEvent.EventType.enter), new TimeEvent(new CoreTime()));
    }

    void activatedF2() {
        specobjects specOb = specobjects.getInstance();
        cSpecObjects spec = specOb.getCurrentObject();
        if (null != spec) {
            EventsController.getInstance().eventHappen(new SpecialObjectEvent(spec, SpecialObjectEvent.EventType.f2), new TimeEvent(new CoreTime()));
        }
    }

    void exitSO() {
        specobjects specOb = specobjects.getInstance();
        cSpecObjects spec = specOb.getCurrentObject();
        if (null != spec) {
            EventsController.getInstance().eventHappen(new SpecialObjectEvent(spec, SpecialObjectEvent.EventType.exit), new TimeEvent(new CoreTime()));
        }
    }

    public CBVideoStroredCall getStoredCall(String name) {
        return CBCallsStorage.getInstance().getStoredCall(name);
    }

    public void launchCall(String name) {
        CBVideoStroredCall qu = this.getStoredCall(name);
        if (qu == null) {
            eng.writeLog("Cannot find call named " + name + " in launchCall.");
        } else {
            qu.makecall();
        }
    }

    public void immediateLaunchCall(String name) {
        CBVideoStroredCall qu = this.getStoredCall(name);
        if (qu == null) {
            eng.writeLog("Cannot find call named " + name + " in launchCall.");
        } else {
            qu.makeImmediateCall();
        }
    }

    void start_help_coch() {
        if (this.f_start_help_coch) {
            return;
        }
        this.f_start_help_coch = true;
        if (null == KohHelpManage.getInstance()) {
            KohHelpManage.constructSingleton(this);
        }
        KohHelpManage.getInstance().start();
    }

    void big_race_scenario() {
        new BigRaceScenario();
    }

    void start_1300() {
        new CursedHiWay();
    }

    void start_1500() {
        new RedRockCanyon(this, false);
    }

    void start_1600() {
        Config enemyBaseConfig = this.configManager.getConfig(0);
        assert (enemyBaseConfig instanceof EnemyBaseConfig) : "illegal config type";
        new EnemyBase((EnemyBaseConfig)enemyBaseConfig, this);
    }

    void start_2050() {
        new ChaseKoh(this, false);
    }

    public void enter_moat() {
        this.blowcar();
    }

    public chaseTopo constructChaseTopo() {
        return new chaseTopo(this, (SpecialCargoConfig)this.configManager.getConfig(4));
    }

    public void chase_topo() {
        ScenarioSave.getInstance().CHASETOPO = this.constructChaseTopo();
        ScenarioSave.getInstance().CHASETOPO.start();
    }

    public matrixJ getChaseTopoMatDakota() {
        if (ScenarioSave.getInstance() != null && ScenarioSave.getInstance().CHASETOPO != null) {
            return ScenarioSave.getInstance().CHASETOPO.getMatDakota();
        }
        return null;
    }

    public vectorJ getChaseTopoPosDakota() {
        if (ScenarioSave.getInstance() != null && ScenarioSave.getInstance().CHASETOPO != null) {
            return ScenarioSave.getInstance().CHASETOPO.getPosDakota();
        }
        return null;
    }

    public void chaseTopoexitAnimationDakota() {
        if (ScenarioSave.getInstance() != null && ScenarioSave.getInstance().CHASETOPO != null) {
            ScenarioSave.getInstance().CHASETOPO.exitAnimation_dakota();
        }
    }

    public void chaseTopoexitAnimationPunktA() {
        if (ScenarioSave.getInstance() != null && ScenarioSave.getInstance().CHASETOPO != null) {
            ScenarioSave.getInstance().CHASETOPO.exitAnimation_punktA();
        }
    }

    public void parkOnPunktB() {
        if (ScenarioSave.getInstance() != null && ScenarioSave.getInstance().CHASETOPO != null) {
            ScenarioSave.getInstance().CHASETOPO.parkOnPunktB();
        }
    }

    public SimplePresets preparePunctBMatrix() {
        return chaseTopo.preparePunctBMatrix();
    }

    public void exitAnimation_punktB() {
        if (ScenarioSave.getInstance() != null && ScenarioSave.getInstance().CHASETOPO != null) {
            ScenarioSave.getInstance().CHASETOPO.exitAnimation_punktB();
        }
    }

    public SimplePresets prepareDarkTruckMatrix() {
        return chaseTopo.prepareDarkTruckMatrix();
    }

    public void finishChaseTopo() {
        if (null != ScenarioSave.getInstance() && null != ScenarioSave.getInstance().CHASETOPO) {
            ScenarioSave.getInstance().CHASETOPO.finish();
            ScenarioSave.getInstance().CHASETOPO = null;
        }
    }

    public void before_save() {
        Sc_serial.getInstance().recieve();
    }

    public void startDorothySave() {
        new preparesc00060(this, false).start();
    }

    public static void compactUids() {
        ScriptRefStorage.getInstance().compact();
    }

    public void sheduleScenarioStopOnNextFrame(boolean successfully) {
        this.scheduleScenarioFinish = true;
        this.scheduledScenarioFinishSuccessful = successfully;
        eng.blockEscapeMenu();
    }

    void run_0_ceconds() {
        ScenarioSave.getInstance().run_0();
        if (null != this.scenarioMachine && null != this.scenarioStage && this.scheduleScenarioFinish && 0 == this.scenarioMachine.getCurrentActiveStatesCount() && !this.scenarioStage.isScenarioFinished()) {
            this.scenarioStage.scenarioFinished(this.scheduledScenarioFinishSuccessful);
            this.scheduleScenarioFinish = false;
        }
        if (menues.cancreate_somenu() && this.showScenarioLooseDialog) {
            this.looseScenario();
            VictoryMenu.createLooseSocial(new VictoryMenuExitListener(){

                public void OnMenuExit(int result) {
                    if (0 == result) {
                        if (!Helper.hasContest()) {
                            TotalVictoryMenu.createGameOverTotal(new VictoryMenuExitListener(){

                                public void OnMenuExit(int result) {
                                    if (1 == result) {
                                        JavaEvents.SendEvent(23, 1, null);
                                    }
                                }
                            });
                        }
                    } else assert (false);
                }
            });
            this.showScenarioLooseDialog = false;
        }
    }

    void run_3_ceconds() {
        Crew.rotateNonLoadedModels();
        if (!menues.cancreate_messagewindow()) {
            return;
        }
        System.err.flush();
        ScenarioSave.getInstance().run_3();
        PedestrianManager.getInstance().process();
        SoBlock.getInstance().process();
        BigRace.process();
    }

    void run_60_ceconds() {
        if (!menues.cancreate_messagewindow()) {
            return;
        }
        ScenarioSave.getInstance().run_60();
    }

    void run_600_ceconds() {
        if (!menues.cancreate_messagewindow()) {
            return;
        }
        ScenarioSave.getInstance().run_600();
        cbapparatus.getInstance().clearFinishedCalls();
    }

    void tickMoveTime() {
        BigRace.process();
    }

    public boolean animaterun(double dt, anm Obj) {
        return Obj.animaterun(dt);
    }

    public void tutorial() {
        f_isTutorialOn = true;
    }

    public static void setTutorial(boolean value) {
        f_isTutorialOn = value;
    }

    public static void setAutotestRun(boolean value) {
        isAutoTestRun = value;
    }

    public static void turnScenarioOff() {
        scenarioscript.resetScenarioFlagsToDefault();
        scenarioscript.resetScenarioFlag("Dorothy_is_available");
        SCENARIO_ON_DEGUG_FLAG = 1;
    }

    public static boolean isScenarioOn() {
        return SCENARIO_ON_DEGUG_FLAG != 1;
    }

    public static boolean isTutorialOn() {
        return f_isTutorialOn;
    }

    public static boolean isAuotestRun() {
        return isAutoTestRun;
    }

    public void teleporttooffice() {
        Office.teleportPlayer();
    }

    public void menu_bankrupt(ILeaveMenuListener listener) {
        menues.gameoverBankrupt(listener);
    }

    public void blowcar() {
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
    }

    public void officeKeyboardHide() {
        JavaEvents.SendEvent(6007, 0, null);
    }

    public void winScenario() {
        JavaEvents.SendEvent(40, 1, null);
    }

    public void looseScenario() {
        JavaEvents.SendEvent(40, 2, null);
    }

    public void winRace() {
        JavaEvents.SendEvent(40, 3, null);
    }

    public void looseRace() {
        JavaEvents.SendEvent(40, 4, null);
    }

    private void initEventsListeners() {
        script.initMessageEvents();
        Helper.addNativeEventListener(new CResumeScript());
        Helper.addNativeEventListener(new CBlowCar());
        Helper.addNativeEventListener(new CTearWireRope());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startNewGame() {
        Object object = this.blownCarFlagLatch;
        synchronized (object) {
            this.playerCarHasBlown = false;
        }
        stage.resetInterruptionStatus();
        this.configManager.load();
        this.configManager.setGameLevel(eng.getDifficultyLevel());
        this.f_messageEventsInited = false;
        cCBVideoCall_Caller.initialize();
        ScenarioFlagsManager.init();
        Dialog.init();
        PickUpEventManager.init();
        EventsControllerHelper.init();
        MissionSystemInitializer.initializeSystem();
        BigRaceScenario.init();
        NativeEventController.init();
        this.initEventsListeners();
        PoliceScene.reserved_for_scene = false;
        restartgame.restart();
        boolean scenarioStatus = eng.getScenarioStatus();
        if (!scenarioStatus) {
            this.buildFSM(false);
            this.setBigRaceStatus(true);
            eng.anonusebigrace();
        } else {
            this.buildFSM(true);
            this.setBigRaceStatus(false);
        }
        this.scenarioStage = new ScenarioStage();
        this.registerScenarioStageChangeListeners();
        this.setCallbackOnScenarioFinish();
        this.beginFSM();
        CBCallsStorage.getInstance().init();
        XmlInit.read();
        Vector<String> filenames = new Vector<String>();
        eng.getFilesAllyed("missions", filenames);
        MissionSystemInitializer.getMissionsManager().deinitialize();
        MissionSystemInitializer.getMissionsManager().initialize(filenames);
        eng.blockSO(64);
        eng.blockSO(1024);
        PedestrianManager.getInstance().setPopulation(0);
        try {
            ScenarioPassing.init();
        }
        catch (ScenarioPassing.InitializedException e) {
            eng.err(e.toString());
        }
        MenuResourcesManager.holdResources();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startQuickRace() {
        Object object = this.blownCarFlagLatch;
        synchronized (object) {
            this.playerCarHasBlown = false;
        }
        ScenarioFlagsManager.init();
        Dialog.init();
        PickUpEventManager.init();
        EventsControllerHelper.init();
        MissionSystemInitializer.initializeSystem();
        BigRaceScenario.init();
        NativeEventController.init();
        this.initEventsListeners();
        PoliceScene.reserved_for_scene = false;
        this.isInstantOrder = true;
        restartgame.restart();
        eng.blockSO(8);
        eng.blockSO(4);
        eng.blockSO(1);
        eng.blockSO(64);
        eng.blockSO(1024);
        PedestrianManager.getInstance().setPopulation(0);
        try {
            ScenarioPassing.init();
        }
        catch (ScenarioPassing.InitializedException e) {
            eng.err(e.toString());
        }
        MissionSystemInitializer.getMissionsManager().deinitialize();
        MenuResourcesManager.holdResources();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadGame() {
        Object object = this.blownCarFlagLatch;
        synchronized (object) {
            this.playerCarHasBlown = false;
        }
        ScriptRefStorage.clearRefferaceTable();
        stage.resetInterruptionStatus();
        this.configManager.load();
        this.configManager.setGameLevel(eng.getDifficultyLevel());
        cCBVideoCall_Caller.initialize();
        ScenarioFlagsManager.init();
        Dialog.init();
        PickUpEventManager.init();
        EventsControllerHelper.init();
        MissionSystemInitializer.initializeSystem();
        BigRaceScenario.init();
        NativeEventController.init();
        this.initEventsListeners();
        PoliceScene.reserved_for_scene = false;
        restartgame.restart();
        this.buildFSM(true);
        XmlInit.read();
        CBCallsStorage.getInstance().init();
        Vector<String> filenames = new Vector<String>();
        eng.getFilesAllyed("missions", filenames);
        MissionSystemInitializer.getMissionsManager().deinitialize();
        MissionSystemInitializer.getMissionsManager().initialize(filenames);
        MenuResourcesManager.holdResources();
        new RenewJourmalAfterLoad();
    }

    public void deinitGame() {
        ScenarioSync.gameWentInMainMenu();
        ObjectXmlSerializator.getInstance().cleanUp();
        this.scheduleScenarioFinish = false;
        this.f_start_help_coch = false;
        this.f_messageEventsInited = false;
        cbapparatus.getInstance().clearAllCalls();
        CBCallsStorage.deinit();
        MissionPassanger.deinit();
        Newspapers.deinit();
        Dialog.deInit();
        EndScenario.deinit();
        PedestrianManager.deinit();
        XmlInit.deinit();
        Organizers.deinit();
        BigRaceScenario.deinit();
        NativeEventController.deinit();
        if (null != this.scenarioController) {
            this.scenarioController.gameDeinitLaunched();
        }
        PoliceScene.reserved_for_scene = false;
        StaticScenarioStuff.makeReadyCursedHiWay(false);
        StaticScenarioStuff.makeReadyPreparesc00060(false);
        this.isInstantOrder = false;
        this.clearFSM();
        cCBVideoCall_Caller.deinitialize();
        organaiser.deinit();
        journal.deinit();
        PedestrianManager.getInstance().setPopulation(0);
        try {
            ScenarioPassing.deinit();
        }
        catch (ScenarioPassing.NotInitializedException e) {
            eng.err(e.toString());
        }
        BigRace.deinit();
        MissionSystemInitializer.deinitializeSystem();
        StarterBase.deinitScenarioMissionsStarters();
        DelayedResourceDisposer.deinit();
        MenuResourcesManager.leaveResources();
        Crew.getInstance().deinit();
        SoBlock.deinit();
        PickUpEventManager.deinit();
        MissionDialogs.deinit();
        ScenarioFlagsManager.deinit();
        FactionRater.deinit();
        RateSystem.deinit();
        if (null != this.scenarioStage) {
            this.scenarioStage.scenarioUnloaded();
        }
        EventsControllerHelper.deinit();
        EventsController.deinit();
        ScriptRefStorage.getInstance().deinit();
        UidStorage.getInstance().reset();
        if (this.clearTasks) {
            ScenarioSave.getInstance().gameDeinitLaunched();
        }
        CrewNamesManager.deinit();
        if (!eng.noNative && !ScriptRefStorage.getRefferaceTable().isEmpty()) {
            eng.fatal("someone added ref to ScriptRefStorage while java were deinitting");
        }
    }

    public void doNotClearTasksOnGameDeinit() {
        this.clearTasks = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void backdoorDeinitLaunched() {
        if (null != this.scenarioStage) {
            Object object = this.scenarioStage.getLatch();
            synchronized (object) {
                boolean scenarioInEnemyBaseStage;
                boolean bl = scenarioInEnemyBaseStage = EnemyBase.class.getAnnotation(ScenarioClass.class).scenarioStage() == this.scenarioStage.getScenarioStage();
                if (scenarioInEnemyBaseStage && null != EnemyBase.getInstance()) {
                    EnemyBase.getInstance().deleteScenesResources();
                }
            }
        }
    }

    public void backdoorKillLaunched() {
        this.backdoorDeinitLaunched();
    }

    public void registerController(ScenarioController controller) {
        if (null == this.scenarioController) {
            this.scenarioController = controller;
        } else {
            String errorMessage = null == controller ? "Attempt to register controller, when previous was not deleted." : "Attempt to register controller, when previous was not deleted. Incloming controller class: " + controller.getClass().getName();
            errorMessage = errorMessage + "Current controller class: " + this.scenarioController.getClass().getName();
            if (isReleaseMode) {
                eng.err(errorMessage);
            } else {
                eng.fatal(errorMessage);
            }
        }
    }

    public void unregisterController(ScenarioController controller) {
        if (this.scenarioController == controller) {
            this.scenarioController = null;
        } else {
            String errorMessage;
            String string = errorMessage = null == controller ? "Attempt to unregister controller, which has not been registered." : "Attempt to unregister controller, which has not been registered. Controller class: " + controller.getClass().getName();
            if (isReleaseMode) {
                eng.err(errorMessage);
            } else {
                eng.fatal(errorMessage);
            }
        }
    }

    private void setCallbackOnScenarioFinish() {
        this.scenarioStage.setCallbackOnScenarioFinish(new Code0(){

            public void execute() {
                eng.setBigRaceStatus(true);
                eng.anonusebigrace();
            }
        });
    }

    static class CTearWireRope
    implements INativeMessageEvent {
        CTearWireRope() {
        }

        public String getMessage() {
            return "tear_wirerope";
        }

        public void onEvent(String message) {
            if (null != EnemyBase.getInstance()) {
                EnemyBase.getInstance().assault_succeded();
                eng.setdword("DWORD_EnemyBaseWireRope", 0);
            }
        }

        public boolean removeOnEvent() {
            return false;
        }
    }

    static class CBlowCar
    implements INativeMessageEvent {
        CBlowCar() {
        }

        public String getMessage() {
            return "blowcar";
        }

        public void onEvent(String message) {
            if (script.setPlayerCarHasBlown()) {
                script.blowcar();
            }
        }

        public boolean removeOnEvent() {
            return false;
        }
    }

    static class CResumeScript
    implements INativeMessageEvent {
        CResumeScript() {
        }

        public String getMessage() {
            return scenarioscript.EVENT_RESUMESCRIPT;
        }

        public void onEvent(String message) {
            ScenarioSync.resumeScene();
        }

        public boolean removeOnEvent() {
            return false;
        }
    }
}

