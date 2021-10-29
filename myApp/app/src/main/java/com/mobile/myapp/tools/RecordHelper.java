package com.mobile.myApp.tools;

import static com.mobile.myApp.fragments.message.ChatWithPersonFragment.JSON;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.helper.network.UploadHelper;
import com.mobile.factory.model.db.entity.Message;
import com.mobile.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Record helper
 */
public class RecordHelper {
    private String receiverId;
    private MediaRecorder mMediaRecorder;
    private File mAudioFile;
    private long mStartRecordTime;
    private long mEndRecordTime;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Context context;
    private String audioPath;  // local audio save path
    private String msgId;  // message id
    private String audioURL;  // audio url

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public RecordHelper(String receiverId, Context context) {
        this.receiverId = receiverId;
        this.context = context;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    /**
     * start recording
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startRecording() {
        releaseRecord();//release recordings
        if (!doStartRecord()) {//The real function to start recording,
            // it returns true if it starts recording successfully, otherwise it returns false
            recordFail("Recording failed"); //Failed to start, prompt the user to start recording failed
        }
    }

    private void releaseRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean doStartRecord() {
        Toast.makeText(context, "Start recording", Toast.LENGTH_SHORT).show();
        try {
            mMediaRecorder = new MediaRecorder();

            // change path and upload，generate a messageId
            msgId = UUID.randomUUID().toString();
            audioPath = context.getExternalCacheDir().getAbsolutePath() + "/" + msgId + ".m4a";
            Log.e("audioPath", audioPath);
            mAudioFile = new File(audioPath);
            mAudioFile.getParentFile().mkdirs();
            mAudioFile.createNewFile();

            //Set to collect sound from the microphone
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //Save the file in mp4 format
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            //Set the sampling frequency supported by all android systems
            mMediaRecorder.setAudioSamplingRate(44100);

            //Set the acc encoding
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            //Set better sound quality
            mMediaRecorder.setAudioEncodingBitRate(96000);

            mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());

            mMediaRecorder.prepare();
            mMediaRecorder.start();

            mStartRecordTime = System.currentTimeMillis();

        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void recordFail(String msg) {
        mAudioFile = null;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }, 100);
    }


    /**
     * TODO stop recording
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean stopRecording() {
        //Realize the real logic of stopping recording,
        // return true if successful, otherwise return false
        return doStopRecord();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean doStopRecord() {
        mMediaRecorder.setOnErrorListener(null);
        mMediaRecorder.setOnInfoListener(null);
        mMediaRecorder.setPreviewDisplay(null);

        try {
            mMediaRecorder.stop();
            mEndRecordTime = System.currentTimeMillis();

            final int second = (int) ((mEndRecordTime - mStartRecordTime) / 1000);

            if (second < 2) {
                recordFail("Time is too short");
                return false;
            } else {
                mHandler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        Toast.makeText(context, "Successful recording", Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    // Upload to the server and save this address to the database
                    audioURL = UploadHelper.uploadAudio(audioPath, context);
                    saveMsg(audioURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("audioURL", audioURL);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            recordFail("Record Fail!");
            return false;
        }

        return true;
    }

    /**
     * Save the message to the database
     */
    private void saveMsg(String audioURL) {
        // save data
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receiverId", receiverId);
            jsonObject.put("message", audioURL);
            jsonObject.put("msgId", msgId);
            jsonObject.put("type", String.valueOf(Message.TYPE_AUDIO));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        try {
            OkHttpClient client = new OkHttpClient();
            Request request2 = new Request.Builder()
                    // Specify the server address for access
                    .url(Config.NETWORK_URL + "message/send")
                    .post(requestBody)
                    .addHeader("token", AccountData.getToken())
                    .build();

            Response response = client.newCall(request2).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                JSONObject jsonObject2 = new JSONObject(responseData);
                int code = jsonObject2.getInt("code");
                if (code != 1) {
                    Toast.makeText(context, "Save error!!! error " + code,
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(context, "Database response error!!! error " + response.code(),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("!!!!", e.getMessage());
            Toast.makeText(context, "Save failed!", Toast.LENGTH_SHORT).show();
        }
    }

}
