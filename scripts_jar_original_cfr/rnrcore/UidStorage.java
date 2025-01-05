/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.LinkedList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class UidStorage {
    public static final int UID_BASE_FOR_NEW_OBJECTS = 1000000;
    private static UidStorage instance = new UidStorage();
    private final Object synchronizationMonitor = new Object();
    private LinkedList<Integer> uids = new LinkedList();
    private int maxCreatedUid = 0;
    static final int UID_CREATION_STEP = 32;

    public static UidStorage getInstance() {
        return instance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset() {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            this.uids.clear();
            this.maxCreatedUid = 0;
        }
    }

    private void generateUids() {
        for (int i = 0; i < 32; ++i) {
            this.uids.add(this.maxCreatedUid + i);
        }
        this.maxCreatedUid += 32;
    }

    private UidStorage() {
        this.generateUids();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getUid() {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            if (!this.uids.isEmpty()) {
                return this.uids.removeFirst();
            }
            this.generateUids();
            return this.uids.removeFirst();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void returnUid(int uid) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            if (0 > uid || this.maxCreatedUid < uid) {
                throw new IllegalArgumentException("invalid param to UidStorage.returnUid: uid is too big or it is not possetive value");
            }
            this.uids.add(uid);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getMaxCreatedUid() {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            return this.maxCreatedUid;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMaxCreatedUid(int maxCreatedUid) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            this.maxCreatedUid = maxCreatedUid + 1000000;
            this.uids.clear();
        }
    }

    public List<Integer> getUids() {
        return this.uids;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setUids(LinkedList<Integer> uids) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            this.uids = uids;
        }
    }
}

