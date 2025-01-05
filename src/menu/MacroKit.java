/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.KeyPair;
import menu.MENUText_field;
import menu.menues;

public class MacroKit {
    public static String Parse(String source, KeyPair[] keys) {
        StringBuffer buf = new StringBuffer(source);
        for (int i = 0; i < keys.length; ++i) {
            int lastIndex = 0;
            int index = buf.indexOf('%' + keys[i].GetKey().toUpperCase() + '%', lastIndex);
            while (index != -1) {
                lastIndex = index + keys[i].GetKey().length() + 2;
                buf.replace(index, lastIndex, keys[i].GetValue());
                lastIndex = 0;
                index = buf.indexOf('%' + keys[i].GetKey().toUpperCase() + '%', lastIndex);
            }
        }
        return buf.toString();
    }

    public static boolean HasMacro(String source, String macro) {
        StringBuffer buf = new StringBuffer(source);
        int lastIndex = 0;
        int index = buf.indexOf('%' + macro + '%', lastIndex);
        return index != -1;
    }

    public static void ApplyToTextfield(MENUText_field text, KeyPair[] keys) {
        if (text != null) {
            if (text.origtext == null) {
                text.origtext = text.text;
            }
            text.text = MacroKit.Parse(text.origtext, keys);
            menues.UpdateField(text);
        }
    }
}

