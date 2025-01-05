/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import menuscript.BarMenu;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import rnrcore.CoreTime;
import rnrloggers.MissionsLogger;
import rnrorg.MissionEventsMaker;
import rnrorg.ScenarioMissionItem;
import rnrorg.ScenarioMissions;
import rnrorg.journal;
import rnrorg.organaiser;
import rnrscenario.ScenarioFlagsManager;
import rnrscenario.missions.DelayedResourceDisposer;
import rnrscenario.missions.MissionDepentantLoadElement;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.MissionsController;
import rnrscenario.missions.PointListExtractor;
import rnrscenario.missions.PriorityTable;
import rnrscenario.missions.ScenarioMission;
import rnrscenario.missions.StartMissionListeners;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.map.AbstractMissionsMap;
import rnrscenario.missions.map.PointsController;
import rnrscenario.missions.requirements.MissionAppearConditions;
import rnrscenario.missions.requirements.MissionsLog;
import rnrscenario.missions.requirements.Requirement;
import rnrscenario.missions.requirements.RequirementsCreationException;
import rnrscenario.missions.requirements.RequirementsFactory;
import rnrscenario.scenarioscript;
import rnrscenario.sctask;
import rnrscr.parkingplace;
import scenarioUtils.Pair;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;
import scenarioXml.XmlNodeDataProcessor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MissionManager
extends sctask {
    private static final int MISSIONS_CACHE_CAPACITY = 300;
    private static final int DEFAULT_ERROR_MESSAGE_CAPACITY = 128;
    private static final int RUN_FREQUENCY = 3;
    private static final String INFO_LOADING_ERROR = "failed to extract mission info from xml node: ";
    private static final int BASE_MISSION_PRIORITY = 10;
    private static boolean isMissionsActive = true;
    private static boolean debugModeOn = true;
    private static boolean agressiveModeOn = false;
    private final MissionAppearConditions requirementsForMissionActivation = new MissionAppearConditions();
    private final List<MissionsController> missionsWaiters = new LinkedList<MissionsController>();
    private PointsController pointsController = PointsController.getInstance();
    private PriorityTable missionsPriorities = new PriorityTable();
    private List<MissionPlacement> activatedMissions = new LinkedList<MissionPlacement>();
    private List<MissionPlacement> finishedMissions = new LinkedList<MissionPlacement>();
    private HashMap<String, HashMap<String, MissionPlacement>> activatedDependentMissions = new HashMap();
    private Map<String, MissionPlacement> missionsPool = new LinkedHashMap<String, MissionPlacement>(300);
    private Map<String, MissionPlacement> dependentMissionsPool = new LinkedHashMap<String, MissionPlacement>(300);
    private Map<String, MissionPlacement> asideMissionsPool = new LinkedHashMap<String, MissionPlacement>(300);
    private boolean initialized = false;
    private boolean loading = false;
    private boolean msEnable = true;
    private boolean mdebug = false;
    private static boolean result_readFile_loadMission = false;

    public MissionPlacement getMissionPlacement(String mname) {
        MissionPlacement mp = this.missionsPool.get(mname);
        if (mp == null && null == (mp = this.dependentMissionsPool.get(mname))) {
            for (MissionPlacement mp1 : this.activatedMissions) {
                if (mname.compareTo(mp1.getInfo().getName()) != 0) continue;
                mp = mp1;
                break;
            }
            if (null == mp) {
                for (MissionPlacement mp1 : this.finishedMissions) {
                    if (mname.compareTo(mp1.getInfo().getName()) != 0) continue;
                    mp = mp1;
                    break;
                }
                if (null == mp && (mp = this.asideMissionsPool.get(mname)) == null) {
                    return null;
                }
            }
        }
        return mp;
    }

    public MissionInfo getMissionInfo(String mname) {
        MissionPlacement mp = this.getMissionPlacement(mname);
        if (null == mp) {
            return null;
        }
        return mp.getInfo();
    }

    public boolean getMissionSystemEnable() {
        return this.msEnable;
    }

    public void setMissionSystemEnable(boolean v) {
        this.msEnable = v;
    }

    public static int getParkingPlace(String mission, String point) {
        return MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission).getParkingFromLock(point);
    }

    public static parkingplace getParking(String mission, String point) {
        return MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission).getParking(point);
    }

    public static boolean getMSEnable() {
        return MissionSystemInitializer.getMissionsManager().getMissionSystemEnable();
    }

    public static boolean isDepended(String main, String dep) {
        MissionInfo info = MissionSystemInitializer.getMissionsManager().getMissionInfo(main);
        return info != null && info.getDependent().contains(dep);
    }

    public static void setMSEnable(boolean v) {
        MissionSystemInitializer.getMissionsManager().setMissionSystemEnable(v);
    }

    public static boolean clickMenu() {
        if (MissionSystemInitializer.getMissionsManager().mdebug) {
            return BarMenu.AnyStartDialogOrExit();
        }
        return false;
    }

    public static void setMDebug(boolean v) {
        MissionSystemInitializer.getMissionsManager().mdebug = v;
    }

    public static void declineMissions() {
        LinkedList<String> list = new LinkedList<String>();
        for (MissionPlacement m : MissionSystemInitializer.getMissionsManager().activatedMissions) {
            if (!m.getInfo().isScenario()) continue;
            list.add(m.getInfo().getName());
        }
        organaiser.declineActiveMissions(list);
        DelayedResourceDisposer.getInstance().setAllToDispose();
        journal.getInstance().declineAll();
    }

    public void initialize(Collection<String> fileList) {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.uploadMissions(fileList);
    }

    public void deinitialize() {
        if (!this.initialized) {
            return;
        }
        this.initialized = false;
        StartMissionListeners.deinit();
        MissionsLog.deinit();
        PointsController.deinitialize();
        this.pointsController = PointsController.getInstance();
        RequirementsFactory.deinit();
        this.missionsWaiters.clear();
        this.activatedMissions.clear();
        this.finishedMissions.clear();
        this.activatedDependentMissions.clear();
        this.missionsPool.clear();
        this.dependentMissionsPool.clear();
        this.asideMissionsPool.clear();
    }

    public static void setActive(boolean value) {
        isMissionsActive = value;
    }

    public static void setDebugMode(boolean value) {
        debugModeOn = value;
    }

    public static void setAgressiveMode(boolean value) {
        agressiveModeOn = value;
    }

    MissionManager(AbstractMissionsMap map) {
        super(3, false);
        assert (null != map) : "map must be non-null reference";
        super.start();
        RequirementsFactory.setPriorityTable(this.missionsPriorities);
    }

    public PriorityTable getPriorityTable() {
        return this.missionsPriorities;
    }

    void addMissionController(MissionsController controller) {
        assert (null != controller) : "controller must be valid non-null reference";
        this.missionsWaiters.add(controller);
    }

    private boolean needDeactivateMisssion(MissionsLog.MissionState state) {
        if (null == state || state.missionFinished()) {
            return true;
        }
        return !state.getOccuredEvents().contains((Object)MissionsLog.Event.MISSION_ACCEPTED) && !state.getOccuredEvents().contains((Object)MissionsLog.Event.PLAYER_DECLINED_MISSION);
    }

    private boolean isMissionActual(String missionName) {
        MissionsLog.MissionState state = MissionsLog.getInstance().getMissionState(missionName);
        if (null != state && state.missionFinished()) {
            return DelayedResourceDisposer.getInstance().hasChannelsForMission(missionName);
        }
        if (null != state && state.getOccuredEvents().contains((Object)MissionsLog.Event.MISSION_ACCEPTED)) {
            return true;
        }
        return this.requirementsForMissionActivation.missionAvalible(missionName);
    }

    private void unloadMissionUtility(String missionName, MissionPlacement placement, boolean isActiveMission, boolean placeBackInPool, boolean unloaddep) {
        this.unloadMission(missionName);
        if (placeBackInPool) {
            if (isActiveMission) {
                this.missionsPool.put(missionName, placement);
            } else {
                this.dependentMissionsPool.put(missionName, placement);
            }
        } else {
            this.finishedMissions.add(placement);
        }
        if (this.activatedDependentMissions.containsKey(missionName) && unloaddep) {
            HashMap<String, MissionPlacement> dependantMissions = this.activatedDependentMissions.get(missionName);
            if (null == dependantMissions) {
                return;
            }
            Set<Map.Entry<String, MissionPlacement>> keys = dependantMissions.entrySet();
            for (Map.Entry<String, MissionPlacement> mission : keys) {
                this.unloadMissionUtility(mission.getKey(), mission.getValue(), false, placeBackInPool, unloaddep);
            }
            this.activatedDependentMissions.remove(missionName);
        }
    }

    private void deactivateUnactualMissions() {
        Iterator<MissionPlacement> loadedMissionNameIter = this.activatedMissions.iterator();
        while (loadedMissionNameIter.hasNext()) {
            MissionsLog.MissionState state;
            MissionPlacement missionAndPoints = loadedMissionNameIter.next();
            MissionInfo mission = missionAndPoints.getInfo();
            String missionName = mission.getName();
            if (this.isMissionActual(missionName) || null == (state = MissionsLog.getInstance().getMissionState(missionName)) && agressiveModeOn) continue;
            for (InformationChannelData channelData : missionAndPoints.getInfo().getAllChannels()) {
                this.pointsController.freePoints(channelData.getPlacesNames(), missionAndPoints);
            }
            String s = missionAndPoints.getInfo().getQuestItemPlacement();
            if (null != s) {
                ArrayList<String> pNames = new ArrayList<String>();
                pNames.add(s);
                this.pointsController.freePoints(pNames, missionAndPoints);
            }
            boolean check = null == state;
            check |= state != null && state.missionFinished();
            check &= !mission.isScenarioMission();
            if (!(check &= !mission.isSelfPlacedMission()) || !this.needDeactivateMisssion(state)) continue;
            MissionsLogger.getInstance().doLog("Deactivated mission: " + missionName, Level.INFO);
            if (null != state) {
                boolean placeBackInPool = !mission.isUnique() && !state.missionFinished();
                this.unloadMissionUtility(missionName, missionAndPoints, true, placeBackInPool, !state.missionFinished());
            }
            loadedMissionNameIter.remove();
        }
    }

    private boolean hasMissionFreePoints(MissionPlacement missionPlacement) {
        MissionInfo info = missionPlacement.getInfo();
        assert (null != info) : "info must be non-null reference";
        for (InformationChannelData channelData : info.getAllChannels()) {
            if (this.pointsController.hasGroupFreePoint(channelData.getPlacesNames(), missionPlacement)) continue;
            return false;
        }
        String s = info.getQuestItemPlacement();
        if (null != s) {
            ArrayList<String> pNames = new ArrayList<String>();
            pNames.add(s);
            if (!this.pointsController.hasGroupFreePoint(pNames, missionPlacement)) {
                return false;
            }
        }
        return true;
    }

    private List<MissionDepentantLoadElement> createDependableMissionsLoadInformation(String parentName, MissionPlacement mi) {
        LinkedList<MissionDepentantLoadElement> missionChainElements = new LinkedList<MissionDepentantLoadElement>();
        missionChainElements.add(new MissionDepentantLoadElement(parentName, mi));
        LinkedList<String> breadthFirstSearchQueue = new LinkedList<String>();
        breadthFirstSearchQueue.addAll(mi.getInfo().getDependent());
        while (!breadthFirstSearchQueue.isEmpty()) {
            String dependentMissionToUpload = (String)breadthFirstSearchQueue.poll();
            MissionPlacement placement = this.dependentMissionsPool.get(dependentMissionToUpload);
            if (null == placement) continue;
            missionChainElements.addAll(this.createDependableMissionsLoadInformation(mi.getInfo().getName(), placement));
        }
        return missionChainElements;
    }

    @Override
    public void run() {
        String[] arrayMissionNames;
        if (debugModeOn || !isMissionsActive) {
            return;
        }
        this.deactivateUnactualMissions();
        if (scenarioscript.isScenarioOn()) {
            this.msEnable = ScenarioFlagsManager.getInstance().getFlagValue("MissionsEnebledByScenario");
            if (!this.msEnable && !MissionEventsMaker.isWide()) {
                MissionManager.declineMissions();
                return;
            }
        }
        Set<String> missionNames = this.missionsPool.keySet();
        for (String iterMissionName : arrayMissionNames = missionNames.toArray(new String[missionNames.size()])) {
            MissionPlacement missionPlacement;
            MissionInfo missionData;
            String missionName;
            if (!this.missionsPool.containsKey(iterMissionName) || !this.requirementsForMissionActivation.missionAvalible(missionName = (missionData = (missionPlacement = this.missionsPool.get(iterMissionName)).getInfo()).getName()) || !this.hasMissionFreePoints(missionPlacement)) continue;
            LinkedList<MissionDepentantLoadElement> missionChainElements = new LinkedList<MissionDepentantLoadElement>();
            LinkedList<String> breadthFirstSearchQueue = new LinkedList<String>();
            breadthFirstSearchQueue.addAll(missionData.getDependent());
            while (!breadthFirstSearchQueue.isEmpty()) {
                String dependentMissionToUpload = (String)breadthFirstSearchQueue.poll();
                MissionPlacement placement = this.dependentMissionsPool.get(dependentMissionToUpload);
                if (null == placement) continue;
                missionChainElements.addAll(this.createDependableMissionsLoadInformation(missionName, placement));
            }
            boolean cant_place_dependants = false;
            for (MissionDepentantLoadElement uploadCandidate : missionChainElements) {
                cant_place_dependants |= !this.hasMissionFreePoints(uploadCandidate.placement);
            }
            if (cant_place_dependants) continue;
            this.placeMissionToWorld(missionPlacement, this.missionsPool);
            this.registerMissionPriority(missionName);
            for (MissionDepentantLoadElement toPlaceInWorld : missionChainElements) {
                this.placeDependantMissionToWorld(toPlaceInWorld.parentName, toPlaceInWorld.placement, this.dependentMissionsPool);
            }
        }
    }

    private void registerMissionPriority(String missionName) {
        int missionPriority = 10;
        Requirement missionRequirement = this.requirementsForMissionActivation.getRequirement(missionName);
        if (null != missionRequirement) {
            missionPriority += missionRequirement.getPriorityIncrement();
        }
        this.missionsPriorities.registerMissionPriority(missionName, missionPriority);
    }

    private void riseMissionPriority_MovedFromDependantToActive(String parentName, String missionName) {
        this.missionsPriorities.movedFromDependantToActive(parentName, missionName);
    }

    public void placeMissionToWorld(MissionPlacement missionPlacement) {
        this.placeMissionToWorld(missionPlacement, null);
    }

    private void placeMissionToWorld(MissionPlacement missionPlacement, Map<String, MissionPlacement> pool) {
        assert (null != missionPlacement) : "missionPlacement must be non-null reference";
        this.uploadMission(missionPlacement.getInfo());
        if (null != pool) {
            pool.remove(missionPlacement.getInfo().getName());
        }
        this.activatedMissions.add(missionPlacement);
        MissionsLogger.getInstance().doLog("Activated mission: " + missionPlacement.getInfo().getName(), Level.INFO);
        for (InformationChannelData channelData : missionPlacement.getInfo().getAllChannels()) {
            this.pointsController.lockPoints(channelData.getPlacesNames(), missionPlacement);
        }
        String s = missionPlacement.getInfo().getQuestItemPlacement();
        if (null != s) {
            ArrayList<String> pNames = new ArrayList<String>();
            pNames.add(s);
            this.pointsController.lockPoints(pNames, missionPlacement);
        }
    }

    private void placeDependantMissionToWorld(String parentMissionName, MissionPlacement missionPlacement, Map<String, MissionPlacement> pool) {
        HashMap<String, MissionPlacement> missionDepednences;
        this.uploadMission(missionPlacement.getInfo());
        pool.remove(missionPlacement.getInfo().getName());
        if (this.activatedDependentMissions.containsKey(parentMissionName)) {
            missionDepednences = this.activatedDependentMissions.get(parentMissionName);
        } else {
            missionDepednences = new HashMap();
            this.activatedDependentMissions.put(parentMissionName, missionDepednences);
        }
        missionDepednences.put(missionPlacement.getInfo().getName(), missionPlacement);
        MissionsLogger.getInstance().doLog("Activated dependent mission: " + missionPlacement.getInfo().getName(), Level.INFO);
        for (InformationChannelData channelData : missionPlacement.getInfo().getAllChannels()) {
            this.pointsController.lockPoints(channelData.getPlacesNames(), missionPlacement);
        }
        String s = missionPlacement.getInfo().getQuestItemPlacement();
        if (null != s) {
            ArrayList<String> pNames = new ArrayList<String>();
            pNames.add(s);
            this.pointsController.lockPoints(pNames, missionPlacement);
        }
    }

    private void uploadMissions(Collection<String> fileList) {
        if (null != fileList) {
            for (String fileName : fileList) {
                this.loadMissions(fileName);
            }
        }
    }

    public boolean activateAsideMission(String _mission_name) {
        return this.debugActivateMission(_mission_name, this.asideMissionsPool);
    }

    private boolean debugActivateMission(String _mission_name, Map<String, MissionPlacement> pool) {
        assert (null != pool) : "pool must be non-null reference";
        assert (null != _mission_name) : "_mission_name must be non-null reference";
        MissionPlacement info = pool.get(_mission_name);
        if (null != info) {
            LinkedList<MissionPlacement> missionChainElements = new LinkedList<MissionPlacement>();
            LinkedList<String> breadthFirstSearchQueue = new LinkedList<String>();
            breadthFirstSearchQueue.addAll(info.getInfo().getDependent());
            while (!breadthFirstSearchQueue.isEmpty()) {
                String dependentMissionToUpload = (String)breadthFirstSearchQueue.poll();
                MissionPlacement placement = this.dependentMissionsPool.get(dependentMissionToUpload);
                if (null == placement) continue;
                missionChainElements.add(placement);
                breadthFirstSearchQueue.addAll(placement.getInfo().getDependent());
            }
            this.hasMissionFreePoints(info);
            this.placeMissionToWorld(info, this.missionsPool);
            for (MissionPlacement toPlaceInWorld : missionChainElements) {
                this.hasMissionFreePoints(toPlaceInWorld);
                this.placeMissionToWorld(toPlaceInWorld, this.dependentMissionsPool);
            }
            return true;
        }
        return false;
    }

    public boolean load_mission_debug(String _mission_name) {
        if (null != _mission_name) {
            boolean activatedFromMissionsPool = this.debugActivateMission(_mission_name, this.missionsPool);
            if (activatedFromMissionsPool) {
                return true;
            }
            boolean activatedFromDependantPool = this.debugActivateMission(_mission_name, this.dependentMissionsPool);
            if (activatedFromDependantPool) {
                return true;
            }
            boolean activatedFromAsideMissionsPool = this.activateAsideMission(_mission_name);
            if (activatedFromAsideMissionsPool) {
                return true;
            }
            ScenarioMissionItem scenarioMissionInfo = ScenarioMissions.getInstance().get(_mission_name);
            if (scenarioMissionInfo != null) {
                ScenarioMission.activateMissionLoad(scenarioMissionInfo.getMission_name(), scenarioMissionInfo.getOrg_name(), scenarioMissionInfo.getPoint_name(), new CoreTime(), scenarioMissionInfo.getMoveTime(), scenarioMissionInfo.getNeedFinishIcon());
                return true;
            }
        }
        return false;
    }

    private boolean loadMissions(String xmlFile) {
        if (null == xmlFile || 0 == xmlFile.length()) {
            return false;
        }
        result_readFile_loadMission = false;
        try {
            XmlDocument document = new XmlDocument(xmlFile, null);
            Document dom = document.getContent();
            XmlFilter missionsFilter = new XmlFilter(dom.getElementsByTagName("missions"));
            Node missionData = missionsFilter.nextElement();
            while (null != missionData) {
                new XmlFilter(missionData.getChildNodes()).visitAllNodes("mission", new XmlNodeDataProcessor(){

                    public void process(Node target, Object param) {
                        String finishPointName;
                        assert (null != target) : "target must be non-null reference";
                        PointListExtractor pointsList = new PointListExtractor(target);
                        if (1 > pointsList.getFinishPoints().size()) {
                            MissionsLogger.getInstance().doLog("invalid count of finish mission points detected", Level.WARNING);
                            finishPointName = "unkonwn";
                        } else {
                            finishPointName = pointsList.getFinishPoints().iterator().next();
                        }
                        MissionInfo missionConstructionData = new MissionInfo(target, finishPointName);
                        MissionPlacement valueToStore = new MissionPlacement(missionConstructionData, new LinkedList<String>(pointsList.getCommonPoints()));
                        if (!missionConstructionData.hasStartDependence() && !missionConstructionData.isScenarioMission()) {
                            MissionManager.this.missionsPool.put(missionConstructionData.getName(), valueToStore);
                            try {
                                Node requirementsNode = new XmlFilter(target.getChildNodes()).nodeNameNext("req");
                                if (null != requirementsNode && 0 < requirementsNode.getChildNodes().getLength()) {
                                    Requirement condition = RequirementsFactory.makeOrRequirement(requirementsNode);
                                    MissionManager.this.requirementsForMissionActivation.addRequirement(missionConstructionData.getName(), condition);
                                }
                            }
                            catch (RequirementsCreationException e) {
                                MissionsLogger.getInstance().doLog(MissionManager.INFO_LOADING_ERROR + e.getLocalizedMessage(), Level.SEVERE);
                                MissionsLogger.getInstance().doLog("failed to parse requirement node, mission name: " + missionConstructionData.getName(), Level.SEVERE);
                            }
                        } else if (!missionConstructionData.isScenarioMission()) {
                            MissionManager.this.dependentMissionsPool.put(missionConstructionData.getName(), valueToStore);
                        } else {
                            MissionManager.this.asideMissionsPool.put(missionConstructionData.getName(), valueToStore);
                        }
                        result_readFile_loadMission = true;
                    }
                }, null);
                missionData = missionsFilter.nextElement();
            }
        }
        catch (IOException e) {
            StringBuilder errorMessage = new StringBuilder(128);
            errorMessage.append("failed load missions from ");
            errorMessage.append(xmlFile);
            errorMessage.append(": ");
            errorMessage.append(e.getLocalizedMessage());
            MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
        }
        return result_readFile_loadMission;
    }

    private void uploadMission(MissionInfo info) {
        assert (null != info) : "info must be non-null reference";
        for (MissionsController controller : this.missionsWaiters) {
            controller.uploadMission(info);
        }
    }

    private void unloadMission(String name) {
        assert (null != name) : "name must be non-null reference";
        for (MissionsController controller : this.missionsWaiters) {
            controller.unloadMission(name);
        }
    }

    public final void activateDependantMission(String parentName, String missionName) {
        if (0 == parentName.compareTo(missionName)) {
            return;
        }
        if (this.activatedDependentMissions.containsKey(parentName)) {
            HashMap<String, MissionPlacement> dependantMissions = this.activatedDependentMissions.get(parentName);
            if (null == dependantMissions) {
                return;
            }
            this.riseMissionPriority_MovedFromDependantToActive(parentName, missionName);
            this.activatedMissions.add(dependantMissions.get(missionName));
            dependantMissions.remove(missionName);
        }
    }

    public final void deactivateDependantMission(String parentName, String missionName) {
        if (this.activatedDependentMissions.containsKey(parentName)) {
            HashMap<String, MissionPlacement> dependantMissions = this.activatedDependentMissions.get(parentName);
            if (null == dependantMissions) {
                return;
            }
            dependantMissions.remove(missionName);
            MissionsLogger.getInstance().doLog("Deactivated dependant mission permanently: " + missionName, Level.INFO);
            this.unloadMission(missionName);
        }
    }

    public List<Pair<String, String>> getActivatedMissions() {
        ArrayList<Pair<String, String>> activedMissionsNames = new ArrayList<Pair<String, String>>();
        for (MissionPlacement info : this.activatedMissions) {
            activedMissionsNames.add(new Pair<String, String>(info.getInfo().getName(), info.getInfo().getMissionStartPlaceName()));
        }
        return activedMissionsNames;
    }

    public List<String> getFinisheddMissions() {
        ArrayList<String> finishedMissionsNames = new ArrayList<String>();
        for (MissionPlacement info : this.finishedMissions) {
            finishedMissionsNames.add(info.getInfo().getName());
        }
        return finishedMissionsNames;
    }

    public void setActivatedMissions(List<Pair<String, String>> missionsNames) {
        this.loading = true;
        for (Pair<String, String> pairMissionNamePlace : missionsNames) {
            String missionName = pairMissionNamePlace.getFirst();
            if (this.load_mission_debug(missionName)) continue;
            MissionsLogger.getInstance().doLog("Failed to load mission for name, came from external source: " + missionName, Level.SEVERE);
        }
        this.loading = false;
    }

    public void setFinishedMissions(List<String> missionsNames) {
        this.finishedMissions.clear();
        for (String name : missionsNames) {
            MissionPlacement placement = this.missionsPool.remove(name);
            if (null != placement) {
                this.finishedMissions.add(placement);
                continue;
            }
            placement = this.dependentMissionsPool.remove(name);
            if (null == placement) continue;
            this.finishedMissions.add(placement);
        }
    }

    public boolean isLoading() {
        return this.loading;
    }

    public PriorityTable getMissionsPriorities() {
        return this.missionsPriorities;
    }
}

