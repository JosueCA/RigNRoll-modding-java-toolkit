/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rnrscenario.missions.MissionInfo;
import rnrscr.parkingplace;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MissionPlacement
implements Serializable {
    static final long serialVersionUID = 0L;
    private MissionInfo missionInfo = null;
    private List<String> points = null;
    private HashMap<String, parkingplace> parking = new HashMap();
    private HashMap<String, Integer> parking_num = new HashMap();
    private HashMap<String, ArrayList<Integer>> parking_list = new HashMap();
    private int p_cnt;

    public void putParking(String point, int N) {
        this.parking_num.put(point, new Integer(N));
    }

    public void putParking(String point, parkingplace pk) {
        this.parking.put(point, pk);
    }

    public void putParking(String point, ArrayList<Integer> list) {
        this.parking_list.put(point, list);
    }

    public int getParkingN(String point) {
        if (this.parking_num.isEmpty()) {
            return 0;
        }
        Integer nn = this.parking_num.get(point);
        if (nn == null) {
            return 0;
        }
        return nn;
    }

    public parkingplace getParking(String point) {
        return this.parking.get(point);
    }

    public ArrayList<Integer> getParkingPP(String point) {
        return this.parking_list.get(point);
    }

    public int getParkingFromLock(String point) {
        ArrayList<Integer> list = this.getParkingPP(point);
        if (list.size() <= this.p_cnt) {
            return 1;
        }
        int pp = list.get(this.p_cnt);
        ++this.p_cnt;
        return pp;
    }

    public MissionPlacement(MissionInfo missionInfo, List<String> points) {
        assert (null != missionInfo) : "missionInfo must be non-null reference";
        assert (null != points) : "points must be non-null reference";
        this.missionInfo = missionInfo;
        this.points = points;
    }

    public MissionInfo getInfo() {
        return this.missionInfo;
    }

    public List<String> getPoints() {
        return this.points;
    }
}

