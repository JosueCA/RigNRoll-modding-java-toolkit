/*
 * Decompiled with CFR 0.151.
 */
package rnr.console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

public final class CommandsReceiver {
    private static final Map<String, Class> classNamesAbbreviation = new TreeMap<String, Class>();

    private CommandsReceiver() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addAbbreviation(String abbreviation, Class clazz) {
        if (null != abbreviation && null != clazz) {
            Map<String, Class> map = classNamesAbbreviation;
            synchronized (map) {
                classNamesAbbreviation.put(abbreviation, clazz);
            }
        }
    }

    public static boolean receive(String className, String methodName, String argument) {
        if (null == className || null == methodName) {
            System.err.print("CommandsReceiver.receive: invalid commands");
            return false;
        }
        try {
            Class<?> clazz = classNamesAbbreviation.get(className);
            if (null == clazz) {
                clazz = Class.forName(className);
            }
            if (null == argument || 0 >= argument.length()) {
                Method methodToExecute = clazz.getDeclaredMethod(methodName, new Class[0]);
                methodToExecute.setAccessible(true);
                methodToExecute.invoke(null, new Object[0]);
            } else {
                Method methodToExecute = clazz.getDeclaredMethod(methodName, String.class);
                methodToExecute.setAccessible(true);
                methodToExecute.invoke(null, argument);
            }
            return true;
        }
        catch (ClassNotFoundException e) {
            CommandsReceiver.processException(e);
        }
        catch (NoSuchMethodException e) {
            CommandsReceiver.processException(e);
        }
        catch (InvocationTargetException e) {
            CommandsReceiver.processException(e);
        }
        catch (IllegalAccessException e) {
            CommandsReceiver.processException(e);
        }
        return false;
    }

    private static void processException(Exception e) {
        System.err.print("Method execution failed: " + e.getMessage());
        e.printStackTrace(System.err);
    }
}

