package com.mobile.myApp.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.mobile.myApp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * image selector, get corresponding activity and context cutting, select and
 * camera TODO：simulator cannot get camera，cannot save after modifying image.
 * Test using mobile phone later, keep the logic. only select one image a time
 * override onResult in activity fragment creates new one in activity，or there
 * will be null pointer ！！！Details in UserInfoActivity.
 */
public class ImgSelector {
    private Context context;
    private Activity activity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_portrait)
    CircleImageView portraitView;

    public final static int PHOTO_REQUEST_CAMERA = 10;// request camera permission
    public final static int PHOTO_REQUEST_ALBUM = 20;// request album permission
    public final static int CAMERA_REQUEST_CODE = 100;// transfer to camera code
    public final static int ALBUM_REQUEST_CODE = 200;// transfer to album code
    public final static int TAILOR_REQUEST_CODE = 300;// image cutting code
    private Uri imageUri;// The Uri of uploading image
    private File file;// The file of the uploading image
    public static String TAG = "ImgSelector";
    public static boolean isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    private final static String SAVE_AVATAR_NAME = "testImgSelector.png";// The file name of the uploading image

    /**
     * input the corresponnding activity and context
     * 
     * @param context
     * @param activity
     */
    public ImgSelector(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public File getImgFile() {
        return file;
    }

    /**
     * pop up the windows of selecting camera or album
     *
     * @param view a button corresponded onclick
     */
    public void onImgRequestClick(View view) {
        List<String> names = new ArrayList<>();
        names.add("Take Picture");
        names.add("Select from Album");
        showDialog(new SelectDialog.SelectDialogListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                case 0: // get camera directly
                    if ((ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(context,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        // transfer to camera when having all permissions
                        openCamera();
                    } else {
                        if (activity != null) {
                            // request permissions
                            ActivityCompat.requestPermissions(activity,
                                    new String[] { Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE },
                                    PHOTO_REQUEST_CAMERA);
                        }
                    }
                    break;
                case 1:
                    // album
                    if ((ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        // open album when having all permissions
                        openAlbum();
                    } else {
                        if (activity != null) {
                            // request permissions
                            ActivityCompat.requestPermissions(activity,
                                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE },
                                    PHOTO_REQUEST_ALBUM);
                        }
                    }
                    break;
                default:
                    break;
                }

            }
        }, names);
    }

    /**
     * show pop up window，select camera or album
     */
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(activity, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.activity.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * open camera
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("QueryPermissionsNeeded")
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // determine have camera or not
        if (activity != null && context != null && intent.resolveActivity(activity.getPackageManager()) != null) {
            File file;
            Uri uri = null;
            if (isAndroidQ) {
                // adpat to Android10
                // uri = createImageUri(context);
                uri = getOutputMediaFileUri(context);
            } else {
                // below Android10
                file = createImageFile(context);
                if (file != null) {
                    // below Android10
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // adapt Android7.0 file authentication
                        uri = FileProvider.getUriForFile(context, "com.mobile.myApp.fileprovider", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                }
            }
            imageUri = uri;
            Log.e(TAG, "The saving image Uri：" + imageUri);
            if (uri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        } else {
            Log.e(TAG, "No camera");
        }
    }

    /**
     * transfer to album
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    /**
     * transfer to cutting
     */
    public void openCrop(Uri uri) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && context != null) {
            file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/0"), SAVE_AVATAR_NAME);
            Log.e(TAG, "The saving path of the cutted image: " + file.getAbsolutePath());
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // set crop
        intent.putExtra("crop", "true");
        // aspectX aspectY width and height
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // the image size after cropping
        intent.putExtra("outputX", 520);
        intent.putExtra("outputY", 520);
        // 适配Android10，saving path
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        // image format
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        activity.startActivityForResult(intent, TAILOR_REQUEST_CODE);
    }

    /**
     * under Android10 version, create image file to save the photos
     *
     * @return file
     */
    private File createImageFile(@NonNull Context context) {
        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file != null && !file.exists()) {
            if (file.mkdir()) {
                Log.e(TAG, "Folder created successfully");
            } else {
                Log.e(TAG, "File is empty or folder created failed");
            }
        }
        File tempFile = new File(file, SAVE_AVATAR_NAME);
        Log.e(TAG, "Temp file path：" + tempFile.getAbsolutePath());
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    /**
     * Android10 create image file to save the photos
     * 
     * @param context
     * @return
     */
    private Uri getOutputMediaFileUri(Context context) {
        File mediaFile = null;
        String cameraPath;
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            Log.e("...", mediaStorageDir.getPath());
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "Pictures/temp.jpg");// Attention: shold
                                                                                                   // be the same as the
                                                                                                   // filepaths.xml
            cameraPath = mediaFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24 android7.0以上
            assert mediaFile != null;
            return FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".fileprovider", // same as the values in
                                                                                        // android:authorities
                    mediaFile);

        } else {
            return Uri.fromFile(mediaFile);// or Uri.isPaise("file://"+file.toString()

        }
    }

}
