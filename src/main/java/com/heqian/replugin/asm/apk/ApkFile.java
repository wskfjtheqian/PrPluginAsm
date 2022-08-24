package com.heqian.replugin.asm.apk;

import com.heqian.replugin.asm.dex.AsmRewriterModule;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.rewriter.DexFileRewriter;
import org.jf.dexlib2.rewriter.DexRewriter;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.Enumeration;
import java.util.StringJoiner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ApkFile {

    @Option(name = "-i", aliases = "--inZip", required = true, usage = "Input Apk file path")
    public File inZipFile;

    @Option(name = "-o", aliases = "--outZip", required = true, usage = "Output Apk file path")
    public File outZipFile;

    @Option(name = "-k", aliases = "--key", required = true, usage = "Key file path")
    public String keyPath;

    @Option(name = "-s", aliases = "--store", required = true, usage = "Key store password")
    public String storePassword;

    @Option(name = "-a", aliases = "--alias", required = true, usage = "Key alias")
    public String keyAlias;

    @Option(name = "-p", aliases = "--pass", required = true, usage = "Key password")
    public String keyPassword;

    @Option(name = "-g", aliases = "--apksigner", required = true, usage = "Android build tools apksignerPath path")
    public String apksignerPath;

    @Option(name = "-z", aliases = "--zipalign", required = true, usage = "Android build tools zipalign path")
    public String zipalignPath;

    private DexBackedDexFile inputStreamToDexFile(InputStream inputStream) throws IOException {
        try {
            return DexBackedDexFile.fromInputStream(Opcodes.getDefault(), inputStream);
        } catch (DexBackedDexFile.NotADexFile ex) {
        }

        try {
            return DexBackedOdexFile.fromInputStream(Opcodes.getDefault(), inputStream);
        } catch (DexBackedOdexFile.NotAnOdexFile ex) {
        }

        return null;
    }

    private File zipGraphDir() throws IOException {
        DexFileRewriter rewriter = new DexFileRewriter(new DexRewriter(new AsmRewriterModule()));

        ZipFile inZip = new ZipFile(inZipFile);
        File notSinger = new File(outZipFile.getParentFile().getPath() + "\\not_zipalign.apk");
        ZipOutputStream zipOutStream = new ZipOutputStream(new FileOutputStream(notSinger));

        byte[] buffer = new byte[4098];

        Enumeration<? extends ZipEntry> entries = inZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().startsWith("META-INF/")) {
                if (!entry.getName().startsWith("META-INF/services")) {
                    System.out.println("delete:" + entry.getName());
                    continue;
                }
            }
            InputStream inputStream = new BufferedInputStream(inZip.getInputStream(entry));

            ZipEntry zipEntry = new ZipEntry(entry);
            zipEntry.setCompressedSize(-1);

            zipOutStream.putNextEntry(zipEntry);

            if (entry.getName().matches("classes(|[\\d]+).dex")) {
                System.out.println("edit:" + entry.getName());
                DexBackedDexFile dexFile = inputStreamToDexFile(inputStream);
                DexFile outDex = rewriter.rewrite(dexFile);

                DexPool dexPool = new DexPool(outDex.getOpcodes());
                for (ClassDef classDef : outDex.getClasses()) {
                    dexPool.internClass(classDef);
                }

                MemoryDataStore store = new MemoryDataStore();
                dexPool.writeTo(store);
                zipOutStream.write(store.getBuffer(), 0, store.getSize());
                continue;
            }

            System.out.println("copy:" + entry.getName());

            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                zipOutStream.write(buffer, 0, len);
            }
            inputStream.close();
            zipOutStream.closeEntry();
        }
        inZip.close();
        zipOutStream.close();
        return notSinger;
    }

    public void run() throws Exception {
        File notSingerApk = zipGraphDir();

        File zipalignApk = new File(outZipFile.getParentFile().getPath() + "\\not_singer.apk");
        if (zipalignApk.exists()) {
            zipalignApk.delete();
        }

        StringJoiner joiner = new StringJoiner(" ", "", "");
        joiner.add(zipalignPath);
        joiner.add("-v");
        joiner.add("4");
        joiner.add(notSingerApk.getPath());
        joiner.add(zipalignApk.getPath());
        exec(joiner);

        if (outZipFile.exists()) {
            outZipFile.delete();
        }
        joiner = new StringJoiner(" ", "", "");
        joiner.add(apksignerPath);
        joiner.add("sign");
        joiner.add("--ks");
        joiner.add(keyPath);
        joiner.add("--ks-pass");
        joiner.add("pass:" + storePassword);
        joiner.add("--ks-key-alias");
        joiner.add(keyAlias);
        joiner.add("--key-pass");
        joiner.add("pass:" + keyPassword);
        joiner.add("-out");
        joiner.add(outZipFile.getPath());
        joiner.add(zipalignApk.getPath());
        exec(joiner);

    }

    private void exec(StringJoiner joiner) throws Exception {
        BufferedReader inputReader = null;
        BufferedReader errorReader = null;
        try {
            System.out.println(joiner.toString());
            Process process = Runtime.getRuntime().exec(joiner.toString());
            String str;
            inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (null != (str = inputReader.readLine())) {
                System.out.println(str);
            }
            inputReader.close();

            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while (null != (str = errorReader.readLine())) {
                System.out.println(str);
            }
            errorReader.close();
            process.waitFor();
        } finally {

            if (null != inputReader) {
                try {
                    inputReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != errorReader
            ) {
                try {
                    errorReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
