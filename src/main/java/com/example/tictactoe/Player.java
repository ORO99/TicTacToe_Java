package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Player {
    private String userName;
    private String password;
    private Button invitePlayer;
    private int status;
    private int score;
    private final Database Db=new Database();
    Player(String playerName){
        setUserName(playerName);
        getPlayerData();
    }
    Player(String playerName, String playerPassword){
        setUserName(playerName);
        setPassword(playerPassword);
        getPlayerData();
    }
    Player(String playerName, String playerPassword, int playerStatus){
        setUserName(playerName);
        setPassword(playerPassword);
        setStatus(playerStatus);
        getPlayerData();
    }
    Player(String playerName , String playerPassword, int playerStatus, int playerScore){
        setUserName(playerName);
        setPassword(playerPassword);
        setScore(playerScore);
        getPlayerData();
    }
    Player(String n, int st, int sc){
        this.userName = n;
        this.status = st;
        this.score = sc;
        this.invitePlayer = new Button("Invite");
        this.invitePlayer.setStyle("-fx-border-color: #000000; -fx-border-width: 2px; fx-text-fill: red; -fx-background-color: #FFFFFF");
        this.invitePlayer.setId(userName);
        this.invitePlayer.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(invitePlayer.getId());
            }
        });
    }
    Player(Player copyPlayer){
        setUserName(copyPlayer.getUserName());
        setPassword(copyPlayer.getPassword());
        setStatus(copyPlayer.getStatus());
        setScore(copyPlayer.getScore());
    }
    private  Player(ResultSet playerData) {
        try {
            setUserName(playerData.getString(1));
            setStatus(playerData.getInt(2));
            setScore(playerData.getInt(3));
        }catch (SQLException e){
            System.out.println("inside Player(ResultSet playerData) constructor: "+e);
        }
    }
    private void getPlayerData(){
        if(isPlayer()){
            try {
                ResultSet playerData = Db.getPlayer(userName);
                if(playerData.first()) {
                    setStatus(playerData.getInt(3));
                    setScore(playerData.getInt(4));
                    setInvitePlayer();
                }
            } catch (SQLException e) {
                System.out.println("inside Player.getPlayerData function: " + e);
            }
        }
    }
    public void setUserName(String playerName){
        if(playerName != null && ! playerName.trim().equals(""))
            userName=playerName;
    }
    public void setPassword(String playerPass){
        if(playerPass != null && !playerPass.equals(""))
            password=playerPass;
    }
    public void setStatus(int playerStatus){
        if(playerStatus == 0 || playerStatus == 1)
            status=playerStatus;
        else
            status=0;
    }
    public void setScore(int playerScore){
        score = Math.max(playerScore, 0);
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public int getStatus(){
        return status;
    }
    public int getScore(){
        return score;
    }
    public boolean isPlayer() { return Db.isDbConnected() && Db.isPlayer(userName); }

    public static String hashPassword(String password) {
        String passwordHashed = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            passwordHashed = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("inside the hashPassword function " + e);
        }
        return passwordHashed;
    }

    public boolean checkPassword() {
        return Db.isDbConnected() && hashPassword(password).equals(Db.getPlayerHashPassword(this.userName));
    }
    public boolean signUp(){
        if (password == null || password.equals(""))
            return false;
        return !isPlayer() && Db.signUp(userName,hashPassword(password));
    }
    public boolean isLogin(){
        return status == 1;
    }
    public boolean login()
    {
        if(isPlayer() && !isLogin() && checkPassword() && Db.login(userName)) {
            status =1;
            return true;
        }
        return false;
    }

    public boolean logout()
    {
        if(isPlayer() && isLogin() && Db.logout(userName)) {
            status =0;
            return true;
        }
        return false;
    }

    public boolean addFriend(String friendName){
        Player friend = new Player(friendName);
        return isPlayer() && isLogin() && friend.isPlayer() && !checkFriendShip(friendName)  && Db.addFriend(userName,friendName);
    }

    public boolean checkFriendShip(String friendName){
        Player friend = new Player(friendName);
        return isPlayer() && friend.isPlayer() && Db.checkFriendShip(userName , friendName);
    }
    // all online players
    public ArrayList<Player> getFriends(){
        ArrayList<Player> friends= new ArrayList<>();
        if (isPlayer()) {
            try{
                ResultSet friendsData = Db.getFriends(userName);
                if(friendsData.first()) {
                    Player friend = new Player(friendsData);
                    friends.add(friend);
                    while (friendsData.next()) {
                        friend = new Player(friendsData);
                        friends.add(friend);
                    }
                }
            }
            catch (SQLException e){
                System.out.println("inside player.getFriends function: " + e);
            }
        }
        return friends;
    }
    public void setInvitePlayer() {
        this.invitePlayer = new Button("Invite");
        this.invitePlayer.setId(userName);
        this.invitePlayer.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("saba7 el foll");
            }
        });
    }
    public Button getInvitePlayer() {
        return invitePlayer;
    }
}
