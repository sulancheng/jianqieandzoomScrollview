package com.example.administrator.myjianqieqi.queredownload;

/**
 * Created by Administrator
 * on 2017/9/18.
 */

public interface  DownLoadObserver {
    /**准备下载*/
    void onPrepare(DownLoadBean bean);
    /** 开始下载 */
    void onStart(DownLoadBean bean);
    /** 下载中 */
    void onProgress(DownLoadBean bean);
    /** 暂停 */
    void onStop(DownLoadBean bean);
    /** 下载完成 */
    void onFinish(DownLoadBean bean);
    /** 下载失败 */
    void onError(DownLoadBean bean);
    /** 删除成功 */
    void onDelete(DownLoadBean bean);
}
