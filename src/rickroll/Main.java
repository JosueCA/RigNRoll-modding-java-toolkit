package rickroll;

import java.io.IOException;
import java.util.Properties;

public class Main {
	
	// CRASH
	static {
        try {
            System.loadLibrary("rnr");
        }
        catch (UnsatisfiedLinkError e) {
            Properties prop = System.getProperties();
            System.err.println("java.library.path: " + prop.getProperty("java.library.path"));
            System.err.println("Cannot link rnr");
        }
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Main.java");
	}

}
