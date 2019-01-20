package com.bawei.demo.shoppingtrolley.utils;

public class Apis {
    //注册
    public static  final String url_register="user/v1/register";
    //登录
    public static  final String url_login="user/v1/login";
    //轮播图
    public static  final String url_banner="commodity/v1/bannerShow";
    //商品
    public static  final String url_hot_goods="commodity/v1/commodityList";
    //一级列表
    public static  final String url_one_Category="commodity/v1/findFirstCategory";
    //二级列表
    public static  final String url_two_Category="commodity/v1/findSecondCategory?firstCategoryId=%s";
    //商品更多
    public static  final String url_hot_Label="commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=%d";
    //根据关键词找商品
    public static  final String url_search_goods="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    //二级列表的商品
    public static  final String url_two_goodsMore="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    //商品详情
    public static  final String url_detail_goods="commodity/v1/findCommodityDetailsById?commodityId=%d";
    //圈子
    public static  final String url_circle="circle/v1/findCircleList?page=%d&count=%d";
    //足迹
    public static  final String url_footprint="commodity/verify/v1/browseList?page=%d&count=%d";
    //点赞
    public static  final String url_addCircle="circle/verify/v1/addCircleGreat";
    //取消赞
    public static  final String url_cancelCircle="circle/verify/v1/cancelCircleGreat?circleId=%d";
    //评论
    public static  final String url_comment="commodity/v1/CommodityCommentList?commodityId=%d&page=%d&count=%d";
    //添加购物车
    public static  final String url_add_shoppingCar="order/verify/v1/syncShoppingCart";
    //查询购物车
    public static  final String url_select_shoppingCar="order/verify/v1/findShoppingCart";
    //创建订单
    public static  final String url_order="order/verify/v1/createOrder";
    //添加地址
    public static  final String url_add_address="user/verify/v1/addReceiveAddress";
    //查询地址
    public static  final String url_sel_address="user/verify/v1/receiveAddressList";
    //默认地址
    public static  final String url_defsult_address="user/verify/v1/setDefaultReceiveAddress";
    //查询订单
    public static  final String url_find_order="order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=%d";
    //我的圈子
    public static  final String url_my_circle="circle/verify/v1/findMyCircleById?page=%d&count=%d";
    //修改昵称
    public static  final String url_update_name="user/verify/v1/modifyUserNick";
    //修改密码
    public static  final String url_update_pass="user/verify/v1/modifyUserPwd";
    //支付
    public static  final String url_pay="order/verify/v1/pay";
    //确定收货
    public static  final String url_confirm_goods="order/verify/v1/confirmReceipt";
    //发表圈子
    public static  final String url_releaseCircle="circle/verify/v1/releaseCircle";
    //发表评论
    public static  final String url_comment_goods="commodity/verify/v1/addCommodityComment";
    //查询钱包
    public static final String url_wallet="user/verify/v1/findUserWallet?page=%d&count=%d";
    //根据用户ID查询用户信息
    public static final String url_user_info="user/verify/v1/getUserById";
    //修改地址
    public static final String url_update_address="user/verify/v1/changeReceiveAddress";
    //删除订单
    public static final String url_delete_order="order/verify/v1/deleteOrder?orderId=%s";
    //上传头像
    public static final String URL_MODIFYHEADPIC="user/verify/v1/modifyHeadPic";





}
