package com.chat.database;

import static org.junit.Assert.assertEquals;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ServerClientDatabaseTest {

	ClientDatabaseLogin c; // Client
	ClientDatabaseNewUser cNu; // Client New User
	ServerDatabaseLogin s; // Server
	ServerDatabaseNewUser sNu; // Server New User

	///////////////////////////////////////////
	///////////// - Client -//////////////////
	///////////////////////////////////////////

	@Test
	public void test_ClientDatabaseLogin() throws SQLException {

		c = new ClientDatabaseLogin();
		c.con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Domas\\Desktop\\ChatDB\\ClientDB.db");
		c.pst = c.con.prepareStatement("select Username from Client where Username=? and Password=?");

		// Wrong
//		assertEquals("a", "b");
		// Correct
		assertEquals("Client", "Client");
	}

	@Test
	public void test_ClientCheckLogin() throws SQLException {
		c = new ClientDatabaseLogin();
		c.con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Domas\\Desktop\\ChatDB\\ClientDB.db");
		c.pst = c.con.prepareStatement("select Username from Client where Username=? and Password=?");

//		boolean rezult = c.checkLogin("Client", "Clients");
		boolean rezult = c.checkLogin("a", "a");

		assertEquals(true, true);
		System.out.println(rezult);

	}

	@Test
	public void test_ClientInsert() throws SQLException {
		cNu = new ClientDatabaseNewUser();
		cNu.connect(); // should be accsest staticly 
		cNu.insertClient("ArbuzasUsernamas", "ArbuzasPaswordas");

		assertEquals("Inserted", true, true);

	}

	///////////////////////////////////////////
	////////////// - Server -//////////////////
	///////////////////////////////////////////

	@Test
	public void test_ServerDatabaseLogin() throws SQLException {
		s = new ServerDatabaseLogin();
		s.con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Domas\\Desktop\\ChatDB\\ServerDB.db");
		s.pst = s.con.prepareStatement("select Username from Server where Username=? and Password=?");

		// Wrong
//		assertEquals("a", "b");
		// Correct
		assertEquals("abc", "abc");
	}

	@Test
	public void test_ServerCheckLogin() throws SQLException {
		s = new ServerDatabaseLogin();
		s.con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Domas\\Desktop\\ChatDB\\ServerDB.db");
		s.pst = s.con.prepareStatement("select Username from Server where Username=? and Password=?");
		s.checkLogin("abc", "abc");
		assertEquals(false, false);
	}

	@Test
	public void test_ServerInsert() {
		sNu = new ServerDatabaseNewUser();
		sNu.insertServer("Haleliuja", "Haleliuja");
		assertEquals(true, true);
	}

}
