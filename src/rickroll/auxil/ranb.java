package rickroll.auxil;

//Decompiled with: CFR 0.152
//Class Version: 5

public class ranb {
 long[] table = new long[55];
 int index1;
 int index2;

 public long next(long l) {
     this.index1 = (this.index1 + 1) % 55;
     this.index2 = (this.index2 + 1) % 55;
     this.table[this.index1] = this.table[this.index1] - this.table[this.index2] & 0xFFFFFFFFL;
     return this.table[this.index1] % l;
 }

 public long next() {
     this.index1 = (this.index1 + 1) % 55;
     this.index2 = (this.index2 + 1) % 55;
     this.table[this.index1] = this.table[this.index1] - this.table[this.index2] & 0xFFFFFFFFL;
     return this.table[this.index1];
 }

 public ranb(long l) {
     this.seed(l);
 }

 public void seed(long l) {
     int n;
     int n2;
     for (int i = 0; i < 55; ++i) {
         this.table[i] = 0L;
     }
     long l2 = 1L;
     this.table[54] = l;
     for (n2 = 0; n2 < 54; ++n2) {
         n = 21 * n2 % 55;
         this.table[n] = l2;
         l2 = l - l2 & 0xFFFFFFFFL;
         l = this.table[n];
     }
     for (n = 0; n < 4; ++n) {
         for (n2 = 0; n2 < 55; ++n2) {
             this.table[n2] = this.table[n2] - this.table[(1 + n2 + 30) % 55] & 0xFFFFFFFFL;
         }
     }
     this.index1 = 0;
     this.index2 = 31;
 }
}
