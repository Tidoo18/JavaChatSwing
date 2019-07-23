package com.chat.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
	Client test;

	@Before
	public void setUp() {
		test = new Client("127.0.0.1");
	}

	@Test
	public void test_startRunnin() {

		try {
			test.startRunning();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		System.out.println("Method testing finished");
	}

}
