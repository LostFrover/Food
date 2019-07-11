package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//刘涛

public class FirstForData extends AppCompatActivity {

    private String name,pwd,id="未知";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_for_data);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent log = getIntent();
        name = log.getStringExtra("name");
        pwd = log.getStringExtra("pwd");
        String serv = "http://203.195.155.114:3389/Login";
        HttpPost httpPost = new HttpPost(serv);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            list.add(new BasicNameValuePair("name", name));
            list.add(new BasicNameValuePair("pwd",pwd));
            httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));// 设置请求参数
        } catch (UnsupportedEncodingException e1) {
            Toast.makeText(FirstForData.this, "发送失败", Toast.LENGTH_SHORT).show();
        }
        //发送请求
        try {
            HttpResponse response = null;
            response = httpClient.execute(httpPost);
            if (response == null) {
                Toast.makeText(FirstForData.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = "用户编号";
                String line = reader.readLine();
                if((!line.equals("Failed"))&& (!line.matches("^(<html>)(\\s)*"))) {
                    result+=line;
                    Toast.makeText(FirstForData.this, result, Toast.LENGTH_SHORT).show();
                    id = line;
                }
                else{
                    Toast.makeText(FirstForData.this, line, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FirstForData.this,e.getMessage(),  Toast.LENGTH_SHORT).show();
        }

        Button change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String blsF,blfF,hF,bdfF,wF,btF,wlF,sexF = null,ageF,twF;
                EditText h,w,bt,bdf,wl,blf,bls,age,tw;
                h=findViewById(R.id.h);       w=findViewById((R.id.w));
                bt=findViewById(R.id.bt);     bdf=findViewById(R.id.bdf);
                wl=findViewById(R.id.wl);     blf=findViewById(R.id.blf);
                bls=findViewById(R.id.bls);   age=findViewById(R.id.age);   tw=findViewById(R.id.tw);
                hF=h.getText().toString();      wF=w.getText().toString();
                btF=bt.getText().toString();    bdfF=bdf.getText().toString();
                wlF=wl.getText().toString();    blfF=blf.getText().toString();
                blsF=bls.getText().toString();  ageF=age.getText().toString();  twF=tw.getText().toString();
                RadioButton F,M;
                F=findViewById(R.id.F);     M=findViewById(R.id.M);
                if(F.isChecked())
                    sexF="女";
                else if(M.isChecked())
                    sexF="男";
                
                String servC = "http://203.195.155.114:3389/HealthDataPost?id="+id+"&sex="+sexF+"&height="+hF+
                            "&weight="+wF+"&waistline="+wlF+"&beat="+btF+"&bodyFat="+bdfF+"&bloodSugar="+blsF+
                            "&bloodFat="+blfF+"&age="+ageF+"&targetWeight="+twF;

                HttpGet httpGet = new HttpGet(servC);
                HttpClient httpClient = new DefaultHttpClient();
                //发送请求
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    if (null == response) {
                        Toast.makeText(FirstForData.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        InputStream inputStream = response.getEntity().getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String result = reader.readLine();
                        if(result.equals("succeess")) {
                            Toast.makeText(FirstForData.this, "录入成功", Toast.LENGTH_SHORT).show();
                            Intent homepage = new Intent(FirstForData.this,HomePage.class);
                            homepage.putExtra("id",id);
                            homepage.putExtra("username",name);
                            homepage.putExtra("pwd",pwd);
                            startActivity(homepage);
                            FirstForData.this.finish();
                        }else
                            Toast.makeText(FirstForData.this, "录入失败", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(FirstForData.this, "录入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
