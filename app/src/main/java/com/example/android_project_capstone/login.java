package com.example.android_project_capstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {
    EditText idText,passwordText;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText)findViewById(R.id.passwordText);


        //로그인
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = idText.getText().toString();
                final String password = passwordText.getText().toString();

                //아이디 또는 비밀번호를 입력하지 않았을때
                if (id.equals("") || password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                    dialog = builder.setMessage("아이디와 비밀번호를 입력해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //
                //스레드사용
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String dataType = "login";
                        try{
                            String rst = new Task(dataType).execute(dataType,id,password).get();
                            Log.d("결과 :",rst);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        //
        //회원가입
        TextView registerText = (TextView)findViewById(R.id.registerButton);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 클릭시
                Toast.makeText(getApplicationContext(), "회원가입", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), joinAsMember.class);
                startActivity(intent);
            }
        });



    }
}