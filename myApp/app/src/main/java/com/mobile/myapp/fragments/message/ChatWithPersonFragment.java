package com.mobile.myApp.fragments.message;

import static com.mobile.util.Config.HOST;
import static com.mobile.util.Config.PORT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.model.db.identity.MessageIdentity;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.UserInfoActivity;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.myApp.tools.MediaManager;
import com.mobile.myApp.tools.RecordHelper;
import com.mobile.util.Config;
import com.mobile.util.app.Fragment;
import com.mobile.factory.model.db.entity.User;
import com.mobile.util.widget.PortraitView;
import com.raizlabs.android.dbflow.StringUtils;

import net.qiujuer.genius.ui.widget.Loading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * one to one chat, does not split baseFragment
 * every messageIdentity bind to one address if the message is voice
 */
public class ChatWithPersonFragment extends Fragment {
    private final User receiver;  // person that you are chatting with
    private MsgAdapter adapter;
    private final List<MessageIdentity> mMsgList = new ArrayList<>();  // notification list

    private Uri my_uri;  // my avatar
    private Uri receiver_uri;  // target avatar

    // JSON format，used in network encapsulation
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    // chats variables
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";

    // store id for read messages
    
    private final List<String> readMsgList = new ArrayList<>();

    private RecordHelper recordHelper = null;  // record helper class


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_portrait)
    PortraitView portraitView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout my_bar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_content)
    EditText editText;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.exit)
    PortraitView exitView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.saving)
    Loading saving;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_record)
    ImageView recordView;


    /**
     * constructor
     *
     * @param user
     */
    public ChatWithPersonFragment(User user) {
        receiver = user;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        recordHelper = new RecordHelper(receiver.getId(), getContext());
    }

    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            String message_senderId = message.getData().getString("senderId");
            String message_receiverId = message.getData().getString("receiverId");
            String message_content = message.getData().getString("content");
            String type = message.getData().getString("type");
            String msgId = message.getData().getString("msgId");
            if (!StringUtils.isNullOrEmpty(message_content)) {
                try {
                    addNewMessage(msgId, message_content, message_senderId, message_receiverId, type);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void addNewMessage(String msgId, String msg, String senderId, String receiverId, String type) throws ParseException {
        MessageIdentity messageIdentity = new MessageIdentity();
        messageIdentity.setId(msgId);
        messageIdentity.setSenderId(senderId);
        messageIdentity.setReceiverId(receiverId);
        messageIdentity.setType(Integer.parseInt(type));
        messageIdentity.setContent(msg);
        mMsgList.add(messageIdentity);
        adapter.notifyItemInserted(mMsgList.size() - 1);
        recyclerView.scrollToPosition(mMsgList.size() - 1);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initialWidget(View view) {
        super.initialWidget(view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(mMsgList);
        recyclerView.setAdapter(adapter);

        // recording 
        // press to trigger handle
        final Handler handler = new Handler();
        final Runnable mLongPressed = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                // press
                recordHelper.startRecording();
            }
        };

        // touch handle
        recordView.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    handler.postDelayed(mLongPressed, 100);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.removeCallbacks(mLongPressed);
                    // release
                    boolean success = recordHelper.stopRecording();
                    // send
                    // if the recording is success
                    if (success) {
                        pushMessage(recordHelper.getMsgId(), socket, out, recordHelper.getAudioURL());
                    }
                }
                return true;
            }
        });
    }


    @Override
    protected void initialData() {
        super.initialData();
        // set up name and avatar
        NetworkHelper.setPortrait(portraitView, receiver, Objects.requireNonNull(getContext()));
        my_bar.setTitle(receiver.getName());

        File my_portrait = new File(
                Objects.requireNonNull(getContext()).getExternalCacheDir().
                        getAbsolutePath() + "/" + AccountData.getUserId() + ".png");
        my_uri = Uri.fromFile(my_portrait);
        File receiver_portrait = new File(
                getContext().getExternalCacheDir().
                        getAbsolutePath() + "/" + receiver.getId() + ".png");
        receiver_uri = Uri.fromFile(receiver_portrait);

        // get the message history
        getHistory();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // run the socket to listen message
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(HOST, PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())), true);

                    while (true) {
                        if (socket.isConnected()) {
                            if (!socket.isInputShutdown()) {
                                if ((content = in.readLine()) != null) {
                                    Message message = new Message();
                                    Bundle bundle = new Bundle();

                                    if (content.startsWith("client:")) {
                                        //  do not handle if someone enter
                                    } else {
                                        // string splitting
                                        String[] split0 = content.split("@");
                                        String msgId = split0[0];
                                        String[] split = split0[1].split("->");
                                        String senderId = split[0];
                                        String[] split1 = split[1].split("#");
                                        String receiverId = split1[0];
                                        String content = split1[1];

                                        // only current conversation accur for veryfication
                                        if ((senderId.equals(AccountData.getUserId()) && receiverId.equals(receiver.getId()))
                                                || (senderId.equals(receiver.getId()) && receiverId.equals(AccountData.getUserId()))) {
                                            // voice handle
                                            if (content.startsWith("http://mobile-oss1.oss-cn-hongkong.aliyuncs.com")) {
                                                bundle.putString("type",
                                                        String.valueOf(com.mobile.factory.model.db.entity.Message.TYPE_AUDIO));
                                                // get local audio
                                                // TODO: download voice
                                                String path = getContext().getExternalCacheDir().getAbsolutePath() + "/" + msgId + ".m4a";
                                                File file = new File(path);
                                                if (!file.exists()) {
                                                    boolean newFile = file.createNewFile();
                                                    if (newFile) {
                                                        NetworkHelper.getAudio(file, content);
                                                    }
                                                }
                                                content = path;
                                                Log.e("current audioPath", content);
                                            } else {
                                                bundle.putString("type",
                                                        String.valueOf(com.mobile.factory.model.db.entity.Message.TYPE_STR));
                                            }
                                            bundle.putString("msgId", msgId);
                                            bundle.putString("content", content);
                                            bundle.putString("senderId", senderId);
                                            bundle.putString("receiverId", receiverId);  //store to Bundle
                                            message.setData(bundle);//mes that uses Bundle for data transition
                                            handler.sendMessage(message);//send message by handler in activity

                                            if (receiverId.equals(AccountData.getUserId())) {
                                                //  refresh the read message
                                                readMsgList.add(msgId);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * enter personal file when avatar clicked
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.im_portrait)
    public void onClickPortrait() {
        UserInfoActivity.show(Objects.requireNonNull(getContext()), receiver);
    }


    /**
     * event when message clicked
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    public void onClickSubmit() {
        if (editText.getText() == null || StringUtils.isNullOrEmpty(editText.getText().toString())) {
            Toast.makeText(getContext(), "Please input a message", Toast.LENGTH_SHORT).show();
        } else {
            String content = editText.getText().toString();
            editText.setText("");
            sendMsg(content);
        }
    }

//    /**
//     * TODO: send picture
//     */
//    @SuppressLint("NonConstantResourceId")
//    @OnClick(R.id.btn_uploadImg)
//    public void onClickUpload() {
//
//    }

    /**
     * exit chat
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.exit)
    public void onClickExit() {
        pushMessage(UUID.randomUUID().toString(), socket, out, "exit server");

        if (readMsgList.size() > 0) {
            saving.start();

            // refresh the read message
            try {
                OkHttpClient client = new OkHttpClient();

                // store the message
                JSONObject jsonObject = new JSONObject();
                StringBuilder list = new StringBuilder();
                for (String s : readMsgList) {
                    list.append(s);
                    list.append(",");
                }
                try {
                    jsonObject.put("msgId", list.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        // request the server from address
                        .url(Config.NETWORK_URL + "message/read").post(requestBody)
                        .addHeader("token", AccountData.getToken())
                        .build();
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Looper.prepare();
                    Toast.makeText(getContext(), "server response error！！！error " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    String responseData = response.body().string();
                    JSONObject jsonObject2 = new JSONObject(responseData);
                    int code = jsonObject2.getInt("code");
                    if (code != 1) {
                        Toast.makeText(getContext(), "save error！！！error " + code,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Looper.prepare();
                Toast.makeText(getContext(), "fail when connect to server！！！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            saving.stop();
        }

        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    /**
     * send meesage, resend if fail
     */
    public void sendMsg(String message) {
        String msgId = UUID.randomUUID().toString();
        // send message
        pushMessage(msgId, socket, out, message);
        // save the database store id
        saveMsg(msgId, message);
    }

    public void pushMessage(String msgId, Socket sock, PrintWriter pw, String m) {
        // excapsulate msgId@sender->receiver#m
        String chat_msg = msgId + "@" + AccountData.getUserId() + "->" + receiver.getId() + "#" + m;
        if (m.equals("exit server")) {
            chat_msg = m;
        }
        final PrintWriter w = pw;
        final Socket s = sock;
        String finalChat_msg = chat_msg;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (s.isConnected()) {
                    if (!s.isOutputShutdown()) {
                        w.println(finalChat_msg);
                    }
                }
            }
        }).start();
    }

    /**
     * store message to database
     */
    public void saveMsg(String msgId, String message) {
        // save the mssage
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("receiverId", receiver.getId());
            jsonObject.put("message", message);
            jsonObject.put("msgId", msgId);
            if (!message.startsWith("http://mobile-oss1.oss-cn-hongkong.aliyuncs.com")) {
                jsonObject.put("type", String.valueOf(com.mobile.factory.model.db.entity.Message.TYPE_STR));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        try {
            OkHttpClient client = new OkHttpClient();
            Request request2 = new Request.Builder()
                    // request to server
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
                    Toast.makeText(getContext(), "save error！！！error " + code,
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "database respond error！！! error " + response.code(),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("!!!!", e.getMessage());
            Toast.makeText(getContext(), "saving error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * get history message
     */
    public void getHistory() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // request
                    .url(Config.NETWORK_URL + "message/" + receiver.getId()).get()
                    .addHeader("token", AccountData.getToken())
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                if (responseData.startsWith("\ufeff")) {
                    responseData = responseData.substring(1);
                }
                parseJSONWithJSONObject(responseData);
            } else {
                Looper.prepare();
                Toast.makeText(getContext(), "respond error！！！error " + response.code(),
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(getContext(), "error connecting to server！！！", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                JSONArray response_result = jsonObject.getJSONArray("response_result");
                for (int curr = 0; curr < response_result.length(); curr++) {
                    JSONObject msg = response_result.getJSONObject(curr);
                    String senderId = msg.getString("senderId");
                    String receiverId = msg.getString("receiverId");
                    String content = msg.getString("content");
                    String msgId = msg.getString("id");
                    Log.e("received!!!", content);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    if (!content.startsWith("http://mobile-oss1.oss-cn-hongkong.aliyuncs.com")) {
                        bundle.putString("type",
                                String.valueOf(com.mobile.factory.model.db.entity.Message.TYPE_STR));
                    } else {
                        // TODO: download voice
                        String path = getContext().getExternalCacheDir().getAbsolutePath() + "/" + msgId + ".m4a";
                        File file = new File(path);
                        if (!file.exists()) {
                            boolean newFile = file.createNewFile();
                            if (newFile) {
                                NetworkHelper.getAudio(file, content);
                            }
                        }
                        content = path;
                        bundle.putString("type",
                                String.valueOf(com.mobile.factory.model.db.entity.Message.TYPE_AUDIO));

                    }
                    bundle.putString("msgId", msgId);
                    bundle.putString("content", content);
                    bundle.putString("senderId", senderId);
                    bundle.putString("receiverId", receiverId);  //往Bundle中存放数据
                    message.setData(bundle);//mes利用Bundle传递数据
                    handler.sendMessage(message);//用activity中的handler发送消息

                }
            } else {
                Looper.prepare();
                Toast.makeText(getContext(), "return error，error " + code, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(getContext(), "extract json error!", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }


    class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
        private final List<MessageIdentity> mMsgList;

        public MsgAdapter(List<MessageIdentity> msgList) {
            mMsgList = msgList;
        }

        @NonNull
        @Override
        public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat, parent, false);
            return new MsgAdapter.ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
            MessageIdentity msg = mMsgList.get(position);

            // left hand side if reciver
            if (AccountData.getUserId().equals(msg.getReceiverId())) {
                holder.rightLayout.setVisibility(View.GONE);
                if (msg.getType() == com.mobile.factory.model.db.entity.Message.TYPE_AUDIO) {
                    holder.left_audio.setVisibility(View.VISIBLE);
                    holder.leftMsg.setClickable(true);
                    // set up voice length
                    holder.leftMsg.setClickable(true);
                    String path = msg.getContent();
                    MediaPlayer mMediaPlayer = new MediaPlayer();
                    try {
                        mMediaPlayer.setDataSource(path);
                        mMediaPlayer.prepare();
                        holder.leftMsg.setText(String.valueOf(mMediaPlayer.getDuration() / 1000) + " ''");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    holder.leftMsg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaManager.playSound(path, new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // ignore
                                }
                            });
                        }
                    });

                    holder.left_audio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaManager.playSound(path, new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // ignore
                                }
                            });
                        }
                    });

                } else {
                    holder.left_audio.setVisibility(View.GONE);
                    holder.leftMsg.setText(msg.getContent());
                    holder.leftMsg.setClickable(false);
                }
                holder.left_portrait.setImageURI(receiver_uri);
                holder.leftLayout.setVisibility(View.VISIBLE);
            } else {
                holder.leftLayout.setVisibility(View.GONE);
                if (msg.getType() == com.mobile.factory.model.db.entity.Message.TYPE_AUDIO) {
                    holder.right_audio.setVisibility(View.VISIBLE);
                    holder.rightMsg.setClickable(true);
                    String path = msg.getContent();
                    MediaPlayer mMediaPlayer = new MediaPlayer();
                    try {
                        mMediaPlayer.setDataSource(path);
                        mMediaPlayer.prepare();
                        holder.rightMsg.setText(String.valueOf(mMediaPlayer.getDuration() / 1000) + " ''");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    holder.rightMsg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaManager.playSound(path, new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {

                                }
                            });
                        }
                    });

                    holder.right_audio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaManager.playSound(path, new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {

                                }
                            });
                        }
                    });

                } else {
                    holder.right_audio.setVisibility(View.GONE);
                    holder.rightMsg.setText(msg.getContent());
                    holder.rightMsg.setClickable(false);
                }
                holder.right_portrait.setImageURI(my_uri);
                holder.rightLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mMsgList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout leftLayout;
            LinearLayout rightLayout;
            net.qiujuer.genius.ui.widget.TextView leftMsg;
            net.qiujuer.genius.ui.widget.TextView rightMsg;
            PortraitView left_portrait;
            PortraitView right_portrait;
            PortraitView left_audio;
            PortraitView right_audio;

            public ViewHolder(@NonNull View view) {
                super(view);
                leftLayout = view.findViewById(R.id.left_layout);
                rightLayout = view.findViewById(R.id.right_layout);
                leftMsg = view.findViewById(R.id.left_text);
                rightMsg = view.findViewById(R.id.right_text);
                left_portrait = view.findViewById(R.id.left_portrait);
                right_portrait = view.findViewById(R.id.right_portrait);
                left_audio = view.findViewById(R.id.left_audio);
                right_audio = view.findViewById(R.id.right_audio);
            }
        }


    }


}
