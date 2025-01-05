/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.io.Serializable;
import java.util.ArrayList;
import rnrscenario.missions.infochannels.IChooseAppropriateChannel;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ChooseChannelByPoints
implements Serializable {
    static final long serialVersionUID = 0L;

    public static void choose(ArrayList<IChooseAppropriateChannel> channels) {
        if (channels == null || channels.isEmpty()) {
            return;
        }
        if (channels.size() == 1) {
            IChooseAppropriateChannel channel_data = channels.get(0);
            ArrayList<String> places = channel_data.getPlaces();
            if (places.size() == 1) {
                channel_data.choose(places.get(0));
            } else if (places.size() > 1) {
                channel_data.choose(places.get(0));
            } else {
                channel_data.choose();
            }
        } else {
            IChooseAppropriateChannel channel_data = channels.get(0);
            ArrayList<String> places = channel_data.getPlaces();
            if (places.size() == 1) {
                channel_data.choose(places.get(0));
            } else if (places.size() > 1) {
                channel_data.choose(places.get(0));
            } else {
                channel_data.choose();
            }
        }
    }
}

