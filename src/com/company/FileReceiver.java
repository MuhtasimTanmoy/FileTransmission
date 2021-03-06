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

    public static final String ANSI_RESET="\u001B[0m";
    public static final String ANSI_GREEN="\u001B[32m";
    public static final String ANSI_BLUE="\u001B[34m";




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



    void receive()  {


        //byte[] contents = new byte[chunkSize+chunkSize/5+6];

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
        int total = 0;//how many bytes read
        int sequenceNo=1;
        int lastSequenceNo=0;
        int errorMode=0;

        while (total < fileSize)    //loop is continued until received byte=totalfilesize
        {

            try {
                int received=Integer.parseInt(bufferedReader.readLine());
                System.out.println("Received length: "+received);

                receivedContent=new byte[received];
                bytesRead = inputStream.read(receivedContent);


//                for(int i=0;i<bytesRead;i++){
//                    receivedContent[i]=contents[i];
//                }

                ByteArrayToStuffedString byteArrayToStuffedString=new ByteArrayToStuffedString();
//            System.out.println(byteArrayToStuffedString.getDeStuffed(content));

                String payloadWithChecksum=byteArrayToStuffedString.getDeStuffed(receivedContent);

                String kindOfFrame=payloadWithChecksum.substring(0,8);
                String sequence=payloadWithChecksum.substring(8,16);
                sequenceNo=(int)stringToByte(sequence);



                /////
                if(sequenceNo==(lastSequenceNo+1) && errorMode==0){
                    lastSequenceNo=sequenceNo;
                }
                else{
                    System.out.println(ANSI_BLUE+"Previous frame missing.Not saving it in server."+ANSI_RESET);

                    errorMode=1;

                }


                ////

                String awknowledgement=payloadWithChecksum.substring(16,24);
                System.out.println("Kind of frame :"+ kindOfFrame+ANSI_GREEN+" Sequence no: "+sequenceNo +" ("+ sequence +")"+ANSI_RESET+" Awknoledgement no: "+awknowledgement);
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

            if(errorMode==0) {

                total += bytesRead;
            }
            try {
                printWriter.println("Total: "+total+" bytes receieved in server");
                printWriter.flush();



                if(errorMode==0) {
                    bos.write(receivedPayload, 0, bytesRead);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println(total + " saved in server.");

            if(sequenceNo%8==0){
                printWriter.println("Total: "+total+" bytes received in server");
                printWriter.println(lastSequenceNo);

                printWriter.flush();

                errorMode=0;

            }


            System.out.println("");
            System.out.println("");
        }
        mtotal=total;
        try {
            bos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


}

