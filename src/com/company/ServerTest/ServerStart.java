package com.company.ServerTest;

import com.company.ByteArrayToStuffedString;
import com.company.StringToByteArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by t on 10/29/17.
 */
public class ServerStart {


    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);

            Socket s = serverSocket.accept();		//TCP Connection

            System.out.println("Accepted");

            InputStream inputStream=s.getInputStream();

            OutputStream outputStream=s.getOutputStream();

            int byteRead;

            byte[] content=new byte[10];
            byteRead=inputStream.read(content);
            byte[] receivedContent=new byte[byteRead];
            for(int i=0;i<byteRead;i++){
                receivedContent[i]=content[i];
            }

          System.out.println(receivedContent[0]+"......"+receivedContent[5]);

            System.out.println(byteRead);

            ByteArrayToStuffedString byteArrayToStuffedString=new ByteArrayToStuffedString();
//            System.out.println(byteArrayToStuffedString.getDeStuffed(content));

            String payloadWithChecksum=byteArrayToStuffedString.getDeStuffed(receivedContent);
            String payload=payloadWithChecksum.substring(0,payloadWithChecksum.length()-8);
            System.out.println(payload);
            StringToByteArray stringToByteArray=new StringToByteArray();
            byte[] got=stringToByteArray.get(payload);
            System.out.println(got.length);
            System.out.println(got[0]+"......"+got[1]+".....");




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
