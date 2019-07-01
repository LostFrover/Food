package ir.mhkz.loginandsignup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Account extends AppCompatActivity {

    String id,name,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent homepage = getIntent();
        id= homepage.getStringExtra("id");
        name= homepage.getStringExtra("name");
        pwd= homepage.getStringExtra("password");
        TextView ID = findViewById(R.id.ID);
        ID.setText("用户编号："+id);
        EditText user,email,fPwd,nPwd,enPwd;
        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        fPwd = findViewById(R.id.formerPwd);
        nPwd = findViewById(R.id.newPwd);
        enPwd = findViewById(R.id.ensureNP);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String serv = "http://203.195.155.114:3389/HealthApp?choise=7&food=";
        HttpGet httpGet = new HttpGet(serv);
        HttpClient httpClient = new DefaultHttpClient();
        //发送请求
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (null == response) {
                Toast.makeText(this, "服务器无响应", Toast.LENGTH_SHORT).show();
            }
            try {
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                if(null!=(result = reader.readLine())) {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageButton back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account.this.finish();
            }
        });

        Button change=findViewById((R.id.changeAccount));
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将同登录页一样，添加判断提示

                AlertDialog ensure = new AlertDialog.Builder(Account.this)
                    .setTitle("请确认")
                    .setMessage("真的要修改用户信息？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Account.this, "已修改，请重新登录", Toast.LENGTH_SHORT).show();
                            //待加入操作内容

                            //
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Account.this, "已取消", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
                ensure.show();
            }
        });
    }
}
