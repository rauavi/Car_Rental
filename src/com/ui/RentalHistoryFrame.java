package com.ui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.database.DBConnection;

public class RentalHistoryFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTable rentaltable;
	private DefaultTableModel tablemodel;
	
	public RentalHistoryFrame() {
		setTitle("Rental History");
		setSize(900,500);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		String[] columns= {"Rental Id","Car Id","Customer Id","Start Date","End Date","Total Price","Available"};
		
		tablemodel =new DefaultTableModel(columns,0);
		rentaltable = new JTable(tablemodel);
		JScrollPane scrollpane = new JScrollPane(rentaltable);
		
		add(scrollpane,BorderLayout.CENTER);
		
		loadAllrentals();
	}

	private void loadAllrentals() {
		
		tablemodel.setRowCount(0);
		
		try(Connection conn = DBConnection.getConnection()){
			String sql = "Select r.id,r.car_id,r.customer_id,r.rent_date,r.return_date,r.total_cost,c.available FROM rentals r JOIN cars c ON r.car_id = c.id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Object[] row = {
						rs.getInt("id"),rs.getInt("car_id"),rs.getInt("customer_id"),
						rs.getDate("rent_date"),rs.getDate("return_date"),rs.getDouble("total_cost"),rs.getBoolean("available") ? "yes": "no"
				};
				tablemodel.addRow(row);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error Loading Rental History");
		}
		
	}

}
