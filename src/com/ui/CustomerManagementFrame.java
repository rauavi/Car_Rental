package com.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.database.DBConnection;

public class CustomerManagementFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private  JTextField namefield,phonefield,emailfield,licencefield;
	private JButton addbutton;
	private JTable customertable;
	private DefaultTableModel tablemodel;
	
	public CustomerManagementFrame(){
		setTitle("Customer Management");
		setSize(600,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		JPanel formpanel = new JPanel(new GridLayout(5,2,10,10));
		formpanel.setBorder(BorderFactory.createTitledBorder("Add New Customer"));
		
		formpanel.add(new JLabel("Name:"));
		namefield = new JTextField();
		formpanel.add(namefield);
		
		formpanel.add(new JLabel("Phone"));
		phonefield = new JTextField();
		formpanel.add(phonefield);
		
		formpanel.add(new JLabel("Email:"));
		emailfield = new JTextField();
		formpanel.add(emailfield);
		
		formpanel.add(new JLabel("License No:"));
		licencefield = new JTextField();
		formpanel.add(licencefield);
		
		addbutton = new JButton("Add Customer");
		formpanel.add(addbutton);
		
		add(formpanel,BorderLayout.NORTH);
		
		String[] columns = {"ID","Name","Phone","Email","License"};
		
		tablemodel = new DefaultTableModel(columns,0);
		customertable = new JTable(tablemodel);
		JScrollPane scrollpane = new JScrollPane(customertable);
		add(scrollpane,BorderLayout.CENTER);
		
		addbutton.addActionListener(e->{
			try {
				addCustomer();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		loadCustomer();
		
	}
	
	private void addCustomer() throws SQLException
	{
		
		String name = namefield.getText();
		String phone = phonefield.getText();
		String email = emailfield.getText();
		String license = licencefield.getText();
		
		try(Connection conn = DBConnection.getConnection())
		{
			String sql = "INSERT INTO customers(name,phone,email,license_no) VALUES (?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, phone);
			statement.setString(3, email);
			statement.setString(4, license);
			statement.executeUpdate();
			
			JOptionPane.showMessageDialog(this, "Customer Added Successfully");
			
			namefield.setText("");
			phonefield.setText("");
			emailfield.setText("");
			licencefield.setText("");
			
			loadCustomer();
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
		}
	}
	
	private void loadCustomer() {
		
		tablemodel.setRowCount(0);
		
		try (Connection conn = DBConnection.getConnection()){
			
			String sql = "SELECT * FROM customers";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Object[] row = {rs.getInt("id"),rs.getString("name"),
						rs.getString("phone"),rs.getString("email"),rs.getString("license_no")};
				tablemodel.addRow(row);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Failed to Load Customer: "+ e.getMessage());
		}
	}
	
	

}
