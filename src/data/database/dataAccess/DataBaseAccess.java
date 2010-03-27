package data.database.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;
import data.database.dataEntities.EstimateNode;
import data.database.dataEntities.QuickEstimationRecord;

/**
 * 数据访问基类，存储数据库连接信息，进行连接数据库及释放连接操作
 * 
 * @author Administrator
 * 
 */
public class DataBaseAccess {

	protected Connection connection = null;
	public Statement statement = null;
	private static HashMap<String, Connection> connectionPool = new HashMap<String, Connection>();
	public final static String MAIN_DB_PATH = "./database/database.db3";

	/**
	 * 构造器
	 */
	public DataBaseAccess() {
		this(MAIN_DB_PATH);
	}

	/**
	 * 构造器
	 * 
	 * @param dataBasePath
	 *            数据库文件路径
	 */
	public DataBaseAccess(String dataBasePath) {
		connection = connectionPool.get(dataBasePath);
		try {
			if (connection == null|| connection.isClosed()) {
				String connectionString = "jdbc:sqlite:" + dataBasePath;
				connection = initConnection(connectionString);
				connectionPool.put(dataBasePath, connection);
			}
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
	public static void disposeConnections() {
		try {
			for(Connection conn:connectionPool.values()){
				conn.close();
			}
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

	protected String getTableName() {
		String className = this.getClass().getSimpleName();
		if(className.equals("EstimateNode")){
			return "nodeBasicInfo";
		}else{
			return className.substring(0, className.length() - 6);
		}		
	}

	public ArrayList<Entity> findAllWhere(String condition) throws SQLException {
		ArrayList<Entity> list = new ArrayList<Entity>();
		String sqlString = "select * from " + getTableName() + " where "
				+ condition;
		ResultSet rs = statement.executeQuery(sqlString);
		ResultSetMetaData metaData = rs.getMetaData();
		while (rs.next()) {
			Entity node = getEntity(getTableName());
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnName(i);
				node.set(columnName, rs.getObject(i));
			}
			list.add(node);
		}
		return list;
	}

	/**
	 * 工厂方法
	 * 
	 * @param name
	 *            类名
	 * @return 类的实例
	 */
	private static Entity getEntity(String name) {
		if (name.equals("EstimateNode")||name.equals("NodeBasicInfo"))
			return new EstimateNode();
		else if (name.equals("QuickEstimation"))
			return new QuickEstimationRecord();
		else if (name.equals("CocomoEstimation"))
			return new CocomoEstimationRecord();
		else 
			return new Entity();
	}

	/**
	 * 通过ID获取记录信息
	 * 
	 * @param id
	 *            记录ID
	 * @return
	 */
	public Entity getByID(int id) {
		try {
			ArrayList<Entity> result = findAllWhere("[id]=" + id);
			if (result.size() > 0) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 插入记录
	 * 
	 * @return 插入记录的ID
	 */
	public int insert(Entity node) {
		try {
			StringBuilder attrList = new StringBuilder();
			StringBuilder valueList = new StringBuilder();
			for (String attr : node.attributes.keySet()) {
				attrList.append("[");
				attrList.append(attr);
				attrList.append("],");
				valueList.append("'");
				valueList.append(node.attributes.get(attr));
				valueList.append("',");
			}
			attrList.deleteCharAt(attrList.length() - 1);// 删除最后一个逗号
			valueList.deleteCharAt(valueList.length() - 1);// 删除最后一个逗号
			statement.executeUpdate("insert into " + getTableName() + " ("
					+ attrList + ") values (" + valueList + ")");
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 更新节点信息(节点ID不可变)
	 * 
	 * @param node
	 */
	public void update(Entity node) {
		try {
			StringBuilder sqlString = new StringBuilder("update "
					+ getTableName() + " set ");
			for (String attr : node.attributes.keySet()) {
				Object value = node.attributes.get(attr);
				if(value == null)
					continue;
				sqlString.append("[");
				sqlString.append(attr);
				sqlString.append("]='");
				sqlString.append(value);
				sqlString.append("',");
			}
			sqlString.deleteCharAt(sqlString.length() - 1);
			sqlString.append(" where [id]=" + node.attributes.get("id"));
			statement.executeUpdate(sqlString.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除节点（通过节点ID判断）
	 * 
	 * @param id
	 *            节点ID
	 */
	public void deleteByID(int id) {
		try {
			String sqlString = "delete from " + getTableName() + " where [id]="
					+ id;
			statement.execute(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
