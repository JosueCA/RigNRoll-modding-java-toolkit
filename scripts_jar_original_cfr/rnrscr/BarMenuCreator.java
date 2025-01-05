/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import menuscript.BarMenu;
import rnrcore.CoreTime;
import rnrcore.vectorJ;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscr.DialogsSet;
import rnrscr.IBarMoveNewspaperAnimation;
import rnrscr.MissionDialogs;
import rnrscr.smi.Newspapers;

public class BarMenuCreator {
    public static final void CreateBarMenu(vectorJ position, IBarMoveNewspaperAnimation animation2) {
        Place bar_place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
        if (null == bar_place) {
            BarMenu.CreateBarMenu("", position, animation2);
        } else {
            BarMenu.CreateBarMenu(bar_place.getName(), position, animation2);
        }
    }

    public static boolean isSinglePaperInBar(vectorJ position) {
        int newspapers = Newspapers.numNews();
        DialogsSet set = MissionDialogs.queueDialogsForSO(8, position, new CoreTime());
        int size = set.getQuestCount();
        return newspapers == 1 && size == 0;
    }
}

