package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket = null;
    private static BufferedReader bufferedReader = null;
    private static PrintWriter printWriter = null;
    private static String id;


    public static void main(String args[]) {
        try {
            socket = new Socket("localhost", 5555);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }


        while (true) {
            try {

                System.out.println("Your id:");
                id = new Scanner(System.in).nextLine();


                //sending id
                printWriter.println(id);
                printWriter.flush();

                String s = bufferedReader.readLine();
                System.out.println(s);

                if (s.startsWith("S")) {
                    break;
                }


            } catch (Exception e) {
                System.err.println("Problem in connecting with the server. Exiting main.");
                System.exit(1);
            }

        }

        Scanner input = new Scanner(System.in);
        String strSend = null, strRecv = null;

        try {
            strRecv = bufferedReader.readLine();
            if (strRecv != null) {
                System.out.println(strRecv);
                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());


            } else {
                System.err.println("Error in reading from the socket. Exiting main.");
                cleanUp();
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println("Error in reading from the socket. Exiting main.");
            cleanUp();
            System.exit(0);
        }

        System.out.println("Type 's' to send 'r' to receive");


        String temp = input.nextLine();
        if (temp.equals("s")) {

            printWriter.println("s");
            printWriter.flush();


            while (true) {
                System.out.println(temp);
                System.out.println("Send to:");
                strSend = input.nextLine();

                printWriter.println(strSend);

                printWriter.flush();

                try {
                    if (bufferedReader.readLine().equals("online")) {

                        System.out.println("Enter file name:");

                        String fileName = input.nextLine();
                        File f = new File("Client/"+fileName);
                        int fileSize = (int) f.length();

                        printWriter.println(fileName);
                        printWriter.println(fileSize);
                        printWriter.flush();


                        String fileId = bufferedReader.readLine();
                        System.out.println(fileId);
                        int chunkSize = Integer.parseInt(bufferedReader.readLine());
                        System.out.println(chunkSize);

                        FileSender fileSender = new FileSender(f, socket, fileId, chunkSize);

                        fileSender.sendFile();

                        printWriter.println("File sent");
                        printWriter.flush();


                        System.out.println(bufferedReader.readLine());




                    } else {

                        System.out.println("Receiver is offline");
                        System.exit(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        } else if (temp.equals('q')) {
            cleanUp();
            System.exit(0);
        }
        else{

            printWriter.println("r");
            printWriter.flush();
            try {
                System.out.println(bufferedReader.readLine());
                int fileSize=Integer.parseInt(bufferedReader.readLine());

                int chunkSize=Integer.parseInt(bufferedReader.readLine());

                String fileId=bufferedReader.readLine();



                byte[] b=new byte[fileSize];
                int received=socket.getInputStream().read(b);
                System.out.println(fileId+" ["+received + " ]bytes successfully received");

                if(received==fileSize){
                    FileOutputStream fos = new FileOutputStream(new File("Receiver/"+fileId));
                    fos.write(b);
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private static void cleanUp() {
        try {
            bufferedReader.close();
            printWriter.close();
            socket.close();
        } catch (Exception e) {

        }
    }
}
