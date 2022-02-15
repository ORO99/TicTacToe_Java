package com.example.tictactoe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseGameController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private TableView<Player> myTable;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Integer> score;

    @FXML
    private TableColumn<Player, Integer> status;

    @FXML
    private TableColumn<Player, String> invite;

    ObservableList<Player> List = FXCollections.observableArrayList(
            new Player("omar", 1, 0),
            new Player("joe", 0, 1),
            new Player("ahmad", 1, 0),
            new Player("fawzy", 0, 1)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
        status.setCellValueFactory(new PropertyValueFactory<Player, Integer>("status"));
        score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        invite.setCellValueFactory(new PropertyValueFactory<Player, String>("invitePlayer"));
        myTable.setItems(List);
    }

    public void playOffline() throws IOException {
        System.out.println("play online");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("OnlineGameGui.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void playOnline() throws IOException{
        System.out.println("play offline");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("GameGui.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
