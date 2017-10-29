package com.company;

/**
 * Created by t on 10/29/17.
 */
public class CheckSum {
    private String checkSum="";

    public String get(String payload){

        checkSum="";

        int length=payload.length()/8;
        int toggler=0;
        for(int i=0;i<8;i++){
            for(int p=0;p<length;p++){
                if(payload.charAt(p*8+i)=='1'){
                    toggler=1-toggler;
                }
            }
            checkSum+=toggler;
            toggler=0;
        }
        return  checkSum;
    }
//
//    public static void main(String args[]){
//        CheckSum checkSum=new CheckSum();
//
//        String s=checkSum.get("1000000000001111");
//
//        System.out.println(s);
//
//        //00100100
//        //00100101
//    }
}
