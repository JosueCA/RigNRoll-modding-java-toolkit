/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.Iterator;
import java.util.Vector;
import rnrcore.BlockedSOObjectXmlSerializable;
import rnrcore.CoreTime;
import rnrcore.ObjectXmlSerializable;
import rnrcore.eng;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SoBlock {
    private Vector<Blocked_SO> blocked = new Vector();
    private static SoBlock instance = null;
    private ObjectXmlSerializable serializable = null;

    public static SoBlock getInstance() {
        if (null == instance) {
            instance = new SoBlock();
        }
        return instance;
    }

    public static void deinit() {
        if (instance != null) {
            instance.deinitSerializator();
        }
        instance = null;
    }

    private SoBlock() {
        this.initSerializator();
    }

    private void initSerializator() {
        this.serializable = new BlockedSOObjectXmlSerializable(this);
        this.serializable.registerObjectXmlSerializable();
    }

    private void deinitSerializator() {
        if (this.serializable != null) {
            this.serializable.unRegisterObjectXmlSerializable();
        }
    }

    public void process() {
        Iterator<Blocked_SO> iter = this.blocked.iterator();
        while (iter.hasNext()) {
            Blocked_SO block = iter.next();
            boolean finish = block.process();
            if (!finish) continue;
            iter.remove();
        }
    }

    private void add(Blocked_SO sobl) {
        this.blocked.add(sobl);
    }

    public void addTimeBlocker_DAYS(int tip, String pointname, int days) {
        TimeOutCondition ti = new TimeOutCondition();
        ti.deltatime = CoreTime.days(days);
        Blocked_SO blocker = new Blocked_SO(tip, pointname, ti);
        this.add(blocker);
        blocker.block();
    }

    public void addTimeBlocker_DAYS(int tip, int days) {
        TimeOutCondition ti = new TimeOutCondition();
        ti.deltatime = CoreTime.days(days);
        Blocked_SO blocker = new Blocked_SO(tip, null, ti);
        this.add(blocker);
        blocker.block();
    }

    public void addBlocker(int tip, String pointname) {
        if (eng.useNative()) {
            eng.blockNamedSO(tip, pointname);
        }
    }

    public void addBlocker(int tip) {
        if (eng.useNative()) {
            eng.blockSO(tip);
        }
    }

    public void removeBlocker(int tip, String pointname) {
        Iterator<Blocked_SO> iter = this.blocked.iterator();
        while (iter.hasNext()) {
            Blocked_SO blocker = iter.next();
            if (!blocker.canBeRemovedByUnblocker(tip, pointname)) continue;
            iter.remove();
        }
    }

    public void removeBlocker(int tip) {
        this.removeBlocker(tip, null);
    }

    public Vector<Blocked_SO> getBlocked() {
        return this.blocked;
    }

    public void setBlocked(Vector<Blocked_SO> blocked) {
        this.blocked = blocked;
    }

    public static class Blocked_SO {
        private boolean finished = false;
        int type = 0;
        String name = "no name";
        TimeOutCondition condition = null;

        public Blocked_SO(int type, String name, TimeOutCondition condition) {
            assert (condition != null);
            this.type = type;
            this.name = name;
            this.condition = condition;
        }

        public void block() {
            if (eng.useNative()) {
                if (null != this.name) {
                    eng.blockNamedSO(this.type, this.name);
                } else {
                    eng.blockSO(this.type);
                }
            }
        }

        public void unblock() {
            if (eng.useNative()) {
                if (null != this.name) {
                    eng.unblockNamedSO(this.type, this.name);
                } else {
                    eng.unblockSO(this.type);
                }
            }
        }

        boolean process() {
            if (this.finished) {
                return true;
            }
            this.finished = this.condition.isFinished();
            if (this.finished) {
                this.unblock();
                return true;
            }
            return false;
        }

        public int getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }

        public TimeOutCondition getCondition() {
            return this.condition;
        }

        private boolean canBeRemovedByUnblocker(int tip, String pointname) {
            if (pointname == null) {
                return this.type == tip;
            }
            return this.type == tip && pointname.compareTo(this.name) == 0;
        }
    }

    public static class TimeOutCondition {
        CoreTime timeStart;
        CoreTime deltatime = null;

        TimeOutCondition() {
            this.timeStart = new CoreTime();
        }

        public TimeOutCondition(CoreTime timeStart, CoreTime deltatime) {
            this.timeStart = timeStart;
            this.deltatime = deltatime;
        }

        public boolean isFinished() {
            return new CoreTime().moreThanOnTime(this.timeStart, this.deltatime) >= 0;
        }

        public CoreTime getDeltatime() {
            return this.deltatime;
        }

        public CoreTime getTimeStart() {
            return this.timeStart;
        }
    }
}

