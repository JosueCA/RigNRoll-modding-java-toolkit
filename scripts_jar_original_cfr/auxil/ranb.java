/*
 * Decompiled with CFR 0.151.
 */
package auxil;

public class ranb {
    long[] table = new long[55];
    int index1;
    int index2;

    public long next(long limit) {
        this.index1 = (this.index1 + 1) % 55;
        this.index2 = (this.index2 + 1) % 55;
        this.table[this.index1] = this.table[this.index1] - this.table[this.index2] & 0xFFFFFFFFL;
        return this.table[this.index1] % limit;
    }

    public long next() {
        this.index1 = (this.index1 + 1) % 55;
        this.index2 = (this.index2 + 1) % 55;
        this.table[this.index1] = this.table[this.index1] - this.table[this.index2] & 0xFFFFFFFFL;
        return this.table[this.index1];
    }

    public ranb(long j) {
        this.seed(j);
    }

    public void seed(long j) {
        int i;
        for (int q = 0; q < 55; ++q) {
            this.table[q] = 0L;
        }
        long k = 1L;
        this.table[54] = j;
        for (i = 0; i < 54; ++i) {
            int ii = 21 * i % 55;
            this.table[ii] = k;
            k = j - k & 0xFFFFFFFFL;
            j = this.table[ii];
        }
        for (int loop = 0; loop < 4; ++loop) {
            for (i = 0; i < 55; ++i) {
                this.table[i] = this.table[i] - this.table[(1 + i + 30) % 55] & 0xFFFFFFFFL;
            }
        }
        this.index1 = 0;
        this.index2 = 31;
    }
}

