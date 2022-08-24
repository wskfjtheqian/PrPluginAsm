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
            apkFile.run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


