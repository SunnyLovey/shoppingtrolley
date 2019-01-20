package com.bawei.demo.shoppingtrolley.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UploadHelper {
    private volatile static UploadHelper mInstance;
    public static Map<String, RequestBody> params;
    private Uri photoUri;

    private UploadHelper() {}

    //单例模式
    public static UploadHelper getInstance() {
        if (mInstance == null) {
            synchronized (UploadHelper.class) {
                if (mInstance == null)
                    mInstance = new UploadHelper();
                params = new HashMap<>();
            }
        }
        return mInstance;
    }

    //根据传进来的Object对象来判断是String还是File类型的参数
    public UploadHelper addParameter(String key, Object o) {
        RequestBody body = null;
        if (o instanceof String) {
            body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), (String) o);
        } else if (o instanceof File) {
            body = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), (File) o);
        }
        params.put(key, body);
        return this;
    }

    //建造者模式
    public Map<String, RequestBody> builder() {
        return params;
    }

    //清除参数
    public void clear(){
        params.clear();
    }

    //最终上传的Bitmap保存为File对象
    public void saveBitmapFile(List<Bitmap> mList, File[] files) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/suggestionUpload");
        if (myDir.exists()) {
            myDir.delete();
        }
        myDir.mkdirs();
        for (int i = 0; i < mList.size(); i++) {
            files[i] = new File(myDir, "ims" + i + ".JPEG");
            try {
                if (files[i].exists()) {
                    files[i].delete();
                }
                files[i].createNewFile();
                FileOutputStream out = new FileOutputStream(files[i]);
                mList.get(i).compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
               // LogUtil.e("最终上传图片的路径------>" + files[i].getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
