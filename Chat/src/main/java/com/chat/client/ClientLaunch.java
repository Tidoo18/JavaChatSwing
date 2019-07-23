package com.chat.client;

import java.io.IOException;

import javax.swing.JFrame;

public class ClientLaunch {
	public static void main(String[] args) {

		Client clientLaunch; // localhost IP Adress
		clientLaunch = new Client("127.0.0.1");
		clientLaunch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			clientLaunch.startRunning();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
