package com.bawei.demo.shoppingtrolley.utils;

public class Apis {
    public static  final String url_register="user/v1/register";
    public static  final String url_login="user/v1/login";
    public static  final String url_banner="commodity/v1/bannerShow";
    public static  final String url_hot_goods="commodity/v1/commodityList";
    public static  final String url_one_Category="commodity/v1/findFirstCategory";
    public static  final String url_two_Category="commodity/v1/findSecondCategory?firstCategoryId=%s";
    public static  final String url_hot_Label="commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=%d";
    public static  final String url_search_goods="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    public static  final String url_two_goodsMore="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    public static  final String url_detail_goods="commodity/v1/findCommodityDetailsById?commodityId=%d";
    public static  final String url_circle="circle/v1/findCircleList?page=%d&count=%d";
    public static  final String url_footprint="commodity/verify/v1/browseList?page=%d&count=%d";
    public static  final String url_addCircle="circle/verify/v1/addCircleGreat";
    public static  final String url_cancelCircle="circle/verify/v1/cancelCircleGreat?circleId=%d";


}
