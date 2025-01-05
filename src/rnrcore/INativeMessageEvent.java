/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.Serializable;

public interface INativeMessageEvent
extends Serializable {
    public String getMessage();

    public boolean removeOnEvent();

    public void onEvent(String var1);
}

