/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.Crew;
import players.ScenarioPersonPassanger;
import players.actorveh;
import players.aiplayer;
import rnrcore.SCRuniperson;
import rnrscenario.IShootChasing;
import rnrscenario.ShootChasing;

public class ChaseKohShootAnimate
extends ShootChasing {
    private static final String[] mACTOR_NAME = new String[]{"ONTANELIO_Slow", "HKMP7PDW", "Model_shoot"};
    private static final String[] mTRACKS = new String[]{"02070shootbegin", "02070shootfinish", "02070shootcycle"};
    private aiplayer[] aux_player = new aiplayer[2];

    public ChaseKohShootAnimate(IShootChasing chase, actorveh car_running, boolean isFromLoad) {
        super(chase, Crew.getIgrokCar(), car_running, mACTOR_NAME, mTRACKS);
        this.init(isFromLoad);
    }

    protected void prepareForScenes(boolean isFromLoad) {
        Crew.getIgrokCar().registerCar("gepard");
        aiplayer person_player = new aiplayer("SC_ONTANIELOLOW");
        person_player.setModelCreator(new ScenarioPersonPassanger(), "shooter");
        aiplayer gun_player = new aiplayer("SC_DAKOTAGUN");
        gun_player.setModelCreator(new ScenarioPersonPassanger(), "gun");
        if (!isFromLoad) {
            person_player.bePassangerOfCar(Crew.getIgrokCar());
            gun_player.bePassangerOfCar_simple(Crew.getIgrokCar());
        }
        this.aux_player[0] = person_player;
        this.aux_player[1] = gun_player;
    }

    protected void prepareForFinish() {
        Crew.getIgrokCar().dropOffPassangersButDriver();
        for (int i = 0; i < this.aux_player.length; ++i) {
            this.aux_player[i].delete();
            this.aux_player[i] = null;
        }
    }

    protected SCRuniperson getShoterModel() {
        return this.aux_player[0].getModel();
    }

    protected SCRuniperson getGunModel() {
        return this.aux_player[1].getModel();
    }
}

