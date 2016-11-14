package su.spb.bstu.a5.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Groups {

	public List<String> groupList = new ArrayList<String>();

	public Groups() {
	
		MySQLConnector mySqlConnector = new MySQLConnector();
		Statement mysqlStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnector.connect();

			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			String sql;
			sql = "SELECT * FROM schedule_party";
			ResultSet rs = mysqlStatement.executeQuery(sql);
			rs = mysqlStatement.executeQuery(sql);

			while (rs.next()) {
				groupList.add(rs.getString("gruppa"));
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

}
