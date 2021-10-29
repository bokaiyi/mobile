package com.mobile.factory.helper.network;

import android.content.Context;
<<<<<<< HEAD
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import com.mobile.util.Config;
import com.mobile.util.utils.Encryptor;

import org.jasypt.util.text.BasicTextEncryptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 上传文件到oss，部署在香港了
 * 加密方法：ENCRYPTOR.getEncryptor().encrypt(str);
 * 解密方法: ENCRYPTOR.getEncryptor().decrypt(str);
=======
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import com.mobile.factory.utils.Encryptor;

import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Upload files to oss and deploy in Hong Kong
 * Encryption method：ENCRYPTOR.getEncryptor().encrypt(str);
 * Decryption method: ENCRYPTOR.getEncryptor().decrypt(str);
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class UploadHelper {
    private static final String TAG = "UploadHelper";
    private static final String ENDPOINT = "http://oss-cn-hongkong.aliyuncs.com";
    private static final String BUCKET_NAME = "mobile-oss1";
    public static final Encryptor ENCRYPTOR = new Encryptor("123456");


    /**
<<<<<<< HEAD
     * !! 不要改密钥
=======
     * !! Don't change the key
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param context
     * @return
     */
    private static OSS getClient(Context context) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                "LTAI5tDYzdfSKyDrZP5brm6y",
                "M6CSJJ7WLN6vvc3pHC2CKpbmJYdhFi");
        return new OSSClient(context, ENDPOINT, credentialProvider);
    }

    /**
<<<<<<< HEAD
     * 上传后返回一个存储的key
     *
     * @param objKey 上传上去后，在服务器上的独立的KEY
     * @param path   需要上传的文件的路径
     * @return 存储的key
     */
    private static String upload(String objKey, String path, Context context) {
        // 构造一个上传请求
=======
     * Return a stored key after uploading
     *
     * @param objKey After uploading, the independent KEY on the server
     * @param path   The path of the file to be uploaded
     * @return Stored key
     */
    private static String upload(String objKey, String path, Context context) {
        // Construct an upload request
>>>>>>> 16b0d4c (fix bugs-Final version)
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
                objKey, path);

        try {
            OSS client = getClient(context);
            PutObjectResult result = client.putObject(request);
<<<<<<< HEAD
            client.presignPublicObjectURL(BUCKET_NAME, objKey);
            // 格式打印输出
            Log.d(TAG, String.format("ObjectKEY:%s", objKey));
            return objKey;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果有异常则返回空
=======
            String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
            // Format printout
            Log.d(TAG, String.format("url:%s", url));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            // If there is an exception, return empty
>>>>>>> 16b0d4c (fix bugs-Final version)
            return null;
        }
    }

    /**
<<<<<<< HEAD
     * 从服务器上异步下载头像，成功后存放
     * @param objKey
     * @param path
     * @param context
     */
    public static void downloadFile(String objKey, Context context, String path) throws ClientException, ServiceException {
        GetObjectRequest get = new GetObjectRequest(BUCKET_NAME, objKey);
        GetObjectResult result = getClient(context).getObject(get);
        long length = result.getContentLength();
        byte[] buffer = new byte[(int) length];
        int readCount = 0;
        while (readCount < length) {
            try{
                readCount += result.getObjectContent().read(buffer, readCount, (int) length - readCount);
            }catch (Exception e){
                Log.e("file download error", e.toString());
            }
        }
        try {
            FileOutputStream fout = new FileOutputStream(path);
            fout.write(buffer);
            fout.close();
        } catch (Exception e) {
            Log.e("file download error", e.toString());
        }
=======
     * Get files from the network and return them as streams
     *
     * @return
     */
    public static InputStream getInputStream(String URL_PATH) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(URL_PATH);                    //server address
        //Open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(3000);//Set the network connection timeout time to 3 seconds
        httpURLConnection.setRequestMethod("GET");        //Set the request method to GET
        httpURLConnection.setDoInput(true);                //Open input stream
        int responseCode = httpURLConnection.getResponseCode();    // Get server response value
        if (responseCode == HttpURLConnection.HTTP_OK) {        //Normal connection
            inputStream = httpURLConnection.getInputStream();        //Get input stream
        }
        return inputStream;
>>>>>>> 16b0d4c (fix bugs-Final version)
    }


    /**
<<<<<<< HEAD
     * 上传音频
=======
     * upload image
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String uploadImage(String path, Context context) throws Exception {
        String key = getImgObjKey(path);
        return upload(key, path, context);
    }

    /**
<<<<<<< HEAD
     * 上传头像
=======
     * upload portrait
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String uploadPortrait(String path, Context context) throws Exception {
        String key = getPortraitObjKey(path);
        return upload(key, path, context);
    }

    /**
<<<<<<< HEAD
     * 上传音频
=======
     * upload audio
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String uploadAudio(String path, Context context) throws Exception {
        String key = getAudioObjKey(path);
        return upload(key, path, context);
    }

    private static String getImgObjKey(String path) throws Exception {
        String[] key = getKey(path);
        return String.format("image/%s/%s.png", key[0], key[1]);
    }

    private static String getPortraitObjKey(String path) throws Exception {
        String[] key = getKey(path);
        return String.format("portrait/%s/%s.png", key[0], key[1]);
    }

    private static String getAudioObjKey(String path) throws Exception {
        String[] key = getKey(path);
<<<<<<< HEAD
        return String.format("audio/%s/%s.mp3", key[0], key[1]);
=======
        return String.format("audio/%s/%s.m4a", key[0], key[1]);
>>>>>>> 16b0d4c (fix bugs-Final version)
    }

    public static String[] getKey(String path) {
        BasicTextEncryptor textEncryptor = ENCRYPTOR.getEncryptor();
        String fileMd5 = textEncryptor.encrypt(path);
        return new String[]{DateFormat.format("yyyyMM", new Date()).toString(), fileMd5};
    }


}
