/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu;

import menu.MENUsimplebutton_field;
import menu.menues;
import rnr.tech.Code1;
import rnr.tech.Code2;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MenuTools {
    public static final int TIME_INFINITE = 1000000000;

    private MenuTools() {
    }

    public static void setButtonClickCallback(final long menuHandle, String buttonName, final Code2<Long, MENUsimplebutton_field> listener) {
        if (0L != menuHandle && null != buttonName && null != listener) {
            MenuTools.findAndProcessFieldOfTypeInMenu(menuHandle, buttonName, MENUsimplebutton_field.class, new Code1<MENUsimplebutton_field>(){

                @Override
                public void execute(MENUsimplebutton_field button) {
                    MenuTools.setButtonClickCallback(button, menuHandle, (Code2<Long, MENUsimplebutton_field>)listener);
                }
            });
        }
    }

    public static void setButtonClickCallback(MENUsimplebutton_field button, long menuHandle, final Code2<Long, MENUsimplebutton_field> listener) {
        menues.SetScriptOnControl(menuHandle, button, new CallbackAdapter(){

            public void callBack(long menuHandle, MENUsimplebutton_field button) {
                listener.execute(menuHandle, button);
            }
        }, "callBack", 4L);
    }

    public static <T> void findAndProcessFieldOfTypeInMenu(long menuHandle, String fieldName, Class<T> expectedFieldType, Code1<T> processor) {
        if (null == fieldName || null == processor || null == expectedFieldType) {
            return;
        }
        long uiElementHandle = menues.FindFieldInMenu(menuHandle, fieldName);
        MenuTools.processFieldOfType(expectedFieldType, processor, uiElementHandle);
    }

    public static <T> void processFieldOfType(Class<T> expectedFieldType, Code1<T> processor, long uiElementHandle) {
        Object uiElement;
        if (0L != uiElementHandle && (uiElement = menues.ConvertMenuFields(uiElementHandle)).getClass() == expectedFieldType) {
            processor.execute(uiElement);
        }
    }

    private static abstract class CallbackAdapter {
        private CallbackAdapter() {
        }

        public abstract void callBack(long var1, MENUsimplebutton_field var3);
    }
}

