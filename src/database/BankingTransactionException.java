package database;

public class BankingTransactionException extends Exception {

	private static final long serialVersionUID = 2114983170559477993L;

	public BankingTransactionException(BankingTransactionErrorCode errorCode) {
		this(errorCode.toString());
	}
	
	public BankingTransactionException(BankingTransactionErrorCode errorCode, Object pretendedCause) {
		if (pretendedCause instanceof Long && errorCode.getErrorCode() == 301) {
			String message = errorCode.toString().concat(" (Transferred saldo is bigger than available saldo!)");
			new BankingTransactionException(message);
		} else {
			new BankingTransactionException(errorCode.toString());
		}
	}

	public BankingTransactionException(String message) {
		super(message);
	}

	public BankingTransactionException(Throwable cause) {
		super(cause);
	}

	public BankingTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankingTransactionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
