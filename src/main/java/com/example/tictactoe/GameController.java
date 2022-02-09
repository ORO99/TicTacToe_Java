package com.example.tictactoe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameController {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Label txt;
    private int board[][] = new int[3][3];
    private int movesLeft = 9;
    private  boolean gameOver = false;
    ArrayList<Button> buttons;

    public void initialize(){
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));
        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void reset(ActionEvent event) {
        movesLeft = 9;
        gameOver = false;
        for (int[] row: board)
            Arrays.fill(row, 0);
        buttons.forEach(this::resetButton);
        txt.setText("");
    }

    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            movesLeft--;
            button.setDisable(true);
            button.setText("X");
            updateBoard(button);
            checkIfGameIsOver();
            if(movesLeft > 1 && !gameOver)
            {
                AImove();
                movesLeft--;
                checkIfGameIsOver();
            }
        });
    }

    private void AImove(){
        int x = 0;
        int y = 0;
        Random rand = new Random();
        do{
            x = rand.nextInt(3);
            y = rand.nextInt(3);
        }while(board[x][y] != 0);
        board[x][y] = 2;

        if(x == 0 && y == 0)
        {
            button1.setText("O");
            button1.setDisable(true);
        }
        else if(x == 0 && y == 1){
            button2.setText("O");
            button2.setDisable(true);
        }
        else if(x == 0 && y == 2){
            button3.setText("O");
            button3.setDisable(true);
        }
        else if(x == 1 && y == 0){
            button4.setText("O");
            button4.setDisable(true);
        }
        else if(x == 1 && y == 1){
            button5.setText("O");
            button5.setDisable(true);
        }
        else if(x == 1 && y == 2){
            button6.setText("O");
            button6.setDisable(true);
        }
        else if(x == 2 && y == 0){
            button7.setText("O");
            button7.setDisable(true);
        }
        else if(x == 2 && y == 1){
            button8.setText("O");
            button8.setDisable(true);
        }
        else if(x == 2 && y == 2){
            button9.setText("O");
            button9.setDisable(true);
        }
    }

    private void updateBoard(Button b){
        int x = 0;
        int y = 0;
        switch (b.getId()){
            case "button1":
                x = 0;
                y = 0;
                break;
            case "button2":
                x = 0;
                y = 1;
                break;
            case "button3":
                x = 0;
                y = 2;
                break;
            case "button4":
                x = 1;
                y = 0;
                break;
            case "button5":
                x = 1;
                y = 1;
                break;
            case "button6":
                x = 1;
                y = 2;
                break;
            case "button7":
                x = 2;
                y = 0;
                break;
            case "button8":
                x = 2;
                y = 1;
                break;
            case "button9":
                x = 2;
                y = 2;
                break;
        }
        board[x][y] = 1;
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            //X winner
            if (line.equals("XXX")) {
                txt.setText("X won!");
                buttons.forEach(button ->{
                    button.setDisable(true);
                });
                gameOver = true;

            }

            //O winner
            else if (line.equals("OOO")) {
                txt.setText("O won!");
                buttons.forEach(button ->{
                    button.setDisable(true);
                });
                gameOver = true;
            }

            //Tie
            if(movesLeft == 0)
            {
                txt.setText("Oh NO!! .... Its A Tie!");
                gameOver = true;
            }
        }
    }
}