/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Testserialize {
    public static void main(String[] args) {
        Testserialize.config();
        try {
            Testserialize.write("E:\\BAZA\\ZZZ\\save\\1.sav");
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }
        System.err.flush();
    }

    private static void config() {
        try {
            System.setErr(new PrintStream(new FileOutputStream("E:\\BAZA\\ZZZ\\save\\err.log", false)));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void write(String filename) throws IOException {
    }
}

