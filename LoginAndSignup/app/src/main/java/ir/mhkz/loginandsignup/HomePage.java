package ir.mhkz.loginandsignup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;
    private static int REQ_1 = 1;
    ImageView mImageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageButton cameraBtn,homeDtlBtn;
        cameraBtn = findViewById(R.id.cameraBtn);
        homeDtlBtn = findViewById(R.id.homeDtlBtn);


        mImageView = findViewById(R.id.imageView);
        //搜索框
        searchView = (SearchView)findViewById(R.id.searchView);
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


        cameraBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用系统camera
                startActivityForResult(intent,REQ_1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQ_1){
                Bundle bundle = data.getExtras();//获得图片的二进制流
                Bitmap bitmap = (Bitmap)bundle.get("data");
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

}
