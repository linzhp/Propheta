package data.database.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据访问基类，存储数据库连接信息，进行连接数据库及释放连接操作
 * 
 * @author Administrator
 * 
 */
public class DataBaseAccess {

	protected Connection connection = null;
	private static Connection defaultConnection;
	protected Statement statement = null;

	/**
	 * 构造器
	 */
	public DataBaseAccess() {
		try {
			if (defaultConnection == null) {
				defaultConnection = initConnection("jdbc:sqlite:./database/database.db3");
			}
			connection = defaultConnection;
			statement = defaultConnection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造器
	 * 
	 * @param dataBasePath
	 *            数据库文件路径
	 */
	public DataBaseAccess(String dataBasePath) {
		String connectionString = "jdbc:sqlite:" + dataBasePath;
		try {
			connection = initConnection(connectionString);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接数据库
	 * 
	 * @throws Exception
	 */
	private Connection initConnection(String connectionString) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection(connectionString);
		return connection;
	}

	/**
	 * 关闭数据库连接，程序结束后将自动调用此方法
	 */
	public static void disposeConnection() {
		try {
			defaultConnection.close();
			defaultConnection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String sql) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}
