package database;

import java.sql.SQLException;

public class Customer {

	private int customerID;
	private String namaLengkap, username, password;
	private long saldo;
	
	public Customer(int customerID, String namaLengkap, String password) {
		this(customerID, namaLengkap, password, customerID);
	}
	
	public Customer(int customerID, String namaLengkap, String password, long saldo) {
		this.customerID = customerID;
		this.namaLengkap = namaLengkap;
		this.username = generateUsername();
		this.password = password;
		this.saldo = saldo;
	}
	
	public Customer(int customerID, String namaLengkap, String username, String password) {
		this(customerID, namaLengkap, username, password, 0);
	}
	
	public Customer(int customerID, String namaLengkap, String username, String password, long saldo) {
		this.customerID = customerID;
		this.namaLengkap = namaLengkap;
		this.username = username;
		this.password = password;
		this.saldo = saldo;
	}
	
	public String generateUsername() {
		return String.format("%s%s%d", getNamaDepan(), getNamaBelakang(), new java.util.Random().nextInt(999));
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getNamaLengkap() {
		return namaLengkap;
	}

	public void setNamaLengkap(String namaLengkap) {
		this.namaLengkap = namaLengkap;
	}
	
	public String getNamaDepan() {
		return namaLengkap.split("\\s+")[0];
	}
	
	public String getNamaBelakang() {
		String names[] = namaLengkap.split("\\s+");
		return names[names.length-1];
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getSaldo() {
		return saldo;
	}

	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}
	
	public void addSaldo(long processedSaldo) {
		this.saldo += processedSaldo;
	}
	
	public void subtractSaldo(long processedSaldo) throws BankingTransactionException {
		if (this.saldo - processedSaldo >= 0) {
			this.saldo -= processedSaldo;
		} else {
			throw new BankingTransactionException(BankingTransactionErrorCode.NOT_ENOUGH_SALDO, saldo);
		}
	}
	
	public Transaction getLatestTransaction() throws SQLException {
		TransactionQuery tq = new TransactionQuery();
		return tq.getLatestUserTransactionData(this.customerID);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerID;
		result = prime * result + ((namaLengkap == null) ? 0 : namaLengkap.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (saldo ^ (saldo >>> 32));
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Customer other = (Customer) obj;
		if (customerID != other.customerID)
			return false;
		if (namaLengkap == null) {
			if (other.namaLengkap != null)
				return false;
		} else if (!namaLengkap.equals(other.namaLengkap))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (saldo != other.saldo)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Customer [customerID=%s, namaLengkap=%s, username=%s, password=%s, saldo=%s]",
				customerID, namaLengkap, username, password, saldo);
	}

}
