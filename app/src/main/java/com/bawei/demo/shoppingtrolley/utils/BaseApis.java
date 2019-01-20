package com.bawei.demo.shoppingtrolley.utils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;


public interface BaseApis<T> {

    //get请求
    @GET
    Observable<ResponseBody> getRequest(@Url String url);

    //post请求
    @POST
    Observable<ResponseBody> postRequest(@Url String url, @QueryMap Map<String,String> map);

    //delete请求
    @DELETE
    Observable<ResponseBody> deleteRequest(@Url String url);

    //put请求
    @PUT
    Observable<ResponseBody> putRequest(@Url String url, @QueryMap Map<String,String> map);
    //上传头像
    @POST
    Observable<ResponseBody> postFile(@Url String url,@Body MultipartBody multipartBody);
    @POST
    @Multipart
    Observable<ResponseBody> postDuoContent(@Url String url ,@QueryMap Map<String, String> map ,@Part MultipartBody.Part[] parts);



}
