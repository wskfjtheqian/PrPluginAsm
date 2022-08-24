package com.heqian.replugin.asm;

import com.heqian.replugin.asm.apk.ApkFile;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    public static void main(String[] args) {
        ApkFile apkFile = new ApkFile();
        CmdLineParser parser = null;
        try {
            parser = new CmdLineParser(apkFile);
            parser.parseArgument(args);
            parser.printUsage(System.out);
        } catch (CmdLineException e) {
            parser.printUsage(System.err);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

//        try {
//
//
//
//            apkFile.setZipalignPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\zipalign.exe");
//            apkFile.setApksignerPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\apksigner.bat");
//            apkFile.setInZipFile(new File("D:\\dev\\live_app\\build\\app\\outputs\\flutter-apk\\app-release.apk"));
//            apkFile.setOutZipFile(new File("D:\\dev\\live_app\\build\\app\\outputs\\flutter-apk\\app-release1.apk"));
//            apkFile.setKeyPath("D:\\dev\\live_app\\android\\key\\tilki_live.jks");
//            apkFile.setStorePassword("u2rXPYCf5wCYmrIW");
//            apkFile.setKeyAlias("tilki");
//            apkFile.setKeyPassword("3v4EHbA6pUTYh8PN");
//            apkFile.run();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//    public static void main(String[] args) {
//        try {
//            ApkFile apkFile = new ApkFile();
//            apkFile.setZipalignPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\zipalign.exe");
//            apkFile.setApksignerPath("C:\\sdk\\Android\\build-tools\\33.0.0-rc2\\apksigner.bat");
//            apkFile.setInZipFile(new File("D:\\dev\\live_app\\build\\app\\outputs\\flutter-apk\\app-release.apk"));
//            apkFile.setOutZipFile(new File("D:\\dev\\live_app\\build\\app\\outputs\\flutter-apk\\app-release1.apk"));
//            apkFile.setKeyPath("D:\\dev\\live_app\\android\\key\\tilki_live.jks");
//            apkFile.setStorePassword("u2rXPYCf5wCYmrIW");
//            apkFile.setKeyAlias("tilki");
//            apkFile.setKeyPassword("3v4EHbA6pUTYh8PN");
//            apkFile.run();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//




