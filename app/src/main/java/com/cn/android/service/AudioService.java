package com.cn.android.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cn.android.R;
import com.cn.android.helper.ActivityStackManager;
import com.cn.android.jpush.ExampleUtil;
import com.cn.android.other.EventBusManager;
import com.cn.android.ui.activity.LoginActivity;
import com.cn.android.utils.SPUtils;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by desk 3-2 on 2018/10/14.
 */

public class AudioService extends Service {

    public static Context context;
    //初始化MediaPlayer
    public MediaPlayer mMediaPlayer = new MediaPlayer();
    private MyBinder mBinder = new MyBinder();


    //歌曲路径
    private int[] music = new int[]{
            R.raw.new_order,
            R.raw.pkgg
    };

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusManager.register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        context = this;
        registerMessageReceiver();
        return mBinder;
    }

    public class MyBinder extends Binder {

        /**
         * 播放current
         */
        public void playOder() {
            playAudio(music[0]);
        }

    }

    private void playAudio(int music) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mMediaPlayer = MediaPlayer.create(AudioService.this, music);
        if (!mMediaPlayer.isPlaying()) {
            //如果还没开始播放，就开始
            mMediaPlayer.start();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mMediaPlayer.reset();
            }
        });
    }

    @Override
    public void onDestroy() {
        Looper.getMainLooper();
        EventBusManager.unregister(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        mBinder.playOder();
    }

}
