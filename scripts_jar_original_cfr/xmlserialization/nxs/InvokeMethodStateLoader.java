/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization.nxs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import xmlserialization.Log;
import xmlserialization.nxs.StateRecordLoader;

final class InvokeMethodStateLoader
implements StateRecordLoader {
    private static final String ERROR_FORMAT_STRING = "NXS-load: InvokeMethodStateLoader failed to invoke '%s' on object of type '%s': %s";
    private final Method delegate;

    public InvokeMethodStateLoader(Method method) {
        assert (null != method);
        if (1 != method.getParameterTypes().length || !String.class.equals(method.getParameterTypes()[0])) {
            throw new IllegalArgumentException("method must take String as argument");
        }
        this.delegate = method;
    }

    public void load(Object host, String recordData) {
        assert (null != host);
        try {
            this.delegate.invoke(host, recordData);
        }
        catch (IllegalAccessException e) {
            Log.warning(String.format(ERROR_FORMAT_STRING, this.delegate.getName(), host.getClass().getName(), e.getMessage()));
        }
        catch (InvocationTargetException e) {
            Log.warning(String.format(ERROR_FORMAT_STRING, this.delegate.getName(), host.getClass().getName(), e.getMessage()));
        }
    }
}

