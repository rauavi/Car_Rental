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

public class CarManagementFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextField brandfield,modelfield,regNofield,pricefield;
	private JButton addbutton;
	private JTable cartable;
	private DefaultTableModel tablemodel;
	
	public CarManagementFrame() throws SQLException 
	{
		setTitle("Car Management");
		setSize(400,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		JPanel panel=new JPanel(new GridLayout(5,2,10,10));
		
		panel.setBorder(BorderFactory.createTitledBorder("Add New Car"));
		
		add(panel,BorderLayout.NORTH);
		
		panel.add(new JLabel("Brand:"));
		brandfield = new JTextField();
		panel.add(brandfield);
		
		panel.add(new JLabel("Model:"));
		modelfield = new JTextField();
		panel.add(modelfield);
		
		panel.add(new JLabel("Registration No:"));
		regNofield = new JTextField();
		panel.add(regNofield);
		
		panel.add(new JLabel("Price Per Day:"));
		pricefield = new JTextField();
		panel.add(pricefield);
		
		addbutton = new JButton("Add Car");
		panel.add(addbutton);
		
		
		String[] columns = {"ID","Brand", "Model", "Reg No", "Available", "Price/Day"};
		tablemodel = new DefaultTableModel(columns,0);
		cartable = new JTable(tablemodel);
		JScrollPane scrollpane = new JScrollPane(cartable);
		
		add(scrollpane, BorderLayout.CENTER);
		
		addbutton.addActionListener(e->addcar());
		
		loadCars();
		
	}
	
	private void addcar() {
		
		String brand = brandfield.getText();
		String model = modelfield.getText();
		String regNo = regNofield.getText();
		double price = Double.parseDouble(pricefield.getText());
		
		try	(Connection conn = DBConnection.getConnection())
		{
				String sql="INSERT INTO cars(brand,model,registration_no,price_per_day)"
						+ " VALUES (?,?,?,?)";
				
				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setString(1, brand);
				statement.setString(2, model);
				statement.setString(3, regNo);
				statement.setDouble(4, price);
				
				statement.executeUpdate();
				
				JOptionPane.showMessageDialog(this, "Car Added Successfully!!");				
		} catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Error: "+e.getMessage());
		}
		
	}
	
	private void loadCars() throws SQLException {
		tablemodel.setRowCount(0);
		
		try(Connection conn = DBConnection.getConnection()){
			String sql= "SELECT * FROM cars";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs=statement.executeQuery();
			
			while(rs.next()) {
				Object[] row = {
						rs.getInt("id"),rs.getString("brand"),rs.getString("model")
						,rs.getString("registration_no"),rs.getBoolean("available"),
						rs.getDouble("price_per_day")
				};
				tablemodel.addRow(row);
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to Load Cars"+e.getMessage());
		}
	}
}
