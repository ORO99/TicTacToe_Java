package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginController {
    @FXML private Pane rootPane;
    @FXML private TextField user_name;
    @FXML private TextField user_pass;

    public void login() throws IOException {
//        Server s =new Server();
        Main.x = new Player(user_name.getText(),user_pass.getText());
        if(Main.x.login()) {
//            Client y = new Client(x);
//            y.printClint();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("ChooseGameGui.fxml"));
//            AnchorPane pane = FXMLLoader.load(getClass().getResource("GameGui.fxml"));
            rootPane.getChildren().setAll(pane);
            System.out.println("login");
        }
        else
            System.out.println("this user can't login");
    }

}
