/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import rnrcore.CoreTime;
import rnrscenario.sctask;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScenarioFlagsManager
extends sctask {
    public static final String MONSTER_CUP_PREPARED = "Start_M_CUP_news";
    public static final String HIGHWAY_108_NEWS_AVALIBLE = "Start_SR108_news";
    public static final String MISSIONS_ENABLED = "MissionsEnebledByScenario";
    public static final String DOROTHY_IS_AVALABLE = "Dorothy_is_available";
    public static final String SAVES_ARE_AVALIBLE = "SavesEnabledByScenario";
    public static final String WAREHOUSES_ARE_AVALIBLE = "WarehousesEnabledByScenario";
    private HashMap<String, Pair<Boolean, IAnimation>> m_flagAnimations = new HashMap();
    private static ScenarioFlagsManager instance = null;

    private ScenarioFlagsManager() {
        super(3, false);
        this.start();
    }

    public static ScenarioFlagsManager getInstance() {
        return instance;
    }

    public static void deinit() {
        instance = null;
    }

    public static void init() {
        instance = new ScenarioFlagsManager();
        instance.setFlagValue(MISSIONS_ENABLED, true);
        instance.setFlagValue(DOROTHY_IS_AVALABLE, false);
        instance.setFlagValue(SAVES_ARE_AVALIBLE, true);
        instance.setFlagValue(WAREHOUSES_ARE_AVALIBLE, true);
        instance.setFlagValue(HIGHWAY_108_NEWS_AVALIBLE, false);
    }

    public static boolean getValue(String flagName) {
        return ScenarioFlagsManager.getInstance().getFlagValue(flagName);
    }

    public static void setValue(String flagName, boolean value) {
        ScenarioFlagsManager.getInstance().setFlagValue(flagName, value);
    }

    public final boolean getFlagValue(String flagName) {
        if (!this.m_flagAnimations.containsKey(flagName)) {
            return false;
        }
        Pair<Boolean, IAnimation> flagValue = this.m_flagAnimations.get(flagName);
        return flagValue.getFirst();
    }

    public final void setFlagValue(String flagName, boolean value) {
        if (this.m_flagAnimations.containsKey(flagName)) {
            Pair<Boolean, IAnimation> flagValue = this.m_flagAnimations.get(flagName);
            flagValue.setFirst(value);
            flagValue.setSecond(new StaticFlag());
        } else {
            Pair<Boolean, StaticFlag> flagValue = new Pair<Boolean, StaticFlag>(value, new StaticFlag());
            this.m_flagAnimations.put(flagName, flagValue);
        }
    }

    public final void setFlagValueTimed(String flagName, int numDays) {
        boolean value = true;
        TimeFlag anim = new TimeFlag(numDays);
        value = anim.animateFlag(value);
        if (this.m_flagAnimations.containsKey(flagName)) {
            Pair<Boolean, IAnimation> flagValue = this.m_flagAnimations.get(flagName);
            flagValue.setFirst(value);
            flagValue.setSecond(anim);
        } else {
            Pair<Boolean, TimeFlag> flagValue = new Pair<Boolean, TimeFlag>(value, anim);
            this.m_flagAnimations.put(flagName, flagValue);
        }
    }

    public final void setFlagValueTimed(String flagName, CoreTime finishTime) {
        boolean value = true;
        TimeFlag anim = new TimeFlag(finishTime);
        value = anim.animateFlag(value);
        if (this.m_flagAnimations.containsKey(flagName)) {
            Pair<Boolean, IAnimation> flagValue = this.m_flagAnimations.get(flagName);
            flagValue.setFirst(value);
            flagValue.setSecond(anim);
        } else {
            Pair<Boolean, TimeFlag> flagValue = new Pair<Boolean, TimeFlag>(value, anim);
            this.m_flagAnimations.put(flagName, flagValue);
        }
    }

    @Override
    public final void run() {
        Set<Map.Entry<String, Pair<Boolean, IAnimation>>> values = this.m_flagAnimations.entrySet();
        for (Map.Entry<String, Pair<Boolean, IAnimation>> entry : values) {
            Pair<Boolean, IAnimation> pairFlaganimation = entry.getValue();
            IAnimation animation2 = pairFlaganimation.getSecond();
            boolean previousValue = pairFlaganimation.getFirst();
            boolean newValue = animation2.animateFlag(previousValue);
            pairFlaganimation.setFirst(newValue);
        }
    }

    public final HashMap<String, Boolean> getStaticFlags() {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        Set<Map.Entry<String, Pair<Boolean, IAnimation>>> values = this.m_flagAnimations.entrySet();
        for (Map.Entry<String, Pair<Boolean, IAnimation>> entry : values) {
            Pair<Boolean, IAnimation> pairFlaganimation = entry.getValue();
            IAnimation animation2 = pairFlaganimation.getSecond();
            if (!(animation2 instanceof StaticFlag)) continue;
            result.put(entry.getKey(), pairFlaganimation.getFirst());
        }
        return result;
    }

    public final HashMap<String, CoreTime> getTimedFlags() {
        HashMap<String, CoreTime> result = new HashMap<String, CoreTime>();
        Set<Map.Entry<String, Pair<Boolean, IAnimation>>> values = this.m_flagAnimations.entrySet();
        for (Map.Entry<String, Pair<Boolean, IAnimation>> entry : values) {
            Pair<Boolean, IAnimation> pairFlaganimation = entry.getValue();
            IAnimation animation2 = pairFlaganimation.getSecond();
            if (!(animation2 instanceof TimeFlag)) continue;
            TimeFlag timeAnimation = (TimeFlag)animation2;
            result.put(entry.getKey(), timeAnimation.getFinishTime());
        }
        return result;
    }

    static class TimeFlag
    implements IAnimation {
        private CoreTime m_finishTime;

        TimeFlag(int numDays) {
            this.m_finishTime = new CoreTime();
            this.m_finishTime.plus_days(numDays);
        }

        TimeFlag(CoreTime finishTime) {
            this.m_finishTime = new CoreTime(finishTime);
        }

        CoreTime getFinishTime() {
            return this.m_finishTime;
        }

        public boolean animateFlag(boolean currentFlagValue) {
            if (currentFlagValue) {
                CoreTime currentTime = new CoreTime();
                return currentTime.moreThan(this.m_finishTime) < 0;
            }
            return false;
        }
    }

    static class StaticFlag
    implements IAnimation {
        StaticFlag() {
        }

        public boolean animateFlag(boolean currentFlagValue) {
            return currentFlagValue;
        }
    }

    static interface IAnimation {
        public boolean animateFlag(boolean var1);
    }
}

