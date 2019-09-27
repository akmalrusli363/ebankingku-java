package ebanking;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

import database.BankingTransactionException;
import database.Customer;
import database.CustomerQuery;

public class VerificationPanel extends JPanel {
	
	private JPanel labels = new JPanel(new GridLayout(2,1));
	private JPanel fillers = new JPanel(new GridLayout(2,1));
	private JLabel usernameLabel = new JLabel("Username: ");
	private JLabel passwordLabel = new JLabel("Password: ");
	private JTextField username = new JTextField();
	private JPasswordField password = new JPasswordField();
	
	static final int CANCELLED = -1;
	static final int VALID = 0;
	static final int INVALID = 1;
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		usernameLabel.setDisplayedMnemonic('u');
		usernameLabel.setLabelFor(username);
		passwordLabel.setDisplayedMnemonic('p');
		passwordLabel.setLabelFor(password);
		labels.add(usernameLabel);
		labels.add(passwordLabel);
		fillers.add(username);
		fillers.add(password);
		add(labels, BorderLayout.WEST);
		add(fillers, BorderLayout.CENTER);
	}
	
	public VerificationPanel() {
		initialize();
	}
	
	public JTextField getUsernameField() {
		return username;
	}

	public JPasswordField getPasswordField() {
		return password;
	}
	
	public String getUsername() {
		return username.getText();
	}
	
	public char[] getPassword() {
		return password.getPassword();
	}
	
	public void clearField() {
		username.setText("");
		password.setText("");
	}

	public static int showVerificationDialog() {
		VerificationPanel login = new VerificationPanel();
		login.username.requestFocusInWindow();
		boolean valid = false;
		int pass = -1;
		do {
			pass = JOptionPane.showConfirmDialog(null, login, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (pass == JOptionPane.OK_OPTION) {
				if (login.username.getText().isEmpty() || login.password.getPassword().length == 0) {
					continue;
				} //System.out.println(String.format("Username: %s | Password: %s", login.username.getText(), String.valueOf(login.password.getPassword())));
				try {
					CustomerQuery cq = new CustomerQuery();
					Customer cc = cq.obtainCustomerInformation(login.username.getText(), String.valueOf(login.password.getPassword()));
					if (cc == null) {
						JOptionPane.showMessageDialog(null, "Wrong username or password!", "Login", JOptionPane.ERROR_MESSAGE);
						login.clearField();
						valid = false;
						continue;
					} else {
						JOptionPane.showMessageDialog(null, "You're logged in!", "Login", JOptionPane.INFORMATION_MESSAGE);
						valid = true;
						return VerificationPanel.VALID;
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "SQL Exception: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
					valid = true;
					e.printStackTrace();
					return VerificationPanel.CANCELLED;
				} catch (BankingTransactionException e) {
					JOptionPane.showMessageDialog(null, "Login error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					login.clearField();
					valid = false;
					continue;
				}
			} else {
				return VerificationPanel.CANCELLED;
			}
		} while ((pass == JOptionPane.OK_OPTION && (login.username.getText().isEmpty() || login.password.getPassword().length == 0)) || valid == false);
		return VerificationPanel.CANCELLED;
	}
	
	public static Customer obtainLoginUser() {
		VerificationPanel login = new VerificationPanel();
		login.username.requestFocusInWindow();
		boolean valid = false;
		int pass = -1;
		do {
			pass = JOptionPane.showConfirmDialog(null, login, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (pass == JOptionPane.OK_OPTION) {
				if (login.username.getText().isEmpty() || login.password.getPassword().length == 0) {
					login.clearField();
					continue;
				} //System.out.println(String.format("Username: %s | Password: %s", login.username.getText(), String.valueOf(login.password.getPassword())));
				try {
					CustomerQuery cq = new CustomerQuery();
					Customer cc = cq.obtainCustomerInformation(login.username.getText(), String.valueOf(login.password.getPassword()));
					if (cc == null) {
						JOptionPane.showMessageDialog(null, "Wrong username or password!", "Login", JOptionPane.ERROR_MESSAGE);
						login.clearField();
						valid = false;
					} else {
						JOptionPane.showMessageDialog(null, "You're logged in!", "Login", JOptionPane.INFORMATION_MESSAGE);
						valid = true;
						return cc;
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "SQL Exception: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
					login.clearField();
					valid = true;
					e.printStackTrace();
					return null;
				} catch (BankingTransactionException e) {
					JOptionPane.showMessageDialog(null, "Login error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					login.clearField();
					valid = false;
					continue;
				}
			} else {
				return null;
			}
		} while ((pass == JOptionPane.OK_OPTION && (login.username.getText().isEmpty() || login.password.getPassword().length == 0)) || valid == false);
		return null;
	}
	
	public static void main(String[] args) {
		Customer cust = VerificationPanel.obtainLoginUser();
		if (cust != null) {
			System.out.println(cust.toString());
		}
	}
}
