/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.List;
import rnrscenario.missions.QINpc;
import scriptEvents.CreateNpcFromInfoChannel;
import scriptEvents.CreateNpcFromQuestItem;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NpcResource
implements EventListener {
    private String m_missionName;
    private QINpc m_quest_item = null;
    private String m_infoChannelPlace = null;

    private void setInfoChannelResource(String place) {
        this.m_infoChannelPlace = place;
    }

    private void setQiResource(QINpc qi) {
        this.m_quest_item = qi;
    }

    public boolean compareMissionName(String missionName) {
        return missionName.compareTo(this.m_missionName) == 0;
    }

    public void createNpcResource() {
        if (this.m_quest_item != null) {
            assert (this.m_missionName != null);
            boolean makePlace = false;
            if (this.m_infoChannelPlace != null && this.m_quest_item.getPlacement() != null) {
                makePlace = this.m_quest_item.getPlacement().compareTo(this.m_infoChannelPlace) != 0;
            }
            this.m_quest_item.doDealyedPlace(this.m_missionName, makePlace);
        }
    }

    public void finish() {
        EventsController.getInstance().removeListener(this);
    }

    public NpcResource(String mission_name) {
        this.m_missionName = mission_name;
        EventsController.getInstance().addListener(this);
    }

    // @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            if (event2 instanceof CreateNpcFromInfoChannel) {
                CreateNpcFromInfoChannel channelEvent = (CreateNpcFromInfoChannel)event2;
                if (!this.compareMissionName(channelEvent.missionName)) continue;
                this.setInfoChannelResource(channelEvent.place);
                continue;
            }
            if (!(event2 instanceof CreateNpcFromQuestItem)) continue;
            CreateNpcFromQuestItem qiEvent = (CreateNpcFromQuestItem)event2;
            if (!this.compareMissionName(qiEvent.missionName)) continue;
            this.setQiResource(qiEvent.questItemNpc);
        }
    }
}

