/*
 * Decompiled with CFR 0.151.
 */
package adjusting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        Node top = XmlUtils.parse(FILENAME);
        if (null == top) {
            Variables.log("ADJUSTING. There is no adjust.xml for adjusting.", true);
            return;
        }
        NodeList vars = top.getNamedChildren(VARIABLE);
        for (int i = 0; i < vars.size(); ++i) {
            Variables.setupNode((Node)vars.get(i));
        }
    }

    private static void setupNode(Node node) {
        String classname = node.getAttribute(CLASS);
        String fieldname = node.getAttribute(FIELD);
        String value = node.getAttribute(VALUE);
        if (null == classname) {
            Variables.log("ADJUSTING. No attribute class", true);
            return;
        }
        if (null == fieldname) {
            Variables.log("ADJUSTING. No attribute field", true);
            return;
        }
        if (null == value) {
            Variables.log("ADJUSTING. No attribute value", true);
            return;
        }
        try {
            String typename;
            Class<?> cls = Class.forName(classname);
            Method getobj = cls.getMethod(GETCURRENTOBJECT, new Class[0]);
            Object result = getobj.invoke(null, new Object[0]);
            Field field = cls.getField(fieldname);
            Class<?> fieldtype = field.getType();
            if (!fieldtype.isPrimitive()) {
                Variables.log("ADJUSTING. Field " + fieldname + " in class " + classname + " is not primitive type. Type is " + fieldtype.getName(), true);
                return;
            }
            if (null == result) {
                Variables.log("ADJUSTING. Null object returned in getCurrentObject for class " + classname, true);
            }
            if ((typename = fieldtype.getName()).compareTo(Double.TYPE.getName()) == 0) {
                double val = new Double(value);
                if (field.getDouble(result) != val) {
                    Variables.log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }
                field.setDouble(result, val);
            } else if (typename.compareTo(Float.TYPE.getName()) == 0) {
                float val = new Float(value).floatValue();
                if (field.getFloat(result) != val) {
                    Variables.log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }
                field.setFloat(result, val);
            } else if (typename.compareTo(Integer.TYPE.getName()) == 0) {
                int val = new Integer(value);
                if (field.getInt(result) != val) {
                    Variables.log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }
                field.setInt(result, val);
            } else if (typename.compareTo(Long.TYPE.getName()) == 0) {
                int val = new Long(value).intValue();
                if (field.getLong(result) != (long)val) {
                    Variables.log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }
                field.setLong(result, val);
            } else if (typename.compareTo(String.class.getName()) == 0) {
                if (((String)field.get(result)).compareTo(value) != 0) {
                    Variables.log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }
                field.set(result, value);
            }
        }
        catch (Exception c) {
            Variables.log("ADJUSTING. " + c.toString(), true);
        }
    }

    private static void log(String str, boolean is_err) {
        if (is_err) {
            eng.err(str);
        }
        eng.console(str);
    }
}

