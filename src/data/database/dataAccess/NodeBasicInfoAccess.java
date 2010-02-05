package data.database.dataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataEntities.NodeBasicInformation;


/**
 * 数据库访问类，用于对NodeBaiscInfo表进行存取操作
 * @author Administrator
 *
 */
public class NodeBasicInfoAccess extends DataBaseAccess{

	
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
		super(dataBasePath);
	}
	
	
	
	/**
	 * 获取所有根节点
	 * @return
	 */
	public ArrayList<NodeBasicInformation> getAllRootNodes(){
		ArrayList<NodeBasicInformation> rootNodes=new ArrayList<NodeBasicInformation>();
		try {
			rootNodes =findAllWhere("[parentID]=-1");
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
			ArrayList<NodeBasicInformation> result = findAllWhere("[nodeID]="+nodeID);
			if(result.size()>0){
				return result.get(0);
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
			childNodes= findAllWhere("[parentID]="+parentID+" and [nodeID]!="+parentID);
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return childNodes;
	}
	
	
	public ArrayList<NodeBasicInformation> findAllWhere(String condition) throws SQLException{
		ArrayList<NodeBasicInformation> list = new ArrayList<NodeBasicInformation>();
		String sqlString="select * from nodeBasicInfo where "+condition;
		ResultSet rs=statement.executeQuery(sqlString);
		ResultSetMetaData metaData = rs.getMetaData();
		while(rs.next()){
			NodeBasicInformation node=new NodeBasicInformation();
			for(int i=1;i<=metaData.getColumnCount();i++){
				String columnName = metaData.getColumnName(i);
				node.attributes.put(columnName, rs.getObject(columnName));
			}
			list.add(node);
		}
		return list;
	}
	
	/**
	 * 插入节点
	 * @param node
	 * @return 插入节点的ID
	 */
	public int insertNode(NodeBasicInformation node){
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
			statement.executeUpdate("insert into nodeBasicInfo ("+attrList+") values ("+valueList+")");
			ResultSet rs=statement.getGeneratedKeys();
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
			StringBuilder sqlString= new StringBuilder("update nodeBasicInfo set ");
			for(String attr:node.attributes.keySet()){
				sqlString.append("[");
				sqlString.append(attr);
				sqlString.append("]='");
				sqlString.append(node.attributes.get(attr));
				sqlString.append("',");
			}
			sqlString.deleteCharAt(sqlString.length()-1);
			sqlString.append(" where [nodeID]="+node.attributes.get("nodeID"));
			statement.executeUpdate(sqlString.toString());
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
