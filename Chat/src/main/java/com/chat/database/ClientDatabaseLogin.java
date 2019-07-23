package com.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDatabaseLogin {

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public ClientDatabaseLogin() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Domas\\Desktop\\ChatDB\\ClientDB.db");
			pst = con.prepareStatement("select * from Client where Username=? and Password=?");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean checkLogin(String Username, String Password) {
		try {
			pst.setString(1, Username);
			pst.setString(2, Password);
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error while validating " + e);
			return false;
		}
	}
}
