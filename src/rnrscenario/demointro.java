// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario;

import players.Crew;
import rnrcore.vectorJ;
import rnrscenario.messgroup;
import rnrscenario.sctask;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;

public class demointro
extends sctask {
    String Enter_for_tutorial = "Exit_to_OV_SB_TS";
    vectorJ vEnter_for_tutorial = new vectorJ(1678.0, -23737.0, 21.0);
    int count_runs = 0;
    int count_2 = 0;
    String Tutorial1TextOld = "To take your first order, go to the nearest base. Be guided by the road signs and the map. To invoke the map press <Tab>. To change the map scale press <Tab> once again. Choose the order and go to the base in the specified destination. To receive the maximal profit you have to overtake all competitors (they will have trailers of the same type as you). To be recovered on the move, press <Ctrl 9>, to refuel and be repaired - press <Backspace>.";
    String Tutorial2TextOld = "You can drive into the truck stop (the exit is to the right from you) to refuel, repair the truck, to learn the news or just to have a rest. - To refuel, drive inside of the gas station and press <F2> after the prompt appeared; - To repair the truck, approach the entrance of the repair station and press <F2> after the prompt appeared; - To learn the news approach the bar entrance and press <F2> after the prompt appeared; - To have a rest, approach the motel and press <F2> after the prompt appeared;";
    public static final String Tutorial1Text = "TUTORIAL - WAREHOUSE";
    public static final String Tutorial2Text = "TUTORIAL - TRUCKSTOP";

    demointro() {
        super(3, false);
    }

    public void run() {
        ++this.count_runs;
        if (this.count_runs == 2) {
            this.createinfoMenu();
        }
        ++this.count_2;
        if (this.count_2 == 2) {
            vectorJ pPlayerDir;
            vectorJ dir;
            vectorJ pPlayer;
            this.count_2 = 0;
            specobjects so = specobjects.getInstance();
            cSpecObjects obj = so.GetNearestTruckStopEnter();
            if (obj != null && obj.name.compareToIgnoreCase(this.Enter_for_tutorial) == 0 && (pPlayer = Crew.getIgrokCar().gPosition()).len2(obj.position) < 22500.0 && (dir = new vectorJ(0.949, -0.31, 0.054)).dot(pPlayerDir = Crew.getIgrokCar().gDir()) > 0.5) {
                this.createTruckStopinfoMenu();
                this.finish();
            }
        }
    }

    void createinfoMenu() {
        messgroup mess = new messgroup(Tutorial1Text, true, true, 30.0, "ESC", true, false);
        mess.start();
    }

    void createTruckStopinfoMenu() {
        messgroup mess = new messgroup(Tutorial2Text, true, true, 30.0, "ESC", true, false);
        mess.start();
    }

    void createJournalnote1() {
    }

    void createJournalnote2() {
    }
}
