package com.heqian.replugin.asm.apk;

import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
import org.jf.dexlib2.dexbacked.OatFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.io.FileDataStore;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ApkFile {
    static DexBackedDexFile inputStreamToDexFile(InputStream inputStream) throws IOException {
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

    public static void zipGraphDir(File inZipFile, File outZipFile) throws IOException {

        ZipFile inZip = new ZipFile(inZipFile);
        ZipOutputStream zipOutStream = new ZipOutputStream(new FileOutputStream(outZipFile));

        byte[] buffer = new byte[1024];

        Enumeration<? extends ZipEntry> entries = inZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            switch (entry.getName()) {
                case "META-INF/TILKI.SF":
                case "META-INF/TILKI.RSA":
                case "META-INF/MANIFEST.MF":
                    System.out.println("delete:" + entry.getName());
                    continue;
            }
            InputStream inputStream = new BufferedInputStream(inZip.getInputStream(entry));

            ZipEntry zipEntry = new ZipEntry(entry.getName());
            zipEntry.setMethod(entry.getMethod());
            if (ZipEntry.STORED == entry.getMethod()) {
                zipEntry.setSize(entry.getSize());
                zipEntry.setCrc(entry.getCrc());
                System.out.println("STORED:" + entry.getName());
            }

            zipOutStream.putNextEntry(zipEntry);

            if (entry.getName().matches("classes(|[\\d]+).dex")) {
                System.out.println("edit:" + entry.getName());
                DexBackedDexFile dexFile = inputStreamToDexFile(inputStream);

                DexPool dexPool = new DexPool(dexFile.getOpcodes());
                for (ClassDef classDef : dexFile.getClasses()) {
                    dexPool.internClass(classDef);
                }
                MemoryDataStore store = new MemoryDataStore();
                dexPool.writeTo(store);
                zipOutStream.write(store.getBuffer());
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
    }

}
