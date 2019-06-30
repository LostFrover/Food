package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        String[] rstarr=result.split("[}], [{]");
        for(int i=0;i<rstarr.length;i++)
        {
            String []temp=rstarr[i].split(", ",2);
            String heat = temp[0].substring(9,temp[0].length()-1);
            String name = temp[1].substring(9,temp[1].length()-1);
            rstarr[i]=name+"\n"+heat;
        }
        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listview_item_style,rstarr);//新建并配置ArrayAapeter
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:default:
                        Toast.makeText(SearchResult.this,"(测试用功能)你点击了"+i+"项",Toast.LENGTH_SHORT).show();
                        break;//当我们点击某一项就能吐司我们点了哪一项
                }
            }
        });

    }
}
