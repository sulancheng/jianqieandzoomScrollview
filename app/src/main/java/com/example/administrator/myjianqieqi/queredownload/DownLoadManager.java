package com.example.administrator.myjianqieqi.queredownload;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator
 * on 2017/9/18.
 */

public class DownLoadManager {
    public static final int STATE_NONE = -1;
    /**
     * 开始下载
     */
    public static final int STATE_START = 0;
    /**
     * 等待中
     */
    public static final int STATE_WAITING = 1;
    /**
     * 下载中
     */
    public static final int STATE_DOWNLOADING = 2;
    /**
     * 暂停
     */
    public static final int STATE_PAUSED = 3;
    /**
     * 下载完毕
     */
    public static final int STATE_DOWNLOADED = 4;
    /**
     * 下载失败
     */
    public static final int STATE_ERROR = 5;
    /**
     * 删除下载成功
     */
    public static final int STATE_DELETE = 6;

    /**
     * 用于记录观察者，当信息发送了改变，需要通知他们
     */
    private Map<String, DownLoadObserver> mObservers = new ConcurrentHashMap<String, DownLoadObserver>();
    /**
     * 用于记录所有下载的任务，方便在取消下载时，通过id能找到该任务进行删除
     */
    private Map<String, DownLoadTask> mTaskMap = new ConcurrentHashMap<String, DownLoadTask>();
    /**
     * 全局记录当前正在下载的bean
     */
    private DownLoadBean down_bean;

    private static DownLoadManager instance;

    public static DownLoadManager getInstance() {
        if (instance == null) {
            instance = new DownLoadManager();
        }
        return instance;
    }

    /**
     * 注册观察者
     */
    public void registerObserver(String id, DownLoadObserver observer) {
        if (!mObservers.containsKey(id)) {
            mObservers.put(id, observer);
        }
    }

    /**
     * 移除观察者
     */
    public void RemoveObserver() {
        mObservers.clear();
    }

    /**
     * 删除当前正在下载的任务
     */
    public void DeleteDownTask(DownLoadBean bean) {
        if (mTaskMap.containsKey(bean.id)) {
            // 拿到当前任务
            DownLoadTask task = mTaskMap.get(bean.id);
            // 暂停下载任务(等于取消了该线程)
            task.bean.downloadState = STATE_DELETE;

            // 再更改删除界面状态(这是也调一次是怕在没下载的时候删除)
            bean.downloadState = STATE_DELETE;
            notifyDownloadStateChanged(bean);

            // 最后删除数据库数据
            //DataBaseUtil.DeleteDownLoadById(bean.id);
            // 删除文件
            File file = new File("");
            if (file.exists()) {
                file.delete();
            }
            file = null;
        }
    }

    /**
     * 销毁的时候关闭线程池以及当前执行的线程，并清空所有数据和把当前下载状态存进数据库
     */
    public void Destory() {
        DownLoadExecutor.stop();
        mObservers.clear();
        mTaskMap.clear();
        if (down_bean != null) {
            down_bean.downloadState = STATE_PAUSED;
            //DataBaseUtil.UpdateDownLoadById(down_bean);
        }
    }

    /**
     * 当下载状态发送改变的时候回调
     */
    private ExecuteHandler handler = new ExecuteHandler();

    /**
     * 拿到主线程Looper
     */
    @SuppressLint("HandlerLeak")
    private class ExecuteHandler extends Handler {
        private ExecuteHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            DownLoadBean bean = (DownLoadBean) msg.obj;
            if (mObservers.containsKey(bean.id)) {
                DownLoadObserver observer = mObservers.get(bean.id);
                switch (bean.downloadState) {
                    case STATE_START:// 开始下载
                        observer.onStart(bean);
                        break;
                    case STATE_WAITING:// 准备下载
                        observer.onPrepare(bean);
                        break;
                    case STATE_DOWNLOADING:// 下载中
                        observer.onProgress(bean);
                        break;
                    case STATE_PAUSED:// 暂停
                        observer.onStop(bean);
                        break;
                    case STATE_DOWNLOADED:// 下载完毕
                        observer.onFinish(bean);
                        break;
                    case STATE_ERROR:// 下载失败
                        observer.onError(bean);
                        break;
                    case STATE_DELETE:// 删除成功
                        observer.onDelete(bean);
                        break;
                }
            }
        }
    }

    /**
     * 当下载状态发送改变的时候调用
     */
    private void notifyDownloadStateChanged(DownLoadBean bean) {
        Message message = handler.obtainMessage();
        message.obj = bean;
        handler.sendMessage(message);
    }

    /**
     * 开启下载，需要传入一个DownAppBean对象
     */
    public void download(DownLoadBean loadBean) {
        // 先判断是否有这个app的下载信息
//        DownLoadBean bean = DataBaseUtil.getDownLoadById(loadBean.id);
        DownLoadBean bean = null;
        if (bean == null) {// 如果没有，则根据loadBean创建一个新的下载信息
            bean = loadBean;
            //DataBaseUtil.insertDown(bean);
        }

        // 判断状态是否为STATE_NONE、STATE_PAUSED、STATE_ERROR、STATE_DELETE。只有这4种状态才能进行下载，其他状态不予处理
        if (bean.downloadState == STATE_NONE
                || bean.downloadState == STATE_PAUSED
                || bean.downloadState == STATE_DELETE
                || bean.downloadState == STATE_ERROR) {
            // 下载之前，把状态设置为STATE_WAITING，因为此时并没有产开始下载，只是把任务放入了线程池中，当任务真正开始执行时，才会改为STATE_DOWNLOADING
            bean.downloadState = STATE_WAITING;
            //DataBaseUtil.UpdateDownLoadById(bean);
            // 每次状态发生改变，都需要回调该方法通知所有观察者
            notifyDownloadStateChanged(bean);

            DownLoadTask task = new DownLoadTask(bean);// 创建一个下载任务，放入线程池
            // 线程放入map里面方便管理
            mTaskMap.put(bean.id, task);

            DownLoadExecutor.execute(task);
        } else if (bean.downloadState == STATE_START
                || bean.downloadState == STATE_DOWNLOADING
                || bean.downloadState == STATE_WAITING) {// 如果正在下载则暂停

            if (mTaskMap.containsKey(bean.id)) {
                DownLoadTask task = mTaskMap.get(bean.id);
                task.bean.downloadState = STATE_PAUSED;
                //DataBaseUtil.UpdateDownLoadById(task.bean);

                // 取消还在排队中的线程
                if (DownLoadExecutor.cancel(task)) {
                    mObservers.get(bean.id).onStop(task.bean);
                }
            }
        }
    }
}
