package com.mobile.factory.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Assistant class，can add file reading and writing methods here
 */
public class FileUtils {
    /**
     * Uri to File
     * alredy adapt to Android10
     * @param uri uri
     * @param context
     * @return file
     */
    public static File uriToFile(Uri uri, Context context){
        File file = null;
        if (uri == null){
            return null;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            //Below Android10
            try {
                @SuppressLint("Recycle")
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    cursor.moveToFirst();
                    String name = cursor.getString(index);
                    file = new File(context.getFilesDir(), name);
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    FileOutputStream outputStream = new FileOutputStream(file);
                    int read;
                    int maxBufferSize = 1024 * 1024;
                    if (inputStream != null) {
                        int bytesAvailable = inputStream.available();
                        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        final byte[] buffers = new byte[bufferSize];
                        while ((read = inputStream.read(buffers)) != -1){
                            outputStream.write(buffers, 0, read);
                        }
                        cursor.close();
                        inputStream.close();
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Higher than Android10
            if (uri.getScheme() != null){
                if (uri.getScheme().equals(ContentResolver.SCHEME_FILE) && uri.getPath() != null){
                    file = new File(uri.getPath());
                } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)){
                    //Copy files to the sandbox directory
                    ContentResolver contentResolver = context.getContentResolver();

                    String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                            + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

                    try {
                        InputStream inputStream = contentResolver.openInputStream(uri);
                        if (context.getExternalCacheDir() != null){
                            File cache = new File(context.getExternalCacheDir().getAbsolutePath(), displayName);
                            FileOutputStream fileOutputStream = new FileOutputStream(cache);
                            if (inputStream != null) {
                                android.os.FileUtils.copy(inputStream, fileOutputStream);
                                file = cache;
                                fileOutputStream.close();
                                inputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file;
    }
}
