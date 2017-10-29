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
        System.out.println(payload);

        String stuffedPayload = new String();
        stuffedPayload = "";
        int countOne = 0;
        int fixer = 0;

        for (int i = 0; i < payload.length(); i++) {
            if (payload.charAt(i) == '0') {
                countOne = 0;
                fixer = 0;
                stuffedPayload += 0;
            } else {
                countOne++;
                if (countOne == (6 + fixer)) {
                    fixer = -1;
                    countOne = 0;
                    stuffedPayload += "01";
                    extraLength++;

                } else {
                    stuffedPayload += "1";
                }

            }
        }

        return stuffedPayload;

    }


}
