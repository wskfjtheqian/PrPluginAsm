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
            ApkFile apkFile = new ApkFile();
            apkFile.setZipalignPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\zipalign.exe");
            apkFile.setApksignerPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\apksigner.bat");
            apkFile.setInZipFile(new File("C:\\Users\\Administrator\\Desktop\\a\\ab\\app-release.apk"));
            apkFile.setOutZipFile(new File("C:\\Users\\Administrator\\Desktop\\a\\ab\\app-release-b.apk"));
            apkFile.setKeyPath("D:\\dev\\RePlugin\\MyApplication\\key\\tilki_live.jks");
            apkFile.setStorePassword("u2rXPYCf5wCYmrIW");
            apkFile.setKeyAlias("tilki");
            apkFile.setKeyPassword("3v4EHbA6pUTYh8PN");
            apkFile.run();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

