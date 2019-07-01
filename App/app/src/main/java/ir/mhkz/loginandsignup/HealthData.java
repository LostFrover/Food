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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HealthData extends AppCompatActivity {
    String id="";
    String pwd="";
    String name="";
    EditText h,w,bt,bdf,wl,blf,bls;
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

        String serv = "http://203.195.155.114:3389/HealthApp?choise=4&id="+id;
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

                    String blsF,blfF,hF,bdfF,wF,btF,wlF;
                    String []arr=result.split(", '");
                    blsF=arr[5];    blfF=arr[4];    hF=arr[6];      bdfF=arr[7];
                    wF=arr[3];      btF=arr[1];     wlF=arr[0];
                    blsF=blsF.substring(13);
                    blfF=blfF.substring(11);
                    hF=hF.substring(9);
                    bdfF=bdfF.substring(10,bdfF.length()-2);
                    wF=wF.substring(9);
                    btF=btF.substring(7);
                    wlF=wlF.substring(15);

                    h=findViewById(R.id.Height);    w=findViewById((R.id.Weight));
                    bt=findViewById(R.id.Beat);     bdf=findViewById(R.id.BodyFat);
                    wl=findViewById(R.id.Waistline);blf=findViewById(R.id.BloodFat);
                    bls=findViewById(R.id.BloodSugar);
                    h.setText(hF);      w.setText(wF);      bt.setText(btF);
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
                String servC = "http://203.195.155.114:3389/HealthApp?choise=3&id="+id+"&height="+h.getText().toString()+
                        "&weight="+w.getText().toString()+"&waistline="+wl.getText().toString()+"&beat="+bt.getText().toString()+
                        "&bodyFat="+bdf.getText().toString()+"&bloodSugar="+bls.getText().toString()+"&bloodFat="+blf.getText().toString();
                        //http://203.195.155.114:3389/HealthApp?choise=3&id=6&height=150
                        // &weight=44&waistline=4&beat=4
                        // &bodyFat=4&bloodSugar=7&bloodFat=7
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
                        String result = "";
                        if((result = reader.readLine()).equals("succeess")) {
                            Toast.makeText(HealthData.this, "修改成功", Toast.LENGTH_SHORT).show();
                            Intent intentHealthD = new Intent(HealthData.this,HealthData.class);
                            intentHealthD.putExtra("id",id);
                            intentHealthD.putExtra("name",name);
                            intentHealthD.putExtra("pwd",pwd);
                            startActivity(intentHealthD);
                            HealthData.this.finish();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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

    }
}
