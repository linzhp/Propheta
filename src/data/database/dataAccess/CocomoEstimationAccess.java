package data.database.dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;

/**
 * 数据访问类，用于cocomoEstimation表的存取操作
 * @author Administrator
 *
 */
public class CocomoEstimationAccess extends DataBaseAccess{

	
	public CocomoEstimationAccess(){
		
	}
	
	public CocomoEstimationAccess(String dataBasePath){
		super(dataBasePath);
	}
	
	
	/**
	 * 通过节点ID获取cocomo估算记录
	 * @param nodeID 节点ID
	 * @return
	 */
	public CocomoEstimationRecord getCocomoEstimationByNodeID(int nodeID){
		try {
			ArrayList<Entity> result = findAllWhere("[nodeID]="+nodeID);
			if(result.size()>0){
				return (CocomoEstimationRecord)result.get(0);
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return null;
	}
	
	
	
	
	public void deleteCocomoEstimationByNodeID(int nodeID){
		try{
			String sqlString="delete from cocomoEstimation where [nodeID]="+nodeID;
			statement.execute(sqlString);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
