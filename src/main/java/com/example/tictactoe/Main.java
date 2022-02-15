package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Player x;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("LoginGui.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop(){
        System.out.println(x.logout());
    }


    public static void main(String[] args) {
//        new Server();
        launch(args);
    }
}