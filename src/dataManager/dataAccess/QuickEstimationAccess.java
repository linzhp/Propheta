package dataManager.dataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	
	/**
	 * 通过快速估算记录ID获取相应的快速估算记录
	 * @param recordID 记录ＩＤ
	 * @return
	 */
	public QuickEstimationRecord getQuickEstimationByEstimationID(int estimationID){
		try {
			String sqlString="select [estimationID],[nodeID],[dataType],[formulaEffort],[historyEffort],[meanProductivity]," +
					"[stanDevProductivity] from quickEstimation where [estimationID]="+estimationID;
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
	
	
	/**
	 * 通过节点ID获取相应的快速估算记录
	 * @param nodeID 估算节点ID
	 * @return
	 */
	public QuickEstimationRecord getQuickEstimationByNodeID(int nodeID){
		try {
			String sqlString="select [estimationID],[nodeID],[dataType],[formulaEffort],[historyEffort],[meanProductivity]," +
					"[stanDevProductivity] from quickEstimation where [nodeID]="+nodeID;
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
	
	
	/**
	 * 插入一条快速估算记录，并返回分配的记录ID号
	 * @param record
	 * @return
	 */
	public int insertQuickEstimation(QuickEstimationRecord record){
		try{
			String sqlString="insert into quickEstimation ([nodeID],[dataType],[formulaEffort],[historyEffort],[meanProductivity]," +
					"[stanDevProductivity]) values (?,?,?,?,?,?)";
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setInt(1, record.getNodeID());
			preStatement.setString(2, record.getDataType());
			preStatement.setDouble(3, record.getFormulaEffort());
			preStatement.setDouble(4, record.getHistoryEffort());
			preStatement.setDouble(5, record.getMeanProductivity());
			preStatement.setDouble(6, record.getStanDevProductivity());
			
			preStatement.execute();
			ResultSet rs=preStatement.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * 更新快速估算记录 
	 * @param record
	 */
	public void updateQuickEstimation(QuickEstimationRecord record){
		try{
			String sqlString="update quickEstimation set [nodeID]=?,[dataType]=?,[formulaEffort]=?,[historyEffort]=?,[meanProductivity]=?," +
			"[stanDevProductivity]=? where [estimationID]="+record.getEstimationID();
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setInt(1, record.getNodeID());
			preStatement.setString(2, record.getDataType());
			preStatement.setDouble(3, record.getFormulaEffort());
			preStatement.setDouble(4, record.getHistoryEffort());
			preStatement.setDouble(5, record.getMeanProductivity());
			preStatement.setDouble(6, record.getStanDevProductivity());
			
			preStatement.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通过记录ID删除快速估算记录
	 * @param recordID
	 */
	public void deleteQuickEstimationByEstimationID(int estimationID){
		try{
			String sqlString="delete from quickEstimation where [estimationID]="+estimationID;
			statement.execute(sqlString);
		}catch(SQLException e){
			e.printStackTrace();
		}
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

