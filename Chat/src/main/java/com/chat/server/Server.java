package com.chat.server;

import java.io.*;
import java.net.*;

import javax.swing.*;

import com.chat.database.ServerDatabaseLogin;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Server extends JFrame {

	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private JTextField userText;
	private JTextArea chatWindow;

	JButton buttonLogin;
	JPanel loginPanel;
	JTextField userName;
	JButton newUSer;
	JLabel username;
	JLabel password;
	JPasswordField pass;
	ServerDatabaseLogin db;

	// constructor
	public Server() {
		super("Server Login Authentication");

		buttonLogin = new JButton("Login");
		loginPanel = new JPanel();
		userName = new JTextField(15);
		pass = new JPasswordField(15);
		newUSer = new JButton("New User");
		username = new JLabel("User - ");
		password = new JLabel("Pass - ");

		db = new ServerDatabaseLogin();

		setSize(300, 200);
		setLocation(500, 280);
		loginPanel.setLayout(null);

		userName.setBounds(70, 30, 150, 20);
		pass.setBounds(70, 65, 150, 20);
		buttonLogin.setBounds(110, 100, 80, 20);
		newUSer.setBounds(110, 135, 80, 20);
		username.setBounds(20, 28, 80, 20);
		password.setBounds(20, 63, 80, 20);

		loginPanel.add(buttonLogin);
		loginPanel.add(userName);
		loginPanel.add(pass);
		loginPanel.add(newUSer);
		loginPanel.add(username);
		loginPanel.add(password);

		getContentPane().add(loginPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JFrame frame = new JFrame("Server Messanger Window");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage(e.getActionCommand());
				userText.setText("");
			}

		});

		chatWindow = new JTextArea();
		frame.add(userText, BorderLayout.SOUTH);
		frame.add(chatWindow);
		frame.setSize(500, 300);

		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] temp_pwd = pass.getPassword();
				String pwd = null;
				pwd = String.copyValueOf(temp_pwd);

				if (db.checkLogin(userName.getText(), pwd) == true) {
					setVisible(false);
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Login failed! Please try again! ", "Failed!!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		newUSer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewUserServer();
				dispose();

			}
		});
	}

	public void startRunnin() {
		try { // port - backport(20 can wait)
			server = new ServerSocket(9999, 20);
			while (true) {
				try {
					waitForConnection();
					setUpStream();
					whileChatting();
				} catch (EOFException eof) {
					showMessage("\n Server ended the connection!");
				} finally {
					closeEverything();
				}
			}
		} catch (Exception io) {
			io.printStackTrace();
		}
	}

	// wait for Connection
	private void waitForConnection() throws IOException {
		try {
			showMessage(" Waiting for someone to connect...\n");
			connection = server.accept(); // returns String , will look 1212354 somethign like this
			showMessage(" Connected to " + connection.getInetAddress().getHostName());
		} catch (Exception aa) {
			aa.printStackTrace();

		}
	}

	// get stream to send and receive data
	private void setUpStream() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are setup! \n");
	}

	// during the chat conversation
	private void whileChatting() throws IOException {
		String message = " Your are now connected! ";
		sendMessage(message);
		// allos user to chat
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch (ClassNotFoundException CE) {
				showMessage("\n Class Not Found");
			} // chat is in objects
		} while (!message.equals("Client - END"));
	}

	// close everthing(Streams and sockets after we are done).
	private void closeEverything() throws IOException {
		showMessage("\n Closing connections... \n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException io) {
			io.printStackTrace();

		}
	}

	// send messages to server
	private void sendMessage(String message) {
		try {
			output.writeObject("Server - " + message);
			output.flush();
			showMessage("\nServer - " + message);
		} catch (IOException io) {
			chatWindow.append("/n Error: Could'n send message.");

		}
	}

	// Update chatWindow
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					chatWindow.append(text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// able to write
	private void ableToType(final boolean write) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(write);
			}
		});
	}

}