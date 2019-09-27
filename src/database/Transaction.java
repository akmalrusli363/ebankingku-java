package database;

import java.sql.SQLException;
import java.util.*;

public class Transaction {

	Customer customer;
	Date dateProcessed;
	int transactionID, customerID;
	long preprocessedSaldo, processedSaldo, sisaSaldo;
	boolean isTopupTransaction = false;
	String description;
	
	public Transaction(Customer customer, long processedSaldo, boolean isTopupTransaction) {
		this(customer, processedSaldo, isTopupTransaction, "");
	}
	
	public Transaction(Customer customer, long processedSaldo, boolean isTopupTransaction, String description) {
		this.customer = customer;
		this.customerID = customer.getCustomerID();
		this.processedSaldo = processedSaldo;
		this.preprocessedSaldo = customer.getSaldo();
		this.sisaSaldo = customer.getSaldo();
		this.isTopupTransaction = isTopupTransaction;
		this.description = description;
		this.dateProcessed = new Date();
	}
	
	public Transaction(Customer customer, Date dateProcessed, int transactionID, int customerID, 
						long preprocessedSaldo, long processedSaldo, long sisaSaldo, boolean isTopupTransaction, String description) {
		this.customer = customer;
		this.dateProcessed = dateProcessed;
		this.transactionID = transactionID;
		this.customerID = customerID;
		this.preprocessedSaldo = processedSaldo;
		this.processedSaldo = processedSaldo;
		this.sisaSaldo = sisaSaldo;
		this.isTopupTransaction = isTopupTransaction;
		this.description = description;
	}

	public static Transaction performTopupTransaction(Customer customer, long processedSaldo, String description) throws SQLException {
		CustomerQuery customerquery = new CustomerQuery();
		Transaction transaction = new Transaction(customer, processedSaldo, true, description);
		transaction.preprocessedSaldo = transaction.getCustomer().getSaldo();
		transaction.getCustomer().addSaldo(processedSaldo);
		transaction.sisaSaldo = transaction.getCustomer().getSaldo();
		customerquery.updateData(customer.getCustomerID(), customer);
		return transaction;
	}
	
	public static Transaction performPaymentTransaction(Customer customer, long processedSaldo, String description) throws SQLException, BankingTransactionException {
		CustomerQuery customerquery = new CustomerQuery();
		Transaction transaction = new Transaction(customer, processedSaldo, false, description);
		transaction.preprocessedSaldo = transaction.getCustomer().getSaldo();
		try {
			transaction.getCustomer().subtractSaldo(processedSaldo);
		} catch (BankingTransactionException e) {
			transaction.processedSaldo = 0;
			throw e;
		}
		transaction.sisaSaldo = transaction.getCustomer().getSaldo();
		customerquery.updateData(customer.getCustomerID(), customer);
		return transaction;
	}
	
	public static Transaction performRefundTransaction(Customer customer, long processedSaldo, boolean isTopupTransaction) throws SQLException, BankingTransactionException {
		CustomerQuery customerquery = new CustomerQuery();
		Transaction transaction = new Transaction(customer, processedSaldo, true, "REVERT TRANSACTION");
		transaction.preprocessedSaldo = transaction.getCustomer().getSaldo();
		if (isTopupTransaction == true) {
			try {
				transaction.getCustomer().subtractSaldo(processedSaldo);
			} catch (BankingTransactionException e) {
				transaction.processedSaldo = 0;
				throw e;
			}
		} else {
			transaction.getCustomer().addSaldo(processedSaldo);
		}
		transaction.sisaSaldo = transaction.getCustomer().getSaldo();
		customerquery.updateData(customer.getCustomerID(), customer);
		return transaction;
	}
	
	public static Transaction performTopupTransaction(Customer customer, long processedSaldo) throws SQLException {
		return performTopupTransaction(customer, processedSaldo, "");
	}
	
	public static Transaction performPaymentTransaction(Customer customer, long processedSaldo) throws SQLException, BankingTransactionException {
		return performPaymentTransaction(customer, processedSaldo, "");
	}
	
	public static Transaction refundPaymentTransaction(Customer customer, long processedSaldo, boolean isTopupTransaction) throws SQLException, BankingTransactionException {
		return performPaymentTransaction(customer, processedSaldo, "");
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public long getPreprocessedSaldo() {
		return preprocessedSaldo;
	}
	
	public void setPreprocessedSaldo(long preprocessedSaldo) {
		this.preprocessedSaldo = preprocessedSaldo;
	}
	
	public long getProcessedSaldo() {
		return processedSaldo;
	}
	
	public void setProcessedSaldo(long processedSaldo) {
		this.processedSaldo = processedSaldo;
	}

	public long getSisaSaldo() {
		return sisaSaldo;
	}

	public void setSisaSaldo(long sisaSaldo) {
		this.sisaSaldo = sisaSaldo;
	}

	public boolean isTopupTransaction() {
		return isTopupTransaction;
	}

	public void setTopupTransaction(boolean isTopupTransaction) {
		this.isTopupTransaction = isTopupTransaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerID;
		result = prime * result + ((dateProcessed == null) ? 0 : dateProcessed.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isTopupTransaction ? 1231 : 1237);
		result = prime * result + (int) (preprocessedSaldo ^ (preprocessedSaldo >>> 32));
		result = prime * result + (int) (processedSaldo ^ (processedSaldo >>> 32));
		result = prime * result + (int) (sisaSaldo ^ (sisaSaldo >>> 32));
		result = prime * result + transactionID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (customerID != other.customerID)
			return false;
		if (dateProcessed == null) {
			if (other.dateProcessed != null)
				return false;
		} else if (!dateProcessed.equals(other.dateProcessed))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (isTopupTransaction != other.isTopupTransaction)
			return false;
		if (preprocessedSaldo != other.preprocessedSaldo)
			return false;
		if (processedSaldo != other.processedSaldo)
			return false;
		if (sisaSaldo != other.sisaSaldo)
			return false;
		if (transactionID != other.transactionID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Transaction [transactionID=%s, customerID=%s, dateProcessed=%s, preprocessedSaldo=%s, processedSaldo=%s, sisaSaldo=%s, isTopupTransaction=%s, description=%s]",
				transactionID, customerID, dateProcessed, preprocessedSaldo, sisaSaldo, processedSaldo, isTopupTransaction, description);
	}
	
}
