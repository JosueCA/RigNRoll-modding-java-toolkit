/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import players.Crew;
import players.actorveh;
import players.aiplayer;

public class MissionPassanger {
    private actorveh car;
    private aiplayer npc;
    private aiplayer pack;
    private static MissionPassanger instance = null;

    public static void deinit() {
        instance = null;
    }

    public static MissionPassanger getInstance() {
        if (null == instance) {
            instance = new MissionPassanger();
        }
        return instance;
    }

    private MissionPassanger() {
    }

    static void start(String identitie, String person_ref_name) {
        MissionPassanger.getInstance();
        if (MissionPassanger.instance.car != null && MissionPassanger.instance.npc != null) {
            MissionPassanger.instance.npc.abondoneCar(MissionPassanger.instance.car);
        }
        MissionPassanger.instance.car = Crew.getIgrokCar();
        MissionPassanger.instance.npc = aiplayer.getRefferencedAiplayer(identitie);
        MissionPassanger.instance.npc.sPoolBased(person_ref_name);
        MissionPassanger.instance.npc.bePassangerOfCar(MissionPassanger.instance.car);
    }

    static void start(String person_ref_name) {
        MissionPassanger.getInstance();
        if (MissionPassanger.instance.car != null && MissionPassanger.instance.pack != null) {
            MissionPassanger.instance.pack.abondoneCar(MissionPassanger.instance.car);
        }
        MissionPassanger.instance.car = Crew.getIgrokCar();
        MissionPassanger.instance.pack = aiplayer.getRefferencedAiplayer(person_ref_name);
        MissionPassanger.instance.pack.sPoolBased(person_ref_name);
        MissionPassanger.instance.pack.bePackOfCar(MissionPassanger.instance.car);
    }

    static void finish(boolean pass) {
        if (pass) {
            MissionPassanger.getInstance();
            if (MissionPassanger.instance.car != null && MissionPassanger.instance.npc != null) {
                MissionPassanger.instance.npc.abondoneCar(MissionPassanger.instance.car);
            }
            MissionPassanger.instance.npc = null;
            if (MissionPassanger.instance.pack == null) {
                MissionPassanger.instance.car = null;
            }
        } else {
            MissionPassanger.getInstance();
            if (MissionPassanger.instance.car != null && MissionPassanger.instance.pack != null) {
                MissionPassanger.instance.pack.abondoneCar(MissionPassanger.instance.car);
            }
            MissionPassanger.instance.pack = null;
            if (MissionPassanger.instance.npc == null) {
                MissionPassanger.instance.car = null;
            }
        }
    }

    public actorveh getCar() {
        return this.car;
    }

    public aiplayer getNpc() {
        return this.npc;
    }

    public aiplayer getPack() {
        return this.pack;
    }

    public void setCar(actorveh car) {
        this.car = car;
    }

    public void setNpc(aiplayer npc) {
        this.npc = npc;
    }

    public void setPack(aiplayer pack) {
        this.pack = pack;
    }
}

