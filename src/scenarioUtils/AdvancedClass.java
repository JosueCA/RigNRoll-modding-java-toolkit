/*
 * Decompiled with CFR 0.151.
 */
package scenarioUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class AdvancedClass {
    private Class internal = null;

    public AdvancedClass(String className, String ... packegesToSearch) throws ClassNotFoundException {
        if (null == className) {
            throw new IllegalArgumentException("className must be non-null reference");
        }
        for (String packageName : packegesToSearch) {
            try {
                this.internal = Class.forName(packageName + '.' + className);
                break;
            }
            catch (ClassNotFoundException ex) {
            }
        }
        if (null == this.internal) {
            throw new ClassNotFoundException(className + "wasn't found");
        }
    }

    public Field findFieldInHierarchy(String fieldName) throws NoSuchFieldException {
        Class hierarchyElement = this.internal;
        while (true) {
            try {
                return hierarchyElement.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException exception) {
                if (Object.class != (hierarchyElement = hierarchyElement.getSuperclass())) continue;
                throw new NoSuchFieldException("Field " + fieldName + " wasn't found in class " + this.internal.getName() + " and in all its ancestors");
            }
            // break;
        }
    }

    public Constructor[] getAllConstructors() {
        return this.internal.getDeclaredConstructors();
    }

    public Method findMethodWithoutParameters(String methodName) {
        try {
            return this.internal.getMethod(methodName, new Class[0]);
        }
        catch (NoSuchMethodException exception) {
            return null;
        }
    }

    public Method findMethodInHierarchy(String methodName, Class ... params2) throws NoSuchMethodException {
        Class hierarchyElement = this.internal;
        while (true) {
            try {
                return hierarchyElement.getDeclaredMethod(methodName, params2);
            }
            catch (NoSuchMethodException exception) {
                if (Object.class != (hierarchyElement = hierarchyElement.getSuperclass())) continue;
                throw new NoSuchMethodException("method " + methodName + " wasn't found in class " + this.internal.getName() + " and in all its ancestors");
            }
            // break;
        }
    }

    public Object callConstructor(Object ... params2) throws InstantiationException {
        try {
            Constructor creator = this.getConstructor(params2);
            creator.setAccessible(true);
            return creator.newInstance(params2);
        }
        catch (SecurityException e) {
            throw new InstantiationException(e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new InstantiationException(e.getMessage());
        }
        catch (InvocationTargetException e) {
            throw new InstantiationException(e.getMessage());
        }
        catch (InstantiationException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    public Constructor getConstructor(Class ... paramsTypes) throws NoSuchMethodException {
        return this.internal.getConstructor(paramsTypes);
    }

    public Constructor getConstructor(Object ... params2) throws InstantiationException {
        try {
            Class[] paramsClasses = new Class[params2.length];
            for (int i = 0; i < params2.length; ++i) {
                if (null != params2[i]) {
                    Object parametrObject = params2[i];
                    if (parametrObject instanceof Integer) {
                        paramsClasses[i] = Integer.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Character) {
                        paramsClasses[i] = Character.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Byte) {
                        paramsClasses[i] = Byte.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Short) {
                        paramsClasses[i] = Short.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Long) {
                        paramsClasses[i] = Long.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Float) {
                        paramsClasses[i] = Float.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Double) {
                        paramsClasses[i] = Double.TYPE;
                        continue;
                    }
                    if (parametrObject instanceof Void) {
                        paramsClasses[i] = Void.TYPE;
                        continue;
                    }
                    paramsClasses[i] = parametrObject.getClass();
                    continue;
                }
                throw new IllegalArgumentException("all parameters must be non-null");
            }
            return this.internal.getDeclaredConstructor(paramsClasses);
        }
        catch (SecurityException e) {
            throw new InstantiationException(e.getMessage());
        }
        catch (NoSuchMethodException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    public Class getInternal() {
        return this.internal;
    }
}

