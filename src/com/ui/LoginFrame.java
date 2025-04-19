package com.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPasswordField passfield;
	private JTextField userfield;
	
	public LoginFrame() {
		setTitle("Admin Login");
		setSize(350,200);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3,2,10,10));
		
		JLabel userlabel = new JLabel("Username:");
		JLabel passlabel = new JLabel("Password:");
		
		userfield = new JTextField();
		passfield = new JPasswordField();
		JButton loginbtn = new JButton("Login");
		
		add(userlabel);
		add(userfield);
		add(passlabel);
		add(passfield);
		add(new JLabel());
		add(loginbtn);
		
		loginbtn.addActionListener(e -> Authenticate());
		
	}

	private void Authenticate() {
		
		String user = userfield.getText();
		String pass = new String(passfield.getPassword());
		
		if(user.equals("admin") && pass.equals("admin@123")) {
		
		JOptionPane.showMessageDialog(this, "Welcome Admin");
		
		new DashboardFrame().setVisible(true);
		dispose();
		}else {
			JOptionPane.showMessageDialog(this, "Invalid Credentials.");
		}
		
	}

}
