package com.mobile.myApp.fragments.account;

import static com.mobile.myApp.tools.ImgSelector.ALBUM_REQUEST_CODE;
import static com.mobile.myApp.tools.ImgSelector.CAMERA_REQUEST_CODE;
import static com.mobile.myApp.tools.ImgSelector.TAILOR_REQUEST_CODE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mobile.factory.present.user.UpdateInfoPresent;
import com.mobile.factory.present.user.UpdateInfoPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.MainActivity;
import com.mobile.myApp.tools.ImgSelector;
import com.mobile.util.app.Fragment;
import com.mobile.factory.helper.network.UploadHelper;
import com.mobile.factory.utils.FileUtils;
import com.mobile.util.widget.PortraitView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.ImageView;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * update user information fragment
 */
public class UpdateInfoFragment extends Fragment implements UpdateInfoPresent.View {

    private final ImgSelector imgSelector; // bind a image selector
    private UpdateInfoPresent.Presenter updateInfoPresent;
    private String localPortraitPath; // local path for avatar
    private boolean isMale = true; // sex setting, male as default
    private String portraitKey; // key for server
    private String portraitURL; // download avatar url

    // bind lint
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    Loading loading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button submit_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_sex)
    ImageView sex;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_portrait)
    PortraitView portraitView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_desc)
    EditText description;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.male)
    RadioButton male;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.female)
    RadioButton female;

    public UpdateInfoFragment(ImgSelector imgSelector) {
        this.imgSelector = imgSelector;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    // presenter initialize，return instance
    protected UpdateInfoPresent.Presenter initialPresenter() {

        return new UpdateInfoPresentImpl(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        updateInfoPresent = initialPresenter();
    }

    @Override
    public void setPresenter(UpdateInfoPresent.Presenter presenter) {
        this.updateInfoPresent = presenter;
    }

    /**
     * Image selector
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        imgSelector.onImgRequestClick(portraitView);
    }

    /**
     * event for gender select
     *
     * @param view
     * @param ischanged
     **/
    @SuppressLint("UseCompatLoadingForDrawables")
    @OnCheckedChanged({ R.id.male, R.id.female })
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
        case R.id.male:
            if (ischanged) {
                isMale = true;
                sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_man));
            }
            break;
        case R.id.female:
            if (ischanged) {
                isMale = false;
                sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_womam));
            }
            break;
        default:
            break;
        }
    }

    /**
     * submit changes
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = description.getText().toString();
        // upload avatar
        try {
            portraitURL = UploadHelper.uploadImage(localPortraitPath, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // store url
        updateInfoPresent.requestUpdate(portraitURL, desc, isMale);
    }

    @Override
    public void UpdateInfoSuccess() {
        Toast.makeText(getContext(), "Update succeed!", Toast.LENGTH_SHORT).show();

        // transfer to main and end current activity
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void UpdateInfoFail(int error) {
        if (placeHolderView != null) {
            placeHolderView.triggerError(error);
        } else {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }

        // stop loading and enable input
        loading.stop();
        sex.setEnabled(true);
        portraitView.setEnabled(true);
        description.setEnabled(true);
        submit_button.setEnabled(true);
    }

    @Override
    public void loading() {
        if (placeHolderView != null) {
            placeHolderView.triggerLoading();
        } else {
            Toast.makeText(getContext(), "loading", Toast.LENGTH_SHORT).show();
        }

        // start loading and disable input
        loading.start();
        sex.setEnabled(false);
        portraitView.setEnabled(false);
        description.setEnabled(false);
        submit_button.setEnabled(false);
    }

    /**
     * all logic after return here
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ImgSelector.TAG, String.valueOf(resultCode));
        // if the activity is to handle image
        if (resultCode == -1 && (requestCode == CAMERA_REQUEST_CODE || requestCode == ALBUM_REQUEST_CODE
                || requestCode == TAILOR_REQUEST_CODE)) {
            // when success
            if (requestCode == CAMERA_REQUEST_CODE) {
                Log.e(ImgSelector.TAG, "相机回调");
                String path = FileUtils.uriToFile(imgSelector.getImageUri(), getContext()).getAbsolutePath();
                // set avatar and local path
                portraitView.setImageURI(imgSelector.getImageUri());
                localPortraitPath = path;
                Log.e(ImgSelector.TAG, localPortraitPath);

                // if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                // {
                // //image clip
                // imgSelector.openCrop(imgSelector.getImageUri());
                // } else {
                // Toast.makeText(getContext(), "No SDCard", Toast.LENGTH_SHORT).show();
                // }
            } else if (requestCode == ALBUM_REQUEST_CODE) {
                if (data != null && data.getData() != null) {
                    Log.e(ImgSelector.TAG, "相册回调成功");
                    String path = FileUtils.uriToFile(data.getData(), getContext()).getAbsolutePath();
                    // set up avatar and local path
                    portraitView.setImageURI(data.getData());
                    localPortraitPath = path;
                    Log.e(ImgSelector.TAG, localPortraitPath);
                }
            } else {
                Log.e(ImgSelector.TAG, "图片剪裁回调");
                Uri uri = Uri.fromFile(imgSelector.getImgFile());
                portraitView.setImageURI(uri); // set up avatar
            }
        } else {
            // TODO: todo here
            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    }

}
