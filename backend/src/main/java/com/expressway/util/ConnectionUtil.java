package com.expressway.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static Connection dbConnection = null;

    private static final String host = "jdbc:mysql://localhost:3306/express_way?autoReconnect=true&useSSL=false";
    private static final String username = "expressway";
    private static final String password = "expressway";
    private static final String driver = "com.mysql.jdbc.Driver";

    public static Connection getConnection() {

        try {

            Class.forName(driver);
            dbConnection = DriverManager.getConnection(host, username, password);

            System.out.println("Connection established.............");
            return dbConnection;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void closeConnection(Connection connection){

        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
