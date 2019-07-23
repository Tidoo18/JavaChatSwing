package com.chat.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import org.junit.Test;

public class ServerTest {
	Server test;

	@Test
	public void test_startRunnin() {

		test = new Server();
		test.startRunnin();

	}
}
