/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;

public class JournalActiveListener {
    private String m_resource;

    public JournalActiveListener(String resourceId) {
        assert (resourceId != null);
        this.m_resource = resourceId;
    }

    public void onAnswerNo() {
        MissionDialogs.sayNo(this.m_resource);
    }

    public void onAnswerYes() {
        MissionDialogs.sayYes(this.m_resource);
    }

    public IMissionInformation getMissionInfo() {
        return MissionDialogs.getMissionInfoAndInCachedToo(this.m_resource);
    }

    public String getResource() {
        return this.m_resource;
    }
}

