package com.company;

import java.util.Random;

/**
 * Created by t on 9/24/17.
 */
public class ServerBufferChecker {

    private String fileName;
    private int fileSize;
    private int chunkSize;
    private Random random;

    public ServerBufferChecker(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        random=new Random();
    }

    public int check(){
        if(Server.used+fileSize<Server.maxSize){
            Server.used+=fileSize;
            chunkSize=random.nextInt(500)+1000;
            return chunkSize;
        }
        else{
            return 0;
        }
    }



}
