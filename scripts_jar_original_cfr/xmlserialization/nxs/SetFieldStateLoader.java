/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization.nxs;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import xmlserialization.Log;
import xmlserialization.nxs.StateRecordLoader;

final class SetFieldStateLoader
implements StateRecordLoader {
    private static final Map<Class, FieldSetter> fieldSetters = new HashMap<Class, FieldSetter>();
    private final Field field;
    private final FieldSetter setter;

    static void addFieldSetter(FieldSetter setter) {
        if (null != setter) {
            fieldSetters.put(setter.getFieldType(), setter);
        }
    }

    SetFieldStateLoader(Field field) {
        assert (null != field);
        this.field = field;
        this.setter = fieldSetters.get(field.getType());
        if (null == this.setter) {
            throw new IllegalArgumentException(String.format("Type '%s' is unsupported", field.getClass().getName()));
        }
    }

    public void load(Object fieldHost, String recordData) {
        assert (null != fieldHost);
        this.field.setAccessible(true);
        this.setter.setField(fieldHost, this.field, recordData);
    }

    static abstract class FieldSetter {
        private final Class fieldType;

        FieldSetter(Class fieldType) {
            this.fieldType = fieldType;
        }

        abstract void set(Object var1, Field var2, String var3) throws IllegalAccessException;

        final Class getFieldType() {
            return this.fieldType;
        }

        final void setField(Object fieldHost, Field field, String data) {
            assert (null != field);
            if (null != data) {
                try {
                    this.set(fieldHost, field, data);
                }
                catch (NumberFormatException e) {
                    Log.warning(String.format("NXS-load: FieldSetter failed to setup field '%s' of class '%s' instance: %s", field.getName(), fieldHost.getClass().getName(), e.getMessage()));
                }
                catch (IllegalAccessException e) {
                    Log.warning(String.format("NXS-load: FieldSetter failed to setup field '%s' of class '%s' instance: %s", field.getName(), fieldHost.getClass().getName(), e.getMessage()));
                }
            }
        }
    }
}

