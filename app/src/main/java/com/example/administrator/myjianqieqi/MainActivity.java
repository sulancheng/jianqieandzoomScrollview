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

import com.yalantis.ucrop.UCrop;

import java.io.File;

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
        Intent i = new Intent(Intent.ACTION_PICK, null);
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(i, REQUESTCODE_PHOTOZOOM);
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
