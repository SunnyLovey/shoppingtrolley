package com.bawei.demo.shoppingtrolley.model;

import com.bawei.demo.shoppingtrolley.utils.MCallBack;
import com.bawei.demo.shoppingtrolley.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.Map;

public class IModelmpl implements IModel {
    @Override
    public void requestData(String path, Map<String, String> map, final Class clazz, final MCallBack mCallBack) {
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

    @Override
    public void requestDataGet(String path, final Class clazz, final MCallBack mCallBack) {
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

    @Override
    public void requestDataDelete(String path, final Class clazz, final MCallBack mCallBack) {
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
