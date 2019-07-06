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
    EditText h,w,bt,bdf,wl,blf,bls,sex,age,tw;
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

                    String blsF="",blfF="",hF="",bdfF="",wF="",btF="",wlF="",sexF="",ageF="",twF="";

                    blsF=jo.getString("bloodSugar");
                    blfF=jo.getString("bloodFat");
                    hF=jo.getString("height");
                    bdfF=jo.getString("bodyFat");
                    wF=jo.getString("weight");
                    btF=jo.getString("beat");
                    wlF=jo.getString("waistline");
                    sexF=jo.getString("sex");
                    ageF=jo.getString("age");
                    twF=jo.getString("targetWeight");

                    h=findViewById(R.id.Height);    w=findViewById((R.id.Weight));
                    bt=findViewById(R.id.Beat);     bdf=findViewById(R.id.BodyFat);
                    wl=findViewById(R.id.Waistline);blf=findViewById(R.id.BloodFat);
                    bls=findViewById(R.id.BloodSugar);  sex=findViewById(R.id.sex);
                    age=findViewById(R.id.age);      tw=findViewById(R.id.targetWeight);
                    h.setText(hF);      w.setText(wF);      bt.setText(btF);    sex.setText(sexF);
                    bdf.setText(bdfF);  wl.setText(wlF);    blf.setText(blfF);  bls.setText(blsF);
                    age.setText(ageF);  tw.setText(twF);

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
                //Toast.makeText(HealthData.this, "点击按钮", Toast.LENGTH_SHORT).show();
                String servC = "http://203.195.155.114:3389/HealthDataPost?id="+id+"&sex="+sex.getText().toString()+"&height="+h.getText().toString()+
                        "&weight="+w.getText().toString()+"&waistline="+wl.getText().toString()+"&beat="+bt.getText().toString()+
                        "&bodyFat="+bdf.getText().toString()+"&bloodSugar="+bls.getText().toString()+"&bloodFat="+blf.getText().toString()+
                        "&age="+age.getText().toString()+"&targetWeight="+tw.getText().toString();

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

        if(     h.getText().toString().isEmpty()  &&
                w.getText().toString().isEmpty()  &&
                sex.getText().toString().isEmpty()&&
                age.getText().toString().isEmpty()
        ){}
            else
        dataAnaly();

    }
    //这里将添加档案分析函数
    private void dataAnaly()
    {
        String report="";
        double hd,wd;
        hd = Double.valueOf(h.getText().toString());
        wd = Double.valueOf(w.getText().toString());
        int sexd,aged;
        aged = Integer.valueOf(age.getText().toString());
        if(sex.getText().toString().equals("男"))
            sexd=1;
        else if(sex.getText().toString().equals("女"))
            sexd=0;
        else
            sexd=-1;
        //身高体重判断
        int BMI=0;
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
        String adv="",adv1="",adv2="";
        switch (BMI)
        {
            case -2:
                adv1="体型瘦弱，建议增重并加强锻炼。\n";
                break;
            case -1:
                adv1="体型偏轻，建议适当增重与锻炼。\n";
                break;
            case 0:
                adv1="体型适中，请继续保持。\n";
                break;
            case 1:
                adv1="体型偏重，建议适当减重与锻炼。\n";
                break;
            case 2:
                adv1="体型偏轻，建议加强锻炼，积极减肥。\n";
                break;
            default:
                adv1="体型分析失误，请见谅。\n";
                break;
        }
        double KKK;
        if (sexd==1)
        {
            KKK=66+(13.7*wd)+(5*hd)-(6.8*aged);
            KKK=KKK*1.3;
            adv2 = "每日所需热量为"+KKK+"大卡，请根据自身情况适当增减";
        }
        else if(sexd==0)
        {
            KKK=655+(9.6*wd)+(1.8*hd)-(4.7*aged);
            KKK=KKK*1.3;
            adv2 = "每日所需热量为"+KKK+"大卡，请根据自身情况适当增减";
        }
        else{
            adv2="每日热量分析失误，请见谅。";
        }
        adv = adv1+adv2;
        TextView A = findViewById(R.id.advice);
        A.setText(adv);

        //体脂腰围
        //血糖血脂判断
        //脉搏
    }
}
