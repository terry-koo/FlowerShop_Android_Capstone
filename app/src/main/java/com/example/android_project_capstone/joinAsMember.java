package com.example.android_project_capstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;


public class joinAsMember extends AppCompatActivity {
    private EditText join_email, join_password, join_name, join_pwck, join_address_sub, join_phone;
    private Button join_button, delete_button;
    private AlertDialog dialog;
    String rst;
    public boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_as_member);


        //아이디값 찾아주기
        join_email = findViewById( R.id.join_email );
        join_password = findViewById( R.id.join_password );
        join_name = findViewById( R.id.join_name );
        join_pwck = findViewById(R.id.join_pwck);
        join_address_sub = findViewById(R.id.join_address_sub);
        join_phone = findViewById(R.id.join_phone);




        //회원가입 버튼 클릭 시 수행
        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB결과에 따른 버튼 동기화
                if(click) {
                click = false;

                final String UserEmail = join_email.getText().toString();
                final String UserPwd = join_password.getText().toString();
                final String UserName = join_name.getText().toString();
                final String PassCk = join_pwck.getText().toString();
                final String UserAddress = join_address_sub.getText().toString();
                final String UserPhone = join_phone.getText().toString();


                //한 칸이라도 입력 안했을 경우
                if (UserEmail.equals("") || UserPwd.equals("") || UserName.equals("") || UserAddress.equals("") || UserPhone.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(joinAsMember.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    click = true;
                    return;
                }

                if (!UserPwd.equals(PassCk)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(joinAsMember.this);
                    dialog = builder.setMessage("비밀번호가 다릅니다").setNegativeButton("확인", null).create();
                    dialog.show();
                    click = true;
                    return;
                }

                //스레드사용
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String dataType = "register";
                        try {
                            rst = new Task(dataType).execute(dataType, UserEmail, UserPwd, UserName, UserAddress, UserPhone).get();
                            Log.d("결과 :", rst);

                            switch (rst) {
                                //로그인 성공시
                                case "REGISTER_SUCCESS":
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    click = true;
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                    break;
                                //로그인 실패시
                                case "REGISTER_FAIL":
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                                            click = true;
                                        }
                                    });
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


                }
            }
        });


        //취소버튼 클릭시
        delete_button = findViewById( R.id.delete );
        delete_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "회원가입 취소", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(joinAsMember.this, login.class);
                startActivity(intent);

            }
        });














    }
}