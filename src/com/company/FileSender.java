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


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public FileSender(File file, Socket socket, String fileId, int chunkSize) throws SocketException {
        this.file = file;
        this.socket = socket;
        this.fileId = fileId;
        this.chunkSize = chunkSize;
        //this.socket.setSoTimeout(30000);

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
         outputStream.write(buffer);

            try {
                System.out.println(br.readLine());
            }catch (SocketTimeoutException e){

            }



         System.out.println("Sending file ... "+current+" bytes done..."+(current*100)/fileSize+"% complete!");

         }
         outputStream.flush();























        } catch (Exception e) {
            System.out.println(e);        }
    }







}
