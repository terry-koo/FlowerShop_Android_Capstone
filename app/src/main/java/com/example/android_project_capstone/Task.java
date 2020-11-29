package com.example.android_project_capstone;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Task extends AsyncTask<String, Void, String> {
    public static String ip ="192.168.0.5:8080"; //IP번호
    String sendMsg, receiveMsg;
    String serverip = "http://"+ip+"/Database_Project_myBatis/android/DBLink.jsp"; // 연결할 jsp주소


    Task(String sendmsg){
        this.sendMsg = sendmsg;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            if(sendMsg.equals("type1")){
                sendMsg = "dataType="+strings[0]+"&num2="+strings[1]+"&num3="+strings[2];
            }else if(sendMsg.equals("login")){
                sendMsg = "dataType="+strings[0]+"&id="+strings[1]+"&password="+strings[2];
            }else{
                Log.d("sendMsg 값 :" ,"잘못된 파라미터");
            }

            //String값만 보낼수 있음
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                boolean start = false;
                while ((str = reader.readLine()) != null) {
                   if(str.equals("result=>")){
                        start = true;
                        continue;
                    }
                    if(start) {
                        buffer.append(str);

                    }

                }
                receiveMsg = buffer.toString();
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }
}