package dataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {

	Connection connect = null;
	ResultSet resultSet = null;
	Statement statement = null;

	public DataAccess() {
		String dataBasePath = "./database/database.db3";
		try {
			Class.forName("org.sqlite.JDBC");
			connect = DriverManager
					.getConnection("jdbc:sqlite:" + dataBasePath);
			statement = connect.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert() {
		try {
			//System.out.println("connected!");
			//statement.execute("insert into prod_BusinessArea values(10,'hello')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String sql) {
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public void close() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connect != null)
				connect.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
