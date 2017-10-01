package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by t on 9/23/17.
 */
public class Server {
    public static int workerThreadCount = 0;

    public static final int SOCKET_PORT=5555;

    public static ArrayList<String> clientList=new ArrayList<>();

    public static int maxSize=1024000;
    public static int used=0;

    public static Hashtable<String,Socket> hashtable=new Hashtable<>();




    public static void main(String args[])
    {
        String clientId;

        try
        {
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

            System.out.println("Server has been started successfully.");

            while(true)
            {
                Socket s = serverSocket.accept();		//TCP Connection

                PrintWriter printWriter;
                InputStreamReader inputStreamReader;
                BufferedReader bufferedReader;

                printWriter=new PrintWriter(s.getOutputStream());
                inputStreamReader=new InputStreamReader(s.getInputStream());
                bufferedReader=new BufferedReader(inputStreamReader);

                clientId=bufferedReader.readLine();



                System.out.println(clientId);

                Boolean idFound=ServerStaticTable.checkIfOnline(clientId,printWriter,s,clientList);


                while(idFound){
                    clientId=bufferedReader.readLine();
                    idFound=ServerStaticTable.checkIfOnline(clientId,printWriter,s,clientList);

                }



                    hashtable.put(clientId,s);
                    ServerThread wt = new ServerThread(s, clientId);
                    Thread t = new Thread(wt);
                    t.start();
                    workerThreadCount++;
                    System.out.println("Client [" + clientId + "] is w connected. . of worker threads = " + workerThreadCount);








            }
        }
        catch(Exception e)
        {
            System.err.println("Problem in ServerSocket operation. Exiting main.");
        }
    }
}
