package com.mobile.myapp.fragments.account;

import static com.mobile.myapp.tools.ImgSelector.ALBUM_REQUEST_CODE;
import static com.mobile.myapp.tools.ImgSelector.CAMERA_REQUEST_CODE;
import static com.mobile.myapp.tools.ImgSelector.TAILOR_REQUEST_CODE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mobile.myapp.R;
import com.mobile.myapp.tools.ImgSelector;
import com.mobile.util.app.Fragment;
import com.mobile.util.widget.PortraitView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更新用户信息所显示的fragment
 */
public class UpdateInfoFragment extends Fragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    private final ImgSelector imgSelector;

    public UpdateInfoFragment(ImgSelector imgSelector) {
        this.imgSelector = imgSelector;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    /**
     * 调起图片选择器
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        imgSelector.onImgRequestClick(mPortrait);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ImgSelector.TAG, String.valueOf(resultCode));
        // 如果是处理图片的回调
        if (resultCode == -1 && (requestCode == CAMERA_REQUEST_CODE ||
                requestCode == ALBUM_REQUEST_CODE || requestCode == TAILOR_REQUEST_CODE)) {
            //回调成功
            if (requestCode == CAMERA_REQUEST_CODE) {
                Log.e(ImgSelector.TAG, "相机回调");
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //照片裁剪
                    imgSelector.openCrop(imgSelector.getImageUri());
                } else {
                    Toast.makeText(getContext(), "No SDCard", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == ALBUM_REQUEST_CODE) {
                Log.e(ImgSelector.TAG, "相册回调");
                if (data != null && data.getData() != null) {
                    Log.e(ImgSelector.TAG, "相册回调成功");
                    imgSelector.openCrop(data.getData()); // 剪裁
                }
            } else {
                Log.e(ImgSelector.TAG, "图片剪裁回调");
                Uri uri = Uri.fromFile(imgSelector.getImgFile());
                mPortrait.setImageURI(uri); // 设置头像

                // 上传到服务器
                File file = imgSelector.getImgFile();

            }
        } else {
            Toast.makeText(getContext(), "Something wrong, please check log", Toast.LENGTH_SHORT).show();
        }
    }
}
