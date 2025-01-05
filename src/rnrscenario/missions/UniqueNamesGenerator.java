/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

public final class UniqueNamesGenerator {
    private String namePrefix = null;
    private long counter = 0L;

    public UniqueNamesGenerator(String namePrefix) {
        assert (null != namePrefix) : "namePrefix must be non-null reference";
        this.namePrefix = namePrefix;
    }

    public String getName() {
        ++this.counter;
        return this.namePrefix + Long.toString(this.counter);
    }
}

