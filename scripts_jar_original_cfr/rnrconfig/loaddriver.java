/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import java.util.HashMap;
import players.actorveh;
import players.aiplayer;
import rnrconfig.PassangerShifts;
import rnrcore.Helper;
import rnrcore.SCRcardriver;
import rnrcore.SCRcarpassanger;
import rnrcore.eng;
import rnrcore.vectorJ;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public class loaddriver {
    private static HashMap<String, String> _modelNameToPrefix = new HashMap();
    private static Boolean _firstConstructorCall = true;
    private static final boolean FAST_DRIVERS = true;

    public loaddriver() {
        if (_firstConstructorCall.booleanValue()) {
            loaddriver.InitHashMap();
            _firstConstructorCall = false;
        }
    }

    private static void InitHashMap() {
        String fileName = "..\\data\\config\\PassangerAnimations.xml";
        Node top = XmlUtils.parse(fileName);
        if (top == null) {
            return;
        }
        String topName = top.getName();
        if (topName != "PassangerAnimations") {
            return;
        }
        NodeList pairs = top.getNamedChildren("Entry");
        for (int i = 0; i < pairs.size(); ++i) {
            Node node = (Node)pairs.get(i);
            String modelName = node.getAttribute("modelName");
            String prefix = node.getAttribute("prefix");
            loaddriver.AddPair(modelName, prefix);
        }
    }

    private static void AddPair(String modelName, String prefix) {
        _modelNameToPrefix.put(modelName, prefix);
    }

    public SCRcardriver Init(aiplayer player) {
        SCRcardriver drv = SCRcardriver.CreateCarDriver(player.getModel());
        String prefix = this.gAnimationPrefix(player.gModelname());
        if (null != prefix) {
            boolean make_fast;
            boolean bl = make_fast = prefix.startsWith("Man") || prefix.startsWith("Woman");
            if (!make_fast) {
                drv.AddSitPose(prefix + "_sit_pose");
                drv.AddPedalingAnimation(prefix);
                drv.AddSteeringAnimation(prefix + "_rul01_RF", 0, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_RF", 0, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_LB", 1, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_LB", 1, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_LF", 2, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_LF", 2, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_RB", 3, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_RB", 3, 0);
                drv.AddGearingAnimation(prefix + "_rul01", 1);
            } else {
                drv.AddSitPose(prefix + "_sit_pose");
                drv.AddSteeringAnimation(prefix + "_rul01_LF", 2, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_RB", 3, 0);
            }
        }
        vectorJ pos = new vectorJ(0.0, 0.0, -0.839);
        drv.SetShift(pos);
        drv.play();
        return drv;
    }

    public SCRcarpassanger InitPassanger(aiplayer player, actorveh car, int stateflag) {
        SCRcarpassanger drv = this.InitPassanger_NoShift(player, stateflag);
        String prefix = this.gAnimationPrefix(player.gModelname());
        if (prefix != null) {
            drv.SetShift(this.GetPassendgerShift(prefix, eng.GetVehiclePrefix(car.getCar())));
        } else if (this.hasPackageShift(player.gPoolRefName())) {
            drv.SetShift(Helper.getPackageShift());
        }
        return drv;
    }

    public SCRcarpassanger InitPassanger_NoShift(aiplayer player, int stateflag) {
        SCRcarpassanger drv = new SCRcarpassanger();
        drv.initPassanger(player.getModel(), stateflag);
        String prefix = this.gAnimationPrefixForPassanger(player.gModelname());
        if (prefix != null) {
            drv.AddSitPose(prefix + "_sit_pose");
            drv.AddRockingAnimation(prefix + "_pas01_RF", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RF1", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB1", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF1", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB", 3, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB1", 3, 0);
        }
        drv.play();
        return drv;
    }

    public SCRcarpassanger InitPassanger_NoShift(aiplayer player, String perfix, int stateflag) {
        SCRcarpassanger drv = new SCRcarpassanger();
        drv.initPassanger(player.getModel(), stateflag);
        String prefix = this.gAnimationPrefixForPassanger(player.gModelname());
        if (prefix != null) {
            drv.AddSitPose(prefix + "_" + perfix + "_sit_pose");
            drv.AddRockingAnimation(prefix + "_pas01_RF", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RF1", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB1", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF1", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB", 3, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB1", 3, 0);
        }
        drv.play();
        return drv;
    }

    private boolean hasNoPassengerShift(String personmodelname) {
        return personmodelname.compareTo("BANDIT_JOE") == 0 || personmodelname.compareTo("IVAN_NEW") == 0;
    }

    private boolean hasPackageShift(String personmodelname) {
        return personmodelname.contains("package");
    }

    public vectorJ GetPassendgerShift(String personmodelname, String prefix) {
        vectorJ shift = new vectorJ(0.0, 0.0, -0.839);
        if (null == personmodelname || null == prefix) {
            return shift;
        }
        if (!this.hasNoPassengerShift(personmodelname) && !this.hasPackageShift(personmodelname)) {
            shift = PassangerShifts.getInstance().getShift(prefix);
        } else if (this.hasPackageShift(personmodelname)) {
            shift = Helper.getPackageShift();
        }
        return shift;
    }

    public String gAnimationPrefix(String modelname) {
        if (null == modelname) {
            return null;
        }
        if (modelname.startsWith("Man")) {
            return "Man";
        }
        if (modelname.startsWith("Woman")) {
            return "Woman";
        }
        if (modelname.endsWith("_Slow")) {
            String[] res = modelname.split("_Slow");
            return res[0];
        }
        return modelname;
    }

    public String gAnimationPrefixForPassanger(String modelname) {
        if (null == modelname) {
            return null;
        }
        if (_modelNameToPrefix.containsKey(modelname)) {
            return _modelNameToPrefix.get(modelname);
        }
        return this.gAnimationPrefix(modelname);
    }
}

