/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import menu.Helper;
import menu.MENUEditBox;
import menu.menues;
import menuscript.IEditFieldListener;

public class EditFieldWrapper {
    private final String METHOD_CHANGENAME = "changeName";
    private final String METHOD_DISSMIS = "dissmisName";
    private ArrayList<IEditFieldListener> listeners = new ArrayList();
    private long control = 0L;

    public void addListener(IEditFieldListener lst) {
        this.listeners.add(lst);
    }

    public void removeListener(IEditFieldListener lst) {
        this.listeners.remove(lst);
    }

    public EditFieldWrapper(long _menu, String controlname) {
        this.control = menues.FindFieldInMenu(_menu, controlname);
        Object obj = menues.ConvertMenuFields(this.control);
        menues.SetScriptOnControl(_menu, obj, this, "dissmisName", 19L);
        menues.SetScriptOnControl(_menu, obj, this, "changeName", 16L);
    }

    public void dissmisName(long _menu, MENUEditBox obj) {
        menues.UpdateMenuField(menues.ConvertMenuFields(this.control));
        String text = this.getText();
        for (IEditFieldListener listener : this.listeners) {
            listener.textDismissed(text);
        }
    }

    public void changeName(long _menu, MENUEditBox obj) {
        menues.UpdateMenuField(menues.ConvertMenuFields(this.control));
        String text = this.getText();
        for (IEditFieldListener listener : this.listeners) {
            listener.textEntered(text);
        }
    }

    public String getText() {
        return menues.GetFieldText(this.control);
    }

    public String setText(String text) {
        String res = menues.GetFieldText(this.control);
        menues.SetFieldText(this.control, text);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.control));
        return res;
    }

    public void setSuffix(String text) {
        menues.setSuffixText(this.control, text);
    }

    public void hide() {
        Helper.setControlShow(this.control, false);
    }

    public void show() {
        Helper.setControlShow(this.control, true);
    }
}

