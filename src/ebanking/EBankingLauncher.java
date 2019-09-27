package ebanking;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import database.Customer;
import database.DBConnector;

public class EBankingLauncher extends JFrame implements ActionListener{

	private JLabel hello = new JLabel("Selmat datang di EBankingku!", SwingConstants.CENTER);
	private JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
	private JButton registerButton = new JButton("Daftar EBanking");
	private JButton loginButton = new JButton("Login EBanking");
	private JButton exitButton = new JButton("Keluar");
	
	private void initialize() {
		setTitle("EBankingku");
		setLayout(new GridLayout(2, 1, 10, 5));
		hello.setFont(hello.getFont().deriveFont(15.0f));
		registerButton.addActionListener(this);
		loginButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		registerButton.setMnemonic('r');
		loginButton.setMnemonic('l');
		exitButton.setMnemonic('k');
		
		buttons.add(registerButton);
		buttons.add(loginButton);
		buttons.add(exitButton);
		
		add(hello);
		add(buttons);
		pack();
		//setSize(400,300);
		setVisible(true);
	}
	
	public EBankingLauncher() {
		boolean retry = false;
		do {
			if (DBConnector.isOnline()) {
				initialize();
				break;
			} else {
				int select = JOptionPane.showOptionDialog(null, "Unable to connect to server!", "EBankingku", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
						null, new String[] {"Retry", "Cancel"}, JOptionPane.YES_OPTION);
				if (select == JOptionPane.YES_OPTION) {
					retry = true;
					continue;
				} else {
					retry = false;
					break;
				}
			}			
		} while (retry);
	}
	
	private void login() {
		Customer cust = VerificationPanel.obtainLoginUser();
		if (cust != null) {
			this.dispose();
			EBankingMainMenu menu = new EBankingMainMenu(cust, this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			RegistrationPanel.performUserRegistration(); 
		} else if (e.getSource() == loginButton) {
			login();
		} else if (e.getSource() == exitButton) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new EBankingLauncher();
	}

}
