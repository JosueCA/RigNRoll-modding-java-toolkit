/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public interface ISelfSerializable {
    public void deserialize(ObjectInputStream var1) throws IOException, ClassNotFoundException;

    public Serializable getSerializationTarget();
}

