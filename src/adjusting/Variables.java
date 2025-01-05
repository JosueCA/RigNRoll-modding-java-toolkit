package adjusting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import rickroll.log.RickRollLog;
import rnrcore.eng;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public class Variables {
  private static final String FILENAME = "adjust.xml";
  
  private static final String VARIABLE = "variable";
  
  private static final String CLASS = "class";
  
  private static final String FIELD = "field";
  
  private static final String VALUE = "value";
  
  private static final String GETCURRENTOBJECT = "getCurrentObject";
  
  public static void adjust() {
    Node top = XmlUtils.parse("adjust.xml");
    if (null == top) {
      log("ADJUSTING. There is no adjust.xml for adjusting.", true);
      return;
    } 
    NodeList vars = top.getNamedChildren("variable");
    for (int i = 0; i < vars.size(); i++)
      setupNode((Node)vars.get(i)); 
  }
  
  private static void setupNode(Node node) {
    String classname = node.getAttribute("class");
    String fieldname = node.getAttribute("field");
    String value = node.getAttribute("value");
    if (null == classname) {
      log("ADJUSTING. No attribute class", true);
      return;
    } 
    if (null == fieldname) {
      log("ADJUSTING. No attribute field", true);
      return;
    } 
    if (null == value) {
      log("ADJUSTING. No attribute value", true);
      return;
    } 
    try {
      Class<?> cls = Class.forName(classname);
      Method getobj = cls.getMethod("getCurrentObject", new Class[0]);
      Object result = getobj.invoke(null, new Object[0]);
      Field field = cls.getField(fieldname);
      Class<?> fieldtype = field.getType();
      if (!fieldtype.isPrimitive()) {
        log("ADJUSTING. Field " + fieldname + " in class " + classname + " is not primitive type. Type is " + fieldtype.getName(), true);
        return;
      } 
      if (null == result)
        log("ADJUSTING. Null object returned in getCurrentObject for class " + classname, true); 
      String typename = fieldtype.getName();
      if (typename.compareTo(double.class.getName()) == 0) {
        double val = (new Double(value)).doubleValue();
        if (field.getDouble(result) != val)
          log("ADJUSTING. Field " + fieldname + " has different value.", false); 
        field.setDouble(result, val);
      } else if (typename.compareTo(float.class.getName()) == 0) {
        float val = (new Float(value)).floatValue();
        if (field.getFloat(result) != val)
          log("ADJUSTING. Field " + fieldname + " has different value.", false); 
        field.setFloat(result, val);
      } else if (typename.compareTo(int.class.getName()) == 0) {
        int val = (new Integer(value)).intValue();
        if (field.getInt(result) != val)
          log("ADJUSTING. Field " + fieldname + " has different value.", false); 
        field.setInt(result, val);
      } else if (typename.compareTo(long.class.getName()) == 0) {
        int val = (new Long(value)).intValue();
        if (field.getLong(result) != val)
          log("ADJUSTING. Field " + fieldname + " has different value.", false); 
        field.setLong(result, val);
      } else if (typename.compareTo(String.class.getName()) == 0) {
        if (((String)field.get(result)).compareTo(value) != 0)
          log("ADJUSTING. Field " + fieldname + " has different value.", false); 
        field.set(result, value);
      } 
    } catch (Exception c) {
      log("ADJUSTING. " + c.toString(), true);
    } 
  }
  
  private static void log(String str, boolean is_err) {
    if (is_err) {
      eng.err(str);
    }
    RickRollLog.dumpStackTrace("Variables: Console command; str" + str);
    eng.console(str);
  }
}
