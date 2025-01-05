/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization.nxs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD, ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface SaveTo {
    public String destinationNodeName();

    public int saveVersion() default 0;

    public int constructorArgumentNumber() default -1;
}

