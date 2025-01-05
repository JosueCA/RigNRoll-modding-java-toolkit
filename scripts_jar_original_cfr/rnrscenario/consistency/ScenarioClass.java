/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.consistency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ScenarioClass {
    public static final int LOOK_FOR_STAGE_IN_OBJECT_FIELD = -1;
    public static final int SHOULD_NEVER_BE_INVOKED = -2;

    public int scenarioStage();

    public String fieldWithDesiredStage() default "!!<field with scenario stage was not specified>!!";
}

