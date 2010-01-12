package dataManager.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataManager.dataEntities.NodeBasicInformation;
import dataManager.dataEntities.QuickEstimationRecord;

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
	
	
	public QuickEstimationRecord getQuickEstimationByNodeID(int nodeID){
		try {
			String sqlString="select [estimationID],[nodeID],[dataType],[formulaEffort],[historyEffort],[meanProductivity]," +
					"[stanDevProductivity] from quickEstimation where nodeID="+nodeID;
			ResultSet rs=statement.executeQuery(sqlString);
			if(rs.next()){
				QuickEstimationRecord record=new QuickEstimationRecord();
				record.setEstimationID(rs.getInt("estimationID"));
				record.setNodeID(rs.getInt("nodeID"));
				record.setDataType(rs.getString("dataType"));
				record.setFormulaEffort(rs.getDouble("formulaEffort"));
				record.setHistoryEffort(rs.getDouble("historyEffort"));
				record.setMeanProductivity(rs.getDouble("meanProductivity"));
				record.setStanDevProductivity(rs.getDouble("stanDevProductivity"));
				
				return record;
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return null;
	}
}

