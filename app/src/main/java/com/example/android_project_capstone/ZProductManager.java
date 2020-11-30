package com.example.android_project_capstone;


import android.util.Log;
import java.util.ArrayList;

public class ZProductManager {
    private static ArrayList<ZProduct> zProductList = new ArrayList<>();
    private static final String DELIMITER = "\\|";

    public static ArrayList<ZProduct> getzProductList(){return zProductList;}
    public static void updateList(){
        String opCode = "showZMainProduct";
        try{
            //리스트 버퍼
            ArrayList<ZProduct> tempList = new ArrayList<>();

            //조회 결과 받기
            String rst = new ZDBLink(opCode).execute(opCode).get();
            //조회 결과 데이터 분리
            String[] a = rst.split(DELIMITER);

            //오류 체크 변수 생성
            int start = 0;
            int end = 0;
        for(int i = 0; i < a.length; i++){
            if(a[i].equals("result=")) {       //result= 는 페이지에서 조회가 시작되는 지점
                i++;
                while (!a[i].equals("end")) {   //end는 조회가 끝나는 지점
                    if (a[i].equals("product start")) { // 하나의 product 조회 시작
                        start++;
                        i++;
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

                        tempList.add(p);
                    } else {
                        i++;
                    }
                    if (a[i].equals("product end")) {   //product 끝
                        end++;
                    }
                }
                break;
            }
        }
            if(start != end){   //만약 시작과 끝 지점이 맞지 않는다면, 즉 중간에 오류가 발생 했다면 예외 발생
                Log.v("zTag","start:"+start+" end:"+end );
                throw new Exception("*count doesn't match");
            }
            zProductList = tempList;    //정상적으로 처리 했을 경우 리스트 버퍼를 zProductList에 덮어 씌움
            Log.v("zTag","updated size : "+zProductList.size());

        }catch (Exception e){
            Log.v("zTag","ZProductManager ERROR "+e.getMessage());
        }
    }
}
