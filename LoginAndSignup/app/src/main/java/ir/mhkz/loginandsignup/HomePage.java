package ir.mhkz.loginandsignup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


public class HomePage extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageButton cameraBtn,homeDtlBtn,searchBtn;
        cameraBtn = findViewById(R.id.cameraBtn);
        homeDtlBtn = findViewById(R.id.homeDtlBtn);

        //搜索框
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.listView);


        //listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mString));

        searchView.setSubmitButtonEnabled(true);
        //为SearchView设置监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //单击搜索是激发该方法
            public boolean onQueryTextSubmit(String newtext) {
                //todo


                return false;
            }
            // 用户输入文字激发该方法
            public boolean onQueryTextChange(String newtext) {
                //todo


                return false;
            }
        });


    }

}
