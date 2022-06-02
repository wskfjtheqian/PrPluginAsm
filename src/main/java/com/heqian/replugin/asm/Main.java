package com.heqian.replugin.asm;

import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.iface.MultiDexContainer;
import org.jf.dexlib2.rewriter.DexFileRewriter;
import org.jf.dexlib2.rewriter.DexRewriter;
import org.jf.dexlib2.writer.io.FileDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

import java.io.File;
import java.io.IOException;

public class Main {

    public static AsmRewriterModule rewriterModule = new AsmRewriterModule();
    public static String className = "";

    public static void main(String[] args) {
        try {
            MultiDexContainer<? extends DexBackedDexFile> multiDex = DexFileFactory.loadDexContainer(new File("C:\\Users\\Administrator\\Desktop\\a\\ysa\\classes.dex"), Opcodes.getDefault());
            for (String name : multiDex.getDexEntryNames()) {
                DexFileRewriter rewriter = new DexFileRewriter(new DexRewriter(rewriterModule));
                DexFile outDex = rewriter.rewrite(multiDex.getEntry(name).getDexFile());
//                DexFileFactory.writeDexFile("C:\\Users\\Administrator\\Desktop\\a\\ysa\\classes2.dex", outDex);

                DexPool dexPool = new DexPool(outDex.getOpcodes());
                for (ClassDef classDef : outDex.getClasses()) {
                    className = classDef.getType();
                    dexPool.internClass(classDef);
                }
                className = "AAAAAAAAAAAAAAAAAA";
                dexPool.writeTo(new FileDataStore(new File("C:\\Users\\Administrator\\Desktop\\a\\ysa\\classes3.dex")));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
