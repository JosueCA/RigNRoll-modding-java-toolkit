/*
 * Decompiled with CFR 0.151.
 */
package scenarioUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Pair<A, B> {
    private A first = null;
    private B second = null;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return this.first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return this.second;
    }

    public void setSecond(B second) {
        this.second = second;
    }
}

