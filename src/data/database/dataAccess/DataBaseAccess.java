package data.database.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import data.database.dataEntities.Entity;

/**
 * 数据访问基类，存储数据库连接信息，进行连接数据库及释放连接操作
 * 
 * @author Administrator
 * 
 */
public class DataBaseAccess {

	protected Connection connection = null;
	protected static Connection defaultConnection;
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
	
	
	public ArrayList<Entity> findAllWhere(String condition) throws SQLException{
		ArrayList<Entity> list = new ArrayList<Entity>();
		String sqlString="select * from "+this.getClass().getName()+" where "+condition;
		ResultSet rs=statement.executeQuery(sqlString);
		ResultSetMetaData metaData = rs.getMetaData();
		while(rs.next()){
			Entity node=new Entity();
			for(int i=1;i<=metaData.getColumnCount();i++){
				String columnName = metaData.getColumnName(i);
				node.set(columnName, rs.getObject(columnName));
			}
			list.add(node);
		}
		return list;
	}
	
	/**
	 * 插入记录
	 * @return 插入记录的ID
	 */
	public int insert(Entity node){
		try{
			StringBuilder attrList = new StringBuilder();
			StringBuilder valueList = new StringBuilder();
			for(String attr:node.attributes.keySet())
			{
				attrList.append("[");
				attrList.append(attr);
				attrList.append("],");
				valueList.append("'");
				valueList.append(node.attributes.get(attr));
				valueList.append("',");
			}
			attrList.deleteCharAt(attrList.length()-1);//删除最后一个逗号
			valueList.deleteCharAt(valueList.length()-1);//删除最后一个逗号
			statement.executeUpdate("insert into "+this.getClass().getName()+" ("+attrList+") values ("+valueList+")");
			ResultSet rs=statement.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return -1;
	}
	
	


}
