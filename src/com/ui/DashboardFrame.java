package com.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DashboardFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public DashboardFrame(){
		
		setTitle("Car Rental Management System");
		setSize(500,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1,15,15));
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
		Font btnfont = new Font("Segoe UI", Font.BOLD,16);
		
		JButton manageCarsBtn = new JButton("Manage Cars");
		JButton managecustomersbtn = new JButton("Manage Customers");
		JButton bookingbtn = new JButton("Book Car");
		JButton returnbtn = new JButton("Return Car");
		JButton historybtn = new JButton("Rental History");
		
		for(JButton btn: new JButton[] {
			manageCarsBtn,managecustomersbtn,bookingbtn,returnbtn,historybtn
		}) {
			btn.setFont(btnfont);
			btn.setBackground(new Color(40,150,200));
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			
			btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
			panel.add(btn);
		}
		add(panel);
		
		manageCarsBtn.addActionListener(e -> {
			try {
				new CarManagementFrame().setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		managecustomersbtn.addActionListener(e -> new CustomerManagementFrame().setVisible(true));
		bookingbtn.addActionListener(e-> new BookingsFrame().setVisible(true));
		returnbtn.addActionListener(e -> new ReturnCarFrame().setVisible(true));
		historybtn.addActionListener(e -> new RentalHistoryFrame().setVisible(true));
		
	}

	

}
