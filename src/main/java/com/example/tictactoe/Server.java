package com.example.tictactoe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
//    static boolean isRun=false;
    public Server() {
////        if(!isRun) {
//            try {
//                serverSocket = new ServerSocket(5005);
////                isRun=true;
//                while (true) {
//                    serverSocket.accept();
//                }
//            } catch (Exception e) {
//                System.out.println("inside private Server constructor" + e);
//            }
////        }
////        else
////            System.out.println("multi instance");
//    }
        try (ServerSocket server = new ServerSocket(7777);) {
            while (true) {
                Socket client = server.accept();
//                SocketHanddler sockethand = new SocketHanddler(client);
//                System.out.println(SocketHanddler.getCount() + " clients are connect ");
//                sockethand.start();
            }//end while loop
        } catch (IOException ex) {
            ex.getMessage();
        }
    }




    public static void main(String[] args) {
        new Server();
    }
}
