package com.bawei.demo.shoppingtrolley.personal;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.utils.RetrofitUtils;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bawei.demo.shoppingtrolley.utils.MeansUtils.toast;
/*
* author:zhangjing
* 修改昵称，头像，密码
* 20190111
* */

public class PersonalActivity extends AppCompatActivity implements IView{
    @BindView(R.id.text_personal_name)
    TextView textView_name;
    @BindView(R.id.image_pic)
    SimpleDraweeView simpleDraweeView_image_pic;
    @BindView(R.id.text_personal_pass)
    TextView textView_pass;
    private IPresenterImpl iPresenter;
    private EditText editText_name;
    private String name;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText editText_pass;
    private String pass;
    private String newPass;
    private String headPic;
    private PopupWindow popupWindow;
    private TextView textView_camera,textView_picture,textView_dismiss;
    private final int REQUEST_PICK = 200;
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICTRUE = 300;
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private final String path =Environment.getExternalStorageDirectory()+ "/image.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        //绑定
        ButterKnife.bind(this);
        iPresenter=new IPresenterImpl(this);
        iPresenter.startRequestGet(Apis.url_user_info,UserBean.class);
        sharedPreferences =getSharedPreferences("new_name",MODE_PRIVATE);
        editor=sharedPreferences.edit();



    }
    @OnClick({R.id.text_personal_name,R.id.text_personal_pass,R.id.image_pic})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.text_personal_name:
                updateName();
                break;
            case R.id.text_personal_pass:
                updatePass();
                break;
            case R.id.image_pic:
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

                break;
        }
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
            Map<String,String> map=new HashMap<>();
            map.put("image",PATH_FILE);
            iPresenter.startRequestPost(Apis.URL_MODIFYHEADPIC,map,AddShoppingBean.class);
        }
    }





    private void updatePass() {
        View view=View.inflate(this,R.layout.custom_update_pass,null);
        editText_pass = view.findViewById(R.id.edit_my_pass);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
      //  pass = sharedPreferences.getString("newPass", null);
       // editText_pass.setText(pass);
        TextView textView=new TextView(this);
        textView.setText("修改密码");
        textView.setPadding(10, 10, 10, 10);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.color.colorYellow);
        textView.setTextColor(Color.WHITE);
        builder.setCustomTitle(textView);
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获得输入框的值
                pass = editText_pass.getText().toString();
                if(TextUtils.isEmpty(pass)){
                    toast("密码不能为空");
                }
                else if(!MeansUtils.isPass(pass)){
                    toast("请输入6-20位的密码");
                }
                else {
                    SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    String oldPass = sharedPreferences.getString("pass", null);

                    Map<String,String> map=new HashMap<>();
                    map.put("oldPwd", oldPass);
                    map.put("newPwd", PersonalActivity.this.pass);

                    iPresenter.startRequestPut(Apis.url_update_pass,map, AddShoppingBean.class);
                  editor.putString("newPass", PersonalActivity.this.pass);
                    editor.commit();

                }


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //修改名字
    private void updateName() {
     View view=View.inflate(this,R.layout.custom_alert,null);
        editText_name = view.findViewById(R.id.edit_my_name);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
       // name = sharedPreferences.getString("newName", null);
       // editText_name.setText(name);
        TextView textView=new TextView(this);
        textView.setText("修改昵称");
        textView.setPadding(10, 10, 10, 10);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.color.colorYellow);
        textView.setTextColor(Color.WHITE);
        builder.setCustomTitle(textView);
        builder.setCancelable(false);
        builder.setView(view);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {
             //获得输入框的值
                name = editText_name.getText().toString();
                if(TextUtils.isEmpty(name)){
                   toast("昵称不能为空");
                }else {
                    Map<String,String> map=new HashMap<>();
                    map.put("nickName", name);
                    iPresenter.startRequestPut(Apis.url_update_name,map, AddShoppingBean.class);
                    editor.putString("newName", name);
                    editor.commit();

                }


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        iPresenter.startRequestGet(Apis.url_user_info,UserBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
             if(data instanceof AddShoppingBean){
                 AddShoppingBean bean= (AddShoppingBean) data;
                 if(bean.getStatus().equals("0000")){
                     toast(bean.getMessage());
                     name = sharedPreferences.getString("newName", null);
                     newPass = sharedPreferences.getString("newPass", null);
                     textView_name.setText(name);
                     textView_pass.setText(newPass);
                 }
             }
             if(data instanceof UserBean){
                 UserBean bean= (UserBean) data;
                 headPic = bean.getResult().getHeadPic();
                 String nickName = bean.getResult().getNickName();
                 String password = bean.getResult().getPassword();
                 simpleDraweeView_image_pic.setImageURI(headPic);
                 textView_name.setText(nickName);
                 textView_pass.setText(password);

             }

    }

    @Override
    public void requestFail(String error) {
      toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();
    }
}
