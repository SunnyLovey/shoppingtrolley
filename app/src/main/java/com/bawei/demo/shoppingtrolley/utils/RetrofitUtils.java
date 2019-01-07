package com.bawei.demo.shoppingtrolley.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bawei.demo.shoppingtrolley.app.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {
    private static  RetrofitUtils instance;
    private OkHttpClient client;
    private final String BASE_URL="http://172.17.8.100/small/";
    private BaseApis baseApis;
    //创建单例
    public static RetrofitUtils getInstance(){
        if(instance==null){
            synchronized (RetrofitUtils.class){
                instance=new RetrofitUtils();
            }
        }
        return instance;
    }
    //构造方法
    public RetrofitUtils(){
        //日志拦截
       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        client=new OkHttpClient.Builder()
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000,TimeUnit.SECONDS)
                .connectTimeout(2000,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求
                        Request request = chain.request();
                        SharedPreferences login = MyApplication.MyApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
                        String userId = login.getString("userId", null);
                        String sessionId = login.getString("sessionId", null);
                        Request.Builder builder=request.newBuilder();
                        builder.method(request.method(),request.body());
                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder.addHeader("userId",userId);
                            builder.addHeader("sessionId",sessionId);
                        }
                        Request build = builder.build();

                        return chain.proceed(build);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();

        baseApis=retrofit.create(BaseApis.class);
    }

   //get请求
    public RetrofitUtils getRequest(String url,HttpCallBack httpCallBack){
        baseApis.getRequest(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpCallBack));
        return instance;
    }
    //delete请求
    public RetrofitUtils deleteRequest(String url,HttpCallBack httpCallBack){
        baseApis.deleteRequest(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpCallBack));
        return instance;
    }

    //post请求
    public RetrofitUtils postRequest(String url, Map<String,String> map,HttpCallBack httpCallBack){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.postRequest(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpCallBack));
        return instance;
    }

    private Observer getObserver(final HttpCallBack callBack) {
        Observer observer=new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(callBack!=null){
                    callBack.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    if(callBack!=null){
                        callBack.onSuccess(result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(callBack!=null){
                        callBack.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }


    //接口传值
    public HttpCallBack httpCallBack;

    public void setHttpCallBack(HttpCallBack callBack){
        this.httpCallBack=callBack;
    }

    public interface HttpCallBack{
        void onSuccess(String data);//成功
        void onFail(String error);//失败
    }
}
