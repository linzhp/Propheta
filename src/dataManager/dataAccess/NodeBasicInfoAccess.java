package dataManager.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataManager.dataEntities.NodeBasicInformation;


/**
 * 数据库访问类，用于对NodeBaiscInfo表进行存取操作
 * @author Administrator
 *
 */
public class NodeBasicInfoAccess {

	private String connectionString="jdbc:sqlite:./database/database.db3";  //数据库连接字符串
	private Connection connection=null;
	private Statement statement=null;
	
	/**
	 * 构造器
	 */
	public NodeBasicInfoAccess(){
		
	}
	
	
	/**
	 * 构造器
	 * @param dataBasePath 数据库连接字符串
	 */
	public NodeBasicInfoAccess(String dataBasePath){
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
		ArrayList<NodeBasicInformation> nodes=new ArrayList<NodeBasicInformation>();
		try {
			String sqlString="select [nodeID],[parentID],[name],[description],[businessArea]," +
				"[SLOC],[functionPoints],[developmentType],[language],[languageType],[developmentPlatform]," +
				"[developmentTechniques],[teamSize,[duration],[estEffort],[estPDR],[estProductivity]," +
				"[estPM],[estPersons],[cocomoEM],[cocomoSCED],[isRoot] " +
				"from nodeBasicInfo";
			ResultSet rs=statement.executeQuery(sqlString);
			while(rs.next()){
				NodeBasicInformation node=new NodeBasicInformation();
				node.setNodeID(rs.getInt("nodeID"));
				node.setParentID(rs.getInt("parentID"));
				node.setName(rs.getString("name"));
				node.setDescription(rs.getString("description"));
				node.setBusinessArea(rs.getString("businessArea"));
				node.setSLOC(rs.getInt("SLOC"));
				node.setFunctionPoints(rs.getInt("functionPoints"));
				node.setDevelopmentType(rs.getString("developmentType"));
				node.setLanguage(rs.getString("language"));
				node.setLanguageType(rs.getString("languageType"));
				node.setDevelopmentPlatform(rs.getString("developmentPlatform"));
				node.setDevelopmentTechniques(rs.getString("developmentTechniques"));
				node.setTeamSize(rs.getDouble("teamSize"));
				node.setDuration(rs.getInt("duration"));
				node.setEstEffort(rs.getDouble("estEffort"));
				node.setEstPDR(rs.getDouble("estPDR"));
				node.setEstProductivity(rs.getDouble("estProductivity"));
				node.setEstPM(rs.getDouble("estPM"));
				node.setEstPersons(rs.getInt("estPersons"));
				//node.setEstOthersInfo
				node.setCocomoEM(rs.getDouble("cocomoEM"));
				node.setCocomoSCED(rs.getDouble("cocomoSCED"));
				node.setIsRoot(rs.getBoolean("isRoot"));
				nodes.add(node);
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return nodes;
	}
	
	
	/**
	 * 获取所有根节点
	 * @return
	 */
	public ArrayList<NodeBasicInformation> getAllRootNodes(){
		ArrayList<NodeBasicInformation> rootNodes=new ArrayList<NodeBasicInformation>();
		try {
			String sqlString="select [nodeID],[parentID],[name],[description],[businessArea]," +
				"[SLOC],[functionPoints],[developmentType],[language],[languageType],[developmentPlatform]," +
				"[developmentTechniques],[teamSize],[duration],[estEffort],[estPDR],[estProductivity]," +
				"[estPM],[estPersons],[cocomoEM],[cocomoSCED],[isRoot] " +
				"from nodeBasicInfo where [isRoot]=1";
			ResultSet rs=statement.executeQuery(sqlString);
			while(rs.next()){
				NodeBasicInformation node=new NodeBasicInformation();
				node.setNodeID(rs.getInt("nodeID"));
				node.setParentID(rs.getInt("parentID"));
				node.setName(rs.getString("name"));
				node.setDescription(rs.getString("description"));
				node.setBusinessArea(rs.getString("businessArea"));
				node.setSLOC(rs.getInt("SLOC"));
				node.setFunctionPoints(rs.getInt("functionPoints"));
				node.setDevelopmentType(rs.getString("developmentType"));
				node.setLanguage(rs.getString("language"));
				node.setLanguageType(rs.getString("languageType"));
				node.setDevelopmentPlatform(rs.getString("developmentPlatform"));
				node.setDevelopmentTechniques(rs.getString("developmentTechniques"));
				node.setTeamSize(rs.getDouble("teamSize"));
				node.setDuration(rs.getInt("duration"));
				node.setEstEffort(rs.getDouble("estEffort"));
				node.setEstPDR(rs.getDouble("estPDR"));
				node.setEstProductivity(rs.getDouble("estProductivity"));
				node.setEstPM(rs.getDouble("estPM"));
				node.setEstPersons(rs.getInt("estPersons"));
				//node.setEstOthersInfo
				node.setCocomoEM(rs.getDouble("cocomoEM"));
				node.setCocomoSCED(rs.getDouble("cocomoSCED"));
				node.setIsRoot(rs.getBoolean("isRoot"));
				rootNodes.add(node);
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return rootNodes;
	}
	
	
	/**
	 * 通过ID获取节点信息
	 * @param nodeID  节点ID
	 * @return
	 */
	public NodeBasicInformation getNodeByID(int nodeID){
		try {
			String sqlString="select [nodeID],[parentID],[name],[description],[businessArea]," +
				"[SLOC],[functionPoints],[developmentType],[language],[languageType],[developmentPlatform]," +
				"[developmentTechniques],[teamSize],[duration],[estEffort],[estPDR],[estProductivity]," +
				"[estPM],[estPersons],[cocomoEM],[cocomoSCED],[isRoot] " +
				"from nodeBasicInfo where [nodeID]="+nodeID;
			ResultSet rs=statement.executeQuery(sqlString);
			if(rs.next()){
				NodeBasicInformation node=new NodeBasicInformation();
				node.setNodeID(rs.getInt("nodeID"));
				node.setParentID(rs.getInt("parentID"));
				node.setName(rs.getString("name"));
				node.setDescription(rs.getString("description"));
				node.setBusinessArea(rs.getString("businessArea"));
				node.setSLOC(rs.getInt("SLOC"));
				node.setFunctionPoints(rs.getInt("functionPoints"));
				node.setDevelopmentType(rs.getString("developmentType"));
				node.setLanguage(rs.getString("language"));
				node.setLanguageType(rs.getString("languageType"));
				node.setDevelopmentPlatform(rs.getString("developmentPlatform"));
				node.setDevelopmentTechniques(rs.getString("developmentTechniques"));
				node.setTeamSize(rs.getDouble("teamSize"));
				node.setDuration(rs.getInt("duration"));
				node.setEstEffort(rs.getDouble("estEffort"));
				node.setEstPDR(rs.getDouble("estPDR"));
				node.setEstProductivity(rs.getDouble("estProductivity"));
				node.setEstPM(rs.getDouble("estPM"));
				node.setEstPersons(rs.getInt("estPersons"));
				//node.setEstOthersInfo
				node.setCocomoEM(rs.getDouble("cocomoEM"));
				node.setCocomoSCED(rs.getDouble("cocomoSCED"));
				node.setIsRoot(rs.getBoolean("isRoot"));
				return node;
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return null;
	}
	
	
	/**
	 * 获取所有子节点
	 * @param parentID 父节点ID
	 * @return
	 */
	public ArrayList<NodeBasicInformation> getNodesByParentID(int parentID){
		ArrayList<NodeBasicInformation> childNodes=new ArrayList<NodeBasicInformation>();
		try {
			String sqlString="select [nodeID],[parentID],[name],[description],[businessArea]," +
				"[SLOC],[functionPoints],[developmentType],[language],[languageType],[developmentPlatform]," +
				"[developmentTechniques],[teamSize],[duration],[estEffort],[estPDR],[estProductivity]," +
				"[estPM],[estPersons],[cocomoEM],[cocomoSCED],[isRoot] " +
				"from nodeBasicInfo where [parentID]="+parentID+" and [nodeID]!="+parentID;
			ResultSet rs=statement.executeQuery(sqlString);
			while(rs.next()){
				NodeBasicInformation node=new NodeBasicInformation();
				node.setNodeID(rs.getInt("nodeID"));
				node.setParentID(rs.getInt("parentID"));
				node.setName(rs.getString("name"));
				node.setDescription(rs.getString("description"));
				node.setBusinessArea(rs.getString("businessArea"));
				node.setSLOC(rs.getInt("SLOC"));
				node.setFunctionPoints(rs.getInt("functionPoints"));
				node.setDevelopmentType(rs.getString("developmentType"));
				node.setLanguage(rs.getString("language"));
				node.setLanguageType(rs.getString("languageType"));
				node.setDevelopmentPlatform(rs.getString("developmentPlatform"));
				node.setDevelopmentTechniques(rs.getString("developmentTechniques"));
				node.setTeamSize(rs.getDouble("teamSize"));
				node.setDuration(rs.getInt("duration"));
				node.setEstEffort(rs.getDouble("estEffort"));
				node.setEstPDR(rs.getDouble("estPDR"));
				node.setEstProductivity(rs.getDouble("estProductivity"));
				node.setEstPM(rs.getDouble("estPM"));
				node.setEstPersons(rs.getInt("estPersons"));
				//node.setEstOthersInfo
				node.setCocomoEM(rs.getDouble("cocomoEM"));
				node.setCocomoSCED(rs.getDouble("cocomoSCED"));
				node.setIsRoot(rs.getBoolean("isRoot"));
				childNodes.add(node);
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return childNodes;
	}
	
	
	/**
	 * 插入节点
	 * @param node
	 * @return 插入节点的ID
	 */
	public int insertNode(NodeBasicInformation node){
		try{
			String sqlString="insert into nodeBasicInfo ([parentID],[name],[description],[businessArea]," +
				"[SLOC],[functionPoints],[developmentType],[language],[languageType],[developmentPlatform],"+
				"[developmentTechniques],[teamSize],[duration],[estEffort],[estPDR],[estProductivity]," +
				"[estPM],[estPersons],[cocomoEM],[cocomoSCED],[isRoot]) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setInt(1, node.getParentID());
			preStatement.setString(2, node.getName());
			preStatement.setString(3, node.getDescription());
			preStatement.setString(4, node.getBusinessArea());
			preStatement.setInt(5, node.getSLOC());
			preStatement.setInt(6, node.getFunctionPoints());
			preStatement.setString(7, node.getDevelopmentType());
			preStatement.setString(8, node.getLanguage());
			preStatement.setString(9, node.getLanguageType());
			preStatement.setString(10, node.getDevelopmentPlatform());
			preStatement.setString(11, node.getDevelopmentTechniques());
			preStatement.setDouble(12, node.getTeamSize());
			preStatement.setInt(13, node.getDuration());
			preStatement.setDouble(14, node.getEstEffort());
			preStatement.setDouble(15, node.getEstPDR());
			preStatement.setDouble(16, node.getEstProductivity());
			preStatement.setDouble(17, node.getEstPM());
			preStatement.setInt(18, node.getEstPersons());
			preStatement.setDouble(19, node.getCocomoEM());
			preStatement.setDouble(20, node.getCocomoSCED());
			preStatement.setBoolean(21, node.getIsRoot());
			
			preStatement.execute();
			ResultSet rs=preStatement.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return -1;
	}
	
	
	/**
	 * 更新节点信息(节点ID不可变)
	 * @param node
	 */
	public void updateNode(NodeBasicInformation node){
		try{
			String sqlString="update nodeBasicInfo set [parentID]=?,[name]=?,[description]=?,[businessArea]=?," +
				"[SLOC]=?,[functionPoints]=?,[developmentType]=?,[language]=?,[languageType]=?,[developmentPlatform]=?," +
				"[developmentTechniques]=?,[teamSize]=?,[duration]=?,[estEffort]=?,[estPDR]=?,[estProductivity]=?," +
				"[estPM]=?,[estPersons]=?,[cocomoEM]=?,[cocomoSCED]=?,[isRoot]=? where [nodeID]="+node.getNodeID();
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setInt(1, node.getParentID());
			preStatement.setString(2, node.getName());
			preStatement.setString(3, node.getDescription());
			preStatement.setString(4, node.getBusinessArea());
			preStatement.setInt(5, node.getSLOC());
			preStatement.setInt(6, node.getFunctionPoints());
			preStatement.setString(7, node.getDevelopmentType());
			preStatement.setString(8, node.getLanguage());
			preStatement.setString(9, node.getLanguageType());
			preStatement.setString(10, node.getDevelopmentPlatform());
			preStatement.setString(11, node.getDevelopmentTechniques());
			preStatement.setDouble(12, node.getTeamSize());
			preStatement.setInt(13, node.getDuration());
			preStatement.setDouble(14, node.getEstEffort());
			preStatement.setDouble(15, node.getEstPDR());
			preStatement.setDouble(16, node.getEstProductivity());
			preStatement.setDouble(17, node.getEstPM());
			preStatement.setInt(18, node.getEstPersons());
			preStatement.setDouble(19, node.getCocomoEM());
			preStatement.setDouble(20, node.getCocomoSCED());
			preStatement.setBoolean(21, node.getIsRoot());
			
			preStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
	}
	
	
	/**
	 * 更新节点名称
	 * @param node
	 */
	public void updateNodeName(int nodeID, String newName){
		try{
			String sqlString="update nodeBasicInfo set [name]=? where [nodeID]="+nodeID;
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setString(1, newName);
			preStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
	}
	
	
	/**
	 * 删除节点（通过节点ID判断）
	 * @param nodeID 节点ID
	 */
	public void deleteNodeByNodeID(int nodeID){
		try{
			String sqlString="delete from nodeBasicInfo where [nodeID]="+nodeID;
			statement.execute(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
	}
	
	
}
