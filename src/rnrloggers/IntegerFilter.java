/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

final class IntegerFilter
implements Filter {
    private int value = 0;

    public IntegerFilter(int toAllow) {
        this.value = toAllow;
    }

    public boolean isLoggable(LogRecord record) {
        if (null == record.getParameters() || 0 >= record.getParameters().length || null == record.getParameters()[0]) {
            return false;
        }
        if (record.getParameters()[0] instanceof Integer) {
            int param = (Integer)record.getParameters()[0];
            return param == this.value;
        }
        return false;
    }
}

