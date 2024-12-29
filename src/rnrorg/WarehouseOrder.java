// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import java.util.ArrayList;

import menu.JavaEvents;
import menu.KeyPair;
import menu.MacroKit;
import rnrconfig.MerchandizeInformation;
import rnrcore.CoreTime;
import rnrcore.listelement;
import rnrcore.loc;
import rnrcore.vectorJ;
import rnrorg.CustomerWarehouseAssociation;
import rnrorg.EventGetPointLocInfo;
import rnrorg.IDeclineOrgListener;
import rnrorg.INPC;
import rnrorg.IStoreorgelement;
import rnrorg.JournalStartWarehouse;
import rnrorg.MissionEventsMaker;
import rnrorg.RewardForfeit;
import rnrorg.UrgetAgent;
import rnrorg.journable;
import rnrorg.organaiser;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;

public class WarehouseOrder
implements listelement,
IStoreorgelement {
    private static final String[] description = new String[]{"Warehouse order description", "Warehouse tender description", "Warehouse contest description", "Warehouse bigrace description"};
    private static final int DESCR_ORDER = 0;
    private static final int DESCR_TENDER = 1;
    private static final int DESCR_CONTEST = 2;
    private static final int DESCR_BIGRACE = 3;
    private static final String MACRO_WHAT = "WHAT";
    private static final String MACRO_SOURCE = "SOURCE";
    private static final String MACRO_DESTINATION = "DESTINATION";
    private static int countNumOrder = 0;
    private boolean is_read = true;
    private String id = "Warehouse order ";
    private IStoreorgelement.Type type = IStoreorgelement.Type.notype;
    private IStoreorgelement.Status status = IStoreorgelement.Status.nostatus;
    private boolean important = false;
    private INPC customer = new CustomerWarehouseAssociation();
    private CoreTime requestTime;
    private CoreTime completeTime = null;
    private journable startNote = null;
    private journable finishNote = null;
    private journable[] failNote = null;
    private int type_failed = 0;
    private int rewardFlag = 5;
    private int forfeitFlag = 5;
    private RewardForfeit reward = new RewardForfeit(1.0, 1.0, 1.0);
    private RewardForfeit forfeit = new RewardForfeit(1.0, 1.0, 1.0);
    private int minutes_to_complete = 0;
    private int seconds_to_complete = 0;
    private int mission_state = -1;
    private double fragility = 0.0;
    private String loadPoint = null;
    private String completePoint = null;
    private String merchandise = "nothing";
    private String racename = "hack";
    private String logoname = "logo";
    private boolean descriptionGenerated = false;
    private String generatedDescription = this.merchandise;
    public static WarehouseOrder currentOrder = null;
    private ArrayList<IDeclineOrgListener> listeners = new ArrayList();
    private int stageId = 0;

    public WarehouseOrder() {
        this.addDeclineListener(new DeclineWarehouseMissionListener());
    }

//    ---------------------------------------- 2024-12-22 11:49:48
//    java.lang.Exception: organaiser add(IStoreorgelement)
//    	at rickroll.log.RickRollLog.dumpStackTrace(RickRollLog.java:172)
//    	at rnrorg.organaiser.add(organaiser.java:63)
//    	at rnrorg.WarehouseOrder.addToList(WarehouseOrder.java:364)
//    	at rnrorg.WarehouseOrder.start(WarehouseOrder.java:305)
//    	at rnrorg.WarehouseOrder.createDelieveryOrder(WarehouseOrder.java:79)
//    	at menu.menues.CallMenuCallBack_OKMenu(Native Method)
//    	at menuscript.WHmenues.OnOk(WHmenues.java:1209)
//    ----------------------------------------
    public static WarehouseOrder createDelieveryOrder() {
        WarehouseOrder order;
        order = new WarehouseOrder();
    	
        order.id = order.id + countNumOrder;
        order.type = IStoreorgelement.Type.baseDelivery;
        
        currentOrder = order;
        
        order.start();
        ++countNumOrder;
        return order;
    }

    public void setOrderDescription(String orderdescription) {
        this.merchandise = orderdescription;
    }

    public void setRaceName(String name) {
        this.racename = name;
    }

    public void setLogoName(String name) {
        this.logoname = name;
    }

    public void createStartNote() {
        JournalStartWarehouse.createStartJournalNote(null, this.type, this.merchandise, this.loadPoint, this.completePoint, this.racename, this.stageId).start();
    }

    public void setTypeOrderDelivery() {
        this.type = IStoreorgelement.Type.baseDelivery;
    }

    public void setTypeOrderTender() {
        this.type = IStoreorgelement.Type.tender;
    }

    public void setTypeOrderContest() {
        this.type = IStoreorgelement.Type.competition;
    }

    public String getRaceName() {
        return this.racename;
    }

    public String getLogoName() {
        return this.logoname;
    }

    public void setStageID(int id) {
        this.stageId = id;
    }

    public int getStageID() {
        return this.stageId;
    }

    public void setTypeOrderBigRace(int leagueId, boolean useSemitrailer, String _racename, String _logoname, int stageId) {
        switch (leagueId) {
            case 4: {
                this.type = useSemitrailer ? IStoreorgelement.Type.bigrace0_semi : IStoreorgelement.Type.bigrace0;
                break;
            }
            case 3: {
                this.type = useSemitrailer ? IStoreorgelement.Type.bigrace1_semi : IStoreorgelement.Type.bigrace1;
                break;
            }
            case 2: {
                this.type = useSemitrailer ? IStoreorgelement.Type.bigrace2_semi : IStoreorgelement.Type.bigrace2;
                break;
            }
            case 1: {
                this.type = useSemitrailer ? IStoreorgelement.Type.bigrace3_semi : IStoreorgelement.Type.bigrace3;
                break;
            }
            case 0: {
                this.type = useSemitrailer ? IStoreorgelement.Type.bigrace4_semi : IStoreorgelement.Type.bigrace4;
            }
        }
        this.setRaceName(_racename);
        this.setLogoName(_logoname);
        this.stageId = stageId;
    }

    public void updateTimeToComplete(int min, int sec, int state, String mission_name) {
        this.minutes_to_complete = min;
        this.seconds_to_complete = sec;
        this.mission_state = state;
        this.status = UrgetAgent.changeStatus(this, this.minutes_to_complete * 60 + this.seconds_to_complete, mission_name);
    }

    public int getMissionState() {
        return this.mission_state;
    }

    public IStoreorgelement.Type getType() {
        return this.type;
    }

    public IStoreorgelement.Status getStatus() {
        return this.status;
    }

    public boolean isImportant() {
        return this.important;
    }

    public void setImportant(boolean value) {
        this.important = value;
    }

    public String getName() {
        return this.id;
    }

    public void setName(String value) {
        this.id = value;
    }

    public String getDescription() {
        if (this.descriptionGenerated) {
            return this.generatedDescription;
        }
        this.descriptionGenerated = true;
        EventGetPointLocInfo info_load = MissionEventsMaker.getLocalisationMissionPointInfo(this.loadPoint);
        EventGetPointLocInfo info_complete = MissionEventsMaker.getLocalisationMissionPointInfo(this.completePoint);
        String MACROFIND = description[0];
        KeyPair[] pairs = new KeyPair[]{};
        MerchandizeInformation merchandizeInfo = new MerchandizeInformation(this.merchandise);
        switch (this.type) {
            case baseDelivery: {
                MACROFIND = description[0];
                pairs = new KeyPair[]{new KeyPair(MACRO_WHAT, merchandizeInfo.getRealName()), new KeyPair(MACRO_SOURCE, info_load.short_name), new KeyPair(MACRO_DESTINATION, info_complete.short_name)};
                break;
            }
            case tender: {
                MACROFIND = description[1];
                pairs = new KeyPair[]{new KeyPair(MACRO_SOURCE, info_load.short_name), new KeyPair(MACRO_DESTINATION, info_complete.short_name)};
                break;
            }
            case competition: {
                MACROFIND = description[2];
                pairs = new KeyPair[]{new KeyPair(MACRO_SOURCE, info_load.short_name), new KeyPair(MACRO_DESTINATION, info_complete.short_name)};
                break;
            }
            case bigrace0: 
            case bigrace1: 
            case bigrace2: 
            case bigrace3: 
            case bigrace4: 
            case bigrace0_semi: 
            case bigrace1_semi: 
            case bigrace2_semi: 
            case bigrace3_semi: 
            case bigrace4_semi: {
                MACROFIND = description[3];
                pairs = new KeyPair[]{new KeyPair(MACRO_SOURCE, info_load.short_name), new KeyPair(MACRO_DESTINATION, info_complete.short_name), new KeyPair(MACRO_WHAT, loc.getBigraceShortName(this.racename))};
            }
        }
        String macro = loc.getOrgString(MACROFIND);
        this.generatedDescription = MacroKit.Parse(macro, pairs);
        return this.generatedDescription;
    }

    public String getDescriptionRef() {
        return "";
    }

    public String loadPoint() {
        return this.loadPoint;
    }

    public String endPoint() {
        return this.completePoint;
    }

    public vectorJ pos_start() {
        return this.pos_load();
    }

    public vectorJ pos_load() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.loadPoint);
        if (null != place) {
            return place.getCoords();
        }
        return new vectorJ();
    }

    public vectorJ pos_complete() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.completePoint);
        if (null != place) {
            return place.getCoords();
        }
        return new vectorJ();
    }

    public int getRewardFlag() {
        return this.rewardFlag;
    }

    public int getForfeitFlag() {
        return this.forfeitFlag;
    }

    public RewardForfeit getReward() {
        return this.reward;
    }

    public RewardForfeit getForfeit() {
        return this.forfeit;
    }

    public INPC getCustomer() {
        return this.customer;
    }

    public CoreTime dateOfRequest() {
        return this.requestTime;
    }

    public CoreTime timeToPickUp() {
        return this.requestTime;
    }

    public CoreTime timeToComplete() {
        return this.completeTime;
    }

    public int getCargoFragility() {
        return (int)(this.fragility * 100.0);
    }

    public void start() {
        this.is_read = true;
        this.addToList();
        this.updateStatus();
    }

    public void finish() {
        this.changeStatus(IStoreorgelement.Status.executedMission);
        organaiser.finishMission(this);
    }

    public void fail(int fail_type) {
        this.changeStatus(IStoreorgelement.Status.failedMission);
        organaiser.finishMission(this);
    }

    public void updateStatus() {
        if (this.status == IStoreorgelement.Status.executedMission || this.status == IStoreorgelement.Status.failedMission) {
            return;
        }
        this.changeStatus(IStoreorgelement.Status.pendingMission);
    }

    private void changeStatus(IStoreorgelement.Status aimed_status) {
        if (aimed_status == this.status) {
            return;
        }
        this.status = aimed_status;
        journable find_journal = null;
        if (this.status == IStoreorgelement.Status.pendingMission || this.status == IStoreorgelement.Status.urgentMission) {
            find_journal = this.startNote;
            this.startNote = null;
        } else if (this.status == IStoreorgelement.Status.failedMission) {
            this.forfeit.applyFactionRatings(-1.0);
            if (null != this.failNote) {
                find_journal = this.failNote[this.type_failed];
            }
            this.failNote = null;
        } else if (this.status == IStoreorgelement.Status.executedMission) {
            this.reward.applyFactionRatings(1.0);
            find_journal = this.finishNote;
            this.finishNote = null;
        }
        if (null != find_journal) {
            find_journal.start();
        }
    }

    public void makeRead() {
        this.is_read = true;
    }

    public boolean isRead() {
        return this.is_read;
    }

    public void deleteFromList() {
    }

    public void addToList() {
        this.requestTime = new CoreTime();
        organaiser.getInstance().add(this);
        organaiser.getInstance().setCurrentWarehouseOrder(this);
    }

    public int get_minutes_toFail() {
        return this.minutes_to_complete;
    }

    public int get_seconds_toFail() {
        return this.seconds_to_complete;
    }

    public void decline() {
        this.fail(0);
        for (IDeclineOrgListener lst : this.listeners) {
            lst.declined();
        }
    }

    public void addDeclineListener(IDeclineOrgListener lst) {
        this.listeners.add(lst);
    }

    public int getType_failed() {
        return this.type_failed;
    }

    public void setType_failed(int type_failed) {
        this.type_failed = type_failed;
    }

    public double getFragility() {
        return this.fragility;
    }

    public void setFragility(double fragility) {
        this.fragility = fragility;
    }

    public String getOrderdescription() {
        return this.merchandise;
    }

    public journable[] getFailNote() {
        return this.failNote;
    }

    public void setFailNote(journable[] failNote) {
        this.failNote = failNote;
    }

    public journable getFinishNote() {
        return this.finishNote;
    }

    public void setFinishNote(journable finishNote) {
        this.finishNote = finishNote;
    }

    public journable getStartNote() {
        return this.startNote;
    }

    public void setStartNote(journable startNote) {
        this.startNote = startNote;
    }

    public void setLoadPoint(String loadPoint) {
        this.loadPoint = loadPoint;
    }

    public void setCompletePoint(String completePoint) {
        this.completePoint = completePoint;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public void setType(IStoreorgelement.Type type) {
        this.type = type;
    }

    public void setStatus(IStoreorgelement.Status status) {
        this.status = status;
    }

    public void setRewardFlag(int rewardFlag) {
        this.rewardFlag = rewardFlag;
    }

    public void setForfeitFlag(int forfeitFlag) {
        this.forfeitFlag = forfeitFlag;
    }

    public void setReward(RewardForfeit reward) {
        this.reward = reward;
    }

    public void setForfeit(RewardForfeit forfeit) {
        this.forfeit = forfeit;
    }

    public void setCustomer(INPC customer) {
        this.customer = customer;
    }

    public void setRequestTime(CoreTime requestTime) {
        this.requestTime = requestTime;
    }

    public void setCompleteTime(CoreTime completeTime) {
        this.completeTime = completeTime;
    }

    public static int getCountNumOrder() {
        return countNumOrder;
    }

    public static void setCountNumOrder(int countNumOrder) {
        WarehouseOrder.countNumOrder = countNumOrder;
    }

    static class DeclineWarehouseMissionListener
    implements IDeclineOrgListener {
        DeclineWarehouseMissionListener() {
        }

        public void declined() {
            MissionEventsMaker.declineWareHouseMission();
        }
    }
}
