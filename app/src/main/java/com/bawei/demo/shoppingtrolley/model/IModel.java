package com.bawei.demo.shoppingtrolley.model;

import com.bawei.demo.shoppingtrolley.utils.MCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String path, Map<String,String> map, Class clazz, MCallBack mCallBack);
    void requestDataGet(String path, Class clazz, MCallBack mCallBack);
    void requestDataDelete(String path, Class clazz, MCallBack mCallBack);
}
