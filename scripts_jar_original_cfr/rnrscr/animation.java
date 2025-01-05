/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import rnrcore.SCRanimparts;
import rnrcore.SCRperson;
import rnrcore.eng;
import rnrscr.Chainanm;
import rnrscr.Eventanm;
import rnrscr.ModelManager;
import rnrscr.Presets;
import rnrscr.animationConst;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class animation
implements animationConst {
    static final int MAX_MAN_MODELS = 20;
    static final int MAX_WOMAN_MODELS = 20;
    static final int BARMAN_TAG = 1;
    static final int DEFAULT_TAG = 0;
    static String DefaultModelName = "Woman016";
    private ArrayList<ModelManager.ModelPresets> peopleModels = new ArrayList();
    protected cSpecObjects currentOwner = null;

    public void StartWorkWithSO(cSpecObjects s) {
        this.currentOwner = s;
        if (null == s.Presets()) {
            Presets ourPresets = new Presets();
            s.SetPresets(ourPresets);
        }
        this.peopleModels = this.currentOwner.Presets().getModels();
    }

    public void PrindDbgInfo(FileWriter out) throws Exception {
    }

    public void ModelReqestReport(int mcReq, int wcReq, int mcResp, int wcResp) {
        if (0 == mcResp && 0 != mcReq) {
            if (null != this.currentOwner) {
                eng.writeLog("!!!ERROR!!! animation wiht owner " + this.currentOwner.name + "reqested" + mcResp + " man models. Zero models getted!!!");
            } else {
                eng.writeLog("!!!ERROR!!! animation reqested " + mcResp + " man models. " + "Zero models getted!!!");
            }
        }
        if (0 == wcResp && 0 != wcReq) {
            if (null != this.currentOwner) {
                eng.writeLog("!!!ERROR!!! animation wiht owner " + this.currentOwner.name + "reqested" + wcReq + " woman models. Zero models getted!!!");
            } else {
                eng.writeLog("!!!ERROR!!! animation reqested " + wcReq + " woman models. " + "Zero models getted!!!");
            }
        }
        if (mcReq > mcResp) {
            eng.writeLog("!!!Warning!!! animation reqested " + mcReq + " man models. " + mcResp + " models getted!!!");
        }
        if (wcReq > wcResp) {
            eng.writeLog("!!!Warning!!! animation reqested " + wcReq + " woman models. " + wcResp + " models getted!!!");
        }
    }

    public void LoadModelByTag(int tag, int nommodels) {
        ArrayList<ModelManager.ModelPresets> availablemodels;
        if (null == this.currentOwner) {
            return;
        }
        specobjects obj = specobjects.getInstance();
        ModelManager modelSource = (ModelManager)obj.GetModelSource();
        if (!this.peopleModels.isEmpty()) {
            for (ModelManager.ModelPresets model : this.peopleModels) {
                if (model.getTag() != tag && (model.getTag() & tag) == 0 || --nommodels != 0) continue;
                return;
            }
        }
        if (!(availablemodels = modelSource.getModels(this.peopleModels, this.currentOwner.sotip, tag, nommodels)).isEmpty()) {
            this.currentOwner.Presets().AddModel(availablemodels);
        }
        if (availablemodels.size() != nommodels) {
            eng.err("LoadModelByTag reports. Models with tag " + tag + " cannot load " + nommodels);
        }
        this.peopleModels = this.currentOwner.Presets().getModels();
    }

    public void LoadModels(int manCount, int womanCount) {
        ArrayList<ModelManager.ModelPresets> models;
        int var;
        if (null == this.currentOwner) {
            this.StartWorkWithSO(new cSpecObjects());
        }
        int requestedMCount = manCount;
        int requestedWCount = womanCount;
        specobjects obj = specobjects.getInstance();
        ModelManager modelSource = (ModelManager)obj.GetModelSource();
        if (!this.peopleModels.isEmpty()) {
            for (ModelManager.ModelPresets model : this.peopleModels) {
                if (model.is_man) {
                    --manCount;
                    continue;
                }
                if (model.is_man) continue;
                --womanCount;
            }
        }
        if (0 < (var = manCount)) {
            models = modelSource.getModels(this.peopleModels, this.currentOwner.sotip, 0, true, var);
            this.currentOwner.Presets().AddModel(models);
            if (models.size() != var) {
                eng.err("LoadModels acnnot load " + var + " rested man models");
            }
        } else if (0 > var) {
            this.currentOwner.Presets().DelModel(true, -var);
        }
        var = womanCount;
        if (0 < var) {
            models = modelSource.getModels(this.peopleModels, this.currentOwner.sotip, 0, false, var);
            this.currentOwner.Presets().AddModel(models);
            if (models.size() != var) {
                eng.err("LoadModels cannot load " + var + "  rested woman models");
            }
        } else if (0 > var) {
            this.currentOwner.Presets().DelModel(false, -var);
        }
        this.peopleModels = this.currentOwner.Presets().getModels();
        this.ModelReqestReport(requestedMCount, requestedWCount, requestedMCount - manCount, requestedWCount - womanCount);
    }

    protected String getModelName(int tag, boolean isMan) {
        if (this.peopleModels.isEmpty()) {
            return DefaultModelName;
        }
        Iterator<ModelManager.ModelPresets> iter = this.peopleModels.iterator();
        while (iter.hasNext()) {
            ModelManager.ModelPresets model = iter.next();
            if (model.is_man != isMan || model.getTag() != tag && (model.getTag() & tag) == 0) continue;
            iter.remove();
            return model.getName();
        }
        return DefaultModelName;
    }

    protected ArrayList<String> getModelNames(int tag) {
        ArrayList<String> names = new ArrayList<String>();
        Iterator<ModelManager.ModelPresets> iter = this.peopleModels.iterator();
        while (iter.hasNext()) {
            ModelManager.ModelPresets model = iter.next();
            if (tag != model.getTag() && (model.getTag() & tag) == 0) continue;
            iter.remove();
            names.add(model.getName());
        }
        return names;
    }

    protected boolean removeManModelWithName(String modelName) {
        if (this.peopleModels.isEmpty()) {
            return false;
        }
        Iterator<ModelManager.ModelPresets> iter = this.peopleModels.iterator();
        while (iter.hasNext()) {
            ModelManager.ModelPresets model = iter.next();
            if (!model.is_man || model.getName().compareTo(modelName) != 0) continue;
            iter.remove();
            return true;
        }
        return false;
    }

    protected boolean removeWomanModelWithName(String modelName) {
        if (this.peopleModels.isEmpty()) {
            return false;
        }
        Iterator<ModelManager.ModelPresets> iter = this.peopleModels.iterator();
        while (iter.hasNext()) {
            ModelManager.ModelPresets model = iter.next();
            if (model.is_man || model.getName().compareTo(modelName) != 0) continue;
            iter.remove();
            return true;
        }
        return false;
    }

    public SCRanimparts CreateAnmanmFive(SCRperson person, String name_mim, String name_leg, String name_body, String name_head, String name_rhand, String name_lhand) {
        SCRanimparts poit_tr = new SCRanimparts();
        poit_tr.nAnimParts(person, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);
        return poit_tr;
    }

    public SCRanimparts CreateAnm(SCRperson pers, String animName, int _type) {
        String commonname = pers.GetName();
        commonname = commonname + '_';
        String name_mim = "_face";
        String name_leg = "_legs";
        String name_body = "_body";
        String name_head = "_head";
        String name_rhand = "_hand_r";
        String name_lhand = "_hand_l";
        name_mim = commonname + animName + name_mim;
        name_leg = commonname + animName + name_leg;
        name_body = commonname + animName + name_body;
        name_head = commonname + animName + name_head;
        name_rhand = commonname + animName + name_rhand;
        name_lhand = commonname + animName + name_lhand;
        SCRanimparts poit_tr = new SCRanimparts();
        poit_tr.AnimPartsIgnorNull(pers, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);
        pers.AddAnimGroup(poit_tr, _type);
        if (_type == 700) {
            poit_tr.TuneLoop(true);
        }
        if (_type == 800) {
            poit_tr.TuneLoop(false);
        }
        return poit_tr;
    }

    public SCRanimparts CreateAnmSingle(SCRperson person, String animName) {
        SCRanimparts poit_tr = new SCRanimparts();
        poit_tr.AnimPartsIgnorNull(person, null, animName, null, null, null, null);
        return poit_tr;
    }

    public SCRanimparts CreateAnm(SCRperson person, String animName) {
        String name_mim = "_face";
        String name_leg = "_legs";
        String name_body = "_body";
        String name_head = "_head";
        String name_rhand = "_hand_r";
        String name_lhand = "_hand_l";
        name_mim = animName + name_mim;
        name_leg = animName + name_leg;
        name_body = animName + name_body;
        name_head = animName + name_head;
        name_rhand = animName + name_rhand;
        name_lhand = animName + name_lhand;
        SCRanimparts poit_tr = new SCRanimparts();
        poit_tr.AnimPartsIgnorNull(person, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);
        return poit_tr;
    }

    public SCRanimparts CreateSimpleAnm(SCRperson person, String animName) {
        String name_mim = "_face";
        String name_leg = "";
        String name_body = "_body";
        String name_head = "_head";
        String name_rhand = "_hand_r";
        String name_lhand = "_hand_l";
        name_mim = animName + name_mim;
        name_leg = animName + name_leg;
        name_body = animName + name_body;
        name_head = animName + name_head;
        name_rhand = animName + name_rhand;
        name_lhand = animName + name_lhand;
        SCRanimparts poit_tr = new SCRanimparts();
        poit_tr.AnimPartsIgnorNull(person, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);
        return poit_tr;
    }

    public Eventanm CreateEvent() {
        Eventanm posss = new Eventanm();
        posss.nevent();
        return posss;
    }

    public static int RandomFromNom(int nm) {
        if (nm <= 1) {
            return nm;
        }
        int res = (int)((double)nm * Math.random() + 1.0);
        if (res > nm) {
            res = nm;
        }
        return res;
    }

    public static double RandomVelocity(double velin) {
        double velR = 0.9;
        return velin *= (velR += 0.2 * Math.random());
    }

    public static double RandomLength(double fromtime, double totime) {
        double velR = fromtime;
        return velR += (totime - fromtime) * Math.random();
    }

    public Chainanm CreateChain(SCRperson inanim) {
        Chainanm posss = new Chainanm();
        posss.nchain(inanim);
        return posss;
    }
}

