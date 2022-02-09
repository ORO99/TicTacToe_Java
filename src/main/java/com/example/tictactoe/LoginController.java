package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginController {
    @FXML
    Button login_btn;
    @FXML
    Pane rootPane;
    @FXML
    public void login() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("GameGui.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
