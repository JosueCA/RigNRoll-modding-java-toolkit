package rickroll.auxil;

//Decompiled with: CFR 0.152
//Class Version: 5

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DInputStream
extends InputStream {
 InputStream in;
 boolean e = false;
 ranb r = null;
 int[] buf = new int[]{-1, -1, -1, -1};
 int ptr = 4;

 public int available() throws IOException {
     return super.available();
 }

 public void close() throws IOException {
     super.close();
 }

 public synchronized void mark(int n) {
     super.mark(n);
 }

 public boolean markSupported() {
     return super.markSupported();
 }

 public synchronized void reset() throws IOException {
     super.reset();
 }

 public long skip(long l) throws IOException {
     return super.skip(l);
 }

 public DInputStream(InputStream inputStream) throws IOException {
     this.in = inputStream;
     for (int i = 0; i < 4 && (this.buf[i] = inputStream.read()) != -1; ++i) {
     }
     if (Arrays.equals(this.buf, new int[]{83, 68, 84, 69})) {
         this.e = true;
         this.r = new ranb(31235L);
     } else {
         this.ptr = 0;
     }
 }

 public int read() throws IOException {
     if (this.e) {
         int n = this.in.read();
         if (n == -1) {
             return n;
         }
         return (int)((long)n ^ this.r.next(256L)) & 0xFF;
     }
     if (this.ptr < 4) {
         if (this.buf[this.ptr] == -1) {
             return -1;
         }
         return this.buf[this.ptr++];
     }
     return this.in.read();
 }
}
