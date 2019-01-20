package com.bawei.demo.shoppingtrolley.presenter;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IPresenter {
    void startRequest(String path, Map<String,String> map,Class clazz);
    void startRequestGet(String path,Class clazz);
    void startRequestDelete(String path,Class clazz);
    void startRequestPut(String path, Map<String,String> map,Class clazz);
    void startRequestPost(String path, Map<String,String> map,Class clazz);
    void startRequestContent(String url, Map<String, String> map, List<File> list, Class clazz);


}
