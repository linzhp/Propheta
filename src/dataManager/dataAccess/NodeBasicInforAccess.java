package dataManager.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import dataManager.dataEntities.NodeBasicInformation;


/**
 * 数据库访问类，用于对NodeBaiscInfor表进行存取操作
 * @author Administrator
 *
 */
public class NodeBasicInforAccess {

	private String connectionString="jdbc:sqlite:./database/database.db3";  //数据库连接字符串
	private Connection connection=null;
	private Statement statement=null;
	
	/**
	 * 构造器
	 */
	public NodeBasicInforAccess(){
		
	}
	
	
	/**
	 * 构造器
	 * @param dataBasePath 数据库连接字符串
	 */
	public NodeBasicInforAccess(String dataBasePath){
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
	public void diposeConnection(){
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
	
	
	/**
	 * 获取所有节点信息
	 * @return
	 */
	public ArrayList<NodeBasicInformation> getAllNodes(){
		return null;
		
	}
	
	
	/**
	 * 获取所有根节点
	 * @return
	 */
	public ArrayList<NodeBasicInformation> getAllRootNodes(){
		return null;
		
	}
	
	
	/**
	 * 通过ID获取节点信息
	 * @param nodeID  节点ID
	 * @return
	 */
	public NodeBasicInformation getNodeByID(int nodeID){
		return null;
	}
	
	
	/**
	 * 获取所有子节点
	 * @param parentID 父节点ID
	 * @return
	 */
	public ArrayList<NodeBasicInformation> getNodesByParentID(int parentID){
		return null;
	}
	
	
	
	
	
}
