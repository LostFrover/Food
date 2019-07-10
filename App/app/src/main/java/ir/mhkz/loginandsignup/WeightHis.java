package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WeightHis extends AppCompatActivity {

    private ImageButton back;
    private String ID;
    int sa = 4;     //标准高度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_his);

        Intent homepage = getIntent();
        ID = homepage.getStringExtra("id");

        TextView[]TT = new TextView[10];
        TT[0] = findViewById(R.id.t1);TT[1] = findViewById(R.id.t2);TT[2] = findViewById(R.id.t3);
        TT[3] = findViewById(R.id.t4);TT[4] = findViewById(R.id.t5);TT[5] = findViewById(R.id.t6);
        TT[6] = findViewById(R.id.t7);TT[7] = findViewById(R.id.t8);TT[8] = findViewById(R.id.t9);TT[9] = findViewById(R.id.t10);
        ImageView[]II = new ImageView[10];
        II[0] = findViewById(R.id.i1);II[1] = findViewById(R.id.i2);II[2] = findViewById(R.id.i3);
        II[3] = findViewById(R.id.i4);II[4] = findViewById(R.id.i5);II[5] = findViewById(R.id.i6);
        II[6] = findViewById(R.id.i7);II[7] = findViewById(R.id.i8);II[8] = findViewById(R.id.i9);II[9] = findViewById(R.id.i10);


        //让主线程可以访问Internet
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String serv = "http://203.195.155.114:3389/weightget?id="+ID;
        HttpGet httpGet = new HttpGet(serv);
        HttpClient httpClient = new DefaultHttpClient();

        String [][]rstarr = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
        //发送请求
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (null == response) {
                Toast.makeText(WeightHis.this, "服务器无响应", Toast.LENGTH_SHORT).show();
            }
            try {
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                result = reader.readLine();
                if(!result.equals("()")) {
                    result = result.replace("datetime.date(","'");
                    result = result.replace(")","'");
                    JSONArray ja = new JSONArray(result);
                    int j=ja.length();
                    for(int i=0;i<j&&i<10;i++)
                    {
                        JSONObject jo = ja.getJSONObject(i);
                        rstarr[i][0] = jo.getString("weight");
                        rstarr[i][1] = jo.getString("date");
                    }

                    TextView []his = {findViewById(R.id.his1),findViewById(R.id.his2),findViewById(R.id.his3)} ;
                    for(int i=0;i<j&&i<3;i++)
                    {
                        JSONObject jo = ja.getJSONObject(i);
                        String date = jo.getString("date");
                        date = date.replace(" ","");
                        date = date.replace(",",".");
                        String weight = jo.getString("weight");
                        String pri = date+"："+weight+"kg";
                        his[i].setText(pri);
                    }

                    for(int i=0;i<10;i++)
                    {
                        if(rstarr[i][1].equals(""))
                            rstarr[i][1]="空";
                        rstarr[i][1]=rstarr[i][1].replace(" ","");
                        rstarr[i][1]=rstarr[i][1].replace(",",".");
                        if(!rstarr[i][1].equals("空"))
                            rstarr[i][1]=rstarr[i][1].substring(0,4)+"\n"+rstarr[i][1].substring(5);
                        if(rstarr[i][0].equals(""))
                            rstarr[i][0]="40.0";
                    }
                    for(int i=0;i<10;i++)
                    {
                        TT[i].setText(rstarr[9-i][1]);
                        int d =0;
                        if(!rstarr[9-i][1].isEmpty())
                        {
                            if(Integer.valueOf(rstarr[9-i][0].substring(0,rstarr[9-i][0].length()-2))>40)
                            {
                                d = Integer.valueOf(rstarr[9-i][0].substring(0,rstarr[9-i][0].length()-2));
                                d=(d-40)*4+10;
                            }else
                                d=1;
                        }
                        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,d);
                        //II[i].setLayoutParams(params);
                        //II[i].setBackgroundColor(Color.parseColor("#FF33B5E5"));


                            Bitmap bitmap = ((BitmapDrawable) II[i].getBackground()).getBitmap();
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            // 计算缩放比例.
                            float scaleHeight = (float) d /10;
                            // 取得想要缩放的matrix参数.
                            Matrix matrix = new Matrix();
                            matrix.postScale(1, scaleHeight);
                            // 得到新的图片.
                            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

                            II[i].setImageBitmap(newBitmap);

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightHis.this.finish();
            }
        });



    }
}
