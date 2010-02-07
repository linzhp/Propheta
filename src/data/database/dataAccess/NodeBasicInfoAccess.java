package data.database.dataAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataEntities.Entity;
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
	public ArrayList<Entity> getAllRootNodes(){
		ArrayList<Entity> rootNodes = null;
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
			ArrayList<Entity> result = findAllWhere("[nodeID]="+nodeID);
			if(result.size()>0){
				return (NodeBasicInformation)result.get(0);
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
	public ArrayList<Entity> getNodesByParentID(int parentID){
		ArrayList<Entity> childNodes=new ArrayList<Entity>();
		try {
			childNodes= findAllWhere("[parentID]="+parentID+" and [nodeID]!="+parentID);
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return childNodes;
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
