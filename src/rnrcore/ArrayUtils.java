/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

public class ArrayUtils {
    public static byte[] merge(byte[] ... arraysToMerge) {
        if (0 == arraysToMerge.length) {
            return new byte[0];
        }
        int size = 0;
        for (byte[] array : arraysToMerge) {
            size += array.length;
        }
        byte[] merged = new byte[size];
        int currentPosition = 0;
        for (byte[] array : arraysToMerge) {
            System.arraycopy(array, 0, merged, currentPosition, array.length);
            currentPosition += array.length;
        }
        return merged;
    }
}

