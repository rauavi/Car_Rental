package com.Client;

import com.ui.LoginFrame;

public class CarRentalSystem {

	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(()->{
			new LoginFrame().setVisible(true);
		});

	}

}
