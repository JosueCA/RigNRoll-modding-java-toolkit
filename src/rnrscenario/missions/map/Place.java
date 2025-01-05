/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.map;

import java.io.Serializable;
import rnrcore.vectorJ;
import rnrscenario.missions.MissionSystemInitializer;

public final class Place
implements Comparable,
Serializable {
    static final long serialVersionUID = 0L;
    public static final int NULL_POINT = -1;
    public static final int BAR_POINT = 0;
    public static final int MISSION_POINT = 1;
    public static final int OFFICE_POINT = 2;
    public static final int MOTEL_POINT = 3;
    public static final int WAREHOUSE_POINT = 4;
    public static final int REPAIR_POINT = 5;
    public static final int GAS_POINT = 6;
    public static final int SCENARIO_POINT = 7;
    private int tip = 0;
    private String name = null;
    private vectorJ position = null;
    public static final String NODE_NAME_TO_CONSTRUCT_FORM = "point";

    public static void createPoint(int tip, String name, vectorJ position) {
        Place place = new Place();
        place.init(tip, name, position);
        MissionSystemInitializer.getMissionsMap().addPlace(place);
    }

    public void init(int tip, String name, vectorJ position) {
        assert (null != position) : "position must be non-null reference";
        assert (null != name) : "externalName must be non-null reference";
        this.position = position;
        this.name = name;
        this.tip = tip;
    }

    public int getTip() {
        return this.tip;
    }

    public String getName() {
        return this.name;
    }

    public vectorJ getCoords() {
        return this.position;
    }

    public double distance(vectorJ point) {
        return vectorJ.oMinus(point, this.position).length();
    }

    public double distance2(vectorJ point) {
        return point.len2(this.position);
    }

    public int compareTo(Object o) {
        if (null == o) {
            return 1;
        }
        assert (o instanceof Place || o instanceof String) : "supported types: String, Place";
        if (o instanceof Place) {
            return this.name.compareTo(((Place)o).name);
        }
        return this.name.compareTo((String)o);
    }
}

