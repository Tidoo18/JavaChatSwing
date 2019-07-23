package com.chat.client;

import javax.swing.*;

import com.chat.database.ClientDatabaseNewUser;

import java.awt.event.*;

@SuppressWarnings("serial")
public class NewUserClient extends JFrame {

	ClientDatabaseNewUser insert = new ClientDatabaseNewUser();

	JButton create;
	JPanel newUserPanel;
	JTextField user;
	JPasswordField pass;
	JLabel username;
	JLabel password;
	
	public NewUserClient() {
		super("Client Registration");

		create = new JButton("Create");
		newUserPanel = new JPanel();
		user = new JTextField(15);
		pass = new JPasswordField(15);
		username = new JLabel("User - ");
		password = new JLabel("Pass - ");
		
		setSize(300, 200);
		setLocation(500, 280);
		newUserPanel.setLayout(null);

		user.setBounds(70, 30, 150, 20);
		pass.setBounds(70, 65, 150, 20);
		create.setBounds(110, 100, 80, 20);
		username.setBounds(20, 28, 80, 20);
		password.setBounds(20, 63, 80, 20);
		
		newUserPanel.add(create);
		newUserPanel.add(user);
		newUserPanel.add(pass);
		newUserPanel.add(username);
		newUserPanel.add(password);
		
		getContentPane().add(newUserPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		create.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				String username = "";
				String password = "";

				username = user.getText().trim();
				password = pass.getText().trim();

				if (username.equals("") && password.equals("")) {
					JOptionPane.showMessageDialog(null, "Please insert Username and Password");
				} else {
					insert.insertClient(password, password);
					JOptionPane.showMessageDialog(null, "Account has been created.");
					dispose();
					System.exit(0);
				}
			}
		});
	}

	public static void main(String[] args) {
		new NewUserClient();
	}
}