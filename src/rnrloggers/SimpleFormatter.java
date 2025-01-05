/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

final class SimpleFormatter
extends Formatter {
    SimpleFormatter() {
    }

    public String format(LogRecord record) {
        return record.getLoggerName() + "::" + record.getLevel().toString() + '\t' + record.getSourceClassName() + '.' + record.getSourceMethodName() + ":  " + record.getMessage();
    }
}

