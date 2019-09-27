package ebanking;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import database.*;

public class TransferPanel {

	private Customer customer = null;
	
	public boolean showVerificationDialog() {
		boolean valid = false;
		int conf = -1;
		JPanel passwordPanel = new JPanel(new BorderLayout());
		JLabel passLabel = new JLabel("Input password untuk konfirmasi:\n");
		JPasswordField password = new JPasswordField(20);
		
		passwordPanel.add(passLabel, BorderLayout.CENTER);
		passwordPanel.add(password, BorderLayout.SOUTH);
		do {
			conf = JOptionPane.showConfirmDialog(null, passwordPanel, "Konfirmasi pembayaran", JOptionPane.OK_CANCEL_OPTION);
			if (conf == JOptionPane.OK_OPTION) {
				String inputPassword = String.valueOf(password.getPassword());
				if (inputPassword.equals(customer.getPassword())) {
					valid = true;
					return true;
				} else {
					valid = false;
					JOptionPane.showMessageDialog(null, "Salah password, silahkan coba lagi!", "Konfirmasi pembayaran", JOptionPane.ERROR_MESSAGE);
					password.setText("");
				}
			} else {
				return false;
			}
		} while (conf == JOptionPane.OK_OPTION && valid == false);
		return false;
	}
	
	public void performUserPayment() {
		boolean valid = false;
		long processedSaldo = 0;
		do {
			String paymentValue = JOptionPane.showInputDialog(null, "Input nominal pembayaran (berupa Rupiah):", "Transaksi pembayaran", JOptionPane.QUESTION_MESSAGE);
			try {
				processedSaldo = Long.valueOf(paymentValue);
			} catch (Exception e) {
				if (paymentValue == null) {
					break;
				}
				JOptionPane.showMessageDialog(null, "Nominal harus berupa Rupiah!", "Transaksi pembayaran", JOptionPane.ERROR_MESSAGE);
				valid = false;
				continue;
			} valid = true;
		} while (valid == false);
		
		String description = JOptionPane.showInputDialog(null, "Input keterangan pembayaran:", "Transaksi pembayaran", JOptionPane.QUESTION_MESSAGE);
		
		boolean confirmedToPay = showVerificationDialog();
		
		if (confirmedToPay == true) {
			try {
				Transaction transaction = Transaction.performPaymentTransaction(customer, processedSaldo, description);
				TransactionQuery tq = new TransactionQuery();
				tq.createData(transaction);
				JOptionPane.showMessageDialog(null, "Transaksi pembayaran berhasil!", "Transaksi pembayaran", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Transaksi gagal! Alasan:\n" + e.toString(), "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
				try {
					Transaction.performRefundTransaction(customer, processedSaldo, false);
					JOptionPane.showMessageDialog(null, "Transaksi gagal, saldo transaksi dikembalikan!", "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Refund gagal! Alasan:\n" + e1.toString(), "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (BankingTransactionException be) {
					JOptionPane.showMessageDialog(null, "Tidak mempunyai saldo cukup untuk refund!", "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
				}
			} catch (BankingTransactionException e) {
				JOptionPane.showMessageDialog(null, "Saldo anda tidak cukup! Masukkan nominal transfer "
						+ "\nsesuai sisa saldo atau lakukan topup dan silahkan coba lagi.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Transaksi dibatalkan!", "Transaksi pembayaran", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void performUserTopup() {
		boolean valid = false;
		long processedSaldo = 0;
		do {
			String paymentValue = JOptionPane.showInputDialog(null, "Input nominal topup (berupa Rupiah):", "Transaksi topup", JOptionPane.QUESTION_MESSAGE);
			try {
				processedSaldo = Long.valueOf(paymentValue);
			} catch (Exception e) {
				if (paymentValue == null) {
					break;
				}
				JOptionPane.showMessageDialog(null, "Nominal harus berupa Rupiah!", "Transaksi topup", JOptionPane.ERROR_MESSAGE);
				valid = false;
				continue;
			} valid = true;
		} while (valid == false);
		
		String description = JOptionPane.showInputDialog(null, "Input keterangan topup:", "Transaksi topup", JOptionPane.QUESTION_MESSAGE);
		
		boolean confirmedToPay = showVerificationDialog();
		
		if (confirmedToPay == true) {
			try {
				Transaction transaction = Transaction.performTopupTransaction(customer, processedSaldo, description);
				TransactionQuery tq = new TransactionQuery();
				tq.createData(transaction);
				JOptionPane.showMessageDialog(null, "Topup berhasil!", "Transaksi topup", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Transaksi gagal! Alasan:\n" + e.toString(), "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
				try {
					Transaction.performRefundTransaction(customer, processedSaldo, true);
					JOptionPane.showMessageDialog(null, "Transaksi gagal, saldo topup digagalkan!", "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Refund gagal! Alasan:\n" + e1.toString(), "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}  catch (BankingTransactionException be) {
					JOptionPane.showMessageDialog(null, "Tidak mempunyai saldo cukup untuk refund!", "Kegagalan transaksi", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Topup dibatalkan!", "Transaksi topup", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public TransferPanel(Customer customer) {
		this.customer = customer;
	}

}
