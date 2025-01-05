/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import rnrcore.ISelfSerializable;
import rnrcore.SelfSerializable;

public class Sc_serial
extends SelfSerializable {
    protected static ISelfSerializable mInterface = null;
    private static Sc_serial instance = null;
    static final long serialVersionUID = 0L;

    public static ISelfSerializable getSerializationInterface() {
        if (null == mInterface) {
            mInterface = new SrializableStub();
        }
        return mInterface;
    }

    public static Sc_serial getInstance() {
        if (null == instance) {
            instance = new Sc_serial();
        }
        return instance;
    }

    public void recieve() {
    }

    private void restore() {
    }

    public void deserialize(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        instance = (Sc_serial)stream.readObject();
        instance.restore();
    }

    static class SrializableStub
    implements ISelfSerializable {
        SrializableStub() {
        }

        public Serializable getSerializationTarget() {
            return Sc_serial.getInstance();
        }

        public void deserialize(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            Sc_serial.getInstance().deserialize(stream);
        }
    }
}

