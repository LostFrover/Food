package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;

public class MainActivity extends AppCompatActivity {


    private EditText username, password, reg_username, reg_password,
             reg_email, reg_confirmpassword;
    private Button login, signUp, reg_register;
    private TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword, txtInLayoutRegConfirmPassword;
    private CheckBox rememberMe;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //用于记住密码

    private boolean uflag = true;
    private boolean pflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        rememberMe = findViewById(R.id.rememberMe);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        editor=sharedPreferences.edit();

        boolean rem = sharedPreferences.getBoolean("remember_password",false);
        if (rem==true)
        {
            username.setText(sharedPreferences.getString("Name",""));
            password.setText(sharedPreferences.getString("Password",""));
            rememberMe.setChecked(true);
        }
        //读取存储用户名与密码以及记住选项

        ClickLoginBtn();

        //SignUp's Button for showing registration page
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });

    }

    //This is method for doing operation of check login
    private void ClickLoginBtn() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remname="",rempassword="";//记录用户名密码

                if (username.getText().toString().trim().isEmpty()) {
                    uflag = false;
                } else {
                    //记住用户名
                    remname=username.getText().toString();
                }
                if (password.getText().toString().trim().isEmpty()) {
                    pflag = false;
                } else {
                    //记住密码
                    rempassword=password.getText().toString();
                }
                if(rememberMe.isChecked())
                {
                    editor.putString("Name",remname).commit();
                    editor.putString("Password",rempassword).commit();
                    editor.putBoolean("remember_password",true).commit();
                }
                else {
                    editor.clear();
                }
                editor.apply();

                //用户名或密码为空的时候的提示
                if(uflag == false ){
                    Snackbar snackbar = Snackbar.make(view, "请输入用户名",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.wrong));
                    snackbar.show();
                    uflag = true;
                }
                else if(uflag != false&&pflag == false){
                    Snackbar snackbar = Snackbar.make(view, "请输入密码",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.wrong));
                    snackbar.show();
                    pflag = true;
                }

                //之后post用户名与密码，比对数据库后返回结果
                //服务器访问接口  ip:端口(3389)?内容

                String serv="https://203.195.155.114:3389/reg?choise=1&name="+remname+"&pwd="+rempassword;
                try {
                    URL url = new URL(serv);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    if (code == 200) {
//                                    InputStream is = conn.getInputStream();
//                                    String info = StreamTools.readInputStream(is);
//                                    return info;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(username.getText().toString().equals("aaa")&&
                    password.getText().toString().equals("aaa"))
                {
                    //临时的主页判断
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }

        });
    }

    //注册监听器
    private void ClickSignUp() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.register, null);
        dialog.setView(dialogView);

        reg_username = dialogView.findViewById(R.id.reg_username);
        reg_password = dialogView.findViewById(R.id.reg_password);
        reg_confirmpassword = dialogView.findViewById(R.id.reg_confirmpassword);
        reg_email = dialogView.findViewById(R.id.reg_email);
        reg_register = dialogView.findViewById(R.id.reg_register);
        txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);
        txtInLayoutRegConfirmPassword = dialogView.findViewById(R.id.txtInLayoutRegConfirmPassword);
        final AlertDialog dia = dialog.show();
        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reg_email.getText().toString().trim().isEmpty()) {

                    reg_email.setError("此行不可留空");
                }
                if (reg_username.getText().toString().trim().isEmpty()) {

                    reg_username.setError("此行不可留空");
                }
                if (reg_password.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                    reg_password.setError("此行不可留空");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                }
                if (reg_confirmpassword.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegConfirmPassword.setPasswordVisibilityToggleEnabled(false);
                    reg_confirmpassword.setError("此行不可留空");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                    if (!reg_confirmpassword.getText().toString().equals(reg_password.getText().toString())) {
                        reg_confirmpassword.setError("与输入密码不同，请检查");
                    }else
                    {
                        if (!(reg_username.getText().toString().trim().isEmpty() &&
                                reg_password.getText().toString().trim().isEmpty() &&
                                reg_confirmpassword.getText().toString().trim().isEmpty() &&
                                reg_email.getText().toString().trim().isEmpty()))
                        {
                            String name=reg_username.getText().toString();
                            String pwd=reg_password.getText().toString();
                            String email=reg_email.getText().toString();
                            //向服务器请求注册，并发送用户名，邮箱，密码
                            //用户编号自动+=1
                            String serv="https://203.195.155.114:3389/healthapp?choise=0&name="+name+"&pwd="+pwd+"&email="+email;
                            try {
                                URL url = new URL(serv);
                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                conn.setConnectTimeout(5000);
                                conn.setRequestMethod("GET");
                                int code = conn.getResponseCode();
                                if (code == 200) {
//                                    InputStream is = conn.getInputStream();
//                                    String info = StreamTools.readInputStream(is);
//                                    return info;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dia.dismiss();
                        }
                    }
                }
            }
        });

    }


}
