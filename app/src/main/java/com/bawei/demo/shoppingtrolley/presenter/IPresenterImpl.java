package com.bawei.demo.shoppingtrolley.presenter;

import com.bawei.demo.shoppingtrolley.model.IModelmpl;
import com.bawei.demo.shoppingtrolley.utils.MCallBack;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelmpl iModelmpl;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModelmpl=new IModelmpl();
    }

    @Override
    public void startRequest(String path, Map<String, String> map, Class clazz) {
        iModelmpl.requestData(path, map, clazz, new MCallBack() {
            @Override
            public void getSuccessData(Object data) {
                 iView.requestSuccess(data);
            }

            @Override
            public void getFailData(String error) {
                 iView.requestFail(error);
            }
        });

    }

    @Override
    public void startRequestGet(String path, Class clazz) {
        iModelmpl.requestDataGet(path, clazz, new MCallBack() {
            @Override
            public void getSuccessData(Object data) {
                iView.requestSuccess(data);
            }

            @Override
            public void getFailData(String error) {
            iView.requestFail(error);
            }
        });
    }

    @Override
    public void startRequestDelete(String path, Class clazz) {
        iModelmpl.requestDataDelete(path, clazz, new MCallBack() {
            @Override
            public void getSuccessData(Object data) {
                iView.requestSuccess(data);
            }

            @Override
            public void getFailData(String error) {
                iView.requestFail(error);
            }
        });
    }

    //解除绑定
    public void detachView(){
        if(iModelmpl!=null){
            iModelmpl=null;
        }
        if(iView!=null){
            iView=null;
        }
    }
}
