package dataManager.dataEntities;

import dataManager.dataAccess.QuickEstimationAccess;

/**
 * 快速估算记录，用于存储快速估算的输入输出
 * @author Administrator
 *
 */
public class QuickEstimationRecord {

	private int estimationID=-1;
	private int nodeID=-1;
	private String dataType=null;
	private double formulaEffort=-1;
	private double historyEffort=-1;
	private double meanProductivity=-1;
	private double stanDevProductivity=-1;
	
	
	public int getEstimationID() {
		return estimationID;
	}
	public void setEstimationID(int estimationID) {
		this.estimationID = estimationID;
	}
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public double getFormulaEffort() {
		return formulaEffort;
	}
	public void setFormulaEffort(double formulaEffort) {
		this.formulaEffort = formulaEffort;
	}
	public double getHistoryEffort() {
		return historyEffort;
	}
	public void setHistoryEffort(double historyEffort) {
		this.historyEffort = historyEffort;
	}
	public double getMeanProductivity() {
		return meanProductivity;
	}
	public void setMeanProductivity(double meanProductivity) {
		this.meanProductivity = meanProductivity;
	}
	public double getStanDevProductivity() {
		return stanDevProductivity;
	}
	public void setStanDevProductivity(double stanDevProductivity) {
		this.stanDevProductivity = stanDevProductivity;
	}
	
	
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
		this.estimationID=estimationID;
		this.nodeID=nodeID;
	}
	
	// 更新某条quickEstimation数据
	public static void saveQuickEstimation(int nodeID, String dataType,
			Double formula_Effort, Double historyEffort,
			Double meanProductivity, Double stanDevProductivity) {
		QuickEstimationRecord qer = new QuickEstimationRecord();
		QuickEstimationAccess qer_access = new QuickEstimationAccess();
		qer_access.initConnection();
		qer = qer_access.getQuickEstimationByNodeID(nodeID);

		qer.setDataType(dataType);
		qer.setFormulaEffort(formula_Effort);
		qer.setHistoryEffort(historyEffort);
		qer.setMeanProductivity(meanProductivity);
		qer.setStanDevProductivity(stanDevProductivity);

		qer_access.updateQuickEstimation(qer);
		qer_access.disposeConnection();
	}

}
