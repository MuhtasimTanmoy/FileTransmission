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

    public int getTotal(){
        return mtotal;
    }



    void receive()  {


        byte[] contents = new byte[chunkSize+chunkSize/5+6];

        byte[] receivedContent;

        byte[] receivedPayload=new byte[1];


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

                receivedContent=new byte[bytesRead];
                for(int i=0;i<bytesRead;i++){
                    receivedContent[i]=contents[i];
                }

                ByteArrayToStuffedString byteArrayToStuffedString=new ByteArrayToStuffedString();
//            System.out.println(byteArrayToStuffedString.getDeStuffed(content));

                String payloadWithChecksum=byteArrayToStuffedString.getDeStuffed(receivedContent);

                String kindOfFrame=payloadWithChecksum.substring(0,8);
                String sequence=payloadWithChecksum.substring(8,16);
                String awknowledgement=payloadWithChecksum.substring(16,24);
                System.out.println("Kind of frame :"+ kindOfFrame+" Sequence no: "+ sequence+" Awknoledgement no: "+awknowledgement);
                String payload=payloadWithChecksum.substring(24,payloadWithChecksum.length()-8);
                String checkSum=payloadWithChecksum.substring(payloadWithChecksum.length()-8,payloadWithChecksum.length());
                System.out.println("Received Payload: "+payload);
                System.out.println("Received Checksum: "+checkSum);

                CheckSum checkSumGetter=new CheckSum();
                String calculatedChecksum=checkSumGetter.get(payload);

                System.out.println("Calculated Checksum: "+calculatedChecksum);

                if(!checkSum.equals(calculatedChecksum)){
                    System.out.println("Checksum does not match");
                    break;

                }

                StringToByteArray stringToByteArray=new StringToByteArray();
                receivedPayload=stringToByteArray.get(payload);

                bytesRead=receivedPayload.length;





            } catch (IOException e1) {
                e1.printStackTrace();
            }
            total += bytesRead;
            try {
                printWriter.println(total+" -receieved in server");
                printWriter.flush();


                bos.write(receivedPayload, 0, bytesRead);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println(total + " received");
        }
        mtotal=total;
        try {
            bos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


}

