package rickroll.events;

import rickroll.log.RickRollLog;
import rnrorg.MissionEventsMaker.UpdateDataWarehouse;
import rnrorg.WarehouseOrder;
import menu.JavaEventCb;

// EXECUTED WITH dispatchEvent(50) instead of the default sendEvent(50)
public class UpdateOrganiserEvent implements JavaEventCb {

	// JavaEvents.SendEvent(50, 1, new UpdateDataWarehouse(mission_name, element));
	
	// var3: rnrorg.MissionEventsMaker$UpdateDataWarehouse
	public void OnEvent(int var1, int var2, Object var3) {
		try {
			RickRollLog.log("UpdateOrganiserEvent callback start");
			UpdateDataWarehouse updateDataWarehouse = (UpdateDataWarehouse) var3;
			WarehouseOrder element = updateDataWarehouse.getElement();

			RickRollLog.log("UpdateOrganiserEvent callback: var1: " + var1 + "; var2: " + var2
					+ "; name: " + updateDataWarehouse.getMissionName()
					+ "; element_name: " + element.getName()
        			+ "; get_minutes_toFail: " + element.get_minutes_toFail()
        			+ "; get_seconds_toFail: " + element.get_seconds_toFail());
	    	
		} catch(Exception e) {
			RickRollLog.log("UpdateOrganiserEvent callback exception; " + e.getMessage());
		}
	}
}
