/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.map;

public final class IllegalPlaceException
extends Exception {
    public static final long serialVersionUID = 0L;

    IllegalPlaceException(String placeName) {
        super("Invalid place; name == '" + placeName + "'");
    }
}

