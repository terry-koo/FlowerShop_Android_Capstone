package com.example.android_project_capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class databaseTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        Button button1 = (Button) findViewById(R.id.button) ;
        Button button2 = (Button) findViewById(R.id.button2) ;

        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataType = "type1";
                try{
                    String rst = new Task(dataType).execute(dataType,"첫번째 값","두번째 값").get();
                    Log.d("결과 :",rst);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ZProductManager.updateList();
                        String a = ZProductManager.getzProductList().get(0).getTitle();
                        String b = ZProductManager.getzProductList().get(0).getContent();
                        Log.v("zTag", a+" "+b);
                    }
                }).start();


            }
        });




    }
}