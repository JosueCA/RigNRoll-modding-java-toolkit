/*
 * Decompiled with CFR 0.151.
 */
package scenarioUtils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class OnlyMessageOutFormatter
extends Formatter {
    public String format(LogRecord record) {
        return record.getMessage();
    }
}

