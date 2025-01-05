/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.ArrayList;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrscenario.missions.infochannels.IDealyedRealInfo;

public class DelayedRealInfoPoster
extends TypicalAnm {
    private ArrayList<IDealyedRealInfo> m_posters = new ArrayList();
    private static DelayedRealInfoPoster instance = null;

    static DelayedRealInfoPoster getInstance() {
        if (null == instance) {
            instance = new DelayedRealInfoPoster();
        }
        return instance;
    }

    private DelayedRealInfoPoster() {
        eng.CreateInfinitScriptAnimation(this);
    }

    public void addPoster(IDealyedRealInfo poster) {
        this.m_posters.add(poster);
    }

    public boolean animaterun(double dt) {
        instance = null;
        for (IDealyedRealInfo info : this.m_posters) {
            info.delayedRealInfoPost();
        }
        this.m_posters.clear();
        return true;
    }
}

