package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import java.sql.*;

public class MainActivity extends AppCompatActivity {


    EditText username, password, reg_username, reg_password,
             reg_email, reg_confirmpassword;
    Button login, signUp, reg_register;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword, txtInLayoutRegConfirmPassword;
    CheckBox rememberMe;

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

                if (username.getText().toString().trim().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(view, "输入有误",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    txtInLayoutUsername.setError("用户名不可为空");
                } else {
                    //the codes for checking username
                }
                if (password.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "输入有误",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    txtInLayoutPassword.setError("密码不可为空");
                } else {
                    //the codes for checking password
                }

                if (rememberMe.isChecked()) {
                    //记住密码功能，待补全
                    //the codes if box is checked

                } else {
                    //if box is not checked
                }
                //之后post用户名与密码，比对数据库后返回结果

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

    //The method for opening the registration page and another processes or checks for registering
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
                    }
                }

                if (!(reg_username.getText().toString().trim().isEmpty() &&
                        reg_password.getText().toString().trim().isEmpty() &&
                        reg_confirmpassword.getText().toString().trim().isEmpty() &&
                        reg_email.getText().toString().trim().isEmpty()))
                {
                    //向服务器请求注册，并发送用户名，邮箱，密码
                    //用户编号自动+=1
                    dia.dismiss();
                }
            }
        });



    }


}
