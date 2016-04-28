package com.usda.fmsc.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private static final int BUFFER = 1024;

    public static boolean delete(String path) {
        File file = new File(path);

        if (file.exists() && file.isFile())
            return file.delete();

        return deleteDirectory(new File(path));
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteDirectory(f);
                    }
                    else {
                        f.delete();
                    }
                }
            }
        }

        return directory.delete();
    }

    public static boolean fileExists(String filePath) {
        if (StringEx.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static boolean folderExists(String folderPath) {
        if (StringEx.isEmpty(folderPath))
            return false;
        File file = new File(folderPath);
        return file.exists() && file.isDirectory();
    }

    public static boolean fileOrFolderExists(String path) {
        return !StringEx.isEmpty(path) && new File(path).exists();
    }

    public static boolean zipFiles(String outputFile, String... files) {
        try  {
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(outputFile);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for(String file : files) {
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                out.putNextEntry(entry);

                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
