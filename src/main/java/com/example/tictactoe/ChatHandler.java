package com.example.tictactoe;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

public class ChatHandler extends Thread {
    Socket chatSocket;
    DataInputStream dataInputStream;
    PrintStream printStream;
    static Vector<ChatHandler> clientVector = new Vector<>();
    //
    // player1 -> massege player2 // index of vector

    public ChatHandler(Socket cs){
        this.chatSocket = cs;
        try{
            dataInputStream = new DataInputStream(cs.getInputStream());
            printStream = new PrintStream(cs.getOutputStream());
            clientVector.add(this);
            start();
        } catch (Exception ex) {

        }
    }

    public void run(){
        while(true){
            try{
                String str = dataInputStream.readLine();
                sendMessageToAll(str);
            } catch (IOException ex) {
                    this.stop();
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                printStream.close();
                    clientVector.remove(this);
                    //close the game when other user closed

            }
        }
    }

    void sendMessageToAll(String msg) {
        for(ChatHandler ch:clientVector){
            ch.printStream.println(msg);
        }
    }
}