package com.company;

/**
 * Created by t on 10/29/17.
 */
public class StringToByteArray {
    private byte stringToByte(String array) {
        String toByte=array;
        byte s=0x00;
        for(int i=0;i<toByte.length();i++) {
            if(toByte.charAt(i)=='1'){
                s= (byte) (s|(1<<(7-i)));
            }
        }
        return s;

    }

    public byte[] get(String array){
        String toByte=array;
        String payloadChunk;
        byte[] totalByte=new byte[toByte.length()/8+1];
        int byteIndex=0;
        for(int i=8;i<=toByte.length();i+=8){
            payloadChunk=toByte.substring(i-8,i);
            System.out.println(payloadChunk);
            totalByte[byteIndex]=stringToByte(payloadChunk);
            byteIndex++;
        }
        int residue=toByte.length()%8;
        if(residue!=0){
            payloadChunk=toByte.substring(toByte.length()-residue,toByte.length());
            System.out.println(payloadChunk);
            totalByte[byteIndex]=stringToByte(payloadChunk);

        }





        return totalByte;

    }
}
