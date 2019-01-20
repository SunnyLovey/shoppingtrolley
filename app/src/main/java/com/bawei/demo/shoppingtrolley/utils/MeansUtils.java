package com.bawei.demo.shoppingtrolley.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.app.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class MeansUtils {
    //判断手机号
    public static boolean isPhone(String phone){
        Pattern compile = compile("^1[0-9]{10}$");
        Matcher matcher = compile.matcher(phone);
        return matcher.matches();
    }
    //判断密码
    public static boolean isPass(String pass){
        return pass.length()>=6 && pass.length()<=20;
    }

    //获取随机数
    public static String getRandom(){
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }
        return sb.toString();
    }

    //吐司
    public static void toast(String message){
        Toast.makeText(MyApplication.MyApplication(),message,Toast.LENGTH_SHORT).show();
    }
    //是否有可用网络
    public static boolean hasNetwork(){
        if (MyApplication.MyApplication() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.MyApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void saveBitmap(Bitmap bitmap, String path, int quality) throws IOException {
        String dir = path.substring(0, path.lastIndexOf("/"));
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            try {
                if (!dirFile.mkdirs()) {
                    return;
                }
            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }

        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(path);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    }
