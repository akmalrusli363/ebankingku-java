package ebanking;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import database.*;

public class TransactionHistoryDialog extends JPanel {

	private Vector<Vector<Object>> transactionData = new Vector<>();
	private int columnSize[] = {10, 10, 100, 70, 70, 70, 20, 120};
	private int customerColumnSize[] = {10, 100, 70, 70, 70, 20, 150};
	
	private JTable historyTable;
	private JScrollPane pilots;
	
	private Vector<Object> convertTransactionToColumns(Transaction transaction) {
		Vector<Object> rows = new Vector<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		rows.add(transaction.getTransactionID());
		rows.add(transaction.getCustomerID());
		rows.add(sdf.format(transaction.getDateProcessed()));
		rows.add(CurrencyParser.parseCurrency(transaction.getPreprocessedSaldo()));
		rows.add(CurrencyParser.parseCurrency(transaction.getProcessedSaldo()));
		rows.add(CurrencyParser.parseCurrency(transaction.getSisaSaldo()));
		rows.add(transaction.isTopupTransaction() ? "Topup" : "Payment");
		rows.add(transaction.getDescription());
		return rows;
	}
	
	private Vector<String> getHeaderList() {
		Vector<String> header = new Vector<>();
		header.add("Transaction ID");
		header.add("Customer ID");
		header.add("Date Processed");
		header.add("Preprocessed Saldo");
		header.add("Processed Saldo");
		header.add("Sisa Saldo");
		header.add("Tipe");
		header.add("Deskripsi");
		return header;
	}
	
	private void getTransactionQuery() throws SQLException {
		TransactionQuery tq = new TransactionQuery();
		ArrayList<Transaction> transactionList = tq.getAllData();
		for (Transaction transaction : transactionList) {
			transactionData.add(convertTransactionToColumns(transaction));
		}
	}
	
	private void getCustomerTransactionQuery(Customer customer) throws SQLException {
		TransactionQuery tq = new TransactionQuery();
		ArrayList<Transaction> transactionList = tq.getSingleCustomerTransactionData(customer.getCustomerID());
		for (Transaction transaction : transactionList) {
			transactionData.add(convertTransactionToColumns(transaction));
		}
	}
	
	private void adjustTableColumnSize() {
		TableColumn col;
		for(int i = 0; i < historyTable.getColumnCount(); i++) {
			col = historyTable.getColumnModel().getColumn(i);
			col.setPreferredWidth(columnSize[i]);
		}
	}
	
	private void adjustCustomerTableColumnSize() {
		TableColumn col;
		for(int i = 0; i < historyTable.getColumnCount(); i++) {
			col = historyTable.getColumnModel().getColumn(i);
			col.setPreferredWidth(customerColumnSize[i]);
		}
	}
	
	private void createDialog() {
		JDialog dialog = new JDialog(new JFrame());
		dialog.setLayout(new BorderLayout());
		dialog.add(this, BorderLayout.CENTER);
		dialog.pack();
		dialog.setVisible(true);
		dialog.setSize(700, 300);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public TransactionHistoryDialog() {
		try {
			getTransactionQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Vector<String> columns = getHeaderList();
		DefaultTableModel dtm = new DefaultTableModel(transactionData, columns);
		historyTable = new FixedTable(dtm);
		adjustTableColumnSize();
		pilots = new JScrollPane(historyTable);
		pilots.setPreferredSize(new Dimension(800,250));
		setLayout(new BorderLayout());
		add(pilots, BorderLayout.CENTER);
	}
	
	public TransactionHistoryDialog(Customer customer) {
		try {
			getCustomerTransactionQuery(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Vector<String> columns = getHeaderList();
		DefaultTableModel dtm = new DefaultTableModel(transactionData, columns);
		historyTable = new FixedTable(dtm);
		historyTable.removeColumn(historyTable.getColumn(dtm.getColumnName(1)));
		adjustCustomerTableColumnSize();
		pilots = new JScrollPane(historyTable);
		pilots.setPreferredSize(new Dimension(800,300));
		setLayout(new BorderLayout());
		add(pilots, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		TransactionHistoryDialog thd = new TransactionHistoryDialog();
		thd.createDialog();
	}
	
	private class FixedTable extends JTable {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		public FixedTable(TableModel tm) {
			this.setModel(tm);
		}
	}

}
