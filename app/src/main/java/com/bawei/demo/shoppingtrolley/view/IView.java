package com.bawei.demo.shoppingtrolley.view;

public interface IView<T> {
    //请求成功的方法
    void requestSuccess(T data);
    //请求失败的方法
    void requestFail(String error);
}
