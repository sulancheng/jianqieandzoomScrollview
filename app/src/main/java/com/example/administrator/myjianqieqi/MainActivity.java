package com.example.administrator.myjianqieqi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myjianqieqi.gouwaddj.GouwuActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;

public class MainActivity extends Activity {
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int REQUESTCODE_PHOTOZOOM = 4;
    private ImageView myphotot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestMultiplePermissions();
        myphotot = (ImageView)findViewById(R.id.myphotot);
        findViewById(R.id.xuanfu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,XuanfuActivity.class));
            }
        });
        findViewById(R.id.recycy).setOnClickListener(view ->
            startActivity(new Intent(MainActivity.this,RecycleActivity.class))
        );
        findViewById(R.id.cehua).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this,CehuaDelAcivity.class))
        );
        findViewById(R.id.bt_download).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this,EventBusDownload.class))
        );
        findViewById(R.id.bt_gou).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this,GouwuActivity.class))
        );
    }
    private void downFile(final String url) {
        //下载完后存到SD中。
        final File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/sfjsj");
        if (!file.exists()) {
            file.mkdirs();
        }
        File appfile = new File(file, new Date().getTime()+"");
        if(appfile.exists()){
            return;
        }
        MyThread myThread = new MyThread(url, appfile);
        myThread.start();
    }

    private class MyThread extends Thread {
        private String url;
        private File file;

        public MyThread(String url, File file) {
            this.url = url;
            this.file = file;
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
                        if (contentLength > 0) {
                        }
                    }
                    if (outputStream != null) {

                        outputStream.close();
                    }
                    if (inputStream != null)
                        inputStream.close();
                    if (!this.isInterrupted()) {
                    }
                }

            } catch (Exception e) {
                if (file != null && file.exists()) {
                    file.delete();
                }
                e.printStackTrace();
            }

        }


    }
    private void requestMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean b1 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            boolean b2 = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            boolean b3 = checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

//            PermissionUtils.requestPermission(this,1000,Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.READ_PHONE_STATE,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.ACCESS_FINE_LOCATION,true);
//            PermissionUtils.requestPermission(this,1000,Manifest.permission.READ_CONTACTS,true);


            if (!b1 || !b2|| !b3 ){//无权限
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE};
                requestPermissions(permissions, 2000);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean flag = false;
        if (requestCode == 2000) {
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    flag = true;
                    break;
                }
            }
        }
    }
    public void xuanz(View v){
        downFile("http://111.205.184.233:5011/vod/3277eb803d497878aec4caf6cbeeb836.flv");
//        Intent i = new Intent(Intent.ACTION_PICK, null);
//        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
//        startActivityForResult(i, REQUESTCODE_PHOTOZOOM);
    }
    public static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
    public static final int REQUESTCODE_PHOTOGRAPH = 3;
    public void xuanz2(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(IMAGE_FILE_LOCATION));
        startActivityForResult(intent, REQUESTCODE_PHOTOGRAPH);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_PHOTOGRAPH) {
            UCrop.of(Uri.parse(IMAGE_FILE_LOCATION), Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"666.jpg")))
                    .withAspectRatio(16, 9)
                    .withMaxResultSize(maxWidth, maxHeight)
                    .start(MainActivity.this);
        }
        if (requestCode == REQUESTCODE_PHOTOZOOM) {
            UCrop.of(data.getData(), Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"555.jpg")))
                    .withAspectRatio(16, 9)
                    .withMaxResultSize(maxWidth, maxHeight)
                    .start(MainActivity.this);
            //zaoxpath = getRealFilePath(mContext, data.getData());
            //暂存选择图片的path
            //Log.i("选择本机的地址 = ");
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Log.i("UCrop","jianqiewanle1");
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap photo = BitmapFactory.decodeFile(resultUri.getPath());
            Log.i("UCrop","jianqiewanle2");
            //saveandcps(photo);
            myphotot.setImageBitmap(photo);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
}
