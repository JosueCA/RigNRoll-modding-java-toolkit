// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

import rickroll.log.RickRollLog;
import menu.JavaEvents;

class CEventsStaticCall {
    CEventsStaticCall() {
    }

    public void DispatchEvent(int ID, int value, Object obj) {
//    	try {
//    		if(obj != null) {
//    			RickRollLog.dumpStackTrace("CEventsStaticCall DispatchEvent(); ID: " + ID + "; value: " + value + "; obj_class: " + obj.getClass());
//    			
//    			if(obj instanceof menu.CInteger) {
//    	    		RickRollLog.log("CEventsStaticCall DispatchEvent(); CInteger value: " + ((CInteger) obj).value); 
//    	    	}
//    		} else {
//    			RickRollLog.dumpStackTrace("CEventsStaticCall DispatchEvent(); ID: " + ID + "; value: " + value + "; obj_class: null");
//    		}
//    	} catch (Exception e) {
//    		RickRollLog.log("CEventsStaticCall excepton; " + e.getMessage());
//    	}
    	
        JavaEvents.DispatchEvent(ID, value, obj);
    }
}
