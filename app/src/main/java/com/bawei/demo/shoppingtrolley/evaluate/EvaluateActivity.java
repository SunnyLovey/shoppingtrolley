package com.bawei.demo.shoppingtrolley.evaluate;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderBean;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.utils.RetrofitUtils;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.bumptech.glide.Glide;
import com.goyourfly.multi_picture.ImageLoader;
import com.goyourfly.multi_picture.MultiPictureView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

/*
* author:zhangjing
* 发表评价，同步到圈子
* 20190112
* */
public class EvaluateActivity extends AppCompatActivity implements IView{
    @BindView(R.id.image_pic)
    ImageView imageView;
    @BindView(R.id.goods_name)
    TextView textView_name;
    @BindView(R.id.goods_price)
    TextView textView_price;
    @BindView(R.id.button_evaluate)
    Button button_evaluate;
    @BindView(R.id.synchronize_circle)
    CheckBox checkBox_synchronize_circle;
    @BindView(R.id.evaluate_content)
    EditText editText_content;
    @BindView(R.id.issue_image_recycle)
    RecyclerView recyclerView;
    private RecycleImageAdpter imageAdpter;
    private IPresenterImpl iPresenter;
    private OrderBean.OrderListBean.DetailListBean bean;
    private String orderId;
   /* private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 裁剪之后*/
   private final int REQUEST_PICK = 200;
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICTRUE = 300;
   //private String path=Environment.getExternalStorageDirectory()+"/header_image.png";
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
  //private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private final String path =Environment.getExternalStorageDirectory()+ "/image.png";
    private PopupWindow popupWindow;
    private TextView textView_camera,textView_picture,textView_dismiss;
    private File file;
    private List<File> file_list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        //实例化
        iPresenter=new IPresenterImpl(this);
        //接收值
        Intent intent = getIntent();
        bean = (OrderBean.OrderListBean.DetailListBean) intent.getSerializableExtra("aList");
        orderId = intent.getStringExtra("orderId");
        //赋值
        String commodityPic = bean.getCommodityPic();
        String[] split = commodityPic.split("\\,");
        Glide.with(this).load(split[0]).into(imageView);
        textView_name.setText(bean.getCommodityName());
        textView_price.setText("￥"+ bean.getCommodityPrice()+".0");
        //imageAdpter的条目点击事件
        oneImageClick();
        //设置图片的list集合和适配器
        initImageAdpter();






    }
    private void initImageAdpter() {

        imageAdpter.getImage(new RecycleImageAdpter.ImageClick() {
            @Override
            public void getdata() {
                show();
            }
        });
    }
    private void oneImageClick() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
       imageAdpter = new RecycleImageAdpter(this);
        recyclerView.setAdapter(imageAdpter);
    }


    private void show() {
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
                    //相机
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            };
            ActivityCompat.requestPermissions(this, mStatenetwork, 100);
        }
        painter();//换头像
    }

    private void painter() {
        View v = View.inflate(this, R.layout.pop_camera, null);
        textView_camera = v.findViewById(R.id.text_camera);
        textView_picture=v.findViewById(R.id.text_picture);
        textView_dismiss=v.findViewById(R.id.text_dismiss);
        //拍摄
        textView_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent, REQUEST_CAMEAR);
                popupWindow.dismiss();

            }


        });
        //相册

        textView_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK);
            }
        });
        //取消
        textView_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(v,Gravity.BOTTOM,0,0);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission  = false;
        if(requestCode == 100){
            for (int i = 0;i<grantResults.length;i++){
                if(grantResults[i] == -1){
                    hasPermission = true;
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMEAR && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, REQUEST_PICTRUE);

        }
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_PICTRUE);
        }
        //获取剪切完的图片数据 bitmap
        if (requestCode == REQUEST_PICTRUE && resultCode == RESULT_OK) {
            Bitmap bitmap =data.getParcelableExtra("data");
            try {
                MeansUtils.saveBitmap(bitmap,PATH_FILE,50);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("TAG",e.getMessage());
            }
            List<Object>  list = new ArrayList<>();
            list.add(bitmap);
            imageAdpter.setmList(list);
            file = new File(PATH_FILE);
            file_list.add(file);
        }
    }

    @OnClick({R.id.button_evaluate})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.button_evaluate:
                if(checkBox_synchronize_circle.isChecked()){
                    publishCircle();
                }else {
                    publichComment();
                }

                break;

        }
    }

    private void publichComment() {
        String content = editText_content.getText().toString();
        int commodityId = bean.getCommodityId();
        Map<String, String> map=new HashMap<>();
        map.put("commodityId",commodityId+"");
        map.put("orderId",orderId+"");
        map.put("content",content+"");
        iPresenter.startRequestContent(Apis.url_comment_goods,map,file_list, AddShoppingBean.class);
    }


    //发表圈子
    private void publishCircle() {
        String content = editText_content.getText().toString();
        int commodityId = bean.getCommodityId();

        Map<String,String> map=new HashMap<>();
        map.put("commodityId",commodityId+"");
        map.put("content",content);
      //  map.put("orderId",orderId);
       iPresenter.startRequestContent(Apis.url_releaseCircle,map,file_list, AddShoppingBean.class);
      // finish();
        //iPresenter.requestDataPduoContext(IssueUrl,map,file_list,IssueBean.class);

    }

    @Override
    public void requestSuccess(Object data) {
           if(data instanceof AddShoppingBean){
               AddShoppingBean bean= (AddShoppingBean) data;
               MeansUtils.toast(bean.getMessage());
           }
    }

    @Override
    public void requestFail(String error) {
        MeansUtils.toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();

    }
}
