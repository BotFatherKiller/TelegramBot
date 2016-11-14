package su.spb.bstu.a5.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class ScheduleList {

	List<Schedule> scheduleList = new ArrayList<Schedule>();

	public ScheduleList() {
				
		MySQLConnector mySqlConnector = new MySQLConnector();
		Statement mysqlStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnector.connect();

			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			String sql;
			sql = "SELECT * FROM schedule";
			ResultSet rs = mysqlStatement.executeQuery(sql);
			rs = mysqlStatement.executeQuery(sql);

			while (rs.next()) {
				scheduleList.add(new Schedule(rs.getInt("id"), rs.getString("chet"), rs.getString("weekday"),
						rs.getString("type"), rs.getString("predmet"), rs.getString("prepod"), rs.getTime("time1"),
						rs.getString("party"), rs.getString("audit")));
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
