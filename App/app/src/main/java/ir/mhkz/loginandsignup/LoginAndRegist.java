package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginAndRegist extends AppCompatActivity {


    private EditText username, password, reg_username, reg_password,
            reg_phone, reg_confirmpassword;
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

        editor = sharedPreferences.edit();

        boolean rem = sharedPreferences.getBoolean("remember_password", false);
        if (rem == true) {
            username.setText(sharedPreferences.getString("Name", ""));
            password.setText(sharedPreferences.getString("Password", ""));
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

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    //This is method for doing operation of check login
    private void ClickLoginBtn() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remname = "", rempassword = "";//记录用户名密码

                if (username.getText().toString().trim().isEmpty()) {
                    uflag = false;
                } else {
                    //记住用户名
                    remname = username.getText().toString();
                }
                if (password.getText().toString().trim().isEmpty()) {
                    pflag = false;
                } else {
                    //记住密码
                    rempassword = password.getText().toString();
                }
                if (rememberMe.isChecked()) {
                    editor.putString("Name", remname).commit();
                    editor.putString("Password", rempassword).commit();
                    editor.putBoolean("remember_password", true).commit();
                } else {
                    editor.clear();
                }
                editor.apply();

                //用户名或密码为空的时候的提示
                if (uflag == false) {
                    Snackbar snackbar = Snackbar.make(view, "请输入用户名",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.wrong));
                    snackbar.show();
                    uflag = true;
                } else if (uflag != false && pflag == false) {
                    Snackbar snackbar = Snackbar.make(view, "请输入密码",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.wrong));
                    snackbar.show();
                    pflag = true;
                }

                //之后post用户名与密码，比对数据库后返回结果

                String serv = "http://203.195.155.114:3389/Login";
                HttpPost httpPost = new HttpPost(serv);
                HttpClient httpClient = new DefaultHttpClient();
                try {
                    List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                    list.add(new BasicNameValuePair("name", remname));
                    list.add(new BasicNameValuePair("pwd",rempassword));
                    httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));// 设置请求参数
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(LoginAndRegist.this, "发送失败", Toast.LENGTH_SHORT).show();
                }
                //发送请求
                try {
                    HttpResponse response = null;
                    response = httpClient.execute(httpPost);
                    if (response == null) {
                        Toast.makeText(LoginAndRegist.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        InputStream inputStream = response.getEntity().getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String result = "用户编号";
                        String line = reader.readLine();
                        if((!line.equals("Failed"))&& (!line.matches("^(<html>)(\\s)*"))) {
                            result+=line;
                            Toast.makeText(LoginAndRegist.this, result, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginAndRegist.this, HomePage.class);
                            intent.putExtra("username",remname);
                            intent.putExtra("pwd",rempassword);
                            intent.putExtra("id",line);
                            startActivity(intent);
                            LoginAndRegist.this.finish();
                            return;
                        }
                        else{
                            Toast.makeText(LoginAndRegist.this, line, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginAndRegist.this,e.getMessage(),  Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    //注册监听器
    private void ClickSignUp() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register, null);
        dialog.setView(dialogView);

        reg_username = dialogView.findViewById(R.id.reg_username);
        reg_password = dialogView.findViewById(R.id.reg_password);
        reg_confirmpassword = dialogView.findViewById(R.id.reg_confirmpassword);
        reg_phone = dialogView.findViewById(R.id.reg_phone);
        reg_register = dialogView.findViewById(R.id.reg_register);
        txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);
        txtInLayoutRegConfirmPassword = dialogView.findViewById(R.id.txtInLayoutRegConfirmPassword);
        final AlertDialog dia = dialog.show();
        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rp=reg_password.getText().toString();
                String em=reg_phone.getText().toString();
                if (reg_phone.getText().toString().trim().isEmpty()) {
                    reg_phone.setError("此行不可留空");
                }else{
                    if(!phonetool.phoneMacth(em)){
                        reg_phone.setError("电话号码格式有误");
                    }
                }
                if (reg_username.getText().toString().trim().isEmpty()) {

                    reg_username.setError("此行不可留空");
                }
                if (reg_password.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                    reg_password.setError("此行不可留空");
                } else {
                    if(rp.length()<8||rp.length()>20)
                    {
                        txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                        reg_password.setError("密码需要8位以上，20位以下");
                    }
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                }
                if (reg_confirmpassword.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegConfirmPassword.setPasswordVisibilityToggleEnabled(false);
                    reg_confirmpassword.setError("此行不可留空");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                    if (!reg_confirmpassword.getText().toString().equals(reg_password.getText().toString())) {
                        reg_confirmpassword.setError("与输入密码不同，请检查");
                    } else {
                        if (    phonetool.phoneMacth(em)&&
                               (rp.length()>7&&rp.length()<21)&&
                              !(reg_username.getText().toString().trim().isEmpty() &&
                                reg_password.getText().toString().trim().isEmpty() &&
                                reg_confirmpassword.getText().toString().trim().isEmpty() &&
                                reg_password.getText().toString().trim().isEmpty())) {
                            String name = reg_username.getText().toString();
                            String pwd = reg_password.getText().toString();
                            String phone = reg_phone.getText().toString();
                            //向服务器请求注册，并发送用户名，邮箱，密码
                            //用户编号自动+=1
                            String serv = "http://203.195.155.114:3389/Reg";
                            HttpPost httpPost = new HttpPost(serv);
                            HttpClient httpClient = new DefaultHttpClient();
                            //发送请求
                            try {
                                try {
                                    List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                                    list.add(new BasicNameValuePair("name", name));
                                    list.add(new BasicNameValuePair("pwd",pwd));
                                    list.add(new BasicNameValuePair("phone",phone));
                                    httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));// 设置请求参数
                                } catch (UnsupportedEncodingException e1) {
                                    Toast.makeText(LoginAndRegist.this, "发送失败", Toast.LENGTH_SHORT).show();
                                }
                                HttpResponse response = httpClient.execute(httpPost);
                                if (null == response) {
                                    Toast.makeText(LoginAndRegist.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                                    return;

                                }
                                try {
                                    InputStream inputStream = response.getEntity().getContent();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                    String result = "";
                                    result = reader.readLine();
                                    if( (result.equals("failed")) && (result.matches("^(<html>)(\\s)*") ) ) {
                                        Toast.makeText(LoginAndRegist.this, "注册失败", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else{
                                        Toast.makeText(LoginAndRegist.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        dia.dismiss();
                                        Intent ffd = new Intent(LoginAndRegist.this,FirstForData.class);
                                        ffd.putExtra("name",name);
                                        ffd.putExtra("pwd",pwd);
                                        startActivity(ffd);
                                        LoginAndRegist.this.finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(LoginAndRegist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    public static  class phonetool{
        public static  boolean phoneMacth(String phone)
        {
            String regex = "^1[1-9][0-9]{9}$";
            return phone.matches(regex);
        }
    }

    public static class StreamTools {
        //工具类，将输入流转化为字符串并返回
        public static String readStream(InputStream inputStream) throws Exception {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            inputStream.close();
            return byteArrayOutputStream.toString();
        }
    }


}