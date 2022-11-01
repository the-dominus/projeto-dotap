package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	static Connection _connection;

	public static Connection getConnection() throws SQLException, ClassNotFoundException {

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/dotap_db?useTimezone=true&serverTimezone=UTC";
		String user = "root";
		String password = "root";

		Class.forName(driver);

		_connection = DriverManager.getConnection(url, user, password);

		return _connection;
	}
}