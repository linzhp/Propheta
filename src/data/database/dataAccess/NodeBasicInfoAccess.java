package data.database.dataAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataEntities.Entity;
import data.database.dataEntities.EstimateNode;


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
	 * @throws SQLException 
	 */
	public ArrayList<Entity> getAllRootNodes() throws SQLException{
		ArrayList<Entity> rootNodes = null;
		rootNodes =findAllWhere("[parentID]=-1");
		return rootNodes;
	}
	
	
	
	/**
	 * 获取所有子节点
	 * @param parentID 父节点ID
	 * @return
	 */
	public ArrayList<Entity> getNodesByParentID(int parentID){
		ArrayList<Entity> childNodes=new ArrayList<Entity>();
		try {
			childNodes= findAllWhere("[parentID]="+parentID+" and [id]!="+parentID);
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return childNodes;
	}
	
	
	
	
	/**
	 * 更新节点名称
	 * @param node
	 */
	public void updateNodeName(int nodeID, String newName){
		try{
			String sqlString="update nodeBasicInfo set [name]=? where [id]="+nodeID;
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setString(1, newName);
			preStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
	}
	
	
	public void updateEstType(int nodeID, String EstType) {
		EstimateNode nb = new EstimateNode();
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nb = (EstimateNode)nbi_access.getByID(nodeID);
		nb.attributes.put("estType", EstType);
		nbi_access.update(nb);
	}
}
