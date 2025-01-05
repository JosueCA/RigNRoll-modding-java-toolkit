/*
 * Decompiled with CFR 0.151.
 */
package auxil;

import auxil.DCProcess;
import java.io.IOException;
import java.io.InputStream;
import rnrcore.eng;

public class DInputStream2
extends InputStream {
    private int markedPtr = 0;
    byte[] outBuf = null;
    int ptr = 0;

    public int available() throws IOException {
        return this.outBuf.length - this.ptr;
    }

    public void close() throws IOException {
        super.close();
    }

    public synchronized void mark(int readlimit) {
        this.markedPtr = this.ptr;
    }

    public boolean markSupported() {
        return true;
    }

    public synchronized void reset() throws IOException {
        this.ptr = this.markedPtr;
    }

    public long skip(long n) throws IOException {
        return super.skip(n);
    }

    public DInputStream2(InputStream in) throws IOException {
        byte[] inBuf = new byte[in.available()];
        int r = in.read(inBuf);
        assert (r == inBuf.length);
        while (in.available() > 0) {
            byte[] buf = new byte[in.available() + inBuf.length];
            System.arraycopy(inBuf, inBuf.length, buf, 0, inBuf.length);
            int r1 = in.read(buf, inBuf.length, in.available());
            assert (r1 == buf.length - inBuf.length);
            inBuf = buf;
        }
        this.outBuf = eng.noNative ? inBuf : DCProcess.process(inBuf, 0);
    }

    public int read() throws IOException {
        if (this.ptr < this.outBuf.length) {
            return this.outBuf[this.ptr++];
        }
        return -1;
    }
}

