package com.example.android_project_capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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


        new Thread(new Runnable() {
            @Override
            public void run() {
                ZProductManager.updateList();
                while(ZProductManager.getzProductList().size() == 0) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new ZMainProductAdapter(ZProductManager.getzProductList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainList.setAdapter(adapter);
                    }
                });

            }
        }).start();

    }


}