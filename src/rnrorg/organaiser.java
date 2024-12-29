// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import menuscript.HeadUpDisplay;
import rickroll.log.RickRollLog;
import rnrcore.eng;
import rnrorg.IStoreorgelement;
import rnrorg.MissionEventsMaker;
import rnrorg.MissionOrganiser;
import rnrorg.Organizers;
import rnrorg.RewardForfeit;
import rnrorg.Scorgelement;
import rnrorg.WarehouseOrder;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class organaiser {
    private Vector<IStoreorgelement> allorgelements = new Vector();
    private IStoreorgelement current = null;
    private IStoreorgelement current_warehouse = null;
    private int has_unread = 0;
    private static organaiser ORG = null;

    public static organaiser getInstance() {
        if (null == ORG) {
            ORG = new organaiser();
            
            // RICK
            // RickRollLog.dumpStackTrace("organaiser getInstance() first time");
        	// END RICK
        }
        return ORG;
    }

    private organaiser() {
    }

    private static boolean isMissionActual(IStoreorgelement element) {
        return element.getStatus() == IStoreorgelement.Status.urgentMission || element.getStatus() == IStoreorgelement.Status.pendingMission;
    }

    public Iterator<IStoreorgelement> gOrganaiser() {
        return this.allorgelements.iterator();
    }

    public IStoreorgelement getCurrentWarehouseOrder() {
        return this.current_warehouse;
    }

    public void setCurrentWarehouseOrder(IStoreorgelement val) {
        this.current_warehouse = val;
    }

    public void add(IStoreorgelement val) {
    	
    	// RICK
    	// RickRollLog.dumpStackTrace("organaiser add(IStoreorgelement)");
    	// END RICK
    	
        if (this.allorgelements.isEmpty() || null == this.current) {
            this.current = val;
        }
        this.allorgelements.add(val);
        if (!val.isRead() && 0 == this.has_unread++) {
            HeadUpDisplay.updateUnread();
        }
    }

    public void addOnRestore(IStoreorgelement val) {
        this.allorgelements.add(val);
    }

    public void remove(IStoreorgelement obj) {
        for (int i = 0; i < this.allorgelements.size(); ++i) {
            if (!this.allorgelements.elementAt(i).equals(obj)) continue;
            this.allorgelements.removeElementAt(i);
            break;
        }
    }

    public void updateMissionsOrgElements() {
    	// RICK
    	// RickRollLog.dumpStackTrace("organaiser updateMissionsOrgElements()");
    	// END RICK
    	
        for (IStoreorgelement element : this.allorgelements) {
            String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(element.getName());
            if (null != mission_name && element instanceof Scorgelement) {
                MissionEventsMaker.updateOrganiser(mission_name, (Scorgelement)element);
                continue;
            }
            if (!(element instanceof WarehouseOrder) || element.getStatus() != IStoreorgelement.Status.pendingMission || element.getStatus() != IStoreorgelement.Status.urgentMission) continue;
            MissionEventsMaker.updateOrganiser(mission_name, (WarehouseOrder)element);
        }
    }

    private void findCurrent() {
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!organaiser.isMissionActual(element)) continue;
            this.current = element;
            break;
        }
    }

    public static void failWarehouseMission_TimeoutPickup(int delta_money, double delta_rating, double delta_rank) {
        organaiser.failWarehouseMission(0, delta_money, delta_rating, delta_rank);
    }

    public static void failWarehouseMission_TimeoutComplete(int delta_money, double delta_rating, double delta_rank) {
        organaiser.failWarehouseMission(1, delta_money, delta_rating, delta_rank);
    }

    public static void failWarehouseMission_Damaged(int delta_money, double delta_rating, double delta_rank) {
        organaiser.failWarehouseMission(2, delta_money, delta_rating, delta_rank);
    }

    public static void failWarehouseMission_Declined(int delta_money, double delta_rating, double delta_rank) {
        organaiser.failWarehouseMission(3, delta_money, delta_rating, delta_rank);
    }

    public static void finishWarehouseMission(int delta_money, double delta_rating, double delta_rank) {
    	
    	// RICK
    	// RickRollLog.dumpStackTrace("organaiser finishWarehouseMission; delta_money: " + delta_money);
    	// END RICK
    	
    	
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!(element instanceof WarehouseOrder) || !organaiser.isMissionActual(element)) continue;
            RewardForfeit summary = element.getReward();
            if (null != summary) {
                summary.setRealMoney(delta_money);
                summary.setRealRate(delta_rating);
                summary.setRealRank((int)delta_rank);
            }
            element.finish();
            break;
        }
    }

    private static void failWarehouseMission(int type_fail, int delta_money, double delta_rating, double delta_rank) {
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!(element instanceof WarehouseOrder) || !organaiser.isMissionActual(element)) continue;
            RewardForfeit summary = element.getForfeit();
            if (null != summary) {
                summary.setRealMoney(delta_money);
                summary.setRealRate(delta_rating);
                summary.setRealRank((int)delta_rank);
            }
            element.fail(type_fail);
            break;
        }
    }

    public static void declineActiveMissions(List<String> list) {
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!organaiser.isMissionActual(element)) continue;
            String nm = element.getName();
            String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(nm);
            if (list.contains(mission_name)) continue;
            element.decline();
        }
    }

    public static void declineMission(String missionToDecline) {
        if (null == missionToDecline) {
            return;
        }
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!organaiser.isMissionActual(element)) continue;
            String organizerName = element.getName();
            String missionName = MissionOrganiser.getInstance().getMissionForOrganiser(organizerName);
            if (0 != missionToDecline.compareTo(missionName)) continue;
            element.decline();
            return;
        }
    }

    public static void getInterestingElements(ListOfMissions fill_this) {
        fill_this.currentmission = organaiser.getInstance().current;
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!organaiser.isMissionActual(element)) continue;
            String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(element.getName());
            boolean is_warehouse_mission = element instanceof WarehouseOrder;
            MissionsListParams params = new MissionsListParams(null == mission_name ? element.getName() : mission_name, is_warehouse_mission);
            fill_this.missions.add(element);
            fill_this.mission_names.add(params);
        }
    }

    public static void choose(IStoreorgelement value) {
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (!value.equals(element) || !organaiser.isMissionActual(element)) continue;
            organaiser.getInstance().current = value;
            organaiser.getInstance().allorgelements.remove(element);
            organaiser.getInstance().allorgelements.add(0, element);
            break;
        }
    }

    public static String getMissionDescription(String mission_name) {
    	
    	// RICK
    	// RickRollLog.dumpStackTrace("organaiser getMissionDescription; mission_name: " + mission_name);
    	// END RICK
    	
    	
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (element.getName().compareTo(org_name) != 0) continue;
            return element.getDescription();
        }
        IStoreorgelement elem = Organizers.getInstance().get(org_name);
        if (null != elem) {
            return elem.getDescription();
        }
        return org_name;
    }

    public static String getMissionDescriptionRef(String mission_name) {
    	
    	// RICK
    	// RickRollLog.dumpStackTrace("organaiser getMissionDescriptionRef; mission_name: " + mission_name);
    	// END RICK
    	
    	
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);
        for (IStoreorgelement element : organaiser.getInstance().allorgelements) {
            if (element.getName().compareTo(org_name) != 0) continue;
            return element.getDescriptionRef();
        }
        IStoreorgelement elem = Organizers.getInstance().get(org_name);
        if (null != elem) {
            return elem.getDescriptionRef();
        }
        return org_name;
    }

    public static void declineMissionByName(String mission_name) {
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);
        if (null == org_name || org_name.length() == 0) {
            eng.err("declineMissionByName couldn't find organiser for mission " + mission_name);
            return;
        }
        IStoreorgelement elem = Organizers.getInstance().get(org_name);
        if (null != elem) {
            elem.decline();
        } else {
            eng.err("declineMissionByName couldn't find organiser element named " + org_name + " for mission " + mission_name);
        }
    }

    public boolean isCurrent(IStoreorgelement elem) {
        return elem.equals(this.current);
    }

    public static IStoreorgelement getCurrent() {
        return organaiser.getInstance().current;
    }

    public static CurrentMissionInfo getCurrentMissionInfo() {
    	
    	// RICK
    	// RickRollLog.dumpStackTrace("organaiser getCurrentMissionInfo;");
    	// END RICK
    	
    	
        String mission_name = organaiser.getInstance().current != null ? MissionOrganiser.getInstance().getMissionForOrganiser(organaiser.getInstance().current.getName()) : null;
        
        
        // return new CurrentMissionInfo(mission_name, organaiser.getInstance().current);
        CurrentMissionInfo curentMissionInfo = new CurrentMissionInfo(mission_name, organaiser.getInstance().current);
        // RickRollLog.log("organaiser getCurrentMissionInfo; mission_name: " + curentMissionInfo.mission_name + "; is_warehouse_order: " + curentMissionInfo.is_warehouse_order); 
        return curentMissionInfo;
        
    }

    public static void finishMission(IStoreorgelement elem) {
        if (organaiser.getInstance().isCurrent(elem)) {
            organaiser.getInstance().current = null;
            organaiser.getInstance().findCurrent();
        }
    }

    public boolean hasUnread() {
        return this.has_unread != 0;
    }

    public void readOne() {
        if (this.has_unread == 0) {
            return;
        }
        --this.has_unread;
        if (this.has_unread == 0) {
            HeadUpDisplay.updateUnread();
        }
    }

    public static void deinit() {
        ORG = null;
        MissionOrganiser.deinit();
    }

    public Vector<IStoreorgelement> getAllorgelements() {
        return this.allorgelements;
    }

    public void setAllorgelements(Vector<IStoreorgelement> allorgelements) {
        this.allorgelements = allorgelements;
    }

    static class CurrentMissionInfo {
        String mission_name;
        IStoreorgelement currentmission;
        boolean is_warehouse_order;
        boolean not_null;

        CurrentMissionInfo(String mission_name, IStoreorgelement currentmission) {
            this.mission_name = mission_name;
            this.currentmission = currentmission;
            this.not_null = null != currentmission;
            this.is_warehouse_order = this.not_null && currentmission instanceof WarehouseOrder;
        }
    }

    static class ListOfMissions {
        Vector missions = new Vector();
        Vector mission_names = new Vector();
        IStoreorgelement currentmission = null;

        ListOfMissions() {
        }
    }

    static class MissionsListParams {
        String name;
        boolean is_warehouse_mission;

        MissionsListParams(String name, boolean is_warehouse_mission) {
            this.is_warehouse_mission = is_warehouse_mission;
            this.name = name;
        }
    }
}
