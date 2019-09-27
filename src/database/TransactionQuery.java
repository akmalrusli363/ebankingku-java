package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

public class TransactionQuery {

	private DBConnector db;
	private ResultSet rs;
	
	public void createData(Transaction transaction) throws SQLException {
		createData(transaction.customerID, transaction.dateProcessed, transaction.preprocessedSaldo, transaction.processedSaldo, 
					transaction.sisaSaldo, transaction.isTopupTransaction, transaction.description);
	}
	
	public void createData(int customerID, Date dateProcessed, long preprocessedSaldo, long processedSaldo, long sisaSaldo, boolean isTopupTransaction, String description) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String queries = String.format("INSERT INTO `bank_transactions`(`customer_id`, `date_processed`, `preprocessed_saldo`, `processed_saldo`, `sisa_saldo`, `is_top_up`, `description`)"
				+ " VALUES (%d, \"%s\", %d, %d, %d, %b, \"%s\")",
				customerID, sdf.format(dateProcessed), preprocessedSaldo, processedSaldo, sisaSaldo, isTopupTransaction, description);
		db = new DBConnector();
		db.update(queries);
		db.close();
	}
	
	public ArrayList<Transaction> getAllData() throws SQLException {
		ArrayList<Transaction> transactions = new ArrayList<>();
		DBConnector db = new DBConnector();
		int transactionID, customerID;
		Customer customer = null;
		Date dateProcessed;
		String description;
		long preprocessedSaldo, processedSaldo, sisaSaldo;
		boolean isTopup = false;
		rs = db.execute("SELECT * FROM `bank_transactions`");
		try {
			while (rs.next()) {
				transactionID = rs.getInt("transaction_id");
				customerID = rs.getInt("customer_id");
				dateProcessed = rs.getTimestamp("date_processed");
				preprocessedSaldo = rs.getLong("preprocessed_saldo");
				processedSaldo = rs.getLong("processed_saldo");
				sisaSaldo = rs.getLong("sisa_saldo");
				isTopup = rs.getBoolean("is_top_up");
				description = rs.getString("description");
				customer = new CustomerQuery().getSingleData(customerID);
				Transaction transacation = new Transaction(customer, dateProcessed, transactionID, customerID, preprocessedSaldo, processedSaldo, sisaSaldo, isTopup, description);
				transactions.add(transacation);
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return transactions;
	}
	
	public ArrayList<Transaction> getSingleCustomerTransactionData(int wantedCustomerID) throws SQLException {
		ArrayList<Transaction> transactions = new ArrayList<>();
		DBConnector db = new DBConnector();
		int transactionID, customerID;
		Customer customer = null;
		Date dateProcessed;
		String description;
		long preprocessedSaldo, processedSaldo, sisaSaldo;
		boolean isTopup = false;
		rs = db.execute("SELECT * FROM `bank_transactions` WHERE `customer_id`=" + wantedCustomerID);
		try {
			while (rs.next()) {
				transactionID = rs.getInt("transaction_id");
				customerID = rs.getInt("customer_id");
				dateProcessed = rs.getTimestamp("date_processed");
				preprocessedSaldo = rs.getLong("preprocessed_saldo");
				processedSaldo = rs.getLong("processed_saldo");
				sisaSaldo = rs.getLong("sisa_saldo");
				isTopup = rs.getBoolean("is_top_up");
				description = rs.getString("description");
				customer = new CustomerQuery().getSingleData(customerID);
				Transaction transacation = new Transaction(customer, dateProcessed, transactionID, customerID, preprocessedSaldo, processedSaldo, sisaSaldo, isTopup, description);
				transactions.add(transacation);
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return transactions;
	}
	
	public Transaction getSingleTransactionData(int wantedTransactionID) throws SQLException {
		DBConnector db = new DBConnector();
		int transactionID, customerID;
		Customer customer = null;
		Date dateProcessed;
		String description;
		long preprocessedSaldo, processedSaldo, sisaSaldo;
		boolean isTopup = false;
		Transaction transacation = null;
		rs = db.execute("SELECT * FROM `bank_transactions` WHERE `transaction_id`=" + wantedTransactionID);
		try {
			while (rs.next()) {
				transactionID = rs.getInt("transaction_id");
				customerID = rs.getInt("customer_id");
				dateProcessed = rs.getTimestamp("date_processed");
				preprocessedSaldo = rs.getLong("preprocessed_saldo");
				processedSaldo = rs.getLong("processed_saldo");
				sisaSaldo = rs.getLong("sisa_saldo");
				isTopup = rs.getBoolean("is_top_up");
				description = rs.getString("description");
				customer = new CustomerQuery().getSingleData(customerID);
				transacation = new Transaction(customer, dateProcessed, transactionID, customerID, preprocessedSaldo, processedSaldo, sisaSaldo, isTopup, description);
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return transacation;
	}
	
	public Transaction getLatestUserTransactionData(int wantedCustomerID) throws SQLException {
		DBConnector db = new DBConnector();
		int transactionID, customerID;
		Customer customer = null;
		Date dateProcessed;
		String description;
		long preprocessedSaldo, processedSaldo, sisaSaldo;
		boolean isTopup = false;
		Transaction transacation = null;
		rs = db.execute("SELECT * FROM `bank_transactions` WHERE `customer_id`=" + wantedCustomerID + " ORDER BY date_processed DESC LIMIT 1");
		try {
			while (rs.next()) {
				transactionID = rs.getInt("transaction_id");
				customerID = rs.getInt("customer_id");
				dateProcessed = rs.getTimestamp("date_processed");
				preprocessedSaldo = rs.getLong("preprocessed_saldo");
				processedSaldo = rs.getLong("processed_saldo");
				sisaSaldo = rs.getLong("sisa_saldo");
				isTopup = rs.getBoolean("is_top_up");
				description = rs.getString("description");
				customer = new CustomerQuery().getSingleData(customerID);
				transacation = new Transaction(customer, dateProcessed, transactionID, customerID, preprocessedSaldo, processedSaldo, sisaSaldo, isTopup, description);
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return transacation;
	}
	
	public void updateData(int wantedID, Transaction transaction) throws SQLException {
		DBConnector db = new DBConnector();
		String updateQuery = String.format("UPDATE `bank_transactions` SET `customer_id`=%d, `preprocessed_saldo`=%d, `processed_saldo`=%d, `sisa_saldo`=%d, `is_top_up`=%b, `description`=\"%s\""
				+ " WHERE `transaction_id`=%d",
				transaction.getCustomerID(), transaction.getPreprocessedSaldo(), transaction.getProcessedSaldo(), transaction.getSisaSaldo(), 
				transaction.isTopupTransaction(), transaction.getDescription(), wantedID);
		db.update(updateQuery);
		db.close();
	}
	
	public void deleteData(int wantedTransactionID) throws SQLException {
		DBConnector db = new DBConnector();
		String deleteQuery = String.format("DELETE FROM `bank_transactions` WHERE `transaction_id`=%d", wantedTransactionID);
		db.update(deleteQuery);
		db.close();
	}
	
}
