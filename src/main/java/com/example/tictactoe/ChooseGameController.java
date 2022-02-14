package com.example.tictactoe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseGameController implements Initializable {

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

    ObservableList<Player> List = FXCollections.observableArrayList(
            new Player("omar", "1", 69),
            new Player("joe", "0", 45),
            new Player("ahmad", "1", 30),
            new Player("fawzy", "0", 20)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
        status.setCellValueFactory(new PropertyValueFactory<Player, Integer>("status"));
        score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        myTable.setItems(List);
    }
}
