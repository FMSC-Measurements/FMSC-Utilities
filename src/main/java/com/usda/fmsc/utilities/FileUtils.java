package com.usda.fmsc.utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

import androidx.documentfile.provider.DocumentFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private static final int BUFFER = 1024;

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


    public static boolean fileExists(Context context, Uri filePath) {
        if (filePath == null)
            return false;

        try {
            context.getContentResolver().openInputStream(filePath).available();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




    public static boolean fileExists(Context context, String filePath) {
        if (StringEx.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static boolean fileExistsOld(String filePath) {
        if (StringEx.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }


    public static boolean folderExists(Context context, Uri uri) {
        return DocumentFile.fromTreeUri(context, uri).exists();
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


    public static boolean fileOrFolderExistsInTree(Context context, Uri tree, String path) {
        DocumentFile childDoc = getDocumentFromTree(context, tree, path);

        return childDoc != null && childDoc.exists();
    }

    public static DocumentFile getDocumentFromTree(Context context, Uri tree, String path) {
        String id = DocumentsContract.getTreeDocumentId(tree);

        if (!path.startsWith("/"))
            id = id + "/" + path;
        else
            id = id + path;

        Uri childUri = DocumentsContract.buildDocumentUriUsingTree(tree, id);
        return DocumentFile.fromSingleUri(context, childUri);
    }

    public static void zipFiles(File outputFile, File... files) throws IOException {
        BufferedInputStream origin;
        FileOutputStream dest = new FileOutputStream(outputFile);

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

        byte[] data = new byte[BUFFER];

        for(File file : files) {
            FileInputStream fi = new FileInputStream(file);
            origin = new BufferedInputStream(fi, BUFFER);

            ZipEntry entry = new ZipEntry(FileUtils.getFileName(file.getPath()));
            out.putNextEntry(entry);

            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
        }

        out.close();
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

    public static void copyFile(Context context, Uri source, Uri dest) throws IOException {
        ContentResolver resolver = context.getContentResolver();

        InputStream input = resolver.openInputStream(source);
        OutputStream output = resolver.openOutputStream(dest);

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0){
            output.write(buffer, 0, buffer.length);
        }
    }


    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] targetArray = new byte[inputStream.available()];
        inputStream.read(targetArray);
        return targetArray;
    }



    public static boolean zipToFile(Uri source, Uri zipDest) {
        final int BUFFER = 2048;

        if (source == null)
            throw new NullPointerException("source");

        if (source.getPath() == null) {
            throw new NullPointerException("source path");
        }

        File sourceFile = new File(source.getPath());
        try {
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(zipDest.getPath());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            if (sourceFile.isDirectory()) {
                zipSubFolder(out, sourceFile, sourceFile.getParent().length());
            } else {
                byte[] data = new byte[BUFFER];
                FileInputStream fi = new FileInputStream(source.getPath());
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(getLastPathComponent(source.getPath()));
                entry.setTime(sourceFile.lastModified()); // to keep modification time after unzipping
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void zipSubFolder(ZipOutputStream out, File folder, int basePathLength) throws IOException {
        final int BUFFER = 2048;

        File[] fileList = folder.listFiles();
        BufferedInputStream origin;

        if (fileList == null)
            throw new NullPointerException("fileList");

        for (File file : fileList) {
            if (file.isDirectory()) {
                zipSubFolder(out, file, basePathLength);
            } else {
                byte[] data = new byte[BUFFER];
                String unmodifiedFilePath = file.getPath();
                String relativePath = unmodifiedFilePath
                        .substring(basePathLength);
                FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(relativePath);
                entry.setTime(file.lastModified()); // to keep modification time after unzipping
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
        }
    }

    private static String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        return segments[segments.length - 1];
    }
}
