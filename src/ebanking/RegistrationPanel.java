package ebanking;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import database.*;

public class RegistrationPanel extends JPanel {

	private JPanel labels = new JPanel(new GridLayout(2,1));
	private JPanel fillers = new JPanel(new GridLayout(2,1));
	private JLabel usernameLabel = new JLabel("Nama Lengkap: ");
	private JLabel passwordLabel = new JLabel("Password: ");
	private JTextField username = new JTextField(20);
	private JPasswordField password = new JPasswordField(20);
	
	static final int CANCELLED = -1;
	static final int VALID = 0;
	static final int INVALID = 1;
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		usernameLabel.setDisplayedMnemonic('n');
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
	
	public RegistrationPanel() {
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

	public static int performUserRegistration() {
		RegistrationPanel register = new RegistrationPanel();
		register.username.requestFocusInWindow();
		boolean valid = false;
		long saldoAwal = 0;
		int registered = -1;
		do {
			registered = JOptionPane.showConfirmDialog(null, register, "Registrasi EBanking", JOptionPane.OK_CANCEL_OPTION);
			if (registered == JOptionPane.OK_OPTION) {
				if (register.getUsername().isEmpty() || register.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "Nama atau password tidak boleh kosong", "Registrasi EBanking", JOptionPane.ERROR_MESSAGE);
					continue;
				} else if (register.getUsername().length() < 5 || !register.getUsername().matches("[A-Za-z ]+")) {
					JOptionPane.showMessageDialog(null, "Nama lengkap terlalu pendek (min: 5 karakter)", "Registrasi EBanking", JOptionPane.ERROR_MESSAGE);
					continue;
				} else if (register.getPassword().length < 8) {
					JOptionPane.showMessageDialog(null, "Password terlalu pendek (min: 8 karakter)", "Registrasi EBanking", JOptionPane.ERROR_MESSAGE);
					continue;
				} else {
					break;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Registrasi dibatalkan!", "Registrasi EBanking", JOptionPane.INFORMATION_MESSAGE);
				return VerificationPanel.CANCELLED;
			}
		} while ((registered == JOptionPane.OK_OPTION && (register.getUsername().isEmpty() || register.getPassword().length == 0)) || valid == false);
		
		do {
			String inputSaldo = JOptionPane.showInputDialog(null, "Input saldo awal anda: (Dalam rupiah)", "Registrasi EBanking", JOptionPane.QUESTION_MESSAGE);
			if (inputSaldo == null) {
				JOptionPane.showMessageDialog(null, "Registrasi dibatalkan!", "Registrasi EBanking", JOptionPane.INFORMATION_MESSAGE);
				return VerificationPanel.CANCELLED;
			}
			try {
				saldoAwal = Long.parseLong(inputSaldo);
				valid = true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Nominal harus berupa digit angka! (tanpa koma/titik)", "Registrasi EBanking", JOptionPane.ERROR_MESSAGE);
				valid = false;
				continue;
			}
		} while (valid == false);
		
		Customer customer = new Customer(new CustomerQuery().getLatestCustomerID()+1, register.getUsername(), String.valueOf(register.getPassword()), saldoAwal);
		CustomerQuery cq = new CustomerQuery();
		try {
			cq.createData(customer);
			StringBuilder customerProperties = new StringBuilder();
			customerProperties.append("- Customer ID: ").append(customer.getCustomerID()).append("\n");
			customerProperties.append("- Nama Lengkap: ").append(customer.getNamaLengkap()).append("\n");
			customerProperties.append("- Username: ").append(customer.getUsername()).append("\n");
			customerProperties.append("- Password: ").append(customer.getPassword()).append("\n");
			customerProperties.append("- Saldo awal: ").append(CurrencyParser.parseCurrency(customer.getSaldo()));
			JOptionPane.showMessageDialog(null, "Registrasi berhasil! Berikut adalah data customer anda:\n" + customerProperties.toString() + "\n\nSilahkan login menggunakan username dan password untuk\nmelakukan topup atau melakukan pembayaran.",
					"Registrasi EBanking", JOptionPane.INFORMATION_MESSAGE);
			return VALID;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Registrasi gagal! Alasan: " + e.toString(), "Registrasi EBanking", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return VerificationPanel.CANCELLED;
		}
		
	}

}
