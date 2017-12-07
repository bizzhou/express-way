package com.expressway.util;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;

import static java.lang.System.out;

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

            out.println("Connection established.............");
            return dbConnection;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Close JDBC connection
     *
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

    public void backUp() throws IOException, InterruptedException {

        String executeCmd = "mysqldump -u "+ username + " -p" + password + " " + "express_way" + " -r " + "./d.sql";
        System.out.println(executeCmd);

        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();
        if(processComplete == 0){
            System.out.println("Backup done");
        } else {
            System.out.println("Error");
        }


    }



}
