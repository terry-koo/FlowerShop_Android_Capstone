package com.example.android_project_capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static java.lang.Thread.sleep;

public class ZMainProduct extends AppCompatActivity {

    private ListView mainList = null;
    private ZMainProductAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_main_product);
        mainList = (ListView) findViewById(R.id.mainProductListView);


        //리스트뷰 불러오기
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZProductManager.updateList();
                adapter = new ZMainProductAdapter(ZProductManager.getzProductList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainList.setAdapter(adapter);
                    }
                });

            }
        }).start();

        //리스트뷰 이벤트 등록
        mainList.setOnItemClickListener(goProductDetailListener);


    }

    //리스트뷰 이벤트 생성
    private AdapterView.OnItemClickListener goProductDetailListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //ZProductDetail로 이동. position 값을 intent에서 등록
            Intent intent = new Intent(getApplicationContext(), ZProductDetail.class);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    };


}