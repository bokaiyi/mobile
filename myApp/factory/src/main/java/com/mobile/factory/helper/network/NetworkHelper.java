package com.mobile.factory.helper.network;

<<<<<<< HEAD
import com.mobile.util.Config;
=======
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.mobile.util.Config;
import com.mobile.factory.model.db.entity.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
>>>>>>> 16b0d4c (fix bugs-Final version)

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {

<<<<<<< HEAD
    public static Retrofit getRetrofit(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        // 设置请求地址并返回
=======
    public static Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        // Set the request address and return
>>>>>>> 16b0d4c (fix bugs-Final version)
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder.baseUrl(Config.NETWORK_URL).client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).build();
    }
<<<<<<< HEAD
=======

    /**
     * Encapsulated method of setting portrait
     *
     * @param view the view need to bind
     * @param user current user
     * @param context current context
     */
    public static void setPortrait(ImageView view, User user, Context context) {
        String path = context.getExternalCacheDir().getAbsolutePath() + "/" + user.getId() + ".png";
        File file = new File(path);
        if (file.exists()) {
            view.setImageURI(Uri.fromFile(file));
        } else {
            try {
                InputStream inputStream = UploadHelper.getInputStream(user.getPortrait());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                view.setImageBitmap(bitmap);

                boolean newFile = file.createNewFile();
                if (newFile) {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    Log.e("localpath", path);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
        }
    }

    /**
     * download audio to local
     */
    public static void getAudio(File file, String url){
        try {
            InputStream inputStream = UploadHelper.getInputStream(url);
            FileOutputStream out = new FileOutputStream(file);
            byte[] bb = new byte[1024]; // receive buffer
            int len;
            while ((len = inputStream.read(bb)) > 0) { // receive
                out.write(bb, 0, len); // Write file
            }
            out.flush();
            out.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e("Exception", e.toString());
        }
    }
>>>>>>> 16b0d4c (fix bugs-Final version)
}
