/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.io.IOException;
import org.w3c.dom.NodeList;
import rnrcore.ScenarioSync;
import rnrcore.eng;
import rnrscenario.missions.MissionInfo;
import rnrscenario.scenarioscript;
import scenarioXml.XmlDocument;

public class MissionsTestClass {
    public static void main(String[] arguments) {
        try {
            XmlDocument document = new XmlDocument("qed_missions.xml");
            eng.noNative = true;
            new scenarioscript();
            NodeList missions = document.getContent().getElementsByTagName("mission");
            new MissionInfo(missions.item(0), "qq");
            ScenarioSync.interruptScriptRunningThread();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

