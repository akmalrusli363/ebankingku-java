package database;

public enum BankingTransactionErrorCode {
	TRANSACTION_SUCCESS(100, "Transaction success!"),
	PAYMENT_SUCCESS(101, "Payment success!"),
	TOPUP_SUCCESS(102, "Topup success!"),
	NOT_ENOUGH_SALDO(201, "Not enough saldo!"),
	INVALID_SALDO_INPUTS(202, "Invalid inputted saldo!"),
	INVALID_USERNAME(301, "Invalid username (assume that it's username are invalid)!"),
	INVALID_PASSWORD(302, "Invalid password (username exists but wrong password)!");
	
	private int errorCode = 100;
	private String message = "";
	
	private BankingTransactionErrorCode(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return String.format("ERROR %d: %s", getErrorCode(), getMessage());
	}
}
