package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HealthData extends AppCompatActivity {
    String id="";
    String pwd="";
    String name="";
    EditText h,w,bt,bdf,wl,blf,bls,sex;
    TextView ID,Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data);

        Intent homepage = getIntent();
        id= homepage.getStringExtra("id");
        name= homepage.getStringExtra("name");
        pwd= homepage.getStringExtra("password");
        ID=findViewById(R.id.ID);
        ID.setText(id);
        Name=findViewById(R.id.userName);
        Name.setText(name);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String serv = "http://203.195.155.114:3389/HealthDataGet?id="+id;
        HttpGet httpGet = new HttpGet(serv);
        HttpClient httpClient = new DefaultHttpClient();
        //发送请求
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (null == response) {
                Toast.makeText(HealthData.this, "服务器无响应", Toast.LENGTH_SHORT).show();
            }
            try {
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                if(null!=(result = reader.readLine())) {
                    JSONObject jo = new JSONObject(result.substring(1,result.length()-1));

                    String blsF,blfF,hF,bdfF,wF,btF,wlF,sexF;

                    blsF=jo.getString("bloodSugar");
                    blfF=jo.getString("bloodFat");
                    hF=jo.getString("height");
                    bdfF=jo.getString("bodyFat");
                    wF=jo.getString("weight");
                    btF=jo.getString("beat");
                    wlF=jo.getString("waistline");
                    sexF=jo.getString("sex");

                    h=findViewById(R.id.Height);    w=findViewById((R.id.Weight));
                    bt=findViewById(R.id.Beat);     bdf=findViewById(R.id.BodyFat);
                    wl=findViewById(R.id.Waistline);blf=findViewById(R.id.BloodFat);
                    bls=findViewById(R.id.BloodSugar);  sex=findViewById(R.id.sex);
                    h.setText(hF);      w.setText(wF);      bt.setText(btF);    sex.setText(sexF);
                    bdf.setText(bdfF);  wl.setText(wlF);    blf.setText(blfF);  bls.setText(blsF);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Button change=findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HealthData.this, "点击按钮", Toast.LENGTH_SHORT).show();
                String servC = "http://203.195.155.114:3389/HealthDtaPost?id="+id+"&sex="+sex.getText().toString()+"&height="+h.getText().toString()+
                        "&weight="+w.getText().toString()+"&waistline="+wl.getText().toString()+"&beat="+bt.getText().toString()+
                        "&bodyFat="+bdf.getText().toString()+"&bloodSugar="+bls.getText().toString()+"&bloodFat="+blf.getText().toString();

                HttpGet httpGet = new HttpGet(servC);
                HttpClient httpClient = new DefaultHttpClient();
                //发送请求
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    if (null == response) {
                        Toast.makeText(HealthData.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        InputStream inputStream = response.getEntity().getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String result = reader.readLine();
                        if(result.equals("succeess")) {
                            Toast.makeText(HealthData.this, "修改成功", Toast.LENGTH_SHORT).show();
                            Intent intentHealthD = new Intent(HealthData.this,HealthData.class);
                            intentHealthD.putExtra("id",id);
                            intentHealthD.putExtra("name",name);
                            intentHealthD.putExtra("pwd",pwd);
                            startActivity(intentHealthD);
                            HealthData.this.finish();
                        }else
                            Toast.makeText(HealthData.this, "修改失败", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HealthData.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthData.this.finish();
            }
        });

        //dataAnaly();

    }
    //这里将添加档案分析函数
    private void dataAnaly()
    {
        String report="";
        double hd,wd,wld,blsd,blfd;
        hd = Double.valueOf(h.getText().toString());
        wd = Double.valueOf(w.getText().toString());
        wld = Double.valueOf(wl.getText().toString());
        blsd = Double.valueOf(bls.getText().toString());
        blfd = Double.valueOf(blf.getText().toString());
        int btd,bdfd,sexd;
        btd = Integer.valueOf(bt.getText().toString());
        bdfd = Integer.valueOf(bdf.getText().toString());
        if(sex.getText().toString().equals("男"))
            sexd=1;
        else if(sex.getText().toString().equals("女"))
            sexd=0;
        else
            sexd=-1;
        //身高体重判断
        int BMI;
        double standerdW;
        switch (sexd) {
            case 1://男
                standerdW = (hd-80)*0.7;
                if(standerdW*0.9<wd&&wd<standerdW*1.1)
                    BMI=0;
                else if(standerdW*0.8<wd&&wd<standerdW*0.9)
                        BMI=-1;
                else if(wd<standerdW*0.8)
                    BMI=-2;
                else if(standerdW*1.1<wd&&wd<standerdW*1.2)
                    BMI=1;
                else if(wd<standerdW*1.2)
                    BMI=2;
                break;
            case 0://女
                standerdW = (hd-70)*0.6;
                if(standerdW*0.9<wd&&wd<standerdW*1.1)
                    BMI=0;
                else if(standerdW*0.8<wd&&wd<standerdW*0.9)
                    BMI=-1;
                else if(wd<standerdW*0.8)
                    BMI=-2;
                else if(standerdW*1.1<wd&&wd<standerdW*1.2)
                    BMI=1;
                else if(wd<standerdW*1.2)
                    BMI=2;
                break;
            case -1://未知
            default:
                standerdW = wd/hd/hd;
                if(18.5<standerdW&&standerdW<24)
                    BMI=0;
                else if(14.5<standerdW&&standerdW<18.5)
                    BMI=-1;
                else if(standerdW<14.5)
                    BMI=-2;
                else if(24<standerdW&&standerdW<28)
                    BMI=1;
                else if(28<standerdW)
                    BMI=2;
                break;
        }

        //体脂腰围

        //血糖血脂判断

        //脉搏

        //
    }
}
