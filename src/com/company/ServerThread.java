package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by t on 9/23/17.
 */
public class ServerThread implements Runnable{
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private String id;

    private String temp;

    public ServerThread(Socket s, String id)
    {
        this.socket = s;

        this.temp="|";

        try
        {
            this.is = this.socket.getInputStream();
            this.os = this.socket.getOutputStream();
        }
        catch(Exception e)
        {
            System.err.println("Sorry. Cant manage client [" + id + "] properly.");
        }

        this.id = id;
    }

    public void run()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
        PrintWriter pr = new PrintWriter(this.os);

        pr.println("Your id is: " + this.id);

        pr.println("Online clients:"+Server.clientList.size());


        Iterator<String> iterator=Server.clientList.iterator();

        while (iterator.hasNext()){

            temp=temp+" "+iterator.next()+" |";


        }


        pr.println(temp);

        pr.flush();

        String receiverId;

        try {
            if(br.readLine().equals('r')){
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


            try {


                receiverId = br.readLine();

                System.out.println(receiverId);
                System.out.println(Server.clientList.contains(receiverId));
                if (Server.clientList.contains(receiverId)) {
                    pr.println("online");
                    pr.flush();





                    String retrievedFileName=br.readLine();
                    System.out.println(retrievedFileName);
                    int retrievedFileSize=Integer.parseInt(br.readLine());
                    System.out.println(retrievedFileSize);




                    ServerBufferChecker serverBufferChecker=new ServerBufferChecker(retrievedFileName,retrievedFileSize);
                    int chunkSize=serverBufferChecker.check();
                    if(chunkSize==0){
                        while(true){
                            System.out.println("Error");
                        }
                    }
                    String fileId="("+id+"to"+receiverId+")"+retrievedFileName;


                    //////toReceiver




                    pr.println(fileId);
                    pr.println(chunkSize);
                    pr.flush();

                    FileReceiver fileReceiver=new FileReceiver("Server/"+fileId,socket,retrievedFileSize,chunkSize);


                    fileReceiver.receive();

                    if(br.readLine().startsWith("File")){
                        if(fileReceiver.getTotal()==retrievedFileSize){
                            pr.println("Successfully received in destination");
                            pr.flush();
                        }
                        else{
                            File f=new File("Server/"+fileId);
                            f.delete();
                        }
                    }

//                    System.out.println(br.readLine()+"tanmoy");


                    PrintWriter prToDesClient=new PrintWriter(Server.hashtable.get(receiverId).getOutputStream());
                    prToDesClient.println("Receive file from: "+id+"?    "+"File Size:"+retrievedFileSize+"    "+"Chunk Size:"+chunkSize);
                    prToDesClient.println(retrievedFileSize);
                    prToDesClient.println(chunkSize);
                    prToDesClient.println(fileId);
                    prToDesClient.flush();



                    Path path = Paths.get("Server/"+fileId);
                    System.out.println(path.getFileName());
                    byte[] data = Files.readAllBytes(path);
                    System.out.println(data.length);

                    Server.hashtable.get(receiverId).getOutputStream().write(data);
                    Server.hashtable.get(receiverId).getOutputStream().flush();








                    //BufferedReader brFromDesClient=new BufferedReader(new InputStreamReader(Server.hashtable.get(receiverId).getInputStream()));
//                    System.out.println(brFromDesClient.readLine());


//                   if(brFromDesClient.readLine().equals("y")){
//
//
//
//                       ///todo
//
////                       FileSender fileSender=new FileSender(new File("Server/"+fileId),Server.hashtable.get(receiverId),fileId,chunkSize);
////                       fileSender.sendFile();
//
//
//                   }
//                   else{
//                       System.out.println("Connection refused");
//                   };


                    File f=new File("Server/"+fileId);
                    f.delete();





//                    PrintWriter printWriter3=new PrintWriter(Server.hashtable.get(receiverId).getOutputStream());
//                    printWriter3.write("Do you want to have a file from ");
//                    printWriter3.flush();
















                } else {
                    pr.println("offline");
                    pr.flush();

                }

            } catch (Exception e) {
                System.out.println(e);
            }


        try
        {
            this.is.close();
            this.os.close();
            this.socket.close();
        }
        catch(Exception e)
        {

        }

        Server.workerThreadCount--;
        Server.clientList.remove(id);
        System.out.println("Client [" + id + "] is w terminating. . of worker threads = "
                + Server.workerThreadCount);
    }
}
