package com.example.tictactoe;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket mySocket;
    Player clintData;
    Client ahmed;//null
    DataInputStream dataInputStream;
    PrintStream printStream;

    Client(Player playerData){
        try{
            mySocket = new Socket("127.0.0.1", 5005);
            dataInputStream = new DataInputStream(mySocket.getInputStream());
            printStream = new PrintStream(mySocket.getOutputStream());
            clintData=new Player(playerData);
            Scanner myObj = new Scanner(System.in);
            sendMove(myObj.nextLine());
        } catch (Exception ex) {
            System.out.println("inside Client constructor: " + ex);
        }
//        dis = new DataInputStream(mySocket.getInputStream());
//        ps = new PrintStream(mySocket.getOutputStream());
    }
    Client(Socket clintSocket, Player playerData) {
        try {
            mySocket = clintSocket;
            dataInputStream = new DataInputStream(clintSocket.getInputStream());
            printStream = new PrintStream(clintSocket.getOutputStream());
            clintData = new Player(playerData);
//            Scanner myObj = new Scanner(System.in);
//            sendMove(myObj.nextLine());
        } catch (Exception ex) {
            System.out.println("inside Client constructor: " + ex);
        }
    }
    public void printClint(){
        System.out.println("my socket=> " + mySocket);
        System.out.println("my player=> " + clintData);
        System.out.println("my socket=> " + dataInputStream);
        System.out.println("my socket=> " + printStream);
    }

    public void setAhmed() {
        Scanner myObj = new Scanner(System.in);
//        System.out.println("enter other");
        sendMove(myObj.nextLine());//stockt

    }

    public boolean sendMove(String message){
        ahmed.printStream.println(message);
        return true;

    }

    public boolean receiveMove(){
        try {
            dataInputStream.readLine();
            return true;
        }catch (Exception e){
            System.out.println("fall to read");
        }
        return false;
    }
//      bt.setOnAction(new EventHandler<ActionEvent>(){
//        @Override
//        public void handle(ActionEvent event){
//            ps.println(tf.getText());
//            tf.setText("");
//        }
//    });

//    th = new Thread(new Runnable(){
//        @Override
//        public void run(){
//            while(true){
//                String replyMsg;
//                try{
//                    replyMsg = dis.readLine();
//                    ta.appendText("\n"+replyMsg);
//                }catch(IOException ex){
//
//                }
//            }
//        }
//    });
//        th.start();
}
