package com.mobile.myApp.activities;

import static android.Manifest.permission.ACCESS_WIFI_STATE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mobile.factory.StaticData.AccountData;
import com.mobile.myApp.R;
import com.mobile.util.app.Activity;

/**
 * app starts activity
 */
public class OpenAppActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.open_app;
    }

    // TODO: Test the Network Permission on phones, works well on virtual devices
    @Override
    protected void onResume() {
        super.onResume();

        com.igexin.sdk.PushManager.getInstance().initialize(this);

        if ((ContextCompat.checkSelfPermission(OpenAppActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this,
                        Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this,
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this,
                        Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this,
                        Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_GRANTED)) {
            // If already log in, enter the home page directly
            if (AccountData.isLogin()) {
                while (!AccountData.isBind()) {
                    Log.e("receiving push id", "!!!!");
                }
                Log.e("received push id", AccountData.getPushId());
                MainActivity.show(this);
            } else {
                AccountActivity.show(this);
            }
            finish();
        } else {
            // ask for permission
            ActivityCompat.requestPermissions(OpenAppActivity.this,
                    new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.RECEIVE_BOOT_COMPLETED,
                            ACCESS_WIFI_STATE },
                    1);
        }
    }

}
