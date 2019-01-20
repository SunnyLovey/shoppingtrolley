package com.bawei.demo.shoppingtrolley.model;

import com.bawei.demo.shoppingtrolley.utils.MCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface IModel {
    void requestData(String path, Map<String,String> map, Class clazz, MCallBack mCallBack);
    void requestDataGet(String path, Class clazz, MCallBack mCallBack);
    void requestDataDelete(String path, Class clazz, MCallBack mCallBack);
    void requestDataPut(String path, Map<String,String> map, Class clazz, MCallBack mCallBack);
    void requestDataPost(String path, Map<String,String> map, Class clazz, MCallBack mCallBack);
    void requestDataMduoContext(String url, Map<String, String> map, List<File> list,Class clazz,MCallBack mCallBack);


}
