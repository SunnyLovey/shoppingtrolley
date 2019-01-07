package com.bawei.demo.shoppingtrolley.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String path, Map<String,String> map,Class clazz);
    void startRequestGet(String path,Class clazz);
    void startRequestDelete(String path,Class clazz);
}
