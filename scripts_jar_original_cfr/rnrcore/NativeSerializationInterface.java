/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import rnrcore.GameXmlSerializator;
import rnrcore.ISelfSerializable;
import rnrloggers.ScriptsLogger;
import rnrscenario.Sc_serial;

public final class NativeSerializationInterface {
    private static final boolean USE_SERIALIZABLES = false;
    private static GameXmlSerializator xmlSerializator = null;
    private static List<ISelfSerializable> binarySerializableTargets = new LinkedList<ISelfSerializable>();

    public static void setGameSerializator(GameXmlSerializator saver) {
        if (null == saver) {
            throw new IllegalArgumentException("saver must be non-null reference");
        }
        xmlSerializator = saver;
    }

    public static void addSelfSerializable(ISelfSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException("target must be non-null reference");
        }
        binarySerializableTargets.add(target);
    }

    public static byte[] serialize(int saveVersion) {
        Sc_serial.getInstance().recieve();
        xmlSerializator.setSave_version(saveVersion);
        try {
            byte[] xmlData = xmlSerializator.saveToByteArray();
            ByteArrayOutputStream binarySerialized = new ByteArrayOutputStream();
            ObjectOutputStream mainStream = new ObjectOutputStream(binarySerialized);
            mainStream.writeObject(xmlData);
            mainStream.close();
            binarySerialized.close();
            return binarySerialized.toByteArray();
        }
        catch (IOException exception) {
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario serialization failed");
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
            return new byte[0];
        }
    }

    private static void closeObject(Closeable target) {
        try {
            if (null != target) {
                target.close();
            }
        }
        catch (IOException exception) {
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        }
    }

    /*
     * Exception decompiling
     */
    public static void deserialize(byte[] dataArray) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:845)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

