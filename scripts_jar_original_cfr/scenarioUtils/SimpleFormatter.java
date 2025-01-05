/*
 * Decompiled with CFR 0.151.
 */
package scenarioUtils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class SimpleFormatter
extends Formatter {
    public String format(LogRecord record) {
        return record.getLoggerName() + "::" + record.getLevel().toString() + '\t' + record.getSourceClassName() + '.' + record.getSourceMethodName() + ":  " + record.getMessage();
    }
}

