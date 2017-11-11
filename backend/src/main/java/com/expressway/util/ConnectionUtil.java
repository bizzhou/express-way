package com.expressway.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {

    private static Connection dbConnection = null;

    private static final String host = "jdbc:mysql://localhost:3306/express_way?autoReconnect=true&useSSL=false";
    private static final String username = "expressway";
    private static final String password = "expressway";

    public static Connection getDbConnectoin() {

        if (dbConnection != null) {

            return dbConnection;

        } else {

            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(host, username, password);
                return con;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return dbConnection;
        }
    }

}
