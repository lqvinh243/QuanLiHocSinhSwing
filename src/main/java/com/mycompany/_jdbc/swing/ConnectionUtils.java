/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public final class ConnectionUtils {

    private static String _serverName = "localhost";
    private static Integer _port = 1433;
    private static String _databaseName = "QuanLyHocSinh";
    private static String _username = "sa";
    private static String _password = "123";
    private static Connection _conn = null;

    public static void Config(String serverName, int port, String databaseName, String username, String password) {
        _serverName = serverName;
        _port = port;
        _databaseName = databaseName;
        _username = username;
        _password = password;
    }

    public static void setServername(String serverName) {
        _serverName = serverName;
    }

    public static String getServername() {
        return _serverName;
    }

    public static void setPort(int port) {
        _port = port;
    }

    public static Integer getPort() {
        return _port;
    }

    public static String getDatabaseName() {
        return _databaseName;
    }

    public static void setDatabasename(String databaseName) {
        _databaseName = databaseName;
    }

    public static String getUsername() {
        return _username;
    }

    public static void setUsername(String username) {
        _username = username;
    }

    public static String getPassword() {
        return _password;
    }

    public static void setPassword(String password) {
        _password = password;
    }

    public static Connection getConnect() {
        if (_conn != null) {
            return _conn;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + _serverName + ":" + _port + ";instance=SQLSERVER;" + "databaseName=" + _databaseName + ";" + "user=" + _username + ";" + "password=" + _password;
            System.out.println(url);
            _conn = DriverManager.getConnection(url);
//            _conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QuanLyHocSinh;user=sa;password=123");
            if (_conn != null) {
                JOptionPane.showMessageDialog(null, "Connect success!");
            }
        } catch (Exception e) {
            e.printStackTrace();
             JOptionPane.showMessageDialog(null, "Connect fail!");
        }
        return _conn;
    }

}
