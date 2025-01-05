/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.logging.Level;
import rnrcore.CoreTime;
import rnrcore.listelement;
import rnrcore.loc;
import rnrcore.vectorJ;
import rnrloggers.ScriptsLogger;
import rnrorg.IDeclineOrgListener;
import rnrorg.INPC;
import rnrorg.IQuestCargo;
import rnrorg.IStoreorgelement;
import rnrorg.MissionTime;
import rnrorg.QuestCargoParams;
import rnrorg.RewardForfeit;
import rnrorg.UrgetAgent;
import rnrorg.journable;
import rnrorg.organaiser;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Scorgelement
implements listelement,
IStoreorgelement {
    private boolean is_read = true;
    private String id = "unknown";
    private IStoreorgelement.Type type = IStoreorgelement.Type.notype;
    private IStoreorgelement.Status status = IStoreorgelement.Status.nostatus;
    private boolean important = false;
    private int reward_flag;
    private RewardForfeit reward;
    private int forfeit_flag;
    private RewardForfeit forfeit;
    private String description = "non initialized";
    private INPC customer;
    private MissionTime coef_time_to_pickup;
    private MissionTime coef_time_to_complete;
    private CoreTime requestTime;
    private CoreTime pickUpTime;
    private CoreTime completeTime;
    private int minutes_to_complete = 0;
    private int seconds_to_complete = 0;
    private IQuestCargo cargo;
    private journable startNote = null;
    private journable finishNote = null;
    private journable[] failNote = null;
    private int choose_fail_note = 0;
    private String startPoint = null;
    private String loadPoint = null;
    private String completePoint = null;
    private int mission_state = -1;
    private ArrayList<IDeclineOrgListener> listeners = new ArrayList();

    public Scorgelement(String id, IStoreorgelement.Type type, boolean important, int reward_flag, RewardForfeit reward, int forfeit_flag, RewardForfeit forfeit, String description, INPC customer, MissionTime coef_time_to_pickup, MissionTime coef_time_to_complete, journable startNote, journable finishNote, journable[] failNote) {
        this.id = id;
        this.type = type;
        this.important = important;
        this.reward_flag = reward_flag;
        this.reward = reward;
        this.forfeit_flag = forfeit_flag;
        this.forfeit = forfeit;
        this.description = description;
        this.customer = customer;
        this.coef_time_to_pickup = coef_time_to_pickup;
        this.coef_time_to_complete = coef_time_to_complete;
        this.startNote = startNote;
        this.finishNote = finishNote;
        this.failNote = failNote;
    }

    public void setCargoParams(QuestCargoParams cargo) {
        this.cargo = cargo;
    }

    public void setRequestTime(int year, int month, int date, int hour, int minuten) {
        this.requestTime = new CoreTime(year, month, date, hour, minuten);
    }

    public void setPickupTime(int year, int month, int date, int hour, int minuten) {
        this.pickUpTime = new CoreTime(year, month, date, hour, minuten);
    }

    public void setCompleteTime(int year, int month, int date, int hour, int minuten) {
        this.completeTime = new CoreTime(year, month, date, hour, minuten);
    }

    public double getCoefTimePickup() {
        return this.coef_time_to_pickup.getCoef();
    }

    public double getCoefTimeComplete() {
        return this.coef_time_to_complete.getCoef();
    }

    @Override
    public void updateTimeToComplete(int min, int sec, int state, String missionName) {
        this.minutes_to_complete = min;
        this.seconds_to_complete = sec;
        this.mission_state = state;
        this.status = UrgetAgent.changeStatus(this, this.minutes_to_complete * 60 + this.seconds_to_complete, missionName);
    }

    @Override
    public int getMissionState() {
        return this.mission_state;
    }

    @Override
    public String getRaceName() {
        return this.description;
    }

    @Override
    public String getLogoName() {
        return this.description;
    }

    public void setSerialPoints(String _start_point, String _load_point, String _complete_point) {
        this.startPoint = _start_point;
        this.loadPoint = _load_point;
        this.completePoint = _complete_point;
    }

    public void changeDestination(String _complete_point) {
        this.completePoint = _complete_point;
    }

    @Override
    public IStoreorgelement.Type getType() {
        return this.type;
    }

    @Override
    public IStoreorgelement.Status getStatus() {
        return this.status;
    }

    @Override
    public boolean isImportant() {
        return this.important;
    }

    @Override
    public String getName() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return loc.getOrgString(this.description);
    }

    @Override
    public String getDescriptionRef() {
        return this.description;
    }

    @Override
    public String loadPoint() {
        return this.loadPoint;
    }

    @Override
    public String endPoint() {
        return this.completePoint;
    }

    @Override
    public vectorJ pos_start() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.startPoint);
        if (null == place) {
            return new vectorJ();
        }
        return place.getCoords();
    }

    @Override
    public vectorJ pos_load() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.loadPoint);
        if (null == place) {
            return new vectorJ();
        }
        return place.getCoords();
    }

    @Override
    public vectorJ pos_complete() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.completePoint);
        if (null == place) {
            return new vectorJ();
        }
        return place.getCoords();
    }

    @Override
    public int getRewardFlag() {
        return this.reward_flag;
    }

    @Override
    public int getForfeitFlag() {
        return this.forfeit_flag;
    }

    @Override
    public RewardForfeit getReward() {
        return this.reward;
    }

    @Override
    public RewardForfeit getForfeit() {
        return this.forfeit;
    }

    @Override
    public INPC getCustomer() {
        return this.customer;
    }

    @Override
    public CoreTime dateOfRequest() {
        return this.requestTime;
    }

    @Override
    public CoreTime timeToPickUp() {
        return this.pickUpTime;
    }

    @Override
    public CoreTime timeToComplete() {
        return this.completeTime;
    }

    @Override
    public int getCargoFragility() {
        if (null == this.cargo || !this.cargo.hasFragility()) {
            return 0;
        }
        int result = (int)(this.cargo.getFragility() * 100.0);
        if (result > 100) {
            result = 100;
        } else if (result < 0) {
            result = 0;
        }
        return result;
    }

    public double getFragility() {
        if (null == this.cargo || !this.cargo.hasFragility()) {
            return 0.0;
        }
        return this.cargo.getFragility();
    }

    public boolean hasFragility() {
        return null != this.cargo && this.cargo.hasFragility();
    }

    @Override
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
            find_journal = this.failNote[this.choose_fail_note];
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

    private void addLogRecord(String message) {
        ScriptsLogger.getInstance().log(Level.INFO, 1, "org: name == " + this.id + " \"" + this.description + "\" " + message);
    }

    @Override
    public void deleteFromList() {
    }

    @Override
    public void addToList() {
        this.requestTime = new CoreTime();
        organaiser.getInstance().add(this);
    }

    @Override
    public void start() {
        this.addToList();
        this.updateStatus();
        this.is_read = false;
        this.addLogRecord("started");
    }

    @Override
    public void finish() {
        this.changeStatus(IStoreorgelement.Status.executedMission);
        organaiser.finishMission(this);
        this.addLogRecord("finished");
    }

    @Override
    public void fail(int type_fail) {
        this.choose_fail_note = type_fail;
        this.changeStatus(IStoreorgelement.Status.failedMission);
        organaiser.finishMission(this);
        this.addLogRecord("failed");
    }

    @Override
    public void makeRead() {
        this.is_read = true;
    }

    @Override
    public boolean isRead() {
        return this.is_read;
    }

    public String gDescription() {
        return this.description;
    }

    @Override
    public int get_minutes_toFail() {
        return this.minutes_to_complete;
    }

    @Override
    public int get_seconds_toFail() {
        return this.seconds_to_complete;
    }

    @Override
    public void decline() {
        for (IDeclineOrgListener lst : this.listeners) {
            lst.declined();
        }
    }

    @Override
    public void addDeclineListener(IDeclineOrgListener lst) {
        this.listeners.add(lst);
    }

    public String getDescriptionOriginal() {
        return this.description;
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

    public String getStartPoint() {
        return this.startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getLoadPoint() {
        return this.loadPoint;
    }

    public void setLoadPoint(String loadPoint) {
        this.loadPoint = loadPoint;
    }

    public String getCompletePoint() {
        return this.completePoint;
    }

    public void setCompletePoint(String completePoint) {
        this.completePoint = completePoint;
    }

    public void setStatus(IStoreorgelement.Status status) {
        this.status = status;
    }

    public void submitLoadedOrgNode(Scorgelement element) {
        this.is_read = element.is_read;
        this.status = element.status;
        this.requestTime = element.requestTime;
        this.pickUpTime = element.pickUpTime;
        this.completeTime = element.completeTime;
        this.minutes_to_complete = element.minutes_to_complete;
        this.seconds_to_complete = element.seconds_to_complete;
        this.choose_fail_note = element.choose_fail_note;
        this.startPoint = element.startPoint;
        this.loadPoint = element.loadPoint;
        this.completePoint = element.completePoint;
        this.mission_state = element.mission_state;
        this.listeners = element.listeners;
        this.reward.sMoney(element.reward.gMoney());
        this.reward.sRank(element.reward.gRank());
        this.reward.sRate(element.reward.gRate());
        this.forfeit.sMoney(element.forfeit.gMoney());
        this.forfeit.sRank(element.forfeit.gRank());
        this.forfeit.sRate(element.forfeit.gRate());
        this.cargo = element.cargo;
    }

    public ArrayList<IDeclineOrgListener> getListeners() {
        return this.listeners;
    }

    public void setListeners(ArrayList<IDeclineOrgListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public int getStageID() {
        return 0;
    }

    @Override
    public void setStageID(int id) {
    }
}

