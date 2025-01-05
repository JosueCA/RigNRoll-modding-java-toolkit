/*
 * Decompiled with CFR 0.151.
 */
package menuscript.parametrs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import menuscript.parametrs.BooleanParametr;
import menuscript.parametrs.IBooleanValueChanger;
import menuscript.parametrs.IIntegerValueChanger;
import menuscript.parametrs.IParametr;
import menuscript.parametrs.IntegerParametr;
import rnrcore.Log;
import rnrcore.Modifier;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ParametrsBlock {
    private HashMap<String, IParametr> params = new HashMap();

    public void addParametr(String name, boolean value, boolean defaul_value, IBooleanValueChanger changer) {
        this.params.put(name, new BooleanParametr(value, defaul_value, changer));
    }

    public void addParametr(String name, int value, int defaul_value, IIntegerValueChanger changer) {
        this.params.put(name, new IntegerParametr(value, defaul_value, changer));
    }

    public ParametrsBlock makeMemo() {
        ParametrsBlock res = new ParametrsBlock();
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            IParametr p = entry.getValue();
            if (p.isBoolean()) {
                res.addParametr(entry.getKey(), p.getBoolean(), false, null);
                continue;
            }
            if (!p.isInteger()) continue;
            res.addParametr(entry.getKey(), p.getInteger(), 0, null);
        }
        return res;
    }

    public void recordMemoChanges(ParametrsBlock block) {
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            IParametr p = entry.getValue();
            if (p.isBoolean()) {
                block.setBooleanValueChange(entry.getKey(), p.getBoolean());
                continue;
            }
            if (!p.isInteger()) continue;
            block.setIntegerValueChange(entry.getKey(), p.getInteger());
        }
    }

    public void restoreMemo(ParametrsBlock memo) {
        Set<Map.Entry<String, IParametr>> _set = memo.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            IParametr p = entry.getValue();
            if (p.isBoolean()) {
                this.setBooleanValue(entry.getKey(), p.getBoolean());
                continue;
            }
            if (!p.isInteger()) continue;
            this.setIntegerValue(entry.getKey(), p.getInteger());
        }
    }

    public void restoreMemoChanges(ParametrsBlock block) {
        Set<Map.Entry<String, IParametr>> _set = block.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            IParametr p = entry.getValue();
            if (p.isBoolean()) {
                this.setBooleanValueChange(entry.getKey(), p.getBooleanChange());
                continue;
            }
            if (!p.isInteger()) continue;
            this.setIntegerValueChange(entry.getKey(), p.getIntegerChange());
        }
    }

    public void onInit() {
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            entry.getValue().updateDefault();
        }
    }

    public void onUpdate() {
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            entry.getValue().update();
        }
    }

    public void onDefault() {
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            entry.getValue().makeDefault();
        }
    }

    public void visitAllParameters(Modifier<Map.Entry<String, IParametr>> visitor) {
        for (Map.Entry<String, IParametr> a_set : this.params.entrySet()) {
            visitor.modify(a_set);
        }
    }

    public void onOk() {
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            entry.getValue().readFromChanger(true);
        }
    }

    public boolean areValuesChanged() {
        Set<Map.Entry<String, IParametr>> _set = this.params.entrySet();
        for (Map.Entry<String, IParametr> entry : _set) {
            if (!entry.getValue().changed()) continue;
            return true;
        }
        return false;
    }

    public int getIntegerValue(String name_param) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. getInegerValue. Cannot find value named " + name_param);
            return -1;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isInteger()) {
            Log.menu("ParametrsBlock. getInegerValue. Parametr " + name_param + " is not integer");
            return -1;
        }
        return param.getInteger();
    }

    public void setIntegerValue(String name_param, int value) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setInegerValue. cannot find value named " + name_param);
            return;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isInteger()) {
            Log.menu("ParametrsBlock. setInegerValue. Parametr " + name_param + " is not integer");
            return;
        }
        param.setInteger(value);
    }

    public void setIntegerValueDefault(String name_param, int value) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setIntegerValueDefault. cannot find value named " + name_param);
            return;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isInteger()) {
            Log.menu("ParametrsBlock. setIntegerValueDefault. Parametr " + name_param + " is not integer");
            return;
        }
        param.setIntegerDefault(value);
    }

    public void setIntegerValueChange(String name_param, int value) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setInegerValue. cannot find value named " + name_param);
            return;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isInteger()) {
            Log.menu("ParametrsBlock. setInegerValue. Parametr " + name_param + " is not integer");
            return;
        }
        param.setIntegerChange(value);
    }

    public int getIntegerValueChange(String name_param) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setInegerValue. cannot find value named " + name_param);
            return 0;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isInteger()) {
            Log.menu("ParametrsBlock. setInegerValue. Parametr " + name_param + " is not integer");
            return 0;
        }
        param.readFromChanger(false);
        return param.getIntegerChange();
    }

    public boolean getBooleanValue(String name_param) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. getBooleanValue. Cannot find value named " + name_param);
            return false;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isBoolean()) {
            Log.menu("ParametrsBlock. getBooleanValue. Parametr " + name_param + " is not boolean");
            return false;
        }
        return param.getBoolean();
    }

    public void setBooleanValue(String name_param, boolean value) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setBooleanValue. cannot find value named " + name_param);
            return;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isBoolean()) {
            Log.menu("ParametrsBlock. setBooleanValue. Parametr " + name_param + " is not boolean");
            return;
        }
        param.setBoolean(value);
    }

    public void setBooleanValueDefault(String name_param, boolean value) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setBooleanValueDefault. cannot find value named " + name_param);
            return;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isBoolean()) {
            Log.menu("ParametrsBlock. setBooleanValueDefault. Parametr " + name_param + " is not boolean");
            return;
        }
        param.setBooleanDefault(value);
    }

    private void setBooleanValueChange(String name_param, boolean value) {
        if (!this.params.containsKey(name_param)) {
            Log.menu("ParametrsBlock. setBooleanValue. cannot find value named " + name_param);
            return;
        }
        IParametr param = this.params.get(name_param);
        if (!param.isBoolean()) {
            Log.menu("ParametrsBlock. setBooleanValue. Parametr " + name_param + " is not boolean");
            return;
        }
        param.setBooleanChange(value);
    }
}

