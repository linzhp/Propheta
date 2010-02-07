package data.database.dataEntities;

import data.database.dataAccess.QuickEstimationAccess;

/**
 * 快速估算记录，用于存储快速估算的输入输出
 * @author Administrator
 *
 */
public class QuickEstimationRecord extends Entity{

	/**
	 *  构造器
	 */
	public QuickEstimationRecord(){
		
	}
	
	
	/**
	 * 构造器
	 * @param estimateID
	 * @param nodeID
	 */
	public QuickEstimationRecord(int estimationID, int nodeID){
		set("estimationID",estimationID);
		set("nodeID",nodeID);
	}
	
	// 更新某条quickEstimation数据
	public static void saveQuickEstimation(int nodeID, String dataType,
			Double formula_Effort, Double historyEffort,
			Double meanProductivity, Double stanDevProductivity) {
		QuickEstimationRecord qer = new QuickEstimationRecord();
		QuickEstimationAccess qer_access = new QuickEstimationAccess();
		qer = qer_access.getQuickEstimationByNodeID(nodeID);

		qer.set("dataType",dataType);
		qer.set("formulaEffort",formula_Effort);
		qer.set("historyEffort",historyEffort);
		qer.set("meanProductivity",meanProductivity);
		qer.set("stanDevProductivity",stanDevProductivity);

		qer_access.updateQuickEstimation(qer);
	}

}
