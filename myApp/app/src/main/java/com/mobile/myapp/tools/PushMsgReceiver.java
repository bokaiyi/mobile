package com.mobile.myApp.tools;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTTransmitMessage;
import com.mobile.factory.BaseFactory;
import com.mobile.factory.helper.account.AccountHelper;
import com.mobile.factory.StaticData.AccountData;

public class PushMsgReceiver extends GTIntentService {
    private static final String TAG = PushMsgReceiver.class.getSimpleName();

    /**
     * Method to get clientId
     * 
     * @param context
     * @param s
     */
    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + s);
        onClientInit(s);
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.e(TAG, "onReceiveServicePid -> " + "pid = " + pid);
    }

    // handle message TODO: need to make sure
    // https://docs.getui.com/getui/mobile/android/androidstudio/
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // // 透传消息的处理，详看 SDK demo
        // byte[] payload = msg.getPayload();
        // if (payload != null) {
        // String message = new String(payload);
        // Log.e(TAG, "GET_MSG_DATA:" + message);
        // // [{"type":0,"content":"Hello","createAt":"2021-10-12T16:40:34.569Z"}]
        // }
    }

    /**
     * initialize Id
     *
     * @param cid device id
     */
    private void onClientInit(String cid) {
        AccountData.setPushId(cid);
        if (AccountData.isLogin()) {
            // bind device
            AccountHelper.bindPushId(null);
        }
    }

}
