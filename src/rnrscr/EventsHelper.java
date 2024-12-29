package rnrscr;

import rickroll.log.RickRollLog;
import menu.JavaEvents;

public class EventsHelper {
	
	// Called when Organiser is opened
//	java.lang.Exception: CollectionOfData getWarehousesData()
//	at rickroll.log.RickRollLog.dumpStackTrace(RickRollLog.java:172)
//	at rnrscr.EventsHelper.getWarehousesData(EventsHelper.java:10)
//	at menuscript.org.OrganiserPane.updateWarehousesOnMapa(OrganiserPane.java:2903)
//	at menuscript.org.OrganiserPane.updateMap(OrganiserPane.java:2737)
//	at menuscript.org.OrganiserPane.getAllLines(OrganiserPane.java:2708)
//	at menuscript.org.OrganiserPane.access$900(OrganiserPane.java:49)
//	at menuscript.org.OrganiserPane$OrgTable.reciveTableData(OrganiserPane.java:877)
//	at menuscript.tablewrapper.TableWrapped.reciveTableDataWrapped(TableWrapped.java:156)
//	at menuscript.tablewrapper.TableWrapped.updateTable(TableWrapped.java:200)
//	at menuscript.org.OrganiserPane.update(OrganiserPane.java:1971)
//	at menuscript.org.OrganiserPane.enterFocus(OrganiserPane.java:1980)
//	at menuscript.org.OrganiserMenu.onTab(OrganiserMenu.java:215)
//	at menu.menues.SetFieldState(Native Method)
//	at menuscript.org.OrganiserMenu.AfterInitMenu(OrganiserMenu.java:154)
  public static CollectionOfData getWarehousesData() {
    CollectionOfData res = new CollectionOfData();
    JavaEvents.SendEvent(44, 8, res);
    return res;
  }
}
