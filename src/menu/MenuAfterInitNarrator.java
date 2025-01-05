// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

import menu.menues;
import rickroll.log.RickRollLog;

public class MenuAfterInitNarrator {
    private static void narrate(long _menu, boolean showcursor, boolean stopworld, boolean immediateshow, boolean managepolosy) {
    	
    	
    	RickRollLog.dumpStackTrace("MenuAfterInitNarrator; stopworld: " + stopworld + "; immediateshow: " + immediateshow + "; managepolosy: " + managepolosy);
        menues.WindowSet_ShowCursor(_menu, showcursor);
        menues.SetStopWorld(_menu, stopworld);
        menues.SetMenagPOLOSY(_menu, managepolosy);
        if (immediateshow) {
            menues.setShowMenu(_menu, true);
        }
    }

    public static void justShow(long _menu) {
        MenuAfterInitNarrator.narrate(_menu, false, false, true, false);
    }

    public static void justShowWithCursor(long _menu) {
        MenuAfterInitNarrator.narrate(_menu, true, false, true, false);
    }

    public static void justShowAndStop(long _menu) {
        MenuAfterInitNarrator.narrate(_menu, true, true, true, false);
    }
}
