package com.bawei.demo.shoppingtrolley.model;

import com.bawei.demo.shoppingtrolley.utils.MCallBack;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class IModelmpl implements IModel {
    @Override
    public void requestData(String path, Map<String, String> map, final Class clazz, final MCallBack mCallBack) {
        //判断有无网络
        if(!MeansUtils.hasNetwork()){
            MeansUtils.toast("当前网络不可用");
        }else {
            RetrofitUtils.getInstance().postRequest(path, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Gson gson=new Gson();
                        Object o = gson.fromJson(data, clazz);
                        mCallBack.getSuccessData(o);
                    }catch (Exception e){
                        e.printStackTrace();
                        mCallBack.getFailData(e.getMessage());
                    }
                }

                @Override
                public void onFail(String error) {
                    mCallBack.getFailData(error);
                }
            });
        }


    }

    @Override
    public void requestDataGet(String path, final Class clazz, final MCallBack mCallBack) {
        //判断有无网络
        if(!MeansUtils.hasNetwork()){
            MeansUtils.toast("当前网络不可用");
        }else {
            RetrofitUtils.getInstance().getRequest(path, new RetrofitUtils.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Gson gson=new Gson();
                        Object o = gson.fromJson(data,clazz);
                        mCallBack.getSuccessData(o);
                    }catch (Exception e){
                        e.printStackTrace();
                        mCallBack.getFailData(e.getMessage());
                    }
                }

                @Override
                public void onFail(String error) {
                    mCallBack.getFailData(error);
                }
            });
        }

    }

    @Override
    public void requestDataDelete(String path, final Class clazz, final MCallBack mCallBack) {
        //判断有无网络
        if(!MeansUtils.hasNetwork()){
            MeansUtils.toast("当前网络不可用");
        }else {
            RetrofitUtils.getInstance().deleteRequest(path, new RetrofitUtils.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Gson gson=new Gson();
                        Object o = gson.fromJson(data,clazz);
                        mCallBack.getSuccessData(o);
                    }catch (Exception e){
                        e.printStackTrace();
                        mCallBack.getFailData(e.getMessage());
                    }
                }

                @Override
                public void onFail(String error) {
                    mCallBack.getFailData(error);
                }
            });
        }

    }

    @Override
    public void requestDataPut(String path, Map<String, String> map, final Class clazz, final MCallBack mCallBack) {
        //判断有无网络
        if(!MeansUtils.hasNetwork()){
            MeansUtils.toast("当前网络不可用");
        }else {
            RetrofitUtils.getInstance().putRequest(path, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Gson gson=new Gson();
                        Object o = gson.fromJson(data, clazz);
                        mCallBack.getSuccessData(o);
                    }catch (Exception e){
                        e.printStackTrace();
                        mCallBack.getFailData(e.getMessage());
                    }
                }

                @Override
                public void onFail(String error) {
                    mCallBack.getFailData(error);
                }
            });
        }



    }

    @Override
    public void requestDataPost(String path, Map<String, String> map, final Class clazz, final MCallBack mCallBack) {
        RetrofitUtils.getInstance().postFile(path, map, new RetrofitUtils.HttpCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    Gson gson=new Gson();
                    Object o = gson.fromJson(data, clazz);
                    mCallBack.getSuccessData(o);
                }catch (Exception e){
                    e.printStackTrace();
                    mCallBack.getFailData(e.getMessage());
                }
            }

            @Override
            public void onFail(String error) {
               mCallBack.getFailData(error);
            }
        });
    }

    @Override
    public void requestDataMduoContext(String url, Map<String, String> map, List<File> list, final Class clazz, final MCallBack mCallBack) {
        RetrofitUtils.getInstance().postduocon(url, map, list, new RetrofitUtils.HttpCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    Gson gson=new Gson();
                    Object o = gson.fromJson(data, clazz);
                    mCallBack.getSuccessData(o);
                }catch (Exception e){
                    e.printStackTrace();
                    mCallBack.getFailData(e.getMessage());
                }
            }

            @Override
            public void onFail(String error) {
               mCallBack.getFailData(error);
            }
        });
    }


}
