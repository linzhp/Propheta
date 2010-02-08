package data.database.dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataEntities.Entity;
import data.database.dataEntities.QuickEstimationRecord;

/**
 * 数据访问类，用于quickEstimation表的存取操作
 * @author Administrator
 *
 */
public class QuickEstimationAccess extends DataBaseAccess{

	public QuickEstimationAccess(){
		
	}
	
	
	public QuickEstimationAccess(String dataBasePath){
		super(dataBasePath);
	}
	
	
	/**
	 * 通过节点ID获取相应的快速估算记录
	 * @param nodeID 估算节点ID
	 * @return
	 */
	public QuickEstimationRecord getQuickEstimationByNodeID(int nodeID){
		try {
			ArrayList<Entity> result = findAllWhere("[nodeID]="+nodeID);
			if(result.size()>0){
				return (QuickEstimationRecord)result.get(0);
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return null;
	}
	
	
	/**
	 * 通过节点ID删除快速估算记录
	 * @param nodeID
	 */
	public void deleteQuickEstimationByNodeID(int nodeID){
		try{
			String sqlString="delete from quickEstimation where [nodeID]="+nodeID;
			statement.execute(sqlString);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}

