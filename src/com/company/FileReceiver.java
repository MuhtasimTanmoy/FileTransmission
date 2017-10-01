package com.company;

import java.io.*;
import java.net.Socket;

/**
 * Created by t on 9/25/17.
 */
public class FileReceiver  {

    private String fileName;
    private Socket socket;
    private int fileSize;
    private InputStream inputStream;
    private OutputStream outputStream;
    private int chunkSize;
    private PrintWriter printWriter;
    BufferedReader bufferedReader;
    private int mtotal=0;


    public FileReceiver(String fileName, Socket socket, int fileSize,int chunkSize) {
        this.fileName = fileName;
        this.socket = socket;
        this.fileSize = fileSize;
        this.chunkSize=chunkSize;


        try{
        inputStream=socket.getInputStream();
        outputStream=socket.getOutputStream();
        printWriter=new PrintWriter(outputStream);
        bufferedReader= new BufferedReader(new InputStreamReader(inputStream));

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getFileName() {

        return fileName;
    }
    public int getTotal(){
        return mtotal;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }



    void receive()  {


        byte[] contents = new byte[chunkSize];


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fileName));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        BufferedOutputStream bos = new BufferedOutputStream(fos);



        int bytesRead = 0;
        int total = 0;            //how many bytes read

        while (total < fileSize)    //loop is continued until received byte=totalfilesize
        {
            try {
                bytesRead = inputStream.read(contents);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            total += bytesRead;
            try {
                printWriter.println(total+" -receieved in server");
                printWriter.flush();


                bos.write(contents, 0, bytesRead);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println(total + "received");
        }
        mtotal=total;
        try {
            bos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


}

