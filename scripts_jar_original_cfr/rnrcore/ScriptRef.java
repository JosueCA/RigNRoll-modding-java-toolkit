/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IScriptRef;
import rnrcore.ScriptRefStorage;
import rnrcore.UidStorage;

public final class ScriptRef {
    private int uid = 0;
    private boolean reged = false;

    public void setUid(int id) {
        this.uid = id;
    }

    public int getUid(IScriptRef ref) {
        if (!this.reged && null != ref) {
            this.reged = true;
            this.uid = UidStorage.getInstance().getUid();
            while (!ScriptRefStorage.addRefference(ref)) {
                this.uid = UidStorage.getInstance().getUid();
            }
        }
        return this.uid;
    }

    public void removeRef(IScriptRef ref) {
        if (!this.reged || null == ref) {
            return;
        }
        ScriptRefStorage.removeRefference(ref);
        this.reged = false;
    }

    public void register(int uid, IScriptRef ref) {
        this.uid = uid;
        this.reged = true;
        ScriptRefStorage.addRefference(ref);
    }
}

