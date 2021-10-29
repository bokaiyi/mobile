package com.mobile.myApp.tools;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * media helper class
 */
public class MediaManager {
    private static MediaPlayer mMediaPlayer;
    private static boolean isPause;
    private String currentFilePath;
    static Context context;

    public MediaManager(Context context) {
        this.context = context;
    }

    /**
     * play music
     *
     * @param filePath
     * @param onCompletionListener
     */
    public static void playSound(String filePath, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            //create an error listener
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
                    Toast.makeText(context, "Play failed", Toast.LENGTH_SHORT).show();
                    mMediaPlayer.reset();
                    return false;
                }
            });
        } else {
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setDataSource(filePath);
            //set volume and looping
            mMediaPlayer.setVolume(1, 1);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Play failed", Toast.LENGTH_SHORT).show();
            release();
        }
    }

    /**
     * Pause playback
     */
    public static void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            //is playing
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    public static boolean isPlaying() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * isPause currenctly
     */
    public static void resume() {
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            isPause = false;
        }
    }

    /**
     * release resource
     */
    public static void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}

