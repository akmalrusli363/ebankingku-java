package database;

import java.text.NumberFormat;

public class CurrencyParser {

	private static NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	public static String parseCurrency(long money) {
		return nf.format(money);
	}
	
	public static String parseCurrency(double money) {
		return nf.format(money);
	}
}
