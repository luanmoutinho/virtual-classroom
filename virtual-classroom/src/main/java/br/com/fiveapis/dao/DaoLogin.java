package br.com.fiveapis.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import br.com.fiveapis.connection.SingleConnection;
import br.com.fiveapis.model.ModelLogin;

public class DaoLogin {
	
	private Connection connection;
	
	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}
	

	public boolean validarLogin(ModelLogin modelLogin) throws SQLException {
			
		
		String sql = "SELECT * FROM userLogin WHERE email = ? AND userPassword = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getEmail());
		statement.setString(2, modelLogin.getUserPassword());
		
		ResultSet resultset = statement.executeQuery();
		if(resultset.next()) {
			return true;
		}
		
		
		return false;

	}

}
