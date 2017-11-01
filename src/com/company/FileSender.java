package com.company;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by t on 9/23/17.
 */
public class FileSender {
    private File file;
    private Socket socket;
    private String fileId;
    private int chunkSize;


    public FileSender(File file, Socket socket, String fileId, int chunkSize) throws SocketTimeoutException {
        this.file = file;
        this.socket = socket;
        this.fileId = fileId;
        this.chunkSize = chunkSize;
        try {
            this.socket.setSoTimeout(30000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    void sendFile(){

        try {
            FileInputStream fileInputStream=new FileInputStream(this.file);
            BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);

            OutputStream outputStream=socket.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


            byte[] buffer;

            int fileSize=(int)file.length();

            int current = 0;
            long start = System.nanoTime();

         while(current<fileSize){
         int size = chunkSize;
         if(fileSize - current >= size)
         current += size;
         else{
         size = (int)(fileSize - current);
         current = fileSize;
         }
         buffer = new byte[size];
         bufferedInputStream.read(buffer, 0, size);




         /////


             ByteArrayToStuffedString byteArrayToStuffedString=new ByteArrayToStuffedString();
             String stuffedString=byteArrayToStuffedString.get(buffer);

             //System.out.println(stuffedString);


             StringToByteArray stringToByteArray=new StringToByteArray();
             byte[] byteArray=stringToByteArray.get(stuffedString);

//             System.out.println(byteArray);




        ////


         outputStream.write(byteArray);
         System.out.println("Sending file ... "+current+" bytes done..."+(current*100)/fileSize+"% complete!");


             try {
                String acknowledgement=br.readLine();
                System.out.println(acknowledgement);


            }catch (SocketTimeoutException e){
                ///timeout message to be sent

                break;

            }




         }
         outputStream.flush();























        } catch (Exception e) {
            System.out.println(e);        }
    }







}
