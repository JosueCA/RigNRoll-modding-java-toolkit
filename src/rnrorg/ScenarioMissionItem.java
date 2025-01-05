/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

public class ScenarioMissionItem {
    private String mission_name;
    private String org_name;
    private String point_name;
    private boolean move_time;
    private boolean needFinishIcon;
    private double time_on_mission;

    public ScenarioMissionItem(String mission_name, String org_name, String point_name, double time_on_mission, boolean move_time, boolean needFinishIcon) {
        this.mission_name = mission_name;
        this.org_name = org_name;
        this.point_name = point_name;
        this.time_on_mission = time_on_mission;
        this.move_time = move_time;
        this.needFinishIcon = needFinishIcon;
    }

    public String getMission_name() {
        return this.mission_name;
    }

    public String getOrg_name() {
        return this.org_name;
    }

    public String getPoint_name() {
        return this.point_name;
    }

    public double getTimeOnMission() {
        return this.time_on_mission;
    }

    public boolean getMoveTime() {
        return this.move_time;
    }

    public boolean getNeedFinishIcon() {
        return this.needFinishIcon;
    }
}

