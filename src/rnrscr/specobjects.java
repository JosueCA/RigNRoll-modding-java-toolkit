/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.Helper;
import rnrscr.ModelManager;
import rnrscr.cSpecObjects;
import rnrscr.sotypes;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class specobjects
implements sotypes {
    private static specobjects instance = null;
    public int last_SO_zone;
    ModelManager modelSource = new ModelManager();
    private ArrayList<cSpecObjects> allLoadedObjCash;
    private HashMap<String, cSpecObjects> allLoadedObjects;

    public static specobjects getInstance() {
        if (null == instance) {
            instance = new specobjects();
        }
        return instance;
    }

    public static BarPresets getBarPresets() {
        BarPresets res = new BarPresets();
        cSpecObjects bar_info = specobjects.getInstance().GetNearestLoadedBar();
        if (bar_info == null) {
            return null;
        }
        res.bardoor = "Space_DoorToBar_" + bar_info.name;
        res.P = bar_info.position;
        res.M = bar_info.matrix;
        return res;
    }

    public static PolicePresets getPolicePresets() {
        PolicePresets res = new PolicePresets();
        cSpecObjects police_info = specobjects.getInstance().GetNearestLoadedPolice();
        if (police_info == null) {
            return null;
        }
        res.P = police_info.position;
        res.M = police_info.matrix;
        return res;
    }

    public static OfficePresets getOfficePresets() {
        OfficePresets res = new OfficePresets();
        cSpecObjects police_info = specobjects.getInstance().GetNearestLoadedOffice();
        if (null == police_info) {
            return null;
        }
        res.P = police_info.position;
        res.M = police_info.matrix;
        return res;
    }

    specobjects() {
        this.modelSource.AddModel("BARMEN", 8, 1, true);
        this.modelSource.AddModel("Man_005", 16, 0, true);
        this.modelSource.AddModel("Man_003", 24, 0, true);
        this.modelSource.AddModel("Man_004", 26, 0, true);
        this.modelSource.AddModel("Man_002", 26, 0, true);
        this.modelSource.AddModel("Man_001", 26, 0, true);
        this.modelSource.AddModel("Man_006", 26, 0, true);
        this.modelSource.AddModel("Man_007", 26, 0, true);
        this.modelSource.AddModel("Man_008", 26, 0, true);
        this.modelSource.AddModel("Man_009", 10, 1, true);
        this.modelSource.AddModel("Man_010", 10, 1, true);
        this.modelSource.AddModel("Man_011", 26, 0, true);
        this.modelSource.AddModel("Man_012", 26, 0, true);
        this.modelSource.AddModel("Man_013", 26, 0, true);
        this.modelSource.AddModel("Man_014", 26, 0, true);
        this.modelSource.AddModel("Man_015", 26, 0, true);
        this.modelSource.AddModel("Man_016", 26, 0, true);
        this.modelSource.AddModel("Man_017", 26, 0, true);
        this.modelSource.AddModel("Man_018", 26, 0, true);
        this.modelSource.AddModel("Man_019", 26, 0, true);
        this.modelSource.AddModel("Man_020", 26, 0, true);
        this.modelSource.AddModel("Man_021", 26, 0, true);
        this.modelSource.AddModel("Man_022", 26, 0, true);
        this.modelSource.AddModel("Man_023", 26, 0, true);
        this.modelSource.AddModel("Man_024", 26, 0, true);
        this.modelSource.AddModel("Man_025", 26, 0, true);
        this.modelSource.AddModel("Man_026", 26, 0, true);
        this.modelSource.AddModel("Man_027", 26, 0, true);
        this.modelSource.AddModel("Man_028", 26, 0, true);
        this.modelSource.AddModel("Man_029", 26, 0, true);
        this.modelSource.AddModel("Woman001", 8, 0, false);
        this.modelSource.AddModel("Woman002", 8, 0, false);
        this.modelSource.AddModel("Woman003", 8, 0, false);
        this.modelSource.AddModel("Woman004", 8, 0, false);
        this.modelSource.AddModel("Woman005", 8, 0, false);
        this.modelSource.AddModel("Woman006", 8, 0, false);
        this.modelSource.AddModel("Woman007", 8, 0, false);
        this.modelSource.AddModel("Woman008", 8, 0, false);
        this.modelSource.AddModel("Woman009", 8, 0, false);
        this.modelSource.AddModel("Woman010", 8, 0, false);
        this.modelSource.AddModel("Woman011", 8, 0, false);
        this.modelSource.AddModel("Woman012", 8, 0, false);
        this.modelSource.AddModel("Woman013", 8, 0, false);
        this.modelSource.AddModel("Woman014", 8, 0, false);
        this.modelSource.AddModel("Woman015", 8, 0, false);
        this.modelSource.AddModel("Woman016", 8, 0, false);
        this.modelSource.AddModel("Woman017", 8, 0, false);
        this.modelSource.AddModel("Woman018", 8, 0, false);
        this.modelSource.AddModel("Woman019", 8, 0, false);
        this.allLoadedObjects = new HashMap();
        this.allLoadedObjCash = new ArrayList();
        this.last_SO_zone = 0;
    }

    public Object GetModelSource() {
        return this.modelSource;
    }

    private cSpecObjects addLoadingObject(cSpecObjects object) {
        this.allLoadedObjects.put(object.name, object);
        for (cSpecObjects cached : this.allLoadedObjCash) {
            if (cached.sotip != object.sotip || 0 != cached.name.compareTo(object.name)) continue;
            cached.matrix = object.matrix;
            cached.position = object.position;
            return cached;
        }
        this.allLoadedObjCash.add(object);
        return object;
    }

    private void removeObjectFromLoaded(String name) {
        this.allLoadedObjects.remove(name);
    }

    private cSpecObjects GetNearestObject(int sotip) {
        if (this.allLoadedObjects.isEmpty()) {
            return null;
        }
        double nearest = 2.5E9;
        vectorJ curpos = Helper.getCurrentPosition();
        if (curpos == null) {
            curpos = new vectorJ();
        }
        cSpecObjects lastgood = null;
        Set<Map.Entry<String, cSpecObjects>> setLoaded = this.allLoadedObjects.entrySet();
        for (Map.Entry<String, cSpecObjects> entry : setLoaded) {
            double diff;
            cSpecObjects current = entry.getValue();
            if (current.sotip != sotip || !((diff = current.position.len2(curpos)) < nearest)) continue;
            nearest = diff;
            lastgood = current;
        }
        return lastgood;
    }

    public cSpecObjects AddLoadedWH(cSpecObjects br) {
        br.sotip = 2;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedBar(cSpecObjects br) {
        br.sotip = 8;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedOffice(cSpecObjects br) {
        br.sotip = 1;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedSTO(cSpecObjects br) {
        br.sotip = 16;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedMotel(cSpecObjects br) {
        br.sotip = 4;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedPolice(cSpecObjects br) {
        br.sotip = 64;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedParking(cSpecObjects br) {
        br.sotip = 128;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedTruckStopExit(cSpecObjects br) {
        br.sotip = 256;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedTruckStopEnter(cSpecObjects br) {
        br.sotip = 512;
        return this.addLoadingObject(br);
    }

    public cSpecObjects AddLoadedScenarioObject(cSpecObjects br) {
        br.sotip = 1024;
        return this.addLoadingObject(br);
    }

    public cSpecObjects getCurrentObject() {
        switch (this.last_SO_zone) {
            case 2: {
                return this.GetNearestLoadedWH();
            }
            case 8: {
                return this.GetNearestLoadedBar();
            }
            case 16: {
                return this.GetNearestLoadedSTO();
            }
            case 4: {
                return this.GetNearestLoadedMotel();
            }
            case 1: {
                return this.GetNearestLoadedOffice();
            }
            case 64: {
                return this.GetNearestLoadedPolice();
            }
        }
        return this.GetNearestObject(this.last_SO_zone);
    }

    public cSpecObjects GetNearestTruckStopExit() {
        return this.GetNearestObject(256);
    }

    public cSpecObjects GetNearestTruckStopEnter() {
        return this.GetNearestObject(512);
    }

    public cSpecObjects GetNearestLoadedBar() {
        return this.GetNearestObject(8);
    }

    public cSpecObjects GetNearestLoadedWH() {
        return this.GetNearestObject(2);
    }

    public cSpecObjects GetNearestLoadedSTO() {
        return this.GetNearestObject(16);
    }

    public cSpecObjects GetNearestLoadedMotel() {
        return this.GetNearestObject(4);
    }

    public cSpecObjects GetNearestLoadedOffice() {
        return this.GetNearestObject(1);
    }

    public cSpecObjects GetNearestLoadedPolice() {
        return this.GetNearestObject(64);
    }

    public cSpecObjects GetNearestLoadedScenarioObject() {
        return this.GetNearestObject(1024);
    }

    public cSpecObjects GetLoadedNamedScenarioObject(String name) {
        return this.allLoadedObjects.get(name);
    }

    public cSpecObjects GetNearestLoadedParkingPlace() {
        return this.GetNearestObject(128);
    }

    public cSpecObjects GetNearestLoadedParkingPlace_nearBar(double lengthlimit) {
        cSpecObjects so = this.GetNearestLoadedBar();
        if (null == so) {
            return null;
        }
        cSpecObjects nearest = null;
        double len = lengthlimit * lengthlimit;
        Set<Map.Entry<String, cSpecObjects>> setLoadedObjects = this.allLoadedObjects.entrySet();
        for (Map.Entry<String, cSpecObjects> entry : setLoadedObjects) {
            double pz;
            double py;
            double px;
            double testlen;
            cSpecObjects ob = entry.getValue();
            if (128 != ob.sotip || !((testlen = (px = so.position.x - ob.position.x) * px + (py = so.position.y - ob.position.y) * py + (pz = so.position.z - ob.position.z) * pz) < len)) continue;
            len = testlen;
            nearest = ob;
        }
        return nearest;
    }

    public cSpecObjects GetNearestLoadedParkingPlace_nearBarNamed(String barfind, double lengthlimit) {
        cSpecObjects so = this.GetNearestLoadedBar();
        if (null == so || 0 != so.name.compareTo(barfind)) {
            return null;
        }
        return this.GetNearestLoadedParkingPlace_nearBar(lengthlimit);
    }

    public void DeleteLoadedBar(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedWH(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedScenarioObject(String name) {
        this.removeObjectFromLoaded(name);
    }

    public boolean ifLoadedBar(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    public boolean ifLoadedOffice(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    public boolean ifLoadedMotel(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    public boolean ifLoadedRepair(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    public boolean ifLoadedPolice(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    public boolean ifLoadedParkingPlaces(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    public void DeleteLoadedOffice(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedSTO(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedMotel(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedPolice(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedParking(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedTruckStopExit(String name) {
        this.removeObjectFromLoaded(name);
    }

    public void DeleteLoadedTruckStopEnter(String name) {
        this.removeObjectFromLoaded(name);
    }

    public ArrayList<cSpecObjects> getAllLoadedObjCash() {
        return this.allLoadedObjCash;
    }

    public void setAllLoadedObjCash(ArrayList<cSpecObjects> value) {
        ArrayList<cSpecObjects> toAdd = new ArrayList<cSpecObjects>();
        for (cSpecObjects newObject : value) {
            boolean wasInList = false;
            for (cSpecObjects currentObject : this.allLoadedObjCash) {
                if (newObject.sotip != currentObject.sotip || newObject.name.compareTo(currentObject.name) != 0) continue;
                wasInList = true;
                currentObject.SetPresets(newObject.Presets());
                break;
            }
            if (wasInList) continue;
            toAdd.add(newObject);
        }
        this.allLoadedObjCash.addAll(toAdd);
    }

    static class KeyObjects {
        private String name;
        private int type;

        KeyObjects(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    static class OfficePresets {
        matrixJ M;
        vectorJ P;

        OfficePresets() {
        }
    }

    static class PolicePresets {
        matrixJ M;
        vectorJ P;

        PolicePresets() {
        }
    }

    static class BarPresets {
        String bardoor;
        matrixJ M;
        vectorJ P;

        BarPresets() {
        }
    }
}

