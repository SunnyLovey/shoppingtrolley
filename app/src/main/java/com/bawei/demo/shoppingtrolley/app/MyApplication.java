package com.bawei.demo.shoppingtrolley.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Environment;
import android.view.WindowManager;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MyApplication extends Application {
    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    private static Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images_zj")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置,生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,config);
        resetDensity();

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }

    public void resetDensity(){
        Point size = new Point();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        getResources().getDisplayMetrics().xdpi = size.x/DESIGN_WIDTH*72f;
    }
    public static Context MyApplication() {
        return context;
    }

}
