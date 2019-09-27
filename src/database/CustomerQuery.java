package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerQuery {

	private DBConnector db;
	private ResultSet rs;
	
	public void createData(Customer customer) throws SQLException {
		String queries = String.format("INSERT INTO `bank_customers`(`customer_id`, `fullname`, `username`, `password`, `saldo`) VALUES (%d, \"%s\", \"%s\", \"%s\", %d)",
				customer.getCustomerID(), customer.getNamaLengkap(), customer.getUsername(), customer.getPassword(), customer.getSaldo());
		db = new DBConnector();
		db.update(queries);
		db.close();
	}
	
	public void createData(int customerID, String fullname, String username, String password, long saldo) throws SQLException {
		String queries = String.format("INSERT INTO `bank_customers`(`customer_id`, `fullname`, `username`, `password`, `saldo`) VALUES (%d, \"%s\", \"%s\", \"%s\", %d)",
				customerID, fullname, username, password, saldo);
		db = new DBConnector();
		db.update(queries);
		db.close();
	}
	
	public int getLatestCustomerID() {
		int customerid = 100;
		try {
			DBConnector db = new DBConnector();
			rs = db.execute("SELECT * FROM `bank_customers` ORDER BY `customer_id` DESC LIMIT 1");
			try {
				while (rs.next()) {
					customerid = rs.getInt("customer_id");
				}
			} catch (SQLException e) {
				throw e;
			} db.close();			
		} catch (SQLException e) {
			return 100 + new java.util.Random().nextInt(1100);
		}
		return customerid;
	}
	
	public ArrayList<Customer> getAllData() throws SQLException {
		ArrayList<Customer> users = new ArrayList<>();
		DBConnector db = new DBConnector();
		int customerid;
		String fullname, username, password;
		long saldo;
		rs = db.execute("SELECT * FROM `bank_customers`");
		try {
			while (rs.next()) {
				customerid = rs.getInt("customer_id");
				fullname = rs.getString("username");
				username = rs.getString("fullname");
				password = rs.getString("password");
				saldo = rs.getLong("saldo");
				Customer customer = new Customer(customerid, fullname, username, password, saldo);
				users.add(customer);
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return users;
	}
	
	public Customer getSingleData(int wantedID) throws SQLException {
		DBConnector db = new DBConnector();
		int customerid;
		String fullname, username, password;
		long saldo;
		Customer customer = null;
		rs = db.execute("SELECT * FROM `bank_customers` WHERE `customer_id`=" + wantedID);
		try {
			while (rs.next()) {
				customerid = rs.getInt("customer_id");
				fullname = rs.getString("fullname");
				username = rs.getString("username");
				password = rs.getString("password");
				saldo = rs.getLong("saldo");
				customer = new Customer(customerid, fullname, username, password, saldo);
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return customer;
	}
	
	public Customer obtainCustomerInformation(String loginUsername, String loginPassword) throws SQLException, BankingTransactionException {
		DBConnector db = new DBConnector();
		int customerid;
		String fullname = "", username = "", password = "";
		long saldo;
		Customer customer = null;
		String queries = String.format("SELECT * FROM `bank_customers` WHERE `username`=\"%s\" AND `password`=\"%s\"", loginUsername, loginPassword);
		rs = db.execute(queries);
		try {
			while (rs.next()) {
				customerid = rs.getInt("customer_id");
				fullname = rs.getString("fullname");
				username = rs.getString("username");
				password = rs.getString("password");
				saldo = rs.getLong("saldo");
				customer = new Customer(customerid, fullname, username, password, saldo);
			} if (loginUsername.equals(username) && loginPassword.equals(password)) {
				db.close();
				return customer;
			} else if (loginUsername.equals(username)) {
				db.close();
				throw new BankingTransactionException(BankingTransactionErrorCode.INVALID_PASSWORD);
			} else {
				db.close();
				throw new BankingTransactionException(BankingTransactionErrorCode.INVALID_USERNAME);
			}
		} catch (SQLException e) {
			throw e;
		} catch (BankingTransactionException bte) {
			throw bte;
		}
	}
	
	public void updateData(int wantedID, Customer customer) throws SQLException {
		DBConnector db = new DBConnector();
		String updateQuery = String.format("UPDATE `bank_customers` SET `customer_id`=%d, `fullname`=\"%s\", `username`=\"%s\", `password`=\"%s\", `saldo`=%d WHERE `customer_id`=%d",
				customer.getCustomerID(), customer.getNamaLengkap(), customer.getUsername(), customer.getPassword(), customer.getSaldo(), wantedID);
		db.update(updateQuery);
		db.close();
	}
	
	public void deleteData(int wantedID) throws SQLException {
		DBConnector db = new DBConnector();
		String deleteQuery = String.format("DELETE FROM `bank_customers` WHERE `customer_id`=%d", wantedID);
		db.update(deleteQuery);
		db.close();
	}

	public boolean isEmpty() throws SQLException {
		DBConnector db = new DBConnector();
		boolean empty = false;
		rs = db.execute("SELECT * FROM `bank_customers` LIMIT 1");
		try {
			if(rs.next() == false) {
				empty = false;
			} else {
				empty = true;
			}
		} catch (SQLException e) {
			throw e;
		} db.close();
		return empty;
	}

}
