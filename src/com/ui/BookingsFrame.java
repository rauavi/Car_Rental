package com.ui;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.database.DBConnection;

public class BookingsFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JComboBox<String> carbox,customerbox;
	private JTextField startdatefield,enddatefield;
	private JButton rentbutton;

	public BookingsFrame() {
		setTitle("Car Booking");
		setSize(500,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5,2,10,10));
		
		add(new JLabel("Select Car: "));
		carbox = new JComboBox<String>();
		add(carbox);
		
		add(new JLabel("Select Customer: "));
		customerbox = new JComboBox<String>();
		add(customerbox);
		
		add(new JLabel("Start Date (YYYY-MM-DD):"));
		startdatefield = new JTextField();
		add(startdatefield);
		
		add(new JLabel("End Date (YYYY-MM-DD):"));
		enddatefield = new JTextField();
		add(enddatefield);
		
		rentbutton = new JButton("Book Car");
		add(rentbutton);
		
		rentbutton.addActionListener(e->bookCar());
		loadCars();
		loadCustomers();
		
	}
	
	private void loadCars() {
		
		try (Connection conn = DBConnection.getConnection())
		{
			String sql = "SELECT id,brand,model FROM cars WHERE available = true";
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String label = id + "-"+rs.getString("brand")+" "+rs.getString("model");
				carbox.addItem(label);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadCustomers() {
		try (Connection conn = DBConnection.getConnection()){
			
			String sql = "select id, name from customers";
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				customerbox.addItem(id+"-"+rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void bookCar() {
		
		
		try {
			int carid = Integer.parseInt(carbox.getSelectedItem().toString().split("-")[0]);
			int custid = Integer.parseInt(customerbox.getSelectedItem().toString().split("-")[0]);
			
			LocalDate start = LocalDate.parse(startdatefield.getText());
			LocalDate end = LocalDate.parse(enddatefield.getText());
			
			long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
			
			if(days <= 0) {
				JOptionPane.showMessageDialog(this, "End Date Must Be After Start Date.");
				return;
			}
			
			//get car price
			
			double price = 0;
			try(Connection conn = DBConnection.getConnection()){
				String sql = "SELECT price_per_day FROM cars WHERE id=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, carid);
				ResultSet rs = statement.executeQuery();
				if(rs.next()) {
					price = rs.getDouble("price_per_day");
				}
			}
			double total = price * days;
			
			//insert booking
			
			try (Connection conn = DBConnection.getConnection()){
				
				conn.setAutoCommit(false);
				String insert = "INSERT INTO rentals (car_id, customer_id,rent_date,"
						+ " return_date, total_cost) VALUES (?,?,?,?,?)";
				
				PreparedStatement statement = conn.prepareStatement(insert);
				statement.setInt(1, carid);
				statement.setInt(2, custid);
				statement.setDate(3, Date.valueOf(start));
				statement.setDate(4, Date.valueOf(end));
				statement.setDouble(5, total);
				statement.executeUpdate();
				
				
				String update = "UPDATE cars SET available = false WHERE id = ?";
				
				PreparedStatement up = conn.prepareStatement(update);
				up.setInt(1, carid);
				up.executeUpdate();
				conn.commit();
				JOptionPane.showMessageDialog(this, "Booking Successful. Total Price: $" + total);		
				
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,"Error: " + e.getMessage());
			} 
		
	}catch (Exception e) {
	
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(this, "Unexpected Error: " + e.getMessage());
	}

	}
	
	
}
