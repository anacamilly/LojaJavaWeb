package web.ufrn.demo.DAOS;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {

	public static Connection getConnection() throws SQLException, URISyntaxException {
		String dbUri = "localhost";
		String dbPort = "5432";
		String dbName = "loja";

		String username = "postgres";
		String password = "postgres";
		String dbUrl = "jdbc:postgresql://" + dbUri + ':' + dbPort + "/" + dbName + "?serverTimezone=UTC";

		return DriverManager.getConnection(dbUrl, username, password);
	}
}