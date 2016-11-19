package su.spb.bstu.a5.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserList {

	public List<User> users = new ArrayList<User>();

	public void updateActiveUsers() {

		this.users.clear();
		MySQLConnector mySqlConnector = new MySQLConnector();
		Statement mysqlStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnector.connect();

			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			String sql;
			sql = "SELECT * FROM bot_users where active=1;";
			ResultSet rs = mysqlStatement.executeQuery(sql);
			rs = mysqlStatement.executeQuery(sql);

			while (rs.next()) {
				users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("chatID"),
						rs.getString("groupname")));
			}
			rs.close();
			mysqlStatement.close();

			mySqlConnector.closeConnection();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (mysqlStatement != null)
					mysqlStatement.close();
			} catch (SQLException se2) {
			}
			if (mySqlConnector.getMysqlConnection() != null)
				mySqlConnector.closeConnection();
		}
	}

	@Override
	public String toString() {
		return "Users [users=" + users + "]";
	}
}
