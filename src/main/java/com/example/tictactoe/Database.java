package com.example.tictactoe;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Scanner;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class Database {
    private String DRIVER;
    private String USER;
    private String PASS;
    private String DB_URL;
    Connection con;
    public Database() {
        Hashtable<String, String> databaseInfo = readDatabaseData();
        if (!databaseInfo.isEmpty()) {
            String DB_Name = databaseInfo.get("databaseName").trim();
            DRIVER = databaseInfo.get("driver").trim();
            USER = databaseInfo.get("userName").trim();
            PASS = databaseInfo.get("pass");
            DB_URL = databaseInfo.get("databaseURL").trim() + "/" + DB_Name;
            try {
                connect();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static Hashtable<String, String> readDatabaseData() {
        Hashtable<String, String> databaseData = new Hashtable<>();
        try {
            File databaseInfoFile = new File("database Info File.txt");
            Scanner myReader = new Scanner(databaseInfoFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.split("=").length == 2) {
                    databaseData.put(data.split("=")[0], data.split("=")[1]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("inside readDatabaseData : "+e);
        }
        return databaseData;
    }

    public boolean connect() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("INSIDE The connect function" + e);
            return false;
        }
    }

    public boolean disconnect() {
        if(isDbConnected()){
            try {
                con.close();
                return true;
            } catch (SQLException e) {
                System.out.println("inside the disconnect function " + e);
            }
        }
        return false;
    }

    public  void reconnect(){ //return 0 false if it can't connect again true if connect
        disconnect();
        connect();
    }

    public boolean isDbConnected() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException ignored) {
            System.err.println("no database connection");
            return false;
        }
    }
    public boolean isPlayer(String userName){
        try {
            PreparedStatement ps = con.prepareStatement("select * from players where userName =? ;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userName);
            ResultSet userData = ps.executeQuery();
            return userData.first();
        }
        catch (SQLException e) {
            System.out.println("inside the isPlayer function " + e);
        }
        return false;
    }
    public ResultSet getPlayer(String userName){
        ResultSet userData = null;
        try {
            PreparedStatement ps = con.prepareStatement("select * from players where userName =? ;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userName);
            userData = ps.executeQuery();
            userData.first();
        } catch (SQLException e) {
            System.out.println("inside the signUp function " + e);
        }
        return userData;
    }
    public boolean signUp(String userName, String password) {// return -1 if disconnect 0 is username already found in DB 1 if he signed up successfully
        try {
            PreparedStatement ps = con.prepareStatement("insert into players (userName, password) values (?,?)");
            ps.setString(1, userName);
            ps.setString(2, password);
            int i = ps.executeUpdate();
            System.out.println("you insert " + i +" player in signUp function");
            reconnect();
            return true;
        }catch (SQLException e) {
            System.out.println("inside the signUp function " + e);
        }
        return false;
    }

    public boolean isLogin(String userName){
        try {
            PreparedStatement ps = con.prepareStatement("select status from players where userName =? ;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userName);
            ResultSet userData = ps.executeQuery();
            userData.first();
            if (userData.getInt(1)==1)
                return true;
        } catch (SQLException e) {
            System.out.println("inside the signUp function " + e);
        }
        return false;
    }

    public boolean login(String userName) {
        try {
            PreparedStatement ps = con.prepareStatement("update players set status =1 where userName =? ;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userName);
            ps.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.out.println("inside the signUp function " + e);
        }
        reconnect();
        return false;
    }
    public String getPlayerHashPassword(String userName){
        try {
            ResultSet playerData = getPlayer(userName);
            if(playerData.first())
                return playerData.getString(2);
        }catch (SQLException e)
        {
            System.out.println("inside getPlayerPassword function: " + e);
        }
        return null;
    }
    public boolean logout(String userName){
        try {
            PreparedStatement ps = con.prepareStatement("update players set status =0 where userName =? ;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userName);
            if(ps.executeUpdate()>0) {
                System.out.println("logout  : " + ps.executeUpdate());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("inside the logout function " + e);
        }
        return false;
    }

    public boolean addFriend(String userName ,String friendName) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into friends values ( ? , ? ) ;");
            ps.setString(1, userName);
            ps.setString(2, friendName);
            int i = ps.executeUpdate();
            System.out.println("you add in friends table : " + i);
            if(i>0)
                return true;
        }catch (SQLException e) {
            System.out.println("inside the addFriends : " + e );
        }
        return false;
    }

    public boolean checkFriendShip(String playerName , String friendName){
        try {
            PreparedStatement ps = con.prepareStatement("select * from friends " +
                    "where (playerName = ? and friendName =?)" +
                    " or ( friendName = ? and playerName =?) ;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, playerName);
            ps.setString(2, friendName);
            ResultSet friendShip = ps.executeQuery();
            return friendShip.first();
        } catch (SQLException e) {
            System.out.println("inside the checkFriendShip : " + e);
        }
        return false;
    }

    public ResultSet getFriends(String userName) {
        ResultSet friendsData = null;
        try {
            PreparedStatement ps = con.prepareStatement("select userName,status,score from players " +
                    "where userName = any(select playerName from " +
                    "(select * from friends where playerName =? or friendName =?) as s" +
                    " where playerName != ? union " +
                    "select friendName from" +
                    " (select * from friends where playerName =? or friendName =?) as s" +
                    " where friendName != ?)" +
                    "order by score desc;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            for(int i = 1 ; i<=6;i++)
                ps.setString(i, userName);
            friendsData = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("inside the getFriends" + e);
        }
        return friendsData;
    }

    public ResultSet getPlayers() { //return null if DB not connect and list of players name ,status and score ordered by sore
        ResultSet playersData = null;
        if (isDbConnected()) {
            try {
                PreparedStatement ps = con.prepareStatement("select userName,status,score from players order by score desc;", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                playersData = ps.executeQuery();
            } catch (SQLException e) {
                System.out.println("inside the getPlayers" + e);
            }
        }
        return playersData;
    }
}
