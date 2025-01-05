/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

public class NoSuchChannelException
extends Exception {
    private static final long serialVersionUID = 0L;

    public NoSuchChannelException(String channelName) {
        super("channel with name '" + channelName + "' doesn't exist");
    }
}

