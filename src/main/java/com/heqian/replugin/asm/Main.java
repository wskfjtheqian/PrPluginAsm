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
    public static void main(String[] args) {
        try {
            MultiDexContainer<? extends DexBackedDexFile> multiDex = DexFileFactory.loadDexContainer(new File("C:\\Users\\Administrator\\Desktop\\a\\as\\classes.dex"), Opcodes.getDefault());
            for (String name : multiDex.getDexEntryNames()) {
                DexFileRewriter rewriter = new DexFileRewriter(new DexRewriter(new AsmRewriterModule()));
                DexFile outDex = rewriter.rewrite(multiDex.getEntry(name).getDexFile());
                DexFileFactory.writeDexFile("C:\\Users\\Administrator\\Desktop\\a\\as\\app-debug\\classes.dex", outDex);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
