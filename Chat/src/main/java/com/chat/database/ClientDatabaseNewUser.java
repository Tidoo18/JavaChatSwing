package com.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDatabaseNewUser {

	public static Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:C:\\Users\\Domas\\Desktop\\ChatDB\\ClientDB.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void insertClient(String Username, String Password) {
		String sql = "INSERT INTO Client (Username,Password) VALUES(?,?)";
		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, Username);
			pstmt.setString(2, Password);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}