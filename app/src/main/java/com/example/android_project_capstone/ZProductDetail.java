package com.example.android_project_capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Locale;

public class ZProductDetail extends AppCompatActivity {

    ImageView imageView = null;
    TextView pDetail = null;
    TextView article = null;
    Button purchaseBTN = null;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_product_detail);
        intent = getIntent();

        imageView = (ImageView) findViewById(R.id.imageView);
        pDetail = (TextView) findViewById(R.id.product_detail);
        article = (TextView) findViewById(R.id.product_article);

        //intent에서 position 들고오기
        int position = intent.getIntExtra("position", 0);
        ZProduct product = ZProductManager.getzProductList().get(position);

        String pvn = null;
        switch (product.getProduct_variety_code()){
            case "1":
                pvn = "화환";
                break;
            case "2":
                pvn = "바구니";
                break;
            case "3":
                pvn = "압화";
                break;
            case "4":
                pvn = "화분식물";
                break;

        }

        String base64String = product.getPicture();
        String base64Image = base64String.split(",")[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);


        NumberFormat krFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        StringBuilder pInfo  = new StringBuilder();
        pInfo.append("상품이름 : "+product.getTitle()+"\n");
        pInfo.append(pvn+"\n");
        pInfo.append("가격 : "+krFormat.format(Integer.parseInt(product.getPrice()))+"원\n");
        pDetail.setText(pInfo.toString());

        article.setText(product.getContent());

    }
}