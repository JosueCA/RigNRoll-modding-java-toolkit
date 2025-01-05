/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.HashMap;
import java.util.Map;
import rnrcore.IScriptRef;
import rnrcore.Log;
import rnrcore.UidStorage;
import rnrcore.eng;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ScriptRefStorage {
    private static final int HASH_MAP_CAPACITY = 300;
    private static ScriptRefStorage ourInstance = new ScriptRefStorage();
    private ScriptRefTable refferenceTable = new ScriptRefTable();
    private static final Object latch = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deinit() {
        Object object = latch;
        synchronized (object) {
            this.refferenceTable.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void compact() {
        Object object = latch;
        synchronized (object) {
            UidStorage.getInstance().reset();
            HashMap<Integer, IScriptRef> newTable = new HashMap<Integer, IScriptRef>();
            for (IScriptRef reference : this.refferenceTable.table.values()) {
                int newUid = UidStorage.getInstance().getUid();
                reference.setUid(newUid);
                newTable.put(newUid, reference);
            }
            this.refferenceTable.table = newTable;
            if (!eng.noNative) {
                eng.reloadScriptObjectsUids();
            }
        }
    }

    private ScriptRefStorage() {
    }

    public static ScriptRefStorage getInstance() {
        return ourInstance;
    }

    public static Map<Integer, IScriptRef> getRefferaceTable() {
        return ScriptRefStorage.ourInstance.refferenceTable.table;
    }

    public static void clearRefferaceTable() {
        ScriptRefStorage.ourInstance.refferenceTable.clear();
    }

    public static IScriptRef getRefference(int uid) {
        return ScriptRefStorage.getInstance().refferenceTable.getReference(uid);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean addRefference(IScriptRef target) {
        Object object = latch;
        synchronized (object) {
            if (ScriptRefStorage.getInstance().refferenceTable.referenceExists(target)) {
                return false;
            }
            ScriptRefStorage.getInstance().refferenceTable.addReference(target);
            return true;
        }
    }

    public static boolean removeRefference(IScriptRef target) {
        return ScriptRefStorage.getInstance().refferenceTable.removeReference(target);
    }

    private static final class ScriptRefTable {
        private Map<Integer, IScriptRef> table = new HashMap<Integer, IScriptRef>(300);

        private ScriptRefTable() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void addReference(IScriptRef target) {
            Object object = latch;
            synchronized (object) {
                if (null != target) {
                    int id = target.getUid();
                    if (id == 0) {
                        Log.simpleMessage("addReference - id == 0");
                    }
                    this.table.put(id, target);
                } else {
                    Log.simpleMessage("addReference - null");
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean referenceExists(IScriptRef target) {
            Object object = latch;
            synchronized (object) {
                return this.table.containsKey(target.getUid());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean removeReference(IScriptRef target) {
            Object object = latch;
            synchronized (object) {
                if (null != target) {
                    int id = target.getUid();
                    boolean bl = null != this.table.remove(id);
                    return bl;
                }
                return false;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        IScriptRef getReference(int id) {
            Object object = latch;
            synchronized (object) {
                if (this.table.containsKey(id)) {
                    return this.table.get(id);
                }
                Log.simpleMessage("getReference(" + id + ") not found");
                return null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void clear() {
            Object object = latch;
            synchronized (object) {
                this.table.clear();
            }
        }
    }
}

