package com.example.android_project_capstone;


import android.util.Log;
import java.util.ArrayList;

public class ZProductManager {
    private static ArrayList<ZProduct> zProductList = new ArrayList<>();

    public static ArrayList<ZProduct> getzProductList(){return zProductList;}
    public static void updateList(){
        String dataType = "showZMainProduct";
        try{
            String rst = new ZDBLink(dataType).execute(dataType).get();
            String[] a = rst.split(",");
            int start = 0;
            int end = 0;
        for(int i = 0; i < a.length; i++){
            if(a[i].equals("result=")){
            i++;
                while(!a[i].equals("end")) {
                    if(a[i].equals("product start")){
                        start++;
                        ZProduct p = new ZProduct();
                        p.setProduct_variety_code(a[i++]);
                        p.setIs_available(a[i++]);
                        p.setProduct_id(a[i++]);
                        p.setPrice(a[i++]);
                        p.setArticle_id(a[i++]);
                        p.setCustomer_id(a[i++]);
                        p.setTitle(a[i++]);
                        p.setContent(a[i++]);
                        p.setIs_linked_to_product(a[i++]);
                        p.setIs_registered(a[i++]);
                        p.setRegistration_date(a[i++]);
                        p.setPicture_id(a[i++]);
                        p.setPicture(a[i++]);
                        zProductList.add(p);
                    }
                    else{
                        i++;
                    }
                    if(a[i].equals("product end")){
                        end++;
                    }

                }
            break;
            }

        }
            if(start != end){
                Log.v("zTag","start:"+start+" end:"+end );
                throw new Exception("*count doesn't match");
            }
            Log.v("zTag","updated size : "+zProductList.size());

        }catch (Exception e){
            Log.v("zTag","error "+e.getMessage());
        }
    }
}
