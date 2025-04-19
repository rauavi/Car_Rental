package com.ui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.database.DBConnection;

public class ReturnCarFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable rentaltable;
	private DefaultTableModel tablemodel;
	private JButton returnbutton;
	
	public ReturnCarFrame() {
		setTitle("Return Car");
		setSize(800,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//table setup
		
		String[] columns = {"Rental ID", "Car ID", "Customer ID", "Start Date","End Date","Total Price"};
		
		tablemodel = new DefaultTableModel(columns, 0);
		rentaltable = new JTable(tablemodel);
		JScrollPane scrollpane = new JScrollPane(rentaltable);
		add(scrollpane, BorderLayout.CENTER);
		
		//button
		
		returnbutton =new JButton("Mark as Returnrd");
		add(returnbutton,BorderLayout.SOUTH);
		
		returnbutton.addActionListener(e -> returnCar());
		
		loadRentals();
	}

	private void loadRentals() {
		tablemodel.setRowCount(0);
		try(Connection conn = DBConnection.getConnection()){
			String sql = "SELECT r.id, r.car_id, r.customer_id, r.rent_date, r.return_date, r.total_cost "
			           + "FROM rentals r JOIN cars c ON r.car_id = c.id "
			           + "WHERE c.available = 0";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Object[] row = {
						rs.getInt("id"),rs.getInt("car_id"),rs.getInt("customer_id"),rs.getDate("rent_date"),rs.getDate("return_date"),rs.getDouble("total_cost")
				};
				tablemodel.addRow(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Error Loading Rentals");
		}
		
	}
	
	private void returnCar() {
		int selectedRow = rentaltable.getSelectedRow();
		if(selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a rental to return.");
			return;
		}
		
		int carid = (int)tablemodel.getValueAt(selectedRow, 1);
		
		try (Connection conn = DBConnection.getConnection()){
			String sql = "UPDATE cars SET available = true WHERE id =?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, carid);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(this, "Car Marked As Returned.");
			loadRentals();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Return Failed.");
		}
		
	}

}
