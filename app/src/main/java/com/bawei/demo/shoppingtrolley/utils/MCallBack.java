package com.bawei.demo.shoppingtrolley.utils;

public interface MCallBack<T> {
    void getSuccessData(T data);
    void getFailData(String error);
}
