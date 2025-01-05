/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.Arrays;
import java.util.LinkedList;

public class MainTestClass {
    private static final double[] DISTANCES = new double[]{10.0, 100.0, 1000.0, 3000.0};
    private static final double[] DISTANCES_q = new double[0];

    public static void main(String[] arguments) throws NoSuchFieldException, IllegalAccessException {
        A goo = new A();
        A.class.getField("a").set(goo, "1");
        int insertion = Arrays.binarySearch(DISTANCES, 40000.0);
        int ins = Arrays.binarySearch(DISTANCES_q, 1.0);
        ins = -(1 + ins);
        insertion = -(1 + insertion);
        int aa = 11;
        System.out.print(aa %= 0);
        String qqq = "qqq";
        if (qqq.getClass() == String.class) {
            System.out.print("qqq");
        }
        LinkedList<Eum> a = new LinkedList<Eum>();
        a.add(Eum.FOO);
        if (a.contains((Object)Eum.FOO)) {
            System.out.print("fff");
        }
        if (a.contains((Object)Eum.TOO)) {
            System.out.print("too");
        }
    }

    static class A {
        public int a = 0;

        A() {
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static enum Eum {
        TOO{

            public String toString() {
                return "too";
            }
        }
        ,
        FOO;

    }
}

