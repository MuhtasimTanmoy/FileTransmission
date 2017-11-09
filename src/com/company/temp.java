package com.company;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Temp {

    private static String bitToString(byte array) {
        byte toString = array;
        String s = new String();
        for (int i = 7; i >= 0; i--) {
            s += (String.valueOf(array >> i & 1));
        }
        return s;
    }

    private static byte stringToByte(String array) {
        String toByte=array;
        byte s=0x00;
        for(int i=0;i<toByte.length();i++) {
            if(toByte.charAt(i)=='1'){
                s= (byte) (s|(1<<(7-i)));
            }
        }
        return s;

    }






    public static void main(String args[]) throws IOException {


//        Path path = Paths.get("Client/" + "n.txt");
//        System.out.println(path.getFileName());
//        byte[] data = Files.readAllBytes(path);
//        System.out.println(data.length);
//        String s;

        //byte[] data = new byte[]{(byte) 0x7f, (byte) 0xff};


//
//        ByteArrayToStuffedString byteArrayToStuffedString=new ByteArrayToStuffedString();
//        s=byteArrayToStuffedString.get(data);
//        System.out.println(s);
//        System.out.println(byteArrayToStuffedString.getExtraLength());
//
//        System.out.println("Data: "+s.substring(s.length()-s.length()%8,s.length()));


//        byte sdata[];
//        s="00001110000011110111011101";
//
//        StringToByteArray stringToByteArray=new StringToByteArray();
//
//        sdata=stringToByteArray.get(s);
//        System.out.println(sdata[0]);
//        System.out.println(sdata[1]);
//        System.out.println(sdata[2]);
//        System.out.println(sdata[3]);





//        byte ss=stringToByte("01111111");
//        System.out.println(ss);

        int p=127;
        byte by=(byte)p;
        String s=bitToString(by);

        System.out.println(s);

        by=stringToByte(s);
        p=(int)by;
        System.out.println(p+1);






    }

}




