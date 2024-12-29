package rnr.console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import rickroll.log.RickRollLog;

public final class CommandsReceiver {
  private static final Map<String, Class> classNamesAbbreviation = (Map)new TreeMap<String, Class<?>>();
  
  public static void addAbbreviation(String abbreviation, Class clazz) {
    if (null != abbreviation && null != clazz)
      synchronized (classNamesAbbreviation) {
        classNamesAbbreviation.put(abbreviation, clazz);
      }  
  }
  
  public static boolean receive(String className, String methodName, String argument) {
    if (null == className || null == methodName) {
      System.err.print("CommandsReceiver.receive: invalid commands");
      return false;
    } 
    try {
    	
    	// RickRollLog.dumpStackTrace("Commands Receiver receive; classname: " + className + "; methodName: " + methodName + "; argument: " + argument);
      Class<?> clazz = classNamesAbbreviation.get(className);
      if (null == clazz)
        clazz = Class.forName(className); 
      if (null == argument || 0 >= argument.length()) {
        Method methodToExecute = clazz.getDeclaredMethod(methodName, new Class[0]);
        methodToExecute.setAccessible(true);
        methodToExecute.invoke(null, new Object[0]);
      } else {
        Method methodToExecute = clazz.getDeclaredMethod(methodName, new Class[] { String.class });
        methodToExecute.setAccessible(true);
        methodToExecute.invoke(null, new Object[] { argument });
      } 
      
      // RickRollLog.dumpStackTrace("Commands Receiver receive; method invoked");
      return true;
    } catch (ClassNotFoundException e) {
      processException(e);
    } catch (NoSuchMethodException e) {
      processException(e);
    } catch (InvocationTargetException e) {
      processException(e);
    } catch (IllegalAccessException e) {
      processException(e);
    } 
    return false;
  }
  
  private static void processException(Exception e) {
    System.err.print("Method execution failed: " + e.getMessage());
    e.printStackTrace(System.err);
  }
}
