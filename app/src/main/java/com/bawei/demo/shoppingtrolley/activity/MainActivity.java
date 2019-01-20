package com.bawei.demo.shoppingtrolley.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.bean.LoginBean;
import com.bawei.demo.shoppingtrolley.home_activity.HomeActivity;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity implements IView{
    @BindView(R.id.text_register)
    TextView textView_register;
    @BindView(R.id.edit_login_phone)
    EditText editText_login_phone;
    @BindView(R.id.edit_login_pass)
    EditText editText_login_pass;
    @BindView(R.id.login_eye)
    ImageView imageView_login_eye;
    @BindView(R.id.checkbox_remember)
    CheckBox checkBox_remember;
   @BindView(R.id.button_login)
    Button button_login;
   private IPresenterImpl iPresenter;
   private SharedPreferences sharedPreferences;
   private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);

        //得到sharedPreferences
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        //得到editor
        editor=sharedPreferences.edit();
        //取出记住密码的状态值进行判断
        boolean c_ischeck = sharedPreferences.getBoolean("c_ischeck", false);
        if(c_ischeck){
            String phone = sharedPreferences.getString("phone", null);
            String pass = sharedPreferences.getString("pass", null);
            editText_login_phone.setText(phone);
            editText_login_pass.setText(pass);
            checkBox_remember.setChecked(true);
        }

    }

    @OnClick({R.id.text_register,R.id.button_login})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.text_register:
                //跳转到注册页面
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.button_login:
                login();

                break;
        }
    }

    //触摸事件
    @OnTouch({R.id.login_eye})
    public boolean onTouch(View view, MotionEvent event){
        switch (view.getId()){
            case R.id.login_eye:
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //显示
                    editText_login_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    //隐藏
                    editText_login_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                break;
        }
        return true;
    }
//登录
    private void login() {
        //获取手机号，密码
        String phone = editText_login_phone.getText().toString();
        String pass = editText_login_pass.getText().toString();
        //存值
        savaData(phone,pass);

        if(phone.equals("")){
            toast("手机号不能为空");

        }else if(!MeansUtils.isPhone(phone)){
            toast("输入的手机格式不对");
        }else if(pass.equals("")){
            toast("密码不能为空");
        }
        else if(!MeansUtils.isPass(pass)){
            toast("请输入6-20位的密码");

        }else {
            Map<String,String> params=new HashMap<>();
            params.put("phone",phone);
            params.put("pwd",pass);
            iPresenter.startRequest(Apis.url_login,params, LoginBean.class);

        }

    }

    private void savaData(String phone,String pass) {
        if(checkBox_remember.isChecked()){
            //将值存入sharedPreferences
            editor.putString("phone",phone);
            editor.putString("pass",pass);
            //存入一个勾选了的状态值
            editor.putBoolean("c_ischeck", true);
            //提交
            editor.commit();
        }else {
            editor.clear();
            editor.commit();
        }
    }

    //吐司
    public void toast(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }
//请求成功
    @Override
    public void requestSuccess(Object data) {
              LoginBean bean= (LoginBean) data;
        if(bean.getStatus().equals("1001")){
            toast(bean.getMessage());
        }
        int userId = bean.getResult().getUserId();
        String sessionId = bean.getResult().getSessionId();
        String nickName = bean.getResult().getNickName();
      //  Log.i("TAG",userId+"-----------"+sessionId);
        //将值存入sharedPreferences
        editor.putString("userId",userId+"");
        editor.putString("sessionId",sessionId);
        editor.putString("nickName",nickName);
        editor.commit();
        if(bean.getStatus().equals("0000")){
                  toast(bean.getMessage());
                  Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                  startActivity(intent);
                  finish();
              }
    }
//请求失败
    @Override
    public void requestFail(String error) {
       // toast(error);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消解绑
        iPresenter.detachView();
    }
}


