package database;

import java.sql.*;

public class DBConnector {

	private Connection connect;
	private ResultSet rs;
	private Statement st;
	
	public DBConnector() throws SQLException {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ebankingku?useTimezone=true&serverTimezone=UTC", "root", "");
			st = connect.createStatement(1004, 1008);
		} catch (ClassNotFoundException e) {
			throw new SQLException("JDBC Driver class not found!", e);
		} catch (com.mysql.cj.jdbc.exceptions.CommunicationsException e) {
			throw new SQLException("SQL Server is offline!", e);
		}
	}
	
	public static boolean isOnline() {
		try {
			new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} return true;
	}
	
	// Read and obtain data from database
	public ResultSet execute(String query) throws SQLException {
		rs = st.executeQuery(query);
		return rs;
	}
	
	// Perform change, input, or delete data query for database
	public void update(String query) throws SQLException {
		st.executeUpdate(query);
	}
	
	public Connection getConnection() {
		return connect;
	}
	
	public void close() throws SQLException {
		connect.close();
	}

}
