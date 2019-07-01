package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent homepage = getIntent();
        String result = homepage.getStringExtra("result");
        if(!result.equals("il")) {
            String[] rstarr = result.split("[}], [{]");
            for (int i = 0; i < rstarr.length; i++) {
                String[] temp = rstarr[i].split(", ", 2);
                String heat = temp[1].substring(9, temp[1].length() - 1);
                String name = temp[0].substring(9, temp[0].length() - 1);
                rstarr[i] = name + "\n" + heat;
            }
            ListView lv = findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_item_style, rstarr);//新建并配置ArrayAapeter
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                        default:
                            Toast.makeText(SearchResult.this, "(测试用功能)你点击了" + i + "项", Toast.LENGTH_SHORT).show();
                            //二次查询
                            String serv = "http://203.195.155.114:3389/HealthApp?choise=7&food=";
                            HttpGet httpGet = new HttpGet(serv);
                            HttpClient httpClient = new DefaultHttpClient();
                            //发送请求
                            try {
                                HttpResponse response = httpClient.execute(httpGet);
                                try {
                                    InputStream inputStream = response.getEntity().getContent();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                    String result = "";
                                    if(null!=(result = reader.readLine())) {
                                        result = result.substring(2,result.length()-2);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;//当我们点击某一项就能吐司我们点了哪一项
                    }
                }
            });
        }
        else{
            Toast.makeText(SearchResult.this,"无结果",Toast.LENGTH_SHORT).show();
        }

        ImageButton back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResult.this.finish();
            }
        });
    }
}
