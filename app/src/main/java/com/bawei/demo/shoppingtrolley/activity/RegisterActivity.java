package com.bawei.demo.shoppingtrolley.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.bean.LoginBean;
import com.bawei.demo.shoppingtrolley.bean.RegisterBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class RegisterActivity extends AppCompatActivity implements IView{
    //获取资源ID
    @BindView(R.id.edit_register_phone)
    EditText editText_register_phone;
    @BindView(R.id.edit_register_code)
    EditText getEditText_register_code;
    @BindView(R.id.get_code)
    TextView textView_get_code;
    @BindView(R.id.edit_register_pass)
    EditText editText_register_pass;
    @BindView(R.id.register__eye)
    ImageView imageView_register_eye;
    @BindView(R.id.get_login)
    TextView textView_get_login;
    @BindView(R.id.button_register)
    Button button_register;
    private String random;
    private IPresenterImpl iPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
    }
    //点击事件
    @OnClick({R.id.edit_register_pass,R.id.button_register,R.id.get_code,R.id.get_login})
    public void viewSetClick(View view){
        switch (view.getId()){
            case R.id.button_register:
             register();
              break;
            case R.id.get_code:
               getCode();
                break;
            case R.id.get_login:
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    //触摸事件
    @OnTouch({R.id.register__eye})
    public boolean onTouch(View view, MotionEvent event){
        switch (view.getId()){
            case R.id.register__eye:
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                editText_register_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                editText_register_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            break;
        }
        return true;
    }
    //注册
    public void register(){
        String phone = editText_register_phone.getText().toString();
        String pass = editText_register_pass.getText().toString();
        String code = getEditText_register_code.getText().toString();
        //判断手机号，密码，验证码
        if(phone.equals("")){
            toast("手机号不能为空");

        }else if(!MeansUtils.isPhone(phone)){
            toast("输入的手机格式不对");
        }else if(code.equals("")){
            toast("验证码不能为空");
        }
        else if(!code.equals(random)){
            toast("验证码填写错误");

        }else if(pass.equals("")){
            toast("密码不能为空");
        }
        else if(!MeansUtils.isPass(pass)){
            toast("请输入6-20位的密码");

        }
        else {
            Map<String,String> params=new HashMap<>();
            params.put("phone",phone);
            params.put("pwd",pass);
            iPresenter.startRequest(Apis.url_register,params, RegisterBean.class);//请求网络
        }
    }
    //获取验证码
    public void getCode(){
        random = MeansUtils.getRandom();
       toast(random);
    }

   //请求成功
    @Override
    public void requestSuccess(Object data) {
       RegisterBean bean= (RegisterBean) data;
       if(bean.getStatus().equals("0000")){
           toast(bean.getMessage());
           finish();
       }else {
           toast(bean.getMessage());
       }

    }
    //吐司
    public void toast(String message){
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
    }
  //请求失败
    @Override
    public void requestFail(String error) {
        toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消解绑
        iPresenter.detachView();
    }
}
