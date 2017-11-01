package com.company.ServerTest;

import com.company.ByteArrayToStuffedString;
import com.company.StringToByteArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by t on 10/29/17.
 */
public class Client {
    public static void main(String args[])
    {
        try {
            Socket socket = new Socket("localhost", 6666);
            System.out.println("Client started");

            InputStream inputStream=socket.getInputStream();

            OutputStream outputStream=socket.getOutputStream();

            byte[] data = new byte[]{(byte) 0x7f, (byte) 0x0f};

            ByteArrayToStuffedString byteArrayToStuffedString=new ByteArrayToStuffedString();
            String s=byteArrayToStuffedString.get(data);

            System.out.println(s);

            StringToByteArray stringToByteArray=new StringToByteArray();
            byte[] sb=stringToByteArray.get(s);



            System.out.println(sb.length);

            for(int i=0;i<sb.length;i++){
                System.out.println(sb[i]);
            }






           outputStream.write(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
