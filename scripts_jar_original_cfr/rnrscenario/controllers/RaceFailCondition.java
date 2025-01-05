/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import rnrcore.ConvertGameTime;
import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.scenarioscript;
import xmlserialization.nxs.AnnotatedSerializable;
import xmlserialization.nxs.LoadFrom;
import xmlserialization.nxs.SaveTo;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
public class RaceFailCondition
implements AnnotatedSerializable {
    public static final String SERIALIZATION_NODE_NAME = "RaceFailCondition";
    private final int scenarioStage = null == scenarioscript.script.getStage() ? 0 : scenarioscript.script.getStage().getScenarioStage();
    @SaveTo(destinationNodeName="race_start_time")
    @LoadFrom(sourceNodeName="race_start_time")
    private CoreTime raceStartTime = new CoreTime();
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_TO_FAIL = 11;

    public void finalizeDeserialization() {
        if (null == this.raceStartTime) {
            this.raceStartTime = new CoreTime();
        }
    }

    public String getId() {
        return SERIALIZATION_NODE_NAME;
    }

    public boolean raceFailed() {
        CoreTime timeOfEnd = ConvertGameTime.convertFromGiven(660, this.raceStartTime);
        return 0 < new CoreTime().moreThan(timeOfEnd);
    }
}

