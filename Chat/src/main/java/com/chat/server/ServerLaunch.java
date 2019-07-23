package com.chat.server;

import javax.swing.JFrame;

public class ServerLaunch {

	public static void main(String[] args) {
		try {
			Server a = new Server();
			a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			a.startRunnin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
