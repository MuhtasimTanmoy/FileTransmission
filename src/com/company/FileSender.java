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
    private ByteArrayToStuffedString byteArrayToStuffedString;

    public static final String ANSI_RESET="\u001B[0m";
    public static final String ANSI_GREEN="\u001B[32m";
    public static final String ANSI_BLUE="\u001B[34m";




    public FileSender(File file, Socket socket, String fileId, int chunkSize) throws SocketTimeoutException {
        this.file = file;
        this.socket = socket;
        this.fileId = fileId;
        this.chunkSize = chunkSize;
        byteArrayToStuffedString=new ByteArrayToStuffedString();

        try {
            this.socket.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    void sendFile(){

        try {
            FileInputStream fileInputStream=new FileInputStream(this.file);
            BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);

            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);

            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


            byte[] buffer;

            int fileSize=(int)file.length();

            int current = 0;

            int sequence=1;

            int once=0;
            int twice=0;


            byte[][] savedByteArray=new byte[8][];

         while(current<fileSize){
             System.out.println("Frame no: "+sequence+" ");
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


             String stuffedString=byteArrayToStuffedString.get(buffer);

             //System.out.println(stuffedString);


             StringToByteArray stringToByteArray=new StringToByteArray();
             byte[] byteArray=stringToByteArray.get(stuffedString);




            System.out.println("Sent bytes: "+byteArray.length);

            savedByteArray[(sequence-1)%8]=byteArray;



            ///////////////////////////////////////////////error introduce//////////////////////

//             if((sequence==2)  && once==0) {
//
//                 System.out.println(ANSI_BLUE+"Not sending this frame.Skipping this for GO-Back N protocol check"+ANSI_RESET);
//                 once=1;
//             }
//             else if((sequence==14)  && twice==0) {
//
//                 System.out.println(ANSI_BLUE+"Not sending this frame.Skipping this for GO-Back N protocol check"+ANSI_RESET);
//                 twice=1;
//             }
//
//             else{

                 //////////////////////////////////////................////////////////////////

             printWriter.println(byteArray.length);
            printWriter.flush();





        ////
         outputStream.write(byteArray);
         outputStream.flush();
         System.out.println("Sending file ... "+current+" bytes done..."+(current*100)/fileSize+"% complete!");


             try {
                String acknowledgement=br.readLine();
                System.out.println(acknowledgement);


            }catch (SocketTimeoutException e){
                ///timeout message to be sent

                break;

            }


            /////////////////////////error introduce////////////////////////////////
        //     }

////////////////////////////......................///////////////////////////////////////



             if(sequence%8==0){
                 String acknowledgement=br.readLine();
//                 System.out.println(acknowledgement);
                 int lastSequenceNo=Integer.parseInt(br.readLine());
//                 System.out.println(lastSequenceNo);
//                 System.out.println("in 8 th index");

//                 int i=0;
//                 while(i<=7){
//                     System.out.println(savedByteArray[i].length);
//                     i++;
//                 }
                 System.out.println("");
                 System.out.println("Checking from server for missing frame.");
                 if(lastSequenceNo!=sequence){
                     System.out.println("Resending "+(lastSequenceNo+1) +" to " +sequence + " frames." );
                 for(int j=lastSequenceNo;j<sequence;j++){
                     printWriter.println(savedByteArray[j%8].length);
                     printWriter.flush();





                     ////
                     outputStream.write(savedByteArray[j%8]);
                     outputStream.flush();
                     System.out.println("Resending "+ (j+1) +" no frame.");



                     try {
                         String ack=br.readLine();
                         System.out.println(ack);


                     }catch (SocketTimeoutException e){
                         ///timeout message to be sent

                         break;

                     }









                 }

                     String ac=br.readLine();
//                     System.out.println(ac);
                     int la=Integer.parseInt(br.readLine());
//                     System.out.println(la);
                     System.out.println("Missing frame recovered.Resuming transmission.");
                 }



             }


             System.out.println("");
             System.out.println("");


             sequence++;


         }
         outputStream.flush();























        } catch (Exception e) {
            System.out.println(e);        }
    }







}
