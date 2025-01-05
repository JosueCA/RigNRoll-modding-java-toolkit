/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

final class OnlyMessageOutFormatter
extends Formatter {
    OnlyMessageOutFormatter() {
    }

    public String format(LogRecord record) {
        return record.getMessage();
    }
}

