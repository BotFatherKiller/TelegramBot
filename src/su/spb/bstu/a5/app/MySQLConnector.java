package su.spb.bstu.a5.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

	private Connection mysqlConnection = null;
	String driver = "com.mysql.jdbc.Driver";
	String database = "jdbc:mysql://195.133.146.90/a5new?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String password = "easy4Rtz";

	public MySQLConnector(String driver, String database, String user, String password) {
		this.driver = driver;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	public MySQLConnector() {
	}

	public void connect() {
		try {
			this.mysqlConnection = DriverManager.getConnection(this.database, this.user, this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			this.mysqlConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Connection getMysqlConnection() {
		return mysqlConnection;
	}

	public void setMysqlConnection(Connection mysqlConnection) {
		this.mysqlConnection = mysqlConnection;
	}

}
