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
public @interface LoadFrom {
    public String sourceNodeName();

    public int fromVersion() default 0;

    public int untilVersion() default 0x7FFFFFFF;
}

