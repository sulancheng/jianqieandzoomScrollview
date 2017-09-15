package com.example.administrator.myjianqieqi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.administrator.myjianqieqi.bean.ProgressInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//eventbuss3.0的使用  listview的列表下载
public class EventBusDownload extends Activity {
    @BindView(R.id.lv_mylist)
    ListView mylistview;
    private ArrayList<ProgressInfo> progressInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_download);
        ButterKnife.bind(this);
        progressInfos = new ArrayList<>();
        for (int i=0;i<100;i++){
            progressInfos.add(new ProgressInfo("下载"+i,i+""));
        }
        mylistview.setAdapter(new myadapter());
    }
    class myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return progressInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return progressInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
//            CommentViewHolder commentViewHolder = CommentViewHolder.getCommentViewHolder(EventBusDownload.this, convertView, R.layout.event_adapter);
//            Button mybt_down = commentViewHolder.getView(R.id.bt_down);
//            ProgressBar my_download = commentViewHolder.getView(R.id.pb_download);
//            ProgressInfo myprogressInfo = progressInfos.get(i);
//            mybt_down.setText(myprogressInfo.getName());
//            int progress = myprogressInfo.getProgress();
//            my_download.setProgress(progress);
//            mybt_down.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sfjsj/demo/");
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
//                    File appfile = new File(file, "5656.flv");
//                    MyThread myThread = new MyThread("http://111.205.184.233:5011/vod/3277eb803d497878aec4caf6cbeeb836.flv", appfile, new Downpro() {
//                        @Override
//                        public void jindu(int progressInfo) {
//
//                        }
//                    });
//                    myThread.start();
//                }
//            });
//            return commentViewHolder.convertView;
            Myholder myholder = null;
            if(convertView == null){
                myholder = new Myholder();
                convertView = View.inflate(EventBusDownload.this,R.layout.event_adapter,null);
                myholder.mybt_down = convertView.findViewById(R.id.bt_down);
                myholder.my_download = convertView.findViewById(R.id.pb_download);
                convertView.setTag(myholder);
            }else {
                myholder = (Myholder) convertView.getTag();
            }
            ProgressInfo progressInfo = progressInfos.get(i);
            myholder.myprogressInfo = progressInfo;
            myholder.mybt_down.setText(progressInfo.getName());
            myholder.my_download.setProgress(progressInfo.getProgress());
            myholder.mybt_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    new DownloadUtils().download(progressInfo);
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sfjsj/demo/");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File appfile = new File(file, "5656.flv");
                    MyThread myThread = new MyThread("http://111.205.184.233:5011/vod/3277eb803d497878aec4caf6cbeeb836.flv", appfile,progressInfo, new Downpro() {
                        @Override
                        public void jindu(ProgressInfo progressInfo) {
                            EventBus.getDefault().post(progressInfo);
                        }
                    });
                    myThread.start();
                }
            });
            return convertView;
        }

    }
    private class MyThread extends Thread {
        private String url;
        private File file;
        private Downpro inter;
        private ProgressInfo progressInfo;

        public MyThread(String url, File file,ProgressInfo progressInfo,Downpro inter) {
            this.url = url;
            this.file = file;
            this.inter = inter;
            this.progressInfo = progressInfo;
        }

        @Override
        public void run() {
            try {
                //1.获取服务器地址（URL）
                //String path = "http://192.168.15.28:8080/weixin/2.jpg";
                String path = url;
                URL url = new URL(path);
                //2.建立连接
                HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
                //3设置请求方式和请求时间
                openConnection.setRequestMethod("GET");
                openConnection.setConnectTimeout(5000);
                //4判断响应
                if (openConnection.getResponseCode() == 200) {
                    //5.响应正确，下载图片（通过流）
                    InputStream inputStream = openConnection.getInputStream();
                    int contentLength = openConnection.getContentLength();
                    //将流变成图片
                    //Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    OutputStream outputStream = new FileOutputStream(file);
                    int len;
                    int count = 0;
                    byte[] arr = new byte[1024];
                    while ((len = inputStream.read(arr)) != -1) {
                        if (this.isInterrupted()) break;
                        outputStream.write(arr, 0, len);
                        count += len;
                        int aa = (count * 100) / contentLength;
                        Log.i("下载进度", "aa =" + aa);
                        progressInfo.setProgress(aa);
                        if(inter!=null){
                            inter.jindu(progressInfo);
                        }
                    }
                    if (outputStream != null) {

                        outputStream.close();
                    }
                    if (inputStream != null)
                        inputStream.close();
                    if (!this.isInterrupted()) {
                        Log.e("DownloadTActivity", "下载成功！");
                    }
                }

            } catch (Exception e) {
                Log.e("DownloadTActivity", "下载失败！");
                if (file != null && file.exists()) {
                    file.delete();
                }
                e.printStackTrace();
            }

        }


    }
    public interface Downpro{
        void jindu(ProgressInfo myprogressInfo);
    }
    private static class Myholder{
        ProgressInfo myprogressInfo;
        Button mybt_down;
        ProgressBar my_download;
        public Myholder(){
            //注册事件
            EventBus.getDefault().register(this);
        }
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMoonEvent(ProgressInfo messageEvent){
            if(this.myprogressInfo == messageEvent){
                this.my_download.setProgress(messageEvent.getProgress());
            }
        }
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            //取消注册事件
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
