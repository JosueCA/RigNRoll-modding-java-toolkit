/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public abstract class SelfSerializable
implements Serializable {
    protected abstract void deserialize(ObjectInputStream var1) throws IOException, ClassNotFoundException;
}

