package com.heqian.replugin.asm;

import com.heqian.replugin.asm.apk.ApkFile;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            ApkFile apkFile = new ApkFile();
            apkFile.setZipalignPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\zipalign.exe");
            apkFile.setApksignerPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\apksigner.bat");
            apkFile.setInZipFile(new File("C:\\Users\\Administrator\\Desktop\\1\\app.apk"));
            apkFile.setOutZipFile(new File("C:\\Users\\Administrator\\Desktop\\1\\app-1.apk"));
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

