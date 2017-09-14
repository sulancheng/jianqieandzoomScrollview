package com.example.administrator.myjianqieqi.utils;

import android.os.Handler;

import com.example.administrator.myjianqieqi.bean.ProgressInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator
 * on 2017/9/14.
 */

public class DownloadUtils {
    protected static final int UPDATE_PROGRESS = 0;
    private int progress;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    ProgressInfo info =(ProgressInfo) msg.obj;
                    break;

                default:
                    break;
            }
        }
    };
    public void download(ProgressInfo progressInfo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress<100){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress++;
                    progressInfo.setProgress(progress);//进度储存进Bean.
                    EventBus.getDefault().post(progressInfo);
                    //handler.obtainMessage(UPDATE_PROGRESS, info).sendToTarget();
                }
            }
        }).start();
    }
}
