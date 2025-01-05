/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.ArrayList;
import players.ImodelCreate;
import players.aiplayer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface routI {
    public aiplayer find(String var1);

    public aiplayer findByVehiclePassport(int var1);

    public aiplayer add(String var1, String var2, ImodelCreate var3, String var4);

    public void remove(String var1);

    public aiplayer removeByVehiclePassport(int var1);

    public ArrayList<aiplayer> gRout();
}

