package com.team.expressway.expressway;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
	public static void main(String[] args) {
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/express_way?useSSL=false";
		String user = "expressway";
		String pass = "expressway";
		
		try{
			
			System.out.println("Connecting to database: " + jdbcUrl);
			
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			System.out.println("Connection successful");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
