package com.usda.fmsc.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private static final int ZIP_BUFFER_SIZE = 2048;

    public static boolean delete(String path) {
        return delete(new File(path));
    }

    public static boolean delete(File file) {
        if (file.exists() && file.isFile())
            return file.delete();

        return deleteDirectory(file);
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

    public static boolean fileOrFolderExists(String path) {
        return !StringEx.isEmpty(path) && new File(path).exists();
    }


    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public static String getFileNameWoExt(String path) {
        String filename = getFileName(path);
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    public static String getFilePathWoExt(String path) {
        return path.substring(0, path.lastIndexOf('.'));
    }


    public static boolean copyFile(File sourceFile, File destFile) {
        FileChannel source = null;
        FileChannel destination = null;

        try {
            if(!destFile.exists()) {
                destFile.createNewFile();
            }

            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            } catch (IOException e) {
                //
            }
        }

        return true;
    }


    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] targetArray = new byte[inputStream.available()];
        inputStream.read(targetArray);
        return targetArray;
    }



    public static void zipFiles(File destZip, File... files) throws IOException {
        if (destZip == null)
            throw new NullPointerException("destZip");

        FileOutputStream dest = new FileOutputStream(destZip);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

        int relativeStartingPathIndex = destZip.getAbsolutePath().lastIndexOf("/") + 1;

        for (File file : files) {
            if(file.isDirectory()) {
                zipSubDir(out, file, relativeStartingPathIndex);
            } else {
                try (BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file))) {
                    zipEntryFile(origin, out, file, relativeStartingPathIndex);
                }
            }
        }

        out.close();
    }

    public static void zipToFile(File source, File destZip) throws IOException {
        if (source == null)
            throw new NullPointerException("source");

        if (destZip == null)
            throw new NullPointerException("destZip");

        int relativeStartingPathIndex = destZip.getAbsolutePath().lastIndexOf("/") + 1;

        try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destZip)))) {
            if (source.isDirectory()) {
                zipSubDir(out, source, relativeStartingPathIndex);
            } else {
                try (BufferedInputStream origin = new BufferedInputStream(new FileInputStream(source))) {
                    zipEntryFile(origin, out, source, relativeStartingPathIndex);
                }
            }
        }
    }

    private static void zipSubDir(ZipOutputStream out, File dir, int relativeStartingPathIndex) throws IOException {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if(file.isDirectory()) {
                    zipSubDir(out, file, relativeStartingPathIndex);
                } else {
                    try (BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file))) {
                        zipEntryFile(origin, out, file, relativeStartingPathIndex);
                    }
                }
            }
        }
    }

    private static void zipEntryFile(BufferedInputStream origin, ZipOutputStream out, File file, int relativeStartingPathIndex) throws IOException {
        String relativePath = file.getAbsolutePath().substring(relativeStartingPathIndex);
        ZipEntry entry = new ZipEntry(relativePath);
        entry.setTime(file.lastModified()); // to keep modification time after unzipping
        out.putNextEntry(entry);
        byte[] data = new byte[ZIP_BUFFER_SIZE];
        int count;
        while ((count = origin.read(data, 0, ZIP_BUFFER_SIZE)) != -1) {
            out.write(data, 0, count);
        }
        out.closeEntry();
    }


    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)))) {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                }
                long time = ze.getTime();
                if (time > 0)
                    file.setLastModified(time);
            }
        }
    }
}
