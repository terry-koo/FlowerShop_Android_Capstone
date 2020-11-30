package com.example.android_project_capstone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ArrayList;

public class ZMainProductAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<ZProduct> zProductList = null;

    public ZMainProductAdapter(ArrayList<ZProduct> dataList){
        zProductList = dataList;

    }

    @Override
    public int getCount() {
        return zProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.product_layout, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.product_info);
        TextView priceView = (TextView) convertView.findViewById(R.id.price_info);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        //정보 등록

        NumberFormat krFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        titleView.setText(zProductList.get(position).getTitle());
        priceView.setText(krFormat.format(Integer.parseInt(zProductList.get(position).getPrice()))+"원");



        //이미지 등록
        String base64String = zProductList.get(position).getPicture();
        String base64Image = base64String.split(",")[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);



        return convertView;
    }
}
