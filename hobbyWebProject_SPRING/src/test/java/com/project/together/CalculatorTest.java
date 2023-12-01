package com.project.together;

import java.sql.Connection;
import java.sql.DriverManager;

public class CalculatorTest {
	public static void main(String[] args) {
	}
	private Connection conn;
	public CalculatorTest(){
		try {
		 	String dbURL = "jdbc:mysql://localhost:3306/hobbyWebProject?useUnicode=true&characterEncoding=UTF-8";
		 	String dbID = "root";
		 	String dbPassword = "9228";
		 	Class.forName("com.mysql.jdbc.Driver");
		 	conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			System.out.println("연결 성공: " + conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
