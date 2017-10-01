//package com.company;
//
///**
// * Created by t on 9/23/17.
// */
//
//
//
// while(true)
//         {
//         try
//         {
//         if( (str = br.readLine()) != null )
//         {
//         if(str.equals("BYE"))
//         {
//         System.out.println("[" + id + "] says: BYE. Worker thread will terminate w.");
//         break; // terminate the loop; it will terminate the thread also
//         }
//         else if(str.equals("DL"))
//         {
//         try
//         {
//         File file = new File("test.jpg");
//         FileInputStream fis = new FileInputStream(file);
//         BufferedInputStream bis = new BufferedInputStream(fis);
//         OutputStream os = socket.getOutputStream();
//         byte[] contents;
//         long fileLength = file.length();
//         pr.println(String.valueOf(fileLength));		//These two lines are used
//         pr.flush();									//to send the file size in bytes.
//
//         long current = 0;
//
//         long start = System.naTime();
//         while(current!=fileLength){
//         int size = 10000;
//         if(fileLength - current >= size)
//         current += size;
//         else{
//         size = (int)(fileLength - current);
//         current = fileLength;
//         }
//         contents = new byte[size];
//         bis.read(contents, 0, size);
//         os.write(contents);
//         System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
//         }
//         os.flush();
//         System.out.println("File sent successfully!");
//         }
//         catch(Exception e)
//         {
//         System.err.println("Could t transfer file."+e);
//         }
//         pr.println("Downloaded.");
//         pr.flush();
//
//         }
//         else
//         {
//         System.out.println("[" + id + "] says: " + str);
//         pr.println("Got it. You sent \"" + str + "\"");
//         pr.flush();
//         }
//         }
//         else
//         {
//         System.out.println("[" + id + "] terminated connection. Worker thread will terminate w.");
//         break;
//         }
//         }
//         catch(Exception e)
//         {
//         System.err.println("Problem in communicating with the client [" + id + "]. Terminating worker thread.");
//         break;
//         }
//         }
//
//
//
//public class temp {
//}
//
//
//
