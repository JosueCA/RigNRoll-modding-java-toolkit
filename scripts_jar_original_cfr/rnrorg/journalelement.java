/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import rnrcore.CoreTime;
import rnrcore.MacroBody;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.gameDate;
import rnrloggers.ScriptsLogger;
import rnrorg.IPickUpEventListener;
import rnrorg.JournalActiveListener;
import rnrorg.PickUpEventManager;
import rnrorg.journable;
import rnrorg.journal;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscr.IMissionInformation;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class journalelement
implements journable {
    gameDate time = null;
    CoreTime m_expirationTime = null;
    private MacroBody macroBody = null;
    private String descriptionCached = null;
    private ArrayList<PickEventAdapter> listeners = new ArrayList();
    private boolean need_menu = true;

    @Override
    public boolean needMenu() {
        return this.need_menu;
    }

    @Override
    public String getMissionName() {
        if (this.listeners.isEmpty()) {
            return "";
        }
        return this.listeners.get(0).getMissionName();
    }

    public journalelement(String description, List<Macros> macroces) {
        this.macroBody = MacroBuilder.makeMacroBody("journal", description, macroces);
    }

    public journalelement(MacroBody body) {
        this.macroBody = body;
    }

    public journalelement() {
        this.macroBody = MacroBuilder.makeSimpleMacroBody("no description");
    }

    @Override
    public void setDeactivationTime(CoreTime time) {
        this.m_expirationTime = new CoreTime(time);
        new Deactivator();
    }

    @Override
    public CoreTime getDeactivationTime() {
        return this.m_expirationTime;
    }

    @Override
    public void decline() {
        if (null != this.m_expirationTime) {
            this.m_expirationTime = new CoreTime();
        }
    }

    @Override
    public void setTime(CoreTime time) {
        if (null != time) {
            this.time = new CoreTime(time);
        }
    }

    @Override
    public int gHour() {
        return this.time.gHour();
    }

    @Override
    public int gMinute() {
        return this.time.gMinute();
    }

    @Override
    public int gMonth() {
        return this.time.gMonth();
    }

    @Override
    public int gYear() {
        return this.time.gYear();
    }

    @Override
    public int gDate() {
        return this.time.gDate();
    }

    @Override
    public CoreTime getTime() {
        if (this.time instanceof CoreTime) {
            return (CoreTime)this.time;
        }
        return null;
    }

    @Override
    public void answerNO() {
        for (PickEventAdapter lst : this.listeners) {
            lst.onSayNo();
        }
        this.listeners.clear();
    }

    @Override
    public void answerYES() {
        for (PickEventAdapter lst : this.listeners) {
            lst.onSayYes();
        }
        this.listeners.clear();
    }

    @Override
    public boolean isQuestion() {
        if (this.listeners.isEmpty()) {
            return false;
        }
        for (PickEventAdapter lst : this.listeners) {
            if (!lst.isInformationActive()) continue;
            return true;
        }
        return false;
    }

    @Override
    public final String description() {
        if (null == this.descriptionCached) {
            this.descriptionCached = this.macroBody.makeString();
        }
        return this.descriptionCached;
    }

    @Override
    public void start() {
        this.time = new CoreTime();
        this.addToList();
        ScriptsLogger.getInstance().log(Level.INFO, 0, "journal record: " + this.macroBody.makeString());
    }

    @Override
    public void deleteFromList() {
    }

    @Override
    public void addToList() {
        journal.getInstance().add(this);
    }

    @Override
    public void makeQuestionFor(JournalActiveListener listener) {
        this.listeners.add(new PickEventAdapter(listener));
        MissionInfo mission = MissionSystemInitializer.getMissionsManager().getMissionInfo(listener.getMissionInfo().getMissionName());
        this.need_menu = mission.hasAcceptAction();
    }

    @Override
    public List<String> getListenersResources() {
        ArrayList<String> result = new ArrayList<String>();
        for (PickEventAdapter adapter : this.listeners) {
            result.add(adapter.m_listener.getResource());
        }
        return result;
    }

    @Override
    public MacroBody getMacroBody() {
        return this.macroBody;
    }

    static class PickEventAdapter
    implements IPickUpEventListener {
        private JournalActiveListener m_listener;

        PickEventAdapter(JournalActiveListener listener) {
            assert (listener != null);
            this.m_listener = listener;
            PickUpEventManager.addListener(this);
        }

        public void onPickUpevent(String missionName) {
            assert (missionName != null);
            IMissionInformation missionInfo = this.m_listener.getMissionInfo();
            if (missionInfo != null && missionInfo.getMissionName().compareTo(missionName) == 0) {
                this.onSayYes();
            }
        }

        public void onSayYes() {
            this.m_listener.onAnswerYes();
            PickUpEventManager.removeListener(this);
            journal.getInstance().updateActiveNotes();
        }

        public void onSayNo() {
            this.m_listener.onAnswerNo();
            PickUpEventManager.removeListener(this);
            journal.getInstance().updateActiveNotes();
        }

        public boolean isInformationActive() {
            IMissionInformation missionInfo = this.m_listener.getMissionInfo();
            if (missionInfo == null) {
                return false;
            }
            return missionInfo.hasQuestion();
        }

        public String getMissionName() {
            IMissionInformation missionInfo = this.m_listener.getMissionInfo();
            if (missionInfo == null) {
                return "";
            }
            return missionInfo.getMissionName();
        }
    }

    class Deactivator
    extends TypicalAnm {
        Deactivator() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            if (journalelement.this.listeners.isEmpty() || null == journalelement.this.m_expirationTime) {
                return true;
            }
            CoreTime current = new CoreTime();
            if (current.moreThan(journalelement.this.m_expirationTime) >= 0) {
                journalelement.this.answerNO();
                journal.getInstance().updateActiveNotes();
                return true;
            }
            return false;
        }
    }
}

