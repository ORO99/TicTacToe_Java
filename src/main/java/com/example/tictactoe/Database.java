package com.example.tictactoe;


import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ahmedsalah
 */
public class Database {

    Hashtable<String, String> databaseinfo = readSplitingData();
    private String DB_Name;
    private String DRIVER;
    private String USER;
    private String PASS;
    private String DB_URL;
    Connection con;
    Statement stmt;
    ResultSet rs;

    public Database() {
        if (!databaseinfo.isEmpty()) {
            DB_Name = databaseinfo.get("databaseName");
            DRIVER = databaseinfo.get("driver");
            USER = databaseinfo.get("userName");
            PASS = databaseinfo.get("pass");
            DB_URL = databaseinfo.get("databaseURL") + "/" + DB_Name;

            try {
                Class.forName(DRIVER);
                con = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("INSIDE The connect function" + e);
            }
        }
    }

    public static Hashtable<String, String> readSplitingData() {
        Hashtable<String, String> databaseinfo = new Hashtable<String, String>();
        try {
            File databaseInfoFile = new File("database Info File.txt");
            Scanner myReader = new Scanner(databaseInfoFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.split("=").length == 2) {
                    databaseinfo.put(data.split("=")[0], data.split("=")[1]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return databaseinfo;
    }

    public void connect() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("INSIDE The connect function" + e);
        }
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("inside the disconnect function " + e);
        }
    }

    public ResultSet runquery(String query) {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return rs;
    }

    public void signUp(String userName, String password) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into players (userName, password , status) values (?,?,0)");
            ps.setString(1, userName);
            ps.setString(2, hashPassword(password));
            int i = ps.executeUpdate();
            System.out.println("you insert " + i);
        } catch (SQLException e) {
            System.out.println("inside the signUp function " + e);
        }
        disconnect();
        connect();
    }

    public boolean login(String userName, String password) {
        try {
            PreparedStatement ps = con.prepareStatement("select * from players where userName =? and password =? ;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userName);
            ps.setString(2, hashPassword(password));
            ResultSet userData = ps.executeQuery();
            if (!userData.first()) {
                return false;
            } else {
                ps = con.prepareStatement("update players set status =1 where userName =? ;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, userName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("inside the signUp function " + e);
        }
        disconnect();
        connect();
        return true;
    }
    private String hashPassword(String password) {
        String Passwordhashed = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            Passwordhashed = sb.toString();
        } catch (NoSuchAlgorithmException e) {

        }
        return Passwordhashed;
    }

    public boolean checkPasswordValue(String password, String passwordhashed) {
        return hashPassword(password).equals(passwordhashed);
    }
}