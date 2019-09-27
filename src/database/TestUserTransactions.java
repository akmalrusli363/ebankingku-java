package database;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestUserTransactions {

	private static TransactionQuery transfers = new TransactionQuery();
	private static CustomerQuery customerQuery = new CustomerQuery();
	
	public static void main(String[] args) {
//		Customer cust1 = new Customer(103, "Andi Wiraka", "p4$sw0Rd_d0e@_kAt4");
//		Customer cust2 = new Customer(106, "Leanna Leopard", "j03$T_d0.iT");
//		try {
//			customerQuery.createData(cust2);
//			System.out.println("Insertion customer success!");
//		} catch (Exception e) {
//			System.out.println("Customer registration failed :(");
//			System.err.println("Reason: " + e.toString());
//			e.printStackTrace();
//		}
		
//		try {
//			Customer cust = customerQuery.getSingleData(106);
//			cust.setSaldo(1200000);
//			customerQuery.updateData(106, cust);
//		} catch (Exception e) {
//			System.out.println("Customer update failed :(");
//			System.err.println("Reason: " + e.toString());
//			e.printStackTrace();
//		}
		
		Customer cust = null;
		
//		try {
//			cust = customerQuery.getSingleData(106);
//			Transaction transfer = Transaction.performPaymentTransaction(cust, 25000);
//			TransactionQuery tq = new TransactionQuery();
//			tq.createData(transfer);
//			System.out.println("Transaction success!");
//		} catch (SQLException e) {
//			System.out.println("Customer update failed :(");
//			System.err.println("Reason: " + e.toString());
//			e.printStackTrace();
//			try {
//				Transaction.performRefundTransaction(cust, 25000, false);
//				System.out.println("Transaction cancelled! Recent processed saldo have been refund.");
//			} catch (SQLException e1) {
//				System.out.println("Customer transaction refund failed :(");
//				System.err.println("Reason: " + e.toString());
//				e1.printStackTrace();
//			}  catch (BankingTransactionException be) {
//				System.out.println("Not enough saldo!");
//			}
//		}  catch (BankingTransactionException be) {
//			System.out.println("Not enough saldo!");
//		}
		
//		try {
//			cust = customerQuery.getSingleData(103);
//			Transaction transfer = Transaction.performTopupTransaction(cust, 125000);
//			TransactionQuery tq = new TransactionQuery();
//			tq.createData(transfer);
//			System.out.println("Topup success!");
//		} catch (Exception e) {
//			System.out.println("Customer update failed :(");
//			System.err.println("Reason: " + e.toString());
//			e.printStackTrace();
//			try {
//				Transaction.performRefundTransaction(cust, 125000, true);
//				System.out.println("Transaction cancelled! Recent processed saldo have been refund.");
//			} catch (SQLException e1) {
//				System.out.println("Customer transaction refund failed :(");
//				System.err.println("Reason: " + e.toString());
//				e1.printStackTrace();
//			} catch (BankingTransactionException be) {
//				System.out.println("Not enough saldo!");
//			}
//		}
//		
//		try {
//			ArrayList<Transaction> transactionList = new ArrayList<>();
//			transactionList = transfers.getAllData();
//			for (Transaction transaction : transactionList) {
//				System.out.println(transaction.toString());
//			}
//		} catch (Exception e) {
//			System.out.println("Transaction listing failed :(");
//			System.err.println("Reason: " + e.toString());
//			e.printStackTrace();
//		}
//		
//		try {
//			ArrayList<Customer> dataCustomer = new ArrayList<>();
//			dataCustomer = customerQuery.getAllData();
//			for (Customer customer : dataCustomer) {
//				System.out.println(customer.toString());
//			}
//		} catch (Exception e) {
//			System.out.println("Customer call failed :(");
//			System.err.println("Reason: " + e.toString());
//			e.printStackTrace();
//		}
		
		try {
			Customer customer = customerQuery.getSingleData(106);
			Transaction transact = customer.getLatestTransaction();
			System.out.println(transact.toString());
		} catch  (Exception e) {
			System.out.println("Customer latest transaction call failed :(");
			System.err.println("Reason: " + e.toString());
			e.printStackTrace();
		}
		
			
	}

}
