package com.heqian.replugin.asm;

import com.heqian.replugin.asm.apk.ApkFile;
import com.heqian.replugin.asm.dex.AsmRewriterModule;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.iface.MultiDexContainer;
import org.jf.dexlib2.rewriter.DexFileRewriter;
import org.jf.dexlib2.rewriter.DexRewriter;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ApkFile.zipGraphDir(
                    new File("C:\\Users\\Administrator\\Desktop\\a\\ab\\app-release.apk"),
                    new File("C:\\Users\\Administrator\\Desktop\\a\\ab\\app-release1.apk")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        try {
//            MultiDexContainer<? extends DexBackedDexFile> multiDex = DexFileFactory.loadDexContainer(new File("C:\\Users\\Administrator\\Desktop\\a\\ab\\classes.dex"), Opcodes.getDefault());
//            for (String name : multiDex.getDexEntryNames()) {
//                DexFileRewriter rewriter = new DexFileRewriter(new DexRewriter(new AsmRewriterModule()));
//                DexFile outDex = rewriter.rewrite(multiDex.getEntry(name).getDexFile());
//                DexFileFactory.writeDexFile("C:\\Users\\Administrator\\Desktop\\a\\ab\\app-release\\classes.dex", outDex);
//            }
//
//             multiDex = DexFileFactory.loadDexContainer(new File("C:\\Users\\Administrator\\Desktop\\a\\ab\\classes2.dex"), Opcodes.getDefault());
//            for (String name : multiDex.getDexEntryNames()) {
//                DexFileRewriter rewriter = new DexFileRewriter(new DexRewriter(new AsmRewriterModule()));
//                DexFile outDex = rewriter.rewrite(multiDex.getEntry(name).getDexFile());
//                DexFileFactory.writeDexFile("C:\\Users\\Administrator\\Desktop\\a\\ab\\app-release\\classes2.dex", outDex);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}

