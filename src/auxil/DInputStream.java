package auxil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DInputStream extends InputStream {
	InputStream in;

	public int available() throws IOException {
		return super.available();
	}

	public void close() throws IOException {
		super.close();
	}

	public synchronized void mark(int readlimit) {
		super.mark(readlimit);
	}

	public boolean markSupported() {
		return super.markSupported();
	}

	public synchronized void reset() throws IOException {
		super.reset();
	}

	public long skip(long n) throws IOException {
		return super.skip(n);
	}

	boolean e = false;

	ranb r = null;

	int[] buf = new int[] { -1, -1, -1, -1 };

	int ptr = 4;

	public DInputStream(InputStream in) throws IOException {
		this.in = in;
		int k = 0;
		this.buf[k] = in.read();
		for (; k < 4 && in.read() != -1; k++)
			;
		if (Arrays.equals(this.buf, new int[] { 83, 68, 84, 69 })) {
			this.e = true;
			this.r = new ranb(31235L);
		} else {
			this.ptr = 0;
		}
	}

	public int read() throws IOException {
		if (this.e) {
			int src = this.in.read();
			if (src == -1)
				return src;
			return (int) (src ^ this.r.next(256L)) & 0xFF;
		}
		if (this.ptr < 4) {
			if (this.buf[this.ptr] == -1)
				return -1;
			return this.buf[this.ptr++];
		}
		return this.in.read();
	}
}
