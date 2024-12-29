package rickroll.events;

import rickroll.log.RickRollLog;
import rnrorg.MissionEventsMaker.UpdateDataWarehouse;
import rnrorg.WarehouseOrder;
import menu.JavaEventCb;
import menu.JavaEvents;

public class CreateWareHouseCargoEvent implements JavaEventCb {

	// JavaEvents.SendEvent(50, 1, new UpdateDataWarehouse(mission_name, element));
	
	// var3: rnrorg.MissionEventsMaker$UpdateDataWarehouse
	public void OnEvent(int var1, int var2, Object var3) {
		try {
			UpdateDataWarehouse updateDataWarehouse = (UpdateDataWarehouse) var3;
			WarehouseOrder element = updateDataWarehouse.getElement();
//			RickRollLog.log("UpdateOrganiserEvent callback: var1: " + var1 + "; var2: " + var2 + "; var3: " + var3.getClass());
			
			element.updateTimeToComplete(49, 9, element.getMissionState(), updateDataWarehouse.getMissionName());
			
			RickRollLog.log("CreateWareHouseCargoEvent callback: var1: " + var1 + "; var2: " + var2
					+ "; name: " + updateDataWarehouse.getMissionName()
        			+ "; get_minutes_toFail: " + element.get_minutes_toFail()
        			+ "; get_seconds_toFail: " + element.get_seconds_toFail());
            
		} catch(Exception e) {
			RickRollLog.log("CreateWareHouseCargoEvent callback; " + e.getMessage());
		}
	}
}
