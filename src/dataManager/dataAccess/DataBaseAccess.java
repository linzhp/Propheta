package dataManager.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 数据访问基类，存储数据库连接信息，进行连接数据库及释放连接操作
 * @author Administrator
 *
 */
public class DataBaseAccess {

	protected String connectionString="jdbc:sqlite:./database/database.db3";  //数据库连接字符串
	protected Connection connection=null;
	protected Statement statement=null;
	
	
	/**
	 * 构造器
	 */
	public DataBaseAccess(){
		
	}
	
	
	/**
	 * 构造器
	 * @param dataBasePath 数据库连接字符串
	 */
	public DataBaseAccess(String dataBasePath){
		this.connectionString="jdbc:sqlite:"+dataBasePath;
		
	}
	
	
	/**
	 * 连接数据库
	 */
	public void initConnection(){
		try{
			Class.forName("org.sqlite.JDBC");
			this.connection=DriverManager.getConnection(this.connectionString);
			this.statement=this.connection.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 关闭数据库连接
	 * (在调用NodeBasicInforAccess对象完成数据库操作后，应立即调用本方法释放数据库连接)
	 */
	public void disposeConnection(){
		try{
			if(this.statement!=null){
				this.statement.close();
			}
			if(this.connection!=null){
				this.connection=null;
			}
		}catch(Exception e){
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
		ResultSet resultSet=null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
}
