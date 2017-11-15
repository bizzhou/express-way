package com.expressway.util;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ConnectionUtil {

    private static Connection dbConnection = null;

    private static final String host = "jdbc:mysql://localhost:3306/express_way?autoReconnect=true&useSSL=false";
    private static final String username = "expressway";
    private static final String password = "expressway";
    private static final String driver = "com.mysql.jdbc.Driver";

    public Connection getConn() {
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

    /**
     * Close JDBC connection
     * @param connection
     * @param preparedStatement
     * @param statement
     * @param resultSet
     */
    public void close(Connection connection, PreparedStatement preparedStatement,
                             Statement statement, ResultSet resultSet) {

        try {
            if (connection != null)
                connection.close();

            if (preparedStatement != null)
                preparedStatement.close();

            if (statement != null)
                statement.close();

            if (resultSet != null)
                resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
