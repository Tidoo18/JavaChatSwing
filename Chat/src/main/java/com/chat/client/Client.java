package com.chat.client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.chat.database.ClientDatabaseLogin;

@SuppressWarnings("serial")
public class Client extends JFrame {

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;

	JButton buttonLogin;
	JPanel loginPanel;
	JTextField userName;
	JButton newUSer;
	JLabel username;
	JLabel password;
	JPasswordField pass;
	ClientDatabaseLogin db;

	public Client() {

	}

	public Client(String host) {
		super("Client Login Authentication");

		buttonLogin = new JButton("Login");
		loginPanel = new JPanel();
		userName = new JTextField(15);
		pass = new JPasswordField(15);
		newUSer = new JButton("New User");
		username = new JLabel("User - ");
		password = new JLabel("Pass - ");

		db = new ClientDatabaseLogin();

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

		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendData(event.getActionCommand());
				userText.setText("");
			}
		});

		JFrame frame = new JFrame("Client Message Window");
		chatWindow = new JTextArea();
		frame.add(userText, BorderLayout.SOUTH);
		frame.add(chatWindow, BorderLayout.CENTER);
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
				new NewUserClient();
				dispose();

			}
		});
	}

	public void startRunning() throws IOException {

		try {
			connectToServer();
			setUpStream();
			whileChatting();
		} catch (EOFException eof) {
			showMessage("\n Client terminated connection.");
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			closeEverything();
		}
	}

	private void connectToServer() throws IOException {
		showMessage(" Attempting to connect...\n");
		connection = new Socket(InetAddress.getByName(serverIP), 9999);
		showMessage(" Connected to " + connection.getInetAddress().getHostName());
	}

	private void setUpStream() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are setup! \n");
	}

	private void whileChatting() throws IOException {
//			ableToType(loginCheck.entry == true);
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch (ClassNotFoundException CNF) {
				showMessage("\n Class Not Found");
			} // chat is in objects
		} while (!message.equals("Server - END"));
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

	private void sendData(String message) {
		try {
			output.writeObject("Client - " + message);
			output.flush();
			showMessage("\nClient - " + message);
		} catch (IOException io) {
			chatWindow.append("/n Error: Could'n send message.");

		}
	}

	// Update chatWindow
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(text);
			}
		});
	}

	private void ableToType(final boolean write) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(write);
			}
		});
	}
}