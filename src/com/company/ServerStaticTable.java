package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by t on 10/1/17.
 */
public class ServerStaticTable {

    static boolean checkIfOnline(String id, PrintWriter pr, Socket s, ArrayList<String> clientList) throws IOException {
        if(clientList.contains(id)){
            pr.println("Login denied.Enter another id.");
            pr.flush();
            return true;
        }
        else{
            pr.println("Successfull login. You can send file.");
            pr.flush();

            clientList.add(id);
//            socketFetcher.put(id,s);
            return false;
        }
    }
}
