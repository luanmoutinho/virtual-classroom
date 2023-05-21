package br.com.fiveapis.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SingleConnection {

	private static String url = "jdbc:mysql://localhost:3306/bd_virtualRoom?autoReconnect=true";
	private static String user = "root";
	private static String password = "301392Foxlu@n";
	private static Connection connection = null;

	public static Connection getConnection() {
		return connection;
	}

	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		
		
		try {
			if(connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
