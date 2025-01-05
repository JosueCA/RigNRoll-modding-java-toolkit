/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.List;
import java.util.logging.Level;
import org.w3c.dom.Node;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.MissionCreationContext;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.infochannels.InformationChannelDataCreationException;
import scenarioXml.XmlNodeDataProcessor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class InfoChannelsXmlExtractor
implements XmlNodeDataProcessor {
    private List<InformationChannelData> store = null;
    private MissionCreationContext context;

    InfoChannelsXmlExtractor(List<InformationChannelData> store, MissionCreationContext context) {
        assert (null != store) : "store must be non-null reference";
        this.store = store;
        this.context = context;
    }

    // @Override
    public void process(Node target, Object param) {
        assert (null != target) : "target must be non-null reference";
        try {
            this.store.add(new InformationChannelData(target, this.context));
        }
        catch (InformationChannelDataCreationException e) {
            MissionsLogger.getInstance().doLog(e.getLocalizedMessage(), Level.SEVERE);
        }
    }
}

