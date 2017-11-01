package com.company;

/**
 * Created by t on 10/29/17.
 */
public class ByteArrayToStuffedString {
    private int extraLength;

    int getExtraLength(){
        return extraLength;
    }

    private static String bitToString(byte array) {
        byte toString = array;
        String s = new String();
        for (int i = 7; i >= 0; i--) {
            s += (String.valueOf(array >> i & 1));
        }
        return s;
    }


    public String get(byte [] data){


        String payload = new String();

        payload = "";
        for (int i = 0; i < data.length; i++) {

            payload += bitToString(data[i]);




        }

        //input byte print
        System.out.println("Payload: "+payload);

        ///Checksome done

        CheckSum checkSum=new CheckSum();

        payload=payload+checkSum.get(payload);


        String stuffedPayload = new String();
        stuffedPayload = "";
        int countOne = 0;

        for (int i = 0; i < payload.length(); i++) {
            if (payload.charAt(i) == '0') {
                countOne = 0;
                stuffedPayload += 0;
            } else {
                countOne++;
                if (countOne == 5) {
                    countOne = 0;

                    stuffedPayload += "10";
                    extraLength++;

                } else {
                    stuffedPayload += "1";
                }

            }
        }

        System.out.println("Stuffed payload: "+stuffedPayload);
        ///delimeter done

        stuffedPayload="01111110"+stuffedPayload+"01111110";

        System.out.println("Frame :"+stuffedPayload);


        return stuffedPayload;

    }

    public String getDeStuffed(byte[] data){
        String payload = new String();
        payload = "";
        for (int i = 0; i < data.length; i++) {
            payload += bitToString(data[i]);
        }

        ////
      System.out.println("Received Frame: "+payload);

        //0111111111111110
        //011111111111111010000001

        int p=0;
        while(true){
            if(payload.substring(payload.length()-8-p,payload.length()-p).equals("01111110")){
                break;
            }
            else{
                p++;
            }
        }

        payload=payload.substring(8,payload.length()-8-p);



//          System.out.println("To be Destuffed payload only:"+payload);



        String deStuffedPayload = new String();
        deStuffedPayload = "";
        int countOne = 0;

        for (int i = 0; i < payload.length(); i++) {
            if (payload.charAt(i) == '0') {
                countOne = 0;
                deStuffedPayload += 0;
            } else {
                countOne++;
                if (countOne == 5) {
                    countOne = 0;

                    deStuffedPayload += "1";
                    i++;

                } else {
                    deStuffedPayload += "1";
                }

            }
        }


//        System.out.println("Destuffed: "+deStuffedPayload);



        return deStuffedPayload;
    }


}
